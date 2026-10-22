//! Decoder for desktop 240 `NPC_INFO` packets (opcodes 150 and 141).

use crate::{Result, openrune_240::bit_reader::BitReader};
pub use rustclient_model::{NewNpc, NpcDefinitionId, NpcIndex, NpcInfoUpdate, NpcMovement};

/// Decode the `NPC_INFO` payload.
/// # Errors
/// Fails on truncated bitstream.
pub fn decode_npc_info(payload: &[u8], is_large: bool) -> Result<NpcInfoUpdate> {
    let mut bit_reader = BitReader::new(payload, "npc info bitstream");
    let mut update = NpcInfoUpdate::default();

    if bit_reader.bits_remaining() < 8 {
        return Ok(update);
    }
    let active_count = bit_reader.read_bits(8)?;
    for _ in 0..active_count {
        if bit_reader.bits_remaining() == 0 {
            break;
        }
        let update_flag = bit_reader.read_bits(1)?;
        if update_flag != 0 {
            let move_type = bit_reader.read_bits(2)?;
            match move_type {
                1 => {
                    let _dir = bit_reader.read_bits(3)?;
                    let _ext = bit_reader.read_bits(1)?;
                }
                2 => {
                    let _ext = bit_reader.read_bits(1)?;
                    let _dir1 = bit_reader.read_bits(3)?;
                    let _dir2 = bit_reader.read_bits(3)?;
                }
                _ => {}
            }
        }
    }

    // New NPCs entering high resolution
    let coord_bits = if is_large { 8 } else { 6 };
    let coord_bound = 1 << coord_bits;
    let coord_half = coord_bound / 2;

    while bit_reader.bits_remaining() >= 16 + coord_bits * 2 + 3 + 14 {
        let index_val = bit_reader.read_bits(16)?;
        if index_val == 65535 {
            break;
        }
        let raw_x = bit_reader.read_bits_i32(coord_bits)?;
        let delta_x = if raw_x >= coord_half {
            raw_x - coord_bound
        } else {
            raw_x
        };

        let check = bit_reader.read_bits(1)? == 1;
        if check && bit_reader.bits_remaining() >= 32 {
            let _ = bit_reader.read_bits(32)?;
        }

        let rot = bit_reader.read_bits(3)?;
        let _has_extended = bit_reader.read_bits(1)?;

        let raw_y = bit_reader.read_bits_i32(coord_bits)?;
        let delta_y = if raw_y >= coord_half {
            raw_y - coord_bound
        } else {
            raw_y
        };

        let type_id = bit_reader.read_bits(14)?;
        let orientation = u16::try_from(rot * 256).unwrap_or(0);

        update.new_npcs.push(NewNpc {
            index: NpcIndex::new(index_val),
            definition_id: NpcDefinitionId::new(type_id),
            delta_x,
            delta_y,
            orientation,
        });
    }

    Ok(update)
}
