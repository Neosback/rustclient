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
    pub public_key: Option<PathBuf>,
    pub server_url: String,
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
    std::fs::File::open(
        options
            .public_key
            .as_ref()
            .ok_or_else(|| failure(LoginFailure::Protocol, "missing OpenRune public key".into()))?,
    )
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
        NativeSessionStream,
        ConnectionDecoder,
        SequencePolicyResolver<DirectoryBlobs>,
    ),
    Failure,
> {
    if options.profile == ServerProfile::TSPS_241 {
        return open_tsps(options, credentials, cancel, emit);
    }
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
            } else if let Some(rejection) = e.rejection {
                LoginFailure::Rejected(rejection)
            } else if e.stage == "authentication" {
                LoginFailure::Authentication
            } else {
                LoginFailure::Protocol
            },
            e.to_string(),
        )
    })?;
    drop(entropy);
    let (mut decoder, authenticated) = ConnectionDecoder::openrune(login);
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
    let cache = std::sync::Arc::new(cache);
    let shape_cache = std::sync::Arc::clone(&cache);
    let profile = options.cache_profile;
    decoder.set_npc_shapes(Box::new(move |id| {
        rustclient_cache::osrs240::npc_path_policy::customisation_shape(&shape_cache, profile, id)
            .ok()
    }));
    let sequence_policies = SequencePolicyResolver::shared(cache, profile);
    Ok((NativeSessionStream::Tcp(stream), decoder, sequence_policies))
}
fn run(
    options: &ConnectionOptions,
    credentials: Credentials,
    window: rustclient_model::WindowMetrics,
    cancel: &ConnectionCancellation,
    actions: &Receiver<Vec<Outbound>>,
    emit: &impl Fn(NetworkEvent),
) -> Result<(), Failure> {
    let (mut stream, mut decoder, mut sequence_policies) =
        open_session(options, credentials, window, cancel, emit)?;
    let profile = options.cache_profile.name();
    session_loop(
        &mut stream,
        &mut decoder,
        &mut |operation| {
            sequence_policies
                .resolve_operation(operation)
                .map_err(|error| {
                    format!("sequence scheduling policy (cache profile {profile}): {error}")
                })
        },
        cancel,
        actions,
        emit,
        &LoopTiming::standard(),
    )
}
mod tsps;
use tsps::{NativeSessionStream, open_tsps};

