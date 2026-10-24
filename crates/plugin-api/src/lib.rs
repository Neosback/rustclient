// Stable, versioned vocabulary shared by the plugin host, runtime adapters and SDK.
//
// These types are public ABI surface. They intentionally do not expose `GameState`, protocol
// structs, renderer values or platform handles. The canonical contract is the WIT package in
// `wit/rustclient/plugin`; this crate adapts it for host-side Rust without becoming a second
// competing contract.

#![forbid(unsafe_code)]

use core::fmt;
use serde::{Deserialize, Serialize};
use std::collections::BTreeSet;

mod manifest;
pub use manifest::{ManifestError, PluginManifest};

/// Major version of the plugin API implemented by this client.
pub const API_MAJOR: u16 = 0;
/// Minor version of the plugin API implemented by this client.
pub const API_MINOR: u16 = 1;

/// Logical reference size of the overlay coordinate space in interface pixels.
pub const OVERLAY_WIDTH: u16 = 765;
/// Logical reference height of the overlay coordinate space in interface pixels.
pub const OVERLAY_HEIGHT: u16 = 503;

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

/// One least-privilege capability a plugin may request.
///
/// Capabilities are deny-by-default: a host grants only what the operator authorized for a
/// plugin package, regardless of what the component imports.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub enum Capability {
    /// Read committed player state.
    GameReadPlayers,
    /// Read committed NPC state.
    GameReadNpcs,
    /// Read committed inventory state.
    GameReadInventory,
    /// Read committed widget/interface state.
    GameReadWidgets,
    /// Submit overlay draw commands.
    UiOverlay,
    /// Submit panel descriptions.
    UiPanel,
    /// Decorate world menu entries.
    UiMenuDecorate,
    /// Persist plugin-local configuration.
    StoragePlugin,
    /// Perform explicit high-trust HTTP requests.
    NetworkHttp,
    /// Request player interactions subject to client policy.
    InteractionRequest,
}

impl Capability {
    /// Every capability understood by this client, in stable order.
    pub const ALL: [Self; 10] = [
        Self::GameReadPlayers,
        Self::GameReadNpcs,
        Self::GameReadInventory,
        Self::GameReadWidgets,
        Self::UiOverlay,
        Self::UiPanel,
        Self::UiMenuDecorate,
        Self::StoragePlugin,
        Self::NetworkHttp,
        Self::InteractionRequest,
    ];

    /// Canonical dotted name used by manifests and diagnostics.
    #[must_use]
    pub const fn as_str(self) -> &'static str {
        match self {
            Self::GameReadPlayers => "game.read.players",
            Self::GameReadNpcs => "game.read.npcs",
            Self::GameReadInventory => "game.read.inventory",
            Self::GameReadWidgets => "game.read.widgets",
            Self::UiOverlay => "ui.overlay",
            Self::UiPanel => "ui.panel",
            Self::UiMenuDecorate => "ui.menu.decorate",
            Self::StoragePlugin => "storage.plugin",
            Self::NetworkHttp => "network.http",
            Self::InteractionRequest => "interaction.request",
        }
    }

    /// Parse a canonical dotted capability name.
    ///
    /// # Errors
    /// Rejects names this client does not implement.
    pub fn parse(text: &str) -> Result<Self, UnknownCapability> {
        Self::ALL
            .into_iter()
            .find(|capability| capability.as_str() == text)
            .ok_or(UnknownCapability)
    }
}

impl fmt::Display for Capability {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        formatter.write_str(self.as_str())
    }
}

impl Serialize for Capability {
    fn serialize<S: serde::Serializer>(&self, serializer: S) -> Result<S::Ok, S::Error> {
        serializer.serialize_str(self.as_str())
    }
}

impl<'de> Deserialize<'de> for Capability {
    fn deserialize<D: serde::Deserializer<'de>>(deserializer: D) -> Result<Self, D::Error> {
        let text = String::deserialize(deserializer)?;
        Self::parse(&text).map_err(serde::de::Error::custom)
    }
}

/// Failure to parse a capability name.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct UnknownCapability;

impl fmt::Display for UnknownCapability {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        formatter.write_str("unknown plugin capability")
    }
}

impl std::error::Error for UnknownCapability {}

