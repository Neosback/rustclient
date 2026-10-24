//! Revision 240 database tables used by the `db_*` script opcodes.
//!
//! Rows live in config group 38, table schemas in config group 39 and the value indexes in
//! cache index 21 (archive = table, file 0 = every row, file `column + 1` = that column).
use crate::{Cache, CacheProfile, FileId, GroupId, IndexId, Source, error::Result, reader::Reader};
use rustclient_platform::ReadOnlyBlobs;
use std::collections::BTreeMap;

/// Script variable type id of `string`; the only text column type.
const TEXT_VAR_TYPE: u16 = 36;
/// Script variable type ids stored as 64-bit values.
const LONG_VAR_TYPES: [u16; 6] = [49, 56, 71, 110, 115, 116];
/// Script variable type ids with no base type.
const INVALID_VAR_TYPES: [u16; 1] = [50];
/// Highest script variable type id that is not reserved.
const MAX_INT_VAR_TYPE: u16 = 118;
/// A script variable type outside the contiguous range that still has an integer base.
const EXTRA_INT_VAR_TYPE: u16 = 209;

/// Storage class of a database value (the client's base variable type).
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum DbBase {
    Int,
    Long,
    Text,
}

impl DbBase {
    /// Base type of a script variable type id read from a row or table definition.
    fn from_var_type(id: u16) -> Option<Self> {
        if id == TEXT_VAR_TYPE {
            Some(Self::Text)
        } else if LONG_VAR_TYPES.contains(&id) {
            Some(Self::Long)
        } else if INVALID_VAR_TYPES.contains(&id)
            || !(id <= MAX_INT_VAR_TYPE || id == EXTRA_INT_VAR_TYPE)
        {
            None
        } else {
            Some(Self::Int)
        }
    }
    /// Base type ordinal as encoded by index files and the script type operand.
    #[must_use]
    pub const fn from_ordinal(ordinal: i32) -> Option<Self> {
        match ordinal {
            0 => Some(Self::Int),
            1 => Some(Self::Long),
            2 => Some(Self::Text),
            _ => None,
        }
    }
}

/// One tuple element of a column.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct DbColumnType {
    pub var_type: u16,
    pub base: DbBase,
}

impl DbColumnType {
    /// Value read for a missing column: booleans and plain integers default to 0, others to -1.
    #[must_use]
    pub const fn default_int(self) -> i32 {
        if self.var_type <= 1 { 0 } else { -1 }
    }
}

#[derive(Debug, Clone, PartialEq)]
pub enum DbValue {
    Int(i32),
    Long(i64),
    Text(String),
}

/// A lookup key; ordered so indexes are deterministic.
#[derive(Debug, Clone, PartialEq, Eq, PartialOrd, Ord)]
pub enum DbKey {
    Int(i32),
    Long(i64),
    Text(String),
}

/// A column's flattened values: `fields * tuple` entries, tuple element varying fastest.
pub type DbColumnValues = Vec<DbValue>;

/// Column types plus optional per-column defaults of a table (config group 39).
#[derive(Debug, Clone, PartialEq, Default)]
pub struct DbTable {
    pub columns: Vec<Option<Vec<DbColumnType>>>,
    pub defaults: Vec<Option<DbColumnValues>>,
}

/// One row (config group 38).
#[derive(Debug, Clone, PartialEq, Default)]
pub struct DbRow {
    pub table: Option<u32>,
    pub types: Vec<Option<Vec<DbColumnType>>>,
    pub values: Vec<Option<DbColumnValues>>,
}

/// Row ids by value for every tuple element of one column (or of the whole table).
#[derive(Debug, Clone, PartialEq, Default)]
pub struct DbIndex {
    pub tuples: Vec<BTreeMap<DbKey, Vec<u32>>>,
}

