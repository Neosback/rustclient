//! Packet-time revision-240 action-sequence scheduling metadata.
//!
//! Live actor scheduling needs only resolved definition policy, not frame/model dependencies. This
//! adapter decodes the selected sequence config file and projects its revision-specific defaults
//! into the shared semantic [`SequencePolicy`] vocabulary.

use super::animation;
use crate::{Cache, CacheProfile, FileId, GroupId, IndexId, Source, error::Result};
use rustclient_model::{SequenceId, SequencePolicy};
use rustclient_platform::ReadOnlyBlobs;

/// Resolve scheduling metadata for one revision-240 sequence definition.
///
/// `Cache::config_file` retains the decoded config archive, so repeated packet-time lookups avoid
/// reopening/decompressing group 12. Callers may additionally memoize the projected policy.
///
/// # Errors
/// Returns source-aware cache/decode errors for missing or malformed sequence definitions.
pub fn resolve<S: ReadOnlyBlobs>(
    cache: &Cache<S>,
    profile: CacheProfile,
    id: SequenceId,
) -> Result<SequencePolicy> {
    let source = Source {
        index: IndexId(2),
        group: Some(GroupId(12)),
        file: Some(FileId(id.get())),
    };
    let bytes = cache.config_file(12, id.get(), profile == CacheProfile::OpenRune240)?;
    Ok(animation::sequence(&bytes, id, source)?.policy())
}
