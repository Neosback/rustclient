//! Group resource residency and authored open events have distinct lifetimes.
use super::{ClientScripts, Result, request};
use rustclient_game_state::{GameState, SemanticOperation};
use rustclient_model::{InterfaceId, InterfaceUpdate, WidgetAddress, WidgetListener};
use rustclient_platform::ReadOnlyBlobs;
use std::collections::{BTreeMap, BTreeSet};

impl<S: ReadOnlyBlobs> ClientScripts<S> {
    /// Load newly visible groups after callers commit state directly.
    /// # Errors
    /// Propagates missing definitions, malformed listeners and queue limits.
    pub fn synchronize(&mut self, state: &GameState) -> Result<()> {
        self.synchronize_with_loads(state, &[])
    }

    pub(super) fn interface_load_groups(
        state: &GameState,
        operation: &SemanticOperation,
    ) -> Vec<InterfaceId> {
        // IF_OPENTOP/IF_OPENSUB invoke classPQ.method9181 on every open.
        // IF_RESYNC_V2 only invokes the setup helpers for changed root/mount groups.
        match operation {
            SemanticOperation::Interface(InterfaceUpdate::SetRoot(group)) => {
                group.iter().copied().collect()
            }
            SemanticOperation::Interface(InterfaceUpdate::Open(mount)) => vec![mount.interface],
            SemanticOperation::Interface(InterfaceUpdate::Resync { root, mounts, .. }) => {
                let mut groups = Vec::new();
                if state.interfaces().root() != *root {
                    groups.extend(root);
                }
                let mut previous: BTreeMap<_, _> = state
                    .interfaces()
                    .mounts()
                    .iter()
                    .map(|(parent, mount)| (*parent, mount.interface))
                    .collect();
                for mount in mounts {
                    if previous.insert(mount.parent, mount.interface) != Some(mount.interface) {
                        groups.push(mount.interface);
                    }
                }
                groups
            }
            _ => Vec::new(),
        }
    }

    pub(super) fn synchronize_with_loads(
        &mut self,
        state: &GameState,
        explicit: &[InterfaceId],
    ) -> Result<()> {
        let current = state.interfaces().groups();
        self.loaded_groups.retain(|group| current.contains(group));
        self.layout_sizes
            .retain(|address, _| current.contains(&address.widget.interface));
        let new_groups: Vec<_> = current.difference(&self.loaded_groups).copied().collect();
        for group in &new_groups {
            if !self.interfaces.contains_key(group) {
                self.interfaces.insert(
                    *group,
                    self.profile
                        .load_interface(&self.cache, *group)
                        .map_err(|error| error.to_string())?,
                );
            }
        }
        // Explicit opens retain wire order and multiplicity. A newly resident group
        // already named by an open must not receive an additional synthetic load.
        let opened: BTreeSet<_> = explicit.iter().copied().collect();
        for group in new_groups.iter().filter(|group| !opened.contains(group)) {
            self.enqueue_group_load(*group)?;
        }
        for group in explicit.iter().filter(|group| current.contains(group)) {
            self.enqueue_group_load(*group)?;
        }
        self.loaded_groups.extend(new_groups);
        Ok(())
    }

    fn enqueue_group_load(&mut self, group: InterfaceId) -> Result<()> {
        // Load listeners belong to base cache components, not script-created children.
        let listeners: Vec<_> = self
            .interfaces
            .get(&group)
            .ok_or_else(|| format!("missing loaded interface {}", group.get()))?
            .widgets()
            .values()
            .filter_map(|widget| {
                widget
                    .listeners
                    .get(&WidgetListener::Load)
                    .map(|args| (widget.id, args.clone()))
            })
            .collect();
        for (widget, args) in listeners {
            if let Some(request) = request(args)? {
                self.enqueue(request, Some(WidgetAddress::component(widget)))?;
            }
        }
        Ok(())
    }
}
