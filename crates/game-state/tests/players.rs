use rustclient_game_state::{
    AuthenticatedPlayer, GameState, InitialPlacement, ReplayEnvelope, SemanticOperation,
};
use rustclient_model::*;

fn initial() -> GameState {
    let mut state = GameState::new();
    state
        .apply_operation(SemanticOperation::AuthenticateSession {
            revision: Revision::with_subrevision(240, 2),
            player: AuthenticatedPlayer {
                profile: ServerProfileId::new(1),
                player: PlayerIndex::new(1),
                member: false,
                staff_level: 0,
            },
        })
        .unwrap();
    state
        .apply_operation(SemanticOperation::EstablishInitialPlacement(
            InitialPlacement {
                position: WorldTile::new(3221, 3218, Plane::MIN),
                map_anchor: WorldTile::new(3216, 3216, Plane::MIN),
                area: None,
            },
        ))
        .unwrap();
    state
}
fn remote(updates: Vec<RemotePlayerUpdate>) -> SemanticOperation {
    SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
        remote: updates,
        ..Default::default()
    })
}
#[test]
fn remote_visibility_retains_appearance_reincarnates_indexes_and_replays_atomically() {
    let mut state = initial();
    let index = PlayerIndex::new(2);
    let grid = PlayerTrackingGrid::new(8192, 256).unwrap();
    let appearance = PlayerAppearance {
        display_name: "Other player".into(),
        ..Default::default()
    };
    let mut recording = Vec::new();
    for operation in [
        remote(vec![RemotePlayerUpdate::Track {
            index,
            grid,
            region: WorldTile::new(0, 0, Plane::MIN),
        }]),
        remote(vec![
            RemotePlayerUpdate::Enter {
                index,
                offset: TileOffset { x: 3220, y: 3219 },
            },
            RemotePlayerUpdate::Attributes {
                index,
                appearance: Some(appearance.clone()),
                sequence: SequenceChange::Unchanged,
                delay: 0,
                facing: Some(1024),
            },
        ]),
        remote(vec![RemotePlayerUpdate::Move {
            index,
            movement: Some(PlayerMovement::Step { direction: 4 }),
        }]),
    ] {
        let commit = state.apply_operation(operation.clone()).unwrap();
        recording.push(ReplayEnvelope::new(operation, commit));
    }
    let first = state.entities().players()[&index].instance;
    assert_eq!(
        state.entities().players()[&index].tile,
        WorldTile::new(3221, 3219, Plane::MIN)
    );
    let before = state.semantic_fingerprint();
    assert!(
        state
            .apply_operation(remote(vec![
                RemotePlayerUpdate::Leave { index },
                RemotePlayerUpdate::Enter {
                    index,
                    offset: TileOffset { x: 8192, y: 0 }
                },
            ]))
            .is_err()
    );
    assert_eq!(
        state.semantic_fingerprint(),
        before,
        "a malformed tail cannot demote the valid prefix"
    );
    for operation in [
        remote(vec![RemotePlayerUpdate::Leave { index }]),
        remote(vec![RemotePlayerUpdate::Shift {
            index,
            regions: TileOffset { x: -1, y: 0 },
            plane_delta: 1,
        }]),
    ] {
        let commit = state.apply_operation(operation.clone()).unwrap();
        recording.push(ReplayEnvelope::new(operation, commit));
    }
    let tracked = state.entities().tracked_player(index).unwrap();
    assert_eq!(
        tracked.region,
        WorldTile::new(2_088_960, 0, Plane::new(1).unwrap())
    );
    assert_eq!(tracked.appearance.as_ref(), Some(&appearance));
    let operation = remote(vec![RemotePlayerUpdate::Enter {
        index,
        offset: TileOffset { x: 10, y: 20 },
    }]);
    let commit = state.apply_operation(operation.clone()).unwrap();
    recording.push(ReplayEnvelope::new(operation, commit));
    let player = &state.entities().players()[&index];
    assert_ne!(player.instance, first);
    assert_eq!(player.appearance.as_ref(), Some(&appearance));
    let mut replay = initial();
    replay.replay_envelopes(&recording).unwrap();
    assert_eq!(state.semantic_fingerprint(), replay.semantic_fingerprint());
    state
        .apply_operation(SemanticOperation::EndSession)
        .unwrap();
    assert!(state.entities().players().is_empty());
    assert!(state.entities().tracked_player(index).is_none());
}
