//! Stock 240 `classEL.method3945`, `classGP.method5615` and `method5609`.
use crate::CacheProfile;
use rustclient_model::{InterfaceId, ItemDefinitionId, SkillId, VarbitId, VarpId, WidgetId};
const WORD_LIMIT: usize = 65_536;
const TEXT_LIMIT: usize = 1_048_576;
pub const PRESENT: i32 = 999_999_999;
/// Revision-independent facts requested by an authored legacy expression.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum Query {
    CurrentLevel(SkillId),
    BaseLevel(SkillId),
    Experience(SkillId),
    /// Presence queries supply 0/1; this adapter owns the stock presence sentinel.
    WidgetItem {
        widget: WidgetId,
        item: Option<ItemDefinitionId>,
        presence: bool,
    },
    Varp(VarpId),
    Varbit(VarbitId),
    CombatLevel,
    RunEnergy,
    Weight,
    WorldX,
    WorldY,
}
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct EvaluationError {
    pub word: usize,
    pub detail: String,
}
impl core::fmt::Display for EvaluationError {
    fn fmt(&self, f: &mut core::fmt::Formatter<'_>) -> core::fmt::Result {
        write!(f, "CS1 word {}: {}", self.word, self.detail)
    }
}
impl std::error::Error for EvaluationError {}
/// A diagnostic result, preserving both stock compatibility sentinels and the failure cause.
#[derive(Debug)]
pub struct Value {
    pub value: i32,
    pub error: Option<EvaluationError>,
}
impl CacheProfile {
    /// Direct local assignment performed after an authored toggle/select button packet.
    /// This examines only expression zero's leading varp query, not its evaluated result.
    /// # Errors
    /// Reports malformed direct references and missing select comparisons.
    pub fn legacy_button_variable(
        self,
        legacy: &crate::widget::LegacyPresentation,
        mut current: impl FnMut(VarpId) -> i32,
    ) -> Result<Option<(VarpId, i32)>, String> {
        use crate::widget::LegacyButton;
        if !matches!(legacy.button, LegacyButton::Toggle | LegacyButton::Select) {
            return Ok(None);
        }
        let Some(words) = legacy.expressions.first() else {
            return Ok(None);
        };
        let first = words.first().ok_or("empty button expression zero")?;
        if *first != 5 {
            return Ok(None);
        }
        let id = words
            .get(1)
            .copied()
            .ok_or("truncated button varp reference")?;
        let id = VarpId::new(u32::try_from(id).map_err(|_| "negative button varp reference")?);
        let previous = current(id);
        let next = if legacy.button == LegacyButton::Toggle {
            1_i32.wrapping_sub(previous)
        } else {
            legacy
                .comparisons
                .first()
                .ok_or("missing button comparison zero")?
                .1
        };
        Ok((next != previous).then_some((id, next)))
    }
    /// Evaluate one expression from immutable metadata and explicit live queries.
    /// Missing expressions return -2, failures return -1 with a retained diagnostic.
    pub fn evaluate_cs1(
        self,
        expressions: &[Vec<i32>],
        index: usize,
        mut query: impl FnMut(Query) -> Result<i32, String>,
    ) -> Value {
        match expressions.get(index) {
            None => Value {
                value: -2,
                error: None,
            },
            Some(words) => match evaluate(words, &mut query) {
                Ok(value) => Value { value, error: None },
                Err(error) => Value {
                    value: -1,
                    error: Some(error),
                },
            },
        }
    }
}
fn read(words: &[i32], at: &mut usize) -> Result<i32, EvaluationError> {
    let value = words.get(*at).copied().ok_or_else(|| EvaluationError {
        word: *at,
        detail: "truncated expression".into(),
    })?;
    *at += 1;
    Ok(value)
}
fn id(value: i32, at: usize) -> Result<u32, EvaluationError> {
    u32::try_from(value).map_err(|_| EvaluationError {
        word: at,
        detail: "negative query index".into(),
    })
}
#[expect(
    clippy::too_many_lines,
    reason = "one source-ordered bounded expression interpreter"
)]
fn evaluate(
    words: &[i32],
    query: &mut impl FnMut(Query) -> Result<i32, String>,
) -> Result<i32, EvaluationError> {
    if words.len() > WORD_LIMIT {
        return Err(EvaluationError {
            word: 0,
            detail: "expression word limit".into(),
        });
    }
    let mut at = 0;
    let mut value = 0_i32;
    let mut operator = 0;
    loop {
        let location = at;
        let code = read(words, &mut at)?;
        if code == 0 {
            return Ok(value);
        }
        if matches!(code, 15..=17) {
            operator = code - 14;
            continue;
        }
        let fact = match code {
            1..=3 => {
                let skill = SkillId::new(id(read(words, &mut at)?, location)?);
                Some(match code {
                    1 => Query::CurrentLevel(skill),
                    2 => Query::BaseLevel(skill),
                    _ => Query::Experience(skill),
                })
            }
            4 | 10 => {
                let high = read(words, &mut at)?;
                let low = read(words, &mut at)?;
                let packed = high.wrapping_shl(16).wrapping_add(low).cast_unsigned();
                let raw = read(words, &mut at)?;
                let item = if raw == -1 {
                    None
                } else {
                    Some(ItemDefinitionId::new(id(raw, location)?))
                };
                Some(Query::WidgetItem {
                    widget: WidgetId::new(InterfaceId::new(packed >> 16), packed & 65_535),
                    item,
                    presence: code == 10,
                })
            }
            5 | 7 | 13 => Some(Query::Varp(VarpId::new(id(
                read(words, &mut at)?,
                location,
            )?))),
            6 => Some(Query::BaseLevel(SkillId::new(id(
                read(words, &mut at)?,
                location,
            )?))),
            8 => Some(Query::CombatLevel),
            11 => Some(Query::RunEnergy),
            12 => Some(Query::Weight),
            14 => Some(Query::Varbit(VarbitId::new(id(
                read(words, &mut at)?,
                location,
            )?))),
            18 => Some(Query::WorldX),
            19 => Some(Query::WorldY),
            _ => None,
        };
        let mut operand = if let Some(fact) = fact {
            query(fact).map_err(|detail| EvaluationError {
                word: location,
                detail,
            })?
        } else {
            0
        };
        match code {
            6 => {
                operand = super::skills::cs1_threshold(operand).ok_or_else(|| EvaluationError {
                    word: location,
                    detail: "base level outside experience table".into(),
                })?;
            }
            7 => operand = operand.wrapping_mul(100) / 46_875,
            10 => operand = if operand == 0 { 0 } else { PRESENT },
            9 => {
                for skill in 0..24 {
                    operand = operand.wrapping_add(
                        query(Query::BaseLevel(SkillId::new(skill))).map_err(|detail| {
                            EvaluationError {
                                word: location,
                                detail,
                            }
                        })?,
                    );
                }
            }
            13 => {
                operand = i32::from(
                    operand & 1_i32.wrapping_shl(read(words, &mut at)?.cast_unsigned()) != 0,
                );
            }
            20 => operand = read(words, &mut at)?,
            _ => {}
        }
        value = match operator {
            1 => value.wrapping_sub(operand),
            2 if operand != 0 => value.wrapping_div(operand),
            2 => value,
            3 => value.wrapping_mul(operand),
            _ => value.wrapping_add(operand),
        };
        operator = 0;
    }
}
/// Source comparisons: 2 less-than, 3 greater-than, 4 not-equal; otherwise equality.
#[must_use]
pub fn matches(comparisons: &[(u8, i32)], mut evaluate: impl FnMut(usize) -> i32) -> bool {
    !comparisons.is_empty()
        && comparisons
            .iter()
            .enumerate()
            .all(|(index, &(kind, target))| {
                let value = evaluate(index);
                match kind {
                    2 => value < target,
                    3 => value > target,
                    4 => value != target,
                    _ => value == target,
                }
            })
}
/// Replace %1 through %5 in source order, preserving the stock '*' threshold.
/// # Errors
/// Rejects text expansions beyond the explicit presentation byte budget.
pub fn substitute(text: &str, mut evaluate: impl FnMut(usize) -> i32) -> Result<String, String> {
    if text.len() > TEXT_LIMIT {
        return Err("CS1 text byte limit".into());
    }
    let mut text = text.to_owned();
    for index in 0..5 {
        let token = format!("%{}", index + 1);
        let count = text.matches(&token).count();
        if count == 0 {
            continue;
        }
        let value = evaluate(index);
        let replacement = if value < PRESENT {
            value.to_string()
        } else {
            "*".into()
        };
        let size = text
            .len()
            .checked_sub(count * 2)
            .and_then(|base| base.checked_add(count.checked_mul(replacement.len())?))
            .ok_or("CS1 text size overflow")?;
        if size > TEXT_LIMIT {
            return Err("CS1 text byte limit".into());
        }
        text = text.replace(&token, &replacement);
    }
    Ok(text)
}
