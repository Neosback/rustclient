//! Scene geometry generation for live player and NPC entities.

use crate::{Result, SceneAsset, SceneGeometry};
use rustclient_cache::{
    osrs240::{
        models::adjusted_model,
        npc_model::build_npc_base,
        player_model::{body_recolors, build_player_base},
    },
    world::{ItemDefinition, KitDefinition, NpcDefinition, StaticModel},
};
use rustclient_game_state::{NpcEntity, PlayerEntity, WorldEntitiesState};
use rustclient_model::{ItemDefinitionId, KitId, ModelId, ModelPoint, NpcDefinitionId};
use std::collections::BTreeMap;

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
    entities: &WorldEntitiesState,
    kits: &BTreeMap<KitId, KitDefinition>,
    items: &BTreeMap<ItemDefinitionId, ItemDefinition>,
    npc_defs: &BTreeMap<NpcDefinitionId, NpcDefinition>,
    models: &BTreeMap<ModelId, StaticModel>,
    geometry: &mut SceneGeometry,
) -> Result<()> {
    for player in entities.players().values() {
        build_player(asset, player, kits, items, models, geometry)?;
    }
    for npc in entities.npcs().values() {
        build_npc(asset, npc, npc_defs, models, geometry)?;
    }
    Ok(())
}

fn build_player(
    asset: &SceneAsset,
    player: &PlayerEntity,
    kits: &BTreeMap<KitId, KitDefinition>,
    items: &BTreeMap<ItemDefinitionId, ItemDefinition>,
    models: &BTreeMap<ModelId, StaticModel>,
    geometry: &mut SceneGeometry,
) -> Result<()> {
    let Some(appearance) = &player.appearance else {
        return Ok(());
    };
    if appearance.hidden {
        return Ok(());
    }

    let Some(base_model) = build_player_base(appearance, kits, items, models) else {
        return Ok(());
    };

    let recolors = body_recolors(appearance.colors);
    let mut model = adjusted_model(&base_model, &recolors, &[], 0);

    let active_seq = player.sequence.or(appearance.movement_sequences.idle);

    if let Some(seq_id) = active_seq
        && let Some(seq) = asset.sources().sequences.get(&seq_id)
        && let Ok(Some(frame_idx)) =
            crate::animation::frame_index(seq, u64::from(player.animation_cycle), None)
        && let Some(frame) = asset.sources().frames.get(&seq.frames[frame_idx])
        && let Some(skeleton) = asset.sources().skeletons.get(&frame.skeleton)
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

    emit_model(
        asset,
        &model,
        player.tile.plane,
        player.tile.x,
        player.tile.y,
        player.orientation,
        geometry,
    )
}

fn build_npc(
    asset: &SceneAsset,
    npc: &NpcEntity,
    npc_defs: &BTreeMap<NpcDefinitionId, NpcDefinition>,
    models: &BTreeMap<ModelId, StaticModel>,
    geometry: &mut SceneGeometry,
) -> Result<()> {
    let Some(def) = npc_defs.get(&npc.definition_id) else {
        return Ok(());
    };

    let Some(base_model) = build_npc_base(def, models) else {
        return Ok(());
    };

    let mut model = base_model;

    let active_seq = npc.sequence.or(def.idle_sequence);

    if let Some(seq_id) = active_seq
        && let Some(seq) = asset.sources().sequences.get(&seq_id)
        && let Ok(Some(frame_idx)) =
            crate::animation::frame_index(seq, u64::from(npc.animation_cycle), None)
        && let Some(frame) = asset.sources().frames.get(&seq.frames[frame_idx])
        && let Some(skeleton) = asset.sources().skeletons.get(&frame.skeleton)
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

    emit_model(
        asset,
        &model,
        npc.tile.plane,
        npc.tile.x,
        npc.tile.y,
        npc.orientation,
        geometry,
    )
}

fn emit_model(
    asset: &SceneAsset,
    model: &StaticModel,
    plane: rustclient_model::Plane,
    tile_x: i32,
    tile_y: i32,
    orientation: u16,
    geometry: &mut SceneGeometry,
) -> Result<()> {
    let origin = asset.descriptor().origin();
    let center_fine_x = (tile_x - origin.x) * 128 + 64;
    let center_fine_z = (tile_y - origin.y) * 128 + 64;
    let ground_height = asset
        .ground_height(plane, [center_fine_x, center_fine_z])
        .unwrap_or(0);

    let normals = crate::lighting::calculate(&model.vertices, &model.faces);
    let merged = vec![None; model.vertices.len()];
    let colors: Vec<Option<[i32; 3]>> = model
        .faces
        .iter()
        .enumerate()
        .map(|(i, &f)| crate::lighting::bake(f, i, &normals, &merged, 64, 850))
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
