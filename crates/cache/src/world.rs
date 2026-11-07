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
    /// Source map-initialization shadow before object shadows are applied.
    pub base_shadow: u8,
}
#[derive(Debug, Clone, Copy)]
pub struct TerrainOverlay {
    pub id: FloorOverlayId,
    pub shape: u8,
    pub rotation: u8,
}
/// Whether the map square is declared by the cache index. Absent squares are source-defined void,
/// distinct from a declared archive with a corrupt or unavailable payload.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum MapSquareSource {
    Archive,
    Absent,
}
/// One source square; traversal is plane, local X, local Y.
#[derive(Debug, Clone)]
pub struct MapSquare {
    pub coord: RegionCoord,
    pub source: MapSquareSource,
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
#[derive(Debug, Clone)]
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
    /// Opcode 96. Actors stand this many fine units higher on a tile whose floor decoration
    /// this is (the client subtracts it from the tile height it reports).
    pub floor_lift: u8,
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
    /// Base-definition opaque depth policy; opcode 147 defers writing until after sorted color.
    pub depth_policy: rustclient_model::ModelDepthPolicy,
    pub turn_rate: rustclient_model::JauTurnRate,
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
    /// Forward, backward, left and right animations for each traversal speed.
    pub run_sequences: [Option<SequenceId>; 4],
    pub crawl_sequences: [Option<SequenceId>; 4],
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
    /// Opcode 126: explicit actor clearance in fine units; otherwise `0.4 * size * 128`.
    pub footprint: Option<u16>,
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
    pub tradeable: bool,
    pub note: Option<ItemDefinitionId>,
    pub note_template: Option<ItemDefinitionId>,
    pub bought: Option<ItemDefinitionId>,
    pub bought_template: Option<ItemDefinitionId>,
    pub inventory_actions: [Option<String>; 5],
    /// Options on the item while it lies on the ground (opcodes 30-34; `Hidden` clears one).
    pub ground_actions: [Option<String>; 5],
    /// Store value (`price`, opcode 12); the reference default is 1.
    pub cost: i32,
    /// Stack-size model variants (`countobj`/`countco`, opcodes 100-109): the item shown once a
    /// stack holds at least the paired count. Unused slots are omitted.
    pub count_variants: Vec<(ItemDefinitionId, u16)>,
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
#[derive(Debug, Clone)]
pub struct StaticModel {
    pub vertices: Vec<ModelPoint>,
    pub faces: Vec<ModelFace>,
    pub vertex_skins: Vec<Option<u8>>,
    pub skeletal_weights: VertexWeights,
    pub texture_triangles: Vec<[u32; 3]>,
    pub texture_types: Vec<u8>,
}
/// Skeletal `(bone, weight)` influences for every vertex of one model in a single allocation.
///
/// Most models carry no influences at all; those store nothing per vertex. Once any vertex has
/// an influence each vertex costs four bytes plus its own pairs, instead of a heap header and
/// allocation each.
#[derive(Debug, Clone, Default, PartialEq, Eq)]
pub struct VertexWeights {
    len: usize,
    /// Exclusive end of each vertex's influences in `influences`; empty while none has any.
    ends: Vec<u32>,
    influences: Vec<(u8, u8)>,
}
impl VertexWeights {
    /// `len` vertices, none with an influence.
    #[must_use]
    pub fn empty(len: usize) -> Self {
        Self {
            len,
            ends: Vec::new(),
            influences: Vec::new(),
        }
    }
    #[must_use]
    pub fn len(&self) -> usize {
        self.len
    }
    #[must_use]
    pub fn is_empty(&self) -> bool {
        self.len == 0
    }
    /// The influences of `vertex`; empty for an unweighted or out-of-range vertex.
    #[must_use]
    pub fn get(&self, vertex: usize) -> &[(u8, u8)] {
        if self.ends.is_empty() || vertex >= self.len {
            return &[];
        }
        let start = vertex
            .checked_sub(1)
            .map_or(0, |previous| self.ends[previous] as usize);
        &self.influences[start..self.ends[vertex] as usize]
    }
    /// Per-vertex influences in vertex order.
    pub fn iter(&self) -> impl Iterator<Item = &[(u8, u8)]> + '_ {
        (0..self.len).map(|vertex| self.get(vertex))
    }
    /// Append one vertex.
    ///
    /// # Panics
    ///
    /// When the model holds more than `u32::MAX` influences, which the model format
    /// (at most 255 per vertex) cannot reach for a decodable model.
    pub fn push(&mut self, influences: &[(u8, u8)]) {
        if influences.is_empty() && self.ends.is_empty() {
            self.len += 1;
            return;
        }
        if self.ends.is_empty() {
            self.ends = vec![0; self.len];
        }
        self.influences.extend_from_slice(influences);
        self.ends.push(
            u32::try_from(self.influences.len()).expect("model influences fit a 32-bit offset"),
        );
        self.len += 1;
    }
    /// Append every vertex of `other`.
    pub fn extend_from(&mut self, other: &Self) {
        for influences in other.iter() {
            self.push(influences);
        }
    }
    /// Heap bytes reserved by the store.
    #[must_use]
    pub fn heap_bytes(&self) -> usize {
        self.ends.capacity() * size_of::<u32>() + self.influences.capacity() * size_of::<(u8, u8)>()
    }
}
/// Complete immutable inputs for a normal map window. Kept behind the scene asset's private owner.
#[derive(Debug, Clone)]
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
    /// Minimap icons by world map element id, for the elements the loaded objects reference.
    pub map_icons: BTreeMap<u32, crate::Sprite>,
    /// Shared with the cache's decoded set and every mesh built from these assets.
    pub textures: std::sync::Arc<BTreeMap<TextureId, TextureAsset>>,
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
    /// Opcode 5. A different action sequence may replace the current one only when its
    /// replacement priority is at least the active sequence's priority.
    pub priority: u8,
    /// Opcode 9 after the revision-240 constructor default is resolved. The actor scheduler
    /// checks this while assignment-time path backlog remains outstanding.
    pub movement_gate_with_backlog: u8,
    /// Opcode 10 after the revision-240 constructor default is resolved. The actor scheduler
    /// checks this after the assignment-time path backlog is exhausted.
    pub movement_gate_without_backlog: u8,
    pub restart: u8,
    pub skeletal: Option<u32>,
    pub range: Option<(u16, u16)>,
    /// Opcode 16 (signed). The drawn actor is raised by this many fine units while the
    /// sequence plays (`Renderable.getAnimationHeightOffset`).
    pub height_offset: i8,
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
#[cfg(test)]
mod vertex_weight_tests {
    use super::VertexWeights;
    #[test]
    fn matches_a_vector_of_vectors_and_stays_empty_without_influences() {
        let lists: [&[(u8, u8)]; 5] = [&[], &[(1, 255)], &[], &[(2, 128), (3, 127)], &[]];
        let mut unweighted = VertexWeights::empty(0);
        let mut weighted = VertexWeights::empty(2);
        for list in [&[][..]; 3] {
            unweighted.push(list);
        }
        assert_eq!(unweighted.len(), 3);
        assert_eq!(unweighted.heap_bytes(), 0);
        assert!(unweighted.iter().all(<[_]>::is_empty));
        for list in lists {
            weighted.push(list);
        }
        // Two leading unweighted vertices exist before the pushed ones.
        assert_eq!(weighted.len(), 7);
        assert!(weighted.get(0).is_empty() && weighted.get(1).is_empty());
        for (index, list) in lists.iter().enumerate() {
            assert_eq!(weighted.get(index + 2), *list);
        }
        assert!(weighted.get(7).is_empty(), "out of range is empty");
        let mut combined = VertexWeights::empty(0);
        combined.extend_from(&unweighted);
        combined.extend_from(&weighted);
        assert_eq!(combined.len(), 10);
        assert!(combined.get(2).is_empty());
        assert_eq!(combined.get(3 + 3), &[(1, 255)]);
        assert_eq!(combined.get(3 + 5), &[(2, 128), (3, 127)]);
        assert_eq!(
            combined.iter().map(<[_]>::len).sum::<usize>(),
            3,
            "influences are not duplicated or lost"
        );
    }
}
