//! Cached-model animation curves: keyframe interpolation and extrapolation.
//!
//! The reference bakes every curve once at load into one sample per integer frame plus two
//! out-of-range values. Baking here evaluates the same function at the same integer frames, so
//! playback reads samples instead of solving cubic segments for every entity every cycle.
use rustclient_cache::world::{Curve, Extrapolation, Keyframe};

/// One-third as the reference's single-precision constant.
const THIRD: f32 = 0.333_333_34;
/// `Math.ulp(1.0f)`, the reference's comparison tolerance.
const EPS: f32 = f32::EPSILON;
/// Upper bound on bracketed root-refinement steps; the reference loops until convergence.
const MAX_REFINEMENT: usize = 256;

/// A curve sampled at every integer frame between its first and last keyframe.
#[derive(Debug, Clone)]
pub(super) struct BakedCurve {
    first: i32,
    samples: Vec<f32>,
    before: f32,
    after: f32,
}

impl BakedCurve {
    pub(super) fn bake(curve: &Curve) -> Self {
        let first = curve.keys[0].time;
        let last = curve.keys[curve.keys.len() - 1].time;
        #[expect(
            clippy::cast_precision_loss,
            reason = "keyframe times are 16-bit and exact in f32"
        )]
        let at = |frame: i32| evaluate(curve, frame as f32);
        Self {
            first,
            samples: (first..=last).map(at).collect(),
            before: at(first - 1),
            after: at(last + 1),
        }
    }

    /// Value at integer `frame`; frames outside the keys read the one-frame-beyond samples.
    pub(super) fn value(&self, frame: i32) -> f32 {
        let index = frame.wrapping_sub(self.first);
        if frame < self.first {
            self.before
        } else {
            usize::try_from(index)
                .ok()
                .and_then(|i| self.samples.get(i))
                .copied()
                .unwrap_or(self.after)
        }
    }
}

/// Reference `method8430`: evaluate a curve at arbitrary time `t`.
#[expect(
    clippy::cast_precision_loss,
    reason = "keyframe times are 16-bit and exact in f32"
)]
fn evaluate(curve: &Curve, t: f32) -> f32 {
    let keys = &curve.keys;
    let (first, last) = (&keys[0], &keys[keys.len() - 1]);
    if t < first.time as f32 {
        return if curve.before == Extrapolation::Constant {
            first.value
        } else {
            extrapolate(curve, t, true)
        };
    }
    if t > last.time as f32 {
        return if curve.after == Extrapolation::Constant {
            last.value
        } else {
            extrapolate(curve, t, false)
        };
    }
    // Largest key at or before `t`; `t` is inside the key range here.
    let index = keys
        .partition_point(|k| (k.time as f32) <= t)
        .saturating_sub(1);
    let key = &keys[index];
    let next = keys.get(index + 1);
    if key.out_x == 0. && key.out_y == 0. {
        return key.value;
    }
    if key.out_x == f32::MAX && key.out_y == f32::MAX {
        // Stepped: hold until the next key, then jump.
        return match next {
            Some(next) if t != key.time as f32 => next.value,
            _ => key.value,
        };
    }
    let Some(next) = next else {
        return key.value;
    };
    segment(curve.bezier, key, next, t)
}

#[expect(
    clippy::cast_precision_loss,
    reason = "keyframe times are 16-bit and exact in f32"
)]
fn segment(bezier: bool, key: &Keyframe, next: &Keyframe, t: f32) -> f32 {
    let x0 = key.time as f32;
    let y0 = key.value;
    let c1x = x0 + key.out_x * THIRD;
    let mut c1y = y0 + THIRD * key.out_y;
    let x1 = next.time as f32;
    let y1 = next.value;
    let c2x = x1 - THIRD * next.in_x;
    let mut c2y = y1 - next.in_y * THIRD;
    if bezier {
        bezier_value(x0, c1x, c2x, x1, y0, &mut c1y, &mut c2y, y1, t)
    } else {
        hermite_value(x0, c1x, c2x, x1, y0, c1y, c2y, y1, t)
    }
}

