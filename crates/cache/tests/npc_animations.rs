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
