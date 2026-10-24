//! Immutable CPU terrain, definition and model assets. No live state or GPU handles.
use rustclient_model::{
    FloorOverlayId, FloorUnderlayId, ItemDefinitionId, KitId, LocOrientation, ModelId, ModelPoint,
    NpcDefinitionId, ObjectDefinitionId, RegionCoord, SequenceId, TextureId, VarbitId, VarpId,
    WorldTile,
};
use std::collections::BTreeMap;
/// Resolved height and surface identifiers of one terrain tile.
#[derive(Debug, Clone, Copy, Default)]
pub struct TerrainTile {
    /// Elevation in fine units, positive upwards.
    pub elevation: i32,
    pub underlay: Option<FloorUnderlayId>,
    pub overlay: Option<TerrainOverlay>,
    /// Original terrain flags, including bridge, roof and minimum-plane rules.
    pub settings: u8,
}
#[derive(Debug, Clone, Copy)]
pub struct TerrainOverlay {
    pub id: FloorOverlayId,
    pub shape: u8,
    pub rotation: u8,
}
/// One decoded source square; traversal is plane, local X, local Y.
#[derive(Debug)]
pub struct MapSquare {
    pub coord: RegionCoord,
    pub tiles: Vec<TerrainTile>,
    pub locations: Vec<StaticLocation>,
}
#[derive(Debug, Clone, Copy)]
pub struct StaticLocation {
    pub object: ObjectDefinitionId,
    pub tile: WorldTile,
    pub shape: u8,
    pub orientation: LocOrientation,
}
#[derive(Debug, Clone, Copy)]
pub struct FloorMaterial {
    pub rgb: u32,
    pub texture: Option<TextureId>,
    pub secondary: Option<u32>,
    pub hide_underlay: bool,
    pub visible: bool,
}
#[derive(Debug, Clone, Copy)]
pub struct ModelReference {
    pub id: ModelId,
    pub shape: Option<u8>,
}
/// Movement participation decoded by the cache adapter, independent of opcode values.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum ObjectCollision {
    None,
    Geometry,
    GeometryAndFloorDecoration,
}
/// Render-relevant immutable definition; other verified fields are consumed by the adapter.
#[derive(Debug)]
#[expect(
    clippy::struct_excessive_bools,
    reason = "independent immutable cache flags retained verbatim"
)]
pub struct ObjectDefinition {
    pub id: ObjectDefinitionId,
    pub name: Option<String>,
    pub actions: [Option<String>; 5],
    pub collision: ObjectCollision,
    pub models: Vec<ModelReference>,
    pub size: [u8; 2],
    pub scale: [u16; 3],
    pub translation: [i32; 3],
    pub mirrored: bool,
    pub contour: Option<u32>,
    pub recolors: Vec<(u16, u16)>,
    pub retextures: Vec<(Option<TextureId>, Option<TextureId>)>,
    pub animation: Option<SequenceId>,
    pub morphs: Option<ObjectMorphs>,
    pub non_flat_shading: bool,
    pub clipped: bool,
    pub model_clipped: bool,
    pub random_start: bool,
    pub int1: i32,
    /// Minimap tile sprite (`mapscene` frame) drawn in place of the object's wall colours.
    pub map_scene: Option<u32>,
    /// Minimap icon (`mapfunction` sprite) drawn over the object's tile.
    pub map_icon: Option<u32>,

    pub ambient: i16,
    pub contrast: i16,
    pub decoration_displacement: u8,
}

/// Variable-driven NPC transformation (client opcodes 106 and 118).
///
/// `transforms` always ends with the default entry used when the selector is
/// negative or past the last mapped value; `None` means the NPC is not shown.
#[derive(Debug, Clone)]
pub struct NpcMorphs {
    pub varbit: Option<VarbitId>,
    pub varp: Option<VarpId>,
    pub transforms: Vec<Option<NpcDefinitionId>>,
}

/// Render-relevant NPC definition.
#[derive(Debug, Clone)]
pub struct NpcDefinition {
    pub id: NpcDefinitionId,
    pub name: Option<String>,
    pub models: Vec<ModelId>,
    pub head_models: Vec<ModelId>,
    pub size: u8,
    pub idle_sequence: Option<SequenceId>,
    pub walk_sequence: Option<SequenceId>,
    pub turn_left_sequence: Option<SequenceId>,
    pub turn_right_sequence: Option<SequenceId>,
    pub walk_back_sequence: Option<SequenceId>,
    pub walk_left_sequence: Option<SequenceId>,
    pub walk_right_sequence: Option<SequenceId>,
    pub recolors: Vec<(u16, u16)>,
    pub retextures: Vec<(Option<TextureId>, Option<TextureId>)>,
    pub combat_level: Option<u16>,
    pub scale_width: u16,
    pub scale_height: u16,
    pub ambient: i16,
    pub contrast: i16,
    pub actions: [Option<String>; 5],
    pub draw_minimap_dot: bool,
    pub interactable: bool,
    pub morphs: Option<NpcMorphs>,
}

