//! Classic `RuneLite` material switches; values are projections of accepted application config.

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct MaterialOptions {
    pub smooth_banding: bool,
    pub bright_textures: bool,
}
impl Default for MaterialOptions {
    fn default() -> Self {
        Self {
            smooth_banding: true,
            bright_textures: false,
        }
    }
}
impl MaterialOptions {
    pub(crate) fn shader_flags(self) -> u32 {
        u32::from(!self.smooth_banding) | (u32::from(self.bright_textures) << 1)
    }
}
