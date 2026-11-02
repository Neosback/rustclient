//! Native pointer normalization; gameplay validation remains in application.
use crate::{NativeApp, network};
use rustclient_game_ui::gameframe::{GameplayPointer, gameplay_pointer};
use rustclient_model::{SceneDescriptor, WorldAction, WorldTarget, WorldTile};
use rustclient_renderer::CameraViewport;
use std::time::Instant;
use winit::window::Window;

/// One entry of the world right-click menu.
#[derive(Debug, Clone, Copy)]
pub(super) enum WorldChoice {
    Action(WorldAction),
    /// Captured world tile and map identity from when the menu was opened.
    Walk {
        scene: SceneDescriptor,
        destination: WorldTile,
    },
    /// An entry a plugin offered, by its slot in `NativeApp::plugin_menu` from when the menu opened.
    Plugin {
        slot: usize,
    },
}

/// Which click-cross animation to play: yellow for movement, red for an interaction.
#[derive(Debug, Clone, Copy)]
pub(super) enum CrossKind {
    Walk,
    Interact,
}

/// The mouse-over text at the top of the world view: the first menu entry under the pointer.
#[derive(Debug, Clone, PartialEq, Eq)]
pub(super) struct HoverText {
    pub verb: String,
    pub target: String,
    pub target_color: [u8; 4],
}

impl NativeApp {
    #[expect(
        clippy::cast_possible_truncation,
        reason = "bounded mask pixel offsets"
    )]
    pub(super) fn walk_click(&mut self, window: &Window, x: f32, y: f32) -> Result<bool, String> {
        let (Some(loaded), Some(camera), Some(player)) = (
            &self.scene.loaded,
            self.scene.camera,
            self.state.entities().local_player(),
        ) else {
            return Ok(false);
        };
        if self.scene.loading || self.scene.diagnostics || self.connection.is_none() {
            return Ok(false);
        }
        let scene = self
            .state
            .loaded_scene()
            .unwrap_or_else(|| loaded.asset.descriptor());
        let (destination, click) = match gameplay_pointer(
            &self.frame,
            self.minimap_mask.as_ref().map(|(_, mask)| mask),
            x,
            y,
        ) {
            GameplayPointer::Blocked => return Ok(false),
            GameplayPointer::Minimap { dx, dy } => {
                let offset = [dx as i32, dy as i32];
                let position = player.path_queue.fine_position(player.tile);
                let fine = [position.x + 64, position.y + 64];
                let Some(target) = rustclient_scene::minimap::click_destination(
                    offset,
                    fine,
                    camera.yaw(),
                    self.state.interfaces().minimap().zoom_quarters,
                ) else {
                    return Ok(false);
                };
                let origin = scene.origin();
                let local = [
                    fine[0] - i64::from(origin.x) * 128,
                    fine[1] - i64::from(origin.y) * 128,
                ];
                let click = rustclient_protocol::MinimapClick {
                    offset: [
                        i8::try_from(offset[0])
                            .map_err(|_| "minimap x offset exceeds telemetry range")?,
                        i8::try_from(offset[1])
                            .map_err(|_| "minimap y offset exceeds telemetry range")?,
                    ],
                    camera_yaw: camera.yaw(),
                    fine_position: [
                        u16::try_from(local[0]).map_err(|_| "minimap fine x outside build area")?,
                        u16::try_from(local[1]).map_err(|_| "minimap fine y outside build area")?,
                    ],
                    shift: self.modifiers.shift_key(),
                };
                (
                    WorldTile::new(target.x, target.y, player.tile.plane),
                    Some(click),
                )
            }
            GameplayPointer::World => {
                let Some((origin, direction)) = self.world_ray(window, x, y) else {
                    return Ok(false);
                };
                let visibility = self.scene_visibility(window);
                let Some(tile) = loaded.picking.pick_terrain(
                    &loaded.mesh,
                    scene,
                    player.tile.plane,
                    origin,
                    direction,
                    &visibility,
                ) else {
                    return Ok(false);
                };
                (tile, None)
            }
        };
        self.send_walk_destination(scene, destination, click)?;
        if click.is_none() {
            self.show_cross(CrossKind::Walk, x, y);
        }
        Ok(true)
    }

    fn send_walk_destination(
        &mut self,
        scene: SceneDescriptor,
        destination: WorldTile,
        click: Option<rustclient_protocol::MinimapClick>,
    ) -> Result<(), String> {
        let loaded = self
            .scene
            .loaded
            .as_ref()
            .ok_or("walk requires a loaded scene")?;
        let connection = self
            .connection
            .as_ref()
            .ok_or("walk requires a connection")?;
        let invert_run = self.modifiers.control_key();
        let ordinary = rustclient_application::validate_walk(
            &self.state,
            &loaded.asset,
            scene,
            destination,
            invert_run,
        )?;
        let intent = click.map_or(ordinary, |click| {
            rustclient_protocol::ClientIntent::MinimapWalk {
                destination,
                invert_run,
                click,
            }
        });
        connection.send(network::Outbound {
            world_action: None,
            map_epoch: self.map_epoch,
            interface_epoch: None,
            intent,
        })?;
        self.state
            .apply_operation(rustclient_game_state::SemanticOperation::SetDestination(
                Some(rustclient_model::MapDestination {
                    x: destination.x,
                    y: destination.y,
                }),
            ))
            .map_err(|error| error.to_string())?;
        Ok(())
    }

    pub(super) fn acknowledge_map(&self) {
        if let Some(connection) = &self.connection
            && let Err(e) = connection.send(network::Outbound {
                world_action: None,
                map_epoch: self.map_epoch,
                interface_epoch: None,
                intent: rustclient_protocol::ClientIntent::MapBuildComplete,
            })
        {
            eprintln!("map acknowledgement: {e}");
        }
    }
}

