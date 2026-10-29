//! Headless native bootstrap diagnostic. Credentials are accepted only on bounded stdin.
#[path = "login_probe/entity_review.rs"]
mod entity_review;
#[path = "login_probe/interfaces.rs"]
mod interfaces;
#[path = "login_probe/journey.rs"]
mod journey;
use rustclient_application::{ConnectionDecoder, ConnectionInput, SequencePolicyResolver};
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
    let frame_only = args.get(2).is_some_and(|s| s == "--review-gameframe");
    let review = frame_only || args.get(2).is_some_and(|s| s == "--review-interfaces");
    let entities = args.get(2).is_some_and(|s| s == "--review-entities");
    let sample_walk = args.get(2).is_some_and(|s| s == "--walk-sample");
    if args.len() != 2 && !(args.len() == 3 && (travel || review || entities || sample_walk)) {
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
            window: rustclient_model::WindowMetrics::default(),
        },
        || cancel.is_cancelled(),
        |s| println!("{s}"),
    ))?;
    drop(entropy);
    let (mut decoder, authenticated) = ConnectionDecoder::new(profile, login);
    let mut sequence_policies = SequencePolicyResolver::new(
        Cache::open(DirectoryBlobs::open(&args[0])?)?,
        rustclient_cache::CacheProfile::OpenRune240,
    );
    let mut scripts = rustclient_application::scripts::ClientScripts::new(
        cache,
        rustclient_cache::CacheProfile::OpenRune240,
    );
    let mut state = GameState::new();
    state.apply_operation(authenticated)?;
    if frame_only {
        state.apply_operation(SemanticOperation::Interface(
            rustclient_model::InterfaceUpdate::Window(
                rustclient_cache::CacheProfile::OpenRune240.window_metrics(
                    rustclient_model::WindowMode::Resizable,
                    rustclient_model::CanvasSize {
                        width: 1280,
                        height: 800,
                    },
                ),
            ),
        ))?;
    }
    println!(
        "authenticated player {}",
        state
            .authenticated_player()
            .ok_or("missing authentication")?
            .player
            .get()
    );
    pollster::block_on(stream.send(&decoder.encode(ClientIntent::WindowStatus {
        mode: rustclient_model::WindowMode::Resizable,
        width: state.interfaces().window().canvas.width.try_into()?,
        height: state.interfaces().window().canvas.height.try_into()?,
    })?))?;
    let mut bytes = [0; 16384];
    let end = Instant::now()
        + Duration::from_secs(if travel {
            240
        } else if entities {
            20
        } else if review {
            24
        } else {
            8
        });
    let mut journey = travel.then(|| journey::Journey::new(args[0].clone().into(), verify));
    let mut next_cycle = Instant::now();
    let mut next_heartbeat = Instant::now() + Duration::from_secs(5);
    let mut reported = false;
    let mut interface_review = interfaces::Review::default();
    interface_review.frame_only = frame_only;
    let mut entity_review = entity_review::Review::default();
    let mut walked_sample = false;
    let mut logout_sent = false;
    let mut reported_window = state.interfaces().window();
    while Instant::now() < end {
        stream.deadline_after(Duration::from_millis(if review { 20 } else { 200 }));
        match pollster::block_on(stream.receive(&mut bytes)) {
            Ok(0) => return Err("game stream closed".into()),
            Ok(n) => {
                let mut operation_error = None;
                let connected = decoder.receive_inputs(&bytes[..n], |input| {
                    if operation_error.is_some() {
                        return;
                    }
                    let result = match input {
                        ConnectionInput::Control(_) => Ok(Vec::new()),
                        ConnectionInput::Operation(op) => sequence_policies
                            .resolve_operation(op)
                            .map_err(|error| format!("actor scheduling cache: {error}"))
                            .and_then(|op| scripts.commit(&mut state, op)),
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
                    if sample_walk {
                        break;
                    }
                    return Err("server logout".into());
                }
            }
            Err(TransportError::Timeout) => {}
            Err(e) => return Err(e.into()),
        }
        if (journey.is_some() || review) && Instant::now() >= next_cycle {
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
        if review {
            for error in scripts.resize_widgets(&mut state)? {
                eprintln!("widget resize: {error}");
            }
            interface_review.step(&mut state, &mut scripts, &mut decoder, &mut stream)?;
            if frame_only && interface_review.completed {
                break;
            }
        }
        for intent in scripts.take_intents() {
            stream.deadline_after(Duration::from_secs(2));
            pollster::block_on(stream.send(&decoder.encode(intent)?))?;
        }
        let window = state.interfaces().window();
        if window != reported_window {
            stream.deadline_after(Duration::from_secs(2));
            pollster::block_on(stream.send(&decoder.encode(ClientIntent::WindowStatus {
                mode: window.mode,
                width: window.canvas.width.try_into()?,
                height: window.canvas.height.try_into()?,
            })?))?;
            reported_window = window;
        }
        if sample_walk
            && !logout_sent
            && walked_sample
            && state.current_tick().is_some_and(|tick| tick.get() >= 8)
        {
            logout_sent =
                entity_review::logout(&mut state, &mut scripts, &mut decoder, &mut stream)?;
        }
        entity_review.observe(&state);
        if sample_walk
            && !walked_sample
            && state.current_tick().is_some_and(|tick| tick.get() >= 3)
            && let Some(player) = state.entities().local_player()
        {
            let destination = rustclient_model::WorldTile::new(
                player.tile.x + 1,
                player.tile.y,
                player.tile.plane,
            );
            stream.deadline_after(Duration::from_secs(2));
            pollster::block_on(stream.send(&decoder.encode(ClientIntent::Walk {
                destination,
                invert_run: false,
            })?))?;
            walked_sample = true;
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
    entity_review.report(entities)?;
    if review {
        if frame_only && !interface_review.completed {
            return Err("gameframe transition review timed out".into());
        }
        if !frame_only && !interface_review.price_opened {
            return Err("guide prices did not mount interface 464".into());
        }
        println!(
            "review mounted interfaces: {:?}",
            state.interfaces().groups()
        );
        let before = state.interfaces().client_variables().clone();
        for character in "hello".chars() {
            let result = scripts.keyboard(&mut state, None, Some(character), true)?;
            for error in result.errors {
                eprintln!("key listener: {error}");
            }
        }
        for (id, value) in state.interfaces().client_variables() {
            if before.get(id) != Some(value) {
                println!("typed input varc {id}: {value:?}");
            }
        }
        if state.interfaces().client_variables().get(&335)
            != Some(&rustclient_model::ScriptValue::Text("hello".into()))
        {
            return Err("cache chat key listener did not retain typed input".into());
        }
        let result = scripts.keyboard(
            &mut state,
            Some(rustclient_model::WidgetKey::Backspace),
            None,
            true,
        )?;
        if !result.errors.is_empty()
            || state.interfaces().client_variables().get(&335)
                != Some(&rustclient_model::ScriptValue::Text("hell".into()))
        {
            return Err("cache chat key listener did not handle backspace".into());
        }
        println!("chat typing and backspace verified through cache listeners");
    }
    println!(
        "bootstrap smoke passed; {} complete game packets framed; tick {}",
        decoder.framed_packets(),
        state.current_tick().ok_or("missing session time")?.get()
    );
    if std::env::var_os("RUSTCLIENT_WIDGET_DIAGNOSTICS").is_some() {
        println!(
            "root {:?} mounts {:?}",
            state.interfaces().root(),
            state.interfaces().mounts()
        );
        let tree = rustclient_game_ui::widgets::WidgetTree::build(
            state.interfaces(),
            &scripts.interfaces,
            rustclient_game_ui::widgets::WidgetBounds {
                x: 0,
                y: 0,
                width: 765,
                height: 503,
            },
        )?;
        for node in tree.nodes {
            let w = rustclient_game_ui::widgets::resolve_widget(
                state.interfaces(),
                &scripts.interfaces,
                node.address,
            )?;
            println!(
                "node {:?} bounds {:?} clip {:?} content {:?} body {:?}",
                node.address, node.bounds, node.clip, w.content, w.body
            );
        }
    }
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
