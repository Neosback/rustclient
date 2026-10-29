use rustclient_cache::{
    GroupId, IndexId, Source,
    osrs240::{definitions::item, item_search::ItemSearchIndex},
};
use rustclient_model::ItemDefinitionId;

fn definition(id: u32, name: &str) -> rustclient_cache::world::ItemDefinition {
    let mut bytes = vec![2];
    bytes.extend_from_slice(name.as_bytes());
    bytes.extend_from_slice(&[0, 0]);
    item(
        &bytes,
        ItemDefinitionId::new(id),
        Source::group(IndexId(2), GroupId(10)),
    )
    .unwrap()
}
fn ids(index: &ItemSearchIndex, query: &str, tradeable: bool) -> Option<Vec<u32>> {
    index
        .find(query, tradeable)
        .map(|ids| ids.into_iter().map(ItemDefinitionId::get).collect())
}

#[test]
fn query_preserves_stock_filters_empty_input_and_exact_name_order() {
    let a = definition(1, "Zamorak staff");
    let mut b = definition(2, "Air staff");
    b.tradeable = true;
    let mut note = definition(3, "Air staff");
    note.note_template = Some(ItemDefinitionId::new(99));
    let mut placeholder = definition(4, "Air staff");
    placeholder.placeholder_template = Some(ItemDefinitionId::new(99));
    let index = ItemSearchIndex::from_definitions(&[a, b, note, placeholder], true).unwrap();
    assert_eq!(ids(&index, "STAFF", false), Some(vec![2, 1]));
    assert_eq!(ids(&index, "staff", true), Some(vec![2]));
    assert_eq!(ids(&index, "", false), Some(vec![2, 1]));
    assert_eq!(ids(&index, "missing", false), Some(vec![]));
}

#[test]
fn duplicate_name_ties_follow_the_stock_alternating_quicksort() {
    // classQA.method9458, equal-name fixture: stable/id ordering is observably different.
    let definitions = (0..4).map(|id| definition(id, "Same")).collect::<Vec<_>>();
    let index = ItemSearchIndex::from_definitions(&definitions, true).unwrap();
    assert_eq!(ids(&index, "same", false), Some(vec![3, 1, 2, 0]));
}

#[test]
fn two_hundred_fifty_results_succeed_and_the_next_match_overflows() {
    let mut definitions = (0..250)
        .map(|id| definition(id, "Coin"))
        .collect::<Vec<_>>();
    assert_eq!(
        ItemSearchIndex::from_definitions(&definitions, true)
            .unwrap()
            .find("", false)
            .unwrap()
            .len(),
        250
    );
    definitions.push(definition(250, "Coin"));
    assert_eq!(
        ids(
            &ItemSearchIndex::from_definitions(&definitions, true).unwrap(),
            "",
            false
        ),
        None
    );
}

#[test]
fn membership_changes_effective_names_and_tradeability_including_bought_items() {
    let mut base = definition(1, "Dragon sword");
    base.members = true;
    base.tradeable = true;
    let mut bought = definition(2, "unused");
    bought.bought = Some(base.id);
    bought.bought_template = Some(ItemDefinitionId::new(99));
    let definitions = [base, bought];
    let free = ItemSearchIndex::from_definitions(&definitions, false).unwrap();
    let member = ItemSearchIndex::from_definitions(&definitions, true).unwrap();
    assert_eq!(ids(&free, "(members)", false).unwrap().len(), 2);
    assert_eq!(ids(&free, "sword", true), Some(vec![]));
    assert_eq!(ids(&member, "sword", true), Some(vec![1]));
    assert_eq!(ids(&member, "(members)", false), Some(vec![]));
}

#[test]
fn corrupt_bought_links_and_duplicate_ids_fail_instead_of_returning_partial_results() {
    let mut cyclic = definition(1, "Cycle");
    cyclic.bought = Some(cyclic.id);
    cyclic.bought_template = Some(ItemDefinitionId::new(99));
    assert!(ItemSearchIndex::from_definitions(&[cyclic], true).is_err());
    assert!(
        ItemSearchIndex::from_definitions(&[definition(1, "A"), definition(1, "B")], true).is_err()
    );
}