impl NativeApp {
    fn target_at(&self, window: &Window, x: f32, y: f32) -> Option<rustclient_model::WorldTarget> {
        let loaded = self.scene.loaded.as_ref()?;
        if self.scene.loading || self.scene.diagnostics {
            return None;
        }
        if !matches!(
            gameplay_pointer(
                &self.frame,
                self.minimap_mask.as_ref().map(|(_, mask)| mask),
                x,
                y,
            ),
            GameplayPointer::World
        ) {
            return None;
        }
        let (origin, direction) = self.world_ray(window, x, y)?;
        let visibility = self.scene_visibility(window);
        let hit =
            loaded
                .picking
                .pick_surface(&loaded.mesh.geometry, origin, direction, &visibility);
        // Static scenery wins exact ties, matching the previous two-way comparison.
        let overlays = [&loaded.mesh.dynamic, &self.scene.entity_geometry]
            .into_iter()
            .filter_map(|geometry| {
                rustclient_scene::pick_surface(geometry, origin, direction, &visibility)
            });
        hit.into_iter()
            .chain(overlays)
            .min_by(|a, b| a.distance.total_cmp(&b.distance))
            .and_then(|hit| hit.target)
    }
    fn world_ray(&self, window: &Window, x: f32, y: f32) -> Option<([f32; 3], [f32; 3])> {
        let (camera, bounds, viewport) = self.world_camera(window)?;
        if !bounds.contains(x, y) {
            return None;
        }
        camera.pointer_ray(viewport, [x - bounds.x, y - bounds.y])
    }
    /// The gameplay camera with the logical bounds and shaped viewport it renders through.
    #[expect(
        clippy::cast_possible_truncation,
        reason = "bounded logical viewport coordinates"
    )]
    fn world_camera(
        &self,
        window: &Window,
    ) -> Option<(
        rustclient_renderer::GameplayCameraController,
        rustclient_game_ui::Rect,
        CameraViewport,
    )> {
        let mut camera = self.scene.camera?;
        let settings = self.state.interfaces().viewport();
        camera.set_zoom(i32::from(settings.zoom[0]), i32::from(settings.zoom[1]));
        let bounds = self.frame.world_viewport.unwrap_or_else(|| {
            if let Some(layout) = self.stretch_layout(window) {
                return rustclient_game_ui::Rect::new(
                    0.,
                    0.,
                    f32::from(u16::try_from(layout.real.0).unwrap_or(u16::MAX)),
                    f32::from(u16::try_from(layout.real.1).unwrap_or(u16::MAX)),
                );
            }
            let size = self.shell.game_size(window);
            rustclient_game_ui::Rect::new(
                0.,
                0.,
                (f64::from(size.width) / window.scale_factor()) as f32,
                (f64::from(size.height) / window.scale_factor()) as f32,
            )
        });
        let viewport =
            CameraViewport::new(bounds.width as i32, bounds.height as i32).with_settings(settings);
        Some((camera, bounds, viewport))
    }
    /// The roof line-of-sight inputs for this frame, once a scene and a player exist.
    fn roof_view(&self, window: &Window) -> Option<rustclient_scene::RoofView> {
        let (Some(loaded), Some((camera, _, viewport)), Some(player)) = (
            self.scene.loaded.as_ref(),
            self.world_camera(window),
            self.state.entities().local_player(),
        ) else {
            return None;
        };
        let origin = loaded.asset.descriptor().origin();
        let corner = self
            .scene
            .motion
            .player(player.index)
            .unwrap_or([player.tile.x * 128, player.tile.y * 128]);
        let eye = camera.pose(viewport).position;
        Some(rustclient_scene::RoofView {
            plane: player.tile.plane,
            subject: [
                corner[0] - origin.x * 128 + 64,
                corner[1] - origin.y * 128 + 64,
            ],
            camera: [eye[0], eye[2]],
            pitch: camera.pitch(),
            roofs_hidden: self
                .state
                .interfaces()
                .settings()
                .get(&rustclient_model::ClientSetting::RoofsHidden)
                .copied()
                .unwrap_or(false),
        })
    }
    /// What the scene draws this frame: the roof line-of-sight cut (or the player's plane when
    /// the "hide roofs" game option is on) plus the roofs plugins asked to hide. Every level
    /// draws before a player exists.
    pub(super) fn scene_visibility(&self, window: &Window) -> rustclient_scene::SceneVisibility {
        let (Some(loaded), Some(view), Some((_camera, _, _viewport))) = (
            self.scene.loaded.as_ref(),
            self.roof_view(window),
            self.world_camera(window),
        ) else {
            return rustclient_scene::SceneVisibility::everything();
        };
        let origin = loaded.asset.descriptor().origin();
        let local = |x: i32, y: i32| [x - origin.x, y - origin.y];
        let modes = rustclient_scene::RoofModes(self.plugins.output().scene.roofs.bits());
        rustclient_scene::SceneVisibility {
            top_level: view.top_level(&loaded.mesh.roofs),
            hidden: view.hidden(
                &loaded.mesh.roofs,
                modes,
                rustclient_scene::RoofTargets {
                    hovered: self.roof_hover,
                    destination: self.state.destination().map(|d| local(d.x, d.y)),
                },
            ),
            ..rustclient_scene::SceneVisibility::everything()
        }
        .with_policy(
            view.camera,
            self.renderer
                .as_ref()
                .map_or(50, |renderer| renderer.gpu_settings().draw_distance),
            self.renderer
                .as_ref()
                .is_none_or(|renderer| renderer.gpu_settings().hide_unrelated_maps),
        )
    }
    /// The tile under the pointer on the local player's plane, as plugins see it.
    pub(super) fn hovered_world_tile(&self) -> Option<rustclient_model::WorldTile> {
        let [x, y] = self.roof_hover?;
        let base = self.state.loaded_scene()?.origin();
        let plane = self.state.entities().local_player()?.tile.plane;
        Some(rustclient_model::WorldTile::new(
            base.x + x,
            base.y + y,
            plane,
        ))
    }
    /// Track the tile under the pointer for plugins and the hovered-roof mode.
    ///
    /// The pick ignores hidden roofs on purpose: pointing at a roof is what asks for it to go.
    pub(super) fn update_roof_hover(&mut self, window: &Window) {
        let wanted = self.plugins.reads_client()
            || self
                .plugins
                .output()
                .scene
                .roofs
                .contains(rustclient_plugin_api::RoofModes::HOVERED);
        let hovered = if wanted
            && let Some(loaded) = &self.scene.loaded
            && let Some((x, y)) = self.cursor
            && let (x, y) = self.ui_pointer(window, x, y)
            && self.pointer_over_world(x, y)
            && let Some((origin, direction)) = self.world_ray(window, x, y)
        {
            let descriptor = self
                .state
                .loaded_scene()
                .unwrap_or_else(|| loaded.asset.descriptor());
            let base = descriptor.origin();
            let plane = self
                .state
                .entities()
                .local_player()
                .map_or(base.plane, |player| player.tile.plane);
            let mut visibility = self.scene_visibility(window);
            visibility.hidden = rustclient_scene::RoofMask::default();
            loaded
                .picking
                .pick_terrain(
                    &loaded.mesh,
                    descriptor,
                    plane,
                    origin,
                    direction,
                    &visibility,
                )
                .map(|tile| [tile.x - base.x, tile.y - base.y])
        } else {
            None
        };
        self.roof_hover = hovered;
    }
    pub(super) fn send_world_action(
        &mut self,
        action: rustclient_model::WorldAction,
    ) -> Result<(), String> {
        let loaded = self
            .scene
            .loaded
            .as_ref()
            .ok_or("world action requires a loaded scene")?;
        let connection = self
            .connection
            .as_ref()
            .ok_or("world action requires a connection")?;
        let intent = rustclient_application::validate_world_action(
            &self.state,
            &loaded.asset,
            &self.scene.entity_catalog,
            action,
            self.modifiers.control_key(),
        )?;
        let destination = match action.target {
            WorldTarget::Npc { index, .. } => {
                self.state.entities().npcs().get(&index).map(|npc| npc.tile)
            }
            WorldTarget::Location { key, .. } => Some(key.tile),
        };
        connection.send(network::Outbound {
            world_action: Some(action),
            map_epoch: self.map_epoch,
            interface_epoch: None,
            intent,
        })?;
        if let Some(tile) = destination {
            self.state
                .apply_operation(rustclient_game_state::SemanticOperation::SetDestination(
                    Some(rustclient_model::MapDestination {
                        x: tile.x,
                        y: tile.y,
                    }),
                ))
                .map_err(|error| error.to_string())?;
        }
        Ok(())
    }
    /// Start the click-cross animation at a UI position.
    pub(super) fn show_cross(&mut self, kind: CrossKind, x: f32, y: f32) {
        self.click_cross = Some((Instant::now(), kind, x, y));
    }
    /// Carry out a world menu entry chosen by a click or key press at `(x, y)`.
    pub(super) fn run_world_choice(
        &mut self,
        _window: &Window,
        choice: WorldChoice,
        x: f32,
        y: f32,
    ) -> Result<(), String> {
        match choice {
            WorldChoice::Action(action) => {
                self.send_world_action(action)?;
                self.show_cross(CrossKind::Interact, x, y);
            }
            WorldChoice::Walk { scene, destination } => {
                self.send_walk_destination(scene, destination, None)?;
                self.show_cross(CrossKind::Walk, x, y);
            }
            WorldChoice::Plugin { slot } => {
                if let Some(entry) = self.plugin_menu.get(slot) {
                    self.plugins.menu_chosen(entry.clone());
                }
            }
        }
        Ok(())
    }
    /// The menu entries for whatever is under the pointer: the target's actions, then
    /// "Walk here" when the pointer is over the world.
    fn world_entries(
        &self,
        window: &Window,
        x: f32,
        y: f32,
    ) -> Result<Vec<(String, WorldChoice, Option<WorldTarget>)>, String> {
        let mut entries = Vec::new();
        let active_scene = self.state.loaded_scene().or_else(|| {
            self.scene
                .loaded
                .as_ref()
                .map(|loaded| loaded.asset.descriptor())
        });
        if let Some(target) = self.target_at(window, x, y)
            && let Some(loaded) = &self.scene.loaded
            && let Some(scene) = active_scene
        {
            entries.extend(
                rustclient_application::world_actions(
                    &self.state,
                    &loaded.asset,
                    &self.scene.entity_catalog,
                    scene,
                    target,
                )?
                .into_iter()
                .map(|(label, action)| (label, WorldChoice::Action(action), Some(target))),
            );
        }
        // The same gate as the left-click walk: nothing to walk on while loading, in the diagnostics
        // view, or without a session.
        if !self.scene.loading
            && !self.scene.diagnostics
            && self.connection.is_some()
            && self.pointer_over_world(x, y)
            && let Some(loaded) = &self.scene.loaded
            && let Some(scene) = active_scene
            && let Some((origin, direction)) = self.world_ray(window, x, y)
            && let Some(destination) = loaded.picking.pick_terrain(
                &loaded.mesh,
                scene,
                self.state
                    .entities()
                    .local_player()
                    .map_or_else(|| scene.origin().plane, |player| player.tile.plane),
                origin,
                direction,
                &self.scene_visibility(window),
            )
        {
            entries.push((
                "Walk here".to_owned(),
                WorldChoice::Walk { scene, destination },
                None,
            ));
        }
        Ok(entries)
    }
    /// Append the entries plugins offered for the tile this menu was opened on (SPEC-0004
    /// `menu_offers`): below "Walk here", so they never become the left-click default, and only
    /// while Shift is held when the offer asks for it. The captured entries stay in `plugin_menu`
    /// until the next menu opens, so a click delivers what the menu showed.
    fn add_plugin_entries(
        &mut self,
        entries: &mut Vec<(String, WorldChoice, Option<WorldTarget>)>,
    ) {
        self.plugin_menu.clear();
        let Some(tile) = entries.iter().find_map(|(_, choice, _)| match choice {
            WorldChoice::Walk { destination, .. } => Some(*destination),
            _ => None,
        }) else {
            return;
        };
        self.plugin_menu = offered_entries(
            &self.plugins.output().menu_offers,
            self.modifiers.shift_key(),
            (tile.x, tile.y),
        );
        for (slot, entry) in self.plugin_menu.iter().enumerate() {
            entries.push((
                format!("{} {}", entry.option, entry.target),
                WorldChoice::Plugin { slot },
                None,
            ));
        }
    }
    /// A world or widget menu is open (SPEC-0021 input arbitration).
    pub(super) fn menu_open(&self) -> bool {
        self.context_menu.is_open() || self.widget_menu.is_open()
    }
    pub(super) fn close_menus(&mut self) {
        self.context_menu.close();
        self.widget_menu.close();
    }
    /// Opening a menu cancels any hold, drag and held camera input without firing their release
    /// listeners: the press that started them now belongs to the menu.
    pub(super) fn cancel_gestures(&mut self) {
        self.scene.input = rustclient_renderer::CameraInput::default();
        self.widget_pointer = crate::widget_pointer::WidgetPointer::default();
        self.cancel_map_input();
    }
    /// A press while a menu is open: the menu selects the row under the pointer or closes, and
    /// nothing beneath receives it. Returns whether a menu took the press.
    pub(super) fn press_open_menu(&mut self, window: &Window, x: f32, y: f32) -> bool {
        if self.context_menu.is_open() {
            if let Err(error) = self.world_click(window, x, y, false) {
                eprintln!("menu action: {error}");
                window.set_title(&format!("RustClient - {error}"));
            }
            return true;
        }
        if self.widget_menu.is_open() {
            if let Err(error) = self.widget_click(x, y, false) {
                eprintln!("widget menu: {error}");
                window.set_title(&format!("RustClient - {error}"));
            }
            return true;
        }
        false
    }
    /// Refresh the mouse-over text after the pointer moved.
    pub(super) fn update_hover(&mut self, window: &Window) {
        if !std::mem::take(&mut self.hover_dirty) {
            return;
        }
        self.update_roof_hover(window);
        let hover = self.hover_at(window);
        if hover != self.hover {
            self.hover = hover;
            window.request_redraw();
        }
    }
    fn hover_at(&self, window: &Window) -> Option<HoverText> {
        if self.context_menu.is_open()
            || self.widget_menu.is_open()
            || self.state.loaded_scene().is_none()
            || self.scene.loading
            || self.scene.diagnostics
        {
            return None;
        }
        let (x, y) = self.cursor?;
        let (x, y) = self.ui_pointer(window, x, y);
        if let Some(scripts) = &self.scripts {
            for node in self.widget_tree.nodes.iter().rev() {
                if !node.bounds.rect().contains(x, y) || !node.clip.rect().contains(x, y) {
                    continue;
                }
                let Ok(widget) = rustclient_game_ui::widgets::resolve_widget(
                    self.state.interfaces(),
                    scripts.interfaces(),
                    node.address,
                ) else {
                    continue;
                };
                if widget.content == rustclient_model::WidgetContent::WorldViewport {
                    break;
                }
                if let Some((label, _)) = rustclient_application::widget_actions(
                    &self.state,
                    scripts.interfaces(),
                    node.address,
                )
                .ok()?
                .into_iter()
                .next()
                {
                    return Some(HoverText {
                        verb: plain_label(&label),
                        target: String::new(),
                        target_color: [255; 4],
                    });
                }
                if matches!(
                    widget.body,
                    rustclient_model::WidgetBody::Container {
                        no_click_through: true,
                        ..
                    }
                ) {
                    return None;
                }
            }
        }
        let (label, _, target) = self.world_entries(window, x, y).ok()?.into_iter().next()?;
        let label = plain_label(&label);
        let (verb, name) = label.split_once(' ').unwrap_or((&label, ""));
        Some(HoverText {
            verb: verb.to_owned(),
            target: name.to_owned(),
            target_color: match target {
                Some(WorldTarget::Npc { .. }) => [255, 255, 0, 255],
                _ => [0, 255, 255, 255],
            },
        })
    }
    pub(super) fn world_click(
        &mut self,
        window: &Window,
        x: f32,
        y: f32,
        right: bool,
    ) -> Result<bool, String> {
        if self.context_menu.is_open() && !right {
            if let Some(choice) = self.context_menu.click(x, y) {
                self.run_world_choice(window, choice, x, y)?;
            }
            return Ok(true);
        }
        self.context_menu.close();
        let mut entries = self.world_entries(window, x, y)?;
        if right {
            self.add_plugin_entries(&mut entries);
            if let Some(assets) = &self.assets {
                self.context_menu.open(
                    x,
                    y,
                    entries
                        .into_iter()
                        .map(|(label, choice, _)| (label, choice))
                        .collect(),
                    &assets.fonts[2],
                    &self.widget_assets.font_icons,
                    self.state.interfaces().window().canvas,
                );
                self.cancel_gestures();
            }
            return Ok(true);
        }
        // Both movement and interaction retain the selected entry and its map identity.
        match entries.into_iter().next() {
            Some((_, choice, _)) => {
                self.run_world_choice(window, choice, x, y)?;
                Ok(true)
            }
            _ => Ok(false),
        }
    }
}

