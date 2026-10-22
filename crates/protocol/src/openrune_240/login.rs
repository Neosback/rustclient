use crate::{
    Isaac, ProtocolError, Result, ServerProfile,
    crypto::xtea,
    wire::{Reader, exact, read, send, string},
};
use num_bigint::BigUint;
use rustclient_model::PlayerIndex;
use rustclient_platform::ByteStream;
use sha2::{Digest, Sha256};
use std::fmt::Write;
use zeroize::Zeroizing;

/// Single-owner transient login input. No Debug, Clone or serialization.
pub struct Credentials {
    username: Zeroizing<String>,
    password: Zeroizing<String>,
}
impl Credentials {
    /// # Errors
    /// Rejects empty, oversized or unencodable input without echoing it.
    pub fn new(username: String, password: String) -> Result<Self> {
        let this = Self {
            username: Zeroizing::new(username),
            password: Zeroizing::new(password),
        };
        if this.username.trim().is_empty()
            || this.username.chars().count() > 320
            || this.password.is_empty()
            || this.password.chars().count() > 20
            || this
                .username
                .chars()
                .chain(this.password.chars())
                .any(|ch| ch.is_control() || crate::wire::cp1252(ch).is_none())
        {
            return Err(ProtocolError::new(
                "credentials",
                "invalid field length or character",
            ));
        }
        Ok(this)
    }
}
/// Explicit operating-system entropy supplied by the composition root.
pub struct LoginEntropy(Zeroizing<[u8; 40]>);
impl LoginEntropy {
    #[must_use]
    pub fn new(bytes: [u8; 40]) -> Self {
        Self(Zeroizing::new(bytes))
    }
}
/// Raw RSA public parameters; the private key is never required by the client.
pub struct PublicKey {
    exponent: BigUint,
    modulus: BigUint,
}
impl PublicKey {
    /// Parse the `OpenRune` two-line hexadecimal public-key format.
    /// # Errors
    /// Rejects malformed or unreasonable keys. Private PEM formats are unsupported.
    pub fn parse(text: &str) -> Result<Self> {
        if text.len() > 2048 {
            return Err(ProtocolError::new("public key", "key file too large"));
        }
        let mut lines = text.lines();
        let exponent = lines
            .next()
            .and_then(|s| s.strip_prefix("Exponent: "))
            .and_then(|s| BigUint::parse_bytes(s.trim().as_bytes(), 16));
        let modulus = lines
            .next()
            .and_then(|s| s.strip_prefix("Modulus: "))
            .and_then(|s| BigUint::parse_bytes(s.trim().as_bytes(), 16));
        let (Some(exponent), Some(modulus)) = (exponent, modulus) else {
            return Err(ProtocolError::new(
                "public key",
                "expected public Exponent and Modulus",
            ));
        };
        if lines.any(|s| !s.trim().is_empty())
            || exponent != BigUint::from(65537_u32)
            || !(1024..=4096).contains(&modulus.bits())
            || modulus.to_bytes_be().last().is_none_or(|b| b & 1 == 0)
        {
            return Err(ProtocolError::new(
                "public key",
                "unsupported RSA parameters",
            ));
        }
        Ok(Self { exponent, modulus })
    }
}
/// Authenticated non-secret identity and private cipher state.
pub struct LoginSession {
    pub player: PlayerIndex,
    pub member: bool,
    pub staff_level: u8,
    pub incoming: Isaac,
    pub outgoing: Isaac,
}

/// Explicit inputs for a single connection attempt. No Debug or Clone.
pub struct LoginAttempt<'a> {
    pub profile: ServerProfile,
    pub key: &'a PublicKey,
    pub credentials: Credentials,
    pub entropy: LoginEntropy,
    pub crcs: &'a [u32; 23],
}

