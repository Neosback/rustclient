//! Port of `net.runelite.client.plugins.idlenotifier.IdleNotifierPlugin` (BSD-2, see `NOTICE`).
//!
//! The source subscribes to game events and, on every `GameTick`, runs a series of edge checks
//! (animation, interaction and movement idleness, idle and six-hour logout, hitpoints, prayer, run
//! energy, oxygen, special attack) that each call `Notifier.notify`. Here the same state machine runs
//! from the typed events and the snapshot, and the notifications it decides on leave through
//! `PluginOutput::notifications`.
//!
//! Time is the client cycle (20 ms), the clock the snapshot carries; the source uses wall-clock
//! `Instant`s, which agree while the client runs.
//!
//! Not ported or not live yet:
//! - The interaction branch (combat and fishing idleness, and the combat countdown that postpones the
//!   idle-logout warning) is complete against the 0.2 API, but the client does not retain the actor
//!   facts it reads (interact target, spot animations, hitsplats), so those events never arrive and the
//!   branch stays dormant until their `GameState` owner lands.
//! - Notification facets (tray, sound, flash, request focus, send when focused): `Notification` maps to
//!   one switch and the host delivers it as a system notification while the window is unfocused.
//! - `migrateConfig`: the Rust client has no pre-migration settings to convert.

use crate::{NativePlugin, idlenotifier_animations::SKILLING_ANIMATIONS};
use rustclient_plugin_api::{
    ActorRef, ClientSnapshot, GameEvent, GameStateKind, InputIdle, Notification, NotificationKind,
    NpcSnapshot, PlayerSnapshot, PluginOutput, Skill, VarKind, WorldPoint,
};
use rustclient_plugin_host::{PluginCall, PluginFault};

/// Milliseconds in one client cycle (`Constants.CLIENT_TICK_LENGTH`).
const CLIENT_TICK_MS: u64 = 20;
/// Warn this many client ticks before the idle logout (`IDLE_LOGOUT_WARNING_BUFFER`).
const IDLE_LOGOUT_WARNING_BUFFER: u32 = 20_000 / 20;
/// `COMBAT_WARNING_MILLIS`: 19 minutes.
const COMBAT_WARNING_MS: u64 = 19 * 60 * 1000;
const COMBAT_WARNING_CLIENT_TICKS: u32 = 19 * 60 * 1000 / 20;
/// `HIGHEST_MONSTER_ATTACK_SPEED`, in game ticks.
const HIGHEST_MONSTER_ATTACK_SPEED: u32 = 8;
/// `SIX_HOUR_LOGOUT_WARNING_AFTER_DURATION`: 340 minutes.
const SIX_HOUR_WARNING_AFTER_MS: u64 = 340 * 60 * 1000;
/// `Client.getIdleTimeout` default: 15000 client ticks (five minutes).
const IDLE_TIMEOUT_CLIENT_TICKS: u32 = 15_000;
/// The mouse counts as active for this long after a click (`getMouseLastPressedMillis`).
const RECENT_CLICK_CLIENT_TICKS: u32 = 1000 / 20;
/// The keyboard counts as active below this many idle ticks.
const RECENT_KEY_CLIENT_TICKS: u32 = 10;

/// `AnimationID.LUNAR_HUMAN_MAGIC_SUMMON2` and the spot animation that makes it a pie bake.
const LUNAR_HUMAN_MAGIC_SUMMON2: u32 = 4413;
const QUEST_LUNAR_BAKEPIE_SPOTANIM: u32 = 746;
/// `SpotanimID.FAILEDSPELL_IMPACT`.
const FAILEDSPELL_IMPACT: u32 = 85;
/// `AnimationID.HUMAN_WOODCUTTING_RUNE_AXE`, assumed while a group gathering buff is shown.
const HUMAN_WOODCUTTING_RUNE_AXE: u32 = 867;
/// `VarPlayerID.GROUP_GATHERING_ACTIVE_BUFF` and its "no buff bar" value.
const GROUP_GATHERING_ACTIVE_BUFF: u32 = 4007;
const BUFF_BAR_NOT_DISPLAYED: i32 = -1;
/// `VarPlayerID.SA_ENERGY`, in tenths of a percent.
const SA_ENERGY: u32 = 300;
/// `VarbitID.FOSSIL_UNDERWATER_OXYGEN`, in tenths of a percent.
const FOSSIL_UNDERWATER_OXYGEN: u32 = 5811;
/// `VarbitID.NZONE_ABSORB_POTION_EFFECTS`: absorption points counted as extra hitpoints.
const NZONE_ABSORB_POTION_EFFECTS: u32 = 3956;

