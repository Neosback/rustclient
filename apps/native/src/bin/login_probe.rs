//! Headless native bootstrap diagnostic. Credentials are accepted only on bounded stdin.
#[path = "login_probe/journey.rs"]
mod journey;
use rustclient_application::{ConnectionDecoder, ConnectionInput};
use rustclient_cache::Cache;
use rustclient_game_state::{GameState, SemanticOperation};
use rustclient_platform::{ByteStream, TransportError};
use rustclient_platform_native::{ConnectionCancellation, DirectoryBlobs, NativeTcp};
use rustclient_protocol::{
    ClientIntent, Credentials, LoginAttempt, LoginEntropy, PublicKey, ServerProfile, authenticate,
    login_checksums, verify_cache,
};
use std::{
    io::{self, Read},
    time::{Duration, Instant},
};
use zeroize::Zeroizing;
fn read_credentials() -> Result<Credentials, Box<dyn std::error::Error>> {
    let mut input = Zeroizing::new(String::new());
    io::stdin().take(2049).read_to_string(&mut input)?;
    if input.len() > 2048 {
        return Err("credential input exceeds limit".into());
    }
    let mut lines = input.lines();
    let username = lines.next().ok_or("username stdin line required")?;
    let password = lines.next().ok_or("password stdin line required")?;
    if lines.next().is_some() {
        return Err("expected exactly two stdin lines".into());
    }
    Ok(Credentials::new(username.to_owned(), password.to_owned())?)
}
#[expect(
    clippy::too_many_lines,
    reason = "bounded live diagnostic stages with one credential lifetime"
)]
fn run() -> Result<(), Box<dyn std::error::Error>> {
    let args: Vec<String> = std::env::args().skip(1).collect();
    let verify = args.get(2).is_some_and(|s| s == "--verify-bank");
    let travel = args
        .get(2)
        .is_some_and(|s| s == "--walk-and-bank" || s == "--verify-bank");
    if args.len() != 2 && !(args.len() == 3 && travel) {
        return Err(
            "usage: login_probe CACHE_DIR PUBLIC_KEY (two stdin lines: username, password)".into(),
        );
    }
    let cache = Cache::open(DirectoryBlobs::open(&args[0])?)?;
    let crcs = login_checksums(cache.reference_crcs().iter().map(|(i, c)| (i.0, *c)))?;
    println!("cache {}", cache.metadata_fingerprint());
    let mut key_text = String::new();
    std::fs::File::open(&args[1])?
        .take(2049)
        .read_to_string(&mut key_text)?;
    let key = PublicKey::parse(&key_text)?;
    let credentials = read_credentials()?;
    let cancel = ConnectionCancellation::default();
    let profile = ServerProfile::OPENRUNE_240_2;
    let mut js5 = NativeTcp::connect("127.0.0.1", 43594, cancel.clone())?;
    pollster::block_on(verify_cache(&mut js5, profile, &crcs))?;
    drop(js5);
    println!("JS5 compatibility verified (23 archives)");
    let mut entropy = Zeroizing::new([0; 40]);
    getrandom::fill(&mut *entropy).map_err(|_| "OS entropy unavailable")?;
    let mut stream = NativeTcp::connect("127.0.0.1", 43594, cancel.clone())?;
    let login = pollster::block_on(authenticate(
        &mut stream,
        LoginAttempt {
            profile,
            key: &key,
            credentials,
            entropy: LoginEntropy::new(*entropy),
            crcs: &crcs,
        },
        || cancel.is_cancelled(),
        |s| println!("{s}"),
    ))?;
    drop(entropy);
    let (mut decoder, authenticated) = ConnectionDecoder::new(profile, login);
    let mut scripts = rustclient_application::scripts::ClientScripts::new(
        cache,
        rustclient_cache::CacheProfile::OpenRune240,
    );
    let mut state = GameState::new();
    state.apply_operation(authenticated)?;
    println!(
        "authenticated player {}",
        state
            .authenticated_player()
            .ok_or("missing authentication")?
            .player
            .get()
    );
    pollster::block_on(stream.send(&decoder.encode(ClientIntent::WindowStatus {
        width: 765,
        height: 503,
    })?))?;
    let mut bytes = [0; 16384];
    let end = Instant::now() + Duration::from_secs(if travel { 240 } else { 8 });
    let mut journey = travel.then(|| journey::Journey::new(args[0].clone().into(), verify));
    let mut next_cycle = Instant::now();
    let mut next_heartbeat = Instant::now() + Duration::from_secs(5);
    let mut reported = false;
    while Instant::now() < end {
        stream.deadline_after(Duration::from_millis(200));
        match pollster::block_on(stream.receive(&mut bytes)) {
            Ok(0) => return Err("game stream closed".into()),
            Ok(n) => {
                let mut operation_error = None;
                let connected = decoder.receive_inputs(&bytes[..n], |input| {
                    let result = match input {
                        ConnectionInput::Operation(op) => scripts.commit(&mut state, op),
                        ConnectionInput::Script(request) => scripts
                            .enqueue(request, None)
                            .map(|()| scripts.drain(&mut state)),
                    };
                    match result {
                        Ok(errors) => {
                            for error in errors {
                                eprintln!("client script: {error}");
                            }
                        }
                        Err(e) => operation_error = Some(e),
                    }
                })?;
                if let Some(error) = operation_error {
                    return Err(error.into());
                }
                if !connected {
                    return Err("server logout".into());
                }
            }
            Err(TransportError::Timeout) => {}
            Err(e) => return Err(e.into()),
        }
        if journey.is_some() && Instant::now() >= next_cycle {
            for error in scripts.commit(
                &mut state,
                SemanticOperation::Scenery(rustclient_game_state::SceneryOperation::AdvanceCycles(
                    1,
                )),
            )? {
                eprintln!("client timer: {error}");
            }
            next_cycle = Instant::now() + Duration::from_millis(20);
        }
        if let Some(journey) = &mut journey {
            journey.step(&mut state, &mut scripts, &mut decoder, &mut stream)?;
            if journey.completed() {
                break;
            }
        }
        if !reported && let Some(placement) = state.initial_placement() {
            println!("initial placement {placement:?}");
            reported = true;
        }
        if Instant::now() >= next_heartbeat {
            stream.deadline_after(Duration::from_secs(2));
            pollster::block_on(stream.send(&decoder.encode(ClientIntent::KeepAlive)?))?;
            next_heartbeat = Instant::now() + Duration::from_secs(5);
        }
    }
    if journey.as_ref().is_some_and(|j| !j.completed()) {
        return Err("walk-and-bank diagnostic timed out".into());
    }
    if !reported {
        return Err("no initial placement received".into());
    }
    println!(
        "bootstrap smoke passed; {} complete game packets framed; tick {}",
        decoder.framed_packets(),
        state.current_tick().ok_or("missing session time")?.get()
    );
    state.apply_operation(SemanticOperation::EndSession)?;
    drop(stream);
    println!("disconnected; authenticated state cleared");
    Ok(())
}
fn main() -> std::process::ExitCode {
    match run() {
        Ok(()) => std::process::ExitCode::SUCCESS,
        Err(e) => {
            eprintln!("login probe: {e}");
            std::process::ExitCode::FAILURE
        }
    }
}
