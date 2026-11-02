//! Event derivation: the difference between two consecutive projections, in committed order.
//!
//! Order per update: lifecycle and map events, timing events, players (despawn, spawn, change,
//! then per-actor changes), NPCs likewise, variables, stats, containers, widgets, chat.

use rustclient_plugin_api::{
    ActorRef, ActorState, Capability, CapabilitySet, EventInterest, GameEvent, GameEventKind,
    GameSnapshot, NpcSnapshot, PlayerSnapshot, VarKind, VarbitDef,
};
use std::collections::{BTreeMap, BTreeSet};

/// Reverse index from a varp to the varbits that live in it.
#[derive(Debug, Clone, Default)]
pub struct VarbitIndex(BTreeMap<u32, Vec<u32>>);

impl VarbitIndex {
    /// Build the index for a varbit table.
    #[must_use]
    pub fn new(varbits: &BTreeMap<u32, VarbitDef>) -> Self {
        let mut index: BTreeMap<u32, Vec<u32>> = BTreeMap::new();
        for (id, def) in varbits {
            index.entry(def.varp).or_default().push(*id);
        }
        Self(index)
    }

    fn varbits_of(&self, varp: u32) -> &[u32] {
        self.0.get(&varp).map_or(&[], Vec::as_slice)
    }
}

/// Timing facts the caller knows and the diff cannot see.
#[derive(Debug, Clone, Copy, Default)]
pub struct Timing {
    /// Client cycles elapsed since the previous update.
    pub client_ticks: u32,
    /// The gameplay tick, when it advanced since the previous update.
    pub game_tick: Option<u64>,
}

/// Derive the events that turn `prev` into `next`.
#[must_use]
pub fn derive(
    prev: &GameSnapshot,
    next: &GameSnapshot,
    varbits: &VarbitIndex,
    timing: Timing,
) -> Vec<GameEvent> {
    derive_interested(prev, next, varbits, timing, None)
}

pub(crate) fn derive_interested(
    prev: &GameSnapshot,
    next: &GameSnapshot,
    varbits: &VarbitIndex,
    timing: Timing,
    interest: Option<&CapabilitySet>,
) -> Vec<GameEvent> {
    derive_filtered(prev, next, varbits, timing, interest, EventInterest::ALL)
}

const ACTOR_CHANGES: EventInterest = EventInterest::NONE
    .with(GameEventKind::AnimationChanged)
    .with(GameEventKind::GraphicChanged)
    .with(GameEventKind::InteractingChanged)
    .with(GameEventKind::HitsplatApplied)
    .with(GameEventKind::OverheadTextChanged);
const PLAYER_EVENTS: EventInterest = ACTOR_CHANGES
    .with(GameEventKind::PlayerSpawned)
    .with(GameEventKind::PlayerDespawned)
    .with(GameEventKind::PlayerChanged);
const NPC_EVENTS: EventInterest = ACTOR_CHANGES
    .with(GameEventKind::NpcSpawned)
    .with(GameEventKind::NpcDespawned)
    .with(GameEventKind::NpcChanged);

pub(crate) fn derive_filtered(
    prev: &GameSnapshot,
    next: &GameSnapshot,
    varbits: &VarbitIndex,
    timing: Timing,
    interest: Option<&CapabilitySet>,
    kinds: EventInterest,
) -> Vec<GameEvent> {
    let wants = |capability| interest.is_none_or(|grants| grants.contains(capability));
    let mut events = Events {
        values: Vec::new(),
        kinds,
    };
    if prev.client.state != next.client.state {
        events.emit(GameEventKind::GameStateChanged, || {
            GameEvent::GameStateChanged(next.client.state)
        });
    }
    if wants(Capability::GameReadClient) && prev.client.resizable != next.client.resizable {
        events.emit(GameEventKind::ResizeableChanged, || {
            GameEvent::ResizeableChanged(next.client.resizable)
        });
    }
    if wants(Capability::GameReadClient) && prev.client.focused != next.client.focused {
        events.emit(GameEventKind::FocusChanged, || {
            GameEvent::FocusChanged(next.client.focused)
        });
    }
    if prev.client.scene_base != next.client.scene_base {
        events.emit(GameEventKind::PreMapLoad, || GameEvent::PreMapLoad);
    }
    if kinds.contains(GameEventKind::ClientTick) {
        for _ in 0..timing.client_ticks {
            events.emit(GameEventKind::ClientTick, || GameEvent::ClientTick);
        }
    }
    if let Some(tick) = timing.game_tick {
        events.emit(GameEventKind::GameTick, || GameEvent::GameTick(tick));
    }
    if wants(Capability::GameReadPlayers) && kinds.intersects(PLAYER_EVENTS) {
        players(prev, next, &mut events);
    }
    if wants(Capability::GameReadNpcs) && kinds.intersects(NPC_EVENTS) {
        npcs(prev, next, &mut events);
    }
    if wants(Capability::GameReadVars) && (kinds.contains(GameEventKind::VarbitChanged)) {
        variables(prev, next, varbits, &mut events);
    }
    if wants(Capability::GameReadSkills) && (kinds.contains(GameEventKind::StatChanged)) {
        stats(prev, next, &mut events);
    }
    if wants(Capability::GameReadInventory) && (kinds.contains(GameEventKind::ItemContainerChanged))
    {
        containers(prev, next, &mut events);
    }
    if wants(Capability::GameReadWidgets)
        && (kinds.contains(GameEventKind::WidgetLoaded)
            || kinds.contains(GameEventKind::WidgetClosed))
    {
        widgets(prev, next, &mut events);
    }
    if wants(Capability::GameReadChat) && (kinds.contains(GameEventKind::ChatMessage)) {
        chat(prev, next, &mut events);
    }
    events.values
}

