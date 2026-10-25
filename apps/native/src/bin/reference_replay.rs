//! Offline replay of an inbound payload stream captured from the pinned reference client
//! (`tools/parity/Packet240Agent` with `--packet-stream`) through the real decoder, script and layout path.
//! Prints the resulting widget layout as TSV so `tools/parity/compare-layout.py` can diff it against a live
//! widget dump taken from the same session. Read-only: no network, no credentials, no input.
use rustclient_application::{ConnectionDecoder, ConnectionInput, scripts::ClientScripts};
use rustclient_cache::{Cache, CacheProfile};
use rustclient_game_state::{GameState, SemanticOperation};
use rustclient_game_ui::widgets::{WidgetBounds, WidgetTree};
use rustclient_model::{CanvasSize, InterfaceUpdate, PlayerIndex, WindowMode};
use rustclient_platform_native::DirectoryBlobs;
use rustclient_protocol::{Isaac, LoginSession, ServerProfile};

/// Packets that change interface, variable, inventory, status, chat or clock state. Actor, scene and zone packets
/// need a map placement this replay does not have and cannot change widget layout.
const REPLAYED: &[u16] = &[
    10, 11, 14, 18, 19, 23, 30, 35, 45, 51, 54, 63, 66, 68, 73, 74, 76, 78, 92, 94, 100, 106, 116,
    120, 127,
];
const SEED: [u32; 4] = [1, 2, 3, 4];

fn hex(text: &str) -> Vec<u8> {
    (0..text.len() / 2)
        .map(|i| u8::from_str_radix(&text[i * 2..i * 2 + 2], 16).unwrap_or(0))
        .collect()
}

/// Frame one payload the way the server does, so the unmodified decoder consumes it.
fn frame(cipher: &mut Isaac, opcode: u16, length_kind: i16, payload: &[u8]) -> Vec<u8> {
    let mut out = Vec::new();
    let mut push = |byte: u8, cipher: &mut Isaac| {
        out.push(byte.wrapping_add(cipher.next_word().to_le_bytes()[0]));
    };
    if opcode < 128 {
        push(u8::try_from(opcode).unwrap_or(0), cipher);
    } else {
        push(u8::try_from(128 + (opcode >> 8)).unwrap_or(0), cipher);
        push(u8::try_from(opcode & 255).unwrap_or(0), cipher);
    }
    match length_kind {
        -1 => out.push(u8::try_from(payload.len()).unwrap_or(0)),
        -2 => out.extend_from_slice(&u16::try_from(payload.len()).unwrap_or(0).to_be_bytes()),
        _ => {}
    }
    out.extend_from_slice(payload);
    out
}

fn run() -> Result<(), Box<dyn std::error::Error>> {
    let args: Vec<String> = std::env::args().skip(1).collect();
    let [cache_dir, stream_path, limit, mode, local] = args.as_slice() else {
        return Err(
            "usage: reference_replay CACHE_DIR STREAM.log MAX_PACKETS fixed|resizable LOCAL_INDEX"
                .into(),
        );
    };
    let limit: usize = limit.parse()?;
    let window_mode = match mode.as_str() {
        "fixed" => WindowMode::Fixed,
        "resizable" => WindowMode::Resizable,
        _ => return Err("mode must be fixed or resizable".into()),
    };
    let (mut scripts, mut state, mut decoder) = open_session(cache_dir, window_mode, local)?;
    let lengths = packet_lengths()?;
    let mut framer = Isaac::new(SEED);
    let (mut replayed, mut diagnostics) = (0_usize, Vec::new());
    for line in std::fs::read_to_string(stream_path)?.lines().take(limit) {
        let mut fields = line.splitn(4, ' ');
        let _sequence = fields.next();
        let opcode: u16 = fields.next().ok_or("opcode")?.parse()?;
        let _length = fields.next();
        let payload = hex(fields.next().unwrap_or(""));
        if !REPLAYED.contains(&opcode) {
            continue;
        }
        let kind = lengths.get(usize::from(opcode)).copied().unwrap_or(-3);
        let bytes = frame(&mut framer, opcode, kind, &payload);
        let mut failure = None;
        decoder.receive_inputs(&bytes, |input| {
            if failure.is_some() {
                return;
            }
            let result = match input {
                ConnectionInput::Operation(op) => scripts.commit(&mut state, op),
                ConnectionInput::Script(request) => scripts
                    .enqueue(request, None)
                    .map(|()| scripts.drain(&mut state)),
            };
            match result {
                Ok(errors) => diagnostics.extend(errors),
                Err(error) => failure = Some(error),
            }
        })?;
        if let Some(error) = failure {
            return Err(format!("packet {replayed} (opcode {opcode}): {error}").into());
        }
        replayed += 1;
    }
    // Timer listeners only run on client cycles, which the stream does not carry; REPLAY_CYCLES advances them.
    if let Some(cycles) = std::env::var("REPLAY_CYCLES")
        .ok()
        .and_then(|v| v.parse::<u32>().ok())
    {
        let errors = scripts.commit(
            &mut state,
            SemanticOperation::Scenery(rustclient_game_state::SceneryOperation::AdvanceCycles(
                cycles,
            )),
        )?;
        diagnostics.extend(errors);
    }
    diagnostics.extend(scripts.resize_widgets(&mut state)?);
    eprintln!(
        "replayed {replayed} packets, {} script diagnostics",
        diagnostics.len()
    );
    for diagnostic in diagnostics.iter().take(20) {
        eprintln!("  {diagnostic}");
    }
    print_layout(&state, &scripts)
}

