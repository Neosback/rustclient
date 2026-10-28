//! Full-viewport widget composition through the actual canvas GPU path.
use rustclient_cache::{GroupId, Image, IndexId, Source};
use rustclient_game_ui::{Rect, ViewportOverlay};
use rustclient_renderer::{ColorBlind, GpuSettings, SurfaceSize, UiScaling};

pub fn capture() -> Result<(), Box<dyn std::error::Error>> {
    let viewport = Rect::new(0., 0., 3., 1.);
    let mut overlay = ViewportOverlay::default();
    assert!(overlay.absorb(Some(viewport), viewport, true, 0x00ff_0000, 128));
    assert!(overlay.absorb(Some(viewport), viewport, true, 0x0000_00ff, 128));
    let source = Source::group(IndexId(0), GroupId(0));
    let image = Image::new(
        3,
        1,
        vec![0, 255, 0, 255, 0, 0, 255, 128, 0, 0, 0, 0],
        source,
    )?;
    let capture = |image: &Image, size, settings| {
        pollster::block_on(rustclient_renderer::capture_canvas_with_overlay(
            image,
            size,
            settings,
            overlay,
            |device| {
                let _ = device.poll(wgpu::PollType::wait_indefinitely());
            },
        ))
    };
    let pixels = capture(
        &image,
        SurfaceSize::new(3, 1),
        GpuSettings {
            ui_scaling: UiScaling::Nearest,
            ..Default::default()
        },
    )?;
    let expected = [0_u8, 255, 0, 255, 31, 0, 191, 255, 63, 0, 127, 255];
    assert!(
        pixels
            .iter()
            .zip(expected)
            .all(|(&v, e)| v.abs_diff(e) <= 1),
        "overlay painter/composition order: {pixels:?}"
    );
    let empty = Image::new(1, 1, vec![0; 4], source)?;
    for ui_scaling in [
        UiScaling::Nearest,
        UiScaling::Linear,
        UiScaling::Mitchell,
        UiScaling::CatmullRom,
        UiScaling::Xbr,
        UiScaling::Hybrid,
    ] {
        let pixels = capture(
            &empty,
            SurfaceSize::new(7, 5),
            GpuSettings {
                ui_scaling,
                ..Default::default()
            },
        )?;
        assert!(
            pixels.as_chunks::<4>().0.iter().all(|p| p
                .iter()
                .zip([63_u8, 0, 127, 255])
                .all(|(&v, e)| v.abs_diff(e) <= 1)),
            "{ui_scaling:?}: overlay changed with scaling"
        );
    }
    let neutral = capture(&empty, SurfaceSize::new(7, 5), GpuSettings::default())?;
    let corrected = capture(
        &empty,
        SurfaceSize::new(7, 5),
        GpuSettings {
            color_blind: ColorBlind::Deuteranopia,
            ..Default::default()
        },
    )?;
    assert_ne!(neutral, corrected, "overlay bypassed UI color correction");
    println!(
        "viewport overlay: source byte accumulation, UI occlusion/premultiplication, six scaling modes and color correction passed"
    );
    Ok(())
}
