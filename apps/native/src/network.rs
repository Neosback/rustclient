//! Native composition only: paths, entropy, TCP and worker lifetime.
use crate::LoadEvent;
use rustclient_application::ConnectionDecoder;
use rustclient_cache::Cache;
use rustclient_game_state::{LoginFailure, SemanticOperation};
use rustclient_platform::{ByteStream, TransportError};
use rustclient_platform_native::{ConnectionCancellation, DirectoryBlobs, NativeTcp};
use rustclient_protocol::{
    ClientIntent, Credentials, LoginAttempt, LoginEntropy, PublicKey, ServerProfile, authenticate,
    login_checksums, verify_cache,
};
use std::{
    io::Read,
    path::PathBuf,
    sync::mpsc::{Receiver, SyncSender, TrySendError, sync_channel},
    time::{Duration, Instant},
};
use winit::event_loop::EventLoopProxy;

const OUTBOUND_CAPACITY: usize = 64;

#[derive(Clone)]
pub(super) struct ConnectionOptions {
    pub profile: ServerProfile,
    pub host: String,
    pub port: u16,
    pub public_key: PathBuf,
    pub cache_dir: PathBuf,
}
pub(super) enum NetworkEvent {
    Progress(&'static str),
    Operation(SemanticOperation),
    Failed(LoginFailure, String),
}

/// Native handle for one live connection worker.
///
/// The worker remains the sole owner of the socket and both game ciphers. UI/application code
/// can only enqueue already validated protocol intents through the bounded channel.
pub(super) struct ConnectionHandle {
    cancel: ConnectionCancellation,
    outbound: SyncSender<ClientIntent>,
}
impl ConnectionHandle {
    pub fn cancel(&self) {
        self.cancel.cancel();
    }

