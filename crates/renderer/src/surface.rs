use crate::ui::{UiGpu, UiStats};
use crate::{GameplayCameraController, world::WorldGpu};
use core::fmt;
use rustclient_cache::TitleAssets;
use rustclient_game_ui::{TitleViewport, UiFrame};
use std::sync::{
    Arc,
    atomic::{AtomicBool, Ordering},
};

/// Physical render-surface size in pixels.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash, Default)]
pub struct SurfaceSize {
    /// Surface width in physical pixels.
    pub width: u32,
    /// Surface height in physical pixels.
    pub height: u32,
}

impl SurfaceSize {
    /// Construct a physical surface size.
    #[must_use]
    pub const fn new(width: u32, height: u32) -> Self {
        Self { width, height }
    }

    /// Return whether the surface is currently non-renderable because one axis is zero.
    #[must_use]
    pub const fn is_zero(self) -> bool {
        self.width == 0 || self.height == 0
    }
}

/// Linear clear color used by the bootstrap renderer.
#[derive(Debug, Clone, Copy, PartialEq)]
pub struct ClearColor {
    /// Red channel.
    pub r: f64,
    /// Green channel.
    pub g: f64,
    /// Blue channel.
    pub b: f64,
    /// Alpha channel.
    pub a: f64,
}

impl ClearColor {
    /// Deterministic CP2 bootstrap clear color.
    pub const BOOTSTRAP: Self = Self {
        r: 0.035,
        g: 0.055,
        b: 0.075,
        a: 1.0,
    };

    const fn as_wgpu(self) -> wgpu::Color {
        wgpu::Color {
            r: self.r,
            g: self.g,
            b: self.b,
            a: self.a,
        }
    }
}

/// Result of one render attempt.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum FrameOutcome {
    /// A frame was submitted and presented.
    Presented,
    /// Rendering is suspended while the surface has a zero dimension.
    Suspended,
    /// The backend temporarily could not provide a presentable texture.
    Skipped,
    /// Surface configuration changed; the caller should request another frame.
    Reconfigured,
    /// The surface was lost and the composition root must rebuild this renderer.
    RecreateRequired,
}

/// Failure to initialize the shared renderer.
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum RendererInitError {
    /// The platform target could not become a `wgpu` surface.
    Surface(Box<str>),
    /// No compatible adapter could be acquired.
    Adapter(Box<str>),
    /// A logical GPU device could not be created.
    Device(Box<str>),
    /// The surface reported no supported texture format.
    NoSurfaceFormat,
}

impl fmt::Display for RendererInitError {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        match self {
            Self::Surface(message) => write!(formatter, "surface creation failed: {message}"),
            Self::Adapter(message) => write!(formatter, "adapter request failed: {message}"),
            Self::Device(message) => write!(formatter, "device request failed: {message}"),
            Self::NoSurfaceFormat => formatter.write_str("surface reported no supported formats"),
        }
    }
}

impl std::error::Error for RendererInitError {}

/// Recoverable or caller-actionable render failure.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum RenderError {
    /// `wgpu` reported a validation failure while acquiring the surface texture.
    Validation,
    /// Physical target exceeds the created device's texture budget.
    SurfaceSize {
        requested: SurfaceSize,
        maximum: u32,
    },
    /// Invalid UI asset, upload or projection input.
    Ui(&'static str),
    /// Invalid world mesh, camera or GPU resource input.
    World(&'static str),
}

impl fmt::Display for RenderError {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        match self {
            Self::SurfaceSize { requested, maximum } => write!(
                formatter,
                "surface {}x{} exceeds device texture dimension {maximum}",
                requested.width, requested.height
            ),
            Self::World(message) => write!(formatter, "world projection: {message}"),
            Self::Ui(message) => write!(formatter, "UI projection: {message}"),
            Self::Validation => formatter.write_str("wgpu surface validation failure"),
        }
    }
}

impl std::error::Error for RenderError {}

/// Shared native/browser `wgpu` surface renderer.
///
/// Window/event-loop ownership remains with the composition root. If the surface
/// is lost, discard this value and rebuild it from the authoritative application
/// state and the still-live platform window/canvas target.
pub struct SurfaceRenderer {
    _instance: wgpu::Instance,
    surface: wgpu::Surface<'static>,
    device: wgpu::Device,
    queue: wgpu::Queue,
    config: wgpu::SurfaceConfiguration,
    view_format: wgpu::TextureFormat,
    configured: bool,
    ui: Option<UiGpu>,
    ui_canvas: Option<crate::ui_canvas::UiCanvas>,
    world: Option<WorldGpu>,
    settings: crate::GpuSettings,
    effective: crate::EffectiveGpu,
    capabilities: crate::GpuCapabilities,
    shell_staging: crate::shell::ShellStaging,
    egui_staging: crate::shell::EguiStaging,
    shell_gpu: crate::shell::ShellGpu,
    game_width: Option<u32>,
    stretch: Option<rustclient_game_ui::StretchedCanvas>,
    /// Latest posed geometry awaiting a presentable frame. wgpu retains every staged
    /// write until the next submission, so uploads are issued only when a frame will
    /// be submitted; a hidden window then costs one bounded CPU copy and no GPU memory.
    staged: StagedGeometry,
    device_lost: Arc<AtomicBool>,
    instrumentation: crate::Instrumentation,
    frame_timings: crate::FrameTimings,
    gpu_timer: Option<crate::timing::GpuTimer>,
}

