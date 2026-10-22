//! Explicit revision adapters. Numeric index knowledge never escapes this module.
use crate::{
    BitmapFont, Cache, GroupId, Image, IndexId, Source, Sprite, decode_sprites, error::Result,
    reader::Reader,
};
use rustclient_platform::ReadOnlyBlobs;
use sha2::{Digest, Sha256};

/// Validated cache layout selection; independent of network server identity.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum CacheProfile {
    /// User-supplied LIVE snapshot, first revision-240 title target.
    Osrs240,
    /// `OpenRune` 240 repack; decoded size hints for the object archive are advisory.
    OpenRune240,
}
impl CacheProfile {
    /// Parse an explicitly supported cache profile.
    ///
    /// # Errors
    /// Rejects unimplemented profiles rather than substituting another revision.
    pub fn parse(name: &str) -> std::result::Result<Self, String> {
        match name {
            "osrs-240" => Ok(Self::Osrs240),
            "openrune-240" => Ok(Self::OpenRune240),
            _ => Err(format!(
                "unsupported cache profile {name:?}; supported: osrs-240, openrune-240"
            )),
        }
    }
    /// Stable configuration name.
    #[must_use]
    pub const fn name(self) -> &'static str {
        match self {
            Self::Osrs240 => "osrs-240",
            Self::OpenRune240 => "openrune-240",
        }
    }

    /// Load only title assets, reporting named decode milestones.
    ///
    /// # Errors
    /// Returns exact source context for missing, corrupt or unsupported assets.
    pub fn load_title<S: ReadOnlyBlobs>(
        self,
        cache: &Cache<S>,
        mut progress: impl FnMut(&str),
    ) -> Result<TitleAssets> {
        match self {
            Self::Osrs240 | Self::OpenRune240 => load_240(cache, &mut progress),
        }
    }
}

/// Integrity evidence for an individually validated title file.
#[derive(Debug, Clone)]
pub struct AssetReport {
    /// Human-readable role/name.
    pub name: String,
    /// Original address.
    pub source: Source,
    /// Decoded file length.
    pub bytes: usize,
    /// SHA-256 of decoded source bytes (before image/font expansion).
    pub sha256: String,
}

/// Authentic OSRS gameframe and minimap HUD sprites from index 8.
#[derive(Debug, Clone)]
pub struct GameFrameSprites {
    pub minimap_frame: Sprite,
    pub minimap_mask: Sprite,
    pub compass_mask: Sprite,
    pub compass: Sprite,
    pub tabs_top: Sprite,
    pub tabs_bottom: Sprite,
    pub tab_icons: Vec<Sprite>,
    pub orb_frame: Option<Sprite>,
    pub hp_orb: Option<Sprite>,
    pub hp_icon: Option<Sprite>,
    pub prayer_orb: Option<Sprite>,
    pub prayer_icon: Option<Sprite>,
    pub run_orb: Option<Sprite>,
    pub run_icon: Option<Sprite>,
    pub spec_orb: Option<Sprite>,
    pub spec_icon: Option<Sprite>,
}

/// Immutable CPU title assets, retained across GPU loss.
#[derive(Debug, Clone)]
pub struct TitleAssets {
    /// Left background; right side is mirrored by the projection.
    pub background: Image,
    /// Standard logo frame.
    pub logo: Sprite,
    /// Login panel.
    pub title_box: Sprite,
    /// Standard button.
    pub button: Sprite,
    /// Twelve rune masks.
    pub runes: Vec<Sprite>,
    /// Music icons (only disabled/muted is used).
    pub mute: Vec<Sprite>,
    /// Unchecked, checked, hover unchecked, hover checked.
    pub checkboxes: Vec<Sprite>,
    /// Plain 11, plain 12, bold 12.
    pub fonts: [BitmapFont; 3],
    /// Three sets of five palette stops from graphics defaults.
    pub flame_colors: [[u32; 5]; 3],
    /// Authentic gameframe HUD sprites if present in cache.
    pub gameframe: Option<GameFrameSprites>,
    /// Exact validated source records.
    pub reports: Vec<AssetReport>,
    /// Reference metadata snapshot identity.
    pub metadata_fingerprint: String,
}

