//! Dependency-light semantic primitives shared across `RustClient`.
//!
//! This crate owns value types that must be understood consistently by cache,
//! protocol, game-state, scene, UI, and tooling without depending on any of
//! those subsystems.

#![forbid(unsafe_code)]

pub mod coords;
pub mod ids;
pub mod orientation;
pub mod revision;
pub mod time;

pub use coords::{
    FINE_UNITS_PER_TILE, FineCoord, MAX_PLANE, ModelPoint, Plane, REGION_SIZE_TILES, RegionCoord,
    RegionTile, SceneTile, WorldFinePoint, WorldTile,
};
pub use ids::{
    InterfaceId, ItemDefinitionId, ModelId, NpcDefinitionId, NpcIndex, ObjectDefinitionId,
    PlayerIndex, RegionId, ScriptId, SequenceId, SpotAnimationId, SpriteId, TextureId, VarbitId,
    VarpId, WidgetId,
};
pub use orientation::{JAU_FULL_TURN, JAU_QUARTER_TURN, JauAngle, LocOrientation, QuarterTurn};
pub use revision::{REVISION_240, Revision, RevisionParseError};
pub use time::{GameTick, TickSpan};
