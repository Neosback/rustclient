//! The cycle contract is independent of window, transport, GPU and platform threads.
use rustclient_application::cycle::{CycleClock, CycleIngress, InboundBatch, SessionCounters};
use rustclient_platform::MonotonicTime;
use std::time::Duration;
fn at(ms: u64) -> MonotonicTime {
    MonotonicTime::from_duration(Duration::from_millis(ms))
}

#[test]
fn catch_up_uses_ten_cycles_and_the_source_strict_deadline() {
    let mut clock = CycleClock::new(at(20));
    assert_eq!(clock.due(at(19)).unwrap(), 0);
    assert_eq!(clock.due(at(20)).unwrap(), 1);
    assert_eq!(clock.due(at(80)).unwrap(), 2); // 40, 60; strict next < now
    assert_eq!(clock.deadline(), at(80));
    assert_eq!(clock.due(at(1000)).unwrap(), 10);
    assert_eq!(clock.deadline(), at(1000));
    assert_eq!(clock.due(at(1000)).unwrap(), 1);
    assert_eq!(clock.deadline(), at(1020));
}
#[test]
fn ordinary_packets_split_at_one_hundred_but_a_group_is_never_split() {
    let mut ingress = CycleIngress::default();
    for n in 0..150 {
        ingress
            .enqueue(InboundBatch {
                packets: 1,
                wire_bytes: 1,
                payload: n,
            })
            .unwrap();
    }
    ingress.begin_cycle();
    let first: Vec<_> = std::iter::from_fn(|| ingress.next_batch()).collect();
    assert_eq!(first, (0..100).collect::<Vec<_>>());
    ingress.begin_cycle();
    let second: Vec<_> = std::iter::from_fn(|| ingress.next_batch()).collect();
    assert_eq!(second, (100..150).collect::<Vec<_>>());
    ingress
        .enqueue(InboundBatch {
            packets: 151,
            wire_bytes: 153,
            payload: 200,
        })
        .unwrap();
    ingress
        .enqueue(InboundBatch {
            packets: 1,
            wire_bytes: 1,
            payload: 201,
        })
        .unwrap();
    ingress.begin_cycle();
    assert_eq!(ingress.next_batch(), Some(200));
    assert_eq!(ingress.next_batch(), None);
    ingress.begin_cycle();
    assert_eq!(ingress.next_batch(), Some(201));
}
#[test]
fn queue_failure_preserves_prior_work_and_reset_discards_it() {
    let mut ingress = CycleIngress::default();
    ingress
        .enqueue(InboundBatch {
            packets: 1,
            wire_bytes: 1,
            payload: 7,
        })
        .unwrap();
    assert!(
        ingress
            .enqueue(InboundBatch {
                packets: 65_536,
                wire_bytes: 1,
                payload: 8
            })
            .is_err()
    );
    ingress.begin_cycle();
    assert_eq!(ingress.next_batch(), Some(7));
    ingress
        .enqueue(InboundBatch {
            packets: 1,
            wire_bytes: 1,
            payload: 9,
        })
        .unwrap();
    ingress.clear();
    assert_eq!(ingress.next_batch(), None);
}
#[test]
fn session_thresholds_are_strict_and_activity_resets_the_right_counter() {
    let mut counters = SessionCounters::default();
    for _ in 0..50 {
        counters.advance();
    }
    assert!(!counters.keepalive_due());
    counters.advance();
    assert!(counters.keepalive_due());
    counters.written();
    assert!(!counters.keepalive_due());
    for _ in 51..750 {
        counters.advance();
    }
    assert!(!counters.reconnect_due());
    counters.advance();
    assert!(counters.reconnect_due());
    counters.received();
    assert!(!counters.reconnect_due());
}

#[test]
fn outbound_flush_preserves_creation_order_and_retries_a_rejected_batch() {
    use rustclient_application::cycle::CycleEgress;
    let egress = CycleEgress::default();
    egress.enqueue("telemetry").unwrap();
    egress.enqueue("menu").unwrap();
    assert_eq!(egress.flush(|batch| Err((batch, "busy"))), Err("busy"));
    egress.enqueue("walk").unwrap();
    let mut sent = Vec::new();
    egress
        .flush::<()>(|batch| {
            sent = batch;
            Ok(())
        })
        .unwrap();
    assert_eq!(sent, ["telemetry", "menu", "walk"]);
    egress
        .flush::<()>(|_| panic!("empty cycle must not flush"))
        .unwrap();
}

#[test]
fn decoded_cycle_boundaries_replay_identical_commits_and_state() {
    use rustclient_application::{ConnectionDecoder, ConnectionInput};
    use rustclient_game_state::{GameState, ReplayEnvelope, SceneryOperation, SemanticOperation};
    use rustclient_model::{GameTick, PlayerIndex};
    use rustclient_protocol::{Isaac, LoginSession, ServerProfile};
    let login = LoginSession {
        player: PlayerIndex::new(1),
        member: true,
        staff_level: 0,
        incoming: Isaac::new([0; 4]),
        outgoing: Isaac::new([0; 4]),
    };
    let (mut decoder, authentication) =
        ConnectionDecoder::new(ServerProfile::OPENRUNE_240_2, login);
    let mut state = GameState::new();
    let commit = state.apply_operation(authentication.clone()).unwrap();
    let mut replay = vec![ReplayEnvelope::new(authentication, commit)];
    let mut cipher = Isaac::new([0; 4]);
    let bytes: Vec<_> = (0..150)
        .map(|_| 92_u8.wrapping_add(cipher.next_word().to_le_bytes()[0]))
        .collect();
    let mut ingress = CycleIngress::default();
    decoder
        .receive_batches(&bytes, |batch| ingress.enqueue(batch).unwrap())
        .unwrap();
    for expected_tick in [100, 150] {
        ingress.begin_cycle();
        while let Some(inputs) = ingress.next_batch() {
            for input in inputs {
                let ConnectionInput::Operation(operation) = input else {
                    panic!("tick fixture has only semantic operations")
                };
                let commit = state.apply_operation(operation.clone()).unwrap();
                replay.push(ReplayEnvelope::new(operation, commit));
            }
        }
        let operation = SemanticOperation::Scenery(SceneryOperation::AdvanceCycles(1));
        let commit = state.apply_operation(operation.clone()).unwrap();
        replay.push(ReplayEnvelope::new(operation, commit));
        assert_eq!(state.current_tick(), Some(GameTick::new(expected_tick)));
    }
    let mut reconstructed = GameState::new();
    reconstructed.replay_envelopes(&replay).unwrap();
    assert_eq!(
        state.semantic_fingerprint(),
        reconstructed.semantic_fingerprint()
    );
}
