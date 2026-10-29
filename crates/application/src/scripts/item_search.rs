//! Session cursor over an immutable revision-owned item search projection.
use super::{Result, ScriptHost};
use rustclient_cache::{CacheProfile, osrs240::item_search::ItemSearchIndex, script::Opcode};
use rustclient_client_script::Stacks;
use rustclient_model::ItemDefinitionId;
use rustclient_platform::ReadOnlyBlobs;

#[derive(Default)]
pub(super) struct ItemSearch {
    context: Option<(CacheProfile, String, bool)>,
    index: Option<ItemSearchIndex>,
    results: Vec<ItemDefinitionId>,
    cursor: usize,
}

pub(super) fn instruction<S: ReadOnlyBlobs>(
    h: &mut ScriptHost<'_, S>,
    op: Opcode,
    stacks: &mut Stacks,
) -> Result<bool> {
    match op {
        Opcode::OcFind => {
            let tradeable = stacks.int()? == 1;
            let query = stacks.text()?;
            let members = h
                .state
                .authenticated_player()
                .is_some_and(|player| player.member);
            let search = &mut h.resources.item_search;
            if !search
                .context
                .as_ref()
                .is_some_and(|(profile, fingerprint, member)| {
                    *profile == h.resources.profile
                        && fingerprint == h.resources.cache.metadata_fingerprint()
                        && *member == members
                })
            {
                let index = h
                    .resources
                    .profile
                    .load_item_search_index(&h.resources.cache, members)
                    .map_err(|e| e.to_string())?;
                search.index = Some(index);
                search.context = Some((
                    h.resources.profile,
                    h.resources.cache.metadata_fingerprint().to_owned(),
                    members,
                ));
            }
            let found = search
                .index
                .as_ref()
                .ok_or("item search index absent")?
                .find(&query, tradeable);
            let count = found
                .as_ref()
                .map_or(-1, |ids| i32::try_from(ids.len()).unwrap_or(-1));
            search.results = found.unwrap_or_default();
            search.cursor = 0;
            stacks.integers.push(count);
        }
        Opcode::OcFindnext => {
            let search = &mut h.resources.item_search;
            let id = search
                .results
                .get(search.cursor)
                .map_or(-1, |id| id.get().cast_signed());
            if id >= 0 {
                search.cursor += 1;
            }
            stacks.integers.push(id);
        }
        Opcode::OcFindreset => h.resources.item_search.cursor = 0,
        _ => return Ok(false),
    }
    Ok(true)
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_cache::{GroupId, IndexId, Source, osrs240::definitions::item};
    use rustclient_game_state::GameState;
    use rustclient_model::ScriptValue;

    #[test]
    fn host_find_next_reset_exhaustion_and_session_reset_use_one_index() {
        let mut resources = super::super::test_support::scripts();
        let definition = item(
            &[2, b'A', 0, 65, 0],
            ItemDefinitionId::new(7),
            Source::group(IndexId(2), GroupId(10)),
        )
        .unwrap();
        resources.item_search.index =
            Some(ItemSearchIndex::from_definitions(&[definition], false).unwrap());
        resources.item_search.context = Some((
            resources.profile,
            resources.cache.metadata_fingerprint().to_owned(),
            false,
        ));
        let mut state = GameState::new();
        let mut host = ScriptHost {
            resources: &mut resources,
            state: &mut state,
        };
        let mut stacks = Stacks::default();
        stacks.push_object(ScriptValue::Text("a".into()));
        stacks.integers.push(1);
        instruction(&mut host, Opcode::OcFind, &mut stacks).unwrap();
        assert_eq!(stacks.int().unwrap(), 1);
        for expected in [7, -1, -1] {
            instruction(&mut host, Opcode::OcFindnext, &mut stacks).unwrap();
            assert_eq!(stacks.int().unwrap(), expected);
        }
        instruction(&mut host, Opcode::OcFindreset, &mut stacks).unwrap();
        instruction(&mut host, Opcode::OcFindnext, &mut stacks).unwrap();
        assert_eq!(stacks.int().unwrap(), 7);
        // This fixture cache has no item archive: a second find proves the loaded index is reused.
        stacks.push_object(ScriptValue::Text(String::new()));
        stacks.integers.push(0);
        instruction(&mut host, Opcode::OcFind, &mut stacks).unwrap();
        assert_eq!(stacks.int().unwrap(), 1);
        resources.reset();
        assert!(resources.item_search.index.is_none());
        assert!(resources.item_search.results.is_empty());
    }
}
