use crate::{
    AuthenticatedPlayer, InitialPlacement, LoginFailure, LoginStatus, TitleInformation, TitleScreen,
};
use core::fmt;

use rustclient_model::{GameTick, Revision, SceneDescriptor};

use crate::{GameState, OperationCommit, SemanticEvent, SessionLifecycle};

/// Version of the canonical semantic fingerprint encoding.
///
/// Any change to field ordering, tags, integer encoding, or fingerprint
/// algorithm that changes canonical output must increment this version.
pub const SEMANTIC_FINGERPRINT_VERSION: u16 = 25;

const FNV_OFFSET_BASIS: u64 = 0xCBF2_9CE4_8422_2325;
const FNV_PRIME: u64 = 0x0000_0100_0000_01B3;
const STATE_DOMAIN: &[u8] = b"rustclient:state";
const COMMIT_STREAM_DOMAIN: &[u8] = b"rustclient:commit-stream";

/// Canonical fingerprint of observable authoritative semantic state.
///
/// This is a deterministic regression/debugging identity, not a cryptographic
/// integrity primitive. The value is produced by versioned `FNV-1a-64` over an
/// explicitly tagged canonical semantic encoding.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct StateFingerprint(u64);

impl StateFingerprint {
    /// Return the exact canonical 64-bit fingerprint value.
    #[must_use]
    pub const fn get(self) -> u64 {
        self.0
    }
}

impl fmt::Display for StateFingerprint {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(formatter, "{:016x}", self.0)
    }
}

/// Canonical fingerprint of an ordered stream of committed semantic outputs.
///
/// Commit order participates in the fingerprint. This is deterministic
/// regression/debugging metadata, not a cryptographic integrity primitive.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct CommitStreamFingerprint(u64);

impl CommitStreamFingerprint {
    /// Return the exact canonical 64-bit fingerprint value.
    #[must_use]
    pub const fn get(self) -> u64 {
        self.0
    }
}

impl fmt::Display for CommitStreamFingerprint {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(formatter, "{:016x}", self.0)
    }
}

/// Incremental builder for an ordered committed-output fingerprint.
///
/// The builder stores only fingerprint accumulator state. It is not an event
/// queue, replay log, or second semantic authority.
#[derive(Debug, Clone, Copy)]
pub struct CommitStreamFingerprintBuilder {
    fingerprint: StableFingerprint64,
}

impl CommitStreamFingerprintBuilder {
    /// Start an empty canonical committed-output stream.
    #[must_use]
    pub fn new() -> Self {
        Self {
            fingerprint: StableFingerprint64::for_domain(COMMIT_STREAM_DOMAIN),
        }
    }

    /// Append one successful operation commit in authoritative commit order.
    pub fn push(&mut self, commit: OperationCommit) {
        // Explicit item marker prevents concatenation ambiguity as commit shapes
        // evolve within a fingerprint schema version.
        self.fingerprint.write_u8(1);
        write_commit(&mut self.fingerprint, commit);
    }

    /// Finish the stream and return its canonical fingerprint.
    #[must_use]
    pub fn finish(mut self) -> CommitStreamFingerprint {
        // Explicit end marker gives the empty stream and every finite prefix a
        // canonical terminal representation.
        self.fingerprint.write_u8(0);
        CommitStreamFingerprint(self.fingerprint.finish())
    }
}

impl Default for CommitStreamFingerprintBuilder {
    fn default() -> Self {
        Self::new()
    }
}

