//! Bounded static geometry subset of the modern FF FD / FF FE `ModelData` families.
use crate::{
    Source,
    error::Result,
    reader::Reader,
    world::{ModelFace, StaticModel},
};
use rustclient_model::{ModelPoint, TextureId};
use std::ops::Range;
struct Layout<'a> {
    bytes: &'a [u8],
    source: Source,
    end: usize,
    offset: usize,
}
impl Layout<'_> {
    fn section(&mut self, n: usize) -> Result<Range<usize>> {
        let end = self
            .offset
            .checked_add(n)
            .filter(|e| *e <= self.end)
            .ok_or_else(|| self.source.error("model layout", "section exceeds footer"))?;
        let range = self.offset..end;
        self.offset = end;
        Ok(range)
    }
    fn reader(&self, range: Range<usize>) -> Reader<'_> {
        Reader::new(&self.bytes[range], self.source)
    }
}
fn flag(r: &mut Reader<'_>, source: Source) -> Result<bool> {
    match r.u8()? {
        0 => Ok(false),
        1 => Ok(true),
        _ => Err(source.error("model header", "invalid flag")),
    }
}
#[derive(Clone, Copy)]
#[expect(
    clippy::struct_excessive_bools,
    reason = "independent binary footer stream-presence flags, not application state"
)]
struct Header {
    modern: bool,
    end: usize,
    vertices: usize,
    faces: usize,
    textures: usize,
    render_info: bool,
    priority: u8,
    alphas: bool,
    face_skins: bool,
    face_textures: bool,
    vertex_skins: bool,
    skeletal: bool,
    x_len: usize,
    y_len: usize,
    z_len: usize,
    index_len: usize,
    selector_len: usize,
    meta_len: usize,
}
struct Sections {
    vertex_flags: Range<usize>,
    renders: Range<usize>,
    modes: Range<usize>,
    priorities: Range<usize>,
    packed: Range<usize>,
    metadata: Range<usize>,
    opacity: Range<usize>,
    indices: Range<usize>,
    face_texture: Range<usize>,
    selectors: Range<usize>,
    colors: Range<usize>,
    xs: Range<usize>,
    ys: Range<usize>,
    zs: Range<usize>,
    skins: Range<usize>,
    texture_triangles: Vec<[u32; 3]>,
    texture_types: Vec<u8>,
    biases: Vec<i8>,
}
/// Decode an OSRS static model from raw index 7 bytes.
/// # Errors
/// Returns error on corrupt model headers, unsupported versions or truncated buffers.
pub fn decode(bytes: &[u8], source: Source) -> Result<StaticModel> {
    let header = header(bytes, source)?;
    let sections = sections(bytes, source, &header)?;
    let (vertices, vertex_skins, skeletal_weights) = vertices(bytes, source, &header, &sections)?;
    let faces = faces(bytes, source, &header, &sections)?;
    Ok(StaticModel {
        vertices,
        faces,
        vertex_skins,
        skeletal_weights,
        texture_triangles: sections.texture_triangles,
        texture_types: sections.texture_types,
    })
}
fn header(bytes: &[u8], source: Source) -> Result<Header> {
    if bytes.len() < 2 {
        return Err(source.error("model", "payload too short"));
    }
    let modern = match &bytes[bytes.len() - 2..] {
        [255, 253] => true,
        [255, 254] => false,
        _ => return Err(source.error("model", "unsupported model discriminator")),
    };
    let footer_size = if modern { 26 } else { 23 };
    if bytes.len() < footer_size {
        return Err(source.error("model", "truncated footer"));
    }
    let end = bytes.len() - footer_size;
    let mut footer = Reader::new(&bytes[end..bytes.len() - 2], source);
    let vertices = usize::from(footer.u16()?);
    let faces = usize::from(footer.u16()?);
    let textures = usize::from(footer.u8()?);
    if vertices > 16384 || faces > 32768 {
        return Err(source.error("model", "geometry count limit"));
    }
    let render_info = flag(&mut footer, source)?;
    let priority = footer.u8()?;
    if priority != 255 && priority > 11 {
        return Err(source.error("model", "invalid priority"));
    }
    let alphas = flag(&mut footer, source)?;
    let face_skins = flag(&mut footer, source)?;
    let face_textures = modern && flag(&mut footer, source)?;
    let vertex_skins = flag(&mut footer, source)?;
    let skeletal = flag(&mut footer, source)?;
    let x_len = usize::from(footer.u16()?);
    let y_len = usize::from(footer.u16()?);
    let z_len = usize::from(footer.u16()?);
    let index_len = usize::from(footer.u16()?);
    let selector_len = if modern {
        usize::from(footer.u16()?)
    } else {
        0
    };
    let meta_len = usize::from(footer.u16()?);
    footer.finish()?;
    Ok(Header {
        modern,
        end,
        vertices,
        faces,
        textures,
        render_info,
        priority,
        alphas,
        face_skins,
        face_textures,
        vertex_skins,
        skeletal,
        x_len,
        y_len,
        z_len,
        index_len,
        selector_len,
        meta_len,
    })
}
#[expect(
    clippy::too_many_lines,
    reason = "audited contiguous binary section layout"
)]
fn sections(bytes: &[u8], source: Source, header: &Header) -> Result<Sections> {
    let Header {
        modern,
        end,
        vertices,
        faces,
        textures,
        render_info,
        priority,
        alphas,
        face_skins,
        face_textures,
        x_len,
        y_len,
        z_len,
        index_len,
        selector_len,
        meta_len,
        ..
    } = *header;
    let mut layout = Layout {
        bytes,
        source,
        end,
        offset: 0,
    };
    let mut texture_types = Vec::new();
    if modern {
        let range = layout.section(textures)?;
        texture_types.extend_from_slice(&bytes[range]);
        if texture_types.iter().any(|t| *t > 3) {
            return Err(source.error("model", "unsupported texture mapping"));
        }
    }
    let vertex_flags = layout.section(vertices)?;
    let renders = if modern && render_info {
        layout.section(faces)?
    } else {
        0..0
    };
    let modes = layout.section(faces)?;
    let priorities = layout.section(if priority == 255 { faces } else { 0 })?;
    let skins = layout.section(if face_skins { faces } else { 0 })?;
    let packed = layout.section(if !modern && render_info { faces } else { 0 })?;
    let metadata = layout.section(meta_len)?;
    let opacity = layout.section(if alphas { faces } else { 0 })?;
    let indices = layout.section(index_len)?;
    let face_texture = layout.section(if face_textures { faces * 2 } else { 0 })?;
    let selectors = layout.section(selector_len)?;
    let colors = layout.section(faces * 2)?;
    let old_textures = if modern {
        0..0
    } else {
        layout.section(textures * 6)?
    };
    let xs = layout.section(x_len)?;
    let ys = layout.section(y_len)?;
    let zs = layout.section(z_len)?;
    let mut texture_triangles = vec![[0; 3]; textures];
    if modern {
        let simple = texture_types
            .iter()
            .fold(0, |n, &t| n + usize::from(t == 0));
        let complex = textures - simple;
        let cubes = texture_types
            .iter()
            .fold(0, |n, &t| n + usize::from(t == 2));
        let simple_triangles = layout.section(simple * 6)?;
        let complex_triangles = layout.section(complex * 6)?;
        let mut simple = layout.reader(simple_triangles);
        let mut complex_reader = layout.reader(complex_triangles);
        for (dest, &kind) in texture_triangles.iter_mut().zip(&texture_types) {
            let r = if kind == 0 {
                &mut simple
            } else {
                &mut complex_reader
            };
            for index in dest {
                *index = u32::from(r.u16()?);
                if *index >= u32::try_from(vertices).expect("bounded model vertex count") {
                    return Err(source.error("model texture", "texture vertex outside model"));
                }
            }
        }
        layout.section(complex * 6 + complex * 2 + complex + complex * 2 + cubes * 2)?;
    } else {
        texture_types = vec![0; textures];
        let mut r = layout.reader(old_textures);
        for triangle in &mut texture_triangles {
            for index in triangle {
                *index = u32::from(r.u16()?);
                if *index >= u32::try_from(vertices).expect("bounded model vertex count") {
                    return Err(source.error("model texture", "texture vertex outside model"));
                }
            }
        }
    }
    let biases = decode_tail(&bytes[layout.offset..end], modern, faces, source)?;
    Ok(Sections {
        vertex_flags,
        renders,
        modes,
        priorities,
        packed,
        metadata,
        opacity,
        indices,
        face_texture,
        selectors,
        colors,
        xs,
        ys,
        zs,
        skins,
        texture_triangles,
        texture_types,
        biases,
    })
}
type VertexData = (Vec<ModelPoint>, Vec<Option<u8>>, Vec<Vec<(u8, u8)>>);
fn vertices(
    bytes: &[u8],
    source: Source,
    header: &Header,
    sections: &Sections,
) -> Result<VertexData> {
    let Header {
        vertices,
        vertex_skins,
        skeletal,
        ..
    } = *header;
    let reader = |range: &Range<usize>| Reader::new(&bytes[range.clone()], source);
    let mut flags = reader(&sections.vertex_flags);
    let mut xr = reader(&sections.xs);
    let mut yr = reader(&sections.ys);
    let mut zr = reader(&sections.zs);
    let mut meta = reader(&sections.metadata);
    let mut points = Vec::with_capacity(vertices);
    let mut skins = Vec::with_capacity(vertices);
    let mut weights = vec![Vec::new(); vertices];
    let mut previous = [0_i32; 3];
    for _ in 0..vertices {
        let bits = flags.u8()?;
        if bits & !7 != 0 {
            return Err(source.error("model vertices", "invalid vertex flags"));
        }
        for (axis, reader) in [&mut xr, &mut yr, &mut zr].into_iter().enumerate() {
            if bits & (1 << axis) != 0 {
                previous[axis] = previous[axis]
                    .checked_add(reader.s_smart()?)
                    .filter(|v| v.abs() <= 1_000_000)
                    .ok_or_else(|| source.error("model vertices", "coordinate limit"))?;
            }
        }
        points.push(ModelPoint::new(previous[0], previous[1], previous[2]));
        skins.push(if vertex_skins { Some(meta.u8()?) } else { None });
    }
    if skeletal {
        for bone in &mut weights {
            let count = usize::from(meta.u8()?);
            for _ in 0..count {
                bone.push((meta.u8()?, meta.u8()?));
            }
        }
    }
    flags.finish()?;
    xr.finish()?;
    yr.finish()?;
    zr.finish()?;
    meta.finish()?;
    Ok((points, skins, weights))
}
#[expect(
    clippy::too_many_lines,
    reason = "one ordered face stream contract handles both modern and packed legacy materials"
)]
fn faces(
    bytes: &[u8],
    source: Source,
    header: &Header,
    sections: &Sections,
) -> Result<Vec<ModelFace>> {
    let Header {
        modern,
        faces,
        vertices,
        textures,
        render_info,
        priority,
        alphas,
        face_textures,
        ..
    } = *header;
    let reader = |range: &Range<usize>| Reader::new(&bytes[range.clone()], source);
    let mut mr = reader(&sections.modes);
    let mut deltas = reader(&sections.indices);
    let mut color = reader(&sections.colors);
    let mut render_types = reader(&sections.renders);
    let mut old_info = reader(&sections.packed);
    let mut alpha = reader(&sections.opacity);
    let mut pr = reader(&sections.priorities);
    let mut tr = reader(&sections.face_texture);
    let mut sr = reader(&sections.selectors);
    let mut skin_reader = reader(&sections.skins);
    let mut out = Vec::with_capacity(faces);
    let (mut a, mut b, mut c, mut last) = (0_i32, 0_i32, 0_i32, 0_i32);
    for _ in 0..faces {
        match mr.u8()? {
            1 => {
                a = add_index(last, deltas.s_smart()?, source)?;
                b = add_index(a, deltas.s_smart()?, source)?;
                c = add_index(b, deltas.s_smart()?, source)?;
            }
            2 => {
                b = c;
                c = add_index(last, deltas.s_smart()?, source)?;
            }
            3 => {
                a = c;
                c = add_index(last, deltas.s_smart()?, source)?;
            }
            4 => {
                std::mem::swap(&mut a, &mut b);
                c = add_index(last, deltas.s_smart()?, source)?;
            }
            _ => return Err(source.error("model faces", "invalid triangle compression")),
        }
        last = c;
        let mut triangle = [0; 3];
        for (dest, index) in triangle.iter_mut().zip([a, b, c]) {
            *dest = u32::try_from(index)
                .map_err(|_| source.error("model faces", "negative vertex index"))?;
            if usize::try_from(*dest).map_err(|_| source.error("model faces", "vertex overflow"))?
                >= vertices
            {
                return Err(source.error("model faces", "vertex index outside model"));
            }
        }
        let mut face_color = color.u16()?;
        let mut texture = None;
        let mut texture_mapping = None;
        let mut kind = if modern && render_info {
            render_types.u8()?
        } else {
            0
        };
        if face_textures {
            let raw = tr.u16()?;
            if raw != 0 {
                texture = Some(TextureId::new(u32::from(raw - 1)));
                if textures > 0 {
                    let selector = sr.u8()?;
                    texture_mapping =
                        (selector != 0).then_some(u16::from(selector.saturating_sub(1)));
                    if selector != 0 && usize::from(selector - 1) >= textures {
                        return Err(source.error("model texture", "selector outside mappings"));
                    }
                }
            }
        }
        if !modern && render_info {
            let info = old_info.u8()?;
            kind = info & 1;
            if info & 2 != 0 {
                texture_mapping = Some(u16::from(info >> 2));
                if usize::from(info >> 2) >= textures {
                    return Err(source.error("model texture", "packed selector outside mappings"));
                }
                if face_color != 65535 {
                    texture = Some(TextureId::new(u32::from(face_color)));
                }
                face_color = 127;
            }
        }
        let raw_alpha = if alphas { alpha.u8()? } else { 0 };
        let priority = if priority == 255 { pr.u8()? } else { priority };
        if priority > 11 {
            return Err(source.error("model faces", "face priority outside 0..11"));
        }
        out.push(ModelFace {
            vertices: triangle,
            color: face_color,
            texture,
            render_type: kind.cast_signed(),
            alpha: raw_alpha.cast_signed(),
            skin: if header.face_skins {
                Some(skin_reader.u8()?)
            } else {
                None
            },
            texture_mapping,
            bias: sections.biases[out.len()],
            priority,
        });
    }
    mr.finish()?;
    deltas.finish()?;
    color.finish()?;
    render_types.finish()?;
    old_info.finish()?;
    alpha.finish()?;
    pr.finish()?;
    tr.finish()?;
    sr.finish()?;
    skin_reader.finish()?;
    Ok(out)
}

