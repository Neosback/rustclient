//! Read-only diagnostic: print the canvas, trim offset and pixel size of cache sprites.
use rustclient_cache::Cache;
use rustclient_platform_native::DirectoryBlobs;

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let mut args = std::env::args().skip(1);
    let path = args
        .next()
        .ok_or("usage: sprite_probe CACHE_DIR SPRITE_ID|named:GROUP ...")?;
    let cache = Cache::open(DirectoryBlobs::open(path)?)?;
    for arg in args {
        if let Some(name) = arg.strip_prefix("named:") {
            for (frame, sprite) in cache.named_sprites(name)?.iter().enumerate() {
                let (w, h) = (
                    sprite.image.width() as usize,
                    sprite.image.height() as usize,
                );
                let centre = (h / 2 * w + w / 2) * 4;
                println!(
                    "{name}[{frame}]: canvas {:?} offset {:?} image {w}x{h} centre {:?}",
                    sprite.canvas,
                    sprite.offset,
                    &sprite.image.pixels()[centre..centre + 4]
                );
            }
            continue;
        }
        if let Some(id) = arg.strip_prefix("raw:") {
            let id: u32 = id.parse()?;
            let files =
                cache.group_files(rustclient_cache::IndexId(8), rustclient_cache::GroupId(id))?;
            for (file, bytes) in files {
                println!(
                    "sprite {id} file {} bytes {}: {bytes:02x?}",
                    file.0,
                    bytes.len()
                );
            }
            continue;
        }
        let id: u32 = arg.parse()?;
        match cache.sprite(id)? {
            Some(sprite) => {
                let pixels = sprite.image.pixels();
                let rgba = pixels.as_chunks::<4>().0;
                let opaque = rgba.iter().filter(|p| p[3] != 0).count();
                let total: [u32; 3] =
                    rgba.iter()
                        .filter(|p| p[3] != 0)
                        .fold([0; 3], |mut sum, p| {
                            for (sum, channel) in sum.iter_mut().zip(p) {
                                *sum += u32::from(*channel);
                            }
                            sum
                        });
                let mean = total.map(|sum| sum / u32::try_from(opaque).unwrap_or(1).max(1));
                println!(
                    "sprite {id}: canvas {:?} offset {:?} image {}x{} opaque pixels {opaque} mean rgb {mean:?}",
                    sprite.canvas,
                    sprite.offset,
                    sprite.image.width(),
                    sprite.image.height()
                );
            }
            None => println!("sprite {id}: missing"),
        }
    }
    Ok(())
}