impl GameState {
    /// Compute the canonical fingerprint of currently observable semantic state.
    ///
    /// Only authoritative semantic values participate. Internal layout,
    /// pointers, renderer/platform state, wall time, and randomized collection
    /// iteration are excluded by construction.
    #[must_use]
    #[expect(
        clippy::missing_panics_doc,
        clippy::too_many_lines,
        reason = "all counts are bounded by validated GameState transitions"
    )]
    pub fn semantic_fingerprint(&self) -> StateFingerprint {
        let mut fingerprint = StableFingerprint64::for_domain(STATE_DOMAIN);

        fingerprint.write_u8(match self.lifecycle() {
            SessionLifecycle::Inactive => 0,
            SessionLifecycle::Active => 1,
        });
        write_optional_revision(&mut fingerprint, self.session_revision());
        write_optional_tick(&mut fingerprint, self.current_tick());
        write_title(&mut fingerprint, self.title_screen());
        fingerprint.write_bytes(&self.chat().filters());
        fingerprint.write_u32(
            u32::try_from(self.chat().filter_text().len()).expect("bounded chat filter"),
        );
        fingerprint.write_bytes(self.chat().filter_text().as_bytes());
        fingerprint.write_u32(self.chat().next_id());
        fingerprint.write_u32(u32::try_from(self.chat().entries().len()).expect("bounded chat"));
        for entry in self.chat().entries() {
            fingerprint.write_u32(entry.id);
            fingerprint.write_u64(entry.cycle.get());
            fingerprint.write_u32(entry.message.channel.get());
            for text in [&entry.message.sender, &entry.message.text] {
                fingerprint.write_u32(u32::try_from(text.len()).expect("bounded message"));
                fingerprint.write_bytes(text.as_bytes());
            }
        }
        match self.authenticated_player() {
            None => fingerprint.write_u8(0),
            Some(player) => {
                fingerprint.write_u8(1);
                write_player(&mut fingerprint, player);
            }
        }
        match self.initial_placement() {
            None => fingerprint.write_u8(0),
            Some(placement) => {
                fingerprint.write_u8(1);
                write_placement(&mut fingerprint, placement);
            }
        }

        match self.loaded_scene() {
            None => fingerprint.write_u8(0),
            Some(scene) => {
                fingerprint.write_u8(1);
                write_scene(&mut fingerprint, scene);
            }
        }
        write_destination(&mut fingerprint, self.destination());
        let scenery = self.scenery();
        fingerprint.write_u64(scenery.cycle().get());
        fingerprint.write_bytes(
            &u32::try_from(scenery.variables().len())
                .expect("bounded variables")
                .to_le_bytes(),
        );
        for (id, value) in scenery.variables() {
            fingerprint.write_bytes(&id.get().to_le_bytes());
            fingerprint.write_bytes(&value.to_le_bytes());
        }
        fingerprint.write_bytes(
            &u32::try_from(scenery.locations().len())
                .expect("bounded overrides")
                .to_le_bytes(),
        );
        for (key, value) in scenery.locations() {
            write_location_key(&mut fingerprint, *key);
            write_one_location_override(&mut fingerprint, *value);
        }

        match self.selected_tab() {
            None => fingerprint.write_u8(0),
            Some(tab) => {
                fingerprint.write_u8(1);
                fingerprint.write_u8(u8::try_from(tab).unwrap_or(255));
            }
        }

        let inventories = self.inventories();
        fingerprint
            .write_u32(u32::try_from(inventories.iter().len()).expect("bounded inventories"));
        for (id, slots) in inventories.iter() {
            fingerprint.write_u32(id.get());
            fingerprint.write_u32(u32::try_from(slots.len()).expect("bounded inventory slots"));
            for stack in slots {
                match stack {
                    None => fingerprint.write_u8(0),
                    Some(stack) => {
                        fingerprint.write_u8(1);
                        fingerprint.write_u32(stack.item.get());
                        fingerprint.write_u32(stack.amount);
                    }
                }
            }
        }
        interfaces::write(&mut fingerprint, self.interfaces());
        fingerprint.write_u64(self.scenery().server_variables().len() as u64);
        for (id, value) in self.scenery().server_variables() {
            fingerprint.write_u32(id.get());
            fingerprint.write_u32(value.cast_unsigned());
        }
        let entities = self.entities();
        fingerprint.write_u64(entities.player_serial);
        fingerprint.write_u64(entities.outside_players.len() as u64);
        for (index, player) in &entities.outside_players {
            fingerprint.write_u32(index.get());
            fingerprint.write_u32(player.grid.region_size());
            fingerprint.write_u32(player.grid.region_count());
            fingerprint.write_u32(player.region.x.cast_unsigned());
            fingerprint.write_u32(player.region.y.cast_unsigned());
            fingerprint.write_u8(player.region.plane.get());
            fingerprint.write_u16(player.orientation);
            write_speed(&mut fingerprint, player.path_speed);
            write_appearance(&mut fingerprint, player.appearance.as_ref());
        }
        fingerprint.write_u32(entities.reboot_cycles());
        fingerprint.write_u32(
            u32::try_from(entities.reboot_message().len()).expect("bounded reboot message"),
        );
        fingerprint.write_bytes(entities.reboot_message().as_bytes());
        fingerprint.write_bytes(&entities.run_energy().to_le_bytes());
        fingerprint.write_bytes(&entities.run_weight().to_le_bytes());
        fingerprint.write_u64(entities.skills().len() as u64);
        for (id, skill) in entities.skills() {
            fingerprint.write_u32(id.get());
            fingerprint.write_u32(skill.experience);
            fingerprint.write_bytes(&skill.current.to_le_bytes());
            fingerprint.write_bytes(&skill.invisible.to_le_bytes());
        }
        match entities.local_player_index() {
            None => fingerprint.write_u8(0),
            Some(idx) => {
                fingerprint.write_u8(1);
                fingerprint.write_u32(idx.get());
            }
        }
        fingerprint.write_bytes(
            &u32::try_from(entities.players().len())
                .expect("bounded players")
                .to_le_bytes(),
        );
        for (idx, player) in entities.players() {
            fingerprint.write_u64(player.instance.get());
            match player.tracking_grid {
                None => fingerprint.write_u8(0),
                Some(grid) => {
                    fingerprint.write_u8(1);
                    fingerprint.write_u32(grid.region_size());
                    fingerprint.write_u32(grid.region_count());
                }
            }
            fingerprint.write_u32(idx.get());
            fingerprint.write_bytes(&player.tile.x.to_le_bytes());
            fingerprint.write_bytes(&player.tile.y.to_le_bytes());
            fingerprint.write_u8(player.tile.plane.get());
            fingerprint.write_u16(player.orientation);
            fingerprint.write_u16(player.current_orientation);
            write_overhead(&mut fingerprint, player.overhead.as_ref());
            write_speed(&mut fingerprint, player.path_speed);
            write_speed(&mut fingerprint, player.movement_speed);
            write_actor_path(&mut fingerprint, player.path_queue, player.tile);
            match player.exact_move {
                None => fingerprint.write_u8(0),
                Some(exact) => {
                    fingerprint.write_u8(1);
                    for tile in [exact.start, exact.end] {
                        fingerprint.write_u32(tile.x.cast_unsigned());
                        fingerprint.write_u32(tile.y.cast_unsigned());
                        fingerprint.write_u8(tile.plane.get());
                    }
                    fingerprint.write_u64(exact.starts_at.get());
                    fingerprint.write_u64(exact.ends_at.get());
                    fingerprint.write_u16(exact.direction);
                }
            }
            fingerprint.write_u32(player.animation_cycle);
            fingerprint.write_u8(match player.locomotion {
                crate::Locomotion::Idle => 0,
                crate::Locomotion::Walk => 1,
                crate::Locomotion::Run => 2,
                crate::Locomotion::Turn => 3,
            });
            write_appearance(&mut fingerprint, player.appearance.as_ref());
            fingerprint.write_u32(player.sequence.cycle.cast_unsigned());
            fingerprint.write_u32(player.sequence.delay.cast_unsigned());
            match player.sequence.frame {
                None => fingerprint.write_u8(0),
                Some(frame) => {
                    fingerprint.write_u8(1);
                    fingerprint.write_u32(frame);
                }
            }
            match player.sequence.sequence {
                None => fingerprint.write_u8(0),
                Some(seq) => {
                    fingerprint.write_u8(1);
                    fingerprint.write_u32(seq.get());
                }
            }
            write_sequence_scheduler(&mut fingerprint, player.sequence);
            write_active_sequence_timing(
                &mut fingerprint,
                player.sequence_timing_resolved,
                player.sequence.sequence,
                &entities.sequence_timings,
            );
        }
        fingerprint.write_bytes(
            &u32::try_from(entities.npcs().len())
                .expect("bounded npcs")
                .to_le_bytes(),
        );
        fingerprint.write_u64(entities.npc_serial);
        for (idx, npc) in entities.npcs() {
            fingerprint.write_u64(npc.instance.get());
            fingerprint.write_u32(idx.get());
            fingerprint.write_u32(npc.definition_id.get());
            fingerprint.write_bytes(&npc.tile.x.to_le_bytes());
            fingerprint.write_bytes(&npc.tile.y.to_le_bytes());
            fingerprint.write_u8(npc.tile.plane.get());
            fingerprint.write_u16(npc.orientation);
            fingerprint.write_u16(npc.current_orientation);
            write_overhead(&mut fingerprint, npc.overhead.as_ref());
            match npc.path {
                None => fingerprint.write_u8(0),
                Some(path) => {
                    fingerprint.write_u8(1);
                    fingerprint.write_u64(path.serial);
                    write_tile(&mut fingerprint, path.first);
                    match path.second {
                        None => fingerprint.write_u8(0),
                        Some(tile) => {
                            fingerprint.write_u8(1);
                            write_tile(&mut fingerprint, tile);
                        }
                    }
                    write_speed(&mut fingerprint, path.speed);
                }
            }
            write_actor_path(&mut fingerprint, npc.path_queue, npc.tile);
            match npc.exact_move {
                None => fingerprint.write_u8(0),
                Some(exact) => {
                    fingerprint.write_u8(1);
                    write_tile(&mut fingerprint, exact.start);
                    write_tile(&mut fingerprint, exact.end);
                    fingerprint.write_u64(exact.starts_at.get());
                    fingerprint.write_u64(exact.ends_at.get());
                    fingerprint.write_u16(exact.direction);
                }
            }
            fingerprint.write_u32(npc.animation_cycle);
            match npc.sequence.sequence {
                None => fingerprint.write_u8(0),
                Some(seq) => {
                    fingerprint.write_u8(1);
                    fingerprint.write_u32(seq.get());
                }
            }
            fingerprint.write_u8(match npc.locomotion {
                crate::Locomotion::Idle => 0,
                crate::Locomotion::Walk => 1,
                crate::Locomotion::Run => 2,
                crate::Locomotion::Turn => 3,
            });
            match &npc.name {
                None => fingerprint.write_u8(0),
                Some(name) => {
                    fingerprint.write_u8(1);
                    fingerprint.write_u64(u64::try_from(name.len()).expect("bounded NPC name"));
                    fingerprint.write_bytes(name.as_bytes());
                }
            }
            for enabled in npc.actions.enabled() {
                fingerprint.write_u8(u8::from(enabled));
            }
            match npc.sequence.frame {
                None => fingerprint.write_u8(0),
                Some(frame) => {
                    fingerprint.write_u8(1);
                    fingerprint.write_u32(frame);
                }
            }
            fingerprint.write_bytes(&npc.sequence.cycle.to_le_bytes());
            fingerprint.write_bytes(&npc.sequence.delay.to_le_bytes());
            write_sequence_scheduler(&mut fingerprint, npc.sequence);
            write_active_sequence_timing(
                &mut fingerprint,
                npc.sequence_timing_resolved,
                npc.sequence.sequence,
                &entities.sequence_timings,
            );
        }
        StateFingerprint(fingerprint.finish())
    }
}

