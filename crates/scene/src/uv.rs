//! Authored texture-plane projection, independent of placement and GPU state.
use rustclient_model::ModelPoint;
#[expect(
    clippy::cast_precision_loss,
    reason = "bounded model coordinates converted to reference f32 UV arithmetic"
)]
pub(crate) fn authored(face: [ModelPoint; 3], texture: [ModelPoint; 3]) -> [[f32; 2]; 3] {
    let f = |p: ModelPoint| [p.x as f32, p.y as f32, p.z as f32];
    let texture = texture.map(f);
    let face = face.map(f);
    let sub = |a: [f32; 3], b: [f32; 3]| std::array::from_fn(|i| a[i] - b[i]);
    let cross = |a: [f32; 3], b: [f32; 3]| {
        [
            a[1] * b[2] - a[2] * b[1],
            a[2] * b[0] - a[0] * b[2],
            a[0] * b[1] - a[1] * b[0],
        ]
    };
    let dot = |a: [f32; 3], b: [f32; 3]| a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
    let tangent = sub(texture[1], texture[0]);
    let bitangent = sub(texture[2], texture[0]);
    let normal = cross(tangent, bitangent);
    let u = cross(bitangent, normal);
    let v = cross(tangent, normal);
    let inverse_u = 1. / dot(u, tangent);
    let inverse_v = 1. / dot(v, bitangent);
    face.map(|p| {
        let r = sub(p, texture[0]);
        [dot(u, r) * inverse_u, dot(v, r) * inverse_v]
    })
}
