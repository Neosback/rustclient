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
pub const SEMANTIC_FINGERPRINT_VERSION: u16 = 6;

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
            write_location_override(&mut fingerprint, *value);
        }

        match self.selected_tab() {
            None => fingerprint.write_u8(0),
            Some(tab) => {
                fingerprint.write_u8(1);
                fingerprint.write_u8(u8::try_from(tab).unwrap_or(255));
            }
        }

        let entities = self.entities();
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
            fingerprint.write_u32(idx.get());
            fingerprint.write_bytes(&player.tile.x.to_le_bytes());
            fingerprint.write_bytes(&player.tile.y.to_le_bytes());
            fingerprint.write_u8(player.tile.plane.get());
            fingerprint.write_u16(player.orientation);
            fingerprint.write_u8(u8::from(player.appearance.is_some()));
            match player.sequence {
                None => fingerprint.write_u8(0),
                Some(seq) => {
                    fingerprint.write_u8(1);
                    fingerprint.write_u32(seq.get());
                }
            }
        }
        fingerprint.write_bytes(
            &u32::try_from(entities.npcs().len())
                .expect("bounded npcs")
                .to_le_bytes(),
        );
        for (idx, npc) in entities.npcs() {
            fingerprint.write_u32(idx.get());
            fingerprint.write_u32(npc.definition_id.get());
            fingerprint.write_bytes(&npc.tile.x.to_le_bytes());
            fingerprint.write_bytes(&npc.tile.y.to_le_bytes());
            fingerprint.write_u8(npc.tile.plane.get());
            fingerprint.write_u16(npc.orientation);
            match npc.sequence {
                None => fingerprint.write_u8(0),
                Some(seq) => {
                    fingerprint.write_u8(1);
                    fingerprint.write_u32(seq.get());
                }
            }
        }
        StateFingerprint(fingerprint.finish())
    }
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
}

