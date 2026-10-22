use crate::{
    Flames, Rect, TitleUi, UiFrame, UiImageId,
    controller::{BACK, CANCEL, HELP, HIDE, LEFT_WELCOME, LOGIN, REMEMBER, RIGHT_WELCOME},
};
use rustclient_cache::{BitmapFont, Sprite, TitleAssets, cp1252};
use rustclient_game_state::{GameState, LoginFailure, LoginStatus, TitleInformation, TitleScreen};

const CLIP: Rect = Rect::new(0., 0., 765., 503.);
const WHITE: [u8; 4] = [255; 4];
const YELLOW: [u8; 4] = [255, 255, 0, 255];

impl TitleUi {
    /// Rebuild a disposable frame from authoritative navigation and transient input.
    /// Password text is replaced with masks before any drawing command is created.
    pub fn project(
        &self,
        assets: &TitleAssets,
        screen: TitleScreen,
        cycle: u64,
        frame: &mut UiFrame,
    ) {
        title_chrome(assets, frame);
        match screen {
            TitleScreen::Welcome => {
                centered(
                    frame,
                    &assets.fonts[2],
                    2,
                    "Welcome to RuneScape",
                    382,
                    251,
                    YELLOW,
                );
                button(frame, assets, LEFT_WELCOME, "New User");
                button(frame, assets, RIGHT_WELCOME, "Existing User");
            }
            TitleScreen::Credentials(status) => {
                self.credentials(assets, status, cycle, frame);
            }
            TitleScreen::Information(info) => {
                let (heading, lines) = match info {
                    TitleInformation::NewUser if self.online() => (
                        "New account",
                        [
                            "Select Existing User to log in",
                            "with a new dev server username.",
                            "Account rules are server-owned.",
                        ],
                    ),
                    TitleInformation::Help if self.online() => (
                        "Login help",
                        [
                            "Enter your dev server credentials.",
                            "Cancel ends a pending connection.",
                            "Passwords are never saved here.",
                        ],
                    ),
                    TitleInformation::NewUser => (
                        "New User",
                        [
                            "This is an offline client preview.",
                            "Account creation requires a server.",
                            "No account service is configured.",
                        ],
                    ),
                    TitleInformation::Help => (
                        "Login help",
                        [
                            "Use any local username and password",
                            "to preview the form. Server login",
                            "will be added in a later milestone.",
                        ],
                    ),
                };
                centered(frame, &assets.fonts[2], 2, heading, 382, 211, YELLOW);
                for (i, line) in lines.into_iter().enumerate() {
                    centered(
                        frame,
                        &assets.fonts[1],
                        1,
                        line,
                        382,
                        241 + i32::try_from(i).unwrap_or(0) * 15,
                        WHITE,
                    );
                }
                button(frame, assets, BACK, "Back");
            }
        }
        self.title_decorations(assets, frame);
    }
    fn title_decorations(&self, assets: &TitleAssets, frame: &mut UiFrame) {
        // Rune flames cover title edges exactly as the classic title overlay.
        frame.image(UiImageId::Flames, Rect::new(-54., 9., 192., 256.), CLIP);
        frame.image(UiImageId::Flames, Rect::new(627., 9., 192., 256.), CLIP);
        sprite(frame, UiImageId::Muted, &assets.mute[1], 725., 463.);
        centered(
            frame,
            &assets.fonts[0],
            0,
            if self.online() {
                "Dev server"
            } else {
                "Offline"
            },
            50,
            478,
            [170, 170, 170, 255],
        );
    }
    /// Project the authenticated bootstrap from `GameState`, without a second session model.
    pub fn project_session(&self, assets: &TitleAssets, state: &GameState, frame: &mut UiFrame) {
        title_chrome(assets, frame);
        centered(
            frame,
            &assets.fonts[2],
            2,
            "Connected to OpenRune",
            382,
            208,
            YELLOW,
        );
        if let Some(player) = state.authenticated_player() {
            centered(
                frame,
                &assets.fonts[1],
                1,
                &format!("Authenticated player {}", player.player.get()),
                382,
                233,
                WHITE,
            );
        }
        let line = state.initial_placement().map_or_else(
            || "Waiting for initial placement...".into(),
            |p| {
                format!(
                    "Starting tile {}, {} - plane {}",
                    p.position.x,
                    p.position.y,
                    p.position.plane.get()
                )
            },
        );
        centered(frame, &assets.fonts[1], 1, &line, 382, 253, WHITE);
        centered(
            frame,
            &assets.fonts[0],
            0,
            "Loading cached spawn scene...",
            382,
            278,
            WHITE,
        );
        button(frame, assets, BACK, "Disconnect");
        self.title_decorations(assets, frame);
    }
    /// Project a compact HUD over the authenticated static scene preview.
    pub fn project_world(&self, assets: &TitleAssets, state: &GameState, frame: &mut UiFrame) {
        frame.quads.clear();
        frame.image(UiImageId::White, Rect::new(8., 8., 749., 54.), CLIP);
        if let Some(q) = frame.quads.last_mut() {
            q.tint = [12, 15, 20, 215];
        }
        text(
            frame,
            &assets.fonts[2],
            2,
            "OpenRune - static spawn preview",
            18,
            27,
            YELLOW,
            CLIP,
        );
        text(
            frame,
            &assets.fonts[0],
            0,
            "Right-drag / arrows: orbit  |  Wheel: zoom  |  Home: reset  |  Esc: disconnect",
            18,
            46,
            WHITE,
            CLIP,
        );
        frame.image(UiImageId::White, Rect::new(8., 463., 749., 32.), CLIP);
        if let Some(q) = frame.quads.last_mut() {
            q.tint = [12, 15, 20, 215];
        }
        let line = state.initial_placement().map_or_else(String::new, |p| {
            format!(
                "Spawn {},{} plane {}  |  Gold marker  |  Average texture colors; static objects",
                p.position.x,
                p.position.y,
                p.position.plane.get()
            )
        });
        text(frame, &assets.fonts[0], 0, &line, 18, 484, WHITE, CLIP);
        text(
            frame,
            &assets.fonts[1],
            1,
            "Disconnect",
            675,
            27,
            WHITE,
            CLIP,
        );
    }
    /// Uses the same centered logical transform as the world HUD.
    #[must_use]
    pub fn world_disconnect_hit(x: f32, y: f32) -> bool {
        Rect::new(667., 8., 90., 28.).contains(x, y)
    }
    /// Hit testing for the authenticated bootstrap's local Disconnect control.
    #[must_use]
    pub fn disconnect_hit(x: f32, y: f32) -> bool {
        BACK.contains(x, y)
    }

