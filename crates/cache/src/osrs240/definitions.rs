use crate::{
    Source,
    error::Result,
    reader::Reader,
    world::{
        FloorMaterial, ItemDefinition, KitDefinition, ModelReference, NpcDefinition, NpcMorphs,
        ObjectDefinition, ObjectMorphs, WearModels,
    },
};
use rustclient_model::{
    ItemDefinitionId, KitId, ModelId, NpcDefinitionId, ObjectDefinitionId, PathAccelerationPolicy,
    SequenceId, TextureId, VarbitId, VarpId,
};
use std::collections::BTreeMap;
/// Decode a floor material definition.
/// # Errors
/// Returns error on corrupt payload or unsupported opcodes.
pub fn floor(bytes: &[u8], overlay: bool, source: Source) -> Result<FloorMaterial> {
    let mut r = Reader::new(bytes, source);
    let mut out = FloorMaterial {
        rgb: 0,
        texture: None,
        secondary: None,
        hide_underlay: true,
        visible: true,
    };
    loop {
        match r.u8()? {
            0 => break,
            1 => out.rgb = r.u24()?,
            2 if overlay => out.texture = Some(TextureId::new(u32::from(r.u8()?))),
            5 if overlay => out.hide_underlay = false,
            7 if overlay => out.secondary = Some(r.u24()?),
            8 if overlay => {}
            9 if overlay => {
                r.u8()?;
            }
            op => return Err(source.error("floor definition", format!("unsupported opcode {op}"))),
        }
    }
    r.finish()?;
    out.visible = out.rgb != 0x00ff_00ff || out.texture.is_some();
    Ok(out)
}
/// Decode one object definition from cache group 6 data.
/// # Errors
/// Returns error on corrupt payload or unsupported opcodes.
#[expect(
    clippy::too_many_lines,
    reason = "one audited opcode grammar keeps all field-width decisions together"
)]
pub fn object(bytes: &[u8], id: ObjectDefinitionId, source: Source) -> Result<ObjectDefinition> {
    let mut r = Reader::new(bytes, source);
    let mut out = ObjectDefinition {
        id,
        name: None,
        actions: Default::default(),
        collision: crate::world::ObjectCollision::Geometry,
        models: Vec::new(),
        size: [1; 2],
        scale: [128; 3],
        translation: [0; 3],
        mirrored: false,
        contour: None,
        recolors: Vec::new(),
        retextures: Vec::new(),
        animation: None,
        morphs: None,
        non_flat_shading: false,
        clipped: true,
        model_clipped: false,
        random_start: true,
        int1: -1,
        map_scene: None,
        map_icon: None,
        ambient: 0,
        contrast: 0,
        decoration_displacement: 16,
        floor_lift: 0,
    };
    let mut has_actions = false;
    let mut solid = false;
    loop {
        let op = r.u8()?;
        match op {
            0 => break,
            1 | 5 | 6 | 7 => {
                let count = usize::from(r.u8()?);
                out.models.clear();
                for _ in 0..count {
                    let model = if op == 6 || op == 7 {
                        r.u32()?
                    } else {
                        u32::from(r.u16()?)
                    };
                    let shape = if op == 1 || op == 6 {
                        Some(r.u8()?)
                    } else {
                        None
                    };
                    out.models.push(ModelReference {
                        id: ModelId::new(model),
                        shape,
                    });
                }
            }
            2 => out.name = Some(r.string_value()?),
            30..=34 => {
                let action = r.string_value()?;
                if !action.eq_ignore_ascii_case("hidden") {
                    has_actions = true;
                    out.actions[usize::from(op - 30)] = Some(action);
                }
            }
            14 => out.size[0] = r.u8()?,
            15 => out.size[1] = r.u8()?,
            17 => out.collision = crate::world::ObjectCollision::None,
            27 => out.collision = crate::world::ObjectCollision::GeometryAndFloorDecoration,
            74 => solid = true,
            18 | 73 | 90 | 94 => {}
            22 => out.non_flat_shading = true,
            23 => out.model_clipped = true,
            64 => out.clipped = false,
            89 => out.random_start = false,
            19 => out.int1 = i32::from(r.u8()?),
            69 | 75 | 91 | 95 => {
                r.u8()?;
            }
            96 => out.floor_lift = r.u8()?,
            21 => out.contour = Some(0),
            24 => {
                let id = r.u16()?;
                out.animation = (id != 65535).then_some(SequenceId::new(u32::from(id)));
            }
            28 => out.decoration_displacement = r.u8()?,
            29 => out.ambient = i16::from(i8::from_be_bytes([r.u8()?])),
            39 => out.contrast = i16::from(i8::from_be_bytes([r.u8()?])) * 25,
            40 => {
                let n = r.u8()?;
                for _ in 0..n {
                    out.recolors.push((r.u16()?, r.u16()?));
                }
            }
            41 => {
                let n = r.u8()?;
                for _ in 0..n {
                    let mut texture = || -> Result<Option<TextureId>> {
                        let id = r.u16()?;
                        Ok((id != 65535).then_some(TextureId::new(u32::from(id))))
                    };
                    let from = texture()?;
                    let to = texture()?;
                    out.retextures.push((from, to));
                }
            }
            42 | 61 => {
                r.u16()?;
            }
            68 => out.map_scene = Some(u32::from(r.u16()?)),
            82 => out.map_icon = Some(u32::from(r.u16()?)),
            62 => out.mirrored = true,
            65..=67 => out.scale[usize::from(op - 65)] = r.u16()?,
            70..=72 => {
                out.translation[usize::from(op - 70)] =
                    i32::from(i16::from_be_bytes(r.u16()?.to_be_bytes()));
            }
            77 | 92 => {
                let bit = r.u16()?;
                let varp = r.u16()?;
                let fallback = if op == 92 {
                    let id = r.u16()?;
                    (id != 65535).then_some(ObjectDefinitionId::new(u32::from(id)))
                } else {
                    None
                };
                let n = usize::from(r.u8()?) + 1;
                let mut targets = Vec::with_capacity(n);
                for _ in 0..n {
                    let id = r.u16()?;
                    targets.push((id != 65535).then_some(ObjectDefinitionId::new(u32::from(id))));
                }
                out.morphs = Some(ObjectMorphs {
                    varbit: (bit != 65535).then_some(VarbitId::new(u32::from(bit))),
                    varp: (varp != 65535).then_some(VarpId::new(u32::from(varp))),
                    targets,
                    fallback,
                });
            }

            78 => {
                r.take(4)?;
            }
            79 => {
                r.take(6)?;
                let count = usize::from(r.u8()?);
                r.take(count * 2)?;
            }
            81 => out.contour = Some(u32::from(r.u8()?) * 256),
            93 => {
                r.take(6)?;
            }
            100 => {
                r.take(2)?;
                r.string()?;
            }
            101 => {
                r.take(13)?;
                r.string()?;
            }
            102 => {
                r.take(15)?;
                r.string()?;
            }
            249 => {
                let count = r.u8()?;
                for _ in 0..count {
                    let kind = r.u8()?;
                    r.u24()?;
                    match kind {
                        0 => {
                            r.u32()?;
                        }
                        1 => r.string()?,
                        _ => {
                            return Err(source.error("object parameters", "invalid parameter type"));
                        }
                    }
                }
            }
            _ => {
                return Err(source.error(
                    "object definition",
                    format!("object {} unsupported opcode {op}", id.get()),
                ));
            }
        }
    }
    if out.size.contains(&0) {
        return Err(source.error(
            "object definition",
            format!(
                "invalid dimensions: size={:?}, scale={:?}",
                out.size, out.scale
            ),
        ));
    }
    if solid {
        out.collision = crate::world::ObjectCollision::None;
    }
    if out.int1 < 0 {
        out.int1 = i32::from(
            has_actions || !out.models.is_empty() && out.models[0].shape.is_none_or(|s| s == 10),
        );
    }
    r.finish()?;
    Ok(out)
}

