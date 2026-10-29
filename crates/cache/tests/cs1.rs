use rustclient_cache::{
    CacheProfile,
    osrs240::cs1::{self, PRESENT, Query},
};
use rustclient_model::{InterfaceId, ItemDefinitionId, SkillId, VarpId, WidgetId};
const PROFILE: CacheProfile = CacheProfile::Osrs240;
#[test]
fn buttons_use_only_the_leading_direct_reference_and_wrapping_toggle_arithmetic() {
    use rustclient_cache::{
        GroupId, IndexId, Source, osrs240::widgets::decode_legacy, widget::LegacyButton,
    };
    let (_, mut legacy) = decode_legacy(
        include_bytes!("fixtures/widgets/if1-3.bin"),
        WidgetId::new(InterfaceId::new(12), 3),
        Source::group(IndexId(3), GroupId(12)),
    )
    .unwrap();
    legacy.button = LegacyButton::Toggle;
    legacy.expressions = vec![vec![5, 42, 15, 20, 999, 0]];
    assert_eq!(
        PROFILE
            .legacy_button_variable(&legacy, |_| i32::MIN)
            .unwrap(),
        Some((VarpId::new(42), -2_147_483_647))
    );
    legacy.button = LegacyButton::Select;
    legacy.comparisons = vec![(4, 7)];
    assert_eq!(
        PROFILE.legacy_button_variable(&legacy, |_| 7).unwrap(),
        None
    );
    assert_eq!(
        PROFILE.legacy_button_variable(&legacy, |_| 6).unwrap(),
        Some((VarpId::new(42), 7))
    );
    legacy.comparisons.clear();
    assert!(PROFILE.legacy_button_variable(&legacy, |_| 6).is_err());
    legacy.expressions = vec![vec![20, 5, 5, 42, 0]];
    assert_eq!(
        PROFILE
            .legacy_button_variable(&legacy, |_| panic!("must not evaluate expression"))
            .unwrap(),
        None
    );
    legacy.expressions.clear();
    assert_eq!(
        PROFILE
            .legacy_button_variable(&legacy, |_| panic!("no direct reference"))
            .unwrap(),
        None
    );
    for malformed in [vec![], vec![5], vec![5, -1]] {
        legacy.expressions = vec![malformed];
        assert!(PROFILE.legacy_button_variable(&legacy, |_| 0).is_err());
    }
}
fn value(words: Vec<i32>) -> i32 {
    PROFILE
        .evaluate_cs1(&[words], 0, |_| Err("unexpected query".into()))
        .value
}
#[test]
fn source_arithmetic_is_ordered_wrapping_and_keeps_zero_division_value() {
    assert_eq!(
        value(vec![20, 10, 15, 20, 3, 17, 20, 4, 16, 20, 0, 20, 2, 0]),
        30
    );
    assert_eq!(value(vec![20, i32::MAX, 20, 1, 0]), i32::MIN);
    assert_eq!(
        PROFILE
            .evaluate_cs1(&[vec![5, 1, 16, 20, -1, 0]], 0, |_| Ok(i32::MIN))
            .value,
        i32::MIN
    );
    assert_eq!(
        value(vec![20, 9, 17, 99, 0]),
        0,
        "unrecognized source words supply zero, not an invented neutral-success CS2 handler"
    );
}
#[test]
fn typed_queries_preserve_240_skill_total_and_raw_energy_units() {
    let words = vec![
        1, 0, 2, 1, 3, 2, 5, 3, 6, 4, 7, 5, 8, 9, 11, 12, 13, 6, 33, 14, 7, 18, 19, 0,
    ];
    let mut seen = Vec::new();
    let result = PROFILE.evaluate_cs1(&[words], 0, |query| {
        seen.push(query);
        Ok(match query {
            Query::CurrentLevel(_) => 7,
            Query::BaseLevel(id) if id == SkillId::new(4) => 1,
            Query::BaseLevel(_) => 2,
            Query::Experience(_) => 300,
            Query::Varp(id) if id == VarpId::new(5) => 46_875,
            Query::Varp(id) if id == VarpId::new(6) => 2,
            Query::Varp(_) => 11,
            Query::CombatLevel => 3,
            Query::RunEnergy => 1234,
            Query::Weight => -5,
            Query::Varbit(_) => 4,
            Query::WorldX => 3200,
            Query::WorldY => 3300,
            Query::WidgetItem { .. } => unreachable!(),
        })
    });
    assert_eq!(
        result.value,
        7 + 2 + 300 + 11 + 83 + 100 + 3 + 47 + 1234 - 5 + 1 + 4 + 3200 + 3300
    );
    assert!(result.error.is_none());
    assert!(seen.contains(&Query::BaseLevel(SkillId::new(23))));
    assert!(!seen.contains(&Query::BaseLevel(SkillId::new(24))));
    assert_eq!(
        PROFILE
            .evaluate_cs1(&[vec![7, 0, 0]], 0, |_| Ok(i32::MAX))
            .value,
        i32::MAX.wrapping_mul(100) / 46_875
    );
}
#[test]
fn widget_queries_keep_address_item_absence_and_presence_distinct() {
    for (code, presence, expected) in [(4, false, 42), (10, true, PRESENT)] {
        let result = PROFILE.evaluate_cs1(&[vec![code, 12, 34, 4151, 0]], 0, |q| {
            assert_eq!(
                q,
                Query::WidgetItem {
                    widget: WidgetId::new(InterfaceId::new(12), 34),
                    item: Some(ItemDefinitionId::new(4151)),
                    presence
                }
            );
            Ok(if presence { 1 } else { expected })
        });
        assert_eq!(result.value, expected);
    }
    let result = PROFILE.evaluate_cs1(&[vec![4, 12, 34, -1, 0]], 0, |q| {
        assert!(matches!(q, Query::WidgetItem { item: None, .. }));
        Ok(0)
    });
    assert_eq!(result.value, 0);
}
#[test]
fn absent_failed_and_successful_negative_results_remain_distinguishable() {
    let missing = PROFILE.evaluate_cs1(&[], 0, |_| unreachable!());
    assert_eq!(missing.value, -2);
    assert!(missing.error.is_none());
    for words in [vec![20], vec![1, -1, 0], vec![6, 0, 0], vec![20, 1]] {
        let failed = PROFILE.evaluate_cs1(&[words], 0, |_| Err("unavailable fact".into()));
        assert_eq!(failed.value, -1);
        assert!(failed.error.is_some());
    }
    let negative = PROFILE.evaluate_cs1(&[vec![20, -1, 0]], 0, |_| unreachable!());
    assert_eq!(negative.value, -1);
    assert!(negative.error.is_none());
    let oversized = PROFILE.evaluate_cs1(&[vec![99; 65_537]], 0, |_| unreachable!());
    assert_eq!(oversized.value, -1);
    assert!(oversized.error.unwrap().detail.contains("limit"));
}
#[test]
fn source_comparisons_include_error_sentinels_and_default_equality() {
    assert!(!cs1::matches(&[], |_| unreachable!()));
    assert!(cs1::matches(
        &[(2, 5), (3, 1), (4, 0), (1, 3), (99, 3)],
        |index| [4, 2, -1, 3, 3][index]
    ));
    assert!(!cs1::matches(&[(2, 5)], |_| 5));
    assert!(!cs1::matches(&[(3, 5)], |_| 5));
    assert!(cs1::matches(&[(2, 0)], |_| -1));
}
#[test]
fn substitution_replaces_only_source_tokens_and_checks_expansion_before_allocating() {
    let text = cs1::substitute("%1 %1 %2 %3 %4 %5 %6 %10", |index| {
        [-1, -2, PRESENT - 1, PRESENT, i32::MAX][index]
    })
    .unwrap();
    assert_eq!(text, "-1 -1 -2 999999998 * * %6 -10");
    assert!(cs1::substitute(&"%1".repeat(500_000), |_| i32::MIN).is_err());
}
