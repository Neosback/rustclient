//! First-slice sample plugin.
//!
//! Demonstrates the four acceptance behaviors from SPEC-0004: subscribe to one event type
//! (`tick`), query one state value (`player-position`), persist plugin-local config
//! (`last_tick`), and submit one overlay command. The same crate builds for native component
//! loading and (later) browser component transpilation.
//!
//! The crate is intentionally `no_std`: a plugin component must import nothing except the
//! granted `rustclient:plugin/host` interface. WASI, filesystem, sockets and environment are
//! absent from the ABI, not merely policy-denied. A future `rustclient plugin new` template
//! will move the guest runtime section below into `rustclient-plugin-sdk`.

#![no_std]

extern crate alloc;

use alloc::{
    format,
    string::{String, ToString},
    vec::Vec,
};
use core::alloc::Layout;

wit_bindgen::generate!({
    path: "../../wit/rustclient/plugin/world.wit",
    world: "plugin",
});

use exports::rustclient::plugin::guest::{Event, Guest, OverlayLine};
use rustclient::plugin::host::{self, LogLevel, Player};

/// Opaque yellow, packed `AARRGGBB`.
const OVERLAY_COLOR: u32 = 0xFFFF_FF00;
const CONFIG_LAST_TICK: &str = "last_tick";
const REFRESH_INTERVAL: u64 = 50;

// ---------------------------------------------------------------------------
// Guest runtime plumbing (canonical ABI + allocator).
// ---------------------------------------------------------------------------

#[global_allocator]
static ALLOCATOR: dlmalloc::GlobalDlmalloc = dlmalloc::GlobalDlmalloc;

#[panic_handler]
fn panic(_info: &core::panic::PanicInfo<'_>) -> ! {
    core::arch::wasm32::unreachable()
}

/// Canonical ABI reallocator. Strong definition overrides the weak wit-bindgen shim, which
/// relies on `std` for `wasm32-wasip2` builds this crate does not use.
///
/// # Safety
///
/// Satisfies the component-model canonical ABI contract: `ptr/old_len` describe a live
/// allocation from this same allocator, or `old_len == 0`.
#[unsafe(no_mangle)]
pub unsafe extern "C" fn cabi_realloc(
    ptr: *mut u8,
    old_len: usize,
    align: usize,
    new_len: usize,
) -> *mut u8 {
    // SAFETY: callers obey the canonical ABI contract described above.
    unsafe {
        use alloc::alloc::{alloc, handle_alloc_error, realloc};
        if old_len == 0 {
            if new_len == 0 {
                return align as *mut u8;
            }
            let layout = Layout::from_size_align_unchecked(new_len, align);
            let allocated = alloc(layout);
            if allocated.is_null() {
                handle_alloc_error(layout);
            }
            allocated
        } else {
            let layout = Layout::from_size_align_unchecked(old_len, align);
            let grown = realloc(ptr, layout, new_len);
            if grown.is_null() {
                handle_alloc_error(layout);
            }
            grown
        }
    }
}

// ---------------------------------------------------------------------------
// Plugin.
// ---------------------------------------------------------------------------

struct State {
    ticks: u64,
    line: Option<String>,
}

static STATE: spin::Mutex<State> = spin::Mutex::new(State {
    ticks: 0,
    line: None,
});

struct HelloOverlay;

impl Guest for HelloOverlay {
    fn init() {
        let restored = host::config_get(CONFIG_LAST_TICK)
            .and_then(|value| value.parse::<u64>().ok())
            .unwrap_or(0);
        STATE.lock().ticks = restored;
        host::log(
            LogLevel::Info,
            &format!("hello-overlay ready (last persisted tick {restored})"),
        );
    }

    fn on_event(events: Vec<Event>) {
        let mut state = STATE.lock();
        for event in events {
            match event {
                Event::Tick(tick) => {
                    state.ticks += 1;
                    if state.ticks % REFRESH_INTERVAL == 0 {
                        let location = host::player_position().map_or_else(
                            || "unknown".to_string(),
                            |Player { x, y, plane }| format!("{x},{y} plane {plane}"),
                        );
                        state.line = Some(format!("hello-overlay · tick {tick} · {location}"));
                        host::config_set(CONFIG_LAST_TICK, &tick.to_string());
                    }
                }
                Event::Started => host::log(LogLevel::Debug, "session started"),
                Event::Stopped => host::log(LogLevel::Debug, "session stopped"),
            }
        }
    }

    fn overlay() -> Vec<OverlayLine> {
        let state = STATE.lock();
        state
            .line
            .iter()
            .map(|text| OverlayLine {
                text: text.clone(),
                x: 8,
                y: 8,
                argb: OVERLAY_COLOR,
            })
            .collect()
    }
}

export!(HelloOverlay);
