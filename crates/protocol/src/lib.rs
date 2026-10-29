//! Explicit server-profile wire adapters. No sockets, filesystem paths or semantic mutation.
#![forbid(unsafe_code)]
mod crypto;
pub mod openrune_240;
mod wire;
pub use crypto::Isaac;
pub use openrune_240::control::SessionControl;
pub use openrune_240::game::{
    Bootstrap, ChatIntent, ClientIntent, GameFramer, GamePacket, PacketBatch, decode_bootstrap,
};
pub use openrune_240::huffman::Huffman;
pub use openrune_240::js5::verify_cache;
pub use openrune_240::login::{
    Credentials, LoginAttempt, LoginEntropy, LoginSession, PublicKey, authenticate,
};
pub use openrune_240::outgoing_chat::PublicChatMessage;
pub use rustclient_model::ServerProfileId;
pub use wire::{ProtocolError, Result};

/// Complete supported profile selection. Wire mappings remain in this adapter crate.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ServerProfile {
    id: ServerProfileId,
}
impl ServerProfile {
    pub const OPENRUNE_240_2: Self = Self {
        id: ServerProfileId::new(1),
    };
    #[must_use]
    pub const fn id(self) -> ServerProfileId {
        self.id
    }
    #[must_use]
    pub const fn revision(self) -> rustclient_model::Revision {
        openrune_240::REVISION
    }
    /// # Errors
    /// Rejects unknown profiles instead of inferring them from cache names.
    pub fn parse(name: &str) -> Result<Self> {
        if name == "openrune-240.2" {
            Ok(Self::OPENRUNE_240_2)
        } else {
            Err(ProtocolError::new("profile", "unsupported server profile"))
        }
    }
}
pub use openrune_240::game::{ServerUpdate, SessionCodec, VariableUpdate};
pub use openrune_240::loc_anim::LocAnimUpdate;
pub use openrune_240::npc_info::{NewNpc, NpcInfoUpdate, NpcMovement};
pub use openrune_240::player_info::{PlayerInfoUpdate, PlayerMovement};

pub use openrune_240::login_checksums;

/// The selected desktop profile's immutable login integrity block.
pub type LoginChecksums = [u32; 23];

pub use openrune_240::MinimapClick;
