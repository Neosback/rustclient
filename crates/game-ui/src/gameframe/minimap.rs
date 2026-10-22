//! Minimap assembly and status orbs layout for the gameframe HUD.

use crate::{Rect, UiFrame, UiImageId};
use rustclient_cache::TitleAssets;
use rustclient_game_state::GameState;

pub fn project_minimap(
    _assets: &TitleAssets,
    _state: &GameState,
    view_w: f32,
    _view_h: f32,
    frame: &mut UiFrame,
) {
    let clip = Rect::new(0., 0., view_w, 10000.);
    let frame_w = 210.0;
    let frame_h = 170.0;
    let map_x = view_w - frame_w - 10.0;
    let map_y = 10.0;

    // 1. Dynamic minimap circle inside the frame hole
    let circle_w = 156.0;
    let circle_h = 156.0;
    let circle_x = map_x + 24.0;
    let circle_y = map_y + 8.0;
    frame.image(
        UiImageId::Minimap,
        Rect::new(circle_x, circle_y, circle_w, circle_h),
        clip,
    );

    // 2. Minimap frame ring (sprite 1177)
    frame.image(
        UiImageId::Sprite(1177),
        Rect::new(map_x, map_y, frame_w, frame_h),
        clip,
    );

    // 3. Rotating compass (sprite 169 masked by 1179)
    let compass_size = 33.0;
    let compass_x = map_x + 5.0;
    let compass_y = map_y + 5.0;
    frame.image(
        UiImageId::Compass,
        Rect::new(compass_x, compass_y, compass_size, compass_size),
        clip,
    );

    // 4. Status Orbs down the left side
    let orb_radius = 27.0;
    let orb_spacing = 38.0;
    let orb_base_x = map_x - 24.0;
    let orb_base_y = map_y + 36.0;

    // Hitpoints Orb (1060 frame, 1067 heart icon)
    frame.image(
        UiImageId::Sprite(1060),
        Rect::new(orb_base_x, orb_base_y, orb_radius, orb_radius),
        clip,
    );
    frame.image(
        UiImageId::Sprite(1067),
        Rect::new(orb_base_x + 6.0, orb_base_y + 6.0, 15.0, 15.0),
        clip,
    );

    // Prayer Orb (1063 frame, 1068 prayer icon)
    frame.image(
        UiImageId::Sprite(1063),
        Rect::new(orb_base_x, orb_base_y + orb_spacing, orb_radius, orb_radius),
        clip,
    );
    frame.image(
        UiImageId::Sprite(1068),
        Rect::new(orb_base_x + 6.0, orb_base_y + orb_spacing + 6.0, 15.0, 15.0),
        clip,
    );

    // Run Energy Orb (1064 frame, 1070 running icon)
    frame.image(
        UiImageId::Sprite(1064),
        Rect::new(
            orb_base_x + 8.0,
            orb_base_y + orb_spacing * 2.0,
            orb_radius,
            orb_radius,
        ),
        clip,
    );
    frame.image(
        UiImageId::Sprite(1070),
        Rect::new(
            orb_base_x + 14.0,
            orb_base_y + orb_spacing * 2.0 + 6.0,
            15.0,
            15.0,
        ),
        clip,
    );

    // Special Attack Orb (1607 frame, 1610 spec icon)
    frame.image(
        UiImageId::Sprite(1607),
        Rect::new(
            orb_base_x + 24.0,
            orb_base_y + orb_spacing * 3.0,
            orb_radius,
            orb_radius,
        ),
        clip,
    );
    frame.image(
        UiImageId::Sprite(1610),
        Rect::new(
            orb_base_x + 30.0,
            orb_base_y + orb_spacing * 3.0 + 6.0,
            15.0,
            15.0,
        ),
        clip,
    );
}
