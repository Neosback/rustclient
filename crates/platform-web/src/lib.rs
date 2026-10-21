//! Browser/WASM implementations of `RustClient` platform capabilities.

#![forbid(unsafe_code)]

#[cfg(target_arch = "wasm32")]
mod clock;
#[cfg(target_arch = "wasm32")]
mod storage;

#[cfg(target_arch = "wasm32")]
pub use clock::{BrowserClock, BrowserClockError};
#[cfg(target_arch = "wasm32")]
pub use storage::LocalStorage;

/// Host builds compile this adapter crate so workspace-wide checks can verify
/// target gating without pretending browser capabilities exist natively.
#[cfg(not(target_arch = "wasm32"))]
pub const WEB_ADAPTER_COMPILE_ONLY: bool = true;
