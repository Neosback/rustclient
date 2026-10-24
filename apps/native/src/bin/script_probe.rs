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
    if let Some(position) = std::env::args().position(|arg| arg == "--widget-references") {
        let group: u32 = std::env::args()
            .nth(position + 1)
            .ok_or("interface ID required")?
            .parse()?;
        let mut skipped = 0;
        for id in cache.tables()[&rustclient_cache::IndexId(12)].groups.keys() {
            let Ok(script) = profile.load_script(&cache, ScriptId::new(id.0)) else {
                skipped += 1;
                continue;
            };
            for (pc, instruction) in script.instructions.iter().enumerate() {
                if profile
                    .script_widget(instruction.integer)
                    .is_ok_and(|w| w.interface.get() == group)
                {
                    println!(
                        "script {} pc {pc}: {:?} {}",
                        id.0, instruction.opcode, instruction.integer
                    );
                }
            }
        }
        eprintln!("widget reference scan skipped {skipped} non-decodable script archives");
        return Ok(());
    }
    if let Some(position) = std::env::args().position(|arg| arg == "--find-opcode") {
        // Name (e.g. GetGameOption) or raw `Unsupported(N)` number; prints preceding operands.
        let wanted = std::env::args()
            .nth(position + 1)
            .ok_or("opcode name or number required")?;
        let wanted = wanted
            .parse::<u32>()
            .map_or(wanted.clone(), |n| format!("Unsupported({n})"));
        // `summary` tallies every opcode number the profile table does not name.
        let mut tally = std::collections::BTreeMap::<String, (u32, u32)>::new();
        for id in cache.tables()[&rustclient_cache::IndexId(12)].groups.keys() {
            let Ok(script) = profile.load_script(&cache, ScriptId::new(id.0)) else {
                continue;
            };
            for (pc, instruction) in script.instructions.iter().enumerate() {
                if wanted == "summary" {
                    let name = format!("{:?}", instruction.opcode);
                    if name.starts_with("Unsupported") {
                        let entry = tally.entry(name).or_insert((0, id.0));
                        entry.0 += 1;
                    }
                    continue;
                }
                if format!("{:?}", instruction.opcode) == wanted {
                    let context: Vec<String> = script.instructions[pc.saturating_sub(4)..=pc]
                        .iter()
                        .map(|i| format!("{:?} {}", i.opcode, i.integer))
                        .collect();
                    println!("script {} pc {pc}: {}", id.0, context.join(" | "));
                }
            }
        }
        for (name, (count, first)) in &tally {
            println!("{name}: {count} uses, first in script {first}");
        }
        return Ok(());
    }
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
    if std::env::args().any(|arg| arg == "--item-check") {
        // Render every item that declares an inventory model through the widget icon path.
        let (mut total, mut no_model, mut no_model_placeholder, mut failed, mut drawn) =
            (0_u32, 0_u32, 0_u32, 0_u32, 0_u32);
        for id in 0..40_000 {
            let Ok(def) = profile.load_item(&cache, rustclient_model::ItemDefinitionId::new(id))
            else {
                continue;
            };
            total += 1;
            let Some(model) = def.icon_model else {
                no_model += 1;
                no_model_placeholder += u32::from(def.placeholder_template.is_some());
                continue;
            };
            let icon = profile
                .load_widget_model(&cache, model)
                .map_err(|e| e.to_string())
                .and_then(|model| {
                    rustclient_scene::item_icon::item_icon(&def, &model).map_err(|e| e.to_string())
                });
            match icon {
                Ok(_) => drawn += 1,
                Err(e) => {
                    failed += 1;
                    if failed <= 5 {
                        println!("item {id}: {e}");
                    }
                }
            }
        }
        println!(
            "items: {total} defined, {drawn} icons drawn, {no_model} without a model ({no_model_placeholder} placeholders), {failed} failed"
        );
        return Ok(());
    }
    if let Some(position) = std::env::args().position(|arg| arg == "--item") {
        for id in std::env::args().skip(position + 1) {
            let id = id.parse::<u32>()?;
            let bytes = profile.script_definition(
                &cache,
                rustclient_cache::osrs240::script_tables::DefinitionKind::Item,
                id,
            )?;
            println!("item {id} raw {bytes:02x?}");
            println!(
                "{:#?}",
                profile.load_item(&cache, rustclient_model::ItemDefinitionId::new(id))?
            );
        }
        return Ok(());
    }
    if std::env::args().any(|a| a == "--db-check") {
        // Decode every table, master index, column index and row the cache provides.
        let (mut tables, mut rows, mut indexes, mut unindexed) = (0_u32, 0_u32, 0_u32, 0_u32);
        for table_id in 0..8192 {
            let Ok(table) = profile.load_db_table(&cache, table_id) else {
                continue;
            };
            tables += 1;
            let Ok(master) = profile.load_db_index(&cache, table_id, None) else {
                unindexed += 1;
                continue;
            };
            let all = master.find(&rustclient_cache::osrs240::db_tables::DbKey::Int(0), 0);
            for row in all.unwrap_or_default() {
                let row = profile.load_db_row(&cache, *row)?;
                assert_eq!(row.table, Some(table_id), "row table id");
                rows += 1;
            }
            for column in 0..table.columns.len() {
                if table.columns[column].is_some()
                    && let Ok(column) = u8::try_from(column)
                    && profile
                        .load_db_index(&cache, table_id, Some(column))
                        .is_ok()
                {
                    indexes += 1;
                }
            }
        }
        println!(
            "db: {tables} tables ({unindexed} without an index), {rows} rows, {indexes} column indexes decoded"
        );
        return Ok(());
    }
    if let Some(position) = std::env::args().position(|a| a == "--enum") {
        for id in std::env::args().skip(position + 1) {
            let id = id.parse::<u32>()?;
            let table = profile.load_script_enum(&cache, id)?;
            println!(
                "enum {id} input {} output {} default {:?}",
                table.input, table.output, table.default
            );
            for (key, value) in &table.entries {
                println!("  {key} -> {value:?}");
            }
        }
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
        // Optional extra interface groups follow `--execute` (e.g. 116 settings, 134 all-settings).
        let mut groups: Vec<u32> = std::env::args()
            .skip_while(|a| a != "--execute")
            .skip(1)
            .filter_map(|a| a.parse().ok())
            .collect();
        if groups.is_empty() {
            groups = vec![12, 15, 149, 548, 161];
        }
        for group in groups {
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
        if std::env::var_os("RUSTCLIENT_COMPONENTS").is_some() {
            for (id, definition) in &runtime.interfaces {
                if id.get() != 116 {
                    continue;
                }
                for widget in definition.widgets().values() {
                    let Ok(resolved) = rustclient_game_ui::widgets::resolve_widget(
                        state.interfaces(),
                        &runtime.interfaces,
                        rustclient_model::WidgetAddress::component(widget.id),
                    ) else {
                        continue;
                    };
                    if !resolved.listeners.is_empty() || !resolved.actions.is_empty() {
                        println!(
                            "component {}:{} hidden {} content {:?} actions {:?} listeners {:?}",
                            widget.id.interface.get(),
                            widget.id.component,
                            resolved.hidden,
                            resolved.content,
                            resolved.actions,
                            resolved.listeners
                        );
                    }
                }
            }
        }
        // `RUSTCLIENT_TREE=INTERFACE` lays out the executed tree and prints that group's nodes.
        if let Some(group) = std::env::var("RUSTCLIENT_TREE")
            .ok()
            .and_then(|value| value.parse::<u32>().ok())
        {
            let mut frame = rustclient_game_ui::UiFrame::default();
            let tree = rustclient_game_ui::widget_visuals::project_widgets(
                &state,
                &runtime.interfaces,
                &rustclient_game_ui::widget_visuals::WidgetVisualAssets::default(),
                &mut frame,
            )?;
            for node in tree
                .nodes
                .iter()
                .filter(|node| node.address.widget.interface.get() == group)
            {
                println!(
                    "node {}:{} child {:?} bounds {:?} clip {:?}",
                    group,
                    node.address.widget.component,
                    node.address.child,
                    node.bounds,
                    node.clip
                );
                if let Some(runtime) = state.interfaces().runtime().get(&node.address) {
                    println!(
                        "  runtime hidden {:?} body {:?} layout {:?}",
                        runtime.hidden, runtime.body, runtime.layout
                    );
                }
                if std::env::var_os("RUSTCLIENT_LISTENERS").is_some()
                    && let Ok(widget) = rustclient_game_ui::widgets::resolve_widget(
                        state.interfaces(),
                        &runtime.interfaces,
                        node.address,
                    )
                {
                    println!(
                        "  listeners {:?} actions {:?} body {:?} content {:?}",
                        widget.listeners, widget.actions, widget.body, widget.content
                    );
                }
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
