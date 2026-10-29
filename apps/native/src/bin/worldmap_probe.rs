//! Read-only cache diagnostic for world map bootstrap, standard image loading and script prerequisites.
use rustclient_cache::{Cache, CacheProfile};
use rustclient_model::ScriptId;
use rustclient_platform_native::DirectoryBlobs;
use std::collections::{BTreeMap, BTreeSet};
#[expect(
    clippy::too_many_lines,
    reason = "read-only cache and viewport diagnostic"
)]
fn main() -> Result<(), Box<dyn std::error::Error>> {
    let path = std::env::args()
        .nth(1)
        .ok_or("usage: worldmap_probe CACHE_DIR")?;
    let cache = Cache::open(DirectoryBlobs::open(path)?)?;
    let profile = CacheProfile::OpenRune240;
    let areas = profile.load_world_map_areas(&cache)?;
    println!("world map areas: {}", areas.len());
    let main = areas
        .iter()
        .find(|area| area.is_main)
        .ok_or("no main area")?;
    println!(
        "main area {}: {} ({})",
        main.id, main.external_name, main.internal_name
    );
    let composite = profile.load_world_map_composite(&cache, main)?;
    let overview = composite.overview.as_ref().ok_or("no main overview")?;
    println!(
        "standard overview: {}x{}; {} RGBA bytes; {} icons",
        overview.image.width(),
        overview.image.height(),
        overview.image.pixels().len(),
        composite.icons.len()
    );
    std::fs::create_dir_all("target/worldmap-probe")?;
    image::save_buffer(
        "target/worldmap-probe/overview.png",
        overview.image.pixels(),
        overview.image.width(),
        overview.image.height(),
        image::ColorType::Rgba8,
    )?;
    let mut data = Vec::new();
    let mut wanted = BTreeSet::new();
    for x in 48..=52 {
        for y in 48..=52 {
            let region = profile.load_world_map_region(&cache, &composite, [x, y])?;
            wanted.extend(
                region
                    .tiles
                    .iter()
                    .flat_map(|t| t.decorations.iter().flatten().filter_map(|d| d.object)),
            );
            data.push(region);
        }
    }
    let mut objects = BTreeMap::new();
    profile.load_world_map_objects(&cache, &wanted, &mut objects)?;
    let overlays = profile.load_world_map_overlays(&cache)?;
    let textures = profile.load_world_map_texture_colors(&cache)?;
    let scenes = cache.named_sprites("mapscene")?;
    let elements = profile.load_world_map_elements(&cache, &mut Vec::new())?;
    let painter = rustclient_scene::world_map::RegionPainter::default();
    let regions: BTreeMap<_, _> = data
        .iter()
        .map(|region| {
            (
                region.region,
                painter.render(
                    region,
                    3,
                    main.region_background,
                    &overlays,
                    &textures,
                    &objects,
                    &scenes,
                ),
            )
        })
        .collect();
    let mut sprites = BTreeMap::new();
    for icon in regions
        .values()
        .flat_map(|r| r.icons.iter())
        .chain(composite.icons.iter())
    {
        if (icon.coord.x - 3221).abs() > 150 || (icon.coord.y - 3218).abs() > 110 {
            continue;
        }
        if let Some(id) = icon
            .element
            .and_then(|id| elements.get(&id))
            .and_then(|e| e.sprite)
            && !sprites.contains_key(&id)
            && let Some(sprite) = cache.sprite(id)?
        {
            sprites.insert(id, sprite);
        }
    }
    let mut fonts = BTreeMap::new();
    for size in [
        rustclient_cache::osrs240::world_map::WorldMapLabelSize::Small,
        rustclient_cache::osrs240::world_map::WorldMapLabelSize::Medium,
        rustclient_cache::osrs240::world_map::WorldMapLabelSize::Large,
    ] {
        fonts.insert(size, profile.load_world_map_font(&cache, size)?);
    }
    let mut pixels = Vec::new();
    rustclient_scene::world_map::compose_world_map_viewport(
        800,
        600,
        [3221, 3218],
        75.,
        Some(main),
        Some(&composite),
        &elements,
        &sprites,
        &BTreeSet::new(),
        &BTreeSet::new(),
        false,
        &BTreeSet::new(),
        &BTreeSet::new(),
        0,
        None,
        &regions,
        &fonts,
        &mut pixels,
    );
    image::save_buffer(
        "target/worldmap-probe/viewport.png",
        &pixels,
        800,
        600,
        image::ColorType::Rgba8,
    )?;
    println!(
        "detailed regions: {}; geography icons: {}; icon sprites: {}; Verdana fonts: {}",
        regions.len(),
        regions.values().map(|r| r.icons.len()).sum::<usize>(),
        sprites.len(),
        fonts.len()
    );
    for script in [1749, 1403] {
        match profile.load_script(&cache, ScriptId::new(script)) {
            Ok(script) => {
                println!(
                    "script {} {} args {:?}",
                    script.id.get(),
                    script.name,
                    script.arguments
                );
                for (index, instruction) in script.instructions.iter().enumerate() {
                    println!(
                        "{index}: {:?} {} {:?}",
                        instruction.opcode, instruction.integer, instruction.text
                    );
                }
            }
            Err(error) => println!("script {script}: {error}"),
        }
    }
    Ok(())
}
