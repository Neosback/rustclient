use rustclient_plugin_api::{
    Capability, GameEvent, GameSnapshot, Notification, OverlayLine, PluginApiVersion, PluginEvent,
    PluginId, PluginManifest, PluginOutput,
};
use rustclient_plugin_host::{
    FaultKind, LifecycleStatus, MemoryLogSink, MemoryStorage, PluginCall, PluginFault,
    PluginInstance, PluginSet, SnapshotView,
};
use std::{cell::RefCell, rc::Rc};
#[derive(Default)]
struct Seen {
    starts: u32,
    stops: u32,
    events: u32,
    fail_start: bool,
    fail_stop: bool,
    fail_event: bool,
    cycle: u64,
}
struct Probe {
    seen: Rc<RefCell<Seen>>,
    interested: bool,
}
impl PluginInstance for Probe {
    fn init(&mut self, call: &mut PluginCall<'_>) -> Result<(), PluginFault> {
        let mut seen = self.seen.borrow_mut();
        seen.starts += 1;
        seen.cycle = call
            .client()
            .map_err(|e| PluginFault::new(FaultKind::Init, e.to_string()))?
            .cycle;
        if seen.fail_start {
            Err(PluginFault::new(FaultKind::Init, "start rejected"))
        } else {
            Ok(())
        }
    }
    fn shutdown(&mut self, _: &mut PluginCall<'_>) -> Result<(), PluginFault> {
        let mut seen = self.seen.borrow_mut();
        seen.stops += 1;
        if seen.fail_stop {
            Err(PluginFault::new(FaultKind::Contract, "cleanup rejected"))
        } else {
            Ok(())
        }
    }
    fn subscribes_game_events(&self) -> bool {
        self.interested
    }
    fn on_event(&mut self, _: &mut PluginCall<'_>, _: &[PluginEvent]) -> Result<(), PluginFault> {
        Ok(())
    }
    fn overlay(&mut self, _: &mut PluginCall<'_>) -> Result<Vec<OverlayLine>, PluginFault> {
        Ok(vec![])
    }
    fn on_game_events(
        &mut self,
        _: &mut PluginCall<'_>,
        _: &[GameEvent],
    ) -> Result<(), PluginFault> {
        let mut seen = self.seen.borrow_mut();
        seen.events += 1;
        if seen.fail_event {
            Err(PluginFault::new(FaultKind::Event, "event rejected"))
        } else {
            Ok(())
        }
    }
    fn output(&mut self, _: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
        Ok(PluginOutput {
            frame_limit: Some(30),
            notifications: vec![Notification {
                kind: rustclient_plugin_api::NotificationKind::Standard,
                title: "once".into(),
                body: "body".into(),
            }],
            ..PluginOutput::default()
        })
    }
}
fn register(
    set: &mut PluginSet,
    name: &str,
    seen: &Rc<RefCell<Seen>>,
    interested: bool,
) -> PluginId {
    let id = PluginId::parse(name).unwrap();
    set.register(
        PluginManifest {
            id: id.clone(),
            name: name.into(),
            version: "1".into(),
            plugin_api: PluginApiVersion::current(),
            capabilities: [
                Capability::GameReadClient,
                Capability::ClientFrameLimit,
                Capability::ClientNotify,
            ]
            .into_iter()
            .collect(),
            entry: String::new(),
        },
        Box::new(Probe {
            seen: seen.clone(),
            interested,
        }),
    )
    .unwrap();
    id
}
#[test]
fn startup_rollback_retry_cleanup_and_activation_identity_are_independent_of_desired_enablement() {
    let mut set = PluginSet::new();
    let seen = Rc::new(RefCell::new(Seen {
        fail_start: true,
        ..Seen::default()
    }));
    let id = register(&mut set, "test.lifecycle", &seen, true);
    let mut game = GameSnapshot::default();
    game.client.cycle = 42;
    let view = SnapshotView(&game);
    let mut storage = MemoryStorage::new();
    let mut logs = MemoryLogSink::default();
    assert!(
        set.configure(
            &id,
            Default::default(),
            true,
            &view,
            &mut storage,
            &mut logs
        )
        .is_err()
    );
    assert_eq!(
        set.iter().next().unwrap().status(),
        LifecycleStatus::Faulted
    );
    assert!(set.iter().next().unwrap().configured_enabled());
    assert_eq!((seen.borrow().starts, seen.borrow().stops), (1, 1));
    set.dispatch_game(&[GameEvent::ClientTick], &view, &mut storage, &mut logs);
    assert_eq!(seen.borrow().events, 0);
    let old = set.iter().next().unwrap().activation();
    seen.borrow_mut().fail_start = false;
    set.configure(
        &id,
        Default::default(),
        true,
        &view,
        &mut storage,
        &mut logs,
    )
    .unwrap();
    let activation = set.iter().next().unwrap().activation();
    assert_ne!(activation, old);
    assert!(!set.iter().next().unwrap().accepts_activation(old));
    assert_eq!(seen.borrow().cycle, 42);
    assert_eq!(
        set.collect_output(&view, &mut storage, &mut logs)
            .frame_limit,
        Some(30)
    );
    assert!(set.retained_output().notifications.is_empty());
    seen.borrow_mut().fail_stop = true;
    assert!(
        set.configure(
            &id,
            Default::default(),
            false,
            &view,
            &mut storage,
            &mut logs
        )
        .is_err()
    );
    assert!(!set.iter().next().unwrap().configured_enabled());
    assert!(!set.iter().next().unwrap().accepts_activation(activation));
    assert_eq!(set.retained_output().frame_limit, None);
    set.unregister(&id).unwrap();
    assert!(set.is_empty());
    assert_eq!(seen.borrow().stops, 2);
}
#[test]
fn output_success_cannot_hide_game_callback_faults_and_uninterested_instances_do_no_event_work() {
    let mut set = PluginSet::new();
    let bad = Rc::new(RefCell::new(Seen {
        fail_event: true,
        ..Seen::default()
    }));
    let good = Rc::new(RefCell::new(Seen::default()));
    let id = register(&mut set, "test.bad", &bad, true);
    register(&mut set, "test.good", &good, false);
    let game = GameSnapshot::default();
    let view = SnapshotView(&game);
    let mut storage = MemoryStorage::new();
    let mut logs = MemoryLogSink::default();
    set.init(&view, &mut storage, &mut logs);
    for _ in 0..5 {
        set.dispatch_game(&[GameEvent::ClientTick], &view, &mut storage, &mut logs);
        let _ = set.collect_output(&view, &mut storage, &mut logs);
    }
    assert_eq!(bad.borrow().events, 3);
    assert_eq!(bad.borrow().stops, 1);
    assert_eq!(good.borrow().events, 0);
    assert!(!set.has_game_subscribers());
    let plugin = set.iter().find(|p| p.manifest.id == id).unwrap();
    assert_eq!(plugin.status(), LifecycleStatus::Faulted);
    assert_eq!(plugin.faults(), 3);
    assert_eq!(set.retained_output().frame_limit, Some(30));
    assert_eq!(good.borrow().stops, 0);
}

