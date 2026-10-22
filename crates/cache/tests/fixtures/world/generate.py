"""Synthetic fixed vectors, authored from the documented 240 stream grammar."""
from pathlib import Path
import struct
out=Path(__file__).parent
u16=lambda n:struct.pack('>H',n)
u32=lambda n:struct.pack('>I',n)
def smart(n):return bytes([n]) if n<128 else u16(n+32768)
def signed(n):return bytes([n+64]) if -64<=n<64 else u16(n+49152)
terrain=b''.join(map(u16,[82,7,2,52,1]))+bytes([7])+bytes((16384-1)*2)
(out/'terrain.bin').write_bytes(terrain)
(out/'locations.bin').write_bytes(smart(4)+smart(8389)+bytes([43,0,0]))
(out/'object.bin').write_bytes(bytes([6,1])+u32(70000)+bytes([10,14,2,15,3,62,65])+u16(256)+bytes([70])+u16(65529)+bytes([40,2])+u16(1)+u16(2)+u16(2)+u16(3)+bytes([81,2,0]))
for modern in [False,True]:
    xs=signed(128)+signed(-128);ys=signed(-128);zs=signed(128);indices=signed(0)+signed(1)+signed(1)
    body=bytes([0,1,7,1])+indices+u16(500)+xs+ys+zs+(bytes([0]) if modern else b'')
    flags=bytes(7 if modern else 6)
    footer=u16(3)+u16(1)+bytes([0])+flags+b''.join(u16(n) for n in [len(xs),len(ys),len(zs),len(indices)]+([0] if modern else [])+[0])+bytes([255,253 if modern else 254])
    (out/('model-fd.bin' if modern else 'model-fe.bin')).write_bytes(body+footer)
