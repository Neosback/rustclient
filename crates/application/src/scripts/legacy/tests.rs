use super::*;
use rustclient_cache::{
    GroupId, IndexId, Source, osrs240::widgets::decode_legacy, widget::InterfaceDefinition,
};
use rustclient_game_state::{SceneryOperation, SemanticOperation};
use rustclient_model::*;
fn setup() -> (
    ClientScripts<rustclient_platform::MemoryBlobs>,
    GameState,
    WidgetId,
    LegacyPresentation,
) {
    let scripts = super::super::test_support::scripts();
    let mut state = GameState::new();
    let id = WidgetId::new(InterfaceId::new(12), 3);
    state
        .apply_operation(SemanticOperation::BeginSession {
            revision: REVISION_240,
        })
        .unwrap();
    let (_, mut legacy) = decode_legacy(
        include_bytes!("../../../../cache/tests/fixtures/widgets/if1-3.bin"),
        id,
        Source::group(IndexId(3), GroupId(12)),
    )
    .unwrap();
    legacy.comparisons = vec![(1, 7)];
    legacy.expressions = vec![vec![5, 42, 0]];
    (scripts, state, id, legacy)
}
#[test]
fn committed_variables_select_paint_without_writing_widget_or_game_state() {
    let (mut scripts, mut state, id, legacy) = setup();
    let (mut widget, _) = decode_legacy(
        include_bytes!("../../../../cache/tests/fixtures/widgets/if1-3.bin"),
        id,
        Source::group(IndexId(3), GroupId(12)),
    )
    .unwrap();
    widget.parent = None;
    widget.hidden = false;
    scripts.interfaces.insert(
        id.interface,
        InterfaceDefinition::from_widgets(id.interface, vec![widget])
            .unwrap()
            .with_legacy(BTreeMap::from([(id, legacy)]))
            .unwrap(),
    );
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::SetRoot(
            Some(id.interface),
        )))
        .unwrap();
    let mut frame = UiFrame::default();
    for (value, tint) in [
        (0, [0x12, 0x34, 0x56, 191]),
        (7, [0xab, 0xcd, 0xef, 191]),
        (0, [0x12, 0x34, 0x56, 191]),
    ] {
        state
            .apply_operation(SemanticOperation::Scenery(SceneryOperation::SetVariable {
                id: VarpId::new(42),
                value,
            }))
            .unwrap();
        let before = state.semantic_fingerprint();
        frame.quads.clear();
        let result = scripts
            .project_widgets(&state, &WidgetVisualAssets::default(), &mut frame, None)
            .unwrap();
        assert!(result.diagnostics.is_empty());
        assert_eq!(frame.quads.last().unwrap().tint, tint);
        assert_eq!(state.semantic_fingerprint(), before);
        assert!(state.interfaces().runtime().is_empty());
    }
}
#[test]
fn alternate_selection_keeps_live_shared_fields_and_empty_text_uses_primary() {
    let (scripts, state, id, mut legacy) = setup();
    legacy.expressions = vec![vec![20, 7, 0], vec![20, 42, 0]];
    legacy.alternate = Some(WidgetBody::Text {
        font: None,
        text: String::new(),
        line_height: 0,
        alignment: [0; 2],
        shadowed: false,
        color: 0x0012_3456,
    });
    let mut widget = rustclient_cache::osrs240::widgets::decode(
        include_bytes!("../../../../cache/tests/fixtures/widgets/text.bin"),
        id,
        Source::group(IndexId(3), GroupId(12)),
    )
    .unwrap();
    widget.body = WidgetBody::Text {
        font: Some(FontId::new(123)),
        text: "live %2".into(),
        line_height: 18,
        alignment: [2, 1],
        shadowed: true,
        color: 0,
    };
    let mut errors = Vec::new();
    scripts
        .present_legacy(
            &state,
            (widget.id, &mut widget.body),
            &legacy,
            &mut errors,
            &mut 1_000_000,
        )
        .unwrap();
    assert!(
        matches!(widget.body,WidgetBody::Text{font:Some(font),ref text,line_height:18,alignment:[2,1],shadowed:true,color:0x0012_3456} if font.get()==123 && text=="live 42")
    );
    assert!(errors.is_empty());
}
#[test]
fn unavailable_facts_use_source_failure_value_with_visible_diagnostics() {
    let (scripts, state, id, mut legacy) = setup();
    legacy.comparisons = vec![(2, 0)];
    legacy.expressions = vec![vec![1, 23, 0]];
    let (mut widget, _) = decode_legacy(
        include_bytes!("../../../../cache/tests/fixtures/widgets/if1-3.bin"),
        id,
        Source::group(IndexId(3), GroupId(12)),
    )
    .unwrap();
    let mut errors = Vec::new();
    scripts
        .present_legacy(
            &state,
            (widget.id, &mut widget.body),
            &legacy,
            &mut errors,
            &mut 1_000_000,
        )
        .unwrap();
    assert_eq!(errors.len(), 1);
    assert!(errors[0].contains("not been received"));
    assert!(matches!(
        widget.body,
        WidgetBody::Rectangle {
            color: 0x00ab_cdef,
            ..
        }
    ));
    assert!(
        scripts
            .cs1_query(
                &state,
                Query::WidgetItem {
                    widget: id,
                    item: Some(ItemDefinitionId::new(1)),
                    presence: false
                }
            )
            .is_err()
    );
}
#[test]
fn real_skill_levels_cap_at_99_and_cs1_energy_keeps_raw_units() {
    let (mut scripts, mut state, _, _) = setup();
    state
        .apply_operation(SemanticOperation::PlayerStatus(PlayerStatusUpdate::Skill {
            id: SkillId::new(0),
            values: SkillValues {
                experience: 200_000_000,
                current: 120,
                invisible: 0,
            },
        }))
        .unwrap();
    state
        .apply_operation(SemanticOperation::PlayerStatus(
            PlayerStatusUpdate::RunEnergy(1234),
        ))
        .unwrap();
    assert_eq!(
        scripts
            .cs1_query(&state, Query::BaseLevel(SkillId::new(0)))
            .unwrap(),
        99
    );
    assert_eq!(
        scripts
            .cs1_query(&state, Query::CurrentLevel(SkillId::new(0)))
            .unwrap(),
        120
    );
    assert_eq!(scripts.cs1_query(&state, Query::RunEnergy).unwrap(), 1234);
    let mut host = super::super::ScriptHost {
        resources: &mut scripts,
        state: &mut state,
    };
    let mut stacks = rustclient_client_script::Stacks::default();
    stacks.integers.push(0);
    rustclient_client_script::Host::instruction(
        &mut host,
        rustclient_cache::script::Opcode::StatBase,
        0,
        &mut stacks,
        &mut rustclient_client_script::Context::default(),
    )
    .unwrap();
    assert_eq!(stacks.int().unwrap(), 99);
    rustclient_client_script::Host::instruction(
        &mut host,
        rustclient_cache::script::Opcode::RunenergyVisible,
        0,
        &mut stacks,
        &mut rustclient_client_script::Context::default(),
    )
    .unwrap();
    assert_eq!(stacks.int().unwrap(), 12);
}

