use rustclient_cache::{
    BitmapFont, FileId, GroupId, IndexId, Source, decode_container, decode_sprites, split_files,
};
const SOURCE: Source = Source::group(IndexId(8), GroupId(42));

#[test]
fn golden_containers_decode_identically_and_enforce_declared_lengths() {
    for encoded in [
        include_bytes!("fixtures/container-0.bin").as_slice(),
        include_bytes!("fixtures/container-1.bin"),
        include_bytes!("fixtures/container-2.bin"),
    ] {
        assert_eq!(
            decode_container(encoded, SOURCE).unwrap(),
            include_bytes!("fixtures/payload.bin")
        );
        for length in 0..encoded.len() {
            assert!(decode_container(&encoded[..length], SOURCE).is_err());
        }
        let mut corrupt = encoded.to_vec();
        corrupt[1..5].copy_from_slice(&u32::MAX.to_be_bytes());
        assert!(decode_container(&corrupt, SOURCE).is_err());
    }
    for encoded in [
        include_bytes!("fixtures/container-1.bin").as_slice(),
        include_bytes!("fixtures/container-2.bin"),
    ] {
        let mut trailing = encoded.to_vec();
        trailing.push(0x7f);
        let length = u32::try_from(encoded.len() - 9 + 1).unwrap();
        trailing[1..5].copy_from_slice(&length.to_be_bytes());
        assert!(decode_container(&trailing, SOURCE).is_err());
    }
    let mut bomb = include_bytes!("fixtures/container-2.bin").to_vec();
    bomb[5..9].copy_from_slice(&u32::MAX.to_be_bytes());
    assert!(decode_container(&bomb, SOURCE).is_err());
    assert!(decode_container(&[3, 0, 0, 0, 0], SOURCE).is_err());
}
#[test]
fn golden_sprite_flags_offsets_palette_and_alpha_are_preserved() {
    let sprites = decode_sprites(include_bytes!("fixtures/sprites.bin"), SOURCE).unwrap();
    assert_eq!(sprites.len(), 2);
    assert_eq!(sprites[0].canvas, [3, 3]);
    assert_eq!(sprites[0].offset, [1, 1]);
    assert_eq!(
        sprites[0].image.pixels(),
        [0, 0, 1, 255, 0, 0, 0, 0, 0, 0, 1, 255, 0, 0, 1, 255]
    );
    assert_eq!(
        sprites[1].image.pixels(),
        [0, 0, 1, 255, 0, 0, 1, 128, 0, 0, 0, 0, 0, 0, 1, 64]
    );
    let bytes = include_bytes!("fixtures/sprites.bin");
    for n in 0..bytes.len() {
        assert!(decode_sprites(&bytes[..n], SOURCE).is_err());
    }
}
#[test]
fn simple_font_uses_metrics_and_converts_colored_pixels_to_masks() {
    let glyph = decode_sprites(include_bytes!("fixtures/sprites.bin"), SOURCE)
        .unwrap()
        .remove(0);
    let font = BitmapFont::decode(
        include_bytes!("fixtures/font-metrics.bin"),
        vec![glyph; 256],
        SOURCE,
    )
    .unwrap();
    assert_eq!(font.ascent, 12);
    assert_eq!(font.width("A<>"), 12);
    assert_eq!(&font.glyphs[65].image.pixels()[..4], &[255; 4]);
    assert!(BitmapFont::decode(&[0; 10], font.glyphs, SOURCE).is_err());
}
#[test]
fn golden_multichunk_group_reconstructs_sparse_files() {
    let ids = [FileId(3), FileId(9)];
    assert_eq!(
        split_files(include_bytes!("fixtures/split.bin"), &ids, SOURCE).unwrap(),
        vec![(FileId(3), b"ABC".to_vec()), (FileId(9), b"defgh".to_vec())]
    );
    let mut bad = include_bytes!("fixtures/split.bin").to_vec();
    bad[8..12].copy_from_slice(&(-1_i32).to_be_bytes());
    assert!(split_files(&bad, &ids, SOURCE).is_err());
    assert!(split_files(&[0], &ids, SOURCE).is_err());
}