fn write_tile(fingerprint: &mut StableFingerprint64, tile: rustclient_model::WorldTile) {
    fingerprint.write_u32(tile.x.cast_unsigned());
    fingerprint.write_u32(tile.y.cast_unsigned());
    fingerprint.write_u8(tile.plane.get());
}

fn write_speed(fingerprint: &mut StableFingerprint64, speed: rustclient_model::MoveSpeed) {
    use rustclient_model::MoveSpeed;
    fingerprint.write_u8(match speed {
        MoveSpeed::Stationary => 0,
        MoveSpeed::Crawl => 1,
        MoveSpeed::Walk => 2,
        MoveSpeed::Run => 3,
    });
}

fn write_actor_path(
    fingerprint: &mut StableFingerprint64,
    path: crate::ActorPathQueue,
    fallback: rustclient_model::WorldTile,
) {
    let fine = path.fine_position(fallback);
    fingerprint.write_bytes(&fine.x.to_le_bytes());
    fingerprint.write_bytes(&fine.y.to_le_bytes());
    match path.acceleration_policy() {
        None => fingerprint.write_u8(0),
        Some(policy) => {
            fingerprint.write_u8(1);
            fingerprint.write_u8(policy.step_six_after);
            fingerprint.write_u8(policy.step_eight_after);
        }
    }
    fingerprint.write_bytes(&path.catch_up_cycles().to_le_bytes());
    fingerprint.write_u8(path.len());
    for entry in path.iter() {
        write_tile(fingerprint, entry.target);
        write_speed(fingerprint, entry.speed);
    }
}

