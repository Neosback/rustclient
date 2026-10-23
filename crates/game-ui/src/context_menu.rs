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
        reason = "at most six menu entries and bounded logical font widths"
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
        let font = &assets.fonts[1];
        let width = (self
            .entries
            .iter()
            .map(|(label, _)| font.width(label))
            .max()
            .unwrap_or(0)
            .max(font.width("Choose Option"))
            + 10) as f32;
        self.canvas = Rect::new(
            0.,
            0.,
            canvas.width.max(1) as f32,
            canvas.height.max(1) as f32,
        );
        let width = width.max(80.).min(self.canvas.width);
        let height = (22. + self.entries.len() as f32 * 16.).min(self.canvas.height);
        self.bounds = Some(Rect::new(
            x.clamp(0., self.canvas.width - width),
            y.clamp(0., self.canvas.height - height),
            width,
            height,
        ));
        self.selected = Some(0);
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
            Rect::new(
                bounds.x + 1.,
                bounds.y + 22. + i as f32 * 16.,
                bounds.width - 2.,
                16.,
            )
            .contains(x, y)
            .then_some(i)
        })
    }
    pub fn navigate(&mut self, delta: i32) {
        if self.entries.is_empty() {
            return;
        }
        let count = i32::try_from(self.entries.len()).unwrap_or(1);
        let next = (i32::try_from(self.selected.unwrap_or(0)).unwrap_or(0) + delta.signum())
            .rem_euclid(count);
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
        frame.fill_rect(bounds, [0, 0, 0, 255], clip);
        frame.fill_rect(
            Rect::new(
                bounds.x + 1.,
                bounds.y + 1.,
                bounds.width - 2.,
                bounds.height - 2.,
            ),
            [93, 84, 71, 255],
            clip,
        );
        frame.fill_rect(
            Rect::new(bounds.x + 1., bounds.y + 1., bounds.width - 2., 20.),
            [0, 0, 0, 255],
            clip,
        );
        crate::presentation::text(
            frame,
            &assets.fonts[1],
            1,
            "Choose Option",
            bounds.x as i32 + 4,
            bounds.y as i32 + 16,
            [93, 84, 71, 255],
            bounds,
        );
        for (i, (label, _)) in self.entries.iter().enumerate() {
            crate::presentation::text(
                frame,
                &assets.fonts[1],
                1,
                label,
                bounds.x as i32 + 4,
                bounds.y as i32 + 35 + i32::try_from(i).unwrap_or(0) * 16,
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
