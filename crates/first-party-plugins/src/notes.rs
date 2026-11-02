//! Local Notes panel from `RuneLite` (BSD-2, NOTICE). Profile switching remains a host dependency.
use crate::NativePlugin;
use rustclient_plugin_api::{
    PanelBlock, PanelEvent, PanelIcon, PanelValue, PluginOutput, PluginPanel, panel_limits,
};
use rustclient_plugin_host::{FaultKind, PluginCall, PluginFault};
use std::sync::Arc;

#[derive(Default)]
pub(crate) struct Notes {
    panel: Option<Arc<PluginPanel>>,
    values: std::collections::BTreeMap<String, String>,
}
impl Notes {
    fn publish(&mut self, text: String, revision: u64) {
        self.panel = Some(Arc::new(PluginPanel {
            id: "notes".into(),
            revision,
            title: "Notes".into(),
            priority: 7,
            icon: PanelIcon::Notes,
            blocks: Arc::from([PanelBlock::Editor {
                id: "notesData".into(),
                text,
                max_bytes: u32::try_from(panel_limits::TEXT_BYTES - "Notes".len()).unwrap_or(0),
            }]),
        }));
    }
}
impl NativePlugin for Notes {
    fn subscribes_game_events(&self) -> bool {
        false
    }
    fn init(&mut self, call: &mut PluginCall<'_>) -> Result<(), PluginFault> {
        self.values = call
            .load_storage()
            .map_err(|e| PluginFault::new(FaultKind::Contract, e.to_string()))?;
        let text = self.values.get("notesData").cloned().unwrap_or_default();
        if text.len() > panel_limits::TEXT_BYTES - "Notes".len() {
            return Err(PluginFault::new(
                FaultKind::Contract,
                "Saved Notes exceeds the panel text budget; saved data was retained",
            ));
        }
        self.publish(text, 1);
        Ok(())
    }
    fn shutdown(&mut self, _: &mut PluginCall<'_>) -> Result<(), PluginFault> {
        self.panel = None;
        self.values.clear();
        Ok(())
    }
    fn panel_event(
        &mut self,
        call: &mut PluginCall<'_>,
        event: &PanelEvent,
    ) -> Result<(), PluginFault> {
        let Some(panel) = &self.panel else {
            return Ok(());
        };
        if event.panel != panel.id
            || event.revision != panel.revision
            || event.element != "notesData"
        {
            return Ok(());
        }
        if let PanelValue::Text(text) = &event.value {
            if self.values.get("notesData") == Some(text) {
                return Ok(());
            }
            let revision = panel
                .revision
                .checked_add(1)
                .ok_or_else(|| PluginFault::new(FaultKind::Contract, "Notes revision exhausted"))?;
            self.values.insert("notesData".into(), text.clone());
            call.save_storage(&self.values)
                .map_err(|e| PluginFault::new(FaultKind::Contract, e.to_string()))?;
            self.publish(text.clone(), revision);
        }
        Ok(())
    }
    fn output(&mut self, _: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
        Ok(PluginOutput {
            panels: self.panel.iter().cloned().collect(),
            ..PluginOutput::default()
        })
    }
}