/// One latest-wins geometry stream: a reusable copy plus whether it still needs uploading.
#[derive(Default)]
struct StagedStream {
    geometry: rustclient_scene::SceneGeometry,
    pending: bool,
    known: bool,
}

impl StagedStream {
    /// Copy the GPU-visible parts, reusing the previous staging allocation.
    fn stage(&mut self, geometry: &rustclient_scene::SceneGeometry) -> bool {
        if self.known
            // SceneVertex is integer POD with no padding; byte comparison has identical
            // equality semantics and lets the platform use its optimized slice comparison.
            && bytemuck::cast_slice::<_, u8>(&self.geometry.vertices) == bytemuck::cast_slice::<_, u8>(&geometry.vertices)
            && self.geometry.opaque == geometry.opaque
            && self.geometry.transparent == geometry.transparent
            && self.geometry.models == geometry.models
            && self.geometry.hidden_static == geometry.hidden_static
        {
            return false;
        }
        let staged = &mut self.geometry;
        staged.vertices.clear();
        staged.vertices.extend_from_slice(&geometry.vertices);
        staged.opaque.clear();
        staged.opaque.extend_from_slice(&geometry.opaque);
        staged.transparent.clear();
        staged.transparent.extend_from_slice(&geometry.transparent);
        staged.models.clear();
        staged.models.extend_from_slice(&geometry.models);
        staged.hidden_static.clone_from(&geometry.hidden_static);
        self.pending = true;
        self.known = true;
        true
    }

    /// The staged geometry exactly once per staging.
    fn apply_pending<E>(
        &mut self,
        apply: impl FnOnce(&rustclient_scene::SceneGeometry) -> Result<(), E>,
    ) -> Result<(), E> {
        if self.pending {
            apply(&self.geometry)?;
            self.pending = false;
        }
        Ok(())
    }
}

/// The two dynamic geometry streams awaiting a presentable frame.
#[derive(Default)]
struct StagedGeometry {
    dynamic: StagedStream,
    entities: StagedStream,
}

/// Explicit CPU staging diagnostic; counts requested uploads rather than submitted GPU work.
#[cfg(not(target_arch = "wasm32"))]
#[derive(Debug, Clone, Copy)]
pub struct WorldStagingTiming {
    pub retained: bool,
    pub cpu: std::time::Duration,
    pub upload_requests: u32,
}
/// Compare the previous copy-every-update policy with identical-payload retention.
/// Uses 256 repetitions of the supplied real geometry; no surface or GPU time is measured.
#[cfg(not(target_arch = "wasm32"))]
#[must_use]
pub fn measure_world_staging(
    geometry: &rustclient_scene::SceneGeometry,
) -> [WorldStagingTiming; 2] {
    [false, true].map(|retained| {
        let mut stream = StagedStream::default();
        let mut upload_requests = 0;
        let started = std::time::Instant::now();
        for _ in 0..256 {
            if !retained {
                stream.known = false;
            }
            stream.stage(geometry);
            if stream.pending {
                upload_requests += 1;
                stream.pending = false;
            }
        }
        WorldStagingTiming {
            retained,
            cpu: started.elapsed(),
            upload_requests,
        }
    })
}

impl SurfaceRenderer {
    /// Apply a validated projection; next present updates the shared shader uniform.
    /// Apply validated GPU preferences. Only resources the change affects are rebuilt.
    /// Returns what this adapter actually does, including any requested-vs-effective deviations.
    pub fn set_gpu_settings(&mut self, settings: crate::GpuSettings) -> &crate::EffectiveGpu {
        let effective = settings.resolve(&self.capabilities);
        if let Some(world) = &mut self.world {
            world.set_samples(&self.device, effective.samples);
            world.set_filtering(&self.device, effective.anisotropy, effective.mipmaps);
        }
        if effective.present_mode != self.config.present_mode {
            self.config.present_mode = effective.present_mode;
            if self.configured {
                self.reconfigure();
            }
        }
        self.settings = settings;
        self.effective = effective;
        &self.effective
    }
    /// Accepted render-policy projection; picking uses the same preferences as rasterization.
    #[must_use]
    pub const fn gpu_settings(&self) -> crate::GpuSettings {
        self.settings
    }

