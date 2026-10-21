//! Browser composition root.

#![forbid(unsafe_code)]

/// Marker retained for host-side workspace checks.
pub const FOUNDATION_READY: bool = true;

#[cfg(target_arch = "wasm32")]
mod browser {
    use std::{cell::RefCell, rc::Rc, sync::Arc};

    use rustclient_renderer::{ClearColor, FrameOutcome, SurfaceRenderer, SurfaceSize};
    use wasm_bindgen::prelude::*;
    use wasm_bindgen_futures::spawn_local;
    use winit::{
        application::ApplicationHandler,
        event::WindowEvent,
        event_loop::{ActiveEventLoop, ControlFlow, EventLoop},
        platform::web::{EventLoopExtWebSys, WindowAttributesExtWebSys},
        window::{Window, WindowId},
    };

    #[derive(Default)]
    struct SharedState {
        window: Option<Arc<Window>>,
        renderer: Option<SurfaceRenderer>,
        initializing_renderer: bool,
    }

    #[derive(Default)]
    struct WebApp {
        shared: Rc<RefCell<SharedState>>,
    }

    impl WebApp {
        fn begin_renderer_init(&self) {
            let window = {
                let mut shared = self.shared.borrow_mut();
                if shared.initializing_renderer || shared.renderer.is_some() {
                    return;
                }
                let Some(window) = shared.window.clone() else {
                    return;
                };
                shared.initializing_renderer = true;
                window
            };
            let size = window.inner_size();
            let shared = Rc::clone(&self.shared);

            spawn_local(async move {
                let result =
                    SurfaceRenderer::new(window.clone(), SurfaceSize::new(size.width, size.height))
                        .await;

                let initialized = {
                    let mut state = shared.borrow_mut();
                    state.initializing_renderer = false;
                    match result {
                        Ok(renderer) => {
                            state.renderer = Some(renderer);
                            true
                        }
                        Err(error) => {
                            log_error(&format!("failed to initialize browser renderer: {error}"));
                            false
                        }
                    }
                };

                if initialized {
                    window.request_redraw();
                }
            });
        }
    }

    impl ApplicationHandler for WebApp {
        fn resumed(&mut self, event_loop: &ActiveEventLoop) {
            if self.shared.borrow().window.is_some() {
                return;
            }

            let attributes = Window::default_attributes()
                .with_title("RustClient")
                .with_append(true);
            let window = match event_loop.create_window(attributes) {
                Ok(window) => Arc::new(window),
                Err(error) => {
                    log_error(&format!("failed to create browser canvas: {error}"));
                    event_loop.exit();
                    return;
                }
            };
            self.shared.borrow_mut().window = Some(window);
            self.begin_renderer_init();
        }

        fn window_event(
            &mut self,
            event_loop: &ActiveEventLoop,
            window_id: WindowId,
            event: WindowEvent,
        ) {
            let Some(window) = self.shared.borrow().window.clone() else {
                return;
            };
            if window.id() != window_id {
                return;
            }

            match event {
                WindowEvent::CloseRequested => event_loop.exit(),
                WindowEvent::Resized(size) => {
                    if let Some(renderer) = self.shared.borrow_mut().renderer.as_mut() {
                        renderer.resize(SurfaceSize::new(size.width, size.height));
                        if size.width != 0 && size.height != 0 {
                            window.request_redraw();
                        }
                    }
                }
                WindowEvent::RedrawRequested => {
                    let result = self.shared.borrow_mut().renderer.as_mut().map(|renderer| {
                        renderer.render_clear(ClearColor::BOOTSTRAP, || window.pre_present_notify())
                    });

                    match result {
                        Some(Ok(FrameOutcome::Reconfigured)) => window.request_redraw(),
                        Some(Ok(FrameOutcome::RecreateRequired)) => {
                            self.shared.borrow_mut().renderer = None;
                            self.begin_renderer_init();
                        }
                        Some(Ok(
                            FrameOutcome::Presented
                            | FrameOutcome::Suspended
                            | FrameOutcome::Skipped,
                        ))
                        | None => {}
                        Some(Err(error)) => {
                            log_error(&format!("browser render failure: {error}"));
                            event_loop.exit();
                        }
                    }
                }
                _ => {}
            }
        }
    }

    fn log_error(message: &str) {
        web_sys::console::error_1(&JsValue::from_str(message));
    }

    #[wasm_bindgen(start)]
    pub fn start() -> Result<(), JsValue> {
        let event_loop = EventLoop::new().map_err(|error| JsValue::from_str(&error.to_string()))?;
        event_loop.set_control_flow(ControlFlow::Wait);
        event_loop.spawn_app(WebApp::default());
        Ok(())
    }
}

#[cfg(target_arch = "wasm32")]
pub use browser::start;
