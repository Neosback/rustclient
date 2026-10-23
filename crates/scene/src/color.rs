//! Definition HSL and terrain lightness arithmetic; GPU performs the display transfer.
#[derive(Debug, Clone, Copy, Default, PartialEq, Eq)]
pub(crate) struct UnderlayHsl {
    pub hue: i32,
    pub saturation: i32,
    pub lightness: i32,
    pub multiplier: i32,
}
#[expect(
    clippy::float_cmp,
    clippy::many_single_char_names,
    reason = "literal reference byte-RGB to HSL truncation"
)]
fn fractions(rgb: u32) -> (f64, f64, f64) {
    let r = f64::from((rgb >> 16) & 255) / 256.;
    let g = f64::from((rgb >> 8) & 255) / 256.;
    let b = f64::from(rgb & 255) / 256.;
    let min = r.min(g).min(b);
    let max = r.max(g).max(b);
    let l = f64::midpoint(min, max);
    if min == max {
        return (0., 0., l);
    }
    let s = if l < 0.5 {
        (max - min) / (min + max)
    } else {
        (max - min) / (2. - max - min)
    };
    let h = if max == r {
        (g - b) / (max - min)
    } else if max == g {
        (b - r) / (max - min) + 2.
    } else {
        (r - g) / (max - min) + 4.
    };
    (h / 6., s, l)
}
#[expect(
    clippy::cast_possible_truncation,
    reason = "reference truncates definition HSL components"
)]
pub(crate) fn underlay(rgb: u32) -> UnderlayHsl {
    let (h, s, l) = fractions(rgb);
    let multiplier = ((if l > 0.5 { 1. - l } else { l }) * s * 512.) as i32;
    let multiplier = multiplier.max(1);
    UnderlayHsl {
        hue: (h * f64::from(multiplier)) as i32,
        saturation: ((s * 256.) as i32).clamp(0, 255),
        lightness: ((l * 256.) as i32).clamp(0, 255),
        multiplier,
    }
}
#[expect(
    clippy::cast_possible_truncation,
    reason = "reference truncates definition HSL components"
)]
pub(crate) fn overlay(rgb: u32) -> i32 {
    let (h, s, l) = fractions(rgb);
    pack(
        (h * 256.) as i32,
        ((s * 256.) as i32).clamp(0, 255),
        ((l * 256.) as i32).clamp(0, 255),
    )
}
pub(crate) fn pack(h: i32, mut s: i32, l: i32) -> i32 {
    for threshold in [179, 192, 217, 243] {
        if l > threshold {
            s /= 2;
        }
    }
    ((s / 32) << 7) + ((h / 4) << 10) + l / 2
}
pub(crate) const SKIP: i32 = 12_345_678;
pub(crate) fn light(packed: i32, light: i32) -> i32 {
    match packed {
        -2 => SKIP,
        -1 => light.clamp(2, 126),
        _ => (packed & 0xff80) + ((packed & 127) * light / 128).clamp(2, 126),
    }
}

/// Reference `Rasterizer3D_colorPalette[hsl16]` at the default 0.8 brightness: quantised byte
/// channels, then brightened. Minimap tiles are painted with palette entries, not GPU shading.
#[expect(
    clippy::cast_possible_truncation,
    clippy::cast_sign_loss,
    reason = "reference palette truncates each channel to a byte"
)]
pub(crate) fn palette_rgb(hsl: i32) -> u32 {
    fn channel(p: f64, q: f64, mut t: f64) -> f64 {
        if t > 1. {
            t -= 1.;
        }
        if t < 0. {
            t += 1.;
        }
        if 6. * t < 1. {
            p + (q - p) * 6. * t
        } else if 2. * t < 1. {
            q
        } else if 3. * t < 2. {
            p + (q - p) * (2. / 3. - t) * 6.
        } else {
            p
        }
    }
    let hue = f64::from((hsl >> 10) & 63) / 64. + 0.007_812_5;
    let saturation = f64::from((hsl >> 7) & 7) / 8. + 0.0625;
    let lightness = f64::from(hsl & 127) / 128.;
    let q = if lightness < 0.5 {
        lightness * (1. + saturation)
    } else {
        lightness + saturation - lightness * saturation
    };
    let p = 2. * lightness - q;
    let byte = |value: f64| {
        let quantised = f64::from((value * 256.) as i32 as u32) / 256.;
        ((quantised.powf(0.8) * 256.) as u32).min(255)
    };
    let rgb = (byte(channel(p, q, hue + 1. / 3.)) << 16)
        | (byte(channel(p, q, hue)) << 8)
        | byte(channel(p, q, hue - 1. / 3.));
    rgb.max(1)
}
