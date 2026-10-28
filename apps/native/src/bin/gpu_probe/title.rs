//! Optional full cache-backed UI pipeline fixture; never logs into an account.
use rustclient_cache::{Cache, CacheProfile};
use rustclient_game_state::TitleScreen;
use rustclient_game_ui::{TitleUi, TitleViewport, UiFrame};
use rustclient_platform_native::DirectoryBlobs;
use rustclient_renderer::{GpuSettings, UiScaling};
use std::path::Path;

pub fn capture(cache: &str, destination: &Path) -> Result<(), Box<dyn std::error::Error>> {
    let cache = Cache::open(DirectoryBlobs::open(cache)?)?;
    let assets = CacheProfile::OpenRune240.load_title(&cache, |_| {})?;
    let mut frame = UiFrame::default();
    TitleUi::new(rustclient_game_ui::TitlePreferences::default()).project(
        &assets,
        TitleScreen::Welcome,
        0,
        &mut frame,
    );
    let mut baseline = Vec::new();
    for (name, width, height, scale) in [("title-1x", 765, 503, 1.), ("title-2x", 1530, 1006, 2.)] {
        let pixels = pollster::block_on(rustclient_renderer::capture_ui_with_settings(
            &assets,
            &std::collections::BTreeMap::default(),
            &frame,
            &[],
            TitleViewport {
                canvas_width: 765,
                physical_width: width,
                physical_height: height,
                scale,
                stretch: None,
            },
            GpuSettings {
                ui_scaling: UiScaling::Nearest,
                ..GpuSettings::default()
            },
            |device| {
                let _ = device.poll(wgpu::PollType::wait_indefinitely());
            },
        ))?;
        assert!(
            pixels
                .as_chunks::<4>()
                .0
                .iter()
                .any(|pixel| pixel[0..3] != [0; 3]),
            "blank title"
        );
        image::save_buffer(
            destination.join(format!("{name}.png")),
            &pixels,
            width,
            height,
            image::ColorType::Rgba8,
        )?;
        if baseline.is_empty() {
            baseline = pixels;
        } else {
            for y in 0..503_usize {
                for x in 0..765_usize {
                    let pixel = &baseline[(y * 765 + x) * 4..(y * 765 + x + 1) * 4];
                    for dy in 0..2 {
                        for dx in 0..2 {
                            let index = ((y * 2 + dy) * 1530 + x * 2 + dx) * 4;
                            assert_eq!(
                                &pixels[index..index + 4],
                                pixel,
                                "HiDPI source coordinate {x},{y}"
                            );
                        }
                    }
                }
            }
        }
    }
    println!("cache-backed UI: 1x/2x full-pipeline pixels match exactly");
    Ok(())
}
