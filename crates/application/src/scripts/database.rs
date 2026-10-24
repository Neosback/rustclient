//! `db_*` script opcodes over the cache's database tables.
//!
//! The cache tables are immutable evidence; the only mutable state is the query cursor, owned
//! by `ClientScripts` and cleared with it so no result survives a session.
use super::{ClientScripts, Result, ScriptHost};
use rustclient_cache::{Cache, CacheProfile};
use rustclient_cache::{
    osrs240::db_tables::{DbBase, DbColumnType, DbIndex, DbKey, DbRow, DbTable, DbValue},
    script::Opcode,
};
use rustclient_client_script::Stacks;
use rustclient_model::ScriptValue;
use rustclient_platform::ReadOnlyBlobs;
use std::{collections::BTreeMap, sync::Arc};

/// Cache-derived definitions plus the active query result.
#[derive(Default)]
pub(super) struct DbState {
    store: DbStore,
    cursor: Cursor,
}

#[derive(Default)]
struct DbStore {
    rows: BTreeMap<u32, Arc<DbRow>>,
    tables: BTreeMap<u32, Arc<DbTable>>,
    indexes: BTreeMap<(u32, Option<u8>), Arc<DbIndex>>,
}

/// The rows matched by the last find and the position of `db_findnext`.
#[derive(Default)]
struct Cursor {
    table: Option<u32>,
    rows: Option<Vec<u32>>,
    next: usize,
}

impl DbState {
    pub(super) fn reset(&mut self) {
        self.cursor = Cursor::default();
    }
}

/// Where definitions come from: the cache in production, fixtures in tests.
trait DbSource {
    fn row(&mut self, id: u32) -> Result<Arc<DbRow>>;
    fn table(&mut self, id: u32) -> Result<Arc<DbTable>>;
    fn index(&mut self, table: u32, column: Option<u8>) -> Result<Arc<DbIndex>>;
}

struct CacheSource<'a, S> {
    cache: &'a Cache<S>,
    profile: CacheProfile,
    store: &'a mut DbStore,
}

impl<S: ReadOnlyBlobs> DbSource for CacheSource<'_, S> {
    fn row(&mut self, id: u32) -> Result<Arc<DbRow>> {
        if let Some(row) = self.store.rows.get(&id) {
            return Ok(row.clone());
        }
        let row = Arc::new(
            self.profile
                .load_db_row(self.cache, id)
                .map_err(|e| e.to_string())?,
        );
        self.store.rows.insert(id, row.clone());
        Ok(row)
    }
    fn table(&mut self, id: u32) -> Result<Arc<DbTable>> {
        if let Some(table) = self.store.tables.get(&id) {
            return Ok(table.clone());
        }
        let table = Arc::new(
            self.profile
                .load_db_table(self.cache, id)
                .map_err(|e| e.to_string())?,
        );
        self.store.tables.insert(id, table.clone());
        Ok(table)
    }
    fn index(&mut self, table: u32, column: Option<u8>) -> Result<Arc<DbIndex>> {
        if let Some(index) = self.store.indexes.get(&(table, column)) {
            return Ok(index.clone());
        }
        let index = Arc::new(
            self.profile
                .load_db_index(self.cache, table, column)
                .map_err(|e| e.to_string())?,
        );
        self.store.indexes.insert((table, column), index.clone());
        Ok(index)
    }
}

/// A packed column reference: table, column and tuple element (-1 selects every element).
#[derive(Clone, Copy)]
struct ColumnRef {
    table: u32,
    column: u8,
    tuple: i32,
}

impl ColumnRef {
    const fn unpack(packed: i32) -> Self {
        let packed = packed.cast_unsigned();
        Self {
            table: packed >> 12,
            column: (packed >> 4).to_le_bytes()[0],
            tuple: (packed & 0xF).cast_signed() - 1,
        }
    }
}

pub(super) fn instruction<S: ReadOnlyBlobs>(
    h: &mut ScriptHost<'_, S>,
    op: Opcode,
    s: &mut Stacks,
) -> Result<bool> {
    if !is_database_opcode(op) {
        return Ok(false);
    }
    let ClientScripts {
        cache, profile, db, ..
    } = &mut *h.resources;
    let mut source = CacheSource {
        cache,
        profile: *profile,
        store: &mut db.store,
    };
    execute(&mut source, &mut db.cursor, op, s)?;
    Ok(true)
}

