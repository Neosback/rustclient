// Development-only oracle. Compiled exclusively inside the staged reference workspace.
use osrs_world::WorldDefinitions;
use std::{env,fs,io::Write};
fn main()->Result<(),Box<dyn std::error::Error>> {
    let args:Vec<_>=env::args().collect();
    let definitions=WorldDefinitions::open(&args[1])?;
    fs::create_dir_all(&args[2])?;
    for image in definitions.textures().images.iter().flatten() {
        let mut file=fs::File::create(format!("{}/{}.rgba",&args[2],image.texture_id))?;
        for pixel in &image.rgba {file.write_all(pixel)?;}
    }
    Ok(())
}
