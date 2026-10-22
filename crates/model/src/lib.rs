//! Dependency-light semantic primitives shared across `RustClient`.
//!
//! This crate owns value types that must be understood consistently by cache,
//! protocol, game-state, scene, UI, and tooling without depending on any of
//! those subsystems.

#![forbid(unsafe_code)]

pub mod coords;
pub mod entity;
pub mod ids;
mod interaction;
pub use interaction::{
    ActionMask, ActionSlot, NpcInstanceId, TileOffset, WorldAction, WorldTarget,
};
mod interface;
pub use interface::{
    ClientSetting, InterfaceMount, InterfaceMountKind, InterfaceUpdate, ScriptRequest, ScriptValue,
    WidgetAction, WidgetEventRange, WidgetOperation, WidgetPermissions, WidgetProperty,
};
mod inventory;
pub use inventory::{InventorySlotUpdate, InventoryUpdate, ItemStack};
pub mod orientation;
pub mod revision;
pub mod time;

pub use coords::{
    FINE_UNITS_PER_TILE, FineCoord, MAX_PLANE, ModelPoint, Plane, REGION_SIZE_TILES, RegionCoord,
    RegionTile, SceneTile, WorldFinePoint, WorldTile,
};
pub use entity::{
    APPEARANCE_SLOTS, ActorSequenceSlot, COLOR_SLOTS, Gender, KIT_PARTS, KIT_SLOT,
    MovementSequences, NewNpc, NpcAttributeUpdate, NpcInfoUpdate, NpcMovement, NpcProperty,
    PlayerAppearance, PlayerInfoUpdate, PlayerMovement, SequenceChange, SlotContent,
};
pub use ids::{
    FloorOverlayId, FloorUnderlayId, FontId, InterfaceId, InventoryId, InventorySlot,
    ItemDefinitionId, KitId, ModelId, NpcDefinitionId, NpcIndex, ObjectDefinitionId, PlayerIndex,
    RegionId, ScriptId, SequenceId, ServerProfileId, SkillId, SpotAnimationId, SpriteId, TextureId,
    VarbitId, VarpId, WidgetId, WorldAreaId,
};
pub use orientation::{JAU_FULL_TURN, JAU_QUARTER_TURN, JauAngle, LocOrientation, QuarterTurn};
pub use revision::{REVISION_240, Revision, RevisionParseError};
pub use time::{ClientCycle, GameTick, TickSpan};
mod scenery;
pub use scenery::{AnimationStart, LocationChange, LocationDefinition, LocationKey, LocationLayer};

mod scene;
pub use scene::{MapAnchor, SceneAssetId, SceneDescriptor};

mod widget;
pub use widget::{
    PositionMode, SizeMode, WidgetAddress, WidgetBody, WidgetContent, WidgetDefinition,
    WidgetLayout, WidgetListener, WidgetRuntime,
};

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct SkillValues {
    pub experience: u32,
    pub current: u16,
    pub invisible: u16,
}
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum PlayerStatusUpdate {
    Skill { id: SkillId, values: SkillValues },
    RunEnergy(u16),
    RunWeight(i16),
}
