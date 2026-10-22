use crate::{Source, error::Result};

pub(crate) struct Reader<'a> {
    bytes: &'a [u8],
    pub(crate) position: usize,
    source: Source,
}
impl<'a> Reader<'a> {
    pub(crate) const fn new(bytes: &'a [u8], source: Source) -> Self {
        Self {
            bytes,
            position: 0,
            source,
        }
    }
    pub(crate) fn take(&mut self, count: usize) -> Result<&'a [u8]> {
        let end = self
            .position
            .checked_add(count)
            .ok_or_else(|| self.source.error("binary", "range overflow"))?;
        let value = self.bytes.get(self.position..end).ok_or_else(|| {
            self.source.error(
                "binary",
                format!("truncated at byte {}, need {count}", self.position),
            )
        })?;
        self.position = end;
        Ok(value)
    }
    pub(crate) fn u8(&mut self) -> Result<u8> {
        Ok(self.take(1)?[0])
    }
    pub(crate) fn u16(&mut self) -> Result<u16> {
        let b = self.take(2)?;
        Ok(u16::from_be_bytes([b[0], b[1]]))
    }
    pub(crate) fn u24(&mut self) -> Result<u32> {
        let b = self.take(3)?;
        Ok(u32::from_be_bytes([0, b[0], b[1], b[2]]))
    }
    pub(crate) fn u32(&mut self) -> Result<u32> {
        let b = self.take(4)?;
        Ok(u32::from_be_bytes([b[0], b[1], b[2], b[3]]))
    }
    pub(crate) fn count(&mut self, protocol: u8) -> Result<u32> {
        if protocol >= 7 && self.bytes.get(self.position).is_some_and(|b| b & 128 != 0) {
            Ok(self.u32()? & 0x7fff_ffff)
        } else {
            Ok(u32::from(self.u16()?))
        }
    }
    pub(crate) fn remaining(&self) -> usize {
        self.bytes.len() - self.position
    }
    pub(crate) fn u_smart(&mut self) -> Result<u16> {
        if self.bytes.get(self.position).is_some_and(|b| *b >= 128) {
            Ok(self.u16()? - 32768)
        } else {
            Ok(u16::from(self.u8()?))
        }
    }
    pub(crate) fn s_smart(&mut self) -> Result<i32> {
        if self.bytes.get(self.position).is_some_and(|b| *b >= 128) {
            Ok(i32::from(self.u16()?) - 49152)
        } else {
            Ok(i32::from(self.u8()?) - 64)
        }
    }
    pub(crate) fn i8(&mut self) -> Result<i8> {
        Ok(self.u8()?.cast_signed())
    }
    pub(crate) fn string_value(&mut self) -> Result<String> {
        let mut bytes = Vec::new();
        loop {
            let b = self.u8()?;
            if b == 0 {
                break;
            }
            bytes.push(b);
            if bytes.len() > 4096 {
                return Err(self.source.error("string", "string length limit"));
            }
        }
        Ok(bytes.into_iter().map(char::from).collect())
    }
    pub(crate) fn string(&mut self) -> Result<()> {
        let mut count = 0;
        while self.u8()? != 0 {
            count += 1;
            if count > 4096 {
                return Err(self.source.error("string", "string length limit"));
            }
        }
        Ok(())
    }
    pub(crate) fn finish(&self) -> Result<()> {
        if self.position == self.bytes.len() {
            Ok(())
        } else {
            Err(self.source.error("binary", "unexpected trailing bytes"))
        }
    }
}
