//! Per-frame roof visibility: the "top level" up to which scene tiles are drawn.
//!
//! Ported from the 240 client's `classCZ.method2850` and the call that feeds it to
//! `Scene.method4435`. Every tile carries a render level (`mesh::render_level`); a tile is drawn
//! when its level is at most the frame's top level. The top level is 3 (everything, so roofs
//! show from outside) unless the camera looks at the player from a shallow pitch through a tile
//! flagged `0x4` ("roof") on the player's plane, in which case it drops to the player's plane.
//! The "hide roofs" game option (`ClientSetting::RoofsHidden`) forces the player's plane.
use rustclient_model::Plane;

/// Number of roof slots the renderer can hide individually; slot 0 means "no roof".
pub const ROOF_SLOTS: usize = 4096;
const MASK_WORDS: usize = ROOF_SLOTS / 32;
/// Roof volumes exist for the planes whose tiles can be roofed over (geometry on plane `n + 1`
/// belongs to the volume of plane `n`).
const ROOF_PLANES: usize = 3;

/// Camera pitch (in the 128..383 gameplay-camera units) below which the roof line-of-sight
/// test runs; the client compares `pitch < 310 << 3` in its finer pitch units.
const SHALLOW_PITCH: u16 = 310;
/// Tile setting bit marking a tile that has a roof over it.
pub(crate) const ROOF: u8 = 4;

/// Everything the top-level decision reads, in scene-local fine units (128 per tile).
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct RoofView {
    /// The local player's plane.
    pub plane: Plane,
    /// The player (camera focus) position.
    pub subject: [i32; 2],
    /// The camera eye position.
    pub camera: [i32; 2],
    /// Camera pitch in gameplay-camera units (128..383).
    pub pitch: u16,
    /// Game option 1: never draw above the player's plane.
    pub roofs_hidden: bool,
}

impl RoofView {
    /// The highest render level drawn this frame.
    #[must_use]
    pub fn top_level(self, roofs: &RoofVolumes) -> u8 {
        let plane = self.plane.get();
        if self.roofs_hidden {
            return plane;
        }
        let (width, height) = (
            i32::try_from(roofs.width).unwrap_or(i32::MAX),
            i32::try_from(roofs.height).unwrap_or(i32::MAX),
        );
        let in_scene = |x: i32, y: i32| x >= 0 && y >= 0 && x < width && y < height;
        let roofed = |x: i32, y: i32| roofs.roofed(self.plane, x, y);
        let [subject_x, subject_y] = self.subject.map(|v| v >> 7);
        let mut top = 3;
        if self.pitch < SHALLOW_PITCH {
            let (x, y) = (self.camera[0] >> 7, self.camera[1] >> 7);
            if !in_scene(x, y) || !in_scene(subject_x, subject_y) {
                return plane;
            }
            walk_line([x, y], [subject_x, subject_y], |x, y| {
                if roofed(x, y) {
                    top = plane;
                }
            });
        }
        if !in_scene(subject_x, subject_y) {
            return plane;
        }
        if roofed(subject_x, subject_y) {
            top = plane;
        }
        top
    }
}

/// Visit every tile of the client's camera-to-player walk, camera tile first.
///
/// The walk is a Bresenham-style traversal with the client's 16.16 fixed-point error term
/// starting half way; it also visits the tile the error step crosses into, so the path is
/// 4-connected.
fn walk_line(from: [i32; 2], to: [i32; 2], mut visit: impl FnMut(i32, i32)) {
    let [mut x, mut y] = from;
    let [target_x, target_y] = to;
    visit(x, y);
    let dx = (target_x - x).abs();
    let dy = (target_y - y).abs();
    if dx > dy {
        let ratio = dy * 65536 / dx;
        let mut error = 32768;
        while x != target_x {
            x += (target_x - x).signum();
            visit(x, y);
            error += ratio;
            if error >= 65536 {
                error -= 65536;
                y += (target_y - y).signum();
                visit(x, y);
            }
        }
    } else if dy > 0 {
        let ratio = dx * 65536 / dy;
        let mut error = 32768;
        while y != target_y {
            y += (target_y - y).signum();
            visit(x, y);
            error += ratio;
            if error >= 65536 {
                error -= 65536;
                x += (target_x - x).signum();
                visit(x, y);
            }
        }
    }
}

/// Roof removal mode flags, matching `net.runelite.api.Scene.ROOF_FLAG_*`.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Default)]
pub struct RoofModes(pub u8);

