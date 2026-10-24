#!/usr/bin/env bash
# symbols.sh CLASS... : print shard-ready member lines (with JVM descriptors) for classes of the semantic jar.
#   c<TAB>CLASS<TAB>CLASS            ownership line (edit the right column to rename the class)
#   <TAB>f<TAB>DESC<TAB>NAME<TAB>NAME   field   (edit the last column)
#   <TAB>m<TAB>DESC<TAB>NAME<TAB>NAME   method  (edit the last column)
source "$(dirname "$0")/env.sh"
client240_require_inputs || exit 1
for cls in "$@"; do
  "$CLIENT240_JAVAP" -p -s -cp "$CLIENT240_SEMANTIC_JAR" "$cls" | python3 -c '
import re, sys
cls = sys.argv[1]
print(f"c\t{cls}\t{cls}")
lines = sys.stdin.read().split("\n")
for i, line in enumerate(lines):
    m = re.match(r"\s+(?:[\w\[\]<>.,? ]+ )?(\w+)(\(.*\))?(?: throws [\w., ]+)?;$", line)
    if not m or i + 1 >= len(lines) or "descriptor:" not in lines[i + 1]:
        continue
    name, is_method = m.group(1), m.group(2) is not None
    desc = lines[i + 1].split("descriptor:", 1)[1].strip()
    if name == cls or name.startswith("<"):
        continue
    kind = "m" if is_method else "f"
    print(f"\t{kind}\t{desc}\t{name}\t{name}")
' "$cls"
done
