//! Port of the built-in swaps of `net.runelite.client.plugins.menuentryswapper`
//! (`MenuEntrySwapperPlugin.setupSwaps` and `onPostMenuSort`; BSD-2, see `NOTICE`).
//!
//! On `PostMenuSort` the source walks the menu array bottom to top. For each entry whose
//! tag-free, lower-case option has an enabled swap whose target predicate matches, it finds the
//! top-most entry below it with the swapped option (exactly, or containing it for the
//! non-strict swaps) and the same target, and exchanges the two. The walk reads the array as it
//! is being mutated. This module replays that walk over the delivered entries and returns the
//! exchanges as [`MenuEdit::Swap`] directives.
//!
//! Not ported: custom left-click and shift-click swaps (they need the "Swap" submenu and per-id
//! configuration), Shift-dependent swaps (GE abort, shop buy/sell, shift-click stairs and the
//! three "Walk here" shift options), the hint-arrow NPC exemption, removing dead NPC entries,
//! bank and UI swaps. Only world menus reach the menu stage today, so item swaps such as
//! `wear -> rub` are carried in the table but have no menu to act on yet.

use crate::NativePlugin;
use rustclient_plugin_api::{
    EventInterest, GameEvent, GameEventKind, MenuEdit, MenuEntry, PluginOutput,
};
use rustclient_plugin_host::{PluginCall, PluginConfig, PluginFault};

#[derive(Default)]
pub(crate) struct MenuEntrySwapper {
    edits: Vec<MenuEdit>,
}