impl RoofModes {
    /// Hide the roof above the player.
    pub const POSITION: u8 = 1;
    /// Hide the roof above the hovered tile.
    pub const HOVERED: u8 = 2;
    /// Hide the roof above the walk destination.
    pub const DESTINATION: u8 = 4;
    /// Hide the roofs between the camera and the player.
    pub const BETWEEN: u8 = 8;

    #[must_use]
    pub const fn has(self, flag: u8) -> bool {
        self.0 & flag != 0
    }
}

/// Tiles, in scene-local coordinates, that the pointer and walk logic are targeting.
#[derive(Debug, Clone, Copy, Default, PartialEq, Eq)]
pub struct RoofTargets {
    pub hovered: Option<[i32; 2]>,
    pub destination: Option<[i32; 2]>,
}

/// Set of roof slots hidden this frame, one bit per slot.
#[derive(Clone, PartialEq, Eq)]
pub struct RoofMask([u32; MASK_WORDS]);

impl Default for RoofMask {
    fn default() -> Self {
        Self([0; MASK_WORDS])
    }
}

impl std::fmt::Debug for RoofMask {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        f.debug_set()
            .entries((1..ROOF_SLOTS).filter(|slot| self.hides(*slot)))
            .finish()
    }
}

impl RoofMask {
    /// Hide `slot`; slot 0 (no roof) and out-of-range slots are ignored.
    pub fn hide(&mut self, slot: u16) {
        let slot = usize::from(slot);
        if slot != 0 && slot < ROOF_SLOTS {
            self.0[slot / 32] |= 1 << (slot % 32);
        }
    }

    #[must_use]
    pub fn hides(&self, slot: usize) -> bool {
        slot != 0 && slot < ROOF_SLOTS && self.0[slot / 32] & (1 << (slot % 32)) != 0
    }

    #[must_use]
    pub fn is_empty(&self) -> bool {
        self.0.iter().all(|word| *word == 0)
    }

    /// The mask as uploaded to the shader: bit `slot % 32` of word `slot / 32`.
    #[must_use]
    pub fn words(&self) -> &[u32; MASK_WORDS] {
        &self.0
    }
}

/// Everything the renderer and picking need to decide whether a vertex is drawn this frame.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct RoofVisibility {
    /// Highest tile render level drawn (see [`RoofView::top_level`]).
    pub top_level: u8,
    /// Roof slots hidden on top of the level cut.
    pub hidden: RoofMask,
}

impl RoofVisibility {
    /// Every level drawn and nothing hidden.
    #[must_use]
    pub fn everything() -> Self {
        Self {
            top_level: 3,
            hidden: RoofMask::default(),
        }
    }

    /// Whether a vertex with this packed render tag survives the cut.
    #[must_use]
    pub fn shows(&self, tag: u16) -> bool {
        usize::from(tag & 3) <= usize::from(self.top_level)
            && !self.hidden.hides(usize::from(tag >> 2))
    }
}

/// Contiguous roofed regions of each plane, numbered from 1.
///
/// A roof is an 8-connected component of tiles flagged `0x4` on one plane. Geometry on plane
/// `n + 1` standing over a tile of that component belongs to it, so hiding the slot hides that
/// building's roof and nothing else. Components past [`ROOF_SLOTS`] stay unnumbered (never
/// hidden individually) rather than aliasing another roof; they still count as roofed for the
/// line-of-sight cut.
#[derive(Debug, Clone, Default, PartialEq, Eq)]
pub struct RoofVolumes {
    width: usize,
    height: usize,
    flags: Vec<bool>,
    slots: Vec<u16>,
}

impl RoofVolumes {
    /// Flood-fill the roof flags of a `width` by `height` scene; `roofed(plane, x, y)` answers
    /// for scene-local tiles.
    pub(crate) fn build(
        width: usize,
        height: usize,
        mut roofed: impl FnMut(usize, usize, usize) -> bool,
    ) -> Self {
        let plane_len = width * height;
        let mut slots = vec![0_u16; ROOF_PLANES * plane_len];
        let mut all_flags = vec![false; ROOF_PLANES * plane_len];
        let mut next = 1_usize;
        let mut stack = Vec::new();
        for plane in 0..ROOF_PLANES {
            let base = plane * plane_len;
            for x in 0..width {
                for y in 0..height {
                    all_flags[base + x * height + y] = roofed(plane, x, y);
                }
            }
            for x in 0..width {
                for y in 0..height {
                    let index = x * height + y;
                    if !all_flags[base + index] || slots[base + index] != 0 {
                        continue;
                    }
                    let slot = u16::try_from(next).ok().filter(|_| next < ROOF_SLOTS);
                    next += 1;
                    // An exhausted slot space still consumes the region so it is not revisited.
                    let mark = slot.unwrap_or(u16::MAX);
                    slots[base + index] = mark;
                    stack.push((x, y));
                    while let Some((cx, cy)) = stack.pop() {
                        for nx in cx.saturating_sub(1)..=(cx + 1).min(width - 1) {
                            for ny in cy.saturating_sub(1)..=(cy + 1).min(height - 1) {
                                let at = nx * height + ny;
                                if all_flags[base + at] && slots[base + at] == 0 {
                                    slots[base + at] = mark;
                                    stack.push((nx, ny));
                                }
                            }
                        }
                    }
                }
            }
        }
        for slot in &mut slots {
            if usize::from(*slot) >= ROOF_SLOTS {
                *slot = 0;
            }
        }
        Self {
            width,
            height,
            flags: all_flags,
            slots,
        }
    }

