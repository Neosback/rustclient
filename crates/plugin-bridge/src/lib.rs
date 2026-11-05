//! Projection of authoritative game state into plugin API 0.2 snapshots and events (ADR-0006).
//!
//! The bridge owns no semantic state. It builds one [`GameSnapshot`] per update from `GameState`
//! and immutable cache definitions, then derives [`GameEvent`]s by diffing against the previous
//! projection. The previous projection is discarded when the session ends.

#![forbid(unsafe_code)]

mod diff;
mod project;

pub use diff::{Timing, VarbitIndex, derive};
pub use project::{Frame, ItemFacts, Sources, level_for_experience};

use rustclient_game_state::GameState;
use rustclient_plugin_api::{
    Capability, CapabilitySet, GameEvent, GameSnapshot, VarbitDef, WidgetSnapshot,
};
use std::collections::BTreeMap;
use std::sync::Arc;

/// Builds snapshots and events. One per session.
#[derive(Default)]
pub struct Projector {
    previous: Option<Arc<GameSnapshot>>,
    previous_domains: Option<rustclient_plugin_api::EventInterest>,
    last_cycle: Option<u64>,
    last_tick: Option<u64>,
    varbits: Option<VarbitCache>,
    widgets: Option<WidgetCache>,
    /// Journal position of the previous projection, so unchanged domains can be carried forward.
    cursor: Option<rustclient_game_state::ProjectionCursor>,
    /// Grants, source revision and varbit table identity the previous projection used.
    reuse_key: Option<ReuseKey>,
    /// Testing switch: rebuild every domain, as before reuse existed.
    rebuild_always: bool,
}

#[derive(Debug, Clone, PartialEq, Eq)]
struct ReuseKey {
    /// Shell session identity: a new connection generation never reuses a previous journal.
    session: u64,
    wanted: CapabilitySet,
    revision: u64,
    varbits: (usize, usize),
}

/// Snapshot domains whose committed state, grants and sources did not change since the previous
/// projection. Their data moves forward unchanged and their events are not derived.
#[derive(Debug, Clone, Copy, Default)]
#[expect(
    clippy::struct_excessive_bools,
    reason = "one independent carry-forward decision per snapshot domain"
)]
struct Reuse {
    /// Players, the local player index and skills (`ENTITIES`).
    entities: bool,
    /// NPCs: entities, the varps their morphs read, and NPC definitions.
    npcs: bool,
    vars: bool,
    containers: bool,
    chat: bool,
    ground_items: bool,
}
impl Reuse {
    /// Event classes that only reused domains produce; they are not derived.
    fn skipped_kinds(self) -> rustclient_plugin_api::EventInterest {
        use rustclient_plugin_api::{EventInterest, GameEventKind as K};
        let mut kinds = EventInterest::NONE;
        let actor = [
            K::AnimationChanged,
            K::GraphicChanged,
            K::InteractingChanged,
            K::HitsplatApplied,
            K::OverheadTextChanged,
        ];
        if self.entities && self.npcs {
            for kind in actor {
                kinds = kinds.with(kind);
            }
        }
        if self.entities {
            for kind in [
                K::PlayerSpawned,
                K::PlayerDespawned,
                K::PlayerChanged,
                K::StatChanged,
            ] {
                kinds = kinds.with(kind);
            }
        }
        if self.npcs {
            for kind in [K::NpcSpawned, K::NpcDespawned, K::NpcChanged] {
                kinds = kinds.with(kind);
            }
        }
        if self.vars {
            kinds = kinds.with(K::VarbitChanged);
        }
        if self.containers {
            kinds = kinds.with(K::ItemContainerChanged);
        }
        if self.chat {
            kinds = kinds.with(K::ChatMessage);
        }
        if self.ground_items {
            kinds = kinds
                .with(K::ItemSpawned)
                .with(K::ItemDespawned)
                .with(K::ItemQuantityChanged);
        }
        kinds
    }
}

/// Move a field out of the previous snapshot when nothing else holds it, else clone it.
fn carry<T: Clone + Default>(
    previous: &mut Option<Arc<GameSnapshot>>,
    field: impl Fn(&mut GameSnapshot) -> &mut T,
) -> T {
    let Some(previous) = previous.as_mut() else {
        return T::default();
    };
    match Arc::get_mut(previous) {
        Some(unique) => std::mem::take(field(unique)),
        None => field(&mut (**previous).clone()).clone(),
    }
}

/// Varbit table plus its reverse index, keyed by the identity of the source table.
struct VarbitCache {
    address: usize,
    len: usize,
    table: Arc<BTreeMap<u32, VarbitDef>>,
    index: Arc<VarbitIndex>,
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

    /// The most recent projection, for synchronous stages between updates (the menu stage).
    #[must_use]
    pub fn latest(&self) -> Option<Arc<GameSnapshot>> {
        self.previous.clone()
    }

    /// Forget history; the next projection reports the world as freshly loaded.
    pub fn reset(&mut self) {
        *self = Self::default();
    }