    fn credentials(
        &self,
        assets: &TitleAssets,
        status: LoginStatus,
        cycle: u64,
        frame: &mut UiFrame,
    ) {
        if status == LoginStatus::Connecting {
            centered(
                frame,
                &assets.fonts[2],
                2,
                "Connecting to server...",
                382,
                231,
                YELLOW,
            );
            centered(frame, &assets.fonts[1], 1, "Please wait", 382, 256, WHITE);
            button(frame, assets, CANCEL, "Cancel");
            return;
        }
        let lines = login_lines(status);
        for (i, line) in lines.into_iter().enumerate() {
            centered(
                frame,
                &assets.fonts[2],
                2,
                line,
                382,
                201 + i32::try_from(i).unwrap_or(0) * 15,
                YELLOW,
            );
        }
        self.fields(assets, cycle, frame);
        let prefs = self.preferences();
        checkbox(
            frame,
            assets,
            REMEMBER,
            prefs.remember_username,
            self.hovered(REMEMBER),
        );
        checkbox(frame, assets, HIDE, prefs.hide_username, self.hovered(HIDE));
        text(
            frame,
            &assets.fonts[0],
            0,
            "Remember username",
            287,
            290,
            YELLOW,
            CLIP,
        );
        text(
            frame,
            &assets.fonts[0],
            0,
            "Hide username",
            428,
            290,
            YELLOW,
            CLIP,
        );
        button(frame, assets, LOGIN, "Login");
        button(frame, assets, CANCEL, "Cancel");
        centered(
            frame,
            &assets.fonts[0],
            0,
            "Can't login? Click here.",
            382,
            357,
            if self.hovered(HELP) { YELLOW } else { WHITE },
        );
    }
    fn fields(&self, assets: &TitleAssets, cycle: u64, frame: &mut UiFrame) {
        text(frame, &assets.fonts[2], 2, "Login: ", 272, 253, WHITE, CLIP);
        let field_clip = Rect::new(312., 238., 200., 18.);
        let mut username = self.displayed_username();
        trim_left(&mut username, &assets.fonts[2], 190);
        text(
            frame,
            &assets.fonts[2],
            2,
            &username,
            312,
            253,
            WHITE,
            field_clip,
        );
        let password = self.masked_password();
        text(
            frame,
            &assets.fonts[2],
            2,
            "Password: ",
            274,
            268,
            WHITE,
            CLIP,
        );
        let password_x = 274 + assets.fonts[2].width("Password: ");
        text(
            frame,
            &assets.fonts[2],
            2,
            &password,
            password_x,
            268,
            WHITE,
            Rect::new(float(password_x), 254., float(512 - password_x), 18.),
        );
        if cycle % 40 < 20 {
            let (value, x, y) = if self.focused_username() {
                (&username, 312, 253)
            } else {
                (&password, password_x, 268)
            };
            text(
                frame,
                &assets.fonts[2],
                2,
                "|",
                x + assets.fonts[2].width(value),
                y,
                YELLOW,
                Rect::new(312., 238., 200., 36.),
            );
        }
    }
}
fn title_chrome(assets: &TitleAssets, frame: &mut UiFrame) {
    frame.quads.clear();
    frame.image(UiImageId::Background, Rect::new(0., 0., 382., 503.), CLIP);
    frame.image(UiImageId::Background, Rect::new(382., 0., 382., 503.), CLIP);
    if let Some(quad) = frame.quads.last_mut() {
        quad.uv = Rect::new(1., 0., -1., 1.);
    }
    sprite(
        frame,
        UiImageId::Logo,
        &assets.logo,
        382. - f32::from(assets.logo.canvas[0]) / 2.,
        18.,
    );
    sprite(frame, UiImageId::TitleBox, &assets.title_box, 202., 171.);
}

