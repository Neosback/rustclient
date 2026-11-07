//! Revision-240 hitmark, health bar and spot animation blocks of the player and NPC info packets.
//!
//! Field order and byte transforms follow the rsprox 240 `PlayerInfoClient`/`NpcInfoClient`
//! (`decodeHitmark(s)`, `decodeHeadbar(s)`, `decodeSpotanims`), which agree with the skip widths
//! this module replaces and with the stock `classDF.method3050` reader.
use super::info_fields::smart;
use crate::{Result, wire::Reader};
use rustclient_model::{
    ActorEffects, ActorSpotAnim, Headbar, HeadbarFill, Hitmark, SpotAnimationId,
};

/// Most entries one block may carry; stock would loop on the byte count, a hostile peer could not
/// exceed 255 anyway, but this keeps the per-update allocation explicit.
const MAX_ENTRIES: usize = 255;
/// `HEADBARS` end time that removes the bar instead of updating it.
const HEADBAR_REMOVED: u16 = 32767;

fn hitmarks(r: &mut Reader<'_>, count: u8, out: &mut ActorEffects) -> Result<()> {
    for _ in 0..usize::from(count).min(MAX_ENTRIES) {
        out.hitmarks.push(Hitmark {
            kind: smart(r)?,
            amount: smart(r)?,
            delay: smart(r)?,
            limit: smart(r)?,
        });
    }
    Ok(())
}

/// Read `count` health bar entries. The start fill is `g1Alt3` (`128 - v`) when `start_fill_sub`
/// (NPCs) and plain `g1` otherwise (players); the end fill, when the bar moves, is always `g1Alt2`.
fn headbars(
    r: &mut Reader<'_>,
    count: u8,
    start_fill_sub: bool,
    out: &mut ActorEffects,
) -> Result<()> {
    for _ in 0..usize::from(count).min(MAX_ENTRIES) {
        let kind = smart(r)?;
        let end_time = smart(r)?;
        if end_time == HEADBAR_REMOVED {
            out.headbars.push(Headbar { kind, fill: None });
            continue;
        }
        let start_time = smart(r)?;
        let start = if start_fill_sub { r.u8_sub()? } else { r.u8()? };
        let end = if end_time > 0 { r.u8_neg()? } else { start };
        out.headbars.push(Headbar {
            kind,
            fill: Some(HeadbarFill {
                start_time,
                end_time,
                start_fill: start,
                end_fill: end,
            }),
        });
    }
    Ok(())
}

fn spot(slot: u8, id: u16, height_delay: u32, looping: bool) -> ActorSpotAnim {
    ActorSpotAnim {
        slot,
        id: (id != u16::MAX).then(|| SpotAnimationId::new(u32::from(id))),
        height: u16::try_from(height_delay >> 16).unwrap_or(u16::MAX),
        delay: u16::try_from(height_delay & 0xffff).unwrap_or(u16::MAX),
        looping,
    }
}

fn be32(r: &mut Reader<'_>) -> Result<u32> {
    let b = r.take(4)?;
    Ok(u32::from_be_bytes([b[0], b[1], b[2], b[3]]))
}
fn le32(r: &mut Reader<'_>) -> Result<u32> {
    let b = r.take(4)?;
    Ok(u32::from_le_bytes([b[0], b[1], b[2], b[3]]))
}