    fn varbit_table(
        &mut self,
        sources: &Sources<'_>,
    ) -> (Arc<BTreeMap<u32, VarbitDef>>, Arc<VarbitIndex>) {
        let key = sources.varbits.map_or((0, 0), |table| {
            (std::ptr::from_ref(table) as usize, table.len())
        });
        if let Some(cache) = &self.varbits
            && (cache.address, cache.len) == key
        {
            return (Arc::clone(&cache.table), Arc::clone(&cache.index));
        }
        let table = project::varbit_table(sources.varbits);
        let index = Arc::new(VarbitIndex::new(&table));
        self.varbits = Some(VarbitCache {
            address: key.0,
            len: key.1,
            table: Arc::clone(&table),
            index: Arc::clone(&index),
        });
        (table, index)
    }

    /// Build current approved facts without advancing event history or timing baselines.
    /// Used for one instance's bootstrap; it cannot consume another plugin's events.
    pub fn snapshot(
        &mut self,
        state: &GameState,
        frame: &Frame,
        sources: &Sources<'_>,
        wanted: &CapabilitySet,
    ) -> GameSnapshot {
        self.build(state, frame, sources, wanted, Reuse::default())
    }

    /// Rebuild every domain on every projection, disabling reuse (for equivalence tests).
    pub fn set_rebuild_always(&mut self, rebuild: bool) {
        self.rebuild_always = rebuild;
    }

    /// The domains that may be carried forward, given the journal since the previous projection.
    fn reusable(
        &self,
        state: &GameState,
        frame: &Frame,
        sources: &Sources<'_>,
        wanted: &CapabilitySet,
    ) -> Reuse {
        let key = ReuseKey {
            session: frame.session,
            wanted: wanted.clone(),
            revision: sources.revision,
            varbits: sources.varbits.map_or((0, 0), |table| {
                (std::ptr::from_ref(table) as usize, table.len())
            }),
        };
        let (Some(cursor), Some(_)) = (self.cursor, &self.previous) else {
            return Reuse::default();
        };
        if self.rebuild_always || self.reuse_key.as_ref() != Some(&key) {
            return Reuse::default();
        }
        let changed = state.projection_changes(cursor).changed_domains();
        let entities = !changed.entities_changed();
        Reuse {
            entities,
            npcs: entities && !changed.scenery_changed(),
            vars: !changed.scenery_changed(),
            containers: !changed.inventory_changed(),
            chat: !changed.chat_changed(),
            ground_items: !changed.ground_items_changed(),
        }
    }

    fn build(
        &mut self,
        state: &GameState,
        frame: &Frame,
        sources: &Sources<'_>,
        wanted: &CapabilitySet,
        reuse: Reuse,
    ) -> GameSnapshot {
        let client = project::client(state, frame);
        let mut snapshot = GameSnapshot {
            client,
            ..GameSnapshot::default()
        };
        let entities = state.entities();
        if reuse.entities {
            snapshot.local_player = self.previous.as_ref().and_then(|p| p.local_player);
            snapshot.players = carry(&mut self.previous, |s| &mut s.players);
            snapshot.skills = carry(&mut self.previous, |s| &mut s.skills);
        } else if project::wants(wanted, Capability::GameReadPlayers) {
            let local = entities.local_player_index;
            snapshot.local_player = local.and_then(|index| u16::try_from(index.get()).ok());
            snapshot.players = entities
                .players
                .values()
                .map(|entity| {
                    project::player(entity, Some(entity.index) == local, state.scenery().cycle())
                })
                .collect();
            snapshot.players.sort_by_key(|player| player.actor.index);
        }
        if reuse.npcs {
            snapshot.npcs = carry(&mut self.previous, |s| &mut s.npcs);
        } else if project::wants(wanted, Capability::GameReadNpcs) {
            snapshot.npcs = entities
                .npcs
                .values()
                .map(|entity| {
                    project::npc(
                        entity,
                        sources
                            .npcs
                            .and_then(|resolve| resolve(entity.definition_id)),
                        state.scenery().cycle(),
                    )
                })
                .collect();
            snapshot.npcs.sort_by_key(|npc| npc.actor.index);
        }
        if !reuse.entities && project::wants(wanted, Capability::GameReadSkills) {
            snapshot.skills = project::skills(state);
        }
        if reuse.vars {
            snapshot.vars = carry(&mut self.previous, |s| &mut s.vars);
        } else if project::wants(wanted, Capability::GameReadVars) {
            let (table, _) = self.varbit_table(sources);
            snapshot.vars = project::vars(state, &table);
        }
        if reuse.containers {
            snapshot.containers = carry(&mut self.previous, |s| &mut s.containers);
        } else if project::wants(wanted, Capability::GameReadInventory) {
            snapshot.containers = project::containers(state);
        }
        if reuse.chat {
            snapshot.chat = carry(&mut self.previous, |s| &mut s.chat);
        } else if project::wants(wanted, Capability::GameReadChat) {
            snapshot.chat = project::chat(state);
        }
        if reuse.ground_items {
            snapshot.ground_items = carry(&mut self.previous, |s| &mut s.ground_items);
        } else if project::wants(wanted, Capability::GameReadGroundItems) {
            snapshot.ground_items = project::ground_items(state, sources.items);
        }
        if project::wants(wanted, Capability::GameReadWidgets) {
            snapshot.widget_groups = project::widget_groups(state);
            snapshot.widgets = self.widget_snapshots(state, sources);
        }

        snapshot
    }

