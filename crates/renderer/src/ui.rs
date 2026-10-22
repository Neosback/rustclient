//! GPU projection of the platform-neutral ordered quad stream.
#![expect(
    clippy::cast_precision_loss,
    reason = "texture/window dimensions are bounded well below f32 integer precision"
)]
use crate::RenderError;
use bytemuck::{Pod, Zeroable};
use rustclient_cache::{Image, TitleAssets};
use rustclient_game_ui::{Flames, Quad, TitleViewport, UiFrame, UiImageId};
use std::collections::BTreeMap;

const ATLAS_WIDTH: u32 = 2048;
const ATLAS_HEIGHT: u32 = 2048;
const MAX_QUADS: usize = 32768;

pub const MINIMAP_WIDTH: u32 = 156;
pub const MINIMAP_HEIGHT: u32 = 156;
pub const COMPASS_WIDTH: u32 = 33;
pub const COMPASS_HEIGHT: u32 = 33;

// Must match shaders/ui.wgsl. Vertex attributes are likewise declared here once.
const IMAGE_GROUP: u32 = 0;
const IMAGE_BINDING: u32 = 0;
const SAMPLER_BINDING: u32 = 1;
const ATTRIBUTES: [wgpu::VertexAttribute; 3] =
    wgpu::vertex_attr_array![0=>Float32x2,1=>Float32x2,2=>Float32x4];

#[repr(C)]
#[derive(Clone, Copy, Pod, Zeroable)]
struct Vertex {
    position: [f32; 2],
    uv: [f32; 2],
    tint: [f32; 4],
}
#[derive(Clone, Copy)]
struct Region {
    x: u32,
    y: u32,
    width: u32,
    height: u32,
}

#[derive(Clone, Copy, PartialEq, Eq)]
enum DrawSource {
    Atlas,
    Flames,
    Minimap,
    Compass,
}

struct Draw {
    first: u32,
    count: u32,
    source: DrawSource,
}

/// Per-frame projection counters, suitable for diagnostics without GPU readback.
#[derive(Debug, Default, Clone, Copy)]
pub struct UiStats {
    /// Submitted nonempty clipped quads.
    pub quads: u32,
    /// Consecutive atlas/flame batches.
    pub draw_calls: u32,
    /// Dynamic texture bytes uploaded in this frame.
    pub dynamic_upload_bytes: u32,
}

