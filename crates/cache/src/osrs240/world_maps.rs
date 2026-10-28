//! Revision-240 normal-map loading, including reference-table-defined empty regions.
use super::{hash_file, terrain};
use crate::{
    Cache, FileId, GroupId, IndexId, Source,
    error::Result,
    world::{MapSquare, MapSquareSource, TerrainTile},
};
use rustclient_model::{ObjectDefinitionId, RegionCoord, WorldTile};
use rustclient_platform::ReadOnlyBlobs;
use sha2::{Digest, Sha256};
use std::collections::BTreeSet;
type Maps = (Vec<MapSquare>, BTreeSet<ObjectDefinitionId>, usize);
type Files = Vec<(FileId, Vec<u8>)>;

pub(super) fn load<S: ReadOnlyBlobs>(
    cache: &Cache<S>,
    origin: WorldTile,
    end: (i32, i32),
    digest: &mut Sha256,
    progress: &mut impl FnMut(&str),
) -> Result<Maps> {
    let source = Source::group(IndexId(5), GroupId(0));
    let table = cache
        .tables()
        .get(&IndexId(5))
        .ok_or_else(|| source.error("map lookup", "map reference table absent"))?;
    load_groups(origin, end, digest, progress, |group| {
        if !table.groups.contains_key(&group) {
            return Ok(None);
        }
        cache.group_files(IndexId(5), group).map(Some)
    })
}
fn load_groups(
    origin: WorldTile,
    end: (i32, i32),
    digest: &mut Sha256,
    progress: &mut impl FnMut(&str),
    mut group_files: impl FnMut(GroupId) -> Result<Option<Files>>,
) -> Result<Maps> {
    let (end_x, end_y) = end;
    let context = Source::group(IndexId(5), GroupId(0));
    let mut auxiliary_map_files = 0;
    let mut squares = Vec::new();
    let mut needed = BTreeSet::new();
    // Include the far border, whose tile anchors supply shared height corners.
    for x in origin.x / 64..=end_x / 64 {
        for y in origin.y / 64..=end_y / 64 {
            if x > 255 || y > 255 {
                return Err(context.error("world extent", "region outside profile range"));
            }
            progress("map squares");
            let group = GroupId(
                u32::try_from(x * 256 + y)
                    .map_err(|_| context.error("map lookup", "group overflow"))?,
            );
            let source = Source::group(IndexId(5), group);
            let Some(files) = group_files(group)? else {
                digest.update(b"rustclient:absent-map-square:v1");
                digest.update(group.0.to_be_bytes());
                squares.push(MapSquare {
                    coord: RegionCoord::new(x, y),
                    source: MapSquareSource::Absent,
                    tiles: vec![TerrainTile::default(); 4 * 64 * 64],
                    locations: Vec::new(),
                });
                continue;
            };
            let get = |id: u32| {
                files
                    .iter()
                    .find(|(f, _)| f.0 == id)
                    .map(|(_, b)| b.as_slice())
                    .ok_or_else(|| source.error("map lookup", format!("missing file {id}")))
            };
            auxiliary_map_files += files.iter().filter(|(id, _)| id.0 > 1).count();
            for (id, bytes) in files.iter().filter(|(id, _)| id.0 <= 1) {
                hash_file(
                    digest,
                    Source {
                        file: Some(*id),
                        ..source
                    },
                    bytes,
                );
            }
            let coord = RegionCoord::new(x, y);
            let tiles = terrain::terrain(
                get(0)?,
                coord,
                Source {
                    file: Some(FileId(0)),
                    ..source
                },
            )?;
            let locations = terrain::locations(
                get(1)?,
                coord,
                Source {
                    file: Some(FileId(1)),
                    ..source
                },
            )?;
            needed.extend(
                locations
                    .iter()
                    .filter(|l| {
                        l.tile.x >= origin.x
                            && l.tile.x < end_x
                            && l.tile.y >= origin.y
                            && l.tile.y < end_y
                    })
                    .map(|l| l.object),
            );
            squares.push(MapSquare {
                coord,
                source: MapSquareSource::Archive,
                tiles,
                locations,
            });
        }
    }
    initialize_missing(&mut squares, origin, end);
    Ok((squares, needed, auxiliary_map_files))
}