    /// Project the current state and derive the events since the previous projection.
    ///
    /// `wanted` is the union of grants across enabled plugins; categories nobody can read are not
    /// built. Newly observed categories establish a baseline without synthetic shared events.
    pub fn project(
        &mut self,
        state: &GameState,
        frame: &Frame,
        sources: &Sources<'_>,
        wanted: &CapabilitySet,
    ) -> (Arc<GameSnapshot>, Vec<GameEvent>) {
        self.project_with_events(state, frame, sources, wanted, true)
    }

    /// Project needed query facts while skipping event derivation if no running subscriber exists.
    /// History still advances: enabling a subscriber cannot replay unobserved cycles or changes.
    pub fn project_with_events(
        &mut self,
        state: &GameState,
        frame: &Frame,
        sources: &Sources<'_>,
        wanted: &CapabilitySet,
        emit_events: bool,
    ) -> (Arc<GameSnapshot>, Vec<GameEvent>) {
        self.project_interested(state, frame, sources, wanted, emit_events.then_some(wanted))
    }

    /// Keep query snapshots complete while deriving only categories a running subscriber can read.
    /// `None` skips derivation; `Some(empty)` retains ungated timing/lifecycle events.
    pub fn project_interested(
        &mut self,
        state: &GameState,
        frame: &Frame,
        sources: &Sources<'_>,
        wanted: &CapabilitySet,
        interest: Option<&CapabilitySet>,
    ) -> (Arc<GameSnapshot>, Vec<GameEvent>) {
        self.project_filtered(
            state,
            frame,
            sources,
            wanted,
            interest,
            rustclient_plugin_api::EventInterest::ALL,
        )
    }

    /// Advance projection history while constructing only explicitly requested event classes.
    /// Query domains remain independent of callback interests.
    pub fn project_filtered(
        &mut self,
        state: &GameState,
        frame: &Frame,
        sources: &Sources<'_>,
        wanted: &CapabilitySet,
        interest: Option<&CapabilitySet>,
        kinds: rustclient_plugin_api::EventInterest,
    ) -> (Arc<GameSnapshot>, Vec<GameEvent>) {
        let reuse = self.reusable(state, frame, sources, wanted);
        let snapshot = self.build(state, frame, sources, wanted, reuse);
        self.cursor = Some(state.projection_cursor());
        self.reuse_key = Some(ReuseKey {
            session: frame.session,
            wanted: wanted.clone(),
            revision: sources.revision,
            varbits: sources.varbits.map_or((0, 0), |table| {
                (std::ptr::from_ref(table) as usize, table.len())
            }),
        });
        let domains = rustclient_plugin_api::EventInterest::ALL.permitted_by(wanted);
        let kinds = kinds.intersection(self.previous_domains.unwrap_or(domains));
        // Carried-forward domains equal the previous projection, so they produce no events; their
        // fields were moved out of `previous` and must not be compared.
        let kinds = kinds.difference(reuse.skipped_kinds());
        let index = (kinds.contains(rustclient_plugin_api::GameEventKind::VarbitChanged)
            && interest.is_some_and(|grants| project::wants(grants, Capability::GameReadVars))
            && project::wants(wanted, Capability::GameReadVars))
        .then(|| self.varbit_table(sources).1);
        let empty_index = VarbitIndex::default();
        let index = index.as_deref().unwrap_or(&empty_index);
        let timing = Timing {
            client_ticks: self.last_cycle.map_or(0, |last| {
                u32::try_from(snapshot.client.cycle.saturating_sub(last)).unwrap_or(u32::MAX)
            }),
            game_tick: (self.last_tick != Some(snapshot.client.tick) && self.previous.is_some())
                .then_some(snapshot.client.tick),
        };
        self.last_cycle = Some(snapshot.client.cycle);
        self.last_tick = Some(snapshot.client.tick);
        let events = if interest.is_none() {
            Vec::new()
        } else if let Some(previous) = &self.previous {
            diff::derive_filtered(previous, &snapshot, index, timing, interest, kinds)
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
            diff::derive_filtered(
                &baseline,
                &snapshot,
                index,
                Timing::default(),
                interest,
                kinds,
            )
        };
        self.previous_domains = Some(domains);
        let snapshot = Arc::new(snapshot);
        self.previous = Some(Arc::clone(&snapshot));
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
