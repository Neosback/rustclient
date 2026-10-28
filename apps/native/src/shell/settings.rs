//! Projection of accepted configuration into native effects and availability notes.
use rustclient_application::builtin_config::value;
use rustclient_plugin_host::configuration::{ConfigDocument, ConfigSchema, ConfigValue};
use rustclient_renderer::{
    AntiAliasing, ColorBlind, EffectiveGpu, GpuSettings, MaterialOptions, VsyncMode,
};
use std::collections::BTreeMap;
use winit::window::{Window, WindowLevel};

pub(super) fn availability(plugin: &str, key: &str) -> Option<String> {
    if key == rustclient_plugin_host::configuration::ENABLED_KEY {
        return None;
    }
    let implemented = match plugin {
        "dev.rustclient.roofremoval" => matches!(
            key,
            "removePosition" | "removeHovered" | "removeDestination" | "removeBetween"
        ),
        "dev.rustclient.gpu" => matches!(
            key,
            "drawDistance"
                | "hideUnrelatedMaps"
                | "uiScalingMode"
                | "smoothBanding"
                | "brightTextures"
                | "antiAliasingMode"
                | "fogDepth"
                | "anisotropicFilteringLevel"
                | "colorBlindMode"
                | "colorBlindIntensity"
                | "vsyncMode"
                | "unlockFps"
                | "fpsTarget"
        ),
        "dev.rustclient.runelite" => matches!(
            key,
            "automaticResizeType"
                | "lockWindowSize"
                | "gameAlwaysOnTop"
                | "sidebarToggleKey"
                | "panelToggleKey"
                | "blockExtraMouseButtons"
        ),
        "dev.rustclient.entityhider" => matches!(
            key,
            "hidePlayers"
                | "hidePlayers2D"
                | "hideLocalPlayer"
                | "hideLocalPlayer2D"
                | "hideNPCs"
                | "hideNPCs2D"
                | "hideAttackers"
                | "hideThralls"
                | "hideRandomEvents"
        ),
        "dev.rustclient.stretchedmode" => {
            matches!(key, "keepAspectRatio" | "integerScaling" | "scalingFactor")
        }
        "dev.rustclient.idlenotifier" => key != "interactionidle",
        "dev.rustclient.fps" => true,
        "dev.rustclient.interfacestyles" => key == "gameframe",
        "dev.rustclient.npchighlight" => matches!(
            key,
            "npcToHighlight"
                | "drawNames"
                | "highlightHull"
                | "highlightTile"
                | "highlightTrueTile"
                | "highlightSouthWestTile"
                | "highlightSouthWestTrueTile"
                | "npcColor"
                | "fillColor"
                | "borderWidth"
        ),
        "dev.rustclient.playerindicators" => matches!(
            key,
            "highlightSelf"
                | "ownNameColor"
                | "highlightOthers"
                | "nonClanMemberColor"
                | "drawPlayerTiles"
                | "playerNamePosition"
        ),
        "dev.rustclient.camera" => matches!(
            key,
            "cameraSpeed" | "invertYaw" | "invertPitch" | "relaxCameraPitch"
        ),
        "dev.rustclient.statusbars" => key != "enableRestorationBars",
        _ => false,
    };
    (!implemented).then(|| pending_reason(plugin, key))
}
#[expect(
    clippy::too_many_lines,
    reason = "one arm per unported setting, grouped by plugin"
)]
fn pending_reason(plugin: &str, key: &str) -> String {
    match (plugin, key) {
        ("dev.rustclient.gpu", "expandedMapLoadingChunks") => {
            "Needs the extended scene loading ring and matching fog/culling bounds; not ported yet.".into()
        }
        ("dev.rustclient.roofremoval", "overridePOH") => {
            "Needs the player-owned-house roof override data, which is not ported yet.".into()
        }
        (
            "dev.rustclient.entityhider",
            "hidePartyMembers" | "hideFriends" | "hideClanMates" | "hideClanChatMembers" | "hideIgnores",
        ) => "Needs the party, friends, friends chat, clan and ignore lists, which the client does not keep yet.".into(),
        ("dev.rustclient.entityhider", "hidePets" | "hideDeadNpcs") => {
            "Needs follower and death state for NPCs, which the client does not keep yet.".into()
        }
        ("dev.rustclient.entityhider", "hideWorldEntities") => {
            "Boats are not loaded by this client yet.".into()
        }
        ("dev.rustclient.entityhider", "hideProjectiles") => {
            "Projectiles are not drawn by this client yet.".into()
        }
        ("dev.rustclient.idlenotifier", "interactionidle") => {
            "Needs the player's interact target, which the client does not keep yet; the idle-logout warning also cannot tell when you are in combat."
                .into()
        }
        (
            "dev.rustclient.camera",
            "inner" | "outerLimit" | "controlFunction" | "ctrlZoomValue" | "zoomIncrement",
        ) => "The zoom limits and scroll-wheel zoom are client-script variables and callbacks, which this client does not run yet.".into(),
        ("dev.rustclient.camera", "disableCameraShake") => {
            "Camera shake is not implemented, so there is nothing to disable.".into()
        }
        (
            "dev.rustclient.camera",
            "rightClickMovesCamera" | "rightClickObjects" | "rightClickExamine"
            | "rightClickMenuBlocksCamera" | "middleClickMenu",
        ) => "Remaps mouse buttons depending on the open menu, which the client does not model yet.".into(),
        ("dev.rustclient.interfacestyles", "hdHealthBars") => {
            "Needs the health bar renderer to take the 2010 bar sprites, which the client does not expose yet."
                .into()
        }
        ("dev.rustclient.interfacestyles", "hdMenu" | "menuAlpha" | "condensePlayerOptions") => {
            "Changes the right-click menu, which the client does not model as a plugin surface yet."
                .into()
        }
        ("dev.rustclient.interfacestyles", "rsCrossSprites") => {
            "Replaces the click cross sprites, which this client does not let plugins override yet."
                .into()
        }
        ("dev.rustclient.interfacestyles", "alwaysStack") => {
            "Changes a client-script callback result, and callbacks are not exposed to plugins yet."
                .into()
        }
        ("dev.rustclient.npchighlight", "highlightOutline" | "outlineFeather") => {
            "The model outline needs an outline renderer, which this client does not have yet."
                .into()
        }
        ("dev.rustclient.npchighlight", "ignoreDeadNpcs" | "ignorePets") => {
            "Needs death and follower state for NPCs, which the client does not keep yet.".into()
        }
        (
            "dev.rustclient.npchighlight",
            "highlightMenuStyle" | "deadNpcMenuColor",
        ) => "Colours entries in the right-click menu, which the client does not model yet.".into(),
        ("dev.rustclient.npchighlight", "showRespawnTimer") => {
            "Needs NPC death and respawn tracking, which the client does not keep yet.".into()
        }
        (
            "dev.rustclient.npchighlight" | "dev.rustclient.playerindicators",
            "drawMinimapNames",
        ) => "Plugin text on the minimap is not drawn by this client yet.".into(),
        (
            "dev.rustclient.playerindicators",
            "highlightPartyMembers" | "highlightFriends" | "highlightFriendsChat"
            | "highlightTeamMembers" | "highlightClanMembers" | "partyMemberNameColor"
            | "friendNameColor" | "clanMemberColor" | "teamMemberColor" | "clanChatMemberColor"
            | "clanMenuIcons" | "clanchatMenuIcons",
        ) => "Needs the party, friends, friends chat, clan and team lists, which the client does not keep yet.".into(),
        ("dev.rustclient.playerindicators", "colorPlayerMenu") => {
            "Colours player entries in the right-click menu, which the client does not model yet."
                .into()
        }
        ("dev.rustclient.statusbars", "enableRestorationBars") => {
            "The restore previews need item and food healing data, which the client does not keep yet.".into()
        }
        ("dev.rustclient.stretchedmode", "increasedPerformance") => {
            "GPU canvas sampling is controlled by the GPU plugin's UI scaling setting; this source toggle is not mapped yet."
                .into()
        }
        ("dev.rustclient.gpu", "removeVertexSnapping" | "numThreads") => {
            "Belongs to RuneLite's zone uploader, which this renderer does not have yet.".into()
        }
        ("dev.rustclient.menuentryswapper", _) => {
            "Not ported: swaps act on the right-click menu's entries, which the client does not expose to plugins yet."
                .into()
        }
        ("dev.rustclient.devtools", _) => {
            "Not ported: the inspectors need widget, variable and script views the client does not expose to plugins yet."
                .into()
        }
        _ => "Effect port pending; source default shown".into(),
    }
}
/// Typed projection of the accepted `dev.rustclient.gpu` document; unknown or mistyped values keep defaults.
pub(super) fn gpu_settings(schema: &ConfigSchema, document: &ConfigDocument) -> GpuSettings {
    let mut settings = GpuSettings::default();
    let boolean = |key| match value(schema, document, key) {
        Some(ConfigValue::Boolean(value)) => Some(*value),
        _ => None,
    };
    let integer = |key| match value(schema, document, key) {
        Some(ConfigValue::Integer(value)) => u32::try_from(*value).ok(),
        _ => None,
    };
    let choice = |key| match value(schema, document, key) {
        Some(ConfigValue::Choice(value)) => Some(value.as_str()),
        _ => None,
    };
    settings.material = MaterialOptions {
        smooth_banding: boolean("smoothBanding").unwrap_or(true),
        bright_textures: boolean("brightTextures").unwrap_or(false),
    };
    settings.draw_distance = integer("drawDistance").unwrap_or(settings.draw_distance);
    settings.hide_unrelated_maps =
        boolean("hideUnrelatedMaps").unwrap_or(settings.hide_unrelated_maps);
    settings.fog_depth = integer("fogDepth").unwrap_or(settings.fog_depth);
    settings.anisotropy = integer("anisotropicFilteringLevel").unwrap_or(settings.anisotropy);
    settings.color_blind_intensity =
        integer("colorBlindIntensity").unwrap_or(settings.color_blind_intensity);
    settings.unlock_fps = boolean("unlockFps").unwrap_or(settings.unlock_fps);
    settings.fps_target = integer("fpsTarget").unwrap_or(settings.fps_target);
    settings.anti_aliasing = match choice("antiAliasingMode") {
        Some("DISABLED") => AntiAliasing::Disabled,
        Some("MSAA_4") => AntiAliasing::Msaa4,
        Some("MSAA_8") => AntiAliasing::Msaa8,
        Some("MSAA_16") => AntiAliasing::Msaa16,
        _ => AntiAliasing::Msaa2,
    };
    settings.color_blind = match choice("colorBlindMode") {
        Some("PROTANOPIA") => ColorBlind::Protanopia,
        Some("DEUTERANOPIA") => ColorBlind::Deuteranopia,
        Some("TRITANOPIA") => ColorBlind::Tritanopia,
        _ => ColorBlind::None,
    };
    settings.ui_scaling = match choice("uiScalingMode") {
        Some("NEAREST") => rustclient_renderer::UiScaling::Nearest,
        Some("LINEAR") => rustclient_renderer::UiScaling::Linear,
        Some("MITCHELL") => rustclient_renderer::UiScaling::Mitchell,
        Some("CATMULL_ROM") => rustclient_renderer::UiScaling::CatmullRom,
        Some("XBR") => rustclient_renderer::UiScaling::Xbr,
        _ => rustclient_renderer::UiScaling::Hybrid,
    };
    settings.vsync = match choice("vsyncMode") {
        Some("ON") => VsyncMode::On,
        Some("ADAPTIVE") => VsyncMode::Adaptive,
        _ => VsyncMode::Off,
    };
    settings
}
pub(super) fn notes(effective: &EffectiveGpu) -> BTreeMap<String, String> {
    effective
        .deviations
        .iter()
        .map(|deviation| (deviation.key.to_owned(), deviation.reason.clone()))
        .collect()
}
/// Behavior that holds on every adapter but that the option's name does not promise.
pub(super) fn static_note(key: &str) -> Option<String> {
    (key == "drawDistance").then(|| {
        "Uses the revision-240 inclusive eight-tile zone square; fog still uses the requested tile distance."
            .into()
    })
}
pub(super) fn apply_effect(schema: &ConfigSchema, document: &ConfigDocument, window: &Window) {
    if schema.plugin.as_str() == "dev.rustclient.runelite" {
        window.set_resizable(
            value(schema, document, "lockWindowSize") != Some(&ConfigValue::Boolean(true)),
        );
        window.set_window_level(
            if value(schema, document, "gameAlwaysOnTop") == Some(&ConfigValue::Boolean(true)) {
                WindowLevel::AlwaysOnTop
            } else {
                WindowLevel::Normal
            },
        );
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_plugin_host::configuration::Configuration;

    fn gpu_configuration() -> Configuration {
        let schema = rustclient_plugin_host::required::schemas()
            .expect("embedded schemas")
            .into_iter()
            .find(|schema| schema.plugin.as_str() == "dev.rustclient.gpu")
            .expect("embedded GPU schema");
        Configuration::new(schema, None).expect("defaults validate")
    }

    #[test]
    fn schema_defaults_equal_the_renderer_source_defaults() {
        let config = gpu_configuration();
        assert_eq!(
            gpu_settings(config.schema(), config.document()),
            GpuSettings::default()
        );
    }

    #[test]
    fn every_gpu_option_is_either_applied_or_explains_why_not() {
        let config = gpu_configuration();
        for setting in &config.schema().settings {
            let reason = availability("dev.rustclient.gpu", &setting.key);
            assert!(
                reason.as_deref() != Some("Effect port pending; source default shown"),
                "{} has no specific pending reason",
                setting.key
            );
        }
    }
}
