use super::{super::ClientScripts, *};
use rustclient_game_state::GameState;
use rustclient_platform::MemoryBlobs;

use super::super::test_support::scripts;
fn state() -> GameState {
    let mut state = GameState::new();
    state
        .apply_operation(SemanticOperation::BeginSession {
            revision: rustclient_model::Revision::with_subrevision(240, 2),
        })
        .unwrap();
    state
}
fn run(
    scripts: &mut ClientScripts<MemoryBlobs>,
    state: &mut GameState,
    op: Opcode,
    ints: &[i32],
) -> Result<Vec<i32>> {
    let mut stacks = Stacks::default();
    stacks.integers.extend_from_slice(ints);
    let mut host = ScriptHost {
        resources: scripts,
        state,
    };
    instruction(&mut host, op, 0, &mut stacks)?;
    Ok(stacks.integers)
}

#[test]
fn cache_chat_history_walk_visits_all_rows_in_both_directions() {
    let (mut scripts, mut state) = (scripts(), state());
    for (channel, text) in [
        (2, "first"),
        (0, "game message"),
        (2, "second"),
        (2, "third"),
    ] {
        state
            .apply_operation(SemanticOperation::GameMessage(
                rustclient_model::GameMessage {
                    channel: rustclient_model::ids::ChatChannelId::new(channel),
                    sender: "Test".into(),
                    text: text.into(),
                },
            ))
            .unwrap();
    }
    assert_eq!(
        run(&mut scripts, &mut state, Opcode::ChatGethistorylength, &[2]).unwrap(),
        [3]
    );
    let mut uid = 3;
    let mut rows = Vec::new();
    while uid != -1 {
        rows.push(uid);
        uid = run(&mut scripts, &mut state, Opcode::ChatGetprevuid, &[uid]).unwrap()[0];
    }
    assert_eq!(rows, [3, 2, 1, 0]);
    assert_eq!(
        run(&mut scripts, &mut state, Opcode::ChatGetnextuid, &[0]).unwrap(),
        [1]
    );
    assert_eq!(
        run(&mut scripts, &mut state, Opcode::ChatGetnextuid, &[3]).unwrap(),
        [-1]
    );
    assert_eq!(
        run(&mut scripts, &mut state, Opcode::ChatGetprevuid, &[99]).unwrap(),
        [-1]
    );
}

#[test]
fn game_option_1_is_the_roof_toggle_owned_by_interface_settings() {
    let (mut scripts, mut state) = (scripts(), state());
    let get = |s: &mut ClientScripts<_>, st: &mut GameState| {
        run(s, st, Opcode::GetGameOption, &[1]).unwrap()
    };
    assert_eq!(get(&mut scripts, &mut state), [0]);
    assert!(
        run(&mut scripts, &mut state, Opcode::SetGameOption, &[1, 1])
            .unwrap()
            .is_empty()
    );
    assert_eq!(get(&mut scripts, &mut state), [1]);
    assert_eq!(
        state
            .interfaces()
            .settings()
            .get(&ClientSetting::RoofsHidden),
        Some(&true)
    );
    run(&mut scripts, &mut state, Opcode::SetGameOption, &[1, 0]).unwrap();
    assert_eq!(get(&mut scripts, &mut state), [0]);
}

#[test]
fn device_option_6_is_a_clamped_numeric_scene_brightness() {
    let (mut scripts, mut state) = (scripts(), state());
    assert_eq!(
        run(&mut scripts, &mut state, Opcode::GetDeviceOption, &[6]).unwrap(),
        [40]
    );
    for (value, expected) in [(-1, 0), (35, 35), (80, 80), (150, 100)] {
        run(
            &mut scripts,
            &mut state,
            Opcode::SetDeviceOption,
            &[6, value],
        )
        .unwrap();
        assert_eq!(
            run(&mut scripts, &mut state, Opcode::GetDeviceOption, &[6]).unwrap(),
            [expected]
        );
        assert_eq!(
            i32::from(state.interfaces().brightness().percent()),
            expected
        );
    }
    run(&mut scripts, &mut state, Opcode::Setbrightness, &[25]).unwrap();
    assert_eq!(
        run(&mut scripts, &mut state, Opcode::GetDeviceOption, &[6]).unwrap(),
        [25]
    );
    assert_eq!(
        run(&mut scripts, &mut state, Opcode::Getbrightness, &[]).unwrap(),
        [25]
    );
}

