//! `GameState` (+ immutable cache definitions) → plugin API 0.2 snapshots.
//!
//! Each category is built only when the enabled plugins hold the matching capability. A fact the
//! client does not retain yet is absent, never guessed.

use rustclient_cache::{
    widget::InterfaceDefinition,
    world::{NpcDefinition, VarbitDefinition},
};
use rustclient_game_state::{GameState, Locomotion, NpcEntity, PlayerEntity, SessionLifecycle};
use rustclient_game_ui::widgets::{WidgetBounds, WidgetTree, resolve_widget};
use rustclient_model::{
    InterfaceId, NpcDefinitionId, VarbitId, WidgetBody, WidgetRuntime,
    ids::{InventoryId, SpriteId},
};
use rustclient_plugin_api::{
    ActorState, CameraSnapshot, Capability, CapabilitySet, ChatKind, ChatLine, ClientSnapshot,
    GameStateKind, ItemStack, NpcSnapshot, PlayerSnapshot, Rect, Skill, SkillLevel, VarbitDef,
    Vars, WidgetSnapshot, WorldPoint,
};
use std::collections::BTreeMap;
use std::sync::Arc;

/// Non-semantic facts owned by the shell, renderer and input layers.
#[derive(Debug, Clone, Copy, Default)]
pub struct Frame {
    /// Measured frames per second.
    pub fps: u32,
    /// Whether the window has focus.
    pub focused: bool,
    /// Whether the scene is still loading.
    pub loading: bool,
    /// Camera state.
    pub camera: CameraSnapshot,
}

/// Immutable cache definitions the projection consults.
#[derive(Clone, Copy, Default)]
pub struct Sources<'a> {
    /// NPC definition resolver for names, combat levels, sizes and actions.
    pub npcs: Option<&'a dyn Fn(NpcDefinitionId) -> Option<&'a NpcDefinition>>,
    /// Varbit layouts.
    pub varbits: Option<&'a BTreeMap<VarbitId, VarbitDefinition>>,
    /// Interface definitions for widget bounds and text.
    pub interfaces: Option<&'a BTreeMap<InterfaceId, InterfaceDefinition>>,
}

/// Standard OSRS experience table: the level a given experience total reaches, capped at 99.
#[must_use]
#[expect(
    clippy::cast_possible_truncation,
    clippy::cast_sign_loss,
    reason = "the table step is a positive float far below u32::MAX for levels up to 99"
)]
pub fn level_for_experience(experience: u32) -> u8 {
    let mut points = 0u32;
    let mut level = 1u8;
    for next in 2..=99u8 {
        let step = f64::from(next - 1) + 300.0 * 2f64.powf(f64::from(next - 1) / 7.0);
        points += step.floor() as u32;
        if experience < points / 4 {
            return level;
        }
        level = next;
    }
    level
}

pub(crate) fn varbit_table(
    definitions: Option<&BTreeMap<VarbitId, VarbitDefinition>>,
) -> Arc<BTreeMap<u32, VarbitDef>> {
    Arc::new(
        definitions
            .into_iter()
            .flatten()
            .map(|(id, def)| {
                (
                    id.get(),
                    VarbitDef {
                        varp: def.varp.get(),
                        lsb: def.low,
                        msb: def.high,
                    },
                )
            })
            .collect(),
    )
}

pub(crate) fn lifecycle(state: &GameState, frame: &Frame) -> GameStateKind {
    if state.lifecycle() != SessionLifecycle::Active {
        GameStateKind::LoginScreen
    } else if frame.loading || state.loaded_scene().is_none() {
        GameStateKind::Loading
    } else {
        GameStateKind::LoggedIn
    }
}

pub(crate) fn client(state: &GameState, frame: &Frame) -> ClientSnapshot {
    let window = state.interfaces().window();
    let origin = state
        .initial_placement()
        .map(|placement| placement.map_anchor);
    ClientSnapshot {
        state: lifecycle(state, frame),
        tick: state
            .current_tick()
            .map_or(0, rustclient_model::GameTick::get),
        cycle: state.scenery().cycle().get(),
        fps: frame.fps,
        canvas: (window.canvas.width, window.canvas.height),
        viewport: Rect {
            x: 0,
            y: 0,
            width: window.canvas.width.cast_signed(),
            height: window.canvas.height.cast_signed(),
        },
        resizable: window.mode == rustclient_model::WindowMode::Resizable,
        focused: frame.focused,
        camera: frame.camera,
        scene_base: origin.map_or((0, 0), |tile| (tile.x, tile.y)),
        run_energy: state.entities().run_energy(),
        weight: state.entities().run_weight(),
    }
}

