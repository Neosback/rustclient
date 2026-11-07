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
        Add, And, Append, AppendNum, AppendSignnum, Clearbit, Compare, Div, Escape, Fromdate,
        GetbitRange, Interpolate, Invpow, Lowercase, Max, Min, Mod, Multiply, Or,
        ParseintOrMinusone, Pow, Removetags, Scale, Setbit, StringIndexofString, StringLength, Sub,
        Substring, Testbit, Tostring,
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
        // `isNumber(text, 10, true)` then `parseInt`: an optional leading sign, at least one
        // digit and no overflow, which is exactly what `str::parse::<i32>` accepts.
        ParseintOrMinusone => Some(parse_int_or_minus_one(&s.text()?)),
        Fromdate => {
            let days = s.int()?;
            s.push_object(ScriptValue::Text(from_date(days)));
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
        Opcode::StringIndexofChar => {
            let code = s.int()?;
            let text = s.text()?;
            Some(
                char::from_u32(code.cast_unsigned())
                    .and_then(|ch| text.find(ch))
                    .and_then(|offset| i32::try_from(text[..offset].encode_utf16().count()).ok())
                    .unwrap_or(-1),
            )
        }
        Opcode::CharIsprintable
        | Opcode::CharIsalphanumeric
        | Opcode::CharIsalpha
        | Opcode::CharIsnumeric => {
            let value = s.int()?;
            let ch = char::from_u32(value.cast_unsigned());
            Some(i32::from(ch.is_some_and(|ch| match op {
                Opcode::CharIsalphanumeric => ch.is_ascii_alphanumeric(),
                Opcode::CharIsalpha => ch.is_ascii_alphabetic(),
                Opcode::CharIsnumeric => ch.is_ascii_digit(),
                _ => {
                    (' '..='~').contains(&ch)
                        || ('\u{a0}'..='\u{ff}').contains(&ch)
                        || matches!(ch, '€' | 'Œ' | '—' | 'œ' | 'Ÿ')
                }
            })))
        }
        Opcode::AppendChar => {
            let value = s.int()?;
            let mut text = s.text()?;
            if text.len() > 65_532 {
                return Err("string allocation limit".into());
            }
            let ch = char::from_u32(value.cast_unsigned() & 65535).unwrap_or('\u{fffd}');
            text.push(ch);
            s.push_object(ScriptValue::Text(text));
            None
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

/// Opcode 4036: the reference checks the text with `classMQ.method7683` and, only when it holds a
/// whole decimal integer, parses it with `classOH.method8481`; anything else is -1. Both read UTF-16
/// units with one optional leading sign, accept ASCII digits only and reject overflow by dividing the
/// wrapped product back, so `-2147483648` parses and `2147483648` does not.
fn parse_int_or_minus_one(text: &str) -> i32 {
    let (mut value, mut negative, mut digits) = (0_i32, false, false);
    for (index, unit) in text.encode_utf16().enumerate() {
        if index == 0 && matches!(unit, 0x2b | 0x2d) {
            negative = unit == 0x2d;
            continue;
        }
        let Some(digit) = char::from_u32(unit.into()).and_then(|c| c.to_digit(10)) else {
            return -1;
        };
        let digit = i32::try_from(digit).unwrap_or_default();
        let next = value
            .wrapping_mul(10)
            .wrapping_add(if negative { -digit } else { digit });
        if next / 10 != value {
            return -1;
        }
        (value, digits) = (next, true);
    }
    if digits { value } else { -1 }
}
#[cfg(test)]
mod tests {
    use super::parse_int_or_minus_one as parse;

    #[test]
    fn parse_matches_the_reference_decimal_reader() {
        for (text, expected) in [
            ("123", 123),
            ("-5", -5),
            ("+7", 7),
            ("007", 7),
            ("2147483647", i32::MAX),
            ("-2147483648", i32::MIN),
            ("2147483648", -1),
            ("-2147483649", -1),
            ("", -1),
            ("-", -1),
            ("+", -1),
            ("1+2", -1),
            ("--1", -1),
            ("12a", -1),
            (" 1", -1),
            ("\u{663}", -1),
        ] {
            assert_eq!(parse(text), expected, "{text:?}");
        }
    }
}

/// `FROMDATE`: the reference formats `86400000 * (11745 + days)` milliseconds with
/// `Calendar.getInstance()` as `day-Mon-year`. `RustClient` formats the UTC date so the result
/// does not depend on the host time zone (deviation `FROMDATE-UTC`).
fn from_date(days: i32) -> String {
    const MONTHS: [&str; 12] = [
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
    ];
    // Days since 1970-01-01 to a proleptic Gregorian civil date (Hinnant's `civil_from_days`).
    let z = i64::from(days) + 11_745 + 719_468;
    let era = z.div_euclid(146_097);
    let doe = z.rem_euclid(146_097);
    let yoe = (doe - doe / 1_460 + doe / 36_524 - doe / 146_096) / 365;
    let doy = doe - (365 * yoe + yoe / 4 - yoe / 100);
    let mp = (5 * doy + 2) / 153;
    let day = doy - (153 * mp + 2) / 5 + 1;
    let month = if mp < 10 { mp + 3 } else { mp - 9 };
    let year = yoe + era * 400 + i64::from(month <= 2);
    let name = MONTHS[usize::try_from(month - 1).unwrap_or(0)];
    format!("{day}-{name}-{year}")
}

#[cfg(test)]
mod date_tests {
    use super::*;

    fn run(op: Opcode, ints: &[i32], texts: &[&str]) -> Stacks {
        let mut s = Stacks::default();
        s.integers.extend_from_slice(ints);
        for text in texts {
            s.push_object(ScriptValue::Text((*text).to_owned()));
        }
        assert!(instruction(op, &mut s).unwrap());
        s
    }

    #[test]
    fn from_date_counts_days_from_the_reference_epoch() {
        // Day 0 is 1970-01-01 + 11745 days = 2002-02-27, the reference's runedate origin.
        for (days, text) in [
            (0, "27-Feb-2002"),
            (1, "28-Feb-2002"),
            (2, "1-Mar-2002"),
            (-11_745, "1-Jan-1970"),
            (8_037, "29-Feb-2024"),
            (-11_686, "1-Mar-1970"),
            (-11_321, "1-Mar-1971"),
            (-729, "29-Feb-2000"),
            (35_796, "1-Mar-2100"),
            (8_343, "31-Dec-2024"),
        ] {
            let mut s = run(Opcode::Fromdate, &[days], &[]);
            assert_eq!(s.text().unwrap(), text, "day {days}");
        }
    }
}
