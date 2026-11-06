//! Verified stock NPC extended blocks.
//!
//! Model customisation blocks are consumed with their exact widths but not retained: no
//! consumer reads them yet. The recolour and retexture blocks of the V2 forms carry no count on
//! the wire; the count is the NPC definition's own, so the decoder asks a [`NpcShapeLookup`].
use super::{
    info_fields::{face, smart},
    player_appearance::sequence_id,
};
use crate::{ProtocolError, Result, wire::Reader};
use rustclient_model::{ActionMask, NpcAttributeUpdate, NpcDefinitionId, NpcIndex, NpcProperty};
/// Recolour and retexture counts of an NPC definition, or `None` when it is unknown.
pub type NpcShapeLookup = Box<dyn FnMut(NpcDefinitionId) -> Option<(usize, usize)> + Send>;
const SUPPORTED: u32 = 0x1
    | 0x2
    | 0x4
    | 0x8
    | 0x10
    | 0x20
    | 0x80
    | 0x100
    | 0x200
    | 0x400
    | 0x800
    | 0x1000
    | 0x2000
    | 0x8000
    | 0x0001_0000
    | 0x0002_0000
    | 0x0004_0000
    | 0x0100_0000
    | 0x0020_0000
    | 0x0008_0000
    | 0x0010_0000
    | 0x0200_0000
    | 0x0040_0000;
