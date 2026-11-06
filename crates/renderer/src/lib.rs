//! Disposable render projection and shared `wgpu` surface bootstrap.
//!
//! The renderer owns GPU projection state only. Semantic game state, platform
//! services and window/event-loop ownership remain outside this crate.

#![forbid(unsafe_code)]

mod capture;
mod shader_sources;
mod surface;
mod timing;
mod ui;
mod ui_canvas;
pub use capture::{
    capture_canvas, capture_canvas_with_overlay, capture_capabilities, capture_ui,
    capture_ui_with_images, capture_ui_with_settings,
};
pub use timing::{FrameTimings, GpuTimingSample, Instrumentation};
pub use ui::{DynamicImage, DynamicImages, UiStats};
#[cfg(not(target_arch = "wasm32"))]
pub use ui::{UiPreparationMeasurement, measure_ui_preparation};
#[cfg(not(target_arch = "wasm32"))]
pub use ui::{UiRasterStep, verify_ui_raster_retention};

pub use surface::{
    ClearColor, FrameOutcome, RenderError, RendererInitError, SurfaceRenderer, SurfaceSize,
};

mod camera;
mod face_priority;
mod material_options;
mod profile;
mod settings;
mod transparency;
mod world;
pub use material_options::MaterialOptions;
pub use profile::{Negotiation, ProfileId, ProfileRequirements, ProfileSelection, negotiate};
pub use settings::{
    AntiAliasing, ColorBlind, Deviation, EffectiveGpu, GpuCapabilities, GpuPreference, GpuSettings,
    UiScaling, VsyncMode,
};
mod shell;
pub use shell::{EguiShellLayer, EguiShellStats, ShellLayer, ShellStats, capture_gpu_shell};
mod world_geometry;
mod world_pipeline;
mod world_target;
mod world_textures;
pub use camera::{
    CameraInput, CameraTarget, CameraTuning, CameraViewport, GameplayCameraController,
    ReferenceCameraPose,
};
pub use world_textures::{array_bytes as texture_array_bytes, layer_count as texture_layers};

mod world_capture;
mod world_upload;
#[cfg(not(target_arch = "wasm32"))]
pub use surface::{WorldStagingTiming, measure_world_staging};
#[cfg(not(target_arch = "wasm32"))]
pub use world_capture::measure_world_replacements;
#[cfg(not(target_arch = "wasm32"))]
pub use world_capture::{WorldUpdateTiming, measure_prepared_world_updates};
pub use world_capture::{
    capture_world, capture_world_at, capture_world_resources, capture_world_with,
};
pub use world_geometry::PreparedLocationUpdate;
#[cfg(not(target_arch = "wasm32"))]
pub use world_geometry::{ZoneResidencySample, verify_location_residency};
pub use world_upload::{PreparedWorldMesh, WorldUpdate};

pub use world::WorldGeometryStats;
