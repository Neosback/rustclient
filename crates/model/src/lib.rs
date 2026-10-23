//! Dependency-light semantic primitives shared across `RustClient`.
//!
//! This crate owns value types that must be understood consistently by cache,
//! protocol, game-state, scene, UI, and tooling without depending on any of
//! those subsystems.

#![forbid(unsafe_code)]

pub mod coords;
pub mod entity;
pub mod ids;
pub mod orientation;
pub mod revision;
pub mod time;

pub use coords::{
    FINE_UNITS_PER_TILE, FineCoord, MAX_PLANE, ModelPoint, Plane, REGION_SIZE_TILES, RegionCoord,
    RegionTile, SceneTile, WorldFinePoint, WorldTile,
};
pub use entity::{
    APPEARANCE_SLOTS, ActorSequenceSlot, COLOR_SLOTS, ExactMove, Gender, KIT_PARTS, KIT_SLOT,
    MoveSpeed, MovementSequences, NewNpc, NpcInfoUpdate, NpcMovement, PlayerAppearance,
    PlayerInfoUpdate, PlayerMovement, PlayerUpdate, SlotContent,
};
pub use ids::{
    FloorOverlayId, FloorUnderlayId, InterfaceId, ItemDefinitionId, KitId, ModelId,
    NpcDefinitionId, NpcIndex, ObjectDefinitionId, PlayerIndex, RegionId, ScriptId, SequenceId,
    ServerProfileId, SpotAnimationId, SpriteId, TextureId, VarbitId, VarpId, WidgetId, WorldAreaId,
};
pub use orientation::{JAU_FULL_TURN, JAU_QUARTER_TURN, JauAngle, LocOrientation, QuarterTurn};
pub use revision::{REVISION_240, Revision, RevisionParseError};
pub use time::{ClientCycle, GameTick, TickSpan};
mod scenery;
pub use scenery::{AnimationStart, LocationChange, LocationDefinition, LocationKey, LocationLayer};

mod scene;
pub use scene::{SceneAssetId, SceneDescriptor};