fn write_event(fingerprint: &mut StableFingerprint64, event: SemanticEvent) {
    match event {
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
            write_location_override(fingerprint, previous);
            write_location_override(fingerprint, current);
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
fn write_location_override(f: &mut StableFingerprint64, value: crate::LocationOverride) {
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

#[cfg(test)]
mod tests {
    use rustclient_model::{GameTick, REVISION_240, Revision, TickSpan};

    use super::*;
    use crate::{ReplayEnvelope, SemanticOperation};

    const BEGIN_240: SemanticOperation = SemanticOperation::BeginSession {
        revision: REVISION_240,
    };

    fn run_operations(operations: &[SemanticOperation]) -> (GameState, CommitStreamFingerprint) {
        let mut state = GameState::new();
        let mut commits = CommitStreamFingerprintBuilder::new();

        state
            .apply_operation_batch_with_commits(operations, |_, commit| commits.push(commit))
            .expect("reference semantic operation sequence must be valid");

        (state, commits.finish())
    }

    #[test]
    fn state_fingerprint_v6_has_exact_regression_vectors() {
        let mut state = GameState::new();
        assert_eq!(
            state.semantic_fingerprint().get(),
            8_648_089_271_838_886_857
        );

        state
            .apply_operation(BEGIN_240)
            .expect("inactive state can begin a session");
        state
            .apply_operation(SemanticOperation::AdvanceTick(TickSpan::new(5)))
            .expect("small tick advance cannot overflow");

        assert_eq!(
            state.semantic_fingerprint().get(),
            1_662_451_992_595_177_179
        );
    }

    #[test]
    fn title_panels_and_feedback_participate_in_canonical_state_and_commits() {
        let screens = [
            TitleScreen::Welcome,
            TitleScreen::Credentials(LoginStatus::Prompt),
            TitleScreen::Credentials(LoginStatus::UsernameRequired),
            TitleScreen::Credentials(LoginStatus::PasswordRequired),
            TitleScreen::Credentials(LoginStatus::Offline),
            TitleScreen::Information(TitleInformation::NewUser),
            TitleScreen::Information(TitleInformation::Help),
        ];
        let mut states = std::collections::BTreeSet::new();
        let mut streams = std::collections::BTreeSet::new();
        for screen in screens {
            let mut state = GameState::new();
            let commit = state
                .apply_operation(SemanticOperation::SetTitleScreen(screen))
                .unwrap();
            let mut stream = CommitStreamFingerprintBuilder::new();
            stream.push(commit);
            assert!(states.insert(state.semantic_fingerprint()));
            assert!(streams.insert(stream.finish()));
        }
    }

    #[test]
    fn state_fingerprint_distinguishes_revision_identity() {
        let mut major_only = GameState::new();
        major_only
            .apply_operation(BEGIN_240)
            .expect("inactive state can begin a session");
        major_only
            .apply_operation(SemanticOperation::AdvanceTick(TickSpan::new(5)))
            .expect("small tick advance cannot overflow");

        let mut subrevision = GameState::new();
        subrevision
            .apply_operation(SemanticOperation::BeginSession {
                revision: Revision::with_subrevision(240, 2),
            })
            .expect("inactive state can begin a session");
        subrevision
            .apply_operation(SemanticOperation::AdvanceTick(TickSpan::new(5)))
            .expect("small tick advance cannot overflow");

        assert_ne!(
            major_only.semantic_fingerprint(),
            subrevision.semantic_fingerprint()
        );
        assert_eq!(
            subrevision.semantic_fingerprint().get(),
            13_610_782_365_595_794_734
        );
    }

    #[test]
    fn equivalent_observable_state_has_equivalent_fingerprint() {
        let initial = GameState::new().semantic_fingerprint();
        let mut state = GameState::new();

        state
            .apply_operation(BEGIN_240)
            .expect("inactive state can begin a session");
        state
            .apply_operation(SemanticOperation::AdvanceTick(TickSpan::new(9)))
            .expect("small tick advance cannot overflow");
        state
            .apply_operation(SemanticOperation::EndSession)
            .expect("active state can end a session");

        assert_eq!(state.semantic_fingerprint(), initial);
    }

    #[test]
    fn commit_stream_fingerprint_v6_has_exact_regression_vector() {
        let operations = [
            BEGIN_240,
            SemanticOperation::AdvanceTick(TickSpan::new(2)),
            SemanticOperation::AdvanceTick(TickSpan::ZERO),
            SemanticOperation::EndSession,
        ];
        let (_, fingerprint) = run_operations(&operations);

        assert_eq!(fingerprint.get(), 3_758_896_452_731_557_813);
        assert_eq!(
            CommitStreamFingerprintBuilder::new().finish().get(),
            3_354_334_176_184_128_508
        );
    }

    #[test]
    fn commit_stream_fingerprint_is_order_sensitive() {
        let mut state = GameState::new();
        let begin = state
            .apply_operation(BEGIN_240)
            .expect("inactive state can begin a session");
        let advance = state
            .apply_operation(SemanticOperation::AdvanceTick(TickSpan::ONE))
            .expect("small tick advance cannot overflow");

        let mut forward = CommitStreamFingerprintBuilder::new();
        forward.push(begin);
        forward.push(advance);

        let mut reversed = CommitStreamFingerprintBuilder::new();
        reversed.push(advance);
        reversed.push(begin);

        assert_ne!(forward.finish(), reversed.finish());
    }

    #[test]
    fn identical_semantic_runs_have_identical_canonical_fingerprints() {
        let operations = [
            BEGIN_240,
            SemanticOperation::AdvanceTick(TickSpan::new(2)),
            SemanticOperation::AdvanceTick(TickSpan::ZERO),
            SemanticOperation::AdvanceTick(TickSpan::new(3)),
            SemanticOperation::EndSession,
            BEGIN_240,
            SemanticOperation::AdvanceTick(TickSpan::ONE),
        ];

        let (left_state, left_commits) = run_operations(&operations);
        let (right_state, right_commits) = run_operations(&operations);

        assert_eq!(
            left_state.semantic_fingerprint(),
            right_state.semantic_fingerprint()
        );
        assert_eq!(left_commits, right_commits);
        assert_eq!(left_state.current_tick(), Some(GameTick::new(1)));
    }

    #[test]
    fn replay_reconstructs_state_and_commit_stream_fingerprints() {
        let operations = [
            BEGIN_240,
            SemanticOperation::AdvanceTick(TickSpan::new(2)),
            SemanticOperation::AdvanceTick(TickSpan::ZERO),
            SemanticOperation::AdvanceTick(TickSpan::new(3)),
        ];
        let mut recorded_state = GameState::new();
        let mut recorded_stream = CommitStreamFingerprintBuilder::new();
        let mut envelopes = Vec::with_capacity(operations.len());

        for operation in operations {
            let commit = recorded_state
                .apply_operation(operation.clone())
                .expect("reference operation sequence must be valid");
            recorded_stream.push(commit);
            envelopes.push(ReplayEnvelope::new(operation, commit));
        }

        let mut replayed_state = GameState::new();
        let mut replayed_stream = CommitStreamFingerprintBuilder::new();
        replayed_state
            .replay_envelopes_with_commits(&envelopes, |_, commit| {
                replayed_stream.push(commit);
            })
            .expect("recorded semantic sequence must replay exactly");

        assert_eq!(
            recorded_state.semantic_fingerprint(),
            replayed_state.semantic_fingerprint()
        );
        assert_eq!(recorded_stream.finish(), replayed_stream.finish());
    }
}
