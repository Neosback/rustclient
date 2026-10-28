//! Bundled shader composition; no runtime filesystem or user-supplied shader source.
pub(crate) fn world() -> String {
    [
        include_str!("shaders/colorblind.wgsl"),
        include_str!("shaders/world.wgsl"),
    ]
    .concat()
}
pub(crate) fn ui_canvas() -> String {
    [
        include_str!("shaders/colorblind.wgsl"),
        include_str!("shaders/ui_scale.wgsl"),
        include_str!("shaders/ui_xbr.wgsl"),
    ]
    .concat()
}
