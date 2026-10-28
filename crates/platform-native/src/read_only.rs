use std::{
    collections::VecDeque,
    fs::File,
    io::{Read, Seek, SeekFrom},
    path::PathBuf,
    sync::{Arc, Mutex},
};

use rustclient_platform::{ReadOnlyBlobs, StorageError, StorageErrorKind, StorageKey};

/// Read-only directory adapter. Construction never creates or repairs its root.
#[derive(Debug)]
pub struct DirectoryBlobs {
    root: PathBuf,
    handles: Mutex<Handles>,
}

const MAX_OPEN_BLOBS: usize = 32;
type SharedFile = Arc<Mutex<File>>;
#[derive(Debug, Default)]
struct Handles {
    entries: VecDeque<(StorageKey, SharedFile)>,
    opens: u64,
    hits: u64,
}

/// Descriptor-cache counters. Blob contents stay on disk and are never retained here.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct BlobReadStats {
    pub cached_files: usize,
    pub file_opens: u64,
    pub cache_hits: u64,
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
        Ok(Self {
            root,
            handles: Mutex::new(Handles::default()),
        })
    }

    /// Bounded native file descriptors; neither whole-file reads nor unbounded RAM caches.
    #[must_use]
    pub fn stats(&self) -> BlobReadStats {
        let handles = self
            .handles
            .lock()
            .unwrap_or_else(std::sync::PoisonError::into_inner);
        BlobReadStats {
            cached_files: handles.entries.len(),
            file_opens: handles.opens,
            cache_hits: handles.hits,
        }
    }
    fn open_blob(&self, key: &StorageKey) -> Result<Option<SharedFile>, StorageError> {
        {
            let mut handles = self.handles.lock().map_err(|_| lock_error(key))?;
            if let Some(index) = handles.entries.iter().position(|(have, _)| have == key) {
                let entry = handles
                    .entries
                    .remove(index)
                    .ok_or_else(|| lock_error(key))?;
                let file = Arc::clone(&entry.1);
                handles.entries.push_back(entry);
                handles.hits += 1;
                return Ok(Some(file));
            }
        }
        // Resolve and open outside the cache lock: unrelated readers must not wait for a cold
        // filesystem open. Confinement is checked before retaining the approved immutable file.

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
        let file = Arc::new(Mutex::new(
            File::open(path).map_err(|error| map_error(key.as_str(), &error))?,
        ));
        let mut handles = self.handles.lock().map_err(|_| lock_error(key))?;
        handles.opens += 1;
        if let Some((_, existing)) = handles.entries.iter().find(|(have, _)| have == key) {
            return Ok(Some(Arc::clone(existing)));
        }
        if handles.entries.len() == MAX_OPEN_BLOBS {
            handles.entries.pop_front();
        }
        handles.entries.push_back((key.clone(), Arc::clone(&file)));
        Ok(Some(file))
    }
}

impl ReadOnlyBlobs for DirectoryBlobs {
    fn len(&self, key: &StorageKey) -> Result<Option<u64>, StorageError> {
        self.open_blob(key)?
            .map(|file| {
                file.lock()
                    .map_err(|_| lock_error(key))?
                    .metadata()
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
        let file = self.open_blob(key)?.ok_or_else(|| {
            StorageError::new(
                StorageErrorKind::Backend,
                format!("missing asset {}", key.as_str()),
            )
        })?;
        // Per-file locking preserves exact positioned reads on every native backend. The
        // descriptor-cache lock is released before I/O; different files remain independent.
        let mut file = file.lock().map_err(|_| lock_error(key))?;
        file.seek(SeekFrom::Start(offset))
            .and_then(|_| file.read_exact(output))
            .map_err(|error| map_error(key.as_str(), &error))
    }
}

fn lock_error(key: &StorageKey) -> StorageError {
    StorageError::new(
        StorageErrorKind::Backend,
        format!("{}: asset handle lock poisoned", key.as_str()),
    )
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
    fn reads_remain_exact_and_concurrent_with_a_bounded_handle_cache() {
        let root = std::env::temp_dir().join(format!("rustclient-blobs-{}", std::process::id()));
        std::fs::create_dir_all(&root).unwrap();
        let expected: Vec<_> = (0..4096)
            .map(|index| u8::try_from(index % 251).unwrap())
            .collect();
        std::fs::write(root.join("data"), &expected).unwrap();
        let source = Arc::new(DirectoryBlobs::open(&root).unwrap());
        let key = StorageKey::parse("data").unwrap();
        std::thread::scope(|scope| {
            for offset in [0, 32, 512, 3000] {
                let source = Arc::clone(&source);
                let key = key.clone();
                let expected = &expected;
                scope.spawn(move || {
                    for _ in 0..100 {
                        let mut bytes = [0; 64];
                        source.read_exact_at(&key, offset, &mut bytes).unwrap();
                        assert_eq!(
                            bytes,
                            expected[usize::try_from(offset).unwrap()
                                ..usize::try_from(offset).unwrap() + 64]
                        );
                    }
                });
            }
        });
        let mut bytes = [0; 8];
        assert!(source.read_exact_at(&key, 4092, &mut bytes).is_err());
        assert!(source.read_exact_at(&key, u64::MAX, &mut bytes).is_err());
        assert_eq!(
            source.len(&StorageKey::parse("absent").unwrap()).unwrap(),
            None
        );
        // Up to four initial cold opens can race before one handle is published.
        // Every later read reuses that accepted handle; duplicate opens are discarded.
        let stats = source.stats();
        assert_eq!(stats.cached_files, 1);
        assert!(stats.file_opens <= 4);
        assert!(stats.cache_hits >= 396);
        for index in 0..40 {
            let name = format!("file-{index}");
            std::fs::write(root.join(&name), [7; 8]).unwrap();
            source
                .read_exact_at(&StorageKey::parse(name).unwrap(), 0, &mut bytes)
                .unwrap();
        }
        assert_eq!(source.stats().cached_files, MAX_OPEN_BLOBS);
        drop(source);
        std::fs::remove_dir_all(root).unwrap();
    }

    #[test]
    fn absent_directory_is_not_created() {
        let root =
            std::env::temp_dir().join(format!("rustclient-absent-cache-{}", std::process::id()));
        assert!(DirectoryBlobs::open(&root).is_err());
        assert!(!root.exists());
    }
}
