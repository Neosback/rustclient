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
fn line(text: &str) -> PlayerSpeech {
    PlayerSpeech {
        index: PlayerIndex::new(1),
        update: SpeechUpdate::Say(text.into()),
    }
}
#[test]
fn received_chat_updates_history_and_overhead_once_expires_and_replays() {
    let mut state = initial();
    let mut replay = initial();
    let mut recording = Vec::new();
    let mut apply = |state: &mut GameState, operation: SemanticOperation| {
        let commit = state.apply_operation(operation.clone()).unwrap();
        recording.push(ReplayEnvelope::new(operation, commit));
        commit
    };
    let public = PlayerSpeech {
        index: PlayerIndex::new(1),
        update: SpeechUpdate::Public {
            text: OverheadText {
                text: " Hello ".into(),
                colour: 6,
                effect: 1,
                pattern: vec![],
            },
            account: ChatAccount::default(),
            autotyper: false,
        },
    };
    let commit = apply(
        &mut state,
        SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
            local_appearance: Some(PlayerAppearance {
                display_name: "Test".into(),
                ..Default::default()
            }),
            speech: vec![public],
            ..Default::default()
        }),
    );
    assert!(commit.delta().chat_changed());
    assert_eq!(state.chat().entries().len(), 1);
    assert_eq!(
        state.chat().entries()[0].message.channel,
        rustclient_model::ids::ChatChannelId::new(2)
    );
    assert_eq!(state.chat().entries()[0].message.sender, "Test");
    assert_eq!(state.chat().entries()[0].message.text, " Hello ");
    let overhead = state
        .entities()
        .local_player()
        .unwrap()
        .overhead
        .as_ref()
        .unwrap();
    assert_eq!((&*overhead.text.text, overhead.remaining), ("Hello", 150));
    let before = state.semantic_fingerprint();
    assert!(
        state
            .apply_operation(SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
                local_movement: Some(PlayerMovement::Step { direction: 4 }),
                speech: vec![line("bad\0text")],
                ..Default::default()
            }))
            .is_err()
    );
    assert_eq!(state.semantic_fingerprint(), before);
    apply(
        &mut state,
        SemanticOperation::Scenery(rustclient_game_state::SceneryOperation::AdvanceCycles(149)),
    );
    assert_eq!(
        state
            .entities()
            .local_player()
            .unwrap()
            .overhead
            .as_ref()
            .unwrap()
            .remaining,
        1
    );
    apply(
        &mut state,
        SemanticOperation::Scenery(rustclient_game_state::SceneryOperation::AdvanceCycles(1)),
    );
    assert!(state.entities().local_player().unwrap().overhead.is_none());
    assert_eq!(state.chat().entries().len(), 1);
    apply(
        &mut state,
        SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
            speech: vec![line("~Shown")],
            ..Default::default()
        }),
    );
    assert_eq!(state.chat().entries().back().unwrap().message.text, "Shown");
    replay.replay_envelopes(&recording).unwrap();
    assert_eq!(state.semantic_fingerprint(), replay.semantic_fingerprint());
    state
        .apply_operation(SemanticOperation::EndSession)
        .unwrap();
    assert!(state.entities().players().is_empty());
    assert!(state.chat().entries().is_empty());
}

#[test]
fn remote_say_prefix_and_actor_reuse_do_not_leak_text_or_duplicate_history() {
    let mut state = initial();
    let index = PlayerIndex::new(2);
    let grid = PlayerTrackingGrid::new(8192, 256).unwrap();
    let appearance = PlayerAppearance {
        display_name: "Other".into(),
        ..Default::default()
    };
    state
        .apply_operation(SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
            remote: vec![
                RemotePlayerUpdate::Track {
                    index,
                    grid,
                    region: WorldTile::new(0, 0, Plane::MIN),
                },
                RemotePlayerUpdate::Enter {
                    index,
                    offset: TileOffset { x: 3220, y: 3219 },
                },
                RemotePlayerUpdate::Attributes {
                    index,
                    appearance: Some(appearance),
                    sequence: SequenceChange::Unchanged,
                    delay: 0,
                    facing: None,
                    path_speed: None,
                    exact_move: None,
                },
            ],
            speech: vec![PlayerSpeech {
                index,
                update: SpeechUpdate::Say("bubble only".into()),
            }],
            ..Default::default()
        }))
        .unwrap();
    assert!(state.chat().entries().is_empty());
    assert_eq!(
        state.entities().players()[&index]
            .overhead
            .as_ref()
            .unwrap()
            .text
            .text,
        "bubble only"
    );
    state
        .apply_operation(SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
            speech: vec![PlayerSpeech {
                index,
                update: SpeechUpdate::Say("~Logged".into()),
            }],
            ..Default::default()
        }))
        .unwrap();
    assert_eq!(state.chat().entries().len(), 1);
    let instance = state.entities().players()[&index].instance;
    state
        .apply_operation(SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
            remote: vec![
                RemotePlayerUpdate::Leave { index },
                RemotePlayerUpdate::Enter {
                    index,
                    offset: TileOffset { x: 3220, y: 3219 },
                },
            ],
            ..Default::default()
        }))
        .unwrap();
    assert_ne!(state.entities().players()[&index].instance, instance);
    assert!(state.entities().players()[&index].overhead.is_none());
}
