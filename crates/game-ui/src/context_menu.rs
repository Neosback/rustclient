//! Disposable action menu; selections carry captured identities for revalidation.
use crate::{Rect, UiFrame};
use rustclient_cache::TitleAssets;
use rustclient_model::WorldAction;
pub struct ContextMenu<A: Copy = WorldAction> {
    bounds: Option<Rect>,
    entries: Vec<(String, Option<A>)>,
    selected: Option<usize>,
    canvas: Rect,
}
impl<A: Copy> Default for ContextMenu<A> {
    fn default() -> Self {
        Self {
            bounds: None,
            entries: Vec::new(),
            selected: None,
            canvas: Rect::new(0., 0., 765., 503.),
        }
    }
}
impl<A: Copy> ContextMenu<A> {
    #[must_use]
    pub const fn is_open(&self) -> bool {
        self.bounds.is_some()
    }
    pub fn close(&mut self) {
        self.bounds = None;
        self.entries.clear();
        self.selected = None;
    }
    #[expect(
        clippy::cast_precision_loss,
        reason = "bounded menu entries and logical font widths"
    )]
    pub fn open(
        &mut self,
        x: f32,
        y: f32,
        entries: Vec<(String, A)>,
        assets: &TitleAssets,
        canvas: rustclient_model::CanvasSize,
    ) {
        self.close();
        if entries.is_empty() || !x.is_finite() || !y.is_finite() {
            return;
        }
        self.entries.extend(
            entries
                .into_iter()
                .take(32)
                .map(|(label, action)| (label, Some(action))),
        );
        self.entries.push(("Cancel".into(), None));
        let font = &assets.fonts[2]; // b12_full
        let width = (self
            .entries
            .iter()
            .map(|(label, _)| crate::widget_text::width(label, font))
            .max()
            .unwrap_or(0)
            .max(font.width("Choose Option"))
            + 8) as f32;
        self.canvas = Rect::new(
            0.,
            0.,
            canvas.width.max(1) as f32,
            canvas.height.max(1) as f32,
        );
        let width = width.min(self.canvas.width);
        let height = (22. + self.entries.len() as f32 * 15.).min(self.canvas.height);
        self.bounds = Some(Rect::new(
            (x - width / 2.).clamp(0., self.canvas.width - width),
            y.clamp(0., self.canvas.height - height),
            width,
            height,
        ));
        self.selected = self.entry_at(x, y);
    }
    pub fn pointer(&mut self, x: f32, y: f32) {
        self.selected = self.entry_at(x, y);
    }
    #[expect(clippy::cast_precision_loss, reason = "bounded menu index")]
    fn entry_at(&self, x: f32, y: f32) -> Option<usize> {
        let bounds = self.bounds?;
        if !bounds.contains(x, y) {
            return None;
        }
        self.entries.iter().enumerate().find_map(|(i, _)| {
            let baseline = bounds.y + 31. + i as f32 * 15.;
            (x > bounds.x && x < bounds.x + bounds.width && y > baseline - 13. && y < baseline + 3.)
                .then_some(i)
        })
    }
    pub fn navigate(&mut self, delta: i32) {
        if self.entries.is_empty() {
            return;
        }
        let count = i32::try_from(self.entries.len()).unwrap_or(1);
        let next = self.selected.map_or_else(
            || if delta < 0 { count - 1 } else { 0 },
            |selected| (i32::try_from(selected).unwrap_or(0) + delta.signum()).rem_euclid(count),
        );
        self.selected = usize::try_from(next).ok();
    }
    pub fn activate(&mut self) -> Option<A> {
        let action = self
            .selected
            .and_then(|i| self.entries.get(i))
            .and_then(|(_, action)| *action);
        self.close();
        action
    }
    pub fn click(&mut self, x: f32, y: f32) -> Option<A> {
        self.selected = self.entry_at(x, y);
        self.activate()
    }
    #[expect(
        clippy::cast_possible_truncation,
        reason = "bounded logical popup/font geometry"
    )]
    pub fn project(&self, assets: &TitleAssets, frame: &mut UiFrame) {
        let Some(bounds) = self.bounds else {
            return;
        };
        let clip = self.canvas;
        frame.fill_rect(bounds, [93, 84, 71, 255], clip);
        frame.fill_rect(
            Rect::new(bounds.x + 1., bounds.y + 1., bounds.width - 2., 16.),
            [0, 0, 0, 255],
            clip,
        );
        let body = Rect::new(
            bounds.x + 1.,
            bounds.y + 18.,
            bounds.width - 2.,
            bounds.height - 19.,
        );
        for border in [
            Rect::new(body.x, body.y, body.width, 1.),
            Rect::new(body.x, body.y + body.height - 1., body.width, 1.),
            Rect::new(body.x, body.y, 1., body.height),
            Rect::new(body.x + body.width - 1., body.y, 1., body.height),
        ] {
            frame.fill_rect(border, [0, 0, 0, 255], clip);
        }
        crate::presentation::text(
            frame,
            &assets.fonts[2],
            2,
            "Choose Option",
            bounds.x as i32 + 3,
            bounds.y as i32 + 14,
            [93, 84, 71, 255],
            bounds,
        );
        for (i, (label, _)) in self.entries.iter().enumerate() {
            crate::widget_text::menu_line(
                frame,
                assets,
                label,
                [
                    bounds.x as i32 + 3,
                    bounds.y as i32 + 31 + i32::try_from(i).unwrap_or(0) * 15,
                ],
                if self.selected == Some(i) {
                    [255, 255, 0, 255]
                } else {
                    [255; 4]
                },
                bounds,
            );
        }
    }
}
