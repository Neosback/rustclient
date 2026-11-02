//! Plugin/version identities and their stable serialization.
use core::fmt;
use serde::{Deserialize, Serialize};

/// Major version of the plugin API implemented by this client.
pub const API_MAJOR: u16 = 0;
/// Minor version of the plugin API implemented by this client.
pub const API_MINOR: u16 = 2;

/// One semantic plugin API version.
///
/// Additive evolution stays within a major version: a plugin built for a lower minor version
/// runs on a host with the same major and an equal or higher minor version.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct PluginApiVersion {
    /// Breaking-change axis.
    pub major: u16,
    /// Additive-change axis.
    pub minor: u16,
}

impl PluginApiVersion {
    /// Construct a version.
    #[must_use]
    pub const fn new(major: u16, minor: u16) -> Self {
        Self { major, minor }
    }

    /// Version implemented by this client.
    #[must_use]
    pub const fn current() -> Self {
        Self::new(API_MAJOR, API_MINOR)
    }

    /// Parse `major.minor`.
    ///
    /// # Errors
    /// Rejects values without exactly two numeric components.
    pub fn parse(text: &str) -> Result<Self, ApiVersionError> {
        let (major, minor) = text.split_once('.').ok_or(ApiVersionError)?;
        Ok(Self::new(
            major.parse().map_err(|_| ApiVersionError)?,
            minor.parse().map_err(|_| ApiVersionError)?,
        ))
    }

    /// Whether a plugin requiring `self` is loadable by a host implementing `host`.
    #[must_use]
    pub const fn is_compatible_with(self, host: Self) -> bool {
        self.major == host.major && self.minor <= host.minor
    }
}

impl fmt::Display for PluginApiVersion {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(formatter, "{}.{}", self.major, self.minor)
    }
}

impl Serialize for PluginApiVersion {
    fn serialize<S: serde::Serializer>(&self, serializer: S) -> Result<S::Ok, S::Error> {
        serializer.serialize_str(&self.to_string())
    }
}

impl<'de> Deserialize<'de> for PluginApiVersion {
    fn deserialize<D: serde::Deserializer<'de>>(deserializer: D) -> Result<Self, D::Error> {
        let text = String::deserialize(deserializer)?;
        Self::parse(&text).map_err(serde::de::Error::custom)
    }
}

/// Failure to parse a plugin API version.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ApiVersionError;

impl fmt::Display for ApiVersionError {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        formatter.write_str("expected a plugin API version in `major.minor` form")
    }
}

impl std::error::Error for ApiVersionError {}

/// Stable identity of one plugin package.
///
/// Lowercase dot-separated segments; the last segment is the plugin name.
#[derive(Debug, Clone, PartialEq, Eq, PartialOrd, Ord, Hash, Serialize, Deserialize)]
#[serde(try_from = "String", into = "String")]
pub struct PluginId(String);

impl PluginId {
    /// Validate and construct a plugin ID.
    ///
    /// # Errors
    /// Rejects empty, oversized, uppercase or malformed identifiers.
    pub fn parse(text: &str) -> Result<Self, InvalidPluginId> {
        if text.is_empty() || text.len() > 128 {
            return Err(InvalidPluginId);
        }
        let mut segments = text.split('.');
        let valid_segment = |segment: &str| {
            !segment.is_empty()
                && segment
                    .bytes()
                    .all(|byte| byte.is_ascii_lowercase() || byte.is_ascii_digit() || byte == b'-')
        };
        if segments.clone().count() < 2 || !segments.all(valid_segment) {
            return Err(InvalidPluginId);
        }
        Ok(Self(text.to_owned()))
    }

    /// Borrow the canonical text form.
    #[must_use]
    pub fn as_str(&self) -> &str {
        &self.0
    }
}

impl fmt::Display for PluginId {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        formatter.write_str(&self.0)
    }
}

impl TryFrom<String> for PluginId {
    type Error = InvalidPluginId;

    fn try_from(value: String) -> Result<Self, Self::Error> {
        Self::parse(&value)
    }
}

impl From<PluginId> for String {
    fn from(value: PluginId) -> Self {
        value.0
    }
}

/// Failure to parse a plugin ID.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct InvalidPluginId;

impl fmt::Display for InvalidPluginId {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        formatter.write_str("expected a lowercase dot-separated plugin id")
    }
}

impl std::error::Error for InvalidPluginId {}
