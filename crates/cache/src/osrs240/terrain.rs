use crate::{
    Source,
    error::Result,
    reader::Reader,
    world::{StaticLocation, TerrainOverlay, TerrainTile},
};
use rustclient_model::{
    FloorOverlayId, FloorUnderlayId, LocOrientation, ObjectDefinitionId, Plane, RegionCoord,
    WorldTile,
};
const TILES: usize = 4 * 64 * 64;
/// The modern terrain stream uses 16-bit opcodes and one-based floor identifiers.
pub fn terrain(bytes: &[u8], coord: RegionCoord, source: Source) -> Result<Vec<TerrainTile>> {
    let mut r = Reader::new(bytes, source);
    let mut tiles = vec![TerrainTile::default(); TILES];
    for plane in 0..4 {
        for x in 0..64 {
            for y in 0..64 {
                let index = plane * 4096 + x * 64 + y;
                let mut tile = TerrainTile::default();
                let mut terminated = false;
                for _ in 0..64 {
                    let opcode = r.u16()?;
                    match opcode {
                        0 | 1 => {
                            let below = if plane == 0 {
                                0
                            } else {
                                tiles[index - 4096].elevation
                            };
                            tile.elevation = if opcode == 1 {
                                let value = r.u8()?;
                                below + i32::from(if value == 1 { 0 } else { value }) * 8
                            } else if plane > 0 {
                                below + 240
                            } else {
                                noise_height(
                                    coord.x * 64
                                        + i32::try_from(x)
                                            .map_err(|_| source.error("terrain", "x overflow"))?,
                                    coord.y * 64
                                        + i32::try_from(y)
                                            .map_err(|_| source.error("terrain", "y overflow"))?,
                                ) * 8
                            };
                            terminated = true;
                            break;
                        }
                        2..=49 => {
                            let id = r.u16()?;
                            tile.overlay = (id != 0).then_some(TerrainOverlay {
                                id: FloorOverlayId::new(u32::from(id.saturating_sub(1))),
                                shape: u8::try_from((opcode - 2) / 4)
                                    .map_err(|_| source.error("terrain", "shape overflow"))?,
                                rotation: u8::try_from((opcode - 2) & 3)
                                    .map_err(|_| source.error("terrain", "rotation overflow"))?,
                            });
                        }
                        50..=81 => {
                            let flags = opcode - 49;
                            tile.settings = u8::try_from(flags)
                                .map_err(|_| source.error("terrain", "settings overflow"))?;
                        }
                        _ => tile.underlay = Some(FloorUnderlayId::new(u32::from(opcode - 82))),
                    }
                }
                if !terminated {
                    return Err(source.error("terrain", "tile opcode limit"));
                }
                tiles[index] = tile;
            }
        }
    }
    if r.remaining() > 0 {
        let flags = r.u8()?;
        if flags & !1 != 0 {
            return Err(source.error("terrain", "unsupported trailing flags"));
        }
        if flags & 1 != 0 {
            for _ in 0..4096 {
                let mut done = false;
                for _ in 0..64 {
                    match r.u16()? {
                        0 => {
                            done = true;
                            break;
                        }
                        1 => {
                            r.u8()?;
                            done = true;
                            break;
                        }
                        2..=49 => {
                            r.u16()?;
                        }
                        _ => {}
                    }
                }
                if !done {
                    return Err(source.error("terrain", "trailing tile opcode limit"));
                }
            }
        }
    }
    r.finish()?;
    Ok(tiles)
}
pub fn locations(bytes: &[u8], coord: RegionCoord, source: Source) -> Result<Vec<StaticLocation>> {
    let mut r = Reader::new(bytes, source);
    let mut id = -1_i64;
    let mut out = Vec::new();
    loop {
        let mut delta = 0_u32;
        loop {
            let value = u32::from(r.u_smart()?);
            delta = delta
                .checked_add(value)
                .ok_or_else(|| source.error("locations", "object delta overflow"))?;
            if value != 32767 {
                break;
            }
        }
        if delta == 0 {
            break;
        }
        id += i64::from(delta);
        let object = ObjectDefinitionId::new(
            u32::try_from(id).map_err(|_| source.error("locations", "object id overflow"))?,
        );
        let mut packed = 0_u32;
        loop {
            let change = u32::from(r.u_smart()?);
            if change == 0 {
                break;
            }
            packed = packed
                .checked_add(change - 1)
                .filter(|p| *p < 16384)
                .ok_or_else(|| source.error("locations", "tile delta outside square"))?;
            let info = r.u8()?;
            let shape = info >> 2;
            if shape > 22 {
                return Err(source.error("locations", "unsupported placement shape"));
            }
            let plane = Plane::new((packed >> 12).to_le_bytes()[0])
                .ok_or_else(|| source.error("locations", "invalid plane"))?;
            let rotation = LocOrientation::new(info & 3)
                .ok_or_else(|| source.error("locations", "invalid orientation"))?;
            if out.len() >= 100_000 {
                return Err(source.error("locations", "location count limit"));
            }
            out.push(StaticLocation {
                object,
                tile: WorldTile::new(
                    coord.x * 64
                        + i32::try_from((packed >> 6) & 63)
                            .map_err(|_| source.error("locations", "x overflow"))?,
                    coord.y * 64
                        + i32::try_from(packed & 63)
                            .map_err(|_| source.error("locations", "y overflow"))?,
                    plane,
                ),
                shape,
                orientation: rotation,
            });
        }
    }
    r.finish()?;
    Ok(out)
}
fn hash(x: i32, y: i32) -> i32 {
    let mut n = x.wrapping_add(y.wrapping_mul(57));
    n ^= n.wrapping_shl(13);
    let v = n
        .wrapping_mul(n.wrapping_mul(n).wrapping_mul(15_731).wrapping_add(789_221))
        .wrapping_add(1_376_312_589)
        & i32::MAX;
    (v >> 19) & 255
}
fn smooth(x: i32, y: i32) -> i32 {
    (hash(x - 1, y - 1) + hash(x + 1, y - 1) + hash(x - 1, y + 1) + hash(x + 1, y + 1)) / 16
        + (hash(x - 1, y) + hash(x + 1, y) + hash(x, y - 1) + hash(x, y + 1)) / 8
        + hash(x, y) / 4
}
fn interpolate(x: i32, y: i32, scale: i32) -> i32 {
    let weight = |v: i32| match (scale, v & (scale - 1)) {
        (1, _) | (_, 0) => 0,
        (2, 1) | (4, 2) => 32768,
        (4, 1) => 9598,
        _ => 55938,
    };
    let lerp = |a: i32, b: i32, w: i32| (((65_536 - w) * a) >> 16) + ((w * b) >> 16);
    let a = lerp(
        smooth(x / scale, y / scale),
        smooth(x / scale + 1, y / scale),
        weight(x),
    );
    let b = lerp(
        smooth(x / scale, y / scale + 1),
        smooth(x / scale + 1, y / scale + 1),
        weight(x),
    );
    lerp(a, b, weight(y))
}
#[expect(
    clippy::cast_possible_truncation,
    reason = "reference noise scales a bounded signed value with truncation toward zero"
)]
fn noise_height(x: i32, y: i32) -> i32 {
    let x = x + 932_731;
    let y = y + 556_238;
    let value = interpolate(x + 45365, y + 91923, 4) - 128
        + ((interpolate(x + 10294, y + 37821, 2) - 128) >> 1)
        + ((interpolate(x, y, 1) - 128) >> 2);
    ((f64::from(value) * 0.3) as i32 + 35).clamp(10, 60)
}
