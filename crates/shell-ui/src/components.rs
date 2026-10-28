use crate::{SettingView, ShellAction, ShellChannel, SidebarState, assets::icon};
use dioxus::prelude::*;
use rustclient_plugin_host::configuration::{ConfigValue, Control, ENABLED_KEY, Font, Hotkey};

pub(crate) const CSS: &str = include_str!("shell.css");

/// `windowSettings` -> `Window settings`, matching how `RuneLite` titles its config sections.
/// Names that already read as titles (`Area overrides`) and the unsectioned group pass through.
fn humanize(section: &str) -> String {
    if section.is_empty() {
        return "General".into();
    }
    if section.contains(' ') {
        return section.to_owned();
    }
    let mut out = String::new();
    for (index, c) in section.chars().enumerate() {
        if c.is_uppercase() && index > 0 {
            out.push(' ');
            out.extend(c.to_lowercase());
        } else if index == 0 {
            out.extend(c.to_uppercase());
        } else {
            out.push(c);
        }
    }
    out
}

#[component]
pub fn Shell(channel: ShellChannel) -> Element {
    let mut query = use_signal(String::new);
    let mut selected = use_signal(|| None::<String>);
    let snapshot = channel.model.borrow();
    let current = selected
        .read()
        .as_ref()
        .and_then(|id| snapshot.plugins.iter().find(|plugin| plugin.id == *id));
    let expanded = snapshot.sidebar == SidebarState::Panel;
    let visible = snapshot.sidebar != SidebarState::Hidden;
    let open_list = channel.clone();
    let needle = query.read().to_lowercase();
    let mut plugins: Vec<_> = snapshot
        .plugins
        .iter()
        .filter(|plugin| matches_query(plugin, &needle))
        .collect();
    plugins.sort_by_cached_key(|plugin| {
        (
            !snapshot.pinned.contains(&plugin.id),
            plugin.name.to_lowercase(),
        )
    });
    let drag_channel = channel.clone();
    rsx! {
        style { "{CSS}" }
        div { id: "shell", class: if visible { "shell" } else { "shell concealed" },
            onmouseup: move |_| drag_channel.finish_drag(),
            nav { class: "rail", "aria-label": "Application tools",
                button { id: "plugins-nav", class: if expanded { "rail-button active" } else { "rail-button" },
                    title: "Configuration", "aria-label": "Plugin configuration", "aria-expanded": expanded,
                    onclick: move |_| {
                        open_list.send(if expanded { ShellAction::Close } else { ShellAction::Open });
                    },
                    img { class: "rail-icon", src: icon!("config_icon"), alt: "" }
                    span { class: "tip", "Configuration" }
                }
                span { class: "rail-spacer" }
            }
            main { id: "shell-panel", class: if expanded { "panel" } else { "panel concealed" },
                if let Some(error) = &snapshot.error {
                    div { class: "error", role: "alert", img { class: "mini", src: icon!("error"), alt: "" } span { "{error}" } }
                }
                if let Some(plugin) = current {
                    header { class: "bar",
                        button { class: "icon-button", title: "Back", "aria-label": "Back to plugins", onclick: move |_| selected.set(None),
                            img { class: "mini", src: icon!("config_back_icon"), alt: "" } }
                        h1 { title: if plugin.description.is_empty() { plugin.name.clone() } else { plugin.description.clone() }, "{plugin.name}" }
                        if plugin.required {
                            span { class: "switch on locked", role: "img", title: "Required plugin: always on", "aria-label": "Always on", span { class: "knob" } }
                        } else {
                            { let channel = channel.clone(); let id = plugin.id.clone();
                              rsx! { PluginSwitch { enabled: plugin.enabled, name: plugin.name.clone(), channel, id } } }
                        }
                    }
                    div { class: "settings", id: "settings-scroll", "data-view": "{plugin.id}",
                        for section in sections(&plugin.settings) {
                            Section { key: "{section}", title: humanize(&section),
                                rows: plugin.settings.iter().filter(|setting| setting.descriptor.section == section).cloned().collect(),
                                plugin: plugin.id.clone(), channel: channel.clone() }
                        }
                        div { class: "panel-footer",
                            { let channel = channel.clone(); let id = plugin.id.clone(); rsx! {
                                button { class: "footer-button reset", title: "Reset {plugin.name} settings to default", "aria-label": "Reset settings",
                                    onclick: move |_| channel.send(ShellAction::Reset { plugin: id.clone() }),
                                    "Reset"
                                }
                            } }
                            button { class: "footer-button", title: "Back to plugin list", "aria-label": "Back",
                                onclick: move |_| selected.set(None),
                                "Back"
                            }
                        }
                    }
                }
                // Keep the filtered list mounted so Back restores its native/browser scroll position.
                div { class: if current.is_some() { "list-view concealed" } else { "list-view" },
                    div { class: "search",
                        img { class: "mini", src: icon!("search"), alt: "" }
                        if query.read().is_empty() { span { class: "placeholder", "Search" } }
                        input { id: "plugin-search", r#type: "text", placeholder: "Search", "aria-label": "Search plugins",
                            value: "{query}", oninput: move |event| query.set(event.value()) }
                        if !query.read().is_empty() {
                            button { id: "clear-search", class: "clear-search", title: "Clear search", "aria-label": "Clear search", onclick: move |_| query.set(String::new()), "×" }
                        }
                    }
                    div { class: "cards", id: "plugin-list",
                        for plugin in plugins {
                            { let id = plugin.id.clone(); let configurable = !plugin.settings.is_empty(); rsx! {
                                div { key: "{plugin.id}", class: "plugin-card", "data-plugin": "{plugin.id}",
                                    { let pin_channel = channel.clone(); let pin_id = plugin.id.clone(); let pinned = snapshot.pinned.contains(&plugin.id); rsx! {
                                        button { class: if pinned { "pin-button pinned" } else { "pin-button" }, title: if pinned { "Unpin plugin" } else { "Pin plugin" },
                                            "aria-label": "Pin {plugin.name}", "aria-pressed": pinned,
                                            onclick: move |_| pin_channel.send(ShellAction::ToggleFavorite { plugin: pin_id.clone() }),
                                            img { class: "pin-star", src: icon!("star_on"), alt: "" }
                                        }
                                    } }
                                    button { class: "card-open", title: if plugin.description.is_empty() { plugin.name.clone() } else { plugin.description.clone() }, onclick: move |_| if configurable { selected.set(Some(id.clone())); },
                                        span { class: "card-text", "{plugin.name}" }
                                    }
                                    if configurable {
                                        { let id = plugin.id.clone(); rsx! {
                                            button { class: "configure-plugin", title: "Edit {plugin.name} configuration", "aria-label": "Configure {plugin.name}", onclick: move |_| selected.set(Some(id.clone())),
                                                img { class: "mini", src: icon!("config_edit_icon"), alt: "" }
                                            }
                                        } }
                                    }
                                    if plugin.required {
                                        span { class: "switch on locked", role: "img", title: "Required plugin: always on", "aria-label": "Always on", span { class: "knob" } }
                                    } else { { let channel = channel.clone(); let id = plugin.id.clone();
                                        rsx! { PluginSwitch { enabled: plugin.enabled, name: plugin.name.clone(), channel, id } } } }
                                }
                            } }
                        }
                        if !snapshot.plugins.iter().any(|plugin| matches_query(plugin, &needle)) {
                            p { class: "empty", "No plugins match “{query}”." }
                        }
                    }
                }
            }
            NativeTooltip { channel: channel.clone() }
        }
    }
}