impl DbIndex {
    /// Row ids whose `tuple` element equals `key`; negative tuple indexes select element 0.
    #[must_use]
    pub fn find(&self, key: &DbKey, tuple: i32) -> Option<&[u32]> {
        let tuple = usize::try_from(tuple.max(0)).ok()?;
        self.tuples
            .get(tuple)
            .and_then(|rows| rows.get(key))
            .map(Vec::as_slice)
    }
}

fn var_int(r: &mut Reader<'_>, source: Source) -> Result<u32> {
    let mut value = 0_u32;
    for shift in (0..35).step_by(7) {
        let byte = r.u8()?;
        value |= u32::from(byte & 0x7F)
            .checked_shl(shift)
            .ok_or_else(|| source.error("database", "variable integer overflow"))?;
        if byte < 128 {
            return Ok(value);
        }
    }
    Err(source.error("database", "variable integer overflow"))
}

fn column_types(r: &mut Reader<'_>, source: Source) -> Result<Vec<DbColumnType>> {
    let count = usize::from(r.u8()?);
    (0..count)
        .map(|_| {
            let var_type = r.u_smart()?;
            let base = DbBase::from_var_type(var_type)
                .ok_or_else(|| source.error("database", format!("column type {var_type}")))?;
            Ok(DbColumnType { var_type, base })
        })
        .collect()
}

fn column_values(
    r: &mut Reader<'_>,
    types: &[DbColumnType],
    source: Source,
) -> Result<DbColumnValues> {
    let fields = usize::from(r.u_smart()?);
    let total = fields
        .checked_mul(types.len())
        .filter(|total| *total <= r.remaining())
        .ok_or_else(|| source.error("database", "column value count exceeds payload"))?;
    let mut values = Vec::with_capacity(total);
    for _ in 0..fields {
        for kind in types {
            values.push(match kind.base {
                DbBase::Int => DbValue::Int(r.u32()?.cast_signed()),
                DbBase::Long => DbValue::Long(i64::from_be_bytes(
                    r.take(8)?
                        .try_into()
                        .map_err(|_| source.error("database", "long value"))?,
                )),
                DbBase::Text => DbValue::Text(r.string_value()?),
            });
        }
    }
    Ok(values)
}

/// Decode a `DBROW` definition.
/// # Errors
/// Rejects unknown fields, column types, out-of-range columns and truncated payloads.
pub fn decode_row(bytes: &[u8], source: Source) -> Result<DbRow> {
    let mut r = Reader::new(bytes, source);
    let mut row = DbRow::default();
    loop {
        match r.u8()? {
            0 => break,
            3 => {
                let count = usize::from(r.u8()?);
                row.types = vec![None; count];
                row.values = vec![None; count];
                loop {
                    let column = usize::from(r.u8()?);
                    if column == 255 {
                        break;
                    }
                    if column >= count {
                        return Err(source.error("database row", "column out of range"));
                    }
                    let types = column_types(&mut r, source)?;
                    row.values[column] = Some(column_values(&mut r, &types, source)?);
                    row.types[column] = Some(types);
                }
            }
            4 => row.table = Some(var_int(&mut r, source)?),
            op => return Err(source.error("database row", format!("unsupported field {op}"))),
        }
    }
    r.finish()?;
    Ok(row)
}

/// Decode a `DBTABLE` definition.
/// # Errors
/// Rejects unknown fields, column types, out-of-range columns and truncated payloads.
pub fn decode_table(bytes: &[u8], source: Source) -> Result<DbTable> {
    let mut r = Reader::new(bytes, source);
    let mut table = DbTable::default();
    loop {
        match r.u8()? {
            0 => break,
            1 => {
                let count = usize::from(r.u8()?);
                table.columns = vec![None; count];
                table.defaults = vec![None; count];
                loop {
                    let setting = r.u8()?;
                    if setting == 255 {
                        break;
                    }
                    let column = usize::from(setting & 0x7F);
                    if column >= count {
                        return Err(source.error("database table", "column out of range"));
                    }
                    let types = column_types(&mut r, source)?;
                    if setting & 0x80 != 0 {
                        table.defaults[column] = Some(column_values(&mut r, &types, source)?);
                    }
                    table.columns[column] = Some(types);
                }
            }
            op => return Err(source.error("database table", format!("unsupported field {op}"))),
        }
    }
    r.finish()?;
    Ok(table)
}