    /// The behavior currently in effect, for diagnostics and the settings panel.
    #[must_use]
    pub fn effective_gpu(&self) -> &crate::EffectiveGpu {
        &self.effective
    }

    /// Stage a retained panel only when its generation changes. Upload follows surface acquisition.
    /// # Errors
    /// Rejects invalid pixel dimensions without replacing the last accepted layer.
    pub fn set_shell_layer(
        &mut self,
        layer: Option<crate::ShellLayer<'_>>,
    ) -> Result<(), RenderError> {
        self.egui_staging.enabled = false;
        self.shell_staging.set(layer)
    }

    /// Stage bounded trusted UI primitives; renderer resources remain engine-owned.
    /// # Errors
    /// Rejects invalid snapshots before changing the accepted cached panel.
    pub fn set_egui_shell_layer(
        &mut self,
        layer: crate::EguiShellLayer<'_>,
    ) -> Result<(), RenderError> {
        self.egui_staging.set(layer, &mut self.shell_staging)
    }
    #[must_use]
    pub fn egui_shell_stats(&self) -> crate::EguiShellStats {
        self.egui_staging.stats
    }

    /// Width of the physical game canvas after reserving shell chrome on the right.
    pub fn set_game_width(&mut self, width: Option<u32>) {
        self.game_width = width;
    }

    /// Where stretched mode puts the logical canvas on the game framebuffer; `None` draws it
    /// at the window scale, centered.
    pub fn set_stretch(&mut self, stretch: Option<rustclient_game_ui::StretchedCanvas>) {
        self.stretch = stretch;
    }

    fn viewport(&self, canvas_width: u32, scale: f64) -> TitleViewport {
        TitleViewport {
            canvas_width,
            physical_width: self
                .game_width
                .unwrap_or(self.config.width)
                .min(self.config.width),
            physical_height: self.config.height,
            scale,
            stretch: self.stretch,
        }
    }

    #[must_use]
    pub fn world_geometry_stats(&self) -> crate::WorldGeometryStats {
        self.world.as_ref().map_or_else(
            crate::WorldGeometryStats::default,
            crate::world::WorldGpu::geometry_stats,
        )
    }

    #[must_use]
    pub fn shell_stats(&self) -> crate::ShellStats {
        self.shell_staging.stats
    }

    /// Initialize one renderer from any target accepted by `wgpu`.
    ///
    /// # Errors
    ///
    /// Returns [`RendererInitError`] if surface creation fails, no compatible
    /// adapter can be acquired, device creation fails, or the surface exposes no
    /// usable texture format.
    pub async fn new<T>(target: T, size: SurfaceSize) -> Result<Self, RendererInitError>
    where
        T: Into<wgpu::SurfaceTarget<'static>>,
    {
        Self::new_with_instrumentation(target, size, crate::Instrumentation::Disabled).await
    }
    /// Construct a renderer with optional asynchronous engine timing diagnostics.
    /// # Errors
    /// Reports the same surface/device failures as `new`.
    pub async fn new_with_instrumentation<T>(
        target: T,
        size: SurfaceSize,
        instrumentation: crate::Instrumentation,
    ) -> Result<Self, RendererInitError>
    where
        T: Into<wgpu::SurfaceTarget<'static>>,
    {
        let instance = wgpu::Instance::default();
        let surface = instance
            .create_surface(target)
            .map_err(|error| RendererInitError::Surface(error.to_string().into()))?;
        let adapter = instance
            .request_adapter(&wgpu::RequestAdapterOptions {
                power_preference: wgpu::PowerPreference::HighPerformance,
                compatible_surface: Some(&surface),
                force_fallback_adapter: false,
                apply_limit_buckets: false,
            })
            .await
            .map_err(|error| RendererInitError::Adapter(error.to_string().into()))?;
        let mut descriptor = crate::settings::device_descriptor(&adapter);
        if instrumentation.enabled() && adapter.features().contains(crate::timing::GPU_FEATURES) {
            descriptor.required_features |= crate::timing::GPU_FEATURES;
        }
        let (device, queue) = adapter
            .request_device(&descriptor)
            .await
            .map_err(|error| RendererInitError::Device(error.to_string().into()))?;

        let device_lost = Arc::new(AtomicBool::new(false));
        let lost_signal = Arc::clone(&device_lost);
        device.set_device_lost_callback(move |_, _| {
            lost_signal.store(true, Ordering::Relaxed);
        });

        let capabilities = surface.get_capabilities(&adapter);
        let gpu_capabilities =
            crate::settings::capabilities(&adapter, &device, capabilities.present_modes.clone());
        let settings = crate::GpuSettings::default();
        let effective = settings.resolve(&gpu_capabilities);
        let surface_format = capabilities
            .formats
            .first()
            .copied()
            .ok_or(RendererInitError::NoSurfaceFormat)?;
        let view_format = surface_format.add_srgb_suffix();
        let config = wgpu::SurfaceConfiguration {
            usage: wgpu::TextureUsages::RENDER_ATTACHMENT,
            format: surface_format,
            color_space: wgpu::SurfaceColorSpace::Auto,
            view_formats: vec![view_format],
            alpha_mode: wgpu::CompositeAlphaMode::Auto,
            width: size.width.max(1),
            height: size.height.max(1),
            desired_maximum_frame_latency: 2,
            present_mode: effective.present_mode,
        };

        // Pipeline compilation belongs to device initialization, never the first sidebar click.
        // Hidden chrome retains only the tiny placeholder image and does no recurring uploads.
        let shell_gpu = crate::shell::ShellGpu::new(&device, view_format);
        let gpu_timer = instrumentation
            .enabled()
            .then(|| crate::timing::GpuTimer::new(&device, &queue))
            .flatten();
        let mut renderer = Self {
            _instance: instance,
            surface,
            device,
            queue,
            config,
            view_format,
            configured: false,
            ui: None,
            ui_canvas: None,
            world: None,
            settings,
            effective,
            capabilities: gpu_capabilities,
            shell_staging: crate::shell::ShellStaging::default(),
            egui_staging: crate::shell::EguiStaging::default(),
            shell_gpu,
            game_width: None,
            stretch: None,
            staged: StagedGeometry::default(),
            device_lost,
            instrumentation,
            frame_timings: crate::FrameTimings::default(),
            gpu_timer,
        };
        renderer
            .resize(size)
            .map_err(|error| RendererInitError::Device(error.to_string().into()))?;
        Ok(renderer)
    }

