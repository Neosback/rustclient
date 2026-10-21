//! Disposable render projection and shared `wgpu` surface bootstrap.
//!
//! The renderer owns GPU projection state only. Semantic game state, platform
//! services and window/event-loop ownership remain outside this crate.

#![forbid(unsafe_code)]

mod surface;

pub use surface::{
    ClearColor, FrameOutcome, RenderError, RendererInitError, SurfaceRenderer, SurfaceSize,
};
