//! Source-neutral model raster ordering and depth-write policy.

/// Opaque model faces may need painter ordering before their depth becomes visible to later draws.
#[derive(Debug, Default, Clone, Copy, PartialEq, Eq)]
pub enum ModelDepthPolicy {
    /// Ordinary opaque depth testing and writing in one draw.
    #[default]
    DepthWrite,
    /// Sort faces, paint with depth testing only, then write opaque depth without changing color.
    SortedDeferredWrite,
}
