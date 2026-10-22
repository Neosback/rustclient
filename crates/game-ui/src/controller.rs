use crate::Rect;
use rustclient_cache::cp1252;
use rustclient_game_state::{LoginStatus, SemanticOperation, TitleInformation, TitleScreen};
use rustclient_input::EditKey;
use rustclient_protocol::Credentials;
use zeroize::{Zeroize, Zeroizing};

/// Non-secret local display preferences. Persistence is application-owned.
#[derive(Debug, Clone, Default, PartialEq, Eq)]
pub struct TitlePreferences {
    /// Explicit opt-in to retain the username.
    pub remember_username: bool,
    /// Mask username on screen.
    pub hide_username: bool,
    /// Optional retained username; empty when remembering is disabled.
    pub username: String,
}

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
enum Focus {
    Username,
    Password,
}

/// Transient input controller. Intentionally has no Debug/Clone implementation.
/// Passwords leave only through the explicit single-use connection handoff.
pub struct TitleUi {
    username: String,
    password: Zeroizing<String>,
    online: bool,
    submission: bool,
    focus: Focus,
    preferences: TitlePreferences,
    hover: Option<(f32, f32)>,
}
impl TitleUi {
    /// Begin editing from non-secret persisted preferences.
    #[must_use]
    pub fn new(mut preferences: TitlePreferences) -> Self {
        preferences.username = preferences
            .username
            .chars()
            .filter(|&ch| printable(ch))
            .take(320)
            .collect();
        if !preferences.remember_username {
            preferences.username.clear();
        }
        let username = preferences.username.clone();
        Self {
            username,
            password: Zeroizing::new(String::new()),
            online: false,
            submission: false,
            focus: Focus::Username,
            preferences,
            hover: None,
        }
    }
    /// Enable the server-login control when the composition root has a profile.
    pub fn set_online(&mut self, available: bool) {
        self.online = available;
    }
    pub(crate) const fn online(&self) -> bool {
        self.online
    }
    /// Move a submitted password exactly once to the connection owner.
    /// No credential data enters the returned semantic operation.
    pub fn take_submission(&mut self) -> Option<Credentials> {
        if !std::mem::take(&mut self.submission) {
            return None;
        }
        Credentials::new(
            self.username.trim().to_owned(),
            std::mem::take(&mut *self.password),
        )
        .ok()
    }
    /// Erase any remaining transient password after connection completion.
    pub fn clear_password(&mut self) {
        self.password.zeroize();
        self.focus = Focus::Username;
        self.submission = false;
    }

    /// Preferences to persist; includes a username only after explicit opt-in.
    #[must_use]
    pub fn preferences(&self) -> TitlePreferences {
        let mut preferences = self.preferences.clone();
        preferences.username = if preferences.remember_username {
            self.username.clone()
        } else {
            String::new()
        };
        preferences
    }
    /// Update hover using title-local logical coordinates.
    pub fn pointer(&mut self, x: f32, y: f32) {
        self.hover = Some((x, y));
    }
    /// Clear hover when the pointer leaves the platform surface.
    pub fn clear_hover(&mut self) {
        self.hover = None;
    }
    /// Insert literal printable CP1252 text into the focused field.
    pub fn text(&mut self, screen: TitleScreen, text: &str) {
        if !matches!(screen, TitleScreen::Credentials(status) if status != LoginStatus::Connecting)
        {
            return;
        }
        let (field, limit) = match self.focus {
            Focus::Username => (&mut self.username, 320),
            Focus::Password => (&mut *self.password, 20),
        };
        let remaining = limit - field.chars().count();
        field.extend(text.chars().filter(|&ch| printable(ch)).take(remaining));
    }
    /// Handle a normalized editing key, returning only non-secret semantic intent.
    #[must_use]
    pub fn key(&mut self, screen: TitleScreen, key: EditKey) -> Option<SemanticOperation> {
        if key == EditKey::Escape {
            return Some(self.cancel());
        }
        if screen == TitleScreen::Credentials(LoginStatus::Connecting) {
            return None;
        }
        match (screen, key) {
            (TitleScreen::Welcome, EditKey::Enter) => {
                Some(show(TitleScreen::Credentials(LoginStatus::Prompt)))
            }
            (TitleScreen::Information(_), EditKey::Enter) => Some(self.cancel()),
            (TitleScreen::Credentials(_), EditKey::Backspace) => {
                match self.focus {
                    Focus::Username => {
                        self.username.pop();
                    }
                    Focus::Password => {
                        self.password.pop();
                    }
                }
                None
            }
            (TitleScreen::Credentials(_), EditKey::Tab { .. }) => {
                self.focus = match self.focus {
                    Focus::Username => Focus::Password,
                    Focus::Password => Focus::Username,
                };
                None
            }
            (TitleScreen::Credentials(_), EditKey::Enter) => Some(self.submit()),
            _ => None,
        }
    }
    /// Activate a local title control. Drawing and hit testing share these rectangles.
    #[must_use]
    pub fn click(&mut self, screen: TitleScreen, x: f32, y: f32) -> Option<SemanticOperation> {
        self.pointer(x, y);
        if screen == TitleScreen::Credentials(LoginStatus::Connecting) {
            return CANCEL.contains(x, y).then(|| self.cancel());
        }
        match screen {
            TitleScreen::Welcome => {
                if LEFT_WELCOME.contains(x, y) {
                    Some(show(TitleScreen::Information(TitleInformation::NewUser)))
                } else if RIGHT_WELCOME.contains(x, y) {
                    self.focus = Focus::Username;
                    Some(show(TitleScreen::Credentials(LoginStatus::Prompt)))
                } else {
                    None
                }
            }
            TitleScreen::Credentials(_) => {
                if USERNAME.contains(x, y) {
                    self.focus = Focus::Username;
                } else if PASSWORD.contains(x, y) {
                    self.focus = Focus::Password;
                } else if REMEMBER.contains(x, y) {
                    self.preferences.remember_username = !self.preferences.remember_username;
                } else if HIDE.contains(x, y) {
                    self.preferences.hide_username = !self.preferences.hide_username;
                } else if LOGIN.contains(x, y) {
                    return Some(self.submit());
                } else if CANCEL.contains(x, y) {
                    return Some(self.cancel());
                } else if HELP.contains(x, y) {
                    self.clear_password();
                    return Some(show(TitleScreen::Information(TitleInformation::Help)));
                }
                None
            }
            TitleScreen::Information(_) => {
                if BACK.contains(x, y) {
                    Some(self.cancel())
                } else {
                    None
                }
            }
        }
    }
    fn submit(&mut self) -> SemanticOperation {
        let status = if self.username.trim().is_empty() {
            self.focus = Focus::Username;
            LoginStatus::UsernameRequired
        } else if self.password.is_empty() {
            self.focus = Focus::Password;
            LoginStatus::PasswordRequired
        } else if self.online {
            self.submission = true;
            LoginStatus::Connecting
        } else {
            LoginStatus::Offline
        };
        show(TitleScreen::Credentials(status))
    }
    fn cancel(&mut self) -> SemanticOperation {
        self.clear_password();
        self.focus = Focus::Username;
        show(TitleScreen::Welcome)
    }
    pub(crate) fn focused_username(&self) -> bool {
        self.focus == Focus::Username
    }
    pub(crate) fn displayed_username(&self) -> String {
        if self.preferences.hide_username {
            "*".repeat(self.username.chars().count())
        } else {
            self.username.clone()
        }
    }
    pub(crate) fn masked_password(&self) -> String {
        "*".repeat(self.password.chars().count())
    }
    pub(crate) fn hovered(&self, rect: Rect) -> bool {
        self.hover.is_some_and(|(x, y)| rect.contains(x, y))
    }
}
fn printable(ch: char) -> bool {
    !ch.is_control() && cp1252(ch).is_some()
}
fn show(screen: TitleScreen) -> SemanticOperation {
    SemanticOperation::SetTitleScreen(screen)
}

