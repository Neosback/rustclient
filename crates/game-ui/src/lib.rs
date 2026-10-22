//! Cache-backed title input and disposable draw projection. No GPU/platform ownership.
#![forbid(unsafe_code)]
#![allow(
    clippy::chunks_exact_to_as_chunks,
    reason = "cache sprite RGBA data is validated as exact four-byte pixels before UI projection"
)]
mod controller;
mod draw;
mod flame;
pub mod gameframe;
mod presentation;

pub use controller::{TitlePreferences, TitleUi};
pub use draw::{Quad, Rect, TitleViewport, UiFrame, UiImageId};
pub use flame::Flames;
pub use gameframe::{ContextMenuEntry, GameFrameUi};
