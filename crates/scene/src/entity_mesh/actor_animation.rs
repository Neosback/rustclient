//! Immutable action playback sampling. Resolved actions consume `GameState` cursors, never a
//! second elapsed-time lifetime calculation. Movement/unresolved fixtures retain elapsed sampling.
use super::EntityCatalog;
use crate::{
    Result, SceneAsset,
    motion::{Facing, Pace, Pose},
};
use rustclient_cache::world::{SequenceAsset, StaticModel};
use rustclient_model::{ActorSequenceSlot, SequenceId};

#[derive(Debug, Clone, Copy)]
pub(super) enum Playback {
    Elapsed {
        sequence: Option<SequenceId>,
        cycle: u32,
        action: bool,
    },
    Committed(ActorSequenceSlot),
}
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub(super) enum PoseSample {
    Rest,
    Cached(SequenceId, Option<i32>),
    Legacy(SequenceId, Option<(usize, u64)>),
}
impl Playback {
    pub(super) fn movement(sequence: Option<SequenceId>, cycle: u32) -> Self {
        Self::Elapsed {
            sequence,
            cycle,
            action: false,
        }
    }
    pub(super) fn for_actor(
        slot: ActorSequenceSlot,
        resolved: bool,
        base: Option<SequenceId>,
        cycle: u32,
    ) -> Self {
        if slot.sequence.is_some() && slot.delay == 0 {
            if resolved {
                Self::Committed(slot)
            } else {
                Self::Elapsed {
                    sequence: slot.sequence,
                    cycle: slot.cycle.max(0).cast_unsigned(),
                    action: true,
                }
            }
        } else {
            Self::movement(base, cycle)
        }
    }
    pub(super) fn sequence(self) -> Option<SequenceId> {
        match self {
            Self::Committed(slot) => slot.sequence,
            Self::Elapsed { sequence, .. } => sequence,
        }
    }
    pub(super) fn sample(
        self,
        asset: &SceneAsset,
        catalog: &EntityCatalog,
        blend: bool,
    ) -> Result<PoseSample> {
        let Some(id) = self.sequence() else {
            return Ok(PoseSample::Rest);
        };
        let Some(sequence) = catalog.animations.sequence(asset, id) else {
            return Ok(PoseSample::Rest);
        };
        if let Some(animation) = sequence.skeletal {
            if catalog.animations.cached(animation).is_none() {
                return Ok(PoseSample::Rest);
            }
            return Ok(PoseSample::Cached(id, self.cached_frame(sequence)));
        }
        Ok(PoseSample::Legacy(
            id,
            self.legacy_frame(sequence)?
                .map(|(frame, cycle)| (frame, if blend { cycle } else { 0 })),
        ))
    }
    fn cached_frame(self, sequence: &SequenceAsset) -> Option<i32> {
        match self {
            Self::Committed(slot) => i32::try_from(slot.frame.unwrap_or(0)).ok(),
            Self::Elapsed { cycle, action, .. } => {
                crate::animation::actor_skeletal_frame(sequence, u64::from(cycle), action)
            }
        }
    }
    fn legacy_frame(self, sequence: &SequenceAsset) -> Result<Option<(usize, u64)>> {
        match self {
            Self::Committed(slot) => {
                let Some(index) = slot.frame.and_then(|frame| usize::try_from(frame).ok()) else {
                    return Ok(None);
                };
                Ok(
                    (index < sequence.frames.len() && index < sequence.delays.len())
                        .then_some((index, u64::from(slot.cycle.max(0).cast_unsigned()))),
                )
            }
            Self::Elapsed { cycle, action, .. } => {
                crate::animation::actor_frame_position(sequence, u64::from(cycle), action)
            }
        }
    }
}

/// Every movement animation an actor authors, indexed forward, back, left, right.
pub(super) struct DirectionalSequences {
    pub(super) idle: Option<SequenceId>,
    pub(super) turn_left: Option<SequenceId>,
    pub(super) turn_right: Option<SequenceId>,
    pub(super) walk: [Option<SequenceId>; 4],
    pub(super) run: [Option<SequenceId>; 4],
    pub(super) crawl: [Option<SequenceId>; 4],
}

/// The animation the reference client plays for a movement pose (`Actor.method3284` and
/// `classKN.method6539`): a missing directional animation falls back to the forward walk, a
/// missing run or crawl variant to the walk of the same direction, and a missing turn animation
/// to the forward walk.
pub(super) fn select_movement(pose: Pose, sequences: &DirectionalSequences) -> Option<SequenceId> {
    match pose {
        Pose::Idle => sequences.idle,
        Pose::TurnLeft => sequences.turn_left.or(sequences.walk[0]),
        Pose::TurnRight => sequences.turn_right.or(sequences.walk[0]),
        Pose::Move { facing, pace } => {
            let direction = match facing {
                Facing::Forward => 0,
                Facing::Back => 1,
                Facing::Left => 2,
                Facing::Right => 3,
            };
            let direction = if sequences.walk[direction].is_some() {
                direction
            } else {
                0
            };
            match pace {
                Pace::Run => sequences.run[direction],
                Pace::Crawl => sequences.crawl[direction],
                Pace::Walk => None,
            }
            .or(sequences.walk[direction])
        }
    }
}

