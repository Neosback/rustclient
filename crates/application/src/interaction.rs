//! Shared validation between pointer intents and revision encoders.
use rustclient_game_state::GameState;
use rustclient_model::{SceneDescriptor, WorldTile};
use rustclient_protocol::ClientIntent;
use rustclient_scene::SceneAsset;

/// Validate a movement click against the currently committed map and entities.
/// The request does not predict or mutate the confirmed position.
/// # Errors
/// Rejects stale projections, missing sessions, wrong-plane or out-of-map tiles.
pub fn validate_walk(
    state: &GameState,
    asset: &SceneAsset,
    expected: SceneDescriptor,
    destination: WorldTile,
    invert_run: bool,
) -> Result<ClientIntent, String> {
    if state.authenticated_player().is_none()
        || state.loaded_scene() != Some(expected)
        || asset.descriptor().with_plane(expected.origin().plane) != expected
    {
        return Err("movement requires the current loaded map".into());
    }
    let player = state
        .entities()
        .local_player()
        .ok_or("movement requires a confirmed player")?;
    if !expected.contains(destination) || player.tile.plane != destination.plane {
        return Err("destination is outside the current map or plane".into());
    }
    // Stock click requests carry the selected tile. Server routing may choose a
    // reachable fallback; a disposable local collision map must not veto the request.
    Ok(ClientIntent::Walk {
        destination,
        invert_run,
    })
}

/// Available world actions from current state and the displayed target identity.
/// # Errors
/// Rejects stale map selections, despawned/reused NPCs and changed location morphs.
pub fn world_actions(
    state: &GameState,
    asset: &SceneAsset,
    catalog: &rustclient_scene::entity_mesh::EntityCatalog,
    scene: SceneDescriptor,
    target: rustclient_model::WorldTarget,
) -> Result<Vec<(String, rustclient_model::WorldAction)>, String> {
    use rustclient_model::{ActionMask, ActionSlot, WorldAction, WorldTarget};
    if state.authenticated_player().is_none()
        || state.loaded_scene() != Some(scene)
        || asset.descriptor().with_plane(scene.origin().plane) != scene
    {
        return Err("world action refers to an obsolete map".into());
    }
    let player_plane = state
        .entities()
        .local_player()
        .map(|player| player.tile.plane)
        .ok_or("world action requires a confirmed player")?;
    let (name, actions, enabled) = match target {
        WorldTarget::Npc {
            index,
            instance,
            definition,
            resolved,
        } => {
            let npc = state
                .entities()
                .npcs()
                .get(&index)
                .filter(|npc| {
                    npc.instance == instance
                        && npc.definition_id == definition
                        && npc.tile.plane == player_plane
                        && scene.contains_footprint(npc.tile)
                })
                .ok_or("NPC target is stale")?;
            let def = catalog
                .resolve_npc(asset, definition, state.scenery().variables())
                .filter(|def| def.id == resolved && def.interactable)
                .ok_or("NPC definition changed or is unavailable")?;
            (
                npc.name.as_deref().or(def.name.as_deref()).unwrap_or("NPC"),
                &def.actions,
                npc.actions,
            )
        }
        WorldTarget::Location {
            key,
            definition,
            resolved,
        } => {
            if key.tile.plane != player_plane || !scene.contains_footprint(key.tile) {
                return Err("location is outside the current map or plane".into());
            }
            let def = asset
                .location_definition(key, definition, &state.scenery().into())
                .map_err(|e| e.to_string())?
                .filter(|def| def.id == resolved)
                .ok_or("location target changed or was removed")?;
            (
                def.name.as_deref().unwrap_or("Object"),
                &def.actions,
                state
                    .scenery()
                    .location_options()
                    .get(&key)
                    .map_or(ActionMask::ALL, |o| o.enabled),
            )
        }
    };
    let target_color = match target {
        WorldTarget::Npc { .. } => "ffff00",
        WorldTarget::Location { .. } => "ffff",
    };
    Ok(actions
        .iter()
        .enumerate()
        .filter_map(|(index, verb)| {
            let slot = ActionSlot::new(u8::try_from(index).ok()?)?;
            if !enabled.allows(slot) {
                return None;
            }
            let verb = match target {
                WorldTarget::Location { key, .. } => state
                    .scenery()
                    .location_options()
                    .get(&key)
                    .and_then(|o| o.labels[index].as_ref())
                    .or(verb.as_ref()),
                WorldTarget::Npc { .. } => verb.as_ref(),
            }?;
            if verb.is_empty() {
                return None;
            }
            Some((
                format!("{verb} <col={target_color}>{name}"),
                WorldAction {
                    scene,
                    target,
                    slot,
                },
            ))
        })
        .collect())
}
/// Revalidate the selected menu entry immediately before creating an outbound intent.
/// # Errors
/// Rejects removed/reused targets, changed definitions and hidden/unavailable actions.
pub fn validate_world_action(
    state: &GameState,
    asset: &SceneAsset,
    catalog: &rustclient_scene::entity_mesh::EntityCatalog,
    action: rustclient_model::WorldAction,
    control: bool,
) -> Result<ClientIntent, String> {
    use rustclient_model::WorldTarget;
    if !world_actions(state, asset, catalog, action.scene, action.target)?
        .iter()
        .any(|(_, entry)| entry.slot == action.slot)
    {
        return Err("selected action is no longer available".into());
    }
    Ok(match action.target {
        WorldTarget::Npc { index, .. } => ClientIntent::NpcAction {
            index,
            slot: action.slot,
            control,
        },
        WorldTarget::Location {
            key, definition, ..
        } => ClientIntent::LocationAction {
            tile: key.tile,
            object: definition,
            slot: action.slot,
            control,
        },
    })
}
