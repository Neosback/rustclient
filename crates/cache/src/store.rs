use crate::{
    FileId, GroupId, IndexId, ReferenceTable, Source,
    container::{MAX_CONTAINER_BYTES, container_end, decode_container},
    error::Result,
    reader::Reader,
};
use rustclient_platform::{ReadOnlyBlobs, StorageKey};
use sha2::{Digest, Sha256};
use std::collections::{BTreeMap, BTreeSet};

/// Read-only validated JS5 cache. No GPU or filesystem ownership.
pub struct Cache<S> {
    source: S,
    config_warnings: std::sync::RwLock<BTreeMap<u32, Vec<String>>>,
    config_files: std::sync::RwLock<BTreeMap<u32, BTreeMap<FileId, Vec<u8>>>>,
    data: StorageKey,
    data_len: u64,
    tables: BTreeMap<IndexId, ReferenceTable>,
    fingerprint: String,
    reference_crcs: BTreeMap<IndexId, u32>,
}

impl<S: ReadOnlyBlobs> Cache<S> {
    /// Open all present reference tables and fingerprint their exact encoded bytes.
    ///
    /// # Errors
    /// Fails on missing core blobs or malformed present metadata.
    pub fn open(source: S) -> Result<Self> {
        let context = Source::group(IndexId(255), GroupId(0));
        let data = key("main_file_cache.dat2", context)?;
        let data_len = source
            .len(&data)
            .map_err(|error| context.error("storage", error.to_string()))?
            .ok_or_else(|| context.error("storage", "missing main_file_cache.dat2"))?;
        if data_len < 520 {
            return Err(context.error("storage", "data file lacks reserved sector"));
        }
        let reference = index_key(IndexId(255), context)?;
        let table_len = source
            .len(&reference)
            .map_err(|error| context.error("storage", error.to_string()))?
            .ok_or_else(|| context.error("storage", "missing main_file_cache.idx255"))?;
        if table_len % 6 != 0 || table_len > 255 * 6 {
            return Err(context.error("index", "invalid reference index length"));
        }
        let mut cache = Self {
            source,
            config_warnings: std::sync::RwLock::default(),
            config_files: std::sync::RwLock::default(),
            data,
            data_len,
            tables: BTreeMap::new(),
            fingerprint: String::new(),
            reference_crcs: BTreeMap::new(),
        };
        let mut digest = Sha256::new();
        digest.update(b"rustclient:js5-reference-metadata:v1");
        for index in 0..u8::try_from(table_len / 6)
            .map_err(|_| context.error("index", "too many indexes"))?
        {
            if let Some(raw) = cache.raw_group(IndexId(255), GroupId(u32::from(index)))? {
                let location = Source::group(IndexId(255), GroupId(u32::from(index)));
                digest.update([index]);
                digest.update((raw.len() as u64).to_be_bytes());
                digest.update(&raw);
                cache.reference_crcs.insert(
                    IndexId(index),
                    crc32fast::hash(&raw[..container_end(&raw, location)?]),
                );
                let table = ReferenceTable::decode(&decode_container(&raw, location)?, location)?;
                cache.tables.insert(IndexId(index), table);
            }
        }
        if cache.tables.is_empty() {
            return Err(context.error("index", "no reference tables"));
        }
        cache.fingerprint = format!("{:x}", digest.finalize());
        Ok(cache)
    }

    /// Metadata snapshot identity; does not hash every asset's contents.
    #[must_use]
    pub fn metadata_fingerprint(&self) -> &str {
        &self.fingerprint
    }

    /// Checksums of the encoded reference containers, excluding stored trailers.
    #[must_use]
    pub const fn reference_crcs(&self) -> &BTreeMap<IndexId, u32> {
        &self.reference_crcs
    }

    /// Available reference tables in index order.
    #[must_use]
    pub const fn tables(&self) -> &BTreeMap<IndexId, ReferenceTable> {
        &self.tables
    }

