//! TSPS cache-241 profile; shared archive framing, independently pinned opcode semantics.
//! Cache-wide live acceptance is not established. See SPEC-0031 and protocol fixture provenance.
use crate::{Source, script::Script};
use rustclient_model::ScriptId;
mod opcodes;

/// Reference metadata of the independently pinned `OpenRS2` cache 2746.
pub const METADATA_SHA256: &str =
    "eed13fcb9fd98865c24a7dfe50fbe41bae6f791df7dcbcd17bc6e04b8d967223";

/// Validate the exact migration snapshot, without guessing a revision from archive names.
/// # Errors
/// Custom or different snapshots need their own evidenced profile instead of a silent fallback.
pub fn validate_identity<S: rustclient_platform::ReadOnlyBlobs>(
    cache: &crate::Cache<S>,
) -> crate::error::Result<()> {
    if cache.metadata_fingerprint() != METADATA_SHA256 {
        return Err(Source::group(crate::IndexId(255), crate::GroupId(0)).error(
            "TSPS cache identity",
            "reference metadata differs from pinned cache 2746",
        ));
    }
    Ok(())
}
/// Decode the 241 long-count script container using this profile's command map.
/// # Errors
/// Rejects invalid containers; unknown command semantics remain explicit Unsupported instructions.
pub fn decode_script(bytes: &[u8], id: ScriptId, source: Source) -> crate::error::Result<Script> {
    crate::osrs240::scripts::decode_with_opcodes(bytes, id, source, opcodes::opcode)
}
