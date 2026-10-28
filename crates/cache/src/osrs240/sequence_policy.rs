//! Packet-time revision-240 action-sequence scheduling metadata.
//!
//! Live actor scheduling consumes resolved definition policy and playback timing, not cache bytes.
//! This adapter decodes the selected sequence config file and projects its revision-specific
//! defaults into shared semantic vocabulary.

use super::animation;
use crate::{
    Cache, CacheProfile, FileId, GroupId, IndexId, Source, error::Result, world::SequenceAsset,
};
use rustclient_model::{SequenceId, SequencePolicy, SequenceTiming};
use rustclient_platform::ReadOnlyBlobs;

fn asset<S: ReadOnlyBlobs>(
    cache: &Cache<S>,
    profile: CacheProfile,
    id: SequenceId,
) -> Result<SequenceAsset> {
    let source = Source {
        index: IndexId(2),
        group: Some(GroupId(12)),
        file: Some(FileId(id.get())),
    };
    let bytes = cache.config_file(12, id.get(), profile == CacheProfile::OpenRune240)?;
    animation::sequence(&bytes, id, source)
}

/// Resolve scheduling metadata for one revision-240 sequence definition.
///
/// `Cache::config_file` retains the decoded config archive, so repeated packet-time lookups avoid
/// reopening/decompressing group 12. Callers additionally memoize the projected policy.
///
/// # Errors
/// Returns source-aware cache/decode errors for missing or malformed sequence definitions.
pub fn resolve<S: ReadOnlyBlobs>(
    cache: &Cache<S>,
    profile: CacheProfile,
    id: SequenceId,
) -> Result<SequencePolicy> {
    Ok(asset(cache, profile, id)?.policy())
}

/// Resolve immutable playback timing needed by semantic client-cycle scheduling.
///
/// Legacy definitions retain their frame delays and rewind count. Opcode-13 cached-model
/// definitions are represented separately because revision 240 bypasses the legacy frame-boundary
/// gate during forced movement for that sequence family.
///
/// # Errors
/// Returns source-aware cache/decode errors for missing or malformed sequence definitions.
pub fn resolve_timing<S: ReadOnlyBlobs>(
    cache: &Cache<S>,
    profile: CacheProfile,
    id: SequenceId,
) -> Result<SequenceTiming> {
    Ok(timing(asset(cache, profile, id)?))
}

fn timing(sequence: SequenceAsset) -> SequenceTiming {
    if sequence.skeletal.is_some() {
        SequenceTiming::CachedModel {
            range: sequence.range,
            frame_step: sequence.frame_step,
        }
    } else {
        SequenceTiming::Legacy {
            frame_delays: sequence.delays,
            frame_step: sequence.frame_step,
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn cached_timing_retains_authored_range_and_rewind_without_assuming_defaults() {
        let source = Source::group(IndexId(2), GroupId(12));
        for (bytes, range, frame_step) in [
            (vec![13, 0, 0, 0, 1, 0], None, None),
            (
                vec![13, 0, 0, 0, 1, 15, 0, 3, 0, 9, 2, 0, 2, 0],
                Some((3, 9)),
                Some(2),
            ),
            (
                vec![13, 0, 0, 0, 1, 15, 0, 9, 0, 3, 2, 0, 0, 0],
                Some((9, 3)),
                Some(0),
            ),
        ] {
            let sequence = animation::sequence(&bytes, SequenceId::new(1), source).unwrap();
            assert_eq!(
                timing(sequence),
                SequenceTiming::CachedModel { range, frame_step }
            );
        }
    }
}