fn write_sequence_scheduler(
    fingerprint: &mut StableFingerprint64,
    slot: rustclient_model::ActorSequenceSlot,
) {
    match slot.policy {
        None => fingerprint.write_u8(0),
        Some(policy) => {
            fingerprint.write_u8(1);
            fingerprint.write_u8(policy.restart_mode);
            fingerprint.write_u8(policy.replacement_priority);
            fingerprint.write_u8(policy.movement_gate_with_backlog);
            fingerprint.write_u8(policy.movement_gate_without_backlog);
            fingerprint.write_u16(policy.max_loops);
        }
    }
    fingerprint.write_u32(slot.iterations);
    fingerprint.write_u32(slot.path_backlog_at_assignment);
}

fn write_active_sequence_timing(
    fingerprint: &mut StableFingerprint64,
    resolved: bool,
    sequence: Option<rustclient_model::SequenceId>,
    timings: &std::collections::BTreeMap<
        rustclient_model::SequenceId,
        rustclient_model::SequenceTiming,
    >,
) {
    if !resolved {
        fingerprint.write_u8(0);
        return;
    }
    let Some(sequence) = sequence else {
        // Preserve an impossible-but-constructible state as distinct instead of collapsing it into
        // an unresolved actor. Valid transitions clear the resolved bit with the action.
        fingerprint.write_u8(3);
        return;
    };
    let Some(timing) = timings.get(&sequence) else {
        fingerprint.write_u8(3);
        return;
    };
    match timing {
        rustclient_model::SequenceTiming::Legacy {
            frame_delays,
            frame_step,
        } => {
            fingerprint.write_u8(1);
            fingerprint.write_u32(
                u32::try_from(frame_delays.len()).expect("bounded sequence frame delays"),
            );
            for delay in frame_delays {
                fingerprint.write_u16(*delay);
            }
            match frame_step {
                None => fingerprint.write_u8(0),
                Some(step) => {
                    fingerprint.write_u8(1);
                    fingerprint.write_u16(*step);
                }
            }
        }
        rustclient_model::SequenceTiming::CachedModel { range } => {
            fingerprint.write_u8(2);
            match range {
                None => fingerprint.write_u8(0),
                Some((start, end)) => {
                    fingerprint.write_u8(1);
                    fingerprint.write_u16(*start);
                    fingerprint.write_u16(*end);
                }
            }
        }
    }
}

