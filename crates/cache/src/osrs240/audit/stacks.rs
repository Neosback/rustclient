//! Conservative CFG stack check against the fixed effects of the pinned 240 interpreter.
//! Stops a path at variable effects, calls and unavailable signatures; never assumes neutral effects.
use crate::script::{Instruction, Opcode, Script};
use std::collections::VecDeque;

#[derive(Debug, Default, PartialEq, Eq)]
pub struct StackAudit {
    pub checked_instructions: usize,
    pub unchecked_paths: Vec<usize>,
    pub failures: Vec<String>,
}
/// Analyze reachable fixed-effect paths from empty operand stacks. Locals already hold arguments.
/// Context-dependent instructions and calls are explicit unchecked boundaries; unvisited code is
/// not validated. A successful result establishes only these local stack contracts.
#[must_use]
pub fn audit_stacks(script: &Script) -> StackAudit {
    let mut report = StackAudit::default();
    let mut heights = vec![None; script.instructions.len()];
    let mut queue = VecDeque::from([(0_usize, [0_usize; 3])]);
    while let Some((pc, height)) = queue.pop_front() {
        let Some(slot) = heights.get_mut(pc) else {
            report
                .failures
                .push(format!("instruction address {pc} outside program"));
            continue;
        };
        if let Some(previous) = *slot {
            if previous != height {
                report.failures.push(format!(
                    "instruction {pc}: stack join {previous:?} != {height:?}"
                ));
            }
            continue;
        }
        *slot = Some(height);
        let instruction = &script.instructions[pc];
        let Some((pops, pushes)) = effect(instruction) else {
            report.unchecked_paths.push(pc);
            continue;
        };
        report.checked_instructions += 1;
        if (0..3).any(|i| height[i] < pops[i]) {
            report.failures.push(format!(
                "instruction {pc}: {:?} underflow {height:?}, needs {pops:?}",
                instruction.opcode
            ));
            continue;
        }
        let next = std::array::from_fn(|i| height[i] - pops[i] + pushes[i]);
        let mut branch = |delta: i32| {
            if let Some(target) = pc
                .checked_add(1)
                .and_then(|v| v.checked_add_signed(delta as isize))
            {
                queue.push_back((target, next));
            } else {
                report
                    .failures
                    .push(format!("instruction {pc}: branch overflow"));
            }
        };
        match instruction.opcode {
            Opcode::Return => {}
            Opcode::Jump => branch(instruction.integer),
            Opcode::IfIcmpne
            | Opcode::IfIcmpeq
            | Opcode::IfIcmplt
            | Opcode::IfIcmpgt
            | Opcode::IfIcmple
            | Opcode::IfIcmpge => {
                branch(instruction.integer);
                branch(0);
            }
            Opcode::Switch => {
                if let Some(table) = usize::try_from(instruction.integer)
                    .ok()
                    .and_then(|n| script.switches.get(n))
                {
                    for delta in table.values() {
                        branch(*delta);
                    }
                    branch(0);
                } else {
                    report
                        .failures
                        .push(format!("instruction {pc}: switch table outside program"));
                }
            }
            _ => branch(0),
        }
    }
    report
}
// [integers, objects, longs]. These contracts come from the pinned interpreter and
// arithmetic handlers, not from the Rust handler being measured. Variable effects stay unknown.
fn effect(i: &Instruction) -> Option<([usize; 3], [usize; 3])> {
    let zero = [0; 3];
    Some(match i.opcode {
        Opcode::Iconst
        | Opcode::Iload
        | Opcode::GetVarp
        | Opcode::GetVarbit
        | Opcode::GetVarcInt
        | Opcode::GetVarclansetting
        | Opcode::GetVarclan
        | Opcode::OcFindnext => (zero, [1, 0, 0]),
        Opcode::Sconst
        | Opcode::Oload
        | Opcode::PushNull
        | Opcode::GetVarcString
        | Opcode::GetVarcStringOld => (zero, [0, 1, 0]),
        Opcode::Lconst | Opcode::Lload => (zero, [0, 0, 1]),
        Opcode::Istore
        | Opcode::SetVarp
        | Opcode::SetVarbit
        | Opcode::SetVarcInt
        | Opcode::PopInt
        | Opcode::DefineArray
        | Opcode::Switch => ([1, 0, 0], zero),
        Opcode::Ostore | Opcode::SetVarcString | Opcode::SetVarcStringOld | Opcode::PopObject => {
            ([0, 1, 0], zero)
        }
        Opcode::Lstore | Opcode::PopLong => ([0, 0, 1], zero),
        Opcode::Jump | Opcode::Return | Opcode::OcFindreset => (zero, zero),
        Opcode::IfIcmpne
        | Opcode::IfIcmpeq
        | Opcode::IfIcmplt
        | Opcode::IfIcmpgt
        | Opcode::IfIcmple
        | Opcode::IfIcmpge => ([2, 0, 0], zero),
        Opcode::JoinString => ([0, usize::try_from(i.integer).ok()?, 0], [0, 1, 0]),
        Opcode::Random
        | Opcode::Randominc
        | Opcode::Bitcount
        | Opcode::OcCost
        | Opcode::OcStackable
        | Opcode::OcCert
        | Opcode::OcUncert
        | Opcode::OcMembers
        | Opcode::OcPlaceholder
        | Opcode::OcUnplaceholder => ([1, 0, 0], [1, 0, 0]),
        Opcode::Add
        | Opcode::Sub
        | Opcode::Multiply
        | Opcode::Div
        | Opcode::Addpercent
        | Opcode::Setbit
        | Opcode::Clearbit
        | Opcode::Testbit
        | Opcode::Mod
        | Opcode::Pow
        | Opcode::Invpow
        | Opcode::And
        | Opcode::Or
        | Opcode::Togglebit => ([2, 0, 0], [1, 0, 0]),
        Opcode::Interpolate => ([5, 0, 0], [1, 0, 0]),
        Opcode::Scale | Opcode::SetbitRange | Opcode::ClearbitRange | Opcode::GetbitRange => {
            ([3, 0, 0], [1, 0, 0])
        }
        Opcode::OcFind => ([1, 1, 0], [1, 0, 0]),
        Opcode::OcName | Opcode::Tostring => ([1, 0, 0], [0, 1, 0]),
        Opcode::Lowercase | Opcode::Escape | Opcode::Removetags => ([0, 1, 0], [0, 1, 0]),
        Opcode::StringLength => ([0, 1, 0], [1, 0, 0]),
        Opcode::Append => ([0, 2, 0], [0, 1, 0]),
        Opcode::AppendNum | Opcode::AppendSignnum | Opcode::AppendChar => ([1, 1, 0], [0, 1, 0]),
        _ => return None,
    })
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_model::ScriptId;
    fn script(ops: &[(Opcode, i32)]) -> Script {
        Script {
            id: ScriptId::new(0),
            name: String::new(),
            locals: [0; 3],
            arguments: [0; 3],
            switches: Vec::new(),
            instructions: ops
                .iter()
                .map(|&(opcode, integer)| Instruction {
                    opcode,
                    integer,
                    text: None,
                    long: 0,
                    array_default: None,
                })
                .collect(),
        }
    }
    #[test]
    fn checks_known_paths_underflow_and_joins_but_stops_at_calls() {
        let valid = script(&[
            (Opcode::Iconst, 0),
            (Opcode::PopInt, 0),
            (Opcode::Return, 0),
        ]);
        assert_eq!(
            audit_stacks(&valid),
            StackAudit {
                checked_instructions: 3,
                ..StackAudit::default()
            }
        );
        assert!(audit_stacks(&script(&[(Opcode::PopObject, 0)])).failures[0].contains("underflow"));
        let branch = script(&[
            (Opcode::Iconst, 0),
            (Opcode::Iconst, 0),
            (Opcode::IfIcmpeq, 1),
            (Opcode::Iconst, 0),
            (Opcode::Return, 0),
        ]);
        assert!(audit_stacks(&branch).failures[0].contains("join"));
        let array = audit_stacks(&script(&[
            (Opcode::Iload, 1),
            (Opcode::GetArrayInt, 0),
            (Opcode::Ostore, 1),
        ]));
        assert_eq!(array.unchecked_paths, [1]);
        assert!(
            array.failures.is_empty(),
            "240 array reads depend on the local array type"
        );
        let call = audit_stacks(&script(&[(Opcode::Invoke, 100), (Opcode::PopInt, 0)]));
        assert_eq!(call.unchecked_paths, [0]);
        assert!(call.failures.is_empty());
    }
}
