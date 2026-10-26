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
/// Player-facing camera preferences a plugin may set (`RuneLite` `CameraMixin`: `cameraSpeed`,
/// `invertYaw`, `invertPitch`, `pitchRelaxEnabled`). The defaults are the unmodified client.
#[derive(Debug, Clone, Copy, PartialEq)]
pub struct CameraTuning {
    /// Scales the keyboard turn targets; `0.2..=5.0`.
    pub speed: f32,
    /// Negate the yaw velocity while the camera is dragged with the middle button.
    pub invert_yaw: bool,
    /// Negate the pitch velocity while the camera is dragged with the middle button.
    pub invert_pitch: bool,
    /// Allow pitch above the vanilla 383 limit, up to 512.
    pub relax_pitch: bool,
}
impl Default for CameraTuning {
    fn default() -> Self {
        Self {
            speed: 1.,
            invert_yaw: false,
            invert_pitch: false,
            relax_pitch: false,
        }
    }
}
impl CameraTuning {
    const PITCH_MAX: i32 = 383;
    const PITCH_MAX_RELAXED: i32 = 512;
    const fn pitch_max(self) -> i32 {
        if self.relax_pitch {
            Self::PITCH_MAX_RELAXED
        } else {
            Self::PITCH_MAX
        }
    }
    /// `(int) (v + (target * speed - v) / 2)` in single precision, as the injected client does.
    #[expect(
        clippy::cast_possible_truncation,
        clippy::cast_precision_loss,
        reason = "mirrors the reference float-to-int cast of small velocities"
    )]
    fn ease(self, velocity: i32, target: f32) -> i32 {
        let current = velocity as f32;
        (current + (target * self.speed - current) / 2.) as i32
    }
}
/// Logical viewport shared by projection and pointer queries.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct CameraViewport {
    inset: [i32; 2],
    zoom: i32,
    width: i32,
    height: i32,
}
impl CameraViewport {
    #[must_use]
    pub fn new(width: i32, height: i32) -> Self {
        Self {
            width,
            height,
            inset: [0; 2],
            zoom: 0,
        }
        .with_settings(rustclient_model::ViewportSettings::default())
    }
    #[must_use]
    pub fn with_settings(self, settings: rustclient_model::ViewportSettings) -> Self {
        let shape = rustclient_game_ui::viewport::viewport_shape(self.width, self.height, settings);
        Self {
            width: shape.size[0],
            height: shape.size[1],
            inset: shape.inset,
            zoom: shape.zoom,
        }
    }
    #[must_use]
    pub const fn inset(self) -> [i32; 2] {
        self.inset
    }
    #[must_use]
    pub const fn size(self) -> [i32; 2] {
        [self.width, self.height]
    }
    #[must_use]
    pub fn zoom(self) -> i32 {
        self.zoom
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
#[derive(Debug, Clone, Copy, PartialEq)]
pub struct GameplayCameraController {
    follow_height: i32,
    focal: [i32; 3],
    yaw: i32,
    pitch: i32,
    yaw_velocity: i32,
    pitch_velocity: i32,
    terrain_distance: i32,
    clicked: [i32; 2],
    zoom_height: i32,
    zoom_width: i32,
    tuning: CameraTuning,
}
impl GameplayCameraController {
    #[must_use]
    pub fn new(target: CameraTarget) -> Self {
        let target = target.bounded();
        Self {
            follow_height: 50,
            focal: [target.x, target.height - 50, target.z],
            yaw: 0,
            pitch: 128,
            yaw_velocity: 0,
            pitch_velocity: 0,
            terrain_distance: 0,
            clicked: [0; 2],
            zoom_height: 256,
            zoom_width: 320,
            tuning: CameraTuning::default(),
        }
    }
    /// Apply plugin camera preferences; turning the relaxed pitch off pulls an over-limit pitch back.
    pub fn set_tuning(&mut self, tuning: CameraTuning) {
        self.tuning = CameraTuning {
            speed: tuning.speed.clamp(0.2, 5.),
            ..tuning
        };
        self.pitch = self.pitch.min(self.tuning.pitch_max());
    }
    /// Script-authored vertical camera offset in scene fine units.
    pub fn set_follow_height(&mut self, height: u32) {
        self.follow_height = i32::try_from(height.min(32767)).unwrap_or(32767);
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
    /// Move the disposable camera into a newly loaded map while retaining the
    /// user's yaw, pitch and zoom preferences.
    pub fn recenter(&mut self, target: CameraTarget) {
        let target = target.bounded();
        self.focal = [target.x, target.height - self.follow_height, target.z];
        self.yaw_velocity = 0;
        self.pitch_velocity = 0;
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
        self.focal[1] = target.height - self.follow_height;
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
            self.pitch_velocity = if self.tuning.invert_pitch {
                -dy * 2
            } else {
                dy * 2
            };
            self.yaw_velocity = if self.tuning.invert_yaw {
                -dx * 2
            } else {
                dx * 2
            };
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
                self.yaw_velocity = self.tuning.ease(self.yaw_velocity, -24.);
            } else if input.right {
                self.yaw_velocity = self.tuning.ease(self.yaw_velocity, 24.);
            } else {
                self.yaw_velocity /= 2;
            }
            if input.up {
                self.pitch_velocity = self.tuning.ease(self.pitch_velocity, 12.);
            } else if input.down {
                self.pitch_velocity = self.tuning.ease(self.pitch_velocity, -12.);
            } else {
                self.pitch_velocity /= 2;
            }
            self.clicked = input.pointer;
        }
        self.yaw = (self.yaw + self.yaw_velocity / 2) & 2047;
        self.pitch = (self.pitch + self.pitch_velocity / 2).clamp(128, self.tuning.pitch_max());
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
    /// Project a scene-local point through the world render matrix into logical viewport pixels.
    /// Returns absence for points behind the near plane or outside the shaped viewport.
    #[must_use]
    #[expect(
        clippy::cast_precision_loss,
        reason = "bounded scene and logical viewport coordinates"
    )]
    pub fn project_point(self, viewport: CameraViewport, point: [i32; 3]) -> Option<[f32; 2]> {
        let clip = self.matrix(viewport) * Vec3::from_array(point.map(|v| v as f32)).extend(1.);
        if !clip.is_finite() || clip.w < 100. {
            return None;
        }
        let ndc = clip.truncate() / clip.w;
        if ndc.x.abs() > 1. || ndc.y.abs() > 1. {
            return None;
        }
        Some([
            viewport.inset[0] as f32 + (ndc.x + 1.) * viewport.width as f32 / 2.,
            viewport.inset[1] as f32 + (1. - ndc.y) * viewport.height as f32 / 2.,
        ])
    }
    /// Scene-local fine-unit ray through a logical pointer, using the exact
    /// world rendering matrix. No GPU readback or semantic mutation is involved.
    #[must_use]
    #[expect(
        clippy::cast_precision_loss,
        reason = "bounded logical viewport dimensions"
    )]
    pub fn pointer_ray(
        self,
        viewport: CameraViewport,
        pointer: [f32; 2],
    ) -> Option<([f32; 3], [f32; 3])> {
        let [x, y] = [
            pointer[0] - viewport.inset[0] as f32,
            pointer[1] - viewport.inset[1] as f32,
        ];
        if !x.is_finite()
            || !y.is_finite()
            || x < 0.
            || y < 0.
            || x >= viewport.width as f32
            || y >= viewport.height as f32
        {
            return None;
        }
        let inverse = self.matrix(viewport).inverse();
        let ndc = [
            2. * x / viewport.width as f32 - 1.,
            1. - 2. * y / viewport.height as f32,
        ];
        let near = inverse.project_point3(Vec3::new(ndc[0], ndc[1], 1.));
        let far = inverse.project_point3(Vec3::new(ndc[0], ndc[1], 0.01));
        let direction = (far - near).try_normalize()?;
        Some((near.to_array(), direction.to_array()))
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

    fn target() -> CameraTarget {
        CameraTarget {
            x: 6848,
            height: -240,
            z: 6464,
            terrain_drop: 0,
        }
    }

    fn held(camera: &mut GameplayCameraController, input: CameraInput, cycles: usize) {
        for _ in 0..cycles {
            camera.step(target(), input);
        }
    }

    #[test]
    fn camera_speed_scales_the_keyboard_turn_rate() {
        let right = CameraInput {
            right: true,
            ..CameraInput::default()
        };
        let mut normal = GameplayCameraController::new(target());
        held(&mut normal, right, 6);
        let mut fast = GameplayCameraController::new(target());
        fast.set_tuning(CameraTuning {
            speed: 2.,
            ..CameraTuning::default()
        });
        held(&mut fast, right, 6);
        assert!(fast.yaw_velocity > normal.yaw_velocity);
        // The velocity settles at about the scaled target (24 * 2), not the vanilla 24.
        assert!(normal.yaw_velocity <= 24 && fast.yaw_velocity > 24);
    }

    #[test]
    fn inverting_flips_only_the_middle_button_drag() {
        let drag = CameraInput {
            middle: true,
            pointer: [100, 120],
            ..CameraInput::default()
        };
        let mut plain = GameplayCameraController::new(target());
        let mut inverted = GameplayCameraController::new(target());
        inverted.set_tuning(CameraTuning {
            invert_yaw: true,
            invert_pitch: true,
            ..CameraTuning::default()
        });
        plain.step(target(), drag);
        inverted.step(target(), drag);
        assert_eq!(inverted.yaw_velocity, -plain.yaw_velocity);
        assert_eq!(inverted.pitch_velocity, -plain.pitch_velocity);
        let up = CameraInput {
            up: true,
            ..CameraInput::default()
        };
        let mut plain = GameplayCameraController::new(target());
        let mut inverted = GameplayCameraController::new(target());
        inverted.set_tuning(CameraTuning {
            invert_pitch: true,
            ..CameraTuning::default()
        });
        held(&mut plain, up, 4);
        held(&mut inverted, up, 4);
        assert_eq!(inverted.pitch, plain.pitch, "keys are not inverted");
    }

    #[test]
    fn relaxed_pitch_reaches_512_and_turning_it_off_restores_the_limit() {
        let up = CameraInput {
            up: true,
            ..CameraInput::default()
        };
        let mut camera = GameplayCameraController::new(target());
        held(&mut camera, up, 200);
        assert_eq!(camera.pitch, 383);
        camera.set_tuning(CameraTuning {
            relax_pitch: true,
            ..CameraTuning::default()
        });
        held(&mut camera, up, 200);
        assert_eq!(camera.pitch, 512);
        camera.set_tuning(CameraTuning::default());
        assert_eq!(camera.pitch, 383);
    }
}
