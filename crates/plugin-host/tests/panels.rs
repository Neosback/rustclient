//! Retained documents must never turn an old UI callback into output from a new activation.
use rustclient_plugin_api::{
    Capability, OverlayLine, PanelBlock, PanelEvent, PanelIcon, PanelValue, PluginApiVersion,
    PluginEvent, PluginId, PluginManifest, PluginOutput, PluginPanel,
};
use rustclient_plugin_host::{
    MemoryLogSink, MemoryStorage, PanelAction, PluginCall, PluginFault, PluginInstance, PluginSet,
    StaticView,
};
use std::{cell::RefCell, rc::Rc, sync::Arc};

type Calls = Rc<RefCell<Vec<PanelEvent>>>;
struct Plugin {
    document: Rc<RefCell<Option<Arc<PluginPanel>>>>,
    calls: Calls,
}
impl PluginInstance for Plugin {
    fn init(&mut self, _: &mut PluginCall<'_>) -> Result<(), PluginFault> {
        Ok(())
    }
    fn on_event(&mut self, _: &mut PluginCall<'_>, _: &[PluginEvent]) -> Result<(), PluginFault> {
        Ok(())
    }
    fn overlay(&mut self, _: &mut PluginCall<'_>) -> Result<Vec<OverlayLine>, PluginFault> {
        Ok(Vec::new())
    }
    fn output(&mut self, _: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
        Ok(PluginOutput {
            panels: self.document.borrow().iter().cloned().collect(),
            ..PluginOutput::default()
        })
    }
    fn on_panel_event(
        &mut self,
        _: &mut PluginCall<'_>,
        event: &PanelEvent,
    ) -> Result<(), PluginFault> {
        self.calls.borrow_mut().push(event.clone());
        Ok(())
    }
}
fn document() -> PluginPanel {
    PluginPanel {
        id: "notes".into(),
        revision: 1,
        title: "Notes".into(),
        priority: 7,
        icon: PanelIcon::Notes,
        blocks: Arc::from([PanelBlock::Editor {
            id: "text".into(),
            text: String::new(),
            max_bytes: 16,
        }]),
    }
}
struct Harness {
    set: PluginSet,
    storage: MemoryStorage,
    logs: MemoryLogSink,
    id: PluginId,
    calls: Calls,
    document: Rc<RefCell<Option<Arc<PluginPanel>>>>,
}
impl Harness {
    fn new(panel: PluginPanel, grants: &[Capability]) -> Self {
        let id = PluginId::parse("test.notes").unwrap();
        let mut set = PluginSet::new();
        let calls = Calls::default();
        let document = Rc::new(RefCell::new(Some(Arc::new(panel))));
        set.register(
            PluginManifest {
                id: id.clone(),
                name: "Notes".into(),
                version: "1".into(),
                plugin_api: PluginApiVersion::current(),
                capabilities: grants.iter().copied().collect(),
                entry: String::new(),
            },
            Box::new(Plugin {
                document: document.clone(),
                calls: calls.clone(),
            }),
        )
        .unwrap();
        let mut harness = Self {
            set,
            storage: MemoryStorage::default(),
            logs: MemoryLogSink::default(),
            id,
            calls,
            document,
        };
        harness.set.init(
            &StaticView::default(),
            &mut harness.storage,
            &mut harness.logs,
        );
        harness
    }
    fn action(&mut self) -> Option<PanelAction> {
        let output =
            self.set
                .collect_output(&StaticView::default(), &mut self.storage, &mut self.logs);
        let panel = output.panels.first()?;
        Some(PanelAction {
            plugin: panel.plugin.clone(),
            activation: panel.activation,
            lease: panel.lease,
            event: PanelEvent {
                panel: panel.document.id.clone(),
                revision: panel.document.revision,
                element: "text".into(),
                value: PanelValue::Text("saved".into()),
            },
        })
    }
    fn dispatch(&mut self, action: &PanelAction) -> bool {
        self.set.dispatch_panel_action(
            action,
            &StaticView::default(),
            &mut self.storage,
            &mut self.logs,
        )
    }
    fn configure(&mut self, enabled: bool) {
        self.set
            .configure(
                &self.id,
                rustclient_plugin_host::PluginConfig::default(),
                enabled,
                &StaticView::default(),
                &mut self.storage,
                &mut self.logs,
            )
            .unwrap();
    }
}
#[test]
fn panel_grants_are_required_for_publication_and_input() {
    let mut h = Harness::new(document(), &[]);
    assert!(h.action().is_none());
    assert!(h.set.retained_output().panels.is_empty());
}
#[test]
fn only_current_declared_elements_accept_the_declared_value_kind_and_byte_limit() {
    let mut h = Harness::new(document(), &[Capability::UiPanel]);
    let action = h.action().unwrap();
    assert!(h.dispatch(&action));
    let mut bad = action.clone();
    bad.event.revision += 1;
    assert!(!h.dispatch(&bad));
    bad = action.clone();
    bad.event.element = "unknown".into();
    assert!(!h.dispatch(&bad));
    bad = action.clone();
    bad.event.value = PanelValue::Activate;
    assert!(!h.dispatch(&bad));
    bad = action;
    bad.event.value = PanelValue::Text("é".repeat(9));
    assert!(!h.dispatch(&bad));
    assert_eq!(h.calls.borrow().len(), 1);
}
#[test]
fn shutdown_revokes_panels_and_prior_activation_actions_after_restart() {
    let mut h = Harness::new(document(), &[Capability::UiPanel]);
    let action = h.action().unwrap();
    h.configure(false);
    assert!(h.set.retained_output().panels.is_empty());
    assert!(!h.dispatch(&action));
    h.configure(true);
    let fresh = h.action().unwrap();
    assert_ne!(fresh.activation, action.activation);
    assert!(!h.dispatch(&action));
    assert!(h.dispatch(&fresh));
}
#[test]
fn invalid_documents_are_rejected_whole_including_duplicate_element_ids() {
    let mut panel = document();
    panel.blocks = Arc::from([panel.blocks[0].clone(), panel.blocks[0].clone()]);
    assert!(
        Harness::new(panel, &[Capability::UiPanel])
            .action()
            .is_none()
    );
    let mut panel = document();
    panel.revision = 0;
    assert!(
        Harness::new(panel, &[Capability::UiPanel])
            .action()
            .is_none()
    );
    let mut panel = document();
    panel.id = "../other".into();
    assert!(
        Harness::new(panel, &[Capability::UiPanel])
            .action()
            .is_none()
    );
    let mut panel = document();
    panel.blocks = Arc::from([PanelBlock::Text("x".repeat(65_537))]);
    assert!(
        Harness::new(panel, &[Capability::UiPanel])
            .action()
            .is_none()
    );
}

#[test]
fn unmount_and_republish_in_one_activation_revoke_the_old_publication_even_with_the_same_document()
{
    let mut h = Harness::new(document(), &[Capability::UiPanel]);
    let old = h.action().unwrap();
    let unchanged = h.action().unwrap();
    assert_eq!(old.lease, unchanged.lease);
    let content = h.document.borrow_mut().take();
    assert!(h.action().is_none());
    *h.document.borrow_mut() = content;
    let fresh = h.action().unwrap();
    assert_eq!(old.activation, fresh.activation);
    assert_ne!(old.lease, fresh.lease);
    assert!(!h.dispatch(&old));
    assert!(h.dispatch(&fresh));
}