fn world(tile: rustclient_model::WorldTile) -> WorldPoint {
    WorldPoint::new(tile.x, tile.y, tile.plane.get())
}

fn sequence(value: Option<rustclient_model::SequenceId>) -> Option<u32> {
    value.map(rustclient_model::SequenceId::get)
}

fn overhead(value: Option<&rustclient_game_state::ActorOverhead>) -> Option<String> {
    value.map(|overhead| overhead.text.text.clone())
}

pub(crate) fn player(entity: &PlayerEntity, local: bool) -> PlayerSnapshot {
    let appearance = entity.appearance.as_ref();
    let movement = appearance.map(|appearance| appearance.movement_sequences);
    let pose = movement.and_then(|movement| match entity.locomotion {
        Locomotion::Idle => movement.idle,
        Locomotion::Walk => movement.walk,
        Locomotion::Run => movement.run.or(movement.walk),
        Locomotion::Turn => movement.turn.or(movement.idle),
    });
    PlayerSnapshot {
        actor: ActorState {
            index: u16::try_from(entity.index.get()).unwrap_or(u16::MAX),
            world: world(entity.tile),
            orientation: entity.current_orientation,
            animation: sequence(entity.sequence.sequence),
            pose: sequence(pose),
            graphic: None,
            overhead_text: overhead(entity.overhead.as_ref()),
            interacting: None,
            hitsplats: Vec::new(),
            healthbars: Vec::new(),
        },
        name: appearance
            .map(|a| a.display_name.clone())
            .unwrap_or_default(),
        combat_level: appearance.map_or(0, |a| a.combat_level),
        skill_level: appearance.map_or(0, |a| a.skill_level),
        is_local: local,
        hidden: appearance.is_some_and(|a| a.hidden),
    }
}

pub(crate) fn npc(entity: &NpcEntity, definition: Option<&NpcDefinition>) -> NpcSnapshot {
    let pose = definition.and_then(|def| match entity.locomotion {
        Locomotion::Idle | Locomotion::Turn => def.idle_sequence,
        Locomotion::Walk => def.walk_sequence,
        Locomotion::Run => def.run_sequences[0].or(def.walk_sequence),
    });
    let size = definition.map_or(1, |def| def.size.max(1));
    NpcSnapshot {
        actor: ActorState {
            index: u16::try_from(entity.index.get()).unwrap_or(u16::MAX),
            world: world(entity.tile),
            orientation: entity.current_orientation,
            animation: sequence(entity.sequence.sequence),
            pose: sequence(pose),
            graphic: None,
            overhead_text: overhead(entity.overhead.as_ref()),
            interacting: None,
            hitsplats: Vec::new(),
            healthbars: Vec::new(),
        },
        id: entity.definition_id.get(),
        name: entity
            .name
            .clone()
            .or_else(|| definition.and_then(|def| def.name.clone())),
        combat_level: definition
            .and_then(|def| def.combat_level)
            .map_or(0, |level| u8::try_from(level).unwrap_or(u8::MAX)),
        size: (size, size),
        actions: definition.map_or_else(Vec::new, |def| def.actions.to_vec()),
    }
}

pub(crate) fn skills(state: &GameState) -> Vec<SkillLevel> {
    Skill::ALL
        .iter()
        .map(|skill| {
            let id = rustclient_model::SkillId::new(u32::try_from(skill.index()).unwrap_or(0));
            state
                .entities()
                .skills()
                .get(&id)
                .map_or_else(SkillLevel::default, |values| SkillLevel {
                    real: level_for_experience(values.experience),
                    boosted: u8::try_from(values.current).unwrap_or(u8::MAX),
                    experience: i32::try_from(values.experience).unwrap_or(i32::MAX),
                })
        })
        .collect()
}

