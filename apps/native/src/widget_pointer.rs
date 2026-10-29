//! Pointer-driven widget listeners: click, hold, release, drag and hover.
//!
//! Scripts attach these listeners to widgets (sliders, dropdowns, buttons); the native client
//! only reports where the pointer is and what it is doing, and the scripts decide the rest.
use crate::{NativeApp, network};
use rustclient_application::scripts::ListenerEvent;
use rustclient_game_ui::widgets::{PlacedWidget, resolve_widget};
use rustclient_model::{
    WidgetAddress, WidgetBody, WidgetContent, WidgetDefinition, WidgetListener,
};

const HOVER_LISTENERS: [WidgetListener; 3] = [
    WidgetListener::MouseOver,
    WidgetListener::MouseRepeat,
    WidgetListener::MouseLeave,
];

#[derive(Debug, Clone, Copy)]
struct Held {
    epoch: u64,
    hold: Option<WidgetAddress>,
    drag: Option<WidgetAddress>,
    dragging: bool,
    origin: (f32, f32),
    grab: [i32; 2],
    cycles: u32,
}

/// Which widgets the pointer is pressing, dragging and hovering.
#[derive(Debug, Default)]
pub(super) struct WidgetPointer {
    held: Option<Held>,
    hovered: Vec<WidgetAddress>,
}

#[expect(
    clippy::cast_possible_truncation,
    reason = "bounded logical pointer coordinates"
)]
fn relative(node: &PlacedWidget, x: f32, y: f32) -> [i32; 2] {
    [x as i32 - node.bounds.x, y as i32 - node.bounds.y]
}

