//! Decoder for desktop 240 `PLAYER_INFO` packet (opcode 91).

use crate::{Result, openrune_240::bit_reader::BitReader, wire::Reader};
pub use rustclient_model::{
    Gender, ItemDefinitionId, KitId, MovementSequences, PlayerAppearance, PlayerInfoUpdate,
    PlayerMovement, SequenceId, SlotContent, WorldTile,
};

/// Decode the `PLAYER_INFO` payload.
/// # Errors
/// Fails on truncated bitstream or malformed appearance blocks.
pub fn decode_player_info(payload: &[u8], _local_tile: WorldTile) -> Result<PlayerInfoUpdate> {
    let mut bit_reader = BitReader::new(payload, "player info bitstream");
    let mut local_movement = None;
    let mut local_extended = false;

    let has_local_update = bit_reader.read_bits(1)? == 1;
    if has_local_update {
        let update_type = bit_reader.read_bits(2)?;
        match update_type {
            0 => {
                local_extended = bit_reader.read_bits(1)? == 1;
            }
            1 => {
                let dir = bit_reader.read_bits_u8(3)?;
                local_extended = bit_reader.read_bits(1)? == 1;
                local_movement = Some(PlayerMovement::Step { direction: dir });
            }
            2 => {
                let dir1 = bit_reader.read_bits_u8(3)?;
                let dir2 = bit_reader.read_bits_u8(3)?;
                local_extended = bit_reader.read_bits(1)? == 1;
                local_movement = Some(PlayerMovement::Run {
                    direction1: dir1,
                    direction2: dir2,
                });
            }
            3 => {
                let _plane_delta = bit_reader.read_bits(2)?;
                let _full_teleport = bit_reader.read_bits(1)? == 1;
                local_extended = bit_reader.read_bits(1)? == 1;
                let dx = bit_reader.read_bits_i32(7)?;
                let dy = bit_reader.read_bits_i32(7)?;
                local_movement = Some(PlayerMovement::Teleport {
                    delta_x: dx - 64,
                    delta_y: dy - 64,
                });
            }
            _ => unreachable!(),
        }
    }

    // Skip remaining bits (other players and low-res players) to reach byte-aligned extended blocks
    bit_reader.byte_align();
    let remaining = bit_reader.remaining_bytes();
    let mut byte_reader = Reader::new(remaining, "player info extended");

    let extended = if local_extended && byte_reader.bytes.len() > byte_reader.offset {
        decode_local_extended(&mut byte_reader)?
    } else {
        LocalExtended::default()
    };

    Ok(PlayerInfoUpdate {
        local_movement,
        local_appearance: extended.appearance,
        local_sequence: extended.sequence,
        local_facing: extended.facing,
    })
}

#[derive(Default)]
struct LocalExtended {
    appearance: Option<PlayerAppearance>,
    sequence: Option<SequenceId>,
    facing: Option<u16>,
}