fn write_appearance(
    f: &mut StableFingerprint64,
    appearance: Option<&rustclient_model::PlayerAppearance>,
) {
    use rustclient_model::{Gender, SlotContent};
    let Some(a) = appearance else {
        f.write_u8(0);
        return;
    };
    f.write_u8(1);
    f.write_u8(match a.gender {
        Gender::Male => 0,
        Gender::Female => 1,
    });
    for slot in a.slots {
        match slot {
            SlotContent::Empty => f.write_u8(0),
            SlotContent::Kit(id) => {
                f.write_u8(1);
                f.write_u32(id.get());
            }
            SlotContent::Item(id) => {
                f.write_u8(2);
                f.write_u32(id.get());
            }
        }
    }
    f.write_bytes(&a.colors);
    let m = a.movement_sequences;
    for sequence in [
        m.idle,
        m.turn,
        m.walk,
        m.walk_back,
        m.walk_left,
        m.walk_right,
        m.run,
    ] {
        match sequence {
            None => f.write_u8(0),
            Some(id) => {
                f.write_u8(1);
                f.write_u32(id.get());
            }
        }
    }
    f.write_u32(u32::try_from(a.display_name.len()).expect("bounded display name"));
    f.write_bytes(a.display_name.as_bytes());
    f.write_u8(a.combat_level);
    f.write_u16(a.skill_level);
    f.write_u8(u8::from(a.hidden));
}

