// Stable, versioned vocabulary shared by the plugin host, runtime adapters and SDK.
//
// These types are public ABI surface. They intentionally do not expose `GameState`, protocol
// structs, renderer values or platform handles. The canonical contract is the WIT package in
// `wit/rustclient/plugin`; this crate adapts it for host-side Rust without becoming a second
// competing contract.

#![forbid(unsafe_code)]

mod actors;
pub use actors::ActorHandle;
mod events;
mod subscriptions;
pub use subscriptions::{EventInterest, GameEventKind};
mod game;
mod manifest;
mod menu;
mod output;
mod panels;
pub use events::{GameEvent, VarKind};
pub use game::{
    ActorRef, ActorState, CameraSnapshot, ChatKind, ChatLine, ClientSnapshot, GameSnapshot,
    GameStateKind, Healthbar, Hitsplat, InputIdle, ItemStack, NpcSnapshot, PlayerSnapshot, Rect,
    Skill, SkillLevel, VarbitDef, Vars, WidgetSnapshot, WorldPoint,
};
pub use manifest::{ManifestError, PluginManifest};
pub use menu::{MenuEdit, MenuEntry, MenuKind};
pub use output::{
    ActorAnchor, AnimationSmoothing, CameraCommand, EntityVisibility, HorizontalAlign, InfoBox,
    LayeredOverlay, MinimapRequest, Notification, NotificationKind, OverlayCommand, OverlayLayer,
    PluginOutput, RoofArea, RoofModes, SceneFlags, SpriteOverride, Stretch, StretchScaling, limits,
};
pub use panels::{PanelBlock, PanelEvent, PanelIcon, PanelValue, PluginPanel, panel_limits};

mod capabilities;
mod identity;
mod legacy;
pub use capabilities::{Capability, CapabilitySet, UnknownCapability};
pub use identity::{
    API_MAJOR, API_MINOR, ApiVersionError, InvalidPluginId, PluginApiVersion, PluginId,
};
pub use legacy::{
    LogLevel, OVERLAY_HEIGHT, OVERLAY_WIDTH, OverlayLine, PlayerPosition, PluginEvent,
};
#[cfg(test)]
mod tests;
