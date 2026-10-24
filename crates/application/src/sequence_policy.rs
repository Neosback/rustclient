//! Resolve definition-derived action-sequence policy before authoritative entity mutation.

use rustclient_cache::{Cache, CacheError, CacheProfile, osrs240::sequence_policy};
use rustclient_game_state::SemanticOperation;
use rustclient_model::{
    NpcInfoUpdate, NpcProperty, PlayerInfoUpdate, RemotePlayerUpdate, SequenceChange, SequenceId,
    SequencePolicy,
};
use rustclient_platform::ReadOnlyBlobs;
use std::collections::{BTreeMap, BTreeSet};

/// Cache-backed, target-neutral memoizer for packet-time action-sequence scheduling policy.
///
/// It owns no live actor state. The authoritative update remains a [`SemanticOperation`]; this
/// service only enriches entity updates with immutable definition-derived scheduling inputs.
pub struct SequencePolicyResolver<S> {
    cache: Cache<S>,
    profile: CacheProfile,
    policies: BTreeMap<SequenceId, SequencePolicy>,
}

impl<S: ReadOnlyBlobs> SequencePolicyResolver<S> {
    #[must_use]
    pub fn new(cache: Cache<S>, profile: CacheProfile) -> Self {
        Self {
            cache,
            profile,
            policies: BTreeMap::new(),
        }
    }

    /// Attach every sequence policy referenced by one decoded semantic operation.
    ///
    /// Non-entity operations pass through untouched. Clear/unchanged sequence fields require no
    /// definition lookup. The attached tables are sorted and contain each referenced id once.
    ///
    /// # Errors
    /// Returns the underlying cache/decode failure rather than allowing GameState to guess policy.
    pub fn resolve_operation(
        &mut self,
        mut operation: SemanticOperation,
    ) -> Result<SemanticOperation, CacheError> {
        match &mut operation {
            SemanticOperation::UpdatePlayers(update) => {
                update.sequence_policies = self.resolve_ids(player_sequence_ids(update))?;
            }
            SemanticOperation::UpdateNpcs(update) => {
                update.sequence_policies = self.resolve_ids(npc_sequence_ids(update))?;
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
                sequence: Some(id),
                ..
            } => Some(*id),
            _ => None,
        })
        .collect()
}

#[cfg(test)]
mod tests {
    use super::{npc_sequence_ids, player_sequence_ids};
    use rustclient_model::{
        NpcAttributeUpdate, NpcIndex, NpcInfoUpdate, NpcProperty, PlayerIndex, PlayerInfoUpdate,
        RemotePlayerUpdate, SequenceChange, SequenceId,
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
        assert_eq!(player_sequence_ids(&update).into_iter().collect::<Vec<_>>(), vec![repeated]);
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
        assert_eq!(npc_sequence_ids(&update).into_iter().collect::<Vec<_>>(), vec![low, high]);
    }
}
