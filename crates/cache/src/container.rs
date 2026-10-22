use crate::{Source, error::Result, reader::Reader};
use std::io::Read;

/// Maximum encoded or decoded bytes in one container. Checked before allocation.
pub const MAX_CONTAINER_BYTES: usize = 32 * 1024 * 1024;

pub(crate) fn container_end(bytes: &[u8], source: Source) -> Result<usize> {
    let mut r = Reader::new(bytes, source);
    let compression = r.u8()?;
    if compression > 2 {
        return Err(source.error("container", "unsupported compression"));
    }
    let length =
        usize::try_from(r.u32()?).map_err(|_| source.error("container", "length overflow"))?;
    if length > MAX_CONTAINER_BYTES {
        return Err(source.error("container", "encoded length limit exceeded"));
    }
    let end = length + if compression == 0 { 5 } else { 9 };
    if bytes.len() != end && bytes.len() != end + 2 {
        return Err(source.error("container", "container length/trailer mismatch"));
    }
    Ok(end)
}

/// Decode an unencrypted JS5 container, allowing its optional two-byte version.
///
/// # Errors
/// Rejects unsupported compression, truncation, size violations and corrupt streams.
pub fn decode_container(bytes: &[u8], source: Source) -> Result<Vec<u8>> {
    let end = container_end(bytes, source)?;
    let mut r = Reader::new(&bytes[..end], source);
    let compression = r.u8()?;
    let encoded =
        usize::try_from(r.u32()?).map_err(|_| source.error("container", "length overflow"))?;
    if compression == 0 {
        return Ok(r.take(encoded)?.to_vec());
    }
    let decoded =
        usize::try_from(r.u32()?).map_err(|_| source.error("container", "length overflow"))?;
    if decoded > MAX_CONTAINER_BYTES {
        return Err(source.error("container", "decoded length limit exceeded"));
    }
    let payload = r.take(encoded)?;
    let mut output = Vec::with_capacity(decoded.min(1024 * 1024));
    let limit =
        u64::try_from(decoded).map_err(|_| source.error("container", "length overflow"))? + 1;
    if compression == 1 {
        // JS5 removes the four-byte bzip2 header; block size 1 is part of the format.
        let stream = b"BZh1".as_slice().chain(payload);
        let mut inflater = bzip2::bufread::BzDecoder::new(stream);
        inflater
            .by_ref()
            .take(limit)
            .read_to_end(&mut output)
            .map_err(|error| source.error("decompression", error.to_string()))?;
        let mut remaining = inflater.into_inner();
        if remaining
            .read(&mut [0_u8; 1])
            .map_err(|error| source.error("decompression", error.to_string()))?
            != 0
        {
            return Err(source.error("decompression", "trailing bzip2 stream bytes"));
        }
    } else {
        let mut inflater = flate2::bufread::GzDecoder::new(payload);
        inflater
            .by_ref()
            .take(limit)
            .read_to_end(&mut output)
            .map_err(|error| source.error("decompression", error.to_string()))?;
        if !inflater.get_ref().is_empty() {
            return Err(source.error("decompression", "trailing gzip stream bytes"));
        }
    }
    if output.len() != decoded {
        return Err(source.error("decompression", "decoded length mismatch"));
    }
    Ok(output)
}
