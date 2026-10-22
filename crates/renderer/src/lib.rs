//! Disposable render projection and shared `wgpu` surface bootstrap.
//!
//! The renderer owns GPU projection state only. Semantic game state, platform
//! services and window/event-loop ownership remain outside this crate.

#![forbid(unsafe_code)]

mod capture;
mod surface;
mod ui;
pub use capture::capture_ui;
pub use ui::UiStats;

pub use surface::{
    ClearColor, FrameOutcome, RenderError, RendererInitError, SurfaceRenderer, SurfaceSize,
};

mod camera;
mod world;
mod world_geometry;
mod world_target;
mod world_textures;
pub use camera::{
    CameraInput, CameraTarget, CameraViewport, GameplayCameraController, ReferenceCameraPose,
};

mod world_capture;
pub use world_capture::{capture_world, capture_world_at};
