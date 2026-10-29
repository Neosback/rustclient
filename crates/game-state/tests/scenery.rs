use rustclient_game_state::{
    GameState, OperationError, ReplayEnvelope, SceneryError, SceneryOperation, SemanticOperation,
};
use rustclient_model::{
    AnimationStart, ClientCycle, LocationChange, LocationKey, LocationLayer, Plane, REVISION_240,
    SequenceId, VarpId, WorldTile,
};
#[test]
fn scenery_inputs_replay_reset_and_do_not_depend_on_projection_state() {
    let key = LocationKey {
        tile: WorldTile::new(3221, 3218, Plane::MIN),
        layer: LocationLayer::GameObject,
    };
    let operations = [
        SemanticOperation::BeginSession {
            revision: REVISION_240,
        },
        SemanticOperation::Scenery(SceneryOperation::SetVariable {
            id: VarpId::new(7),
            value: -31,
        }),
        SemanticOperation::Scenery(SceneryOperation::AdvanceCycles(123)),
        SemanticOperation::Scenery(SceneryOperation::ChangeLocation {
            key,
            change: LocationChange::Remove,
        }),
        SemanticOperation::Scenery(SceneryOperation::AnimateLocation {
            key,
            start: Some(AnimationStart {
                placement: None,
                sequence: Some(SequenceId::new(473)),
                cycle: ClientCycle::new(120),
                random_seed: 7,
            }),
        }),
    ];
    let mut state = GameState::new();
    let mut recording = Vec::new();
    for op in operations {
        recording.push(ReplayEnvelope::new(
            op.clone(),
            state.apply_operation(op).unwrap(),
        ));
    }
    assert_eq!(state.scenery().cycle(), ClientCycle::new(123));
    assert_eq!(state.scenery().variable(VarpId::new(7)), -31);
    let mut replay = GameState::new();
    replay.replay_envelopes(&recording).unwrap();
    assert_eq!(replay.semantic_fingerprint(), state.semantic_fingerprint());
    let before = state.semantic_fingerprint();
    assert_eq!(
        state.apply_operation(SemanticOperation::Scenery(
            SceneryOperation::AnimateLocation {
                key,
                start: Some(AnimationStart {
                    placement: None,
                    sequence: None,
                    cycle: ClientCycle::new(124),
                    random_seed: 0
                })
            }
        )),
        Err(OperationError::Scenery(SceneryError::FutureAnimation))
    );
    assert_eq!(state.semantic_fingerprint(), before);
    state
        .apply_operation(SemanticOperation::Scenery(SceneryOperation::ResetVariables))
        .unwrap();
    assert_eq!(state.scenery().variable(VarpId::new(7)), 0);
    state
        .apply_operation(SemanticOperation::EndSession)
        .unwrap();
    assert!(state.scenery().locations().is_empty());
    assert_eq!(state.scenery().cycle(), ClientCycle::ZERO);
    assert_eq!(
        state.semantic_fingerprint(),
        GameState::new().semantic_fingerprint()
    );
}

#[test]
#[expect(
    clippy::too_many_lines,
    reason = "one recorded location trigger, invalid placement, stop and plane-bounded reset contract"
)]
fn network_animation_uses_current_anchor_cycle_placement_and_area_reset_replays() {
    use rustclient_game_state::{AuthenticatedPlayer, InitialPlacement};
    use rustclient_model::{
        LocOrientation, LocationAnimationPlacement, PlayerIndex, ServerProfileId, TileOffset,
    };
    let mut state = GameState::new();
    let mut recording = Vec::new();
    let mut apply = |state: &mut GameState, operation: SemanticOperation| {
        let commit = state.apply_operation(operation.clone()).unwrap();
        recording.push(ReplayEnvelope::new(operation, commit));
    };
    apply(
        &mut state,
        SemanticOperation::AuthenticateSession {
            revision: REVISION_240,
            player: AuthenticatedPlayer {
                profile: ServerProfileId::new(1),
                player: PlayerIndex::new(1),
                member: false,
                staff_level: 0,
            },
        },
    );
    apply(
        &mut state,
        SemanticOperation::EstablishInitialPlacement(InitialPlacement {
            position: WorldTile::new(3220, 3220, Plane::MIN),
            map_anchor: WorldTile::new(3216, 3216, Plane::MIN),
            area: None,
        }),
    );
    apply(
        &mut state,
        SemanticOperation::Scenery(SceneryOperation::AdvanceCycles(123)),
    );
    let placement = LocationAnimationPlacement {
        shape: 22,
        orientation: LocOrientation::new(3).unwrap(),
    };
    let trigger = SceneryOperation::TriggerLocationAnimation {
        offset: TileOffset { x: 2, y: -1 },
        plane: Some(Plane::MAX),
        placement,
        sequence: Some(SequenceId::new(808)),
    };
    apply(&mut state, SemanticOperation::Scenery(trigger));
    let key = LocationKey::new(
        WorldTile::new(3218, 3215, Plane::MAX),
        LocationLayer::FloorDecoration,
    );
    let start = state.scenery().locations()[&key].animation.unwrap();
    assert_eq!(start.cycle, ClientCycle::new(123));
    assert_eq!(start.placement, Some(placement));
    let before = state.semantic_fingerprint();
    assert!(
        state
            .apply_operation(SemanticOperation::Scenery(
                SceneryOperation::AnimateLocation {
                    key,
                    start: Some(AnimationStart {
                        placement: Some(LocationAnimationPlacement {
                            shape: 10,
                            ..placement
                        }),
                        ..start
                    })
                }
            ))
            .is_err()
    );
    assert_eq!(state.semantic_fingerprint(), before);
    apply(
        &mut state,
        SemanticOperation::Scenery(SceneryOperation::TriggerLocationAnimation {
            sequence: None,
            offset: TileOffset { x: 2, y: -1 },
            plane: Some(Plane::MAX),
            placement,
        }),
    );
    assert_eq!(
        state.scenery().locations()[&key]
            .animation
            .unwrap()
            .sequence,
        None
    );
    apply(
        &mut state,
        SemanticOperation::Scenery(SceneryOperation::ResetLocationArea {
            offset: TileOffset { x: 0, y: -8 },
            plane: Plane::MIN,
            size: [8, 8],
        }),
    );
    assert!(
        state.scenery().locations().contains_key(&key),
        "other plane is retained"
    );
    apply(
        &mut state,
        SemanticOperation::Scenery(SceneryOperation::ResetLocationArea {
            offset: TileOffset { x: 0, y: -8 },
            plane: Plane::MAX,
            size: [8, 8],
        }),
    );
    assert!(state.scenery().locations().is_empty());
    let mut replay = GameState::new();
    replay.replay_envelopes(&recording).unwrap();
    assert_eq!(replay.semantic_fingerprint(), state.semantic_fingerprint());
}

