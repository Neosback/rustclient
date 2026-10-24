//! Resolve definition-derived actor scheduling inputs before authoritative entity mutation.

use rustclient_cache::{
    Cache, CacheError, CacheProfile,
    osrs240::{npc_path_policy, sequence_policy},
};
use rustclient_game_state::SemanticOperation;
use rustclient_model::{
    NpcDefinitionId, NpcInfoUpdate, NpcProperty, PathAccelerationPolicy, PlayerInfoUpdate,
    RemotePlayerUpdate, SequenceChange, SequenceId, SequencePolicy, SequenceTiming,
};
use rustclient_platform::ReadOnlyBlobs;
use std::collections::{BTreeMap, BTreeSet};

/// Cache-backed, target-neutral memoizer for packet-time actor scheduling inputs.
///
/// It owns no live actor state. The authoritative update remains a [`SemanticOperation`]; this
/// service only enriches entity updates with immutable definition-derived inputs.
pub struct SequencePolicyResolver<S> {
    cache: Cache<S>,
    profile: CacheProfile,
    policies: BTreeMap<SequenceId, SequencePolicy>,
    timings: BTreeMap<SequenceId, SequenceTiming>,
    npc_path_policies: BTreeMap<NpcDefinitionId, PathAccelerationPolicy>,
}

impl<S: ReadOnlyBlobs> SequencePolicyResolver<S> {
    #[must_use]
    pub fn new(cache: Cache<S>, profile: CacheProfile) -> Self {
        Self {
            cache,
            profile,
            policies: BTreeMap::new(),
            timings: BTreeMap::new(),
            npc_path_policies: BTreeMap::new(),
        }
    }

    /// Attach every definition-derived scheduling input referenced by one decoded operation.
    ///
    /// Non-entity operations pass through untouched. Attached tables are sorted and contain each
    /// referenced definition id once.
    ///
    /// # Errors
    /// Returns the underlying cache/decode failure rather than allowing `GameState` to guess policy
    /// or playback timing.
    pub fn resolve_operation(
        &mut self,
        mut operation: SemanticOperation,
    ) -> Result<SemanticOperation, CacheError> {
        match &mut operation {
            SemanticOperation::UpdatePlayers(update) => {
                let ids = player_sequence_ids(update);
                update.sequence_policies = self.resolve_ids(ids.clone())?;
                update.sequence_timings = self.resolve_timing_ids(ids)?;
            }
            SemanticOperation::UpdateNpcs(update) => {
                let sequence_ids = npc_sequence_ids(update);
                let definition_ids = npc_definition_ids(update);
                update.sequence_policies = self.resolve_ids(sequence_ids.clone())?;
                update.sequence_timings = self.resolve_timing_ids(sequence_ids)?;
                update.path_acceleration_policies = self.resolve_npc_path_ids(definition_ids)?;
            }
            _ => {}
        }
        Ok(operation)
    }

    fn resolve_ids(
        &mut self,
        ids: BTreeSet<SequenceId>,
    ) -> Result<Vec<(SequenceId, SequencePolicy)>, CacheError> {
        let mut resolved = Vec::with_capacity(ids.len());
        for id in ids {
            let policy = if let Some(policy) = self.policies.get(&id).copied() {
                policy
            } else {
                let policy = sequence_policy::resolve(&self.cache, self.profile, id)?;
                self.policies.insert(id, policy);
                policy
            };
            resolved.push((id, policy));
        }
        Ok(resolved)
    }

    fn resolve_timing_ids(
        &mut self,
        ids: BTreeSet<SequenceId>,
    ) -> Result<Vec<(SequenceId, SequenceTiming)>, CacheError> {
        let mut resolved = Vec::with_capacity(ids.len());
        for id in ids {
            let timing = if let Some(timing) = self.timings.get(&id) {
                timing.clone()
            } else {
                let timing = sequence_policy::resolve_timing(&self.cache, self.profile, id)?;
                self.timings.insert(id, timing.clone());
                timing
            };
            resolved.push((id, timing));
        }
        Ok(resolved)
    }

    fn resolve_npc_path_ids(
        &mut self,
        ids: BTreeSet<NpcDefinitionId>,
    ) -> Result<Vec<(NpcDefinitionId, PathAccelerationPolicy)>, CacheError> {
        let mut resolved = Vec::with_capacity(ids.len());
        for id in ids {
            let policy = if let Some(policy) = self.npc_path_policies.get(&id).copied() {
                policy
            } else {
                let policy = npc_path_policy::resolve(&self.cache, self.profile, id)?;
                self.npc_path_policies.insert(id, policy);
                policy
            };
            resolved.push((id, policy));
        }
        Ok(resolved)
    }
}