/// An ordered, duplicate-free grant set.
#[derive(Debug, Clone, Default, PartialEq, Eq, Serialize, Deserialize)]
#[serde(transparent)]
pub struct CapabilitySet(BTreeSet<Capability>);

impl CapabilitySet {
    /// Empty set: no capability granted.
    #[must_use]
    pub const fn none() -> Self {
        Self(BTreeSet::new())
    }

    /// Whether the set grants `capability`.
    #[must_use]
    pub fn contains(&self, capability: Capability) -> bool {
        self.0.contains(&capability)
    }

    /// Grant one capability.
    pub fn insert(&mut self, capability: Capability) {
        self.0.insert(capability);
    }

    /// Iterate granted capabilities in stable order.
    pub fn iter(&self) -> impl Iterator<Item = Capability> + '_ {
        self.0.iter().copied()
    }

    /// Number of granted capabilities.
    #[must_use]
    pub fn len(&self) -> usize {
        self.0.len()
    }

    /// Whether nothing is granted.
    #[must_use]
    pub fn is_empty(&self) -> bool {
        self.0.is_empty()
    }
}

impl FromIterator<Capability> for CapabilitySet {
    fn from_iter<T: IntoIterator<Item = Capability>>(iter: T) -> Self {
        Self(iter.into_iter().collect())
    }
}

/// Committed local player position exposed to granted plugins.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct PlayerPosition {
    /// World tile x.
    pub x: i32,
    /// World tile y.
    pub y: i32,
    /// World plane.
    pub plane: u8,
}

/// One overlay text command in the [`OVERLAY_WIDTH`] x [`OVERLAY_HEIGHT`] logical space.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct OverlayLine {
    /// Text drawn with the client's interface font.
    pub text: String,
    /// Logical x in interface pixels.
    pub x: u16,
    /// Logical y in interface pixels.
    pub y: u16,
    /// Packed `AARRGGBB`.
    pub argb: u32,
}

/// One committed-gameplay event delivered in batches.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum PluginEvent {
    /// A session began and state is available.
    Started,
    /// One committed gameplay tick.
    Tick(u64),
    /// The session ended; the plugin instance is stopped.
    Stopped,
}

/// Diagnostic severity.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub enum LogLevel {
    /// Fine-grained tracing.
    Trace,
    /// Developer diagnostics.
    Debug,
    /// Normal lifecycle information.
    Info,
    /// Recoverable problems.
    Warn,
    /// Failures.
    Error,
}

impl LogLevel {
    /// Short lowercase name for logs.
    #[must_use]
    pub const fn as_str(self) -> &'static str {
        match self {
            Self::Trace => "trace",
            Self::Debug => "debug",
            Self::Info => "info",
            Self::Warn => "warn",
            Self::Error => "error",
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn api_versions_are_additive_within_a_major() {
        let host = PluginApiVersion::current();
        assert!(PluginApiVersion::new(0, 0).is_compatible_with(host));
        assert!(PluginApiVersion::new(0, 1).is_compatible_with(host));
        assert!(!PluginApiVersion::new(0, 2).is_compatible_with(host));
        assert!(!PluginApiVersion::new(1, 0).is_compatible_with(host));
        assert_eq!(PluginApiVersion::parse("0.1"), Ok(host));
        assert!(PluginApiVersion::parse("0").is_err());
    }

    #[test]
    fn plugin_ids_reject_malformed_names() {
        assert!(PluginId::parse("dev.rustclient.hello-overlay").is_ok());
        assert!(PluginId::parse("hello").is_err());
        assert!(PluginId::parse("dev.RustClient").is_err());
        assert!(PluginId::parse("dev..overlay").is_err());
    }

    #[test]
    fn capability_names_round_trip() {
        for capability in Capability::ALL {
            assert_eq!(Capability::parse(capability.as_str()), Ok(capability));
        }
        assert!(Capability::parse("game.write.players").is_err());
    }

    #[test]
    fn capability_sets_are_deny_by_default() {
        let mut grants = CapabilitySet::none();
        assert!(!grants.contains(Capability::UiOverlay));
        grants.insert(Capability::UiOverlay);
        grants.insert(Capability::UiOverlay);
        assert!(grants.contains(Capability::UiOverlay));
        assert_eq!(grants.len(), 1);
    }
}