    /// Actual adapter/device features and limits for diagnostics and renderer-policy decisions.
    #[must_use]
    pub const fn gpu_capabilities(&self) -> &crate::GpuCapabilities {
        &self.capabilities
    }

    /// Upload immutable title resources once per GPU lifetime.
    ///
    /// # Errors
    /// Returns an error if the asset set exceeds atlas limits.
    pub fn set_title_assets(&mut self, assets: &TitleAssets) -> Result<(), RenderError> {
        self.ui = Some(UiGpu::new(
            &self.device,
            &self.queue,
            crate::ui_canvas::FORMAT,
            assets,
        )?);
        self.ui_canvas = Some(crate::ui_canvas::UiCanvas::new(
            &self.device,
            self.view_format,
        ));
        Ok(())
    }

    /// Upload retained CPU widget images into the same ordered UI pipeline.
    /// # Errors
    /// Rejects assets exceeding the bounded atlas.
    pub fn set_widget_images(
        &mut self,
        assets: &TitleAssets,
        images: &std::collections::BTreeMap<rustclient_game_ui::UiImageId, rustclient_cache::Image>,
    ) -> Result<(), RenderError> {
        if let Some(ui) = &mut self.ui {
            return ui.replace_images(&self.device, &self.queue, assets, images);
        }
        let ui = UiGpu::with_images(
            &self.device,
            &self.queue,
            crate::ui_canvas::FORMAT,
            assets,
            images,
        )?;
        self.ui = Some(ui);
        self.ui_canvas = Some(crate::ui_canvas::UiCanvas::new(
            &self.device,
            self.view_format,
        ));
        Ok(())
    }
    /// Upload a disposable world mesh once per asset/GPU lifetime.
    /// # Errors
    /// Returns bounded mesh/device allocation failures.
    pub fn set_world(&mut self, mesh: &rustclient_scene::WorldMesh) -> Result<(), RenderError> {
        self.upload_world(mesh, false)
    }
    /// Refresh scenery inside the current live scene, retaining its independent actor stream.
    /// A new map/session must use [`Self::set_world`], even if cache asset identity repeats.
    /// # Errors
    /// Returns bounded mesh/device allocation failures.
    pub fn set_world_scenery(
        &mut self,
        mesh: &rustclient_scene::WorldMesh,
    ) -> Result<(), RenderError> {
        self.upload_world(mesh, true)
    }
    /// Publish an owned CPU-prepared projection, returning its source mesh for recovery/picking.
    /// # Errors
    /// Rejects device limits before queued writes; malformed inputs fail during preparation.
    pub fn set_prepared_world(
        &mut self,
        prepared: crate::PreparedWorldMesh,
        update: crate::WorldUpdate,
    ) -> Result<rustclient_scene::WorldMesh, RenderError> {
        let (mesh, upload) = prepared.into_parts();
        let retain_actors = update == crate::WorldUpdate::Scenery
            && self
                .world
                .as_ref()
                .is_some_and(|world| world.asset == mesh.asset);
        if let Some(world) = &mut self.world {
            world.replace_prepared(&self.device, &self.queue, &mesh, upload, update)?;
        } else {
            let mut world = WorldGpu::new_prepared(
                &self.device,
                &self.queue,
                self.view_format,
                SurfaceSize::new(self.config.width, self.config.height),
                &mesh,
                self.effective.samples,
                upload,
            )?;
            world.set_filtering(
                &self.device,
                self.effective.anisotropy,
                self.effective.mipmaps,
            );
            self.world = Some(world);
        }
        if let Some(canvas) = &mut self.ui_canvas {
            canvas.invalidate_world();
        }
        self.staged.dynamic = StagedStream::default();
        if !retain_actors {
            self.staged.entities = StagedStream::default();
        }
        Ok(mesh)
    }
    fn upload_world(
        &mut self,
        mesh: &rustclient_scene::WorldMesh,
        retain_actors: bool,
    ) -> Result<(), RenderError> {
        let same_asset = retain_actors
            && self
                .world
                .as_ref()
                .is_some_and(|world| world.asset == mesh.asset);
        if let Some(world) = &mut self.world {
            if same_asset {
                world.replace_scenery(&self.device, &self.queue, mesh)?;
            } else {
                world.replace_mesh(&self.device, &self.queue, mesh)?;
            }
        } else {
            let mut world = WorldGpu::new(
                &self.device,
                &self.queue,
                self.view_format,
                SurfaceSize::new(self.config.width, self.config.height),
                mesh,
                self.effective.samples,
            )?;
            world.set_filtering(
                &self.device,
                self.effective.anisotropy,
                self.effective.mipmaps,
            );
            self.world = Some(world);
        }
        if let Some(canvas) = &mut self.ui_canvas {
            canvas.invalidate_world();
        }
        // A same-scene door change must not erase actors (or an actor update awaiting present).
        self.staged.dynamic = StagedStream::default();
        if !same_asset {
            self.staged.entities = StagedStream::default();
        }
        Ok(())
    }
    /// Update posed geometry while retaining static vertices, textures and pipelines.
    /// # Errors
    /// Rejects mismatched scene identity and device allocation limits.
    pub fn set_world_dynamic(
        &mut self,
        asset: rustclient_model::SceneAssetId,
        geometry: &rustclient_scene::SceneGeometry,
    ) -> Result<(), RenderError> {
        let world = self
            .world
            .as_ref()
            .ok_or(RenderError::World("scene is not uploaded"))?;
        if world.asset != asset {
            return Err(RenderError::World("dynamic scenery asset mismatch"));
        }
        self.staged.dynamic.stage(geometry);
        Ok(())
    }

