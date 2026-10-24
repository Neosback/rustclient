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
    #[expect(
        clippy::too_many_lines,
        reason = "flat command-line grammar keeps every option and default visible"
    )]
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
        while let Some(arg) = args.next() {
            match arg.as_str() {
                "--help" | "-h" => {
                    Self::usage();
                    return Ok(None);
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
        let cache_dir = cache_dir.ok_or("--cache-dir is required; use --help")?;
        let profile = profile.ok_or("--cache-profile is required; supported: osrs-240")?;
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
            "RustClient desktop title\n\ncargo run -p rustclient-native -- --cache-dir PATH --cache-profile osrs-240\n\nOptions:\n  --server-profile openrune-240.2 --rsa-public-key PATH  enable dev server login\n  --server-host IP --server-port PORT  defaults: 127.0.0.1:43594\n  --preferences-dir PATH  override local preferences directory\n  --plugins-dir PATH      plugin package directory (default: <preferences-dir>/plugins)\n  --capture-dir PATH      capture deterministic GPU frames and verify rebuild, then exit"
        );
    }
}
