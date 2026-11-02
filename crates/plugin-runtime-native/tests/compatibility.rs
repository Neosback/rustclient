//! A declared in-tree API does not make the corresponding external component executable.
use rustclient_plugin_host::FaultKind;
use rustclient_plugin_runtime_native::NativeRuntime;
#[test]
fn deferred_component_version_is_rejected_before_resolving_or_compiling_the_entry() {
    let runtime = NativeRuntime::new().unwrap();
    let package =
        std::path::Path::new(env!("CARGO_MANIFEST_DIR")).join("tests/fixtures/deferred-api");
    let Err(error) = runtime.load(&package) else {
        panic!("API 0.2 execution must remain deferred");
    };
    assert_eq!(error.kind, FaultKind::Contract);
    assert!(error.message.contains("0.2"), "{error}");
    assert!(error.message.contains("0.1"), "{error}");
}

#[test]
fn executable_legacy_component_preserves_queries_storage_logs_and_capability_denial() {
    use rustclient_plugin_api::{Capability, PlayerPosition, PluginEvent};
    use rustclient_plugin_host::{
        MemoryLogSink, MemoryStorage, PluginSet, PluginStorage, StaticView,
    };
    let runtime = NativeRuntime::new().unwrap();
    let package =
        std::path::Path::new(env!("CARGO_MANIFEST_DIR")).join("tests/fixtures/legacy-api");
    for allow_position in [true, false] {
        let (mut manifest, instance) = runtime.load(&package).unwrap();
        if !allow_position {
            manifest.capabilities = [Capability::UiOverlay, Capability::StoragePlugin]
                .into_iter()
                .collect();
        }
        let id = manifest.id.clone();
        let mut plugins = PluginSet::new();
        plugins.register(manifest, instance).unwrap();
        let mut storage = MemoryStorage::default();
        let mut logs = MemoryLogSink::default();
        let view = StaticView {
            position: Some(PlayerPosition {
                x: 3221,
                y: 3218,
                plane: 0,
            }),
            tick: 50,
        };
        plugins.init(&view, &mut storage, &mut logs);
        assert!(
            !plugins.has_game_subscribers(),
            "ABI 0.1 cannot consume typed game events"
        );
        plugins.dispatch(&[PluginEvent::Started], &view, &mut storage, &mut logs);
        for tick in 1..=50 {
            plugins.dispatch(&[PluginEvent::Tick(tick)], &view, &mut storage, &mut logs);
        }
        let lines = plugins.overlay(&view, &mut storage, &mut logs);
        assert_eq!(lines.len(), 1);
        if allow_position {
            assert!(
                lines[0].text.contains("3221,3218 plane 0"),
                "{}",
                lines[0].text
            );
        } else {
            assert!(lines[0].text.contains("unknown"), "{}", lines[0].text);
        }
        assert_eq!(
            storage.load(&id).get("last_tick").map(String::as_str),
            Some("50")
        );
        assert!(logs.records.iter().all(|record| record.plugin == id));
        plugins.dispatch(&[PluginEvent::Stopped], &view, &mut storage, &mut logs);
    }
}
