//! Cache-backed title input and disposable draw projection. No GPU/platform ownership.
#![forbid(unsafe_code)]
mod context_menu;
mod controller;
mod draw;
mod flame;
pub mod gameframe;
pub mod pointer;
mod text;
pub mod widgets;
pub use context_menu::ContextMenu;
mod presentation;

pub use controller::{TitlePreferences, TitleUi};
pub use draw::{Quad, Rect, TitleViewport, UiFrame, UiImageId};
pub use flame::Flames;
pub use text::draw_text;

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
    let font = &assets.fonts[1];
    presentation::text(frame, font, 1, value, x, baseline, color, clip);
    font.width(value)
}

pub mod widget_visuals;

pub mod viewport;
