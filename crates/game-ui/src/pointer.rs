//! Reference widget drag geometry shared by platform input adapters.
use crate::widgets::{PlacedWidget, WidgetBounds};

/// Temporary pointer projection; never commits a scripted widget position.
#[derive(Debug, Clone, Copy)]
pub struct DraggedWidget {
    pub address: rustclient_model::WidgetAddress,
    pub origin: [i32; 2],
}

/// The single topmost interactive widget that owns a primary press.
#[expect(
    clippy::struct_excessive_bools,
    reason = "independent widget listener capabilities"
)]
#[derive(Debug, Clone, Copy)]
pub struct PressTarget {
    pub node: PlacedWidget,
    pub click: bool,
    pub hold: bool,
    pub drag: bool,
    pub actionable: bool,
}
/// Input and visual paint order agree: a knob captures its press before the
/// track beneath it, and a dropdown row captures before its dismiss layer.
pub fn primary_press<'a>(
    nodes: impl IntoIterator<Item = (PlacedWidget, &'a rustclient_model::WidgetDefinition)>,
    actionable: impl Fn(rustclient_model::WidgetAddress) -> bool,
) -> Option<PressTarget> {
    use rustclient_model::WidgetListener;
    for (node, widget) in nodes {
        let click = widget.listeners.contains_key(&WidgetListener::Click);
        let hold = [
            WidgetListener::Hold,
            WidgetListener::ClickRepeat,
            WidgetListener::Release,
        ]
        .iter()
        .any(|kind| widget.listeners.contains_key(kind));
        let drag = widget.listeners.contains_key(&WidgetListener::Drag);
        let has_action = actionable(node.address);
        if click || hold || drag || has_action {
            return Some(PressTarget {
                node,
                click,
                hold,
                drag,
                actionable: has_action,
            });
        }
    }
    None
}

/// Resolve the authored drag coordinate space. Server permissions select an
/// ancestor before a script's explicit drag parent. No parent means a free drag.
/// # Errors
/// Rejects missing definitions or invalid ancestry.
pub fn drag_parent(
    state: &rustclient_game_state::InterfaceState,
    assets: &std::collections::BTreeMap<
        rustclient_model::InterfaceId,
        rustclient_cache::widget::InterfaceDefinition,
    >,
    address: rustclient_model::WidgetAddress,
) -> Result<Option<rustclient_model::WidgetAddress>, crate::widgets::WidgetLayoutError> {
    use crate::widgets::{parent_address, resolve_widget};
    let widget = resolve_widget(state, assets, address)?;
    let depth = state
        .permissions(address.widget, address.child.map_or(-1, u32::cast_signed))
        .unwrap_or(widget.permissions)
        .drag_depth;
    if depth > 0 {
        let mut parent = address;
        for _ in 0..depth {
            let current = resolve_widget(state, assets, parent)?;
            let Some(next) = parent_address(state, parent, &current) else {
                return Ok(None);
            };
            parent = next;
        }
        return Ok(Some(parent));
    }
    Ok(state
        .runtime()
        .get(&address)
        .and_then(|runtime| runtime.drag)
        .and_then(|drag| drag.parent))
}

/// Clamped widget origin in the drag parent's scroll coordinates. The pointer's
/// original offset inside the widget stays fixed throughout a drag.
#[must_use]
pub fn drag_position(
    widget: WidgetBounds,
    parent: PlacedWidget,
    scroll: [i32; 2],
    grab: [i32; 2],
    pointer: [i32; 2],
) -> [i32; 2] {
    let origin = [parent.bounds.x, parent.bounds.y];
    let maximum = [
        (parent.bounds.width - widget.width).max(0),
        (parent.bounds.height - widget.height).max(0),
    ];
    std::array::from_fn(|axis| {
        (pointer[axis] - grab[axis] - origin[axis]).clamp(0, maximum[axis]) + scroll[axis]
    })
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_model::{InterfaceId, WidgetAddress, WidgetId};

    #[test]
    fn slider_keeps_grab_offset_clamps_to_track_and_includes_parent_scroll() {
        let id = WidgetId::new(InterfaceId::new(1), 0);
        let parent = PlacedWidget {
            id,
            address: WidgetAddress::component(id),
            bounds: WidgetBounds {
                x: 100,
                y: 200,
                width: 120,
                height: 20,
            },
            clip: WidgetBounds {
                x: 100,
                y: 200,
                width: 120,
                height: 20,
            },
            scroll_y: 0,
        };
        let knob = WidgetBounds {
            x: 130,
            y: 200,
            width: 10,
            height: 20,
        };
        assert_eq!(
            drag_position(knob, parent, [0, 0], [4, 5], [154, 205]),
            [50, 0]
        );
        assert_eq!(
            drag_position(knob, parent, [7, 9], [4, 5], [999, 999]),
            [117, 9]
        );
        assert_eq!(drag_position(knob, parent, [7, 9], [4, 5], [0, 0]), [7, 9]);
    }
    #[test]
    fn a_knob_or_dropdown_row_owns_the_press_before_the_background_control() {
        let id = WidgetId::new(InterfaceId::new(1), 0);
        let mut background = rustclient_cache::osrs240::widgets::decode(
            include_bytes!("../../cache/tests/fixtures/widgets/container.bin"),
            id,
            rustclient_cache::Source::group(
                rustclient_cache::IndexId(3),
                rustclient_cache::GroupId(1),
            ),
        )
        .unwrap();
        background.listeners.clear();
        background
            .listeners
            .insert(rustclient_model::WidgetListener::Click, vec![]);
        let mut knob = background.clone();
        knob.id = WidgetId::new(id.interface, 1);
        knob.listeners.clear();
        knob.listeners
            .insert(rustclient_model::WidgetListener::Drag, vec![]);
        let node = |id| PlacedWidget {
            id,
            address: WidgetAddress::component(id),
            bounds: WidgetBounds {
                x: 0,
                y: 0,
                width: 16,
                height: 16,
            },
            clip: WidgetBounds {
                x: 0,
                y: 0,
                width: 16,
                height: 16,
            },
            scroll_y: 0,
        };
        let target = primary_press(
            [(node(knob.id), &knob), (node(background.id), &background)],
            |_| false,
        )
        .unwrap();
        assert_eq!(target.node.id, knob.id);
        assert!(target.drag);
        assert!(!target.click);
        knob.listeners.clear();
        knob.listeners
            .insert(rustclient_model::WidgetListener::Click, vec![]);
        let target = primary_press(
            [(node(knob.id), &knob), (node(background.id), &background)],
            |_| false,
        )
        .unwrap();
        assert_eq!(target.node.id, knob.id);
        assert!(target.click);
    }
}