    pub(crate) fn config_group_with<R>(
        &self,
        group: u32,
        advisory: bool,
        warnings: &mut Vec<String>,
        f: impl FnOnce(&BTreeMap<FileId, Vec<u8>>) -> Result<R>,
    ) -> Result<R> {
        let loaded = self
            .config_files
            .read()
            .unwrap_or_else(std::sync::PoisonError::into_inner)
            .contains_key(&group);
        if !loaded {
            let mut group_warnings = Vec::new();
            let files = self
                .group_files_policy(IndexId(2), GroupId(group), advisory, &mut group_warnings)?
                .into_iter()
                .collect();
            self.config_warnings
                .write()
                .unwrap_or_else(std::sync::PoisonError::into_inner)
                .insert(group, group_warnings);
            self.config_files
                .write()
                .unwrap_or_else(std::sync::PoisonError::into_inner)
                .insert(group, files);
        }
        if let Some(group_warnings) = self
            .config_warnings
            .read()
            .unwrap_or_else(std::sync::PoisonError::into_inner)
            .get(&group)
        {
            warnings.extend(group_warnings.iter().cloned());
        }
        let borrowed = self
            .config_files
            .read()
            .unwrap_or_else(std::sync::PoisonError::into_inner);
        let files = borrowed
            .get(&group)
            .expect("config group inserted immediately above");
        f(files)
    }

    pub(crate) fn config_file(&self, group: u32, id: u32, advisory: bool) -> Result<Vec<u8>> {
        self.config_group_with(group, advisory, &mut Vec::new(), |files| {
            files.get(&FileId(id)).cloned().ok_or_else(|| {
                Source {
                    index: IndexId(2),
                    group: Some(GroupId(group)),
                    file: Some(FileId(id)),
                }
                .error("definition", "missing file")
            })
        })
    }
    fn raw_group(&self, index: IndexId, group: GroupId) -> Result<Option<Vec<u8>>> {
        let context = Source::group(index, group);
        let index_key = index_key(index, context)?;
        let Some(len) = self
            .source
            .len(&index_key)
            .map_err(|e| context.error("storage", e.to_string()))?
        else {
            return Ok(None);
        };
        if len % 6 != 0 {
            return Err(context.error("index", "partial index entry"));
        }
        let offset = u64::from(group.0) * 6;
        if offset + 6 > len {
            return Ok(None);
        }
        let mut entry = [0; 6];
        self.read(&index_key, offset, &mut entry, context)?;
        let mut r = Reader::new(&entry, context);
        let length =
            usize::try_from(r.u24()?).map_err(|_| context.error("index", "length overflow"))?;
        let mut sector = r.u24()?;
        if length == 0 {
            if sector != 0 {
                return Err(context.error("index", "zero length with nonzero sector"));
            }
            return Ok(None);
        }
        if length > MAX_CONTAINER_BYTES {
            return Err(context.error("index", "group length limit exceeded"));
        }
        let header = if group.0 > 65535 { 10 } else { 8 };
        let mut out = Vec::with_capacity(length);
        let mut seen = BTreeSet::new();
        let mut chunk = 0_u16;
        while out.len() < length {
            if sector == 0 || !seen.insert(sector) {
                return Err(context.error("sector", "zero or cyclic sector chain"));
            }
            let offset = u64::from(sector) * 520;
            let count = (length - out.len()).min(520 - header);
            if offset + (header + count) as u64 > self.data_len {
                return Err(context.error("sector", "sector outside data file"));
            }
            let mut bytes = [0; 520];
            self.read(&self.data, offset, &mut bytes[..header + count], context)?;
            let mut r = Reader::new(&bytes[..header], context);
            let found_group = if header == 10 {
                r.u32()?
            } else {
                u32::from(r.u16()?)
            };
            let found_chunk = r.u16()?;
            let next = r.u24()?;
            let found_index = r.u8()?;
            if found_group != group.0 || found_chunk != chunk || found_index != index.0 {
                return Err(context.error("sector", format!("header mismatch at sector {sector}")));
            }
            out.extend_from_slice(&bytes[header..header + count]);
            sector = next;
            chunk = chunk
                .checked_add(1)
                .ok_or_else(|| context.error("sector", "chunk overflow"))?;
        }
        if sector != 0 {
            return Err(context.error("sector", "nonterminal chain after advertised bytes"));
        }
        Ok(Some(out))
    }

