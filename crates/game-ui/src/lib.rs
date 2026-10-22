//! Cache-backed title input and disposable draw projection. No GPU/platform ownership.
#![forbid(unsafe_code)]
mod controller;
mod draw;
mod flame;
pub mod gameframe;
mod presentation;

pub use controller::{TitlePreferences, TitleUi};
pub use draw::{Quad, Rect, TitleViewport, UiFrame, UiImageId};
pub use flame::Flames;
pub use gameframe::{ContextMenuEntry, GameFrameUi};
