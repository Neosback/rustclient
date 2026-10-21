use core::fmt;

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
}

impl fmt::Display for RenderError {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        match self {
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
}

impl SurfaceRenderer {
    /// Initialize one renderer from any target accepted by `wgpu`.
    pub async fn new<T>(target: T, size: SurfaceSize) -> Result<Self, RendererInitError>
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
        let (device, queue) = adapter
            .request_device(&wgpu::DeviceDescriptor::default())
            .await
            .map_err(|error| RendererInitError::Device(error.to_string().into()))?;

        let capabilities = surface.get_capabilities(&adapter);
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
            present_mode: wgpu::PresentMode::AutoVsync,
        };

        let mut renderer = Self {
            _instance: instance,
            surface,
            device,
            queue,
            config,
            view_format,
            configured: false,
        };
        renderer.resize(size);
        Ok(renderer)
    }

    /// Reconfigure for a new physical size. Zero-sized surfaces are suspended.
    pub fn resize(&mut self, size: SurfaceSize) {
        if size.is_zero() {
            self.configured = false;
            return;
        }

        self.config.width = size.width;
        self.config.height = size.height;
        self.surface.configure(&self.device, &self.config);
        self.configured = true;
    }

    /// Render and present a deterministic clear frame.
    ///
    /// `before_present` lets a window-system adapter perform its required
    /// pre-present notification without making the renderer depend on that
    /// windowing library.
    pub fn render_clear<F>(
        &mut self,
        color: ClearColor,
        before_present: F,
    ) -> Result<FrameOutcome, RenderError>
    where
        F: FnOnce(),
    {
        if !self.configured {
            return Ok(FrameOutcome::Suspended);
        }

        let surface_texture = match self.surface.get_current_texture() {
            wgpu::CurrentSurfaceTexture::Success(texture) => texture,
            wgpu::CurrentSurfaceTexture::Occluded | wgpu::CurrentSurfaceTexture::Timeout => {
                return Ok(FrameOutcome::Skipped);
            }
            wgpu::CurrentSurfaceTexture::Suboptimal(texture) => {
                drop(texture);
                self.reconfigure();
                return Ok(FrameOutcome::Reconfigured);
            }
            wgpu::CurrentSurfaceTexture::Outdated => {
                self.reconfigure();
                return Ok(FrameOutcome::Reconfigured);
            }
            wgpu::CurrentSurfaceTexture::Lost => return Ok(FrameOutcome::RecreateRequired),
            wgpu::CurrentSurfaceTexture::Validation => return Err(RenderError::Validation),
        };

        let texture_view = surface_texture
            .texture
            .create_view(&wgpu::TextureViewDescriptor {
                format: Some(self.view_format),
                ..Default::default()
            });
        let mut encoder = self
            .device
            .create_command_encoder(&wgpu::CommandEncoderDescriptor {
                label: Some("rustclient bootstrap clear encoder"),
            });
        {
            let _render_pass = encoder.begin_render_pass(&wgpu::RenderPassDescriptor {
                label: Some("rustclient bootstrap clear pass"),
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
        }

        self.queue.submit([encoder.finish()]);
        before_present();
        self.queue.present(surface_texture);
        Ok(FrameOutcome::Presented)
    }

    fn reconfigure(&self) {
        self.surface.configure(&self.device, &self.config);
    }
}

#[cfg(test)]
mod tests {
    use super::*;

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