#[component]
fn NativeTooltip(channel: ShellChannel) -> Element {
    let tooltip = use_signal(|| None::<crate::model::Tooltip>);
    channel.tooltip.set(Some(tooltip));
    let value = tooltip.read();
    rsx! {
        if let Some(value) = value.as_ref() {
            div { class: "native-tooltip", role: "tooltip", style: "left:{value.left}px;top:{value.top}px", "{value.text}" }
        }
    }
}

/// Whether a card matches the search box: name, summary or source tags, case-insensitively.
fn matches_query(plugin: &crate::PluginPanel, needle: &str) -> bool {
    needle.is_empty()
        || plugin.name.to_lowercase().contains(needle)
        || plugin.description.to_lowercase().contains(needle)
        || plugin
            .tags
            .iter()
            .any(|tag| tag.to_lowercase().contains(needle))
}

/// The on/off switch of an optional plugin; it edits the plugin's `enabled` setting like any other.
#[component]
fn PluginSwitch(enabled: bool, name: String, channel: ShellChannel, id: String) -> Element {
    rsx! {
        Switch { value: enabled, disabled: false, label: format!("Enable {name}"),
            onchange: move |value| channel.send(ShellAction::Configure {
                plugin: id.clone(),
                key: ENABLED_KEY.to_owned(),
                value: ConfigValue::Boolean(value),
            }) }
    }
}

