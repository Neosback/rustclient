"""Independent, synthetic IF3 family vectors; no cache runtime dependency."""
from pathlib import Path
import struct
OUT = Path(__file__).parent
u16=lambda n:struct.pack('>H',n)
i16=lambda n:struct.pack('>h',n)
u32=lambda n:struct.pack('>I',n)
string=lambda s:s.encode('cp1252')+b'\0'
for kind,name in [(0,'container'),(3,'rectangle'),(4,'text'),(5,'sprite'),(6,'model'),(9,'line')]:
    modes = bytes([1,2,1,2]) if kind==6 else bytes([0,0,1,2])
    header=b'\xff'+bytes([kind])+u16(0)+i16(-10)+i16(5)+u16(320)+(i16(-25) if kind==9 else u16(240))+modes+u16(65535)+b'\0'
    bodies={
        0:u16(640)+u16(960)+b'\1',
        3:u32(0x123456)+bytes([1,64]),
        4:u16(496)+string('Bank €')+bytes([12,1,2,1])+u32(0xff00ff),
        5:u32(1234)+u16(15)+bytes([1,3,1])+u32(0x123)+bytes([1,0]),
        6:u32(70000)+i16(-4)+i16(5)+u16(100)+u16(200)+u16(300)+u16(400)+u16(808)+b'\1'+u16(0)+u16(640)+u16(480),
        9:bytes([2])+u32(0xffffff)+b'\1',
    }
    # Every listener slot, then the three trigger lists. onLoad has typed args.
    listeners=bytes([3,0])+u32(100)+b'\0'+u32(0xffffffd6)+b'\1'+string('€')+bytes(17)
    tail=b'\xff\xff\xff'+string('Bank')+bytes([2])+string('Open')+string('Bank')+bytes([4,5,1])+string('Use')+listeners+bytes([1])+u32(0xfffffffe)+bytes([2])+u32(93)+u32(95)+bytes([0])
    (OUT/(name+'.bin')).write_bytes(header+bodies[kind]+tail)
