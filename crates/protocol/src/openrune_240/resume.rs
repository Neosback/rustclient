//! Values a client script returns to the server's pending input prompt (`RESUME_P_*`).
//!
//! Stock senders are CS2 ops 3104 (count), 3105 (name), 3106 (string), 3114 (long count) and
//! 3115 (item) in `classKU`; the wire layouts are the selected rsprot decoders: big-endian
//! `g4`/`g8`/`g2` and a null-terminated cp1252 `gjstr` in a `VAR_BYTE` packet.
use crate::{ProtocolError, Result};

/// One prompt answer produced by a script.
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum ResumeIntent {
    /// `RESUME_P_COUNTDIALOG`: 3104 sends the text as an integer, `0` unless it is one.
    Count(i32),
    /// `RESUME_P_COUNTDIALOG_LONG`: 3114, the same with a 64-bit value.
    CountLong(i64),
    /// `RESUME_P_NAMEDIALOG`: 3105.
    Name(String),
    /// `RESUME_P_STRINGDIALOG`: 3106.
    Text(String),
    /// `RESUME_P_OBJDIALOG`: 3115, an item definition id.
    Item(u16),
}

impl ResumeIntent {
    /// The integer a script prompt reads from entered text: stock parses a decimal integer
    /// (`classMQ.method7683`, radix 10, signed) and sends `0` for anything else, overflow
    /// included.
    #[must_use]
    pub fn count_from_text(text: &str) -> Self {
        Self::Count(text.parse().unwrap_or(0))
    }
    /// The 64-bit counterpart used by 3114.
    #[must_use]
    pub fn long_count_from_text(text: &str) -> Self {
        Self::CountLong(text.parse().unwrap_or(0))
    }
    pub(super) fn encode(self) -> Result<(u8, Vec<u8>)> {
        let string = |opcode, value: &str| {
            let mut body: Vec<u8> = value
                .chars()
                .map(|ch| crate::wire::cp1252(ch).unwrap_or(b'?'))
                .collect();
            body.push(0);
            let length = u8::try_from(body.len())
                .map_err(|_| ProtocolError::new("resume encode", "text exceeds one length byte"))?;
            let mut payload = vec![length];
            payload.extend(body);
            Ok((opcode, payload))
        };
        match self {
            Self::Count(value) => Ok((62, value.to_be_bytes().to_vec())),
            Self::CountLong(value) => Ok((111, value.to_be_bytes().to_vec())),
            Self::Item(item) => Ok((3, item.to_be_bytes().to_vec())),
            Self::Name(name) => string(70, &name),
            Self::Text(text) => string(54, &text),
        }
    }
}

#[cfg(test)]
mod tests {
    use super::ResumeIntent;

    #[test]
    fn counts_parse_like_the_stock_script_op() {
        assert_eq!(
            ResumeIntent::count_from_text("1234"),
            ResumeIntent::Count(1234)
        );
        assert_eq!(ResumeIntent::count_from_text("-5"), ResumeIntent::Count(-5));
        for text in ["", "abc", "12k", "99999999999", " 7"] {
            assert_eq!(ResumeIntent::count_from_text(text), ResumeIntent::Count(0));
        }
        assert_eq!(
            ResumeIntent::long_count_from_text("99999999999"),
            ResumeIntent::CountLong(99_999_999_999)
        );
    }

    #[test]
    fn wire_layouts_follow_the_selected_decoders() {
        assert_eq!(
            ResumeIntent::Count(0x0102_0304).encode().unwrap(),
            (62, vec![1, 2, 3, 4])
        );
        assert_eq!(
            ResumeIntent::CountLong(1).encode().unwrap(),
            (111, vec![0, 0, 0, 0, 0, 0, 0, 1])
        );
        assert_eq!(
            ResumeIntent::Item(0x0102).encode().unwrap(),
            (3, vec![1, 2])
        );
        assert_eq!(
            ResumeIntent::Name("Zezima".into()).encode().unwrap(),
            (70, vec![7, b'Z', b'e', b'z', b'i', b'm', b'a', 0])
        );
        assert_eq!(
            ResumeIntent::Text(String::new()).encode().unwrap(),
            (54, vec![1, 0])
        );
        assert!(ResumeIntent::Text("x".repeat(300)).encode().is_err());
    }
}
