#!/usr/bin/env python3
"""Build a checksum-bound rl-suite semantic map from RuneLite API interfaces.

The class names are derived from the interfaces each class already implements
in this exact jar, so they are revision-specific and do not depend on a stale
cross-revision mapping. Only class identities are emitted; fields and methods
keep rl-suite's deterministic generic names.
"""
from __future__ import annotations

import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parent
SOURCES = ROOT / "sources"
INPUT_SHA256 = "25f42961c400bd9dfff1554402441c0ba6d1cffd011163cb9b0b4c42ae194f85"
REVISION = "1.12.39"
OUT = ROOT / "semantic-1.12.39.tsv"

# Most specific API interface first.  A class that implements Player and Actor
# becomes Player; one that implements GameObject and TileObject becomes
# GameObject; the base Renderable only wins when nothing more specific exists.
PRIORITY = [
    "Client", "GameEngine", "Player", "NPC", "Actor", "Widget", "WorldView",
    "Menu", "MenuEntry", "Preferences", "ItemContainer", "Projectile",
    "GraphicsObject", "DynamicObject", "GroundObject", "WallObject",
    "DecorativeObject", "GameObject", "ItemLayer", "TileObject", "TileItem", "Tile",
    "Scene", "SceneTilePaint", "SceneTileModel", "Model", "ModelData",
    "Animation", "Texture", "TextureProvider", "SpritePixels", "IndexedSprite",
    "FontTypeFace", "Script", "ScriptEvent", "ScriptEventBuilder",
    "MessageNode", "ChatLineBuffer", "ChatPlayer", "Friend", "Ignore",
    "FriendContainer", "Nameable", "NameableContainer", "ClanChannel",
    "ClanChannelMember", "ClanSettings", "FriendsChatMember",
    "FriendsChatManager", "GrandExchangeOffer", "World", "WorldEntity",
    "WorldEntityConfig", "ObjectComposition", "ItemComposition",
    "NPCComposition", "EnumComposition", "StructComposition",
    "VarbitComposition", "DBRowConfig", "HealthBarConfig", "Hitsplat",
    "ActorSpotAnim", "AmbientSoundEffect", "PendingLogin", "MidiRequest",
    "NodeCache", "IntegerNode", "Deque", "IterableHashTable",
    "IndexedObjectSet", "HashTable", "BufferProvider", "MainBufferProvider",
    "PacketBuffer", "IndexDataBase", "CollisionData", "AABB",
    "FloatProjection", "IntProjection", "Projection", "RenderOverview",
    "WorldMapRegion", "WorldMapRenderer", "WorldMapData", "WorldMapIcon",
    "WidgetConfigNode", "WidgetNode", "EntityOps", "NpcOverrides",
    "ColorTextureOverride", "PlayerComposition", "Node", "Renderable",
]

# Known names from the reviewed 1.12.37 map where the API interface alone is
# less descriptive, or where the class does not implement a RuneLite interface.
MANUAL = {
    "aaa": "AbstractFont",   # implements FontTypeFace
    "aap": "AccountType",    # no RuneLite interface; enum-like account type
    "cy": "ClientPreferences",  # implements Preferences
}

CLASS_DECL = re.compile(
    r"(?:^|\n)(?:public\s+|final\s+|abstract\s+|static\s+)*class\s+(\w+)([^{]*)\{",
    re.MULTILINE,
)
IMPORT_API = re.compile(
    r"^import\s+(net\.runelite\.api(?:\.[\w.]+)?)\.([A-Za-z_]\w*)\s*;",
    re.MULTILINE,
)
OBF_NAME = re.compile(r'@ObfuscatedName\("([^"]+)"\)')


def api_interfaces(text: str, declaration_tail: str) -> list[str]:
    imports = {name for _, name in IMPORT_API.findall(text)}
    match = re.search(r"\bimplements\s+([^{]+)", declaration_tail)
    if not match:
        return []
    result = []
    for item in match.group(1).split(","):
        item = item.strip().split("<", 1)[0].strip()
        if not item:
            continue
        simple = item.rsplit(".", 1)[-1]
        if simple in imports or item.startswith("net.runelite.api."):
            result.append(simple)
    return result


def choose_name(interfaces: list[str]) -> str | None:
    for candidate in PRIORITY:
        if candidate in interfaces:
            return candidate
    return None


def main() -> int:
    rows: list[tuple[str, str]] = []
    for path in sorted(SOURCES.glob("*.java")):
        text = path.read_text(encoding="utf-8", errors="ignore")
        match = CLASS_DECL.search(text)
        if not match:
            continue
        transformed_name, tail = match.group(1), match.group(2)
        # Only classes rl-suite actually renamed are eligible for a semantic
        # class identity. client and the injected rl* helpers are excluded.
        if not transformed_name.startswith("class"):
            continue
        before = text[: match.start()]
        names = OBF_NAME.findall(before)
        if not names:
            continue
        original = names[-1]
        interfaces = api_interfaces(text, tail)
        semantic = MANUAL.get(original) or choose_name(interfaces)
        if not semantic:
            continue
        rows.append((original, semantic))

    # Semantic class names must be unique.  The only expected duplicate is the
    # two Deque implementations; suffix later ones deterministically.
    used: dict[str, int] = {}
    unique_rows: list[tuple[str, str]] = []
    for original, semantic in rows:
        count = used.get(semantic, 0) + 1
        used[semantic] = count
        unique_rows.append((original, semantic if count == 1 else f"{semantic}{count}"))

    lines = [
        "# format=rl-suite-semantic-map-v1",
        f"# revision={REVISION}",
        f"# input.sha256={INPUT_SHA256}",
        "# source=api-interface-derivation",
        "# note=class identities derived from interfaces implemented in this exact input jar",
    ]
    for original, semantic in unique_rows:
        lines.append(f"class\t{original}\t-\t-\t{semantic}")
    OUT.write_text("\n".join(lines) + "\n", encoding="utf-8")

    print(f"wrote {OUT} ({len(unique_rows)} classes)")
    for original, semantic in unique_rows:
        print(f"{original}\t{semantic}")
    return 0


if __name__ == "__main__":
    sys.exit(main())
