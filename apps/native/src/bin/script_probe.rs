//! Read-only script and transitive listener instruction inventory.
use rustclient_cache::{Cache, CacheProfile};
use rustclient_model::{InterfaceId, ScriptId, ScriptValue};
use rustclient_platform_native::DirectoryBlobs;
use std::collections::{BTreeMap, BTreeSet};
#[expect(
    clippy::too_many_lines,
    reason = "single read-only diagnostic command dispatcher"
)]
fn main() -> Result<(), Box<dyn std::error::Error>> {
    let path = std::env::args()
        .nth(1)
        .ok_or("usage: script_probe CACHE_DIR")?;
    let cache = Cache::open(DirectoryBlobs::open(path)?)?;
    let profile = CacheProfile::OpenRune240;
    if std::env::args().any(|arg| arg == "--fingerprints") {
        use rustclient_game_state::{CommitStreamFingerprintBuilder, GameState, SemanticOperation};
        use rustclient_model::{Revision, TickSpan};
        println!("inactive {}", GameState::new().semantic_fingerprint().get());
        for revision in [Revision::new(240), Revision::with_subrevision(240, 2)] {
            let mut state = GameState::new();
            state.apply_operation(SemanticOperation::BeginSession { revision })?;
            state.apply_operation(SemanticOperation::AdvanceTick(TickSpan::new(5)))?;
            println!("revision {revision} {}", state.semantic_fingerprint().get());
        }
        let mut state = GameState::new();
        let mut stream = CommitStreamFingerprintBuilder::new();
        for op in [
            SemanticOperation::BeginSession {
                revision: Revision::new(240),
            },
            SemanticOperation::AdvanceTick(TickSpan::new(2)),
            SemanticOperation::AdvanceTick(TickSpan::ZERO),
            SemanticOperation::EndSession,
        ] {
            stream.push(state.apply_operation(op)?);
        }
        println!("stream {}", stream.finish().get());
        println!(
            "empty_stream {}",
            CommitStreamFingerprintBuilder::new().finish().get()
        );
        return Ok(());
    }
    if let Some(position) = std::env::args().position(|a| a == "--dump") {
        for id in std::env::args().skip(position + 1) {
            let id = id.parse::<u32>()?;
            let script = profile.load_script(&cache, ScriptId::new(id))?;
            println!(
                "script {id} locals {:?} arguments {:?}",
                script.locals, script.arguments
            );
            for (pc, i) in script.instructions.iter().enumerate() {
                println!("{pc}: {:?} {} {:?}", i.opcode, i.integer, i.text);
            }
        }
        return Ok(());
    }
    if std::env::args().any(|a| a == "--execute") {
        let mut runtime = rustclient_application::scripts::ClientScripts::new(cache, profile);
        let mut state = rustclient_game_state::GameState::new();
        state.apply_operation(rustclient_game_state::SemanticOperation::BeginSession {
            revision: rustclient_model::Revision::with_subrevision(240, 2),
        })?;
        for group in [12, 15, 149, 548, 161] {
            state.apply_operation(rustclient_game_state::SemanticOperation::Interface(
                rustclient_model::InterfaceUpdate::SetRoot(Some(InterfaceId::new(group))),
            ))?;
            runtime.synchronize(&state)?;
            let errors = runtime.drain(&mut state);
            println!(
                "root {group}: {} runtime widgets, {} script errors",
                state.interfaces().runtime().len(),
                errors.len()
            );
            for error in errors {
                println!("{error}");
            }
        }
        return Ok(());
    }
    let mut pending = BTreeSet::new();
    for id in [12, 15, 149, 548, 161] {
        let group = profile.load_interface(&cache, InterfaceId::new(id))?;
        for widget in group.widgets().values() {
            for (kind, args) in &widget.listeners {
                if let Some(ScriptValue::Int(id)) = args.first() {
                    println!(
                        "listener {}:{} {kind:?} script {id}",
                        widget.id.interface, widget.id.component
                    );
                    if let Ok(id) = u32::try_from(*id) {
                        pending.insert(id);
                    }
                }
            }
        }
    }
    let mut seen = BTreeSet::new();
    let mut histogram = BTreeMap::<String, usize>::new();
    while let Some(id) = pending.pop_first() {
        if !seen.insert(id) {
            continue;
        }
        let script = profile.load_script(&cache, ScriptId::new(id))?;
        println!(
            "script {id} {:?} locals {:?} args {:?} instructions {}",
            script.name,
            script.locals,
            script.arguments,
            script.instructions.len()
        );
        for i in &script.instructions {
            *histogram.entry(format!("{:?}", i.opcode)).or_default() += 1;
            if i.opcode == rustclient_cache::script::Opcode::Invoke {
                pending.insert(u32::try_from(i.integer)?);
            }
        }
    }
    println!("{} scripts; instructions: {histogram:?}", seen.len());
    Ok(())
}
