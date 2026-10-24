use rustclient_cache::widget::InterfaceDefinition;
use rustclient_cache::{GroupId, IndexId, Source, osrs240::widgets::decode};
use rustclient_game_state::{GameState, SemanticOperation};
use rustclient_game_ui::{
    pointer::DraggedWidget,
    widgets::{WidgetBounds, WidgetTree},
};
use rustclient_model::*;
use std::collections::BTreeMap;

#[test]
#[expect(
    clippy::too_many_lines,
    reason = "one pointer-layout family including child inheritance and scroll offsets"
)]
fn dragging_repositions_the_widget_and_its_children_without_changing_semantic_layout() {
    let group = InterfaceId::new(1);
    let mut widgets = vec![];
    for component in 0..3 {
        let id = WidgetId::new(group, component);
        let mut widget = decode(
            include_bytes!("../../cache/tests/fixtures/widgets/container.bin"),
            id,
            Source::group(IndexId(3), GroupId(1)),
        )
        .unwrap();
        widget.hidden = false;
        widget.listeners.clear();
        widget.parent = match component {
            0 => None,
            1 => Some(WidgetId::new(group, 0)),
            _ => Some(WidgetId::new(group, 1)),
        };
        widget.layout = WidgetLayout {
            offset: match component {
                0 => [0, 0],
                1 => [10, 10],
                _ => [2, 3],
            },
            size: match component {
                0 => [100, 50],
                1 => [20, 10],
                _ => [5, 5],
            },
            size_mode: [SizeMode::Absolute; 2],
            position_mode: [PositionMode::Start; 2],
        };
        widgets.push(widget);
    }
    let mut assets = BTreeMap::from([(
        group,
        InterfaceDefinition::from_widgets(group, widgets).unwrap(),
    )]);
    let mut state = GameState::new();
    state
        .apply_operation(SemanticOperation::BeginSession {
            revision: Revision::with_subrevision(240, 2),
        })
        .unwrap();
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::SetRoot(
            Some(group),
        )))
        .unwrap();
    let before = state.semantic_fingerprint();
    let tree = WidgetTree::build_with_drag(
        state.interfaces(),
        &assets,
        WidgetBounds::canvas(state.interfaces()),
        Some(DraggedWidget {
            address: WidgetAddress::component(WidgetId::new(group, 1)),
            origin: [40, 20],
        }),
    )
    .unwrap();
    let bounds = tree
        .nodes
        .iter()
        .find(|node| node.id.component == 2)
        .unwrap()
        .bounds;
    assert_eq!([bounds.x, bounds.y], [42, 23]);
    assert_eq!(state.semantic_fingerprint(), before);
    let restored = WidgetTree::build(
        state.interfaces(),
        &assets,
        WidgetBounds::canvas(state.interfaces()),
    )
    .unwrap();
    let bounds = restored
        .nodes
        .iter()
        .find(|node| node.id.component == 2)
        .unwrap()
        .bounds;
    assert_eq!([bounds.x, bounds.y], [12, 13]);

    let root = WidgetId::new(group, 0);
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::SetProperty {
            widget: root,
            property: WidgetProperty::Runtime {
                address: WidgetAddress::component(root),
                value: Box::new(WidgetRuntime {
                    body: Some(WidgetBody::Container {
                        scroll_size: [200, 100],
                        no_click_through: false,
                    }),
                    scroll: Some([5, 7]),
                    ..Default::default()
                }),
            },
        }))
        .unwrap();
    let scrolled = WidgetTree::build(
        state.interfaces(),
        &assets,
        WidgetBounds::canvas(state.interfaces()),
    )
    .unwrap();
    let child = scrolled
        .nodes
        .iter()
        .find(|node| node.id.component == 2)
        .unwrap();
    assert_eq!([child.bounds.x, child.bounds.y], [7, 6]);
    let mounted_group = InterfaceId::new(2);
    let mut mounted = assets[&group].widgets()[&root].clone();
    mounted.id = WidgetId::new(mounted_group, 0);
    mounted.layout.size = [0; 2];
    mounted.layout.size_mode = [SizeMode::ParentMinus; 2];
    assets.insert(
        mounted_group,
        InterfaceDefinition::from_widgets(mounted_group, vec![mounted]).unwrap(),
    );
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::Open(
            InterfaceMount {
                parent: root,
                interface: mounted_group,
                kind: InterfaceMountKind::Overlay,
            },
        )))
        .unwrap();
    let tree = WidgetTree::build(
        state.interfaces(),
        &assets,
        WidgetBounds::canvas(state.interfaces()),
    )
    .unwrap();
    let mounted = tree
        .nodes
        .iter()
        .find(|node| node.id.interface == mounted_group)
        .unwrap();
    assert_eq!([mounted.bounds.width, mounted.bounds.height], [200, 100]);
}
