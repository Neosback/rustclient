use rustclient_protocol::{
    GameFramer, GamePacket, Isaac, ServerProfile, ServerUpdate, SessionCodec, decode_bootstrap,
    login_checksums,
};
fn frame(cipher: &mut Isaac, opcode: u16, payload: &[u8], variable: bool) -> Vec<u8> {
    let mut out = Vec::new();
    for b in if opcode < 128 {
        vec![u8::try_from(opcode).unwrap()]
    } else {
        vec![
            u8::try_from((opcode >> 8) | 128).unwrap(),
            opcode.to_be_bytes()[1],
        ]
    } {
        out.push(b.wrapping_add(cipher.next_word().to_le_bytes()[0]));
    }
    if variable {
        out.extend_from_slice(&u16::try_from(payload.len()).unwrap().to_be_bytes());
    }
    out.extend_from_slice(payload);
    out
}
#[test]
fn bytewise_smart_opcodes_and_lengths_do_not_desynchronize_isaac() {
    let mut cipher = Isaac::new([1, 2, 3, 4]);
    let mut bytes = frame(&mut cipher, 39, &[1, 2, 3, 4], true);
    bytes.extend(frame(&mut cipher, 151, &[7; 8], false));
    bytes.extend(frame(&mut cipher, 92, &[], false));
    let mut reader = GameFramer::new(Isaac::new([1, 2, 3, 4]));
    let mut packets = Vec::new();
    for b in bytes {
        reader.feed(&[b]).unwrap();
        while let Some(packet) = reader.next_packet().unwrap() {
            packets.push((packet.opcode, packet.payload));
        }
    }
    assert_eq!(
        packets,
        vec![(39, vec![1, 2, 3, 4]), (151, vec![7; 8]), (92, vec![])]
    );
}
#[test]
fn bounded_malformed_frames_fail_without_allocation_or_panic() {
    let mut cipher = Isaac::new([0; 4]);
    let mut bytes = frame(&mut cipher, 39, &[], true);
    bytes[1..3].copy_from_slice(&40001_u16.to_be_bytes());
    let mut reader = GameFramer::new(Isaac::new([0; 4]));
    reader.feed(&bytes).unwrap();
    assert!(reader.next_packet().is_err());
    assert!(
        GameFramer::new(Isaac::new([0; 4]))
            .feed(&vec![0; 131_073])
            .is_err()
    );
    let mut cipher = Isaac::new([0; 4]);
    let bytes = frame(&mut cipher, 255, &[], false);
    let mut reader = GameFramer::new(Isaac::new([0; 4]));
    reader.feed(&bytes).unwrap();
    assert!(reader.next_packet().is_err());
}
fn bootstrap() -> Vec<u8> {
    let mut bytes = vec![0; 4608];
    let packed = (3221_u32 << 14) | 0x0c92;
    bytes[..4].copy_from_slice(&(packed << 2).to_be_bytes());
    for n in [402_u16, 65535, 402] {
        let [lo, hi] = n.to_le_bytes();
        bytes.extend_from_slice(&[lo.wrapping_add(128), hi]);
    }
    bytes
}
#[test]
fn initial_gpi_and_footer_become_typed_placement_only_once() {
    let payload = bootstrap();
    let p = decode_bootstrap(&GamePacket {
        opcode: 39,
        payload: payload.clone(),
    })
    .unwrap();
    assert_eq!(
        (p.position.x, p.position.y, p.position.plane.get()),
        (3221, 3218, 0)
    );
    assert_eq!(p.area, None);
    let mut cipher = Isaac::new([0; 4]);
    let bytes = frame(&mut cipher, 39, &payload, true);
    let mut codec = SessionCodec::new(Isaac::new([0; 4]), Isaac::new([0; 4]));
    let mut updates = Vec::new();
    codec.receive(&bytes, |u| updates.push(u)).unwrap();
    assert_eq!(updates, vec![ServerUpdate::InitialPlacement(p)]);
    assert!(!codec.awaiting_initial_rebuild());
    assert!(
        codec
            .receive(&frame(&mut cipher, 39, &[0; 6], true), |_| {})
            .is_err()
    );
    for len in [0, 1, 4607, 4613, 4615] {
        assert!(
            decode_bootstrap(&GamePacket {
                opcode: 39,
                payload: vec![0; len]
            })
            .is_err()
        );
    }
    let mut specific = payload.clone();
    specific[4610..4612].copy_from_slice(&[128, 0]);
    assert_eq!(
        decode_bootstrap(&GamePacket {
            opcode: 39,
            payload: specific.clone()
        })
        .unwrap()
        .area
        .unwrap()
        .get(),
        0
    );
    specific[4610..4612].copy_from_slice(&[126, 255]);
    assert!(
        decode_bootstrap(&GamePacket {
            opcode: 39,
            payload: specific
        })
        .is_err()
    );
    let mut corrupt = payload;
    corrupt[4607] = 1;
    assert!(
        decode_bootstrap(&GamePacket {
            opcode: 39,
            payload: corrupt
        })
        .is_err()
    );
}
#[test]
fn profile_registry_and_archive_absence_are_explicit() {
    assert!(ServerProfile::parse("osrs-241").is_err());
    let crcs = login_checksums((0_u8..23).filter(|&i| i != 16).map(|i| (i, u32::from(i)))).unwrap();
    assert_eq!(crcs[16], 0);
    assert_eq!(crcs[22], 22);
    assert!(login_checksums((0_u8..22).map(|i| (i, 0))).is_err());
}
