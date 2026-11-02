//! Plugin calls cannot select another plugin's storage namespace or diagnostic identity.
use rustclient_plugin_api::{
    Capability, LogLevel, OverlayLine, PluginApiVersion, PluginEvent, PluginId, PluginManifest,
};
use rustclient_plugin_host::{
    MemoryLogSink, MemoryStorage, PluginCall, PluginFault, PluginInstance, PluginSet,
    PluginStorage, StaticView,
};
use std::{cell::RefCell, collections::BTreeMap, rc::Rc};

type Results = Rc<RefCell<Vec<(bool, bool, Option<String>)>>>;
struct Probe {
    results: Results,
}
impl PluginInstance for Probe {
    fn init(&mut self, call: &mut PluginCall<'_>) -> Result<(), PluginFault> {
        let loaded = call.load_storage();
        let original = loaded.as_ref().ok().and_then(|v| v.get("text").cloned());
        let saved = call.save_storage(&BTreeMap::from([("text".into(), "own update".into())]));
        self.results
            .borrow_mut()
            .push((loaded.is_ok(), saved.is_ok(), original));
        call.log(LogLevel::Info, "attributed");
        Ok(())
    }
    fn on_event(&mut self, _: &mut PluginCall<'_>, _: &[PluginEvent]) -> Result<(), PluginFault> {
        Ok(())
    }
    fn overlay(&mut self, _: &mut PluginCall<'_>) -> Result<Vec<OverlayLine>, PluginFault> {
        Ok(Vec::new())
    }
}
#[test]
fn grants_mediate_only_the_executing_namespace_and_logs_keep_the_executing_identity() {
    let allowed = PluginId::parse("test.allowed").unwrap();
    let denied = PluginId::parse("test.denied").unwrap();
    let other = PluginId::parse("test.other").unwrap();
    let results = Results::default();
    let mut set = PluginSet::new();
    for (id, grants) in [
        (&allowed, vec![Capability::StoragePlugin]),
        (&denied, vec![]),
    ] {
        set.register(
            PluginManifest {
                id: id.clone(),
                name: id.to_string(),
                version: "1".into(),
                plugin_api: PluginApiVersion::current(),
                capabilities: grants.into_iter().collect(),
                entry: String::new(),
            },
            Box::new(Probe {
                results: results.clone(),
            }),
        )
        .unwrap();
    }
    let mut storage = MemoryStorage::default();
    storage.save(
        &allowed,
        &BTreeMap::from([("text".into(), "allowed only".into())]),
    );
    storage.save(
        &other,
        &BTreeMap::from([("text".into(), "private other".into())]),
    );
    let mut logs = MemoryLogSink::default();
    set.init(&StaticView::default(), &mut storage, &mut logs);
    assert_eq!(
        *results.borrow(),
        vec![
            (true, true, Some("allowed only".into())),
            (false, false, None)
        ]
    );
    assert_eq!(storage.load(&other)["text"], "private other");
    assert!(storage.load(&denied).is_empty());
    assert_eq!(storage.load(&allowed)["text"], "own update");
    assert_eq!(
        logs.records
            .iter()
            .filter(|r| r.message == "attributed")
            .map(|r| &r.plugin)
            .collect::<Vec<_>>(),
        vec![&allowed, &denied]
    );
    assert_eq!(
        logs.records
            .iter()
            .filter(|r| r.level == LogLevel::Warn)
            .count(),
        1
    );
}