const APP_NAME: &str = "RustClient";
const FISHING_SPOT: &str = "Fishing spot";

#[expect(
    clippy::struct_excessive_bools,
    reason = "one edge flag per source notification"
)]
pub(crate) struct IdleNotifier {
    last_animating: Option<u64>,
    /// `lastAnimation`, `None` for the source's `-1`.
    last_animation: Option<u32>,
    last_interacting: Option<u64>,
    last_interact: Option<ActorRef>,
    last_moving: Option<u64>,
    last_position: Option<WorldPoint>,
    notify_position: bool,
    notify_hitpoints: bool,
    notify_prayer: bool,
    should_notify_low_energy: bool,
    should_notify_high_energy: bool,
    notify_oxygen: bool,
    notify_idle_logout: bool,
    notify_six_hour_logout: bool,
    last_spec_energy: i32,
    last_combat_countdown: u32,
    six_hour_warning: Option<u64>,
    ready: bool,
    last_interact_was_combat: bool,
    state: GameStateKind,
    pending: Vec<Notification>,
}

/// What the checks read from one update, so they stay free of host plumbing.
struct Facts<'a> {
    now: u64,
    local_animation: Option<u32>,
    local_interacting: Option<ActorRef>,
    local_world: WorldPoint,
    hitpoints: (i32, i32),
    prayer: (i32, i32),
    absorption: i32,
    energy_percent: i32,
    oxygen_tenths: i32,
    spec_tenths: i32,
    input: Option<InputIdle>,
    wait_ms: u64,
    config: &'a rustclient_plugin_host::PluginConfig,
}

impl IdleNotifier {
    pub(crate) fn new() -> Self {
        Self {
            last_animating: None,
            last_animation: None,
            last_interacting: None,
            last_interact: None,
            last_moving: None,
            last_position: None,
            notify_position: false,
            notify_hitpoints: true,
            notify_prayer: true,
            should_notify_low_energy: false,
            should_notify_high_energy: false,
            notify_oxygen: true,
            notify_idle_logout: true,
            notify_six_hour_logout: true,
            last_spec_energy: 1000,
            last_combat_countdown: 0,
            six_hour_warning: None,
            ready: false,
            last_interact_was_combat: false,
            state: GameStateKind::LoginScreen,
            pending: Vec::new(),
        }
    }

    /// `resetTimers`: forget idle timers whose action no longer matches the player's.
    fn reset_timers(&mut self, local_animation: Option<u32>, local_interacting: Option<ActorRef>) {
        self.last_animating = None;
        let login = self.state == GameStateKind::LoginScreen;
        if login || self.last_animation != local_animation {
            self.last_animation = None;
        }
        self.last_interacting = None;
        if login || self.last_interact != local_interacting {
            self.last_interact = None;
        }
    }

    fn notify(&mut self, enabled: bool, name: Option<&str>, body: &str) {
        if !enabled {
            return;
        }
        let title = match name {
            Some(name) if !name.is_empty() => format!("{APP_NAME} - {name}"),
            _ => APP_NAME.to_owned(),
        };
        self.pending.push(Notification {
            title,
            body: body.to_owned(),
            kind: NotificationKind::Standard,
        });
    }

    fn check_npc_interaction(&mut self, target: &NpcSnapshot, batch: &Batch<'_>) {
        let attackable = target
            .actions
            .iter()
            .flatten()
            .any(|action| action == "Attack");
        let fishing = target
            .name
            .as_deref()
            .is_some_and(|name| name.contains(FISHING_SPOT));
        if attackable || fishing {
            self.reset_timers(batch.view.0, batch.view.1);
            self.last_interact = Some(ActorRef::Npc(target.actor.index));
            self.last_interacting = Some(batch.now);
            self.last_interact_was_combat = attackable;
        }
    }

