//! Packet-time revision-240 NPC movement scheduling metadata.
//!
//! Live movement needs resolved backlog thresholds and turn rate, not model/animation dependencies.
//! This adapter projects the selected NPC definition into revision-neutral movement policy.

use super::definitions;
use crate::{Cache, CacheProfile, FileId, GroupId, IndexId, Source, error::Result};
use rustclient_model::{NpcDefinitionId, PathAccelerationPolicy};
use rustclient_platform::ReadOnlyBlobs;

/// Resolve ordinary path acceleration for one revision-240 NPC definition.
///
/// # Errors
/// Returns source-aware cache/decode errors for missing or malformed NPC definitions.
pub fn resolve<S: ReadOnlyBlobs>(
    cache: &Cache<S>,
    profile: CacheProfile,
    id: NpcDefinitionId,
) -> Result<PathAccelerationPolicy> {
    resolve_movement(cache, profile, id).map(|policy| policy.path_acceleration)
}

/// Resolve path acceleration and turn rate from the same selected definition.
/// # Errors
/// Returns source-aware cache/decode errors for missing or malformed definitions.
pub fn resolve_movement<S: ReadOnlyBlobs>(
    cache: &Cache<S>,
    profile: CacheProfile,
    id: NpcDefinitionId,
) -> Result<rustclient_model::NpcMovementPolicy> {
    let source = Source {
        index: IndexId(2),
        group: Some(GroupId(9)),
        file: Some(FileId(id.get())),
    };
    let bytes = cache.config_file(9, id.get(), profile == CacheProfile::OpenRune240)?;
    definitions::npc_movement_policy(&bytes, id, source)
}

#[cfg(test)]
mod tests {
    use super::*;

    fn source() -> Source {
        Source {
            index: IndexId(2),
            group: Some(GroupId(9)),
            file: Some(FileId(1)),
        }
    }

    #[test]
    fn opcode_109_selects_early_backlog_thresholds() {
        let id = NpcDefinitionId::new(1);
        assert_eq!(
            definitions::npc_path_acceleration(&[0], id, source()).unwrap(),
            PathAccelerationPolicy::STANDARD
        );
        assert_eq!(
            definitions::npc_path_acceleration(&[109, 0], id, source()).unwrap(),
            PathAccelerationPolicy::EARLY
        );
    }
}
