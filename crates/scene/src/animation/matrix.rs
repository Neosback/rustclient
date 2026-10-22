//! Row-vector 4x4 matrices and quaternions for cached-model animation.
//!
//! The expressions below are transcribed from the reference client with their operand order
//! intact. Single-precision rounding depends on that order, so they are not simplified.
#![expect(
    clippy::many_single_char_names,
    clippy::similar_names,
    clippy::suboptimal_flops,
    clippy::too_many_lines,
    clippy::precedence,
    reason = "reference matrix arithmetic is kept in its original operation order"
)]
pub(super) type Matrix = rustclient_cache::world::Matrix4;
pub(super) const IDENTITY: Matrix = [
    1., 0., 0., 0., 0., 1., 0., 0., 0., 0., 1., 0., 0., 0., 0., 1.,
];
/// `this = this * other` in the reference's row-vector convention.
pub(super) fn multiply(m: &Matrix, o: &Matrix) -> Matrix {
    let v2 = o[12] * m[3] + o[8] * m[2] + m[0] * o[0] + o[4] * m[1];
    let v3 = m[2] * o[9] + m[0] * o[1] + o[5] * m[1] + m[3] * o[13];
    let v4 = m[3] * o[14] + o[6] * m[1] + o[2] * m[0] + m[2] * o[10];
    let v5 = m[3] * o[15] + m[2] * o[11] + o[3] * m[0] + o[7] * m[1];
    let v6 = m[6] * o[8] + m[5] * o[4] + o[0] * m[4] + m[7] * o[12];
    let v7 = o[13] * m[7] + m[5] * o[5] + o[1] * m[4] + m[6] * o[9];
    let v8 = o[14] * m[7] + m[6] * o[10] + o[2] * m[4] + m[5] * o[6];
    let v9 = o[15] * m[7] + m[4] * o[3] + m[5] * o[7] + m[6] * o[11];
    let v10 = o[12] * m[11] + o[8] * m[10] + m[8] * o[0] + m[9] * o[4];
    let v11 = o[13] * m[11] + o[1] * m[8] + o[5] * m[9] + m[10] * o[9];
    let v12 = o[14] * m[11] + m[8] * o[2] + o[6] * m[9] + m[10] * o[10];
    let v13 = m[10] * o[11] + m[9] * o[7] + m[8] * o[3] + m[11] * o[15];
    let v14 = o[12] * m[15] + o[4] * m[13] + o[0] * m[12] + m[14] * o[8];
    let v15 = o[13] * m[15] + o[1] * m[12] + o[5] * m[13] + o[9] * m[14];
    let v16 = m[15] * o[14] + o[10] * m[14] + o[6] * m[13] + m[12] * o[2];
    let v17 = m[14] * o[11] + m[13] * o[7] + o[3] * m[12] + m[15] * o[15];
    [v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12, v13, v14, v15, v16, v17]
}

pub(super) fn determinant(m: &Matrix) -> f32 {
    m[14] * m[3] * m[5] * m[8] + m[5] * m[2] * m[11] * m[12] + (m[12] * m[7] * m[1] * m[10] + (m[1] * m[4] * m[11] * m[14] + (m[9] * m[7] * m[0] * m[14] + m[10] * m[0] * m[5] * m[15] - m[0] * m[5] * m[11] * m[14] - m[9] * m[6] * m[0] * m[15] + m[13] * m[6] * m[0] * m[11] - m[10] * m[0] * m[7] * m[13] - m[15] * m[1] * m[4] * m[10]) + m[8] * m[6] * m[1] * m[15] - m[12] * m[11] * m[6] * m[1] - m[14] * m[8] * m[7] * m[1]) + m[2] * m[4] * m[9] * m[15] - m[11] * m[4] * m[2] * m[13] - m[8] * m[2] * m[5] * m[15]) + m[13] * m[7] * m[2] * m[8] - m[12] * m[9] * m[2] * m[7] - m[4] * m[3] * m[9] * m[14] + m[4] * m[3] * m[10] * m[13] - m[12] * m[5] * m[3] * m[10] - m[8] * m[6] * m[3] * m[13] + m[12] * m[6] * m[3] * m[9]
}