#[test]
fn session_end_revokes_cached_output_without_stopping_the_activation() {
    let mut set = PluginSet::new();
    let seen = Rc::new(RefCell::new(Seen::default()));
    register(&mut set, "test.session", &seen, false);
    let game = GameSnapshot::default();
    let view = SnapshotView(&game);
    let mut storage = MemoryStorage::new();
    let mut logs = MemoryLogSink::default();
    set.init(&view, &mut storage, &mut logs);
    let activation = set.iter().next().unwrap().activation();
    assert_eq!(
        set.collect_output(&view, &mut storage, &mut logs)
            .frame_limit,
        Some(30)
    );
    set.dispatch(&[PluginEvent::Stopped], &view, &mut storage, &mut logs);
    assert_eq!(set.retained_output().frame_limit, None);
    assert!(set.iter().next().unwrap().accepts_activation(activation));
    assert_eq!((seen.borrow().starts, seen.borrow().stops), (1, 0));
    for _ in 0..3 {
        let id = set.iter().next().unwrap().manifest.id.clone();
        set.configure(
            &id,
            Default::default(),
            false,
            &view,
            &mut storage,
            &mut logs,
        )
        .unwrap();
        set.configure(
            &id,
            Default::default(),
            true,
            &view,
            &mut storage,
            &mut logs,
        )
        .unwrap();
    }
    assert_eq!((seen.borrow().starts, seen.borrow().stops), (4, 3));
    assert!(!set.iter().next().unwrap().accepts_activation(activation));
}
