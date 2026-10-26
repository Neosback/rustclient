//! Native implementations of `RustClient` platform capabilities.

#![forbid(unsafe_code)]

mod clipboard;
pub use clipboard::NativeClipboard;

mod clock;
mod read_only;
mod storage;

pub use clock::NativeClock;
pub use read_only::DirectoryBlobs;
pub use storage::DirectoryStorage;

mod tcp;
pub use tcp::{ConnectionCancellation, NativeTcp};
