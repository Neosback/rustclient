//! Embedded native DOM and dirty-only CPU rasterization; no window or GPU ownership.
use crate::{
    Shell, ShellChannel,
    assets::{Collector, EmbeddedAssets},
    components::CSS,
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
use dioxus::prelude::{ScopeId, VirtualDom};
use dioxus_native_dom::DioxusDocument;
use keyboard_types::{Code, Key, Location, Modifiers};
use std::sync::Arc;

#[derive(Debug, Default, Clone, Copy, PartialEq, Eq)]
pub struct PanelStats {
    pub component_updates: u64,
    pub layouts: u64,
    pub rasterizations: u64,
    pub rasterized_bytes: u64,
}

pub struct RetainedPanel {
    doc: DioxusDocument,
    painter: VelloCpuImageRenderer,
    pixels: Vec<u8>,
    size: [u32; 2],
    scale: f32,
    dirty: bool,
    visible: bool,
    stats: PanelStats,
    capture: Option<[f32; 4]>,
    channel: ShellChannel,
    assets: Arc<Collector>,
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
        doc.add_user_agent_stylesheet(CSS);
        doc.initial_build();
        Ok(Self {
            doc,
            painter: VelloCpuImageRenderer::new(size[0], size[1]),
            pixels: Vec::new(),
            size,
            scale,
            dirty: true,
            visible: false,
            stats: PanelStats::default(),
            capture: None,
            channel,
            assets,
        })
    }
    pub fn set_visible(&mut self, visible: bool) {
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
        }
        Ok(())
    }
    /// `None` means reuse the previous texture: no DOM, layout, raster or upload work.
    pub fn render(&mut self) -> Option<&[u8]> {
        if !self.visible || !self.dirty {
            return None;
        }
        if self.doc.poll(None) {
            self.stats.component_updates += 1;
        }
        self.doc.resolve(0.);
        // Icons requested during this layout decode synchronously; adopt them and lay out once more.
        let loaded = self.assets.take();
        if !loaded.is_empty() {
            for resource in loaded {
                self.doc.inner.load_resource(resource);
            }
            self.doc.resolve(0.);
        }
        self.stats.layouts += 1;
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
        if pressed == Some(true) {
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
        if pressed == Some(false) {
            self.capture = None;
        }
        self.dirty = true;
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
        if key == Key::Tab {
            if pressed {
                self.focus_step(modifiers.contains(Modifiers::SHIFT));
            }
            self.dirty = true;
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
    }
    pub fn ime_commit(&mut self, text: String) {
        if self.visible {
            self.doc
                .handle_ui_event(UiEvent::Ime(BlitzImeEvent::Commit(text)));
            self.dirty = true;
        }
    }
    pub fn ime_preedit(&mut self, text: String, selection: Option<(usize, usize)>) {
        if self.visible {
            self.doc
                .handle_ui_event(UiEvent::Ime(BlitzImeEvent::Preedit(text, selection)));
            self.dirty = true;
        }
    }
    pub fn clear_focus(&mut self) {
        self.doc
            .handle_ui_event(UiEvent::Ime(BlitzImeEvent::Disabled));
        self.doc.clear_focus();
        self.capture = None;
        self.dirty = true;
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
        self.doc.set_focus_to(order[index]);
        if let Some([_, top, _, height]) = self.node_bounds(order[index]) {
            let [_, clip_top, _, clip_height] =
                self.element_bounds("#settings-scroll").unwrap_or([
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
        let mut x = node.final_layout.location.x;
        let mut y = node.final_layout.location.y;
        let mut parent = node.layout_parent.get();
        while let Some(id) = parent {
            let node = self.doc.get_node(id)?;
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
