use core::fmt;
use rustclient_platform::ByteStream;
/// Stage-specific diagnostic containing no raw payload or credential data.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct ProtocolError {
    pub stage: &'static str,
    pub detail: String,
    /// Stable transport category when an adapter failed.
    pub transport: Option<rustclient_platform::TransportError>,
}
impl ProtocolError {
    pub(crate) fn new(stage: &'static str, detail: impl Into<String>) -> Self {
        Self {
            stage,
            detail: detail.into(),
            transport: None,
        }
    }
    fn transport(stage: &'static str, error: rustclient_platform::TransportError) -> Self {
        Self {
            stage,
            detail: error.to_string(),
            transport: Some(error),
        }
    }
}
impl fmt::Display for ProtocolError {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "{}: {}", self.stage, self.detail)
    }
}
impl std::error::Error for ProtocolError {}
pub type Result<T> = std::result::Result<T, ProtocolError>;
pub(crate) async fn read<const N: usize>(
    stream: &mut impl ByteStream,
    stage: &'static str,
) -> Result<[u8; N]> {
    let mut data = [0; N];
    exact(stream, &mut data, stage).await?;
    Ok(data)
}
pub(crate) async fn exact(
    stream: &mut impl ByteStream,
    bytes: &mut [u8],
    stage: &'static str,
) -> Result<()> {
    let mut offset = 0;
    while offset < bytes.len() {
        let n = stream
            .receive(&mut bytes[offset..])
            .await
            .map_err(|e| ProtocolError::transport(stage, e))?;
        if n == 0 || n > bytes.len() - offset {
            return Err(ProtocolError::new(stage, "truncated response"));
        }
        offset += n;
    }
    Ok(())
}
pub(crate) async fn send(
    stream: &mut impl ByteStream,
    bytes: &[u8],
    stage: &'static str,
) -> Result<()> {
    stream
        .send(bytes)
        .await
        .map_err(|e| ProtocolError::transport(stage, e))
}
pub(crate) struct Reader<'a> {
    pub bytes: &'a [u8],
    pub offset: usize,
    stage: &'static str,
}
impl<'a> Reader<'a> {
    pub fn new(bytes: &'a [u8], stage: &'static str) -> Self {
        Self {
            bytes,
            offset: 0,
            stage,
        }
    }
    pub fn take(&mut self, n: usize) -> Result<&'a [u8]> {
        let end = self
            .offset
            .checked_add(n)
            .filter(|&v| v <= self.bytes.len())
            .ok_or_else(|| ProtocolError::new(self.stage, "truncated payload"))?;
        let s = &self.bytes[self.offset..end];
        self.offset = end;
        Ok(s)
    }
    pub fn u8(&mut self) -> Result<u8> {
        Ok(self.take(1)?[0])
    }
    pub fn u8_add(&mut self) -> Result<u8> {
        Ok(self.u8()?.wrapping_sub(128))
    }
    pub fn u8_neg(&mut self) -> Result<u8> {
        Ok(self.u8()?.wrapping_neg())
    }
    pub fn u8_sub(&mut self) -> Result<u8> {
        Ok(128_u8.wrapping_sub(self.u8()?))
    }
    pub fn i8_add(&mut self) -> Result<i8> {
        Ok(self.u8()?.wrapping_sub(128).cast_signed())
    }
    pub fn i8_neg(&mut self) -> Result<i8> {
        Ok(self.u8()?.wrapping_neg().cast_signed())
    }
    pub fn i8_sub(&mut self) -> Result<i8> {
        Ok(128_u8.wrapping_sub(self.u8()?).cast_signed())
    }
    pub fn u16(&mut self) -> Result<u16> {
        Ok(u16::from_be_bytes(self.take(2)?.try_into().map_err(
            |_| ProtocolError::new(self.stage, "short integer"),
        )?))
    }
    pub fn u16_le(&mut self) -> Result<u16> {
        Ok(u16::from_le_bytes(self.take(2)?.try_into().map_err(
            |_| ProtocolError::new(self.stage, "short integer"),
        )?))
    }
    pub fn u16_add(&mut self) -> Result<u16> {
        let bytes = self.take(2)?;
        Ok((u16::from(bytes[0]) << 8) | u16::from(bytes[1].wrapping_sub(128)))
    }
    pub fn u16_add_le(&mut self) -> Result<u16> {
        let bytes = self.take(2)?;
        Ok(u16::from(bytes[0].wrapping_sub(128)) | (u16::from(bytes[1]) << 8))
    }
    pub fn u32_me(&mut self) -> Result<u32> {
        let bytes = self.take(4)?;
        Ok((u32::from(bytes[1]) << 24)
            | (u32::from(bytes[0]) << 16)
            | (u32::from(bytes[3]) << 8)
            | u32::from(bytes[2]))
    }
    pub fn u_short_smart(&mut self) -> Result<u16> {
        let Some(&first) = self.bytes.get(self.offset) else {
            return Err(ProtocolError::new(self.stage, "truncated smart integer"));
        };
        if first < 128 {
            Ok(u16::from(self.u8()?))
        } else {
            Ok(self.u16()?.wrapping_sub(32768))
        }
    }
}
pub(crate) fn decode_cp1252(byte: u8) -> char {
    if (128..160).contains(&byte) {
        CP1252_SPECIAL
            .chars()
            .nth(usize::from(byte - 128))
            .unwrap_or('\u{fffd}')
    } else {
        char::from(byte)
    }
}
const CP1252_SPECIAL: &str = "€\u{81}‚ƒ„…†‡ˆ‰Š‹Œ\u{8d}Ž\u{8f}\u{90}‘’“”•–—˜™š›œ\u{9d}žŸ";
pub(crate) fn cp1252(ch: char) -> Option<u8> {
    if matches!(ch,'\u{1}'..='\u{7f}'|'\u{a0}'..='\u{ff}') {
        return u8::try_from(u32::from(ch)).ok();
    }
    CP1252_SPECIAL
        .chars()
        .position(|c| c == ch)
        .and_then(|p| u8::try_from(p + 128).ok())
}
pub(crate) fn string(out: &mut Vec<u8>, s: &str) -> Result<()> {
    for ch in s.chars() {
        out.push(
            cp1252(ch)
                .ok_or_else(|| ProtocolError::new("login encode", "unsupported text character"))?,
        );
    }
    out.push(0);
    Ok(())
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn revision_240_byte_transforms_match_buffer_semantics() {
        let mut reader = Reader::new(&[0x85, 0xfb, 0x7b, 0x12, 0xb4, 0xb4, 0x12], "test");
        assert_eq!(reader.u8_add().unwrap(), 5);
        assert_eq!(reader.u8_neg().unwrap(), 5);
        assert_eq!(reader.u8_sub().unwrap(), 5);
        assert_eq!(reader.u16_add().unwrap(), 0x1234);
        assert_eq!(reader.u16_add_le().unwrap(), 0x1234);
    }

    #[test]
    fn revision_240_middle_endian_int_matches_deob() {
        let mut reader = Reader::new(&[0x22, 0x11, 0x44, 0x33], "test");
        assert_eq!(reader.u32_me().unwrap(), 0x1122_3344);
    }

    #[test]
    fn unsigned_short_smart_uses_one_or_two_bytes() {
        let mut reader = Reader::new(&[127, 0x80, 0x80], "test");
        assert_eq!(reader.u_short_smart().unwrap(), 127);
        assert_eq!(reader.u_short_smart().unwrap(), 128);
    }
}
