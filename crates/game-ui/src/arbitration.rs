//! Input arbitration between an open menu, shell chrome, widgets and the world (SPEC-0021).
//!
//! Pure decisions over facts the platform adapter gathers; no platform event types. Order of
//! authority for a pointer press: shell panel (a separate surface) > open menu > shell chrome on
//! the canvas > everything underneath. A layer that takes a press ends dispatch.

/// Facts about one pointer press.
#[derive(Debug, Clone, Copy, Default, PartialEq, Eq)]
pub struct PressFacts {
    /// A world or widget menu is open.
    pub menu_open: bool,
    /// The press landed on the shell panel or rail (outside the game canvas).
    pub over_shell_panel: bool,
    /// The press landed on the sidebar toggle button drawn on the canvas, and that button is
    /// active (see `toggle_active`).
    pub over_active_toggle: bool,
}

/// Who receives a pointer press.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum PressRoute {
    /// The shell panel; any open canvas menu closes as a side effect.
    ShellPanel,
    /// The open menu selects the row under the pointer or closes.
    Menu,
    /// The sidebar toggle button.
    ShellToggle,
    /// Widgets and the world, in their own order.
    Underlying,
}

/// Route a press. The toggle button never outranks an open menu.
#[must_use]
pub const fn route_press(facts: PressFacts) -> PressRoute {
    if facts.over_shell_panel {
        PressRoute::ShellPanel
    } else if facts.menu_open {
        PressRoute::Menu
    } else if facts.over_active_toggle {
        PressRoute::ShellToggle
    } else {
        PressRoute::Underlying
    }
}

/// Whether the sidebar toggle button may take a press: it must be painted and no menu may sit
/// above it.
#[must_use]
pub const fn toggle_active(painted: bool, menu_open: bool) -> bool {
    painted && !menu_open
}

/// Who receives a key press.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum KeyRoute {
    Menu,
    Shell,
    Game,
}

/// Route a key. Menu navigation keys (Escape, Enter, arrows) reach an open menu whatever the shell
/// focus is. Other keys go to the shell only when it wants them.
#[must_use]
pub const fn route_key(menu_open: bool, menu_key: bool, shell_wants_keys: bool) -> KeyRoute {
    if menu_open && menu_key {
        KeyRoute::Menu
    } else if shell_wants_keys {
        KeyRoute::Shell
    } else {
        KeyRoute::Game
    }
}

/// Gestures that must not act while a menu is open: hover text, widget hold/drag, wheel zoom or
/// scroll and middle-button camera drag.
#[must_use]
pub const fn suppress_gestures(menu_open: bool) -> bool {
    menu_open
}

#[cfg(test)]
mod tests {
    use super::*;

    const OPEN_OVER_BUTTON: PressFacts = PressFacts {
        menu_open: true,
        over_shell_panel: false,
        over_active_toggle: true,
    };

    #[test]
    fn an_open_menu_outranks_the_toggle_button_it_overlaps() {
        assert_eq!(route_press(OPEN_OVER_BUTTON), PressRoute::Menu);
        assert_eq!(
            route_press(PressFacts {
                menu_open: false,
                ..OPEN_OVER_BUTTON
            }),
            PressRoute::ShellToggle
        );
    }

    #[test]
    fn the_shell_panel_is_a_separate_surface_and_always_takes_its_press() {
        assert_eq!(
            route_press(PressFacts {
                over_shell_panel: true,
                ..OPEN_OVER_BUTTON
            }),
            PressRoute::ShellPanel
        );
    }

    #[test]
    fn an_inactive_toggle_passes_the_press_underneath() {
        assert_eq!(
            route_press(PressFacts {
                menu_open: false,
                over_active_toggle: false,
                ..OPEN_OVER_BUTTON
            }),
            PressRoute::Underlying
        );
        assert!(!toggle_active(false, false), "not painted");
        assert!(!toggle_active(true, true), "menu above");
        assert!(toggle_active(true, false));
    }

    #[test]
    fn menu_keys_reach_an_open_menu_even_while_the_shell_has_focus() {
        assert_eq!(route_key(true, true, true), KeyRoute::Menu);
        assert_eq!(route_key(true, false, true), KeyRoute::Shell);
        assert_eq!(route_key(false, true, true), KeyRoute::Shell);
        assert_eq!(route_key(false, true, false), KeyRoute::Game);
    }

    #[test]
    fn gestures_are_suppressed_only_while_a_menu_is_open() {
        assert!(suppress_gestures(true));
        assert!(!suppress_gestures(false));
    }
}
