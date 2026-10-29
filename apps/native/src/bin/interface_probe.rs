//! Read-only interface/cache diagnostic; does not execute CS2 scripts or claim UI parity.
use rustclient_cache::{Cache, CacheProfile};
use rustclient_model::{InterfaceId, WidgetBody};
use rustclient_platform_native::DirectoryBlobs;
#[expect(
    clippy::too_many_lines,
    reason = "one bounded read-only interface diagnostic workflow"
)]
fn run() -> Result<(), Box<dyn std::error::Error>> {
    let mut args = std::env::args().skip(1);
    let path = args
        .next()
        .ok_or("usage: interface_probe CACHE_DIR [INTERFACE_ID ...]")?;
    let cache = Cache::open(DirectoryBlobs::open(path)?)?;
    println!("cache {}", cache.metadata_fingerprint());
    let args: Vec<_> = args.collect();
    let cs1 = args.iter().any(|arg| arg == "--cs1");
    let mut groups = args
        .into_iter()
        .filter(|arg| arg != "--cs1")
        .map(|id| id.parse::<u32>())
        .collect::<Result<Vec<_>, _>>()?;
    if groups.is_empty() {
        groups = vec![12, 149, 548, 161];
    }
    let mut runtime =
        rustclient_application::scripts::ClientScripts::new(cache, CacheProfile::OpenRune240);
    for group in groups {
        let cache = &runtime.cache;
        let definition =
            CacheProfile::OpenRune240.load_interface(cache, InterfaceId::new(group))?;
        let fonts: std::collections::BTreeSet<_> = definition
            .widgets()
            .values()
            .filter_map(|widget| match widget.body {
                WidgetBody::Text { font, .. } => font,
                _ => None,
            })
            .chain(Some(CacheProfile::OpenRune240.quantity_font(cache)?))
            .collect();
        for id in fonts {
            let font = CacheProfile::OpenRune240.load_widget_font(cache, id)?;
            println!(
                "interface {group}: font {} ascent {} ink ascent/descent {}/{}; literal ABC width {}",
                id.get(),
                font.ascent,
                font.max_ascent,
                font.max_descent,
                font.width("ABC")
            );
        }
        if std::env::var_os("RUSTCLIENT_WIDGET_DIAGNOSTICS").is_some() {
            for w in definition.widgets().values() {
                println!(
                    "component {:?} parent {:?} hidden {} actions {:?} listeners {:?}",
                    w.id, w.parent, w.hidden, w.actions, w.listeners
                );
                if std::env::var_os("RUSTCLIENT_WIDGET_GEOMETRY").is_some() {
                    println!(
                        "  geometry content {:?} layout {:?} body {:?}",
                        w.content, w.layout, w.body
                    );
                }
            }
        }
        let listeners: usize = definition
            .widgets()
            .values()
            .map(|w| w.listeners.len())
            .sum();
        let mut state = rustclient_game_state::GameState::new();
        state.apply_operation(rustclient_game_state::SemanticOperation::BeginSession {
            revision: rustclient_model::Revision::with_subrevision(240, 2),
        })?;
        state.apply_operation(rustclient_game_state::SemanticOperation::Interface(
            rustclient_model::InterfaceUpdate::SetRoot(Some(InterfaceId::new(group))),
        ))?;
        let widget_count = definition.widgets().len();
        let legacy_count = definition
            .widgets()
            .keys()
            .filter(|id| definition.legacy(**id).is_some())
            .count();
        runtime.interfaces.clear();
        runtime.interfaces.insert(definition.id, definition);
        let tree = rustclient_game_ui::widgets::WidgetTree::build(
            state.interfaces(),
            &runtime.interfaces,
            rustclient_game_ui::widgets::WidgetBounds {
                x: 0,
                y: 0,
                width: 765,
                height: 503,
            },
        )?;
        if cs1 {
            let before = state.semantic_fingerprint();
            let request = runtime.visual_request(&state)?;
            let visuals = rustclient_application::scripts::load_widget_visuals(
                &runtime.cache,
                runtime.profile,
                &request,
            );
            let mut frame = rustclient_game_ui::UiFrame::default();
            let projected = runtime.project_widgets(&state, &visuals, &mut frame, None)?;
            if before != state.semantic_fingerprint() {
                return Err("CS1 projection mutated semantic state".into());
            }
            println!(
                "CS1 interface {group}: legacy={legacy_count} nodes={} quads={} expression_diagnostics={} asset_diagnostics={} semantic_state_unchanged=true",
                projected.tree.nodes.len(),
                frame.quads.len(),
                projected.diagnostics.len(),
                visuals.failures.len()
            );
            for diagnostic in projected.diagnostics.iter().chain(visuals.failures.iter()) {
                println!("  {diagnostic}");
            }
        }
        println!(
            "interface {group}: {widget_count} widgets, {listeners} listeners, {} laid-out nodes (without scripts)",
            tree.nodes.len()
        );
    }
    Ok(())
}
fn main() -> std::process::ExitCode {
    match run() {
        Ok(()) => std::process::ExitCode::SUCCESS,
        Err(e) => {
            eprintln!("interface diagnostic: {e}");
            std::process::ExitCode::FAILURE
        }
    }
}