#[test]
fn numeric_device_preferences_default_then_round_trip_without_changing_other_options() {
    let (mut scripts, mut state) = (scripts(), state());
    let get = |s: &mut ClientScripts<_>, st: &mut GameState, id: i32| {
        run(s, st, Opcode::GetDeviceOption, &[id]).unwrap()
    };
    assert_eq!(get(&mut scripts, &mut state, 14), [25]);
    assert_eq!(get(&mut scripts, &mut state, 26), [0]);
    run(&mut scripts, &mut state, Opcode::SetDeviceOption, &[14, 70]).unwrap();
    run(&mut scripts, &mut state, Opcode::SetDeviceOption, &[26, -3]).unwrap();
    assert_eq!(get(&mut scripts, &mut state, 14), [70]);
    assert_eq!(get(&mut scripts, &mut state, 26), [-3]);
    assert_eq!(get(&mut scripts, &mut state, 5), [0]);
    assert_eq!(
        state.interfaces().device_preferences().get(&14),
        Some(&70),
        "preferences live in the interface state owner"
    );
}

#[test]
fn minimap_zoom_ops_follow_the_zoomable_flag() {
    let (mut scripts, mut state) = (scripts(), state());
    let before = state.semantic_fingerprint();
    // Not zoomable yet: a script's zoom is ignored and the default reads back.
    run(&mut scripts, &mut state, Opcode::MinimapSetzoom, &[7]).unwrap();
    assert_eq!(
        run(&mut scripts, &mut state, Opcode::MinimapGetzoom, &[]).unwrap(),
        [4]
    );
    assert_eq!(state.semantic_fingerprint(), before);
    run(&mut scripts, &mut state, Opcode::MinimapSetzoomable, &[1]).unwrap();
    run(&mut scripts, &mut state, Opcode::MinimapSetzoom, &[7]).unwrap();
    assert_eq!(
        run(&mut scripts, &mut state, Opcode::MinimapGetzoom, &[]).unwrap(),
        [7]
    );
    run(&mut scripts, &mut state, Opcode::MinimapSetzoom, &[99]).unwrap();
    assert_eq!(
        run(&mut scripts, &mut state, Opcode::MinimapGetzoom, &[]).unwrap(),
        [8]
    );
    run(
        &mut scripts,
        &mut state,
        Opcode::MinimapSeticonzoomlimit,
        &[3],
    )
    .unwrap();
    assert_eq!(state.interfaces().minimap().icon_zoom_limit, 3);
    assert_ne!(state.semantic_fingerprint(), before);
    run(&mut scripts, &mut state, Opcode::MinimapSetzoomable, &[0]).unwrap();
    assert_eq!(
        run(&mut scripts, &mut state, Opcode::MinimapGetzoom, &[]).unwrap(),
        [4],
        "toggling zoomability restores the default scale"
    );
}

#[test]
fn renderself_consumes_its_flag_without_changing_state() {
    let (mut scripts, mut state) = (scripts(), state());
    let before = state.semantic_fingerprint();
    assert!(
        run(&mut scripts, &mut state, Opcode::Renderself, &[1])
            .unwrap()
            .is_empty()
    );
    assert!(run(&mut scripts, &mut state, Opcode::Renderself, &[]).is_err());
    assert_eq!(state.semantic_fingerprint(), before);
}

