//! Shared validation between pointer intents and revision encoders.
use rustclient_game_state::GameState;
use rustclient_model::{SceneDescriptor, WorldTile};
use rustclient_protocol::{ClientIntent, ExamineTarget};
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
    use rustclient_model::{ActionSlot, WorldAction, WorldTarget};
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
    if let WorldTarget::Player {
        index, instance, ..
    } = target
    {
        return player_rows(state, scene, player_plane, index, instance);
    }
    let (name, actions, enabled) = match target {
        WorldTarget::Npc {
            index,
            instance,
            definition,
            resolved,
        } => npc_target(
            state,
            asset,
            catalog,
            scene,
            player_plane,
            (index, instance, definition, resolved),
        )?,
        WorldTarget::Location {
            key,
            definition,
            resolved,
        } => location_target(
            state,
            asset,
            scene,
            player_plane,
            (key, definition, resolved),
        )?,
        WorldTarget::GroundItem { tile, item } => {
            ground_item_target(state, catalog, scene, player_plane, tile, item)?
        }
        WorldTarget::Player { .. } => unreachable!("player targets return before this match"),
    };
    let target_color = target_color(target);
    let mut rows: Vec<(String, rustclient_model::WorldAction)> = actions
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
                WorldTarget::Npc { .. }
                | WorldTarget::GroundItem { .. }
                | WorldTarget::Player { .. } => verb.as_ref(),
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
        .collect();
    // Examine follows the definition's own actions, as in the stock menu.
    rows.push((
        format!("Examine <col={target_color}>{name}"),
        WorldAction {
            scene,
            target,
            slot: ActionSlot::EXAMINE,
        },
    ));
    Ok(rows)
}
/// The options other players' menus carry, as menu rows: one per slot the server set, in slot
/// order, labelled `option <col=ffffff>Name (level-N)`. The player must still be the same
/// incarnation, on this plane and inside the map; the local player has no such menu.
fn player_rows(
    state: &GameState,
    scene: SceneDescriptor,
    player_plane: rustclient_model::Plane,
    index: rustclient_model::PlayerIndex,
    instance: rustclient_model::PlayerInstanceId,
) -> Result<Vec<(String, rustclient_model::WorldAction)>, String> {
    use rustclient_model::{ActionSlot, PlayerOpSlot, WorldAction, WorldTarget};
    let entities = state.entities();
    let player = entities
        .players()
        .get(&index)
        .filter(|player| {
            player.instance == instance
                && player.tile.plane == player_plane
                && scene.contains_footprint(player.tile)
                && entities.local_player_index() != Some(index)
        })
        .ok_or("player target is stale")?;
    let appearance = player
        .appearance
        .as_ref()
        .filter(|appearance| !appearance.hidden)
        .ok_or("player has no visible appearance yet")?;
    let name = format!(
        "{} (level-{})",
        appearance.display_name, appearance.combat_level
    );
    Ok((1..=8)
        .filter_map(|slot| {
            let op = PlayerOpSlot::new(slot)?;
            let option = entities.player_option(slot)?;
            Some((
                format!("{} <col=ffffff>{name}", option.text),
                WorldAction {
                    scene,
                    target: WorldTarget::Player {
                        index,
                        instance,
                        op,
                    },
                    slot: ActionSlot::new(0)?,
                },
            ))
        })
        .collect())
}
/// Name, definition actions and enabled mask of a still-present location.
fn location_target<'a>(
    state: &GameState,
    asset: &'a SceneAsset,
    scene: SceneDescriptor,
    player_plane: rustclient_model::Plane,
    (key, definition, resolved): (
        rustclient_model::LocationKey,
        rustclient_model::ObjectDefinitionId,
        rustclient_model::ObjectDefinitionId,
    ),
) -> Result<
    (
        &'a str,
        &'a [Option<String>; 5],
        rustclient_model::ActionMask,
    ),
    String,