struct Events {
    values: Vec<GameEvent>,
    kinds: EventInterest,
}
impl Events {
    fn emit(&mut self, kind: GameEventKind, create: impl FnOnce() -> GameEvent) {
        if self.kinds.contains(kind) {
            self.values.push(create());
        }
    }
}

fn merge<'a, T>(
    prev: &'a [T],
    next: &'a [T],
    index: impl Fn(&T) -> (u16, u64),
) -> (Vec<&'a T>, Vec<&'a T>, Vec<(&'a T, &'a T)>) {
    let mut removed = Vec::new();
    let mut added = Vec::new();
    let mut kept = Vec::new();
    let (mut a, mut b) = (0, 0);
    loop {
        match (prev.get(a), next.get(b)) {
            (Some(old), Some(new)) if index(old) == index(new) => {
                kept.push((old, new));
                a += 1;
                b += 1;
            }
            (Some(old), Some(new)) if index(old) < index(new) => {
                removed.push(old);
                a += 1;
            }
            (Some(old), None) => {
                removed.push(old);
                a += 1;
            }
            (_, Some(new)) => {
                added.push(new);
                b += 1;
            }
            (None, None) => break,
        }
    }
    (removed, added, kept)
}

fn players(prev: &GameSnapshot, next: &GameSnapshot, events: &mut Events) {
    let (removed, added, kept) = if prev.client.session == next.client.session {
        merge(&prev.players, &next.players, |p| {
            (p.actor.index, p.actor.instance)
        })
    } else {
        (
            prev.players.iter().collect(),
            next.players.iter().collect(),
            Vec::new(),
        )
    };
    for old in removed {
        events.emit(GameEventKind::PlayerDespawned, || {
            GameEvent::PlayerDespawned(Box::new(old.clone()))
        });
    }
    for new in added {
        events.emit(GameEventKind::PlayerSpawned, || {
            GameEvent::PlayerSpawned(Box::new(new.clone()))
        });
    }
    for (old, new) in kept {
        if changed_appearance(old, new) {
            events.emit(GameEventKind::PlayerChanged, || {
                GameEvent::PlayerChanged(new.actor.index)
            });
        }
        actor_changes(
            ActorRef::Player(new.actor.index),
            &old.actor,
            &new.actor,
            events,
        );
    }
}

fn changed_appearance(old: &PlayerSnapshot, new: &PlayerSnapshot) -> bool {
    old.name != new.name
        || old.combat_level != new.combat_level
        || old.skill_level != new.skill_level
        || old.hidden != new.hidden
}

fn npcs(prev: &GameSnapshot, next: &GameSnapshot, events: &mut Events) {
    let (removed, added, kept) = if prev.client.session == next.client.session {
        merge(&prev.npcs, &next.npcs, |n| {
            (n.actor.index, n.actor.instance)
        })
    } else {
        (
            prev.npcs.iter().collect(),
            next.npcs.iter().collect(),
            Vec::new(),
        )
    };
    for old in removed {
        events.emit(GameEventKind::NpcDespawned, || {
            GameEvent::NpcDespawned(Box::new(old.clone()))
        });
    }
    for new in added {
        events.emit(GameEventKind::NpcSpawned, || {
            GameEvent::NpcSpawned(Box::new(new.clone()))
        });
    }
    for (old, new) in kept {
        if old.id != new.id {
            events.emit(GameEventKind::NpcChanged, || GameEvent::NpcChanged {
                index: new.actor.index,
                previous: old.id,
            });
        }
        actor_changes_npc(old, new, events);
    }
}

