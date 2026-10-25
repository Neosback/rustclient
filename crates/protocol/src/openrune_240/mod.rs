//! `OpenRune` 240.2 desktop adapter, pinned to `RSProt` 1.0.0-ALPHA-20260825.
//! See SPEC-0014 and tests/fixtures/PROVENANCE.md for exact evidence and scope.
mod actions;
pub(crate) mod bit_reader;
pub(crate) mod game;
pub(crate) mod huffman;
mod info_fields;
mod interfaces;
mod inventory;
pub(crate) mod js5;
mod lengths;
pub(crate) mod loc_anim;
pub(crate) mod login;
mod npc_extended;
pub(crate) mod npc_info;
mod player_appearance;
mod player_extended;
mod speech;
#[allow(
    clippy::too_many_lines,
    reason = "the rev-240 GPI decoder intentionally mirrors the deob's ordered bitstream state machine"
)]
pub(crate) mod player_info;
mod scripts;
pub(crate) mod zone;
use crate::{ProtocolError, Result};
pub(crate) const REVISION: rustclient_model::Revision =
    rustclient_model::Revision::with_subrevision(240, 2);

/// Build the profile's login CRC block from cache-owned reference checksums.
/// # Errors
/// All desktop archives except the deliberately unused archive 16 are required.
pub fn login_checksums(tables: impl IntoIterator<Item = (u8, u32)>) -> Result<[u32; 23]> {
    let mut out = [None; 23];
    for (index, crc) in tables {
        if let Some(slot) = out.get_mut(usize::from(index)) {
            *slot = Some(crc);
        }
    }
    let mut crcs = [0; 23];
    for (index, slot) in out.into_iter().enumerate() {
        crcs[index] = slot.or_else(|| (index == 16).then_some(0)).ok_or_else(|| {
            ProtocolError::new("cache compatibility", format!("missing archive {index}"))
        })?;
    }
    Ok(crcs)
}

mod chat;

mod minimap;
pub use minimap::MinimapClick;
