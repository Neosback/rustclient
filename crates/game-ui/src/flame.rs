//! Classic title fire: explicit 20 ms steps and caller-supplied deterministic samples.
use rustclient_cache::Sprite;

const AREA: usize = 128 * 256;
/// Cosmetic flame simulation. Contains no game/session authority or platform clock.
pub struct Flames {
    heat: Vec<i32>,
    noise: Vec<i32>,
    scratch: Vec<i32>,
    blur: Vec<i32>,
    offsets: [i32; 256],
    phase: u32,
    noise_offset: usize,
    cycle: u64,
    sparks: usize,
    timers: [i32; 2],
    palettes: [[u32; 256]; 3],
    pixels: Vec<u8>,
}
impl Flames {
    /// Width of the RGBA upload, including horizontal wave padding.
    pub const WIDTH: u32 = 192;
    /// Height of the RGBA upload.
    pub const HEIGHT: u32 = 256;
    /// Initialize from cache palette stops and an injected raw random sample function.
    #[must_use]
    pub fn new(stops: [[u32; 5]; 3], mut sample: impl FnMut() -> u64) -> Self {
        let mut result = Self {
            heat: vec![0; AREA],
            noise: vec![0; AREA],
            scratch: vec![0; AREA],
            blur: vec![0; AREA],
            offsets: [0; 256],
            phase: 0,
            noise_offset: 0,
            cycle: 0,
            sparks: 0,
            timers: [0; 2],
            palettes: stops.map(palette),
            pixels: vec![0; 192 * 256 * 4],
        };
        result.reseed(None, &mut sample);
        result
    }
    /// Advance exactly one cosmetic step; no implicit time or entropy reads.
    pub fn step(&mut self, runes: &[Sprite], mut sample: impl FnMut() -> u64) {
        self.noise_offset += 128;
        if self.noise_offset > AREA {
            self.noise_offset -= AREA;
            let rune = if runes.is_empty() {
                None
            } else {
                runes.get(random(&mut sample, runes.len()))
            };
            self.reseed(rune, &mut sample);
        }
        for i in 0..AREA - 128 {
            self.heat[i] =
                (self.heat[i + 128] - self.noise[(i + self.noise_offset) & (AREA - 1)] / 6).max(0);
        }
        for x in 0..128 {
            self.heat[AREA - 128 + x] = if random(&mut sample, 100) < 50 && x > 10 && x < 118 {
                255
            } else {
                0
            };
        }
        for timer in &mut self.timers {
            *timer = (*timer - 4).max(0);
        }
        if self.timers == [0; 2] {
            match random(&mut sample, 2000) {
                0 => self.timers[0] = 1024,
                1 => self.timers[1] = 1024,
                _ => {}
            }
        }
        self.offsets.copy_within(1.., 0);
        self.offsets[255] = wave(self.phase);
        self.phase = self.phase.wrapping_add(1);
        self.sparks += 100;
        if self.cycle & 1 != 0 {
            for _ in 0..self.sparks {
                let x = random(&mut sample, 124) + 2;
                let y = random(&mut sample, 128) + 128;
                self.heat[y * 128 + x] = 192;
            }
            self.sparks = 0;
            // Radius-one separable box blur, including zero-valued edge samples.
            for y in 0..256 {
                for x in 0..128 {
                    let i = y * 128 + x;
                    self.blur[i] = (self.heat[i]
                        + if x > 0 { self.heat[i - 1] } else { 0 }
                        + if x < 127 { self.heat[i + 1] } else { 0 })
                        / 3;
                }
            }
            for y in 0..256 {
                for x in 0..128 {
                    let i = y * 128 + x;
                    self.heat[i] = (self.blur[i]
                        + if y > 0 { self.blur[i - 128] } else { 0 }
                        + if y < 255 { self.blur[i + 128] } else { 0 })
                        / 3;
                }
            }
        }
        self.cycle = self.cycle.wrapping_add(1);
        self.project();
    }
    /// Current straight-alpha RGBA upload. Buffer capacity is retained.
    #[must_use]
    pub fn pixels(&self) -> &[u8] {
        &self.pixels
    }
    fn reseed(&mut self, rune: Option<&Sprite>, sample: &mut impl FnMut() -> u64) {
        self.noise.fill(0);
        self.scratch.fill(0);
        for _ in 0..5000 {
            let i = random(sample, AREA);
            self.noise[i] = i32::try_from(random(sample, 256)).unwrap_or(0);
        }
        for _ in 0..20 {
            for y in 1..255 {
                for x in 1..127 {
                    let i = y * 128 + x;
                    self.scratch[i] = (self.noise[i - 128]
                        + self.noise[i + 128]
                        + self.noise[i - 1]
                        + self.noise[i + 1])
                        / 4;
                }
            }
            std::mem::swap(&mut self.noise, &mut self.scratch);
        }
        if let Some(rune) = rune {
            let width = usize::try_from(rune.image.width()).unwrap_or(0);
            for (i, pixel) in rune.image.pixels().as_chunks::<4>().0.iter().enumerate() {
                if pixel[3] != 0 && width != 0 {
                    let x = i % width + usize::from(rune.offset[0]) + 16;
                    let y = i / width + usize::from(rune.offset[1]) + 16;
                    if x < 128 && y < 256 {
                        self.noise[y * 128 + x] = 0;
                    }
                }
            }
        }
    }
    fn project(&mut self) {
        self.pixels.fill(0);
        for y in 0..254 {
            let row = i32::try_from(y + 1).unwrap_or(0);
            let shift = (256 - row) * self.offsets[y + 1] / 256;
            for x in 0..128 {
                let intensity = usize::try_from(self.heat[y * 128 + x].clamp(0, 255)).unwrap_or(0);
                let color = if self.timers[0] > 0 {
                    self.color(intensity, 0)
                } else if self.timers[1] > 0 {
                    self.color(intensity, 1)
                } else {
                    self.palettes[0][intensity]
                };
                let Some(dx) = usize::try_from(i32::try_from(x).unwrap_or(0) + shift + 32)
                    .ok()
                    .filter(|&dx| dx < 192)
                else {
                    continue;
                };
                let i = (y * 192 + dx) * 4;
                let rgb = color.to_be_bytes();
                self.pixels[i..i + 4].copy_from_slice(&[
                    rgb[1],
                    rgb[2],
                    rgb[3],
                    u8::try_from(intensity).unwrap_or(0),
                ]);
            }
        }
    }
    fn color(&self, intensity: usize, which: usize) -> u32 {
        let timer = self.timers[which];
        let base = self.palettes[0][intensity];
        let alternate = self.palettes[which + 1][intensity];
        if timer > 768 {
            blend(
                base,
                alternate,
                u32::try_from(1024 - timer).unwrap_or(0),
                256,
            )
        } else if timer > 256 {
            alternate
        } else {
            blend(
                alternate,
                base,
                u32::try_from(256 - timer).unwrap_or(0),
                256,
            )
        }
    }
}
fn random(sample: &mut impl FnMut() -> u64, upper: usize) -> usize {
    usize::try_from(sample() % u64::try_from(upper).unwrap_or(1)).unwrap_or(0)
}
#[expect(
    clippy::cast_possible_truncation,
    reason = "sine wave is bounded to +/-42 logical pixels"
)]
fn wave(phase: u32) -> i32 {
    let phase = f64::from(phase);
    ((phase / 14.).sin() * 16. + (phase / 15.).sin() * 14. + (phase / 16.).sin() * 12.) as i32
}
fn palette(stops: [u32; 5]) -> [u32; 256] {
    std::array::from_fn(|i| {
        blend(
            stops[i / 64],
            stops[i / 64 + 1],
            u32::try_from(i % 64).unwrap_or(0),
            64,
        )
    })
}
fn blend(a: u32, b: u32, weight: u32, denominator: u32) -> u32 {
    let mut result = 0;
    for shift in [0, 8, 16] {
        let channel = (((a >> shift) & 255) * (denominator - weight)
            + ((b >> shift) & 255) * weight)
            / denominator;
        result |= channel << shift;
    }
    result
}