/// Decode one `DBTABLEINDEX` file.
/// # Errors
/// Rejects unknown base types, counts exceeding the payload and truncated payloads.
pub fn decode_index(bytes: &[u8], source: Source) -> Result<DbIndex> {
    let mut r = Reader::new(bytes, source);
    let bounded = |count: u32, r: &Reader<'_>| {
        usize::try_from(count)
            .ok()
            .filter(|count| *count <= r.remaining())
            .ok_or_else(|| source.error("database index", "count exceeds payload"))
    };
    let tuple_count = var_int(&mut r, source)?;
    let tuple_count = bounded(tuple_count, &r)?;
    let mut tuples = Vec::with_capacity(tuple_count);
    for _ in 0..tuple_count {
        let ordinal = i32::from(r.u8()?);
        let base = DbBase::from_ordinal(ordinal)
            .ok_or_else(|| source.error("database index", format!("base type {ordinal}")))?;
        let value_count = var_int(&mut r, source)?;
        let value_count = bounded(value_count, &r)?;
        let mut rows = BTreeMap::new();
        for _ in 0..value_count {
            let key = match base {
                DbBase::Int => DbKey::Int(r.u32()?.cast_signed()),
                DbBase::Long => DbKey::Long(i64::from_be_bytes(
                    r.take(8)?
                        .try_into()
                        .map_err(|_| source.error("database index", "long key"))?,
                )),
                DbBase::Text => DbKey::Text(r.string_value()?),
            };
            let row_count = var_int(&mut r, source)?;
            let row_count = bounded(row_count, &r)?;
            let mut ids = Vec::with_capacity(row_count);
            for _ in 0..row_count {
                ids.push(var_int(&mut r, source)?);
            }
            rows.insert(key, ids);
        }
        tuples.push(rows);
    }
    r.finish()?;
    Ok(DbIndex { tuples })
}

