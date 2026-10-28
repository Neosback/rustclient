use rustclient_cache::CacheProfile;
use std::path::PathBuf;

pub(super) struct Options {
    pub cache_dir: PathBuf,
    pub profile: CacheProfile,
    pub preferences_dir: PathBuf,
    pub plugins_dir: PathBuf,
    pub capture_dir: Option<PathBuf>,
    pub connection: Option<crate::network::ConnectionOptions>,
}
impl Options {
    #[allow(clippy::too_many_lines)]
    pub fn parse() -> Result<Option<Self>, String> {
        let mut args = std::env::args().skip(1);
        let mut cache_dir = None;
        let mut profile = None;
        let mut preferences_dir = None;
        let mut plugins_dir = None;
        let mut capture_dir = None;
        let mut server_profile = None;
        let mut public_key = None;
        let mut host = "127.0.0.1".to_owned();
        let mut port = 43594;
        let mut endpoint_option = false;
        let mut offline = false;
        while let Some(arg) = args.next() {
            match arg.as_str() {
                "--help" | "-h" => {
                    Self::usage();
                    return Ok(None);
                }
                "--offline" => {
                    offline = true;
                }
                "--server-profile" => {
                    server_profile = Some(
                        rustclient_protocol::ServerProfile::parse(
                            &args.next().ok_or("--server-profile requires a name")?,
                        )
                        .map_err(|e| e.to_string())?,
                    );
                }
                "--rsa-public-key" => {
                    public_key = Some(PathBuf::from(
                        args.next().ok_or("--rsa-public-key requires a path")?,
                    ));
                }
                "--server-host" => {
                    host = args.next().ok_or("--server-host requires a host")?;
                    endpoint_option = true;
                }
                "--server-port" => {
                    port = args
                        .next()
                        .ok_or("--server-port requires a port")?
                        .parse::<u16>()
                        .map_err(|_| "invalid server port")?;
                    endpoint_option = true;
                }
                "--cache-dir" => {
                    cache_dir = Some(PathBuf::from(
                        args.next().ok_or("--cache-dir requires a path")?,
                    ));
                }
                "--cache-profile" => {
                    profile = Some(CacheProfile::parse(
                        &args.next().ok_or("--cache-profile requires a name")?,
                    )?);
                }
                "--preferences-dir" => {
                    preferences_dir = Some(PathBuf::from(
                        args.next().ok_or("--preferences-dir requires a path")?,
                    ));
                }
                "--plugins-dir" => {
                    plugins_dir = Some(PathBuf::from(
                        args.next().ok_or("--plugins-dir requires a path")?,
                    ));
                }
                "--capture-dir" => {
                    capture_dir = Some(PathBuf::from(
                        args.next().ok_or("--capture-dir requires a path")?,
                    ));
                }
                _ => return Err(format!("unknown option {arg:?}; use --help")),
            }
        }
        let cache_dir = cache_dir
            .or_else(|| std::env::var_os("RUSTCLIENT_CACHE_DIR").map(PathBuf::from))
            .or_else(|| {
                [
                    "/Users/tylercovalt/Desktop/OpenRune Project/OpenRune-Server/.data/cache/LIVE",
                    "/Users/tylercovalt/Desktop/LIVE",
                    "../OpenRune-Server/.data/cache/LIVE",
                ]
                .iter()
                .map(PathBuf::from)
                .find(|p| p.exists())
            })
            .ok_or("--cache-dir is required (or set RUSTCLIENT_CACHE_DIR); use --help")?;

        let profile = profile
            .or_else(|| {
                std::env::var("RUSTCLIENT_CACHE_PROFILE")
                    .ok()
                    .and_then(|name| CacheProfile::parse(&name).ok())
            })
            .unwrap_or_else(|| {
                if cache_dir.to_string_lossy().contains("OpenRune") {
                    CacheProfile::OpenRune240
                } else {
                    CacheProfile::Osrs240
                }
            });

        if !offline && server_profile.is_none() && public_key.is_none() && !endpoint_option {
            let key_candidate = [
                "/Users/tylercovalt/Desktop/OpenRune Project/OpenRune-Server/.data/client.key",
                "../OpenRune-Server/.data/client.key",
            ]
            .iter()
            .map(PathBuf::from)
            .find(|p| p.exists());

            if let Some(key_path) = key_candidate {
                server_profile = Some(rustclient_protocol::ServerProfile::OPENRUNE_240_2);
                public_key = Some(key_path);
            }
        }

        let preferences_dir = preferences_dir
            .or_else(|| {
                std::env::var_os("HOME").map(|home| PathBuf::from(home).join(".rustclient"))
            })
            .ok_or("--preferences-dir is required when HOME is unavailable")?;
        let plugins_dir = plugins_dir.unwrap_or_else(|| preferences_dir.join("plugins"));
        let connection = match (server_profile, public_key) {
            (None, None) if !endpoint_option => None,
            (Some(server_profile), Some(public_key)) if !host.is_empty() && port != 0 => {
                Some(crate::network::ConnectionOptions {
                    profile: server_profile,
                    cache_profile: profile,
                    host,
                    port,
                    public_key,
                    cache_dir: cache_dir.clone(),
                })
            }
            _ => return Err(
                "online login requires --server-profile openrune-240.2 and --rsa-public-key PATH"
                    .into(),
            ),
        };
        Ok(Some(Self {
            cache_dir,
            profile,
            preferences_dir,
            plugins_dir,
            capture_dir,
            connection,
        }))
    }

    fn usage() {
        println!(
            "RustClient desktop title\n\ncargo run -p rustclient-native\n\nOptions:\n  --cache-dir PATH                       cache directory (auto-detected if omitted)\n  --cache-profile osrs-240|openrune-240  use openrune-240 for the server repack (auto-detected)\n  --server-profile openrune-240.2 --rsa-public-key PATH  enable dev server login (auto-detected)\n  --offline                              disable automatic dev server login\n  --server-host IP --server-port PORT    defaults: 127.0.0.1:43594\n  --preferences-dir PATH                 override local preferences directory\n  --plugins-dir PATH                     plugin package directory (default: <preferences-dir>/plugins)\n  --capture-dir PATH                     capture deterministic GPU frames and verify rebuild, then exit"
        );
    }
}
