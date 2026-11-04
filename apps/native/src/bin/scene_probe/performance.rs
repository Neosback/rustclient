//! Explicit live-cache resource/query diagnostic, requiring no server or account.
use rustclient_model::Plane;
use rustclient_scene::{SceneAsset, ScenePickIndex, SceneVisibility, WorldMesh};
use std::time::Instant;

pub(super) fn run(scene: &SceneAsset, mesh: &WorldMesh) -> Result<(), Box<dyn std::error::Error>> {
    // The minimap is built on the event loop at scene arrival and on every plane change, so its
    // cost is a frame-time tail whenever that happens.
    let mut samples = (0..20)
        .map(|_| {
            let started = Instant::now();
            let image = rustclient_scene::minimap::build_scene_minimap(scene, Plane::MIN);
            std::hint::black_box(&image);
            started.elapsed().as_secs_f64() * 1000.
        })
        .collect::<Vec<_>>();
    samples.sort_by(f64::total_cmp);
    println!(
        "minimap build ms p50={:.3} p95={:.3} max={:.3} (20 builds)",
        samples[10], samples[18], samples[19]
    );
    let started = Instant::now();
    let index = ScenePickIndex::new(&mesh.geometry)?;
    println!(
        "picking index: {:?}, build {:?}",
        index.stats(),
        started.elapsed()
    );
    let geometry = &mesh.geometry;
    println!(
        "static triangles: {} vertices, {} B resident as vertices and index lists; {} B kept by the picking index once they are released",
        geometry.vertex_count(),
        geometry.vertices.len() * size_of::<rustclient_scene::SceneVertex>()
            + (geometry.opaque.len() + geometry.transparent.len()) * size_of::<u32>(),
        index.stats().resident_bytes,
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
    let mut released = mesh.geometry.clone();
    released.release_triangles();
    for step in 0..600_u16 {
        let origin = [
            f32::from(step.wrapping_mul(173) % 13000),
            3000.,
            f32::from(step.wrapping_mul(91) % 13000),
        ];
        let hit = index.pick_surface(&released, origin, [0., -1., 0.], &visibility);
        if hit.map(|hit| (hit.distance, hit.target)) != original[usize::from(step)] {
            return Err(format!("released-geometry query differs for ray {step}").into());
        }
    }
    drop(released);
    println!(
        "reference texture array: {} layers, {} B (the classic shader allows 256 layers, {} B)",
        rustclient_renderer::texture_layers(mesh),
        rustclient_renderer::texture_array_bytes(mesh),
        rustclient_renderer::texture_array_bytes(mesh)
            / u64::from(rustclient_renderer::texture_layers(mesh))
            * 256,
    );
    let resources = pollster::block_on(rustclient_renderer::capture_world_resources(mesh))?;
    println!("actual GPU world resources: {resources:?}");
    println!(
        "600 indexed hits match original picker, also after the triangles are released; resource figures exclude textures/render targets and whole-process RSS"
    );
    Ok(())
}
