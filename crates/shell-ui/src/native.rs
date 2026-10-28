//! Embedded native DOM and dirty-only CPU rasterization; no window or GPU ownership.
use crate::{
    Shell, ShellChannel,
    assets::{Collector, EmbeddedAssets},
};
use anyrender::ImageRenderer;
use anyrender_vello_cpu::VelloCpuImageRenderer;
use blitz_dom::{Document, DocumentConfig};
use blitz_traits::{
    events::{
        BlitzImeEvent, BlitzKeyEvent, BlitzMouseButtonEvent, KeyState, MouseEventButton,
        MouseEventButtons, UiEvent,
    },
    shell::{ColorScheme, Viewport},
};
use dioxus::prelude::{ReadableExt, ScopeId, VirtualDom, WritableExt};
use dioxus_native_dom::DioxusDocument;
use keyboard_types::{Code, Key, Location, Modifiers};
use std::{rc::Rc, sync::Arc, time::Instant};

#[derive(Debug, Default, Clone, Copy, PartialEq, Eq)]
pub struct PanelStats {
    pub component_updates: u64,
    pub layouts: u64,
    pub rasterizations: u64,
    pub rasterized_bytes: u64,
    pub update_micros: u64,
    pub layout_micros: u64,
    pub paint_micros: u64,
}

pub struct RetainedPanel {
    doc: DioxusDocument,
    painter: VelloCpuImageRenderer,
    pixels: Vec<u8>,
    size: [u32; 2],
    scale: f32,
    dirty: bool,
    layout_dirty: bool,
    visible: bool,
    stats: PanelStats,
    capture: Option<[f32; 4]>,
    channel: ShellChannel,
    assets: Arc<Collector>,
    scroll_positions: std::collections::BTreeMap<String, [f64; 2]>,
}
impl RetainedPanel {
    /// Build a panel in physical pixels; generation work is deferred until visible.
    /// # Errors
    /// Rejects invalid or excessive allocation dimensions.
    pub fn new(channel: ShellChannel, size: [u32; 2], scale: f32) -> Result<Self, String> {
        validate_size(size, scale)?;
        let vdom = VirtualDom::new_with_props(
            Shell,
            crate::components::ShellProps {
                channel: channel.clone(),
            },
        );
        let assets = Arc::new(Collector::default());
        let mut doc = DioxusDocument::new(
            vdom,
            DocumentConfig {
                viewport: Some(Viewport::new(size[0], size[1], scale, ColorScheme::Dark)),
                net_provider: Some(Arc::new(EmbeddedAssets(assets.clone()))),
                ..DocumentConfig::default()
            },
        );
        doc.initial_build();
        Ok(Self {
            doc,
            painter: VelloCpuImageRenderer::new(size[0], size[1]),
            pixels: Vec::new(),
            size,
            scale,
            dirty: true,
            layout_dirty: true,
            visible: false,
            stats: PanelStats::default(),
            capture: None,
            channel,
            assets,
            scroll_positions: std::collections::BTreeMap::new(),
        })
    }
    pub fn set_visible(&mut self, visible: bool) {
        if self.visible && !visible {
            self.clear_focus();
        }
        self.visible = visible;
    }
    #[must_use]
    pub fn visible(&self) -> bool {
        self.visible
    }
    #[must_use]
    pub fn stats(&self) -> PanelStats {
        self.stats
    }
    #[must_use]
    pub fn pixels(&self) -> &[u8] {
        &self.pixels
    }
    #[must_use]
    pub fn size(&self) -> [u32; 2] {
        self.size
    }
    pub fn invalidate(&mut self) {
        self.doc.vdom.mark_dirty(ScopeId::ROOT);
        self.doc.vdom.mark_dirty(ScopeId::APP);
        self.dirty = true;
        self.layout_dirty = true;
    }
    /// # Errors
    /// Invalid dimensions leave the existing document and pixel cache unchanged.
    pub fn resize(&mut self, size: [u32; 2], scale: f32) -> Result<(), String> {
        validate_size(size, scale)?;
        if self.size != size || self.scale.to_bits() != scale.to_bits() {
            self.size = size;
            self.scale = scale;
            self.doc
                .set_viewport(Viewport::new(size[0], size[1], scale, ColorScheme::Dark));
            self.painter.resize(size[0], size[1]);
            self.dirty = true;
            self.layout_dirty = true;
        }
        Ok(())
    }
    /// Flush component updates without painting. Composition drains resulting typed actions
    /// before the one presentation-time rasterization.
    pub fn update(&mut self) {
        if !self.visible || !self.dirty {
            return;
        }
        self.save_scroll();
        let start = Instant::now();
        if self.doc.poll(None) {
            self.stats.component_updates += 1;
            self.layout_dirty = true;
        }
        self.stats.update_micros += elapsed_micros(start);
    }
    /// `None` means reuse the previous texture: no DOM, layout, raster or upload work.
    pub fn render(&mut self) -> Option<&[u8]> {
        if !self.visible || !self.dirty {
            return None;
        }
        self.update();
        if self.layout_dirty {
            let start = Instant::now();
            self.doc.resolve(0.);
            self.stats.layouts += 1;
            let loaded = self.assets.take();
            if !loaded.is_empty() {
                for resource in loaded {
                    self.doc.inner.load_resource(resource);
                }
                self.doc.resolve(0.);
                self.stats.layouts += 1;
            }
            self.restore_scroll();
            self.stats.layout_micros += elapsed_micros(start);
            self.layout_dirty = false;
        }
        let start = Instant::now();
        self.painter.reset();
        let dom = &self.doc.inner;
        self.painter.render_to_vec(
            |scene| {
                blitz_paint::paint_scene(
                    scene,
                    dom,
                    f64::from(self.scale),
                    self.size[0],
                    self.size[1],
                );
            },
            &mut self.pixels,
        );
        self.stats.paint_micros += elapsed_micros(start);
        self.stats.rasterizations += 1;
        self.stats.rasterized_bytes += self.pixels.len() as u64;
        self.dirty = false;
        Some(&self.pixels)
    }