/// Section names in schema order (first appearance), not alphabetical.
fn sections(rows: &[SettingView]) -> Vec<String> {
    let mut out: Vec<String> = Vec::new();
    for row in rows {
        if !out.contains(&row.descriptor.section) {
            out.push(row.descriptor.section.clone());
        }
    }
    out
}

#[component]
fn Section(
    title: String,
    rows: Vec<SettingView>,
    plugin: String,
    channel: ShellChannel,
) -> Element {
    let section_id = format!("{plugin}/{title}");
    let saved = channel
        .sections
        .borrow()
        .get(&section_id)
        .copied()
        .unwrap_or(true);
    let mut expanded = use_signal(|| saved);
    let sections = channel.sections.clone();
    let open = *expanded.read();
    rsx! {
        section { class: "group",
            button { class: "section", "aria-expanded": "{open}", onclick: move |_| { expanded.set(!open); sections.borrow_mut().insert(section_id.clone(), !open); },
                img { class: "chevron", src: if open { icon!("expanded") } else { icon!("collapsed") }, alt: "" }
                span { "{title}" }
            }
            if open {
                for row in rows { SettingControl { key: "{row.descriptor.key}", row, plugin: plugin.clone(), channel: channel.clone() } }
            }
        }
    }
}

#[component]
fn SettingControl(row: SettingView, plugin: String, channel: ShellChannel) -> Element {
    let key = row.descriptor.key.clone();
    let disabled = row.unavailable.is_some();
    let pointer_channel = channel.clone();
    let emit = EventHandler::new(move |value| {
        channel.send(ShellAction::Configure {
            plugin: plugin.clone(),
            key: key.clone(),
            value,
        });
    });
    let tooltip = row.descriptor.description.replace("<br>", " ");
    rsx! {
        div { class: if disabled { "setting unavailable-row" } else { "setting" }, title: "{tooltip}",
            div { class: "setting-head",
                label { title: "{tooltip}", "{row.descriptor.label}" }
                match (&row.descriptor.control, &row.value) {
                    (Control::Boolean, ConfigValue::Boolean(value)) => rsx! {
                        Switch { value: *value, disabled, label: row.descriptor.label.clone(), onchange: move |value| emit.call(ConfigValue::Boolean(value)) }
                    },
                    (Control::Choice { options }, ConfigValue::Choice(value)) => rsx! {
                        ChoiceControl { options: options.clone(), value: value.clone(), disabled, onchange: move |value| emit.call(ConfigValue::Choice(value)) }
                    },
                    (Control::Color, ConfigValue::Color(value)) => rsx! {
                        ColorControl { value: *value, disabled, onchange: move |value| emit.call(ConfigValue::Color(value)) }
                    },
                    (Control::Decimal { min, max }, ConfigValue::Decimal(value)) => rsx! {
                        DecimalControl { min: *min, max: *max, value: *value, disabled, onchange: move |value| emit.call(ConfigValue::Decimal(value)) }
                    },
                    (Control::Size, ConfigValue::Size(value)) => rsx! {
                        SizeControl { value: *value, disabled, onchange: move |value| emit.call(ConfigValue::Size(value)) }
                    },
                    _ => rsx! {},
                }
            }
            match (&row.descriptor.control, &row.value) {
                (Control::Integer { min, max }, ConfigValue::Integer(value)) => rsx! {
                    IntegerControl { min: *min, max: *max, value: *value, label: row.descriptor.label.clone(), disabled, channel: pointer_channel, onchange: move |value| emit.call(ConfigValue::Integer(value)) }
                },
                (Control::Hotkey, ConfigValue::Hotkey(value)) => rsx! {
                    HotkeyControl { value: value.clone(), disabled, onchange: move |value| emit.call(ConfigValue::Hotkey(value)) }
                },
                (Control::Font, ConfigValue::Font(value)) => rsx! {
                    FontControl { value: value.clone(), disabled, onchange: move |value| emit.call(ConfigValue::Font(value)) }
                },
                (Control::Text { max_len }, ConfigValue::Text(value)) => rsx! {
                    TextControl { max_len: *max_len, value: value.clone(), disabled, onchange: move |value| emit.call(ConfigValue::Text(value)) }
                },
                (Control::Boolean, ConfigValue::Boolean(_))
                | (Control::Choice { .. }, ConfigValue::Choice(_))
                | (Control::Color, ConfigValue::Color(_))
                | (Control::Decimal { .. }, ConfigValue::Decimal(_))
                | (Control::Size, ConfigValue::Size(_)) => rsx! {},
                _ => rsx! { span { class: "value", "{row.value:?}" } },
            }
            if let Some(note) = &row.note { small { class: "note", "{note}" } }
            if let Some(reason) = &row.unavailable { small { class: "unavailable", img { class: "mini", src: icon!("error"), alt: "" } "{reason}" } }
        }
    }
}