/// The menu entries for the offers made for the tile at `tile`; an offer that asks for Shift needs
/// it held. Each entry is a `MenuOptionClicked` payload of the plugin menu type naming the tile.
fn offered_entries(
    offers: &[(
        rustclient_plugin_api::PluginId,
        rustclient_plugin_api::MenuOffer,
    )],
    shift: bool,
    tile: (i32, i32),
) -> Vec<rustclient_plugin_api::MenuEntry> {
    offers
        .iter()
        .map(|(_, offer)| offer)
        .filter(|offer| (!offer.shift || shift) && (offer.tile.x, offer.tile.y) == tile)
        .map(|offer| rustclient_plugin_api::MenuEntry {
            option: offer.option.clone(),
            target: offer.target.clone(),
            kind: rustclient_plugin_api::MenuKind::Other(rustclient_plugin_api::PLUGIN_MENU_TYPE),
            identifier: 0,
            param0: offer.tile.x,
            param1: offer.tile.y,
            deprioritized: false,
        })
        .collect()
}

fn plain_label(label: &str) -> String {
    let mut in_tag = false;
    label
        .chars()
        .filter(|&ch| match ch {
            '<' => {
                in_tag = true;
                false
            }
            '>' => {
                in_tag = false;
                false
            }
            _ => !in_tag,
        })
        .collect()
}

