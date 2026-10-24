//! NPC batch validation and authoritative ordered movement/trajectory updates.
use crate::{EntityUpdateError, Locomotion, NpcEntity, WorldEntitiesState};
use rustclient_model::{ClientCycle, MoveSpeed, NpcInfoUpdate, NpcMovement, TileOffset, WorldTile};

/// Latest ordered path request, distinct from the NPC's confirmed endpoint.
/// Serial distinguishes repeated requests, including a run returning to its start.
/// Renderers consume each serial once; creating a projection starts at the current tile.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct NpcPath {
    pub serial: u64,
    pub first: WorldTile,
    pub second: Option<WorldTile>,
    pub speed: MoveSpeed,
}
impl NpcPath {
    #[must_use]
    pub fn endpoint(self) -> WorldTile {
        self.second.unwrap_or(self.first)
    }
}
fn path(npc: &NpcEntity, movement: &NpcMovement) -> Result<NpcPath, EntityUpdateError> {
    if !matches!(
        movement.speed,
        MoveSpeed::Walk | MoveSpeed::Crawl | MoveSpeed::Run
    ) || (movement.second.is_some() != (movement.speed == MoveSpeed::Run))
    {
        return Err(EntityUpdateError::InvalidMovement);
    }
    let offset = |tile: WorldTile, step: TileOffset| {
        if !(-1..=1).contains(&step.x)
            || !(-1..=1).contains(&step.y)
            || step == (TileOffset { x: 0, y: 0 })
        {
            return Err(EntityUpdateError::InvalidMovement);
        }
        Ok(WorldTile::new(
            tile.x
                .checked_add(step.x)
                .ok_or(EntityUpdateError::PositionOverflow)?,
            tile.y
                .checked_add(step.y)
                .ok_or(EntityUpdateError::PositionOverflow)?,
            tile.plane,
        ))
    };
    let first = offset(npc.tile, movement.first)?;
    Ok(NpcPath {
        serial: npc
            .path
            .map_or(Some(1), |p| p.serial.checked_add(1))
            .ok_or(EntityUpdateError::InvalidNpcBatch)?,
        first,
        second: movement
            .second
            .map(|step| offset(first, step))
            .transpose()?,
        speed: movement.speed,
    })
}
fn orientation(step: TileOffset) -> u16 {
    match (step.x, step.y) {
        (-1, -1) => 256,
        (0, -1) => 0,
        (1, -1) => 1792,
        (-1, 0) => 512,
        (1, 0) => 1536,
        (-1, 1) => 768,
        (0, 1) => 1024,
        _ => 1280,
    }
}
impl WorldEntitiesState {
    pub(crate) fn validate_npc_info(
        &self,
        update: &NpcInfoUpdate,
        origin: WorldTile,
        cycle: ClientCycle,
    ) -> Result<(), EntityUpdateError> {
        use rustclient_model::NpcProperty;
        use std::collections::BTreeSet;
        if update.new_npcs.len()
            + update.movements.len()
            + update.attributes.len()
            + update.removals.len()
            > 4096
        {
            return Err(EntityUpdateError::InvalidNpcBatch);
        }
        let removed: BTreeSet<_> = update.removals.iter().copied().collect();
        if removed.len() != update.removals.len()
            || removed.iter().any(|id| !self.npcs.contains_key(id))
        {
            return Err(EntityUpdateError::InvalidNpcBatch);
        }
        self.npc_serial
            .checked_add(
                u64::try_from(update.new_npcs.len())
                    .map_err(|_| EntityUpdateError::InvalidNpcBatch)?,
            )
            .ok_or(EntityUpdateError::InvalidNpcBatch)?;
        let mut added = BTreeSet::new();
        for npc in &update.new_npcs {
            if npc.orientation >= rustclient_model::JAU_FULL_TURN {
                return Err(EntityUpdateError::InvalidNpcBatch);
            }
            if !added.insert(npc.index)
                || self.npcs.contains_key(&npc.index) && !removed.contains(&npc.index)
            {
                return Err(EntityUpdateError::InvalidNpcBatch);
            }
            origin
                .x
                .checked_add(npc.delta_x)
                .ok_or(EntityUpdateError::PositionOverflow)?;
            origin
                .y
                .checked_add(npc.delta_y)
                .ok_or(EntityUpdateError::PositionOverflow)?;
        }
        if self.npcs.len() - removed.len() + added.len() > 16384 {
            return Err(EntityUpdateError::InvalidNpcBatch);
        }
        let mut moving = BTreeSet::new();
        for movement in &update.movements {
            let npc = self
                .npcs
                .get(&movement.index)
                .filter(|_| !removed.contains(&movement.index))
                .ok_or(EntityUpdateError::InvalidNpcBatch)?;
            if !moving.insert(movement.index) {
                return Err(EntityUpdateError::InvalidNpcBatch);
            }
            path(npc, movement)?;
        }
        for attributes in &update.attributes {
            if !added.contains(&attributes.index)
                && (removed.contains(&attributes.index)
                    || !self.npcs.contains_key(&attributes.index))
                || attributes.changes.len() > 32
            {
                return Err(EntityUpdateError::InvalidNpcBatch);
            }
            for change in &attributes.changes {
                match change {
                    NpcProperty::ExactMove(request) => {
                        let tile = if let Some(new) =
                            update.new_npcs.iter().find(|n| n.index == attributes.index)
                        {
                            WorldTile::new(
                                origin.x + new.delta_x,
                                origin.y + new.delta_y,
                                origin.plane,
                            )
                        } else {
                            let npc = &self.npcs[&attributes.index];
                            update
                                .movements
                                .iter()
                                .find(|m| m.index == attributes.index)
                                .map_or(Ok(npc.tile), |m| path(npc, m).map(NpcPath::endpoint))?
                        };
                        crate::player_movement::exact_movement(tile, cycle, *request)?;
                    }
                    NpcProperty::Name(Some(name)) if name.len() > 1024 => {
                        return Err(EntityUpdateError::InvalidNpcBatch);
                    }
                    NpcProperty::Sequence { delay, .. } if *delay > i32::MAX.cast_unsigned() => {
                        return Err(EntityUpdateError::InvalidNpcBatch);
                    }
                    NpcProperty::Facing(angle) if *angle >= rustclient_model::JAU_FULL_TURN => {
                        return Err(EntityUpdateError::InvalidNpcBatch);
                    }
                    _ => {}
                }
            }
        }
        Ok(())
    }
    pub(crate) fn apply_npc_info(
        &mut self,
        update: &NpcInfoUpdate,
        origin: WorldTile,
        cycle: ClientCycle,
    ) -> bool {
        use rustclient_model::NpcProperty;
        let mut changed = !update.removals.is_empty() || !update.new_npcs.is_empty();
        for removal in &update.removals {
            self.npcs.remove(removal);
        }
        for npc in &update.new_npcs {
            self.npc_serial += 1;
            self.npcs.insert(
                npc.index,
                NpcEntity {
                    index: npc.index,
                    instance: rustclient_model::NpcInstanceId::new(self.npc_serial),
                    definition_id: npc.definition_id,
                    tile: WorldTile::new(
                        origin.x + npc.delta_x,
                        origin.y + npc.delta_y,
                        origin.plane,
                    ),
                    path: None,
                    exact_move: None,
                    orientation: npc.orientation,
                    sequence: rustclient_model::ActorSequenceSlot::default(),
                    animation_cycle: 0,
                    name: None,
                    actions: rustclient_model::ActionMask::ALL,
                    locomotion: Locomotion::Idle,
                },
            );
        }
        for npc in self.npcs.values_mut() {
            let movement = update.movements.iter().find(|m| m.index == npc.index);
            let locomotion = match movement {
                Some(m) if m.speed == MoveSpeed::Run => Locomotion::Run,
                Some(_) => Locomotion::Walk,
                _ => Locomotion::Idle,
            };
            if npc.locomotion != locomotion
                || movement.is_some_and(|m| npc.path.is_some_and(|p| p.speed != m.speed))
            {
                changed = true;
                npc.animation_cycle = 0;
            }
            npc.locomotion = locomotion;
            if let Some(movement) = movement {
                let path = path(npc, movement).expect("validated NPC path");
                npc.tile = path.endpoint();
                npc.path = Some(path);
                npc.exact_move = None;
                let step = movement.second.unwrap_or(movement.first);
                npc.orientation = orientation(step);
                changed = true;
            }
        }
        for attributes in &update.attributes {
            let Some(npc) = self.npcs.get_mut(&attributes.index) else {
                continue;
            };
            for change in &attributes.changes {
                match change {
                    NpcProperty::ExactMove(request) => {
                        let exact =
                            crate::player_movement::exact_movement(npc.tile, cycle, *request)
                                .expect("validated NPC exact movement");
                        changed |= npc.exact_move != Some(exact);
                        npc.exact_move = Some(exact);
                    }
                    NpcProperty::Definition(id) => {
                        changed |= npc.definition_id != *id;
                        npc.definition_id = *id;
                    }
                    NpcProperty::Sequence { sequence, delay } => {
                        changed |= crate::sequence_scheduler::apply_sequence(
                            &mut npc.sequence,
                            *sequence,
                            delay.cast_signed(),
                            &update.sequence_policies,
                        );
                    }
                    NpcProperty::Facing(angle) => {
                        changed |= npc.orientation != *angle;
                        npc.orientation = *angle;
                    }
                    NpcProperty::Name(name) => {
                        if npc.name != *name {
                            changed = true;
                            npc.name.clone_from(name);
                        }
                    }
                    NpcProperty::Actions(actions) => {
                        changed |= npc.actions != *actions;
                        npc.actions = *actions;
                    }
                }
            }
        }
        if changed {
            self.generation = self.generation.wrapping_add(1);
        }
        changed
    }
}