#[component]
fn Switch(value: bool, disabled: bool, label: String, onchange: EventHandler<bool>) -> Element {
    rsx! {
        button { class: if value { "switch on" } else { "switch" }, role: "switch", "aria-checked": "{value}", "aria-label": "{label}",
            disabled: disabled.then_some(true), onclick: move |_| onchange.call(!value),
            span { class: "knob" } }
    }
}

#[component]
fn IntegerControl(
    min: i32,
    max: i32,
    value: i32,
    label: String,
    disabled: bool,
    channel: ShellChannel,
    onchange: EventHandler<i32>,
) -> Element {
    let mut draft = use_signal(|| None::<i32>);
    let shown = draft().unwrap_or(value);
    #[cfg(not(target_arch = "wasm32"))]
    let update_channel = channel.clone();
    let update = EventHandler::new(move |event: MouseEvent| {
        if disabled {
            return;
        }
        #[cfg(not(target_arch = "wasm32"))]
        let element_x =
            event.client_coordinates().x - update_channel.pointer_origin_x.get().unwrap_or(0.);
        #[cfg(target_arch = "wasm32")]
        let element_x = event.element_coordinates().x;
        let fraction = ((element_x - 1.) / SLIDER_WIDTH).clamp(0., 1.);
        let requested = f64::from(min) + fraction * (f64::from(max) - f64::from(min));
        #[expect(clippy::cast_possible_truncation, reason = "clamped descriptor range")]
        draft.set(Some(requested.round() as i32));
    });
    let commit = EventHandler::new(move |()| {
        let requested = draft.take();
        if let Some(requested) = requested
            && requested != value
        {
            onchange.call(requested);
        }
    });
    let cancel = EventHandler::new(move |()| {
        draft.set(None);
    });
    let key_channel = channel.clone();
    let blur_channel = channel.clone();
    let release_channel = channel.clone();
    let cancel_channel = channel.clone();
    let lost_channel = channel.clone();
    let percent =
        100. * (f64::from(shown) - f64::from(min)) / (f64::from(max) - f64::from(min)).max(1.);
    rsx! {
        div { class: "number",
            div { class: "slider", role: "slider", tabindex: if disabled { "-1" } else { "0" },
                "aria-valuemin": "{min}", "aria-valuemax": "{max}", "aria-valuenow": "{shown}",
                "aria-label": label.clone(), "aria-disabled": disabled,
                onpointerdown: move |event| capture_pointer(&event),
                onpointerup: move |_| release_channel.finish_drag(),
                onpointercancel: move |_| cancel_channel.cancel_drag(),
                onlostpointercapture: move |_| lost_channel.cancel_drag(),
                onmousedown: move |event| { if !disabled { channel.start_drag(cancel, commit); update.call(event); } },
                onmousemove: move |event| { if !event.held_buttons().is_empty() { update.call(event); } },
                onblur: move |_| blur_channel.cancel_drag(),
                onkeydown: move |event| { if !disabled { match event.key() {
                    Key::Escape => key_channel.cancel_drag(),
                    Key::ArrowLeft | Key::ArrowDown => onchange.call(value.saturating_sub(1).max(min)),
                    Key::ArrowRight | Key::ArrowUp => onchange.call(value.saturating_add(1).min(max)),
                    Key::Home => onchange.call(min), Key::End => onchange.call(max), _ => {}
                } } },
                div { class: "slider-fill", style: "width:{percent}%" }
            }
            NumericInput { class: "number-input", value: shown.to_string(), disabled, label: format!("{label} value"),
                format: NumericFormat::Integer { min: i64::from(min), max: i64::from(max) },
                oncommit: move |requested| { if let Ok(requested) = i32::try_from(requested) && requested != value { onchange.call(requested); } }
            }
        }
    }
}

