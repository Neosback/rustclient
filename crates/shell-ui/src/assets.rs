//! Owned pinned `RuneLite` resources; source notices in assets/runelite/.
pub const OPEN_RS_PNG: &[u8] = include_bytes!("../assets/runelite/open_rs.png");
pub(crate) const ICONS: &[(&str, &[u8])] = &[
    (
        "config_icon_lg",
        include_bytes!("../assets/runelite/config_icon_lg.png"),
    ),
    (
        "profile_icon",
        include_bytes!("../assets/runelite/profile_icon.png"),
    ),
    (
        "plugin_hub_icon",
        include_bytes!("../assets/runelite/plugin_hub_icon.png"),
    ),
    (
        "config_icon",
        include_bytes!("../assets/runelite/config_icon.png"),
    ),
    (
        "config_back_icon",
        include_bytes!("../assets/runelite/config_back_icon.png"),
    ),
    (
        "config_edit_icon",
        include_bytes!("../assets/runelite/config_edit_icon.png"),
    ),
    ("refresh", include_bytes!("../assets/runelite/refresh.png")),
    ("star_on", include_bytes!("../assets/runelite/star_on.png")),
    (
        "star_off",
        include_bytes!("../assets/runelite/star_off.png"),
    ),
    ("search", include_bytes!("../assets/runelite/search.png")),
    (
        "expanded",
        include_bytes!("../assets/runelite/expanded.png"),
    ),
    (
        "collapsed",
        include_bytes!("../assets/runelite/collapsed.png"),
    ),
    (
        "switch_on",
        include_bytes!("../assets/runelite/switch_on.png"),
    ),
    (
        "switch_off",
        include_bytes!("../assets/runelite/switch_off.png"),
    ),
];
