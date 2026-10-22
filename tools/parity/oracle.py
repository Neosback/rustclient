#!/usr/bin/env python3
"""Stage immutable reference/cache copies, build independent oracles, capture evidence."""
import argparse, hashlib, json, os, pathlib, shutil, subprocess
ROOT=pathlib.Path(__file__).resolve().parents[2]
def sha(path):return hashlib.sha256(path.read_bytes()).hexdigest()
def main():
    ap=argparse.ArgumentParser()
    ap.add_argument("--reference",type=pathlib.Path,default=ROOT/"references/rustosrs")
    ap.add_argument("--cache",type=pathlib.Path,required=True)
    ap.add_argument("--output",type=pathlib.Path,default=ROOT/"target/parity-oracle/reproducible")
    ap.add_argument("--camera",nargs=6,type=float,default=[4352,-2400,2304,0,0.75,1108.5125])
    args=ap.parse_args()
    reference=args.reference.resolve();cache=args.cache.resolve();out=args.output.resolve()
    manifest=json.loads((ROOT/"tools/parity/reference-manifest.json").read_text())
    if reference==out or reference in out.parents or cache==out or cache in out.parents:
        raise SystemExit("scratch output must be outside reference and original cache")
    commit=subprocess.check_output(["git","-C",str(reference),"rev-parse","HEAD"],text=True).strip()
    if commit!=manifest["reference_commit"]:raise SystemExit("reference commit differs")
    if subprocess.check_output(["git","-C",str(reference),"status","--porcelain"],text=True):
        raise SystemExit("reference tree must be clean")
    for entry in manifest["source_files"]:
        if sha(reference/entry["path"])!=entry["sha256"]:raise SystemExit(f"reference source hash mismatch: {entry['path']}")
    source=out/"source";scratch_cache=out/"cache"
    if not source.exists():
        shutil.copytree(reference,source,ignore=shutil.ignore_patterns(".git","target","node_modules","RuneLite-melxin","OpenRune-Server-main","rs-cache-master",".DS_Store"))
    # Verify every pinned source after staging, including on repeat executions.
    for entry in manifest["source_files"]:
        if sha(source/entry["path"])!=entry["sha256"]:raise SystemExit(f"staged source modified: {entry['path']}")
    scratch_cache.mkdir(parents=True,exist_ok=True)
    cache_hashes={}
    for path in sorted(cache.glob("main_file_cache.*")):
        cache_hashes[path.name]=sha(path)
        dest=scratch_cache/path.name
        if not dest.exists() or sha(dest)!=cache_hashes[path.name]:shutil.copy2(path,dest)
    if not cache_hashes:raise SystemExit("no cache blobs found")
    shutil.copyfile(ROOT/"tools/parity/reference_textures.rs",source/"crates/osrs-world/examples/parity_textures.rs")
    env={k:v for k,v in os.environ.items() if not k.startswith("RENDER_")}
    prefix=["cargo","run","--manifest-path",str(source/"Cargo.toml"),"--ignore-rust-version","-p","osrs-world","--example"]
    with (out/"build.log").open("w") as log:
        subprocess.run(prefix+["parity_textures","--",str(scratch_cache),str(out/"textures")],env=env,stdout=log,stderr=subprocess.STDOUT,check=True)
        subprocess.run(prefix+["render_frame","--",str(scratch_cache),"3168","3168",str(out/"reference.ppm")]+list(map(str,args.camera)),env=env,stdout=log,stderr=subprocess.STDOUT,check=True)
    record={"reference_commit":commit,"cache_blobs_sha256":cache_hashes,"window_origin":[3168,3168],
            "viewport":[1280,720],"camera_fine_radians_scale":args.camera,"variables":"all zero",
            "client_cycle":0,"animation":"pinned placement hash initial phases; smoothing off",
            "settings":manifest["settings"],"source_adjustments":["Added development-only parity_textures example; original reference modules unchanged."],
            "oracle_example_sha256":sha(ROOT/"tools/parity/reference_textures.rs"),
            "reference_ppm_sha256":sha(out/"reference.ppm"),
            "textures":{p.stem:sha(p) for p in sorted((out/"textures").glob("*.rgba"))}}
    (out/"capture-state.json").write_text(json.dumps(record,indent=2)+"\n")
if __name__=="__main__":main()
