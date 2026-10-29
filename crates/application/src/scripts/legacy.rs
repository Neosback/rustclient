//! Disposable IF1 conditions and text; all live facts remain in `GameState`.
#[cfg(test)]
mod button_tests;
use super::ClientScripts;
use rustclient_cache::{
    osrs240::cs1::{self, Query},
    widget::LegacyPresentation,
};
use rustclient_game_state::GameState;
use rustclient_game_ui::{UiFrame, widget_visuals::WidgetVisualAssets};
use rustclient_model::{WidgetAddress, WidgetBody, WidgetId};
use rustclient_platform::ReadOnlyBlobs;
use std::collections::BTreeMap;
/// Current widget layout plus bounded presentation diagnostics; never a semantic state store.
pub struct WidgetProjection {
    pub tree: rustclient_game_ui::widgets::WidgetTree,
    pub diagnostics: Vec<String>,
}
impl<S: ReadOnlyBlobs> ClientScripts<S> {
    pub(super) fn perform_legacy_button(
        &mut self,
        state: &mut GameState,
        action: rustclient_model::WidgetAction,
    ) -> Result<(Vec<rustclient_protocol::ClientIntent>, Vec<String>), String> {
        let widget = rustclient_game_ui::widgets::resolve_widget(
            state.interfaces(),
            &self.interfaces,
            action.address,
        )
        .map_err(|e| e.to_string())?;
        if action.operation == rustclient_model::WidgetOperation::BUTTON_CLICK
            && widget.content != rustclient_model::WidgetContent::None
        {
            return Err(format!(
                "widget {:?}: legacy click content handler {:?} is unsupported",
                action.address, widget.content
            ));
        }
        let legacy = self
            .interfaces
            .get(&action.address.widget.interface)
            .and_then(|group| group.legacy(action.address.widget))
            .ok_or("missing authored button metadata")?;
        // classQP queues IF_BUTTON before applying its direct local varp assignment.
        let intents = vec![rustclient_protocol::ClientIntent::WidgetAction(action)];
        let assignment = self
            .profile
            .legacy_button_variable(legacy, |id| state.scenery().variable(id));
        let mut errors = Vec::new();
        match assignment {
            Ok(Some((id, value))) => {
                let mut host = super::ScriptHost {
                    resources: self,
                    state,
                };
                if let Err(error) = host.set_client_variable(id, value) {
                    errors.push(format!("widget {:?}: {error}", action.address));
                } else {
                    errors.push(format!(
                        "varp {id}: option-type side effects have not been evaluated"
                    ));
                }
            }
            Ok(None) => {}
            Err(error) => errors.push(format!("widget {:?}: {error}", action.address)),
        }
        Ok((intents, errors))
    }
    /// Paint current widget state with revision-owned CS1 conditions and substitution.
    /// # Errors
    /// Reports layout/presentation bounds; expression failures retain stock -1 and diagnostics.
    pub fn project_widgets(
        &self,
        state: &GameState,
        assets: &WidgetVisualAssets,
        frame: &mut UiFrame,
        dragged: Option<rustclient_game_ui::pointer::DraggedWidget>,
    ) -> Result<WidgetProjection, String> {
        let mut diagnostics = Vec::new();
        let mut budget = 1_000_000_usize;
        let tree = rustclient_game_ui::widget_visuals::project_widgets_with_presentation(
            state,
            &self.interfaces,
            assets,
            frame,
            dragged,
            |address, body| {
                if address.child.is_none()
                    && let Some(legacy) = self
                        .interfaces
                        .get(&address.widget.interface)
                        .and_then(|group| group.legacy(address.widget))
                {
                    self.present_legacy(
                        state,
                        (address.widget, body),
                        legacy,
                        &mut diagnostics,
                        &mut budget,
                    )?;
                }
                Ok(())
            },
        )?;
        Ok(WidgetProjection { tree, diagnostics })
    }
    fn present_legacy(
        &self,
        state: &GameState,
        widget: (WidgetId, &mut WidgetBody),
        legacy: &LegacyPresentation,
        diagnostics: &mut Vec<String>,
        budget: &mut usize,
    ) -> Result<(), String> {
        let (id, body) = widget;
        let mut values = BTreeMap::new();
        let mut evaluate = |index| {
            *values.entry(index).or_insert_with(|| {
                let words = legacy.expressions.get(index).map_or(0, Vec::len);
                if words > *budget {
                    if diagnostics.len() < 256 {
                        diagnostics.push(format!(
                            "widget {id:?}: CS1 projection word budget exhausted"
                        ));
                    }
                    return -1;
                }
                *budget -= words;
                let result = self
                    .profile
                    .evaluate_cs1(&legacy.expressions, index, |query| {
                        self.cs1_query(state, query)
                    });
                if let Some(error) = result.error
                    && diagnostics.len() < 256
                {
                    diagnostics.push(format!("widget {id:?} expression {index}: {error}"));
                }
                result.value
            })
        };
        if cs1::matches(&legacy.comparisons, &mut evaluate)
            && let Some(alternate) = &legacy.alternate
        {
            select_body(body, alternate);
            if let Some(sprite) = state
                .interfaces()
                .runtime()
                .get(&WidgetAddress::component(id))
                .and_then(|runtime| runtime.secondary_sprite)
                && let WidgetBody::Sprite { sprite: target, .. } = body
            {
                *target = Some(sprite);
            }
        }
        if let WidgetBody::Text { text, .. } = body
            && text.contains('%')
        {
            *text = cs1::substitute(text, evaluate)?;
        }
        Ok(())
    }
    fn cs1_query(&self, state: &GameState, query: Query) -> Result<i32, String> {
        Ok(match query {
            Query::CurrentLevel(id) | Query::BaseLevel(id) | Query::Experience(id) => {
                let value = state
                    .entities()
                    .skills()
                    .get(&id)
                    .ok_or_else(|| format!("skill {id} has not been received"))?;
                match query {
                    Query::CurrentLevel(_) => i32::from(value.current),
                    Query::BaseLevel(_) => {
                        i32::from(self.profile.real_skill_level(value.experience))
                    }
                    _ => value.experience.cast_signed(),
                }
            }
            Query::Varp(id) => state.scenery().variable(id),
            Query::Varbit(id) => {
                let bit = self
                    .profile
                    .script_varbit(&self.cache, id.get())
                    .map_err(|e| e.to_string())?;
                let mask = u32::MAX >> (32 - u32::from(bit.high - bit.low + 1));
                ((state.scenery().variable(bit.varp).cast_unsigned() >> bit.low) & mask)
                    .cast_signed()
            }
            Query::RunEnergy => i32::from(state.entities().run_energy()),
            Query::Weight => i32::from(state.entities().run_weight()),
            Query::CombatLevel => state
                .entities()
                .local_player()
                .and_then(|p| p.appearance.as_ref())
                .map_or(0, |p| i32::from(p.combat_level)),
            Query::WorldX | Query::WorldY => {
                let player = state
                    .entities()
                    .local_player()
                    .ok_or("local player is absent")?;
                let position = player.path_queue.fine_position(player.tile);
                let corner = if query == Query::WorldX {
                    position.x
                } else {
                    position.y
                };
                i32::try_from(
                    (corner
                        .checked_add(64)
                        .ok_or("player fine coordinate overflow")?)
                        >> 7,
                )
                .map_err(|_| "player tile coordinate overflow")?
            }
            Query::WidgetItem { widget, item, .. } => {
                // Legacy item arrays have no retained owner/binding yet. Modern container IDs or
                // dynamic child items do not establish that old widget-array contract.
                rustclient_game_ui::widgets::resolve_widget(
                    state.interfaces(),
                    &self.interfaces,
                    WidgetAddress::component(widget),
                )
                .map_err(|e| e.to_string())?;
                if item.is_none() {
                    0
                } else {
                    return Err("legacy widget inventory arrays are not retained; no container binding inferred".into());
                }
            }
        })
    }
}
fn select_body(body: &mut WidgetBody, alternate: &WidgetBody) {
    match (body, alternate) {
        (WidgetBody::Rectangle { color, .. }, WidgetBody::Rectangle { color: other, .. }) => {
            *color = *other;
        }
        (
            WidgetBody::Text { color, text, .. },
            WidgetBody::Text {
                color: other,
                text: other_text,
                ..
            },
        ) => {
            *color = *other;
            if !other_text.is_empty() {
                text.clone_from(other_text);
            }
        }
        (WidgetBody::Sprite { sprite, .. }, WidgetBody::Sprite { sprite: other, .. }) => {
            *sprite = *other;
        }
        (
            WidgetBody::Model {
                model, sequence, ..
            },
            WidgetBody::Model {
                model: other,
                sequence: other_sequence,
                ..
            },
        ) => {
            *model = *other;
            *sequence = *other_sequence;
        }
        _ => {}
    }
}
#[cfg(test)]
mod tests;
