//! Read-only cache diagnostic for world map bootstrap, JPEG loading and script prerequisites.
use rustclient_cache::{Cache, CacheProfile};
use rustclient_model::ScriptId;
use rustclient_platform_native::DirectoryBlobs;
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
        "JPEG overview: {}x{}; {} RGBA bytes; {} icons",
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
