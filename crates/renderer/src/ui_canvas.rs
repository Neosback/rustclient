//! Encoded game-canvas raster and GPU scaling, independent from shell chrome/input semantics.
use crate::{GpuSettings, RenderError, shader_sources, ui::UiGpu, world::WorldGpu};
use rustclient_game_ui::{TitleViewport, UiFrame};
use rustclient_model::SceneAssetId;

pub(crate) const FORMAT: wgpu::TextureFormat = wgpu::TextureFormat::Rgba8Unorm;
const UNIFORM_BYTES: u64 = 64;
const GROUP: u32 = 0;
#[repr(C)]
#[derive(Clone, Copy, PartialEq, bytemuck::Pod, bytemuck::Zeroable)]
struct CanvasUniform {
    source: [u32; 2],
    mode: u32,
    color_mode: u32,
    rect: [f32; 4],
    params: [f32; 4],
    overlay: [f32; 4],
}

pub(crate) struct UiCanvas {
    texture: wgpu::Texture,
    view: wgpu::TextureView,
    blank: wgpu::TextureView,
    size: [u32; 2],
    extent: [u32; 2],
    world_key: Option<(SceneAssetId, u64)>,
    uniform: wgpu::Buffer,
    last_uniform: Option<CanvasUniform>,
    layout: wgpu::BindGroupLayout,
    bind: wgpu::BindGroup,
    pipeline: wgpu::RenderPipeline,
    srgb_output: bool,
}
impl UiCanvas {
    pub fn new(device: &wgpu::Device, output: wgpu::TextureFormat) -> Self {
        let layout = device.create_bind_group_layout(&wgpu::BindGroupLayoutDescriptor {
            label: Some("game canvas scaling layout"),
            entries: &[
                wgpu::BindGroupLayoutEntry {
                    binding: 0,
                    visibility: wgpu::ShaderStages::FRAGMENT,
                    ty: wgpu::BindingType::Buffer {
                        ty: wgpu::BufferBindingType::Uniform,
                        has_dynamic_offset: false,
                        min_binding_size: wgpu::BufferSize::new(UNIFORM_BYTES),
                    },
                    count: None,
                },
                image_layout(1),
                image_layout(2),
            ],
        });
        let uniform = device.create_buffer(&wgpu::BufferDescriptor {
            label: Some("game canvas scaling parameters"),
            size: UNIFORM_BYTES,
            usage: wgpu::BufferUsages::UNIFORM | wgpu::BufferUsages::COPY_DST,
            mapped_at_creation: false,
        });
        let texture = create_texture(device, [1, 1]);
        let view = texture.create_view(&wgpu::TextureViewDescriptor::default());
        let blank =
            create_texture(device, [1, 1]).create_view(&wgpu::TextureViewDescriptor::default());
        let bind = bindings(device, &layout, &uniform, &view, &blank);
        let shader = device.create_shader_module(wgpu::ShaderModuleDescriptor {
            label: Some("RuneLite GPU canvas scaling"),
            source: wgpu::ShaderSource::Wgsl(shader_sources::ui_canvas().into()),
        });
        let pipeline_layout = device.create_pipeline_layout(&wgpu::PipelineLayoutDescriptor {
            label: Some("game canvas scaling pipeline layout"),
            bind_group_layouts: &[Some(&layout)],
            immediate_size: 0,
        });
        let pipeline = device.create_render_pipeline(&wgpu::RenderPipelineDescriptor {
            label: Some("game canvas scale and display conversion"),
            layout: Some(&pipeline_layout),
            vertex: wgpu::VertexState {
                module: &shader,
                entry_point: Some("vs_main"),
                compilation_options: wgpu::PipelineCompilationOptions::default(),
                buffers: &[],
            },
            fragment: Some(wgpu::FragmentState {
                module: &shader,
                entry_point: Some("fs_main"),
                compilation_options: wgpu::PipelineCompilationOptions::default(),
                targets: &[Some(wgpu::ColorTargetState {
                    format: output,
                    blend: None,
                    write_mask: wgpu::ColorWrites::ALL,
                })],
            }),
            primitive: wgpu::PrimitiveState::default(),
            depth_stencil: None,
            multisample: wgpu::MultisampleState::default(),
            multiview_mask: None,
            cache: None,
        });
        Self {
            texture,
            view,
            blank,
            size: [1, 1],
            extent: [1, 1],
            world_key: None,
            uniform,
            last_uniform: None,
            layout,
            bind,
            pipeline,
            srgb_output: output.is_srgb(),
        }
    }

