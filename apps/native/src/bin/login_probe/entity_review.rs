//! Headless two-account observation; no predicted entity state feeds the client.
use rustclient_game_state::GameState;
use rustclient_model::{PlayerIndex, WorldTile};
use std::collections::{BTreeMap, BTreeSet};
#[derive(Default)]
pub struct Review {
    previous: BTreeMap<PlayerIndex, WorldTile>,
    seen: BTreeSet<PlayerIndex>,
    peak: usize,
    removed: usize,
    moved: usize,
    appearances: usize,
    npc_peak: usize,
}
impl Review {
    pub fn observe(&mut self, state: &GameState) {
        let local = state.entities().local_player_index;
        let players: BTreeMap<_, _> = state
            .entities()
            .players()
            .iter()
            .filter(|(index, _)| Some(**index) != local)
            .map(|(&index, player)| {
                if self
                    .previous
                    .get(&index)
                    .is_some_and(|tile| *tile != player.tile)
                {
                    self.moved += 1;
                }
                if player.appearance.is_some() {
                    self.seen.insert(index);
                }
                (index, player.tile)
            })
            .collect();
        self.removed += self
            .previous
            .keys()
            .filter(|index| !players.contains_key(index))
            .count();
        self.peak = self.peak.max(players.len());
        self.appearances = self.appearances.max(self.seen.len());
        self.npc_peak = self.npc_peak.max(state.entities().npcs().len());
        self.previous = players;
    }
    pub fn report(&self, required: bool) -> Result<(), Box<dyn std::error::Error>> {
        println!(
            "entity review: remote peak {}, appeared {}, moved {}, removed {}; NPC peak {}",
            self.peak, self.appearances, self.moved, self.removed, self.npc_peak
        );
        if required
            && (self.peak == 0 || self.appearances == 0 || self.moved == 0 || self.removed == 0)
        {
            return Err(
                "two-account player update proof requires appearance, movement and removal".into(),
            );
        }
        Ok(())
    }
}

pub fn logout(
    state: &mut GameState,
    scripts: &mut rustclient_application::scripts::ClientScripts<
        rustclient_platform_native::DirectoryBlobs,
    >,
    decoder: &mut rustclient_application::ConnectionDecoder,
    stream: &mut rustclient_platform_native::NativeTcp,
) -> Result<bool, Box<dyn std::error::Error>> {
    use rustclient_platform::ByteStream;
    let tree = rustclient_game_ui::widgets::WidgetTree::build(
        state.interfaces(),
        &scripts.interfaces,
        rustclient_game_ui::widgets::WidgetBounds::canvas(state.interfaces()),
    )?;
    for node in tree.nodes.iter().rev() {
        for (label, action) in
            rustclient_application::widget_actions(state, &scripts.interfaces, node.address)?
        {
            if label.to_ascii_lowercase().contains("logout") {
                let (intents, errors) = scripts.perform_action(state, action)?;
                if !errors.is_empty() {
                    return Err(errors.join("; ").into());
                }
                let transmitted = !intents.is_empty();
                for intent in intents {
                    stream.deadline_after(std::time::Duration::from_secs(2));
                    pollster::block_on(stream.send(&decoder.encode(intent)?))?;
                }
                println!("entity review logout action: {label}");
                return Ok(transmitted);
            }
        }
    }
    Ok(false)
}
