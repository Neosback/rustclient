//! Source-ordered gameplay dispatch, with borrowed payloads and registration-scoped routes.
use crate::{
    FaultKind, HostView, LoadedPlugin, LogSink, PluginFault, PluginInstance, PluginSet,
    PluginStorage,
};
use rustclient_plugin_api::{EventInterest, GameEvent, GameEventKind};

pub(crate) struct Subscription {
    pub interest: EventInterest,
    source: Box<str>,
    priorities: [f32; GameEventKind::ALL.len()],
}
impl Subscription {
    pub fn new(
        instance: &dyn PluginInstance,
        grants: &rustclient_plugin_api::CapabilitySet,
    ) -> Result<Self, PluginFault> {
        let source = instance.game_event_source();
        if source.is_empty() || source.len() > 1024 {
            return Err(PluginFault::new(
                FaultKind::Contract,
                "invalid subscriber source class",
            ));
        }
        let priorities = GameEventKind::ALL.map(|kind| instance.game_event_priority(kind));
        if priorities.iter().any(|priority| !priority.is_finite()) {
            return Err(PluginFault::new(
                FaultKind::Contract,
                "subscriber priority must be finite",
            ));
        }
        Ok(Self {
            interest: if instance.subscribes_game_events() {
                instance.game_event_interests().permitted_by(grants)
            } else {
                EventInterest::NONE
            },
            source: source.into(),
            priorities,
        })
    }
}

#[derive(Default)]
pub(crate) struct Routes {
    key: Option<(u64, usize)>,
    pub(crate) classes: [Vec<usize>; GameEventKind::ALL.len()],
}
impl Routes {
    fn prepare(&mut self, registration: u64, plugins: &[LoadedPlugin]) {
        let key = (registration, plugins.len());
        if self.key == Some(key) {
            return;
        }
        for kind in GameEventKind::ALL {
            let route = &mut self.classes[kind.index()];
            route.clear();
            route.extend(plugins.iter().enumerate().filter_map(|(index, plugin)| {
                plugin.subscription.interest.contains(kind).then_some(index)
            }));
            route.sort_by(|&left, &right| {
                let a = &plugins[left].subscription;
                let b = &plugins[right].subscription;
                b.priorities[kind.index()]
                    .total_cmp(&a.priorities[kind.index()])
                    .then_with(|| a.source.cmp(&b.source))
                    .then_with(|| left.cmp(&right))
            });
        }
        self.key = Some(key);
    }
}
impl PluginSet {
    /// Union of static interests of currently running subscribers. Grants remain separate.
    #[must_use]
    pub fn game_event_kinds(&self) -> EventInterest {
        self.plugins
            .iter()
            .filter(|plugin| plugin.running() && plugin.wants_game_events())
            .fold(EventInterest::NONE, |all, plugin| {
                all.union(plugin.subscription.interest)
            })
    }

    /// Deliver events in committed order, source priority/class order within each event.
    /// No event payload is cloned for dispatch; faults revoke subsequent scheduling.
    pub fn dispatch_game(
        &mut self,
        events: &[GameEvent],
        view: &dyn HostView,
        storage: &mut dyn PluginStorage,
        logs: &mut dyn LogSink,
    ) {
        // Preserve register-then-dispatch: initialize stopped instances before delivery.
        self.init(view, storage, logs);
        self.event_routes
            .prepare(self.registration_sequence, &self.plugins);
        for event in events {
            for &index in &self.event_routes.classes[event.kind().index()] {
                let plugin = &mut self.plugins[index];
                if !plugin.running()
                    || !plugin.wants_game_events()
                    || event
                        .required_capability()
                        .is_some_and(|grant| !plugin.manifest.capabilities.contains(grant))
                {
                    continue;
                }
                let _ = Self::invoke(
                    plugin,
                    crate::lifecycle::Phase::Game,
                    view,
                    storage,
                    logs,
                    self.fault_limit,
                    |instance, call| instance.on_game_events(call, std::slice::from_ref(event)),
                );
            }
        }
    }
}
