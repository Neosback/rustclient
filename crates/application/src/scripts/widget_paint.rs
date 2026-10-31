//! Disposable dependencies of the currently painted widget prefix.
use super::ClientScripts;
use rustclient_cache::osrs240::cs1::Query;
use rustclient_game_state::GameState;
use rustclient_game_ui::pointer::DraggedWidget;
use rustclient_platform::ReadOnlyBlobs;
use std::collections::BTreeMap;

/// Only the facts actually requested by this paint are observed. This is not a
/// second widget/state model. Visual asset replacement is an explicit caller invalidation.
pub struct WidgetPaintDependencies {
    interface_revision: u64,
    catalog_revision: u64,
    dragged: Option<DraggedWidget>,
    pub(super) queries: BTreeMap<Query, Result<i32, String>>,
}
impl<S: ReadOnlyBlobs> ClientScripts<S> {
    /// Retain a varbit's owning varp value, so unchanged-frame checks never decode
    /// cache definitions. Changing an unrelated bit may repaint, but cannot go stale.
    pub(super) fn finish_widget_paint_dependencies(
        &self,
        state: &GameState,
        dependencies: &mut WidgetPaintDependencies,
    ) {
        let parents: Vec<_> = dependencies
            .queries
            .iter()
            .filter_map(|(query, value)| match (*query, value) {
                (Query::Varbit(id), Ok(_)) => self
                    .profile
                    .script_varbit(&self.cache, id.get())
                    .ok()
                    .map(|bit| (*query, bit.varp)),
                _ => None,
            })
            .collect();
        for (query, parent) in parents {
            dependencies.queries.remove(&query);
            dependencies
                .queries
                .insert(Query::Varp(parent), Ok(state.scenery().variable(parent)));
        }
    }

    pub(super) fn widget_paint_dependencies(
        &self,
        state: &GameState,
        dragged: Option<DraggedWidget>,
    ) -> WidgetPaintDependencies {
        WidgetPaintDependencies {
            interface_revision: state.interfaces().revision(),
            catalog_revision: self.catalog_revision(),
            dragged,
            queries: BTreeMap::new(),
        }
    }

    /// Check authoritative presentation facts without rebuilding layout, evaluating
    /// expression bytecode or constructing quads. The caller must separately invalidate
    /// on asset replacement. No clocks are read: animated widget bodies are not yet supported.
    #[must_use]
    pub fn widget_paint_is_current(
        &self,
        state: &GameState,
        dependencies: &WidgetPaintDependencies,
        dragged: Option<DraggedWidget>,
    ) -> bool {
        dependencies.interface_revision == state.interfaces().revision()
            && dependencies.catalog_revision == self.catalog_revision()
            && dependencies.dragged == dragged
            && dependencies
                .queries
                .iter()
                .all(|(query, value)| self.cs1_query(state, *query) == *value)
    }
}
