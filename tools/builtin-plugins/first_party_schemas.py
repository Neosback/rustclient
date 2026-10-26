#!/usr/bin/env python3
"""Generate the first-party plugin configuration schemas from the vendored RuneLite sources.

Reads each ported plugin's `@PluginDescriptor` and `*Config.java` under `references/` (read-only)
and writes `crates/plugin-host/src/first-party-schemas.json`. Every setting keeps its source file and
member name so the ledger can say which RuneLite option it came from.

Modelling rules (the schema controls are boolean, integer, choice, color, hotkey, size, font, text and
decimal):

* `boolean`, `int`, `String`, `double`, `Color`, `Keybind`/`ModifierlessKeybind` and `enum` map to the
  obvious control. `double` is stored in thousandths.
* `Notification` maps to a boolean "enabled" switch: the Rust client has one notification channel
  (`client.notify`, an in-client message), so tray, sound, flash and focus facets do not exist.
* Items marked `hidden = true` in the source are internal storage and are not shown.
"""
import json
import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
PLUGINS = ROOT / "references/open-runelite-rev-240/runelite-client/src/main/java/net/runelite/client"
OUT = ROOT / "crates/plugin-host/src/first-party-schemas.json"

# (directory, rust id suffix, config interface file or None)
PLUGIN_LIST = [
    ("roofremoval", "roofremoval"),
    ("animsmoothing", "animsmoothing"),
    ("stretchedmode", "stretchedmode"),
    ("fps", "fps"),
    ("statusbars", "statusbars"),
    ("interfacestyles", "interfacestyles"),
    ("devtools", "devtools"),
    ("entityhider", "entityhider"),
    ("npchighlight", "npchighlight"),
    ("playerindicators", "playerindicators"),
    ("idlenotifier", "idlenotifier"),
    ("camera", "camera"),
    ("menuentryswapper", "menuentryswapper"),
]

JAVA_COLORS = {
    "WHITE": 0xFFFFFFFF, "LIGHT_GRAY": 0xFFC0C0C0, "GRAY": 0xFF808080, "DARK_GRAY": 0xFF404040,
    "BLACK": 0xFF000000, "RED": 0xFFFF0000, "PINK": 0xFFFFAFAF, "ORANGE": 0xFFFFC800,
    "YELLOW": 0xFFFFFF00, "GREEN": 0xFF00FF00, "MAGENTA": 0xFFFF00FF, "CYAN": 0xFF00FFFF,
    "BLUE": 0xFF0000FF,
}
# RuneLite's own palette constants used as defaults (net.runelite.client.ui.ColorScheme etc.).
KNOWN_COLORS = {
    "ColorScheme.BRAND_ORANGE": 0xFFDC8A00,
    "ColorScheme.PROGRESS_COMPLETE_COLOR": 0xFF2DC450,
}

problems = []


def strip_comments(text):
    text = re.sub(r"/\*.*?\*/", lambda m: "\n" * m.group(0).count("\n"), text, flags=re.S)
    return re.sub(r"//[^\n]*", "", text)


def constants(text, siblings=()):
    values = {}
    for m in re.finditer(r"\b(?:static\s+|final\s+)*int\s+(\w+)\s*=\s*(-?\d+)\s*;", text):
        values[m.group(1)] = int(m.group(2))
    # Constants of sibling classes are referenced as `Class.NAME`.
    for path in siblings:
        for m in re.finditer(r"\b(?:static\s+|final\s+)*int\s+(\w+)\s*=\s*(-?\d+)\s*;", strip_comments(path.read_text())):
            values[f"{path.stem}.{m.group(1)}"] = int(m.group(2))
    return values


def number(expr, consts):
    expr = expr.strip()
    expr = re.sub(r"Integer\.MAX_VALUE", "2147483647", expr)
    expr = re.sub(r"Integer\.MIN_VALUE", "-2147483648", expr)
    for name, value in sorted(consts.items(), key=lambda kv: -len(kv[0])):
        expr = re.sub(rf"(?<![\w.]){re.escape(name)}\b", str(value), expr)
    expr = re.sub(r"(\d+(?:\.\d+)?)[fFdD]\b", r"\1", expr)
    if not re.fullmatch(r"[-+*/() \d.]+", expr):
        raise ValueError(expr)
    return eval(expr, {"__builtins__": {}}, {})  # noqa: S307 - constant arithmetic only


def parse_color(expr):
    expr = expr.strip()
    if expr in KNOWN_COLORS:
        return KNOWN_COLORS[expr]
    m = re.fullmatch(r"Color\.(\w+)", expr)
    if m and m[1] in JAVA_COLORS:
        return JAVA_COLORS[m[1]]
    m = re.fullmatch(r"new Color\(\s*(\d+)\s*,\s*(\d+)\s*,\s*(\d+)\s*(?:,\s*(\d+)\s*)?\)", expr)
    if m:
        r, g, b = int(m[1]), int(m[2]), int(m[3])
        a = int(m[4]) if m[4] else 255
        return (a << 24) | (r << 16) | (g << 8) | b
    m = re.fullmatch(r"new Color\(\s*(0x[0-9a-fA-F]+|\d+)\s*(?:,\s*(true|false))?\s*\)", expr)
    if m:
        value = int(m[1], 0)
        return value if m[2] == "true" else 0xFF000000 | value
    m = re.fullmatch(r'ColorUtil\.fromHex\("#?([0-9a-fA-F]{6,8})"\)', expr)
    if m:
        value = int(m[1], 16)
        return value if len(m[1]) == 8 else 0xFF000000 | value
    m = re.fullmatch(r'Color\.decode\("#?([0-9a-fA-F]{6})"\)', expr)
    if m:
        return 0xFF000000 | int(m[1], 16)
    if expr in ("null", ""):
        return 0
    raise ValueError(expr)