/// Browser pointer capture is a platform input adapter; native capture lives in `RetainedPanel`.
fn capture_pointer(event: &PointerEvent) {
    #[cfg(target_arch = "wasm32")]
    {
        use dioxus_web::WebEventExt;
        use wasm_bindgen::JsCast;
        let event = event.as_web_event();
        if let Some(element) = event
            .target()
            .and_then(|target| target.dyn_into::<web_sys::Element>().ok())
        {
            let _ = element.set_pointer_capture(event.pointer_id());
        }
    }
    #[cfg(not(target_arch = "wasm32"))]
    let _ = event;
}

/// Inner track width (150px less its two 1px borders) used to map pointer x to a value; must match `.slider`.
const SLIDER_WIDTH: f64 = 148.;

#[component]
fn ChoiceControl(
    options: Vec<String>,
    value: String,
    disabled: bool,
    onchange: EventHandler<String>,
) -> Element {
    let mut expanded = use_signal(|| false);
    let open = *expanded.read();
    rsx! {
        div { class: "choice",
            button { class: "choice-current", "aria-haspopup": "listbox", "aria-expanded": "{open}", disabled: disabled.then_some(true),
                onclick: move |_| expanded.set(!open),
                span { "{value}" } img { class: "chevron", src: if open { icon!("collapsed") } else { icon!("expanded") }, alt: "" } }
            if open {
                div { class: "choice-list", role: "listbox",
                    for option in options {
                        button { key: "{option}", class: if option == value { "choice-option selected" } else { "choice-option" }, role: "option",
                            "aria-selected": "{option == value}",
                            onclick: { let option = option.clone(); move |_| { onchange.call(option.clone()); expanded.set(false); } },
                            "{option}" }
                    }
                }
            }
        }
    }
}

/// `#AARRGGBB`, the order `RuneLite` stores `Color` with alpha.
fn format_color(value: u32) -> String {
    format!("#{value:08X}")
}

