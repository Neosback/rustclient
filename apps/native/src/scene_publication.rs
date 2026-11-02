//! Publish only when the renderer has installed the matching structural resource group.
use crate::{NativeApp, scene_workers::DynamicProjection};
use rustclient_scene::SceneryInputs;
impl NativeApp {
    pub(super) fn upload_scene_projection(
        &mut self,
        scene: crate::scene::PreparedScene,
        update: rustclient_renderer::WorldUpdate,
    ) -> Result<rustclient_application::LoadedScene, rustclient_renderer::RenderError> {
        let mut scene = scene.try_map_mesh(|prepared| match &mut self.renderer {
            Some(renderer) => renderer.set_prepared_world(prepared, update),
            None => Ok(prepared.into_mesh()),
        })?;
        if self.renderer.is_some() {
            scene.mesh.geometry.release_triangles();
        }
        self.preserve_scene_pose(&mut scene)?;
        Ok(scene)
    }

    pub(super) fn accept_dynamic(&mut self, inputs: SceneryInputs, projection: DynamicProjection) {
        self.performance.location_event("prepared",inputs.generation,inputs.cycle.get(),0,||serde_json::json!({"queue_ns":u64::try_from(projection.queue_wait.as_nanos()).unwrap_or(u64::MAX),"worker_ns":u64::try_from(projection.preparation.as_nanos()).unwrap_or(u64::MAX),"rebuilt_zones":projection.rebuilt_zones}));
        let Some(loaded) = &self.scene.loaded else {
            return;
        };
        if let Some(renderer) = &mut self.renderer
            && self.scene.awaiting_world.is_none()
        {
            if let Err(error) =
                renderer.set_prepared_locations(loaded.mesh.asset, projection.prepared)
            {
                eprintln!("location preparation publication: {error}");
                return;
            }
            if let Err(error) = renderer.set_world_dynamic(loaded.mesh.asset, &projection.geometry)
            {
                eprintln!("dynamic GPU staging: {error}");
                return;
            }
        }
        self.scene.pending_dynamic = Some((
            inputs,
            projection.geometry,
            projection.minimap,
            projection.plane,
        ));
        if self.renderer.is_none() {
            self.publish_dynamic_if_ready();
        }
        if let Some(window) = &self.window {
            window.request_redraw();
        }
    }
    pub(super) fn publish_dynamic_if_ready(&mut self) {
        let Some((inputs, geometry, _, _)) = &self.scene.pending_dynamic else {
            return;
        };
        let Some(loaded) = &self.scene.loaded else {
            return;
        };
        if !inputs.matches_state(self.state.scenery(), &loaded.asset) {
            self.scene.pending_dynamic = None;
            return;
        }
        if self.scene.awaiting_world.is_some() {
            return;
        }
        if let Some(renderer) = &mut self.renderer
            && (!renderer.locations_ready(&geometry.location_zones)
                || !renderer.commit_locations(&geometry.location_zones))
        {
            return;
        }
        let Some((inputs, geometry, minimap, plane)) = self.scene.pending_dynamic.take() else {
            return;
        };
        let publication = self.renderer.as_ref().map_or(
            0,
            rustclient_renderer::SurfaceRenderer::location_publication,
        );
        self.performance.location_event(
            "published",
            inputs.generation,
            inputs.cycle.get(),
            publication,
            || serde_json::json!({"scope":"installed for the next frame; not physical scanout"}),
        );
        if self.minimap_plane == Some(plane)
            && self
                .minimap
                .as_ref()
                .is_none_or(|old| !std::sync::Arc::ptr_eq(old, &minimap))
        {
            self.minimap = Some(minimap);
            self.minimap_generation = self.minimap_generation.wrapping_add(1).max(1);
        }
        if let Some(loaded) = &mut self.scene.loaded {
            loaded.mesh.dynamic = geometry;
            loaded
                .picking
                .set_hidden(&loaded.mesh.geometry, &loaded.mesh.dynamic.hidden_static);
            loaded.projection_inputs.cycle = inputs.cycle;
            loaded.projection_inputs.interpolation = inputs.interpolation;
            self.scene.dynamic_cycle = inputs.cycle;
        }
    }
}
