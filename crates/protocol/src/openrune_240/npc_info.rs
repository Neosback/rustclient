//! Stateful revision-240 V6 NPC list decoder. Entity positions remain semantic state.
use super::{bit_reader::BitReader, npc_extended::decode_attributes};
use crate::{ProtocolError, Result, wire::Reader};
use rustclient_model::TileOffset;
pub use rustclient_model::{NewNpc, NpcDefinitionId, NpcIndex, NpcInfoUpdate, NpcMovement};
use std::collections::BTreeSet;

#[derive(Default)]
pub struct NpcInfoDecoder {
    active: Vec<NpcIndex>,
    origin: Option<TileOffset>,
}
impl NpcInfoDecoder {
    pub fn clear_origin(&mut self) {
        self.origin = None;
    }
    /// Translate this profile's build-area origin to map-anchor-relative tile units.
    /// # Errors
    /// Rejects invalid origin packet lengths or coordinates outside the build area.
    pub fn set_origin(&mut self, payload: &[u8]) -> Result<()> {
        let [x, y] = payload else {
            return Err(error("expected two-byte NPC origin"));
        };
        if *x >= 104 || *y >= 104 {
            return Err(error("NPC origin outside normal build area"));
        }
        self.origin = Some(TileOffset {
            x: i32::from(*x) - 48,
            y: i32::from(*y) - 48,
        });
        Ok(())
    }
    /// # Errors
    /// Rejects inconsistent lists, duplicate additions, truncated or unsupported blocks.
    #[expect(
        clippy::too_many_lines,
        reason = "ordered NPC list and extension phases share one bounded packet reader"
    )]
    pub fn decode(&mut self, payload: &[u8], large: bool) -> Result<NpcInfoUpdate> {
        let mut bits = BitReader::new(payload, "NPC info");
        let count = usize::from(bits.read_bits_u8(8)?);
        if count > self.active.len() {
            return Err(error("active NPC count exceeds prior list"));
        }
        let mut update = NpcInfoUpdate {
            origin: self.origin,
            ..Default::default()
        };
        update.removals.extend_from_slice(&self.active[count..]);
        let mut next = Vec::with_capacity(255);
        let mut extended = Vec::new();
        for &index in &self.active[..count] {
            if bits.read_bits(1)? == 0 {
                next.push(index);
                continue;
            }
            match bits.read_bits(2)? {
                0 => extended.push(index),
                1 => {
                    let (dx, dy) = direction(bits.read_bits_u8(3)?);
                    update.movements.push(NpcMovement {
                        index,
                        delta_x: dx,
                        delta_y: dy,
                        running: false,
                    });
                    if bits.read_bits(1)? != 0 {
                        extended.push(index);
                    }
                }
                2 => {
                    let running = bits.read_bits(1)? != 0;
                    let (mut dx, mut dy) = direction(bits.read_bits_u8(3)?);
                    if running {
                        let (x, y) = direction(bits.read_bits_u8(3)?);
                        dx += x;
                        dy += y;
                    }
                    update.movements.push(NpcMovement {
                        index,
                        delta_x: dx,
                        delta_y: dy,
                        running,
                    });
                    if bits.read_bits(1)? != 0 {
                        extended.push(index);
                    }
                }
                _ => {
                    update.removals.push(index);
                    continue;
                }
            }
            next.push(index);
        }
        let mut membership: BTreeSet<_> = next.iter().copied().collect();
        let coord_bits = if large { 8 } else { 6 };
        let mut terminated = false;
        while bits.bits_remaining() >= 16 {
            let id = bits.read_bits(16)?;
            if id == 65535 {
                terminated = true;
                break;
            }
            let index = NpcIndex::new(id);
            if !membership.insert(index) || next.len() >= 255 {
                return Err(error("duplicate or excessive NPC additions"));
            }
            if bits.read_bits(1)? != 0 {
                let code = usize::from(bits.read_bits_u8(2)?);
                bits.read_bits([18, 19, 20, 32][code])?;
            }
            let code = usize::from(bits.read_bits_u8(2)?);
            let definition_id = NpcDefinitionId::new(bits.read_bits([12, 14, 17, 24][code])?);
            if bits.read_bits(1)? != 0 {
                extended.push(index);
            }
            let delta_x = signed(bits.read_bits_i32(coord_bits)?, coord_bits);
            let orientation =
                [0, 1024, 1536, 512, 256, 768, 1280, 1792][usize::from(bits.read_bits_u8(3)?)];
            bits.read_bits(1)?; // teleport-vs-path placement hint, no position authority
            let delta_y = signed(bits.read_bits_i32(coord_bits)?, coord_bits);
            update.new_npcs.push(NewNpc {
                index,
                definition_id,
                delta_x,
                delta_y,
                orientation,
            });
            next.push(index);
        }
        if !extended.is_empty() && !terminated {
            return Err(error("missing extended-info terminator"));
        }
        if !terminated && bits.bits_remaining() > 7 {
            return Err(error("incomplete NPC addition"));
        }
        bits.byte_align();
        let mut reader = Reader::new(bits.remaining_bytes(), "NPC extended info");
        for index in extended {
            update
                .attributes
                .push(decode_attributes(&mut reader, index)?);
        }
        if reader.offset != reader.bytes.len() {
            return Err(error("trailing NPC info bytes"));
        }
        self.active = next;
        Ok(update)
    }
}
fn error(detail: &'static str) -> ProtocolError {
    ProtocolError::new("NPC info", detail)
}
fn signed(value: i32, width: usize) -> i32 {
    if value & (1 << (width - 1)) != 0 {
        value - (1 << width)
    } else {
        value
    }
}
fn direction(direction: u8) -> (i32, i32) {
    [
        (-1, -1),
        (0, -1),
        (1, -1),
        (-1, 0),
        (1, 0),
        (-1, 1),
        (0, 1),
        (1, 1),
    ][usize::from(direction)]
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_model::{NpcProperty, SequenceId};
    #[test]
    fn npc_family_tracks_origin_spawns_movement_masks_removal_and_truncation() {
        let small = include_bytes!("../../tests/fixtures/npc-stock-small.bin");
        let large = include_bytes!("../../tests/fixtures/npc-stock-large.bin");
        let mut decoder = NpcInfoDecoder::default();
        decoder.set_origin(&[50, 49]).unwrap();
        let spawn = decoder.decode(small, false).unwrap();
        assert_eq!(spawn.origin, Some(TileOffset { x: 2, y: 1 }));
        assert_eq!(
            spawn.new_npcs,
            vec![NewNpc {
                index: NpcIndex::new(42),
                definition_id: NpcDefinitionId::new(12),
                delta_x: -2,
                delta_y: 3,
                orientation: 1536
            }]
        );
        let changes = &spawn.attributes[0].changes;
        assert_eq!(
            changes[1],
            NpcProperty::Sequence {
                sequence: Some(SequenceId::new(808)),
                delay: 2
            }
        );
        assert_eq!(changes[2], NpcProperty::Name(Some("Banker A".into())));
        assert_eq!(changes[3], NpcProperty::Facing(512));
        let movement = decoder.decode(&[1, 0xb0], false).unwrap();
        assert_eq!(
            movement.movements,
            vec![NpcMovement {
                index: NpcIndex::new(42),
                delta_x: 1,
                delta_y: 0,
                running: false
            }]
        );
        assert_eq!(
            decoder.decode(&[1, 0xe0], false).unwrap().removals,
            vec![NpcIndex::new(42)]
        );
        let spawn = decoder.decode(large, true).unwrap();
        assert_eq!(spawn.new_npcs[0].definition_id, NpcDefinitionId::new(70000));
        assert_eq!(
            (spawn.new_npcs[0].delta_x, spawn.new_npcs[0].delta_y),
            (-100, 80)
        );
        assert!(
            NpcInfoDecoder::default()
                .decode(&[0], false)
                .unwrap()
                .new_npcs
                .is_empty()
        );
        for length in (0..small.len()).filter(|length| *length != 1) {
            assert!(
                NpcInfoDecoder::default()
                    .decode(&small[..length], false)
                    .is_err(),
                "truncation {length}"
            );
        }
        assert!(NpcInfoDecoder::default().decode(&[1, 0], false).is_err());
        assert!(decoder.set_origin(&[104, 0]).is_err());
    }
}
