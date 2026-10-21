use base64::{Engine as _, engine::general_purpose::STANDARD};
use rustclient_platform::{BlobStorage, StorageError, StorageErrorKind, StorageKey};

const STORAGE_PREFIX: &str = "rustclient:";

/// Browser `localStorage` adapter for small persistent blobs.
///
/// Bytes are base64 encoded because the browser API stores UTF-16 strings. Large
/// assets and caches will use a later IndexedDB-backed capability rather than
/// overloading this bootstrap adapter.
#[derive(Debug, Clone)]
pub struct LocalStorage {
    storage: web_sys::Storage,
}

impl LocalStorage {
    /// Acquire the active window's local storage capability.
    pub fn new() -> Result<Self, StorageError> {
        let window = web_sys::window().ok_or_else(|| {
            StorageError::new(
                StorageErrorKind::Unavailable,
                "browser window is unavailable",
            )
        })?;
        let storage = window
            .local_storage()
            .map_err(map_js_error)?
            .ok_or_else(|| {
                StorageError::new(
                    StorageErrorKind::Unavailable,
                    "browser localStorage is unavailable",
                )
            })?;
        Ok(Self { storage })
    }

    fn namespaced_key(key: &StorageKey) -> String {
        format!("{STORAGE_PREFIX}{}", key.as_str())
    }
}

impl BlobStorage for LocalStorage {
    fn get(&self, key: &StorageKey) -> Result<Option<Vec<u8>>, StorageError> {
        let Some(encoded) = self
            .storage
            .get_item(&Self::namespaced_key(key))
            .map_err(map_js_error)?
        else {
            return Ok(None);
        };

        STANDARD.decode(encoded).map(Some).map_err(|error| {
            StorageError::new(
                StorageErrorKind::Corrupt,
                format!("stored browser blob is not valid base64: {error}"),
            )
        })
    }

    fn put(&self, key: &StorageKey, value: &[u8]) -> Result<(), StorageError> {
        let encoded = STANDARD.encode(value);
        self.storage
            .set_item(&Self::namespaced_key(key), &encoded)
            .map_err(map_js_error)
    }

    fn delete(&self, key: &StorageKey) -> Result<(), StorageError> {
        self.storage
            .remove_item(&Self::namespaced_key(key))
            .map_err(map_js_error)
    }
}

fn map_js_error(value: wasm_bindgen::JsValue) -> StorageError {
    let message = value
        .as_string()
        .unwrap_or_else(|| format!("browser storage error: {value:?}"));
    StorageError::new(StorageErrorKind::Backend, message)
}
