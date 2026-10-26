//! Projection of authoritative game state into plugin API 0.2 snapshots and events (ADR-0006).
//!
//! The bridge owns no semantic state. It builds one [`GameSnapshot`] per update from `GameState`
//! and immutable cache definitions, then derives [`GameEvent`]s by diffing against the previous
//! projection. The previous projection is discarded when the session ends.

#![forbid(unsafe_code)]

mod diff;
mod project;

pub use diff::{Timing, VarbitIndex, derive};
pub use project::{Frame, Sources, level_for_experience};

use rustclient_game_state::GameState;
use rustclient_plugin_api::{
    Capability, CapabilitySet, GameEvent, GameSnapshot, VarbitDef, WidgetSnapshot,
};
use std::collections::BTreeMap;
use std::sync::Arc;

/// Builds snapshots and events. One per session.
#[derive(Default)]
pub struct Projector {
    previous: Option<GameSnapshot>,
    last_cycle: Option<u64>,
    last_tick: Option<u64>,
    varbits: Option<VarbitCache>,
    widgets: Option<WidgetCache>,
}

/// Varbit table plus its reverse index, keyed by the identity of the source table.
struct VarbitCache {
    address: usize,
    len: usize,
    table: Arc<BTreeMap<u32, VarbitDef>>,
    index: VarbitIndex,
}

/// Widget snapshots keyed by interface revision and canvas size.
struct WidgetCache {
    revision: u64,
    canvas: (u32, u32),
    widgets: Arc<[WidgetSnapshot]>,
}

impl Projector {
    /// A projector with no history.
    #[must_use]
    pub fn new() -> Self {
        Self::default()
    }

    /// Forget history; the next projection reports the world as freshly loaded.
    pub fn reset(&mut self) {
        *self = Self::default();
    }

    fn varbit_table(
        &mut self,
        sources: &Sources<'_>,
    ) -> (Arc<BTreeMap<u32, VarbitDef>>, VarbitIndex) {
        let key = sources.varbits.map_or((0, 0), |table| {
            (std::ptr::from_ref(table) as usize, table.len())
        });
        if let Some(cache) = &self.varbits
            && (cache.address, cache.len) == key
        {
            return (Arc::clone(&cache.table), cache.index.clone());
        }
        let table = project::varbit_table(sources.varbits);
        let index = VarbitIndex::new(&table);
        self.varbits = Some(VarbitCache {
            address: key.0,
            len: key.1,
            table: Arc::clone(&table),
            index: index.clone(),
        });
        (table, index)
    }

    /// Project the current state and derive the events since the previous projection.
    ///
    /// `wanted` is the union of grants across enabled plugins; categories nobody can read are not
    /// built. Events for categories whose grant appears later report existing contents as new.
    pub fn project(
        &mut self,
        state: &GameState,
        frame: &Frame,
        sources: &Sources<'_>,
        wanted: &CapabilitySet,
    ) -> (GameSnapshot, Vec<GameEvent>) {
        let (table, index) = self.varbit_table(sources);
        let client = project::client(state, frame);
        let mut snapshot = GameSnapshot {
            client,
            ..GameSnapshot::default()
        };
        let entities = state.entities();
        if project::wants(wanted, Capability::GameReadPlayers) {
            let local = entities.local_player_index;
            snapshot.local_player = local.and_then(|index| u16::try_from(index.get()).ok());
            snapshot.players = entities
                .players
                .values()
                .map(|entity| project::player(entity, Some(entity.index) == local))
                .collect();
            snapshot.players.sort_by_key(|player| player.actor.index);
        }
        if project::wants(wanted, Capability::GameReadNpcs) {
            snapshot.npcs = entities
                .npcs
                .values()
                .map(|entity| {
                    project::npc(
                        entity,
                        sources
                            .npcs
                            .and_then(|resolve| resolve(entity.definition_id)),
                    )
                })
                .collect();
            snapshot.npcs.sort_by_key(|npc| npc.actor.index);
        }
        if project::wants(wanted, Capability::GameReadSkills) {
            snapshot.skills = project::skills(state);
        }
        if project::wants(wanted, Capability::GameReadVars) {
            snapshot.vars = project::vars(state, &table);
        }
        if project::wants(wanted, Capability::GameReadInventory) {
            snapshot.containers = project::containers(state);
        }
        if project::wants(wanted, Capability::GameReadChat) {
            snapshot.chat = project::chat(state);
        }
        if project::wants(wanted, Capability::GameReadWidgets) {
            snapshot.widget_groups = project::widget_groups(state);
            snapshot.widgets = self.widget_snapshots(state, sources);
        }

        let timing = Timing {
            client_ticks: self.last_cycle.map_or(0, |last| {
                u32::try_from(snapshot.client.cycle.saturating_sub(last)).unwrap_or(u32::MAX)
            }),
            game_tick: (self.last_tick != Some(snapshot.client.tick) && self.previous.is_some())
                .then_some(snapshot.client.tick),
        };
        self.last_cycle = Some(snapshot.client.cycle);
        self.last_tick = Some(snapshot.client.tick);
        let events = if let Some(previous) = &self.previous {
            derive(previous, &snapshot, &index, timing)
        } else {
            // First projection: actors present are new and the lifecycle moved out of the
            // login screen, but values and history are not replayed as changes.
            let baseline = GameSnapshot {
                players: Vec::new(),
                npcs: Vec::new(),
                local_player: None,
                client: rustclient_plugin_api::ClientSnapshot {
                    state: rustclient_plugin_api::GameStateKind::LoginScreen,
                    ..snapshot.client
                },
                ..snapshot.clone()
            };
            derive(&baseline, &snapshot, &index, Timing::default())
        };
        self.previous = Some(snapshot.clone());
        (snapshot, events)
    }

    fn widget_snapshots(
        &mut self,
        state: &GameState,
        sources: &Sources<'_>,
    ) -> Arc<[WidgetSnapshot]> {
        let Some(definitions) = sources.interfaces else {
            return Arc::from(Vec::new());
        };
        let revision = state.interfaces().revision();
        let canvas = {
            let window = state.interfaces().window();
            (window.canvas.width, window.canvas.height)
        };
        if let Some(cache) = &self.widgets
            && cache.revision == revision
            && cache.canvas == canvas
        {
            return Arc::clone(&cache.widgets);
        }
        let widgets: Arc<[WidgetSnapshot]> = project::widgets(state, definitions).into();
        self.widgets = Some(WidgetCache {
            revision,
            canvas,
            widgets: Arc::clone(&widgets),
        });
        widgets
    }
}
