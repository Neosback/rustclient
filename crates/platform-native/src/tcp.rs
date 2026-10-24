//! Worker-bound native TCP. No socket handles escape through shared contracts.
use rustclient_platform::{ByteStream, TransportError};
use std::{
    io::{Read, Write},
    net::{IpAddr, Ipv4Addr, SocketAddr, TcpStream},
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
/// Native implementation intended to be driven on a dedicated worker.
pub struct NativeTcp {
    stream: TcpStream,
    cancel: ConnectionCancellation,
    deadline: Instant,
}
impl NativeTcp {
    /// # Errors
    /// Accepts an IP literal or localhost. Fails on connect/setup; host strings and payloads are not included in errors.
    pub fn connect(
        host: &str,
        port: u16,
        cancel: ConnectionCancellation,
    ) -> Result<Self, TransportError> {
        if cancel.is_cancelled() {
            return Err(TransportError::Cancelled);
        }
        let ip = if host == "localhost" {
            IpAddr::V4(Ipv4Addr::LOCALHOST)
        } else {
            host.parse().map_err(|_| TransportError::Unavailable)?
        };
        let stream = TcpStream::connect_timeout(&SocketAddr::new(ip, port), Duration::from_secs(2))
            .map_err(|_| TransportError::Unavailable)?;
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
    #[allow(
        clippy::unused_async_trait_impl,
        reason = "ByteStream keeps one async interface across native and web transports"
    )]
    async fn receive(&mut self, bytes: &mut [u8]) -> Result<usize, TransportError> {
        loop {
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
    #[allow(
        clippy::unused_async_trait_impl,
        reason = "ByteStream keeps one async interface across native and web transports"
    )]
    async fn send(&mut self, bytes: &[u8]) -> Result<(), TransportError> {
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
