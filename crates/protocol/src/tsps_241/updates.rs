use super::{SessionCodec, reader::Reader};
use crate::{Bootstrap, ProtocolError, Result, ServerUpdate, VariableUpdate};
use rustclient_model::{
    InterfaceId, InterfaceUpdate, InventoryId, InventorySlot, InventorySlotUpdate, InventoryUpdate,
    ItemDefinitionId, ItemStack, MapDestination, MovementSequences, Plane, PlayerIndex,
    PlayerMovement, PlayerStatusUpdate, PlayerTrackingGrid, RemotePlayerUpdate, SequenceId, VarpId,
    WidgetId, WidgetProperty, WorldTile,
};

impl SessionCodec {
    #[expect(
        clippy::too_many_lines,
        reason = "ordered bounded profile dispatch; each arm validates its complete packet before publication"
    )]
    pub(super) fn decode(&mut self, opcode: u8, bytes: &[u8]) -> Result<Vec<ServerUpdate>> {
        let mut r = Reader::new(bytes);
        let mut out = Vec::new();
        match opcode {
            0 => {
                r.i32()?;
                r.i32()?;
            } // Informational welcome timing; never the semantic clock.
            1 => {
                r.i32()?;
                r.i32()?;
                out.push(ServerUpdate::Tick);
            }
            4 => {
                if !r.boolean()? {
                    return Err(ProtocolError::new("TSPS logout", "logout refused"));
                }
                if !r.string()?.is_empty() {
                    return Err(ProtocolError::new(
                        "TSPS logout",
                        "reasoned logout presentation is not implemented",
                    ));
                }
                out.push(ServerUpdate::Control(crate::SessionControl::Logout));
            }
            20 => {
                let base_x = r.u16()?;
                let base_y = r.u16()?;
                if base_x & 7 != 0 || base_y & 7 != 0 {
                    return Err(ProtocolError::new(
                        "TSPS player sync",
                        "unaligned scene base",
                    ));
                }
                let local = r.u16()?;
                r.i32()?;
                if u32::from(local) != self.player.get() {
                    return Err(ProtocolError::new(
                        "TSPS player sync",
                        "local index changed",
                    ));
                }
                let size = usize::from(r.u16()?);
                let payload = r.take(size)?;
                if r.remaining() > 0 && r.u16()? != 0 {
                    return Err(ProtocolError::new(
                        "TSPS player sync",
                        "continuous movement extension is not implemented",
                    ));
                }
                r.finish()?;
                let mut update = self.players.decode_with_fields(
                    payload,
                    self.chat.as_ref(),
                    super::players::fields,
                )?;
                if self.awaiting_rebuild {
                    let Some(PlayerMovement::WrappedTeleport {
                        offset,
                        plane_delta,
                        extent: 16_384,
                    }) = update.local_movement.take()
                    else {
                        return Err(ProtocolError::new(
                            "TSPS bootstrap",
                            "expected initial absolute placement",
                        ));
                    };
                    let plane = Plane::new(plane_delta)
                        .ok_or_else(|| ProtocolError::new("TSPS bootstrap", "invalid plane"))?;
                    let position = WorldTile::new(offset.x, offset.y, plane);
                    let anchor =
                        WorldTile::new(i32::from(base_x) + 48, i32::from(base_y) + 48, plane);
                    if (position.x - anchor.x).abs() > 52 || (position.y - anchor.y).abs() > 52 {
                        return Err(ProtocolError::new(
                            "TSPS bootstrap",
                            "base inconsistent with placement",
                        ));
                    }
                    out.push(ServerUpdate::InitialPlacement(Bootstrap {
                        position,
                        map_anchor: anchor,
                        area: None,
                    }));
                    update.local_appearance =
                        update.local_appearance.or_else(|| self.appearance.take());
                    let grid = PlayerTrackingGrid::new(8192, 256).ok_or_else(|| {
                        ProtocolError::new("TSPS bootstrap", "tracking dimensions")
                    })?;
                    // TSPS initializes every external slot's packed region to zero. Commit that
                    // bootstrap once; only GameState subsequently owns remote positions.
                    let mut tracking: Vec<_> = (1..2048)
                        .filter(|index| *index != self.player.get())
                        .map(|index| RemotePlayerUpdate::Track {
                            index: PlayerIndex::new(index),
                            grid,
                            region: WorldTile::new(0, 0, Plane::MIN),
                        })
                        .collect();
                    tracking.append(&mut update.remote);
                    update.remote = tracking;
                    self.awaiting_rebuild = false;
                } else {
                    out.push(ServerUpdate::SetMapAnchor(rustclient_model::MapAnchor {
                        x: i32::from(base_x) + 48,
                        y: i32::from(base_y) + 48,
                    }));
                }
                out.push(ServerUpdate::PlayerInfo(update));
            }
            22 => {
                let mut ids = [0; 11];
                for id in &mut ids {
                    *id = r.u16()?;
                }
                if ids[6..9].iter().any(|id| *id != ids[5]) || ids[10] != ids[9] {
                    return Err(ProtocolError::new(
                        "TSPS animation defaults",
                        "directional run/turn overrides require semantic support",
                    ));
                }
                let sequence = |id| (id != u16::MAX).then(|| SequenceId::new(u32::from(id)));
                let appearance = self.appearance.as_mut().ok_or_else(|| {
                    ProtocolError::new(
                        "TSPS animation defaults",
                        "post-bootstrap animation replacement is not implemented",
                    )
                })?;
                appearance.movement_sequences = MovementSequences {
                    idle: sequence(ids[0]),
                    walk: sequence(ids[1]),
                    walk_back: sequence(ids[2]),
                    walk_left: sequence(ids[3]),
                    walk_right: sequence(ids[4]),
                    run: sequence(ids[5]),
                    turn: sequence(ids[9]),
                };
            }
            40 | 41 => {
                let id = VarpId::new(u32::from(r.u16()?));
                let value = if opcode == 40 {
                    i32::from(r.u8()?)
                } else {
                    r.i32()?
                };
                out.push(ServerUpdate::Variable(VariableUpdate::Set { id, value }));
            }
            42 => {
                let id = u32::from(r.u16()?);
                let value = r.i32()?;
                out.push(self.varbit(id, value)?);
            }
            50 => {
                let count = r.u16()?;
                let mut slots = vec![None; 28];
                let mut seen = [false; 28];
                for _ in 0..count {
                    let (slot, stack) = inventory_slot(&mut r)?;
                    let index = usize::from(slot);
                    if index >= slots.len() || seen[index] {
                        return Err(ProtocolError::new(
                            "TSPS inventory",
                            "invalid or duplicate inventory slot",
                        ));
                    }
                    slots[index] = stack;
                    seen[index] = true;
                }
                out.push(ServerUpdate::Inventory(InventoryUpdate::Replace {
                    id: InventoryId::new(93),
                    slots,
                }));
            }
            51 => {
                let (slot, stack) = inventory_slot(&mut r)?;
                if slot >= 28 {
                    return Err(ProtocolError::new(
                        "TSPS inventory",
                        "inventory slot out of range",
                    ));
                }
                out.push(ServerUpdate::Inventory(InventoryUpdate::Patch {
                    id: InventoryId::new(93),
                    slots: vec![InventorySlotUpdate {
                        slot: InventorySlot::new(u32::from(slot)),
                        stack,
                    }],
                }));
            }
            56 => out.push(ServerUpdate::Inventory(InventoryUpdate::Remove(
                InventoryId::new(u32::from(r.u16()?)),
            ))),
            87 => {
                let (x, y) = (r.u16()?, r.u16()?);
                out.push(ServerUpdate::MapFlag(if x == u16::MAX && y == u16::MAX {
                    None
                } else {
                    Some(MapDestination {
                        x: i32::from(x),
                        y: i32::from(y),
                    })
                }));
            }
            88 => {
                let slot = r.u8()?;
                let priority = r.boolean()?;
                let text = r.string()?;
                if !(1..=8).contains(&slot) {
                    return Err(ProtocolError::new("TSPS player option", "invalid slot"));
                }
                out.push(ServerUpdate::PlayerStatus(
                    PlayerStatusUpdate::PlayerOption {
                        slot,
                        priority,
                        text: (!text.is_empty()).then_some(text),
                    },
                ));
            }
            102 => {
                let group = r.u16()?;
                out.push(ServerUpdate::Interface(InterfaceUpdate::SetRoot(
                    (group != u16::MAX).then(|| InterfaceId::new(u32::from(group))),
                )));
            }
            104 => out.push(ServerUpdate::Interface(InterfaceUpdate::Close(widget(
                r.u32()?,
            )?))),
            105 | 106 => {
                let widget = widget(r.u32()?)?;
                let property = if opcode == 105 {
                    WidgetProperty::Text(r.string()?)
                } else {
                    WidgetProperty::Hidden(r.boolean()?)
                };
                out.push(ServerUpdate::Interface(InterfaceUpdate::SetProperty {
                    widget,
                    property,
                }));
            }
            103 => out.extend(super::interfaces::open(self, &mut r)?),
            109 | 111 | 112 => out.push(ServerUpdate::Interface(super::interfaces::events(
                opcode, &mut r,
            )?)),
            110 | 170 => out.extend(super::scripts::decode(self, opcode, &mut r)?),
            _ => {
                return Err(ProtocolError::new(
                    "TSPS decode",
                    format!("required packet {opcode} is not implemented"),
                ));
            }
        }
        r.finish()?;
        Ok(out)
    }
}
pub(super) fn widget(value: u32) -> Result<WidgetId> {
    if value >> 16 == 65535 || value & 65535 == 65535 {
        return Err(ProtocolError::new("TSPS widget", "invalid address"));
    }
    Ok(WidgetId::new(InterfaceId::new(value >> 16), value & 65535))
}
pub(super) fn inventory_slot(r: &mut Reader<'_>) -> Result<(u16, Option<ItemStack>)> {
    let slot = r.u16()?;
    let id = r.u16()?;
    let amount = r.u8()?;
    let amount = if amount == 255 {
        r.u32()?
    } else {
        u32::from(amount)
    };
    Ok((
        slot,
        (id != 0).then(|| ItemStack {
            item: ItemDefinitionId::new(u32::from(id - 1)),
            amount,
        }),
    ))
}
