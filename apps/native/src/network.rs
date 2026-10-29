//! Native composition only: paths, entropy, TCP and worker lifetime.
use crate::LoadEvent;
use rustclient_application::{ConnectionDecoder, SequencePolicyResolver};
use rustclient_cache::{Cache, CacheProfile};
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
    sync::{
        Arc,
        atomic::{AtomicBool, Ordering},
        mpsc::{self, Receiver, SyncSender},
    },
    time::{Duration, Instant},
};
use winit::event_loop::EventLoopProxy;

#[derive(Clone)]
pub(super) struct ConnectionOptions {
    pub profile: ServerProfile,
    pub cache_profile: CacheProfile,
    pub host: String,
    pub port: u16,
    pub public_key: PathBuf,
    pub cache_dir: PathBuf,
}
#[expect(
    clippy::large_enum_variant,
    reason = "inline bounded operations avoid a heap allocation for every server packet"
)]
pub(super) enum NetworkEvent {
    Ready,
    Control(rustclient_protocol::SessionControl),
    Received,
    Written,
    Progress(&'static str),
    Batch(rustclient_application::cycle::InboundBatch<Vec<NetworkEvent>>),
    Operation(u64, u64, SemanticOperation),
    Script(u64, rustclient_model::ScriptRequest),
    Failed(LoginFailure, String),
}
pub(super) struct ConnectionHandle {
    cancel: ConnectionCancellation,
    actions: SyncSender<Vec<Outbound>>,
    pending: rustclient_application::cycle::CycleEgress<Outbound>,
    events: Receiver<NetworkEvent>,
    wake_pending: Arc<AtomicBool>,
}
pub(super) struct Outbound {
    pub world_action: Option<rustclient_model::WorldAction>,
    pub map_epoch: u64,
    pub interface_epoch: Option<u64>,
    pub intent: ClientIntent,
}
impl ConnectionHandle {
    pub fn drain_events(&self) -> Vec<NetworkEvent> {
        self.wake_pending.store(false, Ordering::Release);
        self.events.try_iter().take(64).collect()
    }
    pub fn cancel(self) {
        self.cancel.cancel();
    }
    pub fn send(&self, action: Outbound) -> Result<(), String> {
        self.pending
            .enqueue(action)
            .map_err(|error| error.to_string())
    }
    pub fn flush(&self, mut valid: impl FnMut(&Outbound) -> bool) -> Result<(), String> {
        self.pending.flush(|mut batch| {
            batch.retain(|action| valid(action));
            if batch.is_empty() {
                return Ok(());
            }
            self.actions.try_send(batch).map_err(|error| match error {
                mpsc::TrySendError::Full(batch) => {
                    (batch, "server action queue is full".to_owned())
                }
                mpsc::TrySendError::Disconnected(batch) => {
                    (batch, "server connection is closed".to_owned())
                }
            })
        })
    }
}
/// One worker owns each transport and its cipher state. Stale generations are ignored by the app.
pub(super) fn spawn(
    options: ConnectionOptions,
    credentials: Credentials,
    window: rustclient_model::WindowMetrics,
    generation: u64,
    proxy: EventLoopProxy<LoadEvent>,
) -> Result<ConnectionHandle, String> {
    let cancel = ConnectionCancellation::default();
    let worker_cancel = cancel.clone();
    let (actions, receiver) = mpsc::sync_channel(32);
    let (event_sender, events) = mpsc::sync_channel(64);
    let wake_pending = Arc::new(AtomicBool::new(false));
    let worker_wake = Arc::clone(&wake_pending);
    std::thread::Builder::new()
        .name("server-connection".into())
        .spawn(move || {
            let emit = |event| {
                if event_sender.send(event).is_err() {
                    worker_cancel.cancel();
                    return;
                }
                if !worker_wake.swap(true, Ordering::AcqRel) {
                    let _ = proxy.send_event(LoadEvent::Network(generation, NetworkEvent::Ready));
                }
            };
            if let Err((category, error)) = run(
                &options,
                credentials,
                window,
                &worker_cancel,
                &receiver,
                &emit,
            ) && !worker_cancel.is_cancelled()
            {
                emit(NetworkEvent::Failed(category, error));
            }
        })
        .map_err(|_| "could not start connection worker".to_owned())?;
    Ok(ConnectionHandle {
        cancel,
        actions,
        events,
        wake_pending,
        pending: rustclient_application::cycle::CycleEgress::default(),
    })
}
type Failure = (LoginFailure, String);
fn prepare(
    options: &ConnectionOptions,
    cancel: &ConnectionCancellation,
) -> Result<
    (
        PublicKey,
        rustclient_protocol::LoginChecksums,
        Cache<DirectoryBlobs>,
    ),
    Failure,
> {
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
    Ok((key, crcs, cache))
}
fn open_session(
    options: &ConnectionOptions,
    credentials: Credentials,
    window: rustclient_model::WindowMetrics,
    cancel: &ConnectionCancellation,
    emit: &impl Fn(NetworkEvent),
) -> Result<
    (
        NativeTcp,
        ConnectionDecoder,
        SequencePolicyResolver<DirectoryBlobs>,
    ),
    Failure,
> {
    let failure = |category, error: String| (category, error);
    emit(NetworkEvent::Progress("Checking cache compatibility"));
    let (key, crcs, cache) = prepare(options, cancel)?;
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
            window,
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
    let sizes = cache
        .huffman_sizes()
        .map_err(|e| {
            failure(
                LoginFailure::CacheMismatch,
                format!("chat Huffman table: {e}"),
            )
        })?
        .ok_or_else(|| {
            failure(
                LoginFailure::CacheMismatch,
                "cache has no chat Huffman table".into(),
            )
        })?;
    decoder.set_chat_codec(
        rustclient_protocol::Huffman::from_sizes(&sizes)
            .map_err(|e| failure(LoginFailure::CacheMismatch, e.to_string()))?,
    );
    emit(NetworkEvent::Operation(0, 0, authenticated));
    let sequence_policies = SequencePolicyResolver::new(cache, options.cache_profile);
    Ok((stream, decoder, sequence_policies))
}
#[expect(
    clippy::too_many_lines,
    reason = "one transport owner orders decode, cache policy resolution, epochs and outbound cipher advancement"
)]
fn run(
    options: &ConnectionOptions,
    credentials: Credentials,
    window: rustclient_model::WindowMetrics,
    cancel: &ConnectionCancellation,
    actions: &Receiver<Vec<Outbound>>,
    emit: &impl Fn(NetworkEvent),
) -> Result<(), Failure> {
    let failure = |category, error: String| (category, error);
    let (mut stream, mut decoder, mut sequence_policies) =
        open_session(options, credentials, window, cancel, emit)?;
    let bootstrap_deadline = Instant::now() + Duration::from_secs(15);

    let mut bytes = [0; 16384];
    let mut last_received = Instant::now();
    let mut map_epoch = 0_u64;
    let mut last_map_anchor = None;
    let mut interface_epoch = 0_u64;
    let mut pending_action = None;
    loop {
        if cancel.is_cancelled() {
            return Ok(());
        }
        stream.deadline_after(Duration::from_secs(2));
        let mut received_data = false;
        match stream.receive_available(&mut bytes) {
            Ok(Some(0)) => {
                return Err(failure(
                    LoginFailure::Disconnected,
                    "server closed the connection".into(),
                ));
            }
            Ok(Some(n)) => {
                received_data = true;
                last_received = Instant::now();
                emit(NetworkEvent::Received);
                let mut policy_error = None;
                let connected = decoder
                    .receive_batches(&bytes[..n], |batch| {
                        let mut events = Vec::new();
                        for input in batch.payload {
                            if policy_error.is_some() {
                                return;
                            }
                            let op = match input {
                                rustclient_application::ConnectionInput::Control(control) => {
                                    events.push(NetworkEvent::Control(control));
                                    continue;
                                }
                                rustclient_application::ConnectionInput::Operation(op) => op,
                                rustclient_application::ConnectionInput::Script(request) => {
                                    interface_epoch = interface_epoch.wrapping_add(1);
                                    events.push(NetworkEvent::Script(interface_epoch, request));
                                    continue;
                                }
                            };
                            let op = match sequence_policies.resolve_operation(op) {
                                Ok(op) => op,
                                Err(error) => {
                                    policy_error = Some(error.to_string());
                                    return;
                                }
                            };
                            if matches!(
                                &op,
                                SemanticOperation::Interface(_) | SemanticOperation::Inventory(_)
                            ) {
                                interface_epoch = interface_epoch.wrapping_add(1);
                            }
                            match &op {
                                SemanticOperation::EstablishInitialPlacement(placement) => {
                                    last_map_anchor =
                                        Some((placement.map_anchor.x, placement.map_anchor.y));
                                    map_epoch = map_epoch.wrapping_add(1);
                                }
                                SemanticOperation::RebuildMap(anchor)
                                    if last_map_anchor != Some((anchor.x, anchor.y)) =>
                                {
                                    last_map_anchor = Some((anchor.x, anchor.y));
                                    map_epoch = map_epoch.wrapping_add(1);
                                }
                                _ => {}
                            }
                            events.push(NetworkEvent::Operation(map_epoch, interface_epoch, op));
                        }
                        emit(NetworkEvent::Batch(
                            rustclient_application::cycle::InboundBatch {
                                packets: batch.packets,
                                wire_bytes: batch.wire_bytes,
                                payload: events,
                            },
                        ));
                    })
                    .map_err(|e| failure(LoginFailure::Protocol, e.to_string()))?;
                if let Some(error) = policy_error {
                    return Err(failure(
                        LoginFailure::CacheMismatch,
                        format!(
                            "sequence scheduling policy (cache profile {}): {error}",
                            options.cache_profile.name()
                        ),
                    ));
                }
                if !connected {
                    return Ok(());
                }
            }
            Ok(None) | Err(TransportError::Timeout) => {}
            Err(e) => return Err(failure(LoginFailure::Disconnected, e.to_string())),
        }
        for action in pending_action
            .take()
            .into_iter()
            .chain(actions.try_iter())
            .take(1)
            .flatten()
        {
            let session_wide = matches!(
                &action.intent,
                ClientIntent::WindowStatus { .. }
                    | ClientIntent::KeepAlive
                    | ClientIntent::PingReply { .. }
            );
            if (!session_wide && map_epoch != action.map_epoch)
                || action
                    .interface_epoch
                    .is_some_and(|epoch| epoch != interface_epoch)
            {
                continue;
            }
            let encoded = match decoder.encode(action.intent) {
                Ok(encoded) => encoded,
                // Encoding validates before cipher advancement. A rejected local action
                // must not tear down an otherwise healthy server session.
                Err(error) => {
                    eprintln!("outbound action rejected: {error}");
                    continue;
                }
            };
            stream.deadline_after(Duration::from_secs(2));
            pollster::block_on(stream.send(&encoded))
                .map_err(|e| failure(LoginFailure::Disconnected, e.to_string()))?;
            emit(NetworkEvent::Written);
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
        if !received_data {
            // A click wakes this wait immediately. Idle inbound polling stays at the 50 Hz client
            // cadence instead of blocking outgoing input behind a 200 ms socket deadline.
            pending_action = actions.recv_timeout(Duration::from_millis(20)).ok();
        }
    }
}