fn login_lines(status: LoginStatus) -> [&'static str; 3] {
    match status {
        LoginStatus::Connecting => ["Connecting to server...", "Please wait", ""],
        LoginStatus::Failed(failure) => match failure {
            LoginFailure::Connection => [
                "Could not connect to server.",
                "Check the server address and port.",
                "Please try again.",
            ],
            LoginFailure::Authentication => [
                "Login was rejected by the server.",
                "Check your account and password.",
                "See diagnostics for the reason.",
            ],
            LoginFailure::CacheMismatch => [
                "Cache does not match the server.",
                "Select the server LIVE cache.",
                "No credentials were submitted.",
            ],
            LoginFailure::Protocol => [
                "Unsupported server response.",
                "See diagnostics for the stage.",
                "Please try again.",
            ],
            LoginFailure::Disconnected => [
                "Disconnected from server.",
                "Your session has ended.",
                "Please log in again.",
            ],
        },
        LoginStatus::Prompt => ["", "Enter your username/email & password.", ""],
        LoginStatus::UsernameRequired => ["", "Please enter your username/email.", ""],
        LoginStatus::PasswordRequired => ["", "Please enter your password.", ""],
        LoginStatus::Offline => [
            "Offline preview",
            "Server login is not configured.",
            "No connection has been made.",
        ],
    }
}

