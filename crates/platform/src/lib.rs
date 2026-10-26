//! Platform capability contracts shared by native and web adapters.
//!
//! This crate owns platform-neutral capability shapes only. Native filesystem,
//! browser APIs, windowing and transport implementations belong in adapter or
//! composition crates.

#![forbid(unsafe_code)]

mod clipboard;
pub use clipboard::{Clipboard, ClipboardError, ClipboardErrorKind};

mod clock;
mod read_only;
mod storage;

pub use clock::{Clock, MonotonicTime, WallTime};
pub use read_only::{MemoryBlobs, ReadOnlyBlobs};
pub use storage::{BlobStorage, StorageError, StorageErrorKind, StorageKey, StorageKeyError};

mod transport;
pub use transport::{ByteStream, TransportError};