    fn read(&self, key: &StorageKey, offset: u64, bytes: &mut [u8], context: Source) -> Result<()> {
        self.source
            .read_exact_at(key, offset, bytes)
            .map_err(|e| context.error("storage", e.to_string()))
    }

    /// Validate and decode a group, preserving file source addresses.
    ///
    /// # Errors
    /// Fails on absent groups, corrupt chains, CRC/version mismatch or malformed payloads.
    pub fn group_files(&self, index: IndexId, group: GroupId) -> Result<Vec<(FileId, Vec<u8>)>> {
        self.group_files_policy(index, group, false, &mut Vec::new())
    }

    /// Decode every frame of the sprite group whose name hash matches `name`.
    ///
    /// Map-scene tiles are one named group of many frames rather than one group per sprite.
    ///
    /// # Errors
    /// Fails on corrupted sprite payloads. An absent group yields no frames.
    pub fn named_sprites(&self, name: &str) -> Result<Vec<crate::Sprite>> {
        let Some(group) = self
            .tables
            .get(&IndexId(8))
            .and_then(|table| table.named_group(name))
            .map(|row| row.id)
        else {
            return Ok(Vec::new());
        };
        let Some((_, bytes)) = self.group_files(IndexId(8), group)?.into_iter().next() else {
            return Ok(Vec::new());
        };
        crate::sprite::decode_sprites(&bytes, Source::group(IndexId(8), group))
    }

    /// The per-byte chat code lengths of the `huffman` group in index 10, if the cache has one.
    ///
    /// # Errors
    /// Fails when the group's container cannot be read.
    pub fn huffman_sizes(&self) -> Result<Option<Vec<u8>>> {
        let Some(group) = self
            .tables
            .get(&IndexId(10))
            .and_then(|table| table.named_group("huffman"))
            .map(|row| row.id)
        else {
            return Ok(None);
        };
        Ok(self
            .group_files(IndexId(10), group)?
            .into_iter()
            .next()
            .map(|(_, bytes)| bytes.clone()))
    }

    /// Read and decode a single indexed sprite group from index 8.
    ///
    /// # Errors
    /// Fails on corrupted sprite payloads. Absent sprites return None.
    pub fn sprite(&self, id: u32) -> Result<Option<crate::Sprite>> {
        let Ok(files) = self.group_files(IndexId(8), GroupId(id)) else {
            return Ok(None);
        };
        let Some((_, bytes)) = files.into_iter().next() else {
            return Ok(None);
        };
        let source = Source::group(IndexId(8), GroupId(id));
        let sprites = crate::sprite::decode_sprites(&bytes, source)?;
        Ok(sprites.into_iter().next())
    }

    pub(crate) fn group_files_policy(
        &self,
        index: IndexId,
        group: GroupId,
        advisory_size: bool,
        warnings: &mut Vec<String>,
    ) -> Result<Vec<(FileId, Vec<u8>)>> {
        let context = Source::group(index, group);
        let row = self
            .tables
            .get(&index)
            .and_then(|t| t.groups.get(&group))
            .ok_or_else(|| context.error("lookup", "group absent from reference table"))?;
        let raw = self
            .raw_group(index, group)?
            .ok_or_else(|| context.error("lookup", "group payload is missing"))?;
        let end = container_end(&raw, context)?;
        if crc32fast::hash(&raw[..end]) != row.crc {
            return Err(context.error("integrity", "CRC mismatch"));
        }
        if raw.len() != end + 2 {
            return Err(context.error("integrity", "missing stored group version trailer"));
        }
        let trailer = u16::from_be_bytes([raw[end], raw[end + 1]]);
        if u32::from(trailer) != row.version & 0xffff {
            return Err(context.error("integrity", "version mismatch"));
        }
        let decoded = decode_container(&raw, context)?;
        // LIVE has publisher encoded-size hints that differ from its actual gzip
        // containers (e.g. logo: 12541 reported vs 13604 stored). Index/container
        // lengths and CRC validate the stored representation; never use that hint
        // to size reads or reject an otherwise valid recompressed container.
        if let Some((_, decoded_size)) = row.sizes
            && u64::from(decoded_size) != decoded.len() as u64
        {
            let message = format!(
                "reference decoded-size metadata mismatch: reported {decoded_size}, decoded {}",
                decoded.len()
            );
            if advisory_size {
                warnings.push(context.error("integrity hint", &message).to_string());
            } else {
                return Err(context.error("integrity", message));
            }
        }
        split_files(&decoded, &row.files, context)
    }