/// Perform the profile's desktop password login using an injected connection.
/// # Errors
/// Returns stage-specific malformed/rejected/cancelled diagnostics without secrets.
pub async fn authenticate(
    stream: &mut impl ByteStream,
    attempt: LoginAttempt<'_>,
    mut cancelled: impl FnMut() -> bool,
    mut progress: impl FnMut(&'static str),
) -> Result<LoginSession> {
    let LoginAttempt {
        profile,
        key,
        credentials,
        entropy,
        crcs,
    } = attempt;
    if cancelled() {
        return Err(ProtocolError::new("login", "cancelled"));
    }
    progress("Requesting login challenge");
    send(stream, &[14], "login handshake").await?;
    let response = read::<1>(stream, "login handshake").await?[0];
    if response != 0 {
        return Err(rejected(response));
    }
    let session_id = read::<8>(stream, "session challenge").await?;
    let seed = std::array::from_fn(|i| {
        u32::from_be_bytes([
            entropy.0[i * 4],
            entropy.0[i * 4 + 1],
            entropy.0[i * 4 + 2],
            entropy.0[i * 4 + 3],
        ])
    });
    if cancelled() {
        return Err(ProtocolError::new("login", "cancelled"));
    }
    let packet = encode(profile, key, &credentials, &entropy, seed, session_id, crcs)?;
    drop(credentials);
    send(stream, &packet, "login request").await?;
    drop(packet);
    let incoming = Isaac::new(seed.map(|s| s.wrapping_add(50)));
    let outgoing = Isaac::new(seed);
    let mut challenges = 0;
    loop {
        if cancelled() {
            return Err(ProtocolError::new("login", "cancelled"));
        }
        let response = read::<1>(stream, "login response").await?[0];
        match response {
            2 => {
                let session = login_success(stream, incoming, outgoing).await?;
                progress("Authenticated; receiving initial state");
                return Ok(session);
            }
            69 if challenges == 0 => {
                challenges += 1;
                progress("Solving server proof of work");
                let len = usize::from(u16::from_be_bytes(read::<2>(stream, "proof length").await?));
                if !(4..=2048).contains(&len) {
                    return Err(ProtocolError::new("proof", "challenge length limit"));
                }
                let mut bytes = vec![0; len];
                exact(stream, &mut bytes, "proof challenge").await?;
                let nonce = solve_proof(&bytes, &mut cancelled)?;
                let mut reply = vec![19, 0, 8];
                reply.extend_from_slice(&nonce.to_be_bytes());
                send(stream, &reply, "proof reply").await?;
                progress("Waiting for authentication");
            }
            _ => return Err(rejected(response)),
        }
    }
}
async fn login_success(
    stream: &mut impl ByteStream,
    mut incoming: Isaac,
    outgoing: Isaac,
) -> Result<LoginSession> {
    if read::<1>(stream, "login success marker").await?[0] != 37 {
        return Err(ProtocolError::new(
            "login success",
            "unexpected size marker",
        ));
    }
    // RSProt explicitly emits marker 37 followed by exactly 34 payload bytes.
    let bytes = read::<34>(stream, "login success").await?;
    let mut r = Reader::new(&bytes, "login success");
    let auth = r.u8()?;
    if auth > 1 {
        return Err(ProtocolError::new(
            "login success",
            "invalid authenticator flag",
        ));
    }
    for _ in 0..4 {
        let _ = r.u8()?;
        if auth == 1 {
            incoming.next_word();
        }
    }
    let staff_level = r.u8()?;
    let _moderator = r.u8()?;
    let player = r.u16()?;
    let member = r.u8()?;
    if !(1..2048).contains(&player) || member > 1 {
        return Err(ProtocolError::new(
            "login success",
            "invalid player identity",
        ));
    }
    Ok(LoginSession {
        player: PlayerIndex::new(u32::from(player)),
        member: member == 1,
        staff_level,
        incoming,
        outgoing,
    })
}

fn encode(
    profile: ServerProfile,
    key: &PublicKey,
    credentials: &Credentials,
    entropy: &LoginEntropy,
    seed: [u32; 4],
    session: [u8; 8],
    crcs: &[u32; 23],
) -> Result<Zeroizing<Vec<u8>>> {
    let mut rsa = Zeroizing::new(vec![1]);
    for word in seed {
        rsa.extend_from_slice(&word.to_be_bytes());
    }
    rsa.extend_from_slice(&session);
    rsa.extend_from_slice(&[2, 0, 0, 0, 0, 0]);
    string(&mut rsa, &credentials.password)?;
    let plain = Zeroizing::new(BigUint::from_bytes_be(&rsa));
    if *plain >= key.modulus {
        return Err(ProtocolError::new(
            "RSA",
            "credential block exceeds modulus",
        ));
    }
    let mut encrypted = plain.modpow(&key.exponent, &key.modulus).to_bytes_be();
    if encrypted.first().is_some_and(|v| v & 128 != 0) {
        encrypted.insert(0, 0);
    }
    let mut body = Zeroizing::new(Vec::new());
    string(&mut body, credentials.username.trim())?;
    body.push(2);
    body.extend_from_slice(&765_u16.to_be_bytes());
    body.extend_from_slice(&503_u16.to_be_bytes());
    body.extend_from_slice(&entropy.0[16..]);
    body.push(0);
    body.extend_from_slice(&[0; 5]); // site settings, affiliate, deep links
    // Minimal reproducible platform stats; no host hardware identifiers are sent.
    body.extend_from_slice(&[9, 3, 1, 0, 0, 4, 21, 0, 0, 1, 2, 0, 1, 0, 0, 0, 0, 0]);
    for _ in 0..4 {
        body.extend_from_slice(&[0, 0]);
    }
    body.extend_from_slice(&[0, 0, 0]);
    for _ in 0..2 {
        body.extend_from_slice(&[0, 0]);
    }
    body.extend_from_slice(&[0; 18]);
    for _ in 0..2 {
        body.extend_from_slice(&[0, 0]);
    }
    body.push(1);
    body.extend_from_slice(&[0; 4]);
    for (index, kind) in [
        (20, 3),
        (0, 2),
        (8, 3),
        (1, 1),
        (13, 1),
        (15, 3),
        (17, 1),
        (9, 3),
        (18, 3),
        (5, 0),
        (7, 1),
        (16, 1),
        (19, 0),
        (3, 1),
        (12, 1),
        (14, 1),
        (6, 1),
        (21, 3),
        (2, 1),
        (4, 0),
        (22, 2),
        (11, 0),
        (10, 2),
    ] {
        let [a, b, c, d] = crcs[index].to_be_bytes();
        body.extend_from_slice(&match kind {
            0 => [a, b, c, d],
            1 => [d, c, b, a],
            2 => [c, d, a, b],
            _ => [b, a, d, c],
        });
    }
    xtea(&mut body, seed);
    let mut payload = Zeroizing::new(Vec::new());
    payload.extend_from_slice(&u32::from(profile.revision().major()).to_be_bytes());
    payload
        .extend_from_slice(&u32::from(profile.revision().subrevision().unwrap_or(0)).to_be_bytes());
    payload.extend_from_slice(&0_u32.to_be_bytes());
    payload.extend_from_slice(&[1, 0, 0]);
    payload.extend_from_slice(
        &u16::try_from(encrypted.len())
            .map_err(|_| ProtocolError::new("RSA", "length overflow"))?
            .to_be_bytes(),
    );
    payload.extend_from_slice(&encrypted);
    payload.extend_from_slice(&body);
    let mut packet = Zeroizing::new(vec![16]);
    packet.extend_from_slice(
        &u16::try_from(payload.len())
            .map_err(|_| ProtocolError::new("login encode", "payload length limit"))?
            .to_be_bytes(),
    );
    packet.extend_from_slice(&payload);
    Ok(packet)
}
fn rejected(code: u8) -> ProtocolError {
    let detail = match code {
        3 => "invalid username or password",
        6 => "client revision or cache mismatch",
        7 => "world full",
        8 => "login service unavailable",
        10 => "invalid session challenge",
        11 => "account migration required",
        13 => "account save unavailable",
        14 => "server updating",
        16 => "too many attempts",
        18 => "account locked",
        22 => "invalid login packet",
        23 => "login service did not reply",
        24 => "account load failed",
        25 => "unexpected login failure",
        29 => "server supplied a login message",
        56 | 57 => "two-factor authentication required (unsupported)",
        61 => "server requested alternate login (unsupported)",
        _ => "unsupported or rejected login response",
    };
    ProtocolError::new("authentication", format!("{detail} (response {code})"))
}
pub(crate) fn solve_proof(bytes: &[u8], cancelled: &mut impl FnMut() -> bool) -> Result<u64> {
    if bytes.len() < 4
        || bytes[0] != 0
        || bytes[1] != 1
        || bytes[2] > 22
        || bytes.last() != Some(&0)
        || bytes[3..bytes.len() - 1].contains(&0)
    {
        return Err(ProtocolError::new("proof", "unsupported challenge"));
    }
    let salt = std::str::from_utf8(&bytes[3..bytes.len() - 1])
        .map_err(|_| ProtocolError::new("proof", "invalid salt"))?;
    let base = format!("{:x}{:x}{salt}", bytes[1], bytes[2]);
    let prefix = Sha256::new().chain_update(base.as_bytes());
    let mut suffix = String::with_capacity(16);
    for nonce in 0_u64..16_777_216 {
        if nonce.is_multiple_of(1024) && cancelled() {
            return Err(ProtocolError::new("proof", "cancelled"));
        }
        suffix.clear();
        write!(suffix, "{nonce:x}").expect("writing a String cannot fail");
        let hash = prefix.clone().chain_update(suffix.as_bytes()).finalize();
        let mut zeros = 0;
        for b in hash {
            zeros += b.leading_zeros();
            if b != 0 {
                break;
            }
        }
        if zeros >= u32::from(bytes[2]) {
            return Ok(nonce);
        }
    }
    Err(ProtocolError::new("proof", "work limit exceeded"))
}

#[cfg(test)]
#[path = "login_tests.rs"]
mod tests;