const fn is_database_opcode(op: Opcode) -> bool {
    matches!(
        op,
        Opcode::DbFind
            | Opcode::DbFindWithCount
            | Opcode::DbFindall
            | Opcode::DbFindallWithCount
            | Opcode::DbFindFilter
            | Opcode::DbFindFilterWithCount
            | Opcode::DbFindnext
            | Opcode::DbGetrow
            | Opcode::DbGetrowtable
            | Opcode::DbGetfield
            | Opcode::DbGetfieldcount
    )
}

fn execute(
    source: &mut impl DbSource,
    cursor: &mut Cursor,
    op: Opcode,
    s: &mut Stacks,
) -> Result<()> {
    match op {
        Opcode::DbFind | Opcode::DbFindWithCount => {
            let key = pop_key(s)?;
            let column = ColumnRef::unpack(s.int()?);
            let index = source.index(column.table, Some(column.column))?;
            let rows = key
                .as_ref()
                .and_then(|key| index.find(key, column.tuple))
                .map(<[u32]>::to_vec);
            let count = set_cursor(cursor, column.table, rows);
            push_count(s, op == Opcode::DbFindWithCount, count)?;
        }
        Opcode::DbFindall | Opcode::DbFindallWithCount => {
            let table = u32::try_from(s.int()?).map_err(|_| "negative database table")?;
            let index = source.index(table, None)?;
            let rows = index.find(&DbKey::Int(0), 0).map(<[u32]>::to_vec);
            let count = set_cursor(cursor, table, rows);
            push_count(s, op == Opcode::DbFindallWithCount, count)?;
        }
        Opcode::DbFindFilter | Opcode::DbFindFilterWithCount => {
            let key = pop_key(s)?;
            let column = ColumnRef::unpack(s.int()?);
            let index = source.index(column.table, Some(column.column))?;
            if cursor.table != Some(column.table) {
                return Err("database filter table differs from the active result".into());
            }
            let Some(active) = cursor.rows.as_deref() else {
                return Err("database filter without an active result".into());
            };
            let matched = key.as_ref().and_then(|key| index.find(key, column.tuple));
            let kept: Vec<u32> = matched.map_or_else(Vec::new, |matched| {
                active
                    .iter()
                    .copied()
                    .filter(|row| matched.contains(row))
                    .collect()
            });
            let count = kept.len();
            cursor.rows = Some(kept);
            cursor.next = 0;
            push_count(s, op == Opcode::DbFindFilterWithCount, count)?;
        }
        Opcode::DbFindnext => {
            let next = cursor
                .rows
                .as_deref()
                .and_then(|rows| rows.get(cursor.next))
                .copied();
            if next.is_some() {
                cursor.next += 1;
            }
            s.integers.push(row_number(next)?);
        }
        Opcode::DbGetrow => {
            let index = s.int()?;
            let row = usize::try_from(index).ok().and_then(|index| {
                cursor
                    .rows
                    .as_deref()
                    .and_then(|rows| rows.get(index))
                    .copied()
            });
            s.integers.push(row_number(row)?);
        }
        Opcode::DbGetrowtable => {
            let row = source.row(row_id(s.int()?)?)?;
            s.integers.push(match row.table {
                Some(table) => i32::try_from(table).map_err(|_| "database table out of range")?,
                None => -1,
            });
        }
        Opcode::DbGetfield => {
            let [row, packed, field] = s.ints()?;
            get_field(source, s, row_id(row)?, ColumnRef::unpack(packed), field)?;
        }
        Opcode::DbGetfieldcount => {
            let [row, packed] = s.ints()?;
            let column = ColumnRef::unpack(packed);
            let row = source.row(row_id(row)?)?;
            let table = source.table(column.table)?;
            let types = column_types(&table, column)?;
            let count = column_values(&row, &table, column)
                .map_or(0, |values| values.len() / types.len().max(1));
            s.integers
                .push(i32::try_from(count).map_err(|_| "database field count out of range")?);
        }
        _ => return Err(format!("not a database opcode {op:?}")),
    }
    Ok(())
}

fn row_id(row: i32) -> Result<u32> {
    u32::try_from(row).map_err(|_| "negative database row".into())
}

