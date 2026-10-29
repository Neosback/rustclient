use rustclient_cache::{
    FileId, GroupId, IndexId, Source,
    osrs240::widgets::decode_legacy,
    widget::{LegacyButton, WidgetBody},
};
use rustclient_model::{FontId, InterfaceId, ModelId, WidgetId};

#[test]
#[expect(
    clippy::too_many_lines,
    reason = "one source-ordered IF1 family fixture contract"
)]
fn legacy_family_preserves_240_fields_conditionals_and_parent_links() {
    let vectors: [&[u8]; 9] = [
        include_bytes!("fixtures/widgets/if1-0.bin"),
        include_bytes!("fixtures/widgets/if1-1.bin"),
        include_bytes!("fixtures/widgets/if1-2.bin"),
        include_bytes!("fixtures/widgets/if1-3.bin"),
        include_bytes!("fixtures/widgets/if1-4.bin"),
        include_bytes!("fixtures/widgets/if1-5.bin"),
        include_bytes!("fixtures/widgets/if1-6.bin"),
        include_bytes!("fixtures/widgets/if1-7.bin"),
        include_bytes!("fixtures/widgets/if1-8.bin"),
    ];
    for (kind, bytes) in vectors.into_iter().enumerate() {
        let part = u32::try_from(kind).unwrap();
        let id = WidgetId::new(InterfaceId::new(12), part);
        let source = Source {
            index: IndexId(3),
            group: Some(GroupId(12)),
            file: Some(FileId(part)),
        };
        let (widget, legacy) = decode_legacy(bytes, id, source).unwrap();
        assert_eq!(widget.layout.offset, [-10, 5]);
        assert_eq!(widget.layout.size, [80, 25]);
        assert_eq!(
            widget.parent,
            (kind != 0).then_some(WidgetId::new(id.interface, 0))
        );
        assert_eq!(legacy.hover_redirect, Some(WidgetId::new(id.interface, 3)));
        assert_eq!(legacy.comparisons, [(2, 42)]);
        assert_eq!(legacy.expressions, [vec![20, -1, 0]]);
        assert!(widget.listeners.is_empty());
        if kind == 6 {
            assert_eq!(legacy.button, LegacyButton::Target);
            assert_eq!(widget.target_verb, "Cast");
            assert_eq!(legacy.target_text, "Spell");
            assert_eq!(widget.permissions.target_mask, 63);
        } else {
            assert_eq!(legacy.button, LegacyButton::Click);
            assert_eq!(legacy.button_text, "Ok");
            assert!(widget.permissions.crm_target);
        }
        match kind {
            0 => {
                assert!(widget.hidden);
                assert!(matches!(
                    widget.body,
                    WidgetBody::Container {
                        scroll_size: [0, 960],
                        ..
                    }
                ));
            }
            3 => {
                assert!(matches!(
                    widget.body,
                    WidgetBody::Rectangle {
                        color: 0x0012_3456,
                        filled: true,
                        transparency: 64
                    }
                ));
                assert!(matches!(
                    legacy.alternate,
                    Some(WidgetBody::Rectangle {
                        color: 0x00ab_cdef,
                        ..
                    })
                ));
                assert_eq!(legacy.hover_colors, [0x0011_2233, 0x0044_5566]);
            }
            4 => {
                let WidgetBody::Text {
                    font,
                    text,
                    alignment,
                    line_height,
                    shadowed,
                    color,
                } = widget.body
                else {
                    panic!()
                };
                assert_eq!(font, Some(FontId::new(496)));
                assert_eq!(text, "Bank €");
                assert_eq!(alignment, [1, 2]);
                assert_eq!(line_height, 14);
                assert!(shadowed);
                assert_eq!(color, 0x0012_3456);
                assert!(
                    matches!(legacy.alternate, Some(WidgetBody::Text {ref text,color:0x00ab_cdef,..}) if text=="Selected")
                );
            }
            5 => {
                assert!(
                    matches!(widget.body, WidgetBody::Sprite { sprite:Some(sprite), .. } if sprite.get()==1234)
                );
                assert!(matches!(
                    legacy.alternate,
                    Some(WidgetBody::Sprite { sprite: None, .. })
                ));
            }
            6 => {
                assert!(
                    matches!(widget.body,WidgetBody::Model {model:Some(model),sequence:Some(seq),angles:[256,512,0],zoom:900,..} if model==ModelId::new(70000) && seq.get()==808)
                );
                assert!(
                    matches!(legacy.alternate,Some(WidgetBody::Model {model:Some(model),sequence:None,..}) if model==ModelId::new(80000))
                );
            }
            8 => {
                assert_eq!(legacy.tooltip.as_deref(), Some("Hover only"));
                assert!(matches!(widget.body,WidgetBody::Text {ref text,..} if text.is_empty()));
            }
            _ => {}
        }
        for end in 0..bytes.len() {
            assert!(
                decode_legacy(&bytes[..end], id, source).is_err(),
                "kind={kind} end={end}"
            );
        }
        let mut trailing = bytes.to_vec();
        trailing.push(0);
        assert!(decode_legacy(&trailing, id, source).is_err());
        let mut invalid = bytes.to_vec();
        invalid[0] = 9;
        assert!(decode_legacy(&invalid, id, source).is_err());
        invalid[0] = 0;
        invalid[1] = 7;
        assert!(decode_legacy(&invalid, id, source).is_err());
    }
}

#[test]
fn oversized_expression_arrays_fail_before_their_payload_is_allocated() {
    let mut bytes = include_bytes!("fixtures/widgets/if1-0.bin").to_vec();
    // Retain the header, no comparisons, two expressions whose total exceeds the cap.
    bytes.truncate(17);
    bytes.extend_from_slice(&[0, 2, 255, 255]);
    bytes.resize(bytes.len() + 65535 * 2, 0);
    bytes.extend_from_slice(&[0, 2]);
    let id = WidgetId::new(InterfaceId::new(12), 0);
    let source = Source {
        index: IndexId(3),
        group: Some(GroupId(12)),
        file: Some(FileId(0)),
    };
    let error = decode_legacy(&bytes, id, source).unwrap_err().to_string();
    assert!(error.contains("expression word limit"), "{error}");
}

#[test]
fn legacy_parent_ids_build_the_existing_graph_without_a_second_child_list() {
    use rustclient_cache::widget::InterfaceDefinition;
    let id = InterfaceId::new(12);
    let source = Source::group(IndexId(3), GroupId(12));
    let root = decode_legacy(
        include_bytes!("fixtures/widgets/if1-0.bin"),
        WidgetId::new(id, 0),
        source,
    )
    .unwrap()
    .0;
    let child = decode_legacy(
        include_bytes!("fixtures/widgets/if1-4.bin"),
        WidgetId::new(id, 4),
        source,
    )
    .unwrap()
    .0;
    let graph = InterfaceDefinition::from_widgets(id, vec![root.clone(), child.clone()]).unwrap();
    assert_eq!(graph.children(None), [root.id]);
    assert_eq!(graph.children(Some(root.id)), [child.id]);
    assert!(InterfaceDefinition::from_widgets(id, vec![child.clone()]).is_err());
    let mut cyclic = root;
    cyclic.parent = Some(child.id);
    assert!(InterfaceDefinition::from_widgets(id, vec![cyclic, child]).is_err());
}