#[test]
fn camera_angles_come_from_the_host_and_commands_queue_one_intent() {
    let (mut scripts, mut state) = (scripts(), state());
    scripts.set_camera_angles(200, 1024);
    assert_eq!(
        run(&mut scripts, &mut state, Opcode::CamGetangleXa, &[]).unwrap(),
        [200]
    );
    assert_eq!(
        run(&mut scripts, &mut state, Opcode::CamGetangleYa, &[]).unwrap(),
        [1024]
    );
    let mut stacks = Stacks::default();
    stacks.push_object(ScriptValue::Text("tele 1 2".into()));
    let mut host = ScriptHost {
        resources: &mut scripts,
        state: &mut state,
    };
    instruction(&mut host, Opcode::Docheat, 0, &mut stacks).unwrap();
    assert_eq!(
        scripts.take_intents().len(),
        1,
        "a command becomes one outbound chat intent"
    );
}

/// Run one instruction against fresh stacks seeded with `ints` and `texts` (push order).
fn run_with_text(
    scripts: &mut ClientScripts<MemoryBlobs>,
    state: &mut GameState,
    op: Opcode,
    ints: &[i32],
    texts: &[&str],
) -> Result<Stacks> {
    let mut stacks = Stacks::default();
    stacks.integers.extend_from_slice(ints);
    for text in texts {
        stacks.push_object(ScriptValue::Text((*text).into()));
    }
    let mut host = ScriptHost {
        resources: scripts,
        state,
    };
    instruction(&mut host, op, 0, &mut stacks)?;
    Ok(stacks)
}

#[test]
fn settings_text_ops_follow_the_reference_stack_effects() {
    let (mut scripts, mut state) = (scripts(), state());
    let mut pick = |condition| {
        let mut out = run_with_text(
            &mut scripts,
            &mut state,
            Opcode::TextSwitch,
            &[condition],
            &["first", "second"],
        )
        .unwrap();
        out.text().unwrap()
    };
    assert_eq!(pick(1), "first");
    assert_eq!(pick(0), "second");
    let range = |scripts: &mut ClientScripts<MemoryBlobs>, state: &mut GameState, option| {
        run_with_text(scripts, state, Opcode::DeviceoptionGetrange, &[option], &[])
            .map(|out| out.integers)
    };
    assert_eq!(range(&mut scripts, &mut state, 14).unwrap(), [25, 90]);
    assert_eq!(range(&mut scripts, &mut state, 3).unwrap(), [0, 1]);
    assert_eq!(range(&mut scripts, &mut state, 20).unwrap(), [100, 400]);
    assert!(range(&mut scripts, &mut state, 99).is_err());
    assert!(
        run_with_text(
            &mut scripts,
            &mut state,
            Opcode::CcFieldConfig(1137),
            &[5],
            &[]
        )
        .unwrap()
        .integers
        .is_empty(),
        "field configuration consumes its operand and pushes nothing"
    );
}

#[test]
fn absent_definitions_answer_defaults_like_the_reference_client() {
    let (mut scripts, mut state) = (scripts(), state());
    let mut run =
        |op, ints: &[i32]| run_with_text(&mut scripts, &mut state, op, ints, &[]).unwrap();
    // An enum that does not exist never matches the requested types: int outputs answer 0.
    assert_eq!(run(Opcode::Enum, &[73, 73, -1, 5]).integers, [0]);
    let mut text = run(Opcode::Enum, &[73, i32::from(b's'), -1, 5]);
    assert_eq!(text.text().unwrap(), "null");
    assert_eq!(run(Opcode::EnumGetoutputcount, &[-1]).integers, [0]);
    // An absent item keeps its id through the note ops and is not stackable.
    assert_eq!(run(Opcode::OcCert, &[-1]).integers, [-1]);
    assert_eq!(run(Opcode::OcUncert, &[-1]).integers, [-1]);
    assert_eq!(run(Opcode::OcStackable, &[-1]).integers, [0]);
}

