//! Target-neutral title loading and non-secret preference persistence.
use rustclient_cache::{Cache, CacheError, CacheProfile, TitleAssets};
use rustclient_game_ui::TitlePreferences;
use rustclient_platform::{BlobStorage, ReadOnlyBlobs, StorageError, StorageErrorKind, StorageKey};
use serde::{Deserialize, Serialize};

/// Load title assets from an injected immutable source. Scheduling belongs to the app.
///
/// # Errors
/// Returns cache/profile diagnostics with exact source provenance.
pub fn load_title<S: ReadOnlyBlobs>(
    source: S,
    profile: CacheProfile,
    mut progress: impl FnMut(&str),
) -> Result<TitleAssets, CacheError> {
    progress("reference tables");
    let cache = Cache::open(source)?;
    profile.load_title(&cache, progress)
}

#[derive(Serialize, Deserialize)]
#[serde(deny_unknown_fields)]
struct PreferencesV1 {
    version: u8,
    remember_username: bool,
    hide_username: bool,
    username: Option<String>,
}

fn key() -> Result<StorageKey, StorageError> {
    StorageKey::parse("title/preferences.json")
        .map_err(|error| StorageError::new(StorageErrorKind::Backend, error.to_string()))
}

/// Read local preferences, retaining a username only after explicit opt-in.
///
/// # Errors
/// Returns storage/corruption diagnostics; callers may fall back to defaults.
pub fn load_title_preferences(
    storage: &impl BlobStorage,
) -> Result<TitlePreferences, StorageError> {
    let Some(bytes) = storage.get(&key()?)? else {
        return Ok(TitlePreferences::default());
    };
    if bytes.len() > 8192 {
        return Err(StorageError::new(
            StorageErrorKind::Corrupt,
            "title preferences exceed size limit",
        ));
    }
    let value: PreferencesV1 = serde_json::from_slice(&bytes).map_err(|error| {
        StorageError::new(
            StorageErrorKind::Corrupt,
            format!("title preferences: {error}"),
        )
    })?;
    if value.version != 1 {
        return Err(StorageError::new(
            StorageErrorKind::Corrupt,
            "unsupported title preference version",
        ));
    }
    let username = if value.remember_username {
        value.username.unwrap_or_default()
    } else {
        String::new()
    };
    // Reuse the presentation owner's bounded normalization rather than duplicate it.
    Ok(rustclient_game_ui::TitleUi::new(TitlePreferences {
        remember_username: value.remember_username,
        hide_username: value.hide_username,
        username,
    })
    .preferences())
}

/// Persist display settings and, only when opted in, the current username.
/// Passwords cannot be passed through this interface.
///
/// # Errors
/// Returns storage/serialization errors without failing the title session.
pub fn save_title_preferences(
    storage: &impl BlobStorage,
    preferences: &TitlePreferences,
) -> Result<(), StorageError> {
    let value = PreferencesV1 {
        version: 1,
        remember_username: preferences.remember_username,
        hide_username: preferences.hide_username,
        username: preferences
            .remember_username
            .then(|| preferences.username.clone()),
    };
    let bytes = serde_json::to_vec(&value)
        .map_err(|error| StorageError::new(StorageErrorKind::Backend, error.to_string()))?;
    storage.put(&key()?, &bytes)
}

#[cfg(test)]
mod tests {
    use super::*;
    use std::cell::RefCell;
    #[derive(Default)]
    struct Memory(RefCell<Option<Vec<u8>>>);
    impl BlobStorage for Memory {
        fn get(&self, _: &StorageKey) -> Result<Option<Vec<u8>>, StorageError> {
            Ok(self.0.borrow().clone())
        }
        fn put(&self, _: &StorageKey, bytes: &[u8]) -> Result<(), StorageError> {
            *self.0.borrow_mut() = Some(bytes.to_vec());
            Ok(())
        }
        fn delete(&self, _: &StorageKey) -> Result<(), StorageError> {
            *self.0.borrow_mut() = None;
            Ok(())
        }
    }
    #[test]
    fn remembering_is_opt_in_and_disabling_removes_stored_username() {
        let store = Memory::default();
        assert_eq!(
            load_title_preferences(&store).unwrap(),
            TitlePreferences::default()
        );
        let mut preferences = TitlePreferences {
            remember_username: true,
            hide_username: true,
            username: "local-preview".into(),
        };
        save_title_preferences(&store, &preferences).unwrap();
        assert_eq!(load_title_preferences(&store).unwrap(), preferences);
        preferences.remember_username = false;
        save_title_preferences(&store, &preferences).unwrap();
        assert!(load_title_preferences(&store).unwrap().username.is_empty());
        assert!(
            !String::from_utf8(store.0.borrow().clone().unwrap())
                .unwrap()
                .contains("local-preview")
        );
    }
    #[test]
    fn corrupt_preferences_are_reported() {
        let store = Memory(RefCell::new(Some(b"not json".to_vec())));
        assert_eq!(
            load_title_preferences(&store).unwrap_err().kind(),
            StorageErrorKind::Corrupt
        );
    }
}
