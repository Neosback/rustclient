use rustclient_cache::{Cache, FileId, GroupId, IndexId, ReferenceTable, Source};
use rustclient_platform::{MemoryBlobs, StorageKey};
const GROUP: u32 = 70000;

fn container(payload: &[u8]) -> Vec<u8> {
    let mut bytes = vec![0];
    bytes.extend_from_slice(&u32::try_from(payload.len()).unwrap().to_be_bytes());
    bytes.extend_from_slice(payload);
    bytes
}
fn append(data: &mut Vec<u8>, index: u8, group: u32, bytes: &[u8]) -> [u8; 6] {
    let mut entry = [0; 6];
    entry[..3].copy_from_slice(&u32::try_from(bytes.len()).unwrap().to_be_bytes()[1..]);
    entry[3..].copy_from_slice(&u32::try_from(data.len() / 520).unwrap().to_be_bytes()[1..]);
    let header = if group > 65535 { 10 } else { 8 };
    let capacity = 520 - header;
    for (chunk, payload) in bytes.chunks(capacity).enumerate() {
        let mut sector = Vec::new();
        if header == 10 {
            sector.extend_from_slice(&group.to_be_bytes());
        } else {
            sector.extend_from_slice(&u16::try_from(group).unwrap().to_be_bytes());
        }
        sector.extend_from_slice(&u16::try_from(chunk).unwrap().to_be_bytes());
        let next = if (chunk + 1) * capacity < bytes.len() {
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
fn source(mutate: impl FnOnce(&mut Vec<u8>, usize)) -> MemoryBlobs {
    let mut data = vec![0; 520];
    let mut idx255 = vec![0; 9 * 6];
    idx255[8 * 6..9 * 6].copy_from_slice(&append(
        &mut data,
        255,
        8,
        &container(include_bytes!("fixtures/reference-7.bin")),
    ));
    let start = data.len();
    let mut raw = include_bytes!("fixtures/container-0.bin").to_vec();
    raw.extend_from_slice(&7_u16.to_be_bytes());
    let mut idx8 = vec![0; (GROUP as usize + 1) * 6];
    idx8[GROUP as usize * 6..].copy_from_slice(&append(&mut data, 8, GROUP, &raw));
    mutate(&mut data, start);
    // Real caches may omit final-sector padding.
    while data.last() == Some(&0) {
        data.pop();
    }
    MemoryBlobs::new([
        (StorageKey::parse("main_file_cache.dat2").unwrap(), data),
        (StorageKey::parse("main_file_cache.idx255").unwrap(), idx255),
        (StorageKey::parse("main_file_cache.idx8").unwrap(), idx8),
    ])
}
#[test]
fn reference_protocols_and_all_optional_columns_have_golden_interpretation() {
    let context = Source::group(IndexId(255), GroupId(8));
    for protocol in [5, 6] {
        let bytes = if protocol == 5 {
            include_bytes!("fixtures/reference-5.bin").as_slice()
        } else {
            include_bytes!("fixtures/reference-6.bin")
        };
        let table = ReferenceTable::decode(bytes, context).unwrap();
        assert_eq!(table.protocol, protocol);
        assert_eq!(table.groups[&GroupId(2)].files, vec![FileId(4)]);
    }
    let table =
        ReferenceTable::decode(include_bytes!("fixtures/reference-7.bin"), context).unwrap();
    let row = table.named_group("TITLE").unwrap();
    assert_eq!(row.id, GroupId(GROUP));
    assert_eq!(row.files, vec![FileId(9)]);
    assert_eq!(row.hash, Some(0x00ab_cdef));
    assert_eq!(row.whirlpool.unwrap()[63], 63);
    assert_eq!(row.sizes.unwrap().1, 750);
    assert_eq!(row.version, 7);
    for n in 0..include_bytes!("fixtures/reference-7.bin").len() {
        assert!(
            ReferenceTable::decode(&include_bytes!("fixtures/reference-7.bin")[..n], context)
                .is_err()
        );
    }
}
#[test]
fn large_group_sector_chain_and_named_sparse_file_decode_end_to_end() {
    let cache = Cache::open(source(|_, _| {})).unwrap();
    let (location, bytes) = cache.named_file(IndexId(8), "title", "").unwrap();
    assert_eq!(location.file, Some(FileId(9)));
    assert_eq!(bytes, include_bytes!("fixtures/payload.bin"));
    assert_eq!(cache.metadata_fingerprint().len(), 64);
}
#[test]
fn sector_corruption_checksum_version_and_cycles_fail_with_context() {
    for kind in 0..4 {
        let cache = Cache::open(source(|data, start| match kind {
            0 => data[start + 9] = 7,   // wrong index
            1 => data[start + 4] = 1,   // wrong chunk
            2 => data[start + 15] ^= 1, // payload checksum
            _ => {
                let sector = u32::try_from(start / 520).unwrap();
                data[start + 6..start + 9].copy_from_slice(&sector.to_be_bytes()[1..]);
            }
        }))
        .unwrap();
        let result = cache.group_files(IndexId(8), GroupId(GROUP));
        let error = result.unwrap_err();
        assert_eq!(error.source.group, Some(GroupId(GROUP)));
    }
    // Version trailer follows the golden uncompressed container across two sectors.
    let cache = Cache::open(source(|data, start| {
        let raw_len = include_bytes!("fixtures/container-0.bin").len() + 2;
        data[start + 520 + 10 + (raw_len - 510) - 1] ^= 1;
    }))
    .unwrap();
    assert!(
        cache
            .group_files(IndexId(8), GroupId(GROUP))
            .unwrap_err()
            .detail
            .contains("version")
    );
}

#[test]
fn publisher_encoded_hint_is_advisory_but_decoded_size_is_enforced() {
    // Golden reference-7 table: sizes follow the header, names, CRC/hash and
    // 64-byte Whirlpool columns. Reference group 8 occupies sector 1.
    const SIZES: usize = 520 + 8 + 5 + 88;
    let cache = Cache::open(source(|data, _| {
        data[SIZES..SIZES + 4].copy_from_slice(&1_u32.to_be_bytes());
    }))
    .unwrap();
    assert_eq!(
        cache.group_files(IndexId(8), GroupId(GROUP)).unwrap()[0].1,
        include_bytes!("fixtures/payload.bin")
    );
    let cache = Cache::open(source(|data, _| {
        data[SIZES + 4..SIZES + 8].copy_from_slice(&1_u32.to_be_bytes());
    }))
    .unwrap();
    let error = cache.named_file(IndexId(8), "title", "").unwrap_err();
    assert!(error.detail.contains("decoded-size"));
    assert_eq!(error.source.file, Some(FileId(9)));
}
