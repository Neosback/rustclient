//! Platform key mapping precedes menu dispatch, including key releases.
use rustclient_renderer::CameraInput;
use winit::keyboard::{Key, NamedKey};

/// Update held camera input. Returns true when an open menu should leave this key to the camera.
pub fn route(input: &mut CameraInput, key: &Key, held: bool, menu_open: bool) -> bool {
    let field = match key {
        Key::Named(NamedKey::ArrowLeft) => &mut input.left,
        Key::Named(NamedKey::ArrowRight) => &mut input.right,
        Key::Named(NamedKey::ArrowUp) => &mut input.up,
        Key::Named(NamedKey::ArrowDown) => &mut input.down,
        _ => return false,
    };
    *field = held;
    menu_open
}
#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn opening_a_menu_cannot_capture_held_camera_key_releases() {
        let mut input = CameraInput::default();
        let right = Key::Named(NamedKey::ArrowRight);
        assert!(!route(&mut input, &right, true, false));
        assert!(input.right);
        assert!(route(&mut input, &right, false, true));
        assert!(!input.right);
        let up = Key::Named(NamedKey::ArrowUp);
        assert!(route(&mut input, &up, true, true));
        assert!(input.up);
        assert!(route(&mut input, &up, false, true));
        assert!(!input.up);
        assert!(!route(&mut input, &Key::Named(NamedKey::Enter), true, true));
        assert!(!route(
            &mut input,
            &Key::Named(NamedKey::Escape),
            true,
            true
        ));
    }
}
