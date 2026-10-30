//! Browser composition root.

#![forbid(unsafe_code)]

/// Marker retained for host-side workspace checks.
pub const FOUNDATION_READY: bool = true;
#[cfg(target_arch = "wasm32")]
mod shell;

#[cfg(target_arch = "wasm32")]
mod browser {
    use std::{cell::RefCell, rc::Rc, sync::Arc};

    use rustclient_renderer::{ClearColor, FrameOutcome, SurfaceRenderer, SurfaceSize};
    use wasm_bindgen::{JsCast, prelude::*};
    fn now() -> f64 {
        web_sys::window()
            .and_then(|w| w.performance())
            .map_or(0., |p| p.now() / 1000.)
    }
    use wasm_bindgen_futures::spawn_local;
    use winit::{
        application::ApplicationHandler,
        event::WindowEvent,
        event_loop::{ActiveEventLoop, ControlFlow, EventLoop},
        platform::web::{EventLoopExtWebSys, WindowAttributesExtWebSys},
        window::{Window, WindowId},
    };

    type ClipboardCallback = Closure<dyn FnMut(web_sys::ClipboardEvent)>;

    #[derive(Default)]
    struct SharedState {
        window: Option<Arc<Window>>,
        renderer: Option<SurfaceRenderer>,
        initializing_renderer: bool,
        shell: Option<super::shell::Shell>,
        clipboard: Vec<(String, ClipboardCallback)>,
    }

    #[derive(Default)]
    struct WebApp {
        shared: Rc<RefCell<SharedState>>,
    }