/// Player `HITMARKS` (0x100000): `g1` count.
pub(super) fn player_hitmarks(r: &mut Reader<'_>, out: &mut ActorEffects) -> Result<()> {
    let count = r.u8()?;
    hitmarks(r, count, out)
}
/// Player `HEADBARS` (0x20000): `g1Alt3` count, start fill `g1`, end fill `g1Alt2`.
pub(super) fn player_headbars(r: &mut Reader<'_>, out: &mut ActorEffects) -> Result<()> {
    let count = r.u8_sub()?;
    headbars(r, count, false, out)
}
/// Player `SPOTANIM` (0x20): `g1Alt1` count; slot `g1Alt2`, id `g2Alt1`, height/delay `g4`, loop
/// `g1Alt3`.
pub(super) fn player_spotanims(r: &mut Reader<'_>, out: &mut ActorEffects) -> Result<()> {
    let count = r.u8_add()?;
    for _ in 0..usize::from(count).min(MAX_ENTRIES) {
        let slot = r.u8_neg()?;
        let id = r.u16_le()?;
        let height_delay = be32(r)?;
        let looping = r.u8_sub()? == 1;
        out.spotanims.push(spot(slot, id, height_delay, looping));
    }
    Ok(())
}
/// Player `SPOTANIM_OLD` (0x80000): `g1Alt3` count; slot `g1Alt1`, id `g2Alt1`, height/delay
/// `g4Alt1`. The old form carries no loop flag.
pub(super) fn player_spotanims_old(r: &mut Reader<'_>, out: &mut ActorEffects) -> Result<()> {
    let count = r.u8_sub()?;
    for _ in 0..usize::from(count).min(MAX_ENTRIES) {
        let slot = r.u8_add()?;
        let id = r.u16_le()?;
        let height_delay = le32(r)?;
        out.spotanims.push(spot(slot, id, height_delay, false));
    }
    Ok(())
}
/// NPC `SPOTANIM_OLD` (0x400000): `g1Alt1` count; slot `g1`, id `g2Alt3`, height/delay `g4Alt3`.
pub(super) fn npc_spotanims_old(r: &mut Reader<'_>, out: &mut ActorEffects) -> Result<()> {
    let count = r.u8_add()?;
    for _ in 0..usize::from(count).min(MAX_ENTRIES) {
        let slot = r.u8()?;
        let id = r.u16_add_le()?;
        let b = r.take(4)?;
        // `g4Alt3` (stock `method13303`): read positions 1, 0, 3, 2 most-significant first.
        let height_delay = u32::from_be_bytes([b[1], b[0], b[3], b[2]]);
        out.spotanims.push(spot(slot, id, height_delay, false));
    }
    Ok(())
}
/// NPC `HITMARKS` (0x20000): `g1Alt1` count.
pub(super) fn npc_hitmarks(r: &mut Reader<'_>, out: &mut ActorEffects) -> Result<()> {
    let count = r.u8_add()?;
    hitmarks(r, count, out)
}
/// NPC `HEADBARS` (0x1000000): `g1Alt1` count, start fill `g1Alt3`, end fill `g1Alt2`.
pub(super) fn npc_headbars(r: &mut Reader<'_>, out: &mut ActorEffects) -> Result<()> {
    let count = r.u8_add()?;
    headbars(r, count, true, out)
}
/// NPC `SPOTANIM` (0x2): `g1Alt3` count; slot `g1`, id `g2`, height/delay `g4Alt1`, loop `g1Alt1`.
pub(super) fn npc_spotanims(r: &mut Reader<'_>, out: &mut ActorEffects) -> Result<()> {
    let count = r.u8_sub()?;
    for _ in 0..usize::from(count).min(MAX_ENTRIES) {
        let slot = r.u8()?;
        let id = r.u16()?;
        let height_delay = le32(r)?;
        let looping = r.u8_add()? == 1;
        out.spotanims.push(spot(slot, id, height_delay, looping));
    }
    Ok(())
}

#[cfg(test)]
mod tests {
    use super::*;

