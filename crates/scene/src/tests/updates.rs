//! Geometry dependency checks preserve the original filtered-snapshot contract.
use super::*;
use rustclient_game_state::{GameState, SceneryOperation, SemanticOperation};
use rustclient_model::{LocationChange, VarbitId, VarpId};

#[test]
fn dependency_checks_follow_commits_without_invalidating_on_cycles_or_unrelated_vars() {
    let mut sources = with_model();
    sources
        .objects
        .get_mut(&ObjectDefinitionId::new(1))
        .unwrap()
        .morphs = Some(ObjectMorphs {
        varp: Some(VarpId::new(7)),
        varbit: Some(VarbitId::new(3)),
        targets: vec![None],
        fallback: None,
    });
    sources.varbits.insert(
        VarbitId::new(3),
        VarbitDefinition {
            varp: VarpId::new(9),
            low: 1,
            high: 2,
        },
    );
    let asset = scene(sources);
    let mut state = GameState::new();
    state
        .apply_operation(SemanticOperation::BeginSession {
            revision: rustclient_model::REVISION_240,
        })
        .unwrap();
    let mut projected = SceneryInputs::from(state.scenery());
    projected.retain_scene_dependencies(&asset);
    for (operation, matches) in [
        (SceneryOperation::AdvanceCycles(1), true),
        (
            SceneryOperation::SetVariable {
                id: VarpId::new(100),
                value: 30,
            },
            true,
        ),
        (
            SceneryOperation::SetVariable {
                id: VarpId::new(7),
                value: 1,
            },
            false,
        ),
        (SceneryOperation::ResetVariables, true),
        (
            SceneryOperation::SetVariable {
                id: VarpId::new(9),
                value: 2,
            },
            false,
        ),
        (SceneryOperation::ResetVariables, true),
        (
            SceneryOperation::ChangeLocation {
                key: location_key(&asset.sources().squares[0].locations[0]),
                change: LocationChange::Remove,
            },
            false,
        ),
        (
            SceneryOperation::ChangeLocation {
                key: location_key(&asset.sources().squares[0].locations[0]),
                change: LocationChange::Inherit,
            },
            true,
        ),
    ] {
        state
            .apply_operation(SemanticOperation::Scenery(operation))
            .unwrap();
        let mut current = SceneryInputs::from(state.scenery());
        current.retain_scene_dependencies(&asset);
        assert_eq!(projected.same_geometry(&current), matches);
        assert_eq!(projected.matches_state(state.scenery(), &asset), matches);
    }
}

#[test]
fn location_overrides_preserve_duplicate_membership_and_source_order() {
    let mut sources = with_model();
    let original = sources.squares[0].locations[0];
    let mut second = definition();
    second.id = ObjectDefinitionId::new(2);
    sources.objects.insert(second.id, second);
    sources.squares[0].locations.extend([
        StaticLocation {
            object: ObjectDefinitionId::new(2),
            ..original
        },
        StaticLocation {
            tile: WorldTile::new(1, 1, Plane::MIN),
            ..original
        },
    ]);
    let asset = scene(sources);
    let key = location_key(&original);
    let mut inputs = SceneryInputs::default();
    for (change, expected) in [
        (LocationChange::Inherit, vec![1, 2, 1]),
        (LocationChange::Remove, vec![1]),
        (
            LocationChange::Replace(rustclient_model::LocationDefinition {
                object: ObjectDefinitionId::new(2),
                shape: original.shape,
                orientation: original.orientation,
            }),
            vec![1, 2],
        ),
        (LocationChange::Inherit, vec![1, 2, 1]),
    ] {
        inputs.locations.insert(
            key,
            rustclient_game_state::LocationOverride {
                change,
                animation: None,
            },
        );
        let effective = locations::effective_locations(&asset, &inputs);
        assert_eq!(
            effective
                .iter()
                .map(|loc| loc.object.get())
                .collect::<Vec<_>>(),
            expected
        );
        for object in [ObjectDefinitionId::new(1), ObjectDefinitionId::new(2)] {
            let present = effective
                .iter()
                .any(|loc| location_key(loc) == key && loc.object == object);
            assert_eq!(
                asset
                    .location_definition(key, object, &inputs)
                    .unwrap()
                    .is_some(),
                present
            );
        }
    }
    // Raw projection inputs can be malformed even though GameState rejects cross-layer shapes.
    // Their previous ordered resolution remains unchanged rather than being silently redefined.
    let boundary = rustclient_model::LocationKey {
        layer: rustclient_model::LocationLayer::Boundary,
        ..key
    };
    inputs.locations.insert(
        boundary,
        rustclient_game_state::LocationOverride {
            change: LocationChange::Remove,
            animation: None,
        },
    );
    inputs.locations.insert(
        key,
        rustclient_game_state::LocationOverride {
            change: LocationChange::Replace(rustclient_model::LocationDefinition {
                object: ObjectDefinitionId::new(2),
                shape: 0,
                orientation: original.orientation,
            }),
            animation: None,
        },
    );
    assert!(
        asset
            .location_definition(boundary, ObjectDefinitionId::new(2), &inputs)
            .unwrap()
            .is_some()
    );
    assert!(
        asset
            .location_definition(key, ObjectDefinitionId::new(2), &inputs)
            .unwrap()
            .is_none()
    );
}
