//! Ordered semantic movement consumption, independent of models or GPU output.
use rustclient_game_state::{AuthenticatedPlayer, GameState, InitialPlacement, SemanticOperation};
use rustclient_model::*;
use rustclient_scene::ActorMotion;
#[test]
#[expect(
    clippy::too_many_lines,
    reason = "one ordered path/trajectory consumer lifecycle without models or GPU output"
)]
fn npc_motion_consumes_each_ordered_request_once_and_resets_on_index_reuse() {
    let mut state = GameState::new();
    state
        .apply_operation(SemanticOperation::AuthenticateSession {
            revision: REVISION_240,
            player: AuthenticatedPlayer {
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
            InitialPlacement {
                position: tile,
                map_anchor: tile,
                area: None,
            },
        ))
        .unwrap();
    let index = NpcIndex::new(42);
    let spawn = NewNpc {
        index,
        definition_id: NpcDefinitionId::new(12),
        delta_x: 0,
        delta_y: 0,
        orientation: 0,
    };
    state
        .apply_operation(SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            new_npcs: vec![spawn.clone()],
            ..Default::default()
        }))
        .unwrap();
    let mut motion = ActorMotion::default();
    let origin = [tile.x * 128, tile.y * 128];
    motion.observe(state.entities(), ClientCycle::ZERO);
    state
        .apply_operation(SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            movements: vec![NpcMovement {
                index,
                first: TileOffset { x: 1, y: 0 },
                second: Some(TileOffset { x: 0, y: 1 }),
                speed: MoveSpeed::Run,
            }],
            ..Default::default()
        }))
        .unwrap();
    motion.observe(state.entities(), ClientCycle::ZERO);
    motion.observe(state.entities(), ClientCycle::ZERO);
    // The NPC first turns toward its new heading, walking at half speed meanwhile, so settle it.
    motion.advance(64);
    assert_eq!(
        motion.npc(index),
        Some([origin[0] + 128, origin[1] + 128]),
        "run passes through the authored intermediate tile to the final one"
    );
    motion.advance(32);
    assert_eq!(
        motion.npc(index),
        Some([origin[0] + 128, origin[1] + 128]),
        "repeated observations do not enqueue the path again"
    );
    state
        .apply_operation(SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            movements: vec![NpcMovement {
                index,
                first: TileOffset { x: 1, y: 0 },
                second: Some(TileOffset { x: -1, y: 0 }),
                speed: MoveSpeed::Run,
            }],
            ..Default::default()
        }))
        .unwrap();
    motion.observe(state.entities(), ClientCycle::ZERO);
    motion.advance(16);
    assert_ne!(
        motion.npc(index),
        Some([origin[0] + 128, origin[1] + 128]),
        "a closed path is still movement"
    );
    motion.advance(96);
    assert_eq!(motion.npc(index), Some([origin[0] + 128, origin[1] + 128]));
    state
        .apply_operation(SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            movements: vec![NpcMovement {
                index,
                first: TileOffset { x: 0, y: 1 },
                second: None,
                speed: MoveSpeed::Crawl,
            }],
            ..Default::default()
        }))
        .unwrap();
    motion.observe(state.entities(), ClientCycle::ZERO);
    motion.advance(1);
    // Crawling halves the half speed of an NPC that is still turning toward its new heading.
    assert_eq!(motion.npc(index), Some([origin[0] + 128, origin[1] + 129]));
    state
        .apply_operation(SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            removals: vec![index],
            new_npcs: vec![spawn],
            ..Default::default()
        }))
        .unwrap();
    motion.observe(state.entities(), ClientCycle::ZERO);
    assert_eq!(motion.npc(index), Some(origin));
    state
        .apply_operation(SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            attributes: vec![NpcAttributeUpdate {
                index,
                changes: vec![NpcProperty::ExactMove(ExactMove {
                    start_delta_x: 1,
                    start_delta_y: 0,
                    end_delta_x: 2,
                    end_delta_y: 0,
                    start_delay: 1,
                    end_delay: 3,
                    direction: 1024,
                })],
            }],
            ..Default::default()
        }))
        .unwrap();
    motion.observe(state.entities(), ClientCycle::ZERO);
    motion.advance(1);
    assert_eq!(motion.npc(index), Some([origin[0] + 128, origin[1]]));
    motion.advance(1);
    assert_eq!(motion.npc(index), Some([origin[0] + 192, origin[1]]));
    motion.advance(1);
    assert_eq!(motion.npc(index), Some([origin[0] + 256, origin[1]]));
    assert_eq!(
        motion.npc_pose(index).map(|drawn| drawn.rotation),
        Some(1024)
    );
    motion.observe(state.entities(), ClientCycle::new(3));
    motion.advance(1);
    assert_ne!(
        motion.npc_pose(index).map(|drawn| drawn.rotation),
        Some(1024),
        "once the trajectory ends the NPC turns back toward its network heading"
    );
}
