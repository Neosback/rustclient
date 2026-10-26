//! Stock 240 classZM.method14115 / classCX.method2697 chat-prefix and payload policy.
use super::huffman::Huffman;
use crate::{ProtocolError, Result};

/// Validated style and compressed text. Raw prefix parsing stays in the selected profile.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct PublicChatMessage {
    colour: u8,
    effect: u8,
    pattern: Vec<u8>,
    compressed: Vec<u8>,
}
impl PublicChatMessage {
    pub(super) fn append(self, chat_type: u8, body: &mut Vec<u8>) {
        body.extend([self.colour, self.effect]);
        body.extend(self.pattern);
        body.extend(self.compressed);
        // CHAT_SENDPUBLIC passes -1 as the clan-channel argument to the shared packet builder.
        // CHAT_SENDCLAN has a separate script operand and is not implemented by this entry point.
        if chat_type == 3 {
            body.push(255);
        }
    }
}
const COLOURS: [&str; 13] = [
    "yellow:", "red:", "green:", "cyan:", "purple:", "white:", "flash1:", "flash2:", "flash3:",
    "glow1:", "glow2:", "glow3:", "rainbow:",
];

impl Huffman {
    /// Parse at most one colour prefix followed by at most one motion prefix, then compress.
    /// Invalid patterns remain literal text, matching the stock parser.
    /// # Errors
    /// Rejects empty/uncodable text and a payload exceeding `MESSAGE_PUBLIC`'s one-byte length.
    pub fn encode_public_chat(&self, text: &str) -> Result<PublicChatMessage> {
        let mut remaining = text;
        let mut colour = 0;
        let mut pattern = Vec::new();
        if let Some((prefix, index)) = COLOURS
            .iter()
            .zip(0_u8..)
            .find(|(prefix, _)| starts(remaining, prefix))
        {
            colour = index;
            remaining = &remaining[prefix.len()..];
        } else if starts(remaining, "pattern")
            && let Some((end, parsed)) = parse_pattern(&remaining[7..])
        {
            colour = 12
                + u8::try_from(parsed.len())
                    .map_err(|_| ProtocolError::new("public chat", "pattern too long"))?;
            pattern = parsed;
            remaining = &remaining[7 + end..];
        }
        let effects = ["wave:", "wave2:", "shake:", "scroll:", "slide:"];
        let effect = if let Some((prefix, index)) = effects
            .iter()
            .zip(1_u8..)
            .find(|(prefix, _)| starts(remaining, prefix))
        {
            remaining = &remaining[prefix.len()..];
            index
        } else {
            0
        };
        let compressed = self.encode_message(remaining)?;
        // Reserve the type/style bytes; encode() also accounts for the conditional clan byte.
        if 3 + pattern.len() + compressed.len() > 255 {
            return Err(ProtocolError::new(
                "public chat",
                "message exceeds one length byte",
            ));
        }
        Ok(PublicChatMessage {
            colour,
            effect,
            pattern,
            compressed,
        })
    }
}
fn starts(text: &str, prefix: &str) -> bool {
    text.get(..prefix.len())
        .is_some_and(|head| head.eq_ignore_ascii_case(prefix))
}
fn parse_pattern(text: &str) -> Option<(usize, Vec<u8>)> {
    let mut values = Vec::with_capacity(8);
    for (offset, byte) in text.bytes().enumerate().take(9) {
        if byte == b':' {
            return (!values.is_empty()).then_some((offset + 1, values));
        }
        if values.len() == 8 {
            return None;
        }
        let value = match byte.to_ascii_lowercase() {
            b'0'..=b'9' => byte - b'0',
            lower @ b'a'..=b'z' => lower - b'a' + 10,
            _ => return None,
        };
        values.push(value);
    }
    None
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::{ChatIntent, ClientIntent, Isaac};
    fn body(text: &str, chat_type: u8) -> Vec<u8> {
        let codec = Huffman::from_sizes(&[8; 256]).unwrap();
        let message = codec.encode_public_chat(text).unwrap();
        let bytes = ClientIntent::Chat(ChatIntent::Public { chat_type, message })
            .encode(&mut Isaac::new([0; 4]))
            .unwrap();
        assert_eq!(usize::from(bytes[1]), bytes.len() - 2);
        bytes[2..].to_vec()
    }
    #[test]
    fn source_prefixes_style_the_packet_without_becoming_display_text() {
        assert_eq!(
            body("WhItE:WaVe:example", 0),
            [&[0, 5, 1, 7][..], b"example"].concat()
        );
        for (index, name) in [
            "yellow", "red", "green", "cyan", "purple", "white", "flash1", "flash2", "flash3",
            "glow1", "glow2", "glow3", "rainbow",
        ]
        .iter()
        .enumerate()
        {
            assert_eq!(
                body(&format!("{name}:x"), 0),
                vec![0, u8::try_from(index).unwrap(), 0, 1, b'x']
            );
        }
        for (index, name) in ["wave", "wave2", "shake", "scroll", "slide"]
            .iter()
            .enumerate()
        {
            assert_eq!(
                body(&format!("{name}:x"), 0),
                vec![0, 0, u8::try_from(index + 1).unwrap(), 1, b'x']
            );
        }
        assert_eq!(
            body("pattern09AZ:slide:€", 0),
            vec![0, 16, 5, 0, 9, 10, 35, 1, 128]
        );
        assert_eq!(
            body("pattern01234567:x", 3),
            vec![3, 20, 0, 0, 1, 2, 3, 4, 5, 6, 7, 1, b'x', 255]
        );
    }
    #[test]
    fn invalid_patterns_and_prefix_order_remain_literal_and_length_is_bounded() {
        let codec = Huffman::from_sizes(&[8; 256]).unwrap();
        for text in [
            "pattern:x",
            "pattern012345678:x",
            "pattern!:x",
            "pattern0",
            "éwhite:x",
        ] {
            let parsed = codec.encode_public_chat(text).unwrap();
            assert_eq!((parsed.colour, parsed.effect), (0, 0));
            assert_eq!(codec.decode_message(&parsed.compressed).unwrap(), text);
        }
        let parsed = codec.encode_public_chat("wave:white:x").unwrap();
        assert_eq!((parsed.colour, parsed.effect), (0, 1));
        assert_eq!(codec.decode_message(&parsed.compressed).unwrap(), "white:x");
        assert!(codec.encode_public_chat("white:").is_err());
        assert!(codec.encode_public_chat(&"x".repeat(300)).is_err());
    }
}
