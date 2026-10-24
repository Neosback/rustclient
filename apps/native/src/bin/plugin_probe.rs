//! Headless probe for the plugin vertical slice.
//!
//! Loads one plugin package through the native component runtime, drives a synthetic session
//! (started + committed ticks + stopped) against a fixed host view, and prints overlays,
//! diagnostics and persisted configuration. Run twice with the same `--storage` directory to
//! observe plugin-local persistence.
//!
//! `cargo run -p rustclient-native --bin plugin_probe -- --package PATH --storage PATH [--ticks N]`

use rustclient_plugin_api::{PlayerPosition, PluginEvent};
use rustclient_plugin_host::{DirectoryStorage, MemoryLogSink, PluginSet, StaticView};
use rustclient_plugin_runtime_native::NativeRuntime;
use std::process::ExitCode;

fn main() -> ExitCode {
    match run() {
        Ok(()) => ExitCode::SUCCESS,
        Err(error) => {
            eprintln!("plugin probe: {error}");
            ExitCode::FAILURE
        }
    }
}

fn run() -> Result<(), String> {
    let mut package = None;
    let mut storage = None;
    let mut ticks = 120_u64;
    let mut args = std::env::args().skip(1);
    while let Some(arg) = args.next() {
        match arg.as_str() {
            "--package" => package = Some(args.next().ok_or("--package requires a path")?),
            "--storage" => storage = Some(args.next().ok_or("--storage requires a path")?),
            "--ticks" => {
                ticks = args
                    .next()
                    .ok_or("--ticks requires a count")?
                    .parse()
                    .map_err(|_| "invalid tick count")?;
            }
            _ => return Err(format!("unknown option {arg:?}")),
        }
    }
    let package = package.ok_or("--package PATH is required")?;
    let storage_root = storage.ok_or("--storage PATH is required")?;

    let runtime = NativeRuntime::new().map_err(|fault| fault.to_string())?;
    let (manifest, instance) = runtime
        .load(std::path::Path::new(&package))
        .map_err(|fault| fault.to_string())?;
    let capabilities = manifest
        .capabilities
        .iter()
        .map(|capability| capability.to_string())
        .collect::<Vec<_>>()
        .join(", ");
    println!(
        "loaded {} {} (api {}) capabilities: [{}]",
        manifest.id, manifest.version, manifest.plugin_api, capabilities
    );

    let mut plugins = PluginSet::new();
    plugins
        .register(manifest, instance)
        .map_err(|fault| fault.to_string())?;
    let mut store = DirectoryStorage::new(storage_root.clone().into());
    let mut logs = MemoryLogSink::default();

    let mut view = StaticView {
        position: Some(PlayerPosition {
            x: 3221,
            y: 3218,
            plane: 0,
        }),
        tick: 0,
    };
    plugins.init(&view, &mut store, &mut logs);
    plugins.dispatch(&[PluginEvent::Started], &view, &mut store, &mut logs);
    for tick in 1..=ticks {
        view.tick = tick;
        view.position = Some(PlayerPosition {
            x: 3221 + i32::try_from(tick / 30).unwrap_or(0),
            y: 3218,
            plane: 0,
        });
        plugins.dispatch(&[PluginEvent::Tick(tick)], &view, &mut store, &mut logs);
    }
    let lines = plugins.overlay(&view, &mut store, &mut logs);
    for line in &lines {
        println!(
            "overlay ({}, {}) #{:08X}: {}",
            line.x, line.y, line.argb, line.text
        );
    }
    plugins.dispatch(&[PluginEvent::Stopped], &view, &mut store, &mut logs);

    for record in &logs.records {
        println!(
            "log {} [{}]: {}",
            record.plugin,
            record.level.as_str(),
            record.message
        );
    }
    if lines.is_empty() {
        return Err("plugin produced no overlay lines".into());
    }
    println!(
        "plugin probe passed: {} overlay line(s), {} log record(s), storage {}",
        lines.len(),
        logs.records.len(),
        storage_root
    );
    Ok(())
}