    // Winit observes CSS size; WebGPU also needs the backing bitmap in physical pixels.
    // Guard writes: assigning either attribute clears the canvas, even if unchanged.
    fn sync_canvas(window: &Window, size: winit::dpi::PhysicalSize<u32>) {
        if let Some(canvas) = winit::platform::web::WindowExtWebSys::canvas(window) {
            if canvas.width() != size.width {
                canvas.set_width(size.width);
            }
            if canvas.height() != size.height {
                canvas.set_height(size.height);
            }
        }
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
            sync_canvas(&window, size);
            let shared = Rc::clone(&self.shared);

            spawn_local(async move {
                let result =
                    SurfaceRenderer::new(window.clone(), SurfaceSize::new(size.width, size.height))
                        .await;

                let initialized = {
                    let mut state = shared.borrow_mut();
                    state.initializing_renderer = false;
                    match result {
                        Ok(mut renderer) => {
                            // CSS/monitor resize can arrive while adapter/device creation awaits.
                            let latest = window.inner_size();
                            sync_canvas(&window, latest);
                            if let Err(error) =
                                renderer.resize(SurfaceSize::new(latest.width, latest.height))
                            {
                                log_error(&format!("initial canvas resize: {error}"));
                                false
                            } else {
                                if let Some(shell) = state.shell.as_mut() {
                                    shell.apply_gpu(&mut renderer);
                                }
                                state.renderer = Some(renderer);
                                true
                            }
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
            window.set_ime_allowed(true);
            match super::shell::Shell::new() {
                Ok(shell) => self.shared.borrow_mut().shell = Some(shell),
                Err(error) => {
                    log_error(&error);
                    event_loop.exit();
                    return;
                }
            }
            self.shared.borrow_mut().window = Some(window);
            if let Some(document) = web_sys::window().and_then(|w| w.document()) {
                for name in ["copy", "cut", "paste"] {
                    let weak = Rc::downgrade(&self.shared);
                    let callback = Closure::<dyn FnMut(web_sys::ClipboardEvent)>::new(
                        move |event: web_sys::ClipboardEvent| {
                            let Some(shared) = weak.upgrade() else {
                                return;
                            };
                            let mut state = shared.borrow_mut();
                            let Some(shell) = state.shell.as_mut() else {
                                return;
                            };
                            if !shell.panel.wants_keyboard_input() {
                                return;
                            }
                            let Some(data) = event.clipboard_data() else {
                                return;
                            };
                            if event.type_() == "paste" {
                                if let Ok(text) = data.get_data("text/plain") {
                                    shell.panel.paste(text);
                                    event.prevent_default();
                                }
                            } else {
                                shell.panel.clipboard_event(event.type_() == "cut");
                                if let Err(error) = shell.panel.render(now()) {
                                    log_error(&error);
                                    return;
                                }
                                if let Some(text) = shell.panel.take_copied_text()
                                    && data.set_data("text/plain", &text).is_ok()
                                {
                                    event.prevent_default();
                                }
                            }
                            if let Some(window) = &state.window {
                                window.request_redraw();
                            }
                        },
                    );
                    if document
                        .add_event_listener_with_callback(name, callback.as_ref().unchecked_ref())
                        .is_ok()
                    {
                        self.shared
                            .borrow_mut()
                            .clipboard
                            .push((name.into(), callback));
                    }
                }
            }
            self.begin_renderer_init();
        }

        #[expect(
            clippy::too_many_lines,
            reason = "one winit dispatch keeps resize, presentation and recovery ordering reviewable"
        )]
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

            if let Some(shell) = self.shared.borrow_mut().shell.as_mut() {
                shell.input(&window, &event);
            }
            match event {
                WindowEvent::CloseRequested => event_loop.exit(),
                WindowEvent::Resized(size) => {
                    sync_canvas(&window, size);
                    if let Some(renderer) = self.shared.borrow_mut().renderer.as_mut() {
                        if let Err(error) =
                            renderer.resize(SurfaceSize::new(size.width, size.height))
                        {
                            log_error(&format!("canvas resize: {error}"));
                        }
                        if size.width != 0 && size.height != 0 {
                            window.request_redraw();
                        }
                    }
                }
                WindowEvent::RedrawRequested => {
                    let result = {
                        let mut state = self.shared.borrow_mut();
                        let SharedState {
                            renderer, shell, ..
                        } = &mut *state;
                        renderer.as_mut().map(|renderer| {
                            if let Some(shell) = shell.as_mut() {
                                if let Err(error) = shell.update(&window, renderer, now()) {
                                    log_error(&error);
                                }
                                if let Some(rect) = shell.panel.ime_cursor() {
                                    let scale = window.scale_factor();
                                    let chrome = f64::from(
                                        rustclient_shell_ui::SidebarMetrics::physical(
                                            f64::from(shell.width()),
                                            scale,
                                        )
                                        .min(window.inner_size().width),
                                    );
                                    let x = f64::from(window.inner_size().width) - chrome
                                        + f64::from(rect[0]) * scale;
                                    window.set_ime_cursor_area(
                                        winit::dpi::PhysicalPosition::new(
                                            x,
                                            f64::from(rect[1]) * scale,
                                        ),
                                        winit::dpi::PhysicalSize::new(
                                            f64::from(rect[2]) * scale,
                                            f64::from(rect[3]) * scale,
                                        ),
                                    );
                                }
                                if let Some(canvas) =
                                    winit::platform::web::WindowExtWebSys::canvas(window.as_ref())
                                {
                                    let _ = canvas.set_attribute(
                                        "data-shell-passes",
                                        &shell.panel.stats().passes.to_string(),
                                    );
                                    let paint_stats = renderer.egui_shell_stats();
                                    let _ = canvas.set_attribute(
                                        "data-shell-paints",
                                        &paint_stats.paints.to_string(),
                                    );
                                    let _ = canvas.set_attribute(
                                        "data-shell-uploads",
                                        &(paint_stats.geometry_uploads
                                            + paint_stats.texture_uploads)
                                            .to_string(),
                                    );
                                    let _ = canvas.set_attribute(
                                        "data-shell-width",
                                        &shell.width().to_string(),
                                    );
                                }
                            }
                            renderer
                                .render_clear(ClearColor::BOOTSTRAP, || window.pre_present_notify())
                        })
                    };

                    if let Some(canvas) =
                        winit::platform::web::WindowExtWebSys::canvas(window.as_ref())
                    {
                        let _ = canvas.set_attribute("data-frame-outcome", &format!("{result:?}"));
                    }
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
                _ => {
                    window.request_redraw();
                }
            }
        }
        fn new_events(&mut self, _event_loop: &ActiveEventLoop, cause: winit::event::StartCause) {
            if matches!(cause, winit::event::StartCause::ResumeTimeReached { .. })
                && let Some(window) = &self.shared.borrow().window
            {
                window.request_redraw();
            }
        }
        fn about_to_wait(&mut self, event_loop: &ActiveEventLoop) {
            let state = self.shared.borrow();
            event_loop.set_control_flow(
                state
                    .shell
                    .as_ref()
                    .and_then(|s| s.panel.repaint_in(now()))
                    .map_or(ControlFlow::Wait, ControlFlow::wait_duration),
            );
        }
        fn exiting(&mut self, _event_loop: &ActiveEventLoop) {
            if let Some(document) = web_sys::window().and_then(|w| w.document()) {
                for (name, callback) in self.shared.borrow_mut().clipboard.drain(..) {
                    let _ = document.remove_event_listener_with_callback(
                        &name,
                        callback.as_ref().unchecked_ref(),
                    );
                }
            }
        }
    }

    fn log_error(message: &str) {
        web_sys::console::error_1(&JsValue::from_str(message));
    }

    /// Start the browser window and shared sidebar adapter.
    /// # Errors
    /// Reports failure to construct the browser event loop.
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
