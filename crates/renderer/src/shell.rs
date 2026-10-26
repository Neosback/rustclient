//! Cached retained-shell composition, independent of the OSRS widget atlas.
use crate::{RenderError, SurfaceSize};

#[derive(Debug, Clone, Copy)]
pub struct ShellLayer<'a> {
    pub pixels: &'a [u8],
    pub size: SurfaceSize,
    pub generation: u64,
    /// Physical left/top. Input uses this same rectangle in the composition root.
    pub origin: [u32; 2],
}

#[derive(Debug, Default, Clone, Copy)]
pub struct ShellStats {
    pub uploads: u64,
    pub uploaded_bytes: u64,
}

#[derive(Default)]
pub(crate) struct ShellStaging {
    pixels: Vec<u8>,
    size: [u32; 2],
    generation: Option<u64>,
    origin: [u32; 2],
    visible: bool,
    pending: bool,
    pub stats: ShellStats,
}
impl ShellStaging {
    pub fn set(&mut self, layer: Option<ShellLayer<'_>>) -> Result<(), RenderError> {
        let Some(layer) = layer else {
            self.visible = false;
            return Ok(());
        };
        let size = [layer.size.width, layer.size.height];
        if size.contains(&0)
            || size.iter().any(|n| *n > 4096)
            || u64::from(size[0]) * u64::from(size[1]) * 4 != layer.pixels.len() as u64
        {
            return Err(RenderError::Ui("invalid retained shell image"));
        }
        if self.generation != Some(layer.generation) || self.size != size {
            self.pixels.clear();
            self.pixels.extend_from_slice(layer.pixels);
            self.size = size;
            self.generation = Some(layer.generation);
            self.pending = true;
        }
        self.origin = layer.origin;
        self.visible = true;
        Ok(())
    }
    pub fn prepare(
        &mut self,
        gpu: &mut Option<ShellGpu>,
        device: &wgpu::Device,
        queue: &wgpu::Queue,
        format: wgpu::TextureFormat,
    ) {
        if !self.visible || !self.pending {
            return;
        }
        let projection = gpu.get_or_insert_with(|| ShellGpu::new(device, format));
        projection.upload(device, queue, self.size, &self.pixels);
        self.pending = false;
        self.stats.uploads += 1;
        self.stats.uploaded_bytes += self.pixels.len() as u64;
        self.pixels.clear();
    }
    pub fn draw<'a>(
        &self,
        gpu: Option<&'a ShellGpu>,
        pass: &mut wgpu::RenderPass<'a>,
        surface: [u32; 2],
    ) {
        if !self.visible {
            return;
        }
        let Some(gpu) = gpu else {
            return;
        };
        let [x, y] = self.origin;
        let width = self.size[0].min(surface[0].saturating_sub(x));
        let height = self.size[1].min(surface[1].saturating_sub(y));
        if width == 0 || height == 0 {
            return;
        }
        #[expect(
            clippy::cast_precision_loss,
            reason = "validated bounded physical surface extents"
        )]
        pass.set_viewport(x as f32, y as f32, width as f32, height as f32, 0., 1.);
        pass.set_scissor_rect(x, y, width, height);
        pass.set_pipeline(&gpu.pipeline);
        pass.set_bind_group(0, &gpu.bind, &[]);
        pass.draw(0..3, 0..1);
    }
}

