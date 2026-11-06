//! Selected `RSProt` desktop-240 writer order, corroborated by rl-suite-240 classDF.method3050.
//! Older PlayerUpdateManager.method2946 is a different revision, including its mask extensions.
use super::info_fields::face;
use super::player_appearance::{decode_appearance, sequence_id};
use crate::{ProtocolError, Result, wire::Reader};
use rustclient_model::{ExactMove, MoveSpeed, PlayerAppearance, SequenceChange};
const KNOWN: u32 = 0x10
    | 4
    | 0x40
    | 8
    | 1
    | 0x1000
    | 0x4000
    | 0x400
    | 0x8000
    | 0x800
    | 0x20
    | 0x20000
    | 0x0010_0000
    | 0x40000
    | 0x10000
    | 0x80
    | 0x2000
    | 0x200
    | 0x80000;
#[derive(Default)]
pub(super) struct Extended {
    pub appearance: Option<PlayerAppearance>,
    pub sequence: SequenceChange,
    pub sequence_delay: u8,
    pub facing: Option<u16>,
    pub temporary_speed: Option<MoveSpeed>,
    pub persistent_speed: Option<MoveSpeed>,
    pub exact_move: Option<ExactMove>,
    pub speech: Vec<rustclient_model::SpeechUpdate>,
    pub effects: rustclient_model::ActorEffects,
}
pub(super) fn decode_extended(
    r: &mut Reader<'_>,
    chat: Option<&super::huffman::Huffman>,
) -> Result<Extended> {
    let mut out = Extended::default();
    let mask = read_mask(r)?;
    if mask & !KNOWN != 0 {
        return Err(ProtocolError::new(
            "player extended",
            format!("unsupported mask bits {:#x}", mask & !KNOWN),
        ));
    }
    if mask & 0x20 != 0 {
        super::actor_effects::player_spotanims(r, &mut out.effects)?;
    }
    if mask & 0x1000 != 0 {
        out.speech.push(super::speech::public(r, chat)?);
    }
    if mask & 0x20000 != 0 {
        super::actor_effects::player_headbars(r, &mut out.effects)?;
    }
    if mask & 4 != 0 {
        let length = usize::from(r.u8_sub()?);
        let mut bytes = r.take(length)?.to_vec();
        bytes.reverse();
        out.appearance = Some(decode_appearance(&bytes)?);
    }
    if mask & 0x40000 != 0 {
        r.take(5)?;
    }
    if mask & 0x40 != 0 {
        let flag = r.u8()?;
        out.facing = face(r, flag)?;
    }
    if mask & 0x200 != 0 {
        // NAME_EXTRAS: the text before the name, after the name and after the combat level.
        for _ in 0..3 {
            skip_text(r)?;
        }
    }
    if mask & 0x4000 != 0 {
        out.persistent_speed = Some(move_speed(r.i8_sub()?)?);
    }
    if mask & 0x800 != 0 {
        let value = r.i8_neg()?;
        out.temporary_speed = Some(if value == 127 {
            MoveSpeed::Stationary
        } else {
            move_speed(value)?
        });
    }
    if mask & 0x80000 != 0 {
        super::actor_effects::player_spotanims_old(r, &mut out.effects)?;
    }
    if mask & 0x10000 != 0 {
        r.take(7)?;
    }
    if mask & 0x0010_0000 != 0 {
        super::actor_effects::player_hitmarks(r, &mut out.effects)?;
    }
    if mask & 0x8000 != 0 {
        out.exact_move = Some(ExactMove {
            start_delta_x: r.u8()?.cast_signed(),
            start_delta_y: r.i8_sub()?,
            end_delta_x: r.u8()?.cast_signed(),
            end_delta_y: r.u8()?.cast_signed(),
            start_delay: r.u16_add_le()?,
            end_delay: r.u16_add_le()?,
            direction: r.u16_add_le()?,
        });
    }
    if mask & 0x10 != 0 {
        out.sequence = SequenceChange::Set(sequence_id(r.u16_add_le()?));
        out.sequence_delay = r.u8_add()?;
    }
    if mask & 0x400 != 0 {
        r.u16_le()?;
        r.u16_add_le()?;
        r.i8_neg()?;
        r.i8_add()?;
        r.u8()?;
        r.u8_add()?;
    }
    if mask & 8 != 0 {
        r.u8_neg()?;
    }
    if mask & 1 != 0 {
        out.speech
            .push(rustclient_model::SpeechUpdate::Say(super::speech::say(r)?));
    }
    Ok(out)
}
/// Consume one NUL-terminated string, bounded like the other info-block texts.
fn skip_text(r: &mut Reader<'_>) -> Result<()> {
    for _ in 0..=256 {
        if r.u8()? == 0 {
            return Ok(());
        }
    }
    Err(ProtocolError::new("player extended", "unterminated text"))
}
fn read_mask(r: &mut Reader<'_>) -> Result<u32> {
    let mut mask = u32::from(r.u8()?);
    if mask & 0x80 != 0 {
        mask |= u32::from(r.u8()?) << 8;
    }
    if mask & 0x2000 != 0 {
        mask |= u32::from(r.u8()?) << 16;
    }
    Ok(mask)
}
fn move_speed(value: i8) -> Result<MoveSpeed> {
    match value {
        -1 => Ok(MoveSpeed::Stationary),
        0 => Ok(MoveSpeed::Crawl),
        1 => Ok(MoveSpeed::Walk),
        2 => Ok(MoveSpeed::Run),
        _ => Err(ProtocolError::new(
            "player extended",
            format!("invalid traversal speed {value}"),
        )),
    }
}
#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_model::SequenceId;
    #[test]
    fn selected_240_mask_extensions_order_and_transforms_match_writer_vector() {
        // Extension bits 7/13; temporary speed, exact move, sequence, overhead text.
        // p1Alt2(2), signed (-1,0,2,1), p2Alt3(5,10,1024), sequence 0x1234 delay 5.
        let bytes = [
            0x91, 0x88, 0xfe, 0xff, 128, 2, 1, 133, 0, 138, 0, 128, 4, 0xb4, 0x12, 0x85, b'H',
            b'i', 0,
        ];
        let mut reader = Reader::new(&bytes, "player family fixture");
        let value = decode_extended(&mut reader, None).unwrap();
        assert_eq!(reader.offset, bytes.len());
        assert_eq!(
            value.sequence,
            SequenceChange::Set(Some(SequenceId::new(0x1234)))
        );
        assert_eq!(value.sequence_delay, 5);
        assert_eq!(
            value.speech,
            vec![rustclient_model::SpeechUpdate::Say("Hi".into())]
        );
        assert_eq!(value.temporary_speed, Some(MoveSpeed::Run));
        assert_eq!(
            value.exact_move,
            Some(ExactMove {
                start_delta_x: -1,
                start_delta_y: 0,
                end_delta_x: 2,
                end_delta_y: 1,
                start_delay: 5,
                end_delay: 10,
                direction: 1024
            })
        );
        let mut extended = Reader::new(&[0x80, 0x20, 0x10], "mask");
        assert_eq!(read_mask(&mut extended).unwrap(), 0x10_2080);
        for end in 0..bytes.len() {
            assert!(decode_extended(&mut Reader::new(&bytes[..end], "truncation"), None).is_err());
        }
    }

    #[test]
    fn selected_240_reversed_appearance_and_speed_controls_match_writer_vectors() {
        // Independently packed core appearance: empty equipment, colours 1..5,
        // sequences 808/823/819/820/821/822/824, name Test, combat 3.
        let bytes = [
            4, 73, 0, 0, 0, 3, 0, 116, 115, 101, 84, 56, 3, 54, 3, 53, 3, 52, 3, 51, 3, 55, 3, 40,
            3, 5, 4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 255, 255, 0,
        ];
        let appearance = decode_extended(&mut Reader::new(&bytes, "appearance vector"), None)
            .unwrap()
            .appearance
            .unwrap();
        assert_eq!(appearance.display_name, "Test");
        assert_eq!(appearance.colors, [1, 2, 3, 4, 5]);
        assert_eq!(
            appearance.movement_sequences.idle,
            Some(SequenceId::new(808))
        );
        assert_eq!(
            appearance.movement_sequences.run,
            Some(SequenceId::new(824))
        );
        // Persistent run, temporary crawl; temporary reset-to-position sentinel 127.
        let speed = decode_extended(
            &mut Reader::new(&[0x80, 0x48, 126, 0], "speed vector"),
            None,
        )
        .unwrap();
        assert_eq!(speed.persistent_speed, Some(MoveSpeed::Run));
        assert_eq!(speed.temporary_speed, Some(MoveSpeed::Crawl));
        let reset =
            decode_extended(&mut Reader::new(&[0x80, 8, 129], "reset vector"), None).unwrap();
        assert_eq!(reset.temporary_speed, Some(MoveSpeed::Stationary));
    }
}