#[expect(
    clippy::too_many_lines,
    reason = "profile-defined ordered block layout"
)]
pub(super) fn decode_attributes(
    r: &mut Reader<'_>,
    index: NpcIndex,
    definition: &mut NpcDefinitionId,
    shapes: &mut dyn FnMut(NpcDefinitionId) -> Option<(usize, usize)>,
) -> Result<NpcAttributeUpdate> {
    let mut mask = u32::from(r.u8()?);
    if mask & 0x20 != 0 {
        mask |= u32::from(r.u8()?) << 8;
    }
    if mask & 0x1000 != 0 {
        mask |= u32::from(r.u8()?) << 16;
    }
    if mask & 0x0004_0000 != 0 {
        mask |= u32::from(r.u8()?) << 24;
    }
    if mask & !SUPPORTED != 0 {
        return Err(ProtocolError::new(
            "NPC extended",
            "unsupported customization or mask bits",
        ));
    }
    let mut changes = Vec::new();
    let mut effects = rustclient_model::ActorEffects::default();
    if mask & 0x200 != 0 {
        r.take(8)?;
    }
    if mask & 0x0020_0000 != 0 {
        // BAS_CHANGE: g4Alt3 flags, then one two-byte animation per flag bit 0..=14.
        let b = r.take(4)?;
        let flags = u32::from_be_bytes([b[1], b[0], b[3], b[2]]);
        r.take(2 * (flags & 0x7fff).count_ones() as usize)?;
    }
    if mask & 2 != 0 {
        super::actor_effects::npc_spotanims(r, &mut effects)?;
    }
    if mask & 0x0008_0000 != 0 {
        customisation(r, Customisation::Head, *definition, shapes)?;
    }
    if mask & 0x0001_0000 != 0 {
        let flags = r.u8()?.wrapping_sub(128);
        for bit in 0..8 {
            if flags & (1 << bit) != 0 {
                let width = if r.bytes.get(r.offset).is_some_and(|v| v & 128 != 0) {
                    4
                } else {
                    2
                };
                r.take(width)?;
                smart(r)?;
            }
        }
    }
    if mask & 0x400 != 0 {
        let flags = 128_u8.wrapping_sub(r.u8()?);
        changes.push(NpcProperty::Actions(ActionMask::new(std::array::from_fn(
            |i| flags & (1 << i) != 0,
        ))));
    }
    if mask & 0x0200_0000 != 0 {
        body_v3(r)?;
    }
    if mask & 0x10 != 0 {
        changes.push(NpcProperty::Overhead(super::speech::say(r)?));
    }
    if mask & 1 != 0 {
        r.take(5)?;
    }
    if mask & 0x800 != 0 {
        r.take(4)?;
    }
    if mask & 0x2000 != 0 {
        changes.push(NpcProperty::ExactMove(rustclient_model::ExactMove {
            start_delta_x: r.i8_add()?,
            start_delta_y: r.i8_sub()?,
            end_delta_x: r.u8()?.cast_signed(),
            end_delta_y: r.i8_add()?,
            start_delay: r.u16_le()?,
            end_delay: r.u16_add()?,
            direction: r.u16_le()?,
        }));
    }
    if mask & 4 != 0 {
        let lo = r.u8()?;
        let hi = r.u8()?;
        let delay = u32::from(r.u8()?.wrapping_sub(128));
        changes.push(NpcProperty::Sequence {
            sequence: sequence_id(u16::from_le_bytes([lo, hi])),
            delay,
        });
    }
    if mask & 0x0002_0000 != 0 {
        super::actor_effects::npc_hitmarks(r, &mut effects)?;
    }
    if mask & 0x0040_0000 != 0 {
        super::actor_effects::npc_spotanims_old(r, &mut effects)?;
    }
    if mask & 0x8000 != 0 {
        r.take(7)?;
    }
    if mask & 0x0010_0000 != 0 {
        customisation(r, Customisation::BodyV2, *definition, shapes)?;
    }
    if mask & 0x100 != 0 {
        let name = read_text(r)?;
        changes.push(NpcProperty::Name((!name.is_empty()).then_some(name)));
    }
    if mask & 8 != 0 {
        let hi = r.u8()?;
        let lo = r.u8()?.wrapping_sub(128);
        *definition = NpcDefinitionId::new(u32::from(u16::from_be_bytes([hi, lo])));
        changes.push(NpcProperty::Definition(*definition));
    }
    if mask & 0x80 != 0 {
        let flag = r.u8()?.wrapping_sub(128);
        let block = face(r, flag)?;
        if let Some(angle) = super::info_fields::angle_of(&block) {
            changes.push(NpcProperty::Facing(angle));
        }
        changes.push(NpcProperty::FacingBlock(block));
    }
    if mask & 0x0100_0000 != 0 {
        super::actor_effects::npc_headbars(r, &mut effects)?;
    }
    if !effects.is_empty() {
        changes.push(NpcProperty::Effects(effects));
    }
    Ok(NpcAttributeUpdate { index, changes })
}
#[derive(Clone, Copy)]
enum Customisation {
    Head,
    BodyV2,
}
/// `HEAD_CUSTOMISATION` (V2) and `BODY_CUSTOMISATION_V2`: `g1Alt1` flags (1 resets), then the
/// model list (count byte, four bytes each), the definition-sized recolour and retexture lists
/// (two bytes each) and a mirror byte.
fn customisation(
    r: &mut Reader<'_>,
    kind: Customisation,
    definition: NpcDefinitionId,
    shapes: &mut dyn FnMut(NpcDefinitionId) -> Option<(usize, usize)>,
) -> Result<()> {
    let flags = r.u8_add()?;
    if flags & 1 != 0 {
        return Ok(());
    }
    if flags & 2 != 0 {
        let count = match kind {
            Customisation::Head => r.u8_add()?,
            Customisation::BodyV2 => r.u8_neg()?,
        };
        r.take(4 * usize::from(count))?;
    }
    if flags & 0xc != 0 {
        let (recolours, retextures) = shapes(definition).ok_or_else(|| {
            ProtocolError::new(
                "NPC extended",
                "customisation needs an NPC definition the cache cannot supply",
            )
        })?;
        if flags & 4 != 0 {
            r.take(2 * recolours)?;
        }
        if flags & 8 != 0 {
            r.take(2 * retextures)?;
        }
    }
    if flags & 0x10 != 0 {
        r.u8()?;
    }
    Ok(())
}
/// `BODY_CUSTOMISATION_V3`: `g1Alt3` flags (1 resets) with self-describing lists: models (`g1`
/// count, four bytes each), recolours (`g1`, two bytes), retextures (`g1Alt2`, two bytes),
/// mirror (no bytes) and a player composition (`g1Alt1` body type, `g1Alt1` kit count, kits).
fn body_v3(r: &mut Reader<'_>) -> Result<()> {
    let flags = r.u8_sub()?;
    if flags & 1 != 0 {
        return Ok(());
    }
    if flags & 2 != 0 {
        let count = usize::from(r.u8()?);
        r.take(4 * count)?;
    }
    if flags & 4 != 0 {
        let count = usize::from(r.u8()?);
        r.take(2 * count)?;
    }
    if flags & 8 != 0 {
        let count = usize::from(r.u8_neg()?);
        r.take(2 * count)?;
    }
    if flags & 0x20 != 0 {
        r.u8_add()?;
        let kits = usize::from(r.u8_add()?);
        r.take(2 * kits)?;
    }
    Ok(())
}
fn read_text(r: &mut Reader<'_>) -> Result<String> {
    let mut text = String::new();
    for _ in 0..=256 {
        let byte = r.u8()?;
        if byte == 0 {
            return Ok(text);
        }
        text.push(crate::wire::decode_cp1252(byte));
    }
    Err(ProtocolError::new("NPC text", "text limit exceeded"))
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn forced_move_and_sequence_mixed_mask_uses_npc_transforms_and_checks_every_prefix() {
        let bytes = [0x24, 0x20, 126, 125, 4, 123, 2, 0, 0, 136, 0, 6, 40, 3, 130];
        let update = decode_attributes(
            &mut Reader::new(&bytes, "fixture"),
            NpcIndex::new(42),
            &mut NpcDefinitionId::new(0),
            &mut |_| None,
        )
        .unwrap();
        assert_eq!(
            update.changes,
            vec![
                NpcProperty::ExactMove(rustclient_model::ExactMove {
                    start_delta_x: -2,
                    start_delta_y: 3,
                    end_delta_x: 4,
                    end_delta_y: -5,
                    start_delay: 2,
                    end_delay: 8,
                    direction: 1536,
                }),
                NpcProperty::Sequence {
                    sequence: Some(rustclient_model::SequenceId::new(808)),
                    delay: 2
                }
            ]
        );
        for length in 0..bytes.len() {
            assert!(
                decode_attributes(
                    &mut Reader::new(&bytes[..length], "fixture"),
                    NpcIndex::new(42),
                    &mut NpcDefinitionId::new(0),
                    &mut |_| None,
                )
                .is_err()
            );
        }
    }

    /// Decode `bytes` for an NPC of definition 5 whose recolour and retexture counts are 2 and 1,
    /// asserting every byte is consumed.
    fn consume(bytes: &[u8]) -> Result<NpcAttributeUpdate> {
        let mut reader = Reader::new(bytes, "fixture");
        let update = decode_attributes(
            &mut reader,
            NpcIndex::new(1),
            &mut NpcDefinitionId::new(5),
            &mut |id| (id == NpcDefinitionId::new(5)).then_some((2, 1)),
        )?;
        assert_eq!(reader.offset, bytes.len(), "block widths");
        Ok(update)
    }

    #[test]
    fn bas_change_consumes_one_animation_per_flag_bit() {
        // Mask 0x201020: BAS_CHANGE. Flags g4Alt3 = 5 (bits 0 and 2) has wire bytes 0, 0, 5, 0.
        let bytes = [0x20, 0x10, 0x20, 0, 0, 5, 0, 1, 2, 3, 4];
        assert!(consume(&bytes).unwrap().changes.is_empty());
        assert!(consume(&bytes[..bytes.len() - 1]).is_err());
    }

    #[test]
    fn model_customisations_use_the_definition_counts_and_the_v3_self_described_lists() {
        // Mask 0x181020: HEAD_CUSTOMISATION (0x80000) and BODY_CUSTOMISATION_V2 (0x100000).
        // Head flags 0x12 (models, mirror): g1Alt1 count 1, one model, mirror byte.
        let mut bytes = vec![0x20, 0x10, 0x18, 0x92, 129, 1, 2, 3, 4, 1];
        // Body V2 flags 0x1e: g1Alt2 count 1 (255), one model, 2 recolours, 1 retexture, mirror.
        bytes.extend([0x9e, 255, 1, 2, 3, 4, 1, 2, 3, 4, 5, 6, 1]);
        assert!(consume(&bytes).unwrap().changes.is_empty());
        for length in 0..bytes.len() {
            assert!(consume(&bytes[..length]).is_err(), "prefix {length}");
        }
        // Customisation sized by the definition fails when the cache cannot supply it.
        let mut unknown = Reader::new(&bytes, "fixture");
        assert!(
            decode_attributes(
                &mut unknown,
                NpcIndex::new(1),
                &mut NpcDefinitionId::new(5),
                &mut |_| None,
            )
            .is_err()
        );
        // Mask 0x2041020 (byte 3 present): BODY_CUSTOMISATION_V3 flags 0x2e = 128 - 82: models
        // (1), recolours (2), retextures (g1Alt2 1), composition (body type, one kit).
        let v3 = [
            0x20, 0x10, 0x04, 0x02, 82, 1, 9, 9, 9, 9, 2, 1, 1, 2, 2, 255, 3, 3, 128, 129, 4, 4,
        ];
        assert!(consume(&v3).unwrap().changes.is_empty());
        assert!(consume(&v3[..v3.len() - 1]).is_err());
    }

    #[test]
    fn old_spot_animations_are_retained_without_a_loop_flag() {
        // Mask 0x401020: SPOTANIM_OLD. One entry: slot 3, id 0x0102, height 7, delay 9.
        let update = consume(&[0x20, 0x10, 0x40, 129, 3, 130, 1, 7, 0, 9, 0]).unwrap();
        let [NpcProperty::Effects(effects)] = update.changes.as_slice() else {
            panic!("expected effects");
        };
        assert_eq!(
            effects.spotanims,
            [rustclient_model::ActorSpotAnim {
                slot: 3,
                id: Some(rustclient_model::SpotAnimationId::new(0x0102)),
                height: 7,
                delay: 9,
                looping: false,
            }]
        );
    }

    #[test]
    fn facing_blocks_keep_their_entity_area_angle_or_cleared_payload() {
        use rustclient_model::{FacingBlock, FacingEntityKind, FacingTarget, FacingWalkPolicy};
        let facing = |bytes: &[u8]| {
            let update = consume(bytes).unwrap();
            let [NpcProperty::FacingBlock(block), ..] = update.changes.as_slice() else {
                // An angle block also emits the plain orientation change first.
                let Some(NpcProperty::FacingBlock(block)) = update.changes.last() else {
                    panic!("no facing block in {:?}", update.changes);
                };
                return *block;
            };
            *block
        };
        // Mask 0x80. Flag g1Alt1: walk 0, kind 0 (entity), control bit 6 -> 0x40 + 128.
        // Entity type 2 (player), index 5 as two bytes, fallback angle 10.
        let entity = facing(&[0x80, 0xc0, 2, 0, 5, 10]);
        assert_eq!(
            entity,
            FacingBlock {
                target: FacingTarget::Entity {
                    kind: FacingEntityKind::Player,
                    index: Some(5),
                    fallback_angle: 10,
                },
                walk: FacingWalkPolicy::CancelOnWalk,
                instant: true,
            }
        );
        assert_eq!(
            entity.interaction(),
            Some(rustclient_model::Interaction::Player(
                rustclient_model::PlayerIndex::new(5)
            ))
        );
        // The null index 32767 and the four-byte form (top bit set, masked off).
        let null = facing(&[0x80, 128, 1, 0x7f, 0xff, 0]);
        assert!(matches!(
            null.target,
            FacingTarget::Entity { index: None, .. }
        ));
        assert_eq!(null.interaction(), None);
        let wide = facing(&[0x80, 128, 1, 0x80, 0, 0, 9, 0]);
        assert!(matches!(
            wide.target,
            FacingTarget::Entity { index: Some(9), .. }
        ));
        // Area (kind 1, walk policy 1): x 3, z 4, extents packed as 2 | 3 << 4.
        let area = facing(&[0x80, 128 + 8 + 1, 3, 4, 0x32]);
        assert_eq!(
            area.target,
            FacingTarget::Area {
                x: 3,
                z: 4,
                size_x: 2,
                size_z: 3,
            }
        );
        assert_eq!(area.walk, FacingWalkPolicy::TurnOnWalk);
        // Angle (kind 2) and cleared (kind 3).
        assert_eq!(
            facing(&[0x80, 128 + 16, 100]).target,
            FacingTarget::Angle(100)
        );
        assert_eq!(facing(&[0x80, 128 + 24]).target, FacingTarget::Cleared);
        // An unknown walk policy or entity type is refused.
        assert!(consume(&[0x80, 128 + 2, 0]).is_err());
        assert!(consume(&[0x80, 128, 9, 0, 0, 0]).is_err());
    }
}