#[derive(Debug, Clone, Copy)]
struct StableFingerprint64 {
    value: u64,
}

impl StableFingerprint64 {
    fn for_domain(domain: &[u8]) -> Self {
        let mut fingerprint = Self {
            value: FNV_OFFSET_BASIS,
        };
        fingerprint.write_bytes(domain);
        fingerprint.write_u16(SEMANTIC_FINGERPRINT_VERSION);
        fingerprint
    }

    fn write_bytes(&mut self, bytes: &[u8]) {
        for byte in bytes {
            self.value ^= u64::from(*byte);
            self.value = self.value.wrapping_mul(FNV_PRIME);
        }
    }

    fn write_u8(&mut self, value: u8) {
        self.write_bytes(&[value]);
    }

    fn write_u16(&mut self, value: u16) {
        self.write_bytes(&value.to_le_bytes());
    }

    fn write_u32(&mut self, value: u32) {
        self.write_bytes(&value.to_le_bytes());
    }

    fn write_u64(&mut self, value: u64) {
        self.write_bytes(&value.to_le_bytes());
    }

    const fn finish(self) -> u64 {
        self.value
    }
}

fn write_optional_revision(fingerprint: &mut StableFingerprint64, revision: Option<Revision>) {
    match revision {
        None => fingerprint.write_u8(0),
        Some(revision) => {
            fingerprint.write_u8(1);
            write_revision(fingerprint, revision);
        }
    }
}

fn write_revision(fingerprint: &mut StableFingerprint64, revision: Revision) {
    fingerprint.write_u16(revision.major());
    match revision.subrevision() {
        None => fingerprint.write_u8(0),
        Some(subrevision) => {
            fingerprint.write_u8(1);
            fingerprint.write_u16(subrevision);
        }
    }
}

fn write_optional_tick(fingerprint: &mut StableFingerprint64, tick: Option<GameTick>) {
    match tick {
        None => fingerprint.write_u8(0),
        Some(tick) => {
            fingerprint.write_u8(1);
            fingerprint.write_u64(tick.get());
        }
    }
}

fn write_commit(fingerprint: &mut StableFingerprint64, commit: OperationCommit) {
    match commit.event() {
        None => fingerprint.write_u8(0),
        Some(event) => {
            fingerprint.write_u8(1);
            write_event(fingerprint, event);
        }
    }

    let delta = commit.delta();
    fingerprint.write_u8(u8::from(delta.lifecycle_changed()));
    fingerprint.write_u8(u8::from(delta.game_tick_changed()));
    fingerprint.write_u8(u8::from(delta.title_changed()));
    fingerprint.write_u8(u8::from(delta.connection_changed()));
    fingerprint.write_u8(u8::from(delta.scene_changed()));
    fingerprint.write_u8(u8::from(delta.scenery_changed()));
    fingerprint.write_u8(u8::from(delta.client_cycle_changed()));
    fingerprint.write_u8(u8::from(delta.entities_changed()));
    fingerprint.write_u8(u8::from(delta.gameframe_changed()));
    fingerprint.write_u8(u8::from(delta.inventory_changed()));
    fingerprint.write_u8(u8::from(delta.interfaces_changed()));
}

