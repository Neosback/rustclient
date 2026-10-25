//! Committed actor speech and chat history share the entity-update transaction.
use crate::{ChatError, GameState, PlayerEntity};
use rustclient_model::{GameMessage, OverheadText, PlayerIndex, PlayerInfoUpdate, RemotePlayerUpdate, SpeechUpdate, ids::ChatChannelId};

#[derive(Debug, Clone, PartialEq, Eq)]
pub struct ActorOverhead {
    pub text: OverheadText,
    pub remaining: u32,
    pub autotyper: bool,
    pub filtered: bool,
}
impl ActorOverhead {
    pub(crate) fn advance(value: &mut Option<Self>, cycles: u32) {
        if let Some(active) = value {
            active.remaining = active.remaining.saturating_sub(cycles);
            if active.remaining == 0 { *value = None; }
        }
    }
    pub(crate) fn say(text: &str, remaining: u32) -> Self {
        Self { text: OverheadText { text: text.into(), ..OverheadText::default() }, remaining, autotyper: false, filtered: false }
    }
}

pub(crate) struct PreparedSpeech {
    index: PlayerIndex,
    overhead: ActorOverhead,
    message: Option<GameMessage>,
}
impl GameState {
    /// Stage only text owned by this update; every fallible history check precedes entity mutation.
    pub(crate) fn prepare_speech(&self, update: &PlayerInfoUpdate) -> Result<Vec<PreparedSpeech>, ChatError> {
        if update.speech.len() > 4096 { return Err(ChatError); }
        let mut prepared = Vec::with_capacity(update.speech.len());
        for speech in &update.speech {
            let local = self.entities.local_player_index == Some(speech.index);
            let existing = self.entities.players.get(&speech.index);
            let visible = update.remote.iter().filter_map(|change| match change {
                RemotePlayerUpdate::Enter { index, .. } if *index == speech.index => Some(true),
                RemotePlayerUpdate::Leave { index } if *index == speech.index => Some(false),
                _ => None,
            }).next_back().unwrap_or(existing.is_some());
            if !visible { return Err(ChatError); }
            let appearance = if local { update.local_appearance.as_ref() } else {
                update.remote.iter().rev().find_map(|change| match change {
                    RemotePlayerUpdate::Attributes { index, appearance: Some(a), .. } if *index == speech.index => Some(a),
                    _ => None,
                })
            }.or_else(|| existing.and_then(|p| p.appearance.as_ref()))
                .or_else(|| self.entities.tracked_player(speech.index).and_then(|p| p.appearance.as_ref()));
            let sender = appearance.map(|a| a.display_name.as_str());
            let (overhead, message) = match &speech.update {
                SpeechUpdate::Say(raw) => {
                    let prefixed = raw.starts_with('~');
                    let text = raw.strip_prefix('~').unwrap_or(raw);
                    let message = sender.filter(|_| local || prefixed).map(|name| GameMessage {
                        channel: ChatChannelId::new(2), sender: name.into(), text: text.into(),
                    });
                    (ActorOverhead::say(text, 150), message)
                }
                SpeechUpdate::Public { text, account, autotyper } => {
                    // Stock ignores public speech until appearance/name exists or while hidden.
                    if appearance.is_none_or(|a| a.hidden) { continue; }
                    let name = sender.unwrap_or_default();
                    let sender = account.icon.map_or_else(|| name.into(), |icon| format!("<img={icon}>{name}"));
                    let channel = match (account.moderator, *autotyper) {
                        (true, true) => 91, (true, false) => 1, (false, true) => 90, (false, false) => 2,
                    };
                    let mut displayed = text.clone();
                    displayed.text = displayed.text.trim().to_owned();
                    let filtered = !local && account.ignorable && !self.chat.filter_text.is_empty()
                        && !text.text.to_lowercase().contains(&self.chat.filter_text);
                    let overhead = ActorOverhead { text: displayed, remaining: 150, autotyper: *autotyper, filtered };
                    (overhead, Some(GameMessage { channel: ChatChannelId::new(channel), sender, text: text.text.clone() }))
                }
            };
            if overhead.text.text.len() > 4096 || overhead.text.text.contains('\0') || overhead.text.pattern.len() > 8 { return Err(ChatError); }
            if let Some(message) = &message { self.chat.validate_message(message)?; }
            prepared.push(PreparedSpeech { index: speech.index, overhead, message });
        }
        let count = u32::try_from(prepared.iter().filter(|p| p.message.is_some()).count()).map_err(|_| ChatError)?;
        self.chat.next_id().checked_add(count).ok_or(ChatError)?;
        Ok(prepared)
    }
    pub(crate) fn commit_speech(&mut self, prepared: Vec<PreparedSpeech>) -> bool {
        let mut history_changed = false;
        for speech in prepared {
            if let Some(PlayerEntity { overhead, .. }) = self.entities.players.get_mut(&speech.index) {
                *overhead = Some(speech.overhead);
                if let Some(message) = speech.message {
                    self.chat.push(message, self.scenery.cycle()).expect("history prevalidated before entity mutation");
                    history_changed = true;
                }
            }
        }
        history_changed
    }
}
