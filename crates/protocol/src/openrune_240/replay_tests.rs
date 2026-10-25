//! Opt-in replay of a payload stream captured from the pinned reference client (`Packet240Agent` with
//! `--packet-stream`). Every player/NPC update must decode with no error and no trailing bytes. The capture is live
//! session data and is never committed; point the test at it:
//!
//! `RUSTCLIENT_PACKET_REPLAY=<stream.log> RUSTCLIENT_PACKET_LOCAL=<local player index> \
//!  cargo test -p rustclient-protocol replay -- --ignored --nocapture`
use super::{npc_info::NpcInfoDecoder, player_info::PlayerInfoDecoder};
use std::collections::BTreeMap;

fn hex(text: &str) -> Vec<u8> {
    (0..text.len() / 2)
        .map(|i| u8::from_str_radix(&text[i * 2..i * 2 + 2], 16).expect("hex payload"))
        .collect()
}

#[test]
#[ignore = "needs RUSTCLIENT_PACKET_REPLAY and RUSTCLIENT_PACKET_LOCAL from a live reference capture"]
fn captured_update_packets_decode_without_trailing_bytes() {
    let path = std::env::var("RUSTCLIENT_PACKET_REPLAY").expect("RUSTCLIENT_PACKET_REPLAY");
    let local: u32 = std::env::var("RUSTCLIENT_PACKET_LOCAL")
        .expect("RUSTCLIENT_PACKET_LOCAL")
        .parse()
        .expect("local player index");
    let stream = std::fs::read_to_string(path).expect("stream log");
    let mut players = PlayerInfoDecoder::new(rustclient_model::PlayerIndex::new(local));
    let mut npcs = NpcInfoDecoder::default();
    let mut seen: BTreeMap<u16, (u32, u32)> = BTreeMap::new();
    let mut failures = Vec::new();
    for line in stream.lines() {
        let mut fields = line.splitn(4, ' ');
        let sequence: u64 = fields.next().expect("sequence").parse().expect("sequence");
        let opcode: u16 = fields.next().expect("opcode").parse().expect("opcode");
        let _length = fields.next();
        let payload = hex(fields.next().unwrap_or(""));
        let result = match opcode {
            91 => players.decode(&payload).map(|_| ()),
            111 => npcs.set_origin(&payload),
            150 => npcs.decode(&payload, false).map(|_| ()),
            141 => npcs.decode(&payload, true).map(|_| ()),
            39 => {
                npcs.clear_origin();
                Ok(())
            }
            _ => continue,
        };
        let entry = seen.entry(opcode).or_default();
        entry.0 += 1;
        if let Err(error) = result {
            entry.1 += 1;
            if failures.len() < 20 {
                failures.push(format!(
                    "seq {sequence} opcode {opcode} ({} bytes): {error}",
                    payload.len()
                ));
            }
        }
    }
    for (opcode, (packets, bad)) in &seen {
        println!("opcode {opcode}: {packets} packets, {bad} failed");
    }
    assert!(!seen.is_empty(), "stream held no update packets");
    assert!(
        failures.is_empty(),
        "decode failures:\n{}",
        failures.join("\n")
    );
}
