use crate::{GameplayCameraController, RenderError, SurfaceSize};
use glam::Vec3;
use rustclient_model::SceneAssetId;
use rustclient_scene::{SceneVertex, WorldMesh};

/// World WGSL group/binding map. The shader declares exactly this layout.
const WORLD_GROUP: u32 = 0;
/// Top level that draws every tile render level, for captures that have no player plane.
const ALL_LEVELS: u8 = 3;
const DEPTH: wgpu::TextureFormat = wgpu::TextureFormat::Depth32Float;
#[repr(C)]
#[derive(Clone, Copy, bytemuck::Pod, bytemuck::Zeroable)]
struct CameraUniform {
    view_projection: [[f32; 4]; 4],
    cycle: u32,
    /// Highest tile render level drawn this frame (see `rustclient_scene::RoofView`).
    top_level: u32,
    brightness: f32,
    padding: u32,
}
pub(crate) struct WorldGpu {
    pub asset: SceneAssetId,
    opaque_pipeline: wgpu::RenderPipeline,
    transparent_pipeline: wgpu::RenderPipeline,
    geometry: super::world_geometry::GeometryGpu,
    dynamic: super::world_geometry::GeometryGpu,
    /// Live players/NPCs; rewritten per client cycle without touching scenery buffers.
    entities: super::world_geometry::GeometryGpu,
    transparent_runs: Vec<super::transparency::TransparentRun>,
    camera: wgpu::Buffer,
    bind: wgpu::BindGroup,
    pub target: super::world_target::WorldTarget,
    surface_format: wgpu::TextureFormat,
    size: SurfaceSize,
    physical_viewport: [f32; 4],
    last_camera: Option<(
        GameplayCameraController,
        crate::CameraViewport,
        u32,
        u8,
        rustclient_model::ScreenBrightness,
    )>,
}
impl WorldGpu {
    #[expect(
        clippy::too_many_lines,
        reason = "GPU pipeline and immutable uploads are built together once per resource lifetime"
    )]
    #[expect(
        clippy::cast_precision_loss,
        reason = "physical viewport dimensions bounded by texture limits"
    )]
    pub fn new(
        device: &wgpu::Device,
        queue: &wgpu::Queue,
        format: wgpu::TextureFormat,
        size: SurfaceSize,
        mesh: &WorldMesh,
    ) -> Result<Self, RenderError> {
        let data: &[u8] = bytemuck::cast_slice(&mesh.geometry.vertices);
        if data.len() as u64 > device.limits().max_buffer_size {
            return Err(RenderError::World("mesh exceeds device buffer limit"));
        }
        let layout = device.create_bind_group_layout(&wgpu::BindGroupLayoutDescriptor {
            label: Some("world camera layout"),
            entries: &[
                wgpu::BindGroupLayoutEntry {
                    binding: 0,
                    visibility: wgpu::ShaderStages::VERTEX,
                    ty: wgpu::BindingType::Buffer {
                        ty: wgpu::BufferBindingType::Uniform,
                        has_dynamic_offset: false,
                        min_binding_size: wgpu::BufferSize::new(80),
                    },
                    count: None,
                },
                wgpu::BindGroupLayoutEntry {
                    binding: 1,
                    visibility: wgpu::ShaderStages::FRAGMENT,
                    ty: wgpu::BindingType::Texture {
                        sample_type: wgpu::TextureSampleType::Float { filterable: true },
                        view_dimension: wgpu::TextureViewDimension::D2Array,
                        multisampled: false,
                    },
                    count: None,
                },
                wgpu::BindGroupLayoutEntry {
                    binding: 2,
                    visibility: wgpu::ShaderStages::FRAGMENT,
                    ty: wgpu::BindingType::Sampler(wgpu::SamplerBindingType::Filtering),
                    count: None,
                },
                wgpu::BindGroupLayoutEntry {
                    binding: 3,
                    visibility: wgpu::ShaderStages::VERTEX,
                    ty: wgpu::BindingType::Buffer {
                        ty: wgpu::BufferBindingType::Uniform,
                        has_dynamic_offset: false,
                        min_binding_size: wgpu::BufferSize::new(4096),
                    },
                    count: None,
                },
            ],
        });
        let camera = device.create_buffer(&wgpu::BufferDescriptor {
            label: Some("world camera"),
            size: 80,
            usage: wgpu::BufferUsages::UNIFORM | wgpu::BufferUsages::COPY_DST,
            mapped_at_creation: false,
        });
        let (textures, sampler, animations) = crate::world_textures::upload(device, queue, mesh);
        let bind = device.create_bind_group(&wgpu::BindGroupDescriptor {
            label: Some("world camera binding"),
            layout: &layout,
            entries: &[
                wgpu::BindGroupEntry {
                    binding: 0,
                    resource: camera.as_entire_binding(),
                },
                wgpu::BindGroupEntry {
                    binding: 1,
                    resource: wgpu::BindingResource::TextureView(&textures),
                },
                wgpu::BindGroupEntry {
                    binding: 2,
                    resource: wgpu::BindingResource::Sampler(&sampler),
                },
                wgpu::BindGroupEntry {
                    binding: 3,
                    resource: animations.as_entire_binding(),
                },
            ],
        });
        let pipeline_layout = device.create_pipeline_layout(&wgpu::PipelineLayoutDescriptor {
            label: Some("world pipeline layout"),
            bind_group_layouts: &[Some(&layout)],
            immediate_size: 0,
        });
        let shader = device.create_shader_module(wgpu::ShaderModuleDescriptor {
            label: Some("world shader"),
            source: wgpu::ShaderSource::Wgsl(include_str!("shaders/world.wgsl").into()),
        });
        let opaque_pipeline = pipeline(
            device,
            super::world_target::FORMAT,
            &pipeline_layout,
            &shader,
            false,
        );
        let transparent_pipeline = pipeline(
            device,
            super::world_target::FORMAT,
            &pipeline_layout,
            &shader,
            true,
        );
        let geometry = super::world_geometry::GeometryGpu::new(device, &mesh.geometry)?;
        let dynamic = super::world_geometry::GeometryGpu::new(device, &mesh.dynamic)?;
        let entities = super::world_geometry::GeometryGpu::new(
            device,
            &rustclient_scene::SceneGeometry::default(),
        )?;
        let target = super::world_target::WorldTarget::new(device, size, format);
        Ok(Self {
            asset: mesh.asset,
            opaque_pipeline,
            transparent_pipeline,
            geometry,
            dynamic,
            entities,
            camera,
            bind,
            target,
            surface_format: format,
            size,
            physical_viewport: [0., 0., size.width as f32, size.height as f32],
            last_camera: None,
            transparent_runs: Vec::new(),
        })
    }
    pub fn update_dynamic(
        &mut self,
        device: &wgpu::Device,
        queue: &wgpu::Queue,
        geometry: &rustclient_scene::SceneGeometry,
    ) -> Result<(), RenderError> {
        self.dynamic.update(device, queue, geometry)?;
        self.last_camera = None;
        Ok(())
    }
    pub fn update_entities(
        &mut self,
        device: &wgpu::Device,
        queue: &wgpu::Queue,
        geometry: &rustclient_scene::SceneGeometry,
    ) -> Result<(), RenderError> {
        self.entities.update(device, queue, geometry)?;
        self.last_camera = None;
        Ok(())
    }
    pub fn resize(&mut self, device: &wgpu::Device, size: SurfaceSize) {
        if self.size != size && !size.is_zero() {
            self.target = super::world_target::WorldTarget::new(device, size, self.surface_format);
            self.size = size;
            self.last_camera = None;
        }
    }
    pub fn prepare(
        &mut self,
        queue: &wgpu::Queue,
        camera: GameplayCameraController,
        scale: f64,
        cycle: u32,
    ) {
        self.prepare_viewport(
            queue,
            camera,
            scale,
            cycle,
            None,
            rustclient_model::ViewportSettings::default(),
            ALL_LEVELS,
            rustclient_model::ScreenBrightness::default(),
        );
    }
    #[expect(
        clippy::cast_precision_loss,
        clippy::cast_possible_truncation,
        reason = "bounded logical/physical viewport values converted for GPU projection"
    )]
    #[expect(
        clippy::too_many_arguments,
        reason = "explicit world viewport, material cycle and roof-level inputs"
    )]
    pub fn prepare_viewport(
        &mut self,
        queue: &wgpu::Queue,
        mut camera: GameplayCameraController,
        scale: f64,
        cycle: u32,
        bounds: Option<rustclient_game_ui::Rect>,
        settings: rustclient_model::ViewportSettings,
        top_level: u8,
        brightness: rustclient_model::ScreenBrightness,
    ) {
        camera.set_zoom(i32::from(settings.zoom[0]), i32::from(settings.zoom[1]));
        let logical = bounds.unwrap_or(rustclient_game_ui::Rect::new(
            0.,
            0.,
            (f64::from(self.size.width) / scale) as f32,
            (f64::from(self.size.height) / scale) as f32,
        ));
        let viewport = crate::CameraViewport::new(logical.width as i32, logical.height as i32)
            .with_settings(settings);
        let inset = viewport.inset();
        let size = viewport.size();
        self.physical_viewport = [
            ((logical.x + inset[0] as f32) * scale as f32).max(0.),
            ((logical.y + inset[1] as f32) * scale as f32).max(0.),
            (size[0] as f32 * scale as f32).min(self.size.width as f32),
            (size[1] as f32 * scale as f32).min(self.size.height as f32),
        ];
        self.physical_viewport[2] =
            self.physical_viewport[2].min(self.size.width as f32 - self.physical_viewport[0]);
        self.physical_viewport[3] =
            self.physical_viewport[3].min(self.size.height as f32 - self.physical_viewport[1]);
        if self.last_camera == Some((camera, viewport, cycle, top_level, brightness)) {
            return;
        }
        let pose = camera.pose(viewport);
        let uniform = CameraUniform {
            view_projection: camera.matrix(viewport).to_cols_array_2d(),
            cycle,
            top_level: u32::from(top_level),
            brightness: 0.5 + f32::from(100 - brightness.percent()) / 200.,
            padding: 0,
        };
        queue.write_buffer(&self.camera, 0, bytemuck::bytes_of(&uniform));
        let eye = Vec3::from_array(pose.position.map(|v| v as f32));
        let yaw = f32::from(pose.yaw) * std::f32::consts::TAU / 2048.;
        let pitch = f32::from(pose.pitch) * std::f32::consts::TAU / 2048.;
        let direction = Vec3::new(
            -yaw.sin() * pitch.cos(),
            pitch.sin(),
            yaw.cos() * pitch.cos(),
        );
        self.geometry.sort(queue, eye, direction);
        self.dynamic.sort(queue, eye, direction);
        self.entities.sort(queue, eye, direction);
        let sources = [&self.geometry, &self.dynamic, &self.entities];
        super::transparency::merge(
            |source, index| sources[source].alpha_depth(index, eye, direction),
            &mut self.transparent_runs,
        );
        self.last_camera = Some((camera, viewport, cycle, top_level, brightness));
    }
    pub fn render_scene(&self, encoder: &mut wgpu::CommandEncoder) {
        let mut pass = encoder.begin_render_pass(&wgpu::RenderPassDescriptor {
            label: Some("classic reference world"),
            color_attachments: &[Some(wgpu::RenderPassColorAttachment {
                view: &self.target.multisampled,
                depth_slice: None,
                resolve_target: Some(&self.target.resolved),
                ops: wgpu::Operations {
                    load: wgpu::LoadOp::Clear(wgpu::Color::BLACK),
                    store: wgpu::StoreOp::Discard,
                },
            })],
            depth_stencil_attachment: Some(wgpu::RenderPassDepthStencilAttachment {
                view: &self.target.depth,
                depth_ops: Some(wgpu::Operations {
                    load: wgpu::LoadOp::Clear(0.),
                    store: wgpu::StoreOp::Discard,
                }),
                stencil_ops: None,
            }),
            timestamp_writes: None,
            occlusion_query_set: None,
            multiview_mask: None,
        });
        self.draw(&mut pass);
    }
    pub fn draw(&self, pass: &mut wgpu::RenderPass<'_>) {
        let [x, y, width, height] = self.physical_viewport;
        if width <= 0. || height <= 0. {
            return;
        }
        pass.set_viewport(x, y, width, height, 0., 1.);
        pass.set_bind_group(WORLD_GROUP, &self.bind, &[]);
        pass.set_pipeline(&self.opaque_pipeline);
        self.geometry.draw(pass, false);
        self.dynamic.draw(pass, false);
        self.entities.draw(pass, false);
        pass.set_pipeline(&self.transparent_pipeline);
        let sources = [&self.geometry, &self.dynamic, &self.entities];
        for run in &self.transparent_runs {
            sources[run.source].draw_alpha(pass, run.indices.clone());
        }
    }
}
fn pipeline(
    device: &wgpu::Device,
    format: wgpu::TextureFormat,
    layout: &wgpu::PipelineLayout,
    shader: &wgpu::ShaderModule,
    transparent: bool,
) -> wgpu::RenderPipeline {
    device.create_render_pipeline(&wgpu::RenderPipelineDescriptor {
        label: Some(if transparent {
            "transparent world pipeline"
        } else {
            "opaque world pipeline"
        }),
        layout: Some(layout),
        vertex: wgpu::VertexState {
            module: shader,
            entry_point: Some("vs_main"),
            compilation_options: wgpu::PipelineCompilationOptions::default(),
            buffers: &[Some(wgpu::VertexBufferLayout {
                array_stride: std::mem::size_of::<SceneVertex>() as u64,
                step_mode: wgpu::VertexStepMode::Vertex,
                attributes: &wgpu::vertex_attr_array![0=>Sint16x4,1=>Uint32,2=>Uint16x4],
            })],
        },
        primitive: wgpu::PrimitiveState {
            cull_mode: Some(wgpu::Face::Back),
            ..Default::default()
        },
        depth_stencil: Some(wgpu::DepthStencilState {
            format: DEPTH,
            depth_write_enabled: Some(!transparent),
            depth_compare: Some(wgpu::CompareFunction::Greater),
            stencil: wgpu::StencilState::default(),
            bias: wgpu::DepthBiasState::default(),
        }),
        multisample: wgpu::MultisampleState {
            count: super::world_target::SAMPLES,
            ..Default::default()
        },
        fragment: Some(wgpu::FragmentState {
            module: shader,
            entry_point: Some("fs_main"),
            compilation_options: wgpu::PipelineCompilationOptions::default(),
            targets: &[Some(wgpu::ColorTargetState {
                format,
                blend: transparent.then_some(wgpu::BlendState {
                    color: wgpu::BlendComponent {
                        src_factor: wgpu::BlendFactor::SrcAlpha,
                        dst_factor: wgpu::BlendFactor::OneMinusSrcAlpha,
                        operation: wgpu::BlendOperation::Add,
                    },
                    alpha: wgpu::BlendComponent {
                        src_factor: wgpu::BlendFactor::One,
                        dst_factor: wgpu::BlendFactor::One,
                        operation: wgpu::BlendOperation::Add,
                    },
                }),
                write_mask: wgpu::ColorWrites::ALL,
            })],
        }),
        multiview_mask: None,
        cache: None,
    })
}