/// Apply the selected immutable playback sample: a cached-model animation when the
/// sequence names one, otherwise the legacy frame.
///
/// A skeletal animation that failed to decode or prepare was already reported as an advisory;
/// its sequence is drawn in rest pose rather than failing the whole scene.
///
/// With `blend`, a legacy frame is mixed toward the next one by how far into its delay the
/// sequence is (animation smoothing); skeletal animations already evaluate per cycle.
pub(super) fn pose_model(
    asset: &SceneAsset,
    catalog: &EntityCatalog,
    model: &mut StaticModel,
    playback: Playback,
    blend: bool,
) -> Result<bool> {
    let Some(sequence) = playback.sequence() else {
        return Ok(true);
    };
    let Some(sequence) = catalog.animations.sequence(asset, sequence) else {
        return Ok(true);
    };
    if let Some(animation) = sequence.skeletal {
        let Some(animation) = catalog.animations.cached(animation) else {
            return Ok(true);
        };
        let Some(frame) = playback.cached_frame(sequence) else {
            return Ok(false);
        };
        crate::animation::pose_skeletal(
            animation,
            frame,
            &mut model.vertices,
            &mut model.faces,
            &model.skeletal_weights,
        )?;
        return Ok(true);
    }
    let Some((index, in_frame)) = playback.legacy_frame(sequence)? else {
        return Ok(false);
    };
    if let Some(frame) = sequence
        .frames
        .get(index)
        .and_then(|id| catalog.animations.frame(asset, *id))
        && let Some(skeleton) = catalog.animations.skeleton(asset, frame.skeleton)
    {
        let blended = blend
            .then(|| {
                let next = sequence
                    .frames
                    .get(index + 1)
                    .and_then(|id| catalog.animations.frame(asset, *id));
                crate::animation::blend_frames(
                    &skeleton.transforms,
                    frame,
                    next,
                    i32::try_from(in_frame.saturating_sub(1)).unwrap_or(0),
                    i32::from(sequence.delays[index]),
                )
            })
            .flatten();
        crate::animation::pose(
            &mut model.vertices,
            &mut model.faces,
            &model.vertex_skins,
            blended.as_ref().unwrap_or(frame),
            &skeleton.transforms,
            0,
        )?;
    }
    Ok(true)
}

#[cfg(test)]
mod tests {
    use super::*;

    fn sequence() -> SequenceAsset {
        SequenceAsset {
            id: SequenceId::new(1),
            frames: vec![10, 20],
            delays: vec![2, 3],
            frame_step: Some(1),
            max_loops: 1,
            priority: 5,
            movement_gate_with_backlog: 0,
            movement_gate_without_backlog: 0,
            restart: 2,
            skeletal: None,
            range: None,
            height_offset: 0,
        }
    }

    #[test]
    fn resolved_legacy_action_samples_committed_frame_instead_of_replaying_small_counter() {
        let slot = ActorSequenceSlot {
            sequence: Some(SequenceId::new(1)),
            frame: Some(1),
            cycle: 1,
            ..Default::default()
        };
        let playback = Playback::for_actor(slot, true, None, 800);
        assert_eq!(playback.legacy_frame(&sequence()).unwrap(), Some((1, 1)));
        let unresolved = Playback::for_actor(slot, false, None, 800);
        assert_eq!(unresolved.legacy_frame(&sequence()).unwrap(), Some((0, 1)));
    }

    #[test]
    fn cached_action_uses_committed_time_without_offset_loop_or_legacy_counter() {
        let mut sequence = sequence();
        sequence.skeletal = Some(1);
        sequence.range = Some((10, 13));
        let slot = ActorSequenceSlot {
            sequence: Some(sequence.id),
            frame: Some(2),
            cycle: 500,
            iterations: 99,
            ..Default::default()
        };
        assert_eq!(
            Playback::for_actor(slot, true, None, 0).cached_frame(&sequence),
            Some(2)
        );
        let delayed = ActorSequenceSlot { delay: 1, ..slot };
        let playback = Playback::for_actor(delayed, true, Some(SequenceId::new(9)), 4);
        assert_eq!(playback.sequence(), Some(SequenceId::new(9)));
        assert!(matches!(
            playback,
            Playback::Elapsed {
                action: false,
                cycle: 4,
                ..
            }
        ));
    }
}
