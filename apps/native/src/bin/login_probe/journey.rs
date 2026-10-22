//! Opt-in live diagnostic: ordinary travel and bank interactions through application validation.
use rustclient_application::{ConnectionDecoder, LoadedScene, scripts::ClientScripts};
use rustclient_game_state::{GameState, SemanticOperation};
use rustclient_model::{
    InterfaceId, InventoryId, ItemDefinitionId, WorldAction, WorldTarget, WorldTile,
};
use rustclient_platform::ByteStream;
use rustclient_platform_native::{DirectoryBlobs, NativeTcp};
use rustclient_protocol::ClientIntent;
use std::{
    path::PathBuf,
    time::{Duration, Instant},
};
pub struct Journey {
    cache: PathBuf,
    scene: Option<LoadedScene>,
    anchor: Option<WorldTile>,
    waypoint: usize,
    target: Option<WorldTile>,
    issued: bool,
    progress: Instant,
    previous: Option<WorldTile>,
    opened: bool,
    bank_action: Option<WorldAction>,
    phase: usize,
    verify: bool,
    completed: bool,
}
impl Journey {
    pub fn new(cache: PathBuf, verify: bool) -> Self {
        Self {
            cache,
            scene: None,
            anchor: None,
            waypoint: 0,
            target: None,
            issued: false,
            progress: Instant::now(),
            previous: None,
            opened: false,
            bank_action: None,
            phase: 0,
            verify,
            completed: false,
        }
    }
    pub fn completed(&self) -> bool {
        self.completed
    }
    #[expect(
        clippy::too_many_lines,
        reason = "explicit live acceptance journey state machine"
    )]
    pub fn step(
        &mut self,
        state: &mut GameState,
        scripts: &mut ClientScripts<DirectoryBlobs>,
        decoder: &mut ConnectionDecoder,
        stream: &mut NativeTcp,
    ) -> Result<(), Box<dyn std::error::Error>> {
        let Some(placement) = state.initial_placement() else {
            return Ok(());
        };
        let Some(player) = state.entities().local_player() else {
            return Ok(());
        };
        let tile = player.tile;
        if self.previous != Some(tile) {
            println!(
                "confirmed player tile {},{},{}",
                tile.x,
                tile.y,
                tile.plane.get()
            );
            self.previous = Some(tile);
            self.progress = Instant::now();
        }
        if self.anchor != Some(placement.map_anchor) {
            println!(
                "loading diagnostic map {},{}",
                placement.map_anchor.x, placement.map_anchor.y
            );
            let loaded = rustclient_application::load_scene(
                DirectoryBlobs::open(&self.cache)?,
                rustclient_cache::CacheProfile::OpenRune240,
                placement,
                |_| {},
            )?;
            state.apply_operation(SemanticOperation::InstallScene(loaded.asset.descriptor()))?;
            send(decoder, stream, ClientIntent::MapBuildComplete)?;
            self.scene = Some(loaded);
            self.anchor = Some(placement.map_anchor);
        }
        let waypoints = [
            (3190, 3224),
            (3165, 3233),
            (3135, 3240),
            (3110, 3244),
            (3092, 3244),
        ];
        while self.waypoint < waypoints.len() {
            let (x, y) = self.target.map_or(waypoints[self.waypoint], |t| (t.x, t.y));
            if (tile.x - x).abs() <= 1 && (tile.y - y).abs() <= 1 || tile.x < x - 8 {
                self.waypoint += 1;
                self.issued = false;
                self.target = None;
                continue;
            }
            if !self.issued {
                let loaded = self.scene.as_ref().ok_or("missing diagnostic scene")?;
                let collision =
                    rustclient_scene::CollisionMap::build(&loaded.asset, &state.scenery().into())?;
                let mut candidates = Vec::new();
                for dx in -8_i32..=8 {
                    for dy in -8_i32..=8 {
                        candidates.push((
                            dx * dx + dy * dy,
                            WorldTile::new(x + dx, y + dy, tile.plane),
                        ));
                    }
                }
                candidates.sort_by_key(|(distance, _)| *distance);
                let destination = candidates
                    .into_iter()
                    .map(|(_, p)| p)
                    .find(|p| collision.reachable(tile, *p))
                    .ok_or("no reachable waypoint near target")?;
                self.target = Some(destination);
                let intent = rustclient_application::validate_walk(
                    state,
                    &loaded.asset,
                    loaded.asset.descriptor(),
                    destination,
                    true,
                )?;
                send(decoder, stream, intent)?;
                println!("walk request {x},{y}");
                self.issued = true;
                self.progress = Instant::now();
            }
            if self.progress.elapsed() > Duration::from_secs(20) {
                return Err("movement stalled before waypoint".into());
            }
            return Ok(());
        }
        if !self.opened {
            let loaded = self.scene.as_ref().ok_or("missing diagnostic scene")?;
            let mut candidates = Vec::new();
            for square in &loaded.asset.sources().squares {
                for loc in &square.locations {
                    if loc.tile.plane != tile.plane
                        || (loc.tile.x - tile.x).abs() > 12
                        || (loc.tile.y - tile.y).abs() > 12
                    {
                        continue;
                    }
                    let Some(def) = loaded.asset.sources().objects.get(&loc.object) else {
                        continue;
                    };
                    if !def
                        .name
                        .as_deref()
                        .is_some_and(|n| n.to_lowercase().contains("bank"))
                    {
                        continue;
                    }
                    let key = rustclient_scene::location_key(loc);
                    let target = WorldTarget::Location {
                        key,
                        definition: loc.object,
                        resolved: loc.object,
                    };
                    let entries = rustclient_application::world_actions(
                        state,
                        &loaded.asset,
                        &Default::default(),
                        loaded.asset.descriptor(),
                        target,
                    )?;
                    for (label, action) in entries {
                        if label.to_lowercase().contains("bank") {
                            candidates.push((loc.tile, action));
                        }
                    }
                }
            }
            candidates.sort_by_key(|(p, _)| (p.x - tile.x).abs() + (p.y - tile.y).abs());
            let (_, action) = candidates
                .first()
                .ok_or("no cache-authored bank target nearby")?;
            let intent = rustclient_application::validate_world_action(
                state,
                &loaded.asset,
                &Default::default(),
                *action,
                false,
            )?;
            send(decoder, stream, intent)?;
            self.bank_action = Some(*action);
            self.opened = true;
            self.progress = Instant::now();
            println!("bank interaction requested");
            return Ok(());
        }
        if self.phase == 11 {
            let action = self.bank_action.ok_or("missing bank target")?;
            let loaded = self.scene.as_ref().ok_or("missing scene")?;
            send(
                decoder,
                stream,
                rustclient_application::validate_world_action(
                    state,
                    &loaded.asset,
                    &Default::default(),
                    action,
                    false,
                )?,
            )?;
            self.phase = 12;
            self.progress = Instant::now();
            return Ok(());
        }
        if !state.interfaces().groups().contains(&InterfaceId::new(12)) {
            if self.phase == 10 {
                println!("bank close confirmed");
                self.phase = 11;
                return Ok(());
            }
            if self.progress.elapsed() > Duration::from_secs(20) {
                return Err("bank interface did not open".into());
            }
            return Ok(());
        }
        let inventory = state
            .inventories()
            .get(InventoryId::new(93))
            .unwrap_or(&[])
            .iter()
            .flatten()
            .filter(|s| s.item.get() == 995)
            .map(|s| u64::from(s.amount))
            .sum::<u64>();
        let bank = state
            .inventories()
            .get(InventoryId::new(95))
            .unwrap_or(&[])
            .iter()
            .flatten()
            .filter(|s| s.item.get() == 995)
            .map(|s| u64::from(s.amount))
            .sum::<u64>();
        if self.verify {
            if inventory == 0 && bank == 10 {
                println!("PERSISTENCE PASS: fresh login inventory=0 bank=10 coins");
                self.completed = true;
                return Ok(());
            }
            if self.progress.elapsed() > Duration::from_secs(10) {
                return Err(
                    format!("persistence mismatch: inventory={inventory} bank={bank}").into(),
                );
            }
            return Ok(());
        }
        if self.phase == 12 {
            if inventory == 0 && bank == 10 {
                println!(
                    "BANK LOOP PASS: reopened bank inventory=0 bank=10 coins; fresh-login verification remains required"
                );
                self.completed = true;
                return Ok(());
            }
            return Err(
                format!("reopened bank counts differ: inventory={inventory} bank={bank}").into(),
            );
        }
        if self.phase == 0 && inventory + bank == 10 && bank > 0 {
            self.phase = 14;
        }
        let expected = match self.phase {
            15 => (10, 0),
            1 => (9, 1),
            3 => (10, 0),
            5 => (0, 10),
            7 => (10, 0),
            9 => (0, 10),
            _ => (inventory, bank),
        };
        if self.phase % 2 == 1 {
            if (inventory, bank) != expected {
                if self.progress.elapsed() > Duration::from_secs(10) {
                    return Err(format!(
                        "bank phase {} expected {expected:?}, got ({inventory},{bank})",
                        self.phase
                    )
                    .into());
                }
                return Ok(());
            }
            println!("bank counts confirmed: inventory={inventory} bank={bank}");
            self.phase = if self.phase == 15 { 0 } else { self.phase + 1 };
        }
        if self.phase == 10 {
            send(decoder, stream, ClientIntent::CloseModal)?;
            self.progress = Instant::now();
            return Ok(());
        }
        if self.phase == 0 && (inventory, bank) != (10, 0) {
            return Err(format!(
                "fixture requires 10 coins in backpack and none in bank; got ({inventory},{bank})"
            )
            .into());
        }
        let label = match self.phase {
            0 => "deposit1",
            2 => "withdraw1",
            4 | 8 => "depositall",
            6 | 14 => "withdrawall",
            _ => return Ok(()),
        };
        let tree = rustclient_game_ui::widgets::WidgetTree::build(
            state.interfaces(),
            &scripts.interfaces,
            rustclient_game_ui::widgets::WidgetBounds {
                x: 0,
                y: 0,
                width: 765,
                height: 503,
            },
        )?;
        let mut actions = Vec::new();
        for node in &tree.nodes {
            for (label, action) in
                rustclient_application::widget_actions(state, &scripts.interfaces, node.address)?
            {
                actions.push((label, action));
            }
        }
        let normalize = |s: &str| {
            s.chars()
                .filter(|c| c.is_ascii_alphanumeric())
                .flat_map(char::to_lowercase)
                .collect::<String>()
        };
        let selected = actions.iter().find(|(name, action)| {
            action.item == Some(ItemDefinitionId::new(995)) && normalize(name) == label
        });
        let Some((name, action)) = selected else {
            for (name, action) in actions
                .iter()
                .filter(|(_, a)| a.item == Some(ItemDefinitionId::new(995)))
            {
                println!("available coin action {name:?} {action:?}");
            }
            if self.progress.elapsed() < Duration::from_secs(10) {
                return Ok(());
            }
            return Err(format!("missing authored bank action {label}").into());
        };
        let (intents, errors) = scripts.perform_action(state, *action)?;
        if !errors.is_empty() {
            return Err(format!("bank listener errors: {errors:?}").into());
        }
        if intents.is_empty() {
            return Err("bank action produced no network request".into());
        }
        for intent in intents {
            send(decoder, stream, intent)?;
        }
        println!("requested bank action {name}");
        self.phase += 1;
        self.progress = Instant::now();
        Ok(())
    }
}
fn send(
    decoder: &mut ConnectionDecoder,
    stream: &mut NativeTcp,
    intent: ClientIntent,
) -> Result<(), Box<dyn std::error::Error>> {
    let bytes = decoder.encode(intent)?;
    stream.deadline_after(Duration::from_secs(2));
    pollster::block_on(stream.send(&bytes))?;
    Ok(())
}