#[derive(Clone, Copy)]
enum Target {
    Any,
    Equals(&'static str),
    EndsWith(&'static str),
    OneOf(&'static [&'static str]),
}
impl Target {
    fn matches(self, target: &str) -> bool {
        match self {
            Self::Any => true,
            Self::Equals(value) => target == value,
            Self::EndsWith(value) => target.ends_with(value),
            Self::OneOf(values) => values.contains(&target),
        }
    }
}

struct Swap {
    option: &'static str,
    target: Target,
    swapped: &'static str,
    strict: bool,
    enabled: fn(&PluginConfig) -> bool,
}

const ESSENCE_MINE_NPCS: &[&str] = &[
    "aubury",
    "archmage sedridor",
    "wizard distentor",
    "wizard cromperty",
    "brimstail",
];
const TEMPOROSS_NPCS: &[&str] = &[
    "captain dudi",
    "captain pudi",
    "first mate deri",
    "first mate peri",
];
const PORTAL_NEXUS_LOCATIONS: &[&str] = &[
    "annakarl",
    "ape atoll dungeon",
    "ardougne",
    "barrows",
    "battlefront",
    "camelot",
    "carrallanger",
    "catherby",
    "cemetery",
    "draynor manor",
    "falador",
    "fenkenstrain's castle",
    "fishing guild",
    "ghorrock",
    "grand exchange",
    "great kourend",
    "harmony island",
    "kharyrll",
    "lumbridge",
    "arceuus library",
    "lunar isle",
    "marim",
    "mind altar",
    "salve graveyard",
    "seers' village",
    "senntisten",
    "troll stronghold",
    "varrock",
    "watchtower",
    "waterbirth island",
    "weiss",
    "west ardougne",
    "yanille",
];
const JEWELLERY_BOX_DESTINATIONS: &[&str] = &[
    "emir's arena",
    "castle wars",
    "ferox enclave",
    "fortis colosseum",
    "burthorpe",
    "barbarian outpost",
    "corporeal beast",
    "tears of guthix",
    "wintertodt camp",
    "warriors' guild",
    "champions' guild",
    "monastery",
    "ranging guild",
    "fishing guild",
    "mining guild",
    "crafting guild",
    "cooking guild",
    "woodcutting guild",
    "farming guild",
    "miscellania",
    "grand exchange",
    "falador park",
    "dondakan's rock",
    "edgeville",
    "karamja",
    "draynor village",
    "al kharid",
];

macro_rules! flag {
    ($key:literal) => {
        |config: &PluginConfig| config.boolean($key)
    };
}
macro_rules! mode {
    ($key:literal, $value:literal) => {
        |config: &PluginConfig| config.choice($key) == $value
    };
}
const fn strict(
    option: &'static str,
    target: Target,
    swapped: &'static str,
    enabled: fn(&PluginConfig) -> bool,
) -> Swap {
    Swap {
        option,
        target,
        swapped,
        strict: true,
        enabled,
    }
}
const fn contains(
    option: &'static str,
    swapped: &'static str,
    enabled: fn(&PluginConfig) -> bool,
) -> Swap {
    Swap {
        option,
        target: Target::Any,
        swapped,
        strict: false,
        enabled,
    }
}

/// `setupSwaps`, in source order. Several entries share an option; the first that succeeds for
/// an entry wins, as in the source's multimap walk. Jewellery box and portal nexus rows are
/// expanded from their source lists below.
const SWAPS: &[Swap] = &[
    strict(
        "talk-to",
        Target::Equals("mage of zamorak"),
        "teleport",
        flag!("swapAbyssTeleport"),
    ),
    strict("talk-to", Target::Any, "bank", flag!("swapBanker")),
    strict("talk-to", Target::Any, "exchange", flag!("swapExchange")),
    strict("talk-to", Target::Any, "help", flag!("swapHelp")),
    strict(
        "talk-to",
        Target::Any,
        "assignment",
        flag!("swapAssignment"),
    ),
    strict("talk-to", Target::Any, "pay", flag!("swapPay")),
    contains("talk-to", "pay (", flag!("swapPay")),
    strict("talk-to", Target::Any, "trade", flag!("swapTrade")),
    strict("talk-to", Target::Any, "trade-with", flag!("swapTrade")),
    strict("talk-to", Target::Any, "shop", flag!("swapTrade")),
    strict("talk-to", Target::Any, "travel", flag!("swapTravel")),
    strict("talk-to", Target::Any, "pay-fare", flag!("swapTravel")),
    strict("talk-to", Target::Any, "charter", flag!("swapTravel")),
    strict("talk-to", Target::Any, "take-boat", flag!("swapTravel")),
    strict("talk-to", Target::Any, "fly", flag!("swapTravel")),
    strict("talk-to", Target::Any, "jatizso", flag!("swapTravel")),
    strict("talk-to", Target::Any, "neitiznot", flag!("swapTravel")),
    strict("talk-to", Target::Any, "rellekka", flag!("swapTravel")),
    strict("talk-to", Target::Any, "ungael", flag!("swapTravel")),
    strict("talk-to", Target::Any, "pirate's cove", flag!("swapTravel")),
    strict(
        "talk-to",
        Target::Any,
        "waterbirth island",
        flag!("swapTravel"),
    ),
    strict(
        "talk-to",
        Target::Any,
        "island of stone",
        flag!("swapTravel"),
    ),
    strict("talk-to", Target::Any, "miscellania", flag!("swapTravel")),
    strict("talk-to", Target::Any, "follow", flag!("swapTravel")),
    strict("talk-to", Target::Any, "transport", flag!("swapTravel")),
    strict("talk-to", Target::Any, "quick-travel", flag!("swapQuick")),
    strict(
        "talk-to",
        Target::OneOf(ESSENCE_MINE_NPCS),
        "teleport",
        flag!("swapEssenceMineTeleport"),
    ),
    strict(
        "talk-to",
        Target::Any,
        "deposit-items",
        flag!("swapDepositItems"),
    ),
    strict(
        "talk-to",
        Target::OneOf(TEMPOROSS_NPCS),
        "leave",
        flag!("swapTemporossLeave"),
    ),
    strict(
        "pass",
        Target::Equals("energy barrier"),
        "pay-toll(2-ecto)",
        flag!("swapTravel"),
    ),
    strict(
        "open",
        Target::Equals("gate"),
        "pay-toll(10gp)",
        flag!("swapTravel"),
    ),
    strict(
        "inspect",
        Target::Equals("trapdoor"),
        "travel",
        flag!("swapTravel"),
    ),
    strict(
        "board",
        Target::Equals("travel cart"),
        "pay-fare",
        flag!("swapTravel"),
    ),
    strict(
        "board",
        Target::Equals("sacrificial boat"),
        "quick-board",
        flag!("swapQuick"),
    ),
    strict("cage", Target::Any, "harpoon", flag!("swapHarpoon")),
    strict("big net", Target::Any, "harpoon", flag!("swapHarpoon")),
    strict("net", Target::Any, "harpoon", flag!("swapHarpoon")),
    strict("lure", Target::Any, "bait", flag!("swapBait")),
    strict("net", Target::Any, "bait", flag!("swapBait")),
    strict("small net", Target::Any, "bait", flag!("swapBait")),
    strict(
        "enter",
        Target::Equals("portal"),
        "home",
        mode!("swapHomePortal", "HOME"),
    ),
    strict(
        "enter",
        Target::Equals("portal"),
        "build mode",
        mode!("swapHomePortal", "BUILD_MODE"),
    ),
    strict(
        "enter",
        Target::Equals("portal"),
        "friend's house",
        mode!("swapHomePortal", "FRIENDS_HOUSE"),
    ),
    contains(
        "zanaris",
        "last-destination",
        mode!("swapFairyRing", "LAST_DESTINATION"),
    ),
    contains("zanaris", "configure", mode!("swapFairyRing", "CONFIGURE")),
    contains(
        "tree",
        "last-destination",
        mode!("swapFairyRing", "LAST_DESTINATION"),
    ),
    contains("tree", "configure", mode!("swapFairyRing", "CONFIGURE")),
    contains("configure", "last-destination", |config: &PluginConfig| {
        matches!(
            config.choice("swapFairyRing"),
            "LAST_DESTINATION" | "ZANARIS"
        )
    }),
    contains("tree", "zanaris", mode!("swapFairyRing", "ZANARIS")),
    strict("check", Target::Any, "reset", flag!("swapBoxTrap")),
    strict("dismantle", Target::Any, "reset", flag!("swapBoxTrap")),
    strict("take", Target::Any, "lay", flag!("swapBoxTrap")),
    strict("pick-up", Target::Any, "chase", flag!("swapChase")),
    strict(
        "interact",
        Target::EndsWith("birdhouse"),
        "empty",
        flag!("swapBirdhouseEmpty"),
    ),
    strict("enter", Target::Any, "quick-enter", flag!("swapQuick")),
    strict(
        "enter-crypt",
        Target::Any,
        "quick-enter",
        flag!("swapQuick"),
    ),
    strict("ring", Target::Any, "quick-start", flag!("swapQuick")),
    strict("pass", Target::Any, "quick-pass", flag!("swapQuick")),
    strict("pass", Target::Any, "quick pass", flag!("swapQuick")),
    strict("open", Target::Any, "quick-open", flag!("swapQuick")),
    strict("climb-down", Target::Any, "quick-start", flag!("swapQuick")),
    strict("climb-down", Target::Any, "pay", flag!("swapQuick")),
    strict("admire", Target::Any, "teleport", flag!("swapAdmire")),
    strict("admire", Target::Any, "spellbook", flag!("swapAdmire")),
    strict("admire", Target::Any, "perks", flag!("swapAdmire")),
    strict("shared", Target::Any, "private", flag!("swapPrivate")),
    strict("pick", Target::Any, "pick-lots", flag!("swapPick")),
    strict("wear", Target::Any, "tele to poh", flag!("swapTeleToPoh")),
    strict("wear", Target::Any, "rub", flag!("swapTeleportItem")),
    strict("wear", Target::Any, "teleport", flag!("swapTeleportItem")),
    strict("wield", Target::Any, "teleport", flag!("swapTeleportItem")),
    strict("wield", Target::Any, "invoke", flag!("swapTeleportItem")),
    strict("wear", Target::Any, "teleports", flag!("swapTeleportItem")),
    strict(
        "wear",
        Target::Any,
        "farm teleport",
        mode!("swapArdougneCloak", "FARM"),
    ),
    strict(
        "wear",
        Target::Any,
        "monastery teleport",
        mode!("swapArdougneCloak", "MONASTERY"),
    ),
    strict(
        "wear",
        Target::Any,
        "gem mine",
        mode!("swapKaramjaGloves", "GEM_MINE"),
    ),
    strict(
        "wear",
        Target::Any,
        "slayer master",
        mode!("swapKaramjaGloves", "SLAYER_MASTER"),
    ),
    strict(
        "equip",
        Target::Any,
        "kourend woodland",
        mode!("swapRadasBlessing", "KOUREND_WOODLAND"),
    ),
    strict(
        "equip",
        Target::Any,
        "mount karuulm",
        mode!("swapRadasBlessing", "MOUNT_KARUULM"),
    ),
    strict(
        "wear",
        Target::Any,
        "ecto teleport",
        mode!("swapMorytaniaLegs", "ECTOFUNTUS"),
    ),
    strict(
        "wear",
        Target::Any,
        "burgh teleport",
        mode!("swapMorytaniaLegs", "BURGH_DE_ROTT"),
    ),
    strict(
        "wear",
        Target::Any,
        "nardah",
        mode!("swapDesertAmulet", "NARDAH"),
    ),
    strict(
        "wear",
        Target::Any,
        "kalphite cave",
        mode!("swapDesertAmulet", "KALPHITE_CAVE"),
    ),
    strict("bury", Target::Any, "use", flag!("swapBones")),
    strict("clean", Target::Any, "use", flag!("swapHerbs")),
    strict(
        "collect-note",
        Target::Any,
        "collect-item",
        mode!("swapGEItemCollect", "ITEMS"),
    ),
    strict(
        "collect-notes",
        Target::Any,
        "collect-items",
        mode!("swapGEItemCollect", "ITEMS"),
    ),
    strict(
        "collect-item",
        Target::Any,
        "collect-note",
        mode!("swapGEItemCollect", "NOTES"),
    ),
    strict(
        "collect-items",
        Target::Any,
        "collect-notes",
        mode!("swapGEItemCollect", "NOTES"),
    ),
    strict(
        "collect to inventory",
        Target::Any,
        "collect to bank",
        mode!("swapGEItemCollect", "BANK"),
    ),
    strict(
        "collect",
        Target::Any,
        "bank",
        mode!("swapGEItemCollect", "BANK"),
    ),
    strict(
        "collect-note",
        Target::Any,
        "bank",
        mode!("swapGEItemCollect", "BANK"),
    ),
    strict(
        "collect-notes",
        Target::Any,
        "bank",
        mode!("swapGEItemCollect", "BANK"),
    ),
    strict(
        "collect-item",
        Target::Any,
        "bank",
        mode!("swapGEItemCollect", "BANK"),
    ),
    strict(
        "collect-items",
        Target::Any,
        "bank",
        mode!("swapGEItemCollect", "BANK"),
    ),
    strict("tan 1", Target::Any, "tan all", flag!("swapTan")),
    // Shift is not visible to plugins, so the left-click stairs mode applies.
    strict(
        "climb",
        Target::Any,
        "climb-up",
        mode!("swapStairsLeftClick", "CLIMB_UP"),
    ),
    strict(
        "climb",
        Target::Any,
        "climb-down",
        mode!("swapStairsLeftClick", "CLIMB_DOWN"),
    ),
];

/// Every swap keyed by `option`, in source order, including the list-expanded rows.
fn swaps_for(option: &str) -> impl Iterator<Item = Swap> + '_ {
    let listed = SWAPS
        .iter()
        .filter(move |swap| swap.option == option)
        .map(|swap| Swap {
            option: swap.option,
            target: swap.target,
            swapped: swap.swapped,
            strict: swap.strict,
            enabled: swap.enabled,
        });
    let jewellery = (option == "teleport menu")
        .then_some(JEWELLERY_BOX_DESTINATIONS)
        .into_iter()
        .flatten()
        .map(|destination| {
            strict(
                "teleport menu",
                Target::Any,
                destination,
                flag!("swapJewelleryBox"),
            )
        });
    let nexus = PORTAL_NEXUS_LOCATIONS
        .iter()
        .filter(move |location| **location == option)
        .map(|location| {
            strict(
                location,
                Target::Equals("portal nexus"),
                "teleport menu",
                flag!("swapPortalNexus"),
            )
        });
    listed.chain(jewellery).chain(nexus)
}

