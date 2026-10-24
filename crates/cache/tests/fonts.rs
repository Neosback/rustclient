//! Synthetic metric-family contract, independent of rendering and the live cache.
use rustclient_cache::{BitmapFont, GroupId, Image, IndexId, Source, Sprite};
use rustclient_model::SpriteId;
fn glyphs() -> Vec<Sprite> {
    let source = Source::group(IndexId(13), GroupId(1));
    (0..256)
        .map(|_| Sprite {
            id: SpriteId::new(1),
            frame: 0,
            canvas: [3, 10],
            offset: [0, 2],
            image: Image::new(3, 7, vec![255; 3 * 7 * 4], source).unwrap(),
        })
        .collect()
}
#[test]
fn simple_and_contour_metrics_preserve_baseline_extents_and_signed_kerning() {
    let source = Source::group(IndexId(13), GroupId(1));
    let mut simple = vec![5; 256];
    simple.push(8);
    let font = BitmapFont::decode(&simple, glyphs(), source).unwrap();
    assert_eq!((font.ascent, font.max_ascent, font.max_descent), (8, 6, 1));
    assert_eq!(font.width("AB\u{a0}A"), 20);
    let mut bytes = vec![5; 256];
    let mut lengths = [0_u8; 256];
    for n in [32, 65, 66] {
        lengths[n] = 1;
    }
    bytes.extend(lengths);
    let mut offsets = [0_u8; 256];
    offsets[32] = 8;
    bytes.extend(offsets);
    bytes.extend([0, 2, 1, 0, 3, 2]); // left space/A/B, then right space/A/B
    let kerned = BitmapFont::decode(&bytes, glyphs(), source).unwrap();
    assert_eq!(kerned.ascent, 9);
    assert_eq!(kerned.advance(Some(b'A'), b'B'), 1);
    assert_eq!(kerned.width("AB"), 6);
    assert_eq!(
        kerned.width("A B"),
        15,
        "spaces never receive contour kerning"
    );
    for length in [0, 255, 256, 258, 511, 767, bytes.len() - 1] {
        assert!(BitmapFont::decode(&bytes[..length], glyphs(), source).is_err());
    }
    bytes.push(0);
    assert!(BitmapFont::decode(&bytes, glyphs(), source).is_err());
}