/// Decode one NPC definition from cache group 9 data.
/// # Errors
/// Returns error on corrupt buffer or malformed parameter structures.
pub fn npc(bytes: &[u8], id: NpcDefinitionId, source: Source) -> Result<NpcDefinition> {
    npc_with_path_acceleration(bytes, id, source).map(|(definition, _)| definition)
}

/// Decode only the definition-derived ordinary path acceleration policy for one NPC.
/// # Errors
/// Returns the same source-aware decode failures as [`npc`].
pub fn npc_path_acceleration(
    bytes: &[u8],
    id: NpcDefinitionId,
    source: Source,
) -> Result<PathAccelerationPolicy> {
    npc_with_path_acceleration(bytes, id, source).map(|(_, policy)| policy)
}

/// Decode immutable movement scheduling inputs without constructing live actors.
/// # Errors
/// Returns the same source-aware decode failures as [`npc`].
pub fn npc_movement_policy(
    bytes: &[u8],
    id: NpcDefinitionId,
    source: Source,
) -> Result<rustclient_model::NpcMovementPolicy> {
    npc_with_path_acceleration(bytes, id, source).map(|(definition, path_acceleration)| {
        rustclient_model::NpcMovementPolicy {
            path_acceleration,
            turn_rate: definition.turn_rate,
        }
    })
}

