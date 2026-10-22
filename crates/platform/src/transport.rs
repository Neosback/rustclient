//! Injected ordered byte stream. Scheduling and deadlines belong to adapters.
use core::fmt;

/// Stable transport failure categories; payload bytes never appear in errors.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum TransportError {
    Closed,
    Timeout,
    Cancelled,
    Unavailable,
}
impl fmt::Display for TransportError {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        f.write_str(match self {
            Self::Closed => "connection closed",
            Self::Timeout => "connection timed out",
            Self::Cancelled => "connection cancelled",
            Self::Unavailable => "transport unavailable",
        })
    }
}
impl std::error::Error for TransportError {}

/// Ordered reliable connection capability without socket or executor types.
pub trait ByteStream {
    /// Receive up to the supplied capacity. Zero means orderly end of stream.
    fn receive(&mut self, bytes: &mut [u8]) -> impl Future<Output = Result<usize, TransportError>>;
    /// Send all supplied bytes in order, or fail explicitly.
    fn send(&mut self, bytes: &[u8]) -> impl Future<Output = Result<(), TransportError>>;
}