#[test]
fn item_placeholder_links_only_convert_back_when_a_template_is_present() {
    use rustclient_cache::osrs240::definitions::item;
    use rustclient_model::ItemDefinitionId;
    let source = Source::group(IndexId(2), GroupId(10));
    // Normal coins has a forward link to its placeholder, but remains coins.
    let normal = item(&[148, 0x38, 0x76, 0], ItemDefinitionId::new(995), source).unwrap();
    assert_eq!(normal.placeholder, Some(ItemDefinitionId::new(14454)));
    assert_eq!(normal.placeholder_template, None);
    assert_eq!(normal.unplaceholder(), ItemDefinitionId::new(995));
    let placeholder = item(
        &[148, 3, 0xe3, 149, 0x38, 0x73, 0],
        ItemDefinitionId::new(14454),
        source,
    )
    .unwrap();
    assert_eq!(
        placeholder.placeholder_template,
        Some(ItemDefinitionId::new(14451))
    );
    assert_eq!(placeholder.unplaceholder(), ItemDefinitionId::new(995));
    let empty = item(&[0], ItemDefinitionId::new(1), source).unwrap();
    assert_eq!(empty.unplaceholder(), ItemDefinitionId::new(1));
    for length in 0..7 {
        assert!(
            item(
                &[148, 3, 0xe3, 149, 0x38, 0x73, 0][..length],
                ItemDefinitionId::new(14454),
                source
            )
            .is_err()
        );
    }
}

#[test]
fn item_inventory_model_uses_the_240_32_bit_opcode_with_legacy_and_absent_forms() {
    use rustclient_cache::osrs240::definitions::item;
    use rustclient_model::{ItemDefinitionId, ModelId};
    let source = Source::group(IndexId(2), GroupId(10));
    // Old school bond (13190) as stored in the live cache: model, offsets, zoom and rotation.
    let bond = item(
        &[
            0x2c, 0, 0, 0x72, 0x1a, 0x4b, 0, 3, 7, 0, 3, 8, 0, 1, 4, 8, 0xfc, 5, 2, 0, 0,
        ],
        ItemDefinitionId::new(13190),
        source,
    )
    .unwrap();
    assert_eq!(bond.icon_model, Some(ModelId::new(29210)));
    assert_eq!(bond.icon_zoom, 2300);
    assert_eq!(bond.icon_angles, [512, 0, 0]);
    assert_eq!(bond.icon_offset, [3, 1]);
    let legacy = item(&[1, 0x15, 0x24, 0], ItemDefinitionId::new(4151), source).unwrap();
    assert_eq!(legacy.icon_model, Some(ModelId::new(5412)));
    let absent = item(
        &[44, 0xff, 0xff, 0xff, 0xff, 0],
        ItemDefinitionId::new(1),
        source,
    )
    .unwrap();
    assert_eq!(absent.icon_model, None);
    assert!(item(&[44, 0, 0, 0x72], ItemDefinitionId::new(1), source).is_err());
}

#[test]
fn zero_location_scale_is_authored_geometry_not_an_invalid_footprint() {
    use rustclient_cache::{GroupId, IndexId, Source, osrs240::definitions};
    use rustclient_model::ObjectDefinitionId;
    let source = Source::group(IndexId(2), GroupId(6));
    let definition = definitions::object(
        &[65, 0, 1, 66, 0, 1, 67, 0, 0, 0],
        ObjectDefinitionId::new(27122),
        source,
    )
    .unwrap();
    assert_eq!(definition.size, [1, 1]);
    assert_eq!(definition.scale, [1, 1, 0]);
    assert!(definitions::object(&[14, 0, 0], ObjectDefinitionId::new(1), source).is_err());
}
