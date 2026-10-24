//! Shared verified actor-info field encodings.
use crate::{ProtocolError, Result, wire::Reader};
pub(super) fn smart(reader: &mut Reader<'_>) -> Result<u16> {
    reader.u_short_smart()
}
pub(super) fn face(reader: &mut Reader<'_>, flag: u8) -> Result<Option<u16>> {
    match (flag >> 3) & 7 {
        0 => {
            smart(reader)?;
            let width = if reader
                .bytes
                .get(reader.offset)
                .is_some_and(|v| v & 128 != 0)
            {
                4
            } else {
                2
            };
            reader.take(width)?;
            smart(reader)?;
            Ok(None)
        }
        1 => {
            smart(reader)?;
            smart(reader)?;
            smart(reader)?;
            Ok(None)
        }
        2 => Ok(Some(smart(reader)?)),
        3 => Ok(None),
        _ => Err(ProtocolError::new("player facing", "unknown facing kind")),
    }
}
