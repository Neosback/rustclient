use rustclient_plugin_api::{PluginEvent, PluginId};
use rustclient_plugin_host::{
    MemoryLogSink, MemoryStorage, PluginSet, StaticView,
    configuration::{ConfigValue, Configuration},
    required::{Builtin, schemas},
};

#[test]
fn required_services_start_without_a_player_and_survive_host_disable_requests() {
    let mut set = PluginSet::new();
    for builtin in Builtin::ALL {
        set.register_required(builtin).unwrap();
    }
    let mut storage = MemoryStorage::new();
    let mut logs = MemoryLogSink::default();
    // Repeated pre-init failures exercise required-service fault retention.
    for _ in 0..4 {
        set.dispatch(
            &[PluginEvent::Started],
            &StaticView::default(),
            &mut storage,
            &mut logs,
        );
    }
    assert!(
        set.iter()
            .all(|plugin| plugin.required() && plugin.enabled())
    );
    set.init(&StaticView::default(), &mut storage, &mut logs);
    for builtin in Builtin::ALL {
        let id = PluginId::parse(builtin.id()).unwrap();
        assert!(set.set_enabled(&id, false).is_err());
        assert!(set.unregister(&id).is_err());
    }
    assert_eq!(set.len(), 2);
    assert!(set.iter().all(|plugin| plugin.faults() == 0));
}

#[test]
fn descriptors_validate_defaults_and_transaction_failure_keeps_accepted_values() {
    let schemas = schemas().unwrap();
    assert_eq!(
        schemas
            .iter()
            .map(|schema| schema.settings.len())
            .sum::<usize>(),
        50
    );
    for schema in &schemas {
        Configuration::new(schema.clone(), None).unwrap();
    }
    let mut gpu = Configuration::new(schemas[0].clone(), None).unwrap();
    assert_eq!(gpu.value("drawDistance"), Some(&ConfigValue::Integer(50)));
    let error = gpu.change("drawDistance", ConfigValue::Integer(80), |_, _| {
        Err("disk full".into())
    });
    assert_eq!(error, Err("disk full".into()));
    assert_eq!(gpu.generation(), 0);
    assert_eq!(gpu.value("drawDistance"), Some(&ConfigValue::Integer(50)));
    let event = gpu
        .change("drawDistance", ConfigValue::Integer(80), |_, _| Ok(()))
        .unwrap()
        .unwrap();
    assert_eq!(event.generation, 1);
    let saved = serde_json::to_vec(gpu.document()).unwrap();
    let restored = Configuration::new(schemas[0].clone(), Some(&saved)).unwrap();
    assert_eq!(
        restored.value("drawDistance"),
        Some(&ConfigValue::Integer(80))
    );
    assert!(
        gpu.change("drawDistance", ConfigValue::Integer(999), |_, _| panic!(
            "invalid value reached owner"
        ))
        .is_err()
    );
    assert!(
        gpu.change("drawDistance", ConfigValue::Integer(80), |_, _| panic!(
            "noop persisted"
        ))
        .unwrap()
        .is_none()
    );
    gpu.reset(|_, _| Ok(())).unwrap();
    assert_eq!(gpu.value("drawDistance"), Some(&ConfigValue::Integer(50)));
}