fn parse_color(text: &str) -> Option<u32> {
    let digits = text.trim().trim_start_matches('#');
    match digits.len() {
        6 => u32::from_str_radix(digits, 16)
            .ok()
            .map(|rgb| 0xFF00_0000 | rgb),
        8 => u32::from_str_radix(digits, 16).ok(),
        _ => None,
    }
}

#[component]
fn ColorControl(value: u32, disabled: bool, onchange: EventHandler<u32>) -> Element {
    let [a, r, g, b] = value.to_be_bytes();
    let alpha = f32::from(a) / 255.;
    let mut draft = use_signal(|| None::<String>);
    let text = draft.read().clone().unwrap_or_else(|| format_color(value));
    let invalid = draft
        .read()
        .as_deref()
        .is_some_and(|d| parse_color(d).is_none());
    rsx! {
        div { class: "color",
            span { class: "swatch", span { class: "swatch-fill", style: "background:rgba({r},{g},{b},{alpha})" } }
            input { class: if invalid { "hex invalid" } else { "hex" }, r#type: "text", value: "{text}", disabled: disabled.then_some(true),
                "aria-label": "Color as #AARRGGBB", "aria-invalid": "{invalid}",
                oninput: move |event| {
                    let entered = event.value();
                    if let Some(color) = parse_color(&entered) { draft.set(None); onchange.call(color); } else { draft.set(Some(entered)); }
                } }
        }
    }
}

const MOD_NAMES: [(u8, &str); 4] = [(2, "Ctrl"), (4, "Alt"), (1, "Shift"), (8, "Meta")];

fn format_hotkey(hotkey: &Hotkey) -> String {
    let mut parts: Vec<&str> = MOD_NAMES
        .iter()
        .filter(|(bit, _)| hotkey.modifiers & bit != 0)
        .map(|(_, name)| *name)
        .collect();
    if !hotkey.key.is_empty() {
        parts.push(&hotkey.key);
    }
    if parts.is_empty() {
        "Not set".into()
    } else {
        parts.join(" + ")
    }
}

fn modifier_bits(modifiers: Modifiers) -> u8 {
    u8::from(modifiers.shift())
        | u8::from(modifiers.ctrl()) << 1
        | u8::from(modifiers.alt()) << 2
        | u8::from(modifiers.meta()) << 3
}

/// A focused read-only text field captures the next chord: Blitz focuses text inputs on click (buttons only by
/// Tab) and dispatches key events to the focused node, so this works for both pointer and keyboard users.
#[component]
fn HotkeyControl(value: Hotkey, disabled: bool, onchange: EventHandler<Hotkey>) -> Element {
    let label = format_hotkey(&value);
    rsx! {
        div { class: "hotkey",
            input { class: "hotkey-capture", r#type: "text", readonly: true, value: "{label}", disabled: disabled.then_some(true),
                "aria-label": "Keyboard shortcut",
                onkeydown: move |event| {
                    event.prevent_default();
                    let modifiers = modifier_bits(event.modifiers());
                    match event.key() {
                        Key::Backspace | Key::Delete => onchange.call(Hotkey { key: String::new(), modifiers: 0 }),
                        Key::F1 | Key::F2 | Key::F3 | Key::F4 | Key::F5 | Key::F6 | Key::F7 | Key::F8 | Key::F9 | Key::F10 | Key::F11 | Key::F12 =>
                            onchange.call(Hotkey { key: format!("{:?}", event.key()), modifiers }),
                        // A modifier alone is a complete binding, as `dragHotkey` uses.
                        Key::Control | Key::Alt | Key::Shift | Key::Meta => onchange.call(Hotkey { key: String::new(), modifiers }),
                        Key::Character(text) if text.chars().all(|c| c.is_ascii_alphanumeric()) =>
                            onchange.call(Hotkey { key: text.to_ascii_uppercase(), modifiers }),
                        _ => {}
                    }
                } }
            span { class: "muted", "Click, then press a shortcut. Backspace clears." }
        }
    }
}

