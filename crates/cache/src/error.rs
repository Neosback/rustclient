use core::fmt;

/// JS5 archive/index namespace.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord)]
pub struct IndexId(pub u8);
/// Group identity within one index.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord)]
pub struct GroupId(pub u32);
/// File identity within one group.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord)]
pub struct FileId(pub u32);

/// Exact source location retained across decoding layers.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct Source {
    /// Source index.
    pub index: IndexId,
    /// Optional group.
    pub group: Option<GroupId>,
    /// Optional file.
    pub file: Option<FileId>,
}

impl Source {
    /// Describe a group before file splitting.
    #[must_use]
    pub const fn group(index: IndexId, group: GroupId) -> Self {
        Self {
            index,
            group: Some(group),
            file: None,
        }
    }

    pub(crate) fn error(self, stage: &'static str, detail: impl Into<String>) -> CacheError {
        CacheError {
            source: self,
            stage,
            detail: detail.into(),
        }
    }
}

/// Bounded decoder failure carrying archive/group/file and stage context.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct CacheError {
    /// Source address.
    pub source: Source,
    /// Decoding stage.
    pub stage: &'static str,
    /// Diagnostic without credentials or platform handles.
    pub detail: String,
}

impl fmt::Display for CacheError {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "index {}", self.source.index.0)?;
        if let Some(group) = self.source.group {
            write!(f, " / group {}", group.0)?;
        }
        if let Some(file) = self.source.file {
            write!(f, " / file {}", file.0)?;
        }
        write!(f, " [{}]: {}", self.stage, self.detail)
    }
}
impl std::error::Error for CacheError {}

pub(crate) type Result<T> = std::result::Result<T, CacheError>;