#[expect(
    clippy::too_many_lines,
    reason = "exhaustive opcode decoder preserves field semantics"
)]
fn npc_with_path_acceleration(
    bytes: &[u8],
    id: NpcDefinitionId,
    source: Source,
) -> Result<(NpcDefinition, PathAccelerationPolicy)> {
    let mut r = Reader::new(bytes, source);
    let mut path_acceleration = PathAccelerationPolicy::STANDARD;
    let mut out = NpcDefinition {
        depth_policy: rustclient_model::ModelDepthPolicy::DepthWrite,
        turn_rate: rustclient_model::JauTurnRate::new(32),
        id,
        name: None,
        models: Vec::new(),
        head_models: Vec::new(),
        size: 1,
        idle_sequence: None,
        walk_sequence: None,
        turn_left_sequence: None,
        turn_right_sequence: None,
        walk_back_sequence: None,
        walk_left_sequence: None,
        walk_right_sequence: None,
        run_sequences: [None; 4],
        crawl_sequences: [None; 4],
        recolors: Vec::new(),
        retextures: Vec::new(),
        combat_level: None,
        scale_width: 128,
        scale_height: 128,
        ambient: 0,
        contrast: 0,
        actions: [None, None, None, None, None],
        draw_minimap_dot: true,
        interactable: true,
        footprint: None,
        morphs: None,
    };
    loop {
        let op = r.u8()?;
        match op {
            0 => break,
            1 => {
                let count = usize::from(r.u8()?);
                out.models.clear();
                for _ in 0..count {
                    out.models.push(ModelId::new(u32::from(r.u16()?)));
                }
            }
            61 => {
                let count = usize::from(r.u8()?);
                out.models.clear();
                for _ in 0..count {
                    out.models.push(ModelId::new(r.u32()?));
                }
            }
            62 => {
                let count = usize::from(r.u8()?);
                out.head_models.clear();
                for _ in 0..count {
                    out.head_models.push(ModelId::new(r.u32()?));
                }
            }
            2 => out.name = Some(r.string_value()?),
            12 => out.size = r.u8()?,
            13 => {
                let seq = r.u16()?;
                out.idle_sequence = (seq != 65535).then_some(SequenceId::new(u32::from(seq)));
            }
            14 => {
                let seq = r.u16()?;
                out.walk_sequence = (seq != 65535).then_some(SequenceId::new(u32::from(seq)));
            }
            15 => {
                let seq = r.u16()?;
                out.turn_left_sequence = (seq != 65535).then_some(SequenceId::new(u32::from(seq)));
            }
            16 => {
                let seq = r.u16()?;
                out.turn_right_sequence = (seq != 65535).then_some(SequenceId::new(u32::from(seq)));
            }
            17 => {
                let w = r.u16()?;
                let wb = r.u16()?;
                let wl = r.u16()?;
                let wr = r.u16()?;
                out.walk_sequence = (w != 65535).then_some(SequenceId::new(u32::from(w)));
                out.walk_back_sequence = (wb != 65535).then_some(SequenceId::new(u32::from(wb)));
                out.walk_left_sequence = (wl != 65535).then_some(SequenceId::new(u32::from(wl)));
                out.walk_right_sequence = (wr != 65535).then_some(SequenceId::new(u32::from(wr)));
            }
            103 => out.turn_rate = rustclient_model::JauTurnRate::new(r.u16()?),
            18 | 42 | 74..=79 | 124 | 146 => {
                r.u16()?;
            }
            126 => out.footprint = Some(r.u16()?),
            30..=34 => {
                let s = r.string_value()?;
                if !s.eq_ignore_ascii_case("Hidden") {
                    out.actions[usize::from(op - 30)] = Some(s);
                }
            }
            40 => {
                let count = usize::from(r.u8()?);
                for _ in 0..count {
                    let from = r.u16()?;
                    let to = r.u16()?;
                    out.recolors.push((from, to));
                }
            }
            41 => {
                let count = usize::from(r.u8()?);
                for _ in 0..count {
                    let from = r.u16()?;
                    let to = r.u16()?;
                    out.retextures.push((
                        Some(TextureId::new(u32::from(from))),
                        Some(TextureId::new(u32::from(to))),
                    ));
                }
            }
            60 => {
                let count = usize::from(r.u8()?);
                for _ in 0..count {
                    out.head_models.push(ModelId::new(u32::from(r.u16()?)));
                }
            }
            93 => out.draw_minimap_dot = false,
            95 => {
                let level = r.u16()?;
                out.combat_level = (level != 65535).then_some(level);
            }
            97 => out.scale_width = r.u16()?,
            98 => out.scale_height = r.u16()?,
            // Flag opcodes without payload: render priority, follower and z-buffer flags.
            99 | 111 | 122 | 123 | 130 | 145 => {}
            147 => out.depth_policy = rustclient_model::ModelDepthPolicy::SortedDeferredWrite,
            // Exact revision-240 Actor.method3284 branch selector: opcode 109 changes the
            // definition from the normal >2/>3 backlog thresholds to >1/>2.
            109 => path_acceleration = PathAccelerationPolicy::EARLY,
            100 => out.ambient = i16::from(r.i8()?),
            101 => out.contrast = i16::from(r.i8()?),
            102 => {
                let bits = r.u8()?;
                for index in 0..8 {
                    if bits & (1 << index) != 0 {
                        // Sprite group (nullable large smart), then icon index (shifted smart).
                        if r.peek_u8()? & 128 != 0 {
                            r.u32()?;
                        } else {
                            r.u16()?;
                        }
                        if r.peek_u8()? < 128 {
                            r.u8()?;
                        } else {
                            r.u16()?;
                        }
                    }
                }
            }
            106 | 118 => {
                let bit = r.u16()?;
                let varp = r.u16()?;
                let default = if op == 118 { r.u16()? } else { 65535 };
                let count = usize::from(r.u8()?);
                let mut transforms = Vec::with_capacity(count + 2);
                for _ in 0..=count {
                    let id = r.u16()?;
                    transforms.push((id != 65535).then_some(NpcDefinitionId::new(u32::from(id))));
                }
                transforms
                    .push((default != 65535).then_some(NpcDefinitionId::new(u32::from(default))));
                out.morphs = Some(NpcMorphs {
                    varbit: (bit != 65535).then_some(VarbitId::new(u32::from(bit))),
                    varp: (varp != 65535).then_some(VarpId::new(u32::from(varp))),
                    transforms,
                });
            }
            107 => out.interactable = false,
            114..=117 => {
                let sequences = if op <= 115 {
                    &mut out.run_sequences
                } else {
                    &mut out.crawl_sequences
                };
                let count = if op % 2 == 0 { 1 } else { 4 };
                for slot in &mut sequences[..count] {
                    let id = r.u16()?;
                    *slot = (id != 65535).then_some(SequenceId::new(u32::from(id)));
                }
            }
            148 => {
                r.take(4)?;
            }
            149 | 151 => {
                r.u8()?;
            }
            150 => {
                r.take(6)?;
            }
            152 => {
                r.take(6)?;
                let count = usize::from(r.u8()?);
                r.take(count * 2)?;
            }
            251 => {
                r.take(2)?;
                r.string()?;
            }
            252 => {
                r.take(13)?;
                r.string()?;
            }
            253 => {
                r.take(15)?;
                r.string()?;
            }
            249 => {
                let count = r.u8()?;
                for _ in 0..count {
                    let kind = r.u8()?;
                    r.u24()?;
                    match kind {
                        0 => {
                            r.u32()?;
                        }
                        1 => r.string()?,
                        _ => return Err(source.error("npc parameters", "invalid parameter type")),
                    }
                }
            }
            op => return Err(source.error("npc", format!("unsupported opcode {op}"))),
        }
    }
    r.finish()?;
    Ok((out, path_acceleration))
}

