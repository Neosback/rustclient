//! Panel publication and activation-scoped actions belong to the host, not the UI toolkit.
use crate::{ActivationId, HostView, LogSink, PluginSet, PluginStorage, lifecycle::Phase};
use rustclient_plugin_api::{
    Capability, PanelBlock, PanelEvent, PanelValue, PluginId, PluginPanel, panel_limits,
};
use std::{collections::BTreeSet, sync::Arc};

/// Host-owned publication identity. Removal or changed content always revokes old actions.
#[derive(Debug, Default, Clone, Copy, PartialEq, Eq, Hash)]
pub struct PanelLease(u64);

/// A validated panel and the only activation allowed to receive its actions.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct HostedPanel {
    pub plugin: PluginId,
    pub activation: ActivationId,
    pub lease: PanelLease,
    pub document: Arc<PluginPanel>,
}

/// UI input is untrusted, including when it originates from a previously rendered frame.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct PanelAction {
    pub plugin: PluginId,
    pub activation: ActivationId,
    pub lease: PanelLease,
    pub event: PanelEvent,
}

fn valid_id(value: &str) -> bool {
    !value.is_empty()
        && value.len() <= panel_limits::ID_BYTES
        && value
            .bytes()
            .all(|c| c.is_ascii_alphanumeric() || b"._-".contains(&c))
}

pub(crate) fn valid_panel(panel: &PluginPanel) -> bool {
    if !valid_id(&panel.id)
        || panel.revision == 0
        || panel.title.is_empty()
        || panel.title.len() > 64
        || panel.blocks.len() > panel_limits::BLOCKS
    {
        return false;
    }
    let mut bytes = panel.title.len();
    let mut ids = BTreeSet::new();
    for block in panel.blocks.iter() {
        let (id, length) = match block {
            PanelBlock::Text(text) => (None, text.len()),
            PanelBlock::Metric { label, value } => (None, label.len() + value.len()),
            PanelBlock::Button { id, label } => (Some(id), label.len()),
            PanelBlock::Editor {
                id,
                text,
                max_bytes,
            } => {
                if *max_bytes as usize > panel_limits::TEXT_BYTES
                    || text.len() > *max_bytes as usize
                {
                    return false;
                }
                (Some(id), text.len())
            }
        };
        if let Some(id) = id
            && (!valid_id(id) || !ids.insert(id))
        {
            return false;
        }
        bytes = bytes.saturating_add(length);
        if bytes > panel_limits::TEXT_BYTES {
            return false;
        }
    }
    true
}

pub(crate) fn publish_panels(
    plugin: &mut crate::LoadedPlugin,
    documents: &[Arc<PluginPanel>],
) -> Result<(), crate::PluginFault> {
    let mut next = Vec::with_capacity(documents.len());
    for document in documents {
        let lease = if let Some(previous) = plugin
            .lifecycle
            .panels
            .iter()
            .find(|p| p.document == *document)
        {
            previous.lease
        } else {
            plugin.lifecycle.panel_sequence = plugin
                .lifecycle
                .panel_sequence
                .checked_add(1)
                .ok_or_else(|| {
                    crate::PluginFault::new(
                        crate::FaultKind::Contract,
                        "panel publication identity exhausted",
                    )
                })?;
            PanelLease(plugin.lifecycle.panel_sequence)
        };
        next.push(HostedPanel {
            plugin: plugin.manifest.id.clone(),
            activation: plugin.activation(),
            lease,
            document: document.clone(),
        });
    }
    plugin.lifecycle.panels = next;
    Ok(())
}

impl PluginSet {
    /// Dispatch one validated action. `false` means stale, unknown or unauthorized input.
    /// Callback errors use the ordinary phase-specific fault policy.
    pub fn dispatch_panel_action(
        &mut self,
        action: &PanelAction,
        view: &dyn HostView,
        storage: &mut dyn PluginStorage,
        logs: &mut dyn LogSink,
    ) -> bool {
        let Some(plugin) = self
            .plugins
            .iter_mut()
            .find(|p| p.manifest.id == action.plugin)
        else {
            return false;
        };
        if !plugin.accepts_activation(action.activation)
            || !plugin.manifest.capabilities.contains(Capability::UiPanel)
        {
            return false;
        }
        if !plugin
            .lifecycle
            .panels
            .iter()
            .any(|p| p.lease == action.lease && p.document.id == action.event.panel)
        {
            return false;
        }
        let Some((activation, output)) = &plugin.lifecycle.output else {
            return false;
        };
        if *activation != action.activation {
            return false;
        }
        let accepted = output.panels.iter().any(|panel| {
            panel.id == action.event.panel
                && panel.revision == action.event.revision
                && panel
                    .blocks
                    .iter()
                    .any(|block| match (block, &action.event.value) {
                        (PanelBlock::Button { id, .. }, PanelValue::Activate) => {
                            id == &action.event.element
                        }
                        (PanelBlock::Editor { id, max_bytes, .. }, PanelValue::Text(text)) => {
                            id == &action.event.element && text.len() <= *max_bytes as usize
                        }
                        _ => false,
                    })
        });
        if !accepted {
            return false;
        }
        Self::invoke(
            plugin,
            Phase::Ui,
            view,
            storage,
            logs,
            self.fault_limit,
            |instance, call| instance.on_panel_event(call, &action.event),
        )
        .is_some()
    }
}
