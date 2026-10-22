use super::*;
use rustclient_model::{ModelId, ModelPoint};
const TERRAIN: &[u8] = include_bytes!("../../tests/fixtures/world/terrain.bin");
const LOCATIONS: &[u8] = include_bytes!("../../tests/fixtures/world/locations.bin");
const OBJECT: &[u8] = include_bytes!("../../tests/fixtures/world/object.bin");
const FD: &[u8] = include_bytes!("../../tests/fixtures/world/model-fd.bin");
const FE: &[u8] = include_bytes!("../../tests/fixtures/world/model-fe.bin");
fn source() -> Source {
    Source {
        index: IndexId(5),
        group: Some(GroupId(258)),
        file: Some(FileId(0)),
    }
}
#[test]
fn modern_terrain_floors_flags_rotation_and_upper_heights() {
    let tiles = terrain::terrain(TERRAIN, RegionCoord::new(1, 2), source()).unwrap();
    assert_eq!(tiles.len(), 16384);
    let t = &tiles[0];
    assert_eq!(t.elevation, 56);
    assert_eq!(t.underlay, Some(FloorUnderlayId::new(0)));
    let overlay = t.overlay.unwrap();
    assert_eq!(
        (overlay.id.get(), overlay.shape, overlay.rotation),
        (1, 1, 1)
    );
    assert!(t.blocked() && t.bridge() && !t.roof());
    assert_eq!(tiles[4096].elevation, 296);
    assert!(
        terrain::terrain(
            &TERRAIN[..TERRAIN.len() - 1],
            RegionCoord::new(1, 2),
            source()
        )
        .is_err()
    );
    let error = terrain::terrain(&[0, 1], RegionCoord::new(1, 2), source())
        .unwrap_err()
        .to_string();
    assert!(error.contains("index 5 / group 258 / file 0"));
    assert!(
        terrain::terrain(&[0, 82].repeat(65), RegionCoord::new(1, 2), source())
            .unwrap_err()
            .to_string()
            .contains("opcode limit")
    );
}
#[test]
fn delta_locations_preserve_plane_shape_orientation_and_world_offsets() {
    let locs = terrain::locations(LOCATIONS, RegionCoord::new(1, 2), source()).unwrap();
    assert_eq!(locs.len(), 1);
    let l = locs[0];
    assert_eq!(l.object.get(), 3);
    assert_eq!((l.tile.x, l.tile.y, l.tile.plane.get()), (67, 132, 2));
    assert_eq!((l.shape, l.orientation.get()), (10, 3));
    for n in 0..LOCATIONS.len() {
        assert!(terrain::locations(&LOCATIONS[..n], RegionCoord::new(1, 2), source()).is_err());
    }
    let mut bad = LOCATIONS.to_vec();
    bad[3] = 255;
    assert!(terrain::locations(&bad, RegionCoord::new(1, 2), source()).is_err());
}
#[test]
fn definitions_keep_modern_model_ids_and_authored_transform_order() {
    let def = definitions::object(OBJECT, ObjectDefinitionId::new(9), source()).unwrap();
    assert_eq!(def.models[0].id, ModelId::new(70000));
    assert_eq!(def.models[0].shape, Some(10));
    assert_eq!(def.size, [2, 3]);
    assert!(def.mirrored);
    assert_eq!(def.scale, [256, 128, 128]);
    assert_eq!(def.translation, [-7, 0, 0]);
    assert_eq!(def.recolors, [(1, 2), (2, 3)]);
    assert_eq!(def.contour, Some(512));
    for n in 0..OBJECT.len() {
        assert!(definitions::object(&OBJECT[..n], def.id, source()).is_err());
    }
    assert!(definitions::object(&[255, 0], def.id, source()).is_err());
    let f = definitions::floor(&[1, 255, 0, 255, 5, 7, 1, 2, 3, 0], true, source()).unwrap();
    assert!(!f.visible && !f.hide_underlay);
    assert_eq!(f.secondary, Some(0x0001_0203));
}
#[test]
fn both_modern_model_families_decode_the_same_authored_triangle() {
    for bytes in [FD, FE] {
        let model = models::decode(bytes, source()).unwrap();
        assert_eq!(
            model.vertices,
            [
                ModelPoint::new(0, 0, 0),
                ModelPoint::new(128, 0, 0),
                ModelPoint::new(0, -128, 128)
            ]
        );
        assert_eq!(model.faces[0].vertices, [0, 1, 2]);
        assert_eq!(model.faces[0].color, 500);
        assert_eq!(model.faces[0].opacity(), 255);
        assert!(!model.faces[0].hidden());
        for n in 0..bytes.len() {
            assert!(models::decode(&bytes[..n], source()).is_err());
        }
        let mut bad = bytes.to_vec();
        bad[6] = 80;
        assert!(models::decode(&bad, source()).is_err());
        let mut bad = bytes.to_vec();
        bad[0] = 128;
        assert!(models::decode(&bad, source()).is_err());
    }
    let mut empty = vec![0; 23];
    empty[21] = 255;
    empty[22] = 254;
    assert_eq!(
        models::decode(&empty, source()).unwrap().vertices,
        Vec::<ModelPoint>::new()
    );
}
#[test]
fn scene_window_is_owned_by_the_explicit_revision_adapter() {
    let anchor = WorldTile::new(3216, 3216, rustclient_model::Plane::MIN);
    let (origin, w, h) = CacheProfile::OpenRune240.scene_window(anchor).unwrap();
    assert_eq!((origin.x, origin.y, w, h), (3168, 3168, 104, 104));
    assert!(
        CacheProfile::Osrs240
            .scene_window(WorldTile::new(0, 0, anchor.plane))
            .is_err()
    );
}

