//! Client/cache/protocol revision identity.
//!
//! A revision is a semantic compatibility label, not a server profile. Multiple
//! private servers may share one revision while using different profile
//! extensions, cache sources, or transport configuration.

use core::{fmt, str::FromStr};

/// Initial RustClient compatibility target.
pub const REVISION_240: Revision = Revision::new(240);

/// OSRS-style revision with an optional subrevision (for example `240.2`).
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct Revision {
    major: u16,
    subrevision: Option<u16>,
}

impl Revision {
    /// Construct a major-only revision such as `240`.
    #[must_use]
    pub const fn new(major: u16) -> Self {
        Self {
            major,
            subrevision: None,
        }
    }

    /// Construct a revision with an explicit subrevision such as `240.2`.
    #[must_use]
    pub const fn with_subrevision(major: u16, subrevision: u16) -> Self {
        Self {
            major,
            subrevision: Some(subrevision),
        }
    }

    /// Return the major revision number.
    #[must_use]
    pub const fn major(self) -> u16 {
        self.major
    }

    /// Return the optional subrevision.
    #[must_use]
    pub const fn subrevision(self) -> Option<u16> {
        self.subrevision
    }
}

impl fmt::Display for Revision {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        match self.subrevision {
            Some(subrevision) => write!(formatter, "{}.{subrevision}", self.major),
            None => self.major.fmt(formatter),
        }
    }
}

/// Failure to parse a textual [`Revision`].
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum RevisionParseError {
    /// Input contained no revision.
    Empty,
    /// Major revision was not a valid `u16`.
    InvalidMajor,
    /// Subrevision was present but not a valid `u16`.
    InvalidSubrevision,
    /// More than one subrevision separator was present.
    TooManyComponents,
}

impl fmt::Display for RevisionParseError {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        let message = match self {
            Self::Empty => "revision is empty",
            Self::InvalidMajor => "revision major component is invalid",
            Self::InvalidSubrevision => "revision subrevision component is invalid",
            Self::TooManyComponents => "revision has too many components",
        };
        formatter.write_str(message)
    }
}

impl std::error::Error for RevisionParseError {}

impl FromStr for Revision {
    type Err = RevisionParseError;

    fn from_str(value: &str) -> Result<Self, Self::Err> {
        let value = value.trim();
        if value.is_empty() {
            return Err(RevisionParseError::Empty);
        }

        if let Some((major, subrevision)) = value.split_once('.') {
            if subrevision.contains('.') {
                return Err(RevisionParseError::TooManyComponents);
            }
            let major = major
                .parse::<u16>()
                .map_err(|_| RevisionParseError::InvalidMajor)?;
            let subrevision = subrevision
                .parse::<u16>()
                .map_err(|_| RevisionParseError::InvalidSubrevision)?;
            Ok(Self::with_subrevision(major, subrevision))
        } else {
            let major = value
                .parse::<u16>()
                .map_err(|_| RevisionParseError::InvalidMajor)?;
            Ok(Self::new(major))
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn revision_240_is_initial_baseline() {
        assert_eq!(REVISION_240.major(), 240);
        assert_eq!(REVISION_240.subrevision(), None);
        assert_eq!(REVISION_240.to_string(), "240");
    }

    #[test]
    fn revision_with_subrevision_round_trips() {
        let revision = "240.2".parse::<Revision>().expect("valid revision");
        assert_eq!(revision, Revision::with_subrevision(240, 2));
        assert_eq!(revision.to_string(), "240.2");
    }

    #[test]
    fn malformed_revision_is_rejected() {
        assert_eq!("".parse::<Revision>(), Err(RevisionParseError::Empty));
        assert_eq!(
            "abc".parse::<Revision>(),
            Err(RevisionParseError::InvalidMajor)
        );
        assert_eq!(
            "240.x".parse::<Revision>(),
            Err(RevisionParseError::InvalidSubrevision)
        );
        assert_eq!(
            "240.2.1".parse::<Revision>(),
            Err(RevisionParseError::TooManyComponents)
        );
    }

    #[test]
    fn major_and_subrevision_are_not_conflated() {
        assert_ne!(Revision::new(240), Revision::with_subrevision(240, 0));
    }
}
