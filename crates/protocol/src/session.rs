//! Dispatch wire state by explicitly selected profile. No revision branches escape this boundary.
use crate::{ClientIntent, Isaac, PacketBatch, Result, ServerUpdate};

pub enum SessionCodec {
    OpenRune(Box<crate::openrune_240::game::SessionCodec>),
    Tsps(Box<crate::tsps_241::SessionCodec>),
}
impl SessionCodec {
    /// Existing `OpenRune` constructor retained for its callers and fixtures.
    #[must_use]
    pub fn new(incoming: Isaac, outgoing: Isaac, player: rustclient_model::PlayerIndex) -> Self {
        Self::OpenRune(Box::new(crate::openrune_240::game::SessionCodec::new(
            incoming, outgoing, player,
        )))
    }
    #[must_use]
    pub fn tsps(login: crate::tsps_241::LoginSession) -> Self {
        Self::Tsps(Box::new(crate::tsps_241::SessionCodec::new(login)))
    }
    pub fn set_chat_codec(&mut self, codec: crate::Huffman) {
        match self {
            Self::OpenRune(c) => c.set_chat_codec(codec),
            Self::Tsps(c) => c.set_chat_codec(codec),
        }
    }
    pub fn set_varbit_lookup(&mut self, lookup: crate::VarbitLookup) {
        if let Self::Tsps(codec) = self {
            codec.set_varbit_lookup(lookup);
        }
    }
    pub fn set_npc_shapes(&mut self, shapes: crate::NpcShapeLookup) {
        if let Self::OpenRune(c) = self {
            c.set_npc_shapes(shapes);
        }
    }
    #[must_use]
    pub fn framed_packets(&self) -> u64 {
        match self {
            Self::OpenRune(c) => c.framed_packets(),
            Self::Tsps(c) => c.framed_packets(),
        }
    }
    #[must_use]
    pub fn awaiting_initial_rebuild(&self) -> bool {
        match self {
            Self::OpenRune(c) => c.awaiting_initial_rebuild(),
            Self::Tsps(c) => c.awaiting_initial_rebuild(),
        }
    }
    #[must_use]
    pub fn unhandled_opcodes(&self) -> &std::collections::BTreeMap<u16, u64> {
        match self {
            Self::OpenRune(c) => c.unhandled_opcodes(),
            Self::Tsps(c) => c.unhandled_opcodes(),
        }
    }
    pub fn take_new_unhandled(&mut self) -> Vec<u16> {
        match self {
            Self::OpenRune(c) => c.take_new_unhandled(),
            Self::Tsps(_) => Vec::new(),
        }
    }
    /// # Errors
    /// Rejects unsupported intents before changing wire state.
    pub fn encode(&mut self, intent: ClientIntent) -> Result<Vec<u8>> {
        match self {
            Self::OpenRune(c) => c.encode(intent),
            Self::Tsps(c) => c.encode(intent),
        }
    }
    /// # Errors
    /// Malformed or unsupported required packets fail with profile context.
    pub fn receive_batches(&mut self, bytes: &[u8], emit: impl FnMut(PacketBatch)) -> Result<()> {
        match self {
            Self::OpenRune(c) => c.receive_batches(bytes, emit),
            Self::Tsps(c) => c.receive_batches(bytes, emit),
        }
    }
    /// # Errors
    /// Forwards profile decode failures.
    pub fn receive(&mut self, bytes: &[u8], mut emit: impl FnMut(ServerUpdate)) -> Result<()> {
        self.receive_batches(bytes, |batch| {
            for update in batch.updates {
                emit(update);
            }
        })
    }
}
