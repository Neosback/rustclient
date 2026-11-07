//! Worker-bound native TCP. No socket handles escape through shared contracts.
use rustclient_platform::{ByteStream, TransportError};
use std::{
    io::{Read, Write},
    net::{IpAddr, Ipv4Addr, SocketAddr, TcpStream, ToSocketAddrs},
    sync::{
        Arc,
        atomic::{AtomicBool, Ordering},
    },
    time::{Duration, Instant},
};
/// Cancellation can be requested from the event thread without touching a socket.
#[derive(Clone, Default)]
pub struct ConnectionCancellation(Arc<AtomicBool>);
impl ConnectionCancellation {
    pub fn cancel(&self) {
        self.0.store(true, Ordering::Release);
    }
    #[must_use]
    pub fn is_cancelled(&self) -> bool {
        self.0.load(Ordering::Acquire)
    }
}
/// Most addresses tried for one host name, so an unreachable name fails in bounded time.
const MAX_ADDRESSES: usize = 4;
/// The socket addresses to try for `host`: IP literals and `localhost` without a lookup, other
/// valid host names through the system resolver.
pub(crate) fn candidate_addresses(
    host: &str,
    port: u16,
) -> Result<Vec<SocketAddr>, TransportError> {
    if host == "localhost" {
        return Ok(vec![SocketAddr::new(IpAddr::V4(Ipv4Addr::LOCALHOST), port)]);
    }
    if let Ok(ip) = host.parse::<IpAddr>() {
        return Ok(vec![SocketAddr::new(ip, port)]);
    }
    let valid_name = !host.is_empty()
        && host.len() <= 253
        && host
            .bytes()
            .all(|byte| byte.is_ascii_alphanumeric() || matches!(byte, b'-' | b'.'));
    if !valid_name {
        return Err(TransportError::Unavailable);
    }
    let addresses: Vec<SocketAddr> = (host, port)
        .to_socket_addrs()
        .map_err(|_| TransportError::Unavailable)?
        .take(MAX_ADDRESSES)
        .collect();
    if addresses.is_empty() {
        Err(TransportError::Unavailable)
    } else {
        Ok(addresses)
    }
}
/// Native implementation intended to be driven on a dedicated worker.
pub struct NativeTcp {
    stream: TcpStream,
    cancel: ConnectionCancellation,
    deadline: Instant,
}
impl NativeTcp {
    /// # Errors
    /// Accepts an IP literal, `localhost` or a host name, which is resolved here (on the worker)
    /// and tried address by address. Fails on resolution/connect/setup; host strings and payloads
    /// are not included in errors.
    pub fn connect(
        host: &str,
        port: u16,
        cancel: ConnectionCancellation,
    ) -> Result<Self, TransportError> {
        if cancel.is_cancelled() {
            return Err(TransportError::Cancelled);
        }
        let mut connected = None;
        for address in candidate_addresses(host, port)? {
            if cancel.is_cancelled() {
                return Err(TransportError::Cancelled);
            }
            if let Ok(stream) = TcpStream::connect_timeout(&address, Duration::from_secs(2)) {
                connected = Some(stream);
                break;
            }
        }
        let stream = connected.ok_or(TransportError::Unavailable)?;
        stream
            .set_read_timeout(Some(Duration::from_millis(100)))
            .map_err(|_| TransportError::Unavailable)?;
        stream
            .set_write_timeout(Some(Duration::from_millis(100)))
            .map_err(|_| TransportError::Unavailable)?;
        stream
            .set_nodelay(true)
            .map_err(|_| TransportError::Unavailable)?;
        Ok(Self {
            stream,
            cancel,
            deadline: Instant::now() + Duration::from_secs(20),
        })
    }
    /// Poll already available input without holding up an application worker's outbound queue.
    /// Socket mode is restored before returning, so send-all/login retain their blocking contract.
    /// # Errors
    /// Reports cancellation/deadline, peer closure on the next read, or socket setup/read failure.
    pub fn receive_available(&mut self, bytes: &mut [u8]) -> Result<Option<usize>, TransportError> {
        self.check()?;
        self.stream
            .set_nonblocking(true)
            .map_err(|_| TransportError::Unavailable)?;
        let result = self.stream.read(bytes);
        self.stream
            .set_nonblocking(false)
            .map_err(|_| TransportError::Unavailable)?;
        match result {
            Ok(n) => Ok(Some(n)),
            Err(error)
                if matches!(
                    error.kind(),
                    std::io::ErrorKind::WouldBlock | std::io::ErrorKind::Interrupted
                ) =>
            {
                Ok(None)
            }
            Err(_) => Err(TransportError::Closed),
        }
    }
    pub fn deadline_after(&mut self, duration: Duration) {
        self.deadline = Instant::now() + duration;
    }
    fn check(&self) -> Result<(), TransportError> {
        if self.cancel.is_cancelled() {
            Err(TransportError::Cancelled)
        } else if Instant::now() >= self.deadline {
            Err(TransportError::Timeout)
        } else {
            Ok(())
        }
    }
}
impl ByteStream for NativeTcp {
    fn receive(&mut self, bytes: &mut [u8]) -> impl Future<Output = Result<usize, TransportError>> {
        let result = (|| loop {
            self.check()?;
            match self.stream.read(bytes) {
                Ok(n) => return Ok(n),
                Err(e)
                    if matches!(
                        e.kind(),
                        std::io::ErrorKind::TimedOut
                            | std::io::ErrorKind::WouldBlock
                            | std::io::ErrorKind::Interrupted
                    ) => {}
                Err(_) => return Err(TransportError::Closed),
            }
        })();
        std::future::ready(result)
    }
    fn send(&mut self, bytes: &[u8]) -> impl Future<Output = Result<(), TransportError>> {
        let result = (|| {
            let mut offset = 0;
            while offset < bytes.len() {
                self.check()?;
                match self.stream.write(&bytes[offset..]) {
                    Ok(0) => return Err(TransportError::Closed),
                    Ok(n) => offset += n,
                    Err(e)
                        if matches!(
                            e.kind(),
                            std::io::ErrorKind::TimedOut
                                | std::io::ErrorKind::WouldBlock
                                | std::io::ErrorKind::Interrupted
                        ) => {}
                    Err(_) => return Err(TransportError::Closed),
                }
            }
            Ok(())
        })();
        std::future::ready(result)
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use std::net::TcpListener;
    #[test]
    fn literals_and_localhost_skip_the_resolver_and_malformed_names_are_refused() {
        assert_eq!(
            candidate_addresses("127.0.0.1", 43594).unwrap(),
            [SocketAddr::new(IpAddr::V4(Ipv4Addr::LOCALHOST), 43594)]
        );
        assert_eq!(candidate_addresses("::1", 1).unwrap().len(), 1);
        assert_eq!(candidate_addresses("localhost", 7).unwrap().len(), 1);
        for bad in ["", "has space", "a/b", "name:80", &"x".repeat(300)] {
            assert_eq!(
                candidate_addresses(bad, 1),
                Err(TransportError::Unavailable),
                "{bad:?}"
            );
        }
    }
    #[test]
    fn idle_receive_poll_does_not_hold_outbound_input_behind_the_server() {
        let listener = TcpListener::bind((Ipv4Addr::LOCALHOST, 0)).unwrap();
        let port = listener.local_addr().unwrap().port();
        let peer = std::thread::spawn(move || {
            let (mut socket, _) = listener.accept().unwrap();
            socket
                .set_read_timeout(Some(Duration::from_secs(2)))
                .unwrap();
            let mut request = [0; 1];
            socket.read_exact(&mut request).unwrap();
            assert_eq!(request, [7]);
            socket.write_all(&[8]).unwrap();
        });
        let mut stream =
            NativeTcp::connect("127.0.0.1", port, ConnectionCancellation::default()).unwrap();
        let mut bytes = [0; 1];
        assert_eq!(
            stream.receive_available(&mut bytes).unwrap(),
            None,
            "server deliberately waits for our action"
        );
        pollster::block_on(stream.send(&[7])).unwrap();
        assert_eq!(pollster::block_on(stream.receive(&mut bytes)).unwrap(), 1);
        assert_eq!(bytes, [8]);
        peer.join().unwrap();
    }
    #[test]
    fn native_stream_exchanges_ordered_bytes_and_reports_deadline_and_cancellation() {
        let listener = TcpListener::bind((Ipv4Addr::LOCALHOST, 0)).unwrap();
        let port = listener.local_addr().unwrap().port();
        let peer = std::thread::spawn(move || {
            let (mut socket, _) = listener.accept().unwrap();
            let mut request = [0; 3];
            socket.read_exact(&mut request).unwrap();
            assert_eq!(&request, b"abc");
            socket.write_all(b"ok").unwrap();
        });
        let cancel = ConnectionCancellation::default();
        let mut stream = NativeTcp::connect("127.0.0.1", port, cancel.clone()).unwrap();
        pollster::block_on(stream.send(b"abc")).unwrap();
        let mut reply = [0; 2];
        let mut offset = 0;
        while offset < 2 {
            offset += pollster::block_on(stream.receive(&mut reply[offset..])).unwrap();
        }
        assert_eq!(&reply, b"ok");
        peer.join().unwrap();
        stream.deadline_after(Duration::ZERO);
        assert_eq!(
            pollster::block_on(stream.receive(&mut reply)),
            Err(TransportError::Timeout)
        );
        cancel.cancel();
        assert_eq!(
            pollster::block_on(stream.receive(&mut reply)),
            Err(TransportError::Cancelled)
        );
        assert!(matches!(
            NativeTcp::connect("127.0.0.1", port, cancel),
            Err(TransportError::Cancelled)
        ));
    }
}
