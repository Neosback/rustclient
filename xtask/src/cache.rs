use rustclient_cache::{Cache, CacheProfile};
use rustclient_platform_native::DirectoryBlobs;

pub(super) fn run(args: Vec<String>) -> Result<(), String> {
    let audit = args.first().map(String::as_str) == Some("audit");
    let world = args.first().map(String::as_str) == Some("inspect-world");
    if !audit && !world && args.first().map(String::as_str) != Some("inspect") {
        return Err(
            "use cache inspect|inspect-world|audit --cache-dir PATH --cache-profile osrs-240"
                .into(),
        );
    }
    let mut args = args.into_iter().skip(1);
    let mut path = None;
    let mut profile = None;
    while let Some(arg) = args.next() {
        match arg.as_str() {
            "--cache-dir" => path = Some(args.next().ok_or("--cache-dir requires a path")?),
            "--cache-profile" => {
                profile = Some(CacheProfile::parse(
                    &args.next().ok_or("--cache-profile requires a name")?,
                )?);
            }
            _ => return Err(format!("unknown cache option {arg}")),
        }
    }
    let path = path.ok_or("--cache-dir is required")?;
    let profile = profile.ok_or("--cache-profile is required")?;
    let cache = Cache::open(DirectoryBlobs::open(path).map_err(|e| e.to_string())?)
        .map_err(|e| e.to_string())?;
    println!(
        "profile: {} (selected, not inferred from cache table revisions)",
        profile.name()
    );
    println!(
        "reference-metadata-sha256: {}",
        cache.metadata_fingerprint()
    );
    if audit {
        print_audit(profile, &cache);
        return Ok(());
    }
    for (index, table) in cache.tables() {
        println!(
            "index {}: protocol {}, table revision {:?}, flags {}, {} groups",
            index.0,
            table.protocol,
            table.revision,
            table.flags,
            table.groups.len()
        );
    }
    if world {
        let origin = rustclient_model::WorldTile::new(
            3168,
            3168,
            rustclient_model::Plane::new(0).ok_or("invalid plane")?,
        );
        let a = profile
            .load_world(&cache, origin, 104, 104, |s| eprintln!("{s}"))
            .map_err(|e| e.to_string())?;
        println!("world content sha256: {:02x?}", a.content_hash);
        println!(
            "objects={} morphs={} models={} textures={} sequences={} legacy_frames={} skeletons={} varbits={}",
            a.objects.len(),
            a.objects.values().filter(|d| d.morphs.is_some()).count(),
            a.models.len(),
            a.textures.len(),
            a.sequences.len(),
            a.frames.len(),
            a.skeletons.len(),
            a.varbits.len()
        );
        for seq in a.sequences.values() {
            println!(
                "sequence {}: frames={} skeletal={:?} range={:?}",
                seq.id.get(),
                seq.frames.len(),
                seq.skeletal,
                seq.range
            );
        }
        return Ok(());
    }
    let assets = profile
        .load_title(&cache, |name| eprintln!("checking {name}"))
        .map_err(|e| e.to_string())?;
    for report in assets.reports {
        println!(
            "OK {:?}: index {} group {:?} file {:?}, {} bytes, sha256 {} (CRC + version validated)",
            report.name,
            report.source.index.0,
            report.source.group.map(|g| g.0),
            report.source.file.map(|f| f.0),
            report.bytes,
            report.sha256
        );
    }
    println!(
        "Publisher encoded-size hints are advisory; stored lengths, CRCs, versions and decoded sizes are validated."
    );
    println!("Title assets validated; other definitions and payloads have not been validated.");
    Ok(())
}

fn print_audit(profile: CacheProfile, cache: &Cache<DirectoryBlobs>) {
    let report = profile.audit_cache(cache);
    let mut counts = [0_usize; 4];
    println!("index\tgroup\tfile\tstatus\tdetail");
    for entry in &report.entries {
        use rustclient_cache::osrs240::audit::AuditStatus;
        counts[match entry.status {
            AuditStatus::Decoded => 0,
            AuditStatus::Unsupported => 1,
            AuditStatus::Failed => 2,
            AuditStatus::Unchecked => 3,
        }] += 1;
        println!(
            "{}\t{}\t{}\t{:?}\t{}",
            entry.source.index.0,
            entry.source.group.map_or(0, |v| v.0),
            entry.source.file.map_or(0, |v| v.0),
            entry.status,
            entry.detail.replace(['\n', '\r', '\t'], " ")
        );
    }
    for warning in &report.warnings {
        eprintln!("audit warning: {warning}");
    }
    eprintln!(
        "audit: declared={} decoded={} unsupported={} failed={} unchecked={}; decode-only, stack boundaries are explicit; no execution/visual parity claim",
        report.entries.len(),
        counts[0],
        counts[1],
        counts[2],
        counts[3]
    );
}
