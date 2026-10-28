//! Bounded actual-GPU shader fixtures. No account/cache/server is needed.
use rustclient_cache::{GroupId, Image, IndexId, Source};
use rustclient_renderer::{ColorBlind, GpuSettings, SurfaceSize, UiScaling};
use std::path::PathBuf;
#[path = "gpu_probe/depth.rs"]
mod depth;
#[path = "gpu_probe/overlay.rs"]
mod overlay;
#[path = "gpu_probe/title.rs"]
mod title;
#[path = "gpu_probe/visibility.rs"]
mod visibility;
fn capture(
    image: &Image,
    size: SurfaceSize,
    settings: GpuSettings,
) -> Result<Vec<u8>, Box<dyn std::error::Error>> {
    Ok(pollster::block_on(rustclient_renderer::capture_canvas(
        image,
        size,
        settings,
        |device| {
            let _ = device.poll(wgpu::PollType::wait_indefinitely());
        },
    ))?)
}
fn main() -> Result<(), Box<dyn std::error::Error>> {
    let destination = PathBuf::from(
        std::env::args()
            .nth(1)
            .unwrap_or_else(|| "target/gpu-probe".into()),
    );
    std::fs::create_dir_all(&destination)?;
    if let Some(cache) = std::env::args().nth(2) {
        title::capture(&cache, &destination)?;
        if std::env::args().nth(3).as_deref() == Some("--regions") {
            visibility::capture(&cache, &destination)?;
        }
    }
    visibility::shader_contract(&destination)?;
    overlay::capture()?;
    depth::capture(&destination)?;
    record_capabilities(&destination)?;
    let source = Source::group(IndexId(0), GroupId(0));
    let solid = Image::new(3, 3, [73, 129, 201, 255].repeat(9), source)?;
    let edges = Image::new(
        2,
        2,
        vec![
            0, 0, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 0, 0, 255,
        ],
        source,
    )?;
    for mode in [
        UiScaling::Nearest,
        UiScaling::Linear,
        UiScaling::Mitchell,
        UiScaling::CatmullRom,
        UiScaling::Xbr,
        UiScaling::Hybrid,
    ] {
        let settings = GpuSettings {
            ui_scaling: mode,
            ..Default::default()
        };
        let pixels = capture(&solid, SurfaceSize::new(11, 9), settings)?;
        for pixel in pixels.as_chunks::<4>().0 {
            assert!(
                pixel
                    .iter()
                    .zip([73, 129, 201, 255])
                    .all(|(&actual, expected)| actual.abs_diff(expected) <= 1),
                "{mode:?}: {pixel:?}"
            );
        }
        let pixels = capture(&edges, SurfaceSize::new(17, 13), settings)?;
        image::save_buffer(
            destination.join(format!("{mode:?}.png")),
            &pixels,
            17,
            13,
            image::ColorType::Rgba8,
        )?;
        println!("{mode:?}: solid/color transfer preserved; edge fixture captured");
    }
    let linear = capture(
        &edges,
        SurfaceSize::new(3, 3),
        GpuSettings {
            ui_scaling: UiScaling::Linear,
            ..Default::default()
        },
    )?;
    assert!(
        linear[16..19]
            .iter()
            .all(|&channel| channel.abs_diff(128) <= 1),
        "encoded-space midpoint {:?}",
        &linear[16..19]
    );
    let baseline = capture(&solid, SurfaceSize::new(11, 9), GpuSettings::default())?;
    let neutral = capture(
        &solid,
        SurfaceSize::new(11, 9),
        GpuSettings {
            color_blind: ColorBlind::Deuteranopia,
            color_blind_intensity: 0,
            ..Default::default()
        },
    )?;
    assert_eq!(baseline, neutral, "zero intensity changes canvas pixels");
    let corrected = capture(
        &solid,
        SurfaceSize::new(11, 9),
        GpuSettings {
            color_blind: ColorBlind::Deuteranopia,
            ..Default::default()
        },
    )?;
    assert_ne!(baseline, corrected, "color correction has no UI effect");
    println!("encoded midpoint and zero/full color correction: passed");
    Ok(())
}

fn record_capabilities(destination: &std::path::Path) -> Result<(), Box<dyn std::error::Error>> {
    let capabilities = pollster::block_on(rustclient_renderer::capture_capabilities())?;
    println!(
        "backend: {:?}; MSAA {:?}; enabled {:?}; supported {:?}",
        capabilities.backend,
        capabilities.sample_counts,
        capabilities.enabled_features,
        capabilities.supported_features
    );
    println!("device limits: {:?}", capabilities.device_limits);
    std::fs::write(
        destination.join("capabilities.txt"),
        format!("{capabilities:#?}"),
    )?;
    Ok(())
}