fn load_240<S: ReadOnlyBlobs>(
    cache: &Cache<S>,
    progress: &mut impl FnMut(&str),
) -> Result<TitleAssets> {
    const SPRITES: IndexId = IndexId(8);
    const BINARY: IndexId = IndexId(10);
    const FONTS: IndexId = IndexId(13);
    let mut reports = Vec::new();
    let (source, bytes) = read(cache, BINARY, "title.jpg", &mut reports, progress)?;
    let background = Image::jpeg(&bytes, source)?;
    let mut sprite = |name: &str| -> Result<Vec<Sprite>> {
        let (source, bytes) = read(cache, SPRITES, name, &mut reports, progress)?;
        decode_sprites(&bytes, source)
    };
    let logo = first(sprite("logo")?, "logo")?;
    let title_box = first(sprite("titlebox")?, "titlebox")?;
    let button = first(sprite("titlebutton")?, "titlebutton")?;
    let runes = sprite("runes")?;
    let mute = sprite("title_mute")?;
    let mut checkboxes = Vec::new();
    for name in [
        "options_radio_buttons,0",
        "options_radio_buttons,2",
        "options_radio_buttons,4",
        "options_radio_buttons,6",
    ] {
        checkboxes.push(first(sprite(name)?, name)?);
    }
    if runes.len() != 12 || mute.len() != 2 {
        return Err(Source::group(SPRITES, GroupId(0))
            .error("title assets", "unexpected rune or mute frame count"));
    }
    let mut fonts = Vec::new();
    for name in ["p11_full", "p12_full", "b12_full"] {
        let (source, bytes) = read(cache, SPRITES, name, &mut reports, progress)?;
        let glyphs = decode_sprites(&bytes, source)?;
        let (source, metrics) = read(cache, FONTS, name, &mut reports, progress)?;
        fonts.push(BitmapFont::decode(&metrics, glyphs, source)?);
    }
    let fonts: [BitmapFont; 3] = fonts
        .try_into()
        .map_err(|_| source.error("font", "font count mismatch"))?;
    progress("graphics defaults");
    let defaults_source = Source {
        index: IndexId(17),
        group: Some(GroupId(3)),
        file: Some(crate::FileId(0)),
    };
    let files = cache.group_files(IndexId(17), GroupId(3))?;
    let defaults = files
        .iter()
        .find(|(id, _)| id.0 == 0)
        .ok_or_else(|| defaults_source.error("graphics defaults", "missing file"))?;
    reports.push(report("graphics defaults", defaults_source, &defaults.1));
    let flame_colors = flame_colors(&defaults.1, defaults_source)?;
    let gameframe = load_gameframe_sprites(cache);
    Ok(TitleAssets {
        background,
        logo,
        title_box,
        button,
        runes,
        mute,
        checkboxes,
        fonts,
        flame_colors,
        gameframe,
        reports,
        metadata_fingerprint: cache.metadata_fingerprint().to_owned(),
    })
}

fn load_gameframe_sprites<S: ReadOnlyBlobs>(cache: &Cache<S>) -> Option<GameFrameSprites> {
    let minimap_frame = cache.sprite(1177).ok()??;
    let minimap_mask = cache.sprite(1178).ok()??;
    let compass_mask = cache.sprite(1179).ok()??;
    let compass = cache.sprite(169).ok()??;
    let tabs_top = cache.sprite(1173).ok()??;
    let tabs_bottom = cache.sprite(1174).ok()??;
    let mut tab_icons = Vec::new();
    for id in 774..=787 {
        if let Ok(Some(sp)) = cache.sprite(id) {
            tab_icons.push(sp);
        }
    }
    let orb_frame = cache.sprite(1071).ok().flatten();
    let hp_orb = cache.sprite(1060).ok().flatten();
    let hp_icon = cache.sprite(1067).ok().flatten();
    let prayer_orb = cache.sprite(1063).ok().flatten();
    let prayer_icon = cache.sprite(1068).ok().flatten();
    let run_orb = cache.sprite(1064).ok().flatten();
    let run_icon = cache.sprite(1070).ok().flatten();
    let spec_orb = cache.sprite(1607).ok().flatten();
    let spec_icon = cache.sprite(1610).ok().flatten();

    Some(GameFrameSprites {
        minimap_frame,
        minimap_mask,
        compass_mask,
        compass,
        tabs_top,
        tabs_bottom,
        tab_icons,
        orb_frame,
        hp_orb,
        hp_icon,
        prayer_orb,
        prayer_icon,
        run_orb,
        run_icon,
        spec_orb,
        spec_icon,
    })
}

