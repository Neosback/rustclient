//! Disposable render projection and shared `wgpu` surface bootstrap.
//!
//! The renderer owns GPU projection state only. Semantic game state, platform
//! services and window/event-loop ownership remain outside this crate.

#![forbid(unsafe_code)]

mod capture;
mod shader_sources;
mod surface;
mod ui;
mod ui_canvas;
pub use capture::{
    capture_canvas, capture_canvas_with_overlay, capture_capabilities, capture_ui,
    capture_ui_with_images, capture_ui_with_settings,
};
pub use ui::{DynamicImage, DynamicImages, UiStats};

pub use surface::{
    ClearColor, FrameOutcome, RenderError, RendererInitError, SurfaceRenderer, SurfaceSize,
};

mod camera;
mod face_priority;
mod material_options;
mod settings;
mod transparency;
mod world;
pub use material_options::MaterialOptions;
pub use settings::{
    AntiAliasing, ColorBlind, Deviation, EffectiveGpu, GpuCapabilities, GpuSettings, UiScaling,
    VsyncMode,
};
mod shell;
pub use shell::{ShellLayer, ShellStats};
mod world_geometry;
mod world_target;
mod world_textures;
pub use camera::{
    CameraInput, CameraTarget, CameraTuning, CameraViewport, GameplayCameraController,
    ReferenceCameraPose,
};

mod world_capture;
pub use world_capture::{capture_world, capture_world_at, capture_world_with};
