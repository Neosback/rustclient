//! Embedded `RuneLite` icons (BSD 2-Clause, see `assets/runelite/LICENSE`) served to the retained DOM.
//!
//! Components reference icons as `asset://runelite/NAME.png`. The native panel answers those requests from
//! the binary, so no file or network access happens at runtime.

/// Raw `open_rs.png` bytes (`16x22` RGBA) for the game-canvas sidebar toggle overlay.
pub const OPEN_RS_PNG: &[u8] = include_bytes!("../assets/runelite/open_rs.png");

/// URL used by components for an embedded icon.
macro_rules! icon {
    ($name:literal) => {
        concat!("asset://runelite/", $name, ".png")
    };
}
pub(crate) use icon;

#[cfg(not(target_arch = "wasm32"))]
pub(crate) use native::{Collector, EmbeddedAssets};

#[cfg(not(target_arch = "wasm32"))]
mod native {
    use blitz_dom::net::Resource;
    use blitz_traits::net::{
        BoxedHandler, Bytes, NetCallback, NetProvider, Request, SharedCallback,
    };
    use std::sync::{Arc, Mutex};

    macro_rules! table {
        ($($name:literal),* $(,)?) => {
            fn lookup(name: &str) -> Option<&'static [u8]> {
                match name {
                    $(concat!($name, ".png") => Some(include_bytes!(concat!("../assets/runelite/", $name, ".png")).as_slice()),)*
                    _ => None,
                }
            }
        };
    }
    table!(
        "config_icon",
        "config_icon_lg",
        "config_back_icon",
        "config_edit_icon",
        "refresh",
        "star_on",
        "profile_icon",
        "runelite_16",
        "runelite_128",
        "open",
        "open_rs",
        "search",
        "error",
        "devtools_icon",
        "collapsed",
        "expanded",
    );

    /// Decoded resources waiting for the document to adopt them.
    #[derive(Default)]
    pub(crate) struct Collector(Mutex<Vec<Resource>>);
    impl Collector {
        pub(crate) fn take(&self) -> Vec<Resource> {
            self.0
                .lock()
                .map(|mut queue| std::mem::take(&mut *queue))
                .unwrap_or_default()
        }
    }
    impl NetCallback<Resource> for Collector {
        fn call(&self, _doc_id: usize, result: Result<Resource, Option<String>>) {
            if let (Ok(resource), Ok(mut queue)) = (result, self.0.lock()) {
                queue.push(resource);
            }
        }
    }

    pub(crate) struct EmbeddedAssets(pub(crate) Arc<Collector>);
    impl NetProvider<Resource> for EmbeddedAssets {
        fn fetch(&self, doc_id: usize, request: Request, handler: BoxedHandler<Resource>) {
            let (host, name) = (
                request.url.host_str().unwrap_or_default(),
                request.url.path().trim_start_matches('/'),
            );
            if request.url.scheme() == "asset"
                && host == "runelite"
                && let Some(bytes) = lookup(name)
            {
                let callback: SharedCallback<Resource> = self.0.clone();
                handler.bytes(doc_id, Bytes::from_static(bytes), callback);
            }
        }
    }
}
