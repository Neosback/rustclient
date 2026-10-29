use super::{Result, ScriptHost};
use rustclient_cache::script::Opcode;
use rustclient_client_script::{Context, Stacks};
use rustclient_model::{
    FontId, InterfaceUpdate, ItemDefinitionId, ScriptValue, SpriteId, WidgetAddress, WidgetBody,
    WidgetListener, WidgetOpKeys, WidgetOperation, WidgetProperty,
};
use rustclient_platform::ReadOnlyBlobs;
#[expect(
    clippy::too_many_lines,
    clippy::many_single_char_names,
    reason = "named widget instruction grammar keeps stack effects next to semantic mutations"
)]
pub(super) fn instruction<S: ReadOnlyBlobs>(
    h: &mut ScriptHost<'_, S>,
    op: Opcode,
    n: i32,
    s: &mut Stacks,
    c: &mut Context,
) -> Result<bool> {
    use Opcode::{
        CcClearops, CcClone, CcCreate, CcCreateNested, CcCreateSibling, CcDelete, CcDeleteall,
        CcFind, CcGetcolour, CcGetheight, CcGethide, CcGetid, CcGetinvcount, CcGetinvobject,
        CcGetlayer, CcGetop, CcGetopbase, CcGetparam, CcGetscrollheight, CcGetscrollwidth,
        CcGetscrollx, CcGetscrolly, CcGettext, CcGetwidth, CcGetx, CcGety, CcSet2Dangle,
        CcSetcolour, CcSetdragdeadtime, CcSetdragdeadzone, CcSetdraggable, CcSetdraggablebehavior,
        CcSetfill, CcSetgraphic, CcSetgraphicshadow, CcSethide, CcSetnoclickthrough, CcSetobject,
        CcSetobjectAlwaysNum, CcSetobjectNonum, CcSetop, CcSetopbase, CcSetoutline, CcSetparam,
        CcSetposition, CcSetscrollpos, CcSetscrollsize, CcSetsize, CcSettargetverb, CcSettext,
        CcSettextalign, CcSettextfont, CcSettextshadow, CcSettiling, CcSettrans, IfFind,
    };
    let active = usize::from(n == 1);
    let (op, explicit) = op.widget();
    match op {
        Opcode::SetkeyscopeActive => {
            let address = c.active[active].ok_or("no active widget")?;
            h.apply(InterfaceUpdate::KeyScope(
                rustclient_model::WidgetKeyScope::Widget(address.widget),
            ))?;
            return Ok(true);
        }
        Opcode::CcFindByParameters => {
            let second_type = s.int()?;
            let first_type = s.int()?;
            let second_value = pop_value(h.resources.profile, s, second_type)?;
            let second_param = s.int()?;
            let first_value = pop_value(h.resources.profile, s, first_type)?;
            let first_param = s.int()?;
            let parent = h.resources.profile.script_widget(s.int()?)?;
            let mut filters = Vec::new();
            for (parameter, value) in [(first_param, first_value), (second_param, second_value)] {
                if let Ok(parameter) = u32::try_from(parameter) {
                    let default = h
                        .resources
                        .profile
                        .parameter_default(&h.resources.cache, parameter)
                        .map_err(|e| e.to_string())?;
                    filters.push((parameter, value, default));
                }
            }
            let found = h
                .state
                .interfaces()
                .runtime()
                .iter()
                .find(|(address, runtime)| {
                    address.widget == parent
                        && address.child.is_some()
                        && filters.iter().all(|(parameter, value, default)| {
                            Some(runtime.parameters.get(parameter).unwrap_or(default))
                                == value.as_ref()
                        })
                })
                .map(|(address, _)| *address);
            s.integers.push(i32::from(found.is_some()));
            if let Some(address) = found {
                c.active[active] = Some(address);
            }
            return Ok(true);
        }
        CcCreate => {
            let [parent, kind, index, reject_replace] = s.ints()?;
            let parent = h.resources.profile.script_widget(parent)?;
            let index = u32::try_from(index).map_err(|_| "negative child index")?;
            h.apply(InterfaceUpdate::CreateChild {
                parent,
                parent_child: None,
                reject_replace: reject_replace != 0,
                index,
                body: h.resources.profile.script_widget_body(kind)?,
            })?;
            c.active[active] = Some(WidgetAddress {
                widget: parent,
                child: Some(index),
            });
            return Ok(true);
        }
        CcClone => {
            let [parent, source, index] = s.ints()?;
            let parent = h.resources.profile.script_widget(parent)?;
            let source = u32::try_from(source).map_err(|_| "clone source")?;
            let index = u32::try_from(index).map_err(|_| "clone destination")?;
            h.apply(InterfaceUpdate::CloneChild {
                parent,
                source,
                index,
            })?;
            c.active[active] = Some(WidgetAddress {
                widget: parent,
                child: Some(index),
            });
            return Ok(true);
        }
        CcCreateNested | CcCreateSibling => {
            let [kind, index] = s.ints()?;
            let current = c.active[active].ok_or("no active widget")?;
            let parent_child = if op == CcCreateNested {
                current.child
            } else {
                h.state
                    .interfaces()
                    .runtime()
                    .get(&current)
                    .and_then(|r| r.parent_child)
            };
            let index = u32::try_from(index).map_err(|_| "nested child index")?;
            h.apply(InterfaceUpdate::CreateChild {
                parent: current.widget,
                parent_child,
                index,
                reject_replace: false,
                body: h.resources.profile.script_widget_body(kind)?,
            })?;
            c.active[active] = Some(WidgetAddress {
                widget: current.widget,
                child: Some(index),
            });
            return Ok(true);
        }
        CcDelete => {
            let address = c.active[active].ok_or("no active widget")?;
            h.apply(InterfaceUpdate::DeleteChild(address))?;
            return Ok(true);
        }
        CcDeleteall => {
            let parent = h.resources.profile.script_widget(s.int()?)?;
            h.apply(InterfaceUpdate::DeleteChildren(parent))?;
            return Ok(true);
        }
        CcFind => {
            let [widget, child] = s.ints()?;
            let widget = h.resources.profile.script_widget(widget)?;
            let child = u32::try_from(child).ok();
            let address = WidgetAddress { widget, child };
            let found = child.is_some() && h.state.interfaces().runtime().contains_key(&address);
            if found {
                c.active[active] = Some(address);
            }
            s.integers.push(i32::from(found));
            return Ok(true);
        }
        IfFind => {
            let widget = h.resources.profile.script_widget(s.int()?)?;
            let found = h
                .resources
                .interfaces
                .get(&widget.interface)
                .is_some_and(|a| a.widgets().contains_key(&widget));
            if found {
                c.active[active] = Some(WidgetAddress::component(widget));
            }
            s.integers.push(i32::from(found));
            return Ok(true);
        }
        Opcode::CcTriggerop => {
            let (address, op_number) = if explicit {
                let op_num = s.int()?;
                let child = s.int()?;
                let widget = h.resources.profile.script_widget(s.int()?)?;
                (
                    WidgetAddress {
                        widget,
                        child: u32::try_from(child).ok(),
                    },
                    op_num,
                )
            } else {
                let op_num = s.int()?;
                let addr = c.active[active].ok_or("no active widget")?;
                (addr, op_num)
            };
            if !(1..=10).contains(&op_number) {
                return Err("widget operation must be 1..=10".into());
            }
            let operation = u8::try_from(
                op_number
                    .checked_sub(1)
                    .ok_or("widget operation must be 1..=10")?,
            )
            .ok()
            .and_then(rustclient_model::WidgetOperation::new)
            .ok_or("widget operation must be 1..=10")?;
            rustclient_game_ui::widgets::resolve_widget(
                h.state.interfaces(),
                &h.resources.interfaces,
                address,
            )
            .map_err(|e| e.to_string())?;
            if h.resources.deferred_operations.len() >= 4096 {
                return Err("deferred widget operation queue limit".into());
            }
            h.resources
                .deferred_operations
                .push_back(rustclient_model::WidgetAction {
                    address,
                    operation,
                    item: h
                        .state
                        .interfaces()
                        .runtime()
                        .get(&address)
                        .and_then(|r| r.item)
                        .map(|(id, _)| id),
                    interface_epoch: h.state.interfaces().epoch(),
                });
            return Ok(true);
        }
        _ => {}
    }
    if !is_widget(op) {
        return Ok(false);
    }
    let param_type = if op == CcSetparam {
        Some(s.int()?)
    } else {
        None
    };
    let address = if explicit && matches!(op, CcSetparam | CcGetparam) {
        let [widget, child] = s.ints()?;
        WidgetAddress {
            widget: h.resources.profile.script_widget(widget)?,
            child: u32::try_from(child).ok(),
        }
    } else if explicit {
        WidgetAddress::component(h.resources.profile.script_widget(s.int()?)?)
    } else {
        c.active[active].ok_or("no active widget")?
    };
    if !h
        .resources
        .interfaces
        .contains_key(&address.widget.interface)
    {
        let group = h
            .resources
            .profile
            .load_interface(&h.resources.cache, address.widget.interface)
            .map_err(|e| e.to_string())?;
        h.resources.interfaces.insert(group.id, group);
    }
    let widget = rustclient_game_ui::widgets::resolve_widget(
        h.state.interfaces(),
        &h.resources.interfaces,
        address,
    )
    .map_err(|e| e.to_string())?;
    if op == Opcode::CcCallonresize {
        if c.resize_depth >= 10 {
            return Err("resize callback depth limit".into());
        }
        if let Some(arguments) = widget.listeners.get(&WidgetListener::Resize)
            && let Some(request) = super::request(arguments.clone())?
        {
            h.resources
                .enqueue_at_depth(request, Some(address), c.resize_depth + 1)?;
        } else {
            c.flow = rustclient_client_script::EventFlow::Finish;
        }
        return Ok(true);
    }
    if op == Opcode::CcResumePausebutton {
        let item = h
            .state
            .interfaces()
            .runtime()
            .get(&address)
            .and_then(|r| r.item)
            .map(|(id, _)| id);
        h.resources
            .effects
            .push(rustclient_protocol::ClientIntent::WidgetAction(
                rustclient_model::WidgetAction {
                    address,
                    operation: WidgetOperation::CONTINUE,
                    item,
                    interface_epoch: h.state.interfaces().epoch(),
                },
            ));
        return Ok(true);
    }
    let mut runtime = h
        .state
        .interfaces()
        .runtime()
        .get(&address)
        .cloned()
        .unwrap_or_default();
    let mut body = widget.body.clone();
    let mut layout = widget.layout;
    if let Some(listener) = listener(op) {
        let mut signature = s.text()?;
        if signature.ends_with('Y') {
            signature.pop();
            let count = usize::try_from(s.int()?).map_err(|_| "listener trigger count")?;
            if count > 4096 {
                return Err("listener trigger limit".into());
            }
            let mut triggers = Vec::new();
            for _ in 0..count {
                triggers.push(s.int()?);
            }
            triggers.reverse();
            runtime.triggers.insert(listener, triggers);
        }
        if signature.len() > 128 {
            return Err("listener argument limit".into());
        }
        let mut args = Vec::new();
        for kind in signature.chars().rev() {
            args.push(match kind {
                's' => ScriptValue::Text(s.text()?),
                'Ï' => ScriptValue::Long(s.long()?),
                _ => ScriptValue::Int(s.int()?),
            });
        }
        args.push(ScriptValue::Int(s.int()?));
        args.reverse();
        let remove = args.first() == Some(&ScriptValue::Int(-1));
        runtime
            .listeners
            .insert(listener, if remove { None } else { Some(args) });
    } else {
        match op {
            CcSetparam => {
                let value = pop_value(h.resources.profile, s, param_type.ok_or("parameter type")?)?
                    .ok_or("absent widget parameter type")?;
                let param = u32::try_from(s.int()?).map_err(|_| "parameter identity")?;
                runtime.parameters.insert(param, value);
            }
            CcGetparam => {
                let param = u32::try_from(s.int()?).map_err(|_| "parameter identity")?;
                let value = if let Some(v) = runtime.parameters.get(&param) {
                    v.clone()
                } else {
                    h.resources
                        .profile
                        .parameter_default(&h.resources.cache, param)
                        .map_err(|e| e.to_string())?
                };
                match value {
                    ScriptValue::Int(n) => s.integers.push(n),
                    ScriptValue::Long(n) => s.longs.push(n),
                    value => s.push_object(value),
                }
                return Ok(true);
            }
            Opcode::CcSetsecondarygraphic => {
                runtime.secondary_sprite = u32::try_from(s.int()?).ok().map(SpriteId::new);
            }
            Opcode::CcDiscardRetiredFlag => {
                s.int()?;
                return Ok(true);
            }
            CcSetposition => {
                let [x, y, a, b] = s.ints()?;
                layout.offset = [x, y];
                layout.position_mode = [
                    h.resources.profile.script_widget_position(a)?,
                    h.resources.profile.script_widget_position(b)?,
                ];
                runtime.layout = Some(layout);
            }
            CcSetsize => {
                let [x, y, a, b] = s.ints()?;
                layout.size = [x, y];
                layout.size_mode = [
                    h.resources.profile.script_widget_size(a)?,
                    h.resources.profile.script_widget_size(b)?,
                ];
                runtime.layout = Some(layout);
            }
            CcSethide => runtime.hidden = Some(s.int()? == 1),
            CcSetscrollpos => {
                let [x, y] = s.ints()?;
                runtime.scroll = Some([x.max(0).cast_unsigned(), y.max(0).cast_unsigned()]);
            }
            CcSetscrollsize => {
                let [x, y] = s.ints()?;
                if let WidgetBody::Container { scroll_size, .. } = &mut body {
                    *scroll_size = [x.max(0).cast_unsigned(), y.max(0).cast_unsigned()];
                }
                runtime.body = Some(body);
            }
            CcSettext => {
                runtime.text = Some(s.text()?);
            }
            CcSettextfont => {
                let value = s.int()?;
                if let WidgetBody::Text { font, .. } = &mut body {
                    *font = u32::try_from(value).ok().map(FontId::new);
                }
                runtime.body = Some(body);
            }
            CcSettextalign => {
                let [x, y, line] = s.ints()?;
                if let WidgetBody::Text {
                    alignment,
                    line_height,
                    ..
                } = &mut body
                {
                    *alignment = [
                        u8::try_from(x).map_err(|_| "text alignment")?,
                        u8::try_from(y).map_err(|_| "text alignment")?,
                    ];
                    *line_height = u8::try_from(line).map_err(|_| "text line height")?;
                }
                runtime.body = Some(body);
            }
            CcSettextshadow => {
                let value = s.int()?;
                if let WidgetBody::Text { shadowed, .. } = &mut body {
                    *shadowed = value == 1;
                }
                runtime.body = Some(body);
            }
            CcSetcolour => {
                let value = s.int()?.cast_unsigned();
                match &mut body {
                    WidgetBody::Rectangle { color, .. }
                    | WidgetBody::Text { color, .. }
                    | WidgetBody::Line { color, .. } => *color = value,
                    _ => {}
                }
                runtime.body = Some(body);
            }
            CcSettrans => {
                let value = s.int()?;
                match &mut body {
                    WidgetBody::Rectangle { transparency, .. }
                    | WidgetBody::Sprite { transparency, .. } => {
                        *transparency = u8::try_from(value).map_err(|_| "transparency")?;
                    }
                    _ => {}
                }
                runtime.body = Some(body);
            }
            Opcode::CcSetlinewid => {
                let value = s.int()?;
                if let WidgetBody::Line { width, .. } = &mut body {
                    *width = u8::try_from(value).map_err(|_| "line width")?;
                }
                runtime.body = Some(body);
            }
            Opcode::CcSetlinedirection => {
                let value = s.int()? == 1;
                if let WidgetBody::Line { reversed, .. } = &mut body {
                    *reversed = value;
                }
                runtime.body = Some(body);
            }
            CcSetfill => {
                let value = s.int()?;
                if let WidgetBody::Rectangle { filled, .. } = &mut body {
                    *filled = value == 1;
                }
                runtime.body = Some(body);
            }
            CcSetgraphic => {
                let value = s.int()?;
                if let WidgetBody::Sprite { sprite, .. } = &mut body {
                    *sprite = u32::try_from(value).ok().map(SpriteId::new);
                }
                runtime.body = Some(body);
            }
            CcSetoutline => {
                let value = s.int()?;
                if let WidgetBody::Sprite { outline, .. } = &mut body {
                    *outline = u8::try_from(value).map_err(|_| "outline")?;
                }
                runtime.body = Some(body);
            }
            CcSetgraphicshadow => {
                let value = s.int()?.cast_unsigned();
                if let WidgetBody::Sprite { shadow, .. } = &mut body {
                    *shadow = value;
                }
                runtime.body = Some(body);
            }
            CcSettiling => {
                let value = s.int()?;
                if let WidgetBody::Sprite { tiled, .. } = &mut body {
                    *tiled = value == 1;
                }
                runtime.body = Some(body);
            }
            CcSet2Dangle => {
                let value = s.int()?;
                if let WidgetBody::Sprite { angle, .. } = &mut body {
                    *angle = u16::try_from(value).map_err(|_| "sprite angle")?;
                }
                runtime.body = Some(body);
            }
            CcSetnoclickthrough => {
                let value = s.int()?;
                if let WidgetBody::Container {
                    no_click_through, ..
                } = &mut body
                {
                    *no_click_through = value == 1;
                }
                runtime.body = Some(body);
            }
            Opcode::CcSetnoscrollthrough => runtime.no_scroll_through = Some(s.int()? == 1),
            Opcode::CcSetmodel => {
                let id = s.int()?;
                if let WidgetBody::Model { model, .. } = &mut body {
                    *model = u32::try_from(id).ok().map(rustclient_model::ModelId::new);
                }
                runtime.item = None;
                runtime.body = Some(body);
            }
            Opcode::CcSetmodelangle => {
                let [x, y, angle_x, angle_y, angle_z, scale] = s.ints()?;
                if let WidgetBody::Model {
                    offset,
                    angles,
                    zoom,
                    ..
                } = &mut body
                {
                    *offset = [x, y];
                    *angles = [
                        model_scalar(angle_x)?,
                        model_scalar(angle_y)?,
                        model_scalar(angle_z)?,
                    ];
                    *zoom = model_scalar(scale)?;
                }
                runtime.body = Some(body);
            }
            Opcode::CcSetmodelanim => {
                let id = s.int()?;
                if let WidgetBody::Model { sequence, .. } = &mut body {
                    *sequence = u32::try_from(id)
                        .ok()
                        .map(rustclient_model::SequenceId::new);
                }
                runtime.body = Some(body);
            }
            Opcode::CcSetmodelorthog => {
                let enabled = s.int()? == 1;
                if let WidgetBody::Model { orthographic, .. } = &mut body {
                    *orthographic = enabled;
                }
                runtime.body = Some(body);
            }
            CcSetobject | CcSetobjectNonum | CcSetobjectAlwaysNum => {
                let [id, count] = s.ints()?;
                runtime.item = u32::try_from(id)
                    .ok()
                    .map(|id| (ItemDefinitionId::new(id), count.max(0).cast_unsigned()));
            }
            Opcode::CcSettargetpriority => {
                let n = s.int()?;
                runtime.target_priority = if n == -1 {
                    None
                } else {
                    Some(
                        WidgetOperation::new(u8::try_from(n - 1).map_err(|_| "target priority")?)
                            .ok_or("target priority")?,
                    )
                };
            }
            Opcode::CcSethflip | Opcode::CcSetvflip => {
                let value = s.int()?;
                if let WidgetBody::Sprite { flip, .. } = &mut body {
                    flip[usize::from(op == Opcode::CcSetvflip)] = value == 1;
                }
                runtime.body = Some(body);
            }
            Opcode::CcGettargetmask => {
                let permissions = h
                    .state
                    .interfaces()
                    .permissions(address.widget, address.child.map_or(-1, u32::cast_signed))
                    .unwrap_or(widget.permissions);
                s.integers.push(i32::from(permissions.target_mask));
                return Ok(true);
            }
            CcSetop => {
                let text = s.text()?;
                let slot = usize::try_from(s.int()? - 1).map_err(|_| "operation slot")?;
                if slot >= 32 {
                    return Err("operation slot limit".into());
                }
                let actions = runtime
                    .actions
                    .get_or_insert_with(|| widget.actions.clone());
                actions.resize(actions.len().max(slot + 1), String::new());
                actions[slot] = text;
            }
            CcClearops => runtime.actions = Some(Vec::new()),
            CcSetopbase => runtime.data_text = Some(s.text()?),
            CcSettargetverb => {
                s.text()?;
            }
            // Slot arguments are 1-based for numbered operations, as in the reference client.
            Opcode::CcSetopkey | Opcode::CcSetoptkey => {
                let [key, modifier] = s.ints()?;
                let slot = op_key_slot(s, op == Opcode::CcSetoptkey)?;
                // Reference bytes are truncating casts of the script integers.
                runtime.op_keys.entry(slot).or_default().keys =
                    vec![(low_byte(key), low_byte(modifier))];
            }
            Opcode::CcSetopkeyrate | Opcode::CcSetoptkeyrate => {
                let target = op == Opcode::CcSetoptkeyrate;
                // The reference pops the target variant's pair in the opposite order.
                let [first, second] = s.ints()?;
                let rate = if target {
                    [second, first]
                } else {
                    [first, second]
                };
                let slot = op_key_slot(s, target)?;
                runtime.op_keys.entry(slot).or_default().rate = Some(rate);
            }
            Opcode::CcSetopkeyignoreheld | Opcode::CcSetoptkeyignoreheld => {
                let slot = op_key_slot(s, op == Opcode::CcSetoptkeyignoreheld)?;
                runtime.op_keys.entry(slot).or_default().ignore_held = true;
            }
            CcSetdraggable => {
                let [parent, child] = s.ints()?;
                let parent = h.resources.profile.script_widget(parent)?;
                let target = WidgetAddress {
                    widget: parent,
                    child: u32::try_from(child).ok(),
                };
                let present = rustclient_game_ui::widgets::resolve_widget(
                    h.state.interfaces(),
                    &h.resources.interfaces,
                    target,
                )
                .is_ok();
                drag_config(&mut runtime, &widget).parent = present.then_some(target);
            }
            CcSetdragdeadtime | CcSetdragdeadzone | CcSetdraggablebehavior => {
                let value = s.int()?;
                let drag = drag_config(&mut runtime, &widget);
                match op {
                    CcSetdragdeadtime => drag.threshold = value,
                    CcSetdragdeadzone => drag.zone = value,
                    _ => drag.scrollbar = value == 1,
                }
            }
            CcGetx | CcGety | CcGetwidth | CcGetheight => {
                let bounds = rustclient_game_ui::widgets::widget_bounds(
                    h.state.interfaces(),
                    &h.resources.interfaces,
                    address,
                    rustclient_game_ui::widgets::WidgetBounds::canvas(h.state.interfaces()),
                )
                .map_err(|e| e.to_string())?;
                s.integers.push(match op {
                    CcGetx => bounds.x,
                    CcGety => bounds.y,
                    CcGetwidth => bounds.width,
                    _ => bounds.height,
                });
                return Ok(true);
            }
            CcGethide => {
                s.integers.push(i32::from(widget.hidden));
                return Ok(true);
            }
            CcGetid => {
                s.integers.push(address.child.map_or(-1, u32::cast_signed));
                return Ok(true);
            }
            CcGetlayer => {
                s.integers.push(
                    widget
                        .parent
                        .map_or(-1, |id| h.resources.profile.script_widget_number(id)),
                );
                return Ok(true);
            }
            CcGettext => {
                s.push_object(ScriptValue::Text(
                    if let WidgetBody::Text { text, .. } = body {
                        text
                    } else {
                        String::new()
                    },
                ));
                return Ok(true);
            }
            CcGetopbase => {
                s.push_object(ScriptValue::Text(widget.data_text));
                return Ok(true);
            }
            CcGetop => {
                let index = usize::try_from(s.int()? - 1).map_err(|_| "widget action index")?;
                s.push_object(ScriptValue::Text(
                    widget.actions.get(index).cloned().unwrap_or_default(),
                ));
                return Ok(true);
            }
            CcGetinvobject | CcGetinvcount => {
                s.integers.push(runtime.item.map_or(
                    if op == CcGetinvobject { -1 } else { 0 },
                    |(id, n)| {
                        if op == CcGetinvobject {
                            id.get().cast_signed()
                        } else {
                            n.cast_signed()
                        }
                    },
                ));
                return Ok(true);
            }
            CcGetscrollx | CcGetscrolly => {
                let axis = usize::from(op == CcGetscrolly);
                s.integers
                    .push(runtime.scroll.map_or(0, |a| a[axis].cast_signed()));
                return Ok(true);
            }
            CcGetscrollwidth | CcGetscrollheight => {
                s.integers
                    .push(if let WidgetBody::Container { scroll_size, .. } = body {
                        scroll_size[usize::from(op == CcGetscrollheight)].cast_signed()
                    } else {
                        0
                    });
                return Ok(true);
            }
            CcGetcolour => {
                s.integers.push(match body {
                    WidgetBody::Rectangle { color, .. }
                    | WidgetBody::Text { color, .. }
                    | WidgetBody::Line { color, .. } => color.cast_signed(),
                    _ => 0,
                });
                return Ok(true);
            }
            _ => return Ok(false),
        }
    }
    h.apply(InterfaceUpdate::SetProperty {
        widget: address.widget,
        property: WidgetProperty::Runtime {
            address,
            value: Box::new(runtime),
        },
    })?;
    Ok(true)
}
/// The reference stores key codes as truncated bytes.
fn low_byte(value: i32) -> u8 {
    u8::try_from(value & 0xFF).unwrap_or_default()
}
/// Pops the 1-based operation slot (`0..=9`), or selects the target slot for `t` variants.
fn drag_config<'a>(
    runtime: &'a mut rustclient_model::WidgetRuntime,
    widget: &rustclient_model::WidgetDefinition,
) -> &'a mut rustclient_model::WidgetDrag {
    runtime.drag.get_or_insert(rustclient_model::WidgetDrag {
        parent: None,
        zone: i32::from(widget.drag_zone),
        threshold: i32::from(widget.drag_threshold),
        scrollbar: widget.scrollbar,
    })
}
fn op_key_slot(s: &mut Stacks, target: bool) -> Result<u8> {
    if target {
        return Ok(WidgetOpKeys::TARGET_SLOT);
    }
    u8::try_from(s.int()? - 1)
        .ok()
        .filter(|slot| *slot < WidgetOpKeys::TARGET_SLOT)
        .ok_or_else(|| "operation key slot".into())
}
fn is_widget(op: Opcode) -> bool {
    format!("{op:?}").starts_with("Cc")
}
fn listener(op: Opcode) -> Option<WidgetListener> {
    use Opcode::{
        CcSetonchattransmit, CcSetonclick, CcSetonclickrepeat, CcSetondrag, CcSetondragcomplete,
        CcSetonhold, CcSetoninvtransmit, CcSetonkey, CcSetonmisctransmit, CcSetonmouseleave,
        CcSetonmouseover, CcSetonmouserepeat, CcSetonop, CcSetonrelease, CcSetonresize,
        CcSetonscrollwheel, CcSetonstattransmit, CcSetonsubchange, CcSetontargetenter,
        CcSetontargetleave, CcSetontimer, CcSetonvartransmit,
    };
    Some(match op {
        Opcode::CcSetonfriendtransmit => WidgetListener::Friend,
        Opcode::CcSetonclantransmit => WidgetListener::Clan,
        Opcode::CcSetondialogabort => WidgetListener::DialogAbort,
        Opcode::CcSetonstocktransmit => WidgetListener::Stock,
        Opcode::CcSetonkeypress => WidgetListener::KeyPress,
        Opcode::CcSetonkeyrelease => WidgetListener::KeyRelease,
        Opcode::CcSetonclansettingstransmit => WidgetListener::ClanSettings,
        Opcode::CcSetonclanchanneltransmit => WidgetListener::ClanChannel,
        CcSetonkey => WidgetListener::Key,
        CcSetonresize => WidgetListener::Resize,
        CcSetonsubchange => WidgetListener::SubChange,
        CcSetonmisctransmit => WidgetListener::Misc,
        CcSetonchattransmit => WidgetListener::Chat,
        CcSetonclick => WidgetListener::Click,
        CcSetonhold => WidgetListener::Hold,
        CcSetonrelease => WidgetListener::Release,
        CcSetonmouseover => WidgetListener::MouseOver,
        CcSetonmouseleave => WidgetListener::MouseLeave,
        CcSetondrag => WidgetListener::Drag,
        CcSetontargetleave => WidgetListener::TargetLeave,
        CcSetonvartransmit => WidgetListener::Variable,
        CcSetontimer => WidgetListener::Timer,
        CcSetonop => WidgetListener::Operation,
        CcSetondragcomplete => WidgetListener::DragComplete,
        CcSetonclickrepeat => WidgetListener::ClickRepeat,
        CcSetonmouserepeat => WidgetListener::MouseRepeat,
        CcSetoninvtransmit => WidgetListener::Inventory,
        CcSetonstattransmit => WidgetListener::Stat,
        CcSetontargetenter => WidgetListener::TargetEnter,
        CcSetonscrollwheel => WidgetListener::Scroll,
        _ => return None,
    })
}