    #[expect(
        clippy::cast_precision_loss,
        reason = "bounded color correction intensity"
    )]
    pub fn prepare(
        &mut self,
        device: &wgpu::Device,
        queue: &wgpu::Queue,
        frame: &UiFrame,
        viewport: TitleViewport,
        settings: GpuSettings,
        world: Option<&WorldGpu>,
    ) -> Result<TitleViewport, RenderError> {
        let size = frame
            .canvas
            .map_or([765, 503], |canvas| [canvas.width, canvas.height]);
        let rect = canvas_rect(size, viewport, device.limits().max_texture_dimension_2d)?;
        let resized = size != self.size;
        if resized {
            self.texture = create_texture(device, size);
            self.view = self
                .texture
                .create_view(&wgpu::TextureViewDescriptor::default());
            self.size = size;
        }
        let key = world.map(|world| (world.asset, world.target_generation));
        if resized || self.world_key != key {
            self.bind = bindings(
                device,
                &self.layout,
                &self.uniform,
                &self.view,
                world.map_or(&self.blank, |world| &world.target.resolved),
            );
            self.world_key = key;
        }
        self.extent = [viewport.physical_width, viewport.physical_height];
        let uniform = CanvasUniform {
            source: size,
            mode: settings.ui_scaling as u32,
            color_mode: settings.color_blind.shader_mode(),
            rect,
            params: [
                settings.color_blind_intensity.min(100) as f32,
                f32::from(u8::from(world.is_some())),
                f32::from(u8::from(self.srgb_output)),
                0.,
            ],
            overlay: frame.viewport_overlay.rgba().map(|v| f32::from(v) / 255.),
        };
        if self.last_uniform != Some(uniform) {
            queue.write_buffer(&self.uniform, 0, bytemuck::bytes_of(&uniform));
            self.last_uniform = Some(uniform);
        }
        Ok(TitleViewport {
            canvas_width: size[0],
            physical_width: size[0],
            physical_height: size[1],
            scale: 1.,
            stretch: None,
        })
    }
    pub fn upload_fixture(&self, queue: &wgpu::Queue, image: &rustclient_cache::Image) {
        let mut pixels = image.pixels().to_vec();
        for pixel in pixels.as_chunks_mut::<4>().0 {
            for channel in 0..3 {
                pixel[channel] =
                    u8::try_from((u32::from(pixel[channel]) * u32::from(pixel[3])) / 255)
                        .expect("premultiplied byte channel");
            }
        }
        queue.write_texture(
            wgpu::TexelCopyTextureInfo {
                texture: &self.texture,
                mip_level: 0,
                origin: wgpu::Origin3d::ZERO,
                aspect: wgpu::TextureAspect::All,
            },
            &pixels,
            wgpu::TexelCopyBufferLayout {
                offset: 0,
                bytes_per_row: Some(self.size[0] * 4),
                rows_per_image: Some(self.size[1]),
            },
            wgpu::Extent3d {
                width: self.size[0],
                height: self.size[1],
                depth_or_array_layers: 1,
            },
        );
    }
    pub fn invalidate_world(&mut self) {
        self.world_key = None;
    }
    pub fn render(&self, encoder: &mut wgpu::CommandEncoder, ui: &UiGpu) {
        let mut pass = encoder.begin_render_pass(&wgpu::RenderPassDescriptor {
            label: Some("logical game canvas"),
            color_attachments: &[Some(wgpu::RenderPassColorAttachment {
                view: &self.view,
                depth_slice: None,
                resolve_target: None,
                ops: wgpu::Operations {
                    load: wgpu::LoadOp::Clear(wgpu::Color::TRANSPARENT),
                    store: wgpu::StoreOp::Store,
                },
            })],
            depth_stencil_attachment: None,
            timestamp_writes: None,
            occlusion_query_set: None,
            multiview_mask: None,
        });
        ui.draw(&mut pass);
    }
    #[expect(
        clippy::cast_precision_loss,
        reason = "validated framebuffer dimensions"
    )]
    pub fn draw(&self, pass: &mut wgpu::RenderPass<'_>) {
        pass.set_viewport(0., 0., self.extent[0] as f32, self.extent[1] as f32, 0., 1.);
        pass.set_scissor_rect(0, 0, self.extent[0], self.extent[1]);
        pass.set_pipeline(&self.pipeline);
        pass.set_bind_group(GROUP, &self.bind, &[]);
        pass.draw(0..3, 0..1);
    }
}
fn image_layout(binding: u32) -> wgpu::BindGroupLayoutEntry {
    wgpu::BindGroupLayoutEntry {
        binding,
        visibility: wgpu::ShaderStages::FRAGMENT,
        ty: wgpu::BindingType::Texture {
            sample_type: wgpu::TextureSampleType::Float { filterable: false },
            view_dimension: wgpu::TextureViewDimension::D2,
            multisampled: false,
        },
        count: None,
    }
}
fn create_texture(device: &wgpu::Device, size: [u32; 2]) -> wgpu::Texture {
    device.create_texture(&wgpu::TextureDescriptor {
        label: Some("encoded logical game canvas"),
        size: wgpu::Extent3d {
            width: size[0],
            height: size[1],
            depth_or_array_layers: 1,
        },
        mip_level_count: 1,
        sample_count: 1,
        dimension: wgpu::TextureDimension::D2,
        format: FORMAT,
        usage: wgpu::TextureUsages::RENDER_ATTACHMENT
            | wgpu::TextureUsages::TEXTURE_BINDING
            | wgpu::TextureUsages::COPY_DST,
        view_formats: &[],
    })
}
fn bindings(
    device: &wgpu::Device,
    layout: &wgpu::BindGroupLayout,
    uniform: &wgpu::Buffer,
    canvas: &wgpu::TextureView,
    world: &wgpu::TextureView,
) -> wgpu::BindGroup {
    device.create_bind_group(&wgpu::BindGroupDescriptor {
        label: Some("canvas + world display binding"),
        layout,
        entries: &[
            wgpu::BindGroupEntry {
                binding: 0,
                resource: uniform.as_entire_binding(),
            },
            wgpu::BindGroupEntry {
                binding: 1,
                resource: wgpu::BindingResource::TextureView(canvas),
            },
            wgpu::BindGroupEntry {
                binding: 2,
                resource: wgpu::BindingResource::TextureView(world),
            },
        ],
    })
}
/// The compositor and input both derive from `TitleViewport`; source raster stays 1:1.
#[expect(
    clippy::cast_precision_loss,
    clippy::cast_possible_truncation,
    reason = "validated bounded canvas coordinates"
)]
fn canvas_rect(
    size: [u32; 2],
    viewport: TitleViewport,
    maximum: u32,
) -> Result<[f32; 4], RenderError> {
    let (sx, sy) = viewport.scales();
    let rect = [
        viewport.physical_origin_x() as f32,
        0.,
        size[0] as f32 * sx as f32,
        size[1] as f32 * sy as f32,
    ];
    if size.contains(&0)
        || size.iter().any(|&side| side > maximum)
        || rect.iter().any(|value| !value.is_finite())
        || rect[2] <= 0.
        || rect[3] <= 0.
    {
        return Err(RenderError::Ui("invalid canvas scaling dimensions"));
    }
    Ok(rect)
}
#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn all_scaling_shaders_validate_with_portable_capabilities_and_uniform_layout() {
        let source = shader_sources::ui_canvas();
        let module = naga::front::wgsl::parse_str(&source)
            .unwrap_or_else(|e| panic!("{}", e.emit_to_string(&source)));
        naga::valid::Validator::new(
            naga::valid::ValidationFlags::all(),
            naga::valid::Capabilities::empty(),
        )
        .validate(&module)
        .expect("portable canvas shaders");
        let (_, block) = module
            .types
            .iter()
            .find(|(_, ty)| ty.name.as_deref() == Some("CanvasUniform"))
            .unwrap();
        let naga::TypeInner::Struct { span, .. } = block.inner else {
            panic!("uniform struct");
        };
        assert_eq!(u64::from(span), UNIFORM_BYTES);
        assert_eq!(size_of::<CanvasUniform>() as u64, UNIFORM_BYTES);
    }
}

