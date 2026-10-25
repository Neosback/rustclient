//! Cache-authored load callbacks observed through committed semantic variables.
use super::*;
use rustclient_cache::{
    GroupId, IndexId, Source,
    osrs240::widgets::decode,
    script::{Instruction, Opcode, Script},
};
use rustclient_model::*;
use rustclient_platform::MemoryBlobs;

fn setup() -> (ClientScripts<MemoryBlobs>, GameState) {
    let mut scripts = test_support::scripts();
    for group in 1..=3 {
        let interface = InterfaceId::new(group);
        let id = WidgetId::new(interface, 0);
        let mut widget = decode(
            include_bytes!("../../../cache/tests/fixtures/widgets/container.bin"),
            id,
            Source::group(IndexId(3), GroupId(group)),
        )
        .unwrap();
        widget.parent = None;
        widget.listeners.clear();
        widget.listeners.insert(
            WidgetListener::Load,
            vec![ScriptValue::Int(90000 + i32::try_from(group).unwrap())],
        );
        scripts.interfaces.insert(
            interface,
            InterfaceDefinition::from_widgets(interface, vec![widget]).unwrap(),
        );
        let id = ScriptId::new(90000 + group);
        // Append this group number to varc 7. This observes callback ordering,
        // multiplicity and execution, rather than private scheduler bookkeeping.
        scripts.scripts.insert(
            id,
            Arc::new(Script {
                id,
                name: String::new(),
                locals: [0; 3],
                arguments: [0; 3],
                switches: vec![],
                instructions: [
                    (Opcode::GetVarcInt, 7),
                    (Opcode::Iconst, 10),
                    (Opcode::Multiply, 0),
                    (Opcode::Iconst, i32::try_from(group).unwrap()),
                    (Opcode::Add, 0),
                    (Opcode::SetVarcInt, 7),
                    (Opcode::Return, 0),
                ]
                .into_iter()
                .map(|(opcode, integer)| Instruction {
                    opcode,
                    integer,
                    text: None,
                    long: 0,
                    array_default: None,
                })
                .collect(),
            }),
        );
    }
    let mut state = GameState::new();
    state
        .apply_operation(SemanticOperation::BeginSession {
            revision: Revision::with_subrevision(240, 2),
        })
        .unwrap();
    state
        .apply_operation(SemanticOperation::Interface(
            InterfaceUpdate::ClientVariable {
                id: 7,
                value: ScriptValue::Int(0),
            },
        ))
        .unwrap();
    (scripts, state)
}

fn commit(
    scripts: &mut ClientScripts<MemoryBlobs>,
    state: &mut GameState,
    update: InterfaceUpdate,
) {
    let diagnostics = scripts
        .commit(state, SemanticOperation::Interface(update))
        .unwrap();
    assert!(diagnostics.is_empty(), "{diagnostics:?}");
}

fn observed(state: &GameState) -> i32 {
    let Some(ScriptValue::Int(value)) = state.interfaces().client_variables().get(&7) else {
        panic!("expected observable callback result");
    };
    *value
}

fn mount(parent_component: u32, group: u32) -> InterfaceMount {
    InterfaceMount {
        parent: WidgetId::new(InterfaceId::new(1), parent_component),
        interface: InterfaceId::new(group),
        kind: InterfaceMountKind::Overlay,
    }
}

#[test]
fn explicit_same_root_open_runs_its_load_callback_again() {
    let (mut scripts, mut state) = setup();
    let open = InterfaceUpdate::SetRoot(Some(InterfaceId::new(1)));
    commit(&mut scripts, &mut state, open.clone());
    commit(&mut scripts, &mut state, open);
    assert_eq!(observed(&state), 11);
}

#[test]
fn explicit_same_subinterface_open_runs_its_load_callback_again() {
    let (mut scripts, mut state) = setup();
    commit(
        &mut scripts,
        &mut state,
        InterfaceUpdate::SetRoot(Some(InterfaceId::new(1))),
    );
    let open = InterfaceUpdate::Open(mount(0, 2));
    commit(&mut scripts, &mut state, open.clone());
    commit(&mut scripts, &mut state, open);
    assert_eq!(observed(&state), 122);
}

