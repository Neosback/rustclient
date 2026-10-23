//! Native pointer normalization; gameplay validation remains in application.
use crate::{NativeApp, network};
use rustclient_game_ui::gameframe::{GameplayPointer, gameplay_pointer};
use rustclient_model::{WorldAction, WorldTarget, WorldTile};
use rustclient_renderer::CameraViewport;
use std::time::Instant;
use winit::window::Window;

/// One entry of the world right-click menu.
#[derive(Debug, Clone, Copy)]
pub(super) enum WorldChoice {
    Action(WorldAction),
    /// Walk to the tile under the pointer position the menu was opened at.
    Walk {
        x: f32,
        y: f32,
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
        reason = "bounded logical pointer/window and map coordinates"
    )]
    pub(super) fn walk_click(
        &self,
        window: &Window,
        title_x: f32,
        title_y: f32,
    ) -> Result<bool, String> {
        let (Some(loaded), Some(camera), Some(connection)) =
            (&self.scene.loaded, self.scene.camera, &self.connection)
        else {
            return Ok(false);
        };
        if self.scene.loading || self.scene.diagnostics {
            return Ok(false);
        }
        let Some(player) = self.state.entities().local_player() else {
            return Ok(false);
        };
        let descriptor = loaded.asset.descriptor();
        let destination = match gameplay_pointer(
            &self.frame,
            self.minimap_mask.as_ref().map(|(_, mask)| mask),
            title_x,
            title_y,
        ) {
            GameplayPointer::Blocked => return Ok(false),
            GameplayPointer::Minimap { dx, dy } => {
                let yaw = f32::from(camera.yaw()) * std::f32::consts::TAU / 2048.;
                let (x, y) = rustclient_scene::minimap::screen_to_tile(
                    dx,
                    dy,
                    (
                        f64::from(player.tile.x) + 0.5,
                        f64::from(player.tile.y) + 0.5,
                    ),
                    yaw,
                );
                WorldTile::new(x.floor() as i32, y.floor() as i32, player.tile.plane)
            }
            GameplayPointer::World => {
                let Some((origin, direction)) = self.world_ray(window, title_x, title_y) else {
                    return Ok(false);
                };
                let Some(tile) =
                    rustclient_scene::pick_terrain(&loaded.mesh, descriptor, origin, direction)
                else {
                    return Ok(false);
                };
                tile
            }
        };
        let intent = rustclient_application::validate_walk(
            &self.state,
            &loaded.asset,
            descriptor,
            destination,
            self.modifiers.control_key(),
        )?;
        connection.send(network::Outbound {
            map_epoch: self.map_epoch,
            interface_epoch: None,
            intent,
        })?;
        Ok(true)
    }

    pub(super) fn acknowledge_map(&self) {
        if let Some(connection) = &self.connection
            && let Err(e) = connection.send(network::Outbound {
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
        let hit = rustclient_scene::pick_surface(&loaded.mesh.geometry, origin, direction);
        // Static scenery wins exact ties, matching the previous two-way comparison.
        let overlays = [&loaded.mesh.dynamic, &self.scene.entity_geometry]
            .into_iter()
            .filter_map(|geometry| rustclient_scene::pick_surface(geometry, origin, direction));
        hit.into_iter()
            .chain(overlays)
            .min_by(|a, b| a.distance.total_cmp(&b.distance))
            .and_then(|hit| hit.target)
    }
    #[expect(
        clippy::cast_possible_truncation,
        reason = "bounded logical viewport coordinates"
    )]
    fn world_ray(&self, window: &Window, x: f32, y: f32) -> Option<([f32; 3], [f32; 3])> {
        let mut camera = self.scene.camera?;
        let settings = self.state.interfaces().viewport();
        camera.set_zoom(i32::from(settings.zoom[0]), i32::from(settings.zoom[1]));
        let bounds = self.frame.world_viewport.unwrap_or_else(|| {
            let size = window.inner_size();
            rustclient_game_ui::Rect::new(
                0.,
                0.,
                (f64::from(size.width) / window.scale_factor()) as f32,
                (f64::from(size.height) / window.scale_factor()) as f32,
            )
        });
        if !bounds.contains(x, y) {
            return None;
        }
        camera.pointer_ray(
            CameraViewport::new(bounds.width as i32, bounds.height as i32).with_settings(settings),
            [x - bounds.x, y - bounds.y],
        )
    }
    pub(super) fn send_world_action(
        &self,
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
        connection.send(network::Outbound {
            map_epoch: self.map_epoch,
            interface_epoch: None,
            intent,
        })
    }
    /// Start the click-cross animation at a UI position.
    pub(super) fn show_cross(&mut self, kind: CrossKind, x: f32, y: f32) {
        self.click_cross = Some((Instant::now(), kind, x, y));
    }
    /// Carry out a world menu entry chosen by a click or key press at `(x, y)`.
    pub(super) fn run_world_choice(
        &mut self,
        window: &Window,
        choice: WorldChoice,
        x: f32,
        y: f32,
    ) -> Result<(), String> {
        match choice {
            WorldChoice::Action(action) => {
                self.send_world_action(action)?;
                self.show_cross(CrossKind::Interact, x, y);
            }
            WorldChoice::Walk { x: wx, y: wy } => {
                if self.walk_click(window, wx, wy)? {
                    self.show_cross(CrossKind::Walk, x, y);
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
        if let Some(target) = self.target_at(window, x, y)
            && let Some(loaded) = &self.scene.loaded
        {
            entries.extend(
                rustclient_application::world_actions(
                    &self.state,
                    &loaded.asset,
                    &self.scene.entity_catalog,
                    loaded.asset.descriptor(),
                    target,
                )?
                .into_iter()
                .map(|(label, action)| (label, WorldChoice::Action(action), Some(target))),
            );
        }
        if self.pointer_over_world(x, y) && self.scene.loaded.is_some() {
            entries.push(("Walk here".to_owned(), WorldChoice::Walk { x, y }, None));
        }
        Ok(entries)
    }
    /// Refresh the mouse-over text after the pointer moved.
    pub(super) fn update_hover(&mut self, window: &Window) {
        if !std::mem::take(&mut self.hover_dirty) {
            return;
        }
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
                    &scripts.interfaces,
                    node.address,
                ) else {
                    continue;
                };
                if widget.content == rustclient_model::WidgetContent::WorldViewport {
                    break;
                }
                if let Some((label, _)) = rustclient_application::widget_actions(
                    &self.state,
                    &scripts.interfaces,
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
        let entries = self.world_entries(window, x, y)?;
        if right {
            if entries.is_empty() {
                return Ok(false);
            }
            if let Some(assets) = &self.assets {
                self.context_menu.open(
                    x,
                    y,
                    entries
                        .into_iter()
                        .map(|(label, choice, _)| (label, choice))
                        .collect(),
                    assets,
                    self.state.interfaces().window().canvas,
                );
                self.scene.input = rustclient_renderer::CameraInput::default();
            }
            return Ok(true);
        }
        // The default action is the first entry; "Walk here" is left to the movement click.
        match entries.into_iter().next() {
            Some((_, choice @ WorldChoice::Action(_), _)) => {
                self.run_world_choice(window, choice, x, y)?;
                Ok(true)
            }
            _ => Ok(false),
        }
    }
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
pub(super) fn project_pointer_overlays(
    frame: &mut rustclient_game_ui::UiFrame,
    assets: &rustclient_cache::TitleAssets,
    widget_assets: &rustclient_game_ui::widget_visuals::WidgetVisualAssets,
    hover: Option<&HoverText>,
    cross: &mut Option<(Instant, CrossKind, f32, f32)>,
) {
    use rustclient_game_ui::{Rect, UiImageId, shadowed_text};
    let clip = Rect::new(0., 0., 16384., 16384.);
    if let (Some(hover), Some(viewport)) = (hover, frame.world_viewport) {
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
        if let Some(sprite) = widget_assets.images.get(&image) {
            #[expect(clippy::cast_precision_loss, reason = "small sprite extents")]
            let (w, h) = (sprite.width() as f32, sprite.height() as f32);
            frame.image(image, Rect::new(x - w / 2., y - h / 2., w, h), clip);
        }
    }
}
