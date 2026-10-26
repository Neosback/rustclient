//! Chat text compression: the revision-240 client's Huffman code, built from the cache's
//! per-byte code lengths (index 10, group `huffman`).
use crate::wire::{ProtocolError, Result};

/// Canonical code assignment for one 256-entry length table.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct Huffman {
    sizes: [u8; 256],
    /// Code for each byte, left-aligned in 32 bits.
    masks: [u32; 256],
    tree: Vec<DecodeNode>,
}

#[derive(Debug, Clone, Default, PartialEq, Eq)]
struct DecodeNode {
    children: [Option<usize>; 2],
    symbol: Option<u8>,
}

impl Huffman {
    /// Assign codes from per-byte code lengths exactly as the reference constructor does.
    /// # Errors
    /// Rejects tables that are not 256 entries or that hold a length above 32 bits.
    pub fn from_sizes(table: &[u8]) -> Result<Self> {
        let sizes: [u8; 256] = table.try_into().map_err(|_| {
            ProtocolError::new("huffman", "code length table must hold 256 entries")
        })?;
        if sizes.iter().any(|&size| size > 32) {
            return Err(ProtocolError::new("huffman", "code length above 32 bits"));
        }
        let mut masks = [0_u32; 256];
        let mut next = [0_u32; 33];
        for (symbol, &size) in sizes.iter().enumerate() {
            let size = usize::from(size);
            if size == 0 {
                continue;
            }
            let bit = 1_u32 << (32 - size);
            let code = next[size];
            masks[symbol] = code;
            let after = if code & bit == 0 {
                for shorter in (1..size).rev() {
                    let value = next[shorter];
                    if value != code {
                        break;
                    }
                    let shorter_bit = 1_u32 << (32 - shorter);
                    if value & shorter_bit != 0 {
                        next[shorter] = next[shorter - 1];
                        break;
                    }
                    next[shorter] = value | shorter_bit;
                }
                code | bit
            } else {
                next[size - 1]
            };
            next[size] = after;
            for value in next.iter_mut().skip(size + 1) {
                if *value == code {
                    *value = after;
                }
            }
        }
        let mut tree = vec![DecodeNode::default()];
        for (symbol, &size) in sizes.iter().enumerate().filter(|(_, size)| **size != 0) {
            let mut node = 0;
            for shift in (32 - size..32).rev() {
                if tree[node].symbol.is_some() {
                    return Err(ProtocolError::new("huffman", "overlapping prefix codes"));
                }
                let branch = usize::from((masks[symbol] >> shift & 0x1) != 0);
                node = if let Some(next) = tree[node].children[branch] {
                    next
                } else {
                    let next = tree.len();
                    tree.push(DecodeNode::default());
                    tree[node].children[branch] = Some(next);
                    next
                };
            }
            if tree[node].symbol.is_some() || tree[node].children.iter().any(Option::is_some) {
                return Err(ProtocolError::new("huffman", "overlapping codes"));
            }
            tree[node].symbol = u8::try_from(symbol).ok();
        }
        Ok(Self { sizes, masks, tree })
    }
    /// Decode a smart-length, bounded CP1252 message.
    /// # Errors
    /// Rejects truncated codes and decoded lengths above 32767 bytes.
    pub fn decode_message(&self, bytes: &[u8]) -> Result<String> {
        let fail = |detail| ProtocolError::new("chat decode", detail);
        let (&first, rest) = bytes.split_first().ok_or_else(|| fail("empty message"))?;
        let (length, data) = if first < 128 {
            (usize::from(first), rest)
        } else {
            let (&second, data) = rest.split_first().ok_or_else(|| fail("truncated length"))?;
            (
                usize::from(u16::from_be_bytes([first, second]) - 0x8000),
                data,
            )
        };
        if length > 32767 {
            return Err(fail("message length limit"));
        }
        let mut out = String::with_capacity(length);
        let mut bit = 0_usize;
        for _ in 0..length {
            let mut node = 0;
            let byte = loop {
                let value = data
                    .get(bit / 8)
                    .ok_or_else(|| fail("truncated code bits"))?;
                let branch = usize::from(value & (0x80 >> (bit % 8)) != 0);
                node = self.tree[node].children[branch].ok_or_else(|| fail("unassigned code"))?;
                bit += 1;
                if let Some(symbol) = self.tree[node].symbol {
                    break symbol;
                }
            };
            out.push(crate::wire::decode_cp1252(byte));
        }
        Ok(out)
    }
    /// Frame a chat string: its byte length as a one- or two-byte smart, then the coded bits.
    /// # Errors
    /// Rejects empty or oversized text and bytes the table cannot code.
    pub fn encode_message(&self, text: &str) -> Result<Vec<u8>> {
        let bytes: Vec<u8> = text
            .chars()
            .map(|ch| crate::wire::cp1252(ch).unwrap_or(b'?'))
            .collect();
        if bytes.is_empty() || bytes.len() > 255 {
            return Err(ProtocolError::new(
                "chat encode",
                "message length outside 1..=255",
            ));
        }
        let mut out = Vec::with_capacity(bytes.len());
        match u8::try_from(bytes.len()) {
            Ok(length) if length < 128 => out.push(length),
            _ => out.extend((u16::try_from(bytes.len()).unwrap_or(0) | 0x8000).to_be_bytes()),
        }
        let mut bit_count = 0_usize;
        for &byte in &bytes {
            let size = usize::from(self.sizes[usize::from(byte)]);
            if size == 0 {
                return Err(ProtocolError::new("chat encode", "character has no code"));
            }
            let mask = self.masks[usize::from(byte)];
            for index in 0..size {
                if bit_count.is_multiple_of(8) {
                    out.push(0);
                }
                if mask >> (31 - index) & 1 == 1
                    && let Some(last) = out.last_mut()
                {
                    *last |= 0x80 >> (bit_count % 8);
                }
                bit_count += 1;
            }
        }
        Ok(out)
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    /// Eight-bit codes in symbol order, so each byte codes as itself.
    fn flat() -> Huffman {
        Huffman::from_sizes(&[8; 256]).unwrap()
    }

    #[test]
    fn equal_length_codes_are_assigned_in_symbol_order() {
        let huffman = flat();
        for symbol in [0_usize, 1, 65, 255] {
            assert_eq!(huffman.masks[symbol], u32::try_from(symbol).unwrap() << 24);
        }
    }

    #[test]
    fn messages_carry_a_length_prefix_and_packed_codes() {
        assert_eq!(flat().encode_message("Hi").unwrap(), [2, b'H', b'i']);
        let long = "a".repeat(130);
        let coded = flat().encode_message(&long).unwrap();
        assert_eq!(coded[..2], [0x80, 130]);
        assert_eq!(coded.len(), 2 + 130);
    }

    #[test]
    fn shorter_codes_pack_across_byte_boundaries() {
        // 'a' and 'b' get 2-bit codes 00 and 01, every other byte 8 bits is not needed here.
        let mut sizes = [0_u8; 256];
        sizes[usize::from(b'a')] = 2;
        sizes[usize::from(b'b')] = 2;
        let huffman = Huffman::from_sizes(&sizes).unwrap();
        // a=00 b=01 → "abab" is 00 01 00 01 = 0x11 after the length byte.
        assert_eq!(huffman.encode_message("abab").unwrap(), [4, 0x11]);
    }

    #[test]
    fn invalid_tables_and_uncodable_text_are_rejected() {
        assert!(Huffman::from_sizes(&[8; 255]).is_err());
        let mut sizes = [8_u8; 256];
        sizes[0] = 33;
        assert!(Huffman::from_sizes(&sizes).is_err());
        let mut sparse = [0_u8; 256];
        sparse[usize::from(b'a')] = 1;
        let huffman = Huffman::from_sizes(&sparse).unwrap();
        assert!(huffman.encode_message("z").is_err());
        assert!(huffman.encode_message("").is_err());
    }

    #[test]
    fn chat_intents_frame_as_var_byte_public_messages_and_null_terminated_commands() {
        use super::super::game::{ChatIntent, ClientIntent};
        let encode = |chat| {
            let mut cipher = crate::Isaac::new([1, 2, 3, 4]);
            let mut mirror = crate::Isaac::new([1, 2, 3, 4]);
            let bytes = ClientIntent::Chat(chat).encode(&mut cipher).unwrap();
            let key = mirror.next_word().to_le_bytes()[0];
            (bytes[0].wrapping_sub(key), bytes[1..].to_vec())
        };
        let message = flat().encode_public_chat("Hi").unwrap();
        assert_eq!(
            encode(ChatIntent::Public {
                chat_type: 0,
                message
            }),
            (56, vec![6, 0, 0, 0, 2, b'H', b'i'])
        );
        assert_eq!(
            encode(ChatIntent::Command("tele".into())),
            (79, vec![5, b't', b'e', b'l', b'e', 0])
        );
        let mut cipher = crate::Isaac::new([1, 2, 3, 4]);
        assert!(
            ClientIntent::Chat(ChatIntent::Command("x".repeat(300)))
                .encode(&mut cipher)
                .is_err()
        );
    }
}
