//! Composition of shared actor anchors, camera projection and cache-font speech drawing.
use crate::scene::SceneView;
use rustclient_cache::TitleAssets;
use rustclient_game_state::GameState;
use rustclient_game_ui::{
    Rect, UiFrame,
    overhead::{self, SpeechLabel},
};
use rustclient_renderer::CameraViewport;
use rustclient_scene::{ActorLabelAnchor, ActorLabelId};

#[expect(
    clippy::cast_possible_truncation,
    clippy::cast_precision_loss,
    reason = "bounded logical viewport coordinates match the world renderer"
)]
pub fn project(
    scene: &SceneView,
    state: &GameState,
    assets: &TitleAssets,
    frame: &mut UiFrame,
    top_level: u8,
) {
    let (Some(mut camera), Some(bounds)) = (scene.camera, frame.world_viewport) else {
        return;
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
    let label = |anchor: &ActorLabelAnchor| {
        if anchor.level > top_level {
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
        point[0] += bounds.x;
        point[1] += bounds.y;
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