/// Value-only cubic through the slopes at both ends (reference `ObjectSound.method2336`).
#[expect(
    clippy::too_many_arguments,
    reason = "mirrors the reference control-point layout"
)]
fn hermite_value(
    x0: f32,
    c1x: f32,
    c2x: f32,
    x1: f32,
    y0: f32,
    c1y: f32,
    c2y: f32,
    y1: f32,
    t: f32,
) -> f32 {
    let dx = x1 - x0;
    let dy = y1 - y0;
    let mut run = c1x - x0;
    let slope_out = if run == 0. { 0. } else { (c1y - y0) / run };
    run = x1 - c2x;
    let slope_in = if run == 0. { 0. } else { (y1 - c2y) / run };
    let inverse_square = 1. / (dx * dx);
    let out = slope_out * dx;
    let into = dx * slope_in;
    let a = inverse_square * (into + out - dy - dy) / dx;
    let b = (dy + dy + dy - out - out - into) * inverse_square;
    let x = t - x0;
    y0 + x * (slope_out + (a * x + b) * x)
}

/// Bezier in both time and value (reference `ItemComposition.method4897`).
#[expect(
    clippy::too_many_arguments,
    reason = "mirrors the reference control-point layout"
)]
fn bezier_value(
    x0: f32,
    c1x: f32,
    c2x: f32,
    x1: f32,
    y0: f32,
    c1y: &mut f32,
    c2y: &mut f32,
    y1: f32,
    t: f32,
) -> f32 {
    let span = x1 - x0;
    if span == 0. {
        return y0;
    }
    let mut a = [(c1x - x0) / span, (c2x - x0) / span];
    let linear = a[0] == THIRD && a[1] == 0.666_666_7;
    let (original0, original1) = (a[0], a[1]);
    a[0] = a[0].max(0.);
    a[1] = a[1].min(1.);
    if a[0] > 1. || a[1] < -1. {
        a[1] = 1. - a[1];
        a[0] = a[0].max(0.);
        a[1] = a[1].max(0.);
        if (a[0] > 1. || a[1] > 1.)
            && (a[0] - 2. + a[1]) * a[0] + (a[1] - 2.) * a[1] + 1. + EPS > 0.
        {
            clamp_handles(&mut a);
        }
        a[1] = 1. - a[1];
    }
    if a[0] != original0 && original0 != 0. {
        *c1y = y0 + a[0] * (*c1y - y0) / original0;
    }
    if original1 != a[1] && original1 != 1. {
        *c2y = y1 - (1. - a[1]) * (y1 - *c2y) / (1. - original1);
    }
    let x_poly = bezier_coefficients(0., a[0], a[1], 1.);
    let y_poly = bezier_coefficients(y0, *c1y, *c2y, y1);
    let u = if t == x0 {
        0.
    } else if x1 == t {
        1.
    } else {
        (t - x0) / (x1 - x0)
    };
    let s = if linear {
        u
    } else {
        let mut roots = [0_f32; 5];
        let count = roots_in_range(
            &[x_poly[0] - u, x_poly[1], x_poly[2], x_poly[3]],
            3,
            0.,
            true,
            1.,
            true,
            &mut roots,
        );
        if count == 1 { roots[0] } else { 0. }
    };
    ((y_poly[1] + s * (y_poly[3] * s + y_poly[2])) * s) + y_poly[0]
}

/// Power-basis coefficients `[c0, c1, c2, c3]` of a cubic Bezier with the given control values.
fn bezier_coefficients(p0: f32, p1: f32, p2: f32, p3: f32) -> [f32; 4] {
    let d1 = p1 - p0;
    let d2 = p2 - p1;
    let d3 = p3 - p2;
    let dd = d2 - d1;
    [p0, d1 + d1 + d1, dd + dd + dd, d3 - d2 - dd]
}

/// Pull Bezier time handles back inside the monotonic region (reference `method2181`).
fn clamp_handles(a: &mut [f32; 2]) {
    if EPS + a[0] < 1.333_333_4 {
        let b = a[0] - 2.;
        let c = a[0] - 1.;
        #[expect(
            clippy::cast_possible_truncation,
            reason = "the reference takes the square root in double precision then narrows"
        )]
        let root = f64::from(b * b - c * 4. * c).sqrt() as f32;
        let mut limit = (root + -b) * 0.5;
        if EPS + a[1] > limit {
            a[1] = limit - EPS;
        } else {
            limit = 0.5 * (-b - root);
            if a[1] < limit + EPS {
                a[1] = EPS + limit;
            }
        }
    } else {
        a[0] = 1.333_333_4 - EPS;
        a[1] = THIRD - EPS;
    }
}