fn actor_changes_npc(old: &NpcSnapshot, new: &NpcSnapshot, events: &mut Events) {
    actor_changes(
        ActorRef::Npc(new.actor.index),
        &old.actor,
        &new.actor,
        events,
    );
}

fn actor_changes(actor: ActorRef, old: &ActorState, new: &ActorState, events: &mut Events) {
    if old.animation != new.animation {
        events.emit(GameEventKind::AnimationChanged, || {
            GameEvent::AnimationChanged {
                actor,
                animation: new.animation,
            }
        });
    }
    if old.graphic != new.graphic {
        events.emit(GameEventKind::GraphicChanged, || {
            GameEvent::GraphicChanged {
                actor,
                graphic: new.graphic,
            }
        });
    }
    if old.interacting != new.interacting {
        events.emit(GameEventKind::InteractingChanged, || {
            GameEvent::InteractingChanged {
                source: actor,
                target: new.interacting,
            }
        });
    }
    if old.overhead_text != new.overhead_text {
        events.emit(GameEventKind::OverheadTextChanged, || {
            GameEvent::OverheadTextChanged {
                actor,
                text: new.overhead_text.clone(),
            }
        });
    }
    for hitsplat in &new.hitsplats {
        if !old.hitsplats.contains(hitsplat) {
            events.emit(GameEventKind::HitsplatApplied, || {
                GameEvent::HitsplatApplied {
                    actor,
                    hitsplat: *hitsplat,
                }
            });
        }
    }
}

fn variables(prev: &GameSnapshot, next: &GameSnapshot, varbits: &VarbitIndex, events: &mut Events) {
    let ids: BTreeSet<u32> = prev
        .vars
        .varps
        .keys()
        .chain(next.vars.varps.keys())
        .copied()
        .collect();
    for id in ids {
        let (old, new) = (prev.vars.varp(id), next.vars.varp(id));
        if old == new {
            continue;
        }
        events.emit(GameEventKind::VarbitChanged, || GameEvent::VarbitChanged {
            kind: VarKind::Varp,
            id,
            value: new,
        });
        for &varbit in varbits.varbits_of(id) {
            let (Some(before), Some(after)) = (
                prev.vars.varbits.get(&varbit).map(|def| def.extract(old)),
                next.vars.varbits.get(&varbit).map(|def| def.extract(new)),
            ) else {
                continue;
            };
            if before != after {
                events.emit(GameEventKind::VarbitChanged, || GameEvent::VarbitChanged {
                    kind: VarKind::Varbit,
                    id: varbit,
                    value: after,
                });
            }
        }
    }
}

fn stats(prev: &GameSnapshot, next: &GameSnapshot, events: &mut Events) {
    for (index, level) in next.skills.iter().enumerate() {
        let Some(skill) = rustclient_plugin_api::Skill::from_index(index) else {
            continue;
        };
        if prev.skills.get(index) != Some(level) {
            events.emit(GameEventKind::StatChanged, || GameEvent::StatChanged {
                skill,
                level: *level,
            });
        }
    }
}

fn containers(prev: &GameSnapshot, next: &GameSnapshot, events: &mut Events) {
    let ids: BTreeSet<u32> = prev
        .containers
        .keys()
        .chain(next.containers.keys())
        .copied()
        .collect();
    for id in ids {
        let same = prev.containers.get(&id).map(Vec::as_slice)
            == next.containers.get(&id).map(Vec::as_slice);
        if !same {
            events.emit(GameEventKind::ItemContainerChanged, || {
                GameEvent::ItemContainerChanged { container: id }
            });
        }
    }
}

fn widgets(prev: &GameSnapshot, next: &GameSnapshot, events: &mut Events) {
    for group in &prev.widget_groups {
        if !next.widget_groups.contains(group) {
            events.emit(GameEventKind::WidgetClosed, || GameEvent::WidgetClosed {
                group: *group,
            });
        }
    }
    for group in &next.widget_groups {
        if !prev.widget_groups.contains(group) {
            events.emit(GameEventKind::WidgetLoaded, || GameEvent::WidgetLoaded {
                group: *group,
            });
        }
    }
}