fn write_event(fingerprint: &mut StableFingerprint64, event: SemanticEvent) {
    match event {
        SemanticEvent::DestinationChanged(destination) => {
            fingerprint.write_u8(21);
            write_destination(fingerprint, destination);
        }
        SemanticEvent::LocationAreaReset { origin, size } => {
            fingerprint.write_u8(20);
            write_tile(fingerprint, origin);
            for value in size {
                fingerprint.write_u32(value);
            }
        }
        SemanticEvent::ClientCyclesAdvanced { previous, current } => {
            fingerprint.write_u8(7);
            fingerprint.write_u64(previous.get());
            fingerprint.write_u64(current.get());
        }
        SemanticEvent::VariableChanged {
            id,
            previous,
            current,
        } => {
            fingerprint.write_u8(8);
            fingerprint.write_bytes(&id.get().to_le_bytes());
            fingerprint.write_bytes(&previous.to_le_bytes());
            fingerprint.write_bytes(&current.to_le_bytes());
        }
        SemanticEvent::VariablesReset => fingerprint.write_u8(9),
        SemanticEvent::LocationChanged {
            key,
            previous,
            current,
        } => {
            fingerprint.write_u8(10);
            write_location_key(fingerprint, key);
            write_location_override(fingerprint, previous, current);
        }
        SemanticEvent::MapRebuilt(placement) => {
            fingerprint.write_u8(13);
            write_placement(fingerprint, placement);
        }
        SemanticEvent::SceneInstalled(scene) => {
            fingerprint.write_u8(6);
            write_scene(fingerprint, scene);
        }
        SemanticEvent::SessionAuthenticated { revision, player } => {
            fingerprint.write_u8(4);
            write_revision(fingerprint, revision);
            write_player(fingerprint, player);
        }
        SemanticEvent::InitialPlacementEstablished(placement) => {
            fingerprint.write_u8(5);
            write_placement(fingerprint, placement);
        }
        SemanticEvent::TitleScreenChanged { previous, current } => {
            fingerprint.write_u8(3);
            write_title(fingerprint, previous);
            write_title(fingerprint, current);
        }
        SemanticEvent::SessionBegan { revision } => {
            fingerprint.write_u8(0);
            write_revision(fingerprint, revision);
        }
        SemanticEvent::SessionEnded => fingerprint.write_u8(1),
        SemanticEvent::GameTickAdvanced { previous, current } => {
            fingerprint.write_u8(2);
            fingerprint.write_u64(previous.get());
            fingerprint.write_u64(current.get());
        }
        SemanticEvent::VariablesSynchronized => fingerprint.write_u8(16),
        SemanticEvent::ServerVariableChanged {
            id,
            previous,
            current,
        } => {
            fingerprint.write_u8(17);
            fingerprint.write_u32(id.get());
            fingerprint.write_u32(previous.cast_unsigned());
            fingerprint.write_u32(current.cast_unsigned());
        }
        SemanticEvent::ChatSettingsChanged => fingerprint.write_u8(19),
        SemanticEvent::ChatMessageAdded(id) => {
            fingerprint.write_u8(18);
            fingerprint.write_u32(id);
        }
        SemanticEvent::InterfacesChanged => fingerprint.write_u8(15),
        SemanticEvent::InventoryChanged(id) => {
            fingerprint.write_u8(14);
            fingerprint.write_u32(id.get());
        }
        SemanticEvent::EntitiesUpdated => fingerprint.write_u8(11),
        SemanticEvent::GameFrameTabSelected(tab) => {
            fingerprint.write_u8(12);
            match tab {
                None => fingerprint.write_u8(0),
                Some(tab) => {
                    fingerprint.write_u8(1);
                    fingerprint.write_u8(u8::try_from(tab).unwrap_or(255));
                }
            }
        }
    }
}

fn write_location_key(f: &mut StableFingerprint64, key: rustclient_model::LocationKey) {
    f.write_bytes(&key.tile.x.to_le_bytes());
    f.write_bytes(&key.tile.y.to_le_bytes());
    f.write_u8(key.tile.plane.get());
    f.write_u8(match key.layer {
        rustclient_model::LocationLayer::Boundary => 0,
        rustclient_model::LocationLayer::WallDecoration => 1,
        rustclient_model::LocationLayer::GameObject => 2,
        rustclient_model::LocationLayer::FloorDecoration => 3,
    });
}
fn write_location_override(
    f: &mut StableFingerprint64,
    previous: crate::LocationOverride,
    current: crate::LocationOverride,
) {
    write_one_location_override(f, previous);
    write_one_location_override(f, current);
}
fn write_one_location_override(f: &mut StableFingerprint64, value: crate::LocationOverride) {
    use rustclient_model::LocationChange;
    match value.change {
        LocationChange::Inherit => f.write_u8(0),
        LocationChange::Remove => f.write_u8(1),
        LocationChange::Replace(def) => {
            f.write_u8(2);
            f.write_bytes(&def.object.get().to_le_bytes());
            f.write_u8(def.shape);
            f.write_u8(def.orientation.get());
        }
    }
    match value.animation {
        None => f.write_u8(0),
        Some(start) => {
            f.write_u8(1);
            match start.sequence {
                None => f.write_u8(0),
                Some(sequence) => {
                    f.write_u8(1);
                    f.write_bytes(&sequence.get().to_le_bytes());
                }
            }
            f.write_u64(start.cycle.get());
            f.write_u64(start.random_seed);
            match start.placement {
                None => f.write_u8(0),
                Some(placement) => {
                    f.write_u8(1);
                    f.write_u8(placement.shape);
                    f.write_u8(placement.orientation.get());
                }
            }
        }
    }
}

