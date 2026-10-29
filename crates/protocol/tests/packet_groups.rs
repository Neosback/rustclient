//! Boundaries are measured in complete encrypted wire bytes, not emitted operation count.
use rustclient_model::PlayerIndex;
use rustclient_protocol::{Isaac, SessionCodec};
fn packet(cipher: &mut Isaac, opcode: u16, payload: &[u8]) -> Vec<u8> {
    let header = if opcode < 128 {
        vec![u8::try_from(opcode).unwrap()]
    } else {
        vec![
            u8::try_from((opcode >> 8) + 128).unwrap(),
            opcode.to_le_bytes()[0],
        ]
    };
    let mut bytes: Vec<_> = header
        .into_iter()
        .map(|byte| byte.wrapping_add(cipher.next_word().to_le_bytes()[0]))
        .collect();
    bytes.extend_from_slice(payload);
    bytes
}
fn codec() -> SessionCodec {
    SessionCodec::new(Isaac::new([0; 4]), Isaac::new([0; 4]), PlayerIndex::new(1))
}
#[test]
fn fragmented_group_publishes_once_and_then_continues_in_order() {
    let mut cipher = Isaac::new([0; 4]);
    let mut bytes = packet(&mut cipher, 57, &150_u16.to_be_bytes());
    for _ in 0..150 {
        bytes.extend(packet(&mut cipher, 92, &[]));
    }
    bytes.extend(packet(&mut cipher, 92, &[]));
    let mut codec = codec();
    let mut batches = Vec::new();
    codec
        .receive_batches(&bytes[..100], |batch| batches.push(batch))
        .unwrap();
    assert!(batches.is_empty());
    codec
        .receive_batches(&bytes[100..], |batch| batches.push(batch))
        .unwrap();
    assert_eq!(batches.len(), 2);
    assert_eq!(
        (
            batches[0].packets,
            batches[0].wire_bytes,
            batches[0].updates.len()
        ),
        (151, 153, 150)
    );
    assert_eq!((batches[1].packets, batches[1].wire_bytes), (1, 1));
    assert_eq!(codec.framed_packets(), 152);
}
#[test]
fn invalid_group_does_not_publish_its_prefix() {
    let mut cipher = Isaac::new([0; 4]);
    let mut bytes = packet(&mut cipher, 57, &2_u16.to_be_bytes());
    bytes.extend(packet(&mut cipher, 92, &[]));
    bytes.extend(packet(&mut cipher, 127, &[0, 0, 0])); // four bytes crosses the remaining one-byte bound
    let mut codec = codec();
    let mut published = 0;
    assert!(codec.receive_batches(&bytes, |_| published += 1).is_err());
    assert_eq!(published, 0);
}

#[test]
fn controls_keep_reason_transfer_and_ping_distinct_from_protocol_failures() {
    use rustclient_protocol::{ClientIntent, ServerUpdate, SessionControl};
    let mut cipher = Isaac::new([0; 4]);
    let ping = packet(&mut cipher, 115, &[1, 2, 3, 4, 5, 6, 7, 8]);
    let mut codec = codec();
    let mut updates = Vec::new();
    codec.receive(&ping, |u| updates.push(u)).unwrap();
    assert_eq!(
        updates,
        [ServerUpdate::Control(SessionControl::Ping {
            first: 0x0102_0304,
            second: 0x0506_0708
        })]
    );
    let reply = codec
        .encode(ClientIntent::PingReply {
            first: 0x0102_0304,
            second: 0x0506_0708,
            fps: 50,
        })
        .unwrap();
    assert_eq!(&reply[1..], &[178, 0, 4, 3, 2, 1, 6, 5, 8, 7]);
    let fast_reply = codec
        .encode(ClientIntent::PingReply {
            first: 0,
            second: 0,
            fps: 300,
        })
        .unwrap();
    assert_eq!(fast_reply[1], 172); // Java p1Alt1 keeps the low byte; it does not clamp FPS.

    updates.clear();
    codec
        .receive(&packet(&mut cipher, 56, &[2]), |u| updates.push(u))
        .unwrap();
    assert_eq!(
        updates,
        [ServerUpdate::Control(SessionControl::LogoutWithReason(2))]
    );
}

#[test]
fn group_byte_count_includes_smart_opcodes_and_variable_length_prefixes() {
    use rustclient_protocol::{ServerUpdate, SessionControl};
    let mut cipher = Isaac::new([0; 4]);
    let mut members_cipher = Isaac::new([0; 4]);
    // Advance the member cipher past the group-marker opcode.
    members_cipher.next_word();
    let mut members = packet(&mut members_cipher, 151, &[0; 8]);
    let mut transfer = b"localhost\0".to_vec();
    transfer.extend_from_slice(&301_u16.to_be_bytes());
    transfer.extend_from_slice(&0x0102_0304_u32.to_be_bytes());
    let mut variable = vec![u8::try_from(transfer.len()).unwrap()];
    variable.extend(transfer);
    members.extend(packet(&mut members_cipher, 26, &variable));
    let mut bytes = packet(
        &mut cipher,
        57,
        &u16::try_from(members.len()).unwrap().to_be_bytes(),
    );
    bytes.extend(members);
    let mut batches = Vec::new();
    codec()
        .receive_batches(&bytes, |batch| batches.push(batch))
        .unwrap();
    assert_eq!(batches.len(), 1);
    assert_eq!(
        (batches[0].packets, batches[0].wire_bytes),
        (3, bytes.len())
    );
    assert_eq!(
        batches[0].updates,
        [
            ServerUpdate::Deferred,
            ServerUpdate::Control(SessionControl::Transfer {
                host: "localhost".into(),
                world: 301,
                properties: 0x0102_0304
            })
        ]
    );
}
