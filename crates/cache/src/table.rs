use crate::{FileId, GroupId, Source, error::Result, reader::Reader};
use std::collections::BTreeMap;

const MAX_ENTRIES: usize = 262_144;

/// One reference-table row, preserving integrity and optional metadata.
#[derive(Debug, Clone)]
pub struct Group {
    /// Source group.
    pub id: GroupId,
    /// Optional case-insensitive name hash.
    pub name_hash: Option<u32>,
    /// Container CRC, excluding the version trailer.
    pub crc: u32,
    /// Full reference-table version (stored trailer uses low 16 bits).
    pub version: u32,
    /// Optional hash column.
    pub hash: Option<u32>,
    /// Optional Whirlpool digest.
    pub whirlpool: Option<[u8; 64]>,
    /// Optional compressed/uncompressed size metadata.
    pub sizes: Option<(u32, u32)>,
    /// Ordered file IDs.
    pub files: Vec<FileId>,
    /// Optional file-name hashes, in file order.
    pub file_names: Option<Vec<u32>>,
}

/// Parsed index metadata. Table revision is not the client revision.
#[derive(Debug, Clone)]
pub struct ReferenceTable {
    /// Binary table protocol, 5 through 7.
    pub protocol: u8,
    /// Cache publisher's table revision.
    pub revision: Option<u32>,
    /// Metadata flags.
    pub flags: u8,
    /// Sparse ordered group rows.
    pub groups: BTreeMap<GroupId, Group>,
}

impl ReferenceTable {
    /// Parse a decompressed table with bounded entry counts.
    ///
    /// # Errors
    /// Rejects unsupported protocols/flags, duplicate IDs, truncation and trailing bytes.
    pub fn decode(bytes: &[u8], source: Source) -> Result<Self> {
        let mut r = Reader::new(bytes, source);
        let protocol = r.u8()?;
        if !(5..=7).contains(&protocol) {
            return Err(source.error("reference table", "unsupported protocol"));
        }
        let revision = if protocol >= 6 { Some(r.u32()?) } else { None };
        let flags = r.u8()?;
        if flags & !15 != 0 {
            return Err(source.error("reference table", "unsupported metadata flags"));
        }
        let count = bounded_count(&mut r, protocol, source)?;
        let ids = delta_ids(&mut r, protocol, count, source)?;
        let mut rows = Vec::with_capacity(count);
        for id in ids {
            rows.push(Group {
                id: GroupId(id),
                name_hash: None,
                crc: 0,
                version: 0,
                hash: None,
                whirlpool: None,
                sizes: None,
                files: Vec::new(),
                file_names: None,
            });
        }
        if flags & 1 != 0 {
            for row in &mut rows {
                row.name_hash = Some(r.u32()?);
            }
        }
        for row in &mut rows {
            row.crc = r.u32()?;
        }
        if flags & 8 != 0 {
            for row in &mut rows {
                row.hash = Some(r.u32()?);
            }
        }
        if flags & 2 != 0 {
            for row in &mut rows {
                let mut digest = [0; 64];
                digest.copy_from_slice(r.take(64)?);
                row.whirlpool = Some(digest);
            }
        }
        if flags & 4 != 0 {
            for row in &mut rows {
                row.sizes = Some((r.u32()?, r.u32()?));
            }
        }
        for row in &mut rows {
            row.version = r.u32()?;
        }
        let mut counts = Vec::with_capacity(count);
        let mut total = 0;
        for _ in 0..count {
            let n = bounded_count(&mut r, protocol, source)?;
            total += n;
            if n == 0 || total > MAX_ENTRIES {
                return Err(
                    source.error("reference table", "invalid or excessive total file count")
                );
            }
            counts.push(n);
        }
        for (row, n) in rows.iter_mut().zip(counts) {
            row.files = delta_ids(&mut r, protocol, n, source)?
                .into_iter()
                .map(FileId)
                .collect();
        }
        if flags & 1 != 0 {
            for row in &mut rows {
                row.file_names = Some(
                    (0..row.files.len())
                        .map(|_| r.u32())
                        .collect::<Result<_>>()?,
                );
            }
        }
        r.finish()?;
        Ok(Self {
            protocol,
            revision,
            flags,
            groups: rows.into_iter().map(|row| (row.id, row)).collect(),
        })
    }

    /// Resolve a group name using the JS5 CP1252 name hash.
    #[must_use]
    pub fn named_group(&self, name: &str) -> Option<&Group> {
        let hash = name_hash(name);
        self.groups.values().find(|row| row.name_hash == Some(hash))
    }
}

fn bounded_count(r: &mut Reader<'_>, protocol: u8, source: Source) -> Result<usize> {
    let n = usize::try_from(r.count(protocol)?)
        .map_err(|_| source.error("reference table", "count overflow"))?;
    if n > MAX_ENTRIES {
        return Err(source.error("reference table", "entry count limit exceeded"));
    }
    Ok(n)
}
fn delta_ids(r: &mut Reader<'_>, protocol: u8, count: usize, source: Source) -> Result<Vec<u32>> {
    let mut ids = Vec::with_capacity(count);
    let mut id = 0_u32;
    for index in 0..count {
        let delta = r.count(protocol)?;
        if index > 0 && delta == 0 {
            return Err(source.error("reference table", "duplicate ID"));
        }
        id = id
            .checked_add(delta)
            .ok_or_else(|| source.error("reference table", "ID overflow"))?;
        ids.push(id);
    }
    Ok(ids)
}

/// Case-insensitive Java/JS5 string hash over signed CP1252 bytes.
#[must_use]
pub fn name_hash(name: &str) -> u32 {
    name.to_lowercase().chars().fold(0_u32, |hash, ch| {
        let byte = cp1252(ch).unwrap_or(b'?');
        let signed = i32::from(i8::from_ne_bytes([byte]));
        hash.wrapping_mul(31)
            .wrapping_add(u32::from_ne_bytes(signed.to_ne_bytes()))
    })
}

/// Encode one character in the cache font/name character set.
#[must_use]
pub fn cp1252(ch: char) -> Option<u8> {
    const EXTRA: &str = "€\u{81}‚ƒ„…†‡ˆ‰Š‹Œ\u{8d}Ž\u{8f}\u{90}‘’“”•–—˜™š›œ\u{9d}žŸ";
    let code = u32::from(ch);
    if code < 128 || (160..=255).contains(&code) {
        return u8::try_from(code).ok();
    }
    EXTRA
        .chars()
        .position(|c| c == ch)
        .and_then(|i| u8::try_from(i + 128).ok())
}
