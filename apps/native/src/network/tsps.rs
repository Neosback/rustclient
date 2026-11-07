//! Native transport composition; TSPS packet knowledge lives in the protocol adapter.
use super::{
    ByteStream, ConnectionCancellation, ConnectionDecoder, ConnectionOptions, Credentials,
    DirectoryBlobs, Duration, Failure, LoginFailure, NativeTcp, NetworkEvent,
    SequencePolicyResolver, SessionStream, TransportError,
};
use rustclient_platform_native::NativeWebSocket;

pub(super) enum NativeSessionStream {
    Tcp(NativeTcp),
    WebSocket(Box<NativeWebSocket>),
}
impl ByteStream for NativeSessionStream {
    fn receive(&mut self, bytes: &mut [u8]) -> impl Future<Output = Result<usize, TransportError>> {
        std::future::ready(match self {
            Self::Tcp(s) => pollster::block_on(s.receive(bytes)),
            Self::WebSocket(s) => pollster::block_on(s.receive(bytes)),
        })
    }
    fn send(&mut self, bytes: &[u8]) -> impl Future<Output = Result<(), TransportError>> {
        std::future::ready(match self {
            Self::Tcp(s) => pollster::block_on(s.send(bytes)),
            Self::WebSocket(s) => pollster::block_on(s.send(bytes)),
        })
    }
}
impl SessionStream for NativeSessionStream {
    fn deadline_after(&mut self, duration: Duration) {
        match self {
            Self::Tcp(s) => s.deadline_after(duration),
            Self::WebSocket(s) => s.deadline_after(duration),
        }
    }
    fn receive_available(&mut self, bytes: &mut [u8]) -> Result<Option<usize>, TransportError> {
        match self {
            Self::Tcp(s) => s.receive_available(bytes),
            Self::WebSocket(s) => s.receive_available(bytes),
        }
    }
}
pub(super) fn open_tsps(
    options: &ConnectionOptions,
    credentials: Credentials,
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
    emit(NetworkEvent::Progress("Opening TSPS connection"));
    let source = DirectoryBlobs::open(&options.cache_dir)
        .map_err(|e| (LoginFailure::CacheMismatch, e.to_string()))?;
    let cache = rustclient_cache::Cache::open(source)
        .map_err(|e| (LoginFailure::CacheMismatch, e.to_string()))?;
    rustclient_cache::tsps241::validate_identity(&cache)
        .map_err(|e| (LoginFailure::CacheMismatch, e.to_string()))?;
    let sizes = cache
        .huffman_sizes()
        .map_err(|e| (LoginFailure::CacheMismatch, e.to_string()))?
        .ok_or_else(|| {
            (
                LoginFailure::CacheMismatch,
                "cache has no chat Huffman table".into(),
            )
        })?;
    let chat = rustclient_protocol::Huffman::from_sizes(&sizes)
        .map_err(|e| (LoginFailure::CacheMismatch, e.to_string()))?;
    let mut stream =
        NativeWebSocket::connect(&options.server_url, cancel.clone()).map_err(|e| {
            (
                LoginFailure::Connection,
                format!("TSPS WebSocket connection: {e}"),
            )
        })?;
    let login = pollster::block_on(rustclient_protocol::tsps_241::authenticate(
        &mut stream,
        credentials,
    ))
    .map_err(|e| {
        let category = if e.transport.is_some() {
            LoginFailure::Connection
        } else {
            e.rejection
                .map_or(LoginFailure::Protocol, LoginFailure::Rejected)
        };
        (category, e.to_string())
    })?;
    let (mut decoder, authenticated) = ConnectionDecoder::tsps(login);
    decoder.set_chat_codec(chat);
    let cache = std::sync::Arc::new(cache);
    let definitions = std::sync::Arc::clone(&cache);
    let profile = options.cache_profile;
    decoder.set_varbit_lookup(Box::new(move |id| {
        let definition = profile.script_varbit(&definitions, id.get()).map_err(|e| {
            rustclient_protocol::ProtocolError::new("TSPS varbit definition", e.to_string())
        })?;
        rustclient_model::VarpBitRange::new(definition.varp, definition.low, definition.high)
            .ok_or_else(|| {
                rustclient_protocol::ProtocolError::new(
                    "TSPS varbit definition",
                    "invalid bit range",
                )
            })
    }));
    emit(NetworkEvent::Operation(0, 0, authenticated));
    Ok((
        NativeSessionStream::WebSocket(Box::new(stream)),
        decoder,
        SequencePolicyResolver::shared(cache, options.cache_profile),
    ))
}