/// Horner evaluation of `coefficients[0..=degree]`, lowest order first.
fn horner(coefficients: &[f32], degree: usize, x: f32) -> f32 {
    let mut value = coefficients[degree];
    for i in (0..degree).rev() {
        value = coefficients[i] + value * x;
    }
    value
}

/// Real roots of a polynomial inside `[low, high]` (reference `class157.method4017`).
///
/// Roots are written ascending to `out`; the return is the root count, or `-1` when the
/// polynomial is numerically zero. Bracketed roots are refined with Brent's method.
#[expect(
    clippy::too_many_lines,
    clippy::many_single_char_names,
    clippy::too_many_arguments,
    clippy::cast_precision_loss,
    clippy::cast_possible_wrap,
    reason = "direct port of the reference root finder, keeping its variable roles"
)]
fn roots_in_range(
    coefficients: &[f32],
    mut degree: usize,
    low: f32,
    low_inclusive: bool,
    high: f32,
    high_inclusive: bool,
    out: &mut [f32],
) -> i32 {
    let mut sum = 0.0_f32;
    for c in &coefficients[..=degree] {
        sum += c.abs();
    }
    let tolerance = (low.abs() + high.abs()) * (degree + 1) as f32 * EPS;
    if sum <= tolerance {
        return -1;
    }
    let normalized: Vec<f32> = coefficients[..=degree]
        .iter()
        .map(|c| c * (1. / sum))
        .collect();
    while degree > 0 && normalized[degree].abs() < tolerance {
        degree -= 1;
    }
    let mut count: i32 = 0;
    if degree == 0 {
        return count;
    }
    if degree == 1 {
        out[0] = -normalized[0] / normalized[1];
        let above_low = if low_inclusive {
            low < tolerance + out[0]
        } else {
            low < out[0] - tolerance
        };
        let below_high = if high_inclusive {
            high > out[0] - tolerance
        } else {
            high > out[0] + tolerance
        };
        count = i32::from(above_low && below_high);
        if count > 0 {
            if low_inclusive && out[0] < low {
                out[0] = low;
            } else if high_inclusive && out[0] > high {
                out[0] = high;
            }
        }
        return count;
    }
    let mut derivative = vec![0.0_f32; degree + 1];
    for k in 1..=degree {
        derivative[k - 1] = normalized[k] * k as f32;
    }
    let mut turning = vec![0.0_f32; degree + 1];
    let turning_count = roots_in_range(&derivative, degree - 1, low, false, high, false, &mut turning);
    if turning_count == -1 {
        return 0;
    }
    let turning_count = usize::try_from(turning_count).unwrap_or(0);
    let mut skip_next = false;
    let mut value_at_end = 0.0_f32;
    let mut value_at_start;
    let mut end = 0.0_f32;
    for k in 0..=turning_count {
        if count as usize > degree {
            return count;
        }
        let start;
        if k == 0 {
            start = low;
            value_at_start = horner(&normalized, degree, low);
            if value_at_start.abs() <= tolerance && low_inclusive {
                out[count as usize] = low;
                count += 1;
            }
        } else {
            start = end;
            value_at_start = value_at_end;
        }
        if k == turning_count {
            end = high;
            skip_next = false;
        } else {
            end = turning[k];
        }
        value_at_end = horner(&normalized, degree, end);
        if skip_next {
            skip_next = false;
        } else if value_at_end.abs() < tolerance {
            if k != turning_count || high_inclusive {
                out[count as usize] = end;
                count += 1;
                skip_next = true;
            }
        } else if (value_at_start < 0. && value_at_end > 0.)
            || (value_at_start > 0. && value_at_end < 0.)
        {
            let slot = count as usize;
            count += 1;
            out[slot] = brent(&normalized, degree, start, end);
            if count > 1 && out[count as usize - 2] >= out[count as usize - 1] - tolerance {
                out[count as usize - 2] = (out[count as usize - 2] + out[count as usize - 1]) * 0.5;
                count -= 1;
            }
        }
    }
    count
}