    fn check_full_spec_energy(&mut self, current: i32, threshold_percent: i32) -> bool {
        let threshold = threshold_percent.saturating_mul(10);
        if threshold == 0 {
            self.last_spec_energy = current;
            return false;
        }
        // Regenerated over the threshold, and by a regeneration-sized step.
        let notify = self.last_spec_energy < threshold
            && current >= threshold
            && current - self.last_spec_energy <= 100;
        self.last_spec_energy = current;
        notify
    }

    fn check_low_oxygen(&mut self, tenths: i32, threshold_percent: i32) -> bool {
        if threshold_percent == 0 {
            return false;
        }
        // `threshold >= oxygen * 0.1` with the varbit in tenths of a percent.
        if threshold_percent.saturating_mul(10) >= tenths {
            if !self.notify_oxygen {
                self.notify_oxygen = true;
                return true;
            }
        } else {
            self.notify_oxygen = false;
        }
        false
    }

    fn check_low_hitpoints(
        &mut self,
        (real, boosted): (i32, i32),
        absorption: i32,
        threshold: i32,
    ) -> bool {
        if threshold == 0 || real <= threshold {
            return false;
        }
        if boosted + absorption <= threshold {
            if !self.notify_hitpoints {
                self.notify_hitpoints = true;
                return true;
            }
        } else {
            self.notify_hitpoints = false;
        }
        false
    }

    fn check_low_prayer(&mut self, (real, boosted): (i32, i32), threshold: i32) -> bool {
        if threshold == 0 || real <= threshold {
            return false;
        }
        if boosted <= threshold {
            if !self.notify_prayer {
                self.notify_prayer = true;
                return true;
            }
        } else {
            self.notify_prayer = false;
        }
        false
    }

    fn check_low_energy(&mut self, percent: i32, threshold: i32) -> bool {
        if threshold >= 100 {
            return false;
        }
        if percent <= threshold {
            if self.should_notify_low_energy {
                self.should_notify_low_energy = false;
                return true;
            }
        } else {
            self.should_notify_low_energy = true;
        }
        false
    }

    fn check_high_energy(&mut self, percent: i32, threshold: i32) -> bool {
        if threshold == 0 {
            return false;
        }
        if percent >= threshold {
            if self.should_notify_high_energy {
                self.should_notify_high_energy = false;
                return true;
            }
        } else {
            self.should_notify_high_energy = true;
        }
        false
    }

    fn check_interaction_idle(&mut self, facts: &Facts<'_>) -> bool {
        if self.last_interact.is_none() {
            return false;
        }
        if facts.local_interacting.is_none() {
            if self
                .last_interacting
                .is_some_and(|at| facts.now >= at + facts.wait_ms)
                && self.last_combat_countdown == 0
            {
                self.last_interact = None;
                self.last_interacting = None;
                // Keep the animation check from firing as well.
                self.last_animation = None;
                self.last_animating = None;
                return true;
            }
        } else {
            self.last_interacting = Some(facts.now);
        }
        false
    }

    fn check_idle_logout(&mut self, input: InputIdle) -> bool {
        // Client-side AFK comes first: the server only disconnects after it.
        let idle = input.keyboard.min(input.mouse);
        if idle < IDLE_TIMEOUT_CLIENT_TICKS - IDLE_LOGOUT_WARNING_BUFFER {
            self.notify_idle_logout = true;
            return false;
        }
        // Without hitsplats the server can AFK-kick.
        if self.last_combat_countdown == 0 {
            return std::mem::take(&mut self.notify_idle_logout);
        }
        // In combat only the long out-of-combat timer applies.
        let last_interaction_ms = u64::from(input.mouse_press) * CLIENT_TICK_MS;
        if last_interaction_ms < COMBAT_WARNING_MS || input.keyboard < COMBAT_WARNING_CLIENT_TICKS {
            self.notify_idle_logout = true;
            return false;
        }
        std::mem::take(&mut self.notify_idle_logout)
    }

