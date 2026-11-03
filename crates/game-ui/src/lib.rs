//! Cache-backed title input and disposable draw projection. No GPU/platform ownership.
#![forbid(unsafe_code)]
pub mod arbitration;
mod context_menu;
mod controller;
mod draw;
mod fill_gradient;
mod flame;
pub mod gameframe;
pub mod overhead;
pub mod pointer;
mod text;
pub mod widgets;
pub use context_menu::ContextMenu;
mod presentation;
mod viewport_overlay;
pub use viewport_overlay::ViewportOverlay;

pub use controller::{TitlePreferences, TitleUi};
pub use draw::{Quad, Rect, RetainedUi, StretchedCanvas, TitleViewport, UiFrame, UiImageId};
pub mod stretch;
pub use flame::Flames;
pub use text::{draw_text, text_width};

/// Draw one line of shadowed menu-font text at a baseline, returning its advance in pixels.
pub fn shadowed_text(
    frame: &mut UiFrame,
    assets: &rustclient_cache::TitleAssets,
    value: &str,
    x: i32,
    baseline: i32,
    color: [u8; 4],
    clip: Rect,
) -> i32 {
    let font = &assets.fonts[2]; // b12_full, also used by the reference context menu.
    presentation::text(frame, font, 2, value, x, baseline, color, clip);
    font.width(value)
}

/// The stock loading message (`classLU.method7199`): plain12 text at (10,10) inside a black box
/// with a one-pixel white border at (6,6), padded by four pixels (SPEC-0023).
#[expect(
    clippy::cast_precision_loss,
    reason = "text widths and the 13 px line height are far below f32 precision"
)]
pub fn loading_box(frame: &mut UiFrame, assets: &rustclient_cache::TitleAssets, value: &str) {
    const PAD: f32 = 4.;
    const LINE: f32 = 13.;
    const WHITE: [u8; 4] = [255; 4];
    let font = &assets.fonts[1]; // p12_full
    let (x, y) = (6., 6.);
    let (w, h) = (font.width(value) as f32 + PAD * 2., LINE + PAD * 2.);
    let clip = Rect::new(0., 0., 16384., 16384.);
    frame.fill_rect(Rect::new(x, y, w, h), [0, 0, 0, 255], clip);
    for edge in [
        Rect::new(x, y, w, 1.),
        Rect::new(x, y + h - 1., w, 1.),
        Rect::new(x, y, 1., h),
        Rect::new(x + w - 1., y, 1., h),
    ] {
        frame.fill_rect(edge, WHITE, clip);
    }
    presentation::text(frame, font, 1, value, 10, 21, WHITE, clip);
}

pub mod widget_text;
pub mod widget_visuals;

pub mod viewport;
