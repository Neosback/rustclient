//! Why the server refused a login, from its one-byte response code.
//!
//! The codes and their meanings are the stock client's login response table; texts for them live
//! in presentation. Codes without a dedicated variant (and the server-supplied message of code 29)
//! are [`LoginRejection::Other`], with the detail kept in diagnostics.

/// A login refusal the player can act on.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash)]
pub enum LoginRejection {
    InvalidCredentials,
    AccountDisabled,
    AlreadyLoggedIn,
    ClientOutOfDate,
    WorldFull,
    ServerOffline,
    LoginLimit,
    BadSession,
    PasswordCompromised,
    MembersWorld,
    CouldNotComplete,
    ServerUpdating,
    TooManyAttempts,
    MembersArea,
    AccountLocked,
    ClosedBeta,
    InvalidLoginServer,
    ProfileTransfer,
    MalformedPacket,
    NoReply,
    ProfileError,
    UnexpectedResponse,
    AddressBlocked,
    ServiceUnavailable,
    TwoFactorRequired,
    Other,
}
impl LoginRejection {
    /// The refusal a response code stands for. Every non-success code maps to something.
    #[must_use]
    pub const fn from_code(code: u8) -> Self {
        match code {
            3 => Self::InvalidCredentials,
            4 => Self::AccountDisabled,
            5 => Self::AlreadyLoggedIn,
            6 => Self::ClientOutOfDate,
            7 => Self::WorldFull,
            8 => Self::ServerOffline,
            9 => Self::LoginLimit,
            10 => Self::BadSession,
            11 => Self::PasswordCompromised,
            12 => Self::MembersWorld,
            13 => Self::CouldNotComplete,
            14 => Self::ServerUpdating,
            16 => Self::TooManyAttempts,
            17 => Self::MembersArea,
            18 => Self::AccountLocked,
            19 => Self::ClosedBeta,
            20 => Self::InvalidLoginServer,
            21 => Self::ProfileTransfer,
            22 => Self::MalformedPacket,
            23 => Self::NoReply,
            24 => Self::ProfileError,
            25 => Self::UnexpectedResponse,
            26 => Self::AddressBlocked,
            27 => Self::ServiceUnavailable,
            56 | 57 => Self::TwoFactorRequired,
            _ => Self::Other,
        }
    }
    /// A stable number for fingerprints and replay, independent of declaration order.
    #[must_use]
    pub const fn stable_id(self) -> u8 {
        match self {
            Self::InvalidCredentials => 3,
            Self::AccountDisabled => 4,
            Self::AlreadyLoggedIn => 5,
            Self::ClientOutOfDate => 6,
            Self::WorldFull => 7,
            Self::ServerOffline => 8,
            Self::LoginLimit => 9,
            Self::BadSession => 10,
            Self::PasswordCompromised => 11,
            Self::MembersWorld => 12,
            Self::CouldNotComplete => 13,
            Self::ServerUpdating => 14,
            Self::TooManyAttempts => 16,
            Self::MembersArea => 17,
            Self::AccountLocked => 18,
            Self::ClosedBeta => 19,
            Self::InvalidLoginServer => 20,
            Self::ProfileTransfer => 21,
            Self::MalformedPacket => 22,
            Self::NoReply => 23,
            Self::ProfileError => 24,
            Self::UnexpectedResponse => 25,
            Self::AddressBlocked => 26,
            Self::ServiceUnavailable => 27,
            Self::TwoFactorRequired => 56,
            Self::Other => 0,
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn every_code_round_trips_through_its_stable_id() {
        for code in 0..=u8::MAX {
            let rejection = LoginRejection::from_code(code);
            if rejection != LoginRejection::Other {
                assert_eq!(LoginRejection::from_code(rejection.stable_id()), rejection);
            }
        }
        assert_eq!(LoginRejection::from_code(7), LoginRejection::WorldFull);
        assert_eq!(
            LoginRejection::from_code(57),
            LoginRejection::TwoFactorRequired
        );
        assert_eq!(LoginRejection::from_code(29), LoginRejection::Other);
    }
}
