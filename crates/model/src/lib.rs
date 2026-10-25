//! Dependency-light semantic primitives shared across `RustClient`.
//!
//! This crate owns value types that must be understood consistently by cache,
//! protocol, game-state, scene, UI, and tooling without depending on any of
//! those subsystems.

#![forbid(unsafe_code)]

mod brightness;
pub mod coords;
pub use brightness::ScreenBrightness;
mod destination;
pub use destination::MapDestination;
pub mod entity;
pub mod ids;
mod interaction;
pub use interaction::{
    ActionMask, ActionSlot, NpcInstanceId, PlayerInstanceId, TileOffset, WorldAction, WorldTarget,
};
mod interface;
pub use interface::{
    CanvasSize, ClientSetting, InterfaceMount, InterfaceMountKind, InterfaceUpdate, ScriptRequest,
    ScriptValue, WidgetAction, WidgetEventRange, WidgetOperation, WidgetPermissions,
    WidgetProperty, WindowMetrics, WindowMode,
};
mod inventory;
pub use inventory::{InventorySlotUpdate, InventoryUpdate, ItemStack};
mod movement;
pub use movement::PathAccelerationPolicy;
pub mod orientation;
pub mod revision;
mod speech;
pub use speech::{ChatAccount, OverheadText, PlayerSpeech, SpeechUpdate};
mod sequence;
mod sequence_timing;
pub mod time;

pub use coords::{
    FINE_UNITS_PER_TILE, FineCoord, MAX_PLANE, ModelPoint, Plane, REGION_SIZE_TILES, RegionCoord,
    RegionTile, SceneTile, WorldFinePoint, WorldTile,
};
pub use entity::{
    APPEARANCE_SLOTS, ActorSequenceSlot, COLOR_SLOTS, ExactMove, Gender, KIT_PARTS, KIT_SLOT,
    MoveSpeed, MovementSequences, NewNpc, NpcAttributeUpdate, NpcInfoUpdate, NpcMovement,
    NpcProperty, PlayerAppearance, PlayerInfoUpdate, PlayerMovement, PlayerTrackingGrid,
    RemotePlayerUpdate, SequenceAssignmentOutcome, SequenceChange, SlotContent,
};
pub use ids::{
    FloorOverlayId, FloorUnderlayId, FontId, InterfaceId, InventoryId, InventorySlot,
    ItemDefinitionId, KitId, ModelId, NpcDefinitionId, NpcIndex, ObjectDefinitionId, PlayerIndex,
    RegionId, ScriptId, SequenceId, ServerProfileId, SkillId, SpotAnimationId, SpriteId, TextureId,
    VarbitId, VarpId, WidgetId, WorldAreaId,
};
pub use orientation::{JAU_FULL_TURN, JAU_QUARTER_TURN, JauAngle, LocOrientation, QuarterTurn};
pub use revision::{REVISION_240, Revision, RevisionParseError};
pub use sequence::SequencePolicy;
pub use sequence_timing::SequenceTiming;
pub use time::{ClientCycle, GameTick, TickSpan};
mod scenery;
pub use scenery::{
    AnimationStart, LocationAnimationPlacement, LocationChange, LocationDefinition, LocationKey,
    LocationLayer,
};

mod scene;
pub use scene::{MapAnchor, SceneAssetId, SceneDescriptor};

mod widget;
pub use widget::{
    PositionMode, SizeMode, WidgetAddress, WidgetBody, WidgetContent, WidgetDefinition, WidgetDrag,
    WidgetKey, WidgetLayout, WidgetListener, WidgetOpKeys, WidgetRuntime,
};

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct SkillValues {
    pub experience: u32,
    pub current: u16,
    pub invisible: u16,
}
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum PlayerStatusUpdate {
    Reboot {
        cycles: u32,
        message: Option<String>,
    },
    Skill {
        id: SkillId,
        values: SkillValues,
    },
    RunEnergy(u16),
    RunWeight(i16),
}

/// Server-provided game message, before local history identity is assigned.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct GameMessage {
    pub channel: ids::ChatChannelId,
    pub sender: String,
    pub text: String,
}

/// Script-authored projection factors, camera distances and aspect constraints.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ViewportSettings {
    pub follow_height: u32,
    pub fov: [u16; 2],
    pub zoom: [u16; 2],
    pub clamp: [u16; 4],
}
impl Default for ViewportSettings {
    fn default() -> Self {
        Self {
            follow_height: 50,
            fov: [256, 205],
            zoom: [256, 320],
            clamp: [1, 32767, 1, 32767],
        }
    }
}