/// Pop `[value][base type]`; a type of -1 carries no value and matches nothing.
fn pop_key(s: &mut Stacks) -> Result<Option<DbKey>> {
    let ordinal = s.int()?;
    if ordinal == -1 {
        return Ok(None);
    }
    match DbBase::from_ordinal(ordinal) {
        Some(DbBase::Int) => Ok(Some(DbKey::Int(s.int()?))),
        Some(DbBase::Text) => Ok(Some(DbKey::Text(s.text()?))),
        _ => Err(format!("unsupported database value type {ordinal}")),
    }
}

fn set_cursor(cursor: &mut Cursor, table: u32, rows: Option<Vec<u32>>) -> usize {
    let count = rows.as_ref().map_or(0, Vec::len);
    cursor.table = rows.is_some().then_some(table);
    cursor.rows = rows;
    cursor.next = 0;
    count
}

fn push_count(s: &mut Stacks, wanted: bool, count: usize) -> Result<()> {
    if wanted {
        s.integers
            .push(i32::try_from(count).map_err(|_| "database result count out of range")?);
    }
    Ok(())
}

fn row_number(row: Option<u32>) -> Result<i32> {
    row.map_or(Ok(-1), |row| {
        i32::try_from(row).map_err(|_| "database row out of range".into())
    })
}

fn column_types(table: &DbTable, column: ColumnRef) -> Result<&[DbColumnType]> {
    table
        .columns
        .get(usize::from(column.column))
        .and_then(Option::as_deref)
        .ok_or_else(|| {
            format!(
                "database table {} has no column {}",
                column.table, column.column
            )
        })
}

/// The row's own values for a column, else the table default.
fn column_values<'a>(
    row: &'a DbRow,
    table: &'a DbTable,
    column: ColumnRef,
) -> Option<&'a [DbValue]> {
    let column = usize::from(column.column);
    row.values
        .get(column)
        .and_then(Option::as_deref)
        .or_else(|| table.defaults.get(column).and_then(Option::as_deref))
}

