//! Mounts every cache interface group and reports what does not load.
//!
//! Each group is opened as the root of a fresh authenticated session, run for a few game cycles,
//! then asked for the sprites, fonts and item icons it draws. The result is a TSV, one row per group,
//! that can be written as a baseline and checked later so a regression names the group it broke.
//! This is decoder and script-host evidence, not a visual parity claim.
use rustclient_application::scripts::{ClientScripts, load_widget_visuals};
use rustclient_cache::osrs240::world_map::MapCoord;
use rustclient_cache::{Cache, CacheProfile, IndexId};
use rustclient_game_state::{AuthenticatedPlayer, GameState, SceneryOperation, SemanticOperation};
use rustclient_model::{
    InterfaceId, InterfaceUpdate, PlayerIndex, Revision, ScriptId, ScriptRequest, ScriptValue,
    ServerProfileId,
};
use rustclient_platform_native::DirectoryBlobs;
use std::{
    collections::{BTreeMap, BTreeSet},
    fmt::Write as _,
    panic::{AssertUnwindSafe, catch_unwind},
};

const CYCLES: u32 = 20;
const WORLD_MAP_GROUP: u32 = 595;
const HEADER: &str = "group\tstatus\twidgets\truntime\tscript_errors\tsprites\tmissing_sprites\tfonts\tmissing_fonts\titems\tfailed_items\tatlas_px\tquads\tdiagnostics";

#[derive(Default)]
struct Row {
    group: u32,
    status: String,
    widgets: usize,
    runtime: usize,
    script_errors: usize,
    sprites: usize,
    missing_sprites: usize,
    fonts: usize,
    missing_fonts: usize,
    items: usize,
    failed_items: usize,
    atlas_px: u64,
    quads: usize,
    diagnostics: usize,
    /// Detail lines for `--verbose`; not part of the baseline.
    notes: Vec<String>,
}

impl Row {
    fn tsv(&self) -> String {
        format!(
            "{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}",
            self.group,
            self.status,
            self.widgets,
            self.runtime,
            self.script_errors,
            self.sprites,
            self.missing_sprites,
            self.fonts,
            self.missing_fonts,
            self.items,
            self.failed_items,
            self.atlas_px,
            self.quads,
            self.diagnostics
        )
    }
}

type Scripts = ClientScripts<DirectoryBlobs>;

fn audit_group(runtime: &mut Scripts, group: u32) -> Result<Row, Box<dyn std::error::Error>> {
    let mut row = Row {
        group,
        status: "ok".into(),
        ..Row::default()
    };
    let definition =
        match CacheProfile::OpenRune240.load_interface(&runtime.cache, InterfaceId::new(group)) {
            Ok(definition) => definition,
            Err(error) => {
                row.status = "load-error".into();
                row.notes.push(error.to_string());
                return Ok(row);
            }
        };
    row.widgets = definition.widgets().len();
    let (state, errors) = mount(runtime, group)?;
    row.runtime = state.interfaces().runtime().len();
    row.script_errors = errors.len();
    row.notes
        .extend(errors.into_iter().map(|e| format!("script: {e}")));
    if row.script_errors > 0 {
        row.status = "script-errors".into();
    }
    measure_assets(runtime, &state, &mut row);
    Ok(row)
}

/// Open `group` as the root of a fresh authenticated session and run it for a few game cycles.
fn mount(
    runtime: &mut Scripts,
    group: u32,
) -> Result<(GameState, Vec<String>), Box<dyn std::error::Error>> {
    runtime.reset();
    let mut state = GameState::new();
    state.apply_operation(SemanticOperation::AuthenticateSession {
        revision: Revision::with_subrevision(240, 2),
        player: AuthenticatedPlayer {
            profile: ServerProfileId::new(1),
            player: PlayerIndex::new(1),
            member: false,
            staff_level: 0,
        },
    })?;
    let mut errors = runtime.initialize_session(&mut state)?;
    state.apply_operation(SemanticOperation::Interface(InterfaceUpdate::SetRoot(
        Some(InterfaceId::new(group)),
    )))?;
    if group == WORLD_MAP_GROUP {
        // The world map builds its buttons from a script the server opens it with.
        runtime.initialize_world_map()?;
        runtime.enqueue(
            ScriptRequest {
                id: ScriptId::new(1749),
                arguments: vec![ScriptValue::Int(MapCoord::new(0, 3221, 3218).packed())],
            },
            None,
        )?;
    }
    runtime.synchronize(&state)?;
    errors.extend(runtime.drain(&mut state));
    for _ in 0..CYCLES {
        errors.extend(runtime.commit(
            &mut state,
            SemanticOperation::Scenery(SceneryOperation::AdvanceCycles(1)),
        )?);
        errors.extend(runtime.resize_widgets(&mut state)?);
    }
    Ok((state, errors))
}