    /// Replace live entity geometry; scenery and static buffers are untouched.
    /// The upload happens with the next presented frame.
    /// # Errors
    /// Rejects mismatched scene identity.
    pub fn set_world_entities(
        &mut self,
        asset: rustclient_model::SceneAssetId,
        geometry: &rustclient_scene::SceneGeometry,
    ) -> Result<(), RenderError> {
        let world = self
            .world
            .as_ref()
            .ok_or(RenderError::World("scene is not uploaded"))?;
        if world.asset != asset {
            return Err(RenderError::World("entity geometry asset mismatch"));
        }
        self.staged.entities.stage(geometry);
        Ok(())
    }

    /// Render committed world geometry with a disposable camera and UI overlay.
    /// # Errors
    /// Returns missing/mismatched resources or surface validation failures.
    #[expect(
        clippy::too_many_arguments,
        reason = "composition-root frame inputs; grouping deferred to renderer API pass"
    )]
    pub fn render_world(
        &mut self,
        asset: rustclient_model::SceneAssetId,
        camera: GameplayCameraController,
        visibility: &rustclient_scene::SceneVisibility,
        cycle: rustclient_model::ClientCycle,
        frame: &UiFrame,
        images: crate::ui::DynamicImages<'_>,
        scale: f64,
        before_present: impl FnOnce(),
    ) -> Result<FrameOutcome, RenderError> {
        if !self.configured {
            return Ok(FrameOutcome::Suspended);
        }
        if self.device_lost.load(Ordering::Relaxed) {
            return Ok(FrameOutcome::RecreateRequired);
        }
        let viewport = self.viewport(frame.canvas.map_or(765, |s| s.width), scale);
        let world = self
            .world
            .as_mut()
            .ok_or(RenderError::World("scene is not uploaded"))?;
        if world.asset != asset {
            return Err(RenderError::World(
                "GPU scene does not match authoritative asset reference",
            ));
        }
        // Acquire before staging any upload: a frame that cannot be presented must not
        // queue writes, because wgpu holds them until the next submission.
        let surface_texture = match self.acquire() {
            Ok(texture) => texture,
            Err(outcome) => return outcome,
        };
        let preparation = crate::timing::CpuClock::start(self.instrumentation.enabled());
        let world = self
            .world
            .as_mut()
            .ok_or(RenderError::World("scene is not uploaded"))?;
        self.staged
            .dynamic
            .apply_pending(|geometry| world.update_dynamic(&self.device, &self.queue, geometry))?;
        self.staged
            .entities
            .apply_pending(|geometry| world.update_entities(&self.device, &self.queue, geometry))?;
        let bounds = frame.world_viewport;
        world.prepare_viewport(
            &self.queue,
            camera,
            viewport,
            (cycle.get() & 127) as u32,
            bounds,
            frame.viewport_settings,
            visibility,
            frame.scene_brightness,
            self.settings,
        );
        self.prepare_ui(frame, &[], images, viewport, true)?;
        self.frame_timings.preparation = preparation.elapsed();
        Ok(self.present_frame(
            surface_texture,
            ClearColor {
                r: 0.,
                g: 0.,
                b: 0.,
                a: 1.,
            },
            true,
            true,
            before_present,
        ))
    }

    /// Timing spans for the latest attempted frame; presentation calls are not display timestamps.
    #[must_use]
    pub const fn frame_timings(&self) -> crate::FrameTimings {
        self.frame_timings
    }
    /// Completed samples are consumed once, without waiting for the GPU.
    pub fn take_gpu_timings(&mut self) -> [Option<crate::GpuTimingSample>; 3] {
        self.gpu_timer
            .as_mut()
            .map_or([None; 3], crate::timing::GpuTimer::take_samples)
    }
    /// Poll diagnostic callbacks without waiting; useful after the last benchmark frame.
    pub fn poll_gpu_timings(&mut self) -> usize {
        self.gpu_timer.as_mut().map_or(0, |timer| {
            timer.poll(&self.device);
            timer.pending()
        })
    }
    /// Latest title projection counters, without GPU readback.
    #[must_use]
    pub fn ui_stats(&self) -> UiStats {
        self.ui.as_ref().map_or(UiStats::default(), |ui| ui.stats)
    }

    /// Present title commands using the same physical/logical input transform.
    ///
    /// # Errors
    /// Returns an error for invalid commands, missing resources or surface validation.
    pub fn render_ui<F: FnOnce()>(
        &mut self,
        frame: &UiFrame,
        flames: &[u8],
        scale: f64,
        before_present: F,
    ) -> Result<FrameOutcome, RenderError> {
        self.render_ui_with_flame_revision(frame, flames, 0, scale, before_present)
    }
    /// Present title commands with a named cosmetic flame image. Zero forces upload.
    /// # Errors
    /// Reports the same failures as `render_ui`.
    pub fn render_ui_with_flame_revision<F: FnOnce()>(
        &mut self,
        frame: &UiFrame,
        flames: &[u8],
        flame_revision: u64,
        scale: f64,
        before_present: F,
    ) -> Result<FrameOutcome, RenderError> {
        if !self.configured {
            return Ok(FrameOutcome::Suspended);
        }
        if self.device_lost.load(Ordering::Relaxed) {
            return Ok(FrameOutcome::RecreateRequired);
        }
        let surface_texture = match self.acquire() {
            Ok(texture) => texture,
            Err(outcome) => return outcome,
        };
        let viewport = self.viewport(frame.canvas.map_or(765, |s| s.width), scale);
        let preparation = crate::timing::CpuClock::start(self.instrumentation.enabled());
        self.prepare_ui(
            frame,
            flames,
            crate::ui::DynamicImages {
                flame_revision,
                ..Default::default()
            },
            viewport,
            false,
        )?;
        self.frame_timings.preparation = preparation.elapsed();
        Ok(self.present_frame(
            surface_texture,
            ClearColor {
                r: 0.,
                g: 0.,
                b: 0.,
                a: 1.,
            },
            true,
            false,
            before_present,
        ))
    }

    /// Reconfigure for a new physical size. Zero-sized surfaces are suspended.
    /// # Errors
    /// Rejects targets beyond the negotiated device texture dimension, preserving old resources.
    pub fn resize(&mut self, size: SurfaceSize) -> Result<(), RenderError> {
        if size.is_zero() {
            self.configured = false;
            return Ok(());
        }
        validate_size(size, self.device.limits().max_texture_dimension_2d)?;
        if self.configured && self.config.width == size.width && self.config.height == size.height {
            return Ok(());
        }
        self.config.width = size.width;
        self.config.height = size.height;
        self.surface.configure(&self.device, &self.config);
        self.configured = true;
        if let Some(world) = &mut self.world {
            world.resize(&self.device, size);
        }
        Ok(())
    }

    /// Render and present a deterministic clear frame.
    ///
    /// `before_present` lets a window-system adapter perform its required
    /// pre-present notification without making the renderer depend on that
    /// windowing library.
    ///
    /// # Errors
    ///
    /// Returns [`RenderError::Validation`] when `wgpu` reports a validation
    /// failure while acquiring the next surface texture. Recoverable surface
    /// state changes are reported through [`FrameOutcome`] instead.
    pub fn render_clear<F>(
        &mut self,
        color: ClearColor,
        before_present: F,
    ) -> Result<FrameOutcome, RenderError>
    where
        F: FnOnce(),
    {
        self.render_frame(color, false, false, before_present)
    }

    fn render_frame<F: FnOnce()>(
        &mut self,
        color: ClearColor,
        draw_ui: bool,
        draw_world: bool,
        before_present: F,
    ) -> Result<FrameOutcome, RenderError> {
        match self.acquire() {
            Ok(texture) => {
                Ok(self.present_frame(texture, color, draw_ui, draw_world, before_present))
            }
            Err(outcome) => outcome,
        }
    }

    /// Next presentable surface texture, or the outcome that explains why there is none.
    ///
    /// Callers acquire before preparing uploads so skipped frames stage nothing.
    fn acquire(&mut self) -> Result<wgpu::SurfaceTexture, Result<FrameOutcome, RenderError>> {
        let next = self.frame_timings.frame.wrapping_add(1);
        self.frame_timings = crate::FrameTimings {
            frame: next,
            gpu_available: self.gpu_timer.is_some(),
            ..Default::default()
        };
        if let Some(timer) = &mut self.gpu_timer {
            timer.poll(&self.device);
            self.frame_timings.gpu_samples_dropped = timer.dropped;
        }
        if !self.configured {
            return Err(Ok(FrameOutcome::Suspended));
        }
        if self.device_lost.load(Ordering::Relaxed) {
            return Err(Ok(FrameOutcome::RecreateRequired));
        }
        let started = crate::timing::CpuClock::start(self.instrumentation.enabled());
        let texture = self.surface.get_current_texture();
        self.frame_timings.acquire = started.elapsed();
        match texture {
            wgpu::CurrentSurfaceTexture::Success(texture) => Ok(texture),
            wgpu::CurrentSurfaceTexture::Occluded | wgpu::CurrentSurfaceTexture::Timeout => {
                Err(Ok(FrameOutcome::Skipped))
            }
            wgpu::CurrentSurfaceTexture::Suboptimal(texture) => {
                drop(texture);
                self.reconfigure();
                Err(Ok(FrameOutcome::Reconfigured))
            }
            wgpu::CurrentSurfaceTexture::Outdated => {
                self.reconfigure();
                Err(Ok(FrameOutcome::Reconfigured))
            }
            wgpu::CurrentSurfaceTexture::Lost => Err(Ok(FrameOutcome::RecreateRequired)),
            wgpu::CurrentSurfaceTexture::Validation => Err(Err(RenderError::Validation)),
        }
    }

    fn prepare_ui(
        &mut self,
        frame: &UiFrame,
        flames: &[u8],
        images: crate::ui::DynamicImages<'_>,
        viewport: TitleViewport,
        draw_world: bool,
    ) -> Result<(), RenderError> {
        let canvas = self
            .ui_canvas
            .as_mut()
            .ok_or(RenderError::Ui("game canvas unavailable"))?;
        let source_viewport = canvas.prepare(
            &self.device,
            &self.queue,
            frame,
            viewport,
            self.settings,
            self.world.as_ref().filter(|_| draw_world),
        )?;
        self.ui
            .as_mut()
            .ok_or(RenderError::Ui("UI assets unavailable"))?
            .prepare(
                &self.device,
                &self.queue,
                frame,
                flames,
                images,
                source_viewport,
            )
    }

    fn present_frame<F: FnOnce()>(
        &mut self,
        surface_texture: wgpu::SurfaceTexture,
        color: ClearColor,
        draw_ui: bool,
        draw_world: bool,
        before_present: F,
    ) -> FrameOutcome {
        let cpu_started = crate::timing::CpuClock::start(self.instrumentation.enabled());
        self.shell_staging
            .prepare(&mut self.shell_gpu, &self.device, &self.queue);
        let texture_view = surface_texture
            .texture
            .create_view(&wgpu::TextureViewDescriptor {
                format: Some(self.view_format),
                ..Default::default()
            });
        let mut encoder = self
            .device
            .create_command_encoder(&wgpu::CommandEncoderDescriptor {
                label: Some("rustclient surface encoder"),
            });
        let query = self
            .gpu_timer
            .as_mut()
            .and_then(|timer| timer.begin(&mut encoder, self.frame_timings.frame));
        if draw_world && let Some(world) = &self.world {
            world.render_scene(&mut encoder);
        }
        if draw_ui && let (Some(canvas), Some(ui)) = (&mut self.ui_canvas, &mut self.ui) {
            canvas.render(&mut encoder, ui);
        }
        self.egui_staging
            .prepare(&self.device, &self.queue, &mut encoder, &mut self.shell_gpu);
        {
            let mut pass = encoder.begin_render_pass(&wgpu::RenderPassDescriptor {
                label: Some("game display and shell"),
                color_attachments: &[Some(wgpu::RenderPassColorAttachment {
                    view: &texture_view,
                    depth_slice: None,
                    resolve_target: None,
                    ops: wgpu::Operations {
                        load: wgpu::LoadOp::Clear(color.as_wgpu()),
                        store: wgpu::StoreOp::Store,
                    },
                })],
                depth_stencil_attachment: None,
                timestamp_writes: None,
                occlusion_query_set: None,
                multiview_mask: None,
            });
            if draw_ui {
                if let Some(canvas) = &self.ui_canvas {
                    canvas.draw(&mut pass);
                }
            } else if draw_world && let Some(world) = &self.world {
                world.target.composite(&mut pass);
            }
            self.shell_staging.draw(
                &self.shell_gpu,
                &mut pass,
                [self.config.width, self.config.height],
            );
        }
        if let Some(index) = query
            && let Some(timer) = &self.gpu_timer
        {
            timer.end(&mut encoder, index);
        }
        self.queue.submit([encoder.finish()]);
        if let Some(index) = query
            && let Some(timer) = &mut self.gpu_timer
        {
            timer.submitted(index);
            self.frame_timings.gpu_samples_dropped = timer.dropped;
        }
        self.frame_timings.encoding_submit = cpu_started.elapsed();
        let present_started = crate::timing::CpuClock::start(self.instrumentation.enabled());
        before_present();
        self.queue.present(surface_texture);
        self.frame_timings.present_call = present_started.elapsed();
        FrameOutcome::Presented
    }

    fn reconfigure(&self) {
        self.surface.configure(&self.device, &self.config);
    }
}

