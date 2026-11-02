//! Source event-bus ordering and lifecycle/capability routing through the public host.
use rustclient_plugin_api::{
    Capability, EventInterest, GameEvent, GameEventKind, OverlayLine, PluginApiVersion,
    PluginEvent, PluginId, PluginManifest,
};
use rustclient_plugin_host::{
    FaultKind, MemoryLogSink, MemoryStorage, PluginCall, PluginFault, PluginInstance, PluginSet,
    StaticView,
};
use std::{cell::RefCell, rc::Rc};

type Calls = Rc<RefCell<Vec<(&'static str, GameEventKind)>>>;
struct Subscriber {
    source: &'static str,
    calls: Calls,
    interests: EventInterest,
    priority: f32,
    fail: bool,
}
impl PluginInstance for Subscriber {
    fn init(&mut self, _: &mut PluginCall<'_>) -> Result<(), PluginFault> {
        Ok(())
    }
    fn on_event(&mut self, _: &mut PluginCall<'_>, _: &[PluginEvent]) -> Result<(), PluginFault> {
        Ok(())
    }
    fn overlay(&mut self, _: &mut PluginCall<'_>) -> Result<Vec<OverlayLine>, PluginFault> {
        Ok(Vec::new())
    }
    fn game_event_interests(&self) -> EventInterest {
        self.interests
    }
    fn game_event_source(&self) -> &str {
        self.source
    }
    fn game_event_priority(&self, kind: GameEventKind) -> f32 {
        if kind == GameEventKind::ClientTick {
            self.priority
        } else {
            0.
        }
    }
    fn on_game_events(
        &mut self,
        _: &mut PluginCall<'_>,
        events: &[GameEvent],
    ) -> Result<(), PluginFault> {
        assert_eq!(
            events.len(),
            1,
            "callbacks observe committed event order, not per-plugin batches"
        );
        self.calls
            .borrow_mut()
            .push((self.source, events[0].kind()));
        if self.fail {
            Err(PluginFault::new(FaultKind::Event, "handler failed"))
        } else {
            Ok(())
        }
    }
}
fn register(
    set: &mut PluginSet,
    source: &'static str,
    calls: &Calls,
    kinds: EventInterest,
    priority: f32,
    grants: &[Capability],
    fail: bool,
) -> Result<(), PluginFault> {
    set.register(
        PluginManifest {
            id: PluginId::parse(&format!("test.{}", source.to_lowercase())).unwrap(),
            name: source.into(),
            version: "1".into(),
            plugin_api: PluginApiVersion::current(),
            capabilities: grants.iter().copied().collect(),
            entry: String::new(),
        },
        Box::new(Subscriber {
            source,
            calls: calls.clone(),
            interests: kinds,
            priority,
            fail,
        }),
    )
}
#[test]
fn events_are_outermost_and_priority_then_source_class_order_each_delivery() {
    let calls = Calls::default();
    let mut set = PluginSet::new();
    let kinds = EventInterest::one(GameEventKind::ClientTick).with(GameEventKind::GameTick);
    register(&mut set, "Z", &calls, kinds, 2., &[], false).unwrap();
    register(&mut set, "B", &calls, kinds, 1., &[], false).unwrap();
    register(&mut set, "A", &calls, kinds, 1., &[], false).unwrap();
    let mut storage = MemoryStorage::default();
    let mut logs = MemoryLogSink::default();
    set.init(&StaticView::default(), &mut storage, &mut logs);
    set.dispatch_game(
        &[GameEvent::ClientTick, GameEvent::GameTick(1)],
        &StaticView::default(),
        &mut storage,
        &mut logs,
    );
    assert_eq!(
        *calls.borrow(),
        vec![
            ("Z", GameEventKind::ClientTick),
            ("A", GameEventKind::ClientTick),
            ("B", GameEventKind::ClientTick),
            ("A", GameEventKind::GameTick),
            ("B", GameEventKind::GameTick),
            ("Z", GameEventKind::GameTick)
        ]
    );
}
#[test]
fn denied_classes_do_not_create_interest_or_receive_callbacks() {
    let calls = Calls::default();
    let mut set = PluginSet::new();
    register(
        &mut set,
        "A",
        &calls,
        EventInterest::one(GameEventKind::StatChanged),
        0.,
        &[],
        false,
    )
    .unwrap();
    let mut storage = MemoryStorage::default();
    let mut logs = MemoryLogSink::default();
    set.init(&StaticView::default(), &mut storage, &mut logs);
    assert!(!set.has_game_subscribers());
    assert!(set.game_event_interest().is_none());
    assert!(set.game_event_kinds().is_empty());
    set.dispatch_game(
        &[GameEvent::ClientTick],
        &StaticView::default(),
        &mut storage,
        &mut logs,
    );
    assert!(calls.borrow().is_empty());
}
#[test]
fn faults_during_a_batch_revoke_remaining_callbacks_without_affecting_other_subscribers() {
    let calls = Calls::default();
    let mut set = PluginSet::with_fault_limit(2);
    let kinds = EventInterest::one(GameEventKind::ClientTick);
    register(&mut set, "A", &calls, kinds, 0., &[], true).unwrap();
    register(&mut set, "B", &calls, kinds, 0., &[], false).unwrap();
    let mut storage = MemoryStorage::default();
    let mut logs = MemoryLogSink::default();
    set.init(&StaticView::default(), &mut storage, &mut logs);
    set.dispatch_game(
        &[
            GameEvent::ClientTick,
            GameEvent::ClientTick,
            GameEvent::ClientTick,
        ],
        &StaticView::default(),
        &mut storage,
        &mut logs,
    );
    assert_eq!(
        calls
            .borrow()
            .iter()
            .filter(|(name, _)| *name == "A")
            .count(),
        2
    );
    assert_eq!(
        calls
            .borrow()
            .iter()
            .filter(|(name, _)| *name == "B")
            .count(),
        3
    );
}
#[test]
fn nonfinite_source_priorities_are_rejected_before_activation() {
    let calls = Calls::default();
    let mut set = PluginSet::new();
    assert!(
        register(
            &mut set,
            "A",
            &calls,
            EventInterest::ALL,
            f32::NAN,
            &[],
            false
        )
        .is_err()
    );
}

#[test]
fn unloading_rebuilds_routes_before_a_new_registration_reuses_the_vector_slot() {
    let calls = Calls::default();
    let mut set = PluginSet::new();
    let kinds = EventInterest::one(GameEventKind::ClientTick);
    register(&mut set, "A", &calls, kinds, 0., &[], false).unwrap();
    register(&mut set, "B", &calls, kinds, 0., &[], false).unwrap();
    let mut storage = MemoryStorage::default();
    let mut logs = MemoryLogSink::default();
    set.init(&StaticView::default(), &mut storage, &mut logs);
    set.dispatch_game(
        &[GameEvent::ClientTick],
        &StaticView::default(),
        &mut storage,
        &mut logs,
    );
    let a = PluginId::parse("test.a").unwrap();
    set.configure(
        &a,
        rustclient_plugin_host::PluginConfig::default(),
        false,
        &StaticView::default(),
        &mut storage,
        &mut logs,
    )
    .unwrap();
    set.unregister(&a).unwrap();
    register(&mut set, "C", &calls, kinds, 1., &[], false).unwrap();
    set.init(&StaticView::default(), &mut storage, &mut logs);
    calls.borrow_mut().clear();
    set.dispatch_game(
        &[GameEvent::ClientTick],
        &StaticView::default(),
        &mut storage,
        &mut logs,
    );
    assert_eq!(
        *calls.borrow(),
        vec![
            ("C", GameEventKind::ClientTick),
            ("B", GameEventKind::ClientTick)
        ]
    );
}