/// Draw the mouse-over text and the click cross above the world view and widgets.
#[expect(
    clippy::cast_precision_loss,
    reason = "cache sprite trim offsets are bounded logical pixels"
)]
pub(super) fn project_pointer_overlays(
    frame: &mut rustclient_game_ui::UiFrame,
    assets: &rustclient_cache::TitleAssets,
    widget_assets: &rustclient_game_ui::widget_visuals::WidgetVisualAssets,
    hover: Option<&HoverText>,
    cross: &mut Option<(Instant, CrossKind, f32, f32)>,
    state: &rustclient_game_state::GameState,
) {
    use rustclient_game_ui::{Rect, UiImageId, shadowed_text};
    let clip = Rect::new(0., 0., 16384., 16384.);
    let enabled = |key| {
        state
            .interfaces()
            .settings()
            .get(&key)
            .copied()
            .unwrap_or(true)
    };
    if let (Some(hover), Some(viewport)) = (
        hover.filter(|_| enabled(rustclient_model::ClientSetting::ShowMouseOverText)),
        frame.world_viewport,
    ) {
        #[expect(clippy::cast_possible_truncation, reason = "bounded logical viewport")]
        let (x, y) = (viewport.x as i32 + 4, viewport.y as i32 + 15);
        let mut x = x;
        x += shadowed_text(frame, assets, &hover.verb, x, y, [255; 4], clip);
        if !hover.target.is_empty() {
            x += shadowed_text(frame, assets, " ", x, y, [255; 4], clip);
            shadowed_text(frame, assets, &hover.target, x, y, hover.target_color, clip);
        }
    }
    if let Some((started, kind, x, y)) = *cross {
        let frame_index = started.elapsed().as_millis() / 100;
        if frame_index >= 4 {
            *cross = None;
            return;
        }
        let base: u8 = match kind {
            CrossKind::Walk => 0,
            CrossKind::Interact => 4,
        };
        let image = UiImageId::Cross(base + u8::try_from(frame_index).unwrap_or(0));
        if enabled(rustclient_model::ClientSetting::ShowMouseCross)
            && let Some(sprite) = widget_assets.images.get(&image)
        {
            #[expect(clippy::cast_precision_loss, reason = "small sprite extents")]
            let (w, h) = (sprite.width() as f32, sprite.height() as f32);
            let offset = widget_assets
                .cross_frames
                .get(&(base + u8::try_from(frame_index).unwrap_or(0)))
                .map_or([0; 2], |metadata| metadata.offset);
            frame.image(
                image,
                Rect::new(x - 8. + offset[0] as f32, y - 8. + offset[1] as f32, w, h),
                clip,
            );
        }
    }
}

#[cfg(test)]
mod plugin_menu_tests {
    use super::offered_entries;
    use rustclient_plugin_api::{MenuKind, MenuOffer, PLUGIN_MENU_TYPE, PluginId, WorldPoint};

    fn offer(x: i32, shift: bool) -> (PluginId, MenuOffer) {
        (
            PluginId::parse("dev.rustclient.groundmarkers").unwrap(),
            MenuOffer {
                option: "Mark".into(),
                target: "Tile".into(),
                tile: WorldPoint::new(x, 3218, 0),
                shift,
            },
        )
    }

    #[test]
    fn offers_show_only_for_their_tile_and_with_shift_when_they_ask_for_it() {
        let offers = [offer(3222, true), offer(3223, true), offer(3222, false)];
        assert_eq!(offered_entries(&offers, false, (3222, 3218)).len(), 1);
        let shown = offered_entries(&offers, true, (3222, 3218));
        assert_eq!(shown.len(), 2);
        assert_eq!(shown[0].kind, MenuKind::Other(PLUGIN_MENU_TYPE));
        assert_eq!((shown[0].param0, shown[0].param1), (3222, 3218));
        assert!(offered_entries(&offers, true, (1, 1)).is_empty());
    }
}
