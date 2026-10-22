//! Native pointer normalization; gameplay validation remains in application.
use crate::{NativeApp, network};
use rustclient_game_ui::gameframe::{GameplayPointer, gameplay_pointer};
use rustclient_model::WorldTile;
use rustclient_renderer::CameraViewport;
use winit::window::Window;

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
    ) -> Result<(), String> {
        let (Some(loaded), Some(camera), Some(assets), Some(connection)) = (
            &self.scene.loaded,
            self.scene.camera,
            &self.assets,
            &self.connection,
        ) else {
            return Ok(());
        };
        if self.scene.loading || self.scene.diagnostics {
            return Ok(());
        }
        let Some(player) = self.state.entities().local_player() else {
            return Ok(());
        };
        let descriptor = loaded.asset.descriptor();
        let destination = match gameplay_pointer(&self.frame, assets, title_x, title_y) {
            GameplayPointer::Blocked => return Ok(()),
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
                let Some((x, y)) = self.cursor else {
                    return Ok(());
                };
                let size = window.inner_size();
                let scale = window.scale_factor();
                let viewport = CameraViewport::new(
                    (f64::from(size.width) / scale) as i32,
                    (f64::from(size.height) / scale) as i32,
                );
                let Some((origin, direction)) =
                    camera.pointer_ray(viewport, [(x / scale) as f32, (y / scale) as f32])
                else {
                    return Ok(());
                };
                let Some(tile) =
                    rustclient_scene::pick_terrain(&loaded.mesh, descriptor, origin, direction)
                else {
                    return Ok(());
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
        })
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
    #[expect(
        clippy::cast_possible_truncation,
        reason = "bounded logical viewport and pointer"
    )]
    fn target_at(&self, window: &Window, x: f32, y: f32) -> Option<rustclient_model::WorldTarget> {
        let loaded = self.scene.loaded.as_ref()?;
        if self.scene.loading || self.scene.diagnostics {
            return None;
        }
        if !matches!(
            gameplay_pointer(&self.frame, self.assets.as_ref()?, x, y),
            GameplayPointer::World
        ) {
            return None;
        }
        let camera = self.scene.camera?;
        let size = window.inner_size();
        let scale = window.scale_factor();
        let (px, py) = self.cursor?;
        let (origin, direction) = camera.pointer_ray(
            CameraViewport::new(
                (f64::from(size.width) / scale) as i32,
                (f64::from(size.height) / scale) as i32,
            ),
            [(px / scale) as f32, (py / scale) as f32],
        )?;
        let hit = rustclient_scene::pick_surface(&loaded.mesh.geometry, origin, direction);
        let dynamic =
            rustclient_scene::pick_surface(&self.scene.displayed_dynamic, origin, direction);
        match (hit, dynamic) {
            (Some(a), Some(b)) => {
                if a.distance <= b.distance {
                    a.target
                } else {
                    b.target
                }
            }
            (Some(a), None) | (None, Some(a)) => a.target,
            _ => None,
        }
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
    pub(super) fn world_click(
        &mut self,
        window: &Window,
        x: f32,
        y: f32,
        right: bool,
    ) -> Result<bool, String> {
        if self.context_menu.is_open() && !right {
            if let Some(action) = self.context_menu.click(x, y) {
                self.send_world_action(action)?;
            }
            return Ok(true);
        }
        self.context_menu.close();
        let Some(target) = self.target_at(window, x, y) else {
            return Ok(false);
        };
        let Some(loaded) = &self.scene.loaded else {
            return Ok(false);
        };
        let entries = rustclient_application::world_actions(
            &self.state,
            &loaded.asset,
            &self.scene.entity_catalog,
            loaded.asset.descriptor(),
            target,
        )?;
        if right {
            if let Some(assets) = &self.assets {
                self.context_menu.open(x, y, entries, assets);
                self.scene.input = rustclient_renderer::CameraInput::default();
            }
        } else if let Some((_, action)) = entries.first() {
            self.send_world_action(*action)?;
        } else {
            return Ok(false);
        }
        Ok(true)
    }
}
