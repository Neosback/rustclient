use crate::{SettingView, ShellAction, ShellChannel, assets::icon};
use dioxus::prelude::*;
use rustclient_plugin_host::configuration::{ConfigValue, Control, ENABLED_KEY, Font, Hotkey};

pub(crate) const CSS: &str = include_str!("shell.css");

/// Navigation icon for a built-in plugin; unknown (external) plugins fall back to a monogram.
fn plugin_icon(id: &str) -> Option<&'static str> {
    match id {
        "dev.rustclient.runelite" => Some(icon!("runelite_16")),
        _ => None,
    }
}

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
fn PluginGlyph(id: String, name: String) -> Element {
    rsx! {
        if let Some(src) = plugin_icon(&id) {
            img { class: "glyph", src: "{src}", alt: "" }
        } else {
            span { class: "glyph monogram", "{name.chars().next().unwrap_or('?')}" }
        }
    }
}

#[component]
pub fn Shell(channel: ShellChannel) -> Element {
    let mut query = use_signal(String::new);
    let mut selected = use_signal(|| None::<String>);
    let snapshot = channel.model.borrow().clone();
    let current = selected
        .read()
        .as_ref()
        .and_then(|id| snapshot.plugins.iter().find(|plugin| plugin.id == *id));
    let close = channel.clone();
    let open_list = channel.clone();
    let needle = query.read().to_lowercase();
    rsx! {
        div { id: "shell", class: "shell",
            nav { class: "rail", "aria-label": "Sidebar",
                button { id: "plugins-nav", class: if current.is_none() { "rail-button active" } else { "rail-button" },
                    title: "Plugin configuration", "aria-label": "Plugin configuration",
                    onclick: move |_| { selected.set(None); open_list.send(ShellAction::Open); },
                    img { class: "rail-icon", src: icon!("config_icon_lg"), alt: "" }
                    span { class: "tip", "Configuration" }
                }
                for plugin in &snapshot.plugins {
                    { let id = plugin.id.clone(); let open = channel.clone();
                      let active = current.is_some_and(|c| c.id == plugin.id);
                      rsx! { button { key: "{plugin.id}", class: if active { "rail-button active" } else { "rail-button" },
                          title: "{plugin.name}", "aria-label": "{plugin.name}",
                          onclick: move |_| { selected.set(Some(id.clone())); open.send(ShellAction::Open); },
                          PluginGlyph { id: plugin.id.clone(), name: plugin.name.clone() }
                          span { class: "tip", "{plugin.name}" }
                      } } }
                }
                span { class: "rail-spacer" }
                button { id: "close-shell", class: "rail-button", title: "Close sidebar", "aria-label": "Close sidebar",
                    onclick: move |_| close.send(ShellAction::Close),
                    span { class: "close-glyph", "×" }
                    span { class: "tip", "Close" }
                }
            }
            if snapshot.panel_visible { main { class: "panel",
                if let Some(error) = &snapshot.error {
                    div { class: "error", role: "alert", img { class: "mini", src: icon!("error"), alt: "" } span { "{error}" } }
                }
                if let Some(plugin) = current {
                    header { class: "bar",
                        button { class: "icon-button", title: "Back to plugins", "aria-label": "Back to plugins", onclick: move |_| selected.set(None),
                            img { class: "mini", src: icon!("config_back_icon"), alt: "" } }
                        h1 { "{plugin.name}" }
                        { let channel = channel.clone(); let id = plugin.id.clone(); rsx! {
                            button { class: "icon-button reset", title: "Reset {plugin.name} settings", "aria-label": "Reset settings",
                                onclick: move |_| channel.send(ShellAction::Reset { plugin: id.clone() }),
                                img { class: "mini", src: icon!("refresh"), alt: "" } }
                        } }
                    }
                    div { class: "plugin-meta",
                        if plugin.required { span { class: "badge", "Always on" } }
                        else { { let channel = channel.clone(); let id = plugin.id.clone();
                            rsx! { PluginSwitch { enabled: plugin.enabled, name: plugin.name.clone(), channel, id } } } }
                        span { class: "muted", "{plugin.settings.len()} settings" } }
                    if !plugin.description.is_empty() { p { class: "plugin-description", "{plugin.description}" } }
                    div { class: "settings", id: "settings-scroll",
                        for section in sections(&plugin.settings) {
                            Section { key: "{section}", title: humanize(&section),
                                rows: plugin.settings.iter().filter(|setting| setting.descriptor.section == section).cloned().collect(),
                                plugin: plugin.id.clone(), channel: channel.clone() }
                        }
                    }
                } else {
                    header { class: "bar", h1 { "Plugins" } span { class: "count", "{snapshot.plugins.len()}" } }
                    div { class: "search",
                        img { class: "mini", src: icon!("search"), alt: "" }
                        if query.read().is_empty() { span { class: "placeholder", "Search plugins" } }
                        input { id: "plugin-search", r#type: "text", placeholder: "Search plugins", "aria-label": "Search plugins",
                            value: "{query}", oninput: move |event| query.set(event.value()) }
                    }
                    div { class: "cards",
                        for plugin in snapshot.plugins.iter().filter(|plugin| matches_query(plugin, &needle)) {
                            { let id = plugin.id.clone(); rsx! {
                                div { key: "{plugin.id}", class: "plugin-card", "data-plugin": "{plugin.id}",
                                    button { class: "card-open", onclick: move |_| selected.set(Some(id.clone())),
                                        PluginGlyph { id: plugin.id.clone(), name: plugin.name.clone() }
                                        span { class: "card-text",
                                            strong { "{plugin.name}" }
                                            span { class: "muted", if plugin.description.is_empty() { "{plugin.settings.len()} settings" } else { "{plugin.description}" } }
                                        }
                                    }
                                    if plugin.required {
                                        span { class: "badge", "Always on" }
                                        span { class: "switch on locked", role: "img", "aria-label": "Always on", span { class: "knob" } }
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
            } }
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
    let mut expanded = use_signal(|| true);
    let open = *expanded.read();
    rsx! {
        section { class: "group",
            button { class: "section", "aria-expanded": "{open}", onclick: move |_| expanded.set(!open),
                img { class: "chevron", src: if open { icon!("expanded") } else { icon!("collapsed") }, alt: "" } span { "{title}" } span { class: "count", "{rows.len()}" } }
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
    rsx! {
        div { class: if disabled { "setting unavailable-row" } else { "setting" }, title: "{row.descriptor.description}",
            div { class: "setting-head",
                label { "{row.descriptor.label}" }
                match (&row.descriptor.control, &row.value) {
                    (Control::Boolean, ConfigValue::Boolean(value)) => rsx! {
                        Switch { value: *value, disabled, label: row.descriptor.label.clone(), onchange: move |value| emit.call(ConfigValue::Boolean(value)) }
                    },
                    _ => rsx! {},
                }
            }
            if !row.descriptor.description.is_empty() { p { class: "description", "{row.descriptor.description.replace(\"<br>\", \" \")}" } }
            match (&row.descriptor.control, &row.value) {
                (Control::Integer { min, max }, ConfigValue::Integer(value)) => rsx! {
                    IntegerControl { min: *min, max: *max, value: *value, disabled, channel: pointer_channel, onchange: move |value| emit.call(ConfigValue::Integer(value)) }
                },
                (Control::Choice { options }, ConfigValue::Choice(value)) => rsx! {
                    ChoiceControl { options: options.clone(), value: value.clone(), disabled, onchange: move |value| emit.call(ConfigValue::Choice(value)) }
                },
                (Control::Color, ConfigValue::Color(value)) => rsx! {
                    ColorControl { value: *value, disabled, onchange: move |value| emit.call(ConfigValue::Color(value)) }
                },
                (Control::Hotkey, ConfigValue::Hotkey(value)) => rsx! {
                    HotkeyControl { value: value.clone(), disabled, onchange: move |value| emit.call(ConfigValue::Hotkey(value)) }
                },
                (Control::Size, ConfigValue::Size(value)) => rsx! {
                    SizeControl { value: *value, disabled, onchange: move |value| emit.call(ConfigValue::Size(value)) }
                },
                (Control::Font, ConfigValue::Font(value)) => rsx! {
                    FontControl { value: value.clone(), disabled, onchange: move |value| emit.call(ConfigValue::Font(value)) }
                },
                (Control::Text { max_len }, ConfigValue::Text(value)) => rsx! {
                    TextControl { max_len: *max_len, value: value.clone(), disabled, onchange: move |value| emit.call(ConfigValue::Text(value)) }
                },
                (Control::Decimal { min, max }, ConfigValue::Decimal(value)) => rsx! {
                    DecimalControl { min: *min, max: *max, value: *value, disabled, onchange: move |value| emit.call(ConfigValue::Decimal(value)) }
                },
                (Control::Boolean, ConfigValue::Boolean(_)) => rsx! {},
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
    disabled: bool,
    channel: ShellChannel,
    onchange: EventHandler<i32>,
) -> Element {
    let update = EventHandler::new(move |event: MouseEvent| {
        if disabled {
            return;
        }
        #[cfg(not(target_arch = "wasm32"))]
        let element_x = event.client_coordinates().x - channel.pointer_origin_x.get().unwrap_or(0.);
        #[cfg(target_arch = "wasm32")]
        let element_x = event.element_coordinates().x;
        // The track's one-pixel borders must not make either endpoint unreachable.
        let fraction = ((element_x - 1.) / SLIDER_WIDTH).clamp(0., 1.);
        let value = f64::from(min) + fraction * (f64::from(max) - f64::from(min));
        #[expect(
            clippy::cast_possible_truncation,
            reason = "clamped to the i32 descriptor range"
        )]
        onchange.call(value.round() as i32);
    });
    let percent =
        100. * (f64::from(value) - f64::from(min)) / (f64::from(max) - f64::from(min)).max(1.);
    rsx! {
        div { class: "number",
            div { class: "slider", role: "slider", tabindex: if disabled { "-1" } else { "0" },
                "aria-valuemin": "{min}", "aria-valuemax": "{max}", "aria-valuenow": "{value}",
                onmousedown: update,
                onmousemove: move |event| { if !event.held_buttons().is_empty() { update.call(event); } },
                onkeydown: move |event| { if !disabled { match event.key() {
                    Key::ArrowLeft | Key::ArrowDown => onchange.call(value.saturating_sub(1).max(min)),
                    Key::ArrowRight | Key::ArrowUp => onchange.call(value.saturating_add(1).min(max)),
                    Key::Home => onchange.call(min), Key::End => onchange.call(max), _ => {}
                } } },
                div { class: "slider-fill", style: "width:{percent}%" }
            }
            input { class: "number-input", r#type: "text", value: "{value}", disabled: disabled.then_some(true), "aria-label": "Value",
                oninput: move |event| { if let Ok(value) = event.value().parse::<i32>() { onchange.call(value.clamp(min,max)); } } }
        }
    }
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
    let mut w_draft = use_signal(|| None::<String>);
    let mut h_draft = use_signal(|| None::<String>);
    let w_text = w_draft.read().clone().unwrap_or_else(|| width.to_string());
    let h_text = h_draft.read().clone().unwrap_or_else(|| height.to_string());
    let w_bad = w_draft.read().is_some();
    let h_bad = h_draft.read().is_some();
    rsx! {
        div { class: "size",
            input { class: if w_bad { "dim invalid" } else { "dim" }, r#type: "text", value: "{w_text}", disabled: disabled.then_some(true), "aria-label": "Width",
                oninput: move |event| { let entered = event.value();
                    match entered.parse::<u32>() { Ok(w) if (320..=16384).contains(&w) => { w_draft.set(None); onchange.call([w, height]); } _ => w_draft.set(Some(entered)) } } }
            span { class: "times", "×" }
            input { class: if h_bad { "dim invalid" } else { "dim" }, r#type: "text", value: "{h_text}", disabled: disabled.then_some(true), "aria-label": "Height",
                oninput: move |event| { let entered = event.value();
                    match entered.parse::<u32>() { Ok(h) if (200..=16384).contains(&h) => { h_draft.set(None); onchange.call([width, h]); } _ => h_draft.set(Some(entered)) } } }
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
    let mut size_draft = use_signal(|| None::<String>);
    let size_text = size_draft
        .read()
        .clone()
        .unwrap_or_else(|| value.size.to_string());
    rsx! {
        div { class: "font",
            input { class: "family", r#type: "text", value: "{value.family}", disabled: disabled.then_some(true), "aria-label": "Font family",
                oninput: move |event| { let family = event.value(); if !family.is_empty() && family.len() <= 128 { onchange.call(Font { family, ..family_value.clone() }); } } }
            input { class: if size_draft.read().is_some() { "dim invalid" } else { "dim" }, r#type: "text", value: "{size_text}", disabled: disabled.then_some(true), "aria-label": "Font size",
                oninput: move |event| { let entered = event.value();
                    match entered.parse::<u16>() { Ok(size) if (6..=96).contains(&size) => { size_draft.set(None); onchange.call(Font { size, ..size_value.clone() }); } _ => size_draft.set(Some(entered)) } } }
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

#[component]
fn DecimalControl(
    min: i32,
    max: i32,
    value: i32,
    disabled: bool,
    onchange: EventHandler<i32>,
) -> Element {
    let mut draft = use_signal(|| None::<String>);
    let text = draft
        .read()
        .clone()
        .unwrap_or_else(|| format_decimal(value));
    let invalid = draft.read().is_some();
    rsx! {
        div { class: "decimal",
            input { class: if invalid { "number-input invalid" } else { "number-input" }, r#type: "text", value: "{text}", disabled: disabled.then_some(true),
                "aria-label": "Value", "aria-invalid": "{invalid}",
                oninput: move |event| {
                    let entered = event.value();
                    match parse_decimal(&entered) {
                        Some(parsed) if (min..=max).contains(&parsed) => { draft.set(None); onchange.call(parsed); }
                        _ => draft.set(Some(entered)),
                    }
                } }
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
