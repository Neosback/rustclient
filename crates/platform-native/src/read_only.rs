use std::{
    fs::File,
    io::{Read, Seek, SeekFrom},
    path::PathBuf,
};

use rustclient_platform::{ReadOnlyBlobs, StorageError, StorageErrorKind, StorageKey};

/// Read-only directory adapter. Construction never creates or repairs its root.
#[derive(Debug)]
pub struct DirectoryBlobs {
    root: PathBuf,
}

impl DirectoryBlobs {
    /// Open an existing directory, resolving it once for traversal confinement.
    ///
    /// # Errors
    /// Returns an error if the root is absent, inaccessible or not a directory.
    pub fn open(root: impl Into<PathBuf>) -> Result<Self, StorageError> {
        let root = root.into();
        let root = root
            .canonicalize()
            .map_err(|error| map_error("cache root", &error))?;
        if !root.is_dir() {
            return Err(StorageError::new(
                StorageErrorKind::Backend,
                "cache root is not a directory",
            ));
        }
        Ok(Self { root })
    }

    fn open_blob(&self, key: &StorageKey) -> Result<Option<File>, StorageError> {
        let path = match self.root.join(key.as_str()).canonicalize() {
            Ok(path) => path,
            Err(error) if error.kind() == std::io::ErrorKind::NotFound => return Ok(None),
            Err(error) => return Err(map_error(key.as_str(), &error)),
        };
        if !path.starts_with(&self.root) {
            return Err(StorageError::new(
                StorageErrorKind::PermissionDenied,
                "asset symlink escapes configured root",
            ));
        }
        File::open(path)
            .map(Some)
            .map_err(|error| map_error(key.as_str(), &error))
    }
}

impl ReadOnlyBlobs for DirectoryBlobs {
    fn len(&self, key: &StorageKey) -> Result<Option<u64>, StorageError> {
        self.open_blob(key)?
            .map(|file| {
                file.metadata()
                    .map(|m| m.len())
                    .map_err(|error| map_error(key.as_str(), &error))
            })
            .transpose()
    }

    fn read_exact_at(
        &self,
        key: &StorageKey,
        offset: u64,
        output: &mut [u8],
    ) -> Result<(), StorageError> {
        let mut file = self.open_blob(key)?.ok_or_else(|| {
            StorageError::new(
                StorageErrorKind::Backend,
                format!("missing asset {}", key.as_str()),
            )
        })?;
        file.seek(SeekFrom::Start(offset))
            .and_then(|_| file.read_exact(output))
            .map_err(|error| map_error(key.as_str(), &error))
    }
}

fn map_error(context: &str, error: &std::io::Error) -> StorageError {
    let kind = if error.kind() == std::io::ErrorKind::PermissionDenied {
        StorageErrorKind::PermissionDenied
    } else {
        StorageErrorKind::Backend
    };
    StorageError::new(kind, format!("{context}: {error}"))
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn absent_directory_is_not_created() {
        let root =
            std::env::temp_dir().join(format!("rustclient-absent-cache-{}", std::process::id()));
        assert!(DirectoryBlobs::open(&root).is_err());
        assert!(!root.exists());
    }
}