impl NativeApp {
    pub(super) fn flush_script_intents(&mut self) {
        let intents = self.scripts.as_mut().map_or_else(
            Vec::new,
            rustclient_application::scripts::ClientScripts::take_intents,
        );
        self.send_script_intents(intents);
    }
    pub(super) fn game_key(
        &mut self,
        key: Option<rustclient_model::WidgetKey>,
        character: Option<char>,
        pressed: bool,
    ) {
        let Some(scripts) = &mut self.scripts else {
            return;
        };
        match scripts.keyboard(&mut self.state, key, character, pressed) {
            Ok(outcome) => {
                for error in outcome.errors {
                    eprintln!("widget key: {error}");
                }
                self.send_script_intents(outcome.intents);
                self.request_widget_assets();
            }
            Err(error) => eprintln!("widget key: {error}"),
        }
    }
    /// Widgets under the pointer, topmost first, down to and including the first one that
    /// blocks clicks from reaching those behind it.
    fn widgets_under(&self, x: f32, y: f32) -> Vec<(PlacedWidget, WidgetDefinition)> {
        let Some(scripts) = &self.scripts else {
            return Vec::new();
        };
        let mut out = Vec::new();
        for node in self.widget_tree.nodes.iter().rev() {
            if !node.bounds.rect().contains(x, y) || !node.clip.rect().contains(x, y) {
                continue;
            }
            let Ok(widget) =
                resolve_widget(self.state.interfaces(), &scripts.interfaces, node.address)
            else {
                continue;
            };
            let blocks = matches!(
                widget.body,
                WidgetBody::Container {
                    no_click_through: true,
                    ..
                }
            ) || widget.content == WidgetContent::WorldViewport;
            out.push((*node, widget));
            if blocks {
                break;
            }
        }
        out
    }
    fn placed(&self, address: WidgetAddress) -> Option<PlacedWidget> {
        self.widget_tree
            .nodes
            .iter()
            .find(|node| node.address == address)
            .copied()
    }
    /// The widget a drag listener measures the pointer against: the container that holds it.
    fn drag_parent(&self, address: WidgetAddress) -> Option<PlacedWidget> {
        let scripts = self.scripts.as_ref()?;
        let parent = rustclient_game_ui::pointer::drag_parent(
            self.state.interfaces(),
            &scripts.interfaces,
            address,
        )
        .ok()??;
        self.placed(parent)
    }
    fn drag_mouse(&self, held: Held, address: WidgetAddress, x: f32, y: f32) -> Option<[i32; 2]> {
        let node = self.placed(address)?;
        let Some(parent) = self.drag_parent(address) else {
            #[expect(
                clippy::cast_possible_truncation,
                reason = "bounded logical pointer coordinates"
            )]
            return Some([x as i32 - held.grab[0], y as i32 - held.grab[1]]);
        };
        let scroll = self
            .state
            .interfaces()
            .runtime()
            .get(&parent.address)
            .and_then(|r| r.scroll)
            .unwrap_or([0, parent.scroll_y]);
        let local = relative(&parent, x, y);
        Some(rustclient_game_ui::pointer::drag_position(
            node.bounds,
            parent,
            scroll.map(|n| i32::try_from(n).unwrap_or(i32::MAX)),
            held.grab,
            [local[0] + parent.bounds.x, local[1] + parent.bounds.y],
        ))
    }
    pub(super) fn dragged_widget(&self) -> Option<rustclient_game_ui::pointer::DraggedWidget> {
        let held = self
            .widget_pointer
            .held
            .filter(|held| held.dragging && held.epoch == self.state.interfaces().epoch())?;
        let address = held.drag?;
        let window = self.window.as_ref()?;
        let (x, y) = self.cursor?;
        let (x, y) = self.ui_pointer(window, x, y);
        let mouse = self.drag_mouse(held, address, x, y)?;
        let Some(parent) = self.drag_parent(address) else {
            return Some(rustclient_game_ui::pointer::DraggedWidget {
                address,
                origin: mouse,
            });
        };
        let scroll = self
            .state
            .interfaces()
            .runtime()
            .get(&parent.address)
            .and_then(|r| r.scroll)
            .unwrap_or([0, parent.scroll_y]);
        Some(rustclient_game_ui::pointer::DraggedWidget {
            address,
            origin: [
                parent.bounds.x + mouse[0] - i32::try_from(scroll[0]).ok()?,
                parent.bounds.y + mouse[1] - i32::try_from(scroll[1]).ok()?,
            ],
        })
    }
    fn fire_listener(
        &mut self,
        address: WidgetAddress,
        kind: WidgetListener,
        mouse: [i32; 2],
        target: Option<WidgetAddress>,
    ) {
        let Some(scripts) = &mut self.scripts else {
            return;
        };
        let event = ListenerEvent {
            mouse,
            wheel: None,
            target,
            ..ListenerEvent::default()
        };
        match scripts.dispatch_listener(&mut self.state, address, kind, event) {
            Ok(Some(outcome)) => {
                for error in outcome.errors {
                    if self.reported_script_errors.len() < 256
                        && self.reported_script_errors.insert(error.clone())
                    {
                        eprintln!("widget listener {kind:?}: {error}");
                    }
                }
                self.send_script_intents(outcome.intents);
                self.request_widget_assets();
                if let Some(window) = &self.window {
                    window.request_redraw();
                }
            }
            Ok(None) => {}
            Err(error) => eprintln!("widget listener {kind:?}: {error}"),
        }
    }
    pub(super) fn send_script_intents(&self, intents: Vec<rustclient_protocol::ClientIntent>) {
        let Some(connection) = &self.connection else {
            return;
        };
        for intent in intents {
            if let Err(error) = connection.send(network::Outbound {
                world_action: None,
                map_epoch: self.map_epoch,
                interface_epoch: Some(self.interface_epoch),
                intent,
            }) {
                eprintln!("script intent: {error}");
            }
        }
    }
    /// A left press: run click listeners and start tracking hold and drag targets.
    pub(super) fn widget_pointer_press(&mut self, x: f32, y: f32) -> bool {
        self.widget_pointer.held = None;
        if self.widget_menu.is_open() || self.context_menu.is_open() {
            return false;
        }
        self.ensure_widget_tree();
        let under = self.widgets_under(x, y);
        if std::env::var_os("RUSTCLIENT_POINTER_DIAGNOSTICS").is_some() {
            for (node, widget) in &under {
                eprintln!(
                    "pointer press {x},{y}: {:?} {:?} {:?} drag {:?}",
                    node.address,
                    node.bounds,
                    widget.listeners.keys(),
                    self.state
                        .interfaces()
                        .runtime()
                        .get(&node.address)
                        .and_then(|r| r.drag)
                );
            }
        }
        let mut held = Held {
            epoch: self.state.interfaces().epoch(),
            hold: None,
            drag: None,
            dragging: false,
            origin: (x, y),
            grab: [0; 2],
            cycles: 0,
        };
        let target = rustclient_game_ui::pointer::primary_press(
            under.iter().map(|(node, widget)| (*node, widget)),
            |address| {
                self.scripts.as_ref().is_some_and(|scripts| {
                    rustclient_application::widget_actions(
                        &self.state,
                        &scripts.interfaces,
                        address,
                    )
                    .is_ok_and(|entries| !entries.is_empty())
                })
            },
        );
        let clicked = target
            .filter(|target| target.click)
            .map(|target| target.node);
        if let Some(target) = target {
            held.hold = target.hold.then_some(target.node.address);
            held.drag = target.drag.then_some(target.node.address);
            held.grab = relative(&target.node, x, y);
        }
        if held.hold.is_some() || held.drag.is_some() {
            self.widget_pointer.held = Some(held);
        }
        if let Some(node) = clicked {
            self.fire_listener(
                node.address,
                WidgetListener::Click,
                relative(&node, x, y),
                None,
            );
        }
        (clicked.is_some() || held.hold.is_some() || held.drag.is_some())
            && !target.is_some_and(|t| t.actionable && !t.drag)
    }
    /// A left release: finish the hold and, if the press became a drag, the drag.
    pub(super) fn widget_pointer_release(&mut self, x: f32, y: f32) {
        let Some(held) = self.widget_pointer.held.take() else {
            return;
        };
        if held.epoch != self.state.interfaces().epoch() {
            return;
        }
        self.ensure_widget_tree();
        if let Some(address) = held.hold
            && let Some(node) = self.placed(address)
        {
            self.fire_listener(
                address,
                WidgetListener::Release,
                relative(&node, x, y),
                None,
            );
        }
        if held.dragging
            && let Some(address) = held.drag
        {
            let target = self
                .widgets_under(x, y)
                .into_iter()
                .map(|(node, _)| node.address)
                .find(|candidate| *candidate != address);
            if let Some(mouse) = self.drag_mouse(held, address, x, y) {
                self.fire_listener(address, WidgetListener::DragComplete, mouse, target);
            }
        }
    }
    /// One 20 ms client cycle of pointer listeners: repeats while held, drags and hover changes.
    pub(super) fn widget_pointer_cycle(&mut self) {
        if self.state.authenticated_player().is_none() || self.state.interfaces().root().is_none() {
            return;
        }
        if self.widget_menu.is_open() || self.context_menu.is_open() {
            return;
        }
        self.ensure_widget_tree();
        if self.widget_tree.nodes.is_empty() {
            return;
        }
        let pointer = self
            .window
            .clone()
            .zip(self.cursor)
            .map(|(window, (x, y))| self.ui_pointer(&window, x, y));
        if let (Some(mut held), Some((x, y))) = (self.widget_pointer.held, pointer) {
            if std::env::var_os("RUSTCLIENT_POINTER_DIAGNOSTICS").is_some() {
                eprintln!(
                    "pointer held {held:?} at {x},{y} epoch {}",
                    self.state.interfaces().epoch()
                );
            }
            if held.epoch != self.state.interfaces().epoch() {
                self.widget_pointer.held = None;
                return;
            }
            held.cycles = held.cycles.saturating_add(1);
            if let Some(address) = held.hold
                && let Some(node) = self.placed(address)
            {
                let mouse = relative(&node, x, y);
                self.fire_listener(address, WidgetListener::Hold, mouse, None);
                self.fire_listener(address, WidgetListener::ClickRepeat, mouse, None);
            }
            if let Some(address) = held.drag {
                let Some(scripts) = &self.scripts else {
                    return;
                };
                let Ok(widget) =
                    resolve_widget(self.state.interfaces(), &scripts.interfaces, address)
                else {
                    self.widget_pointer.held = None;
                    return;
                };
                let config = self
                    .state
                    .interfaces()
                    .runtime()
                    .get(&address)
                    .and_then(|r| r.drag);
                let zone = config.map_or(i32::from(widget.drag_zone), |d| d.zone);
                let threshold = config.map_or(i32::from(widget.drag_threshold), |d| d.threshold);
                #[expect(clippy::cast_precision_loss, reason = "logical pointer extents")]
                let moved = (x - held.origin.0).abs() > zone as f32
                    || (y - held.origin.1).abs() > zone as f32;
                held.dragging |= i64::from(held.cycles) > i64::from(threshold) && moved;
                if held.dragging
                    && let Some(mouse) = self.drag_mouse(held, address, x, y)
                {
                    self.fire_listener(address, WidgetListener::Drag, mouse, None);
                }
            }
            self.widget_pointer.held = Some(held);
            return;
        }
        let now: Vec<(WidgetAddress, PlacedWidget)> = pointer
            .filter(|_| !self.widget_menu.is_open() && !self.context_menu.is_open())
            .map(|(x, y)| self.widgets_under(x, y))
            .unwrap_or_default()
            .into_iter()
            .filter(|(_, widget)| {
                HOVER_LISTENERS
                    .iter()
                    .any(|kind| widget.listeners.contains_key(kind))
            })
            .map(|(node, _)| (node.address, node))
            .collect();
        let (x, y) = pointer.unwrap_or_default();
        let previous = std::mem::take(&mut self.widget_pointer.hovered);
        for address in &previous {
            if now.iter().all(|(a, _)| a != address) {
                let mouse = self
                    .placed(*address)
                    .map_or([0, 0], |node| relative(&node, x, y));
                self.fire_listener(*address, WidgetListener::MouseLeave, mouse, None);
            }
        }
        for (address, node) in &now {
            let mouse = relative(node, x, y);
            if !previous.contains(address) {
                self.fire_listener(*address, WidgetListener::MouseOver, mouse, None);
            }
            self.fire_listener(*address, WidgetListener::MouseRepeat, mouse, None);
        }
        self.widget_pointer.hovered = now.into_iter().map(|(address, _)| address).collect();
    }
}
