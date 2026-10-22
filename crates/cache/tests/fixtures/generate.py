"""Deterministic authored fixtures. Not a cache writer or production implementation."""
from pathlib import Path
import bz2, gzip, struct
root = Path(__file__).parent
payload = b"RustClient title fixture\0" * 30
(root / "payload.bin").write_bytes(payload)
for kind, encoded in [(0, payload), (1, bz2.compress(payload, compresslevel=1)[4:]), (2, gzip.compress(payload, mtime=0))]:
    header = bytes([kind]) + struct.pack(">I", len(encoded))
    if kind:
        header += struct.pack(">I", len(payload))
    (root / f"container-{kind}.bin").write_bytes(header + encoded)
# Two 2x2 frames: row-major and column-major+alpha, on a 3x3 canvas.
pixels = bytes([0, 1, 0, 1, 1, 3, 1, 0, 1, 1, 255, 0, 128, 64])
palette = bytes([0, 0, 0])  # nontransparent palette RGB zero must become RGB one
metadata = struct.pack(">HHB", 3, 3, 1)
for values in [(1, 0), (1, 0), (2, 2), (2, 2)]:
    metadata += struct.pack(">HH", *values)
(root / "sprites.bin").write_bytes(pixels + palette + metadata + struct.pack(">H", 2))
# Simple title font metrics, independent of copyrighted glyph artwork.
(root / "font-metrics.bin").write_bytes(bytes([4] * 256 + [12]))
# Two chunks, two files: ABC + defgh. Chunk lengths [1,2], then [2,3].
(root / "split.bin").write_bytes(b"AdeBCfgh" + struct.pack(">iiiiB", 1, 1, 2, 1, 2))
import zlib

def name_hash(name):
    result = 0
    for ch in name:
        result = (result * 31 + ord(ch)) & 0xffffffff
    return result

def count(value):
    return struct.pack(">H", value) if value < 32768 else struct.pack(">I", value | 0x80000000)
raw = (root / "container-0.bin").read_bytes()
table = bytes([7]) + struct.pack(">IB", 123, 15) + count(1) + count(70000)
table += struct.pack(">III", name_hash("title"), zlib.crc32(raw), 0xabcdef)
table += bytes(range(64)) + struct.pack(">III", len(raw), len(payload), 7)
table += count(1) + count(9) + struct.pack(">I", 0)
(root / "reference-7.bin").write_bytes(table)
for protocol in [5, 6]:
    table = bytes([protocol]) + (struct.pack(">I", 456) if protocol == 6 else b"")
    table += bytes([0]) + struct.pack(">HHIIHH", 1, 2, zlib.crc32(raw), 7, 1, 4)
    (root / f"reference-{protocol}.bin").write_bytes(table)
