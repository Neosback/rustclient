//! Native implementations of `RustClient` platform capabilities.

#![forbid(unsafe_code)]

mod clipboard;
mod jobs;
pub use clipboard::NativeClipboard;
pub use jobs::{JobPoolStats, NativeJobPool};

mod clock;
mod read_only;
mod storage;

pub use clock::NativeClock;
pub use read_only::{BlobReadStats, DirectoryBlobs};
pub use storage::DirectoryStorage;

mod tcp;
pub use tcp::{ConnectionCancellation, NativeTcp};

mod websocket;
pub use websocket::NativeWebSocket;