fn read<S: ReadOnlyBlobs>(
    cache: &Cache<S>,
    index: IndexId,
    name: &str,
    reports: &mut Vec<AssetReport>,
    progress: &mut impl FnMut(&str),
) -> Result<(Source, Vec<u8>)> {
    progress(name);
    let (source, bytes) = cache.named_file(index, name, "")?;
    reports.push(report(name, source, &bytes));
    Ok((source, bytes))
}
fn report(name: &str, source: Source, bytes: &[u8]) -> AssetReport {
    AssetReport {
        name: name.to_owned(),
        source,
        bytes: bytes.len(),
        sha256: format!("{:x}", Sha256::digest(bytes)),
    }
}
fn first(mut sprites: Vec<Sprite>, name: &str) -> Result<Sprite> {
    if sprites.len() != 1 {
        return Err(Source::group(IndexId(8), GroupId(0))
            .error("title assets", format!("{name} must have one frame")));
    }
    Ok(sprites.remove(0))
}
fn flame_colors(bytes: &[u8], source: Source) -> Result<[[u32; 5]; 3]> {
    let mut r = Reader::new(bytes, source);
    let mut colors = None;
    loop {
        match r.u8()? {
            0 => {
                r.finish()?;
                return colors
                    .ok_or_else(|| source.error("graphics defaults", "missing flame palettes"));
            }
            1 => {
                r.u24()?;
            }
            2 => {
                for _ in 0..11 {
                    r.count(7)?;
                }
            }
            3 => {
                let mut stops = [[0; 5]; 3];
                for palette in &mut stops {
                    for stop in palette {
                        *stop = r.u24()?;
                    }
                }
                colors = Some(stops);
            }
            4 => {
                r.count(7)?;
                r.count(7)?;
            }
            // LIVE/240 extends the sprite list to twelve IDs (opcode 6),
            // and stores the final two graphics IDs as fixed u32s (opcode 5).
            // These fields are not consumed by the title projection.
            5 => {
                r.u32()?;
                r.u32()?;
            }
            6 => {
                for _ in 0..12 {
                    r.count(7)?;
                }
            }
            opcode => {
                return Err(
                    source.error("graphics defaults", format!("unsupported opcode {opcode}"))
                );
            }
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn live_240_graphics_defaults_have_fixed_palette_semantics() {
        let bytes = include_bytes!("../tests/fixtures/live-graphics-defaults.bin");
        assert_eq!(
            format!("{:x}", Sha256::digest(bytes)),
            "a7b3038174bfab8344550aac92443c93806425654a57ed645bc512fb631de6ca"
        );
        let source = Source::group(IndexId(17), GroupId(3));
        let colors = flame_colors(bytes, source).unwrap();
        assert_eq!(
            colors[0],
            [0, 0x00ff_0000, 0x00ff_ff00, 0x00ff_ffff, 0x00ff_ffff]
        );
        assert_eq!(
            colors[1],
            [0, 0x0000_ff00, 0x0000_ffff, 0x00ff_ffff, 0x00ff_ffff]
        );
        assert_eq!(
            colors[2],
            [0, 0x0000_00ff, 0x00ff_00ff, 0x00ff_ffff, 0x00ff_ffff]
        );
        for n in 0..bytes.len() {
            assert!(flame_colors(&bytes[..n], source).is_err());
        }
        assert!(CacheProfile::parse("osrs-241").is_err());
    }
}