#[test]
fn resync_keeps_root_then_wire_mount_order_instead_of_numeric_group_order() {
    let (mut scripts, mut state) = setup();
    commit(
        &mut scripts,
        &mut state,
        InterfaceUpdate::Resync {
            root: Some(InterfaceId::new(3)),
            mounts: vec![
                InterfaceMount {
                    parent: WidgetId::new(InterfaceId::new(3), 0),
                    ..mount(0, 2)
                },
                InterfaceMount {
                    parent: WidgetId::new(InterfaceId::new(3), 1),
                    ..mount(1, 1)
                },
            ],
            events: vec![],
        },
    );
    assert_eq!(observed(&state), 321);
}

#[test]
fn unchanged_resync_does_not_reopen_retained_root_or_mounts() {
    let (mut scripts, mut state) = setup();
    let resync = InterfaceUpdate::Resync {
        root: Some(InterfaceId::new(1)),
        mounts: vec![mount(0, 2)],
        events: vec![],
    };
    commit(&mut scripts, &mut state, resync.clone());
    commit(&mut scripts, &mut state, resync);
    assert_eq!(observed(&state), 12);
}

#[test]
fn resync_opens_a_reused_group_at_a_new_parent_once() {
    let (mut scripts, mut state) = setup();
    commit(
        &mut scripts,
        &mut state,
        InterfaceUpdate::Resync {
            root: Some(InterfaceId::new(1)),
            mounts: vec![mount(0, 2)],
            events: vec![],
        },
    );
    commit(
        &mut scripts,
        &mut state,
        InterfaceUpdate::Resync {
            root: Some(InterfaceId::new(1)),
            mounts: vec![mount(0, 2), mount(1, 2)],
            events: vec![],
        },
    );
    assert_eq!(observed(&state), 122);
}

#[test]
fn moving_an_existing_mount_does_not_reopen_it() {
    let (mut scripts, mut state) = setup();
    commit(
        &mut scripts,
        &mut state,
        InterfaceUpdate::Resync {
            root: Some(InterfaceId::new(1)),
            mounts: vec![mount(0, 2)],
            events: vec![],
        },
    );
    commit(
        &mut scripts,
        &mut state,
        InterfaceUpdate::Move {
            source: mount(0, 2).parent,
            destination: mount(1, 2).parent,
        },
    );
    assert_eq!(observed(&state), 12);
}

#[test]
fn repeated_resource_synchronization_does_not_reopen_groups() {
    let (mut scripts, mut state) = setup();
    commit(
        &mut scripts,
        &mut state,
        InterfaceUpdate::SetRoot(Some(InterfaceId::new(1))),
    );
    scripts.synchronize(&state).unwrap();
    scripts.synchronize(&state).unwrap();
    assert!(scripts.drain(&mut state).is_empty());
    assert_eq!(observed(&state), 1);
}

#[test]
fn rejected_topology_cannot_queue_a_load_callback() {
    let (mut scripts, mut state) = setup();
    commit(
        &mut scripts,
        &mut state,
        InterfaceUpdate::SetRoot(Some(InterfaceId::new(1))),
    );
    let invalid = SemanticOperation::Interface(InterfaceUpdate::Open(mount(0, 1)));
    assert!(scripts.commit(&mut state, invalid).is_err());
    assert!(scripts.drain(&mut state).is_empty());
    assert_eq!(observed(&state), 1);
}

#[test]
fn initial_resync_preserves_two_opens_of_the_same_group() {
    let (mut scripts, mut state) = setup();
    commit(
        &mut scripts,
        &mut state,
        InterfaceUpdate::Resync {
            root: Some(InterfaceId::new(1)),
            mounts: vec![mount(0, 2), mount(1, 2)],
            events: vec![],
        },
    );
    assert_eq!(observed(&state), 122);
}

#[test]
fn group_loads_follow_component_index_even_if_definitions_arrive_out_of_order() {
    let (mut scripts, mut state) = setup();
    let group = InterfaceId::new(1);
    let first = scripts.interfaces[&group]
        .widgets()
        .values()
        .next()
        .unwrap()
        .clone();
    let mut second = first.clone();
    second.id = WidgetId::new(group, 1);
    second
        .listeners
        .insert(WidgetListener::Load, vec![ScriptValue::Int(90002)]);
    scripts.interfaces.insert(
        group,
        InterfaceDefinition::from_widgets(group, vec![second, first]).unwrap(),
    );
    commit(
        &mut scripts,
        &mut state,
        InterfaceUpdate::SetRoot(Some(group)),
    );
    assert_eq!(observed(&state), 12);
}