#[component]
fn SizeControl(value: [u32; 2], disabled: bool, onchange: EventHandler<[u32; 2]>) -> Element {
    let [width, height] = value;
    rsx! {
        div { class: "size",
            NumericInput { class: "dim", value: width.to_string(), disabled, label: "Width",
                format: NumericFormat::Integer { min: 320, max: 16384 },
                oncommit: move |requested| { if let Ok(width) = u32::try_from(requested) { onchange.call([width, height]); } }
            }
            span { class: "times", "×" }
            NumericInput { class: "dim", value: height.to_string(), disabled, label: "Height",
                format: NumericFormat::Integer { min: 200, max: 16384 },
                oncommit: move |requested| { if let Ok(height) = u32::try_from(requested) { onchange.call([width, height]); } }
            }
            span { class: "muted", "px" }
        }
    }
}

#[component]
fn FontControl(value: Font, disabled: bool, onchange: EventHandler<Font>) -> Element {
    let family_value = value.clone();
    let size_value = value.clone();
    let bold_value = value.clone();
    let italic_value = value.clone();
    rsx! {
        div { class: "font",
            input { class: "family", r#type: "text", value: "{value.family}", disabled: disabled.then_some(true), "aria-label": "Font family",
                oninput: move |event| { let family = event.value(); if !family.is_empty() && family.len() <= 128 { onchange.call(Font { family, ..family_value.clone() }); } } }
            NumericInput { class: "dim", value: value.size.to_string(), disabled, label: "Font size",
                format: NumericFormat::Integer { min: 6, max: 96 },
                oncommit: move |requested| { if let Ok(size) = u16::try_from(requested) { onchange.call(Font { size, ..size_value.clone() }); } }
            }
            button { class: if value.bold { "style-toggle on" } else { "style-toggle" }, "aria-pressed": "{value.bold}", "aria-label": "Bold", disabled: disabled.then_some(true),
                onclick: move |_| onchange.call(Font { bold: !bold_value.bold, ..bold_value.clone() }), "B" }
            button { class: if value.italic { "style-toggle italic on" } else { "style-toggle italic" }, "aria-pressed": "{value.italic}", "aria-label": "Italic", disabled: disabled.then_some(true),
                onclick: move |_| onchange.call(Font { italic: !italic_value.italic, ..italic_value.clone() }), "I" }
        }
    }
}

/// Free text, committed on every edit; over-long or control-character input keeps its draft visible and invalid.
#[component]
fn TextControl(
    max_len: usize,
    value: String,
    disabled: bool,
    onchange: EventHandler<String>,
) -> Element {
    let mut draft = use_signal(|| None::<String>);
    let text = draft.read().clone().unwrap_or_else(|| value.clone());
    let invalid = draft.read().is_some();
    rsx! {
        div { class: "text",
            input { class: if invalid { "text-input invalid" } else { "text-input" }, r#type: "text", value: "{text}", disabled: disabled.then_some(true),
                "aria-label": "Value", "aria-invalid": "{invalid}",
                oninput: move |event| {
                    let entered = event.value();
                    if entered.len() <= max_len && !entered.chars().any(|c| c.is_control() && c != '\n') {
                        draft.set(None);
                        onchange.call(entered);
                    } else {
                        draft.set(Some(entered));
                    }
                } }
        }
    }
}

/// `1500` thousandths shown as `1.5`; a draft that is not a number within range stays visible and invalid.
fn format_decimal(thousandths: i32) -> String {
    let sign = if thousandths < 0 { "-" } else { "" };
    let magnitude = thousandths.unsigned_abs();
    let (whole, fraction) = (magnitude / 1000, magnitude % 1000);
    if fraction == 0 {
        format!("{sign}{whole}")
    } else {
        format!("{sign}{whole}.{fraction:03}")
            .trim_end_matches('0')
            .to_owned()
    }
}

