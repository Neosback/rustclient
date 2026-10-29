//! Immutable decoded interface definitions, independent of live component state.
use rustclient_model::{InterfaceId, WidgetId};
pub use rustclient_model::{
    PositionMode, SizeMode, WidgetBody, WidgetContent, WidgetDefinition, WidgetLayout,
    WidgetListener,
};
use std::collections::BTreeMap;
#[derive(Debug)]
pub struct InterfaceDefinition {
    pub id: InterfaceId,
    pub(crate) widgets: BTreeMap<WidgetId, WidgetDefinition>,
    pub(crate) children: BTreeMap<Option<WidgetId>, Vec<WidgetId>>,
    pub reports: Vec<crate::AssetReport>,
    pub(crate) legacy: BTreeMap<WidgetId, LegacyPresentation>,
}

impl InterfaceDefinition {
    /// Attach immutable metadata while assembling a decoded definition graph.
    /// # Errors
    /// Rejects metadata for a component outside this graph.
    pub fn with_legacy(
        mut self,
        legacy: BTreeMap<WidgetId, LegacyPresentation>,
    ) -> Result<Self, crate::CacheError> {
        if legacy.keys().any(|id| !self.widgets.contains_key(id)) {
            return Err(
                crate::Source::group(crate::IndexId(3), crate::GroupId(self.id.get()))
                    .error("interface", "legacy metadata has no component"),
            );
        }
        self.legacy = legacy;
        Ok(self)
    }
    /// Authored conditional/tooltip metadata of a legacy component, when present.
    #[must_use]
    pub fn legacy(&self, id: WidgetId) -> Option<&LegacyPresentation> {
        self.legacy.get(&id)
    }
    #[must_use]
    pub const fn widgets(&self) -> &BTreeMap<WidgetId, WidgetDefinition> {
        &self.widgets
    }
    #[must_use]
    pub fn children(&self, parent: Option<WidgetId>) -> &[WidgetId] {
        self.children.get(&parent).map_or(&[], Vec::as_slice)
    }
}

impl InterfaceDefinition {
    /// Assemble a bounded immutable definition graph; used by profile decoders and fixtures.
    /// # Errors
    /// Rejects duplicate/cross-group components, missing parents and cycles.
    pub fn from_widgets(
        id: InterfaceId,
        definitions: Vec<WidgetDefinition>,
    ) -> Result<Self, crate::CacheError> {
        let source = crate::Source::group(crate::IndexId(3), crate::GroupId(id.get()));
        if definitions.len() > 8192 {
            return Err(source.error("interface", "component count limit"));
        }
        let mut widgets = BTreeMap::new();
        for widget in definitions {
            let address = widget.id;
            if address.interface != id || widgets.insert(address, widget).is_some() {
                return Err(source.error("interface", "duplicate or cross-group widget"));
            }
        }
        let mut children = BTreeMap::<_, Vec<_>>::new();
        for widget in widgets.values() {
            let mut visited = std::collections::BTreeSet::from([widget.id]);
            let mut parent = widget.parent;
            while let Some(id) = parent {
                if !visited.insert(id) {
                    return Err(source.error("interface", "cyclic component graph"));
                }
                parent = widgets
                    .get(&id)
                    .ok_or_else(|| source.error("interface", "missing parent"))?
                    .parent;
            }
            children.entry(widget.parent).or_default().push(widget.id);
        }
        Ok(Self {
            id,
            widgets,
            children,
            reports: Vec::new(),
            legacy: BTreeMap::new(),
        })
    }
}

/// Immutable legacy presentation metadata. Expressions are retained for revision-owned interpretation,
/// never treated as a second store of live variables or inventory contents.
#[derive(Debug, Clone)]
pub struct LegacyPresentation {
    pub button: LegacyButton,
    pub button_text: String,
    pub target_text: String,
    pub hover_redirect: Option<WidgetId>,
    pub comparisons: Vec<(u8, i32)>,
    pub expressions: Vec<Vec<i32>>,
    pub alternate: Option<WidgetBody>,
    pub hover_colors: [u32; 2],
    pub tooltip: Option<String>,
}

/// The authored action meaning of a legacy component.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum LegacyButton {
    None,
    Click,
    Target,
    Close,
    Toggle,
    Select,
    Continue,
}
