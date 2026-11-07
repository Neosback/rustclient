use super::{
    framing::{Framer, packet},
    reader::{Reader, text},
};
use crate::{
    Credentials, ProtocolError, Result,
    wire::{read, send},
};
use rustclient_model::{
    Gender, ItemDefinitionId, KIT_SLOT, KitId, PlayerAppearance, PlayerIndex, SlotContent,
};
use rustclient_platform::ByteStream;
use zeroize::Zeroizing;

/// Non-secret authenticated identity. TSPS does not use `OpenRune`'s ISAAC login state.
pub struct LoginSession {
    pub player: PlayerIndex,
    pub member: bool,
    pub staff_level: u8,
    pub appearance: PlayerAppearance,
}

/// Use the pinned TSPS password/handshake contract, without retaining credentials after the attempt.
/// # Errors
/// Bounds input and reports typed refusals, malformed replies or transport failure without secrets.
pub async fn authenticate(
    stream: &mut impl ByteStream,
    credentials: Credentials,
) -> Result<LoginSession> {
    let mut hello = b"rustclient\0\0".to_vec();
    send(stream, &packet(200, &hello)?, "TSPS hello").await?;
    hello.clear();
    let mut body = Zeroizing::new(Vec::new());
    text(&mut body, &credentials.username)?;
    text(&mut body, &credentials.password)?;
    body.extend(241_i32.to_be_bytes());
    body.push(0); // Password login; remembered-login tokens are not requested.
    let request = Zeroizing::new(packet(204, &body)?);
    send(stream, &request, "TSPS login").await?;
    drop(request);
    drop(body);
    drop(credentials);
    let mut framer = Framer::default();
    let mut accepted = false;
    for _ in 0..65_536 {
        // Stop exactly at the handshake boundary. The transport retains subsequent batched packets.
        let byte = read::<1>(stream, "TSPS authentication").await?;
        framer.feed(&byte)?;
        let Some(packet) = framer.next()? else {
            continue;
        };
        let mut r = Reader::new(&packet.payload);
        match packet.opcode {
            0 => {
                r.i32()?;
                r.i32()?;
                r.finish()?;
            }
            3 if !accepted => {
                let success = r.boolean()?;
                let code = r.i32()?;
                r.string()?; // Untrusted server error text is never included in credential diagnostics.
                r.string()?; // Display name is supplied in the authenticated handshake.
                if r.remaining() > 0 {
                    r.string()?;
                } // Optional token; not requested, never persisted.
                r.finish()?;
                if !success {
                    let mut error = ProtocolError::new(
                        "TSPS authentication",
                        format!("login refused (code {code})"),
                    );
                    error.rejection = Some(if code == 2 {
                        rustclient_model::LoginRejection::WorldFull
                    } else {
                        rustclient_model::LoginRejection::from_code(u8::try_from(code).unwrap_or(0))
                    });
                    return Err(error);
                }
                accepted = true;
                // Empty name, no appearance override, desktop layout; do not claim gateway-native packets.
                send(stream, &packet::handshake()?, "TSPS handshake").await?;
            }
            2 if accepted => return handshake(&mut r),
            _ => {
                return Err(ProtocolError::new(
                    "TSPS authentication",
                    "unexpected packet before handshake",
                ));
            }
        }
    }
    Err(ProtocolError::new(
        "TSPS authentication",
        "login byte budget exceeded",
    ))
}
mod packet {
    pub(super) fn handshake() -> crate::Result<Vec<u8>> {
        super::packet(202, &[0, 0, 0])
    }
}
fn handshake(r: &mut Reader<'_>) -> Result<LoginSession> {
    let id = r.u32()?;
    if !(1..2048).contains(&id) {
        return Err(ProtocolError::new(
            "TSPS handshake",
            "invalid local player index",
        ));
    }
    let mut appearance = PlayerAppearance {
        display_name: r.string()?,
        ..PlayerAppearance::default()
    };
    if r.boolean()? {
        appearance.gender = match r.u8()? {
            0 => Gender::Male,
            1 => Gender::Female,
            _ => {
                return Err(ProtocolError::new(
                    "TSPS handshake",
                    "unsupported body type",
                ));
            }
        };
        let colors = usize::from(r.u8()?);
        if colors != appearance.colors.len() {
            return Err(ProtocolError::new("TSPS handshake", "invalid colour count"));
        }
        for color in &mut appearance.colors {
            *color = r.u8()?;
        }
        if usize::from(r.u8()?) != KIT_SLOT.len() {
            return Err(ProtocolError::new("TSPS handshake", "invalid kit count"));
        }
        for slot in KIT_SLOT {
            let kit = r.u16()?;
            if kit != u16::MAX {
                appearance.slots[slot] = SlotContent::Kit(KitId::new(u32::from(kit)));
            }
        }
        let count = usize::from(r.u8()?);
        if count != 14 {
            return Err(ProtocolError::new(
                "TSPS handshake",
                "invalid equipment count",
            ));
        }
        for index in 0..count {
            let item = r.u16()?;
            if item != u16::MAX {
                let slot = appearance.slots.get_mut(index).ok_or_else(|| {
                    ProtocolError::new(
                        "TSPS handshake",
                        "ring/ammo appearance extensions are not implemented",
                    )
                })?;
                *slot = SlotContent::Item(ItemDefinitionId::new(u32::from(item)));
            }
        }
    }
    if r.u8()? != 0 {
        return Err(ProtocolError::new(
            "TSPS handshake",
            "chat icon projection is not implemented",
        ));
    }
    if !r.string()?.is_empty() {
        return Err(ProtocolError::new(
            "TSPS handshake",
            "chat prefix projection is not implemented",
        ));
    }
    let admin = r.boolean()?;
    let member = r.boolean()?;
    r.finish()?;
    Ok(LoginSession {
        player: PlayerIndex::new(id),
        member,
        staff_level: u8::from(admin) * 2,
        appearance,
    })
}