#[test]
fn random_ops_stay_inside_their_bound_and_follow_the_seed() {
    let (mut scripts, mut state) = (scripts(), state());
    let mut draw = |scripts: &mut ClientScripts<MemoryBlobs>, op, bound| {
        run_with_text(scripts, &mut state, op, &[bound], &[])
            .unwrap()
            .integers[0]
    };
    let first: Vec<i32> = (0..64)
        .map(|_| draw(&mut scripts, Opcode::Random, 6))
        .collect();
    assert!(first.iter().all(|n| (0..6).contains(n)));
    assert!(
        first
            .iter()
            .collect::<std::collections::BTreeSet<_>>()
            .len()
            > 1
    );
    assert!((0..16).all(|_| draw(&mut scripts, Opcode::Randominc, 2) <= 2));
    assert_eq!(
        draw(&mut scripts, Opcode::Random, 0),
        0,
        "an empty range is 0"
    );
    scripts.seed_random(crate::RandomSeed::new(7));
    let seeded: Vec<i32> = (0..8)
        .map(|_| draw(&mut scripts, Opcode::Random, 1000))
        .collect();
    scripts.seed_random(crate::RandomSeed::new(7));
    let again: Vec<i32> = (0..8)
        .map(|_| draw(&mut scripts, Opcode::Random, 1000))
        .collect();
    assert_eq!(seeded, again);
}

#[test]
fn string_vectors_add_dedupe_remove_and_clear() {
    let (mut scripts, mut state) = (scripts(), state());
    let mut call = |op, ints: &[i32], texts: &[&str]| {
        run_with_text(&mut scripts, &mut state, op, ints, texts).unwrap()
    };
    call(Opcode::StringvectorAdd, &[7], &["Alpha"]);
    call(Opcode::StringvectorAddunique, &[7, 1], &["alpha"]);
    call(Opcode::StringvectorAddunique, &[7, 0], &["alpha"]);
    assert_eq!(call(Opcode::StringvectorSize, &[7], &[]).integers, [2]);
    assert_eq!(
        call(Opcode::StringvectorGet, &[7, 1], &[]).text().unwrap(),
        "alpha"
    );
    assert_eq!(
        call(Opcode::StringvectorGet, &[7, 9], &[]).text().unwrap(),
        "",
        "an index past the end reads as the empty string"
    );
    assert_eq!(
        call(Opcode::StringvectorContains, &[7, 1, 0], &["ALPHA"]).integers,
        [1]
    );
    assert_eq!(
        call(Opcode::StringvectorContains, &[7, 0, 0], &["ALPHA"]).integers,
        [0]
    );
    call(Opcode::StringvectorRemove, &[7, 1], &["alpha"]);
    assert_eq!(call(Opcode::StringvectorSize, &[7], &[]).integers, [0]);
    call(Opcode::StringvectorAdd, &[7], &["x"]);
    call(Opcode::StringvectorClear, &[7], &[]);
    assert_eq!(call(Opcode::StringvectorSize, &[7], &[]).integers, [0]);
}

#[test]
fn mes_files_a_senderless_game_message() {
    let (mut scripts, mut state) = (scripts(), state());
    let mut stacks = Stacks::default();
    stacks.push_object(ScriptValue::Text("That setting is locked.".into()));
    let mut host = ScriptHost {
        resources: &mut scripts,
        state: &mut state,
    };
    instruction(&mut host, Opcode::Mes, 0, &mut stacks).unwrap();
    let entries = state.chat().entries();
    assert_eq!(entries.len(), 1);
    assert_eq!(entries[0].message.text, "That setting is locked.");
}

#[test]
fn prompt_answers_queue_one_resume_intent_each() {
    use rustclient_protocol::{ClientIntent, ResumeIntent};
    let (mut scripts, mut state) = (scripts(), state());
    for (op, text, expected) in [
        (Opcode::ResumeCountdialog, "1234", ResumeIntent::Count(1234)),
        (Opcode::ResumeCountdialog, "12k", ResumeIntent::Count(0)),
        (
            Opcode::ResumeNamedialog,
            "Zezima",
            ResumeIntent::Name("Zezima".into()),
        ),
        (
            Opcode::ResumeStringdialog,
            "hello",
            ResumeIntent::Text("hello".into()),
        ),
    ] {
        let mut stacks = Stacks::default();
        stacks.push_object(ScriptValue::Text(text.into()));
        let mut host = ScriptHost {
            resources: &mut scripts,
            state: &mut state,
        };
        instruction(&mut host, op, 0, &mut stacks).unwrap();
        let intents = scripts.take_intents();
        assert!(matches!(
            intents.as_slice(),
            [ClientIntent::Resume(resume)] if *resume == expected
        ));
    }
    assert!(
        run(
            &mut scripts,
            &mut state,
            Opcode::ResumeObjdialog,
            &[0x1_0005]
        )
        .unwrap()
        .is_empty()
    );
    assert!(matches!(
        scripts.take_intents().as_slice(),
        [ClientIntent::Resume(ResumeIntent::Item(5))]
    ));
}