pub(crate) struct UiGpu {
    pipeline: wgpu::RenderPipeline,
    atlas: wgpu::BindGroup,
    flames: wgpu::BindGroup,
    flame_texture: wgpu::Texture,
    minimap: wgpu::BindGroup,
    minimap_texture: wgpu::Texture,
    minimap_size: [u32; 2],
    compass_size: [u32; 2],
    compass: wgpu::BindGroup,
    compass_texture: wgpu::Texture,
    regions: BTreeMap<UiImageId, Region>,
    vertex_buffer: wgpu::Buffer,
    vertices: Vec<Vertex>,
    draws: Vec<Draw>,
    pub(crate) stats: UiStats,
}
impl UiGpu {
    pub(crate) fn new(
        device: &wgpu::Device,
        queue: &wgpu::Queue,
        format: wgpu::TextureFormat,
        assets: &TitleAssets,
    ) -> Result<Self, RenderError> {
        Self::with_images(device, queue, format, assets, &BTreeMap::new())
    }
    #[expect(
        clippy::too_many_lines,
        reason = "one-time pipeline and buffer construction"
    )]
    pub(crate) fn with_images(
        device: &wgpu::Device,
        queue: &wgpu::Queue,
        format: wgpu::TextureFormat,
        assets: &TitleAssets,
        images: &BTreeMap<UiImageId, Image>,
    ) -> Result<Self, RenderError> {
        let layout = device.create_bind_group_layout(&wgpu::BindGroupLayoutDescriptor {
            label: Some("title images: group 0, texture 0, sampler 1"),
            entries: &[
                wgpu::BindGroupLayoutEntry {
                    binding: IMAGE_BINDING,
                    visibility: wgpu::ShaderStages::FRAGMENT,
                    ty: wgpu::BindingType::Texture {
                        sample_type: wgpu::TextureSampleType::Float { filterable: true },
                        view_dimension: wgpu::TextureViewDimension::D2,
                        multisampled: false,
                    },
                    count: None,
                },
                wgpu::BindGroupLayoutEntry {
                    binding: SAMPLER_BINDING,
                    visibility: wgpu::ShaderStages::FRAGMENT,
                    ty: wgpu::BindingType::Sampler(wgpu::SamplerBindingType::Filtering),
                    count: None,
                },
            ],
        });
        let pipeline_layout = device.create_pipeline_layout(&wgpu::PipelineLayoutDescriptor {
            label: Some("title 2D layout"),
            bind_group_layouts: &[Some(&layout)],
            immediate_size: 0,
        });
        let shader = device.create_shader_module(wgpu::ShaderModuleDescriptor {
            label: Some("title 2D shader"),
            source: wgpu::ShaderSource::Wgsl(include_str!("shaders/ui.wgsl").into()),
        });
        let pipeline = device.create_render_pipeline(&wgpu::RenderPipelineDescriptor {
            label: Some("title 2D quad pipeline"),
            layout: Some(&pipeline_layout),
            vertex: wgpu::VertexState {
                module: &shader,
                entry_point: Some("vs"),
                compilation_options: wgpu::PipelineCompilationOptions::default(),
                buffers: &[Some(wgpu::VertexBufferLayout {
                    array_stride: std::mem::size_of::<Vertex>() as u64,
                    step_mode: wgpu::VertexStepMode::Vertex,
                    attributes: &ATTRIBUTES,
                })],
            },
            primitive: wgpu::PrimitiveState::default(),
            depth_stencil: None,
            multisample: wgpu::MultisampleState::default(),
            fragment: Some(wgpu::FragmentState {
                module: &shader,
                entry_point: Some("fs"),
                compilation_options: wgpu::PipelineCompilationOptions::default(),
                targets: &[Some(wgpu::ColorTargetState {
                    format,
                    blend: Some(wgpu::BlendState::ALPHA_BLENDING),
                    write_mask: wgpu::ColorWrites::ALL,
                })],
            }),
            multiview_mask: None,
            cache: None,
        });
        let sampler = device.create_sampler(&wgpu::SamplerDescriptor {
            label: Some("title nearest pixel sampler"),
            mag_filter: wgpu::FilterMode::Nearest,
            min_filter: wgpu::FilterMode::Nearest,
            ..Default::default()
        });
        let (pixels, regions) = atlas(assets, images)?;
        let atlas_texture = texture(device, ATLAS_WIDTH, ATLAS_HEIGHT, "title static atlas");
        upload(queue, &atlas_texture, ATLAS_WIDTH, ATLAS_HEIGHT, &pixels);
        let flame_texture = texture(
            device,
            Flames::WIDTH,
            Flames::HEIGHT,
            "title dynamic flames",
        );
        let minimap_size = assets
            .gameframe
            .as_ref()
            .map_or([MINIMAP_WIDTH, MINIMAP_HEIGHT], |g| {
                [g.minimap_mask.image.width(), g.minimap_mask.image.height()]
            });
        let compass_size = assets
            .gameframe
            .as_ref()
            .map_or([COMPASS_WIDTH, COMPASS_HEIGHT], |g| {
                [g.compass_mask.image.width(), g.compass_mask.image.height()]
            });
        let minimap_texture = texture(
            device,
            minimap_size[0],
            minimap_size[1],
            "gameframe dynamic minimap",
        );
        let compass_texture = texture(
            device,
            compass_size[0],
            compass_size[1],
            "gameframe dynamic compass",
        );
        let atlas = bind(device, &layout, &sampler, &atlas_texture);
        let flames = bind(device, &layout, &sampler, &flame_texture);
        let minimap = bind(device, &layout, &sampler, &minimap_texture);
        let compass = bind(device, &layout, &sampler, &compass_texture);
        let vertex_buffer = device.create_buffer(&wgpu::BufferDescriptor {
            label: Some("title reusable quad vertices"),
            size: (MAX_QUADS * 6 * std::mem::size_of::<Vertex>()) as u64,
            usage: wgpu::BufferUsages::VERTEX | wgpu::BufferUsages::COPY_DST,
            mapped_at_creation: false,
        });
        Ok(Self {
            pipeline,
            atlas,
            flames,
            flame_texture,
            minimap,
            minimap_texture,
            minimap_size,
            compass_size,
            compass,
            compass_texture,
            regions,
            vertex_buffer,
            vertices: Vec::with_capacity(MAX_QUADS * 6),
            draws: Vec::with_capacity(16),
            stats: UiStats::default(),
        })
    }
    #[expect(
        clippy::too_many_lines,
        reason = "single painter-ordered batching pass"
    )]
    pub(crate) fn prepare(
        &mut self,
        queue: &wgpu::Queue,
        frame: &UiFrame,
        flame_pixels: &[u8],
        minimap_pixels: &[u8],
        compass_pixels: &[u8],
        viewport: TitleViewport,
    ) -> Result<(), RenderError> {
        if (!flame_pixels.is_empty()
            && flame_pixels.len()
                != usize::try_from(Flames::WIDTH * Flames::HEIGHT * 4).unwrap_or(0))
            || (!minimap_pixels.is_empty()
                && minimap_pixels.len()
                    != usize::try_from(self.minimap_size[0] * self.minimap_size[1] * 4)
                        .unwrap_or(0))
            || (!compass_pixels.is_empty()
                && compass_pixels.len()
                    != usize::try_from(self.compass_size[0] * self.compass_size[1] * 4)
                        .unwrap_or(0))
            || frame.quads.len() > MAX_QUADS
            || !viewport.scale.is_finite()
            || viewport.scale <= 0.
            || viewport.physical_width == 0
            || viewport.physical_height == 0
        {
            return Err(RenderError::Ui("invalid UI upload or viewport"));
        }
        self.vertices.clear();
        self.draws.clear();
        self.stats = UiStats::default();
        if !flame_pixels.is_empty() {
            upload(
                queue,
                &self.flame_texture,
                Flames::WIDTH,
                Flames::HEIGHT,
                flame_pixels,
            );
            self.stats.dynamic_upload_bytes += Flames::WIDTH * Flames::HEIGHT * 4;
        }
        if !minimap_pixels.is_empty() {
            upload(
                queue,
                &self.minimap_texture,
                self.minimap_size[0],
                self.minimap_size[1],
                minimap_pixels,
            );
            self.stats.dynamic_upload_bytes += self.minimap_size[0] * self.minimap_size[1] * 4;
        }
        if !compass_pixels.is_empty() {
            upload(
                queue,
                &self.compass_texture,
                self.compass_size[0],
                self.compass_size[1],
                compass_pixels,
            );
            self.stats.dynamic_upload_bytes += self.compass_size[0] * self.compass_size[1] * 4;
        }
        for quad in &frame.quads {
            let (source, region, width, height) = match quad.image {
                UiImageId::Flames => (
                    DrawSource::Flames,
                    Region {
                        x: 0,
                        y: 0,
                        width: Flames::WIDTH,
                        height: Flames::HEIGHT,
                    },
                    Flames::WIDTH,
                    Flames::HEIGHT,
                ),
                UiImageId::Minimap => (
                    DrawSource::Minimap,
                    Region {
                        x: 0,
                        y: 0,
                        width: self.minimap_size[0],
                        height: self.minimap_size[1],
                    },
                    self.minimap_size[0],
                    self.minimap_size[1],
                ),
                UiImageId::Compass => (
                    DrawSource::Compass,
                    Region {
                        x: 0,
                        y: 0,
                        width: self.compass_size[0],
                        height: self.compass_size[1],
                    },
                    self.compass_size[0],
                    self.compass_size[1],
                ),
                _ => {
                    let region = self
                        .regions
                        .get(&quad.image)
                        .copied()
                        .ok_or(RenderError::Ui("unknown UI image"))?;
                    (DrawSource::Atlas, region, ATLAS_WIDTH, ATLAS_HEIGHT)
                }
            };
            let before = self.vertices.len();
            add_quad(&mut self.vertices, quad, region, width, height, viewport);
            if self.vertices.len() == before {
                continue;
            }
            if self.vertices.len() > MAX_QUADS * 6 {
                return Err(RenderError::Ui("UI vertex budget exceeded"));
            }
            let count = u32::try_from(self.vertices.len() - before)
                .map_err(|_| RenderError::Ui("vertex overflow"))?;
            let first = u32::try_from(before).map_err(|_| RenderError::Ui("vertex overflow"))?;
            if let Some(draw) = self.draws.last_mut().filter(|draw| draw.source == source) {
                draw.count += count;
            } else {
                self.draws.push(Draw {
                    first,
                    count,
                    source,
                });
            }
            self.stats.quads += 1;
        }
        self.stats.draw_calls =
            u32::try_from(self.draws.len()).map_err(|_| RenderError::Ui("draw overflow"))?;
        if !self.vertices.is_empty() {
            queue.write_buffer(&self.vertex_buffer, 0, bytemuck::cast_slice(&self.vertices));
        }
        Ok(())
    }
    pub(crate) fn draw(&self, pass: &mut wgpu::RenderPass<'_>) {
        pass.set_pipeline(&self.pipeline);
        pass.set_vertex_buffer(0, self.vertex_buffer.slice(..));
        for draw in &self.draws {
            pass.set_bind_group(
                IMAGE_GROUP,
                match draw.source {
                    DrawSource::Atlas => &self.atlas,
                    DrawSource::Flames => &self.flames,
                    DrawSource::Minimap => &self.minimap,
                    DrawSource::Compass => &self.compass,
                },
                &[],
            );
            pass.draw(draw.first..draw.first + draw.count, 0..1);
        }
    }
}
fn texture(device: &wgpu::Device, width: u32, height: u32, label: &str) -> wgpu::Texture {
    device.create_texture(&wgpu::TextureDescriptor {
        label: Some(label),
        size: wgpu::Extent3d {
            width,
            height,
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
fn upload(queue: &wgpu::Queue, texture: &wgpu::Texture, width: u32, height: u32, pixels: &[u8]) {
    queue.write_texture(
        wgpu::TexelCopyTextureInfo {
            texture,
            mip_level: 0,
            origin: wgpu::Origin3d::ZERO,
            aspect: wgpu::TextureAspect::All,
        },
        pixels,
        wgpu::TexelCopyBufferLayout {
            offset: 0,
            bytes_per_row: Some(width * 4),
            rows_per_image: Some(height),
        },
        wgpu::Extent3d {
            width,
            height,
            depth_or_array_layers: 1,
        },
    );
}
fn bind(
    device: &wgpu::Device,
    layout: &wgpu::BindGroupLayout,
    sampler: &wgpu::Sampler,
    texture: &wgpu::Texture,
) -> wgpu::BindGroup {
    device.create_bind_group(&wgpu::BindGroupDescriptor {
        label: Some("title image bindings"),
        layout,
        entries: &[
            wgpu::BindGroupEntry {
                binding: IMAGE_BINDING,
                resource: wgpu::BindingResource::TextureView(
                    &texture.create_view(&wgpu::TextureViewDescriptor::default()),
                ),
            },
            wgpu::BindGroupEntry {
                binding: SAMPLER_BINDING,
                resource: wgpu::BindingResource::Sampler(sampler),
            },
        ],
    })
}

fn atlas(
    assets: &TitleAssets,
    extra: &BTreeMap<UiImageId, Image>,
) -> Result<(Vec<u8>, BTreeMap<UiImageId, Region>), RenderError> {
    let mut images = vec![
        (UiImageId::Background, &assets.background),
        (UiImageId::Logo, &assets.logo.image),
        (UiImageId::TitleBox, &assets.title_box.image),
        (UiImageId::Button, &assets.button.image),
        (UiImageId::Muted, &assets.mute[1].image),
    ];
    for (i, sprite) in assets.checkboxes.iter().enumerate() {
        images.push((
            UiImageId::Checkbox(u8::try_from(i).map_err(|_| RenderError::Ui("checkbox overflow"))?),
            &sprite.image,
        ));
    }
    for (font, font_assets) in assets.fonts.iter().enumerate() {
        for (glyph, sprite) in font_assets.glyphs.iter().enumerate() {
            images.push((
                UiImageId::Glyph(
                    u8::try_from(font).map_err(|_| RenderError::Ui("font overflow"))?,
                    u8::try_from(glyph).map_err(|_| RenderError::Ui("glyph overflow"))?,
                ),
                &sprite.image,
            ));
        }
    }
    if let Some(gf) = &assets.gameframe {
        images.push((UiImageId::Sprite(1177), &gf.minimap_frame.image));
        images.push((UiImageId::Sprite(1178), &gf.minimap_mask.image));
        images.push((UiImageId::Sprite(1179), &gf.compass_mask.image));
        images.push((UiImageId::Sprite(169), &gf.compass.image));
        images.push((UiImageId::Sprite(1173), &gf.tabs_top.image));
        images.push((UiImageId::Sprite(1174), &gf.tabs_bottom.image));
        for (i, icon) in gf.tab_icons.iter().enumerate() {
            let id = 774 + u32::try_from(i).unwrap_or(0);
            images.push((UiImageId::Sprite(id), &icon.image));
        }
        for (opt, id) in [
            (&gf.orb_frame, 1071),
            (&gf.hp_orb, 1060),
            (&gf.hp_icon, 1067),
            (&gf.prayer_orb, 1063),
            (&gf.prayer_icon, 1068),
            (&gf.run_orb, 1064),
            (&gf.run_icon, 1070),
            (&gf.spec_orb, 1607),
            (&gf.spec_icon, 1610),
        ] {
            if let Some(sp) = opt {
                images.push((UiImageId::Sprite(id), &sp.image));
            }
        }
    }
    let mut pixels = vec![0; usize::try_from(ATLAS_WIDTH * ATLAS_HEIGHT * 4).unwrap_or(0)];
    pixels[..4].copy_from_slice(&[255; 4]);
    images.extend(extra.iter().map(|(id, image)| (*id, image)));
    let mut regions = BTreeMap::from([(
        UiImageId::White,
        Region {
            x: 0,
            y: 0,
            width: 1,
            height: 1,
        },
    )]);
    let (mut x, mut y, mut row_height) = (2, 0, 1);
    for (id, image) in images {
        if image.width() == 0 || image.height() == 0 {
            continue;
        }
        if x + image.width() + 1 > ATLAS_WIDTH {
            x = 0;
            y += row_height + 1;
            row_height = 0;
        }
        if image.width() > ATLAS_WIDTH || y + image.height() > ATLAS_HEIGHT {
            return Err(RenderError::Ui("title atlas capacity exceeded"));
        }
        copy_image(&mut pixels, image, x, y);
        regions.insert(
            id,
            Region {
                x,
                y,
                width: image.width(),
                height: image.height(),
            },
        );
        x += image.width() + 1;
        row_height = row_height.max(image.height());
    }
    Ok((pixels, regions))
}
fn copy_image(pixels: &mut [u8], image: &Image, x: u32, y: u32) {
    let width = usize::try_from(image.width()).unwrap_or(0);
    for row in 0..usize::try_from(image.height()).unwrap_or(0) {
        let dest =
            ((usize::try_from(y).unwrap_or(0) + row) * ATLAS_WIDTH as usize + x as usize) * 4;
        pixels[dest..dest + width * 4]
            .copy_from_slice(&image.pixels()[row * width * 4..(row + 1) * width * 4]);
    }
}
fn add_quad(
    vertices: &mut Vec<Vertex>,
    quad: &Quad,
    region: Region,
    tw: u32,
    th: u32,
    viewport: TitleViewport,
) {
    let rect = quad.rect;
    let clip = quad.clip;
    if rect.width <= 0. || rect.height <= 0. {
        return;
    }
    if quad.rotation != 0 {
        add_rotated_quad(vertices, quad, region, tw, th, viewport);
        return;
    }
    let x0 = rect.x.max(clip.x);
    let y0 = rect.y.max(clip.y);
    let x1 = (rect.x + rect.width).min(clip.x + clip.width);
    let y1 = (rect.y + rect.height).min(clip.y + clip.height);
    if x1 <= x0 || y1 <= y0 {
        return;
    }
    let u = |x: f32| {
        (region.x as f32
            + (quad.uv.x + (x - rect.x) / rect.width * quad.uv.width) * region.width as f32)
            / tw as f32
    };
    let v = |y: f32| {
        (region.y as f32
            + (quad.uv.y + (y - rect.y) / rect.height * quad.uv.height) * region.height as f32)
            / th as f32
    };
    #[expect(
        clippy::cast_possible_truncation,
        reason = "DPI scales are small bounded window-system values"
    )]
    let scale = viewport.scale as f32;
    let position = |x: f32, y: f32| {
        [
            (x + viewport.origin_x()) * scale / viewport.physical_width as f32 * 2. - 1.,
            1. - y * scale / viewport.physical_height as f32 * 2.,
        ]
    };
    let tint = [
        linear(quad.tint[0]),
        linear(quad.tint[1]),
        linear(quad.tint[2]),
        f32::from(quad.tint[3]) / 255.,
    ];
    let top_left = Vertex {
        position: position(x0, y0),
        uv: [u(x0), v(y0)],
        tint,
    };
    let top_right = Vertex {
        position: position(x1, y0),
        uv: [u(x1), v(y0)],
        tint,
    };
    let bottom_right = Vertex {
        position: position(x1, y1),
        uv: [u(x1), v(y1)],
        tint,
    };
    let bottom_left = Vertex {
        position: position(x0, y1),
        uv: [u(x0), v(y1)],
        tint,
    };
    vertices.extend_from_slice(&[
        top_left,
        top_right,
        bottom_right,
        top_left,
        bottom_right,
        bottom_left,
    ]);
}
/// Rotated sprites use a bounded eight-corner clip polygon; ordinary quads keep the fast path.
#[expect(
    clippy::cast_possible_truncation,
    reason = "bounded DPI scale and texture coordinates"
)]
fn add_rotated_quad(
    vertices: &mut Vec<Vertex>,
    quad: &Quad,
    region: Region,
    tw: u32,
    th: u32,
    viewport: TitleViewport,
) {
    let rect = quad.rect;
    let center = [rect.x + rect.width / 2., rect.y + rect.height / 2.];
    let radians = f32::from(quad.rotation) * std::f32::consts::TAU / 65_536.;
    let (sin, cos) = radians.sin_cos();
    let tint = [
        linear(quad.tint[0]),
        linear(quad.tint[1]),
        linear(quad.tint[2]),
        f32::from(quad.tint[3]) / 255.,
    ];
    let mut polygon = Vec::with_capacity(8);
    for (x, y, u, v) in [
        (rect.x, rect.y, 0., 0.),
        (rect.x + rect.width, rect.y, 1., 0.),
        (rect.x + rect.width, rect.y + rect.height, 1., 1.),
        (rect.x, rect.y + rect.height, 0., 1.),
    ] {
        let delta = [x - center[0], y - center[1]];
        polygon.push(Vertex {
            position: [
                center[0] + delta[0] * cos - delta[1] * sin,
                center[1] + delta[0] * sin + delta[1] * cos,
            ],
            uv: [
                (region.x as f32 + (quad.uv.x + u * quad.uv.width) * region.width as f32)
                    / tw as f32,
                (region.y as f32 + (quad.uv.y + v * quad.uv.height) * region.height as f32)
                    / th as f32,
            ],
            tint,
        });
    }
    for (axis, boundary, positive) in [
        (0, quad.clip.x, true),
        (0, quad.clip.x + quad.clip.width, false),
        (1, quad.clip.y, true),
        (1, quad.clip.y + quad.clip.height, false),
    ] {
        let mut clipped = Vec::with_capacity(8);
        if let Some(mut previous) = polygon.last().copied() {
            let inside = |point: Vertex| {
                if positive {
                    point.position[axis] >= boundary
                } else {
                    point.position[axis] <= boundary
                }
            };
            for current in polygon {
                if inside(previous) != inside(current) {
                    let t = (boundary - previous.position[axis])
                        / (current.position[axis] - previous.position[axis]);
                    clipped.push(Vertex {
                        position: std::array::from_fn(|i| {
                            previous.position[i] + t * (current.position[i] - previous.position[i])
                        }),
                        uv: std::array::from_fn(|i| {
                            previous.uv[i] + t * (current.uv[i] - previous.uv[i])
                        }),
                        tint,
                    });
                }
                if inside(current) {
                    clipped.push(current);
                }
                previous = current;
            }
        }
        polygon = clipped;
    }
    for point in &mut polygon {
        point.position = [
            (point.position[0] + viewport.origin_x()) * viewport.scale as f32
                / viewport.physical_width as f32
                * 2.
                - 1.,
            1. - point.position[1] * viewport.scale as f32 / viewport.physical_height as f32 * 2.,
        ];
    }
    for index in 1..polygon.len().saturating_sub(1) {
        vertices.extend_from_slice(&[polygon[0], polygon[index], polygon[index + 1]]);
    }
}

