//! Java-compatible integer and string operations; no wall clock or hidden randomness.
use crate::{Result, Stacks};
use rustclient_cache::script::Opcode;
use rustclient_model::ScriptValue;
#[expect(
    clippy::too_many_lines,
    reason = "named arithmetic and string instruction grammar"
)]
#[expect(
    clippy::many_single_char_names,
    reason = "literal mathematical operand names keep Java arithmetic legible"
)]
pub(super) fn instruction(op: Opcode, s: &mut Stacks) -> Result<bool> {
    use Opcode::{
        Add, And, Append, AppendNum, AppendSignnum, Clearbit, Compare, Div, Escape, GetbitRange,
        Interpolate, Invpow, Lowercase, Max, Min, Mod, Multiply, Or, Pow, Removetags, Scale,
        Setbit, StringIndexofString, StringLength, Sub, Substring, Testbit, Tostring,
    };
    let result = match op {
        Add | Sub | Multiply | Div | Mod | And | Or | Setbit | Clearbit | Testbit | Pow
        | Invpow | Min | Max => {
            let [a, b] = s.ints()?;
            Some(match op {
                Min => a.min(b),
                Max => a.max(b),
                Add => a.wrapping_add(b),
                Sub => a.wrapping_sub(b),
                Multiply => a.wrapping_mul(b),
                Div => a
                    .checked_div(b)
                    .or_else(|| (a == i32::MIN && b == -1).then_some(i32::MIN))
                    .ok_or("division by zero")?,
                Mod => a
                    .checked_rem(b)
                    .or_else(|| (a == i32::MIN && b == -1).then_some(0))
                    .ok_or("modulo by zero")?,
                And => a & b,
                Or => a | b,
                Setbit => a | 1_i32.wrapping_shl(b.cast_unsigned()),
                Clearbit => a & !1_i32.wrapping_shl(b.cast_unsigned()),
                Testbit => i32::from(a & 1_i32.wrapping_shl(b.cast_unsigned()) != 0),
                Pow | Invpow => power(a, b, op),
                _ => unreachable!(),
            })
        }
        Interpolate => {
            let [a, b, x, y, p] = s.ints()?;
            Some(
                a.wrapping_add(
                    b.wrapping_sub(a)
                        .wrapping_mul(p.wrapping_sub(x))
                        .checked_div(y.wrapping_sub(x))
                        .ok_or("interpolate division")?,
                ),
            )
        }
        Scale => {
            let [a, b, c] = s.ints()?;
            Some(
                i32::try_from(
                    (i64::from(a) * i64::from(c))
                        .checked_div(i64::from(b))
                        .ok_or("scale division by zero")?,
                )
                .map_err(|_| "scale overflow")?,
            )
        }
        GetbitRange => {
            let [v, a, b] = s.ints()?;
            let shift = 31 - b;
            Some(
                v.wrapping_shl(shift.cast_unsigned())
                    .cast_unsigned()
                    .wrapping_shr((a + shift).cast_unsigned())
                    .cast_signed(),
            )
        }
        Tostring => {
            let n = s.int()?;
            s.push_object(ScriptValue::Text(n.to_string()));
            None
        }
        AppendNum | AppendSignnum => {
            let n = s.int()?;
            let a = s.text()?;
            let suffix = if op == AppendSignnum && n >= 0 {
                format!("+{n}")
            } else {
                n.to_string()
            };
            s.push_object(ScriptValue::Text(a + &suffix));
            None
        }
        Append => {
            let b = s.text()?;
            let a = s.text()?;
            if a.len() + b.len() > 65_536 {
                return Err("string allocation limit".into());
            }
            s.push_object(ScriptValue::Text(a + &b));
            None
        }
        Lowercase => {
            let a = s.text()?;
            s.push_object(ScriptValue::Text(a.to_lowercase()));
            None
        }
        StringLength => {
            let a = s.text()?;
            Some(i32::try_from(a.encode_utf16().count()).map_err(|_| "text size")?)
        }
        Substring => {
            let [start, end] = s.ints()?;
            let a = s.text()?;
            let utf16: Vec<_> = a.encode_utf16().collect();
            let slice = utf16
                .get(
                    usize::try_from(start).map_err(|_| "substring start")?
                        ..usize::try_from(end).map_err(|_| "substring end")?,
                )
                .ok_or("substring bounds")?;
            s.push_object(ScriptValue::Text(String::from_utf16_lossy(slice)));
            None
        }
        StringIndexofString => {
            let start = usize::try_from(s.int()?).map_err(|_| "string index")?;
            let needle = s.text()?;
            let a = s.text()?;
            let offset = a.char_indices().nth(start).map_or(a.len(), |(i, _)| i);
            Some(
                a[offset..]
                    .find(&needle)
                    .and_then(|i| {
                        i32::try_from(start + a[offset..offset + i].encode_utf16().count()).ok()
                    })
                    .unwrap_or(-1),
            )
        }
        Compare => {
            let b = s.text()?;
            let a = s.text()?;
            Some(match a.to_lowercase().cmp(&b.to_lowercase()) {
                std::cmp::Ordering::Less => -1,
                std::cmp::Ordering::Equal => 0,
                std::cmp::Ordering::Greater => 1,
            })
        }
        Removetags => {
            let a = s.text()?;
            let mut inside = false;
            let out = a
                .chars()
                .filter(|c| {
                    if *c == '<' {
                        inside = true;
                        false
                    } else if *c == '>' {
                        inside = false;
                        false
                    } else {
                        !inside
                    }
                })
                .collect();
            s.push_object(ScriptValue::Text(out));
            None
        }
        Escape => {
            let a = s.text()?;
            let mut out = String::new();
            for c in a.chars() {
                match c {
                    '<' => out.push_str("<lt>"),
                    '>' => out.push_str("<gt>"),
                    c => out.push(c),
                }
            }
            s.push_object(ScriptValue::Text(out));
            None
        }
        _ => return Ok(false),
    };
    if let Some(n) = result {
        s.integers.push(n);
    }
    Ok(true)
}
#[expect(
    clippy::cast_possible_truncation,
    reason = "Java double-to-int narrowing saturates and maps NaN to zero, as Rust's cast does"
)]
fn power(a: i32, b: i32, op: Opcode) -> i32 {
    if a == 0 {
        return 0;
    }
    if op == Opcode::Pow {
        f64::from(a).powf(f64::from(b)) as i32
    } else if b == 0 {
        i32::MAX
    } else {
        f64::from(a).powf(1.0 / f64::from(b)) as i32
    }
}
