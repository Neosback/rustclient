//! Worker-owned binary WebSocket transport. Protocol packets remain opaque ordered bytes.
use crate::ConnectionCancellation;
use rustclient_platform::{ByteStream, TransportError};
use std::{
    io,
    net::TcpStream,
    time::{Duration, Instant},
};
use tungstenite::{Message, WebSocket, protocol::WebSocketConfig, stream::MaybeTlsStream};

// One maximum TSPS var-short packet includes its opcode and two length bytes.
// Ordinary server batches cap at 64 KiB, but a single full packet may exceed that by two bytes.
const LIMIT: usize = 65_538;

pub struct NativeWebSocket {
    socket: WebSocket<MaybeTlsStream<TcpStream>>,
    pending: Vec<u8>,
    offset: usize,
    cancel: ConnectionCancellation,
    deadline: Instant,
}
impl NativeWebSocket {
    /// Connect to an explicit ws/wss endpoint; TLS verifies the endpoint's certificate.
    /// # Errors
    /// Reports transport categories without including endpoint credentials or payloads.
    pub fn connect(url: &str, cancel: ConnectionCancellation) -> Result<Self, TransportError> {
        use tungstenite::client::IntoClientRequest;
        let handshake_deadline = Instant::now() + Duration::from_secs(20);
        check_deadline(&cancel, handshake_deadline)?;
        let request = url
            .into_client_request()
            .map_err(|_| TransportError::Unavailable)?;
        if request
            .uri()
            .authority()
            .is_none_or(|a| a.as_str().contains('@'))
        {
            return Err(TransportError::Unavailable);
        }
        let secure = match request.uri().scheme_str() {
            Some("ws") => false,
            Some("wss") => true,
            _ => return Err(TransportError::Unavailable),
        };
        let host = request
            .uri()
            .host()
            .ok_or(TransportError::Unavailable)?
            .trim_matches(['[', ']']);
        let port = request
            .uri()
            .port_u16()
            .unwrap_or(if secure { 443 } else { 80 });
        let mut connected = None;
        for address in crate::tcp::candidate_addresses(host, port)? {
            check_deadline(&cancel, handshake_deadline)?;
            let timeout = Duration::from_secs(2)
                .min(handshake_deadline.saturating_duration_since(Instant::now()));
            if let Ok(stream) = TcpStream::connect_timeout(&address, timeout) {
                connected = Some(stream);
                break;
            }
        }
        let stream = connected.ok_or(TransportError::Unavailable)?;
        stream
            .set_nonblocking(true)
            .map_err(|_| TransportError::Unavailable)?;
        stream
            .set_nodelay(true)
            .map_err(|_| TransportError::Unavailable)?;
        let config = WebSocketConfig::default()
            .read_buffer_size(16_384)
            .write_buffer_size(0)
            .max_write_buffer_size(2 * LIMIT)
            .max_message_size(Some(LIMIT))
            .max_frame_size(Some(LIMIT));
        // TLS and HTTP upgrade must be cancellable too. Per-read timeouts on a blocking
        // handshake do not bound a peer that keeps sending partial headers.
        let mut handshake =
            tungstenite::client_tls_with_config(request, stream, Some(config), None);
        let (socket, _) = loop {
            check_deadline(&cancel, handshake_deadline)?;
            match handshake {
                Ok(result) => break result,
                Err(tungstenite::HandshakeError::Interrupted(pending)) => {
                    std::thread::sleep(Duration::from_millis(2));
                    handshake = pending.handshake();
                }
                Err(tungstenite::HandshakeError::Failure(_)) => {
                    return Err(TransportError::Unavailable);
                }
            }
        };
        Ok(Self {
            socket,
            pending: Vec::new(),
            offset: 0,
            cancel,
            deadline: Instant::now() + Duration::from_secs(20),
        })
    }
    pub fn deadline_after(&mut self, duration: Duration) {
        self.deadline = Instant::now() + duration;
    }
    fn check(&self) -> Result<(), TransportError> {
        check_deadline(&self.cancel, self.deadline)
    }
    /// Poll without holding the connection worker behind an idle peer.
    /// # Errors
    /// Rejects text messages and oversized frames instead of accepting an alternate protocol.
    pub fn receive_available(&mut self, bytes: &mut [u8]) -> Result<Option<usize>, TransportError> {
        self.check()?;
        if bytes.is_empty() {
            return Ok(Some(0));
        }
        if self.offset < self.pending.len() {
            let count = bytes.len().min(self.pending.len() - self.offset);
            bytes[..count].copy_from_slice(&self.pending[self.offset..self.offset + count]);
            self.offset += count;
            if self.offset == self.pending.len() {
                self.pending.clear();
                self.offset = 0;
            }
            return Ok(Some(count));
        }
        // Bound control-frame work per poll as well as buffered message bytes.
        for _ in 0..16 {
            match self.socket.read() {
                Ok(Message::Binary(data)) if !data.is_empty() => {
                    self.pending = data.to_vec();
                    return self.receive_available(bytes);
                }
                Ok(Message::Binary(_) | Message::Pong(_)) => {}
                Ok(Message::Ping(_)) => match self.socket.flush() {
                    Ok(()) => {}
                    Err(tungstenite::Error::Io(e)) if transient(&e) => {}
                    Err(_) => return Err(TransportError::Closed),
                },
                Ok(Message::Close(_)) | Err(tungstenite::Error::ConnectionClosed) => {
                    return Ok(Some(0));
                }
                Err(tungstenite::Error::Io(e)) if transient(&e) => return Ok(None),
                Ok(_) | Err(_) => return Err(TransportError::Closed),
            }
        }
        Ok(None)
    }
}
fn check_deadline(
    cancel: &ConnectionCancellation,
    deadline: Instant,
) -> Result<(), TransportError> {
    if cancel.is_cancelled() {
        Err(TransportError::Cancelled)
    } else if Instant::now() >= deadline {
        Err(TransportError::Timeout)
    } else {
        Ok(())
    }
}
fn transient(e: &io::Error) -> bool {
    matches!(
        e.kind(),
        io::ErrorKind::WouldBlock | io::ErrorKind::Interrupted | io::ErrorKind::TimedOut
    )
}
impl ByteStream for NativeWebSocket {
    fn receive(&mut self, bytes: &mut [u8]) -> impl Future<Output = Result<usize, TransportError>> {
        let result = (|| loop {
            if let Some(count) = self.receive_available(bytes)? {
                return Ok(count);
            }
            std::thread::sleep(Duration::from_millis(2));
        })();
        std::future::ready(result)
    }
    fn send(&mut self, bytes: &[u8]) -> impl Future<Output = Result<(), TransportError>> {
        let result = (|| {
            self.check()?;
            if bytes.len() > LIMIT {
                return Err(TransportError::Unavailable);
            }
            match self.socket.write(Message::Binary(bytes.to_vec().into())) {
                Ok(()) => {}
                // Tungstenite retains the accepted message on WouldBlock. Flush it, never resend.
                Err(tungstenite::Error::Io(e)) if transient(&e) => {}
                Err(_) => return Err(TransportError::Closed),
            }
            loop {
                self.check()?;
                match self.socket.flush() {
                    Ok(()) => return Ok(()),
                    Err(tungstenite::Error::Io(e)) if transient(&e) => {
                        std::thread::sleep(Duration::from_millis(2));
                    }
                    Err(_) => return Err(TransportError::Closed),
                }
            }
        })();
        std::future::ready(result)
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use std::net::TcpListener;

    #[test]
    fn cancellation_interrupts_a_peer_that_never_completes_the_http_upgrade() {
        use std::{io::Read, sync::mpsc};
        let listener = TcpListener::bind("127.0.0.1:0").unwrap();
        let address = listener.local_addr().unwrap();
        let (accepted, ready) = mpsc::channel();
        let peer = std::thread::spawn(move || {
            let (mut stream, _) = listener.accept().unwrap();
            stream
                .set_read_timeout(Some(Duration::from_secs(3)))
                .unwrap();
            accepted.send(()).unwrap();
            // Deliberately withhold the HTTP upgrade response. Client cancellation closes TCP.
            let mut bytes = [0; 1024];
            while stream.read(&mut bytes).unwrap() != 0 {}
        });
        let cancel = ConnectionCancellation::default();
        let connection_cancel = cancel.clone();
        let (result, completed) = mpsc::channel();
        let worker = std::thread::spawn(move || {
            result
                .send(
                    NativeWebSocket::connect(&format!("ws://{address}/"), connection_cancel)
                        .map(drop),
                )
                .unwrap();
        });
        ready.recv_timeout(Duration::from_secs(2)).unwrap();
        cancel.cancel();
        assert_eq!(
            completed.recv_timeout(Duration::from_secs(2)).unwrap(),
            Err(TransportError::Cancelled)
        );
        worker.join().unwrap();
        peer.join().unwrap();
    }

    #[test]
    fn binary_transport_preserves_order_across_messages_and_partial_consumer_reads() {
        let listener = TcpListener::bind("127.0.0.1:0").unwrap();
        let address = listener.local_addr().unwrap();
        let peer = std::thread::spawn(move || {
            let (stream, _) = listener.accept().unwrap();
            stream
                .set_read_timeout(Some(Duration::from_secs(3)))
                .unwrap();
            let mut ws = tungstenite::accept(stream).unwrap();
            assert_eq!(ws.read().unwrap().into_data().as_ref(), b"action");
            ws.send(Message::Binary(vec![1, 2, 3].into())).unwrap();
            ws.send(Message::Binary(vec![4, 5].into())).unwrap();
            ws.close(None).unwrap();
        });
        let cancel = ConnectionCancellation::default();
        let mut stream =
            NativeWebSocket::connect(&format!("ws://{address}/"), cancel.clone()).unwrap();
        let mut data = [0; 2];
        assert_eq!(stream.receive_available(&mut data).unwrap(), None);
        pollster::block_on(stream.send(b"action")).unwrap();
        let mut actual = Vec::new();
        while actual.len() < 5 {
            let count = pollster::block_on(stream.receive(&mut data)).unwrap();
            assert!(count > 0);
            actual.extend_from_slice(&data[..count]);
        }
        assert_eq!(actual, [1, 2, 3, 4, 5]);
        cancel.cancel();
        assert_eq!(
            stream.receive_available(&mut data),
            Err(TransportError::Cancelled)
        );
        peer.join().unwrap();
    }

    #[test]
    fn text_frames_fail_and_pre_cancelled_connections_do_not_open_a_socket() {
        let cancelled = ConnectionCancellation::default();
        cancelled.cancel();
        assert!(matches!(
            NativeWebSocket::connect("ws://127.0.0.1:1/", cancelled),
            Err(TransportError::Cancelled)
        ));
        let listener = TcpListener::bind("127.0.0.1:0").unwrap();
        let address = listener.local_addr().unwrap();
        let peer = std::thread::spawn(move || {
            let (stream, _) = listener.accept().unwrap();
            let mut ws = tungstenite::accept(stream).unwrap();
            ws.send(Message::Text("not the binary protocol".into()))
                .unwrap();
        });
        let mut stream = NativeWebSocket::connect(
            &format!("ws://{address}/"),
            ConnectionCancellation::default(),
        )
        .unwrap();
        let error = pollster::block_on(stream.receive(&mut [0; 8])).unwrap_err();
        assert_eq!(error, TransportError::Closed);
        peer.join().unwrap();
    }
}
