#!/usr/bin/env python3
"""Run literal pinned Java projection/bounds plus uploader face/depth expressions.

Scaffolding supplies integer posed inputs; this does not run a gamepack, float animation,
world views, source GPU frames or the complete uploader. Reference files remain unchanged.
"""
import argparse
import hashlib
import json
import os
from pathlib import Path
import shutil
import subprocess

ROOT = Path(__file__).resolve().parents[2]
PROJECTION = 'sources-semantic/IntProjection.java'
MODEL = 'sources-semantic/Model.java'
CLIENT = 'sources-semantic/client.java'
UPLOADER = 'runelite-client/src/main/java/net/runelite/client/plugins/gpu/ModelUploader.java'


def method(source, signature):
    start = source.index(signature)
    opening = source.index('{', start)
    nesting = 1
    end = opening + 1
    while nesting:
        nesting += (source[end] == '{') - (source[end] == '}')
        end += 1
    return source[start:end]


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--java-home', type=Path,
                        default=Path(os.environ['JAVA_HOME']) if os.environ.get('JAVA_HOME') else None)
    parser.add_argument('--output', type=Path, default=ROOT / 'target/gpu-model-sort-oracle')
    args = parser.parse_args()
    manifest = json.loads((ROOT / 'docs/reference/source-manifest.json').read_text())
    pinned = {}
    hashes = {}
    for source_id, name in [('rl-suite-240', PROJECTION), ('rl-suite-240', MODEL), ('rl-suite-240', CLIENT),
                            ('open-runelite-240', UPLOADER)]:
        baseline = next(s for s in manifest['sources'] if s['id'] == source_id)
        path = ROOT / baseline['root'] / name
        expected = next(f['sha256'] for f in baseline['files'] if f['path'] == name)
        digest = hashlib.sha256(path.read_bytes()).hexdigest()
        if digest != expected:
            raise SystemExit(f'Pinned source hash mismatch: {path}')
        pinned[name] = path.read_text()
        hashes[str(path.relative_to(ROOT))] = digest
    project = method(pinned[PROJECTION], 'public float[] project(float var1, float var2, float var3, float[] var4)')
    bounds = method(pinned[MODEL], 'public static void method5254(')
    angle = method(pinned[CLIENT], 'public static float method2268(')
    front = '(aX - bX) * (cY - bY) - (cX - bX) * (aY - bY) > 0'
    depth = 'radius + (distances[v1] + distances[v2] + distances[v3]) / 3'
    assert front in pinned[UPLOADER] and depth in pinned[UPLOADER]
    program = '''public class ModelSortOracle {
      static class Projection {
        float field1926,field1929,field1928,field1934,field1930,field1933,field1931;
        ANGLE
        Projection(int[] eye,int yaw,int pitch) {
          field1926=eye[0];field1929=eye[1];field1928=eye[2];
          float yawAngle=method2268(yaw*8), pitchAngle=method2268(pitch*8);
          field1930=(float)Math.sin(yawAngle);field1934=(float)Math.cos(yawAngle);
          field1931=(float)Math.sin(pitchAngle);field1933=(float)Math.cos(pitchAngle);
        }
        PROJECT
      }
      static class Model {
        int field2491,field2422,field2490,field1741,field2488,field2487,field2489;
        float[] field2425,field2428,field2424;
        void method5292() { throw new AssertionError(); }
        BOUNDS
      }
      static int depth(Projection view,int[] origin,int[][] points,int radius) {
        int zero=(int)view.project(origin[0],origin[1],origin[2],new float[3])[2];
        int[] distances=new int[3]; float[][] p=new float[3][];
        for(int i=0;i<3;i++) {
          p[i]=view.project(points[i][0],points[i][1],points[i][2],new float[3]);
          distances[i]=(int)p[i][2]-zero;
        }
        float aX=p[0][0]/p[0][2],aY=p[0][1]/p[0][2],bX=p[1][0]/p[1][2],
          bY=p[1][1]/p[1][2],cX=p[2][0]/p[2][2],cY=p[2][1]/p[2][2];
        if(!(FRONT)) return Integer.MIN_VALUE;
        int v1=0,v2=1,v3=2;
        return DEPTH;
      }
      public static void main(String[] args) {
        Projection view=new Projection(new int[]{13,-70,29},321,177);
        int[][] points={{-100,100,800},{100,100,800},{0,-100,800}};
        for(int[] point:points) {
          float[] p=view.project(point[0],point[1],point[2],new float[3]);
          System.out.printf("%d %d %d%n",Float.floatToIntBits(p[0]),Float.floatToIntBits(p[1]),Float.floatToIntBits(p[2]));
        }
        System.out.println(depth(view,new int[]{0,0,800},points,200));
        System.out.println(depth(view,new int[]{0,0,800},new int[][]{points[0],points[2],points[1]},200));
        // A negative fractional sum exercises Java signed division toward zero.
        points[2][2]=807;
        System.out.println(depth(view,new int[]{0,0,900},points,200));
        Model model=new Model(); model.field2422=2;
        model.field2425=new float[]{3,1};model.field2428=new float[]{-4,2};model.field2424=new float[]{4,1};
        Model.method5254(model);
        System.out.printf("%d %d%n",model.field2487,model.field2489);
      }
    }'''.replace('PROJECT', project).replace('BOUNDS', bounds).replace('ANGLE', angle).replace('FRONT', front).replace('DEPTH', depth)
    def java_tool(name):
        path = args.java_home / 'bin' / name if args.java_home else shutil.which(name)
        if not path or not Path(path).is_file():
            raise SystemExit(f'{name} unavailable; select a JDK with --java-home or JAVA_HOME')
        return str(path)
    args.output.mkdir(parents=True, exist_ok=True)
    harness = args.output / 'ModelSortOracle.java'
    harness.write_text(program)
    subprocess.run([java_tool('javac'), '-d', str(args.output), str(harness)],
                   check=True, timeout=30, capture_output=True)
    result = subprocess.check_output([java_tool('java'), '-cp', str(args.output), 'ModelSortOracle'],
                                     text=True, timeout=30)
    rows = [[int(v) for v in line.split()] for line in result.splitlines()]
    expected = [[1141962888, -1024009660, 1141202776],
                [1143775013, -1038792472, 1136880072],
                [1142868951, -1015309716, 1135831014],
                [217], [-2147483648], [172], [7, 13]]
    report = {'sourceSha256': hashes, 'rows': rows, 'expected': expected, 'matched': rows == expected,
              'limits': 'Literal arithmetic on integer posed inputs; full uploader/runtime/visual parity NOT-RUN.'}
    (args.output / 'result.json').write_text(json.dumps(report, indent=2) + '\n')
    print(json.dumps(report, indent=2))
    if not report['matched']:
        raise SystemExit(1)


if __name__ == '__main__':
    main()
