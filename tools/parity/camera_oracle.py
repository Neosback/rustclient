#!/usr/bin/env python3
"""Execute literal camera arithmetic extracted from the pinned Java deob.

Only generated scratch files are compiled. The original sources are read-only.
"""
import argparse, hashlib, json, pathlib, subprocess

COMMIT="1ad572d7dcdbc0fb67a4a00f0c2f959d5ab25abc"
def main():
    ap=argparse.ArgumentParser()
    ap.add_argument("deob",type=pathlib.Path)
    ap.add_argument("--output",type=pathlib.Path,default=pathlib.Path("target/parity-oracle/camera"))
    args=ap.parse_args()
    if subprocess.check_output(["git","-C",str(args.deob),"rev-parse","HEAD"],text=True).strip()!=COMMIT:
        raise SystemExit("deob commit differs from pinned reference")
    src=args.deob/"runescape-client/src/main/java"
    client=(src/"Client.java").read_text()
    begin=client.index("if (MouseHandler.MouseHandler_currentButton == 4 && UrlRequest.mouseCam)")
    end=client.index("if (field412)",begin)
    angles=client[begin:end]
    for old,new in {
        "MouseHandler.MouseHandler_currentButton":"button", "UrlRequest.mouseCam":"true",
        "MouseHandler.MouseHandler_y":"my","MouseHandler.MouseHandler_x":"mx",
        "indexCheck.isValidIndexInRange(96)":"left","indexCheck.isValidIndexInRange(97)":"right",
        "indexCheck.isValidIndexInRange(98)":"up","indexCheck.isValidIndexInRange(99)":"down",
    }.items(): angles=angles.replace(old,new)
    camera=(src/"class33.java").read_text()
    begin=camera.index("int var4 = Client.camAngleX;")
    end=camera.index("if (Client.isCameraLocked)",begin)
    pose=camera[begin:end]
    pose=pose.replace("Client.","").replace("class40.oculusOrbFocalPointX","fx").replace("class289.oculusOrbFocalPointZ","fy").replace("class4.oculusOrbFocalPointY","fz")
    pose=pose.replace("GraphicsObject.method1936(var4)","(var4 * 3 + 600)")
    pose=pose.replace("Rasterizer3D.Rasterizer3D_sine","sine").replace("Rasterizer3D.Rasterizer3D_cosine","cosine")
    # field543 is all false: the excluded server camera-shake path.
    code="""public class CameraOracle {
static int camAngleX=128, camAngleY=0, camAngleDX=0,camAngleDY=0;
static int mouseCamClickedX=0, mouseCamClickedY=0,field447=0;
static int fx=6848,fy=-290,fz=6464,zoomWidth=320,zoomHeight=256;
static boolean[] field543=new boolean[5];static int[] field545=new int[5];
static int[] sine=new int[2048],cosine=new int[2048];
static void step(boolean left,boolean right,boolean up,boolean down,int button,int mx,int my) {
int var5,var6;
"""+angles+"""
}
static void pose(int var3,int width) {
"""+pose+"""
int zoom=var3*(256+(205-256)*Math.min(100,Math.max(0,var3-334))/100)/334;
System.out.printf(",%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d%n",
camAngleY,camAngleX,camAngleDY,camAngleDX,field447,
var6-var13,var7-var14,var8-var15,var5,var4,zoom);
}
public static void main(String[] args) {
for(int i=0;i<2048;i++){sine[i]=(int)(Math.sin(i*0.0030679615D)*65536.0D);cosine[i]=(int)(Math.cos(i*0.0030679615D)*65536.0D);}
System.out.println("# cycle,left,right,up,down,middle,mx,my,targetX,targetZ,height,drop,width,height,yaw,pitch,yawVelocity,pitchVelocity,terrainDistance,cameraX,cameraY,cameraZ,poseYaw,posePitch,zoom");
for(int cycle=0;cycle<400;cycle++){
boolean left=cycle<80,right=cycle>=100&&cycle<260,up=cycle<100,down=cycle>=200&&cycle<300;
boolean middle=cycle>=300&&cycle<340;
int mx=cycle<300?50:50+(cycle-300)*3;
int my=cycle<300?80:80-(cycle-300)*2;
int tx=cycle<350?6848:7008,tz=cycle<360?6464:6528;
int height=-240,drop=cycle>=120&&cycle<180?400:0;
if(fx-tx>=-500&&fx-tx<=500&&fz-tz>=-500&&fz-tz<=500){fx+=(tx-fx)/16;fz+=(tz-fz)/16;}else{fx=tx;fz=tz;}
fy=height-50;
int distance=Math.min(98048,Math.max(32768,drop*192));
if(distance>field447)field447+=(distance-field447)/24;else if(distance<field447)field447+=(distance-field447)/80;
step(left,right,up,down,middle?4:0,mx,my);
int width=cycle<200?765:1024,h=cycle<200?503:334;
System.out.printf("%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d",cycle,left?1:0,right?1:0,up?1:0,down?1:0,middle?1:0,mx,my,tx,tz,height,drop,width,h);
pose(h,width);
}
}}
"""
    out=args.output.resolve();out.mkdir(parents=True,exist_ok=True)
    java=out/"CameraOracle.java";java.write_text(code)
    subprocess.run(["javac",str(java)],check=True)
    result=subprocess.check_output(["java","-cp",str(out),"CameraOracle"],text=True)
    (out/"trace.csv").write_text(result)
    manifest={"commit":COMMIT,"sources":{p:hashlib.sha256((src/p).read_bytes()).hexdigest() for p in ["Client.java","class33.java","Rasterizer3D.java","SoundCache.java"]},
        "adjustments":["Literal held-key/middle-mouse block with input substitutions; literal class33 pose block with field substitutions; shake disabled; 400 deterministic input cycles; extracted default viewport zoom arithmetic."],
        "generated_java_sha256":hashlib.sha256(code.encode()).hexdigest(),"trace_sha256":hashlib.sha256(result.encode()).hexdigest()}
    (out/"provenance.json").write_text(json.dumps(manifest,indent=2)+"\n")
if __name__=="__main__":main()
