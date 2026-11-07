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
        let mut server_url = None;
        let mut connection_config = None;
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
                "--server-url" => {
                    server_url = Some(args.next().ok_or("--server-url requires a URL")?);
                }
                "--connection-config" => {
                    connection_config = Some(PathBuf::from(
                        args.next().ok_or("--connection-config requires a path")?,
                    ));
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
        let preferences_dir = preferences_dir
            .or_else(|| {
                default_preferences_dir(
                    std::env::consts::OS,
                    |name| std::env::var_os(name),
                    std::path::Path::exists,
                )
            })
            .ok_or("--preferences-dir is required when no home directory can be found")?;
        let saved_path =
            connection_config.unwrap_or_else(|| preferences_dir.join("connection.json"));
        let saved = SavedConnection::read(&saved_path)?;
        let server_profile = server_profile
            .or(saved
                .server_profile
                .as_deref()
                .map(rustclient_protocol::ServerProfile::parse)
                .transpose()
                .map_err(|e| e.to_string())?)
            .unwrap_or(rustclient_protocol::ServerProfile::TSPS_241);
        let profile = profile
            .or(std::env::var("RUSTCLIENT_CACHE_PROFILE")
                .ok()
                .as_deref()
                .map(CacheProfile::parse)
                .transpose()?)
            .or(saved
                .cache_profile
                .as_deref()
                .map(CacheProfile::parse)
                .transpose()?)
            .unwrap_or(
                if server_profile == rustclient_protocol::ServerProfile::TSPS_241 {
                    CacheProfile::Tsps241
                } else {
                    CacheProfile::OpenRune240
                },
            );
        if !offline
            && ((server_profile == rustclient_protocol::ServerProfile::TSPS_241)
                != (profile == CacheProfile::Tsps241))
        {
            return Err("server and cache profiles are incompatible; TSPS requires --cache-profile tsps-241".into());
        }
        let cache_dir = cache_dir
            .or_else(|| std::env::var_os("RUSTCLIENT_CACHE_DIR").map(PathBuf::from))
            .or(saved.cache_dir)
            .unwrap_or_else(|| preferences_dir.join("cache"));
        if !cache_dir.is_dir() {
            return Err(format!(
                "cache directory {} is missing; use --cache-dir PATH or connection.json",
                cache_dir.display()
            ));
        }
        let public_key = public_key.or(saved.rsa_public_key);
        let server_url = server_url
            .or(saved.server_url)
            .unwrap_or_else(|| format!("ws://{host}:{port}/"));
        let plugins_dir = plugins_dir.unwrap_or_else(|| preferences_dir.join("plugins"));
        let connection = if offline {
            None
        } else {
            if server_profile == rustclient_protocol::ServerProfile::OPENRUNE_240_2
                && public_key.is_none()
            {
                return Err("OpenRune login requires --rsa-public-key PATH or connection.json rsa_public_key".into());
            }
            if host.is_empty() || port == 0 {
                return Err("invalid server endpoint".into());
            }
            if endpoint_option && server_profile == rustclient_protocol::ServerProfile::TSPS_241 {
                return Err("TSPS uses --server-url ws://HOST:PORT/; --server-host/--server-port select OpenRune TCP".into());
            }
            Some(crate::network::ConnectionOptions {
                profile: server_profile,
                cache_profile: profile,
                host,
                port,
                public_key,
                server_url,
                cache_dir: cache_dir.clone(),
            })
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
            "RustClient\n\ncargo play --cache-dir PATH\n\nOptions:\n  --cache-dir PATH\n  --cache-profile tsps-241|osrs-240|openrune-240\n  --server-profile tsps-241|openrune-240.2  (default: tsps-241, experimental)\n  --server-url ws://HOST:PORT/             TSPS endpoint (default: ws://127.0.0.1:43594/)\n  --server-host HOST --server-port PORT    OpenRune TCP endpoint\n  --rsa-public-key PATH                    OpenRune public key\n  --connection-config PATH                 saved profile JSON (default: <preferences-dir>/connection.json)\n  --offline                               no live connection\n  --preferences-dir PATH\n  --plugins-dir PATH\n  --capture-dir PATH"
        );
    }
}