pub(super) fn pop_value(
    profile: rustclient_cache::CacheProfile,
    stacks: &mut Stacks,
    kind: i32,
) -> Result<Option<ScriptValue>> {
    use rustclient_cache::osrs240::scripts::ScriptValueType;
    Ok(match profile.script_value_type(kind)? {
        Some(ScriptValueType::Integer) => Some(ScriptValue::Int(stacks.int()?)),
        Some(ScriptValueType::Long) => Some(ScriptValue::Long(stacks.long()?)),
        Some(ScriptValueType::Text) => Some(ScriptValue::Text(stacks.text()?)),
        None => None,
    })
}
fn model_scalar(value: i32) -> Result<u16> {
    u16::try_from(value).map_err(|_| "widget model scalar outside retained range".into())
}

#[cfg(test)]
mod tests {
    use super::*;

    fn stacks(ints: &[i32]) -> Stacks {
        let mut s = Stacks::default();
        s.integers.extend_from_slice(ints);
        s
    }

    #[test]
    fn operation_key_slots_are_one_based_and_target_variants_use_the_eleventh() {
        assert_eq!(op_key_slot(&mut stacks(&[1]), false), Ok(0));
        assert_eq!(op_key_slot(&mut stacks(&[10]), false), Ok(9));
        assert!(op_key_slot(&mut stacks(&[0]), false).is_err());
        assert!(op_key_slot(&mut stacks(&[11]), false).is_err());
        let mut untouched = stacks(&[3]);
        assert_eq!(
            op_key_slot(&mut untouched, true),
            Ok(WidgetOpKeys::TARGET_SLOT)
        );
        assert_eq!(untouched.integers, [3], "target variants pop no slot");
    }

    #[test]
    fn triggerop_executes_action_on_active_or_specified_widget() {
        let mut resources = crate::scripts::test_support::scripts();
        let mut state = rustclient_game_state::GameState::new();
        let mut host = super::ScriptHost {
            resources: &mut resources,
            state: &mut state,
        };
        let mut s = Stacks::default();
        let mut c = rustclient_client_script::Context::default();
        let packed_widget = (160 << 16) | 0x37;
        s.integers.push(packed_widget);
        s.integers.push(-1);
        s.integers.push(1);
        let handled = instruction(
            &mut host,
            rustclient_cache::script::Opcode::IfTriggerop,
            0,
            &mut s,
            &mut c,
        );
        assert!(
            handled.is_err(),
            "unresolved widget triggers must fail explicitly"
        );
    }
}
