use rustclient_cache::{GroupId, IndexId, Source, osrs240::definitions};
use rustclient_model::{NpcDefinitionId, SequenceId};
#[test]
fn npc_speed_animation_sets_retain_directional_sequences_and_absence() {
    let bytes = [
        114, 0, 10, 116, 0, 20, 115, 0, 11, 0, 12, 255, 255, 0, 14, 117, 0, 21, 0, 22, 0, 23, 255,
        255, 0,
    ];
    let def = definitions::npc(
        &bytes,
        NpcDefinitionId::new(1),
        Source::group(IndexId(2), GroupId(9)),
    )
    .unwrap();
    assert_eq!(
        def.run_sequences,
        [
            Some(SequenceId::new(11)),
            Some(SequenceId::new(12)),
            None,
            Some(SequenceId::new(14))
        ]
    );
    assert_eq!(
        def.crawl_sequences,
        [
            Some(SequenceId::new(21)),
            Some(SequenceId::new(22)),
            Some(SequenceId::new(23)),
            None
        ]
    );
    for length in 0..bytes.len() {
        assert!(
            definitions::npc(
                &bytes[..length],
                NpcDefinitionId::new(1),
                Source::group(IndexId(2), GroupId(9))
            )
            .is_err()
        );
    }
}

#[test]
fn npc_turn_rates_preserve_default_zero_and_full_unsigned_range() {
    let id = NpcDefinitionId::new(1);
    let source = Source::group(IndexId(2), GroupId(9));
    assert_eq!(
        definitions::npc(&[0], id, source).unwrap().turn_rate.get(),
        32
    );
    for rate in [0_u16, 1, 32, 2048, 65535] {
        let [hi, lo] = rate.to_be_bytes();
        let bytes = [103, hi, lo, 109, 0];
        let definition = definitions::npc(&bytes, id, source).unwrap();
        let policy = definitions::npc_movement_policy(&bytes, id, source).unwrap();
        assert_eq!(definition.turn_rate.get(), rate);
        assert_eq!(policy.turn_rate, definition.turn_rate);
        assert_eq!(
            policy.path_acceleration,
            rustclient_model::PathAccelerationPolicy::EARLY
        );
        for length in 0..bytes.len() {
            assert!(definitions::npc_movement_policy(&bytes[..length], id, source).is_err());
        }
    }
}
