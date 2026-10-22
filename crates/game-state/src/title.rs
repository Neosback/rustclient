//! Non-secret pre-session title navigation. Credentials never enter this module.
use core::fmt;

/// Credential form feedback, independent of authentication/transport.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Default)]
pub enum LoginStatus {
    /// Initial instruction.
    #[default]
    Prompt,
    /// Username was empty.
    UsernameRequired,
    /// Password was empty.
    PasswordRequired,
    /// An opt-in server login is in flight.
    Connecting,
    /// A bounded non-secret failure category.
    Failed(LoginFailure),
    /// This milestone has no authentication backend.
    Offline,
}
/// Local information shown without invoking external account services.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum TitleInformation {
    /// Account creation is a future server capability.
    NewUser,
    /// Explain the offline preview.
    Help,
}
/// Authoritative pre-session panel and non-secret feedback.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Default)]
pub enum TitleScreen {
    /// Initial welcome panel.
    #[default]
    Welcome,
    /// Credential form; drafts are presentation-owned.
    Credentials(LoginStatus),
    /// Local information panel with a Back action.
    Information(TitleInformation),
}
/// Rejection of title navigation during an active semantic session.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct TitleTransitionError;
impl fmt::Display for TitleTransitionError {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        f.write_str("title navigation requires an inactive game session")
    }
}
impl std::error::Error for TitleTransitionError {}

/// Non-secret connection feedback suitable for replay and local presentation.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum LoginFailure {
    Connection,
    Authentication,
    CacheMismatch,
    Protocol,
    Disconnected,
}
