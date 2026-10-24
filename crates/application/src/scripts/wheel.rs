//! IF3 wheel delivery follows the cache-authored scrollbar listeners.
use super::{ClientScripts, ListenerEvent, ListenerOutcome, Result};
use rustclient_game_state::GameState;
use rustclient_model::{WidgetBody, WidgetContent, WidgetListener};
use rustclient_platform::ReadOnlyBlobs;
impl<S: ReadOnlyBlobs> ClientScripts<S> {
    /// Queue visible wheel listeners in painter order, respecting frontmost wheel barriers.
    /// The host reports rotation, never an independent container scroll mutation.
    /// # Errors
    /// Rejects invalid layout or malformed listeners through the ordinary script diagnostics.
    pub fn scroll_at(
        &mut self,
        state: &mut GameState,
        point: [f32; 2],
        rotation: i32,
        world_hit: bool,
    ) -> Result<Option<ListenerOutcome>> {
        if rotation == 0 || point.iter().any(|n| !n.is_finite()) {
            return Ok(None);
        }
        let tree = rustclient_game_ui::widgets::WidgetTree::build(
            state.interfaces(),
            &self.interfaces,
            rustclient_game_ui::widgets::WidgetBounds::canvas(state.interfaces()),
        )
        .map_err(|e| e.to_string())?;
        let mut addresses = Vec::new();
        let mut blocked = false;
        for node in tree.nodes.iter().rev() {
            if !node.bounds.rect().contains(point[0], point[1])
                || !node.clip.rect().contains(point[0], point[1])
            {
                continue;
            }
            let widget = rustclient_game_ui::widgets::resolve_widget(
                state.interfaces(),
                &self.interfaces,
                node.address,
            )
            .map_err(|e| e.to_string())?;
            if widget.listeners.contains_key(&WidgetListener::Scroll)
                && (widget.content != WidgetContent::WorldViewport || world_hit)
            {
                addresses.push(node.address);
            }
            blocked = matches!(
                widget.body,
                WidgetBody::Container {
                    no_click_through: true,
                    ..
                }
            ) || state
                .interfaces()
                .runtime()
                .get(&node.address)
                .is_some_and(|r| r.no_scroll_through == Some(true));
            if blocked {
                break;
            }
        }
        if addresses.is_empty() && !blocked {
            return Ok(None);
        }
        for address in addresses.into_iter().rev() {
            self.queue_listener(
                state,
                address,
                WidgetListener::Scroll,
                ListenerEvent {
                    wheel: Some(rotation),
                    ..Default::default()
                },
            )?;
        }
        let errors = self.drain(state);
        let intents = std::mem::take(&mut self.effects);
        Ok(Some(ListenerOutcome { intents, errors }))
    }
}
