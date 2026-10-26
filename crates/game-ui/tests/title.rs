use rustclient_game_state::{GameState, LoginStatus, SemanticOperation, TitleScreen};
use rustclient_game_ui::{TitlePreferences, TitleUi, TitleViewport};
use rustclient_input::EditKey;
fn apply(state: &mut GameState, operation: Option<SemanticOperation>) {
    if let Some(operation) = operation {
        state.apply_operation(operation).unwrap();
    }
}

#[test]
fn editing_validation_offline_submission_cancel_and_preferences_are_local() {
    let mut state = GameState::new();
    let mut ui = TitleUi::new(TitlePreferences::default());
    let operation = ui.key(state.title_screen(), EditKey::Enter);
    apply(&mut state, operation);
    let operation = ui.key(state.title_screen(), EditKey::Enter);
    apply(&mut state, operation);
    assert_eq!(
        state.title_screen(),
        TitleScreen::Credentials(LoginStatus::UsernameRequired)
    );
    ui.text(state.title_screen(), "Preview<> 🦀\n");
    let operation = ui.key(state.title_screen(), EditKey::Enter);
    apply(&mut state, operation);
    assert_eq!(
        state.title_screen(),
        TitleScreen::Credentials(LoginStatus::PasswordRequired)
    );
    ui.text(state.title_screen(), "password");
    let operation = ui.key(state.title_screen(), EditKey::Enter);
    apply(&mut state, operation);
    assert_eq!(
        state.title_screen(),
        TitleScreen::Credentials(LoginStatus::Offline)
    );
    assert_eq!(state.session_revision(), None);
    assert!(!ui.preferences().remember_username);
    assert!(ui.preferences().username.is_empty());
    let operation = ui.click(state.title_screen(), 270., 280.);
    apply(&mut state, operation);
    assert_eq!(ui.preferences().username, "Preview<> ");
    let operation = ui.click(state.title_screen(), 410., 280.);
    apply(&mut state, operation);
    assert!(ui.preferences().hide_username);
    let operation = ui.key(state.title_screen(), EditKey::Escape);
    apply(&mut state, operation);
    let operation = ui.key(state.title_screen(), EditKey::Enter);
    apply(&mut state, operation);
    let operation = ui.key(state.title_screen(), EditKey::Enter);
    apply(&mut state, operation);
    assert_eq!(
        state.title_screen(),
        TitleScreen::Credentials(LoginStatus::PasswordRequired)
    );
}
#[test]
fn username_limit_counts_characters_and_backspace_respects_utf8() {
    let screen = TitleScreen::Credentials(LoginStatus::Prompt);
    let mut ui = TitleUi::new(TitlePreferences::default());
    ui.text(screen, &"€".repeat(400));
    let _ = ui.click(screen, 270., 280.);
    assert_eq!(ui.preferences().username.chars().count(), 320);
    let _ = ui.key(screen, EditKey::Backspace);
    assert_eq!(ui.preferences().username.chars().count(), 319);
    ui.text(TitleScreen::Welcome, "ignored");
    assert_eq!(ui.preferences().username.chars().count(), 319);
}
#[test]
fn same_title_hit_geometry_is_used_after_resize_and_hidpi_transform() {
    for viewport in [
        TitleViewport {
            canvas_width: 765,
            physical_width: 765,
            physical_height: 503,
            scale: 1.,
            stretch: None,
        },
        TitleViewport {
            canvas_width: 765,
            physical_width: 1800,
            physical_height: 1100,
            scale: 2.,
            stretch: None,
        },
    ] {
        let physical_x = (f64::from(viewport.origin_x()) + 400.) * viewport.scale;
        let (x, y) = viewport.pointer(physical_x, 290. * viewport.scale);
        let mut ui = TitleUi::new(TitlePreferences::default());
        assert_eq!(
            ui.click(TitleScreen::Welcome, x, y),
            Some(SemanticOperation::SetTitleScreen(TitleScreen::Credentials(
                LoginStatus::Prompt
            )))
        );
    }
}
