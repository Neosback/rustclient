use std::fs;
use std::io::ErrorKind;
use std::io::Write;
use std::path::{Path, PathBuf};

use rustclient_platform::{BlobStorage, StorageError, StorageErrorKind, StorageKey};

/// Native directory-backed blob storage.
///
/// Semantic storage keys are mapped under one configured root. This adapter does
/// not expose absolute paths through the shared storage contract.
#[derive(Debug, Clone)]
pub struct DirectoryStorage {
    root: PathBuf,
}

impl DirectoryStorage {
    /// Create/open storage rooted at `root`.
    ///
    /// # Errors
    ///
    /// Returns [`StorageError`] if the root directory cannot be created or
    /// accessed by the native filesystem backend.
    pub fn new(root: impl Into<PathBuf>) -> Result<Self, StorageError> {
        let root = root.into();
        fs::create_dir_all(&root).map_err(|error| map_io_error(&error))?;
        Ok(Self { root })
    }

    fn path_for(&self, key: &StorageKey) -> PathBuf {
        key.as_str()
            .split('/')
            .fold(self.root.clone(), |path, segment| path.join(segment))
    }

    fn ensure_parent(path: &Path) -> Result<(), StorageError> {
        if let Some(parent) = path.parent() {
            fs::create_dir_all(parent).map_err(|error| map_io_error(&error))?;
        }
        Ok(())
    }
}

impl BlobStorage for DirectoryStorage {
    fn get(&self, key: &StorageKey) -> Result<Option<Vec<u8>>, StorageError> {
        let path = self.path_for(key);
        match fs::read(path) {
            Ok(value) => Ok(Some(value)),
            Err(error) if error.kind() == ErrorKind::NotFound => Ok(None),
            Err(error) => Err(map_io_error(&error)),
        }
    }

    fn put(&self, key: &StorageKey, value: &[u8]) -> Result<(), StorageError> {
        let path = self.path_for(key);
        Self::ensure_parent(&path)?;
        // Stage alongside the target so rename stays on the same filesystem. A
        // failed write/flush/rename never truncates the last accepted document.
        let mut nonce = [0_u8; 16];
        getrandom::fill(&mut nonce).map_err(|error| {
            StorageError::new(
                StorageErrorKind::Backend,
                format!("stage storage value: {error}"),
            )
        })?;
        let suffix = u128::from_be_bytes(nonce);
        let staged = path.with_extension(format!("{suffix:032x}.pending"));
        let result = (|| {
            let mut file = fs::OpenOptions::new()
                .create_new(true)
                .write(true)
                .open(&staged)?;
            file.write_all(value)?;
            file.sync_all()?;
            fs::rename(&staged, &path)
        })();
        if let Err(error) = result {
            let _ = fs::remove_file(&staged);
            return Err(map_io_error(&error));
        }
        Ok(())
    }

    fn delete(&self, key: &StorageKey) -> Result<(), StorageError> {
        match fs::remove_file(self.path_for(key)) {
            Ok(()) => Ok(()),
            Err(error) if error.kind() == ErrorKind::NotFound => Ok(()),
            Err(error) => Err(map_io_error(&error)),
        }
    }
}

fn map_io_error(error: &std::io::Error) -> StorageError {
    let kind = match error.kind() {
        ErrorKind::PermissionDenied => StorageErrorKind::PermissionDenied,
        ErrorKind::StorageFull | ErrorKind::FileTooLarge => StorageErrorKind::Capacity,
        _ => StorageErrorKind::Backend,
    };
    StorageError::new(kind, error.to_string())
}

#[cfg(test)]
mod tests {
    use std::time::{SystemTime, UNIX_EPOCH};

    use super::*;

    fn test_root() -> PathBuf {
        let nonce = SystemTime::now()
            .duration_since(UNIX_EPOCH)
            .expect("test clock after epoch")
            .as_nanos();
        std::env::temp_dir().join(format!("rustclient-storage-{}-{nonce}", std::process::id()))
    }

    #[test]
    fn round_trips_namespaced_blob() {
        let root = test_root();
        let storage = DirectoryStorage::new(&root).expect("create storage");
        let key = StorageKey::parse("plugins/example/config.bin").expect("valid key");

        assert_eq!(storage.get(&key).expect("read missing"), None);
        storage.put(&key, b"hello").expect("write value");
        assert_eq!(
            storage.get(&key).expect("read value"),
            Some(b"hello".to_vec())
        );
        storage.delete(&key).expect("delete value");
        assert_eq!(storage.get(&key).expect("read deleted"), None);

        fs::remove_dir_all(root).expect("clean test directory");
    }
}