/// `Text.removeTags(...).toLowerCase()`.
fn plain(text: &str) -> String {
    let mut out = String::with_capacity(text.len());
    let mut in_tag = false;
    for ch in text.chars() {
        match ch {
            '<' => in_tag = true,
            '>' if in_tag => in_tag = false,
            _ if !in_tag => out.extend(ch.to_lowercase()),
            _ => {}
        }
    }
    out
}

/// Replay `onPostMenuSort`'s built-in swaps over `entries` (top first) and return the exchanges.
fn swap_edits(entries: &[MenuEntry], config: &PluginConfig) -> Vec<MenuEdit> {
    // The source's array is bottom first: position 0 is the bottom entry.
    let mut slots: Vec<(usize, String, String)> = entries
        .iter()
        .enumerate()
        .rev()
        .map(|(index, entry)| (index, plain(&entry.option), plain(&entry.target)))
        .collect();
    let mut edits = Vec::new();
    for position in 0..slots.len() {
        let (_, option, target) = slots[position].clone();
        for swap in swaps_for(&option) {
            if !swap.target.matches(&target) || !(swap.enabled)(config) {
                continue;
            }
            // `findIndex`: the top-most entry below this one with the swapped option and target.
            let found = (0..position).rev().find(|&below| {
                let (_, other_option, other_target) = &slots[below];
                *other_target == target
                    && if swap.strict {
                        other_option == swap.swapped
                    } else {
                        other_option.contains(swap.swapped)
                    }
            });
            if let Some(below) = found {
                edits.push(MenuEdit::Swap {
                    first: slots[below].0,
                    second: slots[position].0,
                });
                slots.swap(below, position);
                break;
            }
        }
    }
    edits
}

