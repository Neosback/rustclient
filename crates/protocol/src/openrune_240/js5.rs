use crate::{
    ProtocolError, Result, ServerProfile,
    wire::{exact, read, send},
};
use rustclient_platform::ByteStream;
/// Compare the selected cache with the server's actual JS5 master index.
/// # Errors
/// Rejects unavailable JS5, malformed block framing or any login archive mismatch.
pub async fn verify_cache(
    stream: &mut impl ByteStream,
    profile: ServerProfile,
    crcs: &[u32; 23],
) -> Result<()> {
    let mut hello = vec![15];
    hello.extend_from_slice(&u32::from(profile.revision().major()).to_be_bytes());
    hello.extend_from_slice(&[0; 16]);
    send(stream, &hello, "JS5 handshake").await?;
    if read::<1>(stream, "JS5 handshake").await?[0] != 0 {
        return Err(ProtocolError::new("JS5", "revision rejected"));
    }
    send(stream, &[3, 0, 0, 0, 1, 255, 0, 255], "JS5 master request").await?;
    let header = read::<8>(stream, "JS5 master header").await?;
    if header[..4] != [255, 0, 255, 0] {
        return Err(ProtocolError::new("JS5", "unexpected master container"));
    }
    let len = u32::from_be_bytes([header[4], header[5], header[6], header[7]]) as usize;
    if !(23 * 8..=255 * 8).contains(&len) || !len.is_multiple_of(8) {
        return Err(ProtocolError::new("JS5", "invalid master size"));
    }
    let mut payload = vec![0; len];
    let mut offset = 0;
    let mut capacity = 504;
    while offset < len {
        let n = (len - offset).min(capacity);
        exact(stream, &mut payload[offset..offset + n], "JS5 master body").await?;
        offset += n;
        if offset < len {
            if read::<1>(stream, "JS5 continuation").await?[0] != 255 {
                return Err(ProtocolError::new("JS5", "invalid block delimiter"));
            }
            capacity = 511;
        }
    }
    for (index, &local) in crcs.iter().enumerate() {
        let server = u32::from_be_bytes([
            payload[index * 8],
            payload[index * 8 + 1],
            payload[index * 8 + 2],
            payload[index * 8 + 3],
        ]);
        if server != local {
            return Err(ProtocolError::new(
                "cache compatibility",
                format!("archive {index} CRC differs; use the server LIVE cache"),
            ));
        }
    }
    Ok(())
}
