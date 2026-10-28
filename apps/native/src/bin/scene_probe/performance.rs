//! Explicit live-cache resource/query diagnostic, requiring no server or account.
use rustclient_scene::{ScenePickIndex, SceneVisibility, WorldMesh};
use std::time::Instant;

pub(super) fn run(mesh: &WorldMesh) -> Result<(), Box<dyn std::error::Error>> {
    let started = Instant::now();
    let index = ScenePickIndex::new(&mesh.geometry)?;
    println!(
        "picking index: {:?}, build {:?}",
        index.stats(),
        started.elapsed()
    );
    let visibility = SceneVisibility::everything();
    let mut original = Vec::new();
    for indexed in [false, true] {
        let mut samples = Vec::with_capacity(600);
        for step in 0..600_u16 {
            let origin = [
                f32::from(step.wrapping_mul(173) % 13000),
                3000.,
                f32::from(step.wrapping_mul(91) % 13000),
            ];
            let direction = [0., -1., 0.];
            let started = Instant::now();
            let hit = if indexed {
                index.pick_surface(&mesh.geometry, origin, direction, &visibility)
            } else {
                rustclient_scene::pick_surface(&mesh.geometry, origin, direction, &visibility)
            };
            samples.push(started.elapsed().as_secs_f64() * 1000.);
            let result = hit.map(|hit| (hit.distance, hit.target));
            if indexed {
                if result != original[usize::from(step)] {
                    return Err(format!("indexed query differs for ray {step}").into());
                }
            } else {
                original.push(result);
            }
        }
        samples.sort_by(f64::total_cmp);
        println!(
            "indexed={indexed}: surface query ms p50={:.4} p95={:.4} max={:.4}",
            samples[300], samples[570], samples[599]
        );
    }
    let resources = pollster::block_on(rustclient_renderer::capture_world_resources(mesh))?;
    println!("actual GPU world resources: {resources:?}");
    println!(
        "600 indexed hits match original picker; resource figures exclude textures/render targets and whole-process RSS"
    );
    Ok(())
}