#[test]
#[expect(
    clippy::too_many_lines,
    reason = "one placement replacement/options/replay/reset contract"
)]
fn server_location_replacement_retains_actions_clears_animation_and_replays_atomically() {
    use rustclient_game_state::{AuthenticatedPlayer, InitialPlacement};
    use rustclient_model::{
        ActionMask, LocOrientation, LocationAnimationPlacement, LocationOptions,
        ObjectDefinitionId, PlayerIndex, ServerProfileId, TileOffset,
    };
    let mut state = GameState::new();
    let mut recording = Vec::new();
    let mut apply = |state: &mut GameState, op: SemanticOperation| {
        let commit = state.apply_operation(op.clone()).unwrap();
        recording.push(ReplayEnvelope::new(op, commit));
    };
    apply(
        &mut state,
        SemanticOperation::AuthenticateSession {
            revision: REVISION_240,
            player: AuthenticatedPlayer {
                profile: ServerProfileId::new(1),
                player: PlayerIndex::new(1),
                member: false,
                staff_level: 0,
            },
        },
    );
    apply(
        &mut state,
        SemanticOperation::EstablishInitialPlacement(InitialPlacement {
            position: WorldTile::new(3220, 3220, Plane::MIN),
            map_anchor: WorldTile::new(3216, 3216, Plane::MIN),
            area: None,
        }),
    );
    let placement = LocationAnimationPlacement {
        shape: 0,
        orientation: LocOrientation::new(2).unwrap(),
    };
    let key = LocationKey::new(
        WorldTile::new(3218, 3215, Plane::MIN),
        LocationLayer::Boundary,
    );
    let options = LocationOptions {
        enabled: ActionMask::new([true, false, false, false, false]),
        labels: [Some("Open".into()), None, None, None, None],
    };
    let update = |object, options| {
        SemanticOperation::Scenery(SceneryOperation::UpdateLocation {
            offset: TileOffset { x: 2, y: -1 },
            plane: Plane::MIN,
            placement,
            object,
            options,
        })
    };
    apply(
        &mut state,
        update(Some(ObjectDefinitionId::new(1521)), options.clone()),
    );
    assert_eq!(state.scenery().location_options()[&key], options);
    let original = state.semantic_fingerprint();
    let mut renamed = options.clone();
    renamed.labels[0] = Some("Close".into());
    apply(
        &mut state,
        update(Some(ObjectDefinitionId::new(1521)), renamed),
    );
    assert_ne!(state.semantic_fingerprint(), original);
    apply(
        &mut state,
        SemanticOperation::Scenery(SceneryOperation::TriggerLocationAnimation {
            offset: TileOffset { x: 2, y: -1 },
            plane: Some(Plane::MIN),
            placement,
            sequence: Some(SequenceId::new(808)),
        }),
    );
    assert!(state.scenery().locations()[&key].animation.is_some());
    apply(
        &mut state,
        update(
            Some(ObjectDefinitionId::new(1522)),
            LocationOptions::default(),
        ),
    );
    assert!(state.scenery().locations()[&key].animation.is_none());
    assert!(!state.scenery().location_options().contains_key(&key));
    let before = state.semantic_fingerprint();
    assert!(
        state
            .apply_operation(SemanticOperation::Scenery(
                SceneryOperation::UpdateLocation {
                    offset: TileOffset { x: 2, y: -1 },
                    plane: Plane::MIN,
                    placement: LocationAnimationPlacement {
                        shape: 99,
                        ..placement
                    },
                    object: Some(ObjectDefinitionId::new(1523)),
                    options
                }
            ))
            .is_err()
    );
    assert_eq!(state.semantic_fingerprint(), before);
    apply(&mut state, update(None, LocationOptions::default()));
    assert_eq!(
        state.scenery().locations()[&key].change,
        LocationChange::Remove
    );
    let mut replay = GameState::new();
    replay.replay_envelopes(&recording).unwrap();
    assert_eq!(replay.semantic_fingerprint(), state.semantic_fingerprint());
    state
        .apply_operation(SemanticOperation::EndSession)
        .unwrap();
    assert!(state.scenery().locations().is_empty());
    assert!(state.scenery().location_options().is_empty());
    assert_eq!(
        state.semantic_fingerprint(),
        GameState::new().semantic_fingerprint()
    );
}
