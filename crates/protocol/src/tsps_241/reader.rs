use crate::{ProtocolError, Result};
pub(super) struct Reader<'a> {
    pub inner: crate::wire::Reader<'a>,
}
impl<'a> Reader<'a> {
    pub fn new(bytes: &'a [u8]) -> Self {
        Self {
            inner: crate::wire::Reader::new(bytes, "TSPS payload"),
        }
    }
    pub fn take(&mut self, n: usize) -> Result<&'a [u8]> {
        self.inner.take(n)
    }
    pub fn u8(&mut self) -> Result<u8> {
        self.inner.u8()
    }
    pub fn u16(&mut self) -> Result<u16> {
        self.inner.u16()
    }
    pub fn u32(&mut self) -> Result<u32> {
        let b = self.take(4)?;
        Ok(u32::from_be_bytes([b[0], b[1], b[2], b[3]]))
    }
    pub fn i32(&mut self) -> Result<i32> {
        Ok(self.u32()?.cast_signed())
    }
    pub fn long(&mut self) -> Result<i64> {
        let b = self.take(8)?;
        Ok(i64::from_be_bytes(b.try_into().map_err(|_| {
            ProtocolError::new("TSPS payload", "invalid long")
        })?))
    }
    pub fn boolean(&mut self) -> Result<bool> {
        match self.u8()? {
            0 => Ok(false),
            1 => Ok(true),
            _ => Err(ProtocolError::new("TSPS payload", "invalid boolean")),
        }
    }
    pub fn string(&mut self) -> Result<String> {
        let mut text = String::new();
        loop {
            let byte = self.u8()?;
            if byte == 0 {
                return Ok(text);
            }
            if text.len() >= 16_384 {
                return Err(ProtocolError::new("TSPS payload", "text limit"));
            }
            text.push(char::from(byte));
        }
    }
    pub fn remaining(&self) -> usize {
        self.inner.bytes.len() - self.inner.offset
    }
    pub fn finish(&self) -> Result<()> {
        if self.remaining() == 0 {
            Ok(())
        } else {
            Err(ProtocolError::new("TSPS payload", "trailing bytes"))
        }
    }
}
pub(super) fn text(out: &mut Vec<u8>, text: &str) -> Result<()> {
    // TSPS writes the low byte of JS charCodeAt, not stock cp1252; reject lossy input.
    for ch in text.chars() {
        let byte = u8::try_from(u32::from(ch))
            .map_err(|_| ProtocolError::new("TSPS encode", "text is not representable"))?;
        if byte == 0 {
            return Err(ProtocolError::new(
                "TSPS encode",
                "embedded text terminator",
            ));
        }
        out.push(byte);
    }
    out.push(0);
    Ok(())
}