/// Player identity kit definition (body part models and recolors).
#[derive(Debug, Clone)]
pub struct KitDefinition {
    pub id: KitId,
    pub body_part: Option<u8>,
    pub models: Vec<ModelId>,
    pub recolors: Vec<(u16, u16)>,
    pub retextures: Vec<(Option<TextureId>, Option<TextureId>)>,
}

/// Male/female worn item models.
#[derive(Debug, Clone, Default)]
pub struct WearModels {
    pub models: [Option<ModelId>; 3],
    pub offset: u8,
}

/// Worn item appearance definition.
#[derive(Debug, Clone)]
pub struct ItemDefinition {
    pub icon_model: Option<ModelId>,
    pub icon_zoom: u16,
    pub icon_angles: [u16; 3],
    pub icon_offset: [i32; 2],
    pub stackable: bool,
    pub members: bool,
    pub inventory_actions: [Option<String>; 5],
    pub parameters: BTreeMap<u32, rustclient_model::ScriptValue>,
    pub placeholder: Option<ItemDefinitionId>,
    pub placeholder_template: Option<ItemDefinitionId>,
    pub id: ItemDefinitionId,
    pub name: Option<String>,
    pub equip_slot: Option<u8>,
    pub hides: [Option<u8>; 2],
    pub male: WearModels,
    pub female: WearModels,
    pub recolors: Vec<(u16, u16)>,
    pub retextures: Vec<(Option<TextureId>, Option<TextureId>)>,
}
impl ItemDefinition {
    /// Convert a placeholder definition back to its real item. A real item's
    /// forward placeholder link does not make it a placeholder itself.
    #[must_use]
    pub fn unplaceholder(&self) -> ItemDefinitionId {
        if self.placeholder_template.is_some() {
            self.placeholder.unwrap_or(self.id)
        } else {
            self.id
        }
    }
}

#[derive(Debug, Clone, Copy)]
pub struct ModelFace {
    pub vertices: [u32; 3],
    pub color: u16,
    pub texture: Option<TextureId>,
    pub render_type: i8,
    pub alpha: i8,
    pub skin: Option<u8>,
    pub texture_mapping: Option<u16>,
    pub bias: i8,
    pub priority: u8,
}
#[derive(Debug)]
pub struct StaticModel {
    pub vertices: Vec<ModelPoint>,
    pub faces: Vec<ModelFace>,
    pub vertex_skins: Vec<Option<u8>>,
    pub skeletal_weights: Vec<Vec<(u8, u8)>>,
    pub texture_triangles: Vec<[u32; 3]>,
    pub texture_types: Vec<u8>,
}
/// Complete immutable inputs for a normal map window. Kept behind the scene asset's private owner.
#[derive(Debug)]
pub struct WorldAssets {
    pub squares: Vec<MapSquare>,
    pub underlays: BTreeMap<FloorUnderlayId, FloorMaterial>,
    pub overlays: BTreeMap<FloorOverlayId, FloorMaterial>,
    pub objects: BTreeMap<ObjectDefinitionId, ObjectDefinition>,
    pub models: BTreeMap<ModelId, StaticModel>,
    /// Packed HSL average colors, as supplied by texture definitions.
    pub texture_colors: BTreeMap<TextureId, u16>,
    /// Frames of the `mapscene` sprite group, indexed by `ObjectDefinition::map_scene`.
    pub map_scenes: Vec<crate::Sprite>,
    /// Frames of the `mapdots` sprite group: item, NPC, player, friend, team and so on.
    pub map_dots: Vec<crate::Sprite>,
    pub textures: BTreeMap<TextureId, TextureAsset>,
    pub sequences: BTreeMap<SequenceId, SequenceAsset>,
    pub frames: BTreeMap<u32, LegacyFrame>,
    pub skeletons: BTreeMap<u16, Skeleton>,
    pub varbits: BTreeMap<VarbitId, VarbitDefinition>,
    pub kits: BTreeMap<KitId, KitDefinition>,
    pub metadata_fingerprint: String,
    pub content_hash: [u8; 32],
    pub auxiliary_map_files: usize,
    /// Explicit profile-approved stale publisher size hints encountered.
    pub size_hint_warnings: Vec<String>,
}