#[test]
fn public_chat_needs_a_valid_type_and_the_cache_code_table() {
    let (mut scripts, mut state) = (scripts(), state());
    let mut stacks = Stacks::default();
    stacks.push_object(ScriptValue::Text("hello".into()));
    stacks.integers.push(300);
    let mut host = ScriptHost {
        resources: &mut scripts,
        state: &mut state,
    };
    assert!(instruction(&mut host, Opcode::ChatSendpublic, 0, &mut stacks).is_err());
    assert!(scripts.take_intents().is_empty());
}

#[test]
fn volume_options_keep_their_range_and_unknown_options_fail() {
    let (mut scripts, mut state) = (scripts(), state());
    run(&mut scripts, &mut state, Opcode::SetGameOption, &[7, 40]).unwrap();
    assert_eq!(
        run(&mut scripts, &mut state, Opcode::GetGameOption, &[7]).unwrap(),
        [40]
    );
    assert!(run(&mut scripts, &mut state, Opcode::SetGameOption, &[7, 101]).is_err());
    assert!(run(&mut scripts, &mut state, Opcode::SetDeviceOption, &[19, -1]).is_err());
    assert!(run(&mut scripts, &mut state, Opcode::GetGameOption, &[2]).is_err());
    assert!(run(&mut scripts, &mut state, Opcode::GetDeviceOption, &[99]).is_err());
}

#[test]
fn sound_synth_queues_bounded_requests_in_order() {
    let (mut scripts, mut state) = (scripts(), state());
    run(&mut scripts, &mut state, Opcode::SoundSynth, &[2266, 1, 0]).unwrap();
    run(&mut scripts, &mut state, Opcode::SoundSynth, &[10, 0, 0]).unwrap();
    run(&mut scripts, &mut state, Opcode::SoundSynth, &[3809, 2, 30]).unwrap();
    assert_eq!(
        scripts.take_sound_effects(),
        [
            super::super::SoundEffectRequest {
                id: 2266,
                loops: 1,
                delay: 0
            },
            super::super::SoundEffectRequest {
                id: 3809,
                loops: 2,
                delay: 30
            },
        ]
    );
    assert!(scripts.take_sound_effects().is_empty());
    for _ in 0..60 {
        run(&mut scripts, &mut state, Opcode::SoundSynth, &[1, 1, 0]).unwrap();
    }
    assert_eq!(scripts.take_sound_effects().len(), 50);
    run(&mut scripts, &mut state, Opcode::SoundSynth, &[1, 1, 0]).unwrap();
    scripts.reset();
    assert!(scripts.take_sound_effects().is_empty());
    assert!(run(&mut scripts, &mut state, Opcode::SoundSynth, &[-1, 1, 0]).is_err());
}

