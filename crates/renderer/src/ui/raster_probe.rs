//! Explicit offscreen lifecycle diagnostic; no reference pixels or timing claims.
use super::{
    DynamicImage, DynamicImages, Image, RenderError, TitleAssets, TitleViewport, UiFrame, UiGpu,
    UiImageId, UiStats,
};
use crate::{GpuSettings, ui_canvas::UiCanvas};
use std::collections::BTreeMap;

/// One actual submitted GPU lifecycle step.
#[derive(Debug)]
pub struct UiRasterStep {
    pub stage: &'static str,
    pub stats: UiStats,
}

struct Probe {
    device: wgpu::Device,
    queue: wgpu::Queue,
    ui: UiGpu,
    canvas: UiCanvas,
    viewport: TitleViewport,
    steps: Vec<UiRasterStep>,
}
impl Probe {
    fn step(
        &mut self,
        frame: &UiFrame,
        images: DynamicImages<'_>,
        expected_passes: u32,
        stage: &'static str,
    ) -> Result<(), RenderError> {
        let viewport = self.canvas.prepare(
            &self.device,
            &self.queue,
            frame,
            self.viewport,
            GpuSettings::default(),
            None,
        )?;
        self.ui
            .prepare(&self.device, &self.queue, frame, &[], images, viewport)?;
        let mut encoder = self
            .device
            .create_command_encoder(&wgpu::CommandEncoderDescriptor {
                label: Some("retained UI canvas lifecycle diagnostic"),
            });
        self.canvas.render(&mut encoder, &mut self.ui, None);
        self.queue.submit([encoder.finish()]);
        if self.ui.stats.raster_passes != expected_passes {
            return Err(RenderError::Ui(stage));
        }
        self.steps.push(UiRasterStep {
            stage,
            stats: self.ui.stats,
        });
        Ok(())
    }
}

/// Submit the shared UI GPU path across retained/repaint/reconstruction boundaries.
/// This diagnostic owns a fresh offscreen device and writes no files. The caller
/// supplies native completion polling; no synchronous GPU readback is used.
/// # Errors
/// Reports invalid frames/assets, unavailable devices, unexpected raster decisions
/// and GPU validation failures. Does not establish visual or gameplay parity.
pub async fn verify_ui_raster_retention(
    assets: &TitleAssets,
    images: &BTreeMap<UiImageId, Image>,
    frame: &mut UiFrame,
    viewport: TitleViewport,
    drive_completion: impl FnOnce(&wgpu::Device),
) -> Result<Vec<UiRasterStep>, RenderError> {
    if !frame.reuse_widgets() {
        return Err(RenderError::Ui(
            "UI raster diagnostic requires a sealed widget prefix",
        ));
    }
    let (device, queue, _) = crate::capture::capture_device().await?;
    let errors = device.push_error_scope(wgpu::ErrorFilter::Validation);
    let ui = UiGpu::with_images(&device, &queue, crate::ui_canvas::FORMAT, assets, images)?;
    let canvas = UiCanvas::new(&device, wgpu::TextureFormat::Rgba8UnormSrgb);
    let mut probe = Probe {
        device,
        queue,
        ui,
        canvas,
        viewport,
        steps: Vec::new(),
    };
    let empty = DynamicImages::default();
    probe.step(frame, empty, 1, "initial raster")?;
    probe.step(frame, empty, 0, "unchanged canvas reused")?;
    let rect = rustclient_game_ui::Rect::new(0., 0., 10., 10.);
    frame.image(UiImageId::White, rect, rect);
    let overlay = probe.step(frame, empty, 1, "overlay added");
    frame.quads.pop();
    overlay?;
    probe.step(frame, empty, 1, "overlay erased")?;
    probe.step(frame, empty, 0, "static canvas recovered")?;
    frame.image(UiImageId::Sprite(u32::MAX), rect, rect);
    let failed = probe.step(frame, empty, 1, "unknown asset must fail preparation");
    frame.quads.pop();
    if !matches!(failed, Err(RenderError::Ui("unknown UI image"))) {
        return Err(RenderError::Ui(
            "UI raster diagnostic did not reject the unknown asset",
        ));
    }
    probe.step(frame, empty, 1, "failed asset preparation recovered")?;
    frame.seal_widgets();
    probe.step(frame, empty, 1, "new command identity")?;
    let pixels = [255; 16];
    let dynamic = DynamicImages {
        minimap: DynamicImage {
            pixels: &pixels,
            width: 2,
            height: 2,
            revision: 1,
        },
        ..DynamicImages::default()
    };
    probe.step(frame, dynamic, 1, "dynamic pixels uploaded")?;
    probe.step(frame, dynamic, 0, "unchanged dynamic revision reused")?;
    let original_canvas = frame.canvas;
    frame.canvas = Some(rustclient_model::CanvasSize {
        width: 766,
        height: 503,
    });
    let resized = probe.step(frame, dynamic, 1, "logical canvas resized");
    frame.canvas = original_canvas;
    resized?;
    probe.step(frame, dynamic, 1, "logical canvas restored")?;
    probe.canvas = UiCanvas::new(&probe.device, wgpu::TextureFormat::Rgba8UnormSrgb);
    probe.step(frame, dynamic, 1, "canvas reconstructed")?;
    probe
        .ui
        .replace_images(&probe.device, &probe.queue, assets, images)?;
    probe.step(frame, dynamic, 1, "atlas replaced")?;
    probe.ui = UiGpu::with_images(
        &probe.device,
        &probe.queue,
        crate::ui_canvas::FORMAT,
        assets,
        images,
    )?;
    probe.step(frame, dynamic, 1, "UI renderer reconstructed")?;
    probe.step(frame, dynamic, 0, "reconstructed canvas reused")?;
    let errors = errors.pop();
    drive_completion(&probe.device);
    if errors.await.is_some() {
        return Err(RenderError::Ui(
            "UI raster diagnostic GPU validation failed",
        ));
    }
    Ok(probe.steps)
}