impl CacheProfile {
    /// # Errors
    /// Rejects missing or malformed row definitions.
    pub fn load_db_row<S: ReadOnlyBlobs>(self, cache: &Cache<S>, id: u32) -> Result<DbRow> {
        let source = Source {
            index: IndexId(2),
            group: Some(GroupId(38)),
            file: Some(FileId(id)),
        };
        decode_row(&cache.config_file(38, id, false)?, source)
    }
    /// # Errors
    /// Rejects missing or malformed table definitions.
    pub fn load_db_table<S: ReadOnlyBlobs>(self, cache: &Cache<S>, id: u32) -> Result<DbTable> {
        let source = Source {
            index: IndexId(2),
            group: Some(GroupId(39)),
            file: Some(FileId(id)),
        };
        decode_table(&cache.config_file(39, id, false)?, source)
    }
    /// Load the value index of one table column, or of every row when `column` is `None`.
    /// # Errors
    /// Rejects tables or columns that have no index, and malformed index files.
    pub fn load_db_index<S: ReadOnlyBlobs>(
        self,
        cache: &Cache<S>,
        table: u32,
        column: Option<u8>,
    ) -> Result<DbIndex> {
        let file = column.map_or(0, |column| u32::from(column) + 1);
        let source = Source {
            index: IndexId(21),
            group: Some(GroupId(table)),
            file: Some(FileId(file)),
        };
        let files = cache.group_files(IndexId(21), GroupId(table))?;
        let (_, bytes) = files
            .iter()
            .find(|(id, _)| id.0 == file)
            .ok_or_else(|| source.error("database index", "missing file"))?;
        decode_index(bytes, source)
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    fn source() -> Source {
        Source::group(IndexId(2), GroupId(38))
    }

    #[test]
    fn var_types_map_to_reference_base_types() {
        assert_eq!(DbBase::from_var_type(0), Some(DbBase::Int));
        assert_eq!(DbBase::from_var_type(36), Some(DbBase::Text));
        assert_eq!(DbBase::from_var_type(49), Some(DbBase::Long));
        assert_eq!(DbBase::from_var_type(209), Some(DbBase::Int));
        assert_eq!(DbBase::from_var_type(50), None);
        assert_eq!(DbBase::from_var_type(119), None);
        assert_eq!(DbBase::from_var_type(210), None);
    }

    #[test]
    fn decodes_rows_with_integer_and_text_tuples() {
        // table 300 (var-int 0xAC 0x02); column 1 holds one (int, string) field.
        let mut bytes = vec![4, 0xAC, 0x02, 3, 2, 1, 2, 0, 36, 1];
        bytes.extend_from_slice(&7_i32.to_be_bytes());
        bytes.extend_from_slice(b"hi\0");
        bytes.extend_from_slice(&[255, 0]);
        let row = decode_row(&bytes, source()).unwrap();
        assert_eq!(row.table, Some(300));
        assert!(row.values[0].is_none());
        assert_eq!(
            row.values[1],
            Some(vec![DbValue::Int(7), DbValue::Text("hi".into())])
        );
        assert_eq!(row.types[1].as_ref().unwrap()[1].base, DbBase::Text);
    }

    #[test]
    fn rejects_truncated_and_out_of_range_rows() {
        assert!(decode_row(&[3, 1, 5, 0], source()).is_err());
        assert!(decode_row(&[3, 1, 0, 1, 0, 255, 0], source()).is_err());
        assert!(decode_row(&[9, 0], source()).is_err());
    }

    #[test]
    fn decodes_tables_with_defaults_and_unset_columns() {
        let mut bytes = vec![1, 3, 0x80 | 2, 1, 0, 1];
        bytes.extend_from_slice(&(-1_i32).to_be_bytes());
        bytes.extend_from_slice(&[0, 1, 36, 255, 0]);
        let table = decode_table(&bytes, source()).unwrap();
        assert_eq!(table.columns.len(), 3);
        assert!(table.columns[1].is_none());
        assert_eq!(table.defaults[2], Some(vec![DbValue::Int(-1)]));
        assert_eq!(table.columns[0].as_ref().unwrap()[0].base, DbBase::Text);
        assert_eq!(table.columns[2].as_ref().unwrap()[0].default_int(), 0);
    }

    #[test]
    fn decodes_indexes_and_selects_tuple_elements() {
        // One tuple: int keys 5 -> rows [2, 130] and 9 -> [4].
        let mut bytes = vec![1, 0, 2];
        bytes.extend_from_slice(&5_i32.to_be_bytes());
        bytes.extend_from_slice(&[2, 2, 0x82, 0x01]);
        bytes.extend_from_slice(&9_i32.to_be_bytes());
        bytes.extend_from_slice(&[1, 4]);
        let index = decode_index(&bytes, source()).unwrap();
        assert_eq!(index.find(&DbKey::Int(5), 0), Some(&[2, 130][..]));
        assert_eq!(index.find(&DbKey::Int(5), -1), Some(&[2, 130][..]));
        assert_eq!(index.find(&DbKey::Int(9), 0), Some(&[4][..]));
        assert_eq!(index.find(&DbKey::Int(6), 0), None);
        assert_eq!(index.find(&DbKey::Int(5), 1), None);
        assert_eq!(index.find(&DbKey::Text("5".into()), 0), None);
    }

    #[test]
    fn rejects_index_counts_beyond_payload() {
        assert!(decode_index(&[1, 0, 0x7F], source()).is_err());
        assert!(decode_index(&[1, 7, 0], source()).is_err());
    }
}
