//! Cache-backed title input and disposable draw projection. No GPU/platform ownership.
#![forbid(unsafe_code)]
mod context_menu;
mod controller;
mod draw;
mod flame;
pub mod gameframe;
pub mod widgets;
pub use context_menu::ContextMenu;
mod presentation;

pub use controller::{TitlePreferences, TitleUi};
pub use draw::{Quad, Rect, TitleViewport, UiFrame, UiImageId};
pub use flame::Flames;
pub use gameframe::GameFrameUi;

pub mod widget_visuals;