/// Brent's method on a sign-changing bracket (the inner loop of `method4017`).
#[expect(
    clippy::many_single_char_names,
    reason = "direct port of the reference iteration, keeping its variable roles"
)]
fn brent(poly: &[f32], degree: usize, start: f32, end: f32) -> f32 {
    let mut a = start;
    let mut b = end;
    let mut fa = horner(poly, degree, a);
    if fa.abs() < EPS {
        return a;
    }
    let mut fb = horner(poly, degree, b);
    if fb.abs() < EPS {
        return b;
    }
    let (mut c, mut fc) = (0.0_f32, 0.0_f32);
    let (mut step, mut previous_step) = (0.0_f32, 0.0_f32);
    let mut restart = true;
    for _ in 0..MAX_REFINEMENT {
        if restart {
            c = a;
            fc = fa;
            step = b - a;
            previous_step = step;
            restart = false;
        }
        if fc.abs() < fb.abs() {
            a = b;
            b = c;
            c = a;
            fa = fb;
            fb = fc;
            fc = fa;
        }
        let tolerance = 2. * EPS * b.abs() + 0.;
        let half = (c - b) * 0.5;
        if !(half.abs() > tolerance && fb != 0.) {
            break;
        }
        if previous_step.abs() >= tolerance && fa.abs() > fb.abs() {
            let s = fb / fa;
            let (mut p, mut q);
            if c == a {
                p = s * half * 2.;
                q = 1. - s;
            } else {
                q = fa / fc;
                let r = fb / fc;
                p = s * (q * 2. * half * (q - r) - (r - 1.) * (b - a));
                q = (r - 1.) * (q - 1.) * (s - 1.);
            }
            if p > 0. {
                q = -q;
            } else {
                p = -p;
            }
            let earlier = previous_step;
            previous_step = step;
            if 2. * p < 3. * half * q - (tolerance * q).abs() && p < (earlier * 0.5 * q).abs() {
                step = p / q;
            } else {
                step = half;
                previous_step = half;
            }
        } else {
            step = half;
            previous_step = half;
        }
        a = b;
        fa = fb;
        if step.abs() > tolerance {
            b += step;
        } else if half > 0. {
            b += tolerance;
        } else {
            b -= tolerance;
        }
        fb = horner(poly, degree, b);
        if fb * (fc / fc.abs()) > 0. {
            restart = true;
        }
    }
    b
}

/// Evaluate beyond the keyed range according to the curve's extrapolation mode
/// (reference `class175.method4234`).
#[expect(
    clippy::cast_precision_loss,
    clippy::cast_possible_truncation,
    reason = "keyframe times are 16-bit; the reference truncates the cycle count to int"
)]
fn extrapolate(curve: &Curve, t: f32, before: bool) -> f32 {
    let keys = &curve.keys;
    let first_key = &keys[0];
    let last_key = &keys[keys.len() - 1];
    let first = first_key.time as f32;
    let last = last_key.time as f32;
    let span = last - first;
    if span == 0. {
        return first_key.value;
    }
    let relative = if t > last {
        (t - last) / span
    } else {
        (t - first) / span
    };
    let whole = (relative as i32) as f32;
    let fraction = (relative - whole).abs();
    let mut position = span * fraction;
    let cycles = (1. + whole).abs();
    let half = cycles / 2.;
    let odd = half - (half as i32) as f32;
    let net = last_key.value - first_key.value;
    let mode = if before { curve.before } else { curve.after };
    match mode {
        Extrapolation::Mirror => {
            let from_start = if before { odd != 0. } else { odd == 0. };
            position = if from_start {
                position + first
            } else {
                last - position
            };
        }
        Extrapolation::Repeat | Extrapolation::Offset => {
            position = if before {
                last - position
            } else {
                position + first
            };
        }
        Extrapolation::Tangent => {
            return if before {
                let run = first - t;
                let mut value = first_key.value;
                if first_key.in_x != 0. {
                    value -= first_key.in_y * run / first_key.in_x;
                }
                value
            } else {
                let run = t - last;
                let mut value = last_key.value;
                if last_key.out_x != 0. {
                    value += last_key.out_y * run / last_key.out_x;
                }
                value
            };
        }
        Extrapolation::Constant => {}
    }
    let mut value = evaluate(curve, position);
    if mode == Extrapolation::Offset {
        if before {
            value -= cycles * net;
        } else {
            value += net * cycles;
        }
    }
    value
}
