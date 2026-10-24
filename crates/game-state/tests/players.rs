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
                path_speed: None,
                exact_move: None,
            },
        ]),
        remote(vec![RemotePlayerUpdate::Move {
            index,
            movement: Some(PlayerMovement::Step { direction: 4 }),
            speed: None,
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

#[test]
#[expect(
    clippy::too_many_lines,
    reason = "one recorded movement lifecycle and its rejected transition"
)]
fn traversal_forced_anchors_and_wrapped_teleports_are_authoritative_atomic_and_replayable() {
    let mut state = initial();
    let mut recording = Vec::new();
    let mut commit = |state: &mut GameState, update: PlayerInfoUpdate| {
        let operation = SemanticOperation::UpdatePlayers(update);
        let result = state.apply_operation(operation.clone()).unwrap();
        recording.push(ReplayEnvelope::new(operation, result));
    };
    commit(
        &mut state,
        PlayerInfoUpdate {
            local_movement: Some(PlayerMovement::Step { direction: 4 }),
            local_path_speed: Some(MoveSpeed::Run),
            local_movement_speed: Some(MoveSpeed::Run),
            ..Default::default()
        },
    );
    assert_eq!(
        state.entities().local_player().unwrap().locomotion,
        rustclient_game_state::Locomotion::Run
    );
    commit(
        &mut state,
        PlayerInfoUpdate {
            local_movement: Some(PlayerMovement::Step { direction: 4 }),
            local_movement_speed: Some(MoveSpeed::Crawl),
            ..Default::default()
        },
    );
    let player = state.entities().local_player().unwrap();
    assert_eq!(
        (player.path_speed, player.movement_speed),
        (MoveSpeed::Run, MoveSpeed::Crawl)
    );
    commit(
        &mut state,
        PlayerInfoUpdate {
            local_movement: Some(PlayerMovement::Step { direction: 4 }),
            local_exact_move: Some(ExactMove {
                start_delta_x: -1,
                start_delta_y: 0,
                end_delta_x: 2,
                end_delta_y: 1,
                start_delay: 5,
                end_delay: 10,
                direction: 1024,
            }),
            ..Default::default()
        },
    );
    let player = state.entities().local_player().unwrap();
    assert_eq!(player.tile, WorldTile::new(3224, 3218, Plane::MIN));
    assert_eq!(
        player.movement_speed,
        MoveSpeed::Run,
        "temporary speed did not overwrite the default"
    );
    let exact = player.exact_move.unwrap();
    assert_eq!(exact.start, WorldTile::new(3223, 3218, Plane::MIN));
    assert_eq!(exact.end, WorldTile::new(3226, 3219, Plane::MIN));
    assert_eq!((exact.starts_at.get(), exact.ends_at.get()), (5, 10));
    let before = state.semantic_fingerprint();
    let bad = PlayerInfoUpdate {
        local_movement: Some(PlayerMovement::Step { direction: 4 }),
        local_exact_move: Some(ExactMove {
            start_delta_x: 0,
            start_delta_y: 0,
            end_delta_x: 1,
            end_delta_y: 0,
            start_delay: 10,
            end_delay: 5,
            direction: 0,
        }),
        ..Default::default()
    };
    assert!(
        state
            .apply_operation(SemanticOperation::UpdatePlayers(bad))
            .is_err()
    );
    assert_eq!(state.semantic_fingerprint(), before);
    commit(
        &mut state,
        PlayerInfoUpdate {
            local_movement: Some(PlayerMovement::WrappedTeleport {
                offset: TileOffset { x: 16383, y: 0 },
                plane_delta: 1,
                extent: 16384,
            }),
            ..Default::default()
        },
    );
    let player = state.entities().local_player().unwrap();
    assert_eq!(
        player.tile,
        WorldTile::new(3223, 3218, Plane::new(1).unwrap())
    );
    assert_eq!(player.movement_speed, MoveSpeed::Run);
    assert_eq!(player.exact_move, None);
    let heading = player.orientation;
    commit(
        &mut state,
        PlayerInfoUpdate {
            local_movement: Some(PlayerMovement::Step { direction: 7 }),
            local_movement_speed: Some(MoveSpeed::Stationary),
            ..Default::default()
        },
    );
    let player = state.entities().local_player().unwrap();
    assert_eq!(player.orientation, heading);
    assert_eq!(player.locomotion, rustclient_game_state::Locomotion::Idle);
    let mut replay = initial();
    replay.replay_envelopes(&recording).unwrap();
    assert_eq!(state.semantic_fingerprint(), replay.semantic_fingerprint());
    state
        .apply_operation(SemanticOperation::EndSession)
        .unwrap();
    assert!(state.entities().players().is_empty());
}

#[test]
fn remote_temporary_speed_never_restarts_an_unchanged_locomotion() {
    let mut state = initial();
    let index = PlayerIndex::new(2);
    state
        .apply_operation(remote(vec![
            RemotePlayerUpdate::Track {
                index,
                grid: PlayerTrackingGrid::new(8192, 256).unwrap(),
                region: WorldTile::new(0, 0, Plane::MIN),
            },
            RemotePlayerUpdate::Enter {
                index,
                offset: TileOffset { x: 3220, y: 3218 },
            },
            RemotePlayerUpdate::Move {
                index,
                movement: Some(PlayerMovement::Step { direction: 4 }),
                speed: Some(MoveSpeed::Crawl),
            },
            RemotePlayerUpdate::Attributes {
                index,
                appearance: None,
                sequence: SequenceChange::Unchanged,
                delay: 0,
                facing: None,
                path_speed: Some(MoveSpeed::Run),
                exact_move: None,
            },
        ]))
        .unwrap();
    state
        .apply_operation(SemanticOperation::Scenery(
            rustclient_game_state::SceneryOperation::AdvanceCycles(7),
        ))
        .unwrap();
    state
        .apply_operation(remote(vec![RemotePlayerUpdate::Move {
            index,
            movement: Some(PlayerMovement::Step { direction: 4 }),
            speed: Some(MoveSpeed::Crawl),
        }]))
        .unwrap();
    let player = &state.entities().players()[&index];
    assert_eq!(
        (player.path_speed, player.movement_speed),
        (MoveSpeed::Run, MoveSpeed::Crawl)
    );
    assert_eq!(player.animation_cycle, 7);
    state
        .apply_operation(remote(vec![RemotePlayerUpdate::Move {
            index,
            movement: Some(PlayerMovement::Step { direction: 4 }),
            speed: None,
        }]))
        .unwrap();
    let player = &state.entities().players()[&index];
    assert_eq!(player.movement_speed, MoveSpeed::Run);
    assert_eq!(player.locomotion, rustclient_game_state::Locomotion::Run);
    assert_eq!(player.animation_cycle, 0);
    state
        .apply_operation(remote(vec![
            RemotePlayerUpdate::Leave { index },
            RemotePlayerUpdate::Enter {
                index,
                offset: TileOffset { x: 3223, y: 3218 },
            },
        ]))
        .unwrap();
    let player = &state.entities().players()[&index];
    assert_eq!(player.path_speed, MoveSpeed::Run);
    assert_eq!(player.movement_speed, MoveSpeed::Run);
}