/// Ask for everything the mounted group draws and record what could not be loaded.
fn measure_assets(runtime: &Scripts, state: &GameState, row: &mut Row) {
    let request = match runtime.visual_request(state) {
        Ok(request) => request,
        Err(error) => {
            row.status = "layout-error".into();
            row.notes.push(format!("layout: {error}"));
            return;
        }
    };
    row.sprites = request.sprites.len();
    row.fonts = request.fonts.len();
    row.items = request.items.len();
    let visuals = load_widget_visuals(&runtime.cache, runtime.profile, &request);
    row.missing_sprites = request
        .sprites
        .iter()
        .filter(|id| !visuals.sprite_frames.contains_key(id))
        .count();
    row.missing_fonts = request
        .fonts
        .iter()
        .filter(|id| !visuals.fonts.contains_key(id))
        .count();
    row.failed_items = request
        .items
        .iter()
        .filter(|id| {
            !visuals
                .images
                .contains_key(&rustclient_game_ui::UiImageId::ItemIcon(**id))
        })
        .count();
    row.atlas_px = visuals
        .images
        .values()
        .map(|image| u64::from(image.width()) * u64::from(image.height()))
        .sum();
    row.notes
        .extend(visuals.failures.iter().map(|f| format!("asset: {f}")));
    let mut frame = rustclient_game_ui::UiFrame::default();
    match runtime.project_widgets(state, &visuals, &mut frame, None) {
        Ok(projected) => {
            row.quads = frame.quads.len();
            row.diagnostics = projected.diagnostics.len();
            row.notes.extend(
                projected
                    .diagnostics
                    .iter()
                    .map(|d| format!("project: {d}")),
            );
        }
        Err(error) => {
            row.status = "project-error".into();
            row.notes.push(format!("project: {error}"));
        }
    }
    if row.status == "ok" && (row.missing_sprites + row.missing_fonts + row.failed_items) > 0 {
        row.status = "missing-assets".into();
    }
}

fn parse_baseline(text: &str) -> BTreeMap<u32, String> {
    text.lines()
        .skip(1)
        .filter_map(|line| Some((line.split('\t').next()?.parse().ok()?, line.to_owned())))
        .collect()
}

/// A row regresses when a failure count rises or a clean group stops being clean.
fn regressed(old: &str, new: &str) -> bool {
    let (old, new): (Vec<_>, Vec<_>) = (old.split('\t').collect(), new.split('\t').collect());
    if old.len() != new.len() {
        return true;
    }
    // status, script_errors, missing_sprites, missing_fonts, failed_items, diagnostics
    [1, 4, 6, 8, 10, 13].iter().any(|&column| {
        if column == 1 {
            old[column] == "ok" && new[column] != "ok"
        } else {
            let value = |cells: &[&str]| cells[column].parse::<u64>().unwrap_or(u64::MAX);
            value(&new) > value(&old)
        }
    })
}

