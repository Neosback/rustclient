//! Availability belongs to the shared configuration owner, independent of UI toolkit.
#[must_use]
pub fn availability(plugin: &str, key: &str) -> Option<String> {
    if key == rustclient_plugin_host::configuration::ENABLED_KEY {
        return matches!(
            plugin,
            "dev.rustclient.menuentryswapper" | "dev.rustclient.devtools"
        )
        .then(|| pending_reason(plugin, key));
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