/// Decode the local player's extended-info block.
fn decode_local_extended(byte_reader: &mut Reader<'_>) -> Result<LocalExtended> {
    let mut out = LocalExtended::default();
    let b0 = byte_reader.u8()?;
    let mut mask = u32::from(b0);
    if mask & 0x80 != 0 {
        mask |= u32::from(byte_reader.u8()?) << 8;
    }
    if mask & 0x2000 != 0 {
        mask |= u32::from(byte_reader.u8()?) << 16;
    }

    // SPOTANIM (0x20)
    if mask & 0x20 != 0 {
        let _ = byte_reader.take(6)?;
    }
    // CHAT (0x1000)
    if mask & 0x1000 != 0 {
        let len = usize::from(byte_reader.u8()?);
        let _ = byte_reader.take(len)?;
    }
    // HEADBARS (0x20000)
    if mask & 0x20000 != 0 {
        let count = usize::from(byte_reader.u8()?);
        let _ = byte_reader.take(count * 6)?;
    }
    // APPEARANCE (0x4)
    if mask & 0x4 != 0 {
        let raw_len = byte_reader.u8()?;
        let capacity = usize::from(128u8.wrapping_sub(raw_len));
        let data = byte_reader.take(capacity)?;
        let mut reversed = data.to_vec();
        reversed.reverse();
        out.appearance = Some(decode_appearance(&reversed)?);
    }
    // FREEZE (0x40000)
    if mask & 0x40000 != 0 {
        let _ = byte_reader.u8()?;
    }
    // FACE (0x40)
    if mask & 0x40 != 0 {
        let angle = byte_reader.u16()?;
        out.facing = Some(angle);
    }
    // MOVE_SPEED (0x4000)
    if mask & 0x4000 != 0 {
        let _ = byte_reader.u8()?;
    }
    // TEMP_MOVE_SPEED (0x800)
    if mask & 0x800 != 0 {
        let _ = byte_reader.u8()?;
    }
    // TRANSPARENCY (0x10000)
    if mask & 0x10000 != 0 {
        let _ = byte_reader.u8()?;
    }
    // HITMARKS (0x100000)
    if mask & 0x10_0000 != 0 {
        let count = usize::from(byte_reader.u8()?);
        let _ = byte_reader.take(count * 8)?;
    }
    // EXACT_MOVE (0x8000)
    if mask & 0x8000 != 0 {
        let _ = byte_reader.take(12)?;
    }
    // SEQUENCE (0x10)
    if mask & 0x10 != 0 {
        let lo = byte_reader.u8()?.wrapping_sub(128);
        let hi = byte_reader.u8()?;
        let seq = u16::from_le_bytes([lo, hi]);
        let _delay = byte_reader.u8()?.wrapping_sub(128);
        if seq != 65535 {
            out.sequence = Some(SequenceId::new(u32::from(seq)));
        }
    }
    Ok(out)
}

fn decode_appearance(bytes: &[u8]) -> Result<PlayerAppearance> {
    let mut r = Reader::new(bytes, "appearance payload");
    let gender = if r.u8()? == 0 {
        Gender::Male
    } else {
        Gender::Female
    };
    let _skull = r.u8()?;
    let _overhead = r.u8()?;

    let mut slots = [SlotContent::Empty; 12];
    for slot in &mut slots {
        let first = r.u8()?;
        if first == 0 {
            *slot = SlotContent::Empty;
        } else {
            let second = r.u8()?;
            let val = (u16::from(first) << 8) | u16::from(second);
            if val >= 0x800 {
                *slot = SlotContent::Item(ItemDefinitionId::new(u32::from(val - 0x800)));
            } else if val >= 0x100 {
                *slot = SlotContent::Kit(KitId::new(u32::from(val - 0x100)));
            }
        }
    }

    // Skip the 12 kit duplicate slots in wire appearance
    for _ in 0..12 {
        let first = r.u8()?;
        if first != 0 {
            r.u8()?;
        }
    }

    let mut colors = [0u8; 5];
    for c in &mut colors {
        *c = r.u8()?;
    }

    let ready_anim = SequenceId::new(u32::from(r.u16()?));
    let turn_anim = SequenceId::new(u32::from(r.u16()?));
    let walk_anim = SequenceId::new(u32::from(r.u16()?));
    let walk_anim_back = SequenceId::new(u32::from(r.u16()?));
    let walk_anim_left = SequenceId::new(u32::from(r.u16()?));
    let walk_anim_right = SequenceId::new(u32::from(r.u16()?));
    let run_anim = SequenceId::new(u32::from(r.u16()?));

    let movement_sequences = MovementSequences {
        idle: Some(ready_anim),
        turn: Some(turn_anim),
        walk: Some(walk_anim),
        walk_back: Some(walk_anim_back),
        walk_left: Some(walk_anim_left),
        walk_right: Some(walk_anim_right),
        run: Some(run_anim),
    };

    // Name string (terminated with 0)
    while r.u8()? != 0 {}

    let combat_level = r.u8()?;
    let skill_level = r.u16()?;
    let hidden = r.u8()? == 1;

    Ok(PlayerAppearance {
        gender,
        slots,
        colors,
        movement_sequences,
        combat_level,
        skill_level,
        hidden,
    })
}
