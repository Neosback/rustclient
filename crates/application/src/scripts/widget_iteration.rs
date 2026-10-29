//! 240 `classLZ` child-query cursor. Addresses are a disposable query, never widget ownership.
use super::{Result, ScriptHost};
use rustclient_cache::script::Opcode;
use rustclient_client_script::{Context, Stacks};
use rustclient_model::{WidgetAddress, WidgetBody};
use rustclient_platform::ReadOnlyBlobs;
use std::collections::{BTreeMap, VecDeque};
#[derive(Default)]
pub(super) struct WidgetIteration {
    entries: VecDeque<WidgetAddress>,
}
#[expect(
    clippy::too_many_lines,
    reason = "one bounded child-query family shares its cursor and traversal order"
)]
pub(super) fn instruction<S: ReadOnlyBlobs>(
    h: &mut ScriptHost<'_, S>,
    op: Opcode,
    n: i32,
    s: &mut Stacks,
    c: &mut Context,
) -> Result<bool> {
    let active = usize::from(n == 1);
    match op {
        Opcode::CcFindchildren | Opcode::CcFindchildrenActive => {
            let (mode, address) = if op == Opcode::CcFindchildren {
                let [mode, widget, child] = s.ints()?;
                (
                    mode,
                    WidgetAddress {
                        widget: h.resources.profile.script_widget(widget)?,
                        child: u32::try_from(child).ok(),
                    },
                )
            } else {
                (s.int()?, c.active[active].ok_or("no active widget")?)
            };
            if !(0..=2).contains(&mode) {
                return Err("widget child query mode".into());
            }
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
            let root = rustclient_game_ui::widgets::resolve_widget(
                h.state.interfaces(),
                &h.resources.interfaces,
                WidgetAddress::component(address.widget),
            )
            .map_err(|e| e.to_string())?;
            if !matches!(root.body, WidgetBody::Container { .. }) {
                return Err("widget child query requires a container".into());
            }
            let mut all = Vec::new();
            let mut by_parent = BTreeMap::<Option<u32>, Vec<WidgetAddress>>::new();
            for (key, child) in h.state.interfaces().runtime() {
                if key.widget == address.widget && key.child.is_some() {
                    all.push(*key);
                    by_parent.entry(child.parent_child).or_default().push(*key);
                }
            }
            let entries = if mode == 0 {
                all
            } else {
                let mut entries = Vec::new();
                let mut pending: Vec<_> = by_parent
                    .get(&address.child)
                    .into_iter()
                    .flatten()
                    .rev()
                    .copied()
                    .collect();
                while let Some(child) = pending.pop() {
                    entries.push(child);
                    if mode == 2 {
                        pending.extend(
                            by_parent
                                .get(&child.child)
                                .into_iter()
                                .flatten()
                                .rev()
                                .copied(),
                        );
                    }
                }
                entries
            };
            s.integers
                .push(i32::try_from(entries.len()).map_err(|_| "widget child query size")?);
            h.resources.widget_iteration.entries = entries.into();
        }
        Opcode::CcFindchildNext => {
            let address = h
                .resources
                .widget_iteration
                .entries
                .pop_front()
                .filter(|address| h.state.interfaces().runtime().contains_key(address));
            s.integers.push(i32::from(address.is_some()));
            if let Some(address) = address {
                c.active[active] = Some(address);
            }
        }
        Opcode::CcFindchildIndexNext => {
            s.integers.push(
                h.resources
                    .widget_iteration
                    .entries
                    .pop_front()
                    .and_then(|address| address.child)
                    .map_or(-1, u32::cast_signed),
            );
        }
        _ => return Ok(false),
    }
    Ok(true)
}
