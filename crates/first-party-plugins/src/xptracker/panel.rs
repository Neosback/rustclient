//! Local XP panel projection over the existing tracker; never a second XP owner.
use super::XpTracker;
use rustclient_plugin_api::{
    GameStateKind, PanelBlock, PanelEvent, PanelIcon, PanelValue, PluginPanel,
};
use rustclient_plugin_host::PluginCall;
use std::sync::Arc;

impl XpTracker {
    pub(super) fn publish_panel(&mut self, call: &mut PluginCall<'_>) {
        let client = call.client().unwrap_or_default();
        let key = (client.cycle / 50, client.state, self.ready_after);
        if !self.panel_dirty && self.panel_key == Some(key) {
            return;
        }
        self.panel_dirty = false;
        self.panel_key = Some(key);
        let mut blocks = Vec::new();
        let gained: i64 = self
            .skills
            .iter()
            .flatten()
            .map(|s| s.summary().gained)
            .sum();
        let per_hour: u64 = self
            .skills
            .iter()
            .flatten()
            .map(|s| s.summary().xp_per_hour)
            .sum();
        blocks.push(PanelBlock::Metric {
            label: "Gained:".into(),
            value: gained.to_string(),
        });
        blocks.push(PanelBlock::Metric {
            label: "Per hour:".into(),
            value: per_hour.to_string(),
        });
        if self.recent.is_empty() {
            blocks.push(PanelBlock::Text(
                "You have not gained experience yet.".into(),
            ));
        }
        for skill in &self.recent {
            let Some(tracker) = &self.skills[skill.index()] else {
                continue;
            };
            let summary = tracker.summary();
            blocks.push(PanelBlock::Text(format!("{skill:?}")));
            blocks.push(PanelBlock::Metric {
                label: "Gained:".into(),
                value: summary.gained.to_string(),
            });
            blocks.push(PanelBlock::Metric {
                label: "Per hour:".into(),
                value: summary.xp_per_hour.to_string(),
            });
            if blocks.len() + 4 >= rustclient_plugin_api::panel_limits::BLOCKS {
                break;
            }
        }
        if gained > 0 && client.state == GameStateKind::LoggedIn && self.ready_after == 0 {
            blocks.push(PanelBlock::Button {
                id: "reset-all".into(),
                label: "Reset All".into(),
            });
            blocks.push(PanelBlock::Button {
                id: "reset-rate".into(),
                label: "Reset All/hr".into(),
            });
        }
        if self
            .panel
            .as_ref()
            .is_some_and(|p| p.blocks.as_ref() == blocks.as_slice())
        {
            return;
        }
        let Some(revision) = self
            .panel
            .as_ref()
            .map_or(Some(1), |p| p.revision.checked_add(1))
        else {
            return;
        };
        self.panel = Some(Arc::new(PluginPanel {
            id: "xp".into(),
            revision,
            title: "XP Tracker".into(),
            priority: 2,
            icon: PanelIcon::XpTracker,
            blocks: blocks.into(),
        }));
    }

    pub(super) fn panel_action(&mut self, call: &mut PluginCall<'_>, event: &PanelEvent) {
        let client = call.client().unwrap_or_default();
        if client.state != GameStateKind::LoggedIn
            || self.ready_after != 0
            || event.value != PanelValue::Activate
            || !self
                .panel
                .as_ref()
                .is_some_and(|p| p.id == event.panel && p.revision == event.revision)
        {
            return;
        }
        match event.element.as_str() {
            "reset-all" => {
                for skill in self.skills.iter_mut().flatten() {
                    *skill = super::SkillTracker::new(skill.current_xp());
                }
                self.recent.clear();
            }
            "reset-rate" => {
                for skill in self.skills.iter_mut().flatten() {
                    skill.reset_rate();
                }
            }
            _ => return,
        }
        self.panel_dirty = true;
    }
}
