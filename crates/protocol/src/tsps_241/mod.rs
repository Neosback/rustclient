//! TSPS custom binary protocol at the pinned cache-241 baseline (SPEC-0031).
//! This adapter is experimental; unsupported required behavior fails explicitly.
mod appearance;
mod framing;
mod interfaces;
mod lengths;
mod login;
mod outbound;
mod players;
mod reader;
mod scripts;
mod updates;
use crate::{ClientIntent, Huffman, PacketBatch, ProtocolError, Result, VarbitLookup};
use framing::Framer;
pub use login::{LoginSession, authenticate};
use rustclient_model::{PlayerAppearance, PlayerIndex, VarbitId};

pub struct SessionCodec {
    framer: Framer,
    player: PlayerIndex,
    appearance: Option<PlayerAppearance>,
    players: crate::openrune_240::player_info::PlayerInfoDecoder,
    chat: Option<Huffman>,
    varbits: Option<VarbitLookup>,
    awaiting_rebuild: bool,
    framed_packets: u64,
    unhandled: std::collections::BTreeMap<u16, u64>,
}
impl SessionCodec {
    #[must_use]
    pub fn new(login: LoginSession) -> Self {
        Self {
            framer: Framer::default(),
            player: login.player,
            appearance: Some(login.appearance),
            players: crate::openrune_240::player_info::PlayerInfoDecoder::tsps(login.player),
            chat: None,
            varbits: None,
            awaiting_rebuild: true,
            framed_packets: 0,
            unhandled: std::collections::BTreeMap::new(),
        }
    }
    pub fn set_chat_codec(&mut self, codec: Huffman) {
        self.chat = Some(codec);
    }
    pub fn set_varbit_lookup(&mut self, lookup: VarbitLookup) {
        self.varbits = Some(lookup);
    }
    fn varbit(&mut self, id: u32, value: i32) -> Result<crate::ServerUpdate> {
        let lookup = self.varbits.as_mut().ok_or_else(|| {
            ProtocolError::new("TSPS varbit", "cache definition resolver is unavailable")
        })?;
        let range = lookup(VarbitId::new(id))?;
        Ok(crate::ServerUpdate::Variable(
            crate::VariableUpdate::SetBits { range, value },
        ))
    }
    #[must_use]
    pub const fn framed_packets(&self) -> u64 {
        self.framed_packets
    }
    #[must_use]
    pub const fn awaiting_initial_rebuild(&self) -> bool {
        self.awaiting_rebuild
    }
    #[must_use]
    pub const fn unhandled_opcodes(&self) -> &std::collections::BTreeMap<u16, u64> {
        &self.unhandled
    }
    /// # Errors
    /// Unsupported or out-of-range intents never produce partial packets.
    pub fn encode(&mut self, intent: ClientIntent) -> Result<Vec<u8>> {
        outbound::encode(intent, self.chat.as_ref())
    }
    /// # Errors
    /// Rejects malformed or unsupported required packets before publishing that packet's updates.
    pub fn receive_batches(
        &mut self,
        bytes: &[u8],
        mut emit: impl FnMut(PacketBatch),
    ) -> Result<()> {
        self.framer.feed(bytes)?;
        while let Some(packet) = self.framer.next()? {
            let updates = self.decode(packet.opcode, &packet.payload).map_err(|e| {
                ProtocolError::new(e.stage, format!("opcode {}: {}", packet.opcode, e.detail))
            })?;
            self.framed_packets = self.framed_packets.saturating_add(1);
            emit(PacketBatch {
                packets: 1,
                wire_bytes: packet.wire_bytes,
                updates,
            });
        }
        Ok(())
    }
}
