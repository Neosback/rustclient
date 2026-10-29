//! Immutable search projection of item definitions; stock 240 `classOG.method8468`.
use crate::{
    Cache, CacheProfile, FileId, GroupId, IndexId, Source, error::Result, world::ItemDefinition,
};
use rustclient_model::ItemDefinitionId;
use rustclient_platform::ReadOnlyBlobs;
use std::collections::BTreeMap;

/// Maximum successful result count; the next match returns the stock overflow sentinel.
pub const MAX_RESULTS: usize = 250;

#[derive(Debug)]
struct Entry {
    id: ItemDefinitionId,
    name: String,
    folded: String,
    tradeable: bool,
}

/// Rebuildable search data for one cache/profile and membership context.
#[derive(Debug)]
pub struct ItemSearchIndex {
    entries: Vec<Entry>,
}

impl ItemSearchIndex {
    /// Build a search projection without retaining another mutable definition catalog.
    /// # Errors
    /// Rejects IDs outside the stock unsigned-short result space and cyclic/missing bought links.
    pub fn from_definitions(definitions: &[ItemDefinition], members: bool) -> Result<Self> {
        let source = Source::group(IndexId(2), GroupId(10));
        let by_id: BTreeMap<_, _> = definitions.iter().map(|d| (d.id, d)).collect();
        if by_id.len() != definitions.len() || by_id.keys().any(|id| id.get() > 65535) {
            return Err(source.error("item search", "duplicate or out-of-range definition"));
        }
        let mut entries = Vec::new();
        for definition in by_id.values() {
            if definition.note_template.is_some() || definition.placeholder_template.is_some() {
                continue;
            }
            let (name, tradeable) = effective_name(definition, &by_id, members, 0)?;
            entries.push(Entry {
                id: definition.id,
                folded: name.to_lowercase(),
                name,
                tradeable,
            });
        }
        Ok(Self { entries })
    }

    /// Find exact stock-ordered IDs; `None` is the stock `-1` overflow result.
    /// Empty input is a valid substring query, not a special empty result.
    #[must_use]
    pub fn find(&self, query: &str, tradeable_only: bool) -> Option<Vec<ItemDefinitionId>> {
        let query = query.to_lowercase();
        let mut found = Vec::new();
        for entry in &self.entries {
            if (!tradeable_only || entry.tradeable) && entry.folded.contains(&query) {
                if found.len() == MAX_RESULTS {
                    return None;
                }
                found.push(entry);
            }
        }
        // The stock quicksort deliberately alternates equal-name ties by the absolute index.
        // A stable sort (or sorting the whole catalog in advance) changes duplicate-name results.
        if !found.is_empty() {
            let last = found.len() - 1;
            stock_sort(&mut found, 0, last);
        }
        Some(found.into_iter().map(|entry| entry.id).collect())
    }
}

fn effective_name(
    definition: &ItemDefinition,
    definitions: &BTreeMap<ItemDefinitionId, &ItemDefinition>,
    members: bool,
    depth: usize,
) -> Result<(String, bool)> {
    let source = Source {
        index: IndexId(2),
        group: Some(GroupId(10)),
        file: Some(FileId(definition.id.get())),
    };
    if depth >= 32 {
        return Err(source.error("item search", "cyclic or excessive bought-item links"));
    }
    if definition.bought_template.is_some() {
        let parent = definition
            .bought
            .and_then(|id| definitions.get(&id))
            .ok_or_else(|| source.error("item search", "missing bought-item base"))?;
        let (name, _) = effective_name(parent, definitions, members, depth + 1)?;
        return Ok((name, false));
    }
    let mut name = definition.name.clone().unwrap_or_else(|| "null".into());
    let restricted = definition.members && !members;
    if restricted {
        name.push_str(" (Members)");
    }
    Ok((name, definition.tradeable && !restricted))
}

fn stock_sort(entries: &mut [&Entry], low: usize, high: usize) {
    if low >= high {
        return;
    }
    let middle = usize::midpoint(low, high);
    entries.swap(middle, high);
    let mut split = low;
    for index in low..high {
        let comparison = entries[index]
            .name
            .encode_utf16()
            .cmp(entries[high].name.encode_utf16());
        if comparison.is_lt() || (index & 1 == 1 && comparison.is_eq()) {
            entries.swap(index, split);
            split += 1;
        }
    }
    entries.swap(split, high);
    if split > low {
        stock_sort(entries, low, split - 1);
    }
    if split < high {
        stock_sort(entries, split + 1, high);
    }
}

impl CacheProfile {
    /// Decode the actual config archive once; sparse IDs retain stock default definitions.
    /// # Errors
    /// Returns contextual cache/definition failures; a partial index is never returned.
    pub fn load_item_search_index<S: ReadOnlyBlobs>(
        self,
        cache: &Cache<S>,
        members: bool,
    ) -> Result<ItemSearchIndex> {
        cache.config_group_with(10, self == Self::OpenRune240, &mut Vec::new(), |files| {
            let count = files
                .keys()
                .next_back()
                .map_or(0, |id| id.0.saturating_add(1));
            if count > 65536 {
                return Err(Source::group(IndexId(2), GroupId(10))
                    .error("item search", "item archive exceeds result ID range"));
            }
            let definitions = (0..count)
                .map(|id| {
                    super::definitions::item(
                        files.get(&FileId(id)).map_or(&[0][..], Vec::as_slice),
                        ItemDefinitionId::new(id),
                        Source {
                            index: IndexId(2),
                            group: Some(GroupId(10)),
                            file: Some(FileId(id)),
                        },
                    )
                })
                .collect::<Result<Vec<_>>>()?;
            ItemSearchIndex::from_definitions(&definitions, members)
        })
    }
}
