//! GPU projection of the platform-neutral ordered quad stream.
use crate::RenderError;
use bytemuck::{Pod, Zeroable};
use rustclient_cache::{Image, TitleAssets};
use rustclient_game_ui::{Flames, Quad, TitleViewport, UiFrame, UiImageId};
mod commands;
mod geometry;
#[cfg(not(target_arch = "wasm32"))]
mod measurement;
#[cfg(not(target_arch = "wasm32"))]
mod raster_probe;
use commands::{ImageLayout, PreparedUi};
#[cfg(not(target_arch = "wasm32"))]
pub use measurement::{UiPreparationMeasurement, measure_ui_preparation};
#[cfg(not(target_arch = "wasm32"))]
pub use raster_probe::{UiRasterStep, verify_ui_raster_retention};
use std::collections::BTreeMap;

const ATLAS_WIDTH: u32 = 2048;
const ATLAS_HEIGHT: u32 = 2048;
const MAX_QUADS: usize = 32768;

/// Largest widget-sized dynamic image (minimap or compass or world map) accepted from the application.
const MAX_DYNAMIC_SIDE: u32 = 2048;

/// CPU pixels for a script-sized dynamic widget image; empty pixels keep the previous upload.
///
/// A nonzero `revision` names the pixel content: while the revision and size match what this
/// texture last received the upload is skipped. Zero always uploads. A recreated texture has
/// received nothing, so it uploads on its first frame whatever the revision.
#[derive(Clone, Copy, Debug, Default)]
pub struct DynamicImage<'a> {
    pub pixels: &'a [u8],
    pub width: u32,
    pub height: u32,
    pub revision: u64,
}
/// Script-sized widget images supplied for one frame.
#[derive(Clone, Copy, Debug, Default)]
pub struct DynamicImages<'a> {
    /// Zero preserves the legacy always-upload flame contract.
    pub flame_revision: u64,
    pub minimap: DynamicImage<'a>,
    pub compass: DynamicImage<'a>,
    pub world_map: DynamicImage<'a>,
    pub world_map_overview: DynamicImage<'a>,
}
impl DynamicImage<'_> {
    fn valid(self) -> bool {
        self.pixels.is_empty()
            || (self.width > 0
                && self.height > 0
                && self.width <= MAX_DYNAMIC_SIDE
                && self.height <= MAX_DYNAMIC_SIDE
                && u64::from(self.width) * u64::from(self.height) * 4 == self.pixels.len() as u64)
    }
}
/// A texture recreated only when the widget-authored dimensions change.
struct DynamicTexture {
    texture: wgpu::Texture,
    bind: wgpu::BindGroup,
    size: [u32; 2],
    /// Revision of the pixels last uploaded; zero when none carried a revision.
    revision: u64,
    label: &'static str,
}
impl DynamicTexture {
    fn new(
        device: &wgpu::Device,
        layout: &wgpu::BindGroupLayout,
        sampler: &wgpu::Sampler,
        label: &'static str,
    ) -> Self {
        let texture = texture(device, 1, 1, label);
        let bind = bind(device, layout, sampler, &texture);
        Self {
            texture,
            bind,
            size: [1, 1],
            revision: 0,
            label,
        }
    }
    fn upload(
        &mut self,
        device: &wgpu::Device,
        queue: &wgpu::Queue,
        layout: &wgpu::BindGroupLayout,
        sampler: &wgpu::Sampler,
        image: DynamicImage<'_>,
    ) -> u32 {
        if image.pixels.is_empty() {
            return 0;
        }
        if self.size != [image.width, image.height] {
            self.texture = texture(device, image.width, image.height, self.label);
            self.bind = bind(device, layout, sampler, &self.texture);
            self.size = [image.width, image.height];
            self.revision = 0;
        } else if image.revision != 0 && self.revision == image.revision {
            return 0;
        }
        self.revision = image.revision;
        upload(
            queue,
            &self.texture,
            image.width,
            image.height,
            image.pixels,
        );
        image.width * image.height * 4
    }
}

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
    WorldMap,
    WorldMapOverview,
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
    /// Quad commands prepared this frame, including fully clipped commands.
    pub prepared_quads: u32,
    /// Nonempty widget quads reused without preparing vertices.
    pub retained_quads: u32,
    /// Vertex bytes uploaded this frame, independent of texture uploads.
    pub vertex_upload_bytes: u32,
    /// Actual logical UI raster passes (zero when the existing canvas is reused).
    pub raster_passes: u32,
}

