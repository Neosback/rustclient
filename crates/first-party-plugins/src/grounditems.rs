//! Port of the text overlay of `net.runelite.client.plugins.grounditems`
//! (`GroundItemsPlugin`, `GroundItemsOverlay`, `ItemList`, `ItemThreshold`; BSD-2, see `NOTICE`).
//!
//! Each ground item within 2500 local units of the player is collected per tile and item id with
//! quantities summed (`collectedGroundItems`). It is coloured by the highlight list, the hidden
//! list or the value tiers, filtered by ownership and the hide-under-value rule, and labelled
//! `name (quantity) (HA: price gp)` 20 units above the ground. Labels on one tile stack upwards
//! 15 pixels apart.
//!
//! Not ported: Grand Exchange prices (they need the price service, so the GE value is 0 and
//! only high alchemy values show), menu recolouring and deprioritising, tile highlights, the
//! hotkey with its show-all and highlight/hide boxes, notifications, loot beams, despawn timers
//! and per-item colours.

use crate::NativePlugin;
use rustclient_plugin_api::{
    GroundItemSnapshot, LayeredOverlay, OverlayCommand, OverlayLayer, PluginOutput, WorldPoint,
};
use rustclient_plugin_host::{PluginCall, PluginConfig, PluginFault};
use std::fmt::Write as _;

pub(crate) struct GroundItems;

/// `GroundItemsOverlay.MAX_DISTANCE`, local units.
const MAX_DISTANCE: i64 = 2500;
/// `GroundItemsOverlay.OFFSET_Z`.
const OFFSET_Z: i32 = 20;
/// `GroundItemsOverlay.STRING_GAP`.
const STRING_GAP: i32 = 15;
/// `ItemID.COINS_995`: never shows a price.
const COINS: u32 = 995;
/// `VarbitID.IRONMAN`: non-zero for every restricted account type.
const IRONMAN: u32 = 1777;

/// `ItemThreshold`: a name, optionally with `*` wildcards, and a `<n` or `>n` quantity rule.
#[derive(Debug, Clone, PartialEq, Eq)]
struct Threshold {
    name: String,
    quantity: u32,
    less_than: bool,
    wildcard: bool,
}
impl Threshold {
    /// `ItemThreshold.fromName`.
    fn parse(entry: &str) -> Option<Self> {
        if entry.is_empty() {
            return None;
        }
        let mut name = entry;
        let (mut quantity, mut less_than) = (0, false);
        if let Some(at) = entry
            .char_indices()
            .rev()
            .find(|(_, c)| !c.is_ascii_digit() && !c.is_whitespace())
            .map(|(at, _)| at)
        {
            let operator = &entry[at..=at];
            if (operator == "<" || operator == ">") && at + 1 < entry.len() {
                match entry[at + 1..].trim().parse::<u32>() {
                    Ok(value) => {
                        quantity = value;
                        less_than = operator == "<";
                    }
                    Err(_) => quantity = 0,
                }
                name = &entry[..at];
            }
        }
        Some(Self {
            name: name.trim().to_owned(),
            quantity,
            less_than,
            wildcard: entry.contains('*'),
        })
    }
    fn holds(&self, quantity: u32) -> bool {
        if self.less_than {
            quantity < self.quantity
        } else {
            quantity > self.quantity
        }
    }
}

#[derive(Clone, Copy, PartialEq, Eq)]
enum Match {
    None,
    Exact,
    Wildcard,
}

/// `ItemList.matches`: an exact (case-insensitive) name first, then wildcard patterns.
fn matches(list: &[Threshold], name: &str, quantity: u32) -> Match {
    if list
        .iter()
        .any(|t| !t.wildcard && t.name.eq_ignore_ascii_case(name) && t.holds(quantity))
    {
        return Match::Exact;
    }
    if list
        .iter()
        .any(|t| t.wildcard && wildcard(&t.name, name) && t.holds(quantity))
    {
        return Match::Wildcard;
    }
    Match::None
}

/// `WildcardMatcher.matches`: `*` matches any run of characters, case-insensitively.
fn wildcard(pattern: &str, text: &str) -> bool {
    let (pattern, text) = (pattern.to_lowercase(), text.to_lowercase());
    let mut parts = pattern.split('*');
    let first = parts.next().unwrap_or_default();
    let Some(mut rest) = text.strip_prefix(first) else {
        return false;
    };
    let parts: Vec<&str> = parts.collect();
    for (index, part) in parts.iter().enumerate() {
        if index + 1 == parts.len() {
            return rest.ends_with(part);
        }
        match rest.find(part) {
            Some(at) => rest = &rest[at + part.len()..],
            None => return false,
        }
    }
    rest.is_empty()
}