def find_enum(name, files):
    for path in files:
        text = strip_comments(path.read_text())
        m = re.search(rf"\benum\s+{name}\b[^{{]*\{{(.*?)\n\s*;?\s*(?:private|public|@|\w+\s+\w+\s*;|\}})", text, re.S)
        if not m:
            m = re.search(rf"\benum\s+{name}\b[^{{]*\{{(.*?)\}}", text, re.S)
        if m:
            body = m.group(1)
            head = re.split(r";|\n\s*(?:private|public|final)\b", body, maxsplit=1)[0]
            names = re.findall(r"(?m)^\s*([A-Z][A-Z0-9_]*)\s*(?:\([^)]*\))?\s*(?:,|$)", head)
            if not names:
                names = re.findall(r"\b([A-Z][A-Z0-9_]*)\s*(?:\((?:[^()]|\([^()]*\))*\))?\s*[,;]?", head)
            seen = []
            for n in names:
                if n not in seen:
                    seen.append(n)
            return seen
    return None


def descriptor(plugin_dir):
    for path in sorted(plugin_dir.glob("*Plugin.java")):
        text = strip_comments(path.read_text())
        m = re.search(r"@PluginDescriptor\((.*?)\)\s*(?:@\w+(?:\([^)]*\))?\s*)*public class", text, re.S)
        if m:
            attrs = m.group(1)
            name = re.search(r'name\s*=\s*"([^"]*)"', attrs)
            desc = re.search(r'description\s*=\s*"([^"]*)"', attrs)
            tags = re.search(r"tags\s*=\s*\{(.*?)\}", attrs, re.S)
            default = re.search(r"enabledByDefault\s*=\s*(true|false)", attrs)
            developer = re.search(r"developerPlugin\s*=\s*true", attrs)
            return {
                "name": name[1] if name else path.stem,
                "description": desc[1] if desc else "",
                "tags": re.findall(r'"([^"]*)"', tags[1]) if tags else [],
                "enabled_default": False if developer else ((default[1] == "true") if default else True),
                "source": str(path.relative_to(ROOT)),
            }
    raise SystemExit(f"no plugin descriptor in {plugin_dir}")


def split_args(args):
    out, depth, current, quote = [], 0, "", False
    for ch in args:
        if ch == '"':
            quote = not quote
        if not quote:
            if ch in "([{":
                depth += 1
            elif ch in ")]}":
                depth -= 1
            elif ch == "," and depth == 0:
                out.append(current)
                current = ""
                continue
        current += ch
    if current.strip():
        out.append(current)
    return out


def attributes(args):
    result = {}
    for part in split_args(args):
        if "=" in part:
            key, value = part.split("=", 1)
            result[key.strip()] = value.strip()
    return result


def unquote(value):
    m = re.fullmatch(r'"((?:[^"\\]|\\.)*)"', value.strip())
    return json.loads('"' + m[1] + '"') if m else value.strip()