fn linear(channel: u8) -> f32 {
    let value = f32::from(channel) / 255.;
    if value <= 0.04045 {
        value / 12.92
    } else {
        ((value + 0.055) / 1.055).powf(2.4)
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_game_ui::Rect;

    #[test]
    fn clipping_preserves_mirrored_uvs_and_hidpi_positions() {
        let quad = Quad {
            image: UiImageId::Background,
            rect: Rect::new(-10., 0., 20., 20.),
            uv: Rect::new(1., 0., -1., 1.),
            tint: [255; 4],
            clip: Rect::new(0., 0., 765., 503.),
            rotation: 0,
        };
        let region = Region {
            x: 0,
            y: 0,
            width: 20,
            height: 20,
        };
        let viewport = TitleViewport {
            physical_width: 1800,
            physical_height: 1100,
            scale: 2.,
        };
        let mut vertices = Vec::new();
        add_quad(&mut vertices, &quad, region, 20, 20, viewport);
        assert_eq!(vertices.len(), 6);
        assert!((vertices[0].uv[0] - 0.5).abs() < f32::EPSILON);
        assert!(vertices[0].uv[1].abs() < f32::EPSILON);
        assert!(vertices[1].uv.iter().all(|v| v.abs() < f32::EPSILON));
        let expected = (viewport.origin_x() * 2.) / 1800. * 2. - 1.;
        assert!((vertices[0].position[0] - expected).abs() < 0.00001);
        let mut outside = quad;
        outside.rect = Rect::new(-30., 0., 20., 20.);
        vertices.clear();
        add_quad(&mut vertices, &outside, region, 20, 20, viewport);
        assert!(vertices.is_empty());
    }
}
