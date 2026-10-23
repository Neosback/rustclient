//! In-memory cache metadata for host contracts that do not read asset groups.
use super::ClientScripts;
use rustclient_cache::{Cache, CacheProfile};
use rustclient_platform::{MemoryBlobs, StorageKey};
/// Append one value to the sector chain the way the store reads it back (group < 65536).
fn append(data: &mut Vec<u8>, index: u8, group: u16, bytes: &[u8]) -> [u8; 6] {
    let mut entry = [0; 6];
    entry[..3].copy_from_slice(&u32::try_from(bytes.len()).unwrap().to_be_bytes()[1..]);
    entry[3..].copy_from_slice(&u32::try_from(data.len() / 520).unwrap().to_be_bytes()[1..]);
    for (chunk, payload) in bytes.chunks(512).enumerate() {
        let mut sector = group.to_be_bytes().to_vec();
        sector.extend_from_slice(&u16::try_from(chunk).unwrap().to_be_bytes());
        let next = if (chunk + 1) * 512 < bytes.len() {
            u32::try_from(data.len() / 520 + 1).unwrap()
        } else {
            0
        };
        sector.extend_from_slice(&next.to_be_bytes()[1..]);
        sector.push(index);
        sector.extend_from_slice(payload);
        sector.resize(520, 0);
        data.extend_from_slice(&sector);
    }
    entry
}
pub(super) fn scripts() -> ClientScripts<MemoryBlobs> {
    // Only the reference table of index 8 exists: these queries never read cache groups.
    let mut data = vec![0; 520];
    let mut container = vec![0];
    let table = include_bytes!("../../../cache/tests/fixtures/reference-7.bin");
    container.extend_from_slice(&u32::try_from(table.len()).unwrap().to_be_bytes());
    container.extend_from_slice(table);
    let mut idx255 = vec![0; 9 * 6];
    idx255[8 * 6..].copy_from_slice(&append(&mut data, 255, 8, &container));
    let blobs = MemoryBlobs::new([
        (StorageKey::parse("main_file_cache.dat2").unwrap(), data),
        (StorageKey::parse("main_file_cache.idx255").unwrap(), idx255),
    ]);
    ClientScripts::new(Cache::open(blobs).unwrap(), CacheProfile::OpenRune240)
}