    fn check_six_hour_logout(&mut self, now: u64) -> bool {
        let Some(warning) = self.six_hour_warning else {
            return false;
        };
        if now >= warning {
            if self.notify_six_hour_logout {
                self.notify_six_hour_logout = false;
                return true;
            }
        } else {
            self.notify_six_hour_logout = true;
        }
        false
    }

    fn check_animation_idle(&mut self, facts: &Facts<'_>) -> bool {
        if self.last_animation.is_none() {
            return false;
        }
        if facts.local_animation.is_none() {
            if self
                .last_animating
                .is_some_and(|at| facts.now >= at + facts.wait_ms)
            {
                self.last_animation = None;
                self.last_animating = None;
                // Keep the interaction check from firing as well.
                self.last_interact = None;
                self.last_interacting = None;
                return true;
            }
        } else {
            self.last_animating = Some(facts.now);
        }
        false
    }

    fn check_movement_idle(&mut self, facts: &Facts<'_>) -> bool {
        let Some(last) = self.last_position else {
            self.last_position = Some(facts.local_world);
            return false;
        };
        if last == facts.local_world {
            if self.notify_position
                && facts.local_animation.is_none()
                && self
                    .last_moving
                    .is_some_and(|at| facts.now >= at + facts.wait_ms)
            {
                self.notify_position = false;
                // Only when this is not just the end of an animation.
                return self.last_animation.is_none();
            }
        } else {
            self.notify_position = true;
            self.last_position = Some(facts.local_world);
            self.last_moving = Some(facts.now);
        }
        false
    }

    /// `onGameTick` after the player and input gates.
    fn tick(&mut self, facts: &Facts<'_>, name: Option<&str>) {
        let config = facts.config;
        if let Some(input) = facts.input
            && self.check_idle_logout(input)
        {
            self.notify(
                config.boolean("logoutidle"),
                name,
                "You are about to log out from idling too long!",
            );
        }
        if self.check_six_hour_logout(facts.now) {
            self.notify(
                config.boolean("sixHourLogout"),
                name,
                "You are about to log out from being online for 6 hours!",
            );
        }
        if self.check_animation_idle(facts) {
            self.notify(config.boolean("animationidle"), name, "You are now idle!");
        }
        if self.check_movement_idle(facts) {
            self.notify(
                config.boolean("movementidle"),
                name,
                "You have stopped moving!",
            );
        }
        if self.check_interaction_idle(facts) {
            let message = if self.last_interact_was_combat {
                "You are now out of combat!"
            } else {
                "You are now idle!"
            };
            self.notify(config.boolean("interactionidle"), name, message);
        }
        if self.check_low_hitpoints(
            facts.hitpoints,
            facts.absorption,
            config.integer("hitpoints"),
        ) {
            self.notify(
                config.boolean("hitpointsNotification"),
                name,
                "You have low hitpoints!",
            );
        }
        if self.check_low_prayer(facts.prayer, config.integer("prayer")) {
            self.notify(
                config.boolean("prayerNotification"),
                name,
                "You have low prayer!",
            );
        }
        if self.check_low_energy(facts.energy_percent, config.integer("lowEnergy")) {
            self.notify(
                config.boolean("lowEnergyNotification"),
                name,
                "You have low run energy!",
            );
        }
        if self.check_high_energy(facts.energy_percent, config.integer("highEnergy")) {
            self.notify(
                config.boolean("highEnergyNotification"),
                name,
                "You have restored run energy!",
            );
        }
        if self.check_low_oxygen(facts.oxygen_tenths, config.integer("oxygen")) {
            self.notify(
                config.boolean("oxygenNotification"),
                name,
                "You have low oxygen!",
            );
        }
        if self.check_full_spec_energy(facts.spec_tenths, config.integer("spec")) {
            self.notify(
                config.boolean("specNotification"),
                name,
                "You have restored spec energy!",
            );
        }
    }
}

fn level(call: &mut PluginCall<'_>, skill: Skill) -> (i32, i32) {
    call.skills()
        .ok()
        .and_then(|skills| skills.get(skill.index()))
        .map_or((0, 0), |level| {
            (i32::from(level.real), i32::from(level.boosted))
        })
}

