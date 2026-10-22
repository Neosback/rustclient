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
