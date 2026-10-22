use super::StableFingerprint64;
use crate::InterfaceState;
use rustclient_model::{InterfaceId, InterfaceMountKind, WidgetId, WidgetPermissions};
fn widget(f: &mut StableFingerprint64, id: WidgetId) {
    f.write_u32(id.interface.get());
    f.write_u32(id.component);
}
fn group(f: &mut StableFingerprint64, id: Option<InterfaceId>) {
    match id {
        None => f.write_u8(0),
        Some(id) => {
            f.write_u8(1);
            f.write_u32(id.get());
        }
    }
}
fn permissions(f: &mut StableFingerprint64, p: WidgetPermissions) {
    f.write_u32(p.enabled_operations);
    f.write_u8(u8::from(p.continue_enabled));
    f.write_u8(p.target_mask);
    f.write_u8(p.drag_depth);
    f.write_u8(u8::from(p.accepts_drag));
    f.write_u8(u8::from(p.accepts_target));
    f.write_u8(u8::from(p.crm_target));
    f.write_u8(u8::from(p.script_trigger));
}
pub(super) fn write(f: &mut StableFingerprint64, state: &InterfaceState) {
    f.write_u64(state.settings().len() as u64);
    for (key, value) in state.settings() {
        f.write_u8(key.tag());
        f.write_u8(u8::from(*value));
    }
    group(f, state.root());
    f.write_u64(u64::try_from(state.mounts().len()).expect("bounded mounts"));
    for (parent, mount) in state.mounts() {
        widget(f, *parent);
        f.write_u32(mount.interface.get());
        f.write_u8(match mount.kind {
            InterfaceMountKind::Modal => 0,
            InterfaceMountKind::Overlay => 1,
            InterfaceMountKind::Client => 2,
        });
    }
    f.write_u64(state.runtime().len() as u64);
    for (address, value) in state.runtime() {
        widget(f, address.widget);
        option(f, value.target_priority, |f, op| f.write_u8(op.index()));
        option(f, value.text.as_deref(), string);
        option(f, value.parent_child, StableFingerprint64::write_u32);
        option(f, address.child, super::StableFingerprint64::write_u32);
        f.write_u64(value.parameters.len() as u64);
        for (id, v) in &value.parameters {
            f.write_u32(*id);
            script_value(f, v);
        }
        option(f, value.hidden, |f, n| f.write_u8(u8::from(n)));
        option(f, value.scroll, |f, a| {
            for n in a {
                f.write_u32(n);
            }
        });
        option(f, value.layout, |f, l| {
            for n in l.offset.into_iter().chain(l.size) {
                f.write_u32(n.cast_unsigned());
            }
            for n in l.position_mode {
                f.write_u8(match n {
                    rustclient_model::PositionMode::Start => 0,
                    rustclient_model::PositionMode::Center => 1,
                    rustclient_model::PositionMode::End => 2,
                    rustclient_model::PositionMode::ProportionalStart => 3,
                    rustclient_model::PositionMode::ProportionalCenter => 4,
                    rustclient_model::PositionMode::ProportionalEnd => 5,
                });
            }
            for n in l.size_mode {
                f.write_u8(match n {
                    rustclient_model::SizeMode::Absolute => 0,
                    rustclient_model::SizeMode::ParentMinus => 1,
                    rustclient_model::SizeMode::Proportional => 2,
                    rustclient_model::SizeMode::Aspect => 3,
                });
            }
        });
        option(f, value.body.as_ref(), body);
        option(f, value.actions.as_ref(), |f, a| {
            f.write_u64(a.len() as u64);
            for s in a {
                string(f, s);
            }
        });
        option(f, value.data_text.as_deref(), string);
        option(f, value.item, |f, (id, n)| {
            f.write_u32(id.get());
            f.write_u32(n);
        });
        f.write_u64(value.listeners.len() as u64);
        for (kind, args) in &value.listeners {
            listener(f, *kind);
            option(f, args.as_ref(), |f, args| {
                f.write_u64(args.len() as u64);
                for a in args {
                    script_value(f, a);
                }
            });
        }
        f.write_u64(value.triggers.len() as u64);
        for (kind, triggers) in &value.triggers {
            listener(f, *kind);
            f.write_u64(triggers.len() as u64);
            for n in triggers {
                f.write_u32(n.cast_unsigned());
            }
        }
    }
    f.write_u64(state.client_variables().len() as u64);
    for (id, value) in state.client_variables() {
        f.write_u32(*id);
        script_value(f, value);
    }
    f.write_u64(u64::try_from(state.event_ranges().len()).expect("bounded event ranges"));
    for range in state.event_ranges() {
        widget(f, range.widget);
        f.write_bytes(&range.first.to_le_bytes());
        f.write_bytes(&range.last.to_le_bytes());
        permissions(f, range.permissions);
    }
}