/// What the player was doing when this batch of events was dispatched.
type LocalView = (Option<u32>, Option<ActorRef>);

/// The inputs shared by every event of one batch.
struct Batch<'a> {
    now: u64,
    local: Option<&'a PlayerSnapshot>,
    /// The local player's animation and target, advanced as events in the batch change them.
    view: LocalView,
}

impl Batch<'_> {
    fn is_local(&self, actor: ActorRef) -> bool {
        self.local
            .is_some_and(|player| actor == ActorRef::Player(player.actor.index))
    }
}

impl IdleNotifier {
    fn game_state_changed(&mut self, next: GameStateKind, batch: &Batch<'_>) {
        let previous = std::mem::replace(&mut self.state, next);
        self.last_interacting = None;
        match next {
            GameStateKind::LoginScreen => self.reset_timers(batch.view.0, batch.view.1),
            GameStateKind::LoggingIn | GameStateKind::ConnectionLost => self.ready = true,
            // The client has no separate "logging in" state: leaving the title screen is the
            // same moment.
            GameStateKind::Loading if previous == GameStateKind::LoginScreen => self.ready = true,
            GameStateKind::LoggedIn if self.ready => {
                self.six_hour_warning = Some(batch.now + SIX_HOUR_WARNING_AFTER_MS);
                self.ready = false;
                self.reset_timers(batch.view.0, batch.view.1);
            }
            GameStateKind::LoggedIn | GameStateKind::Loading => {}
        }
    }

    fn animation_changed(&mut self, animation: Option<u32>, batch: &mut Batch<'_>) {
        if self.state != GameStateKind::LoggedIn {
            return;
        }
        batch.view.0 = animation;
        let graphic = batch.local.and_then(|player| player.actor.graphic);
        match animation {
            Some(id) if SKILLING_ANIMATIONS.binary_search(&id).is_ok() => {
                self.reset_timers(batch.view.0, batch.view.1);
                self.last_animation = Some(id);
                self.last_animating = Some(batch.now);
            }
            Some(LUNAR_HUMAN_MAGIC_SUMMON2) if graphic == Some(QUEST_LUNAR_BAKEPIE_SPOTANIM) => {
                self.reset_timers(batch.view.0, batch.view.1);
                self.last_animation = animation;
                self.last_animating = Some(batch.now);
            }
            // The source's summon case falls through into the "no animation" one.
            Some(LUNAR_HUMAN_MAGIC_SUMMON2) | None => self.last_animating = Some(batch.now),
            // An unknown animation is assumed invalid: no notification for it.
            Some(_) => {
                self.last_animation = None;
                self.last_animating = None;
            }
        }
    }

    fn interacting_changed(
        &mut self,
        target: Option<ActorRef>,
        npc: Option<&NpcSnapshot>,
        batch: &Batch<'_>,
    ) {
        if target.is_some() {
            self.last_interact = None;
        } else {
            self.last_interacting = Some(batch.now);
        }
        if let (Some(ActorRef::Npc(_)), Some(npc)) = (target, npc) {
            self.check_npc_interaction(npc, batch);
        }
    }

    /// `onGameTick` up to the player and input gates.
    fn game_tick(&mut self, call: &mut PluginCall<'_>, client: ClientSnapshot, batch: &Batch<'_>) {
        self.last_combat_countdown = self.last_combat_countdown.saturating_sub(1);
        let Some(local) = batch.local else {
            self.reset_timers(batch.view.0, batch.view.1);
            return;
        };
        // Someone who just clicked or typed is not idle.
        if client.input_idle.is_some_and(|input| {
            input.mouse_press < RECENT_CLICK_CLIENT_TICKS
                || input.keyboard < RECENT_KEY_CLIENT_TICKS
        }) {
            self.reset_timers(local.actor.animation, local.actor.interacting);
            return;
        }
        let absorption = call
            .varbit(NZONE_ABSORB_POTION_EFFECTS)
            .ok()
            .flatten()
            .unwrap_or(0);
        let facts = Facts {
            now: batch.now,
            local_animation: local.actor.animation,
            local_interacting: local.actor.interacting,
            local_world: local.actor.world,
            hitpoints: level(call, Skill::Hitpoints),
            prayer: level(call, Skill::Prayer),
            absorption,
            energy_percent: i32::from(client.run_energy / 100),
            oxygen_tenths: call
                .varbit(FOSSIL_UNDERWATER_OXYGEN)
                .ok()
                .flatten()
                .unwrap_or(0),
            spec_tenths: call.varp(SA_ENERGY).unwrap_or(0),
            input: client.input_idle,
            wait_ms: u64::try_from(call.config.integer("timeout")).unwrap_or(0),
            config: call.config,
        };
        let name = (!local.name.is_empty()).then_some(local.name.as_str());
        self.tick(&facts, name);
    }
}