    fn run(
        bytes: &[u8],
        decode: fn(&mut Reader<'_>, &mut ActorEffects) -> Result<()>,
    ) -> Result<ActorEffects> {
        let mut out = ActorEffects::default();
        let mut r = Reader::new(bytes, "test");
        decode(&mut r, &mut out)?;
        assert_eq!(r.offset, bytes.len(), "block consumed exactly");
        Ok(out)
    }

    #[test]
    fn player_hitmarks_read_four_smarts_each() {
        // count 2: (type 12, 7, delay 0, limit 1) and (type 18, 300 as a two-byte smart, 4, 0)
        let bytes = [2, 12, 7, 0, 1, 18, 0x81, 0x2c, 4, 0];
        let out = run(&bytes, player_hitmarks).unwrap();
        assert_eq!(
            out.hitmarks,
            [
                Hitmark {
                    kind: 12,
                    amount: 7,
                    delay: 0,
                    limit: 1
                },
                Hitmark {
                    kind: 18,
                    amount: 0x12c,
                    delay: 4,
                    limit: 0
                },
            ]
        );
        assert!(run(&[2, 12, 7, 0], player_hitmarks).is_err(), "truncated");
    }

    #[test]
    fn headbars_distinguish_removal_movement_and_instant_fill() {
        // Player: count g1Alt3 = 128 - 3. Bar 1 removed (end 32767 = smart 0xffff),
        // bar 2 moves (end 30): start smart 5, start fill g1 40, end fill g1Alt2 = -10,
        // bar 3 instant (end 0): start smart 0, start fill 25.
        let bytes = [
            128 - 3,
            1,
            0xff,
            0xff,
            2,
            30,
            5,
            40,
            10_u8.wrapping_neg(),
            3,
            0,
            0,
            25,
        ];
        let out = run(&bytes, player_headbars).unwrap();
        assert_eq!(
            out.headbars,
            [
                Headbar {
                    kind: 1,
                    fill: None
                },
                Headbar {
                    kind: 2,
                    fill: Some(HeadbarFill {
                        start_time: 5,
                        end_time: 30,
                        start_fill: 40,
                        end_fill: 10
                    })
                },
                Headbar {
                    kind: 3,
                    fill: Some(HeadbarFill {
                        start_time: 0,
                        end_time: 0,
                        start_fill: 25,
                        end_fill: 25
                    })
                },
            ]
        );
        // NPC: count g1Alt1, start fill g1Alt3 = 128 - v, end fill g1Alt2.
        let npc = [128 + 1, 2, 30, 5, 128 - 40, 10_u8.wrapping_neg()];
        let out = run(&npc, npc_headbars).unwrap();
        assert_eq!(
            out.headbars[0].fill,
            Some(HeadbarFill {
                start_time: 5,
                end_time: 30,
                start_fill: 40,
                end_fill: 10
            })
        );
    }

    #[test]
    fn spot_animations_split_height_and_delay_and_clear_with_65535() {
        // Player: count g1Alt1 = 128 + 2; slot g1Alt2 = -slot; id g2Alt1 LE; hd g4 BE; loop g1Alt3.
        let mut bytes = vec![128 + 2];
        bytes.extend([2_u8.wrapping_neg()]);
        bytes.extend(246_u16.to_le_bytes());
        bytes.extend(((92_u32 << 16) | 15).to_be_bytes());
        bytes.push(128 - 1);
        bytes.extend([0_u8.wrapping_neg()]);
        bytes.extend(65535_u16.to_le_bytes());
        bytes.extend(0_u32.to_be_bytes());
        bytes.push(128);
        let out = run(&bytes, player_spotanims).unwrap();
        assert_eq!(
            out.spotanims,
            [
                ActorSpotAnim {
                    slot: 2,
                    id: Some(SpotAnimationId::new(246)),
                    height: 92,
                    delay: 15,
                    looping: true
                },
                ActorSpotAnim {
                    slot: 0,
                    id: None,
                    height: 0,
                    delay: 0,
                    looping: false
                },
            ]
        );
        // NPC: count g1Alt3; slot g1; id g2 BE; hd g4Alt1 LE; loop g1Alt1.
        let mut npc = vec![128 - 1, 4];
        npc.extend(246_u16.to_be_bytes());
        npc.extend(((92_u32 << 16) | 15).to_le_bytes());
        npc.push(128 + 1);
        let out = run(&npc, npc_spotanims).unwrap();
        assert_eq!(
            out.spotanims,
            [ActorSpotAnim {
                slot: 4,
                id: Some(SpotAnimationId::new(246)),
                height: 92,
                delay: 15,
                looping: true
            }]
        );
    }
}