/// Decode an identity kit definition from cache group 3.
/// # Errors
/// Returns error on malformed kit payload.
pub fn kit(bytes: &[u8], id: KitId, source: Source) -> Result<KitDefinition> {
    let mut r = Reader::new(bytes, source);
    let mut out = KitDefinition {
        id,
        body_part: None,
        models: Vec::new(),
        recolors: Vec::new(),
        retextures: Vec::new(),
    };
    loop {
        let op = r.u8()?;
        match op {
            0 => break,
            1 => out.body_part = Some(r.u8()?),
            2 | 5 => {
                let count = usize::from(r.u8()?);
                out.models.clear();
                for _ in 0..count {
                    let model_id = if op == 5 {
                        r.u32()?
                    } else {
                        u32::from(r.u16()?)
                    };
                    if model_id != 65535 && model_id != u32::MAX {
                        out.models.push(ModelId::new(model_id));
                    }
                }
            }
            40 => {
                let count = usize::from(r.u8()?);
                for _ in 0..count {
                    let from = r.u16()?;
                    let to = r.u16()?;
                    out.recolors.push((from, to));
                }
            }
            41 => {
                let count = usize::from(r.u8()?);
                for _ in 0..count {
                    let from = r.u16()?;
                    let to = r.u16()?;
                    out.retextures.push((
                        Some(TextureId::new(u32::from(from))),
                        Some(TextureId::new(u32::from(to))),
                    ));
                }
            }
            60..=69 => {
                r.u16()?;
            }
            70..=79 => {
                r.u32()?;
            }
            _ => {}
        }
    }
    r.finish()?;
    Ok(out)
}

