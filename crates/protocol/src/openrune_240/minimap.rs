//! Revision-240 minimap movement telemetry and packed destination flags.
use crate::{ProtocolError, Result};
use rustclient_model::{MapDestination, WorldTile};

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct MinimapClick {
    /// Signed offsets from the integer centre of the unscaled mask canvas.
    pub offset: [i8; 2],
    pub camera_yaw: u16,
    /// Footprint-centred coordinates relative to the build-area origin.
    pub fine_position: [u16; 2],
    pub shift: bool,
}

pub(super) fn movement(
    destination: WorldTile,
    control: bool,
    click: MinimapClick,
) -> Result<(u8, Vec<u8>)> {
    let x = u16::try_from(destination.x)
        .map_err(|_| ProtocolError::new("minimap movement", "x outside profile range"))?
        .to_le_bytes();
    let y = u16::try_from(destination.y)
        .map_err(|_| ProtocolError::new("minimap movement", "y outside profile range"))?
        .to_le_bytes();
    if click.camera_yaw > 2047 {
        return Err(ProtocolError::new(
            "minimap movement",
            "camera yaw outside profile range",
        ));
    }
    let keys: u8 = if control {
        if click.shift { 2 } else { 1 }
    } else {
        0
    };
    let mut payload = vec![
        18,
        y[0].wrapping_add(128),
        y[1],
        keys.wrapping_neg(),
        x[0].wrapping_add(128),
        x[1],
        click.offset[0].cast_unsigned(),
        click.offset[1].cast_unsigned(),
    ];
    payload.extend_from_slice(&click.camera_yaw.to_be_bytes());
    payload.extend_from_slice(&[57, 0, 0, 89]);
    payload.extend_from_slice(&click.fine_position[0].to_be_bytes());
    payload.extend_from_slice(&click.fine_position[1].to_be_bytes());
    payload.push(63);
    Ok((61, payload))
}

pub(super) fn flag(payload: &[u8]) -> Result<Option<MapDestination>> {
    let bytes: [u8; 4] = payload
        .try_into()
        .map_err(|_| ProtocolError::new("map flag", "expected four bytes"))?;
    let packed = u32::from_be_bytes(bytes);
    Ok((packed != u32::MAX).then_some(MapDestination {
        x: ((packed >> 14) & 16383).cast_signed(),
        y: (packed & 16383).cast_signed(),
    }))
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_model::Plane;

    #[test]
    fn minimap_packet_matches_selected_rsprot_240_reader() {
        let (opcode, payload) = movement(
            WorldTile::new(3221, 3218, Plane::MIN),
            true,
            MinimapClick {
                offset: [-20, 12],
                camera_yaw: 512,
                fine_position: [6464, 6592],
                shift: true,
            },
        )
        .unwrap();
        assert_eq!(opcode, 61);
        assert_eq!(
            payload,
            [
                18, 0x12, 0x0c, 0xfe, 0x15, 0x0c, 0xec, 12, 2, 0, 57, 0, 0, 89, 0x19, 0x40, 0x19,
                0xc0, 63
            ]
        );
    }

    #[test]
    fn packed_flag_preserves_world_coordinates_ignores_plane_and_has_explicit_absence() {
        let packed = (3_u32 << 28) | (3221 << 14) | 0x0c92;
        assert_eq!(
            flag(&packed.to_be_bytes()).unwrap(),
            Some(MapDestination { x: 3221, y: 3218 })
        );
        assert_eq!(flag(&[255; 4]).unwrap(), None);
        assert_eq!(flag(&[0; 4]).unwrap(), Some(MapDestination { x: 0, y: 0 }));
        for size in [0, 1, 2, 3, 5] {
            assert!(flag(&vec![0; size]).is_err());
        }
    }
}
