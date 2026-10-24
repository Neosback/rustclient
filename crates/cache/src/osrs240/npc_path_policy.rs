//! Packet-time revision-240 NPC ordinary path acceleration metadata.
//!
//! Live movement needs only the resolved backlog thresholds, not model or animation dependencies.
//! This adapter decodes the selected NPC config file and projects opcode 109 into the shared
//! revision-neutral [`PathAccelerationPolicy`] vocabulary.

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
    let source = Source {
        index: IndexId(2),
        group: Some(GroupId(9)),
        file: Some(FileId(id.get())),
    };
    let bytes = cache.config_file(9, id.get(), profile == CacheProfile::OpenRune240)?;
    definitions::npc_path_acceleration(&bytes, id, source)
}
