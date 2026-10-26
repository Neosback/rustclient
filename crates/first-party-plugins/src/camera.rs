//! Port of `net.runelite.client.plugins.camera.CameraPlugin` (BSD-2, see `NOTICE`), the camera preferences.
//!
//! The source forwards its settings to `Client.setCameraSpeed`, `setInvertYaw`, `setInvertPitch` and
//! `setCameraPitchRelaxerEnabled` (injected into the camera arithmetic by `CameraMixin`) and restores the
//! defaults on shutdown. Here they are returned as level-triggered camera commands: a plugin that is
//! switched off stops sending them and the shell falls back to the unmodified client.
//!
//! Not ported, because the pieces they act on are absent: the zoom limit, outer limit, control-to-zoom,
//! control-to-reset and zoom speed settings rewrite client-script variables and the scroll-wheel script
//! callbacks (this client does not run the camera zoom scripts); `disableCameraShake` has nothing to
//! disable because camera shake is not implemented; the right-click, examine, object and middle-button
//! settings remap mouse buttons depending on the open menu, which the client does not model yet.

use crate::NativePlugin;
use rustclient_plugin_api::{CameraCommand, PluginOutput};
use rustclient_plugin_host::{PluginCall, PluginFault};

pub(crate) struct Camera;

impl NativePlugin for Camera {
    fn output(&mut self, call: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
        let config = call.config;
        // `cameraSpeed` is a decimal in thousandths; `setCameraSpeed` clamps to 0.2..=5.0.
        #[expect(
            clippy::cast_precision_loss,
            reason = "thousandths of a speed setting are far below f32 integer precision"
        )]
        let speed = (config.decimal("cameraSpeed") as f32 / 1000.).clamp(0.2, 5.);
        Ok(PluginOutput {
            camera: vec![
                CameraCommand::Speed(speed),
                CameraCommand::InvertYaw(config.boolean("invertYaw")),
                CameraCommand::InvertPitch(config.boolean("invertPitch")),
                CameraCommand::RelaxPitch(config.boolean("relaxCameraPitch")),
            ],
            ..PluginOutput::default()
        })
    }
}