/// Persisted endpoint/asset configuration only; credentials never belong in this file.
#[derive(Default, serde::Deserialize)]
#[serde(deny_unknown_fields)]
struct SavedConnection {
    server_profile: Option<String>,
    server_url: Option<String>,
    cache_profile: Option<String>,
    cache_dir: Option<PathBuf>,
    rsa_public_key: Option<PathBuf>,
}
impl SavedConnection {
    fn read(path: &std::path::Path) -> Result<Self, String> {
        use std::io::Read;
        let file = match std::fs::File::open(path) {
            Ok(file) => file,
            Err(e) if e.kind() == std::io::ErrorKind::NotFound => return Ok(Self::default()),
            Err(_) => return Err("could not read connection profile".into()),
        };
        let mut bytes = Vec::new();
        file.take(16_385)
            .read_to_end(&mut bytes)
            .map_err(|_| "could not read connection profile")?;
        if bytes.len() > 16_384 {
            return Err("connection profile exceeds 16 KiB".into());
        }
        serde_json::from_slice(&bytes).map_err(|_| "invalid connection profile JSON".into())
    }
}

/// Where preferences live when `--preferences-dir` is not given: an existing legacy `~/.rustclient`
/// first, so earlier runs keep their data, then the platform's per-user application folder.
fn default_preferences_dir(
    os: &str,
    env: impl Fn(&str) -> Option<std::ffi::OsString>,
    exists: impl Fn(&std::path::Path) -> bool,
) -> Option<PathBuf> {
    let home = env("HOME")
        .or_else(|| env("USERPROFILE"))
        .map(PathBuf::from);
    if let Some(legacy) = home.as_ref().map(|home| home.join(".rustclient"))
        && exists(&legacy)
    {
        return Some(legacy);
    }
    match os {
        "windows" => env("APPDATA").map(|dir| PathBuf::from(dir).join("RustClient")),
        "macos" => home.map(|home| home.join("Library/Application Support/RustClient")),
        _ => env("XDG_CONFIG_HOME")
            .map(|dir| PathBuf::from(dir).join("rustclient"))
            .or_else(|| home.map(|home| home.join(".config/rustclient"))),
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use std::{collections::HashMap, ffi::OsString, path::Path};

    fn env<'a>(pairs: &'a [(&'a str, &'a str)]) -> impl Fn(&str) -> Option<OsString> + 'a {
        let map: HashMap<_, _> = pairs.iter().copied().collect();
        move |name| map.get(name).map(OsString::from)
    }

    #[test]
    fn preferences_follow_the_platform_and_keep_an_existing_legacy_folder() {
        let home = [("HOME", "/home/a")];
        assert_eq!(
            default_preferences_dir("linux", env(&home), |_| false),
            Some(PathBuf::from("/home/a/.config/rustclient"))
        );
        assert_eq!(
            default_preferences_dir(
                "linux",
                env(&[("HOME", "/home/a"), ("XDG_CONFIG_HOME", "/x")]),
                |_| false
            ),
            Some(PathBuf::from("/x/rustclient"))
        );
        assert_eq!(
            default_preferences_dir("macos", env(&home), |_| false),
            Some(PathBuf::from(
                "/home/a/Library/Application Support/RustClient"
            ))
        );
        assert_eq!(
            default_preferences_dir("windows", env(&[("APPDATA", "C:/Users/a/AppData")]), |_| {
                false
            }),
            Some(PathBuf::from("C:/Users/a/AppData/RustClient"))
        );
        // Earlier runs wrote to ~/.rustclient; keep using it.
        assert_eq!(
            default_preferences_dir("linux", env(&home), |path| path
                == Path::new("/home/a/.rustclient")),
            Some(PathBuf::from("/home/a/.rustclient"))
        );
        assert_eq!(default_preferences_dir("linux", env(&[]), |_| false), None);
    }

    #[test]
    fn saved_connections_accept_endpoint_configuration_and_reject_credentials() {
        let saved: SavedConnection = serde_json::from_str(r#"{"server_profile":"tsps-241","server_url":"wss://example.test/game","cache_profile":"tsps-241","cache_dir":"cache"}"#).unwrap();
        assert_eq!(saved.server_profile.as_deref(), Some("tsps-241"));
        assert_eq!(saved.server_url.as_deref(), Some("wss://example.test/game"));
        for json in [
            r#"{"password":"secret"}"#,
            r#"{"username":"player"}"#,
            r#"{"cache_dir":12}"#,
        ] {
            assert!(serde_json::from_str::<SavedConnection>(json).is_err());
        }
    }
}