pub(crate) struct UiGpu {
    pipeline: wgpu::RenderPipeline,
    atlas: wgpu::BindGroup,
    flames: wgpu::BindGroup,
    flame_texture: wgpu::Texture,
    flame_revision: u64,
    layout: wgpu::BindGroupLayout,
    sampler: wgpu::Sampler,
    minimap: DynamicTexture,
    compass: DynamicTexture,
    world_map: DynamicTexture,
    world_map_overview: DynamicTexture,
    regions: BTreeMap<UiImageId, Region>,
    vertex_buffer: wgpu::Buffer,
    geometry: PreparedUi,
    draw_extent: [u32; 2],
    pub(crate) raster_changed: bool,
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
    #[allow(clippy::too_many_lines)]
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
        let atlas = bind(device, &layout, &sampler, &atlas_texture);
        let flames = bind(device, &layout, &sampler, &flame_texture);
        let minimap = DynamicTexture::new(device, &layout, &sampler, "widget dynamic minimap");
        let compass = DynamicTexture::new(device, &layout, &sampler, "widget dynamic compass");
        let world_map = DynamicTexture::new(device, &layout, &sampler, "widget dynamic world map");
        let world_map_overview = DynamicTexture::new(
            device,
            &layout,
            &sampler,
            "widget dynamic world map overview",
        );
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
            flame_revision: 0,
            layout,
            sampler,
            minimap,
            compass,
            world_map,
            world_map_overview,
            regions,
            vertex_buffer,
            geometry: PreparedUi::default(),
            draw_extent: [1, 1],
            raster_changed: true,
            stats: UiStats::default(),
        })
    }
    /// Repack the static atlas for a grown image set, keeping the pipeline, buffers and
    /// dynamic textures. Only the atlas texture and its bind group are replaced.
    pub(crate) fn replace_images(
        &mut self,
        device: &wgpu::Device,
        queue: &wgpu::Queue,
        assets: &TitleAssets,
        images: &BTreeMap<UiImageId, Image>,
    ) -> Result<(), RenderError> {
        let (pixels, regions) = atlas(assets, images)?;
        let atlas_texture = texture(device, ATLAS_WIDTH, ATLAS_HEIGHT, "title static atlas");
        upload(queue, &atlas_texture, ATLAS_WIDTH, ATLAS_HEIGHT, &pixels);
        self.atlas = bind(device, &self.layout, &self.sampler, &atlas_texture);
        self.regions = regions;
        self.geometry.invalidate();
        Ok(())
    }
    pub(crate) fn prepare(
        &mut self,
        device: &wgpu::Device,
        queue: &wgpu::Queue,
        frame: &UiFrame,
        flame_pixels: &[u8],
        images: DynamicImages<'_>,
        viewport: TitleViewport,
    ) -> Result<(), RenderError> {
        if (!flame_pixels.is_empty()
            && flame_pixels.len()
                != usize::try_from(Flames::WIDTH * Flames::HEIGHT * 4).unwrap_or(0))
            || !images.minimap.valid()
            || !images.compass.valid()
            || !images.world_map.valid()
            || !images.world_map_overview.valid()
            || frame.quads.len().saturating_add(frame.world_quads.len()) > MAX_QUADS
            || !viewport.scale.is_finite()
            || viewport.scale <= 0.
            || viewport.physical_width == 0
            || viewport.physical_height == 0
        {
            return Err(RenderError::Ui("invalid UI upload or viewport"));
        }
        self.draw_extent = [viewport.physical_width, viewport.physical_height];
        self.stats = UiStats::default();
        if !flame_pixels.is_empty()
            && (images.flame_revision == 0 || images.flame_revision != self.flame_revision)
        {
            self.flame_revision = images.flame_revision;
            upload(
                queue,
                &self.flame_texture,
                Flames::WIDTH,
                Flames::HEIGHT,
                flame_pixels,
            );
            self.stats.dynamic_upload_bytes += Flames::WIDTH * Flames::HEIGHT * 4;
        }
        self.stats.dynamic_upload_bytes +=
            self.minimap
                .upload(device, queue, &self.layout, &self.sampler, images.minimap);
        self.stats.dynamic_upload_bytes +=
            self.compass
                .upload(device, queue, &self.layout, &self.sampler, images.compass);
        self.stats.dynamic_upload_bytes +=
            self.world_map
                .upload(device, queue, &self.layout, &self.sampler, images.world_map);
        self.stats.dynamic_upload_bytes += self.world_map_overview.upload(
            device,
            queue,
            &self.layout,
            &self.sampler,
            images.world_map_overview,
        );
        let layout = ImageLayout {
            regions: &self.regions,
            dynamic: [
                self.minimap.size,
                self.compass.size,
                self.world_map.size,
                self.world_map_overview.size,
            ],
        };
        let upload = self.geometry.prepare(frame, layout, viewport)?;
        self.raster_changed = !self.geometry.unchanged() || self.stats.dynamic_upload_bytes != 0;
        self.stats.quads = self.geometry.quads();
        self.stats.draw_calls = u32::try_from(self.geometry.draws().count())
            .map_err(|_| RenderError::Ui("draw overflow"))?;
        self.stats.prepared_quads = self.geometry.prepared_quads();
        self.stats.retained_quads = self.geometry.retained_quads();
        let vertices = &self.geometry.vertices[upload.clone()];
        self.stats.vertex_upload_bytes = u32::try_from(std::mem::size_of_val(vertices))
            .map_err(|_| RenderError::Ui("vertex byte overflow"))?;
        if !vertices.is_empty() {
            queue.write_buffer(
                &self.vertex_buffer,
                (upload.start * std::mem::size_of::<Vertex>()) as u64,
                bytemuck::cast_slice(vertices),
            );
        }
        Ok(())
    }
    pub(crate) fn draw(&self, pass: &mut wgpu::RenderPass<'_>) {
        // Vertices are normalized to the game canvas, which excludes application chrome.
        // The raster viewport MUST use that same extent, even on a wider window surface.
        #[expect(
            clippy::cast_precision_loss,
            reason = "bounded physical framebuffer extents"
        )]
        pass.set_viewport(
            0.,
            0.,
            self.draw_extent[0] as f32,
            self.draw_extent[1] as f32,
            0.,
            1.,
        );
        pass.set_scissor_rect(0, 0, self.draw_extent[0], self.draw_extent[1]);
        pass.set_pipeline(&self.pipeline);
        pass.set_vertex_buffer(0, self.vertex_buffer.slice(..));
        for draw in self.geometry.draws() {
            pass.set_bind_group(
                IMAGE_GROUP,
                match draw.source {
                    DrawSource::Atlas => &self.atlas,
                    DrawSource::Flames => &self.flames,
                    DrawSource::Minimap => &self.minimap.bind,
                    DrawSource::Compass => &self.compass.bind,
                    DrawSource::WorldMap => &self.world_map.bind,
                    DrawSource::WorldMapOverview => &self.world_map_overview.bind,
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
        format: wgpu::TextureFormat::Rgba8Unorm,
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