fn add_index(a: i32, b: i32, source: Source) -> Result<i32> {
    a.checked_add(b)
        .ok_or_else(|| source.error("model faces", "index overflow"))
}
fn decode_tail(bytes: &[u8], modern: bool, faces: usize, source: Source) -> Result<Vec<i8>> {
    let mut r = Reader::new(bytes, source);
    if modern && r.u8()? != 0 {
        r.take(10)?;
    }
    let mut biases = vec![0; faces];
    if r.remaining() > 0 {
        match r.u8()? {
            0 => {}
            1 => {
                for b in &mut biases {
                    *b = r.u8()?.cast_signed();
                }
            }
            _ => return Err(source.error("model trailer", "unsupported face bias extension")),
        }
    }
    r.finish()?;
    Ok(biases)
}

/// Combine multiple static models into a single composite static model.
#[must_use]
pub fn combine_models(models: &[&StaticModel]) -> StaticModel {
    let total_vertices: usize = models.iter().map(|m| m.vertices.len()).sum();
    let total_faces: usize = models.iter().map(|m| m.faces.len()).sum();
    let mut vertices = Vec::with_capacity(total_vertices);
    let mut faces = Vec::with_capacity(total_faces);
    let mut vertex_skins = Vec::with_capacity(total_vertices);
    let mut skeletal_weights = Vec::with_capacity(total_vertices);
    let mut texture_triangles = Vec::new();
    let mut texture_types = Vec::new();

    let mut vertex_base = 0u32;
    for model in models {
        vertices.extend_from_slice(&model.vertices);
        vertex_skins.extend_from_slice(&model.vertex_skins);
        skeletal_weights.extend_from_slice(&model.skeletal_weights);
        for face in &model.faces {
            let mut f = *face;
            f.vertices[0] += vertex_base;
            f.vertices[1] += vertex_base;
            f.vertices[2] += vertex_base;
            faces.push(f);
        }
        for tri in &model.texture_triangles {
            texture_triangles.push([
                tri[0] + vertex_base,
                tri[1] + vertex_base,
                tri[2] + vertex_base,
            ]);
        }
        texture_types.extend_from_slice(&model.texture_types);
        vertex_base =
            vertex_base.saturating_add(u32::try_from(model.vertices.len()).unwrap_or(u32::MAX));
    }
    StaticModel {
        vertices,
        faces,
        vertex_skins,
        skeletal_weights,
        texture_triangles,
        texture_types,
    }
}

/// Apply face recoloring, retexturing, and vertical vertex offset to a static model copy.
#[must_use]
pub fn adjusted_model(
    source: &StaticModel,
    recolors: &[(u16, u16)],
    retextures: &[(Option<TextureId>, Option<TextureId>)],
    y_offset: i32,
) -> StaticModel {
    let mut vertices = source.vertices.clone();
    if y_offset != 0 {
        for v in &mut vertices {
            v.y = v.y.saturating_add(y_offset);
        }
    }
    let mut faces = source.faces.clone();
    for f in &mut faces {
        for (from, to) in recolors {
            if f.color == *from {
                f.color = *to;
            }
        }
        for (from, to) in retextures {
            if f.texture == *from {
                f.texture = *to;
            }
        }
    }
    StaticModel {
        vertices,
        faces,
        vertex_skins: source.vertex_skins.clone(),
        skeletal_weights: source.skeletal_weights.clone(),
        texture_triangles: source.texture_triangles.clone(),
        texture_types: source.texture_types.clone(),
    }
}
