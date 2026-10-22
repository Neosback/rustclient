//! Scene geometry generation for live player and NPC entities.

use crate::{Result, SceneAsset, SceneGeometry, lighting::Light};
use rustclient_cache::{
    osrs240::{
        entity_assets::{EntityAssets, EntityRequest},
        models::adjusted_model,
        npc_model::build_npc_base,
        player_model::{body_recolors, build_player_base},
    },
    world::{
        ItemDefinition, LegacyFrame, NpcDefinition, SequenceAsset, SkeletonTransform, StaticModel,
    },
};
use rustclient_game_state::{NpcEntity, PlayerEntity, WorldEntitiesState};
use rustclient_model::{
    ItemDefinitionId, ModelId, ModelPoint, NpcDefinitionId, SequenceId, SlotContent,
};
use std::collections::{BTreeMap, BTreeSet};

/// Grow-only entity dependencies layered over the immutable spawn window.
///
/// This is catalog data, not semantic state: it can be dropped and rebuilt from the cache
/// without affecting `GameState`.
#[derive(Debug, Default)]
pub struct EntityCatalog {
    npcs: BTreeMap<NpcDefinitionId, NpcDefinition>,
    items: BTreeMap<ItemDefinitionId, ItemDefinition>,
    models: BTreeMap<ModelId, StaticModel>,
    sequences: BTreeMap<SequenceId, SequenceAsset>,
    frames: BTreeMap<u32, LegacyFrame>,
    skeletons: BTreeMap<u16, Vec<SkeletonTransform>>,
    requested_npcs: BTreeSet<NpcDefinitionId>,
    requested_items: BTreeSet<ItemDefinitionId>,
    requested_sequences: BTreeSet<SequenceId>,
}

impl EntityCatalog {
    /// Definitions that live entities reference and no earlier request covered.
    ///
    /// Each id is requested once; the loader never retries absent or undecodable assets.
    pub fn request_missing(
        &mut self,
        asset: &SceneAsset,
        entities: &WorldEntitiesState,
    ) -> Option<EntityRequest> {
        let mut request = EntityRequest::default();
        let mut want_sequence = |id: SequenceId, requested: &mut BTreeSet<SequenceId>| {
            if !asset.sources().sequences.contains_key(&id) && requested.insert(id) {
                request.sequences.insert(id);
            }
        };
        for player in entities.players().values() {
            if let Some(sequence) = player.sequence {
                want_sequence(sequence, &mut self.requested_sequences);
            }
            let Some(appearance) = &player.appearance else {
                continue;
            };
            let movement = appearance.movement_sequences;
            for sequence in [
                movement.idle,
                movement.turn,
                movement.walk,
                movement.walk_back,
                movement.walk_left,
                movement.walk_right,
                movement.run,
            ]
            .into_iter()
            .flatten()
            {
                want_sequence(sequence, &mut self.requested_sequences);
            }
            for slot in &appearance.slots {
                if let SlotContent::Item(item) = slot
                    && self.requested_items.insert(*item)
                {
                    request.items.insert(*item);
                }
            }
        }
        for npc in entities.npcs().values() {
            if let Some(sequence) = npc.sequence {
                want_sequence(sequence, &mut self.requested_sequences);
            }
            if self.requested_npcs.insert(npc.definition_id) {
                request.npcs.insert(npc.definition_id);
            }
        }
        // Sequences referenced by NPC definitions are resolved by the loader itself.
        self.requested_sequences
            .extend(request.sequences.iter().copied());
        (!request.is_empty()).then_some(request)
    }

    /// Fold a decoded batch into the catalog.
    pub fn merge(&mut self, assets: EntityAssets) {
        self.npcs.extend(assets.npcs);
        self.items.extend(assets.items);
        self.models.extend(assets.models);
        self.sequences.extend(assets.sequences);
        self.frames.extend(assets.frames);
        self.skeletons.extend(assets.skeletons);
    }

    fn model<'a>(&'a self, asset: &'a SceneAsset, id: ModelId) -> Option<&'a StaticModel> {
        self.models
            .get(&id)
            .or_else(|| asset.sources().models.get(&id))
    }

    fn frame<'a>(&'a self, asset: &'a SceneAsset, id: u32) -> Option<&'a LegacyFrame> {
        self.frames
            .get(&id)
            .or_else(|| asset.sources().frames.get(&id))
    }

    fn skeleton<'a>(&'a self, asset: &'a SceneAsset, id: u16) -> Option<&'a [SkeletonTransform]> {
        self.skeletons
            .get(&id)
            .or_else(|| asset.sources().skeletons.get(&id))
            .map(Vec::as_slice)
    }
}

/// Rotate a model point around the Y axis by an OSRS orientation angle (0..2047).
#[must_use]
pub fn rotate_y(p: ModelPoint, angle: u16) -> ModelPoint {
    let theta = f64::from(angle & 2047) * std::f64::consts::TAU / 2048.0;
    let (sin, cos) = theta.sin_cos();
    #[expect(
        clippy::cast_possible_truncation,
        reason = "coordinate bounds clamp inside i32"
    )]
    let x = (f64::from(p.x) * cos + f64::from(p.z) * sin).round() as i32;
    #[expect(
        clippy::cast_possible_truncation,
        reason = "coordinate bounds clamp inside i32"
    )]
    let z = (-f64::from(p.x) * sin + f64::from(p.z) * cos).round() as i32;
    ModelPoint::new(x, p.y, z)
}

/// Append live entity geometry (players and NPCs) into `geometry`.
/// # Errors
/// Returns error on vertex allocation limit or pose failures.
pub fn build_entities_geometry(
    asset: &SceneAsset,
    catalog: &EntityCatalog,
    entities: &WorldEntitiesState,
    geometry: &mut SceneGeometry,
) -> Result<()> {
    for player in entities.players().values() {
        build_player(asset, catalog, player, geometry)?;
    }
    for npc in entities.npcs().values() {
        build_npc(asset, catalog, npc, geometry)?;
    }
    Ok(())
}