fn write_title(fingerprint: &mut StableFingerprint64, screen: TitleScreen) {
    match screen {
        TitleScreen::Welcome => fingerprint.write_u8(0),
        TitleScreen::Credentials(status) => {
            fingerprint.write_u8(1);
            fingerprint.write_u8(match status {
                LoginStatus::Prompt => 0,
                LoginStatus::UsernameRequired => 1,
                LoginStatus::PasswordRequired => 2,
                LoginStatus::Offline => 3,
                LoginStatus::Connecting => 4,
                LoginStatus::Failed(_) => 5,
            });
            if let LoginStatus::Failed(failure) = status {
                fingerprint.write_u8(match failure {
                    LoginFailure::Connection => 0,
                    LoginFailure::Authentication => 1,
                    LoginFailure::CacheMismatch => 2,
                    LoginFailure::Protocol => 3,
                    LoginFailure::Disconnected => 4,
                });
            }
        }
        TitleScreen::Information(info) => {
            fingerprint.write_u8(2);
            fingerprint.write_u8(match info {
                TitleInformation::NewUser => 0,
                TitleInformation::Help => 1,
            });
        }
    }
}

fn write_scene(f: &mut StableFingerprint64, scene: SceneDescriptor) {
    f.write_bytes(&scene.id().bytes());
    f.write_bytes(&scene.origin().x.to_le_bytes());
    f.write_bytes(&scene.origin().y.to_le_bytes());
    f.write_u8(scene.origin().plane.get());
    f.write_bytes(&scene.width().to_le_bytes());
    f.write_bytes(&scene.height().to_le_bytes());
}
fn write_player(f: &mut StableFingerprint64, player: AuthenticatedPlayer) {
    f.write_bytes(&player.profile.get().to_le_bytes());
    f.write_bytes(&player.player.get().to_le_bytes());
    f.write_u8(u8::from(player.member));
    f.write_u8(player.staff_level);
}
fn write_placement(f: &mut StableFingerprint64, placement: InitialPlacement) {
    for tile in [placement.position, placement.map_anchor] {
        f.write_bytes(&tile.x.to_le_bytes());
        f.write_bytes(&tile.y.to_le_bytes());
        f.write_u8(tile.plane.get());
    }
    match placement.area {
        None => f.write_u8(0),
        Some(area) => {
            f.write_u8(1);
            f.write_bytes(&area.get().to_le_bytes());
        }
    }
}

mod interfaces;
#[cfg(test)]
mod tests;

fn write_destination(
    f: &mut StableFingerprint64,
    destination: Option<rustclient_model::MapDestination>,
) {
    match destination {
        None => f.write_u8(0),
        Some(value) => {
            f.write_u8(1);
            f.write_bytes(&value.x.to_le_bytes());
            f.write_bytes(&value.y.to_le_bytes());
        }
    }
}

fn write_overhead(f: &mut StableFingerprint64, value: Option<&crate::ActorOverhead>) {
    if let Some(value) = value {
        f.write_u8(1);
        f.write_u32(u32::try_from(value.text.text.len()).expect("bounded speech"));
        f.write_bytes(value.text.text.as_bytes());
        f.write_u8(value.text.colour); f.write_u8(value.text.effect);
        f.write_u8(u8::try_from(value.text.pattern.len()).expect("bounded pattern"));
        f.write_bytes(&value.text.pattern);
        f.write_u32(value.remaining);
        f.write_u8(u8::from(value.autotyper)); f.write_u8(u8::from(value.filtered));
    } else { f.write_u8(0); }
}