/// Reference adjugate inverse, with its operation order preserved.
pub(super) fn invert(m: &Matrix) -> Matrix {
    let v1 = 1.0_f32 / determinant(m);
    let v2 = v1 * (m[13] * m[11] * m[6] + (m[15] * m[5] * m[10] - m[5] * m[11] * m[14] - m[15] * m[6] * m[9]) + m[9] * m[7] * m[14] - m[13] * m[7] * m[10]);
    let v3 = (m[10] * -m[1] * m[15] + m[14] * m[1] * m[11] + m[2] * m[9] * m[15] - m[13] * m[11] * m[2] - m[14] * m[9] * m[3] + m[10] * m[3] * m[13]) * v1;
    let v4 = (m[14] * m[3] * m[5] + m[15] * m[1] * m[6] - m[1] * m[7] * m[14] - m[5] * m[2] * m[15] + m[13] * m[7] * m[2] - m[13] * m[3] * m[6]) * v1;
    let v5 = (m[6] * m[3] * m[9] + (m[6] * -m[1] * m[11] + m[10] * m[7] * m[1] + m[11] * m[5] * m[2] - m[9] * m[7] * m[2] - m[10] * m[3] * m[5])) * v1;
    let v6 = (m[12] * m[10] * m[7] + (m[14] * m[11] * m[4] + m[15] * m[10] * -m[4] + m[8] * m[6] * m[15] - m[6] * m[11] * m[12] - m[8] * m[7] * m[14])) * v1;
    let v7 = (m[14] * m[3] * m[8] + m[12] * m[2] * m[11] + (m[0] * m[10] * m[15] - m[14] * m[0] * m[11] - m[2] * m[8] * m[15]) - m[10] * m[3] * m[12]) * v1;
    let v8 = v1 * (m[15] * m[2] * m[4] + m[14] * m[0] * m[7] + -m[0] * m[6] * m[15] - m[12] * m[2] * m[7] - m[3] * m[4] * m[14] + m[3] * m[6] * m[12]);
    let v9 = v1 * (m[6] * m[0] * m[11] - m[10] * m[7] * m[0] - m[2] * m[4] * m[11] + m[8] * m[2] * m[7] + m[10] * m[4] * m[3] - m[6] * m[3] * m[8]);
    let v10 = v1 * (m[12] * m[11] * m[5] + (m[9] * m[4] * m[15] - m[13] * m[4] * m[11] - m[8] * m[5] * m[15]) + m[7] * m[8] * m[13] - m[12] * m[7] * m[9]);
    let v11 = v1 * (m[9] * -m[0] * m[15] + m[11] * m[0] * m[13] + m[15] * m[1] * m[8] - m[1] * m[11] * m[12] - m[8] * m[3] * m[13] + m[12] * m[9] * m[3]);
    let v12 = v1 * (m[12] * m[7] * m[1] + (m[15] * m[0] * m[5] - m[0] * m[7] * m[13] - m[1] * m[4] * m[15]) + m[13] * m[3] * m[4] - m[12] * m[5] * m[3]);
    let v13 = v1 * (m[8] * m[3] * m[5] + (m[4] * m[1] * m[11] + m[11] * -m[0] * m[5] + m[9] * m[0] * m[7] - m[8] * m[1] * m[7] - m[3] * m[4] * m[9]));
    let v14 = (m[9] * m[6] * m[12] + (m[5] * m[8] * m[14] + m[14] * -m[4] * m[9] + m[10] * m[4] * m[13] - m[10] * m[5] * m[12] - m[6] * m[8] * m[13])) * v1;
    let v15 = v1 * (m[12] * m[1] * m[10] + (m[14] * m[9] * m[0] - m[10] * m[0] * m[13] - m[8] * m[1] * m[14]) + m[8] * m[2] * m[13] - m[12] * m[9] * m[2]);
    let v16 = v1 * (m[12] * m[5] * m[2] + (m[6] * m[0] * m[13] + m[5] * -m[0] * m[14] + m[14] * m[4] * m[1] - m[12] * m[1] * m[6] - m[13] * m[2] * m[4]));
    let v17 = (m[2] * m[4] * m[9] + m[8] * m[6] * m[1] + (m[10] * m[0] * m[5] - m[6] * m[0] * m[9] - m[4] * m[1] * m[10]) - m[8] * m[5] * m[2]) * v1;
    [v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12, v13, v14, v15, v16, v17]
}