fn chat(prev: &GameSnapshot, next: &GameSnapshot, events: &mut Events) {
    let seen = prev.chat.last().map(|line| line.id);
    for line in &next.chat {
        if seen.is_none_or(|last| line.id > last) {
            events.emit(GameEventKind::ChatMessage, || {
                GameEvent::ChatMessage(line.clone())
            });
        }
    }
}

#[cfg(test)]
#[expect(
    clippy::field_reassign_with_default,
    reason = "tests start from an empty snapshot and set only the fields under test"
)]
mod tests {
    use super::*;
    use rustclient_plugin_api::{
        ChatKind, ChatLine, GameStateKind, Hitsplat, ItemStack, Skill, SkillLevel, Vars, WorldPoint,
    };
    use std::sync::Arc;

    fn actor(index: u16) -> ActorState {
        ActorState {
            instance: 1,
            index,
            world: WorldPoint::new(3200, 3200, 0),
            orientation: 0,
            animation: None,
            pose: None,
            graphic: None,
            overhead_text: None,
            interacting: None,
            hitsplats: Vec::new(),
            healthbars: Vec::new(),
        }
    }

    fn player(index: u16) -> PlayerSnapshot {
        PlayerSnapshot {
            actor: actor(index),
            name: format!("p{index}"),
            combat_level: 3,
            skill_level: 0,
            is_local: index == 1,
            hidden: false,
        }
    }

    fn npc(index: u16, id: u32) -> NpcSnapshot {
        NpcSnapshot {
            actor: actor(index),
            id,
            name: None,
            combat_level: 0,
            size: (1, 1),
            actions: Vec::new(),
        }
    }

    fn run(prev: &GameSnapshot, next: &GameSnapshot) -> Vec<GameEvent> {
        derive(
            prev,
            next,
            &VarbitIndex::new(&next.vars.varbits),
            Timing::default(),
        )
    }

    #[test]
    fn identical_snapshots_produce_no_events() {
        let mut snapshot = GameSnapshot::default();
        snapshot.players = vec![player(1)];
        snapshot.npcs = vec![npc(4, 10)];
        assert!(run(&snapshot, &snapshot.clone()).is_empty());
    }

    #[test]
    fn actors_spawn_despawn_and_change_in_index_order() {
        let mut prev = GameSnapshot::default();
        prev.players = vec![player(1), player(3)];
        prev.npcs = vec![npc(2, 7), npc(5, 9)];
        let mut next = GameSnapshot::default();
        next.players = vec![player(1), player(2)];
        next.npcs = vec![npc(5, 12), npc(6, 1)];
        next.players[0].name = "renamed".into();
        let events = run(&prev, &next);
        let kinds: Vec<String> = events
            .iter()
            .map(|event| match event {
                GameEvent::PlayerDespawned(p) => format!("pd{}", p.actor.index),
                GameEvent::PlayerSpawned(p) => format!("ps{}", p.actor.index),
                GameEvent::PlayerChanged(i) => format!("pc{i}"),
                GameEvent::NpcDespawned(n) => format!("nd{}", n.actor.index),
                GameEvent::NpcSpawned(n) => format!("ns{}", n.actor.index),
                GameEvent::NpcChanged { index, previous } => format!("nc{index}:{previous}"),
                other => format!("{other:?}"),
            })
            .collect();
        assert_eq!(kinds, ["pd3", "ps2", "pc1", "nd2", "ns6", "nc5:9"]);
        // Despawn events carry the last snapshot, which the new snapshot no longer holds.
        assert!(next.player(3).is_none());
    }

    #[test]
    fn actor_property_changes_emit_typed_events() {
        let mut prev = GameSnapshot::default();
        prev.players = vec![player(1)];
        prev.npcs = vec![npc(4, 10)];
        let mut next = prev.clone();
        next.players[0].actor.animation = Some(808);
        next.players[0].actor.overhead_text = Some("hi".into());
        next.npcs[0].actor.graphic = Some(86);
        next.npcs[0].actor.interacting = Some(ActorRef::Player(1));
        let hit = Hitsplat {
            kind: 16,
            amount: 3,
            cycle: 100,
        };
        next.players[0].actor.hitsplats.push(hit);
        let events = run(&prev, &next);
        assert_eq!(
            events,
            [
                GameEvent::AnimationChanged {
                    actor: ActorRef::Player(1),
                    animation: Some(808)
                },
                GameEvent::OverheadTextChanged {
                    actor: ActorRef::Player(1),
                    text: Some("hi".into())
                },
                GameEvent::HitsplatApplied {
                    actor: ActorRef::Player(1),
                    hitsplat: hit
                },
                GameEvent::GraphicChanged {
                    actor: ActorRef::Npc(4),
                    graphic: Some(86)
                },
                GameEvent::InteractingChanged {
                    source: ActorRef::Npc(4),
                    target: Some(ActorRef::Player(1))
                },
            ]
        );
        // The same hitsplat in two consecutive snapshots is not applied twice.
        assert!(run(&next, &next.clone()).is_empty());
    }

