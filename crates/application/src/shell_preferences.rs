//! Application-owned sidebar preferences, independent of any plugin configuration or game state.
use rustclient_platform::{BlobStorage, StorageKey};
use rustclient_plugin_api::PluginId;
use serde::{Deserialize, Serialize};
use std::collections::BTreeSet;

#[derive(Debug, Default, Clone, PartialEq, Eq, Serialize, Deserialize)]
#[serde(deny_unknown_fields)]
pub struct ShellPreferences {
    pub pinned: BTreeSet<PluginId>,
}
impl ShellPreferences {
    fn key() -> Result<StorageKey, String> {
        StorageKey::parse("application/sidebar-v1.json").map_err(|error| error.to_string())
    }
    /// Missing preferences use defaults; malformed data is retained and reported to the shell.
    /// # Errors
    /// Reports storage failures, malformed JSON, or an excessive number of favorites.
    pub fn load(storage: &dyn BlobStorage) -> Result<Self, String> {
        let bytes = storage
            .get(&Self::key()?)
            .map_err(|error| error.to_string())?;
        let preferences: Self = bytes
            .as_deref()
            .map(serde_json::from_slice)
            .transpose()
            .map_err(|error| format!("Sidebar preferences: {error}"))?
            .unwrap_or_default();
        if preferences.pinned.len() > 1024 {
            return Err("Sidebar preferences contain too many favorites".into());
        }
        Ok(preferences)
    }
    /// Publish only after atomic persistence, just like accepted plugin settings.
    /// # Errors
    /// Invalid IDs or failed writes leave accepted favorites unchanged.
    pub fn toggle(&mut self, plugin: &str, storage: &dyn BlobStorage) -> Result<(), String> {
        let id = PluginId::parse(plugin).map_err(|error| error.to_string())?;
        let mut candidate = self.clone();
        if !candidate.pinned.remove(&id) {
            if candidate.pinned.len() >= 1024 {
                return Err("Too many sidebar favorites".into());
            }
            candidate.pinned.insert(id);
        }
        let bytes = serde_json::to_vec(&candidate).map_err(|error| error.to_string())?;
        storage
            .put(&Self::key()?, &bytes)
            .map_err(|error| format!("Save sidebar favorites: {error}"))?;
        *self = candidate;
        Ok(())
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_platform::{StorageError, StorageErrorKind};
    use std::cell::{Cell, RefCell};

    #[derive(Default)]
    struct Storage {
        bytes: RefCell<Option<Vec<u8>>>,
        fail: Cell<bool>,
    }
    impl BlobStorage for Storage {
        fn get(&self, _: &StorageKey) -> Result<Option<Vec<u8>>, StorageError> {
            Ok(self.bytes.borrow().clone())
        }
        fn put(&self, _: &StorageKey, bytes: &[u8]) -> Result<(), StorageError> {
            if self.fail.get() {
                return Err(StorageError::new(StorageErrorKind::Backend, "test failure"));
            }
            *self.bytes.borrow_mut() = Some(bytes.to_vec());
            Ok(())
        }
        fn delete(&self, _: &StorageKey) -> Result<(), StorageError> {
            Ok(())
        }
    }
    #[test]
    fn favorites_round_trip_and_failed_writes_retain_accepted_state() {
        let storage = Storage::default();
        let mut preferences = ShellPreferences::load(&storage).unwrap();
        preferences.toggle("dev.rustclient.gpu", &storage).unwrap();
        assert_eq!(ShellPreferences::load(&storage).unwrap(), preferences);
        storage.fail.set(true);
        let before = preferences.clone();
        assert!(preferences.toggle("dev.rustclient.gpu", &storage).is_err());
        assert_eq!(preferences, before);
        storage.fail.set(false);
        preferences.toggle("dev.rustclient.gpu", &storage).unwrap();
        assert!(ShellPreferences::load(&storage).unwrap().pinned.is_empty());
    }
    #[test]
    fn malformed_preferences_are_reported_without_overwriting_bytes() {
        let storage = Storage::default();
        *storage.bytes.borrow_mut() = Some(b"broken".to_vec());
        assert!(ShellPreferences::load(&storage).is_err());
        assert_eq!(
            storage.bytes.borrow().as_deref(),
            Some(b"broken".as_slice())
        );
    }
}
