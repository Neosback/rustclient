//! Read-only interface/cache diagnostic; does not execute scripts or claim UI parity.
use rustclient_cache::{Cache, CacheProfile};
use rustclient_model::{InterfaceId, WidgetBody};
use rustclient_platform_native::DirectoryBlobs;
fn run() -> Result<(), Box<dyn std::error::Error>> {
    let mut args = std::env::args().skip(1);
    let path = args
        .next()
        .ok_or("usage: interface_probe CACHE_DIR [INTERFACE_ID ...]")?;
    let cache = Cache::open(DirectoryBlobs::open(path)?)?;
    println!("cache {}", cache.metadata_fingerprint());
    let mut groups = args
        .map(|id| id.parse::<u32>())
        .collect::<Result<Vec<_>, _>>()?;
    if groups.is_empty() {
        groups = vec![12, 149, 548, 161];
    }
    for group in groups {
        let definition =
            CacheProfile::OpenRune240.load_interface(&cache, InterfaceId::new(group))?;
        let fonts: std::collections::BTreeSet<_> = definition
            .widgets()
            .values()
            .filter_map(|widget| match widget.body {
                WidgetBody::Text { font, .. } => font,
                _ => None,
            })
            .chain(Some(CacheProfile::OpenRune240.quantity_font(&cache)?))
            .collect();
        for id in fonts {
            let font = CacheProfile::OpenRune240.load_widget_font(&cache, id)?;
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
        let assets = std::collections::BTreeMap::from([(definition.id, definition)]);
        let tree = rustclient_game_ui::widgets::WidgetTree::build(
            state.interfaces(),
            &assets,
            rustclient_game_ui::widgets::WidgetBounds {
                x: 0,
                y: 0,
                width: 765,
                height: 503,
            },
        )?;
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
