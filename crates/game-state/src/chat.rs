//! Bounded server-driven message history shared by scripts and presentation.
use rustclient_model::{ClientCycle, GameMessage, ids::ChatChannelId};
use std::collections::VecDeque;

#[derive(Debug, Clone, PartialEq, Eq)]
pub struct ChatEntry {
    pub id: u32,
    pub cycle: ClientCycle,
    pub message: GameMessage,
}
#[derive(Debug, Default)]
pub struct ChatState {
    entries: VecDeque<ChatEntry>,
    next_id: u32,
    pub(crate) filter_text: String,
    pub(crate) filters: [u8; 3],
}
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ChatError;
impl core::fmt::Display for ChatError {
    fn fmt(&self, f: &mut core::fmt::Formatter<'_>) -> core::fmt::Result {
        f.write_str("chat history identity or message allocation limit")
    }
}
impl std::error::Error for ChatError {}
impl ChatState {
    #[must_use]
    pub fn filter_text(&self) -> &str {
        &self.filter_text
    }
    #[must_use]
    pub const fn filters(&self) -> [u8; 3] {
        self.filters
    }
    #[must_use]
    pub fn entries(&self) -> &VecDeque<ChatEntry> {
        &self.entries
    }
    #[must_use]
    pub const fn next_id(&self) -> u32 {
        self.next_id
    }
    pub fn channel(&self, channel: ChatChannelId) -> impl Iterator<Item = &ChatEntry> {
        self.entries
            .iter()
            .rev()
            .filter(move |e| e.message.channel == channel)
    }
    pub(crate) fn validate_message(&self, message: &GameMessage) -> Result<(), ChatError> {
        if message.sender.len() > 320
            || message.text.len() > 4096
            || message.sender.contains('\0')
            || message.text.contains('\0')
        {
            return Err(ChatError);
        }
        Ok(())
    }
    pub(crate) fn push(
        &mut self,
        message: GameMessage,
        cycle: ClientCycle,
    ) -> Result<u32, ChatError> {
        let next = self.next_id.checked_add(1).ok_or(ChatError)?;
        self.validate_message(&message)?;
        if self.channel(message.channel).count() >= 100
            && let Some(index) = self
                .entries
                .iter()
                .position(|e| e.message.channel == message.channel)
        {
            self.entries.remove(index);
        }
        if self.entries.len() >= 4096 {
            self.entries.pop_front();
        }
        let id = self.next_id;
        self.next_id = next;
        self.entries.push_back(ChatEntry { id, cycle, message });
        Ok(id)
    }
}