/// The socket operations the post-login loop needs, so tests can drive it from memory.
pub(super) trait SessionStream: ByteStream {
    /// Bound the next read or write.
    fn deadline_after(&mut self, duration: Duration);
    /// Read what is available now: `Ok(None)` when nothing is, `Ok(Some(0))` at end of stream.
    fn receive_available(&mut self, bytes: &mut [u8]) -> Result<Option<usize>, TransportError>;
}
impl SessionStream for NativeTcp {
    fn deadline_after(&mut self, duration: Duration) {
        NativeTcp::deadline_after(self, duration);
    }
    fn receive_available(&mut self, bytes: &mut [u8]) -> Result<Option<usize>, TransportError> {
        NativeTcp::receive_available(self, bytes)
    }
}
/// Time limits of the post-login loop.
pub(super) struct LoopTiming {
    /// The server must send something at least this often.
    pub silence_limit: Duration,
    /// The initial placement must arrive within this long of the loop starting.
    pub bootstrap_limit: Duration,
    /// How long an idle loop waits for an outgoing action before polling inbound data again.
    pub idle_wait: Duration,
}
impl LoopTiming {
    pub const fn standard() -> Self {
        Self {
            silence_limit: Duration::from_secs(15),
            bootstrap_limit: Duration::from_secs(15),
            idle_wait: Duration::from_millis(20),
        }
    }
}
/// Receive, decode and forward server packets and write queued client actions until the session
/// ends, is cancelled or fails. `resolve` enriches each decoded operation with cache-derived
/// inputs and reports its failure as text.
#[expect(
    clippy::too_many_lines,
    reason = "one transport owner orders decode, cache policy resolution, epochs and outbound cipher advancement"
)]
pub(super) fn session_loop<S: SessionStream>(
    stream: &mut S,
    decoder: &mut ConnectionDecoder,
    resolve: &mut dyn FnMut(SemanticOperation) -> Result<SemanticOperation, String>,
    cancel: &ConnectionCancellation,
    actions: &Receiver<Vec<Outbound>>,
    emit: &impl Fn(NetworkEvent),
    timing: &LoopTiming,
) -> Result<(), Failure> {
    let failure = |category, error: String| (category, error);
    let bootstrap_deadline = Instant::now() + timing.bootstrap_limit;

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
                            let op = match resolve(op) {
                                Ok(op) => op,
                                Err(error) => {
                                    policy_error = Some(error);
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
                for opcode in decoder.take_new_unhandled() {
                    eprintln!(
                        "server opcode {opcode} is not decoded by this client yet (first seen; {} distinct so far)",
                        decoder.unhandled_opcodes().len()
                    );
                }
                if let Some(error) = policy_error {
                    return Err(failure(LoginFailure::CacheMismatch, error));
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
            // A profile may have no wire equivalent for a local notification. Do not
            // count that as a network write or reset the successful-write watchdog.
            if encoded.is_empty() {
                continue;
            }
            stream.deadline_after(Duration::from_secs(2));
            pollster::block_on(stream.send(&encoded))
                .map_err(|e| failure(LoginFailure::Disconnected, e.to_string()))?;
            emit(NetworkEvent::Written);
        }
        if last_received.elapsed() > timing.silence_limit {
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
            pending_action = actions.recv_timeout(timing.idle_wait).ok();
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_model::PlayerIndex;
    use rustclient_protocol::{Isaac, LoginSession};
    use std::{cell::RefCell, collections::VecDeque, future::Future};

    /// One step of what the "server" does when the loop reads.
    enum Step {
        Data(Vec<u8>),
        Idle,
        Close,
    }
    /// An in-memory socket: a script of reads, then cancellation, and a record of writes.
    struct Scripted {
        steps: VecDeque<Step>,
        written: Vec<u8>,
        cancel: ConnectionCancellation,
        cancel_when_done: bool,
    }
    impl ByteStream for Scripted {
        fn receive(&mut self, _: &mut [u8]) -> impl Future<Output = Result<usize, TransportError>> {
            std::future::ready(Err(TransportError::Unavailable))
        }
        fn send(&mut self, bytes: &[u8]) -> impl Future<Output = Result<(), TransportError>> {
            self.written.extend_from_slice(bytes);
            std::future::ready(Ok(()))
        }
    }
    impl SessionStream for Scripted {
        fn deadline_after(&mut self, _: Duration) {}
        fn receive_available(&mut self, bytes: &mut [u8]) -> Result<Option<usize>, TransportError> {
            match self.steps.pop_front() {
                Some(Step::Data(data)) => {
                    bytes[..data.len()].copy_from_slice(&data);
                    Ok(Some(data.len()))
                }
                Some(Step::Idle) => Ok(None),
                Some(Step::Close) => Ok(Some(0)),
                None => {
                    if self.cancel_when_done {
                        self.cancel.cancel();
                    }
                    Ok(None)
                }
            }
        }
    }

    const SEED: [u32; 4] = [1, 2, 3, 4];
    fn decoder() -> ConnectionDecoder {
        ConnectionDecoder::openrune(LoginSession {
            player: PlayerIndex::new(1),
            member: true,
            staff_level: 0,
            incoming: Isaac::new(SEED),
            outgoing: Isaac::new(SEED),
        })
        .0
    }
    /// A server frame with an ISAAC-masked opcode, like the wire.
    fn frame(cipher: &mut Isaac, opcode: u8, payload: &[u8]) -> Vec<u8> {
        let mut out = vec![opcode.wrapping_add(cipher.next_word().to_le_bytes()[0])];
        out.extend_from_slice(payload);
        out
    }
    fn outbound(intent: ClientIntent, map_epoch: u64) -> Outbound {
        Outbound {
            world_action: None,
            map_epoch,
            interface_epoch: None,
            intent,
        }
    }
    struct Run {
        result: Result<(), Failure>,
        written: Vec<u8>,
        events: Vec<String>,
    }
    fn drive(
        steps: Vec<Step>,
        queued: Vec<Vec<Outbound>>,
        timing: &LoopTiming,
        mut resolve: impl FnMut(SemanticOperation) -> Result<SemanticOperation, String>,
    ) -> Run {
        let cancel = ConnectionCancellation::default();
        let mut stream = Scripted {
            steps: steps.into(),
            written: Vec::new(),
            cancel: cancel.clone(),
            cancel_when_done: true,
        };
        let (sender, actions) = mpsc::sync_channel(32);
        for batch in queued {
            sender.send(batch).unwrap();
        }
        let events = RefCell::new(Vec::new());
        let emit = |event: NetworkEvent| {
            let label = match event {
                NetworkEvent::Received => "received".to_owned(),
                NetworkEvent::Written => "written".to_owned(),
                NetworkEvent::Batch(batch) => format!("batch:{}", batch.payload.len()),
                NetworkEvent::Failed(..) => "failed".to_owned(),
                _ => "other".to_owned(),
            };
            events.borrow_mut().push(label);
        };
        let result = session_loop(
            &mut stream,
            &mut decoder(),
            &mut resolve,
            &cancel,
            &actions,
            &emit,
            timing,
        );
        Run {
            result,
            written: stream.written,
            events: events.into_inner(),
        }
    }
    fn timing(silence: u64, bootstrap: u64) -> LoopTiming {
        LoopTiming {
            silence_limit: Duration::from_millis(silence),
            bootstrap_limit: Duration::from_millis(bootstrap),
            idle_wait: Duration::from_millis(1),
        }
    }
    fn identity() -> impl FnMut(SemanticOperation) -> Result<SemanticOperation, String> {
        |op| Ok(op)
    }

    #[test]
    fn a_closed_socket_ends_the_session_as_a_disconnect() {
        let run = drive(
            vec![Step::Close],
            vec![],
            &timing(10_000, 10_000),
            identity(),
        );
        assert_eq!(
            run.result.unwrap_err(),
            (
                LoginFailure::Disconnected,
                "server closed the connection".to_owned()
            )
        );
    }

    #[test]
    fn silence_past_the_limit_is_a_disconnect_and_a_missing_placement_is_a_protocol_failure() {
        let steps = || (0..2000).map(|_| Step::Idle).collect::<Vec<_>>();
        let silent = drive(steps(), vec![], &timing(20, 60_000), identity());
        assert_eq!(
            silent.result.unwrap_err(),
            (
                LoginFailure::Disconnected,
                "server stopped sending data".to_owned()
            )
        );
        let slow = drive(steps(), vec![], &timing(60_000, 20), identity());
        assert_eq!(
            slow.result.unwrap_err(),
            (
                LoginFailure::Protocol,
                "initial placement timed out".to_owned()
            )
        );
    }

    #[test]
    fn a_malformed_group_is_a_protocol_failure() {
        let mut cipher = Isaac::new(SEED);
        // A group header with a negative byte count.
        let bytes = frame(&mut cipher, 57, &[0xff, 0xff]);
        let run = drive(
            vec![Step::Data(bytes)],
            vec![],
            &timing(10_000, 10_000),
            identity(),
        );
        let (category, _) = run.result.unwrap_err();
        assert_eq!(category, LoginFailure::Protocol);
    }

    #[test]
    fn decoded_packets_are_forwarded_in_one_batch_and_a_policy_failure_names_its_cause() {
        let mut cipher = Isaac::new(SEED);
        let mut bytes = frame(&mut cipher, 92, &[]);
        bytes.extend(frame(&mut cipher, 92, &[]));
        let run = drive(
            vec![Step::Data(bytes.clone())],
            vec![],
            &timing(10_000, 10_000),
            identity(),
        );
        assert!(run.result.is_ok());
        assert_eq!(run.events, ["received", "batch:1", "batch:1"]);
        let failed = drive(
            vec![Step::Data(bytes)],
            vec![],
            &timing(10_000, 10_000),
            |_| Err("sequence 9 is missing".to_owned()),
        );
        assert_eq!(
            failed.result.unwrap_err(),
            (
                LoginFailure::CacheMismatch,
                "sequence 9 is missing".to_owned()
            )
        );
    }

    #[test]
    fn queued_actions_are_encoded_in_order_and_actions_for_another_map_are_dropped() {
        let mut reference = decoder();
        let keep_alive = reference.encode(ClientIntent::KeepAlive).unwrap();
        let close = reference.encode(ClientIntent::CloseModal).unwrap();
        // Epoch 0 is the loop's current map. The second batch targets a map that never arrived;
        // the keep-alive is session-wide and is sent regardless.
        let run = drive(
            vec![Step::Idle, Step::Idle, Step::Idle],
            vec![
                vec![
                    outbound(ClientIntent::KeepAlive, 0),
                    outbound(ClientIntent::CloseModal, 0),
                ],
                vec![outbound(ClientIntent::CloseModal, 7)],
            ],
            &timing(10_000, 10_000),
            identity(),
        );
        assert!(run.result.is_ok());
        let expected = [keep_alive, close].concat();
        assert_eq!(run.written, expected);
        assert_eq!(run.events.iter().filter(|e| *e == "written").count(), 2);
    }
}
