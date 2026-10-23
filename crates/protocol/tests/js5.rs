use rustclient_platform::{ByteStream, TransportError};
use rustclient_protocol::{ServerProfile, verify_cache};
use std::collections::VecDeque;
struct Memory {
    input: VecDeque<u8>,
    output: Vec<u8>,
}
#[allow(
    clippy::unused_async_trait_impl,
    reason = "test transport implements the shared async ByteStream contract"
)]
impl ByteStream for Memory {
    async fn receive(&mut self, out: &mut [u8]) -> Result<usize, TransportError> {
        let n = out.len().min(self.input.len()).min(3);
        for v in &mut out[..n] {
            *v = self.input.pop_front().unwrap();
        }
        Ok(n)
    }
    async fn send(&mut self, bytes: &[u8]) -> Result<(), TransportError> {
        self.output.extend_from_slice(bytes);
        Ok(())
    }
}
fn master() -> Memory {
    let mut bytes = vec![0, 255, 0, 255, 0];
    bytes.extend_from_slice(&(70_u32 * 8).to_be_bytes());
    let mut payload = Vec::new();
    for i in 0_u32..70 {
        payload.extend_from_slice(&i.to_be_bytes());
        payload.extend_from_slice(&123_u32.to_be_bytes());
    }
    bytes.extend_from_slice(&payload[..504]);
    bytes.push(255);
    bytes.extend_from_slice(&payload[504..]);
    Memory {
        input: bytes.into(),
        output: Vec::new(),
    }
}
#[test]
fn actual_master_crc_validation_handles_partial_reads_and_block_delimiters() {
    let mut stream = master();
    let crcs = std::array::from_fn(|i| u32::try_from(i).unwrap());
    pollster::block_on(verify_cache(
        &mut stream,
        ServerProfile::OPENRUNE_240_2,
        &crcs,
    ))
    .unwrap();
    assert!(stream.input.is_empty());
    assert_eq!(stream.output[0], 15);
    assert_eq!(&stream.output[21..], &[3, 0, 0, 0, 1, 255, 0, 255]);
}
#[test]
fn mismatch_and_malformed_containers_never_start_game_login() {
    let mut stream = master();
    let error = pollster::block_on(verify_cache(
        &mut stream,
        ServerProfile::OPENRUNE_240_2,
        &[0; 23],
    ))
    .unwrap_err();
    assert_eq!(error.stage, "cache compatibility");
    assert!(error.detail.contains("archive 1"));
    assert_eq!(stream.output.len(), 29);
    let mut stream = master();
    stream.input[513] = 0;
    assert!(
        pollster::block_on(verify_cache(
            &mut stream,
            ServerProfile::OPENRUNE_240_2,
            &[0; 23]
        ))
        .is_err()
    );
    let mut stream = master();
    stream.input.truncate(12);
    assert!(
        pollster::block_on(verify_cache(
            &mut stream,
            ServerProfile::OPENRUNE_240_2,
            &[0; 23]
        ))
        .is_err()
    );
}