    /// Logical panel coordinates; pointer capture persists outside a slider's rectangle.
    pub fn pointer(&mut self, mut x: f32, mut y: f32, pressed: Option<bool>, held: bool) {
        if !self.visible {
            return;
        }
        let previous_focus = self.doc.get_focussed_node_id();
        let hit = self.doc.hit(x, y).map(|hit| hit.node_id);
        if pressed.is_none() && !held && hit == self.doc.get_hover_node_id() {
            self.dirty |= self.update_tooltip();
            return;
        }
        if pressed == Some(true) {
            // Deliver blur explicitly: Blitz changes its focus styles but does not emit DOM
            // focus events. Numeric editors must commit before another control takes focus.
            if previous_focus != hit {
                self.blur(previous_focus);
            }
            self.capture = self.doc.hit(x, y).and_then(|hit| {
                let node = self.doc.get_node(hit.node_id)?;
                (node
                    .element_data()?
                    .attr(blitz_dom::LocalName::from("role"))
                    == Some("slider"))
                .then_some([
                    x - hit.x,
                    y - hit.y,
                    node.final_layout.size.width,
                    node.final_layout.size.height,
                ])
            });
        }
        if pressed == Some(true)
            && self.capture.is_some()
            && let Some(hit) = hit
        {
            self.doc.set_focus_to(hit);
        }
        if let Some([left, top, width, height]) = self.capture {
            x = x.clamp(left + 0.5, left + width - 0.5);
            y = top + height / 2.;
        }
        self.channel
            .pointer_origin_x
            .set(self.capture.map(|rect| f64::from(rect[0])));
        let event = BlitzMouseButtonEvent {
            x,
            y,
            button: MouseEventButton::Main,
            buttons: if held {
                MouseEventButtons::Primary
            } else {
                MouseEventButtons::None
            },
            mods: Modifiers::empty(),
        };
        self.doc.handle_ui_event(UiEvent::MouseMove(event.clone()));
        if let Some(down) = pressed {
            self.doc.handle_ui_event(if down {
                UiEvent::MouseDown(event)
            } else {
                UiEvent::MouseUp(event)
            });
        }
        if self.capture.is_some()
            && let Some(hit) = self.doc.hit(x, y)
        {
            self.doc.set_focus_to(hit.node_id);
        }
        if pressed == Some(false) {
            self.channel.finish_drag();
            self.capture = None;
        }
        if !held && pressed.is_none() {
            self.update_tooltip();
        }
        if pressed == Some(true)
            && let Some(mut tooltip) = self.channel.tooltip.get()
        {
            tooltip.set(None);
        }
        self.dirty = true;
        self.layout_dirty = true;
    }
    pub fn pointer_leave(&mut self) {
        if self.visible && self.capture.is_none() {
            self.pointer(-1., -1., None, false);
        }
    }
    pub fn scroll(&mut self, x: f32, y: f32, delta: [f64; 2]) {
        if !self.visible {
            return;
        }
        if let Some(hit) = self.doc.hit(x, y) {
            self.dirty |= self
                .doc
                .scroll_node_by_has_changed(hit.node_id, delta[0], delta[1]);
        }
    }
    /// Platform-neutral keyboard event. The shell consumes it only while focused.
    pub fn key(
        &mut self,
        key: Key,
        code: Code,
        modifiers: Modifiers,
        pressed: bool,
        text: Option<String>,
    ) {
        if !self.visible {
            return;
        }
        if pressed && key == Key::Escape {
            self.channel.cancel_drag();
            self.capture = None;
        }
        if key == Key::Tab {
            if pressed {
                self.focus_step(modifiers.contains(Modifiers::SHIFT));
            }
            self.dirty = true;
            self.layout_dirty = true;
            return;
        }
        let activate = (key == Key::Enter && pressed)
            || (matches!(&key, Key::Character(text) if text == " ") && !pressed);
        if activate && let Some(id) = self.doc.get_focussed_node_id() {
            let button = self
                .doc
                .get_node(id)
                .and_then(|node| node.element_data())
                .is_some_and(|element| {
                    let tag = &element.name.local;
                    !element.has_attr(blitz_dom::LocalName::from("disabled"))
                        && (tag.as_ref() == "button"
                            || (tag.as_ref() == "input"
                                && element.attr(blitz_dom::LocalName::from("type"))
                                    == Some("checkbox")))
                });
            if button && let Some([x, y, width, height]) = self.node_bounds(id) {
                self.pointer(x + width / 2., y + height / 2., Some(true), true);
                self.pointer(x + width / 2., y + height / 2., Some(false), false);
                return;
            }
        }
        let event = BlitzKeyEvent {
            key,
            code,
            modifiers,
            location: Location::Standard,
            is_auto_repeating: false,
            is_composing: false,
            state: if pressed {
                KeyState::Pressed
            } else {
                KeyState::Released
            },
            text: text.map(Into::into),
        };
        self.doc.handle_ui_event(if pressed {
            UiEvent::KeyDown(event)
        } else {
            UiEvent::KeyUp(event)
        });
        self.dirty = true;
        self.layout_dirty = true;
    }
    pub fn ime_commit(&mut self, text: String) {
        if self.visible {
            self.doc
                .handle_ui_event(UiEvent::Ime(BlitzImeEvent::Commit(text)));
            self.dirty = true;
            self.layout_dirty = true;
        }
    }
    pub fn ime_preedit(&mut self, text: String, selection: Option<(usize, usize)>) {
        if self.visible {
            self.doc
                .handle_ui_event(UiEvent::Ime(BlitzImeEvent::Preedit(text, selection)));
            self.dirty = true;
            self.layout_dirty = true;
        }
    }
    pub fn clear_focus(&mut self) {
        self.channel.cancel_drag();
        self.blur(self.doc.get_focussed_node_id());
        self.doc
            .handle_ui_event(UiEvent::Ime(BlitzImeEvent::Disabled));
        self.doc.clear_focus();
        self.capture = None;
        if let Some(mut tooltip) = self.channel.tooltip.get() {
            tooltip.set(None);
        }
        self.dirty = true;
        self.layout_dirty = true;
    }
    /// Supply only clipboard permission to the toolkit, without exposing window/filesystem services.
    pub fn set_clipboard(&mut self, clipboard: Box<dyn rustclient_platform::Clipboard>) {
        #[expect(
            clippy::arc_with_non_send_sync,
            reason = "DOM/clipboard are confined to the event-loop thread; Blitz requires Arc"
        )]
        self.doc
            .set_shell_provider(std::sync::Arc::new(ClipboardProvider(
                std::cell::RefCell::new(clipboard),
            )));
    }
    /// Trusted composition-root clipboard/redraw adapter; never passed to an external plugin.
    pub fn set_native_services(
        &mut self,
        services: std::sync::Arc<dyn blitz_traits::shell::ShellProvider>,
    ) {
        self.doc.set_shell_provider(services);
    }
    #[must_use]
    pub fn input_value(&self, selector: &str) -> Option<String> {
        let id = self.doc.query_selector(selector).ok()??;
        Some(
            self.doc
                .get_node(id)?
                .element_data()?
                .text_input_data()?
                .editor
                .raw_text()
                .to_owned(),
        )
    }

    fn scroll_nodes(&self) -> Vec<(usize, String)> {
        ["#plugin-list", "#settings-scroll"]
            .iter()
            .filter_map(|selector| {
                let id = self.doc.query_selector(selector).ok()??;
                self.node_bounds(id)?;
                let node = self.doc.get_node(id)?;
                let view = node
                    .element_data()?
                    .attr(blitz_dom::LocalName::from("data-view"))
                    .unwrap_or("list");
                Some((id, view.to_owned()))
            })
            .collect()
    }
    fn save_scroll(&mut self) {
        for (id, key) in self.scroll_nodes() {
            if let Some(node) = self.doc.get_node(id) {
                self.scroll_positions
                    .insert(key, [node.scroll_offset.x, node.scroll_offset.y]);
            }
        }
    }
    fn restore_scroll(&mut self) {
        for (id, key) in self.scroll_nodes() {
            if let Some([x, y]) = self.scroll_positions.get(&key).copied()
                && let Some(node) = self.doc.get_node(id)
            {
                let [dx, dy] = [node.scroll_offset.x - x, node.scroll_offset.y - y];
                if dx != 0. || dy != 0. {
                    self.doc.scroll_node_by_has_changed(id, dx, dy);
                }
            }
        }
    }
    fn blur(&self, node: Option<usize>) {
        let Some(id) = node
            .and_then(|id| self.doc.get_node(id))
            .and_then(|node| node.element_data())
            .and_then(|element| element.attr(blitz_dom::LocalName::from("data-dioxus-id")))
            .and_then(|id| id.parse::<usize>().ok())
        else {
            return;
        };
        let data: Rc<dyn std::any::Any> =
            Rc::new(dioxus::html::PlatformEventData::new(Box::new(())));
        self.doc.vdom.runtime().handle_event(
            "blur",
            dioxus::prelude::Event::new(data, false),
            dioxus::dioxus_core::ElementId(id),
        );
    }
    #[expect(clippy::cast_precision_loss, reason = "bounded DOM coordinates")]
    fn update_tooltip(&mut self) -> bool {
        let mut node = self.doc.get_hover_node_id();
        let mut value = None;
        while let Some(id) = node {
            let Some(current) = self.doc.get_node(id) else {
                break;
            };
            if let Some(title) = current
                .element_data()
                .and_then(|element| element.attr(blitz_dom::LocalName::from("title")))
                .filter(|title| !title.is_empty())
            {
                if let Some([x, y, _, height]) = self.node_bounds(id) {
                    value = Some(crate::model::Tooltip {
                        text: title.replace("<br>", " "),
                        left: x.clamp(4., (self.size[0] as f32 / self.scale - 230.).max(4.)),
                        top: (y + height + 5.)
                            .min((self.size[1] as f32 / self.scale - 80.).max(4.)),
                    });
                }
                break;
            }
            node = current.parent.or_else(|| current.layout_parent.get());
        }
        if let Some(mut tooltip) = self.channel.tooltip.get()
            && *tooltip.peek() != value
        {
            tooltip.set(value);
            return true;
        }
        false
    }
    #[expect(
        clippy::cast_precision_loss,
        reason = "physical panel dimensions are bounded to 4096"
    )]
    fn focus_step(&mut self, reverse: bool) {
        let mut order = Vec::new();
        self.doc.visit(|id, node| {
            if node.is_focussable()
                && node.final_layout.size.width > 0.
                && node.final_layout.size.height > 0.
                && self.node_bounds(id).is_some()
            {
                order.push(id);
            }
        });
        if order.is_empty() {
            return;
        }
        let position = self
            .doc
            .get_focussed_node_id()
            .and_then(|id| order.iter().position(|candidate| *candidate == id));
        let index = match (position, reverse) {
            (Some(position), true) => (position + order.len() - 1) % order.len(),
            (Some(position), false) => (position + 1) % order.len(),
            (None, true) => order.len() - 1,
            (None, false) => 0,
        };
        self.blur(self.doc.get_focussed_node_id());
        self.doc.set_focus_to(order[index]);
        if let Some([_, top, _, height]) = self.node_bounds(order[index]) {
            let [_, clip_top, _, clip_height] = self
                .element_bounds("#settings-scroll")
                .filter(|bounds| bounds[3] > 0.)
                .or_else(|| self.element_bounds("#plugin-list"))
                .unwrap_or([
                    0.,
                    0.,
                    self.size[0] as f32 / self.scale,
                    self.size[1] as f32 / self.scale,
                ]);
            let delta = if top < clip_top {
                clip_top - top
            } else if top + height > clip_top + clip_height {
                clip_top + clip_height - top - height
            } else {
                0.
            };
            if delta != 0. {
                self.doc
                    .scroll_node_by_has_changed(order[index], 0., f64::from(delta));
            }
        }
    }
    /// Logical rectangle for diagnostics and native acceptance tests, never gameplay picking.
    pub fn element_bounds(&self, selector: &str) -> Option<[f32; 4]> {
        let id = self.doc.query_selector(selector).ok()??;
        self.node_bounds(id)
    }
    #[expect(
        clippy::cast_possible_truncation,
        reason = "bounded logical panel scroll offsets join f32 DOM geometry"
    )]
    fn node_bounds(&self, id: usize) -> Option<[f32; 4]> {
        let node = self.doc.get_node(id)?;
        if is_concealed(node) {
            return None;
        }
        let mut x = node.final_layout.location.x;
        let mut y = node.final_layout.location.y;
        let mut parent = node.layout_parent.get();
        while let Some(id) = parent {
            let node = self.doc.get_node(id)?;
            if is_concealed(node) {
                return None;
            }
            x += node.final_layout.location.x - node.scroll_offset.x as f32;
            y += node.final_layout.location.y - node.scroll_offset.y as f32;
            parent = node.layout_parent.get();
        }
        Some([
            x,
            y,
            node.final_layout.size.width,
            node.final_layout.size.height,
        ])
    }
}