#[test]
fn cs1_coordinates_follow_committed_fine_position_before_the_queued_endpoint() {
    let (scripts, _, _, _) = setup();
    let mut state = GameState::new();
    state
        .apply_operation(SemanticOperation::AuthenticateSession {
            revision: REVISION_240,
            player: rustclient_game_state::AuthenticatedPlayer {
                profile: ServerProfileId::new(1),
                player: PlayerIndex::new(1),
                member: false,
                staff_level: 0,
            },
        })
        .unwrap();
    let tile = WorldTile::new(3200, 3200, Plane::MIN);
    state
        .apply_operation(SemanticOperation::EstablishInitialPlacement(
            rustclient_game_state::InitialPlacement {
                position: tile,
                map_anchor: tile,
                area: None,
            },
        ))
        .unwrap();
    state
        .apply_operation(SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
            local_movement: Some(PlayerMovement::Step { direction: 4 }),
            ..Default::default()
        }))
        .unwrap();
    assert_eq!(state.entities().local_player().unwrap().tile.x, 3201);
    assert_eq!(scripts.cs1_query(&state, Query::WorldX).unwrap(), 3200);
    assert_eq!(scripts.cs1_query(&state, Query::WorldY).unwrap(), 3200);
}

#[test]
fn alternate_sprite_absence_stays_absent_and_asset_requests_cover_both_choices() {
    let (mut scripts, mut state, id, mut legacy) = setup();
    let mut widget = rustclient_cache::osrs240::widgets::decode(
        include_bytes!("../../../../cache/tests/fixtures/widgets/sprite.bin"),
        id,
        Source::group(IndexId(3), GroupId(12)),
    )
    .unwrap();
    widget.parent = None;
    widget.hidden = false;
    let mut absent = widget.body.clone();
    let mut alternate = widget.body.clone();
    if let WidgetBody::Sprite { sprite, .. } = &mut alternate {
        *sprite = None;
    }
    select_body(&mut absent, &alternate);
    assert!(matches!(absent, WidgetBody::Sprite { sprite: None, .. }));
    if let WidgetBody::Sprite { sprite, .. } = &mut alternate {
        *sprite = Some(SpriteId::new(2345));
    }
    legacy.alternate = Some(alternate);
    scripts.interfaces.insert(
        id.interface,
        InterfaceDefinition::from_widgets(id.interface, vec![widget])
            .unwrap()
            .with_legacy(BTreeMap::from([(id, legacy)]))
            .unwrap(),
    );
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::SetRoot(
            Some(id.interface),
        )))
        .unwrap();
    let request = scripts.visual_request(&state).unwrap();
    assert!(request.sprites.contains(&SpriteId::new(1234)));
    assert!(request.sprites.contains(&SpriteId::new(2345)));
}