/// Decode an item definition from cache group 10 (worn models & appearance).
/// # Errors
/// Returns error on malformed item payload.
#[expect(
    clippy::too_many_lines,
    reason = "exhaustive opcode decoder preserves field semantics"
)]
pub fn item(bytes: &[u8], id: ItemDefinitionId, source: Source) -> Result<ItemDefinition> {
    let mut r = Reader::new(bytes, source);
    let mut out = ItemDefinition {
        id,
        icon_model: None,
        icon_zoom: 2000,
        icon_angles: [0; 3],
        icon_offset: [0; 2],
        stackable: false,
        members: false,
        tradeable: false,
        note: None,
        note_template: None,
        bought: None,
        bought_template: None,
        inventory_actions: [None, None, None, None, Some("Drop".into())],
        parameters: BTreeMap::new(),
        placeholder: None,
        placeholder_template: None,
        name: None,
        equip_slot: None,
        hides: [None; 2],
        male: WearModels::default(),
        female: WearModels::default(),
        recolors: Vec::new(),
        retextures: Vec::new(),
    };
    loop {
        let op = r.u8()?;
        match op {
            0 => break,
            2 => out.name = Some(r.string_value()?),
            1 => out.icon_model = Some(ModelId::new(u32::from(r.u16()?))),
            4 => out.icon_zoom = r.u16()?,
            5 => out.icon_angles[0] = r.u16()?,
            6 => out.icon_angles[1] = r.u16()?,
            7 => out.icon_offset[0] = i32::from(r.i16()?),
            8 => out.icon_offset[1] = i32::from(r.i16()?),
            95 => out.icon_angles[2] = r.u16()?,
            11 => out.stackable = true,
            16 => out.members = true,
            65 => out.tradeable = true,
            97 => out.note = Some(ItemDefinitionId::new(u32::from(r.u16()?))),
            98 => out.note_template = Some(ItemDefinitionId::new(u32::from(r.u16()?))),
            139 => out.bought = Some(ItemDefinitionId::new(u32::from(r.u16()?))),
            140 => out.bought_template = Some(ItemDefinitionId::new(u32::from(r.u16()?))),
            149 => out.placeholder_template = Some(ItemDefinitionId::new(u32::from(r.u16()?))),
            148 => out.placeholder = Some(ItemDefinitionId::new(u32::from(r.u16()?))),
            35..=39 => out.inventory_actions[usize::from(op - 35)] = Some(r.string_value()?),
            3 | 30..=34 => r.string()?,
            75 | 90..=94 | 99 | 110..=112 => {
                r.u16()?;
            }
            // Flag opcodes without payload: stackability, tradeability, membership, market, unlock.
            15 | 160 | 251 => {}
            42 | 113..=115 => {
                r.u8()?;
            }
            100..=109 => {
                r.take(4)?;
            }
            43 => {
                r.u8()?;
                while r.u8()? != 0 {
                    r.string()?;
                }
            }
            200 => {
                r.take(2)?;
                r.string()?;
            }
            201 => {
                r.take(13)?;
                r.string()?;
            }
            202 => {
                r.take(15)?;
                r.string()?;
            }
            // Revision 240 stores the inventory model as a 32-bit id; opcode 1 is the legacy form.
            44 => {
                let model = r.u32()?;
                out.icon_model =
                    (model != u32::MAX && model != 65535).then_some(ModelId::new(model));
            }
            12 | 51..=54 => {
                r.u32()?;
            }
            13 => out.equip_slot = Some(r.u8()?),
            14 => out.hides[0] = Some(r.u8()?),
            27 => out.hides[1] = u8::try_from(r.i8()?).ok(),
            23 => {
                let m = r.u16()?;
                out.male.models[0] = (m != 65535).then_some(ModelId::new(u32::from(m)));
                out.male.offset = r.u8()?;
            }
            24 => {
                let m = r.u16()?;
                out.male.models[1] = (m != 65535).then_some(ModelId::new(u32::from(m)));
            }
            78 => {
                let m = r.u16()?;
                out.male.models[2] = (m != 65535).then_some(ModelId::new(u32::from(m)));
            }
            25 => {
                let m = r.u16()?;
                out.female.models[0] = (m != 65535).then_some(ModelId::new(u32::from(m)));
                out.female.offset = r.u8()?;
            }
            26 => {
                let m = r.u16()?;
                out.female.models[1] = (m != 65535).then_some(ModelId::new(u32::from(m)));
            }
            79 => {
                let m = r.u16()?;
                out.female.models[2] = (m != 65535).then_some(ModelId::new(u32::from(m)));
            }
            40 => {
                let count = usize::from(r.u8()?);
                for _ in 0..count {
                    let from = r.u16()?;
                    let to = r.u16()?;
                    out.recolors.push((from, to));
                }
            }
            41 => {
                let count = usize::from(r.u8()?);
                for _ in 0..count {
                    let from = r.u16()?;
                    let to = r.u16()?;
                    out.retextures.push((
                        Some(TextureId::new(u32::from(from))),
                        Some(TextureId::new(u32::from(to))),
                    ));
                }
            }
            45 => {
                let m = r.u32()?;
                out.male.models[0] = (m != u32::MAX && m != 65535).then_some(ModelId::new(m));
                out.male.offset = r.u8()?;
            }
            46 => {
                let m = r.u32()?;
                out.male.models[1] = (m != u32::MAX && m != 65535).then_some(ModelId::new(m));
            }
            47 => {
                let m = r.u32()?;
                out.male.models[2] = (m != u32::MAX && m != 65535).then_some(ModelId::new(m));
            }
            48 => {
                let m = r.u32()?;
                out.female.models[0] = (m != u32::MAX && m != 65535).then_some(ModelId::new(m));
                out.female.offset = r.u8()?;
            }
            49 => {
                let m = r.u32()?;
                out.female.models[1] = (m != u32::MAX && m != 65535).then_some(ModelId::new(m));
            }
            50 => {
                let m = r.u32()?;
                out.female.models[2] = (m != u32::MAX && m != 65535).then_some(ModelId::new(m));
            }
            161 => {
                let count = usize::from(r.u16()?);
                for _ in 0..count {
                    r.u16()?;
                }
            }
            249 => {
                for _ in 0..r.u8()? {
                    let kind = r.u8()?;
                    let key = r.u24()?;
                    let value = match kind {
                        0 => rustclient_model::ScriptValue::Int(r.u32()?.cast_signed()),
                        1 => rustclient_model::ScriptValue::Text(r.string_value()?),
                        _ => return Err(source.error("item parameters", "invalid type")),
                    };
                    out.parameters.insert(key, value);
                }
            }
            op => return Err(source.error("item", format!("unsupported opcode {op}"))),
        }
    }
    r.finish()?;
    Ok(out)
}