fn parse_decimal(text: &str) -> Option<i32> {
    let text = text.trim();
    let (negative, digits) = text
        .strip_prefix('-')
        .map_or((false, text), |rest| (true, rest));
    let (whole, fraction) = digits.split_once('.').unwrap_or((digits, ""));
    if whole.is_empty() && fraction.is_empty()
        || fraction.len() > 3
        || !whole
            .chars()
            .chain(fraction.chars())
            .all(|c| c.is_ascii_digit())
    {
        return None;
    }
    let whole: i64 = if whole.is_empty() {
        0
    } else {
        whole.parse().ok()?
    };
    let fraction: i64 = format!("{fraction:0<3}").parse().ok()?;
    let value = whole.checked_mul(1000)?.checked_add(fraction)?;
    i32::try_from(if negative { -value } else { value }).ok()
}

#[derive(Clone, PartialEq)]
enum NumericFormat {
    Integer { min: i64, max: i64 },
    Decimal { min: i32, max: i32 },
}
impl NumericFormat {
    fn parse(&self, text: &str) -> Option<i64> {
        match *self {
            Self::Integer { min, max } => text
                .parse::<i64>()
                .ok()
                .filter(|value| (min..=max).contains(value)),
            Self::Decimal { min, max } => parse_decimal(text)
                .filter(|value| (min..=max).contains(value))
                .map(i64::from),
        }
    }
}

#[component]
fn NumericInput(
    class: String,
    value: String,
    label: String,
    disabled: bool,
    format: NumericFormat,
    oncommit: EventHandler<i64>,
) -> Element {
    let mut draft = use_signal(|| None::<String>);
    let text = draft.read().clone().unwrap_or(value);
    let invalid = format.parse(&text).is_none();
    let commit = EventHandler::new(move |()| {
        let entered = draft.read().clone();
        if let Some(parsed) = entered.as_deref().and_then(|text| format.parse(text)) {
            draft.set(None);
            oncommit.call(parsed);
        }
    });
    rsx! {
        input { class: if invalid { format!("{class} invalid") } else { class }, r#type: "text", value: "{text}",
            disabled: disabled.then_some(true), "aria-label": label, "aria-invalid": invalid,
            oninput: move |event| draft.set(Some(event.value())),
            onblur: move |_| commit.call(()),
            onkeydown: move |event| match event.key() {
                Key::Enter => commit.call(()), Key::Escape => draft.set(None), _ => {}
            }
        }
    }
}

#[component]
fn DecimalControl(
    min: i32,
    max: i32,
    value: i32,
    disabled: bool,
    onchange: EventHandler<i32>,
) -> Element {
    rsx! {
        div { class: "decimal",
            NumericInput { class: "number-input", value: format_decimal(value), disabled, label: "Value",
                format: NumericFormat::Decimal { min, max },
                oncommit: move |requested| { if let Ok(requested) = i32::try_from(requested) { onchange.call(requested); } }
            }
        }
    }
}

#[cfg(test)]
mod tests {
    use super::{format_decimal, parse_decimal};

    #[test]
    fn decimals_round_trip_through_thousandths() {
        for (text, value) in [
            ("1", 1000),
            ("1.5", 1500),
            ("0.025", 25),
            ("-2.5", -2500),
            ("2", 2000),
            (".5", 500),
        ] {
            assert_eq!(parse_decimal(text), Some(value), "{text}");
        }
        for value in [0, 1000, 1500, 25, -2500, 123_456] {
            assert_eq!(parse_decimal(&format_decimal(value)), Some(value));
        }
        for bad in ["", ".", "-", "1.2345", "a", "1e3", "--1", "99999999999"] {
            assert_eq!(parse_decimal(bad), None, "{bad}");
        }
        assert_eq!(format_decimal(1500), "1.5");
        assert_eq!(format_decimal(-2500), "-2.5");
        assert_eq!(format_decimal(25), "0.025");
    }
}