#[cfg(test)]
mod viewport_tests {
    use super::*;
    #[test]
    #[expect(clippy::cast_precision_loss, reason = "small bounded test canvas")]
    fn compositor_and_input_share_fixed_resizable_hidpi_and_stretched_coordinates() {
        for (size, viewport) in [
            (
                [765, 503],
                TitleViewport {
                    canvas_width: 765,
                    physical_width: 765,
                    physical_height: 503,
                    scale: 1.,
                    stretch: None,
                },
            ),
            (
                [765, 503],
                TitleViewport {
                    canvas_width: 765,
                    physical_width: 1530,
                    physical_height: 1006,
                    scale: 2.,
                    stretch: None,
                },
            ),
            (
                [1100, 700],
                TitleViewport {
                    canvas_width: 1100,
                    physical_width: 2200,
                    physical_height: 1400,
                    scale: 2.,
                    stretch: None,
                },
            ),
            (
                [765, 503],
                TitleViewport {
                    canvas_width: 765,
                    physical_width: 1500,
                    physical_height: 900,
                    scale: 2.,
                    stretch: Some(rustclient_game_ui::StretchedCanvas {
                        canvas_height: 503,
                        x: 50.,
                        width: 1400.,
                        height: 900.,
                    }),
                },
            ),
        ] {
            let rect = canvas_rect(size, viewport, 8192).unwrap();
            // A logical point rendered through this rect round-trips through game hit-testing.
            let point = [321_f32, 211_f32];
            let physical = [
                rect[0] + point[0] * rect[2] / size[0] as f32,
                point[1] * rect[3] / size[1] as f32,
            ];
            let actual = viewport.pointer(f64::from(physical[0]), f64::from(physical[1]));
            assert!((actual.0 - point[0]).abs() < 0.001);
            assert!((actual.1 - point[1]).abs() < 0.001);
        }
    }
    #[test]
    fn invalid_canvas_is_rejected_before_texture_creation() {
        let mut viewport = TitleViewport {
            canvas_width: 765,
            physical_width: 765,
            physical_height: 503,
            scale: 1.,
            stretch: None,
        };
        assert!(canvas_rect([0, 503], viewport, 8192).is_err());
        assert!(canvas_rect([8193, 503], viewport, 8192).is_err());
        viewport.scale = f64::NAN;
        assert!(canvas_rect([765, 503], viewport, 8192).is_err());
        viewport.scale = 0.;
        assert!(canvas_rect([765, 503], viewport, 8192).is_err());
    }
}