fn option<T>(
    f: &mut StableFingerprint64,
    value: Option<T>,
    write: impl FnOnce(&mut StableFingerprint64, T),
) {
    if let Some(value) = value {
        f.write_u8(1);
        write(f, value);
    } else {
        f.write_u8(0);
    }
}
fn string(f: &mut StableFingerprint64, s: &str) {
    f.write_u64(s.len() as u64);
    f.write_bytes(s.as_bytes());
}
fn script_value(f: &mut StableFingerprint64, v: &rustclient_model::ScriptValue) {
    use rustclient_model::ScriptValue;
    match v {
        ScriptValue::Int(n) => {
            f.write_u8(0);
            f.write_u32(n.cast_unsigned());
        }
        ScriptValue::Text(s) => {
            f.write_u8(1);
            string(f, s);
        }
        ScriptValue::Long(n) => {
            f.write_u8(2);
            f.write_bytes(&n.to_le_bytes());
        }
        ScriptValue::Integers(a) => {
            f.write_u8(3);
            f.write_u64(a.len() as u64);
            for n in a {
                f.write_u32(n.cast_unsigned());
            }
        }
        ScriptValue::Strings(a) => {
            f.write_u8(4);
            f.write_u64(a.len() as u64);
            for s in a {
                string(f, s);
            }
        }
    }
}
fn listener(f: &mut StableFingerprint64, v: rustclient_model::WidgetListener) {
    use rustclient_model::WidgetListener::{
        Chat, Clan, ClanChannel, ClanSettings, Click, ClickRepeat, DialogAbort, Drag, DragComplete,
        Friend, Hold, Inventory, Key, Load, Misc, MouseLeave, MouseOver, MouseRepeat, Operation,
        Release, Resize, Scroll, Stat, Stock, SubChange, TargetEnter, TargetLeave, Timer, Variable,
    };
    f.write_u8(match v {
        Key => 18,
        Resize => 19,
        SubChange => 20,
        Misc => 21,
        Chat => 22,
        Friend => 23,
        Clan => 24,
        DialogAbort => 25,
        Stock => 26,
        ClanSettings => 27,
        ClanChannel => 28,
        Load => 0,
        MouseOver => 1,
        MouseLeave => 2,
        TargetLeave => 3,
        TargetEnter => 4,
        Variable => 5,
        Inventory => 6,
        Stat => 7,
        Timer => 8,
        Operation => 9,
        MouseRepeat => 10,
        Click => 11,
        ClickRepeat => 12,
        Release => 13,
        Hold => 14,
        Drag => 15,
        DragComplete => 16,
        Scroll => 17,
    });
}
fn body(f: &mut StableFingerprint64, b: &rustclient_model::WidgetBody) {
    use rustclient_model::WidgetBody;
    match b {
        WidgetBody::Container {
            scroll_size,
            no_click_through,
        } => {
            f.write_u8(0);
            for n in scroll_size {
                f.write_u32(*n);
            }
            f.write_u8(u8::from(*no_click_through));
        }
        WidgetBody::Rectangle {
            color,
            filled,
            transparency,
        } => {
            f.write_u8(1);
            f.write_u32(*color);
            f.write_u8(u8::from(*filled));
            f.write_u8(*transparency);
        }
        WidgetBody::Text {
            font,
            text,
            line_height,
            alignment,
            shadowed,
            color,
        } => {
            f.write_u8(2);
            option(f, *font, |f, id| f.write_u32(id.get()));
            string(f, text);
            f.write_u8(*line_height);
            f.write_bytes(alignment);
            f.write_u8(u8::from(*shadowed));
            f.write_u32(*color);
        }
        WidgetBody::Sprite {
            sprite,
            angle,
            tiled,
            transparency,
            outline,
            shadow,
            flip,
        } => {
            f.write_u8(3);
            option(f, *sprite, |f, id| f.write_u32(id.get()));
            f.write_bytes(&angle.to_le_bytes());
            f.write_u8(u8::from(*tiled));
            f.write_u8(*transparency);
            f.write_u8(*outline);
            f.write_u32(*shadow);
            for n in flip {
                f.write_u8(u8::from(*n));
            }
        }
        WidgetBody::Model {
            model,
            offset,
            angles,
            zoom,
            sequence,
            orthographic,
            sizing,
        } => {
            f.write_u8(4);
            option(f, *model, |f, id| f.write_u32(id.get()));
            for n in offset {
                f.write_u32(n.cast_unsigned());
            }
            for n in angles {
                f.write_bytes(&n.to_le_bytes());
            }
            f.write_bytes(&zoom.to_le_bytes());
            option(f, *sequence, |f, id| f.write_u32(id.get()));
            f.write_u8(u8::from(*orthographic));
            for n in sizing {
                f.write_bytes(&n.to_le_bytes());
            }
        }
        WidgetBody::Line {
            width,
            color,
            reversed,
        } => {
            f.write_u8(5);
            f.write_u8(*width);
            f.write_u32(*color);
            f.write_u8(u8::from(*reversed));
        }
    }
}
