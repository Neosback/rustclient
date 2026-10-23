//! Array-object instructions, ported from the 240 client's `8000`-family handler.
use super::{Result, ScriptHost, widgets::pop_value};
use rustclient_cache::script::Opcode;
use rustclient_client_script::{ArraySearch, Stacks};
use rustclient_platform::ReadOnlyBlobs;

pub(super) fn instruction<S: ReadOnlyBlobs>(
    h: &ScriptHost<'_, S>,
    op: Opcode,
    s: &mut Stacks,
) -> Result<bool> {
    let mode = match op {
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
