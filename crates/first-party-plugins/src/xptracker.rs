//! Local XP Tracker port from `RuneLite` (BSD-2, NOTICE). Full panel/network port remains separate.
mod state;
use crate::NativePlugin;
use rustclient_plugin_api::{
    GameEvent, GameStateKind, HorizontalAlign, LayeredOverlay, OverlayCommand, OverlayLayer,
    PluginOutput, Skill,
};
use rustclient_plugin_host::{PluginCall, PluginFault};
pub use state::{PausePolicy, SkillTracker, XpSummary};

#[derive(Default)]
pub(crate) struct XpTracker {
    skills: Vec<Option<SkillTracker>>,
    recent: Vec<Skill>,
    ready_after: u8,
    last_cycle: Option<u64>,
    player: String,
    was_logged_in: bool,
}
impl NativePlugin for XpTracker {
    fn init(&mut self, _call: &mut PluginCall<'_>) -> Result<(), PluginFault> {
        *self = Self::default();
        self.skills = vec![None; 23];
        self.ready_after = 2;
        Ok(())
    }
    fn events(
        &mut self,
        call: &mut PluginCall<'_>,
        events: &[GameEvent],
    ) -> Result<(), PluginFault> {
        let client = call.client().unwrap_or_default();
        let logged_in = client.state == GameStateKind::LoggedIn
            || (client.state == GameStateKind::Loading && self.was_logged_in);
        let player = call
            .local_player()
            .ok()
            .flatten()
            .map_or("", |p| p.name.as_str());
        if !player.is_empty() && player != self.player {
            player.clone_into(&mut self.player);
            self.skills.fill(None);
            self.recent.clear();
            self.ready_after = 2;
            self.last_cycle = None;
        }
        if logged_in && !self.was_logged_in {
            self.ready_after = 2;
            self.last_cycle = None;
        }
        if logged_in {
            self.was_logged_in = true;
        } else if client.state != GameStateKind::Loading {
            self.was_logged_in = false;
        }
        let elapsed = self
            .last_cycle
            .map_or(0, |old| client.cycle.saturating_sub(old).saturating_mul(20));
        self.last_cycle = Some(client.cycle);
        let policy = PausePolicy {
            logged_out: !logged_in,
            pause_after_ms: u64::try_from(call.config.integer("pauseSkillAfter")).unwrap_or(0)
                * 60_000,
            reset_rate_after_ms: u64::try_from(call.config.integer("resetSkillRateAfter"))
                .unwrap_or(0)
                * 60_000,
        };
        for skill in self.skills.iter_mut().flatten() {
            skill.advance(elapsed, policy);
        }
        for event in events {
            if matches!(event, GameEvent::GameTick(_)) && logged_in && self.ready_after > 0 {
                self.ready_after -= 1;
                if self.ready_after == 0 {
                    for (index, level) in call
                        .skills()
                        .unwrap_or_default()
                        .iter()
                        .take(23)
                        .enumerate()
                    {
                        let Ok(xp) = u32::try_from(level.experience) else {
                            continue;
                        };
                        // Login sync and offline gains are baseline updates, never training actions.
                        let preserve = self.skills[index]
                            .as_ref()
                            .is_some_and(|s| s.current_xp() == xp);
                        if !preserve {
                            self.skills[index] = Some(SkillTracker::new(xp));
                        }
                    }
                }
            }
            if let GameEvent::StatChanged { skill, level } = event {
                if !logged_in || self.ready_after > 0 {
                    continue;
                }
                if call.config.boolean("hideMaxed") && level.real >= 99 {
                    self.skills[skill.index()] = None;
                    self.recent.retain(|s| s != skill);
                    continue;
                }
                let Ok(xp) = u32::try_from(level.experience) else {
                    continue;
                };
                let tracker =
                    self.skills[skill.index()].get_or_insert_with(|| SkillTracker::new(xp));
                if tracker.update(xp) {
                    if call.config.boolean("prioritizeRecentXpSkills") {
                        self.recent.retain(|s| s != skill);
                        self.recent.insert(0, *skill);
                    } else if !self.recent.contains(skill) {
                        self.recent.push(*skill);
                    }
                }
            }
        }
        Ok(())
    }
    fn output(&mut self, call: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
        let client = call.client().unwrap_or_default();
        let mut output = PluginOutput::default();
        if client.state != GameStateKind::LoggedIn || self.ready_after > 0 {
            return Ok(output);
        }
        let levels = call.skills().unwrap_or_default();
        let hide_maxed = call.config.boolean("hideMaxed");
        for (row, skill) in self
            .recent
            .iter()
            .filter(|skill| {
                !hide_maxed
                    || levels
                        .get(skill.index())
                        .is_none_or(|level| level.real < 99)
            })
            .take(6)
            .enumerate()
        {
            let Some(tracker) = self.skills[skill.index()].as_ref() else {
                continue;
            };
            let summary = tracker.summary();
            if summary.gained <= 0 {
                continue;
            }
            let y = client
                .viewport
                .y
                .saturating_add(24)
                .saturating_add(i32::try_from(row).unwrap_or(0) * 30);
            let x = client.viewport.x.saturating_add(8);
            let text = format!(
                "{skill:?}: +{} XP | {}/hr | {} actions",
                summary.gained, summary.xp_per_hour, summary.actions
            );
            output.overlay.push(LayeredOverlay {
                layer: OverlayLayer::AboveScene,
                command: OverlayCommand::Text {
                    text,
                    x,
                    y,
                    argb: 0xffff_ffff,
                    align: HorizontalAlign::Left,
                },
            });
        }
        Ok(output)
    }
}
#[cfg(test)]
mod tests;
