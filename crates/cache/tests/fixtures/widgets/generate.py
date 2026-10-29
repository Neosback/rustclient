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

# IF1 vectors authored independently against Widget.method7215, revision 240.
for kind in range(9):
    b=bytearray([kind,2 if kind==6 else 1])
    b+=struct.pack('>HhhHHBHH',0,-10,5,80,25,64,65535 if kind==0 else 0,3)
    b+=bytes([1,2])+u16(42)+bytes([1])+u16(3)+u16(20)+u16(65535)+u16(0)
    if kind==0:b+=u16(960)+bytes([1])
    if kind==1:b+=u16(123)+bytes([0])
    if kind==3:b+=bytes([1])
    if kind in (1,4):b+=struct.pack('>BBBHB',1,2,14,496,1)
    if kind==4:b+=string('Bank €')+string('Selected')
    if kind in (1,3,4):b+=u32(0x123456)
    if kind in (3,4):b+=u32(0xabcdef)+u32(0x112233)+u32(0x445566)
    if kind==5:b+=u32(1234)+u32(0xffffffff)
    if kind==6:b+=struct.pack('>IIHHHHH',70000,80000,808,65535,900,256,512)
    if kind==8:b+=string('Hover only')
    b+=string('Cast')+string('Spell')+u16(0xffff) if kind==6 else bytes([0])
    (OUT/f'if1-{kind}.bin').write_bytes(b)
