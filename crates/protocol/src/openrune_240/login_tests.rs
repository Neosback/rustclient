use super::*;
use rustclient_model::CanvasSize;
use rustclient_platform::TransportError;
use std::collections::VecDeque;
struct Memory {
    input: VecDeque<u8>,
    output: Vec<u8>,
    chunk: usize,
}
impl ByteStream for Memory {
    async fn receive(&mut self, bytes: &mut [u8]) -> std::result::Result<usize, TransportError> {
        let n = bytes.len().min(self.chunk).min(self.input.len());
        for b in &mut bytes[..n] {
            *b = self.input.pop_front().unwrap();
        }
        Ok(n)
    }
    async fn send(&mut self, bytes: &[u8]) -> std::result::Result<(), TransportError> {
        self.output.extend_from_slice(bytes);
        Ok(())
    }
}
fn entropy() -> LoginEntropy {
    let mut bytes = [0; 40];
    for (i, k) in [1_u32, 2, 3, 4].iter().enumerate() {
        bytes[i * 4..i * 4 + 4].copy_from_slice(&k.to_be_bytes());
    }
    for i in 0..24 {
        bytes[16 + i] = u8::try_from(i).unwrap();
    }
    LoginEntropy::new(bytes)
}
fn key() -> PublicKey {
    PublicKey::parse(include_str!("../../tests/fixtures/public.key")).unwrap()
}
fn credentials() -> Credentials {
    Credentials::new("fixture".into(), "example".into()).unwrap()
}
#[test]
fn login_request_matches_independent_python_wire_fixture() {
    let crcs = std::array::from_fn(|i| 0x1020_3040 + u32::try_from(i).unwrap());
    let encoded = encode(
        ServerProfile::OPENRUNE_240_2,
        &key(),
        &credentials(),
        &entropy(),
        [1, 2, 3, 4],
        0x0102_0304_0506_0708_u64.to_be_bytes(),
        &crcs,
        WindowMetrics::default(),
    )
    .unwrap();
    assert_eq!(
        &**encoded,
        include_bytes!("../../tests/fixtures/login-request.bin")
    );
}
fn success(auth: bool, pow: bool) -> Memory {
    let mut input = vec![0];
    input.extend_from_slice(&0x0102_0304_0506_0708_u64.to_be_bytes());
    if pow {
        input.extend_from_slice(&[69, 0, 7, 0, 1, 0, b't', b'e', b's', 0]);
    }
    input.extend_from_slice(&[2, 37, u8::from(auth), 0, 0, 0, 0, 0, 0, 0, 1, 0]);
    input.extend_from_slice(&[0; 24]);
    input.extend_from_slice(&[0xab, 0xcd, 0xef]);
    Memory {
        input: input.into(),
        output: Vec::new(),
        chunk: 1,
    }
}
#[test]
fn partial_success_consumes_exactly_34_bytes_and_optional_authenticator_cipher_words() {
    for auth in [false, true] {
        let mut stream = success(auth, true);
        let mut login = pollster::block_on(authenticate(
            &mut stream,
            LoginAttempt {
                profile: ServerProfile::OPENRUNE_240_2,
                key: &key(),
                credentials: credentials(),
                entropy: entropy(),
                crcs: &[0; 23],
                window: WindowMetrics::default(),
            },
            || false,
            |_| {},
        ))
        .unwrap();
        assert_eq!(login.player.get(), 1);
        assert_eq!(stream.input, VecDeque::from([0xab, 0xcd, 0xef]));
        assert_eq!(
            &stream.output[stream.output.len() - 11..],
            &[19, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0]
        );
        let mut expected = Isaac::new([51, 52, 53, 54]);
        if auth {
            for _ in 0..4 {
                expected.next_word();
            }
        }
        assert_eq!(login.incoming.next_word(), expected.next_word());
    }
}
#[test]
fn malformed_and_cancelled_challenges_fail_boundedly() {
    assert!(solve_proof(&[0, 1, 23, 0], &mut || false).is_err());
    assert!(solve_proof(&[0, 1, 18, b'a', 0], &mut || true).is_err());
    assert_eq!(solve_proof(&[0, 1, 0, b'a', 0], &mut || false).unwrap(), 0);
    let mut stream = success(false, false);
    stream.input.truncate(30);
    assert!(
        pollster::block_on(authenticate(
            &mut stream,
            LoginAttempt {
                profile: ServerProfile::OPENRUNE_240_2,
                key: &key(),
                credentials: credentials(),
                entropy: entropy(),
                crcs: &[0; 23],
                window: WindowMetrics::default(),
            },
            || false,
            |_| {}
        ))
        .is_err()
    );
}
#[test]
fn credential_validation_and_public_key_parsing_do_not_echo_input() {
    for (username, password) in [
        ("", "example"),
        ("fixture", ""),
        ("fixture", "contains\0nul"),
    ] {
        assert!(Credentials::new(username.into(), password.into()).is_err());
    }
    let err = Credentials::new("fixture".into(), "private-marker".repeat(5))
        .err()
        .unwrap();
    assert!(!err.to_string().contains("private-marker"));
    assert!(PublicKey::parse("-----BEGIN PRIVATE KEY-----").is_err());
    assert!(PublicKey::parse(&"x".repeat(2049)).is_err());
}

fn window(mode: WindowMode, width: u32, height: u32) -> WindowMetrics {
    let size = CanvasSize { width, height };
    WindowMetrics {
        mode,
        available: size,
        canvas: size,
    }
}
#[test]
fn login_block_carries_the_real_window_mode_and_canvas() {
    let crcs = [0; 23];
    for (metrics, expected) in [
        (window(WindowMode::Fixed, 765, 503), [0, 2, 253, 1, 247]),
        (window(WindowMode::Resizable, 1280, 720), [2, 5, 0, 2, 208]),
    ] {
        let body = login_body(&credentials(), &entropy(), &crcs, metrics).unwrap();
        // Username is NUL-terminated, then the flag byte and both canvas extents.
        let start = "fixture".len() + 1;
        assert_eq!(&body[start..start + 5], expected);
    }
}
#[test]
fn oversized_canvas_is_rejected_before_encoding() {
    let crcs = [0; 23];
    assert!(
        encode(
            ServerProfile::OPENRUNE_240_2,
            &key(),
            &credentials(),
            &entropy(),
            [1, 2, 3, 4],
            [0; 8],
            &crcs,
            window(WindowMode::Resizable, 70_000, 503),
        )
        .is_err()
    );
}