fn get_field(
    source: &mut impl DbSource,
    s: &mut Stacks,
    row: u32,
    column: ColumnRef,
    field: i32,
) -> Result<()> {
    let row = source.row(row)?;
    let table = source.table(column.table)?;
    let types = column_types(&table, column)?;
    let width = types.len();
    let (first, end) = match usize::try_from(column.tuple) {
        Ok(tuple) if tuple >= width => {
            return Err(format!(
                "Tuple index out-of-bounds. Requested: {tuple}, Max: {width}"
            ));
        }
        Ok(tuple) => (tuple, tuple + 1),
        Err(_) => (0, width),
    };
    let Some(values) = column_values(&row, &table, column) else {
        for kind in &types[first..end] {
            match kind.base {
                DbBase::Text => s.push_object(ScriptValue::Text(String::new())),
                DbBase::Int => s.integers.push(kind.default_int()),
                DbBase::Long => return Err("unsupported database long column".into()),
            }
        }
        return Ok(());
    };
    let field = usize::try_from(field)
        .ok()
        .filter(|field| field * width < values.len())
        .ok_or("database field index out of range")?;
    for element in first..end {
        match &values[field * width + element] {
            DbValue::Int(value) => s.integers.push(*value),
            DbValue::Text(value) => s.push_object(ScriptValue::Text(value.clone())),
            DbValue::Long(_) => return Err("unsupported database long column".into()),
        }
    }
    Ok(())
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_client_script::Object;

    const TABLE: u32 = 5;

    fn packed(table: u32, column: u8, tuple: i32) -> i32 {
        ((table << 12) | (u32::from(column) << 4) | (tuple + 1).cast_unsigned()).cast_signed()
    }
    fn kind(var_type: u16, base: DbBase) -> DbColumnType {
        DbColumnType { var_type, base }
    }

    /// Table 5: column 0 integers, column 1 (text, int) tuples, column 2 defaulted to 7.
    struct Fixture {
        rows: BTreeMap<u32, Arc<DbRow>>,
        table: Arc<DbTable>,
        indexes: BTreeMap<(u32, Option<u8>), Arc<DbIndex>>,
    }

    impl Fixture {
        fn new() -> Self {
            let int = kind(0, DbBase::Int);
            let table = DbTable {
                columns: vec![
                    Some(vec![int]),
                    Some(vec![kind(36, DbBase::Text), kind(1, DbBase::Int)]),
                    Some(vec![kind(5, DbBase::Int)]),
                ],
                defaults: vec![None, None, Some(vec![DbValue::Int(7)])],
            };
            let row = |table, values: Vec<Option<Vec<DbValue>>>| {
                Arc::new(DbRow {
                    table,
                    types: table_types(),
                    values,
                })
            };
            let text = |s: &str| DbValue::Text(s.into());
            let rows = BTreeMap::from([
                (
                    10,
                    row(
                        Some(TABLE),
                        vec![
                            Some(vec![DbValue::Int(1), DbValue::Int(2)]),
                            Some(vec![text("a"), DbValue::Int(3)]),
                            None,
                        ],
                    ),
                ),
                (
                    11,
                    row(Some(TABLE), vec![Some(vec![DbValue::Int(1)]), None, None]),
                ),
                (12, row(None, vec![Some(vec![DbValue::Int(9)]), None, None])),
            ]);
            let index = |tuples: Vec<Vec<(DbKey, Vec<u32>)>>| {
                Arc::new(DbIndex {
                    tuples: tuples
                        .into_iter()
                        .map(|t| t.into_iter().collect())
                        .collect(),
                })
            };
            let indexes = BTreeMap::from([
                (
                    (TABLE, Some(0)),
                    index(vec![vec![
                        (DbKey::Int(1), vec![10, 11]),
                        (DbKey::Int(9), vec![12]),
                    ]]),
                ),
                (
                    (TABLE, Some(1)),
                    index(vec![
                        vec![(DbKey::Text("a".into()), vec![10])],
                        vec![(DbKey::Int(3), vec![10])],
                    ]),
                ),
                (
                    (TABLE, None),
                    index(vec![vec![(DbKey::Int(0), vec![10, 11, 12])]]),
                ),
            ]);
            Self {
                rows,
                table: Arc::new(table),
                indexes,
            }
        }
    }

    fn table_types() -> Vec<Option<Vec<DbColumnType>>> {
        Fixture::table_columns()
    }

    impl Fixture {
        fn table_columns() -> Vec<Option<Vec<DbColumnType>>> {
            vec![
                Some(vec![kind(0, DbBase::Int)]),
                Some(vec![kind(36, DbBase::Text), kind(1, DbBase::Int)]),
                None,
            ]
        }
    }

    impl DbSource for Fixture {
        fn row(&mut self, id: u32) -> Result<Arc<DbRow>> {
            self.rows
                .get(&id)
                .cloned()
                .ok_or_else(|| "missing row".into())
        }
        fn table(&mut self, id: u32) -> Result<Arc<DbTable>> {
            if id == TABLE {
                Ok(self.table.clone())
            } else {
                Err("missing table".into())
            }
        }
        fn index(&mut self, table: u32, column: Option<u8>) -> Result<Arc<DbIndex>> {
            self.indexes
                .get(&(table, column))
                .cloned()
                .ok_or_else(|| "missing index".into())
        }
    }

    fn run(fixture: &mut Fixture, cursor: &mut Cursor, op: Opcode, ints: &[i32]) -> Result<Stacks> {
        let mut stacks = Stacks::default();
        stacks.integers.extend_from_slice(ints);
        execute(fixture, cursor, op, &mut stacks)?;
        Ok(stacks)
    }

    #[test]
    fn packed_column_references_split_table_column_and_tuple() {
        let column = ColumnRef::unpack(0x0007_5061);
        assert_eq!((column.table, column.column, column.tuple), (0x75, 6, 0));
        assert_eq!(ColumnRef::unpack(0x0007_5060).tuple, -1);
        assert_eq!(ColumnRef::unpack(packed(TABLE, 1, 1)).tuple, 1);
    }

    #[test]
    fn find_sets_the_result_and_findnext_walks_it_then_reports_the_end() {
        let (mut db, mut cursor) = (Fixture::new(), Cursor::default());
        let column = packed(TABLE, 0, 0);
        let stacks = run(
            &mut db,
            &mut cursor,
            Opcode::DbFindWithCount,
            &[column, 1, 0],
        )
        .unwrap();
        assert_eq!(stacks.integers, [2]);
        for expected in [10, 11, -1, -1] {
            let stacks = run(&mut db, &mut cursor, Opcode::DbFindnext, &[]).unwrap();
            assert_eq!(stacks.integers, [expected]);
        }
        // The count-free variant pushes nothing but still replaces the result.
        let stacks = run(&mut db, &mut cursor, Opcode::DbFind, &[column, 9, 0]).unwrap();
        assert!(stacks.integers.is_empty());
        assert_eq!(cursor.rows, Some(vec![12]));
        assert_eq!(cursor.table, Some(TABLE));
    }

    #[test]
    fn find_without_a_match_clears_the_previous_result() {
        let (mut db, mut cursor) = (Fixture::new(), Cursor::default());
        let column = packed(TABLE, 0, 0);
        run(&mut db, &mut cursor, Opcode::DbFind, &[column, 1, 0]).unwrap();
        let stacks = run(
            &mut db,
            &mut cursor,
            Opcode::DbFindWithCount,
            &[column, 4, 0],
        )
        .unwrap();
        assert_eq!(stacks.integers, [0]);
        assert_eq!(cursor.table, None);
        let stacks = run(&mut db, &mut cursor, Opcode::DbFindnext, &[]).unwrap();
        assert_eq!(stacks.integers, [-1]);
        // A null value (type -1) consumes no value and matches nothing.
        let stacks = run(&mut db, &mut cursor, Opcode::DbFindWithCount, &[column, -1]).unwrap();
        assert_eq!(stacks.integers, [0]);
    }

    #[test]
    fn text_keys_are_popped_from_the_object_stack() {
        let (mut db, mut cursor) = (Fixture::new(), Cursor::default());
        let mut stacks = Stacks::default();
        stacks.integers.push(packed(TABLE, 1, 0));
        stacks.push_object(ScriptValue::Text("a".into()));
        stacks.integers.push(2);
        execute(&mut db, &mut cursor, Opcode::DbFindWithCount, &mut stacks).unwrap();
        assert_eq!(stacks.integers, [1]);
        assert!(stacks.objects.is_empty());
        // A text key against the integer tuple element finds nothing.
        let mut stacks = Stacks::default();
        stacks.integers.push(packed(TABLE, 1, 1));
        stacks.push_object(ScriptValue::Text("a".into()));
        stacks.integers.push(2);
        execute(&mut db, &mut cursor, Opcode::DbFindWithCount, &mut stacks).unwrap();
        assert_eq!(stacks.integers, [0]);
    }

    #[test]
    fn unsupported_value_types_are_rejected() {
        let (mut db, mut cursor) = (Fixture::new(), Cursor::default());
        let column = packed(TABLE, 0, 0);
        assert!(run(&mut db, &mut cursor, Opcode::DbFind, &[column, 1, 1]).is_err());
        assert!(run(&mut db, &mut cursor, Opcode::DbFind, &[column, 1, 5]).is_err());
    }

    #[test]
    fn findall_lists_every_row_and_getrow_indexes_the_result() {
        let (mut db, mut cursor) = (Fixture::new(), Cursor::default());
        let stacks = run(
            &mut db,
            &mut cursor,
            Opcode::DbFindallWithCount,
            &[TABLE.cast_signed()],
        )
        .unwrap();
        assert_eq!(stacks.integers, [3]);
        for (index, expected) in [(0, 10), (2, 12), (3, -1), (-1, -1)] {
            let stacks = run(&mut db, &mut cursor, Opcode::DbGetrow, &[index]).unwrap();
            assert_eq!(stacks.integers, [expected]);
        }
        assert!(run(&mut db, &mut cursor, Opcode::DbFindall, &[-1]).is_err());
    }

    #[test]
    fn filter_intersects_the_active_result_and_requires_the_same_table() {
        let (mut db, mut cursor) = (Fixture::new(), Cursor::default());
        let column = packed(TABLE, 0, 0);
        assert!(
            run(
                &mut db,
                &mut cursor,
                Opcode::DbFindFilterWithCount,
                &[column, 1, 0]
            )
            .is_err(),
            "no active result"
        );
        run(
            &mut db,
            &mut cursor,
            Opcode::DbFindall,
            &[TABLE.cast_signed()],
        )
        .unwrap();
        let stacks = run(
            &mut db,
            &mut cursor,
            Opcode::DbFindFilterWithCount,
            &[column, 1, 0],
        )
        .unwrap();
        assert_eq!(stacks.integers, [2]);
        assert_eq!(cursor.rows, Some(vec![10, 11]));
        let stacks = run(&mut db, &mut cursor, Opcode::DbFindFilter, &[column, 9, 0]).unwrap();
        assert!(stacks.integers.is_empty());
        assert_eq!(cursor.rows, Some(vec![]));
        cursor.table = Some(TABLE + 1);
        assert!(run(&mut db, &mut cursor, Opcode::DbFindFilter, &[column, 1, 0]).is_err());
    }

    #[test]
    fn getfield_selects_fields_tuple_elements_and_defaults() {
        let (mut db, mut cursor) = (Fixture::new(), Cursor::default());
        let stacks = run(
            &mut db,
            &mut cursor,
            Opcode::DbGetfield,
            &[10, packed(TABLE, 0, 0), 1],
        )
        .unwrap();
        assert_eq!(stacks.integers, [2]);
        // The whole tuple pushes each element in order, strings on the object stack.
        let stacks = run(
            &mut db,
            &mut cursor,
            Opcode::DbGetfield,
            &[10, packed(TABLE, 1, -1), 0],
        )
        .unwrap();
        assert_eq!(stacks.integers, [3]);
        assert!(matches!(&stacks.objects[..], [Object::Text(text)] if text == "a"));
        let stacks = run(
            &mut db,
            &mut cursor,
            Opcode::DbGetfield,
            &[10, packed(TABLE, 1, 1), 0],
        )
        .unwrap();
        assert_eq!(stacks.integers, [3]);
        // A column the row lacks falls back to the table default, then the type default.
        let stacks = run(
            &mut db,
            &mut cursor,
            Opcode::DbGetfield,
            &[11, packed(TABLE, 2, 0), 0],
        )
        .unwrap();
        assert_eq!(stacks.integers, [7]);
        let stacks = run(
            &mut db,
            &mut cursor,
            Opcode::DbGetfield,
            &[11, packed(TABLE, 1, -1), 0],
        )
        .unwrap();
        // The tuple's second element is a boolean (type 1), whose default is 0, not -1.
        assert_eq!(stacks.integers, [0]);
        assert!(matches!(&stacks.objects[..], [Object::Text(text)] if text.is_empty()));
    }

    #[test]
    fn getfield_rejects_out_of_range_fields_tuples_and_columns() {
        let (mut db, mut cursor) = (Fixture::new(), Cursor::default());
        for ints in [
            [10, packed(TABLE, 0, 0), 2],
            [10, packed(TABLE, 0, 0), -1],
            [10, packed(TABLE, 0, 1), 0],
            [10, packed(TABLE, 3, 0), 0],
            [99, packed(TABLE, 0, 0), 0],
            [-1, packed(TABLE, 0, 0), 0],
        ] {
            assert!(
                run(&mut db, &mut cursor, Opcode::DbGetfield, &ints).is_err(),
                "{ints:?}"
            );
        }
    }

    #[test]
    fn field_count_and_row_table_report_row_shape() {
        let (mut db, mut cursor) = (Fixture::new(), Cursor::default());
        let count = |db: &mut Fixture, cursor: &mut Cursor, row, column| {
            run(
                db,
                cursor,
                Opcode::DbGetfieldcount,
                &[row, packed(TABLE, column, -1)],
            )
            .unwrap()
            .integers
        };
        assert_eq!(count(&mut db, &mut cursor, 10, 0), [2]);
        assert_eq!(count(&mut db, &mut cursor, 10, 1), [1]);
        assert_eq!(count(&mut db, &mut cursor, 11, 1), [0]);
        assert_eq!(
            count(&mut db, &mut cursor, 11, 2),
            [1],
            "table default counts"
        );
        let table = |db: &mut Fixture, cursor: &mut Cursor, row| {
            run(db, cursor, Opcode::DbGetrowtable, &[row])
                .unwrap()
                .integers
        };
        assert_eq!(table(&mut db, &mut cursor, 10), [TABLE.cast_signed()]);
        assert_eq!(table(&mut db, &mut cursor, 12), [-1]);
    }

    #[test]
    fn reset_clears_the_active_result_only() {
        let (mut db, mut state) = (Fixture::new(), DbState::default());
        run(
            &mut db,
            &mut state.cursor,
            Opcode::DbFindall,
            &[TABLE.cast_signed()],
        )
        .unwrap();
        state.reset();
        assert!(state.cursor.rows.is_none() && state.cursor.table.is_none());
    }
}