#[derive(Debug, Clone)]
pub struct ObjectMorphs {
    pub varbit: Option<VarbitId>,
    pub varp: Option<VarpId>,
    pub targets: Vec<Option<ObjectDefinitionId>>,
    pub fallback: Option<ObjectDefinitionId>,
}
#[derive(Debug, Clone)]
pub struct TextureAsset {
    pub image: crate::Image,
    pub animation_direction: u8,
    pub animation_speed: u8,
    pub opaque: bool,
}
#[derive(Debug, Clone)]
pub struct SequenceAsset {
    pub id: SequenceId,
    pub frames: Vec<u32>,
    pub delays: Vec<u16>,
    pub frame_step: Option<u16>,
    pub max_loops: u16,
    pub restart: u8,
    pub skeletal: Option<u32>,
    pub range: Option<(u16, u16)>,
}
#[derive(Debug, Clone, Copy)]
pub struct VarbitDefinition {
    pub varp: VarpId,
    pub low: u8,
    pub high: u8,
}
#[derive(Debug, Clone)]
pub struct SkeletonTransform {
    pub kind: u8,
    pub labels: Vec<u8>,
}
/// One revision-240 skeleton resource: legacy label transforms plus the optional bone hierarchy
/// that cached-model (skeletal) animations drive.
#[derive(Debug, Clone)]
pub struct Skeleton {
    pub transforms: Vec<SkeletonTransform>,
    pub bones: Option<BoneHierarchy>,
}
/// Bind-pose bone hierarchy. Every bone carries one local matrix per pose variant.
#[derive(Debug, Clone)]
pub struct BoneHierarchy {
    /// Number of bind-pose variants each bone stores.
    pub variants: u8,
    pub bones: Vec<Bone>,
}
/// Row-major 4x4 matrix in the reference's row-vector convention (translation in 12..=14).
pub type Matrix4 = [f32; 16];
#[derive(Debug, Clone)]
pub struct Bone {
    /// Index into [`BoneHierarchy::bones`], validated by the decoder.
    pub parent: Option<usize>,
    /// Local bind matrices, one per pose variant.
    pub bind: Vec<Matrix4>,
}
/// How a curve continues outside its first and last keyframe.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum Extrapolation {
    Constant,
    /// Continue along the end keyframe's tangent.
    Tangent,
    Repeat,
    /// Repeat, shifting by the curve's total value change on each cycle.
    Offset,
    Mirror,
}
#[derive(Debug, Clone, Copy)]
pub struct Keyframe {
    pub time: i32,
    pub value: f32,
    pub in_x: f32,
    pub in_y: f32,
    pub out_x: f32,
    pub out_y: f32,
}
/// One animated scalar channel. Keyframe times are ascending and there is at least one key.
#[derive(Debug, Clone)]
pub struct Curve {
    pub before: Extrapolation,
    pub after: Extrapolation,
    /// Curve segments are cubic Beziers in both time and value, not value-only polynomials.
    pub bezier: bool,
    pub keys: Vec<Keyframe>,
}
/// Per-bone channels: rotation XYZ, translation XYZ, scale XYZ.
pub const BONE_CHANNELS: usize = 9;
/// A cached-model (skeletal) animation resource.
#[derive(Debug, Clone)]
pub struct CachedAnimation {
    pub skeleton: u16,
    /// Which bind-pose variant of every bone the animation starts from.
    pub variant: u8,
    /// Indexed by bone; absent channels keep the bind pose.
    pub bone_curves: Vec<[Option<Curve>; BONE_CHANNELS]>,
    /// Channels attached to skeleton label transforms (alpha lives here), indexed by transform.
    pub transform_curves: BTreeMap<usize, Vec<Option<Curve>>>,
    /// At least one alpha-channel curve exists, so faces need a transparency pass.
    pub has_alpha: bool,
}
#[derive(Debug, Clone)]
pub struct LegacyFrame {
    pub skeleton: u16,
    pub transforms: Vec<FrameTransform>,
}
#[derive(Debug, Clone, Copy)]
pub struct FrameTransform {
    pub slot: usize,
    pub values: [i32; 3],
}
impl ModelFace {
    #[must_use]
    pub const fn hidden(self) -> bool {
        self.render_type == 2 || self.alpha == -1
    }
    #[must_use]
    pub const fn opacity(self) -> u8 {
        if self.alpha == -2 {
            255
        } else {
            255 - self.alpha.cast_unsigned()
        }
    }
}

impl TerrainTile {
    #[must_use]
    pub const fn bridge(self) -> bool {
        self.settings & 2 != 0
    }
    #[must_use]
    pub const fn roof(self) -> bool {
        self.settings & 4 != 0
    }
    #[must_use]
    pub const fn blocked(self) -> bool {
        self.settings & 1 != 0
    }
}
