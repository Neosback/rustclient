//! Worker-friendly CPU asset preparation from a disposable list of visible dependencies.
use super::ClientScripts;
use rustclient_cache::{Cache, CacheProfile};
use rustclient_game_state::GameState;
use rustclient_game_ui::{UiImageId, widget_visuals::WidgetVisualAssets};
use rustclient_model::{FontId, ItemDefinitionId, SpriteId, WidgetBody};
use rustclient_platform::ReadOnlyBlobs;
use std::collections::BTreeSet;
#[derive(Debug, Clone, Default, PartialEq, Eq)]
pub struct WidgetAssetRequest {
    pub sprites: BTreeSet<SpriteId>,
    pub fonts: BTreeSet<FontId>,
    pub items: BTreeSet<ItemDefinitionId>,
}
impl<S: ReadOnlyBlobs> ClientScripts<S> {
    /// # Errors
    /// Rejects a missing definition/layout rather than guessing widget geometry.
    pub fn visual_request(&self, state: &GameState) -> Result<WidgetAssetRequest, String> {
        let tree = rustclient_game_ui::widgets::WidgetTree::build(
            state.interfaces(),
            &self.interfaces,
            rustclient_game_ui::widgets::WidgetBounds::canvas(state.interfaces()),
        )
        .map_err(|e| e.to_string())?;
        let mut request = WidgetAssetRequest::default();
        for node in tree.nodes {
            let w = rustclient_game_ui::widgets::resolve_widget(
                state.interfaces(),
                &self.interfaces,
                node.address,
            )
            .map_err(|e| e.to_string())?;
            match w.body {
                WidgetBody::Sprite {
                    sprite: Some(id), ..
                } => {
                    request.sprites.insert(id);
                }
                WidgetBody::Text { font: Some(id), .. } => {
                    request.fonts.insert(id);
                }
                _ => {}
            }
            if let Some((item, _)) = state
                .interfaces()
                .runtime()
                .get(&node.address)
                .and_then(|r| r.item)
            {
                request.items.insert(item);
            }
        }
        Ok(request)
    }
}
/// Decode immutable images off the UI thread. Missing assets remain explicit diagnostics.
#[must_use]
pub fn load_widget_visuals<S: ReadOnlyBlobs>(
    cache: &Cache<S>,
    profile: CacheProfile,
    request: &WidgetAssetRequest,
) -> WidgetVisualAssets {
    let mut assets = WidgetVisualAssets::default();
    for id in &request.sprites {
        match cache.sprite(id.get()) {
            Ok(Some(sprite)) => {
                assets.sprite_frames.insert(
                    *id,
                    rustclient_game_ui::widget_visuals::SpriteFrame {
                        canvas: sprite.canvas.map(u32::from),
                        offset: sprite.offset.map(u32::from),
                    },
                );
                assets
                    .images
                    .insert(UiImageId::Sprite(id.get()), sprite.image);
            }
            Ok(None) => assets.failures.push(format!("missing widget sprite {id}")),
            Err(e) => assets.failures.push(e.to_string()),
        }
    }
    match cache.named_sprites("cross") {
        Ok(frames) => {
            for (index, sprite) in frames.into_iter().take(8).enumerate() {
                if let Ok(index) = u8::try_from(index) {
                    assets.images.insert(UiImageId::Cross(index), sprite.image);
                }
            }
        }
        Err(e) => assets.failures.push(e.to_string()),
    }
    for id in &request.fonts {
        match profile.load_widget_font(cache, *id) {
            Ok(font) => {
                for (byte, glyph) in font.glyphs.iter().enumerate() {
                    if glyph.image.width() > 0
                        && glyph.image.height() > 0
                        && let Ok(byte) = u8::try_from(byte)
                    {
                        assets
                            .images
                            .insert(UiImageId::WidgetGlyph(*id, byte), glyph.image.clone());
                    }
                }
                assets.fonts.insert(*id, font);
            }
            Err(e) => assets.failures.push(e.to_string()),
        }
    }
    for id in &request.items {
        let result = (|| {
            let def = profile.load_item(cache, *id).map_err(|e| e.to_string())?;
            let model = profile
                .load_widget_model(cache, def.icon_model.ok_or("item has no inventory model")?)
                .map_err(|e| e.to_string())?;
            rustclient_scene::item_icon::item_icon(&def, &model).map_err(|e| e.to_string())
        })();
        match result {
            Ok(image) => {
                assets.images.insert(UiImageId::ItemIcon(*id), image);
            }
            Err(e) => assets.failures.push(format!("item {id}: {e}")),
        }
    }
    assets
}