#[expect(
    clippy::too_many_lines,
    reason = "one command line workflow: select groups, audit, report, compare"
)]
fn run() -> Result<std::process::ExitCode, Box<dyn std::error::Error>> {
    let mut args = std::env::args().skip(1);
    let path = args.next().ok_or(
        "usage: interface_audit CACHE_DIR [--write FILE | --check FILE] [--verbose] [GROUP ...]",
    )?;
    let mut write = None;
    let mut check = None;
    let mut verbose = false;
    let mut hex = None;
    let mut groups = Vec::new();
    while let Some(arg) = args.next() {
        match arg.as_str() {
            "--write" => write = Some(args.next().ok_or("--write needs a file")?),
            "--check" => check = Some(args.next().ok_or("--check needs a file")?),
            "--verbose" => verbose = true,
            "--hex" => {
                let group: u32 = args.next().ok_or("--hex needs GROUP FILE")?.parse()?;
                let file: u32 = args.next().ok_or("--hex needs GROUP FILE")?.parse()?;
                hex = Some((group, file));
            }
            other => groups.push(other.parse::<u32>()?),
        }
    }
    let cache = Cache::open(DirectoryBlobs::open(path)?)?;
    if let Some((group, file)) = hex {
        for (id, bytes) in cache.group_files(IndexId(3), rustclient_cache::GroupId(group))? {
            if id.0 == file {
                println!(
                    "{group}:{file} {} bytes: {}",
                    bytes.len(),
                    bytes
                        .iter()
                        .map(|b| format!("{b:02x}"))
                        .collect::<Vec<_>>()
                        .join(" ")
                );
            }
        }
        return Ok(std::process::ExitCode::SUCCESS);
    }
    if std::env::var_os("RUSTCLIENT_SCAN_MODELS").is_some() {
        let mut shapes = BTreeMap::<(u8, u8, usize), usize>::new();
        for group in cache
            .tables()
            .get(&IndexId(3))
            .ok_or("no interfaces")?
            .groups
            .keys()
        {
            for (_, bytes) in cache.group_files(IndexId(3), *group).unwrap_or_default() {
                if bytes.len() > 16 && bytes[0] == 255 && bytes[1] == 6 {
                    *shapes
                        .entry((bytes[12], bytes[13], bytes.len()))
                        .or_default() += 1;
                }
            }
        }
        println!("(width mode, height mode, length) -> count: {shapes:?}");
        return Ok(std::process::ExitCode::SUCCESS);
    }
    if groups.is_empty() {
        groups = cache
            .tables()
            .get(&IndexId(3))
            .ok_or("cache has no interface index")?
            .groups
            .keys()
            .map(|group| group.0)
            .collect();
    }
    let mut runtime = ClientScripts::new(cache, CacheProfile::OpenRune240);
    let mut rows = Vec::new();
    for group in groups {
        let result = catch_unwind(AssertUnwindSafe(|| audit_group(&mut runtime, group)));
        let row = match result {
            Ok(Ok(row)) => row,
            Ok(Err(error)) => Row {
                group,
                status: "host-error".into(),
                notes: vec![error.to_string()],
                ..Row::default()
            },
            Err(_) => Row {
                group,
                status: "panic".into(),
                ..Row::default()
            },
        };
        if verbose {
            println!("{}", row.tsv());
            for note in &row.notes {
                println!("    {note}");
            }
        }
        rows.push(row);
    }
    let mut table = String::from(HEADER);
    for row in &rows {
        table.push('\n');
        table.push_str(&row.tsv());
    }
    table.push('\n');

    let mut by_status = BTreeMap::<&str, usize>::new();
    for row in &rows {
        *by_status.entry(row.status.as_str()).or_default() += 1;
    }
    let mut summary = format!("{} groups:", rows.len());
    for (status, count) in &by_status {
        write!(summary, " {status}={count}")?;
    }
    let worst = rows.iter().max_by_key(|row| row.atlas_px);
    if let Some(row) = worst {
        write!(
            summary,
            "; largest sprite area group {} = {} px",
            row.group, row.atlas_px
        )?;
    }
    println!("{summary}");

    if let Some(file) = write {
        std::fs::write(&file, &table)?;
        println!("wrote {file}");
    }
    if let Some(file) = check {
        let baseline = parse_baseline(&std::fs::read_to_string(&file)?);
        let seen: BTreeSet<u32> = rows.iter().map(|row| row.group).collect();
        let mut failed = false;
        for row in &rows {
            let line = row.tsv();
            match baseline.get(&row.group) {
                Some(old) if regressed(old, &line) => {
                    failed = true;
                    println!(
                        "REGRESSION group {}:\n  baseline {old}\n  now      {line}",
                        row.group
                    );
                }
                None => {
                    failed = true;
                    println!("NEW group {} not in baseline: {line}", row.group);
                }
                Some(_) => {}
            }
        }
        for group in baseline.keys().filter(|group| !seen.contains(group)) {
            println!("note: baseline group {group} was not audited this run");
        }
        if failed {
            return Ok(std::process::ExitCode::FAILURE);
        }
        println!("no regressions against {file}");
    }
    Ok(std::process::ExitCode::SUCCESS)
}

fn main() -> std::process::ExitCode {
    match run() {
        Ok(code) => code,
        Err(error) => {
            eprintln!("interface audit: {error}");
            std::process::ExitCode::FAILURE
        }
    }
}