fn repacked_objects(corrupt: bool) -> Cache<rustclient_platform::MemoryBlobs> {
    use rustclient_platform::{MemoryBlobs, StorageKey};
    let container = |payload: &[u8]| {
        let mut v = vec![0];
        v.extend_from_slice(&u32::try_from(payload.len()).unwrap().to_be_bytes());
        v.extend_from_slice(payload);
        v
    };
    let mut payload = container(&[0]);
    let crc = crc32fast::hash(&payload);
    payload.extend_from_slice(&1_u16.to_be_bytes());
    if corrupt {
        payload[5] = 1;
    }
    // Protocol 7, decoded-size column, one group 6 / file 1.
    let mut table = vec![7, 0, 0, 0, 1, 4, 0, 1, 0, 6];
    table.extend_from_slice(&crc.to_be_bytes());
    table.extend_from_slice(&6_u32.to_be_bytes());
    table.extend_from_slice(&999_u32.to_be_bytes());
    table.extend_from_slice(&1_u32.to_be_bytes());
    table.extend_from_slice(&[0, 1, 0, 1]);
    let mut data = vec![0; 520];
    let mut append = |index: u8, group: u16, bytes: &[u8]| {
        let sector = u32::try_from(data.len() / 520).unwrap();
        let mut entry = [0; 6];
        entry[..3].copy_from_slice(&u32::try_from(bytes.len()).unwrap().to_be_bytes()[1..]);
        entry[3..].copy_from_slice(&sector.to_be_bytes()[1..]);
        let mut block = Vec::new();
        block.extend_from_slice(&group.to_be_bytes());
        block.extend_from_slice(&[0, 0, 0, 0, 0, index]);
        block.extend_from_slice(bytes);
        block.resize(520, 0);
        data.extend_from_slice(&block);
        entry
    };
    let mut master = vec![0; 18];
    master[12..18].copy_from_slice(&append(255, 2, &container(&table)));
    let mut index = vec![0; 42];
    index[36..42].copy_from_slice(&append(2, 6, &payload));
    Cache::open(MemoryBlobs::new([
        (StorageKey::parse("main_file_cache.dat2").unwrap(), data),
        (StorageKey::parse("main_file_cache.idx255").unwrap(), master),
        (StorageKey::parse("main_file_cache.idx2").unwrap(), index),
    ]))
    .unwrap()
}
#[test]
fn repacked_object_size_exception_is_explicit_and_preserves_crc_enforcement() {
    let needed = BTreeSet::from([ObjectDefinitionId::new(1)]);
    let mut warnings = Vec::new();
    let cache = repacked_objects(false);
    assert!(
        load_objects(
            &cache,
            CacheProfile::Osrs240,
            &needed,
            &mut Sha256::new(),
            &mut warnings
        )
        .is_err()
    );
    assert_eq!(warnings.as_slice(), &[] as &[String]);
    let (objects, _) = load_objects(
        &cache,
        CacheProfile::OpenRune240,
        &needed,
        &mut Sha256::new(),
        &mut warnings,
    )
    .unwrap();
    assert_eq!(objects.len(), 1);
    assert_eq!(warnings.len(), 1);
    assert!(warnings[0].contains("reported 999, decoded 1"));
    assert!(
        load_objects(
            &repacked_objects(true),
            CacheProfile::OpenRune240,
            &needed,
            &mut Sha256::new(),
            &mut Vec::new()
        )
        .unwrap_err()
        .detail
        .contains("CRC")
    );
}