def parse_config(plugin_dir, rel_dir):
    configs = sorted(plugin_dir.glob("*Config.java"))
    if not configs:
        return None, []
    path = configs[0]
    raw = path.read_text()
    text = strip_comments(raw)
    consts = constants(text, sorted(plugin_dir.glob("*.java")))
    enum_files = sorted(plugin_dir.glob("*.java")) + sorted((PLUGINS / "config").glob("*.java")) + [PLUGINS / "Notification.java"]
    sections = {}
    for m in re.finditer(r"@ConfigSection\((.*?)\)\s*(?:public\s+)?(?:static\s+)?(?:final\s+)?String\s+(\w+)\s*=\s*\"([^\"]+)\"", text, re.S):
        attrs = attributes(m[1])
        sections[m[2]] = {
            "id": m[3],
            "name": unquote(attrs.get("name", '"' + m[3] + '"')),
            "position": int(attrs.get("position", "0")),
        }
    items = []
    for m in re.finditer(r"@ConfigItem\(", text):
        start = m.start()
        # attributes: balanced parens
        depth, i = 0, m.end() - 1
        while True:
            if text[i] == "(":
                depth += 1
            elif text[i] == ")":
                depth -= 1
                if depth == 0:
                    break
            i += 1
        attrs = attributes(text[m.end():i])
        rest = text[i + 1:]
        sig = re.match(r"\s*((?:@\w+(?:\((?:[^()]|\([^()]*\))*\))?\s*)*)(?:default\s+)?([\w<>\[\], ?.]+?)\s+(\w+)\(\)\s*(?:\{(.*?)\n\t\}|;)", rest, re.S)
        if not sig and re.match(r"\s*(?:@\w+(?:\((?:[^()]|\([^()]*\))*\))?\s*)*void\s", rest):
            continue  # a setter generated for the same key
        if not sig:
            problems.append(f"{path.name}: cannot read member after @ConfigItem at offset {start}")
            continue
        trailing, jtype, symbol, body = sig.groups()
        before = text[max(text.rfind(";", 0, start), text.rfind("}", 0, start)) + 1:start]
        annotations = before + " " + trailing
        range_m = re.search(r"@Range\(((?:[^()]|\([^()]*\))*)\)", annotations)
        rng = attributes(range_m[1]) if range_m else {}
        units = re.search(r'@Units\(([^)]*)\)', annotations)
        default_expr = (re.search(r"return\s+(.*?);", body or "", re.S) or [None, ""])[1].strip()
        key = unquote(attrs.get("keyName", ""))
        if attrs.get("hidden", "false") == "true" or not key:
            continue
        section = attrs.get("section")
        item = {
            "key": key,
            "label": unquote(attrs.get("name", '"' + key + '"')),
            "description": unquote(attrs.get("description", '""')),
            "section": sections[section]["name"] if section in sections else "",
            "position": int(attrs["position"]) if "position" in attrs and attrs["position"].isdigit() else 0,
            "source": f"{rel_dir}/{path.name}#{symbol}",
        }
        try:
            control, default = model(jtype, default_expr, rng, units, consts, enum_files, annotations)
        except (ValueError, KeyError) as error:
            problems.append(f"{path.name}#{symbol}: {jtype} default `{default_expr}`: {error}")
            continue
        item["control"], item["default"] = control, default
        items.append(item)
    items.sort(key=lambda it: (sections_order(sections, it["section"]), it["position"]))
    for it in items:
        del it["position"]
    return sections, items


def sections_order(sections, name):
    for section in sections.values():
        if section["name"] == name:
            return section["position"] + 1
    return 0


def model(jtype, expr, rng, units, consts, enum_files, annotations):
    jtype = jtype.strip()
    if jtype == "boolean":
        return {"type": "boolean"}, {"type": "boolean", "value": expr == "true"}
    if jtype in ("int", "Integer"):
        low = number(rng["min"], consts) if "min" in rng else -2147483648
        high = number(rng["max"], consts) if "max" in rng else 2147483647
        value = int(number(expr, consts))
        return {"type": "integer", "min": int(low), "max": int(high)}, {"type": "integer", "value": value}
    if jtype == "double":
        value = number(expr, consts)
        return (
            {"type": "decimal", "min": -1000000 * 1000, "max": 1000000 * 1000},
            {"type": "decimal", "value": round(value * 1000)},
        )
    if jtype == "String":
        return {"type": "text", "max_len": 4096}, {"type": "text", "value": json.loads(expr) if expr.startswith('"') else ""}
    if jtype == "Color":
        return {"type": "color"}, {"type": "color", "value": parse_color(expr)}
    if jtype in ("Keybind", "ModifierlessKeybind"):
        m = re.search(r"VK_(\w+)", expr)
        key = m[1] if m else ""
        return {"type": "hotkey"}, {"type": "hotkey", "value": {"key": key, "modifiers": 0}}
    if jtype == "Notification":
        enabled = bool(re.match(r"Notification\.(ON|OFF)", expr)) and expr.startswith("Notification.ON")
        return {"type": "boolean"}, {"type": "boolean", "value": enabled}
    names = find_enum(jtype, enum_files)
    if names:
        constant = expr.rsplit(".", 1)[-1]
        if constant not in names:
            raise ValueError(f"default {constant} not among {names}")
        return {"type": "choice", "options": names}, {"type": "choice", "value": constant}
    raise ValueError("unmodelled type")


def main():
    schemas = []
    for directory, suffix in PLUGIN_LIST:
        plugin_dir = PLUGINS / "plugins" / directory
        meta = descriptor(plugin_dir)
        rel = str(plugin_dir.relative_to(ROOT))
        sections, items = parse_config(plugin_dir, rel)
        settings = [{
            "key": "enabled",
            "label": "Enabled",
            "description": f"Switch {meta['name']} on or off.",
            "section": "",
            "control": {"type": "boolean"},
            "default": {"type": "boolean", "value": meta["enabled_default"]},
            "source": meta["source"] + "#PluginDescriptor.enabledByDefault",
        }] + items
        schemas.append({
            "plugin": f"dev.rustclient.{suffix}",
            "name": meta["name"],
            "version": 1,
            "description": meta["description"],
            "tags": meta["tags"],
            "required": False,
            "settings": settings,
        })
    OUT.write_text(json.dumps(schemas, indent=1, ensure_ascii=False) + "\n")
    print(json.dumps({"plugins": len(schemas), "settings": sum(len(s["settings"]) - 1 for s in schemas)}))
    for problem in problems:
        print("PROBLEM:", problem, file=sys.stderr)
    return 1 if problems else 0


if __name__ == "__main__":
    sys.exit(main())
