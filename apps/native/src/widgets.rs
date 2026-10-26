//! Native widget composition, input routing and background CPU asset decoding.
use crate::{LoadEvent, NativeApp, network};
use rustclient_game_ui::gameframe::{GameplayPointer, gameplay_pointer};
use rustclient_game_ui::widgets::resolve_widget;
use rustclient_model::{WidgetAction, WidgetBody, WidgetContent};
impl NativeApp {
    #[expect(
        clippy::cast_possible_truncation,
        clippy::cast_sign_loss,
        reason = "finite logical window extents are clamped before conversion"
    )]
    pub(super) fn synchronize_window(&mut self) {
        let Some(window) = &self.window else {
            return;
        };
        let size = self.shell.game_size(window);
        if size.width == 0 || size.height == 0 {
            return;
        }
        let stretch = self.stretch_layout(window);
        let available = if let Some(layout) = stretch {
            rustclient_model::CanvasSize {
                width: layout.real.0.clamp(765, 16_384),
                height: layout.real.1.clamp(503, 16_384),
            }
        } else {
            rustclient_model::CanvasSize {
                width: (f64::from(size.width) / window.scale_factor())
                    .floor()
                    .clamp(765., 16_384.) as u32,
                height: (f64::from(size.height) / window.scale_factor())
                    .floor()
                    .clamp(503., 16_384.) as u32,
            }
        };
        let metrics = self
            .widget_cache_profile
            .window_metrics(self.state.interfaces().window().mode, available);
        if metrics != self.state.interfaces().window() {
            self.widget_menu.close();
            self.context_menu.close();
            self.apply(Some(rustclient_game_state::SemanticOperation::Interface(
                rustclient_model::InterfaceUpdate::Window(metrics),
            )));
            self.request_widget_assets();
        }
        if self.reported_window != Some(metrics)
            && let Some(connection) = &self.connection
        {
            match connection.send(network::Outbound {
                map_epoch: self.map_epoch,
                interface_epoch: None,
                intent: rustclient_protocol::ClientIntent::WindowStatus {
                    mode: metrics.mode,
                    width: u16::try_from(metrics.canvas.width).expect("profile canvas cap"),
                    height: u16::try_from(metrics.canvas.height).expect("profile canvas cap"),
                },
            }) {
                Ok(()) => self.reported_window = Some(metrics),
                Err(e) => eprintln!("window status: {e}"),
            }
        }
    }
    pub(super) fn request_widget_assets(&mut self) {
        if self.widget_loading || self.state.authenticated_player().is_none() {
            return;
        }
        let Some(scripts) = &self.scripts else {
            return;
        };
        let mut request = match scripts.visual_request(&self.state) {
            Ok(r) => r,
            Err(e) => {
                eprintln!("widget dependencies: {e}");
                return;
            }
        };
        request.sprites.extend(
            self.plugins
                .requested_sprites()
                .map(rustclient_model::SpriteId::new),
        );
        // Everything loaded stays loaded (the cache is immutable); only new visuals are decoded.
        let request = request.missing_from(&self.widget_requested);
        if request.is_empty() {
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
    pub(super) fn ensure_widget_tree(&mut self) {
        if !self.widget_tree.nodes.is_empty()
            || self.state.authenticated_player().is_none()
            || self.state.interfaces().root().is_none()
        {
            return;
        }
        let Some(scripts) = &self.scripts else {
            return;
        };
        if let Ok(tree) = rustclient_game_ui::widgets::WidgetTree::build(
            self.state.interfaces(),
            &scripts.interfaces,
            rustclient_game_ui::widgets::WidgetBounds::canvas(self.state.interfaces()),
        ) {
            self.widget_tree = tree;
        }
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
        self.ensure_widget_tree();
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
            if widget.content == WidgetContent::Compass && !right {
                if let Some(camera) = &mut self.scene.camera {
                    camera.set_yaw(0);
                }
                return Ok(true);
            }
            if matches!(
                widget.content,
                WidgetContent::WorldViewport | WidgetContent::Minimap
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
                        self.widget_menu.open(
                            x,
                            y,
                            entries,
                            &assets.fonts[2],
                            &self.widget_assets.font_icons,
                            self.state.interfaces().window().canvas,
                        );
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
                if right && let Some(assets) = &self.assets {
                    self.widget_menu.open(
                        x,
                        y,
                        Vec::new(),
                        &assets.fonts[2],
                        &self.widget_assets.font_icons,
                        self.state.interfaces().window().canvas,
                    );
                    self.context_menu.close();
                }
                return Ok(true);
            }
        }
        Ok(false)
    }
    pub(super) fn widget_scroll(&mut self, x: f32, y: f32, delta: i32) -> Result<bool, String> {
        let world_hit = self.pointer_over_world(x, y);
        let Some(scripts) = &mut self.scripts else {
            return Ok(false);
        };
        let Some(outcome) = scripts.scroll_at(&mut self.state, [x, y], delta, world_hit)? else {
            return Ok(false);
        };
        for error in outcome.errors {
            eprintln!("widget scroll listener: {error}");
        }
        self.send_script_intents(outcome.intents);
        Ok(true)
    }

    pub(super) fn pointer_over_world(&self, x: f32, y: f32) -> bool {
        matches!(
            gameplay_pointer(
                &self.frame,
                self.minimap_mask.as_ref().map(|(_, mask)| mask),
                x,
                y,
            ),
            GameplayPointer::World
        )
    }
}