/// The NPC with scene index `index`, from the index-ordered snapshot list.
fn npc_at<'a>(call: &mut PluginCall<'a>, index: u16) -> Option<&'a NpcSnapshot> {
    let npcs = call.npcs().ok()?;
    let at = npcs
        .binary_search_by_key(&index, |npc| npc.actor.index)
        .ok()?;
    npcs.get(at)
}

impl NativePlugin for IdleNotifier {
    fn init(&mut self, call: &mut PluginCall<'_>) -> Result<(), PluginFault> {
        // `startUp`: the six-hour mark cannot be known when enabled while already logged in.
        *self = Self::new();
        self.state = call.client().map(|client| client.state).unwrap_or_default();
        Ok(())
    }

    fn events(
        &mut self,
        call: &mut PluginCall<'_>,
        events: &[GameEvent],
    ) -> Result<(), PluginFault> {
        let Ok(client) = call.client() else {
            return Ok(());
        };
        let local = call.local_player().ok().flatten();
        let mut batch = Batch {
            now: client.cycle * CLIENT_TICK_MS,
            local,
            view: local.map_or((None, None), |player| {
                (player.actor.animation, player.actor.interacting)
            }),
        };
        for event in events {
            match event {
                GameEvent::GameStateChanged(next) => self.game_state_changed(*next, &batch),
                GameEvent::AnimationChanged { actor, animation } if batch.is_local(*actor) => {
                    self.animation_changed(*animation, &mut batch);
                }
                GameEvent::InteractingChanged { source, target } if batch.is_local(*source) => {
                    let npc = match target {
                        Some(ActorRef::Npc(index)) => npc_at(call, *index),
                        _ => None,
                    };
                    self.interacting_changed(*target, npc, &batch);
                }
                // Some NPCs start combat through an option other than "Attack" and change form.
                GameEvent::NpcChanged { index, .. }
                    if batch.local.is_some_and(|player| {
                        player.actor.interacting == Some(ActorRef::Npc(*index))
                    }) =>
                {
                    if let Some(npc) = npc_at(call, *index) {
                        self.check_npc_interaction(npc, &batch);
                    }
                }
                GameEvent::HitsplatApplied { actor, hitsplat }
                    if batch.is_local(*actor) && hitsplat.is_mine() =>
                {
                    self.last_combat_countdown = HIGHEST_MONSTER_ATTACK_SPEED;
                }
                GameEvent::GraphicChanged { actor, graphic }
                    if batch.is_local(*actor) && *graphic == Some(FAILEDSPELL_IMPACT) =>
                {
                    self.last_combat_countdown = HIGHEST_MONSTER_ATTACK_SPEED;
                }
                GameEvent::VarbitChanged {
                    kind: VarKind::Varp,
                    id: GROUP_GATHERING_ACTIVE_BUFF,
                    value: BUFF_BAR_NOT_DISPLAYED,
                } => {
                    self.reset_timers(batch.view.0, batch.view.1);
                    self.last_animation = Some(HUMAN_WOODCUTTING_RUNE_AXE);
                    self.last_animating = Some(batch.now);
                }
                GameEvent::GameTick(_) => self.game_tick(call, client, &batch),
                _ => {}
            }
        }
        Ok(())
    }

    fn output(&mut self, _call: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
        Ok(PluginOutput {
            notifications: std::mem::take(&mut self.pending),
            ..PluginOutput::default()
        })
    }
}