/// `Text.fromCSV`.
fn list(text: &str) -> Vec<Threshold> {
    text.split(',')
        .map(str::trim)
        .filter_map(Threshold::parse)
        .collect()
}

/// `QuantityFormatter.quantityToStackSize`.
#[expect(
    clippy::cast_precision_loss,
    reason = "stack sizes and prices stay far below 2^52"
)]
fn stack_size(quantity: i64) -> String {
    if quantity < 0 {
        return format!("-{}", stack_size(quantity.saturating_neg()));
    }
    if quantity < 10_000 {
        return grouped(quantity);
    }
    let (suffix, divide) = [("B", 1e9), ("M", 1e6), ("K", 1e3)]
        .into_iter()
        .find(|(_, divide)| quantity as f64 / divide >= 1.)
        .unwrap_or(("", 1.));
    // `NumberFormat` keeps up to three fraction digits, rounding half even; only four characters
    // are kept and a trailing point is dropped.
    let mut text = format!("{:.3}", quantity as f64 / divide);
    while text.contains('.') && (text.ends_with('0') || text.ends_with('.')) {
        text.pop();
    }
    let mut short: String = text.chars().take(4).collect();
    if short.ends_with('.') {
        short.pop();
    }
    short + suffix
}
fn grouped(value: i64) -> String {
    let digits = value.to_string();
    let mut out = String::with_capacity(digits.len() + digits.len() / 3);
    for (index, ch) in digits.chars().enumerate() {
        if index > 0 && (digits.len() - index).is_multiple_of(3) {
            out.push(',');
        }
        out.push(ch);
    }
    out
}

/// One collected label.
struct Collected<'a> {
    first: &'a GroundItemSnapshot,
    quantity: u32,
}

/// What the overlay draws for one collected item: its colour, or `None` when it is not drawn.
fn label_colour(
    config: &PluginConfig,
    highlighted: &[Threshold],
    hidden: &[Threshold],
    name: &str,
    quantity: u32,
    ha_price: i64,
) -> Option<u32> {
    let explicit = match (
        matches(highlighted, name, quantity),
        matches(hidden, name, quantity),
    ) {
        (Match::Exact, _) => Some(true),
        (_, Match::Exact) => Some(false),
        (Match::Wildcard, _) => Some(true),
        (_, Match::Wildcard) => Some(false),
        _ => None,
    };
    // GE prices are not available, so the GE value is 0 (`getGePrice` without a price).
    let ge_price = 0_i64;
    let value = match config.choice("highlightValueCalculation") {
        "GE" => ge_price,
        "HA" => ha_price,
        _ => ge_price.max(ha_price),
    };
    let tier = [
        ("insaneValuePrice", "insaneValueColor"),
        ("highValuePrice", "highValueColor"),
        ("mediumValuePrice", "mediumValueColor"),
        ("lowValuePrice", "lowValueColor"),
    ]
    .into_iter()
    .find(|(price, _)| {
        let threshold = i64::from(config.integer(price));
        threshold > 0 && value > threshold
    })
    .map(|(_, colour)| config.color(colour));
    let highlight = match explicit {
        Some(true) => Some(config.color("highlightedColor")),
        Some(false) => None,
        None => tier,
    };
    let under = i64::from(config.integer("hideUnderValue"));
    // An untradeable item has no GE price, so only `dontHideUntradeables` off lets it hide.
    let can_hide = ge_price > 0 || !config.boolean("dontHideUntradeables");
    let is_hidden = explicit == Some(false)
        || (explicit != Some(true) && can_hide && ge_price < under && ha_price < under);
    if highlight.is_none() {
        if is_hidden || config.boolean("showHighlightedOnly") {
            return None;
        }
        return Some(config.color("defaultColor"));
    }
    highlight
}