/// Apply the legacy frame selected by `cycle`. Skeletal sequences are not posed here.
fn pose_model(
    asset: &SceneAsset,
    catalog: &EntityCatalog,
    model: &mut StaticModel,
    sequence: Option<SequenceId>,
    cycle: u32,
) -> Result<()> {
    let Some(sequence) = sequence else {
        return Ok(());
    };
    let Some(sequence) = catalog
        .sequences
        .get(&sequence)
        .or_else(|| asset.sources().sequences.get(&sequence))
    else {
        return Ok(());
    };
    let Ok(Some(index)) = crate::animation::frame_index(sequence, u64::from(cycle), None) else {
        return Ok(());
    };
    if let Some(frame) = sequence
        .frames
        .get(index)
        .and_then(|id| catalog.frame(asset, *id))
        && let Some(skeleton) = catalog.skeleton(asset, frame.skeleton)
    {
        crate::animation::pose(
            &mut model.vertices,
            &mut model.faces,
            &model.vertex_skins,
            frame,
            skeleton,
            0,
        )?;
    }
    Ok(())
}

fn build_player(
    asset: &SceneAsset,
    catalog: &EntityCatalog,
    player: &PlayerEntity,
    geometry: &mut SceneGeometry,
) -> Result<()> {
    let Some(appearance) = &player.appearance else {
        return Ok(());
    };
    if appearance.hidden {
        return Ok(());
    }

    let Some(base_model) =
        build_player_base(appearance, &asset.sources().kits, &catalog.items, |id| {
            catalog.model(asset, id)
        })
    else {
        return Ok(());
    };

    let recolors = body_recolors(appearance.colors);
    let mut model = adjusted_model(&base_model, &recolors, &[], 0);
    let sequence = player.sequence.or(appearance.movement_sequences.idle);
    pose_model(asset, catalog, &mut model, sequence, player.animation_cycle)?;

    emit_model(
        asset,
        &model,
        Placement {
            tile: player.tile,
            size: 1,
            orientation: player.orientation,
        },
        Light::entity(64, 850),
        geometry,
    )
}

fn build_npc(
    asset: &SceneAsset,
    catalog: &EntityCatalog,
    npc: &NpcEntity,
    geometry: &mut SceneGeometry,
) -> Result<()> {
    let Some(def) = catalog.npcs.get(&npc.definition_id) else {
        return Ok(());
    };
    let Some(mut model) = build_npc_base(def, |id| catalog.model(asset, id)) else {
        return Ok(());
    };
    if def.scale_width != 128 || def.scale_height != 128 {
        let (width, height) = (i32::from(def.scale_width), i32::from(def.scale_height));
        for p in &mut model.vertices {
            *p = ModelPoint::new(p.x * width / 128, p.y * height / 128, p.z * width / 128);
        }
    }
    let sequence = npc.sequence.or(def.idle_sequence);
    pose_model(asset, catalog, &mut model, sequence, npc.animation_cycle)?;

    emit_model(
        asset,
        &model,
        Placement {
            tile: npc.tile,
            size: def.size.max(1),
            orientation: npc.orientation,
        },
        Light::entity(
            i32::from(def.ambient) + 64,
            i32::from(def.contrast) * 5 + 850,
        ),
        geometry,
    )
}

#[derive(Clone, Copy)]
struct Placement {
    tile: rustclient_model::WorldTile,
    /// Footprint edge in tiles; the model is centred on the whole footprint.
    size: u8,
    orientation: u16,
}

fn emit_model(
    asset: &SceneAsset,
    model: &StaticModel,
    placement: Placement,
    light: Light,
    geometry: &mut SceneGeometry,
) -> Result<()> {
    let Placement {
        tile,
        size,
        orientation,
    } = placement;
    let origin = asset.descriptor().origin();
    let half_footprint = i32::from(size) * 64;
    let center_fine_x = (tile.x - origin.x) * 128 + half_footprint;
    let center_fine_z = (tile.y - origin.y) * 128 + half_footprint;
    let ground_height = asset
        .ground_height(tile.plane, [center_fine_x, center_fine_z])
        .unwrap_or(0);

    let normals = crate::lighting::calculate(&model.vertices, &model.faces);
    let merged = vec![None; model.vertices.len()];
    let colors: Vec<Option<[i32; 3]>> = model
        .faces
        .iter()
        .enumerate()
        .map(|(i, &f)| crate::lighting::bake_lit(f, i, &normals, &merged, light))
        .collect();

    for (index, &face) in model.faces.iter().enumerate() {
        let Some(face_colors) = colors[index] else {
            continue;
        };

        let uv = if face.texture.is_some() {
            face.texture_mapping
                .and_then(|s| model.texture_triangles.get(usize::from(s)))
                .map_or([[0., 0.], [1., 0.], [0., 1.]], |t| {
                    crate::uv::authored(
                        face.vertices.map(|v| model.vertices[v as usize]),
                        t.map(|v| model.vertices[v as usize]),
                    )
                })
        } else {
            [[0.; 2]; 3]
        };

        let points = face.vertices.map(|v| {
            let orig = model.vertices[v as usize];
            let rot = rotate_y(orig, orientation);
            [
                center_fine_x + rot.x,
                ground_height - rot.y,
                center_fine_z + rot.z,
            ]
        });

        let alpha = if face.alpha == -2 {
            0
        } else {
            face.alpha.cast_unsigned()
        };

        geometry.triangle(points, face_colors, face.texture, uv, alpha, face.bias)?;
    }

    Ok(())
}
