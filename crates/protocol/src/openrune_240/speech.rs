//! Selected RSProt PlayerChatEncoder / PlayerSayEncoder; classDF / classDC chat semantics.
use crate::{ProtocolError, Result, wire::Reader};
use super::huffman::Huffman;
use rustclient_model::{ChatAccount, OverheadText, SpeechUpdate};

pub(super) fn say(r: &mut Reader<'_>) -> Result<String> {
    let mut text = String::new();
    for _ in 0..4096 {
        let byte = r.u8()?;
        if byte == 0 { return Ok(text); }
        text.push(crate::wire::decode_cp1252(byte));
    }
    Err(ProtocolError::new("speech", "unterminated or oversized text"))
}

pub(super) fn public(r: &mut Reader<'_>, codec: Option<&Huffman>) -> Result<SpeechUpdate> {
    let style = r.u16()?;
    let account = account(r.u8_add()?)?;
    let autotyper = r.u8_neg()? != 0;
    let length = usize::from(r.u8_add()?);
    // RSProt pdataAlt3 reverses the buffer and adds 128 to each byte.
    let coded: Vec<u8> = r.take(length)?.iter().rev().map(|b| b.wrapping_sub(128)).collect();
    let colour = style.to_be_bytes()[0];
    let mut pattern = Vec::new();
    if (13..=20).contains(&colour) {
        for _ in 0..colour - 12 { pattern.push(r.u8_neg()?); }
    }
    let codec = codec.ok_or_else(|| ProtocolError::new("public chat", "cache Huffman codec not installed"))?;
    Ok(SpeechUpdate::Public {
        text: OverheadText { text: format_chat_text(&codec.decode_message(&coded)?),
            colour, effect: style.to_be_bytes()[1], pattern }, account, autotyper,
    })
}

fn account(id: u8) -> Result<ChatAccount> {
    // Exact 240 classKL account table: icon, moderator, subject to ignore/text filtering.
    const TYPES: [(Option<u16>, bool, bool); 17] = [
        (None,false,true),(Some(0),true,true),(Some(1),true,false),
        (Some(2),false,true),(Some(3),false,true),(Some(10),false,true),
        (Some(22),false,true),(Some(41),false,true),(Some(42),false,true),
        (Some(43),false,true),(Some(44),false,true),(Some(45),false,true),
        (Some(46),false,true),(Some(47),false,true),(Some(48),false,true),
        (Some(49),false,true),(Some(52),false,true),
    ];
    let &(icon, moderator, ignorable) = TYPES.get(usize::from(id))
        .ok_or_else(|| ProtocolError::new("public chat", "unknown account type"))?;
    Ok(ChatAccount { icon, moderator, ignorable })
}

/// Client chat text policy: sentence-case the string, then escape `<`, `>` and `@` so rich-text
/// markup cannot be injected (deob `AbstractFont.method6(classJE.method6400(..))`).
#[must_use]
pub(super) fn format_chat_text(raw: &str) -> String {
    // State 0: inside a word, 1: after a space or punctuation, 2: at a sentence start.
    let mut state = 2_u8;
    let mut out = String::with_capacity(raw.len());
    for ch in raw.chars() {
        let ch = match state {
            0 => single(ch.to_lowercase()),
            2 => title(ch),
            _ if ch.is_uppercase() => title(ch),
            _ => ch,
        };
        state = if ch.is_alphabetic() {
            0
        } else if matches!(ch, '.' | '?' | '!') {
            2
        } else if ch.is_whitespace() {
            if state == 2 { 2 } else { 1 }
        } else {
            1
        };
        match ch {
            '<' => out.push_str("<lt>"),
            '>' => out.push_str("<gt>"),
            '@' => out.push_str("<at>"),
            other => out.push(other),
        }
    }
    out
}

fn single(mut chars: impl Iterator<Item = char>) -> char {
    let first = chars.next().unwrap_or('\u{fffd}');
    if chars.next().is_some() { '\u{fffd}' } else { first }
}

/// Java `Character.toTitleCase` for the Windows-1252 range; µ and ƒ are left unchanged.
fn title(ch: char) -> char {
    if matches!(ch, '\u{b5}' | '\u{192}') {
        return ch;
    }
    let mut upper = ch.to_uppercase();
    match (upper.next(), upper.next()) {
        (Some(single), None) => single,
        _ => ch,
    }
}

