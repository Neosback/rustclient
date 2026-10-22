//! Bounded, read-only asset access, independent of filesystems and browsers.

use std::collections::BTreeMap;

use crate::{StorageError, StorageErrorKind, StorageKey};

/// Random access to immutable named blobs. Implementations must not create files.
pub trait ReadOnlyBlobs {
    /// Return the byte length, or `None` for an absent blob.
    ///
    /// # Errors
    /// Returns a contextual storage error on backend failure.
    fn len(&self, key: &StorageKey) -> Result<Option<u64>, StorageError>;

    /// Fill `output` with an exact range. Short reads are errors, not zero padding.
    ///
    /// # Errors
    /// Returns an error for missing blobs, invalid ranges or backend failure.
    fn read_exact_at(
        &self,
        key: &StorageKey,
        offset: u64,
        output: &mut [u8],
    ) -> Result<(), StorageError>;
}

/// Owned in-memory blobs for tests and preloaded browser assets.
#[derive(Debug, Default)]
pub struct MemoryBlobs(BTreeMap<StorageKey, Vec<u8>>);

impl MemoryBlobs {
    /// Construct from already acquired immutable blob contents.
    #[must_use]
    pub fn new(blobs: impl IntoIterator<Item = (StorageKey, Vec<u8>)>) -> Self {
        Self(blobs.into_iter().collect())
    }
}

impl ReadOnlyBlobs for MemoryBlobs {
    fn len(&self, key: &StorageKey) -> Result<Option<u64>, StorageError> {
        Ok(self.0.get(key).map(|bytes| bytes.len() as u64))
    }

    fn read_exact_at(
        &self,
        key: &StorageKey,
        offset: u64,
        output: &mut [u8],
    ) -> Result<(), StorageError> {
        let source = self.0.get(key).ok_or_else(|| range_error(key))?;
        let start = usize::try_from(offset).map_err(|_| range_error(key))?;
        let end = start
            .checked_add(output.len())
            .ok_or_else(|| range_error(key))?;
        let bytes = source.get(start..end).ok_or_else(|| range_error(key))?;
        output.copy_from_slice(bytes);
        Ok(())
    }
}

fn range_error(key: &StorageKey) -> StorageError {
    StorageError::new(
        StorageErrorKind::Corrupt,
        format!("missing or invalid range: {}", key.as_str()),
    )
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn exact_ranges_never_pad_or_overflow() {
        let key = StorageKey::parse("assets/data").unwrap();
        let blobs = MemoryBlobs::new([(key.clone(), vec![1, 2, 3])]);
        let mut output = [0; 2];
        blobs.read_exact_at(&key, 1, &mut output).unwrap();
        assert_eq!(output, [2, 3]);
        assert!(blobs.read_exact_at(&key, 2, &mut output).is_err());
        assert!(blobs.read_exact_at(&key, u64::MAX, &mut output).is_err());
        assert_eq!(
            blobs.len(&StorageKey::parse("absent").unwrap()).unwrap(),
            None
        );
    }
}
