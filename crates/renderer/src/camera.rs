//! Gameplay camera arithmetic ported from the pinned deob Client and class33.
//! Coordinates here are scene-local fine units with negative Y pointing upwards.
use glam::{Mat4, Vec3};
/// A camera target derived from authoritative placement and bridge-aware terrain.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct CameraTarget {
    pub x: i32,
    pub height: i32,
    pub z: i32,
    /// Largest downward height difference in the reference nine-by-nine scan.
    pub terrain_drop: i32,
}
impl CameraTarget {
    fn bounded(self) -> Self {
        Self {
            x: self.x.clamp(0, 24576),
            height: self.height.clamp(-32768, 32767),
            z: self.z.clamp(0, 24576),
            terrain_drop: self.terrain_drop.max(0),
        }
    }
}
/// One sampled input state; OS key repeat never advances the controller.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Default)]
#[expect(
    clippy::struct_excessive_bools,
    reason = "independent held keys and middle-button input sample"
)]
pub struct CameraInput {
    pub left: bool,
    pub right: bool,
    pub up: bool,
    pub down: bool,
    pub middle: bool,
    pub pointer: [i32; 2],
}
/// Logical viewport shared by projection and pointer queries.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct CameraViewport {
    width: i32,
    height: i32,
}
impl CameraViewport {
    #[must_use]
    pub fn new(width: i32, height: i32) -> Self {
        Self {
            width: width.clamp(1, 16_384),
            height: height.clamp(1, 16_384),
        }
    }
    #[must_use]
    pub fn zoom(self) -> i32 {
        let factor = 256 + (205 - 256) * (self.height - 334).clamp(0, 100) / 100;
        // Default aspect bounds are 1..32767, so ordinary desktop viewports
        // never acquire the script-configurable letterbox bars.
        self.height * factor / 334
    }
}
/// Explicit integer output, before float GPU projection.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ReferenceCameraPose {
    pub position: [i32; 3],
    pub yaw: u16,
    pub pitch: u16,
    pub zoom: i32,
}
/// Disposable camera state; retained independently of GPU resource recreation.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct GameplayCameraController {
    focal: [i32; 3],
    yaw: i32,
    pitch: i32,
    yaw_velocity: i32,
    pitch_velocity: i32,
    terrain_distance: i32,
    clicked: [i32; 2],
    zoom_height: i32,
    zoom_width: i32,
}
impl GameplayCameraController {
    #[must_use]
    pub fn new(target: CameraTarget) -> Self {
        let target = target.bounded();
        Self {
            focal: [target.x, target.height - 50, target.z],
            yaw: 0,
            pitch: 128,
            yaw_velocity: 0,
            pitch_velocity: 0,
            terrain_distance: 0,
            clicked: [0; 2],
            zoom_height: 256,
            zoom_width: 320,
        }
    }
    /// Current camera yaw in OSRS integer units (0..2047).
    #[must_use]
    #[expect(clippy::cast_sign_loss, reason = "masked to 0..2047")]
    pub const fn yaw(&self) -> u16 {
        (self.yaw & 2047) as u16
    }
    /// Current camera pitch in OSRS integer units (128..383).
    #[must_use]
    #[expect(
        clippy::cast_sign_loss,
        clippy::cast_possible_truncation,
        reason = "pitch is clamped to 128..383"
    )]
    pub const fn pitch(&self) -> u16 {
        self.pitch as u16
    }
    /// Current camera focus coordinate in scene fine units.
    #[must_use]
    pub const fn focal(&self) -> [i32; 3] {
        self.focal
    }
    /// Set camera yaw directly (e.g. snapping to North on compass click).
    pub fn set_yaw(&mut self, yaw: u16) {
        self.yaw = i32::from(yaw & 2047);
        self.yaw_velocity = 0;
    }
    /// Advance exactly one 20 ms client cycle.
    #[expect(
        clippy::manual_midpoint,
        reason = "reference uses truncating (a+b)/2; i32::midpoint differs for signed input"
    )]
    pub fn step(&mut self, target: CameraTarget, input: CameraInput) {
        let target = target.bounded();
        let input = CameraInput {
            pointer: input.pointer.map(|v| v.clamp(-16384, 16384)),
            ..input
        };
        if (self.focal[0] - target.x).abs() > 500 || (self.focal[2] - target.z).abs() > 500 {
            self.focal[0] = target.x;
            self.focal[2] = target.z;
        } else {
            self.focal[0] += (target.x - self.focal[0]) / 16;
            self.focal[2] += (target.z - self.focal[2]) / 16;
        }
        self.focal[1] = target.height - 50;
        let distance = target.terrain_drop.saturating_mul(192).clamp(32768, 98048);
        let divisor = if distance > self.terrain_distance {
            24
        } else {
            80
        };
        self.terrain_distance += (distance - self.terrain_distance) / divisor;
        if input.middle {
            let dy = input.pointer[1] - self.clicked[1];
            let dx = self.clicked[0] - input.pointer[0];
            self.pitch_velocity = dy * 2;
            self.yaw_velocity = dx * 2;
            self.clicked[1] = if dy.abs() == 1 {
                input.pointer[1]
            } else {
                (input.pointer[1] + self.clicked[1]) / 2
            };
            self.clicked[0] = if dx.abs() == 1 {
                input.pointer[0]
            } else {
                (input.pointer[0] + self.clicked[0]) / 2
            };
        } else {
            if input.left {
                self.yaw_velocity += (-24 - self.yaw_velocity) / 2;
            } else if input.right {
                self.yaw_velocity += (24 - self.yaw_velocity) / 2;
            } else {
                self.yaw_velocity /= 2;
            }
            if input.up {
                self.pitch_velocity += (12 - self.pitch_velocity) / 2;
            } else if input.down {
                self.pitch_velocity += (-12 - self.pitch_velocity) / 2;
            } else {
                self.pitch_velocity /= 2;
            }
            self.clicked = input.pointer;
        }
        self.yaw = (self.yaw + self.yaw_velocity / 2) & 2047;
        self.pitch = (self.pitch + self.pitch_velocity / 2).clamp(128, 383);
    }
    /// Apply verified `VIEWPORT_SETZOOM` values. Wheel-script policy belongs above
    /// this arithmetic boundary and must supply the script's positive values.
    pub fn set_zoom(&mut self, height: i32, width: i32) {
        self.zoom_height = if height > 0 { height.min(32767) } else { 256 };
        self.zoom_width = if width > 0 { width.min(32767) } else { 320 };
    }
    #[must_use]
    #[expect(
        clippy::missing_panics_doc,
        reason = "camera angle bounds guarantee successful u16 conversion"
    )]
    pub fn pose(self, viewport: CameraViewport) -> ReferenceCameraPose {
        let pitch = self.pitch.max(self.terrain_distance / 256);
        let factor = (viewport.height - 334).clamp(0, 100);
        let zoom = (self.zoom_width - self.zoom_height) * factor / 100 + self.zoom_height;
        let distance = (pitch * 3 + 600) * zoom / 256;
        let (sin_pitch, cos_pitch) = trig((2048 - pitch) & 2047);
        let mut offset = [
            0,
            (-sin_pitch * distance) >> 16,
            (cos_pitch * distance) >> 16,
        ];
        let (sin_yaw, cos_yaw) = trig((2048 - self.yaw) & 2047);
        offset[0] = (offset[2] * sin_yaw) >> 16;
        offset[2] = (offset[2] * cos_yaw) >> 16;
        ReferenceCameraPose {
            position: std::array::from_fn(|i| self.focal[i] - offset[i]),
            yaw: u16::try_from(self.yaw).expect("wrapped yaw"),
            pitch: u16::try_from(pitch).expect("bounded camera pitch"),
            zoom: viewport.zoom(),
        }
    }
    #[expect(
        clippy::cast_precision_loss,
        reason = "bounded reference fine coordinates and viewport converted once for GPU projection"
    )]
    pub(crate) fn matrix(self, viewport: CameraViewport) -> Mat4 {
        let pose = self.pose(viewport);
        let scale = Mat4::from_scale(Vec3::new(pose.zoom as f32, pose.zoom as f32, 1.));
        let projection = Mat4::from_cols_array_2d(&[
            [2. / viewport.width as f32, 0., 0., 0.],
            [0., -2. / viewport.height as f32, 0., 0.],
            [0., 0., 0., 1.],
            [0., 0., 100., 0.],
        ]);
        let pitch = f32::from(pose.pitch) * std::f32::consts::TAU / 2048.;
        let yaw = f32::from(pose.yaw) * std::f32::consts::TAU / 2048.;
        let translation =
            Mat4::from_translation(-Vec3::from_array(pose.position.map(|v| v as f32)));
        scale * projection * Mat4::from_rotation_x(pitch) * Mat4::from_rotation_y(yaw) * translation
    }
}
#[expect(
    clippy::cast_possible_truncation,
    reason = "reference casts its 16.16 trigonometry table"
)]
fn trig(angle: i32) -> (i32, i32) {
    let angle = f64::from(angle) * 0.003_067_961_5;
    ((angle.sin() * 65536.) as i32, (angle.cos() * 65536.) as i32)
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn recorded_deob_input_trace_matches_every_integer_output() {
        let target = CameraTarget {
            x: 6848,
            height: -240,
            z: 6464,
            terrain_drop: 0,
        };
        let mut camera = GameplayCameraController::new(target);
        for line in include_str!("../tests/fixtures/camera/deob-trace.csv")
            .lines()
            .filter(|s| !s.starts_with('#'))
        {
            let v: Vec<i32> = line.split(',').map(|s| s.parse().unwrap()).collect();
            camera.step(
                CameraTarget {
                    x: v[8],
                    z: v[9],
                    height: v[10],
                    terrain_drop: v[11],
                },
                CameraInput {
                    left: v[1] != 0,
                    right: v[2] != 0,
                    up: v[3] != 0,
                    down: v[4] != 0,
                    middle: v[5] != 0,
                    pointer: [v[6], v[7]],
                },
            );
            assert_eq!(
                [
                    camera.yaw,
                    camera.pitch,
                    camera.yaw_velocity,
                    camera.pitch_velocity,
                    camera.terrain_distance
                ],
                v[14..19],
                "cycle {}",
                v[0]
            );
            let pose = camera.pose(CameraViewport::new(v[12], v[13]));
            assert_eq!(pose.position, v[19..22], "position cycle {}", v[0]);
            assert_eq!(
                [i32::from(pose.yaw), i32::from(pose.pitch), pose.zoom],
                v[22..25],
                "pose cycle {}",
                v[0]
            );
        }
    }
}
