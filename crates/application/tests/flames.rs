use rustclient_application::{DeterministicRandomSource, RandomSeed, SplitMix64Random};
use rustclient_cache::{GroupId, IndexId, Source, decode_sprites};
use rustclient_game_ui::Flames;
use sha2::{Digest, Sha256};

#[test]
fn injected_seed_and_explicit_steps_freeze_flames_through_noise_reseed() {
    let stops = [
        [0, 0x00ff_0000, 0x00ff_ff00, 0x00ff_ffff, 0x00ff_ffff],
        [0, 0x0000_ff00, 0x0000_ffff, 0x00ff_ffff, 0x00ff_ffff],
        [0, 0x0000_00ff, 0x00ff_00ff, 0x00ff_ffff, 0x00ff_ffff],
    ];
    let runes = decode_sprites(
        include_bytes!("../../cache/tests/fixtures/sprites.bin"),
        Source::group(IndexId(8), GroupId(42)),
    )
    .unwrap();
    let run = || {
        let mut random = SplitMix64Random::new(RandomSeed::new(240));
        let mut flames = Flames::new(stops, || random.next_sample().get());
        for _ in 0..300 {
            flames.step(&runes, || random.next_sample().get());
        }
        assert!(
            flames
                .pixels()
                .as_chunks::<4>()
                .0
                .iter()
                .any(|pixel| pixel[3] > 0)
        );
        format!("{:x}", Sha256::digest(flames.pixels()))
    };
    assert_eq!(run(), run());
    assert_eq!(
        run(),
        "1a566d543bfb159c085df93b97aef792e900d459e10c357d4db26943f7911c0c"
    );
}