/// rl4.method10138, after every present terrain stream has been decoded. Heights are stored
/// positive-up here; copying preserves the reference order despite its opposite sign convention.
fn initialize_missing(squares: &mut [MapSquare], origin: WorldTile, end: (i32, i32)) {
    if origin.y / 8 + 6 >= 800 {
        return;
    }
    let missing: Vec<_> = squares
        .iter()
        .filter(|s| s.source == MapSquareSource::Absent)
        .map(|s| s.coord)
        .collect();
    for coord in missing {
        let (left, south) = (coord.x * 64, coord.y * 64);
        for y in south..=south + 64 {
            for x in left..=left + 64 {
                if x < origin.x || y < origin.y || x >= end.0 || y >= end.1 {
                    continue;
                }
                let mut elevation = tile(squares, x, y).map_or(0, |t| t.elevation);
                let sample = |sx, sy| {
                    if sx < origin.x || sy < origin.y || sx >= end.0 || sy >= end.1 {
                        return 0;
                    }
                    tile(squares, sx, sy).map_or(0, |t| t.elevation)
                };
                if x == left && x > origin.x {
                    elevation = sample(x - 1, y);
                }
                if x == left + 64 {
                    elevation = sample(x + 1, y);
                }
                if y == south && y > origin.y {
                    elevation = sample(x, y - 1);
                }
                if y == south + 64 {
                    elevation = sample(x, y + 1);
                }
                if let Some(target) = tile_mut(squares, x, y) {
                    target.elevation = elevation;
                    target.base_shadow = 127;
                }
            }
        }
    }
}
fn tile(squares: &[MapSquare], x: i32, y: i32) -> Option<&TerrainTile> {
    let square = squares
        .iter()
        .find(|s| s.coord == RegionCoord::new(x.div_euclid(64), y.div_euclid(64)))?;
    square
        .tiles
        .get(usize::try_from(x.rem_euclid(64) * 64 + y.rem_euclid(64)).ok()?)
}
fn tile_mut(squares: &mut [MapSquare], x: i32, y: i32) -> Option<&mut TerrainTile> {
    let square = squares
        .iter_mut()
        .find(|s| s.coord == RegionCoord::new(x.div_euclid(64), y.div_euclid(64)))?;
    square
        .tiles
        .get_mut(usize::try_from(x.rem_euclid(64) * 64 + y.rem_euclid(64)).ok()?)
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_model::Plane;
    fn files() -> Files {
        vec![
            (FileId(0), [0, 1, 10].repeat(4 * 64 * 64)),
            (FileId(1), vec![0]),
        ]
    }
    #[test]
    fn sparse_archive_contract_preserves_source_initialization_and_strict_present_errors() {
        let origin = WorldTile::new(0, 0, Plane::MIN);
        let mut hash = Sha256::new();
        let (maps, objects, auxiliary) =
            load_groups(origin, (104, 104), &mut hash, &mut |_| {}, |group| {
                Ok((group == GroupId(0)).then(files))
            })
            .unwrap();
        assert_eq!(maps.len(), 4);
        assert!(objects.is_empty());
        assert_eq!(auxiliary, 0);
        assert_eq!(maps[0].source, MapSquareSource::Archive);
        assert_eq!(maps[1].source, MapSquareSource::Absent);
        assert_eq!(tile(&maps, 63, 20).unwrap().elevation, 80);
        assert_eq!(tile(&maps, 64, 20).unwrap().elevation, 80); // west edge inherits its neighbour
        assert_eq!(tile(&maps, 65, 20).unwrap().elevation, 0);
        assert_eq!(tile(&maps, 64, 20).unwrap().base_shadow, 127);
        assert_eq!(tile(&maps, 20, 64).unwrap().elevation, 80); // south edge
        assert_eq!(tile(&maps, 64, 64).unwrap().elevation, 80); // later void square inherits already initialized neighbours
        let (east, _, _) = load_groups(
            origin,
            (104, 104),
            &mut Sha256::new(),
            &mut |_| {},
            |group| Ok((group == GroupId(256)).then(files)),
        )
        .unwrap();
        assert_eq!(tile(&east, 64, 20).unwrap().elevation, 80);
        assert_eq!(tile(&east, 64, 20).unwrap().base_shadow, 127); // inclusive boundary touches present neighbour
        assert_eq!(east[2].source, MapSquareSource::Archive);

        assert_eq!(maps[2].tiles[4096 + 20].elevation, 0); // upper void planes stay zero
        assert!(
            maps[2]
                .tiles
                .iter()
                .all(|t| t.underlay.is_none() && t.overlay.is_none())
        );
        // Isolate one empty square: at its northeast corner north must overwrite west.
        let mut corner: Vec<_> = [(0, 0, 80), (0, 1, 160), (1, 0, 0), (1, 1, 240)]
            .map(|(x, y, elevation)| MapSquare {
                coord: RegionCoord::new(x, y),
                source: if (x, y) == (1, 0) {
                    MapSquareSource::Absent
                } else {
                    MapSquareSource::Archive
                },
                tiles: vec![
                    TerrainTile {
                        elevation,
                        ..TerrainTile::default()
                    };
                    4 * 64 * 64
                ],
                locations: Vec::new(),
            })
            .into();
        initialize_missing(&mut corner, origin, (104, 104));
        assert_eq!(tile(&corner, 64, 64).unwrap().elevation, 240);
        assert_eq!(tile(&corner, 64, 64).unwrap().base_shadow, 127);
        let sparse_hash = hash.finalize();
        let mut hash = Sha256::new();
        load_groups(origin, (104, 104), &mut hash, &mut |_| {}, |_| {
            Ok(Some(files()))
        })
        .unwrap();
        assert_ne!(sparse_hash, hash.finalize());
        for kind in 0..3 {
            let error = load_groups(origin, (2, 2), &mut Sha256::new(), &mut |_| {}, |group| {
                let source = Source::group(IndexId(5), group);
                match kind {
                    0 => Err(source.error("lookup", "group payload is missing")),
                    1 => Err(source.error("integrity", "CRC mismatch")),
                    _ => Ok(Some(vec![(FileId(1), vec![0])])),
                }
            })
            .unwrap_err();
            assert_eq!(error.source.index, IndexId(5));
            assert_eq!(
                error.stage,
                if kind == 0 {
                    "lookup"
                } else if kind == 1 {
                    "integrity"
                } else {
                    "map lookup"
                }
            );
        }
        let (maps, _, _) = load_groups(
            WorldTile::new(0, 6352, Plane::MIN),
            (104, 6456),
            &mut Sha256::new(),
            &mut |_| {},
            |_| Ok(None),
        )
        .unwrap();
        assert!(
            maps.iter()
                .flat_map(|s| &s.tiles)
                .all(|t| t.elevation == 0 && t.base_shadow == 0)
        );
    }
}