fn is_concealed(node: &blitz_dom::Node) -> bool {
    node.element_data()
        .and_then(|element| element.attr(blitz_dom::LocalName::from("class")))
        .is_some_and(|class| class.split_whitespace().any(|name| name == "concealed"))
}

fn elapsed_micros(start: Instant) -> u64 {
    u64::try_from(start.elapsed().as_micros()).unwrap_or(u64::MAX)
}

fn validate_size([width, height]: [u32; 2], scale: f32) -> Result<(), String> {
    if width == 0
        || height == 0
        || width > 4096
        || height > 4096
        || !scale.is_finite()
        || !(0.5..=4.).contains(&scale)
    {
        return Err("shell panel dimensions/scale outside supported bounds".into());
    }
    Ok(())
}

struct ClipboardProvider(std::cell::RefCell<Box<dyn rustclient_platform::Clipboard>>);
impl blitz_traits::shell::ShellProvider for ClipboardProvider {
    fn get_clipboard_text(&self) -> Result<String, blitz_traits::shell::ClipboardError> {
        self.0.borrow_mut().read_text().map_err(|error| {
            eprintln!("shell clipboard read ({:?}): {error}", error.kind());
            blitz_traits::shell::ClipboardError
        })
    }
    fn set_clipboard_text(&self, text: String) -> Result<(), blitz_traits::shell::ClipboardError> {
        self.0.borrow_mut().write_text(text).map_err(|error| {
            eprintln!("shell clipboard write ({:?}): {error}", error.kind());
            blitz_traits::shell::ClipboardError
        })
    }
}
