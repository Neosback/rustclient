//! Native widget composition, input routing and background CPU asset decoding.
use crate::{LoadEvent, NativeApp, network};
use rustclient_game_ui::widgets::resolve_widget;
use rustclient_model::{WidgetAction, WidgetBody, WidgetContent};
impl NativeApp {
    pub(super) fn request_widget_assets(&mut self) {
        if self.widget_loading || self.state.authenticated_player().is_none() {
            return;
        }
        let Some(scripts) = &self.scripts else {
            return;
        };
        let request = match scripts.visual_request(&self.state) {
            Ok(r) => r,
            Err(e) => {
                eprintln!("widget dependencies: {e}");
                return;
            }
        };
        if request == self.widget_requested {
            return;
        }
        self.widget_loading = true;
        let path = self.widget_cache_dir.clone();
        let profile = self.widget_cache_profile;
        let proxy = self.proxy.clone();
        let generation = self.connection_generation;
        let result = std::thread::Builder::new()
            .name("widget-assets".into())
            .spawn(move || {
                let assets = rustclient_platform_native::DirectoryBlobs::open(path)
                    .map_err(|e| e.to_string())
                    .and_then(|source| {
                        rustclient_cache::Cache::open(source).map_err(|e| e.to_string())
                    })
                    .map_or_else(
                        |e| {
                            let mut assets =
                                rustclient_game_ui::widget_visuals::WidgetVisualAssets::default();
                            assets.failures.push(e);
                            assets
                        },
                        |cache| {
                            rustclient_application::scripts::load_widget_visuals(
                                &cache, profile, &request,
                            )
                        },
                    );
                let _ = proxy.send_event(LoadEvent::WidgetAssets(
                    generation,
                    request,
                    Box::new(assets),
                ));
            });
        if let Err(e) = result {
            self.widget_loading = false;
            eprintln!("widget asset worker: {e}");
        }
    }
    pub(super) fn send_widget_action(&mut self, action: WidgetAction) -> Result<(), String> {
        let scripts = self
            .scripts
            .as_mut()
            .ok_or("widget definitions are loading")?;
        let (intents, errors) = scripts.perform_action(&mut self.state, action)?;
        for error in errors {
            eprintln!("widget listener: {error}");
        }
        let connection = self
            .connection
            .as_ref()
            .ok_or("widget action requires a connection")?;
        for intent in intents {
            connection.send(network::Outbound {
                map_epoch: self.map_epoch,
                interface_epoch: Some(self.interface_epoch),
                intent,
            })?;
        }
        self.widget_tree.nodes.clear();
        self.request_widget_assets();
        Ok(())
    }
    pub(super) fn widget_click(&mut self, x: f32, y: f32, right: bool) -> Result<bool, String> {
        if self.state.interfaces().root().is_none() {
            return Ok(false);
        }
        if self.widget_menu.is_open() && !right {
            if let Some(action) = self.widget_menu.click(x, y) {
                self.send_widget_action(action)?;
            }
            return Ok(true);
        }
        self.widget_menu.close();
        let Some(scripts) = &self.scripts else {
            return Ok(true);
        };
        if self.widget_tree.nodes.is_empty() {
            return Ok(true);
        }
        for node in self.widget_tree.nodes.iter().rev() {
            if !node.bounds.rect().contains(x, y) || !node.clip.rect().contains(x, y) {
                continue;
            }
            let widget = resolve_widget(self.state.interfaces(), &scripts.interfaces, node.address)
                .map_err(|e| e.to_string())?;
            if matches!(
                widget.content,
                WidgetContent::WorldViewport | WidgetContent::Minimap | WidgetContent::Compass
            ) {
                return Ok(false);
            }
            let entries = rustclient_application::widget_actions(
                &self.state,
                &scripts.interfaces,
                node.address,
            )?;
            if !entries.is_empty() {
                if right {
                    if let Some(assets) = &self.assets {
                        self.widget_menu.open(x, y, entries, assets);
                        self.context_menu.close();
                    }
                } else if let Some((_, action)) = entries.first() {
                    self.send_widget_action(*action)?;
                }
                return Ok(true);
            }
            if matches!(
                widget.body,
                WidgetBody::Container {
                    no_click_through: true,
                    ..
                }
            ) {
                return Ok(true);
            }
        }
        Ok(false)
    }
    pub(super) fn widget_scroll(&mut self, x: f32, y: f32, delta: i32) -> Result<bool, String> {
        let Some(scripts) = &self.scripts else {
            return Ok(false);
        };
        for node in self.widget_tree.nodes.iter().rev() {
            if !node.bounds.rect().contains(x, y) || !node.clip.rect().contains(x, y) {
                continue;
            }
            let widget = resolve_widget(self.state.interfaces(), &scripts.interfaces, node.address)
                .map_err(|e| e.to_string())?;
            if let WidgetBody::Container { scroll_size, .. } = widget.body
                && scroll_size[1] > node.bounds.height.max(0).cast_unsigned()
            {
                let mut runtime = self
                    .state
                    .interfaces()
                    .runtime()
                    .get(&node.address)
                    .cloned()
                    .unwrap_or_default();
                let maximum =
                    scroll_size[1].saturating_sub(node.bounds.height.max(0).cast_unsigned());
                let mut scroll = runtime.scroll.unwrap_or([0, node.scroll_y]);
                scroll[1] = scroll[1].saturating_add_signed(delta).min(maximum);
                runtime.scroll = Some(scroll);
                let op = rustclient_game_state::SemanticOperation::Interface(
                    rustclient_model::InterfaceUpdate::SetProperty {
                        widget: node.id,
                        property: rustclient_model::WidgetProperty::Runtime {
                            address: node.address,
                            value: Box::new(runtime),
                        },
                    },
                );
                self.apply(Some(op));
                return Ok(true);
            }
        }
        Ok(false)
    }
}
