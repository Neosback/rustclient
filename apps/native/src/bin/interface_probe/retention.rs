//! Explicit cache-backed CPU workload; not live gameplay or visual parity acceptance.
use rustclient_application::scripts::ClientScripts;
use rustclient_game_state::GameState;
use rustclient_game_ui::{
    Rect, TitleViewport, UiFrame, UiImageId, widget_visuals::WidgetVisualAssets,
};
use rustclient_platform_native::DirectoryBlobs;
use std::{hint::black_box, time::Instant};

pub(super) fn run(
    scripts: &ClientScripts<DirectoryBlobs>,
    state: &GameState,
    assets: &WidgetVisualAssets,
    group: u32,
) -> Result<(), Box<dyn std::error::Error>> {
    const ITERATIONS: u32 = 2000;
    let mut frame = UiFrame::default();
    let before = state.semantic_fingerprint();
    for retained in [false, true] {
        let mut tree = None;
        let mut dependencies = None;
        let mut samples = Vec::new();
        let mut paints = 0_u64;
        for batch in 0..11 {
            let started = Instant::now();
            for _ in 0..ITERATIONS {
                if retained
                    && dependencies.as_ref().is_some_and(|dependencies| {
                        scripts.widget_paint_is_current(black_box(state), dependencies, None)
                    })
                    && frame.reuse_widgets()
                {
                    black_box(&frame.quads);
                    continue;
                }
                frame.clear_quads();
                let projection = scripts.project_widgets_reusing(
                    state,
                    assets,
                    &mut frame,
                    None,
                    tree.take(),
                )?;
                tree = Some(projection.tree);
                dependencies = Some(projection.dependencies);
                frame.seal_widgets();
                if batch >= 2 {
                    paints += 1;
                }
                black_box(&frame.quads);
            }
            if batch >= 2 {
                samples.push(started.elapsed().as_nanos());
            }
        }
        println!(
            "{{\"workload\":\"widget_paint\",\"interface\":{group},\"retained\":{retained},\"iterations_per_sample\":{ITERATIONS},\"samples_ns\":{samples:?},\"measured_paints\":{paints},\"quads\":{}}}",
            frame.quads.len()
        );
    }
    if state.semantic_fingerprint() != before {
        return Err("retained widget workload changed semantic state".into());
    }
    let title = scripts.profile.load_title(&scripts.cache, |_| {})?;
    let viewport = TitleViewport {
        canvas_width: 765,
        physical_width: 1530,
        physical_height: 1006,
        scale: 2.,
        stretch: None,
    };
    for overlays in [false, true] {
        if overlays {
            add_overlays(&mut frame)?;
        }
        for measurement in rustclient_renderer::measure_ui_preparation(
            &title,
            &assets.images,
            &mut frame,
            viewport,
            ITERATIONS,
        )? {
            println!(
                "{{\"workload\":\"ui_vertices\",\"interface\":{group},\"overlays\":{overlays},\"retained\":{},\"iterations_per_sample\":{},\"samples_ns\":{:?},\"prepared_quads\":{},\"retained_quads\":{},\"vertex_upload_bytes\":{},\"unchanged_geometry_frames\":{},\"retention_contracts\":true,\"gpu_time_measured\":false}}",
                measurement.retained,
                measurement.iterations_per_sample,
                measurement.samples_ns,
                measurement.prepared_quads,
                measurement.retained_quads,
                measurement.vertex_upload_bytes,
                measurement.unchanged_geometry_frames,
            );
        }
    }
    Ok(())
}
fn add_overlays(frame: &mut UiFrame) -> Result<(), &'static str> {
    // Exercise both transient layers against real cache-authored widgets. These are
    // diagnostic rectangles, not fabricated actor/gameplay events.
    frame.image(
        UiImageId::White,
        Rect::new(10., 10., 10., 10.),
        Rect::new(0., 0., 765., 503.),
    );
    frame
        .world_quads
        .push(frame.quads.pop().ok_or("missing diagnostic quad")?);
    frame.image(
        UiImageId::White,
        Rect::new(20., 20., 10., 10.),
        Rect::new(0., 0., 765., 503.),
    );
    Ok(())
}

pub(super) fn gpu(
    scripts: &ClientScripts<DirectoryBlobs>,
    state: &GameState,
    assets: &WidgetVisualAssets,
    group: u32,
) -> Result<(), Box<dyn std::error::Error>> {
    let mut frame = UiFrame::default();
    scripts.project_widgets(state, assets, &mut frame, None)?;
    frame.seal_widgets();
    let title = scripts.profile.load_title(&scripts.cache, |_| {})?;
    let viewport = TitleViewport {
        canvas_width: 765,
        physical_width: 1530,
        physical_height: 1006,
        scale: 2.,
        stretch: None,
    };
    let steps = pollster::block_on(rustclient_renderer::verify_ui_raster_retention(
        &title,
        &assets.images,
        &mut frame,
        viewport,
        |device| {
            let _ = device.poll(wgpu::PollType::wait_indefinitely());
        },
    ))?;
    for step in steps {
        println!(
            "{{\"workload\":\"ui_gpu_lifecycle\",\"interface\":{group},\"stage\":\"{}\",\"raster_passes\":{},\"vertex_upload_bytes\":{},\"dynamic_upload_bytes\":{},\"gpu_time_measured\":false}}",
            step.stage,
            step.stats.raster_passes,
            step.stats.vertex_upload_bytes,
            step.stats.dynamic_upload_bytes,
        );
    }
    Ok(())
}
