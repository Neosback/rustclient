//! Array-object instructions, ported from the 240 client's `8000`-family handler.
use super::{Result, ScriptHost, widgets::pop_value};
use rustclient_cache::script::Opcode;
use rustclient_client_script::{ArraySearch, Object, Stacks};
use rustclient_model::ScriptValue;
use rustclient_platform::ReadOnlyBlobs;

/// Pushes a removed element onto the stack its type lives on.
fn push_value(s: &mut Stacks, value: ScriptValue) -> Result<()> {
    match value {
        ScriptValue::Int(v) => s.integers.push(v),
        ScriptValue::Text(_) => s.push_object(value),
        _ => return Err("array element type".into()),
    }
    Ok(())
}

/// Enum key (`values == false`) or value arrays (reference opcodes 8020 and 8021): the type
/// operand must match the enum's declared type, and the result is a read-only copy in cache order.
fn enum_array<S: ReadOnlyBlobs>(
    h: &mut ScriptHost<'_, S>,
    s: &mut Stacks,
    values: bool,
) -> Result<()> {
    let [kind, enum_id] = s.ints()?;
    let enum_id = u32::try_from(enum_id).map_err(|_| "enum id")?;
    if !h.resources.enums.contains_key(&enum_id) {
        let loaded = h
            .resources
            .profile
            .load_script_enum(&h.resources.cache, enum_id)
            .map_err(|e| e.to_string())?;
        h.resources.enums.insert(enum_id, loaded);
    }
    let table = &h.resources.enums[&enum_id];
    let declared = if values { table.output } else { table.input };
    if kind != i32::from(declared) {
        return Err(format!(
            "enum {enum_id} declares type {declared}, not {kind}"
        ));
    }
    let array = if !values {
        ScriptValue::Integers(table.keys.clone())
    } else if declared == b's' {
        ScriptValue::Strings(
            table
                .values
                .iter()
                .map(|v| match v {
                    ScriptValue::Text(t) => Ok(t.clone()),
                    _ => Err("enum string value"),
                })
                .collect::<std::result::Result<_, _>>()?,
        )
    } else {
        ScriptValue::Integers(
            table
                .values
                .iter()
                .map(|v| match v {
                    ScriptValue::Int(i) => Ok(*i),
                    _ => Err("enum integer value"),
                })
                .collect::<std::result::Result<_, _>>()?,
        )
    };
    s.push_object(array);
    if let Some(object) = s.objects.last().cloned() {
        s.freeze_array(&object);
    }
    Ok(())
}

pub(super) fn instruction<S: ReadOnlyBlobs>(
    h: &mut ScriptHost<'_, S>,
    op: Opcode,
    s: &mut Stacks,
) -> Result<bool> {
    let index = |value: i32| usize::try_from(value).map_err(|_| "array index");
    let mode = match op {
        Opcode::ArrayIsNull => {
            let value = s.object()?;
            s.integers.push(i32::from(matches!(value, Object::Null)));
            return Ok(true);
        }
        Opcode::EnumKeys | Opcode::EnumValues => {
            enum_array(h, s, op == Opcode::EnumValues)?;
            return Ok(true);
        }
        Opcode::ArrayNew => {
            // Stack: type, length, capacity. Only the length decides the size; both are bounded.
            let [kind, length, capacity] = s.ints()?;
            if !(0..=5000).contains(&length) || !(0..=5000).contains(&capacity) {
                return Err("array size".into());
            }
            let init = if kind == 105 || kind == 49 { 0 } else { -1 };
            let array = s.new_array(kind == 115, init, index(length)?)?;
            s.objects.push(array);
            return Ok(true);
        }
        Opcode::ArrayResize => {
            let length = index(s.int()?)?;
            let array = s.object()?;
            s.resize_array(&array, length)?;
            return Ok(true);
        }
        Opcode::ArrayPush | Opcode::ArrayInsert => {
            let (position, kind) = if op == Opcode::ArrayInsert {
                let [position, kind] = s.ints()?;
                (Some(position), kind)
            } else {
                (None, s.int()?)
            };
            let value = pop_value(h.resources.profile, s, kind)?.ok_or("array value type")?;
            let array = s.object()?;
            let at = match position {
                Some(position) => index(position)?,
                None => s.array_len(&array)?,
            };
            s.insert_array(&array, at, value)?;
            return Ok(true);
        }
        Opcode::ArrayRemove => {
            let at = index(s.int()?)?;
            let array = s.object()?;
            let value = s.remove_array(&array, at)?;
            push_value(s, value)?;
            return Ok(true);
        }
        Opcode::ArrayAppendAll | Opcode::ArrayInsertAll => {
            let position = if op == Opcode::ArrayInsertAll {
                Some(s.int()?)
            } else {
                None
            };
            let source = s.object()?;
            let target = s.object()?;
            let at = match position {
                Some(position) => index(position)?,
                None => s.array_len(&target)?,
            };
            s.insert_all_array(&target, &source, at)?;
            return Ok(true);
        }
        Opcode::ArrayRemoveRange => {
            let [count, at] = s.ints()?;
            let array = s.object()?;
            s.remove_array_range(&array, at, count)?;
            return Ok(true);
        }
        Opcode::ArraySort => {
            // Stack: keys, companion (or null) on top.
            let companion = s.object()?;
            let keys = s.object()?;
            s.sort_array(&keys, &companion)?;
            return Ok(true);
        }
        Opcode::ArrayIndexOf => ArraySearch::First,
        Opcode::ArrayLastIndexOf => ArraySearch::Last,
        Opcode::ArrayCount => ArraySearch::Count,
        _ => return Ok(false),
    };
    // Stack: array, value, then `from`, `to` and the value's script type on the integer stack.
    let [from, to, kind] = s.ints()?;
    let value = pop_value(h.resources.profile, s, kind)?.ok_or("array search value type")?;
    let array = s.object()?;
    let found = s.search_array(&array, &value, [from, to], mode)?;
    s.integers.push(found);
    Ok(true)
}