> {
    if key.tile.plane != player_plane || !scene.contains_footprint(key.tile) {
        return Err("location is outside the current map or plane".into());
    }
    let def = asset
        .location_definition(key, definition, &state.scenery().into())
        .map_err(|e| e.to_string())?
        .filter(|def| def.id == resolved)
        .ok_or("location target changed or was removed")?;
    Ok((
        def.name.as_deref().unwrap_or("Object"),
        &def.actions,
        state
            .scenery()
            .location_options()
            .get(&key)
            .map_or(rustclient_model::ActionMask::ALL, |o| o.enabled),
    ))
}
/// Name, definition actions and enabled mask of a still-present, interactable NPC.
fn npc_target<'a>(
    state: &'a GameState,
    asset: &SceneAsset,
    catalog: &'a rustclient_scene::entity_mesh::EntityCatalog,
    scene: SceneDescriptor,
    player_plane: rustclient_model::Plane,
    (index, instance, definition, resolved): (
        rustclient_model::NpcIndex,
        rustclient_model::NpcInstanceId,
        rustclient_model::NpcDefinitionId,
        rustclient_model::NpcDefinitionId,
    ),
) -> Result<
    (
        &'a str,
        &'a [Option<String>; 5],
        rustclient_model::ActionMask,
    ),
    String,
> {
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
    Ok((
        npc.name.as_deref().or(def.name.as_deref()).unwrap_or("NPC"),
        &def.actions,
        npc.actions,
    ))
}
/// The `<col=...>` tag of a target's name: NPCs yellow, locations cyan, ground items orange
/// (`colorStartTag(16748608)`).
const fn target_color(target: rustclient_model::WorldTarget) -> &'static str {
    match target {
        rustclient_model::WorldTarget::Npc { .. } => "ffff00",
        rustclient_model::WorldTarget::Location { .. } => "ffff",
        rustclient_model::WorldTarget::GroundItem { .. } => "ff9040",
        rustclient_model::WorldTarget::Player { .. } => "ffffff",
    }
}
/// Name, ground options and enabled mask of the first item with id `item` on `tile`.
fn ground_item_target<'a>(
    state: &GameState,
    catalog: &'a rustclient_scene::entity_mesh::EntityCatalog,
    scene: SceneDescriptor,
    player_plane: rustclient_model::Plane,
    tile: WorldTile,
    item: rustclient_model::ItemDefinitionId,
) -> Result<
    (
        &'a str,
        &'a [Option<String>; 5],
        rustclient_model::ActionMask,
    ),
    String,
> {
    if tile.plane != player_plane || !scene.contains_footprint(tile) {
        return Err("ground item is outside the current map or plane".into());
    }
    let on_ground = state
        .ground_items()
        .pile(tile)
        .iter()
        .find(|entry| entry.item == item)
        .ok_or("ground item was removed")?;
    let def = catalog
        .item(item)
        .ok_or("ground item definition is not loaded yet")?;
    Ok((
        catalog.item_name(item).unwrap_or("null"),
        &def.ground_actions,
        on_ground.ops,
    ))
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
        .any(|(_, entry)| entry.slot == action.slot && entry.target == action.target)
    {
        return Err("selected action is no longer available".into());
    }
    if let WorldTarget::Player { index, op, .. } = action.target {
        return Ok(ClientIntent::PlayerAction {
            index,
            slot: op,
            control,
        });
    }
    if action.slot.is_examine() {
        return Ok(ClientIntent::Examine(match action.target {
            WorldTarget::Npc { definition, .. } => ExamineTarget::Npc(definition),
            WorldTarget::Location { definition, .. } => ExamineTarget::Location(definition),
            WorldTarget::GroundItem { tile, item } => ExamineTarget::GroundItem { tile, item },
            WorldTarget::Player { .. } => unreachable!("handled above"),
        }));
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
        WorldTarget::GroundItem { tile, item } => ClientIntent::GroundItemAction {
            tile,
            item,
            slot: action.slot,
            control,
        },
        WorldTarget::Player { .. } => unreachable!("handled above"),
    })
}
