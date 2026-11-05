//! Projection of world menu rows into the plugin API's `MenuEntry` (`RuneLite` `MenuEntry`).
//!
//! The client owns the menu; plugins see these records in `MenuEntryAdded`, `MenuOpened`,
//! `PostMenuSort` and `MenuOptionClicked`. Identifiers follow `RuneLite`'s conventions: an NPC
//! entry carries the NPC index, a location entry its object id and scene tile, a ground item
//! entry its item id and scene tile, and "Walk here" the scene tile.
use rustclient_model::{SceneDescriptor, WorldAction, WorldTarget, WorldTile};
use rustclient_plugin_api::{MenuEntry, MenuKind};

/// One world menu row as the client built it.
#[derive(Debug, Clone, Copy)]
pub enum WorldMenuRow {
    /// A target's definition-authored option.
    Action(WorldAction),
    /// "Walk here" on `destination`.
    Walk {
        scene: SceneDescriptor,
        destination: WorldTile,
    },
}

/// Split a `"<verb> <col=...>target"` label into option and target. A label without a coloured
/// target (such as "Walk here") is all option.
#[must_use]
pub fn split_label(label: &str) -> (&str, &str) {
    label
        .find(" <col=")
        .map_or((label, ""), |at| (&label[..at], &label[at + 1..]))
}

/// The label shown for an entry whose option a plugin replaced.
#[must_use]
pub fn relabel(label: &str, option: &str) -> String {
    let (_, target) = split_label(label);
    if target.is_empty() {
        option.to_owned()
    } else {
        format!("{option} {target}")
    }
}

fn scene_xy(scene: SceneDescriptor, tile: WorldTile) -> (i32, i32) {
    let origin = scene.origin();
    (
        tile.x.saturating_sub(origin.x),
        tile.y.saturating_sub(origin.y),
    )
}

/// The plugin record for a widget menu row (`MenuAction.CC_OP`): the option is the action text,
/// the identifier is the one-based operation index, `param0` the child index (-1 for the widget
/// itself) and `param1` the packed widget id. Operations beyond ten, continue and button clicks
/// have no `CC_OP` equivalent and map to `MenuKind::Other`.
#[must_use]
pub fn widget_menu_entry(label: &str, action: rustclient_model::WidgetAction) -> MenuEntry {
    let widget = action.address.widget;
    let (kind, identifier) = match action.operation.index() {
        Some(index) if index < 10 => (MenuKind::WidgetOption(index + 1), i32::from(index) + 1),
        Some(index) => (MenuKind::Other(57), i32::from(index) + 1),
        None => (MenuKind::Other(30), 0),
    };
    MenuEntry {
        option: split_label(label).0.to_owned(),
        target: split_label(label).1.to_owned(),
        kind,
        identifier,
        param0: action.address.child.map_or(-1, u32::cast_signed),
        param1: i32::try_from((widget.interface.get() << 16) | widget.component)
            .unwrap_or(i32::MAX),
        deprioritized: false,
    }
}

/// Reorder `items` by `edited.order` (dropping removed ones) and relabel those with a replaced
/// option through `relabel`. `items` are the rows the stage was run over, in the same order.
pub fn apply_edited_menu<T>(
    items: Vec<T>,
    edited: &rustclient_plugin_host::EditedMenu,
    relabel: impl Fn(&mut T, &str),
) -> Vec<T> {
    let mut slots: Vec<Option<T>> = items.into_iter().map(Some).collect();
    edited
        .order
        .iter()
        .filter_map(|&index| {
            let mut item = slots.get_mut(index)?.take()?;
            if let Some(option) = edited.options.get(&index) {
                relabel(&mut item, option);
            }
            Some(item)
        })
        .collect()
}

