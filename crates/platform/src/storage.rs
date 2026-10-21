//! Platform-neutral blob storage capability.

use core::fmt;

const MAX_STORAGE_KEY_LEN: usize = 256;

/// Validated semantic storage key.
///
/// Keys may contain ASCII letters, digits, `-`, `_`, `.`, and `/`. Slash creates
/// semantic namespace segments; empty, `.` and `..` segments are rejected.
#[derive(Debug, Clone, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct StorageKey(Box<str>);

impl StorageKey {
    /// Parse and validate a semantic storage key.
    ///
    /// # Errors
    ///
    /// Returns [`StorageKeyError`] when the value is empty, too long, contains
    /// non-portable characters, or contains an invalid namespace segment.
    pub fn parse(value: impl Into<Box<str>>) -> Result<Self, StorageKeyError> {
        let value = value.into();
        validate_key(&value)?;
        Ok(Self(value))
    }

    /// Return the validated key text.
    #[must_use]
    pub fn as_str(&self) -> &str {
        &self.0
    }
}

fn validate_key(value: &str) -> Result<(), StorageKeyError> {
    if value.is_empty() {
        return Err(StorageKeyError::Empty);
    }
    if value.len() > MAX_STORAGE_KEY_LEN {
        return Err(StorageKeyError::TooLong);
    }
    if !value
        .bytes()
        .all(|byte| byte.is_ascii_alphanumeric() || matches!(byte, b'-' | b'_' | b'.' | b'/'))
    {
        return Err(StorageKeyError::InvalidCharacter);
    }
    if value
        .split('/')
        .any(|segment| segment.is_empty() || segment == "." || segment == "..")
    {
        return Err(StorageKeyError::InvalidSegment);
    }
    Ok(())
}

/// Failure to validate a [`StorageKey`].
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum StorageKeyError {
    /// Key text was empty.
    Empty,
    /// Key exceeded the maximum supported semantic key length.
    TooLong,
    /// Key contained a character outside the portable key alphabet.
    InvalidCharacter,
    /// Key contained an empty, current-directory, or parent-directory segment.
    InvalidSegment,
}

impl fmt::Display for StorageKeyError {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        formatter.write_str(match self {
            Self::Empty => "storage key is empty",
            Self::TooLong => "storage key is too long",
            Self::InvalidCharacter => "storage key contains an invalid character",
            Self::InvalidSegment => "storage key contains an invalid path segment",
        })
    }
}

impl std::error::Error for StorageKeyError {}

/// Stable category for storage failures across platform implementations.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum StorageErrorKind {
    /// The requested storage capability is unavailable.
    Unavailable,
    /// The platform denied access to storage.
    PermissionDenied,
    /// The platform reported an I/O or browser storage failure.
    Backend,
    /// Stored bytes could not be decoded by the adapter.
    Corrupt,
    /// Storage quota or an implementation limit was exceeded.
    Capacity,
}

/// Platform-neutral storage error with stable category and diagnostic context.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct StorageError {
    kind: StorageErrorKind,
    message: Box<str>,
}

impl StorageError {
    /// Construct a storage error.
    #[must_use]
    pub fn new(kind: StorageErrorKind, message: impl Into<Box<str>>) -> Self {
        Self {
            kind,
            message: message.into(),
        }
    }

    /// Return the stable failure category.
    #[must_use]
    pub const fn kind(&self) -> StorageErrorKind {
        self.kind
    }

    /// Return adapter-provided diagnostic context.
    #[must_use]
    pub fn message(&self) -> &str {
        &self.message
    }
}

impl fmt::Display for StorageError {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        formatter.write_str(&self.message)
    }
}

impl std::error::Error for StorageError {}

/// Persistent binary key/value capability.
///
/// The contract intentionally exposes neither filesystem paths nor browser
/// storage objects. Durability/transaction guarantees beyond one operation are
/// adapter-specific until a later persistence spec defines them.
pub trait BlobStorage {
    /// Read a value, returning `None` when the key does not exist.
    ///
    /// # Errors
    ///
    /// Returns [`StorageError`] when the active platform backend cannot service
    /// the read or stored bytes cannot be decoded by that adapter.
    fn get(&self, key: &StorageKey) -> Result<Option<Vec<u8>>, StorageError>;

    /// Replace the complete value stored at `key`.
    ///
    /// # Errors
    ///
    /// Returns [`StorageError`] when the platform denies the write, storage is
    /// unavailable/full, or the backend otherwise fails the operation.
    fn put(&self, key: &StorageKey, value: &[u8]) -> Result<(), StorageError>;

    /// Remove a value. Removing a missing key succeeds.
    ///
    /// # Errors
    ///
    /// Returns [`StorageError`] when the platform backend cannot perform the
    /// removal for reasons other than the key being absent.
    fn delete(&self, key: &StorageKey) -> Result<(), StorageError>;
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn accepts_portable_namespaced_keys() {
        let key = StorageKey::parse("plugins/example/config-v1.bin").expect("valid storage key");
        assert_eq!(key.as_str(), "plugins/example/config-v1.bin");
    }

    #[test]
    fn rejects_path_traversal_and_empty_segments() {
        assert_eq!(
            StorageKey::parse("plugins/../secret"),
            Err(StorageKeyError::InvalidSegment)
        );
        assert_eq!(
            StorageKey::parse("plugins//config"),
            Err(StorageKeyError::InvalidSegment)
        );
        assert_eq!(StorageKey::parse(""), Err(StorageKeyError::Empty));
    }

    #[test]
    fn rejects_non_portable_characters() {
        assert_eq!(
            StorageKey::parse("config\\windows"),
            Err(StorageKeyError::InvalidCharacter)
        );
        assert_eq!(
            StorageKey::parse("config?query"),
            Err(StorageKeyError::InvalidCharacter)
        );
    }
}
