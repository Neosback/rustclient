use rustclient_cache::script::{Instruction, Opcode, Script};
use rustclient_client_script::{Context, Host, Limits, Stacks, execute};
use rustclient_model::{ScriptId, ScriptRequest};
use std::{collections::BTreeMap, sync::Arc};
fn instruction(opcode: Opcode, integer: i32) -> Instruction {
    Instruction {
        array_default: (opcode == Opcode::DefineArray)
            .then_some(rustclient_cache::script::ArrayInitialValue::Integer(0)),
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
        context: &mut Context,
    ) -> Result<(), String> {
        if op == Opcode::SetVarp {
            self.observed.push(s.int()?);
            Ok(())
        } else if op == Opcode::CcCallonresize {
            context.flow = rustclient_client_script::EventFlow::Finish;
            Ok(())
        } else {
            Err(format!("unsupported {op:?}"))
        }
    }
}
#[test]
fn host_finish_ends_the_whole_event_and_preserves_committed_prefix() {
    let mut host = TestHost::default();
    host.programs.insert(
        ScriptId::new(1),
        program(
            1,
            [0; 3],
            [0; 3],
            &[
                (Opcode::Iconst, 7),
                (Opcode::SetVarp, 0),
                (Opcode::Invoke, 2),
                (Opcode::Iconst, 9),
                (Opcode::SetVarp, 0),
                (Opcode::Return, 0),
            ],
        ),
    );
    host.programs.insert(
        ScriptId::new(2),
        program(
            2,
            [0; 3],
            [0; 3],
            &[
                (Opcode::CcCallonresize, 0),
                (Opcode::Iconst, 8),
                (Opcode::SetVarp, 0),
                (Opcode::Return, 0),
            ],
        ),
    );
    execute(
        &mut host,
        ScriptRequest {
            id: ScriptId::new(1),
            arguments: vec![],
        },
        Context::default(),
        Limits::default(),
    )
    .unwrap();
    assert_eq!(host.observed, [7]);
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
            (DefineArray, 0),
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

mod arrays {
    use rustclient_client_script::{ArraySearch, Object, Stacks};
    use rustclient_model::ScriptValue;

    fn push(stacks: &mut Stacks, value: ScriptValue) -> Object {
        stacks.push_object(value);
        stacks.objects.last().cloned().unwrap()
    }
    #[test]
    fn sorting_reorders_a_companion_array_with_its_keys() {
        let mut stacks = Stacks::default();
        let keys = push(&mut stacks, ScriptValue::Integers(vec![30, 10, 20]));
        let names = push(
            &mut stacks,
            ScriptValue::Strings(vec!["c".into(), "a".into(), "b".into()]),
        );
        stacks.sort_array(&keys, &names).unwrap();
        for (i, (key, name)) in [(10, "a"), (20, "b"), (30, "c")].into_iter().enumerate() {
            let at = i32::try_from(i).unwrap();
            let range = [at, at + 1];
            assert_eq!(
                stacks
                    .search_array(&keys, &ScriptValue::Int(key), range, ArraySearch::First)
                    .unwrap(),
                at
            );
            assert_eq!(
                stacks
                    .search_array(
                        &names,
                        &ScriptValue::Text(name.into()),
                        range,
                        ArraySearch::First
                    )
                    .unwrap(),
                at
            );
        }
    }

    #[test]
    fn string_keys_sort_by_utf16_code_unit_and_null_companions_are_allowed() {
        let mut stacks = Stacks::default();
        let keys = push(
            &mut stacks,
            ScriptValue::Strings(vec!["b".into(), "B".into(), "a".into()]),
        );
        stacks.sort_array(&keys, &Object::Null).unwrap();
        let first = |stacks: &Stacks, text: &str| {
            stacks
                .search_array(
                    &keys,
                    &ScriptValue::Text(text.into()),
                    [0, -1],
                    ArraySearch::First,
                )
                .unwrap()
        };
        assert_eq!(
            (
                first(&stacks, "B"),
                first(&stacks, "a"),
                first(&stacks, "b")
            ),
            (0, 1, 2)
        );
    }

    #[test]
    fn sorting_rejects_short_companions_and_ignores_trivial_arrays() {
        let mut stacks = Stacks::default();
        let keys = push(&mut stacks, ScriptValue::Integers(vec![2, 1]));
        let short = push(&mut stacks, ScriptValue::Integers(vec![9]));
        assert!(stacks.sort_array(&keys, &short).is_err());
        let single = push(&mut stacks, ScriptValue::Integers(vec![5]));
        stacks.sort_array(&single, &short).unwrap();
        assert!(stacks.sort_array(&Object::Null, &Object::Null).is_err());
    }

    #[test]
    fn searches_clamp_the_range_and_report_misses_like_the_reference() {
        let mut stacks = Stacks::default();
        let array = push(&mut stacks, ScriptValue::Integers(vec![7, 3, 7, 7, 1]));
        let seven = ScriptValue::Int(7);
        let search = |mode, range| stacks.search_array(&array, &seven, range, mode).unwrap();
        assert_eq!(search(ArraySearch::First, [0, -1]), 0);
        assert_eq!(search(ArraySearch::Last, [0, -1]), 3);
        assert_eq!(search(ArraySearch::Count, [0, 99]), 3);
        assert_eq!(search(ArraySearch::First, [-5, 1]), 0);
        assert_eq!(search(ArraySearch::First, [1, 2]), -1);
        assert_eq!(search(ArraySearch::Last, [3, 3]), -1);
        assert_eq!(search(ArraySearch::Count, [3, 2]), 0);
        let null_search = |mode| {
            stacks
                .search_array(&Object::Null, &seven, [0, -1], mode)
                .unwrap()
        };
        assert_eq!(
            (
                null_search(ArraySearch::First),
                null_search(ArraySearch::Count)
            ),
            (-1, 0)
        );
        assert!(
            stacks
                .search_array(
                    &array,
                    &ScriptValue::Text("7".into()),
                    [0, -1],
                    ArraySearch::First
                )
                .is_err()
        );
    }
}
