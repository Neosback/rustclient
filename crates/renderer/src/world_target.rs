//! Non-sRGB multisampled world target and explicit display conversion.
use crate::SurfaceSize;
pub(crate) const FORMAT: wgpu::TextureFormat = wgpu::TextureFormat::Rgba8Unorm;
pub(crate) struct WorldTarget {
    pub texture: wgpu::Texture,
    pub resolved: wgpu::TextureView,
    /// `None` when the world renders straight into `resolved` without multisampling.
    pub multisampled: Option<wgpu::TextureView>,
    pub depth: wgpu::TextureView,
    pub samples: u32,
    bind: wgpu::BindGroup,
    pipeline: wgpu::RenderPipeline,
}
impl WorldTarget {
    #[expect(
        clippy::too_many_lines,
        reason = "one target/composite resource lifetime; no per-frame allocations"
    )]
    pub fn new(
        device: &wgpu::Device,
        size: SurfaceSize,
        surface: wgpu::TextureFormat,
        samples: u32,
    ) -> Self {
        let create = |label, samples, format, usage| {
            device.create_texture(&wgpu::TextureDescriptor {
                label: Some(label),
                size: wgpu::Extent3d {
                    width: size.width.max(1),
                    height: size.height.max(1),
                    depth_or_array_layers: 1,
                },
                mip_level_count: 1,
                sample_count: samples,
                dimension: wgpu::TextureDimension::D2,
                format,
                usage,
                view_formats: &[],
            })
        };
        let texture = create(
            "reference world resolve",
            1,
            FORMAT,
            wgpu::TextureUsages::RENDER_ATTACHMENT
                | wgpu::TextureUsages::TEXTURE_BINDING
                | wgpu::TextureUsages::COPY_SRC,
        );
        let resolved = texture.create_view(&wgpu::TextureViewDescriptor::default());
        let multisampled = (samples > 1).then(|| {
            create(
                "reference world MSAA",
                samples,
                FORMAT,
                wgpu::TextureUsages::RENDER_ATTACHMENT,
            )
            .create_view(&wgpu::TextureViewDescriptor::default())
        });
        let depth = create(
            "reference reverse Z",
            samples,
            wgpu::TextureFormat::Depth32Float,
            wgpu::TextureUsages::RENDER_ATTACHMENT,
        )
        .create_view(&wgpu::TextureViewDescriptor::default());
        let layout = device.create_bind_group_layout(&wgpu::BindGroupLayoutDescriptor {
            label: Some("world composite layout"),
            entries: &[wgpu::BindGroupLayoutEntry {
                binding: 0,
                visibility: wgpu::ShaderStages::FRAGMENT,
                ty: wgpu::BindingType::Texture {
                    sample_type: wgpu::TextureSampleType::Float { filterable: false },
                    view_dimension: wgpu::TextureViewDimension::D2,
                    multisampled: false,
                },
                count: None,
            }],
        });
        let bind = device.create_bind_group(&wgpu::BindGroupDescriptor {
            label: Some("world composite binding"),
            layout: &layout,
            entries: &[wgpu::BindGroupEntry {
                binding: 0,
                resource: wgpu::BindingResource::TextureView(&resolved),
            }],
        });
        let convert = if surface.is_srgb() {
            "select(pow((rgb+0.055)/1.055,vec3<f32>(2.4)),rgb/12.92,rgb<=vec3<f32>(0.04045))"
        } else {
            "rgb"
        };
        let source = format!(
            r"
@group(0) @binding(0) var image:texture_2d<f32>;
@vertex fn vs_main(@builtin(vertex_index) i:u32)->@builtin(position) vec4<f32> {{
 let p=array<vec2<f32>,3>(vec2<f32>(-1.0,-1.0),vec2<f32>(3.0,-1.0),vec2<f32>(-1.0,3.0));
 return vec4<f32>(p[i],0.0,1.0);
}}
@fragment fn fs_main(@builtin(position) p:vec4<f32>)->@location(0) vec4<f32> {{
 let rgb=textureLoad(image,vec2<i32>(p.xy),0).rgb;
 return vec4<f32>({convert},1.0);
}}"
        );
        let shader = device.create_shader_module(wgpu::ShaderModuleDescriptor {
            label: Some("one world display conversion"),
            source: wgpu::ShaderSource::Wgsl(source.into()),
        });
        let pipeline_layout = device.create_pipeline_layout(&wgpu::PipelineLayoutDescriptor {
            label: Some("world composite pipeline layout"),
            bind_group_layouts: &[Some(&layout)],
            immediate_size: 0,
        });
        let pipeline = device.create_render_pipeline(&wgpu::RenderPipelineDescriptor {
            label: Some("reference world composite"),
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
                    format: surface,
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
            resolved,
            multisampled,
            depth,
            samples,
            bind,
            pipeline,
        }
    }
    pub fn composite(&self, pass: &mut wgpu::RenderPass<'_>) {
        pass.set_pipeline(&self.pipeline);
        pass.set_bind_group(0, &self.bind, &[]);
        pass.draw(0..3, 0..1);
    }
}