fn player_sequence_ids(update: &PlayerInfoUpdate) -> BTreeSet<SequenceId> {
    let mut ids = BTreeSet::new();
    if let SequenceChange::Set(Some(id)) = update.local_sequence {
        ids.insert(id);
    }
    for remote in &update.remote {
        if let RemotePlayerUpdate::Attributes {
            sequence: SequenceChange::Set(Some(id)),
            ..
        } = remote
        {
            ids.insert(*id);
        }
    }
    ids
}

fn npc_sequence_ids(update: &NpcInfoUpdate) -> BTreeSet<SequenceId> {
    update
        .attributes
        .iter()
        .flat_map(|attributes| &attributes.changes)
        .filter_map(|change| match change {
            NpcProperty::Sequence {
                sequence: Some(id), ..
            } => Some(*id),
            _ => None,
        })
        .collect()
}

fn npc_definition_ids(update: &NpcInfoUpdate) -> BTreeSet<NpcDefinitionId> {
    let mut ids: BTreeSet<_> = update
        .new_npcs
        .iter()
        .map(|npc| npc.definition_id)
        .collect();
    ids.extend(
        update
            .attributes
            .iter()
            .flat_map(|attributes| &attributes.changes)
            .filter_map(|change| match change {
                NpcProperty::Definition(id) => Some(*id),
                _ => None,
            }),
    );
    ids
}

#[cfg(test)]
mod tests {
    use super::{npc_definition_ids, npc_sequence_ids, player_sequence_ids};
    use rustclient_model::{
        NewNpc, NpcAttributeUpdate, NpcDefinitionId, NpcIndex, NpcInfoUpdate, NpcProperty,
        PlayerIndex, PlayerInfoUpdate, RemotePlayerUpdate, SequenceChange, SequenceId,
    };

    #[test]
    fn player_policy_ids_are_unique_and_ignore_clears() {
        let repeated = SequenceId::new(40);
        let update = PlayerInfoUpdate {
            local_sequence: SequenceChange::Set(Some(repeated)),
            remote: vec![
                RemotePlayerUpdate::Attributes {
                    index: PlayerIndex::new(2),
                    appearance: None,
                    sequence: SequenceChange::Set(Some(repeated)),
                    delay: 0,
                    facing: None,
                    path_speed: None,
                    exact_move: None,
                },
                RemotePlayerUpdate::Attributes {
                    index: PlayerIndex::new(3),
                    appearance: None,
                    sequence: SequenceChange::Set(None),
                    delay: 0,
                    facing: None,
                    path_speed: None,
                    exact_move: None,
                },
            ],
            ..Default::default()
        };
        assert_eq!(
            player_sequence_ids(&update).into_iter().collect::<Vec<_>>(),
            vec![repeated]
        );
    }

    #[test]
    fn npc_policy_ids_are_unique_and_sorted() {
        let low = SequenceId::new(4);
        let high = SequenceId::new(9);
        let update = NpcInfoUpdate {
            attributes: vec![NpcAttributeUpdate {
                index: NpcIndex::new(1),
                changes: vec![
                    NpcProperty::Sequence {
                        sequence: Some(high),
                        delay: 0,
                    },
                    NpcProperty::Sequence {
                        sequence: Some(low),
                        delay: 0,
                    },
                    NpcProperty::Sequence {
                        sequence: Some(high),
                        delay: 2,
                    },
                    NpcProperty::Sequence {
                        sequence: None,
                        delay: 0,
                    },
                ],
            }],
            ..Default::default()
        };
        assert_eq!(
            npc_sequence_ids(&update).into_iter().collect::<Vec<_>>(),
            vec![low, high]
        );
    }

    #[test]
    fn npc_definition_ids_include_spawns_and_definition_changes() {
        let low = NpcDefinitionId::new(4);
        let high = NpcDefinitionId::new(9);
        let update = NpcInfoUpdate {
            new_npcs: vec![NewNpc {
                index: NpcIndex::new(1),
                definition_id: high,
                delta_x: 0,
                delta_y: 0,
                orientation: 0,
            }],
            attributes: vec![NpcAttributeUpdate {
                index: NpcIndex::new(1),
                changes: vec![NpcProperty::Definition(low), NpcProperty::Definition(high)],
            }],
            ..Default::default()
        };
        assert_eq!(
            npc_definition_ids(&update).into_iter().collect::<Vec<_>>(),
            vec![low, high]
        );
    }
}
