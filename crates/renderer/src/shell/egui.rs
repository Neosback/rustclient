//! Engine-owned GPU panel painting; unchanged generations only composite the cached texture.
use super::{ShellGpu, ShellStaging};
use crate::{RenderError, SurfaceSize};
use egui::{
    TextureId,
    epaint::{ClippedPrimitive, ImageDelta, Primitive},
};
use std::collections::BTreeMap;
#[derive(Clone, Copy)]
pub struct EguiShellLayer<'a> {
    pub jobs: &'a [ClippedPrimitive],
    pub textures: &'a BTreeMap<TextureId, (ImageDelta, u64)>,
    pub size: SurfaceSize,
    pub scale: f32,
    pub generation: u64,
    pub origin: [u32; 2],
}
#[derive(Default, Debug, Clone, Copy)]
pub struct EguiShellStats {
    pub paints: u64,
    pub geometry_uploads: u64,
    pub texture_uploads: u64,
    pub texture_bytes: u64,
}
#[derive(Default)]
pub(crate) struct EguiStaging {
    pub enabled: bool,
    pending: bool,
    generation: Option<u64>,
    size: [u32; 2],
    scale: f32,
    jobs: Vec<ClippedPrimitive>,
    textures: BTreeMap<TextureId, (ImageDelta, u64)>,
    uploaded: BTreeMap<TextureId, u64>,
    renderer: Option<egui_wgpu::Renderer>,
    pub stats: EguiShellStats,
}
impl EguiStaging {
    pub fn set(
        &mut self,
        layer: EguiShellLayer<'_>,
        shell: &mut ShellStaging,
    ) -> Result<(), RenderError> {
        let size = [layer.size.width, layer.size.height];
        if size.contains(&0)
            || size.iter().any(|side| *side > 4096)
            || !layer.scale.is_finite()
            || layer.scale <= 0.
            || layer.scale > 8.
        {
            return Err(RenderError::Ui("invalid GPU shell extent"));
        }
        if self.generation != Some(layer.generation)
            || self.size != size
            || self.scale.to_bits() != layer.scale.to_bits()
        {
            let mut vertices = 0_usize;
            for job in layer.jobs {
                let Primitive::Mesh(mesh) = &job.primitive else {
                    return Err(RenderError::Ui("shell paint callbacks are not supported"));
                };
                vertices = vertices.saturating_add(mesh.vertices.len());
                if vertices > 3_000_000
                    || mesh
                        .indices
                        .iter()
                        .any(|index| *index as usize >= mesh.vertices.len())
                    || mesh
                        .vertices
                        .iter()
                        .any(|v| !v.pos.is_finite() || !v.uv.is_finite())
                {
                    return Err(RenderError::Ui("invalid or excessive shell geometry"));
                }
            }
            let mut bytes = 0_usize;
            for (image, _) in layer.textures.values() {
                let [width, height] = image.image.size();
                bytes = bytes.saturating_add(width.saturating_mul(height).saturating_mul(4));
                if image.pos.is_some()
                    || width == 0
                    || height == 0
                    || width > 4096
                    || height > 4096
                    || bytes > 64 * 1024 * 1024
                {
                    return Err(RenderError::Ui(
                        "invalid or excessive shell texture snapshots",
                    ));
                }
            }
            if layer.textures.len() > 16 {
                return Err(RenderError::Ui("too many shell textures"));
            }
            self.jobs.clear();
            self.jobs.extend_from_slice(layer.jobs);
            self.textures.clone_from(layer.textures);
            self.size = size;
            self.scale = layer.scale;
            self.generation = Some(layer.generation);
            self.pending = true;
        }
        self.enabled = true;
        shell.visible = true;
        shell.pending = false;
        shell.size = size;
        shell.origin = layer.origin;
        Ok(())
    }
    pub fn prepare(
        &mut self,
        device: &wgpu::Device,
        queue: &wgpu::Queue,
        encoder: &mut wgpu::CommandEncoder,
        gpu: &mut ShellGpu,
    ) {
        if !self.enabled || !self.pending {
            return;
        }
        gpu.ensure_size(device, self.size);
        let renderer = self.renderer.get_or_insert_with(|| {
            egui_wgpu::Renderer::new(
                device,
                wgpu::TextureFormat::Rgba8UnormSrgb,
                egui_wgpu::RendererOptions {
                    dithering: false,
                    ..Default::default()
                },
            )
        });
        let freed: Vec<_> = self
            .uploaded
            .keys()
            .filter(|id| !self.textures.contains_key(id))
            .copied()
            .collect();
        for id in freed {
            renderer.free_texture(&id);
            self.uploaded.remove(&id);
        }
        for (id, (image, generation)) in &self.textures {
            if self.uploaded.get(id) != Some(generation) {
                renderer.update_texture(device, queue, *id, image);
                self.uploaded.insert(*id, *generation);
                self.stats.texture_uploads += 1;
                self.stats.texture_bytes += (image.image.width() * image.image.height() * 4) as u64;
            }
        }
        let screen = egui_wgpu::ScreenDescriptor {
            size_in_pixels: self.size,
            pixels_per_point: self.scale,
        };
        let commands = renderer.update_buffers(device, queue, encoder, &self.jobs, &screen);
        if !commands.is_empty() {
            queue.submit(commands);
        }
        self.stats.geometry_uploads += 1;
        let view = gpu.texture.create_view(&Default::default());
        let pass = encoder.begin_render_pass(&wgpu::RenderPassDescriptor {
            label: Some("changed retained GPU shell"),
            color_attachments: &[Some(wgpu::RenderPassColorAttachment {
                view: &view,
                depth_slice: None,
                resolve_target: None,
                ops: wgpu::Operations {
                    load: wgpu::LoadOp::Clear(wgpu::Color::TRANSPARENT),
                    store: wgpu::StoreOp::Store,
                },
            })],
            ..Default::default()
        });
        renderer.render(&mut pass.forget_lifetime(), &self.jobs, &screen);
        self.stats.paints += 1;
        self.pending = false;
    }
}