    /// Resolve a named group/file through its reference metadata, then validate it.
    ///
    /// # Errors
    /// Fails on absent names, missing file data or any underlying decode error.
    pub fn named_file(
        &self,
        index: IndexId,
        group_name: &str,
        file_name: &str,
    ) -> Result<(Source, Vec<u8>)> {
        let mut context = Source {
            index,
            group: None,
            file: None,
        };
        let row = self
            .tables
            .get(&index)
            .and_then(|t| t.named_group(group_name))
            .ok_or_else(|| context.error("lookup", format!("missing named group {group_name}")))?;
        context.group = Some(row.id);
        let hash = crate::name_hash(file_name);
        let position = row
            .file_names
            .as_ref()
            .and_then(|names| names.iter().position(|&v| v == hash))
            .ok_or_else(|| context.error("lookup", format!("missing named file {file_name:?}")))?;
        let id = row.files[position];
        context.file = Some(id);
        let bytes = self
            .group_files(index, row.id)
            .map_err(|mut error| {
                error.source.file = Some(id);
                error
            })?
            .into_iter()
            .find(|(file, _)| *file == id)
            .map(|(_, bytes)| bytes)
            .ok_or_else(|| context.error("lookup", "file absent after splitting"))?;
        Ok((context, bytes))
    }
}

fn key(name: &str, source: Source) -> Result<StorageKey> {
    StorageKey::parse(name).map_err(|error| source.error("key", error.to_string()))
}
fn index_key(index: IndexId, source: Source) -> Result<StorageKey> {
    key(&format!("main_file_cache.idx{}", index.0), source)
}

/// Split JS5 multi-file chunks, validating all offsets before file allocation.
///
/// # Errors
/// Rejects invalid chunk tables, negative sizes, overflow and payload inconsistencies.
pub fn split_files(bytes: &[u8], ids: &[FileId], source: Source) -> Result<Vec<(FileId, Vec<u8>)>> {
    if ids.is_empty() {
        return Err(source.error("file split", "group has no files"));
    }
    if ids.len() == 1 {
        return Ok(vec![(ids[0], bytes.to_vec())]);
    }
    let chunks = usize::from(
        *bytes
            .last()
            .ok_or_else(|| source.error("file split", "missing chunk count"))?,
    );
    if chunks == 0 {
        return Err(source.error("file split", "zero chunks"));
    }
    let table_size = chunks
        .checked_mul(ids.len())
        .and_then(|n| n.checked_mul(4))
        .ok_or_else(|| source.error("file split", "table overflow"))?;
    let start = bytes
        .len()
        .checked_sub(table_size + 1)
        .ok_or_else(|| source.error("file split", "truncated chunk table"))?;
    let mut r = Reader::new(&bytes[start..bytes.len() - 1], source);
    let mut sizes = vec![0_usize; ids.len()];
    let mut spans = Vec::with_capacity(chunks * ids.len());
    let mut offset = 0_usize;
    for _ in 0..chunks {
        let mut length = 0_i64;
        for size in &mut sizes {
            length += i64::from(i32::from_be_bytes(r.u32()?.to_be_bytes()));
            let n = usize::try_from(length)
                .map_err(|_| source.error("file split", "negative chunk length"))?;
            let end = offset
                .checked_add(n)
                .filter(|&end| end <= start)
                .ok_or_else(|| source.error("file split", "chunk outside payload"))?;
            *size = size
                .checked_add(n)
                .ok_or_else(|| source.error("file split", "file size overflow"))?;
            spans.push(offset..end);
            offset = end;
        }
    }
    if offset != start {
        return Err(source.error("file split", "chunk payload length mismatch"));
    }
    let mut output: Vec<_> = ids
        .iter()
        .zip(sizes)
        .map(|(&id, n)| (id, Vec::with_capacity(n)))
        .collect();
    for (i, span) in spans.into_iter().enumerate() {
        output[i % ids.len()].1.extend_from_slice(&bytes[span]);
    }
    Ok(output)
}
