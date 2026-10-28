//! Persistent world pipelines, including revision-240 sorted opaque color/depth passes.
use rustclient_scene::SceneVertex;
const DEPTH: wgpu::TextureFormat = wgpu::TextureFormat::Depth32Float;
#[derive(Clone, Copy)]
enum PassKind {
    Opaque,
    Transparent,
    DeferredColor,
    DeferredDepth,
}
impl PassKind {
    fn label(self) -> &'static str {
        match self {
            Self::Opaque => "opaque world pipeline",
            Self::Transparent => "transparent world pipeline",
            Self::DeferredColor => "sorted actor color without depth writes",
            Self::DeferredDepth => "sorted actor depth without color writes",
        }
    }
}
pub(crate) struct WorldPipelines {
    pub opaque: wgpu::RenderPipeline,
    pub transparent: wgpu::RenderPipeline,
    pub deferred_color: wgpu::RenderPipeline,
    pub deferred_depth: wgpu::RenderPipeline,
}
impl WorldPipelines {
    pub fn new(
        device: &wgpu::Device,
        layout: &wgpu::PipelineLayout,
        shader: &wgpu::ShaderModule,
        samples: u32,
    ) -> Self {
        let build = |kind| {
            pipeline(
                device,
                super::world_target::FORMAT,
                layout,
                shader,
                kind,
                samples,
            )
        };
        Self {
            opaque: build(PassKind::Opaque),
            transparent: build(PassKind::Transparent),
            deferred_color: build(PassKind::DeferredColor),
            deferred_depth: build(PassKind::DeferredDepth),
        }
    }
}
fn pipeline(
    device: &wgpu::Device,
    format: wgpu::TextureFormat,
    layout: &wgpu::PipelineLayout,
    shader: &wgpu::ShaderModule,
    kind: PassKind,
    samples: u32,
) -> wgpu::RenderPipeline {
    device.create_render_pipeline(&wgpu::RenderPipelineDescriptor {
        label: Some(kind.label()),
        layout: Some(layout),
        vertex: wgpu::VertexState {
            module: shader,
            entry_point: Some("vs_main"),
            compilation_options: wgpu::PipelineCompilationOptions::default(),
            buffers: &[Some(wgpu::VertexBufferLayout {
                array_stride: std::mem::size_of::<SceneVertex>() as u64,
                step_mode: wgpu::VertexStepMode::Vertex,
                attributes: &wgpu::vertex_attr_array![0=>Sint16x4,1=>Uint32,2=>Uint16x4,3=>Uint32],
            })],
        },
        primitive: wgpu::PrimitiveState {
            cull_mode: Some(wgpu::Face::Back),
            ..Default::default()
        },
        depth_stencil: Some(wgpu::DepthStencilState {
            format: DEPTH,
            depth_write_enabled: Some(matches!(kind, PassKind::Opaque | PassKind::DeferredDepth)),
            depth_compare: Some(wgpu::CompareFunction::Greater),
            stencil: wgpu::StencilState::default(),
            bias: wgpu::DepthBiasState::default(),
        }),
        multisample: wgpu::MultisampleState {
            count: samples,
            ..Default::default()
        },
        fragment: Some(wgpu::FragmentState {
            module: shader,
            entry_point: Some("fs_main"),
            compilation_options: wgpu::PipelineCompilationOptions::default(),
            targets: &[Some(wgpu::ColorTargetState {
                format,
                blend: matches!(kind, PassKind::Transparent).then_some(wgpu::BlendState {
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
                write_mask: if matches!(kind, PassKind::DeferredDepth) {
                    wgpu::ColorWrites::empty()
                } else {
                    wgpu::ColorWrites::ALL
                },
            })],
        }),
        multiview_mask: None,
        cache: None,
    })
}
