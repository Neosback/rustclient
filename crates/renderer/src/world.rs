use crate::{GameplayCameraController, RenderError, SurfaceSize};
use glam::Vec3;
use rustclient_model::SceneAssetId;
use rustclient_scene::{SceneVisibility, WorldMesh};

/// World WGSL group/binding map. The shader declares exactly this layout.
const WORLD_GROUP: u32 = 0;
#[repr(C)]
#[derive(Clone, Copy, bytemuck::Pod, bytemuck::Zeroable)]
struct CameraUniform {
    view_projection: [[f32; 4]; 4],
    cycle: u32,
    /// Highest tile render level drawn this frame (see `rustclient_scene::RoofView`).
    top_level: u32,
    brightness: f32,
    material_flags: u32,
    /// x: fog depth in tiles (0 disables), y: draw distance in local units, z/w: eye x/z.
    fog: [f32; 4],
    fog_color: [f32; 4],
    /// x: color-blind mode (0 off), y: intensity percent.
    color: [f32; 4],
    /// Hidden roof slots, bit `slot % 32` of word `slot / 32` (`rustclient_scene::RoofMask`).
    hidden_roofs: [u32; 128],
    zones: [i32; 4],
    admission: [u32; 4],
}
const CAMERA_BYTES: u64 = 672;
/// Local units per tile, `Perspective.LOCAL_TILE_SIZE`.
const LOCAL_TILE_SIZE: f32 = 128.;
/// Resident geometry resources and CPU ordering work; excludes textures and render targets.
#[derive(Debug, Default, Clone, Copy, PartialEq, Eq)]
pub struct WorldGeometryStats {
    pub gpu_buffer_bytes: u64,
    pub expanded_vertex_bytes: u64,
    pub resident_vertex_bytes: u64,
    pub cpu_remap_bytes: u64,
    pub ordering_runs: u64,
    pub ordering_reuses: u64,
}
pub(crate) struct WorldGpu {
    pub asset: SceneAssetId,
    pipelines: super::world_pipeline::WorldPipelines,
    geometry: super::world_geometry::GeometryGpu,
    dynamic: super::world_geometry::GeometryGpu,
    /// Live players/NPCs; rewritten per client cycle without touching scenery buffers.
    entities: super::world_geometry::GeometryGpu,
    transparent_runs: Vec<super::transparency::TransparentRun>,
    camera: wgpu::Buffer,
    bind: wgpu::BindGroup,
    bind_layout: wgpu::BindGroupLayout,
    pipeline_layout: wgpu::PipelineLayout,
    shader: wgpu::ShaderModule,
    textures: wgpu::TextureView,
    animations: wgpu::Buffer,
    textures_fingerprint: u64,
    anisotropy: u16,
    mipmaps: bool,
    pub target: super::world_target::WorldTarget,
    pub target_generation: u64,
    surface_format: wgpu::TextureFormat,
    size: SurfaceSize,
    physical_viewport: [f32; 4],
    last_camera: Option<(
        GameplayCameraController,
        crate::CameraViewport,
        u32,
        SceneVisibility,
        rustclient_model::ScreenBrightness,
        crate::GpuSettings,
    )>,
}
impl WorldGpu {
    pub fn geometry_stats(&self) -> WorldGeometryStats {
        let mut sum = WorldGeometryStats::default();
        for source in [&self.geometry, &self.dynamic, &self.entities] {
            let stats = source.stats();
            sum.gpu_buffer_bytes += stats.gpu_buffer_bytes;
            sum.expanded_vertex_bytes += stats.expanded_vertex_bytes;
            sum.resident_vertex_bytes += stats.resident_vertex_bytes;
            sum.cpu_remap_bytes += stats.cpu_remap_bytes;
            sum.ordering_runs += stats.ordering_runs;
            sum.ordering_reuses += stats.ordering_reuses;
        }
        sum
    }
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
        samples: u32,
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
                    visibility: wgpu::ShaderStages::VERTEX_FRAGMENT,
                    ty: wgpu::BindingType::Buffer {
                        ty: wgpu::BufferBindingType::Uniform,
                        has_dynamic_offset: false,
                        min_binding_size: wgpu::BufferSize::new(CAMERA_BYTES),
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
            size: CAMERA_BYTES,
            usage: wgpu::BufferUsages::UNIFORM | wgpu::BufferUsages::COPY_DST,
            mapped_at_creation: false,
        });
        let (textures, animations) = crate::world_textures::upload(device, queue, mesh)?;
        let textures_fingerprint = crate::world_textures::fingerprint(&mesh.textures);
        let bind = bind_group(device, &layout, &camera, &textures, 1, true, &animations);
        let pipeline_layout = device.create_pipeline_layout(&wgpu::PipelineLayoutDescriptor {
            label: Some("world pipeline layout"),
            bind_group_layouts: &[Some(&layout)],
            immediate_size: 0,
        });
        let shader = device.create_shader_module(wgpu::ShaderModuleDescriptor {
            label: Some("world shader"),
            source: wgpu::ShaderSource::Wgsl(crate::shader_sources::world().into()),
        });
        let pipelines =
            super::world_pipeline::WorldPipelines::new(device, &pipeline_layout, &shader, samples);
        let geometry = super::world_geometry::GeometryGpu::new_static(device, &mesh.geometry)?;
        let dynamic = super::world_geometry::GeometryGpu::new(device, &mesh.dynamic)?;
        let entities = super::world_geometry::GeometryGpu::new(
            device,
            &rustclient_scene::SceneGeometry::default(),
        )?;
        let target = super::world_target::WorldTarget::new(device, size, format, samples);
        Ok(Self {
            asset: mesh.asset,
            pipelines,
            geometry,
            dynamic,
            entities,
            camera,
            bind,
            bind_layout: layout,
            pipeline_layout,
            shader,
            textures,
            animations,
            textures_fingerprint,
            anisotropy: 1,
            mipmaps: true,
            target,
            target_generation: 1,
            surface_format: format,
            size,
            physical_viewport: [0., 0., size.width as f32, size.height as f32],
            last_camera: None,
            transparent_runs: Vec::new(),
        })
    }
    /// Replace the scene geometry in place while retaining compiled shaders, pipelines,
    /// render targets, and unchanged texture arrays.
    pub fn replace_mesh(
        &mut self,
        device: &wgpu::Device,
        queue: &wgpu::Queue,
        mesh: &WorldMesh,
    ) -> Result<(), RenderError> {
        let data: &[u8] = bytemuck::cast_slice(&mesh.geometry.vertices);
        if data.len() as u64 > device.limits().max_buffer_size {
            return Err(RenderError::World("mesh exceeds device buffer limit"));
        }
        crate::world_textures::validate(mesh, &device.limits())?;
        let next_textures_fingerprint = crate::world_textures::fingerprint(&mesh.textures);
        let uploaded_textures = if next_textures_fingerprint == self.textures_fingerprint {
            None
        } else {
            Some(crate::world_textures::upload(device, queue, mesh)?)
        };
        let geometry = super::world_geometry::GeometryGpu::new_static(device, &mesh.geometry)?;
        let dynamic = super::world_geometry::GeometryGpu::new(device, &mesh.dynamic)?;
        let entities = super::world_geometry::GeometryGpu::new(
            device,
            &rustclient_scene::SceneGeometry::default(),
        )?;
        if let Some((textures, animations)) = uploaded_textures {
            self.bind = bind_group(
                device,
                &self.bind_layout,
                &self.camera,
                &textures,
                self.anisotropy,
                self.mipmaps,
                &animations,
            );
            self.textures = textures;
            self.animations = animations;
            self.textures_fingerprint = next_textures_fingerprint;
        }
        self.geometry = geometry;
        self.dynamic = dynamic;
        self.entities = entities;
        self.asset = mesh.asset;
        self.last_camera = None;
        self.transparent_runs.clear();
        Ok(())
    }
    /// Rebuild only the sample-count dependent resources: all pipelines and the render target.
    pub fn set_samples(&mut self, device: &wgpu::Device, samples: u32) {
        if self.target.samples == samples {
            return;
        }
        self.pipelines = super::world_pipeline::WorldPipelines::new(
            device,
            &self.pipeline_layout,
            &self.shader,
            samples,
        );
        self.target =
            super::world_target::WorldTarget::new(device, self.size, self.surface_format, samples);
        self.target_generation = self.target_generation.wrapping_add(1);
        self.last_camera = None;
    }
    /// Replace only the texture sampler (and the bind group that references it).
    pub fn set_filtering(&mut self, device: &wgpu::Device, anisotropy: u16, mipmaps: bool) {
        if self.anisotropy == anisotropy && self.mipmaps == mipmaps {
            return;
        }
        self.bind = bind_group(
            device,
            &self.bind_layout,
            &self.camera,
            &self.textures,
            anisotropy,
            mipmaps,
            &self.animations,
        );
        self.anisotropy = anisotropy;
        self.mipmaps = mipmaps;
        self.last_camera = None;
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
            self.target = super::world_target::WorldTarget::new(
                device,
                size,
                self.surface_format,
                self.target.samples,
            );
            self.size = size;
            self.target_generation = self.target_generation.wrapping_add(1);
            self.last_camera = None;
        }
    }
    pub fn prepare(
        &mut self,
        queue: &wgpu::Queue,
        camera: GameplayCameraController,
        scale: f64,
        cycle: u32,
        gpu: crate::GpuSettings,
    ) {
        self.prepare_viewport(
            queue,
            camera,
            rustclient_game_ui::TitleViewport {
                canvas_width: 765,
                physical_width: self.size.width,
                physical_height: self.size.height,
                scale,
                stretch: None,
            },
            cycle,
            None,
            rustclient_model::ViewportSettings::default(),
            &SceneVisibility::everything(),
            rustclient_model::ScreenBrightness::default(),
            gpu,
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
        transform: rustclient_game_ui::TitleViewport,
        cycle: u32,
        bounds: Option<rustclient_game_ui::Rect>,
        settings: rustclient_model::ViewportSettings,
        visibility: &SceneVisibility,
        brightness: rustclient_model::ScreenBrightness,
        gpu: crate::GpuSettings,
    ) {
        camera.set_zoom(i32::from(settings.zoom[0]), i32::from(settings.zoom[1]));
        let (sx, sy) = transform.scales();
        let (logical, origin) = bounds.map_or_else(
            || {
                (
                    rustclient_game_ui::Rect::new(
                        0.,
                        0.,
                        (f64::from(self.size.width) / sx) as f32,
                        (f64::from(self.size.height) / sy) as f32,
                    ),
                    0.,
                )
            },
            |bounds| (bounds, transform.physical_origin_x()),
        );
        let viewport = crate::CameraViewport::new(logical.width as i32, logical.height as i32)
            .with_settings(settings);
        let inset = viewport.inset();
        let size = viewport.size();
        self.physical_viewport = [
            (origin + f64::from(logical.x + inset[0] as f32) * sx).max(0.) as f32,
            (f64::from(logical.y + inset[1] as f32) * sy).max(0.) as f32,
            ((f64::from(size[0]) * sx) as f32).min(self.size.width as f32),
            ((f64::from(size[1]) * sy) as f32).min(self.size.height as f32),
        ];
        self.physical_viewport[2] =
            self.physical_viewport[2].min(self.size.width as f32 - self.physical_viewport[0]);
        self.physical_viewport[3] =
            self.physical_viewport[3].min(self.size.height as f32 - self.physical_viewport[1]);
        let pose = camera.pose(viewport);
        // Normal UI rendering supplies the same frame admission used by picking. Standalone
        // world captures derive it here, after zoom/DPI/viewport shaping are resolved.
        let derived;
        let visibility = if visibility.zones.is_none() {
            derived = visibility.clone().with_policy(
                [pose.position[0], pose.position[2]],
                gpu.draw_distance,
                gpu.hide_unrelated_maps,
            );
            &derived
        } else {
            visibility
        };
        if self.last_camera.as_ref().is_some_and(|last| {
            *last == (camera, viewport, cycle, visibility.clone(), brightness, gpu)
        }) {
            return;
        }
        let eye = Vec3::from_array(pose.position.map(|v| v as f32));
        let (zones, admission) = visibility.gpu_admission();
        let uniform = CameraUniform {
            view_projection: camera.matrix(viewport).to_cols_array_2d(),
            cycle,
            top_level: u32::from(visibility.top_level),
            brightness: 0.5 + f32::from(100 - brightness.percent()) / 200.,
            material_flags: gpu.material.shader_flags(),
            fog: [
                gpu.fog_depth as f32,
                gpu.draw_distance.min(184) as f32 * LOCAL_TILE_SIZE,
                eye.x,
                eye.z,
            ],
            // RuneLite fogs toward the sky color; the world clears to black until the skybox
            // color is ported, so the fog target matches what lies behind the scene.
            fog_color: [0., 0., 0., 1.],
            color: [
                gpu.color_blind.shader_mode() as f32,
                gpu.color_blind_intensity as f32,
                0.,
                0.,
            ],
            hidden_roofs: *visibility.hidden.words(),
            zones,
            admission,
        };
        queue.write_buffer(&self.camera, 0, bytemuck::bytes_of(&uniform));
        let view = pose.model_view();
        let static_changed = self.geometry.sort(queue, view);
        let dynamic_changed = self.dynamic.sort(queue, view);
        let entities_changed = self.entities.sort(queue, view);
        if static_changed || dynamic_changed || entities_changed {
            let sources = [&self.geometry, &self.dynamic, &self.entities];
            super::transparency::merge(
                |source, index| sources[source].alpha_depth(index, eye),
                &mut self.transparent_runs,
            );
        }
        self.last_camera = Some((camera, viewport, cycle, visibility.clone(), brightness, gpu));
    }
    pub fn render_scene(&self, encoder: &mut wgpu::CommandEncoder) {
        // Without multisampling the pass renders straight into the texture that is composited.
        let (view, resolve_target, store) = match &self.target.multisampled {
            Some(multisampled) => (
                multisampled,
                Some(&self.target.resolved),
                wgpu::StoreOp::Discard,
            ),
            None => (&self.target.resolved, None, wgpu::StoreOp::Store),
        };
        let mut pass = encoder.begin_render_pass(&wgpu::RenderPassDescriptor {
            label: Some("classic reference world"),
            color_attachments: &[Some(wgpu::RenderPassColorAttachment {
                view,
                depth_slice: None,
                resolve_target,
                ops: wgpu::Operations {
                    load: wgpu::LoadOp::Clear(wgpu::Color::BLACK),
                    store,
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
        self.geometry.draw_opaque(pass, &self.pipelines);
        self.dynamic.draw_opaque(pass, &self.pipelines);
        self.entities.draw_opaque(pass, &self.pipelines);
        pass.set_pipeline(&self.pipelines.transparent);
        let sources = [&self.geometry, &self.dynamic, &self.entities];
        for run in &self.transparent_runs {
            sources[run.source].draw_alpha(pass, run.indices.clone());
        }
    }
}
fn bind_group(
    device: &wgpu::Device,
    layout: &wgpu::BindGroupLayout,
    camera: &wgpu::Buffer,
    textures: &wgpu::TextureView,
    anisotropy: u16,
    mipmaps: bool,
    animations: &wgpu::Buffer,
) -> wgpu::BindGroup {
    let sampler = crate::world_textures::sampler(device, anisotropy, mipmaps);
    device.create_bind_group(&wgpu::BindGroupDescriptor {
        label: Some("world camera binding"),
        layout,
        entries: &[
            wgpu::BindGroupEntry {
                binding: 0,
                resource: camera.as_entire_binding(),
            },
            wgpu::BindGroupEntry {
                binding: 1,
                resource: wgpu::BindingResource::TextureView(textures),
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
    })
}

#[cfg(test)]
mod tests {
    use super::{CAMERA_BYTES, CameraUniform};

    /// The shader and the Rust uniform must agree on the camera block: validate the WGSL and
    /// check the `Camera` struct occupies exactly the bytes the buffer is sized for.
    #[test]
    fn world_shader_validates_and_camera_block_matches_the_uniform() {
        let source = crate::shader_sources::world();
        let module = naga::front::wgsl::parse_str(&source).expect("world.wgsl parses");
        let info = naga::valid::Validator::new(
            naga::valid::ValidationFlags::all(),
            naga::valid::Capabilities::empty(),
        )
        .validate(&module)
        .expect("world.wgsl validates");
        drop(info);
        let (_, camera) = module
            .types
            .iter()
            .find(|(_, ty)| ty.name.as_deref() == Some("Camera"))
            .expect("Camera struct");
        let naga::TypeInner::Struct { span, .. } = camera.inner else {
            panic!("Camera is a struct");
        };
        assert_eq!(u64::from(span), CAMERA_BYTES);
        assert_eq!(size_of::<CameraUniform>() as u64, CAMERA_BYTES);
    }
}
