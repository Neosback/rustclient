#!/usr/bin/env python3
"""Independent byte-level reference vectors for the documented v5 schema."""
import struct
def h(domain,payload):
    value=0xcbf29ce484222325
    for byte in domain+struct.pack("<H",5)+payload:value=((value^byte)*0x100000001b3)&0xffffffffffffffff
    return f"{value:016x}"
def state(active,tick=0,sub=None):
    prefix=b"\1\1"+struct.pack("<H",240)+(b"\0" if sub is None else b"\1"+struct.pack("<H",sub))+b"\1"+struct.pack("<Q",tick) if active else bytes(3)
    # title welcome; no authentication, placement or scene; cycle zero; empty vars/locations
    return h(b"rustclient:state",prefix+bytes(4)+struct.pack("<QII",0,0,0))
print("inactive",state(False))
print("tick5",state(True,5))
print("subrevision_tick5",state(True,5,2))
# item + optional event present + event payload + seven explicit domain booleans
boundary=bytes([1,1,0,1,1,1,1])
begin=b"\1\1\0"+struct.pack("<H",240)+b"\0"+boundary
advance=b"\1\1\2"+struct.pack("<QQ",0,2)+bytes([0,1,0,0,0,0,0])
noop=b"\1\0"+bytes(7)
end=b"\1\1\1"+boundary
print("commit_stream",h(b"rustclient:commit-stream",begin+advance+noop+end+b"\0"))
print("empty_stream",h(b"rustclient:commit-stream",b"\0"))
revision=struct.pack("<HBH",240,1,2)
player=struct.pack("<IIBB",1,1,0,0)
placement=struct.pack("<iiBiiBB",3221,3218,0,3216,3216,0,0)
payload=b"\1\1"+revision+b"\1"+struct.pack("<Q",0)+b"\0\1"+player+b"\1"+placement+b"\0"+struct.pack("<QII",0,0,0)
print("placed_state",h(b"rustclient:state",payload))
auth_commit=b"\1\1\4"+revision+player+boundary
placement_commit=b"\1\1\5"+placement+bytes([0,0,0,1,0,0,0])
print("placed_stream",h(b"rustclient:commit-stream",auth_commit+placement_commit+b"\0"))
