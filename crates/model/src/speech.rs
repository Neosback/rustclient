//! Source-neutral actor speech received with an entity update.
use crate::PlayerIndex;

#[derive(Debug, Clone, Default, PartialEq, Eq)]
pub struct OverheadText {
    pub text: String,
    pub colour: u8,
    pub effect: u8,
    pub pattern: Vec<u8>,
}

/// Resolved account presentation; profile-specific account ids stay in the adapter.
#[derive(Debug, Clone, Copy, Default, PartialEq, Eq)]
pub struct ChatAccount {
    pub icon: Option<u16>,
    pub moderator: bool,
    pub ignorable: bool,
}
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum SpeechUpdate {
    Say(String),
    Public { text: OverheadText, account: ChatAccount, autotyper: bool },
}
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct PlayerSpeech {
    pub index: PlayerIndex,
    pub update: SpeechUpdate,
}
