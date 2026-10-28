//! Composition of shared actor anchors, camera projection and cache-font speech drawing.
use crate::scene::SceneView;
use rustclient_cache::TitleAssets;
use rustclient_game_state::GameState;
use rustclient_game_ui::{
    Rect, UiFrame,
    overhead::{self, SpeechLabel},
};
use rustclient_renderer::{CameraViewport, GameplayCameraController};
use rustclient_scene::{ActorLabelAnchor, ActorLabelId};

/// The world camera and viewport as 2D overlays see them.
pub struct WorldView {
    pub camera: GameplayCameraController,
    pub viewport: CameraViewport,
    /// Top-left of the world viewport in frame coordinates.
    pub origin: [f32; 2],
    /// The shaped world viewport in frame coordinates; overlays clip to it.
    pub clip: Rect,
}

#[expect(
    clippy::cast_possible_truncation,
    clippy::cast_precision_loss,
    reason = "bounded logical viewport coordinates match the world renderer"
)]
pub fn world_view(scene: &SceneView, frame: &UiFrame) -> Option<WorldView> {
    let (Some(mut camera), Some(bounds)) = (scene.camera, frame.world_viewport) else {
        return None;
    };
    let viewport = CameraViewport::new(bounds.width as i32, bounds.height as i32)
        .with_settings(frame.viewport_settings);
    camera.set_zoom(
        i32::from(frame.viewport_settings.zoom[0]),
        i32::from(frame.viewport_settings.zoom[1]),
    );
    let inset = viewport.inset();
    let size = viewport.size();
    let clip = Rect::new(
        bounds.x + inset[0] as f32,
        bounds.y + inset[1] as f32,
        size[0] as f32,
        size[1] as f32,
    );
    Some(WorldView {
        camera,
        viewport,
        origin: [bounds.x, bounds.y],
        clip,
    })
}

pub fn project(
    scene: &SceneView,
    state: &GameState,
    assets: &TitleAssets,
    frame: &mut UiFrame,
    visibility: &rustclient_scene::SceneVisibility,
) {
    let Some(WorldView {
        camera,
        viewport,
        origin,
        clip,
    }) = world_view(scene, frame)
    else {
        return;
    };
    let label = |anchor: &ActorLabelAnchor| {
        if scene
            .entity_geometry
            .vertices
            .get(anchor.vertices.start)
            .is_none_or(|vertex| !visibility.shows_vertex(vertex))
        {
            return None;
        }
        let (speech, player, local) = match anchor.actor {
            ActorLabelId::Player(index, instance) => {
                let actor = state.entities().players().get(&index)?;
                if scene.hidden_overlays.player(actor.index.get()) {
                    return None;
                }
                if actor.instance != instance {
                    return None;
                }
                (
                    actor.overhead.as_ref()?,
                    true,
                    Some(index) == state.entities().local_player_index(),
                )
            }
            ActorLabelId::Npc(index, instance) => {
                let actor = state.entities().npcs().get(&index)?;
                if scene.hidden_overlays.npc(actor.index.get()) {
                    return None;
                }
                if actor.instance != instance {
                    return None;
                }
                (actor.overhead.as_ref()?, false, false)
            }
        };
        let mut point = camera.project_point(viewport, anchor.position)?;
        point[0] += origin[0];
        point[1] += origin[1];
        Some(SpeechLabel {
            speech,
            point,
            player,
            local,
        })
    };
    // Stock draws the local player's overhead after other actors for overlap placement.
    let labels = scene
        .entity_geometry
        .actor_labels
        .iter()
        .filter_map(label)
        .filter(|l| !l.local)
        .chain(
            scene
                .entity_geometry
                .actor_labels
                .iter()
                .filter_map(label)
                .filter(|l| l.local),
        );
    overhead::project(
        frame,
        assets,
        labels,
        state.scenery().cycle().get(),
        state.chat().filters()[0],
        clip,
    );
}
