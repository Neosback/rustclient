//! Revision-specific widget type and alignment operands.
use crate::CacheProfile;
use rustclient_model::{PositionMode, SizeMode, WidgetBody};
type Result<T> = std::result::Result<T, String>;
impl CacheProfile {
    /// The reference caps the drawable canvas independently of the host window.
    #[must_use]
    pub fn window_metrics(
        self,
        mode: rustclient_model::WindowMode,
        available: rustclient_model::CanvasSize,
    ) -> rustclient_model::WindowMetrics {
        use rustclient_model::{CanvasSize, WindowMetrics, WindowMode};
        let available = CanvasSize {
            width: available.width.clamp(765, 16_384),
            height: available.height.clamp(503, 16_384),
        };
        let cap = match mode {
            WindowMode::Fixed => [765, 503],
            WindowMode::Resizable => [7680, 2160],
        };
        WindowMetrics {
            mode,
            available,
            canvas: CanvasSize {
                width: available.width.min(cap[0]),
                height: available.height.min(cap[1]),
            },
        }
    }
    /// # Errors
    /// Rejects values outside the reference's two supported window modes.
    pub fn script_window_mode(self, value: i32) -> Result<rustclient_model::WindowMode> {
        match value {
            1 => Ok(rustclient_model::WindowMode::Fixed),
            2 => Ok(rustclient_model::WindowMode::Resizable),
            _ => Err("invalid window mode".into()),
        }
    }
    #[must_use]
    pub const fn script_window_mode_value(self, mode: rustclient_model::WindowMode) -> i32 {
        match mode {
            rustclient_model::WindowMode::Fixed => 1,
            rustclient_model::WindowMode::Resizable => 2,
        }
    }
    /// # Errors
    /// Rejects unsupported size-alignment operands.
    pub fn script_widget_size(self, n: i32) -> Result<SizeMode> {
        match n {
            0 => Ok(SizeMode::Absolute),
            1 => Ok(SizeMode::ParentMinus),
            2 => Ok(SizeMode::Proportional),
            4 => Ok(SizeMode::Aspect),
            _ => Err("unsupported widget size alignment".into()),
        }
    }
    /// # Errors
    /// Rejects unsupported position-alignment operands.
    pub fn script_widget_position(self, n: i32) -> Result<PositionMode> {
        match n {
            0 => Ok(PositionMode::Start),
            1 => Ok(PositionMode::Center),
            2 => Ok(PositionMode::End),
            3 => Ok(PositionMode::ProportionalStart),
            4 => Ok(PositionMode::ProportionalCenter),
            5 => Ok(PositionMode::ProportionalEnd),
            _ => Err("unsupported widget position alignment".into()),
        }
    }
    /// # Errors
    /// Rejects unsupported dynamic widget kinds.
    pub fn script_widget_body(self, kind: i32) -> Result<WidgetBody> {
        match kind {
            0 => Ok(WidgetBody::Container {
                scroll_size: [0; 2],
                no_click_through: false,
            }),
            3 => Ok(WidgetBody::Rectangle {
                color: 0,
                filled: false,
                transparency: 0,
            }),
            4 => Ok(WidgetBody::Text {
                font: None,
                text: String::new(),
                line_height: 0,
                alignment: [0; 2],
                shadowed: false,
                color: 0,
            }),
            5 => Ok(WidgetBody::Sprite {
                sprite: None,
                angle: 0,
                tiled: false,
                transparency: 0,
                outline: 0,
                shadow: 0,
                flip: [false; 2],
            }),
            6 => Ok(WidgetBody::Model {
                model: None,
                offset: [0; 2],
                angles: [0; 3],
                zoom: 100,
                sequence: None,
                orthographic: false,
                sizing: [0; 3],
            }),
            9 => Ok(WidgetBody::Line {
                width: 1,
                color: 0,
                reversed: false,
            }),
            _ => Err(format!("unsupported dynamic widget type {kind}")),
        }
    }
}
