//! Cached listener candidates; callbacks always resolve their current authoritative values.
use super::{ClientScripts, Result, request};
use rustclient_cache::widget::InterfaceDefinition;
use rustclient_game_state::GameState;
use rustclient_model::{InterfaceId, WidgetAddress, WidgetListener};
use rustclient_platform::ReadOnlyBlobs;
use std::collections::{BTreeMap, BTreeSet};

#[derive(Default)]
pub(super) struct ListenerIndex {
    key: Option<(u64, u64, u64)>,
    candidates: BTreeMap<WidgetListener, Vec<WidgetAddress>>,
    rebuilds: u64,
}
impl ListenerIndex {
    fn ensure(
        &mut self,
        state: &GameState,
        definitions: &BTreeMap<InterfaceId, InterfaceDefinition>,
        catalog: u64,
    ) {
        let interfaces = state.interfaces();
        let key = (interfaces.epoch(), interfaces.listener_revision(), catalog);
        if self.key == Some(key) {
            return;
        }
        self.candidates.clear();
        let groups = interfaces.groups();
        let mut addresses = BTreeSet::new();
        for group in &groups {
            if let Some(definition) = definitions.get(group) {
                addresses.extend(
                    definition
                        .widgets()
                        .keys()
                        .copied()
                        .map(WidgetAddress::component),
                );
            }
        }
        addresses.extend(
            interfaces
                .runtime()
                .keys()
                .filter(|address| {
                    address.child.is_some() && groups.contains(&address.widget.interface)
                })
                .copied(),
        );
        for address in addresses {
            let base = definitions
                .get(&address.widget.interface)
                .and_then(|group| group.widgets().get(&address.widget));
            let runtime = interfaces.runtime().get(&address);
            let kinds: BTreeSet<_> = base
                .into_iter()
                .filter(|_| address.child.is_none())
                .flat_map(|widget| widget.listeners.keys())
                .chain(
                    runtime
                        .into_iter()
                        .flat_map(|widget| widget.listeners.keys()),
                )
                .copied()
                .collect();
            for kind in kinds {
                self.candidates.entry(kind).or_default().push(address);
            }
        }
        self.key = Some(key);
        self.rebuilds = self.rebuilds.saturating_add(1);
    }
    fn addresses(&self, kind: WidgetListener) -> &[WidgetAddress] {
        self.candidates.get(&kind).map_or(&[], Vec::as_slice)
    }
}
impl<S: ReadOnlyBlobs> ClientScripts<S> {
    /// Number of disposable listener-index rebuilds, for bounded diagnostics.
    #[must_use]
    pub fn listener_index_rebuilds(&self) -> u64 {
        self.listener_index.rebuilds
    }
    /// Queue current listeners in source address order, preserving trigger filters.
    /// # Errors
    /// Reports malformed listener arguments or the existing event-queue bound.
    pub fn notify(
        &mut self,
        state: &GameState,
        kind: WidgetListener,
        changed: Option<i32>,
    ) -> Result<()> {
        self.listener_index
            .ensure(state, &self.interfaces, self.catalog_revision);
        let mut pending = Vec::new();
        for &address in self.listener_index.addresses(kind) {
            let runtime = state.interfaces().runtime().get(&address);
            let base = self
                .interfaces
                .get(&address.widget.interface)
                .and_then(|group| group.widgets().get(&address.widget));
            let args = runtime.and_then(|r| r.listeners.get(&kind)).map_or_else(
                || {
                    if address.child.is_none() {
                        base.and_then(|w| w.listeners.get(&kind))
                    } else {
                        None
                    }
                },
                Option::as_ref,
            );
            if let Some(args) = args {
                let triggers = runtime.and_then(|r| r.triggers.get(&kind)).map_or_else(
                    || {
                        if address.child.is_none() {
                            base.map_or(&[][..], |w| match kind {
                                WidgetListener::Inventory => w.inventory_triggers.as_slice(),
                                WidgetListener::Variable => w.variable_triggers.as_slice(),
                                WidgetListener::Stat => w.stat_triggers.as_slice(),
                                _ => &[],
                            })
                        } else {
                            &[]
                        }
                    },
                    Vec::as_slice,
                );
                if triggers.is_empty() || changed.is_none_or(|c| triggers.contains(&c)) {
                    pending.push((address, args.clone()));
                }
            }
        }
        for (widget, args) in pending {
            if let Some(request) = request(args)? {
                self.enqueue(request, Some(widget))?;
            }
        }
        Ok(())
    }
}
