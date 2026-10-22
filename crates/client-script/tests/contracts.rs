use rustclient_cache::script::{Instruction, Opcode, Script};
use rustclient_client_script::{Context, Host, Limits, Stacks, execute};
use rustclient_model::{ScriptId, ScriptRequest};
use std::{collections::BTreeMap, sync::Arc};
fn instruction(opcode: Opcode, integer: i32) -> Instruction {
    Instruction {
        opcode,
        integer,
        text: None,
        long: 0,
    }
}
fn program(
    id: u32,
    locals: [usize; 3],
    arguments: [usize; 3],
    ops: &[(Opcode, i32)],
) -> Arc<Script> {
    Arc::new(Script {
        id: ScriptId::new(id),
        name: String::new(),
        locals,
        arguments,
        instructions: ops.iter().map(|(op, n)| instruction(*op, *n)).collect(),
        switches: Vec::new(),
    })
}
#[derive(Default)]
struct TestHost {
    programs: BTreeMap<ScriptId, Arc<Script>>,
    observed: Vec<i32>,
}
impl Host for TestHost {
    fn script(&mut self, id: ScriptId) -> Result<Arc<Script>, String> {
        self.programs
            .get(&id)
            .cloned()
            .ok_or_else(|| "missing script".into())
    }
    fn instruction(
        &mut self,
        op: Opcode,
        _: i32,
        s: &mut Stacks,
        _: &mut Context,
    ) -> Result<(), String> {
        if op == Opcode::SetVarp {
            self.observed.push(s.int()?);
            Ok(())
        } else {
            Err(format!("unsupported {op:?}"))
        }
    }
}
#[test]
fn calls_share_array_identity_and_event_locals_default_without_consuming_invoke_arguments() {
    use Opcode::{DefineArray, GetArrayInt, Iconst, Invoke, Oload, Return, SetArrayInt, SetVarp};
    let root = program(
        1,
        [0, 1, 0],
        [0; 3],
        &[
            (Iconst, 1),
            (DefineArray, 105),
            (Oload, 0),
            (Invoke, 2),
            (Iconst, 0),
            (GetArrayInt, 0),
            (SetVarp, 1),
            (Return, 0),
        ],
    );
    let child = program(
        2,
        [0, 1, 0],
        [0, 1, 0],
        &[(Iconst, 0), (Iconst, 42), (SetArrayInt, 0), (Return, 0)],
    );
    let mut host = TestHost::default();
    host.programs.insert(root.id, root);
    host.programs.insert(child.id, child);
    execute(
        &mut host,
        ScriptRequest {
            id: ScriptId::new(1),
            arguments: Vec::new(),
        },
        Context::default(),
        Limits::default(),
    )
    .unwrap();
    assert_eq!(host.observed, [42]);
}
#[test]
fn runaway_programs_and_malformed_stack_use_fail_with_exact_program_context() {
    use Opcode::{Invoke, Jump, PopInt};
    for (opcode, operand, expected) in [
        (Jump, -1, "instruction budget"),
        (PopInt, 0, "underflow"),
        (Invoke, 1, "call depth"),
    ] {
        let root = program(1, [0; 3], [0; 3], &[(opcode, operand)]);
        let mut host = TestHost::default();
        host.programs.insert(root.id, root);
        let error = execute(
            &mut host,
            ScriptRequest {
                id: ScriptId::new(1),
                arguments: Vec::new(),
            },
            Context::default(),
            Limits {
                instructions: 8,
                depth: 3,
                ..Limits::default()
            },
        )
        .unwrap_err();
        assert_eq!(error.script, ScriptId::new(1));
        assert_eq!(error.instruction, 0);
        assert!(error.detail.contains(expected), "{}", error.detail);
    }
}