    fn index(&self, plane: u8, x: i32, y: i32) -> Option<usize> {
        let (x, y) = (usize::try_from(x).ok()?, usize::try_from(y).ok()?);
        let plane = usize::from(plane);
        (plane < ROOF_PLANES && x < self.width && y < self.height)
            .then(|| plane * self.width * self.height + x * self.height + y)
    }

    /// Whether scene-local tile `(x, y)` of `plane` has a roof over it (flag `0x4`, or an area
    /// override); planes that cannot be roofed over are never roofed.
    #[must_use]
    pub fn roofed(&self, plane: Plane, x: i32, y: i32) -> bool {
        self.index(plane.get(), x, y).is_some_and(|i| self.flags[i])
    }

    /// The roof over scene-local tile `(x, y)` of `plane`; 0 when the tile is not roofed.
    pub(crate) fn slot(&self, plane: u8, x: i32, y: i32) -> u16 {
        self.index(plane, x, y).map_or(0, |i| self.slots[i])
    }

    /// The roof slot geometry on `source` over scene-local tile `(x, y)` belongs to: that of
    /// the plane beneath, since a roof is the floor of the plane above it.
    pub(crate) fn covering(&self, source: Plane, x: i32, y: i32) -> u16 {
        source
            .get()
            .checked_sub(1)
            .map_or(0, |below| self.slot(below, x, y))
    }
}

/// A block of world tiles on a range of planes that counts as roofed regardless of its tile
/// flags (`RoofRemovalPlugin` area overrides).
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct RoofArea {
    /// South-west world tile, inclusive.
    pub min: [i32; 2],
    /// North-east world tile, inclusive.
    pub max: [i32; 2],
    /// Lowest and highest plane, inclusive.
    pub planes: [u8; 2],
}

impl RoofArea {
    pub(crate) fn contains(&self, plane: u8, x: i32, y: i32) -> bool {
        (self.planes[0]..=self.planes[1]).contains(&plane)
            && (self.min[0]..=self.max[0]).contains(&x)
            && (self.min[1]..=self.max[1]).contains(&y)
    }
}

impl RoofView {
    /// The roofs hidden by `modes` this frame.
    ///
    /// Each target tile hides the roof of every plane from the player's up to the highest
    /// roofed plane over it, so standing on the ground floor of a two-storey building hides
    /// both of its roofs while standing upstairs hides only the top one.
    #[must_use]
    pub fn hidden(self, roofs: &RoofVolumes, modes: RoofModes, targets: RoofTargets) -> RoofMask {
        let mut mask = RoofMask::default();
        if modes.0 == 0 {
            return mask;
        }
        let mut hide_over = |x: i32, y: i32| {
            for plane in self.plane.get()..u8::try_from(ROOF_PLANES).unwrap_or(0) {
                mask.hide(roofs.slot(plane, x, y));
            }
        };
        if modes.has(RoofModes::POSITION) {
            let [x, y] = self.subject.map(|v| v >> 7);
            hide_over(x, y);
        }
        if modes.has(RoofModes::HOVERED)
            && let Some([x, y]) = targets.hovered
        {
            hide_over(x, y);
        }
        if modes.has(RoofModes::DESTINATION)
            && let Some([x, y]) = targets.destination
        {
            hide_over(x, y);
        }
        if modes.has(RoofModes::BETWEEN) {
            let [subject_x, subject_y] = self.subject.map(|v| v >> 7);
            let (x, y) = (self.camera[0] >> 7, self.camera[1] >> 7);
            walk_line([x, y], [subject_x, subject_y], &mut hide_over);
        }
        mask
    }
}
