use super::*;
use rustclient_plugin_api::{PanelBlock, PanelEvent, PanelValue};
use rustclient_plugin_host::PanelAction;
use std::sync::Arc;

#[test]
fn notes_reuses_unchanged_content_saves_focus_commit_and_reloads_after_activation() {
    let mut h = Harness::new();
    h.enable("notes", &[]);
    let first = h.output(&GameSnapshot::default()).panels.remove(0);
    let unchanged = h.output(&GameSnapshot::default()).panels.remove(0);
    assert!(Arc::ptr_eq(&first.document, &unchanged.document));
    assert_eq!(first.document.priority, 7);
    h.set.dispatch(
        &[PluginEvent::Stopped],
        &StaticView::default(),
        &mut h.storage,
        &mut h.logs,
    );
    let offline = h.set.retained_output();
    assert_eq!(offline.panels[0].lease, first.lease);
    assert_eq!(offline.panels[0].activation, first.activation);

    let action = PanelAction {
        plugin: first.plugin.clone(),
        activation: first.activation,
        lease: first.lease,
        event: PanelEvent {
            panel: "notes".into(),
            revision: first.document.revision,
            element: "notesData".into(),
            value: PanelValue::Text("Door test ✓\nsecond line".into()),
        },
    };
    assert!(h.set.dispatch_panel_action(
        &action,
        &StaticView::default(),
        &mut h.storage,
        &mut h.logs
    ));
    let updated = h.output(&GameSnapshot::default()).panels.remove(0);
    assert!(updated.document.revision > first.document.revision);
    assert!(
        matches!(&updated.document.blocks[0], PanelBlock::Editor { text, .. } if text == "Door test ✓\nsecond line")
    );
    assert!(!h.set.dispatch_panel_action(
        &action,
        &StaticView::default(),
        &mut h.storage,
        &mut h.logs
    ));
    h.set
        .configure(
            &first.plugin,
            PluginConfig::default(),
            false,
            &StaticView::default(),
            &mut h.storage,
            &mut h.logs,
        )
        .unwrap();
    assert!(h.set.retained_output().panels.is_empty());
    h.enable("notes", &[]);
    let restored = h.output(&GameSnapshot::default()).panels.remove(0);
    assert_ne!(restored.activation, first.activation);
    assert_eq!(restored.document.blocks, updated.document.blocks);
    assert!(h.logs.records.is_empty());
}

#[test]
fn notes_hidden_storage_is_not_exposed_as_an_ordinary_setting() {
    let schemas = first_party_schemas().unwrap();
    let notes = schemas
        .iter()
        .find(|s| s.plugin.as_str() == "dev.rustclient.notes")
        .unwrap();
    assert_eq!(notes.settings.len(), 1);
    assert_eq!(notes.settings[0].key, "enabled");
}
