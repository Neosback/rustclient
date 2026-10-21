#![forbid(unsafe_code)]

use std::sync::Arc;

use rustclient_renderer::{ClearColor, FrameOutcome, SurfaceRenderer, SurfaceSize};
use winit::{
    application::ApplicationHandler,
    event::WindowEvent,
    event_loop::{ActiveEventLoop, ControlFlow, EventLoop},
    window::{Window, WindowId},
};

#[derive(Default)]
struct NativeApp {
    window: Option<Arc<Window>>,
    renderer: Option<SurfaceRenderer>,
}

impl NativeApp {
    fn create_renderer(&mut self, event_loop: &ActiveEventLoop) {
        let Some(window) = self.window.clone() else {
            return;
        };
        let size = window.inner_size();
        match pollster::block_on(SurfaceRenderer::new(
            window.clone(),
            SurfaceSize::new(size.width, size.height),
        )) {
            Ok(renderer) => {
                self.renderer = Some(renderer);
                window.request_redraw();
            }
            Err(error) => {
                eprintln!("failed to initialize renderer: {error}");
                event_loop.exit();
            }
        }
    }
}

impl ApplicationHandler for NativeApp {
    fn resumed(&mut self, event_loop: &ActiveEventLoop) {
        if self.window.is_some() {
            return;
        }

        let attributes = Window::default_attributes().with_title("RustClient");
        let window = match event_loop.create_window(attributes) {
            Ok(window) => Arc::new(window),
            Err(error) => {
                eprintln!("failed to create native window: {error}");
                event_loop.exit();
                return;
            }
        };
        self.window = Some(window);
        self.create_renderer(event_loop);
    }

    fn window_event(
        &mut self,
        event_loop: &ActiveEventLoop,
        window_id: WindowId,
        event: WindowEvent,
    ) {
        let Some(window) = self.window.clone() else {
            return;
        };
        if window.id() != window_id {
            return;
        }

        match event {
            WindowEvent::CloseRequested => event_loop.exit(),
            WindowEvent::Resized(size) => {
                if let Some(renderer) = self.renderer.as_mut() {
                    renderer.resize(SurfaceSize::new(size.width, size.height));
                    if size.width != 0 && size.height != 0 {
                        window.request_redraw();
                    }
                }
            }
            WindowEvent::RedrawRequested => {
                let result = self.renderer.as_mut().map(|renderer| {
                    renderer.render_clear(ClearColor::BOOTSTRAP, || window.pre_present_notify())
                });

                match result {
                    Some(Ok(FrameOutcome::Reconfigured)) => window.request_redraw(),
                    Some(Ok(FrameOutcome::RecreateRequired)) => self.create_renderer(event_loop),
                    Some(Ok(
                        FrameOutcome::Presented | FrameOutcome::Suspended | FrameOutcome::Skipped,
                    ))
                    | None => {}
                    Some(Err(error)) => {
                        eprintln!("render failure: {error}");
                        event_loop.exit();
                    }
                }
            }
            _ => {}
        }
    }
}

fn main() {
    let event_loop = EventLoop::new().expect("create native event loop");
    event_loop.set_control_flow(ControlFlow::Wait);

    let mut app = NativeApp::default();
    event_loop.run_app(&mut app).expect("run native client");
}