pub(crate) const LEFT_WELCOME: Rect = Rect::new(229., 271., 147., 41.);
pub(crate) const RIGHT_WELCOME: Rect = Rect::new(389., 271., 147., 41.);
pub(crate) const USERNAME: Rect = Rect::new(312., 241., 200., 15.);
pub(crate) const PASSWORD: Rect = Rect::new(312., 256., 200., 15.);
pub(crate) const REMEMBER: Rect = Rect::new(265., 277., 141., 17.);
pub(crate) const HIDE: Rect = Rect::new(406., 277., 130., 17.);
pub(crate) const LOGIN: Rect = Rect::new(229., 301., 147., 41.);
pub(crate) const CANCEL: Rect = Rect::new(389., 301., 147., 41.);
pub(crate) const HELP: Rect = Rect::new(300., 346., 165., 15.);
pub(crate) const BACK: Rect = Rect::new(309., 301., 147., 41.);

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn password_limit_masking_and_reverse_tab_do_not_export_credentials() {
        let screen = TitleScreen::Credentials(LoginStatus::Prompt);
        let mut ui = TitleUi::new(TitlePreferences::default());
        let _ = ui.key(screen, EditKey::Tab { reverse: true });
        ui.text(screen, &"secret".repeat(10));
        assert_eq!(ui.password.chars().count(), 20);
        assert_eq!(ui.masked_password(), "*".repeat(20));
        let _ = ui.key(screen, EditKey::Backspace);
        assert_eq!(ui.masked_password().len(), 19);
        assert!(!format!("{:?}", ui.preferences()).contains("secret"));
        let _ = ui.key(screen, EditKey::Escape);
        assert!(ui.password.is_empty());
    }
}

#[cfg(test)]
mod online_tests {
    use super::*;
    #[test]
    fn online_submission_is_single_use_and_connecting_accepts_only_cancel() {
        let screen = TitleScreen::Credentials(LoginStatus::Prompt);
        let mut ui = TitleUi::new(TitlePreferences::default());
        ui.set_online(true);
        ui.text(screen, "fixture");
        let _ = ui.key(screen, EditKey::Tab { reverse: false });
        ui.text(screen, "example");
        assert_eq!(
            ui.key(screen, EditKey::Enter),
            Some(show(TitleScreen::Credentials(LoginStatus::Connecting)))
        );
        assert!(ui.take_submission().is_some());
        assert!(ui.take_submission().is_none());
        assert!(ui.password.is_empty());
        let connecting = TitleScreen::Credentials(LoginStatus::Connecting);
        ui.text(connecting, "should-not-enter");
        assert!(ui.key(connecting, EditKey::Enter).is_none());
        assert!(ui.password.is_empty());
        assert_eq!(
            ui.key(connecting, EditKey::Escape),
            Some(show(TitleScreen::Welcome))
        );
        assert!(ui.take_submission().is_none());
    }
    #[test]
    fn cancel_before_handoff_erases_pending_credentials() {
        let screen = TitleScreen::Credentials(LoginStatus::Prompt);
        let mut ui = TitleUi::new(TitlePreferences::default());
        ui.set_online(true);
        ui.text(screen, "fixture");
        let _ = ui.key(screen, EditKey::Tab { reverse: false });
        ui.text(screen, "example");
        let _ = ui.key(screen, EditKey::Enter);
        let _ = ui.key(
            TitleScreen::Credentials(LoginStatus::Connecting),
            EditKey::Escape,
        );
        assert!(ui.take_submission().is_none());
        assert!(ui.masked_password().is_empty());
    }
}