#[test]
fn invother_reads_the_containers_at_the_other_inventory_offset() {
    let (mut scripts, mut state) = (scripts(), state());
    let stack = |item, amount| {
        Some(rustclient_model::ItemStack {
            item: rustclient_model::ItemDefinitionId::new(item),
            amount,
        })
    };
    for (id, slots) in [
        (7, vec![stack(995, 3)]),
        (7 + 32_768, vec![None, stack(995, 40)]),
    ] {
        state
            .apply_operation(SemanticOperation::Inventory(
                rustclient_model::InventoryUpdate::Replace {
                    id: InventoryId::new(id),
                    slots,
                },
            ))
            .unwrap();
    }
    assert_eq!(
        run(&mut scripts, &mut state, Opcode::InvGetnum, &[7, 0]).unwrap(),
        [3]
    );
    assert_eq!(
        run(&mut scripts, &mut state, Opcode::InvotherGetobj, &[7, 1]).unwrap(),
        [995]
    );
    assert_eq!(
        run(&mut scripts, &mut state, Opcode::InvotherGetnum, &[7, 1]).unwrap(),
        [40]
    );
    assert_eq!(
        run(&mut scripts, &mut state, Opcode::InvotherTotal, &[7, 995]).unwrap(),
        [40]
    );
    assert_eq!(
        run(&mut scripts, &mut state, Opcode::InvotherGetobj, &[7, 0]).unwrap(),
        [-1]
    );
    assert!(
        run(
            &mut scripts,
            &mut state,
            Opcode::InvotherTotal,
            &[i32::MAX, 1]
        )
        .is_err()
    );
}

#[test]
fn friend_and_clan_queries_report_the_unloaded_social_state() {
    let (mut scripts, mut state) = (scripts(), state());
    assert_eq!(
        run(&mut scripts, &mut state, Opcode::FriendCount, &[]).unwrap(),
        [-2]
    );
    assert_eq!(
        run(
            &mut scripts,
            &mut state,
            Opcode::ActiveclansettingsFindAffined,
            &[0]
        )
        .unwrap(),
        [0]
    );
    assert_eq!(
        run(
            &mut scripts,
            &mut state,
            Opcode::ActiveclansettingsFindListened,
            &[]
        )
        .unwrap(),
        [0]
    );
    assert!(
        run(
            &mut scripts,
            &mut state,
            Opcode::ActiveclansettingsFindAffined,
            &[]
        )
        .is_err()
    );
}

#[test]
fn camera_key_speeds_take_their_pair_and_leave_nothing() {
    let (mut scripts, mut state) = (scripts(), state());
    let out = run(
        &mut scripts,
        &mut state,
        Opcode::CamSetkeypanspeeds,
        &[3, 9],
    )
    .unwrap();
    assert!(out.is_empty());
    assert!(run(&mut scripts, &mut state, Opcode::CamSetkeypanspeeds, &[3]).is_err());
}

#[test]
fn a_negative_npc_id_names_the_default_definition() {
    let (mut scripts, mut state) = (scripts(), state());
    let mut stacks = Stacks::default();
    stacks.integers.push(-1);
    let mut host = ScriptHost {
        resources: &mut scripts,
        state: &mut state,
    };
    assert!(instruction(&mut host, Opcode::NcName, 0, &mut stacks).unwrap());
    assert_eq!(stacks.text().unwrap(), "null");
}

#[test]
fn invother_ops_read_the_other_player_inventory_offset() {
    let (mut scripts, mut state) = (scripts(), state());
    let stack = |item, amount| {
        Some(rustclient_model::ItemStack {
            item: rustclient_model::ItemDefinitionId::new(item),
            amount,
        })
    };
    state
        .apply_operation(SemanticOperation::Inventory(
            rustclient_model::InventoryUpdate::Replace {
                id: InventoryId::new(93 + 32_768),
                slots: vec![stack(995, 50), None, stack(995, 7)],
            },
        ))
        .unwrap();
    let mut ask = |op, ints: &[i32]| run(&mut scripts, &mut state, op, ints).unwrap();
    assert_eq!(ask(Opcode::InvotherGetobj, &[93, 0]), [995]);
    assert_eq!(ask(Opcode::InvotherGetobj, &[93, 1]), [-1]);
    assert_eq!(ask(Opcode::InvotherGetnum, &[93, 2]), [7]);
    assert_eq!(ask(Opcode::InvotherTotal, &[93, 995]), [57]);
    // The local inventory with the same id is a different container.
    assert_eq!(ask(Opcode::InvGetobj, &[93, 0]), [-1]);
}
