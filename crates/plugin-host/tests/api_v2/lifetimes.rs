use super::*;
use rustclient_plugin_api::{ActorHandle, OverlayLine};

type Reads = Rc<RefCell<Vec<Result<Option<u64>, CapabilityDenied>>>>;
type Captures = Rc<RefCell<Vec<Result<Option<ActorHandle>, CapabilityDenied>>>>;
struct ActorReader {
    captures: Captures,
    handle: ActorHandle,
    reads: Reads,
}
impl PluginInstance for ActorReader {
    fn init(&mut self, _: &mut PluginCall<'_>) -> Result<(), PluginFault> {
        Ok(())
    }
    fn on_event(&mut self, _: &mut PluginCall<'_>, _: &[PluginEvent]) -> Result<(), PluginFault> {
        Ok(())
    }
    fn overlay(&mut self, _: &mut PluginCall<'_>) -> Result<Vec<OverlayLine>, PluginFault> {
        Ok(Vec::new())
    }
    fn output(&mut self, call: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
        self.captures
            .borrow_mut()
            .push(call.actor_handle(self.handle.actor()));
        self.reads.borrow_mut().push(
            call.actor(self.handle)
                .map(|actor| actor.map(|actor| actor.instance)),
        );
        Ok(PluginOutput::default())
    }
}
#[test]
fn actor_queries_require_grants_and_reject_reused_slots_and_sessions() {
    let mut game = snapshot();
    game.client.session = 7;
    game.npcs[0].actor.instance = 41;
    let actor = ActorRef::Npc(game.npcs[0].actor.index);
    let handle = game.actor_handle(actor).unwrap();
    let mut set = PluginSet::new();
    let allowed = Reads::default();
    let denied = Reads::default();
    let allowed_captures = Captures::default();
    let denied_captures = Captures::default();
    for (id, grants, reads, captures) in [
        (
            "test.allowed",
            vec![Capability::GameReadNpcs],
            allowed.clone(),
            allowed_captures.clone(),
        ),
        (
            "test.denied",
            vec![Capability::GameReadPlayers],
            denied.clone(),
            denied_captures.clone(),
        ),
    ] {
        set.register(
            manifest(id, &grants),
            Box::new(ActorReader {
                captures,
                handle,
                reads,
            }),
        )
        .unwrap();
    }
    let mut storage = MemoryStorage::default();
    let mut logs = MemoryLogSink::default();
    set.init(&SnapshotView(&game), &mut storage, &mut logs);
    let _ = set.collect_output(&SnapshotView(&game), &mut storage, &mut logs);
    game.npcs[0].actor.instance = 42;
    let _ = set.collect_output(&SnapshotView(&game), &mut storage, &mut logs);
    game.npcs[0].actor.instance = 41;
    game.client.session = 8;
    let _ = set.collect_output(&SnapshotView(&game), &mut storage, &mut logs);
    assert_eq!(*allowed.borrow(), vec![Ok(Some(41)), Ok(None), Ok(None)]);
    assert!(denied.borrow().iter().all(Result::is_err));
    assert_eq!(
        *allowed_captures.borrow(),
        vec![
            Ok(ActorHandle::new(7, actor, 41)),
            Ok(ActorHandle::new(7, actor, 42)),
            Ok(ActorHandle::new(8, actor, 41)),
        ]
    );
    assert!(denied_captures.borrow().iter().all(Result::is_err));

    assert_eq!(logs.records.len(), 1, "a denied read is diagnosed once");
}
#[test]
fn unknown_actor_identities_cannot_be_promoted_to_lifetime_handles() {
    let mut game = snapshot();
    let actor = ActorRef::Npc(game.npcs[0].actor.index);
    assert!(game.actor_handle(actor).is_none());
    game.client.session = 1;
    game.npcs[0].actor.instance = 0;
    assert!(game.actor_handle(actor).is_none());
    assert!(ActorHandle::new(0, actor, 1).is_none());
}
