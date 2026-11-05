//! Capability names and allocation-aware grant sets.
use core::fmt;
use serde::{Deserialize, Serialize};
use std::collections::BTreeSet;

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
    /// Read varps and varbits (API 0.2).
    GameReadVars,
    /// Read chat lines (API 0.2).
    GameReadChat,
    /// Read skill levels and experience (API 0.2).
    GameReadSkills,
    /// Read client facts: fps, canvas, camera, focus, run energy (API 0.2).
    GameReadClient,
    /// Read the sorted menu and edit its entries (API 0.2).
    UiMenuSwap,
    /// Hide actors and effects from the scene (API 0.2).
    UiEntitiesHide,
    /// Replace interface sprites (API 0.2).
    UiSpriteOverride,
    /// Show info boxes (API 0.2).
    UiInfobox,
    /// Control stretched mode (API 0.2).
    UiViewport,
    /// Set roof and animation-smoothing scene flags (API 0.2).
    SceneControl,
    /// Move the camera (API 0.2).
    CameraControl,
    /// Raise user notifications (API 0.2).
    ClientNotify,
    /// Cap the frame rate (API 0.2).
    ClientFrameLimit,
    /// Zoom the minimap and recolor its dots (API 0.2).
    UiMinimap,
    /// Read the items lying on the ground (API 0.2, additive).
    GameReadGroundItems,
}

impl Capability {
    /// Every capability understood by this client, in stable order.
    pub const ALL: [Self; 25] = [
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
        Self::GameReadVars,
        Self::GameReadChat,
        Self::GameReadSkills,
        Self::GameReadClient,
        Self::UiMenuSwap,
        Self::UiEntitiesHide,
        Self::UiSpriteOverride,
        Self::UiInfobox,
        Self::UiViewport,
        Self::SceneControl,
        Self::CameraControl,
        Self::ClientNotify,
        Self::ClientFrameLimit,
        Self::UiMinimap,
        Self::GameReadGroundItems,
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
            Self::GameReadVars => "game.read.vars",
            Self::GameReadChat => "game.read.chat",
            Self::GameReadSkills => "game.read.skills",
            Self::GameReadClient => "game.read.client",
            Self::UiMenuSwap => "ui.menu.swap",
            Self::UiEntitiesHide => "ui.entities.hide",
            Self::UiSpriteOverride => "ui.sprite.override",
            Self::UiInfobox => "ui.infobox",
            Self::UiViewport => "ui.viewport",
            Self::SceneControl => "scene.control",
            Self::CameraControl => "camera.control",
            Self::ClientNotify => "client.notify",
            Self::ClientFrameLimit => "client.frame_limit",
            Self::UiMinimap => "ui.minimap",
            Self::GameReadGroundItems => "game.read.ground_items",
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