type Session = (ClientScripts<DirectoryBlobs>, GameState, ConnectionDecoder);

/// Authenticate an in-memory session at a fixed placement so the replayed packets are accepted.
fn open_session(
    cache_dir: &str,
    window_mode: WindowMode,
    local: &str,
) -> Result<Session, Box<dyn std::error::Error>> {
    let profile = CacheProfile::OpenRune240;
    let scripts = ClientScripts::new(Cache::open(DirectoryBlobs::open(cache_dir)?)?, profile);
    let login = LoginSession {
        player: PlayerIndex::new(local.parse()?),
        member: true,
        staff_level: 0,
        incoming: Isaac::new(SEED),
        outgoing: Isaac::new([5, 6, 7, 8]),
    };
    let (decoder, authenticated) = ConnectionDecoder::new(ServerProfile::OPENRUNE_240_2, login);
    let mut state = GameState::new();
    state.apply_operation(authenticated)?;
    state.apply_operation(SemanticOperation::Interface(InterfaceUpdate::Window(
        profile.window_metrics(
            window_mode,
            CanvasSize {
                width: 765,
                height: 503,
            },
        ),
    )))?;
    // The login rebuild is not in the stream; any placement activates the session for the replayed packets.
    state.apply_operation(SemanticOperation::EstablishInitialPlacement(
        rustclient_game_state::InitialPlacement {
            position: rustclient_model::WorldTile::new(
                3200,
                3200,
                rustclient_model::Plane::new(0).ok_or("plane")?,
            ),
            map_anchor: rustclient_model::WorldTile::new(
                3136,
                3136,
                rustclient_model::Plane::new(0).ok_or("plane")?,
            ),
            area: None,
        },
    ))?;
    Ok((scripts, state, decoder))
}

fn print_layout(
    state: &GameState,
    scripts: &ClientScripts<DirectoryBlobs>,
) -> Result<(), Box<dyn std::error::Error>> {
    let tree = WidgetTree::build(
        state.interfaces(),
        &scripts.interfaces,
        WidgetBounds::canvas(state.interfaces()),
    )?;
    println!("group\tcomponent\tchild\tx\ty\twidth\theight\tclip_width\tclip_height");
    for node in tree.nodes {
        println!(
            "{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}",
            node.id.interface.get(),
            node.id.component,
            node.address.child.map_or(-1, i64::from),
            node.bounds.x,
            node.bounds.y,
            node.bounds.width,
            node.bounds.height,
            node.clip.width,
            node.clip.height
        );
    }
    Ok(())
}

/// Server packet length kinds from the pinned client table (-1 byte-sized, -2 short-sized, >= 0 fixed).
fn packet_lengths() -> Result<Vec<i16>, Box<dyn std::error::Error>> {
    let table = std::fs::read_to_string("docs/reference/client-240/inputs/server-packets.tsv")?;
    let mut lengths = vec![-3_i16; 256];
    for line in table.lines().filter(|l| !l.starts_with('#')) {
        let mut parts = line.split('\t');
        let (Some(id), _, Some(length)) = (parts.next(), parts.next(), parts.next()) else {
            continue;
        };
        lengths[usize::from(id.parse::<u16>()?)] = length.parse()?;
    }
    Ok(lengths)
}

fn main() -> std::process::ExitCode {
    match run() {
        Ok(()) => std::process::ExitCode::SUCCESS,
        Err(error) => {
            eprintln!("reference replay: {error}");
            std::process::ExitCode::FAILURE
        }
    }
}