/// The plugin record for a row labelled `label`.
#[must_use]
pub fn plugin_menu_entry(label: &str, row: WorldMenuRow) -> MenuEntry {
    let (option, target) = split_label(label);
    let (kind, identifier, (param0, param1)) = match row {
        WorldMenuRow::Walk { scene, destination } => {
            (MenuKind::Walk, 0, scene_xy(scene, destination))
        }
        WorldMenuRow::Action(action) => {
            let op = action.slot.get() + 1;
            match action.target {
                WorldTarget::Npc { index, .. } => (
                    MenuKind::NpcOption(op),
                    i32::try_from(index.get()).unwrap_or(i32::MAX),
                    (0, 0),
                ),
                WorldTarget::Location {
                    key, definition, ..
                } => (
                    MenuKind::ObjectOption(op),
                    definition.get().cast_signed(),
                    scene_xy(action.scene, key.tile),
                ),
                WorldTarget::GroundItem { tile, item } => (
                    MenuKind::GroundItemOption(op),
                    item.get().cast_signed(),
                    scene_xy(action.scene, tile),
                ),
            }
        }
    };
    MenuEntry {
        option: option.to_owned(),
        target: target.to_owned(),
        kind,
        identifier,
        param0,
        param1,
        deprioritized: false,
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_model::{ActionSlot, ItemDefinitionId, Plane};

    #[test]
    fn labels_split_at_the_coloured_target_and_relabel_keeps_it() {
        assert_eq!(
            split_label("Talk-to <col=ffff00>Hans"),
            ("Talk-to", "<col=ffff00>Hans")
        );
        assert_eq!(split_label("Walk here"), ("Walk here", ""));
        assert_eq!(
            relabel("Take <col=ff9040>Coins", "Loot"),
            "Loot <col=ff9040>Coins"
        );
        assert_eq!(relabel("Walk here", "Go"), "Go");
    }

    #[test]
    fn rows_use_runelite_identifiers_and_scene_relative_tiles() {
        let scene = SceneDescriptor::new(
            rustclient_model::SceneAssetId::new([1; 32]),
            WorldTile::new(3200, 3200, Plane::MIN),
            104,
            104,
        )
        .unwrap();
        let tile = WorldTile::new(3210, 3205, Plane::MIN);
        let take = plugin_menu_entry(
            "Take <col=ff9040>Coins",
            WorldMenuRow::Action(WorldAction {
                scene,
                target: WorldTarget::GroundItem {
                    tile,
                    item: ItemDefinitionId::new(995),
                },
                slot: ActionSlot::new(2).unwrap(),
            }),
        );
        assert_eq!(
            take,
            MenuEntry {
                option: "Take".into(),
                target: "<col=ff9040>Coins".into(),
                kind: MenuKind::GroundItemOption(3),
                identifier: 995,
                param0: 10,
                param1: 5,
                deprioritized: false,
            }
        );
        let walk = plugin_menu_entry(
            "Walk here",
            WorldMenuRow::Walk {
                scene,
                destination: tile,
            },
        );
        assert_eq!(
            (walk.kind, walk.option.as_str(), walk.param0, walk.param1),
            (MenuKind::Walk, "Walk here", 10, 5)
        );
    }

    #[test]
    fn widget_rows_use_cc_op_identifiers_and_reorder_by_the_edited_menu() {
        use rustclient_model::{InterfaceId, WidgetAddress, WidgetId, WidgetOperation};
        let action = |op: u8, child: Option<u32>| rustclient_model::WidgetAction {
            address: WidgetAddress {
                widget: WidgetId::new(InterfaceId::new(149), 0),
                child,
            },
            operation: WidgetOperation::new(op).unwrap(),
            item: None,
            interface_epoch: 1,
        };
        let wield = widget_menu_entry("Wield <col=ff9040>Whip", action(1, Some(3)));
        assert_eq!(
            (wield.kind, wield.identifier, wield.param0, wield.param1),
            (MenuKind::WidgetOption(2), 2, 3, 149 << 16)
        );
        assert_eq!(
            (wield.option.as_str(), wield.target.as_str()),
            ("Wield", "<col=ff9040>Whip")
        );
        let widget_itself = widget_menu_entry("Bank", action(0, None));
        assert_eq!(
            (widget_itself.param0, widget_itself.kind),
            (-1, MenuKind::WidgetOption(1))
        );
        let beyond = widget_menu_entry("Far", action(10, None));
        assert_eq!(beyond.kind, MenuKind::Other(57));
        assert_eq!(
            widget_menu_entry(
                "Continue",
                rustclient_model::WidgetAction {
                    operation: WidgetOperation::CONTINUE,
                    ..action(0, None)
                }
            )
            .kind,
            MenuKind::Other(30)
        );

        let edited = rustclient_plugin_host::apply_menu_edits(
            3,
            &[
                rustclient_plugin_api::MenuEdit::MoveToTop { index: 2 },
                rustclient_plugin_api::MenuEdit::Remove { index: 1 },
                rustclient_plugin_api::MenuEdit::SetOption {
                    index: 0,
                    option: "Equip".into(),
                },
            ],
        );
        let rows = vec![
            ("Wield <col=ffff>A".to_owned(), 0),
            ("Drop <col=ffff>A".to_owned(), 1),
            ("Use <col=ffff>A".to_owned(), 2),
        ];
        let rows = apply_edited_menu(rows, &edited, |(label, _), option| {
            *label = relabel(label, option);
        });
        assert_eq!(
            rows,
            [
                ("Use <col=ffff>A".to_owned(), 2),
                ("Equip <col=ffff>A".to_owned(), 0)
            ]
        );
    }
}
