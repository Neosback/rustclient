//! Port of `net.runelite.client.plugins.fps.FpsPlugin` (BSD-2, see `NOTICE`).
//!
//! The source has an overlay that prints the frame rate in the top-right corner (red while a limit is
//! being enforced, yellow otherwise) and a draw listener that sleeps after each paint to hold the
//! configured rate, a global one and an optional one while the window is unfocused. Here the overlay is
//! returned as overlay text and the limit as `PluginOutput::frame_limit`; the shell paces its redraws.
//!
//! The limit can only lower the rate. The client still renders once per 20 ms game cycle (50 per
//! second at most), so a target above 50 changes nothing, as in the source where the client itself caps
//! drawing at the cycle rate.

use crate::NativePlugin;
use rustclient_plugin_api::{
    HorizontalAlign, LayeredOverlay, OverlayCommand, OverlayLayer, PluginOutput, limits,
};
use rustclient_plugin_host::{PluginCall, PluginFault};

/// `ToplevelPreEoc.ICON10`: the logout button the resizable bottom-line layout puts in the top-right
/// corner.
const LOGOUT_BUTTON: u32 = 0x00A4_0023;
const X_OFFSET: i32 = 1;
const Y_OFFSET: i32 = 1;
/// `ascent - descent` of the 16 point `RuneScape Small` font that dynamic overlays use: 11 - 1.
const TEXT_HEIGHT: i32 = 10;
const RED: u32 = 0xFFFF_0000;
const YELLOW: u32 = 0xFFFF_FF00;
const BLACK: u32 = 0xFF00_0000;

pub(crate) struct Fps;

/// The frame limit in effect for `focused`, if one is enforced: `FpsDrawListener.reloadConfig`.
fn enforced_limit(
    limit_global: bool,
    global: i32,
    limit_unfocused: bool,
    unfocused: i32,
    focused: bool,
) -> Option<u32> {
    let enforced = limit_global || (limit_unfocused && !focused);
    let target = if limit_unfocused && !focused {
        unfocused
    } else {
        global
    };
    let fps = u32::try_from(target).unwrap_or(0);
    enforced.then(|| fps.clamp(limits::MIN_FRAME_LIMIT, limits::MAX_FRAME_LIMIT))
}

impl NativePlugin for Fps {
    fn output(&mut self, call: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
        let config = call.config;
        let client = call.client().unwrap_or_default();
        let limit = enforced_limit(
            config.boolean("limitFps"),
            config.integer("maxFps"),
            config.boolean("limitFpsUnfocused"),
            config.integer("maxFpsUnfocused"),
            client.focused,
        );
        let mut output = PluginOutput {
            frame_limit: limit,
            ..PluginOutput::default()
        };
        if config.boolean("drawFps") {
            // The resizable bottom-line layout puts the logout button where the text would go.
            let logout_width = call
                .widgets()
                .ok()
                .and_then(|widgets| widgets.iter().find(|widget| widget.id == LOGOUT_BUTTON))
                .filter(|widget| !widget.hidden)
                .map_or(0, |widget| widget.bounds.width);
            let width = i32::try_from(client.canvas.0).unwrap_or(i32::MAX);
            let x = width.saturating_sub(X_OFFSET + logout_width);
            let y = TEXT_HEIGHT + Y_OFFSET;
            let text = format!("{} FPS", client.fps);
            let color = if limit.is_some() { RED } else { YELLOW };
            // `OverlayUtil.renderTextLocation`: a black shadow one pixel down and right.
            for (dx, argb) in [(1, BLACK), (0, color)] {
                output.overlay.push(LayeredOverlay {
                    layer: OverlayLayer::Top,
                    command: OverlayCommand::Text {
                        text: text.clone(),
                        x: x + dx,
                        y: y + dx,
                        argb,
                        align: HorizontalAlign::Right,
                    },
                });
            }
        }
        Ok(output)
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn nothing_is_enforced_with_both_limits_off() {
        assert_eq!(enforced_limit(false, 50, false, 20, true), None);
        assert_eq!(enforced_limit(false, 50, false, 20, false), None);
    }

    #[test]
    fn the_global_limit_applies_focused_or_not() {
        assert_eq!(enforced_limit(true, 30, false, 20, true), Some(30));
        assert_eq!(enforced_limit(true, 30, false, 20, false), Some(30));
    }

    #[test]
    fn the_unfocused_limit_applies_only_without_focus_and_wins_over_the_global_one() {
        assert_eq!(enforced_limit(false, 30, true, 5, true), None);
        assert_eq!(enforced_limit(false, 30, true, 5, false), Some(5));
        assert_eq!(enforced_limit(true, 30, true, 5, true), Some(30));
        assert_eq!(enforced_limit(true, 30, true, 5, false), Some(5));
    }

    #[test]
    fn targets_are_kept_inside_the_accepted_range() {
        assert_eq!(enforced_limit(true, 0, false, 0, true), Some(1));
        assert_eq!(enforced_limit(true, -4, false, 0, true), Some(1));
        assert_eq!(enforced_limit(true, 1000, false, 0, true), Some(360));
    }
}
