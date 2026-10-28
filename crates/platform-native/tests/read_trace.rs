//! Repeatable sector-read trace using a local temporary fixture, never the user's cache.
use rustclient_platform::{ReadOnlyBlobs, StorageKey};
use rustclient_platform_native::DirectoryBlobs;
use std::time::Instant;

#[test]
#[ignore = "manual optimized filesystem performance trace"]
fn sector_reads() {
    let root = std::env::temp_dir().join(format!("rustclient-read-trace-{}", std::process::id()));
    std::fs::create_dir_all(&root).unwrap();
    std::fs::write(root.join("sectors"), vec![7; 512 * 1024]).unwrap();
    let source = DirectoryBlobs::open(&root).unwrap();
    let key = StorageKey::parse("sectors").unwrap();
    let mut output = [0; 520];
    let mut samples = Vec::new();
    let all = Instant::now();
    for index in 0..20_000_u64 {
        let started = Instant::now();
        source
            .read_exact_at(&key, (index % 1000) * 512, &mut output)
            .unwrap();
        samples.push(started.elapsed().as_secs_f64() * 1_000_000.);
    }
    let total = all.elapsed();
    assert_eq!(output, [7; 520]);
    samples.sort_by(f64::total_cmp);
    println!(
        "20,000 sector reads: total={total:?} p50={:.3}us p95={:.3}us",
        samples[10_000], samples[19_000]
    );
    drop(source);
    std::fs::remove_dir_all(root).unwrap();
}
