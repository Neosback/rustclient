//! `MESSAGE_GAME`, rsprox osrs-240 `MessageGameDecoder`: smart channel, optional sender, text.
use crate::{ProtocolError, Result, wire::Reader};
use rustclient_model::{GameMessage, ids::ChatChannelId};
fn text(r: &mut Reader<'_>) -> Result<String> {
    let mut value = String::new();
    loop {
        let byte = r.u8()?;
        if byte == 0 {
            return Ok(value);
        }
        value.push(crate::wire::decode_cp1252(byte));
    }
}
pub(super) fn decode(bytes: &[u8]) -> Result<GameMessage> {
    if bytes.len() > 255 {
        return Err(ProtocolError::new("game message", "payload limit"));
    }
    let mut r = Reader::new(bytes, "game message");
    let first = r.u8()?;
    let channel = if first < 128 {
        u16::from(first)
    } else {
        (u16::from(first) << 8 | u16::from(r.u8()?)) - 32768
    };
    let sender = match r.u8()? {
        0 => String::new(),
        1 => text(&mut r)?,
        _ => return Err(ProtocolError::new("game message", "invalid sender flag")),
    };
    let message = GameMessage {
        channel: ChatChannelId::new(u32::from(channel)),
        sender,
        text: text(&mut r)?,
    };
    if r.offset != bytes.len() {
        return Err(ProtocolError::new("game message", "trailing bytes"));
    }
    Ok(message)
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn game_messages_decode_smart_channels_names_and_reject_malformed_payloads() {
        let bytes = b"\x80\xc8\x01Banker\0Welcome.\0";
        let m = decode(bytes).unwrap();
        assert_eq!(
            (m.channel.get(), m.sender.as_str(), m.text.as_str()),
            (200, "Banker", "Welcome.")
        );
        for end in 0..bytes.len() {
            assert!(decode(&bytes[..end]).is_err());
        }
        assert!(decode(b"\x00\x02x\0").is_err());
        assert!(decode(b"\x00\x00x\0extra").is_err());
    }
}