    pub fn send(&self, intent: ClientIntent) -> Result<(), String> {
        self.outbound.try_send(intent).map_err(|error| match error {
            TrySendError::Full(_) => "outbound gameplay queue is full".to_owned(),
            TrySendError::Disconnected(_) => "connection worker is not available".to_owned(),
        })
    }
}

/// One worker owns each transport and its cipher state. Stale generations are ignored by the app.
pub(super) fn spawn(
    options: ConnectionOptions,
    credentials: Credentials,
    generation: u64,
    proxy: EventLoopProxy<LoadEvent>,
) -> Result<ConnectionHandle, String> {
    let cancel = ConnectionCancellation::default();
    let worker_cancel = cancel.clone();
    let (outbound, outbound_rx) = sync_channel(OUTBOUND_CAPACITY);
    std::thread::Builder::new()
        .name("server-connection".into())
        .spawn(move || {
            let emit = |event| {
                let _ = proxy.send_event(LoadEvent::Network(generation, event));
            };
            if let Err((category, error)) = run(
                &options,
                credentials,
                &worker_cancel,
                &outbound_rx,
                &emit,
            ) && !worker_cancel.is_cancelled()
            {
                emit(NetworkEvent::Failed(category, error));
            }
        })
        .map_err(|_| "could not start connection worker".to_owned())?;
    Ok(ConnectionHandle { cancel, outbound })
}
type Failure = (LoginFailure, String);
fn prepare(
    options: &ConnectionOptions,
    cancel: &ConnectionCancellation,
) -> Result<(PublicKey, rustclient_protocol::LoginChecksums), Failure> {
    let failure = |category, error: String| (category, error);
    let cache = DirectoryBlobs::open(&options.cache_dir)
        .map_err(|e| failure(LoginFailure::CacheMismatch, e.to_string()))
        .and_then(|source| {
            Cache::open(source).map_err(|e| failure(LoginFailure::CacheMismatch, e.to_string()))
        })?;
    let crcs = login_checksums(
        cache
            .reference_crcs()
            .iter()
            .map(|(index, crc)| (index.0, *crc)),
    )
    .map_err(|e| failure(LoginFailure::CacheMismatch, e.to_string()))?;
    let mut key_text = String::new();
    std::fs::File::open(&options.public_key)
        .and_then(|file| file.take(2049).read_to_string(&mut key_text))
        .map_err(|_| {
            failure(
                LoginFailure::Protocol,
                "could not read public RSA key".into(),
            )
        })?;
    let key =
        PublicKey::parse(&key_text).map_err(|e| failure(LoginFailure::Protocol, e.to_string()))?;
    let connect = || {
        NativeTcp::connect(&options.host, options.port, cancel.clone())
            .map_err(|e| failure(LoginFailure::Connection, e.to_string()))
    };
    let mut js5 = connect()?;
    pollster::block_on(verify_cache(&mut js5, options.profile, &crcs)).map_err(|e| {
        failure(
            if e.stage == "cache compatibility" {
                LoginFailure::CacheMismatch
            } else {
                LoginFailure::Connection
            },
            e.to_string(),
        )
    })?;
    drop(js5);
    Ok((key, crcs))
}
fn run(
    options: &ConnectionOptions,
    credentials: Credentials,
    cancel: &ConnectionCancellation,
    outbound: &Receiver<ClientIntent>,
    emit: &impl Fn(NetworkEvent),
) -> Result<(), Failure> {
    let failure = |category, error: String| (category, error);
    emit(NetworkEvent::Progress("Checking cache compatibility"));
    let (key, crcs) = prepare(options, cancel)?;
    let connect = || {
        NativeTcp::connect(&options.host, options.port, cancel.clone())
            .map_err(|e| failure(LoginFailure::Connection, e.to_string()))
    };
    let mut entropy = zeroize::Zeroizing::new([0; 40]);
    getrandom::fill(&mut *entropy)
        .map_err(|_| failure(LoginFailure::Connection, "OS entropy unavailable".into()))?;
    let mut stream = connect()?;
    let login = pollster::block_on(authenticate(
        &mut stream,
        LoginAttempt {
            profile: options.profile,
            key: &key,
            credentials,
            entropy: LoginEntropy::new(*entropy),
            crcs: &crcs,
        },
        || cancel.is_cancelled(),
        |s| emit(NetworkEvent::Progress(s)),
    ))
    .map_err(|e| {
        failure(
            if e.transport.is_some() {
                LoginFailure::Connection
            } else if e.stage == "authentication" {
                LoginFailure::Authentication
            } else {
                LoginFailure::Protocol
            },
            e.to_string(),
        )
    })?;
    drop(entropy);
    let (mut decoder, authenticated) = ConnectionDecoder::new(options.profile, login);
    emit(NetworkEvent::Operation(authenticated));
    pollster::block_on(stream.send(&decoder.encode(ClientIntent::WindowStatus {
        width: 765,
        height: 503,
    })))
    .map_err(|e| failure(LoginFailure::Disconnected, e.to_string()))?;
    let bootstrap_deadline = Instant::now() + Duration::from_secs(15);
    let mut next_heartbeat = Instant::now() + Duration::from_secs(5);
    let mut bytes = [0; 16384];
    let mut last_received = Instant::now();
    loop {
        if cancel.is_cancelled() {
            return Ok(());
        }

        while let Ok(intent) = outbound.try_recv() {
            stream.deadline_after(Duration::from_secs(2));
            pollster::block_on(stream.send(&decoder.encode(intent)))
                .map_err(|e| failure(LoginFailure::Disconnected, e.to_string()))?;
        }

        stream.deadline_after(Duration::from_millis(50));
        match pollster::block_on(stream.receive(&mut bytes)) {
            Ok(0) => {
                return Err(failure(
                    LoginFailure::Disconnected,
                    "server closed the connection".into(),
                ));
            }
            Ok(n) => {
                last_received = Instant::now();
                let connected = decoder
                    .receive(&bytes[..n], |op| emit(NetworkEvent::Operation(op)))
                    .map_err(|e| failure(LoginFailure::Protocol, e.to_string()))?;
                if !connected {
                    return Err(failure(
                        LoginFailure::Disconnected,
                        "server ended the session".into(),
                    ));
                }
            }
            Err(TransportError::Timeout) => {}
            Err(e) => return Err(failure(LoginFailure::Disconnected, e.to_string())),
        }
        if last_received.elapsed() > Duration::from_secs(15) {
            return Err(failure(
                LoginFailure::Disconnected,
                "server stopped sending data".into(),
            ));
        }
        if decoder.awaiting_initial_rebuild() && Instant::now() > bootstrap_deadline {
            return Err(failure(
                LoginFailure::Protocol,
                "initial placement timed out".into(),
            ));
        }
        if Instant::now() >= next_heartbeat {
            stream.deadline_after(Duration::from_secs(2));
            pollster::block_on(stream.send(&decoder.encode(ClientIntent::KeepAlive)))
                .map_err(|e| failure(LoginFailure::Disconnected, e.to_string()))?;
            next_heartbeat = Instant::now() + Duration::from_secs(5);
        }
    }
}