fn sprite(frame: &mut UiFrame, id: UiImageId, sprite: &Sprite, x: f32, y: f32) {
    frame.image(
        id,
        Rect::new(
            x + f32::from(sprite.offset[0]),
            y + f32::from(sprite.offset[1]),
            f32::from(u16::try_from(sprite.image.width()).unwrap_or(0)),
            f32::from(u16::try_from(sprite.image.height()).unwrap_or(0)),
        ),
        CLIP,
    );
}
fn checkbox(frame: &mut UiFrame, assets: &TitleAssets, rect: Rect, checked: bool, hover: bool) {
    let state = u8::from(checked) + 2 * u8::from(hover);
    sprite(
        frame,
        UiImageId::Checkbox(state),
        &assets.checkboxes[usize::from(state)],
        rect.x,
        rect.y,
    );
}
fn button(frame: &mut UiFrame, assets: &TitleAssets, rect: Rect, label: &str) {
    sprite(frame, UiImageId::Button, &assets.button, rect.x, rect.y);
    centered(
        frame,
        &assets.fonts[2],
        2,
        label,
        coordinate(rect.x) + 73,
        coordinate(rect.y) + 25,
        WHITE,
    );
}
fn centered(
    frame: &mut UiFrame,
    font: &BitmapFont,
    id: u8,
    value: &str,
    x: i32,
    y: i32,
    color: [u8; 4],
) {
    text(
        frame,
        font,
        id,
        value,
        x - font.width(value) / 2,
        y,
        color,
        CLIP,
    );
}
#[expect(
    clippy::too_many_arguments,
    reason = "literal font projection parameters are explicit and independent"
)]
pub(crate) fn text(
    frame: &mut UiFrame,
    font: &BitmapFont,
    id: u8,
    value: &str,
    mut x: i32,
    baseline: i32,
    color: [u8; 4],
    clip: Rect,
) {
    for ch in value.chars() {
        let byte = cp1252(ch).unwrap_or(b'?');
        let glyph = &font.glyphs[usize::from(byte)];
        if glyph.image.width() > 0 && glyph.image.height() > 0 {
            let px = x + i32::from(glyph.offset[0]);
            let py = baseline - i32::from(font.ascent) + i32::from(glyph.offset[1]);
            let rect = Rect::new(
                float(px),
                float(py),
                float(i32::try_from(glyph.image.width()).unwrap_or(0)),
                float(i32::try_from(glyph.image.height()).unwrap_or(0)),
            );
            frame.image(
                UiImageId::Glyph(id, byte),
                Rect::new(rect.x + 1., rect.y + 1., rect.width, rect.height),
                clip,
            );
            if let Some(quad) = frame.quads.last_mut() {
                quad.tint = [0, 0, 0, 255];
            }
            frame.image(UiImageId::Glyph(id, byte), rect, clip);
            if let Some(quad) = frame.quads.last_mut() {
                quad.tint = color;
            }
        }
        x += i32::from(font.advances[usize::from(byte)]);
    }
}
fn trim_left(value: &mut String, font: &BitmapFont, max: i32) {
    while font.width(value) > max {
        if let Some(ch) = value.chars().next() {
            value.drain(..ch.len_utf8());
        } else {
            break;
        }
    }
}
#[expect(
    clippy::cast_precision_loss,
    reason = "title coordinates and glyph dimensions are bounded to a few thousand pixels"
)]
fn float(value: i32) -> f32 {
    value as f32
}
#[expect(
    clippy::cast_possible_truncation,
    reason = "button geometry uses integral bounded title coordinates"
)]
fn coordinate(value: f32) -> i32 {
    value as i32
}

// Assert that the dynamic upload shape remains an explicit part of the projection contract.
const _: u32 = Flames::WIDTH;