pub(crate) fn containers(state: &GameState) -> BTreeMap<u32, Vec<Option<ItemStack>>> {
    state
        .inventories()
        .iter()
        .map(|(id, slots): (InventoryId, _)| {
            (
                id.get(),
                slots
                    .iter()
                    .map(|slot| {
                        slot.map(|stack| ItemStack {
                            id: stack.item.get(),
                            quantity: stack.amount,
                        })
                    })
                    .collect(),
            )
        })
        .collect()
}

/// Message classes follow the `RuneLite` `ChatMessageType` ordinals the 240 client uses.
fn chat_kind(channel: u32) -> ChatKind {
    match channel {
        0 => ChatKind::Game,
        2 => ChatKind::Public,
        3 => ChatKind::Private,
        9 | 41 => ChatKind::Channel,
        other => ChatKind::Other(u16::try_from(other).unwrap_or(u16::MAX)),
    }
}

pub(crate) fn chat(state: &GameState) -> Vec<ChatLine> {
    state
        .chat()
        .entries()
        .iter()
        .map(|entry| ChatLine {
            id: entry.id,
            kind: chat_kind(entry.message.channel.get()),
            name: entry.message.sender.clone(),
            text: entry.message.text.clone(),
        })
        .collect()
}

pub(crate) fn widget_groups(state: &GameState) -> Vec<u16> {
    state
        .interfaces()
        .groups()
        .into_iter()
        .filter_map(|group| u16::try_from(group.get()).ok())
        .collect()
}

fn rect(bounds: WidgetBounds) -> Rect {
    Rect {
        x: bounds.x,
        y: bounds.y,
        width: bounds.width,
        height: bounds.height,
    }
}

/// Resolve every painted widget into a snapshot with canvas bounds.
pub(crate) fn widgets(
    state: &GameState,
    definitions: &BTreeMap<InterfaceId, InterfaceDefinition>,
) -> Vec<WidgetSnapshot> {
    let Ok(tree) = WidgetTree::build(
        state.interfaces(),
        definitions,
        WidgetBounds::canvas(state.interfaces()),
    ) else {
        return Vec::new();
    };
    tree.nodes
        .iter()
        .filter_map(|node| {
            let widget = resolve_widget(state.interfaces(), definitions, node.address).ok()?;
            let runtime: Option<&WidgetRuntime> = state.interfaces().runtime().get(&node.address);
            let (text, sprite) = match &widget.body {
                WidgetBody::Text { text, .. } => (Some(text.clone()), None),
                WidgetBody::Sprite { sprite, .. } => (None, sprite.map(|id: SpriteId| id.get())),
                _ => (None, None),
            };
            Some(WidgetSnapshot {
                id: (widget_group(node.address.widget) << 16)
                    | (node.address.widget.component & 0xFFFF),
                child_index: node
                    .address
                    .child
                    .map_or(-1, |child| i32::try_from(child).unwrap_or(i32::MAX)),
                bounds: rect(node.bounds),
                clip: rect(node.clip),
                text,
                sprite,
                item: runtime
                    .and_then(|runtime| runtime.item)
                    .map(|(item, amount)| (item.get(), amount)),
                hidden: widget.hidden,
                actions: widget.actions,
            })
        })
        .collect()
}

const fn widget_group(widget: rustclient_model::WidgetId) -> u32 {
    widget.interface.get() & 0xFFFF
}

/// Which snapshot categories a set of grants needs.
pub(crate) fn wants(granted: &CapabilitySet, capability: Capability) -> bool {
    granted.contains(capability)
}

pub(crate) fn vars(state: &GameState, table: &Arc<BTreeMap<u32, VarbitDef>>) -> Vars {
    Vars {
        varps: state
            .scenery()
            .variables()
            .iter()
            .map(|(id, value)| (id.get(), *value))
            .collect(),
        varbits: Arc::clone(table),
    }
}

#[cfg(test)]
mod tests {
    use super::level_for_experience;

    #[test]
    fn experience_thresholds_match_the_osrs_table() {
        assert_eq!(level_for_experience(0), 1);
        assert_eq!(level_for_experience(82), 1);
        assert_eq!(level_for_experience(83), 2);
        assert_eq!(level_for_experience(1_154), 10);
        assert_eq!(level_for_experience(13_034_430), 98);
        assert_eq!(level_for_experience(13_034_431), 99);
        assert_eq!(level_for_experience(200_000_000), 99);
    }
}