pub(crate) struct ShellGpu {
    texture: wgpu::Texture,
    bind: wgpu::BindGroup,
    layout: wgpu::BindGroupLayout,
    sampler: wgpu::Sampler,
    pipeline: wgpu::RenderPipeline,
    size: [u32; 2],
}
impl ShellGpu {
    fn image(device: &wgpu::Device, size: [u32; 2]) -> wgpu::Texture {
        device.create_texture(&wgpu::TextureDescriptor {
            label: Some("retained shell pixels"),
            size: wgpu::Extent3d {
                width: size[0],
                height: size[1],
                depth_or_array_layers: 1,
            },
            mip_level_count: 1,
            sample_count: 1,
            dimension: wgpu::TextureDimension::D2,
            format: wgpu::TextureFormat::Rgba8UnormSrgb,
            usage: wgpu::TextureUsages::TEXTURE_BINDING | wgpu::TextureUsages::COPY_DST,
            view_formats: &[],
        })
    }
    fn bind(
        device: &wgpu::Device,
        layout: &wgpu::BindGroupLayout,
        sampler: &wgpu::Sampler,
        texture: &wgpu::Texture,
    ) -> wgpu::BindGroup {
        device.create_bind_group(&wgpu::BindGroupDescriptor {
            label: Some("retained shell image"),
            layout,
            entries: &[
                wgpu::BindGroupEntry {
                    binding: 0,
                    resource: wgpu::BindingResource::TextureView(
                        &texture.create_view(&wgpu::TextureViewDescriptor::default()),
                    ),
                },
                wgpu::BindGroupEntry {
                    binding: 1,
                    resource: wgpu::BindingResource::Sampler(sampler),
                },
            ],
        })
    }
    fn new(device: &wgpu::Device, format: wgpu::TextureFormat) -> Self {
        let layout = device.create_bind_group_layout(&wgpu::BindGroupLayoutDescriptor {
            label: Some("retained shell layout"),
            entries: &[
                wgpu::BindGroupLayoutEntry {
                    binding: 0,
                    visibility: wgpu::ShaderStages::FRAGMENT,
                    ty: wgpu::BindingType::Texture {
                        sample_type: wgpu::TextureSampleType::Float { filterable: true },
                        view_dimension: wgpu::TextureViewDimension::D2,
                        multisampled: false,
                    },
                    count: None,
                },
                wgpu::BindGroupLayoutEntry {
                    binding: 1,
                    visibility: wgpu::ShaderStages::FRAGMENT,
                    ty: wgpu::BindingType::Sampler(wgpu::SamplerBindingType::Filtering),
                    count: None,
                },
            ],
        });
        let sampler = device.create_sampler(&wgpu::SamplerDescriptor {
            label: Some("retained shell sampler"),
            ..Default::default()
        });
        let texture = Self::image(device, [1, 1]);
        let bind = Self::bind(device, &layout, &sampler, &texture);
        let shader = device.create_shader_module(wgpu::ShaderModuleDescriptor {
            label: Some("retained shell composite"),
            source: wgpu::ShaderSource::Wgsl(include_str!("shaders/shell.wgsl").into()),
        });
        let pipeline_layout = device.create_pipeline_layout(&wgpu::PipelineLayoutDescriptor {
            label: Some("retained shell pipeline"),
            bind_group_layouts: &[Some(&layout)],
            immediate_size: 0,
        });
        let pipeline = device.create_render_pipeline(&wgpu::RenderPipelineDescriptor {
            label: Some("retained shell composite"),
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
                    format,
                    blend: Some(wgpu::BlendState::PREMULTIPLIED_ALPHA_BLENDING),
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
            bind,
            layout,
            sampler,
            pipeline,
            size: [1, 1],
        }
    }
    fn upload(
        &mut self,
        device: &wgpu::Device,
        queue: &wgpu::Queue,
        size: [u32; 2],
        pixels: &[u8],
    ) {
        if self.size != size {
            self.texture = Self::image(device, size);
            self.bind = Self::bind(device, &self.layout, &self.sampler, &self.texture);
            self.size = size;
        }
        queue.write_texture(
            wgpu::TexelCopyTextureInfo {
                texture: &self.texture,
                mip_level: 0,
                origin: wgpu::Origin3d::ZERO,
                aspect: wgpu::TextureAspect::All,
            },
            pixels,
            wgpu::TexelCopyBufferLayout {
                offset: 0,
                bytes_per_row: Some(size[0] * 4),
                rows_per_image: Some(size[1]),
            },
            wgpu::Extent3d {
                width: size[0],
                height: size[1],
                depth_or_array_layers: 1,
            },
        );
    }
}
