use rustclient_cache::{
    FileId, GroupId, IndexId, Source,
    osrs240::widgets::decode,
    widget::{PositionMode, SizeMode, WidgetBody, WidgetListener},
};
use rustclient_model::{FontId, InterfaceId, ModelId, ScriptValue, WidgetId};
#[test]
#[expect(
    clippy::too_many_lines,
    reason = "one definition-family fixture contract"
)]
fn if3_family_retains_revision_240_geometry_models_actions_and_typed_listeners() {
    let vectors: [(&[u8], u8); 6] = [
        (include_bytes!("fixtures/widgets/container.bin"), 0),
        (include_bytes!("fixtures/widgets/rectangle.bin"), 3),
        (include_bytes!("fixtures/widgets/text.bin"), 4),
        (include_bytes!("fixtures/widgets/sprite.bin"), 5),
        (include_bytes!("fixtures/widgets/model.bin"), 6),
        (include_bytes!("fixtures/widgets/line.bin"), 9),
    ];
    for (bytes, kind) in vectors {
        let source = Source {
            index: IndexId(3),
            group: Some(GroupId(12)),
            file: Some(FileId(u32::from(kind))),
        };
        let id = WidgetId::new(InterfaceId::new(12), u32::from(kind));
        let widget = decode(bytes, id, source).unwrap();
        assert_eq!(widget.id, id);
        assert_eq!(widget.parent, None);
        assert_eq!(widget.layout.offset, [-10, 5]);
        assert_eq!(
            widget.layout.position_mode,
            [PositionMode::Center, PositionMode::End]
        );
        assert_eq!(widget.actions, vec!["Open", "Bank"]);
        assert_eq!(widget.permissions.enabled_operations, 1023);
        assert!(widget.permissions.crm_target && widget.permissions.script_trigger);
        assert_eq!(widget.permissions.drag_depth, 7);
        assert_eq!(widget.permissions.target_mask, 63);
        assert_eq!(
            widget.listeners[&WidgetListener::Load],
            vec![
                ScriptValue::Int(100),
                ScriptValue::Int(-42),
                ScriptValue::Text("€".into())
            ]
        );
        assert_eq!(widget.variable_triggers, vec![-2]);
        assert_eq!(widget.inventory_triggers, vec![93, 95]);
        match widget.body {
            WidgetBody::Container {
                scroll_size,
                no_click_through,
            } => {
                assert_eq!(kind, 0);
                assert_eq!(scroll_size, [640, 960]);
                assert!(no_click_through);
            }
            WidgetBody::Rectangle {
                color,
                filled,
                transparency,
            } => {
                assert_eq!(kind, 3);
                assert_eq!((color, filled, transparency), (0x0012_3456, true, 64));
            }
            WidgetBody::Text { font, text, .. } => {
                assert_eq!(kind, 4);
                assert_eq!(font, Some(FontId::new(496)));
                assert_eq!(text, "Bank €");
            }
            WidgetBody::Sprite { sprite, flip, .. } => {
                assert_eq!(kind, 5);
                assert_eq!(sprite.unwrap().get(), 1234);
                assert_eq!(flip, [true, false]);
            }
            WidgetBody::Model {
                model,
                sizing,
                sequence,
                ..
            } => {
                assert_eq!(kind, 6);
                assert_eq!(model, Some(ModelId::new(70000)));
                assert_eq!(sizing, [0, 640, 480]);
                assert_eq!(sequence.unwrap().get(), 808);
                assert_eq!(
                    widget.layout.size_mode,
                    [SizeMode::ParentMinus, SizeMode::Proportional]
                );
            }
            WidgetBody::Line { reversed, .. } => {
                assert_eq!(kind, 9);
                assert_eq!(widget.layout.size[1], -25);
                assert!(reversed);
            }
        }
        for length in 0..bytes.len() {
            assert!(decode(&bytes[..length], id, source).is_err());
        }
        let mut extra = bytes.to_vec();
        extra.push(0);
        assert!(decode(&extra, id, source).is_err());
        let mut wrong = bytes.to_vec();
        wrong[0] = 0;
        assert!(decode(&wrong, id, source).is_err());
        let mut wrong = bytes.to_vec();
        wrong[1] = 12;
        assert!(decode(&wrong, id, source).is_err());
        let mut wrong = bytes.to_vec();
        wrong[12] = 3;
        assert!(decode(&wrong, id, source).is_err());
    }
}
