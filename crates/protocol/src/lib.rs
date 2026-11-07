//! Explicit server-profile wire adapters. No sockets, filesystem paths or semantic mutation.
#![forbid(unsafe_code)]
mod crypto;
mod messages;
pub mod openrune_240;
mod session;
pub mod tsps_241;
mod wire;
pub use crypto::Isaac;
pub use messages::{
    Bootstrap, ChatIntent, ClientIntent, ExamineTarget, PacketBatch, ServerUpdate, VarbitLookup,
    VariableUpdate,
};
pub use openrune_240::control::SessionControl;
pub use openrune_240::game::{GameFramer, GamePacket, decode_bootstrap};
pub use openrune_240::huffman::Huffman;
pub use openrune_240::js5::verify_cache;
pub use openrune_240::login::{
    Credentials, LoginAttempt, LoginEntropy, LoginSession, PublicKey, authenticate,
};
pub use openrune_240::outgoing_chat::PublicChatMessage;
pub use openrune_240::resume::ResumeIntent;
pub use rustclient_model::ServerProfileId;
pub use session::SessionCodec;
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
    /// TSPS custom binary protocol, distinct from stock revision-241 wire compatibility.
    pub const TSPS_241: Self = Self {
        id: ServerProfileId::new(2),
    };
    #[must_use]
    pub const fn id(self) -> ServerProfileId {
        self.id
    }
    /// Selectable development profiles are not necessarily validated gameplay targets.
    #[must_use]
    pub const fn is_experimental(self) -> bool {
        self.id.get() == Self::TSPS_241.id.get()
    }
    #[must_use]
    pub const fn revision(self) -> rustclient_model::Revision {
        if self.id.get() == 2 {
            rustclient_model::Revision::new(241)
        } else {
            openrune_240::REVISION
        }
    }
    /// # Errors
    /// Rejects unknown profiles instead of inferring them from cache names.
    /// A parsed experimental profile is not a declaration of playable compatibility.
    pub fn parse(name: &str) -> Result<Self> {
        match name {
            "openrune-240.2" => Ok(Self::OPENRUNE_240_2),
            "tsps-241" => Ok(Self::TSPS_241),
            _ => Err(ProtocolError::new("profile", "unsupported server profile")),
        }
    }
}

pub use openrune_240::loc_anim::LocAnimUpdate;
pub use openrune_240::npc_extended::NpcShapeLookup;
pub use openrune_240::npc_info::{NewNpc, NpcInfoUpdate, NpcMovement};
pub use openrune_240::player_info::{PlayerInfoUpdate, PlayerMovement};

pub use openrune_240::login_checksums;

/// The selected desktop profile's immutable login integrity block.
pub type LoginChecksums = [u32; 23];

pub use openrune_240::MinimapClick;
