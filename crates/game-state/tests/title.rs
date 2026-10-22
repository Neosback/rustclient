use rustclient_game_state::{
    GameState, LoginStatus, ReplayEnvelope, SemanticOperation, SessionLifecycle, TitleInformation,
    TitleScreen,
};
use rustclient_model::REVISION_240;

#[test]
fn nonsecret_title_flow_replays_without_beginning_a_session() {
    let operations = [
        TitleScreen::Credentials(LoginStatus::Prompt),
        TitleScreen::Credentials(LoginStatus::UsernameRequired),
        TitleScreen::Credentials(LoginStatus::PasswordRequired),
        TitleScreen::Credentials(LoginStatus::Offline),
        TitleScreen::Information(TitleInformation::Help),
        TitleScreen::Welcome,
    ];
    let mut state = GameState::new();
    let mut envelopes = Vec::new();
    for screen in operations {
        let operation = SemanticOperation::SetTitleScreen(screen);
        let commit = state.apply_operation(operation.clone()).unwrap();
        assert!(commit.delta().title_changed());
        assert!(!commit.delta().lifecycle_changed());
        envelopes.push(ReplayEnvelope::new(operation, commit));
        assert_eq!(state.lifecycle(), SessionLifecycle::Inactive);
    }
    let mut replay = GameState::new();
    replay.replay_envelopes(&envelopes).unwrap();
    assert_eq!(replay.semantic_fingerprint(), state.semantic_fingerprint());
}

#[test]
fn title_reset_and_active_session_rejection_preserve_single_authority() {
    let mut state = GameState::new();
    state
        .apply_operation(SemanticOperation::SetTitleScreen(TitleScreen::Credentials(
            LoginStatus::Offline,
        )))
        .unwrap();
    let before = state.semantic_fingerprint();
    let commit = state
        .apply_operation(SemanticOperation::BeginSession {
            revision: REVISION_240,
        })
        .unwrap();
    assert!(commit.delta().title_changed());
    assert_eq!(state.title_screen(), TitleScreen::Welcome);
    assert_ne!(before, state.semantic_fingerprint());
    let active = state.semantic_fingerprint();
    assert!(
        state
            .apply_operation(SemanticOperation::SetTitleScreen(TitleScreen::Credentials(
                LoginStatus::Prompt
            )))
            .is_err()
    );
    assert_eq!(active, state.semantic_fingerprint());
    state
        .apply_operation(SemanticOperation::EndSession)
        .unwrap();
    assert_eq!(
        state.semantic_fingerprint(),
        GameState::new().semantic_fingerprint()
    );
    assert!(
        state
            .apply_operation(SemanticOperation::SetTitleScreen(TitleScreen::Welcome))
            .unwrap()
            .delta()
            .is_empty()
    );
}