#[cfg(test)]
mod name_extras_tests {
    use super::*;

    #[test]
    fn name_extras_and_old_spot_animations_are_consumed_in_writer_order() {
        // Mask 0x802a0 = 0x80 | 0x200 | 0x2000 | 0x80000 with the 0x20 spot animation absent.
        // NAME_EXTRAS: three NUL-terminated strings. SPOTANIM_OLD: g1Alt3 count 1, slot g1Alt1 3,
        // id g2Alt1 0x0102, height/delay g4Alt1 7 and 9.
        let bytes = [
            0x80, 0x22, 0x08, b'a', 0, 0, b'b', b'c', 0, 127, 131, 2, 1, 9, 0, 7, 0,
        ];
        let mut reader = Reader::new(&bytes, "fixture");
        let out = decode_extended(&mut reader, None).unwrap();
        assert_eq!(reader.offset, bytes.len());
        assert_eq!(
            out.effects.spotanims,
            [rustclient_model::ActorSpotAnim {
                slot: 3,
                id: Some(rustclient_model::SpotAnimationId::new(0x0102)),
                height: 7,
                delay: 9,
                looping: false,
            }]
        );
        for length in 0..bytes.len() {
            assert!(
                decode_extended(&mut Reader::new(&bytes[..length], "fixture"), None).is_err(),
                "prefix {length}"
            );
        }
    }
}
