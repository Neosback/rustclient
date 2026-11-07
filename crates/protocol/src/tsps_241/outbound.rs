use super::{framing::packet, reader::text};
use crate::{ChatIntent, ClientIntent, Huffman, ProtocolError, Result, ResumeIntent};

fn short(value: u32) -> Result<u16> {
    u16::try_from(value)
        .map_err(|_| ProtocolError::new("TSPS encode", "identifier outside short range"))
}
fn coordinate(value: i32) -> Result<u16> {
    u16::try_from(value)
        .map_err(|_| ProtocolError::new("TSPS encode", "coordinate outside profile range"))
}
fn le_add(value: u16) -> [u8; 2] {
    let [a, b] = value.to_le_bytes();
    [a.wrapping_add(128), b]
}
fn be_add(value: u16) -> [u8; 2] {
    let [a, b] = value.to_be_bytes();
    [a, b.wrapping_add(128)]
}
#[expect(
    clippy::too_many_lines,
    reason = "profile-owned intent dispatch keeps each opcode beside its validated field order"
)]
pub(super) fn encode(intent: ClientIntent, chat: Option<&Huffman>) -> Result<Vec<u8>> {
    let mut body = Vec::new();
    let opcode = match intent {
        // TSPS has no window-status or map-loaded acknowledgement packet.
        ClientIntent::WindowStatus { .. } | ClientIntent::MapBuildComplete => return Ok(Vec::new()),
        ClientIntent::KeepAlive | ClientIntent::PingReply { .. } => {
            body.extend(0_i32.to_be_bytes());
            201
        }
        ClientIntent::Walk {
            destination,
            invert_run,
        }
        | ClientIntent::MinimapWalk {
            destination,
            invert_run,
            ..
        } => {
            body.extend(coordinate(destination.x)?.to_be_bytes());
            body.extend(coordinate(destination.y)?.to_be_bytes());
            // TSPS bit 0 forces running; modifier bit 0 (wire bit 1) inverts the
            // player's existing run setting. Preserve the shared intent's meaning.
            body.push(u8::from(invert_run) << 1);
            210
        }
        ClientIntent::CloseModal => 55,
        ClientIntent::Resume(ResumeIntent::Count(value)) => {
            body.extend(value.to_be_bytes());
            192
        }
        ClientIntent::Resume(ResumeIntent::Name(value)) => {
            text(&mut body, &value)?;
            193
        }
        ClientIntent::Resume(ResumeIntent::Text(value)) => {
            text(&mut body, &value)?;
            194
        }
        ClientIntent::Chat(message) => {
            match message {
                ChatIntent::Command(command) => {
                    body.push(1);
                    text(&mut body, &format!("::{command}"))?;
                }
                ChatIntent::Public { chat_type, message } => {
                    if chat_type != 0 {
                        return Err(ProtocolError::new(
                            "TSPS chat",
                            "unsupported public chat type",
                        ));
                    }
                    let codec =
                        chat.ok_or_else(|| ProtocolError::new("TSPS chat", "missing chat codec"))?;
                    let (message, colour, effect, pattern) = message.plain(codec)?;
                    body.push(0);
                    text(&mut body, &message)?;
                    if colour != 0 || effect != 0 {
                        body.extend([colour, effect]);
                        body.extend(pattern);
                    }
                }
            }
            190
        }
        ClientIntent::NpcAction {
            index,
            slot,
            control,
        } => {
            let id = short(index.get())?;
            let flag = u8::from(control);
            match slot.get() {
                0 => {
                    body.push(flag);
                    body.extend(le_add(id));
                    76
                }
                1 => {
                    body.extend(le_add(id));
                    body.push(flag);
                    12
                }
                2 => {
                    body.extend(be_add(id));
                    body.push(flag.wrapping_neg());
                    34
                }
                3 => {
                    body.push(flag.wrapping_neg());
                    body.extend(id.to_le_bytes());
                    70
                }
                4 => {
                    body.push(flag.wrapping_add(128));
                    body.extend(id.to_le_bytes());
                    57
                }
                _ => return Err(ProtocolError::new("TSPS NPC action", "invalid action slot")),
            }
        }
        ClientIntent::LocationAction {
            tile,
            object,
            slot,
            control,
        } => {
            let (x, y, id, flag) = (
                coordinate(tile.x)?,
                coordinate(tile.y)?,
                short(object.get())?,
                u8::from(control),
            );
            match slot.get() {
                0 => {
                    body.extend(be_add(x));
                    body.extend(y.to_le_bytes());
                    body.push(flag.wrapping_neg());
                    body.extend(le_add(id));
                    96
                }
                1 => {
                    body.extend(le_add(x));
                    body.extend(le_add(y));
                    body.push(128_u8.wrapping_sub(flag));
                    body.extend(id.to_be_bytes());
                    28
                }
                2 => {
                    body.extend(y.to_le_bytes());
                    body.extend(id.to_le_bytes());
                    body.extend(le_add(x));
                    body.push(128_u8.wrapping_sub(flag));
                    42
                }
                3 => {
                    body.extend(be_add(x));
                    body.extend(id.to_le_bytes());
                    body.extend(be_add(y));
                    body.push(flag.wrapping_neg());
                    38
                }
                4 => {
                    body.extend(x.to_be_bytes());
                    body.push(flag.wrapping_add(128));
                    body.extend(be_add(y));
                    body.extend(be_add(id));
                    51
                }
                _ => {
                    return Err(ProtocolError::new(
                        "TSPS location action",
                        "invalid action slot",
                    ));
                }
            }
        }
        ClientIntent::WidgetAction(action) => {
            let packed = (u32::from(short(action.address.widget.interface.get())?) << 16)
                | u32::from(short(action.address.widget.component)?);
            if action.operation.is_button() {
                body.extend(packed.to_be_bytes());
                13
            } else if action.operation.is_continue() {
                body.extend(packed.to_be_bytes());
                body.extend(short(action.address.child.unwrap_or(65535))?.to_be_bytes());
                252
            } else {
                body.extend(packed.to_be_bytes());
                body.extend(short(action.address.widget.interface.get())?.to_be_bytes());
                body.extend(short(action.address.widget.component)?.to_be_bytes());
                text(&mut body, "")?;
                text(&mut body, "")?;
                let op =
                    action.operation.index().ok_or_else(|| {
                        ProtocolError::new("TSPS widget action", "missing operation")
                    })? + 1;
                body.extend([op, op]);
                body.extend([0; 4]);
                body.push(1);
                body.extend(short(action.address.child.unwrap_or(65535))?.to_be_bytes());
                body.extend(
                    short(
                        action
                            .item
                            .map_or(65535, rustclient_model::ItemDefinitionId::get),
                    )?
                    .to_be_bytes(),
                );
                251
            }
        }
        _ => {
            return Err(ProtocolError::new(
                "TSPS encode",
                "intent is not implemented by this profile",
            ));
        }
    };
    packet(opcode, &body)
}