/// Euler angles recovered from a matrix (the reference's `method9420`).
#[expect(
    clippy::cast_possible_truncation,
    reason = "the reference narrows double-precision trigonometry to float"
)]
pub(super) fn euler(m: &Matrix) -> [f32; 3] {
    let x = (-f64::from(m[6]).asin()) as f32;
    let cos = f64::from(x).cos();
    if cos.abs() > 0.005 {
        [
            x,
            f64::from(m[2]).atan2(f64::from(m[10])) as f32,
            f64::from(m[4]).atan2(f64::from(m[5])) as f32,
        ]
    } else {
        let (y, z) = (f64::from(m[1]), f64::from(m[0]));
        let yaw = if m[6] < 0. {
            y.atan2(z) as f32
        } else {
            (-y.atan2(z)) as f32
        };
        [x, yaw, 0.]
    }
}
/// Lengths of the three basis rows, used as the bind-pose scale.
#[expect(
    clippy::cast_possible_truncation,
    reason = "the reference computes the square root in double precision then narrows"
)]
pub(super) fn row_lengths(m: &Matrix) -> [f32; 3] {
    let length = |a: f32, b: f32, c: f32| ((c * c + b * b + a * a) as f64).sqrt() as f32;
    // The reference sums the squares as z, y, x of its (x, z, y) coordinate holder.
    [
        length(m[0], m[1], m[2]),
        length(m[4], m[5], m[6]),
        length(m[8], m[9], m[10]),
    ]
}
/// Unit-quaternion components `(x, y, z, w)`.
#[derive(Clone, Copy)]
pub(super) struct Quaternion {
    x: f32,
    y: f32,
    z: f32,
    w: f32,
}
impl Quaternion {
    pub(super) const IDENTITY: Self = Self {
        x: 0.,
        y: 0.,
        z: 0.,
        w: 1.,
    };
    #[expect(
        clippy::cast_possible_truncation,
        reason = "the reference narrows double-precision sine and cosine to float"
    )]
    pub(super) fn axis_angle(x: f32, y: f32, z: f32, angle: f32) -> Self {
        let sin = f64::from(0.5_f32 * angle).sin() as f32;
        let cos = f64::from(0.5_f32 * angle).cos() as f32;
        Self {
            x: sin * x,
            y: y * sin,
            z: sin * z,
            w: cos,
        }
    }
    /// `self = self * o`.
    pub(super) fn multiply(self, o: Self) -> Self {
        let t = self;
        Self {
            x: o.w * t.x + o.x * t.w + t.z * o.y - o.z * t.y,
            y: o.y * t.w + (o.w * t.y - t.z * o.x) + t.x * o.z,
            z: t.z * o.w + o.x * t.y - t.x * o.y + o.z * t.w,
            w: o.w * t.w - o.x * t.x - o.y * t.y - t.z * o.z,
        }
    }
    /// Rotation block of an identity matrix (the reference does not normalise first).
    pub(super) fn matrix(self) -> Matrix {
        let q = self;
        let v2 = q.w * q.w;
        let v3 = q.x * q.w;
        let v4 = q.y * q.w;
        let v5 = q.w * q.z;
        let v6 = q.x * q.x;
        let v7 = q.y * q.x;
        let v8 = q.x * q.z;
        let v9 = q.y * q.y;
        let v10 = q.z * q.y;
        let v11 = q.z * q.z;
        let mut m = IDENTITY;
        m[0] = v6 + v2 - v11 - v9;
        m[1] = v7 + v5 + v7 + v5;
        m[2] = v8 + (v8 - v4 - v4);
        m[4] = v7 - v5 - v5 + v7;
        m[5] = v2 + v9 - v6 - v11;
        m[6] = v10 + v3 + v10 + v3;
        m[8] = v4 + v8 + v8 + v4;
        m[9] = v10 - v3 - v3 + v10;
        m[10] = v2 + v11 - v9 - v6;
        m
    }
}
/// A diagonal scale matrix.
pub(super) fn scale(x: f32, y: f32, z: f32) -> Matrix {
    let mut m = IDENTITY;
    m[0] = x;
    m[5] = y;
    m[10] = z;
    m
}
