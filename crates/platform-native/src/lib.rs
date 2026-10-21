//! Native implementations of `RustClient` platform capabilities.

#![forbid(unsafe_code)]

mod clock;
mod storage;

pub use clock::NativeClock;
pub use storage::DirectoryStorage;
