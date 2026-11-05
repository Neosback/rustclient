//! The synchronous menu stage: hook order, capability gating and edit application.
use rustclient_plugin_api::{
    Capability, EventInterest, GameEvent, GameEventKind, MenuEdit, MenuEntry, MenuKind,
    OverlayLine, PluginApiVersion, PluginEvent, PluginId, PluginManifest, PluginOutput,
};
use rustclient_plugin_host::{
    MemoryLogSink, MemoryStorage, PluginCall, PluginFault, PluginInstance, PluginSet, StaticView,
};
use std::{cell::RefCell, rc::Rc};

type Seen = Rc<RefCell<Vec<GameEventKind>>>;

/// Moves the first entry whose option is `wanted` to the top, like Menu Entry Swapper.
struct Swapper {
    source: &'static str,
    wanted: &'static str,
    seen: Seen,
    edits: Vec<MenuEdit>,
}
impl PluginInstance for Swapper {
    fn init(&mut self, _: &mut PluginCall<'_>) -> Result<(), PluginFault> {
        Ok(())
    }
    fn on_event(&mut self, _: &mut PluginCall<'_>, _: &[PluginEvent]) -> Result<(), PluginFault> {
        Ok(())
    }
    fn overlay(&mut self, _: &mut PluginCall<'_>) -> Result<Vec<OverlayLine>, PluginFault> {
        Ok(Vec::new())
    }
    fn game_event_interests(&self) -> EventInterest {
        EventInterest::one(GameEventKind::MenuEntryAdded)
            .with(GameEventKind::MenuOpened)
            .with(GameEventKind::PostMenuSort)
    }
    fn game_event_source(&self) -> &str {
        self.source
    }
    fn on_game_events(
        &mut self,
        _: &mut PluginCall<'_>,
        events: &[GameEvent],
    ) -> Result<(), PluginFault> {
        for event in events {
            self.seen.borrow_mut().push(event.kind());
            if let GameEvent::PostMenuSort(entries) = event {
                self.edits = entries
                    .iter()
                    .position(|entry| entry.option == self.wanted)
                    .map(|index| MenuEdit::MoveToTop { index })
                    .into_iter()
                    .collect();
            }
        }
        Ok(())
    }
    fn output(&mut self, _: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
        Ok(PluginOutput {
            menu_edits: self.edits.clone(),
            ..PluginOutput::default()
        })
    }
}

fn entry(option: &str) -> MenuEntry {
    MenuEntry {
        option: option.into(),
        target: "<col=ffff00>Man".into(),
        kind: MenuKind::NpcOption(1),
        identifier: 7,
        param0: 0,
        param1: 0,
        deprioritized: false,
    }
}

fn set_with(grants: &[Capability], seen: &Seen) -> PluginSet {
    let mut set = PluginSet::new();
    set.register(
        PluginManifest {
            id: PluginId::parse("test.swapper").unwrap(),
            name: "Swapper".into(),
            version: "1".into(),
            plugin_api: PluginApiVersion::current(),
            capabilities: grants.iter().copied().collect(),
            entry: String::new(),
        },
        Box::new(Swapper {
            source: "Swapper",
            wanted: "Pickpocket",
            seen: seen.clone(),
            edits: Vec::new(),
        }),
    )
    .unwrap();
    set
}

#[test]
fn post_menu_sort_edits_reorder_the_menu_and_hooks_arrive_in_runelite_order() {
    let seen = Seen::default();
    let mut set = set_with(&[Capability::UiMenuSwap], &seen);
    let (mut storage, mut logs) = (MemoryStorage::default(), MemoryLogSink::default());
    set.init(&StaticView::default(), &mut storage, &mut logs);
    assert!(set.wants_menu_stage());
    let entries = [entry("Talk-to"), entry("Attack"), entry("Pickpocket")];
    let menu = set.menu_stage(
        &entries,
        true,
        &StaticView::default(),
        &mut storage,
        &mut logs,
    );
    assert_eq!(menu.order, [2, 0, 1]);
    assert_eq!(
        *seen.borrow(),
        [
            GameEventKind::MenuEntryAdded,
            GameEventKind::MenuEntryAdded,
            GameEventKind::MenuEntryAdded,
            GameEventKind::MenuOpened,
            GameEventKind::PostMenuSort,
        ]
    );
    // Consulting the menu for the left click or mouse-over text does not open it.
    seen.borrow_mut().clear();
    set.menu_stage(
        &entries,
        false,
        &StaticView::default(),
        &mut storage,
        &mut logs,
    );
    assert!(!seen.borrow().contains(&GameEventKind::MenuOpened));
}

#[test]
fn menu_hooks_need_the_swap_grant_and_an_absent_subscriber_skips_the_stage() {
    let seen = Seen::default();
    let mut set = set_with(&[], &seen);
    let (mut storage, mut logs) = (MemoryStorage::default(), MemoryLogSink::default());
    set.init(&StaticView::default(), &mut storage, &mut logs);
    let entries = [entry("Talk-to"), entry("Pickpocket")];
    // Menu interests are filtered by grants, so an ungranted subscriber is not one.
    assert!(!set.wants_menu_stage());
    let menu = set.menu_stage(
        &entries,
        true,
        &StaticView::default(),
        &mut storage,
        &mut logs,
    );
    assert_eq!(menu.order, [0, 1]);
    assert!(seen.borrow().is_empty());
}