impl NativePlugin for MenuEntrySwapper {
    fn game_event_interests(&self) -> EventInterest {
        EventInterest::one(GameEventKind::PostMenuSort)
    }
    fn init(&mut self, _call: &mut PluginCall<'_>) -> Result<(), PluginFault> {
        self.edits.clear();
        Ok(())
    }
    fn events(
        &mut self,
        call: &mut PluginCall<'_>,
        events: &[GameEvent],
    ) -> Result<(), PluginFault> {
        for event in events {
            if let GameEvent::PostMenuSort(entries) = event {
                self.edits = swap_edits(entries, call.config());
            }
        }
        Ok(())
    }
    fn output(&mut self, _call: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
        Ok(PluginOutput {
            menu_edits: self.edits.clone(),
            ..PluginOutput::default()
        })
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_plugin_api::MenuKind;
    use rustclient_plugin_host::apply_menu_edits;
    use rustclient_plugin_host::configuration::ConfigValue::{Boolean as Flag, Choice};

    fn entry(option: &str, target: &str) -> MenuEntry {
        MenuEntry {
            option: option.into(),
            target: target.into(),
            kind: MenuKind::NpcOption(1),
            identifier: 0,
            param0: 0,
            param1: 0,
            deprioritized: false,
        }
    }
    fn config(
        pairs: &[(&str, rustclient_plugin_host::configuration::ConfigValue)],
    ) -> PluginConfig {
        use rustclient_plugin_host::configuration::Configuration;
        let schema = rustclient_plugin_host::required::first_party_schemas()
            .unwrap()
            .into_iter()
            .find(|schema| schema.plugin.as_str() == "dev.rustclient.menuentryswapper")
            .expect("menu entry swapper schema");
        let mut configuration = Configuration::new(schema, None).unwrap();
        for (key, value) in pairs {
            configuration
                .change(key, value.clone(), |_, _| Ok(()))
                .unwrap_or_else(|error| panic!("{key}: {error}"));
        }
        PluginConfig::of(&configuration)
    }
    fn swapped(entries: &[MenuEntry], config: &PluginConfig) -> Vec<String> {
        let edited = apply_menu_edits(entries.len(), &swap_edits(entries, config));
        edited
            .order
            .iter()
            .map(|&index| entries[index].option.clone())
            .collect()
    }

    #[test]
    fn talk_to_swaps_with_bank_only_for_the_same_target_and_while_enabled() {
        let menu = [
            entry("Talk-to", "<col=ffff00>Banker"),
            entry("Bank", "<col=ffff00>Banker"),
            entry("Collect", "<col=ffff00>Banker"),
            entry("Walk here", ""),
        ];
        assert_eq!(
            swapped(&menu, &config(&[])),
            ["Bank", "Talk-to", "Collect", "Walk here"]
        );
        assert_eq!(
            swapped(&menu, &config(&[("swapBanker", Flag(false))])),
            ["Talk-to", "Bank", "Collect", "Walk here"]
        );
        // Another NPC's "Bank" option is not this target's.
        let other = [
            entry("Talk-to", "<col=ffff00>Hans"),
            entry("Bank", "<col=ffff00>Banker"),
        ];
        assert_eq!(swapped(&other, &config(&[])), ["Talk-to", "Bank"]);
    }

    #[test]
    fn non_strict_and_mode_swaps_follow_the_source_table() {
        let fairy = [
            entry("Zanaris", "<col=ffff>Fairy ring"),
            entry("Configure", "<col=ffff>Fairy ring"),
            entry("Last-destination (DIS)", "<col=ffff>Fairy ring"),
        ];
        // Default LAST_DESTINATION: "zanaris" swaps with the entry containing "last-destination".
        assert_eq!(swapped(&fairy, &config(&[]))[0], "Last-destination (DIS)");
        let portal = [
            entry("Enter", "<col=ffff>Portal"),
            entry("Home", "<col=ffff>Portal"),
            entry("Build mode", "<col=ffff>Portal"),
        ];
        assert_eq!(
            swapped(
                &portal,
                &config(&[("swapHomePortal", Choice("BUILD_MODE".into()))])
            )[0],
            "Build mode"
        );
        let nexus = [
            entry("Varrock", "<col=ffff>Portal Nexus"),
            entry("Teleport Menu", "<col=ffff>Portal Nexus"),
        ];
        assert_eq!(
            swapped(&nexus, &config(&[("swapPortalNexus", Flag(true))]))[0],
            "Teleport Menu"
        );
    }
}