    #[test]
    fn varp_changes_fan_out_to_the_varbits_they_contain() {
        let table = Arc::new(BTreeMap::from([
            (
                10,
                VarbitDef {
                    varp: 5,
                    lsb: 0,
                    msb: 1,
                },
            ),
            (
                11,
                VarbitDef {
                    varp: 5,
                    lsb: 2,
                    msb: 3,
                },
            ),
            (
                12,
                VarbitDef {
                    varp: 6,
                    lsb: 0,
                    msb: 0,
                },
            ),
        ]));
        let prev = GameSnapshot {
            vars: Vars {
                varps: BTreeMap::from([(5, 0b0001)]),
                varbits: Arc::clone(&table),
            },
            ..GameSnapshot::default()
        };
        let next = GameSnapshot {
            vars: Vars {
                varps: BTreeMap::from([(5, 0b0101)]),
                varbits: table,
            },
            ..GameSnapshot::default()
        };
        assert_eq!(
            run(&prev, &next),
            [
                GameEvent::VarbitChanged {
                    kind: VarKind::Varp,
                    id: 5,
                    value: 5
                },
                GameEvent::VarbitChanged {
                    kind: VarKind::Varbit,
                    id: 11,
                    value: 1
                },
            ],
            "varbit 10 kept its value and varbit 12 lives in an unchanged varp"
        );
    }

    #[test]
    fn lifecycle_stats_containers_widgets_and_chat_are_reported() {
        let prev = GameSnapshot::default();
        let mut next = GameSnapshot::default();
        next.client.state = GameStateKind::LoggedIn;
        next.client.scene_base = (3136, 3136);
        next.skills = vec![
            SkillLevel {
                real: 1,
                boosted: 1,
                experience: 0,
            };
            2
        ];
        next.skills[1].experience = 83;
        let mut base = prev.clone();
        base.skills = vec![SkillLevel {
            real: 1,
            boosted: 1,
            experience: 0,
        }];
        next.containers.insert(
            93,
            vec![Some(ItemStack {
                id: 995,
                quantity: 5,
            })],
        );
        next.widget_groups = vec![161, 149];
        next.chat = vec![ChatLine {
            id: 7,
            kind: ChatKind::Game,
            name: String::new(),
            text: "hello".into(),
        }];
        let timing = Timing {
            client_ticks: 2,
            game_tick: Some(40),
        };
        let events = derive(&base, &next, &VarbitIndex::default(), timing);
        assert_eq!(
            events[0],
            GameEvent::GameStateChanged(GameStateKind::LoggedIn)
        );
        assert_eq!(events[1], GameEvent::PreMapLoad);
        assert_eq!(events[2], GameEvent::ClientTick);
        assert_eq!(events[3], GameEvent::ClientTick);
        assert_eq!(events[4], GameEvent::GameTick(40));
        assert!(events.iter().any(|event| matches!(event,
            GameEvent::StatChanged { skill: Skill::Defence, level } if level.experience == 83)));
        assert!(events.contains(&GameEvent::ItemContainerChanged { container: 93 }));
        assert!(events.contains(&GameEvent::WidgetLoaded { group: 161 }));
        assert!(events.contains(&GameEvent::WidgetLoaded { group: 149 }));
        assert!(matches!(events.last(), Some(GameEvent::ChatMessage(line)) if line.id == 7));
        // Closing is reported before loading, and old chat lines are not replayed.
        let reverse = derive(&next, &base, &VarbitIndex::default(), Timing::default());
        assert!(reverse.contains(&GameEvent::WidgetClosed { group: 161 }));
        assert!(
            !reverse
                .iter()
                .any(|e| matches!(e, GameEvent::ChatMessage(_)))
        );
    }
}
