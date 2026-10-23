//! Shared script-configured viewport shape, including reference letterboxing.
use rustclient_model::ViewportSettings;
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ViewportShape {
    pub inset: [i32; 2],
    pub size: [i32; 2],
    pub zoom: i32,
}
/// Shape a bounded logical viewport using the reference aspect constraints.
#[must_use]
pub fn viewport_shape(width: i32, height: i32, settings: ViewportSettings) -> ViewportShape {
    let mut width = i64::from(width.clamp(1, 16384));
    let mut height = i64::from(height.clamp(1, 16384));
    let factor = (height - 334).clamp(0, 100);
    let [near, far] = settings.fov.map(i64::from);
    let mut fov = near + (far - near) * factor / 100;
    let [min_fov, max_fov, min_aspect, max_aspect] = settings.clamp.map(i64::from);
    let aspect = height * fov * 512 / (width * 334);
    let mut inset = [0_i64; 2];
    if aspect < min_aspect {
        fov = min_aspect * width * 334 / (height * 512);
        if fov > max_fov {
            fov = max_fov;
            let shaped = (height * fov * 512 / (min_aspect.max(1) * 334)).clamp(1, width);
            inset[0] = (width - shaped) / 2;
            width -= 2 * inset[0];
        }
    } else if aspect > max_aspect {
        fov = max_aspect * width * 334 / (height * 512);
        if fov < min_fov {
            fov = min_fov;
            let shaped = (max_aspect * width * 334 / (fov.max(1) * 512)).clamp(1, height);
            inset[1] = (height - shaped) / 2;
            height -= 2 * inset[1];
        }
    }
    ViewportShape {
        inset: inset.map(|v| i32::try_from(v).unwrap_or(0)),
        size: [width, height].map(|v| i32::try_from(v).unwrap_or(1)),
        zoom: i32::try_from(height * fov / 334).unwrap_or(i32::MAX),
    }
}