fn validate_size(size: SurfaceSize, maximum: u32) -> Result<(), RenderError> {
    if !size.is_zero() && (size.width > maximum || size.height > maximum) {
        return Err(RenderError::SurfaceSize {
            requested: size,
            maximum,
        });
    }
    Ok(())
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn unchanged_streams_coalesce_but_metadata_changes_and_failed_uploads_remain_pending() {
        use rustclient_scene::{ModelSortSource, SceneGeometry, SceneModel};
        let mut stream = StagedStream::default();
        let mut geometry = SceneGeometry::default();
        assert!(stream.stage(&geometry)); // Even empty input must clear an unknown GPU lifetime.
        stream.apply_pending(|_| Ok::<_, ()>(())).unwrap();
        assert!(!stream.stage(&geometry));
        geometry.vertices = vec![
            rustclient_scene::SceneVertex {
                position: [0; 4],
                abhsl: 0,
                texture_uv: [0; 4],
                draw_tile: rustclient_scene::DrawTile::default()
            };
            3
        ];
        geometry.opaque = vec![0, 1, 2];
        geometry.models.push(SceneModel {
            vertices: 0..3,
            depth_policy: rustclient_model::ModelDepthPolicy::DepthWrite,
            sort_source: None,
        });
        assert!(stream.stage(&geometry));
        assert!(!stream.stage(&geometry));
        assert!(stream.apply_pending(|_| Err(())).is_err());
        assert!(stream.pending);
        assert!(!stream.stage(&geometry));
        stream.apply_pending(|_| Ok::<_, ()>(())).unwrap();
        assert!(!stream.pending);
        geometry.vertices[1].abhsl = 123;
        assert!(stream.stage(&geometry));
        stream.apply_pending(|_| Ok::<_, ()>(())).unwrap();
        geometry.models[0].sort_source = Some(Arc::new(ModelSortSource {
            origin: rustclient_model::ModelPoint::new(1, 2, 3),
            vertices: Arc::from([rustclient_model::ModelPoint::new(1, 2, 3)]),
            radius: 0,
            diameter: 0,
        }));
        assert!(stream.stage(&geometry)); // Admission metadata matters even with unchanged vertices.
    }

    #[test]
    fn physical_targets_respect_the_negotiated_limit_before_creation() {
        assert!(validate_size(SurfaceSize::new(8192, 8192), 8192).is_ok());
        assert!(matches!(
            validate_size(SurfaceSize::new(8193, 1), 8192),
            Err(RenderError::SurfaceSize { maximum: 8192, .. })
        ));
        assert!(validate_size(SurfaceSize::new(1, 8193), 8192).is_err());
        assert!(validate_size(SurfaceSize::new(0, u32::MAX), 8192).is_ok());
    }

    #[test]
    fn zero_dimension_suspends_surface() {
        assert!(SurfaceSize::new(0, 720).is_zero());
        assert!(SurfaceSize::new(1280, 0).is_zero());
        assert!(!SurfaceSize::new(1280, 720).is_zero());
    }

    #[test]
    fn bootstrap_color_is_deterministic() {
        assert_eq!(ClearColor::BOOTSTRAP, ClearColor::BOOTSTRAP);
    }
}
