//! Native widget composition, input routing and background CPU asset decoding.
use crate::{LoadEvent, NativeApp, network};
use rustclient_game_ui::gameframe::{GameplayPointer, gameplay_pointer};
use rustclient_game_ui::widgets::resolve_widget;
use rustclient_model::{WidgetAction, WidgetBody, WidgetContent};
use rustclient_platform::CpuJobExecutor;
pub(super) type WidgetLayoutKey = (u64, u64, Option<rustclient_game_ui::pointer::DraggedWidget>);
impl NativeApp {
    pub(super) fn widget_sprites_missing(&self) -> bool {
        self.plugins.requested_sprites().any(|sprite| {
            !self
                .widget_requested
                .sprites
                .contains(&rustclient_model::SpriteId::new(sprite))
        })
    }
    pub(super) fn cpu_jobs(
        &mut self,
    ) -> Result<std::sync::Arc<rustclient_platform_native::NativeJobPool>, String> {
        self.scene.cpu_jobs()
    }
    pub(super) fn widget_layout_key(&self) -> Option<WidgetLayoutKey> {
        self.scripts.as_ref().map(|scripts| {
            (
                self.state.interfaces().revision(),
                scripts.catalog_revision(),
                self.dragged_widget(),
            )
        })
    }

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
            self.cancel_map_input();
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
                world_action: None,
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
        let key = (
            self.state.interfaces().revision(),
            scripts.catalog_revision(),
        );
        if self.widget_asset_key == Some(key) && !self.widget_sprites_missing() {
            return;
        }
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
            self.widget_asset_key = Some(key);
            return;
        }
        self.widget_loading = true;
        let path = self.widget_cache_dir.clone();
        let profile = self.widget_cache_profile;
        let shared_cache = std::sync::Arc::clone(&self.scene.shared_cache);
        let proxy = self.proxy.clone();
        let generation = self.connection_generation;
        let pool = match self.cpu_jobs() {
            Ok(pool) => pool,
            Err(error) => {
                self.widget_loading = false;
                eprintln!("{error}");
                return;
            }
        };
        let identity = rustclient_platform::JobIdentity {
            session: generation,
            ..Default::default()
        };
        let fault_proxy = proxy.clone();
        let fault_request = request.clone();
        let job = rustclient_platform::CpuJob::new(
            identity,
            rustclient_platform::JobPriority::Visible,
            self.widget_job_scope.clone(),
            move |cancellation| {
                let assets = crate::scene::SceneView::open_shared_cache(&path, &shared_cache)
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
                if cancellation.is_cancelled() {
                    return;
                }
                let _ = proxy.send_event(LoadEvent::WidgetAssets(
                    generation,
                    request,
                    Box::new(assets),
                ));
            },
        )
        .with_fault_handler(move || {
            let mut assets = rustclient_game_ui::widget_visuals::WidgetVisualAssets::default();
            assets
                .failures
                .push("widget asset worker panicked; reconnect to retry".into());
            let _ = fault_proxy.send_event(LoadEvent::WidgetAssets(
                generation,
                fault_request,
                Box::new(assets),
            ));
        });
        if pool.try_submit(job).is_err() {
            // Nothing was marked loaded/requested. The next cycle retries admission.
            self.widget_loading = false;
        } else {
            self.widget_asset_key = Some(key);
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
                world_action: None,
                map_epoch: self.map_epoch,
                interface_epoch: Some(self.interface_epoch),
                intent,
            })?;
        }
        self.request_widget_assets();
        Ok(())
    }
    /// Retain widget commands until one of the actual paint dependencies changes.
    pub(super) fn project_game_widgets(&mut self) {
        let dragged = self.dragged_widget();
        let key = self.widget_layout_key();
        let Some(scripts) = &self.scripts else {
            self.frame.clear_quads();
            self.widget_paint = None;
            return;
        };
        if self.widget_paint.as_ref().is_some_and(|dependencies| {
            scripts.widget_paint_is_current(&self.state, dependencies, dragged)
        }) && self.frame.reuse_widgets()
        {
            return;
        }
        self.frame.clear_quads();
        self.widget_paint = None;
        let started = std::time::Instant::now();
        let retained = (self.widget_tree_key == key).then(|| std::mem::take(&mut self.widget_tree));
        match scripts.project_widgets_reusing(
            &self.state,
            &self.widget_assets,
            &mut self.frame,
            dragged,
            retained,
        ) {
            Ok(projection) => {
                self.widget_tree = projection.tree;
                self.widget_tree_key = key;
                self.widget_paint = Some(projection.dependencies);
                self.frame.seal_widgets();
                for error in projection.diagnostics {
                    if self.reported_script_errors.len() < 256
                        && self.reported_script_errors.insert(error.clone())
                    {
                        eprintln!("widget projection: {error}");
                    }
                }
            }
            Err(error) => {
                eprintln!("widget projection: {error}");
                self.widget_tree.nodes.clear();
                self.widget_tree_key = None;
                self.frame.clear_quads();
            }
        }
        self.performance
            .stage(crate::performance::Stage::WidgetProjection, started);
    }
    pub(super) fn ensure_widget_tree(&mut self) {
        let key = self.widget_layout_key();
        if self.widget_tree_key == key
            || self.state.authenticated_player().is_none()
            || self.state.interfaces().root().is_none()
        {
            return;
        }
        let Some(scripts) = &self.scripts else {
            return;
        };
        if let Ok(tree) = rustclient_game_ui::widgets::WidgetTree::build_with_drag(
            self.state.interfaces(),
            scripts.interfaces(),
            rustclient_game_ui::widgets::WidgetBounds::canvas(self.state.interfaces()),
            self.dragged_widget(),
        ) {
            self.widget_tree = tree;
            self.widget_tree_key = key;
        }
    }
    /// Run the plugin menu stage over a widget's actions (SPEC-0021): hooks, ordered edits and
    /// relabels. The rows are remembered so choosing one reaches plugins as `MenuOptionClicked`.
    pub(super) fn stage_widget_entries(
        &mut self,
        entries: Vec<(String, WidgetAction)>,
        opened: bool,
    ) -> Vec<(String, WidgetAction)> {
        let records: Vec<_> = entries
            .iter()
            .map(|(label, action)| rustclient_application::menu::widget_menu_entry(label, *action))
            .collect();
        self.widget_menu_records = entries
            .iter()
            .zip(&records)
            .map(|((_, action), record)| (*action, record.clone()))
            .collect();
        if entries.is_empty() || !self.plugins.wants_menu_stage() {
            return entries;
        }
        let edited = self.plugins.menu_stage(&records, opened);
        rustclient_application::menu::apply_edited_menu(entries, &edited, |(label, _), option| {
            *label = rustclient_application::menu::relabel(label, option);
        })
    }
    /// A widget menu entry was chosen: deliver it to plugins, then run it.
    fn choose_widget_action(&mut self, action: WidgetAction) -> Result<(), String> {
        if let Some((_, record)) = self.widget_menu_records.iter().find(|(a, _)| *a == action) {
            self.plugins.menu_chosen(record.clone());
        }
        self.send_widget_action(action)
    }
    pub(super) fn widget_click(&mut self, x: f32, y: f32, right: bool) -> Result<bool, String> {
        if self.state.interfaces().root().is_none() {
            return Ok(false);
        }
        if self.widget_menu.is_open() && !right {
            if let Some(action) = self.widget_menu.click(x, y) {
                self.choose_widget_action(action)?;
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
        let mut staged = None;
        for node in self.widget_tree.nodes.iter().rev() {
            if !node.bounds.rect().contains(x, y) || !node.clip.rect().contains(x, y) {
                continue;
            }
            let widget =
                resolve_widget(self.state.interfaces(), scripts.interfaces(), node.address)
                    .map_err(|e| e.to_string())?;
            if widget.content == WidgetContent::Compass && !right {
                if let Some(camera) = &mut self.scene.camera {
                    camera.set_yaw(0);
                }
                return Ok(true);
            }
            if widget.content == WidgetContent::WorldMap && !right {
                self.world_map_input_bounds = Some(node.bounds.rect());
                self.map_pointer([x, y], true, true);
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
                scripts.interfaces(),
                node.address,
            )?;
            if !entries.is_empty() {
                staged = Some(entries);
                break;
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
                    self.cancel_gestures();
                }
                return Ok(true);
            }
        }
        if let Some(entries) = staged {
            let entries = self.stage_widget_entries(entries, right);
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
                    self.cancel_gestures();
                }
            } else if let Some((_, action)) = entries.first() {
                self.choose_widget_action(*action)?;
            }
            return Ok(true);
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
