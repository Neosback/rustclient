//! Reference integer normals and baked face colors. GPU code never relights these.
use rustclient_cache::world::ModelFace;
use rustclient_model::ModelPoint;
#[derive(Debug, Clone, Copy, Default, PartialEq, Eq)]
pub(crate) struct Normal {
    pub xyz: [i32; 3],
    pub count: i32,
}
impl Normal {
    pub fn add(&mut self, other: Self) {
        for i in 0..3 {
            self.xyz[i] = self.xyz[i].wrapping_add(other.xyz[i]);
        }
        self.count = self.count.wrapping_add(other.count);
    }
    fn dot(self, sun: [i32; 3]) -> i32 {
        self.xyz[0] * sun[0] + self.xyz[1] * sun[1] + self.xyz[2] * sun[2]
    }
}
pub(crate) struct Normals {
    pub vertices: Vec<Normal>,
    pub faces: Vec<Normal>,
}
#[expect(
    clippy::cast_possible_truncation,
    reason = "reference truncates square root and normalizes to 256"
)]
#[expect(clippy::many_single_char_names, reason = "cross-product notation")]
pub(crate) fn normal(a: ModelPoint, b: ModelPoint, c: ModelPoint) -> Normal {
    let u = [b.x - a.x, b.y - a.y, b.z - a.z];
    let v = [c.x - a.x, c.y - a.y, c.z - a.z];
    let mut n = [
        u[1].wrapping_mul(v[2])
            .wrapping_sub(u[2].wrapping_mul(v[1])),
        u[2].wrapping_mul(v[0])
            .wrapping_sub(u[0].wrapping_mul(v[2])),
        u[0].wrapping_mul(v[1])
            .wrapping_sub(u[1].wrapping_mul(v[0])),
    ];
    while n.iter().any(|&n| !(-8192..=8192).contains(&n)) {
        n = n.map(|n| n >> 1);
    }
    let len = (f64::from(n[0] * n[0] + n[1] * n[1] + n[2] * n[2]).sqrt() as i32).max(1);
    Normal {
        xyz: n.map(|n| n * 256 / len),
        count: 1,
    }
}
pub(crate) fn calculate(vertices: &[ModelPoint], faces: &[ModelFace]) -> Normals {
    let mut normals = Normals {
        vertices: vec![Normal::default(); vertices.len()],
        faces: vec![Normal::default(); faces.len()],
    };
    for (i, face) in faces.iter().enumerate() {
        let [a, b, c] = face.vertices.map(|v| vertices[v as usize]);
        let n = normal(a, b, c);
        if face.render_type == 0 {
            for v in face.vertices {
                normals.vertices[v as usize].add(n);
            }
        } else if face.render_type == 1 {
            normals.faces[i] = n;
        }
    }
    normals
}
fn adjust(color: u16, light: i32, textured: bool) -> i32 {
    if textured {
        light.clamp(2, 126)
    } else {
        let color = i32::from(color);
        (color & 0xff80) + (((color & 127) * light) >> 7).clamp(2, 126)
    }
}
/// Reference `ModelData.toModel` lighting rig: ambient, contrast and sun direction.
#[derive(Debug, Clone, Copy)]
pub(crate) struct Light {
    /// Final ambient term, including the reference +64 offset.
    ambient: i32,
    /// Final contrast term already scaled by the sun-vector length.
    contrast: i32,
    sun: [i32; 3],
}
impl Light {
    /// Scenery rig: `toModel(ambient + 64, contrast + 768, -50, -10, -50)`.
    pub fn scenery(ambient: i16, contrast: i16) -> Self {
        Self::new(
            i32::from(ambient) + 64,
            i32::from(contrast) + 768,
            [-50, -10, -50],
        )
    }
    /// Player and NPC rig: sun `(-30, -50, -30)`; callers pass the reference arguments.
    pub fn entity(ambient: i32, contrast: i32) -> Self {
        Self::new(ambient, contrast, [-30, -50, -30])
    }
    #[expect(
        clippy::cast_possible_truncation,
        reason = "reference truncates the sun vector length"
    )]
    fn new(ambient: i32, contrast: i32, sun: [i32; 3]) -> Self {
        let squares: i32 = sun.iter().map(|c| c * c).sum();
        let length = f64::from(squares).sqrt() as i32;
        Self {
            ambient,
            contrast: (length * contrast) >> 8,
            sun,
        }
    }
}
/// None is reference C=-2 suppression; flat faces repeat A for upload.
pub(crate) fn bake(
    face: ModelFace,
    index: usize,
    normals: &Normals,
    merged: &[Option<Normal>],
    ambient: i16,
    contrast: i16,
) -> Option<[i32; 3]> {
    bake_lit(
        face,
        index,
        normals,
        merged,
        Light::scenery(ambient, contrast),
    )
}
pub(crate) fn bake_lit(
    face: ModelFace,
    index: usize,
    normals: &Normals,
    merged: &[Option<Normal>],
    light: Light,
) -> Option<[i32; 3]> {
    let kind = match face.alpha {
        -2 => 3,
        -1 => 2,
        _ => face.render_type,
    };
    let Light {
        ambient,
        contrast,
        sun,
    } = light;
    let textured = face.texture.is_some();
    match kind {
        0 => Some(face.vertices.map(|v| {
            let n = merged[v as usize].unwrap_or(normals.vertices[v as usize]);
            let light = ambient + n.dot(sun) / (contrast * n.count).max(1);
            adjust(face.color, light, textured)
        })),
        1 => {
            let light = ambient + normals.faces[index].dot(sun) / (contrast + contrast / 2).max(1);
            Some([adjust(face.color, light, textured); 3])
        }
        3 if !textured => Some([128; 3]),
        _ => None,
    }
}
#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn smooth_and_flat_integer_lighting_preserve_alpha_sentinels() {
        let vertices = [
            ModelPoint::new(0, 0, 0),
            ModelPoint::new(128, 0, 0),
            ModelPoint::new(0, 0, 128),
        ];
        let mut face = ModelFace {
            vertices: [0, 1, 2],
            color: 500,
            texture: None,
            render_type: 0,
            alpha: 0,
            skin: None,
            texture_mapping: None,
            bias: 0,
            priority: 0,
        };
        let normals = calculate(&vertices, &[face]);
        assert_eq!(
            normals.vertices[0],
            Normal {
                xyz: [0, -256, 0],
                count: 1
            }
        );
        assert_eq!(bake(face, 0, &normals, &[None; 3], 0, 0), Some([452; 3]));
        face.alpha = -1;
        assert_eq!(bake(face, 0, &normals, &[None; 3], 0, 0), None);
        face.alpha = -2;
        assert_eq!(bake(face, 0, &normals, &[None; 3], 0, 0), Some([128; 3]));
    }
}
