//! Plugin package manifest: the operator-visible contract for one plugin.
use crate::{CapabilitySet, PluginApiVersion, PluginId};
use serde::Deserialize;
use std::path::{Path, PathBuf};

/// Contents of a plugin package's `plugin.json`.
#[derive(Debug, Clone, Deserialize)]
#[serde(deny_unknown_fields)]
pub struct PluginManifest {
    /// Stable plugin identity.
    pub id: PluginId,
    /// Human-readable name.
    pub name: String,
    /// Package version.
    pub version: String,
    /// Plugin API version the package was built against.
    #[serde(rename = "plugin-api")]
    pub plugin_api: PluginApiVersion,
    /// Capabilities requested by the package; the host grants only what the operator enables.
    #[serde(default)]
    pub capabilities: CapabilitySet,
    /// Component file name inside the package directory.
    pub entry: String,
}

impl PluginManifest {
    /// Parse a manifest from JSON text.
    ///
    /// # Errors
    /// Rejects malformed JSON, unknown fields and invalid typed fields.
    pub fn parse(text: &str) -> Result<Self, ManifestError> {
        serde_json::from_str(text).map_err(|error| ManifestError::Json(error.to_string()))
    }

    /// Read and parse `plugin.json` from a package directory.
    ///
    /// # Errors
    /// Propagates I/O and parse failures.
    pub fn from_dir(directory: &Path) -> Result<Self, ManifestError> {
        let path = directory.join("plugin.json");
        let text = std::fs::read_to_string(&path)
            .map_err(|error| ManifestError::Io(format!("{}: {error}", path.display())))?;
        Self::parse(&text)
    }

    /// Check this package against a host API version.
    ///
    /// # Errors
    /// Rejects packages from a different major or a newer minor version.
    pub fn check_api(&self, host: PluginApiVersion) -> Result<(), ManifestError> {
        if self.plugin_api.is_compatible_with(host) {
            return Ok(());
        }
        Err(ManifestError::IncompatibleApi {
            required: self.plugin_api,
            host,
        })
    }

    /// Resolve the component path inside a package directory.
    ///
    /// # Errors
    /// Rejects entry names containing separators so a manifest cannot escape its package.
    pub fn entry_path(&self, directory: &Path) -> Result<PathBuf, ManifestError> {
        if self.entry.is_empty()
            || self.entry.contains('/')
            || self.entry.contains('\\')
            || self.entry == "."
            || self.entry == ".."
        {
            return Err(ManifestError::InvalidEntry);
        }
        Ok(directory.join(&self.entry))
    }
}

/// Failure to load or validate a plugin manifest.
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum ManifestError {
    /// The manifest file could not be read.
    Io(String),
    /// The manifest JSON was malformed.
    Json(String),
    /// The plugin targets an incompatible API version.
    IncompatibleApi {
        /// Version the package was built against.
        required: PluginApiVersion,
        /// Version this client implements.
        host: PluginApiVersion,
    },
    /// The component entry name is not a plain file name.
    InvalidEntry,
}

impl std::fmt::Display for ManifestError {
    fn fmt(&self, formatter: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        match self {
            Self::Io(message) => write!(formatter, "cannot read plugin manifest: {message}"),
            Self::Json(message) => write!(formatter, "invalid plugin manifest: {message}"),
            Self::IncompatibleApi { required, host } => write!(
                formatter,
                "plugin requires API {required} but this client implements {host}"
            ),
            Self::InvalidEntry => formatter.write_str("plugin entry must be a plain file name"),
        }
    }
}

impl std::error::Error for ManifestError {}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::Capability;

    const VALID: &str = r#"{
        "id": "dev.rustclient.hello-overlay",
        "name": "Hello Overlay",
        "version": "0.1.0",
        "plugin-api": "0.1",
        "capabilities": ["game.read.players", "ui.overlay", "storage.plugin"],
        "entry": "hello_overlay.wasm"
    }"#;

    #[test]
    fn valid_manifest_parses_with_grants() {
        let manifest = PluginManifest::parse(VALID).expect("manifest should parse");
        assert_eq!(manifest.id.as_str(), "dev.rustclient.hello-overlay");
        assert_eq!(manifest.plugin_api, PluginApiVersion::new(0, 1));
        manifest
            .check_api(PluginApiVersion::current())
            .expect("0.1 packages keep loading on a 0.2 host");
        assert!(manifest.capabilities.contains(Capability::UiOverlay));
        assert!(!manifest.capabilities.contains(Capability::NetworkHttp));
        assert_eq!(
            manifest
                .entry_path(Path::new("/plugins/hello"))
                .expect("plain entry"),
            PathBuf::from("/plugins/hello/hello_overlay.wasm")
        );
    }

    #[test]
    fn api_0_2_capabilities_parse_and_future_minors_are_rejected() {
        let v2 = VALID
            .replace("\"plugin-api\": \"0.1\"", "\"plugin-api\": \"0.2\"")
            .replace("ui.overlay", "ui.entities.hide\", \"game.read.vars");
        let manifest = PluginManifest::parse(&v2).expect("0.2 manifest parses");
        assert!(manifest.capabilities.contains(Capability::UiEntitiesHide));
        assert!(manifest.capabilities.contains(Capability::GameReadVars));
        manifest
            .check_api(PluginApiVersion::current())
            .expect("0.2 package loads");
        assert!(manifest.check_api(PluginApiVersion::new(0, 1)).is_err());
    }

    #[test]
    fn manifests_cannot_escape_their_package() {
        let escape = VALID.replace("hello_overlay.wasm", "../evil.wasm");
        let manifest = PluginManifest::parse(&escape).expect("json itself is valid");
        assert_eq!(
            manifest.entry_path(Path::new("/plugins/hello")),
            Err(ManifestError::InvalidEntry)
        );
    }

    #[test]
    fn incompatible_api_is_rejected_with_context() {
        let future = VALID.replace("\"plugin-api\": \"0.1\"", "\"plugin-api\": \"0.9\"");
        let manifest = PluginManifest::parse(&future).expect("json itself is valid");
        assert!(manifest.check_api(PluginApiVersion::current()).is_err());
    }

    #[test]
    fn unknown_capabilities_are_rejected() {
        let unknown = VALID.replace("ui.overlay", "ui.teleport");
        assert!(PluginManifest::parse(&unknown).is_err());
    }
}