impl NativePlugin for GroundItems {
    fn subscribes_game_events(&self) -> bool {
        false
    }
    fn output(&mut self, call: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
        let mut output = PluginOutput::default();
        let config = call.config();
        if matches!(config.choice("itemHighlightMode"), "MENU" | "NONE") {
            return Ok(output);
        }
        let Some(player) = call.local_player().ok().flatten().map(|p| p.actor.world) else {
            return Ok(output);
        };
        let account_type = call.varbit(IRONMAN).ok().flatten().unwrap_or(0);
        let items = call.ground_items().unwrap_or(&[]);
        let (highlighted, hidden) = (
            list(config.text("highlightedItems")),
            list(config.text("hiddenItems")),
        );
        // `collectedGroundItems`: one entry per tile and item id, quantities summed.
        let mut collected: Vec<Collected<'_>> = Vec::new();
        for item in items.iter().filter(|item| item.tile.plane == player.plane) {
            match collected
                .iter_mut()
                .find(|c| c.first.tile == item.tile && c.first.id == item.id)
            {
                Some(c) => c.quantity = c.quantity.saturating_add(item.quantity),
                None => collected.push(Collected {
                    first: item,
                    quantity: item.quantity,
                }),
            }
        }
        let mut stacked: Vec<(WorldPoint, i32)> = Vec::new();
        for item in collected {
            let distance = {
                let dx = i64::from(item.first.tile.x - player.x) * 128;
                let dy = i64::from(item.first.tile.y - player.y) * 128;
                dx * dx + dy * dy
            };
            let visible_owner = match config.choice("ownershipFilterMode") {
                "DROPS" => matches!(item.first.ownership, 1 | 3),
                "TAKEABLE" => item.first.ownership != 2 || account_type == 0,
                _ => true,
            };
            if distance > MAX_DISTANCE * MAX_DISTANCE || !visible_owner {
                continue;
            }
            let name = item.first.name.as_deref().unwrap_or("null");
            // `ItemComposition.getHaPrice`: `(int) (price * 0.6f)`, per unit, times the stack.
            #[expect(
                clippy::cast_possible_truncation,
                clippy::cast_precision_loss,
                reason = "the reference multiplies in single precision and truncates to int"
            )]
            let ha_unit = (item.first.cost as f32 * 0.6) as i64;
            let ha_price = ha_unit * i64::from(item.quantity);
            let Some(argb) =
                label_colour(config, &highlighted, &hidden, name, item.quantity, ha_price)
            else {
                continue;
            };
            let mut text = name.to_owned();
            if item.quantity > 1 {
                let _ = write!(text, " ({})", stack_size(i64::from(item.quantity)));
            }
            if item.first.id != COINS
                && matches!(config.choice("priceDisplayMode"), "BOTH" | "HA")
                && ha_price > 0
            {
                if config.choice("priceDisplayMode") == "BOTH" {
                    let _ = write!(text, " (HA: {} gp)", stack_size(ha_price));
                } else {
                    let _ = write!(text, " ({} gp)", stack_size(ha_price));
                }
            }
            let offset = if let Some((_, count)) = stacked
                .iter_mut()
                .find(|(tile, _)| *tile == item.first.tile)
            {
                *count += 1;
                *count
            } else {
                stacked.push((item.first.tile, 0));
                0
            };
            output.overlay.push(LayeredOverlay {
                layer: OverlayLayer::AboveScene,
                command: OverlayCommand::TileText {
                    tile: item.first.tile,
                    text,
                    z_offset: OFFSET_Z,
                    y_offset: -STRING_GAP * offset,
                    argb,
                },
            });
        }
        Ok(output)
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn thresholds_parse_quantity_rules_and_wildcards_like_item_threshold() {
        assert_eq!(
            Threshold::parse("Coins > 1000"),
            Some(Threshold {
                name: "Coins".into(),
                quantity: 1000,
                less_than: false,
                wildcard: false
            })
        );
        let rune = Threshold::parse("*rune<50").unwrap();
        assert!(rune.less_than && rune.wildcard && rune.quantity == 50 && rune.name == "*rune");
        assert_eq!(Threshold::parse("Bones").unwrap().quantity, 0);
        let list = list("Bones, *rune<50, Dragon*");
        assert!(matches(&list, "bones", 1) == Match::Exact);
        assert!(matches(&list, "Fire rune", 10) == Match::Wildcard);
        assert!(matches(&list, "Fire rune", 60) == Match::None);
        assert!(matches(&list, "Dragon bones", 1) == Match::Wildcard);
        assert!(wildcard("*a*b*", "xaxxbx") && !wildcard("a*b", "acbd"));
    }

    #[test]
    fn stack_sizes_follow_quantity_formatter() {
        for (value, text) in [
            (1, "1"),
            (9_999, "9,999"),
            (10_000, "10K"),
            (12_345, "12.3K"),
            (100_000, "100K"),
            (1_234_567, "1.23M"),
            (2_147_483_647, "2.14B"),
        ] {
            assert_eq!(stack_size(value), text, "{value}");
        }
    }
}
