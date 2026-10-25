#!/usr/bin/env python3
"""Build an isolated Open RuneLite shell around the original pinned 240 jar."""
import argparse
import hashlib
import http.server
import json
import os
from pathlib import Path
import re
import shutil
import subprocess
import threading
import zipfile

ROOT = Path(__file__).resolve().parents[2]
PIN = "25f42961c400bd9dfff1554402441c0ba6d1cffd011163cb9b0b4c42ae194f85"
IGNORED = {"build", ".gradle", ".git", ".idea", "out", ".DS_Store"}


def sha(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def run(args, cwd, env, log):
    with log.open("ab") as output:
        result = subprocess.run(list(map(str, args)), cwd=cwd, env=env,
                                stdout=output, stderr=subprocess.STDOUT)
    if result.returncode:
        raise SystemExit(f"Command failed ({result.returncode}); see {log}")


def prepare(args):
    source = args.source.resolve()
    if not (source / "runelite-client/src/main/java/net/runelite/client/RuneLite.java").is_file():
        raise SystemExit("--source must name the supplied Open RuneLite source checkout")
    jar = ROOT / "references/gamepacks/osrs-240/injected-client-1.12.39.jar"
    if sha(jar) != PIN:
        raise SystemExit("Original injected client checksum mismatch")
    java_home = args.java_home or os.environ.get("JAVA_HOME")
    if not java_home and Path("/usr/libexec/java_home").exists():
        java_home = subprocess.check_output(["/usr/libexec/java_home", "-v", "11"], text=True).strip()
    if not java_home:
        raise SystemExit("Select JDK 11 with --java-home or JAVA_HOME")
    java_home = Path(java_home).resolve()
    java = java_home / "bin/java"
    version = subprocess.check_output([str(java), "-version"], stderr=subprocess.STDOUT, text=True)
    if 'version "11.' not in version:
        raise SystemExit("This reference launcher requires JDK 11 (including javac)")
    files = sorted(p for p in source.rglob("*") if p.is_file()
                   and not IGNORED.intersection(p.relative_to(source).parts))
    source_hashes = {str(p.relative_to(source)): sha(p) for p in files}
    digest = hashlib.sha256(json.dumps(source_hashes, sort_keys=True).encode()).hexdigest()
    output = args.output.resolve()
    output.mkdir(parents=True, exist_ok=True)
    scratch = output / ("source-" + digest[:16])
    if not scratch.exists():
        shutil.copytree(source, scratch, ignore=lambda _, names: IGNORED.intersection(names))
        # Upstream processResources executes git; give the scratch copy its own identity.
        subprocess.run(["git", "init", "-q", str(scratch)], check=True)
        subprocess.run(["git", "add", "."], cwd=scratch, check=True)
        subprocess.run(["git", "-c", "user.name=RustClient Reference",
                        "-c", "user.email=reference@localhost", "commit", "-qm",
                        "Reference source snapshot " + digest], cwd=scratch, check=True)
    (output / "source-manifest.json").write_text(json.dumps({
        "source": str(source), "files": source_hashes, "sha256": digest,
        "injectedJarSha256": PIN, "java": version.strip(),
        "adapter": "Local240Agent: one public RSA modulus constant; no rendering/input patches",
    }, indent=2) + "\n")
    init = output / "oracle.init.gradle"
    init.write_text("""gradle.projectsEvaluated {
    if (gradle.rootProject.name != 'runelite') return
    def client = gradle.rootProject.project(':client')
    client.configurations.runtimeClasspath.exclude(group: 'net.runelite', module: 'injected-client')
    // Runtime dependencies declared by the pinned injected-client 1.12.39 POM.
    client.dependencies.add('runtimeOnly', 'org.json:json:20090211')
    client.dependencies.add('runtimeOnly', 'net.runelite:rlicn:1.1:windows')
    client.tasks.register('oracleClasspath') {
        dependsOn client.tasks.named('classes')
        doLast {
            def jar = new File(System.getProperty('oracle.jar'))
            new File(System.getProperty('oracle.classpath')).text = (client.sourceSets.main.runtimeClasspath + client.files(jar)).asPath
        }
    }
}
""")
    env = dict(os.environ, JAVA_HOME=str(java_home))
    classpath = output / "classpath.txt"
    run(["bash", "./gradlew", "--no-daemon", "-I", init,
         "-Doracle.jar=" + str(jar), "-Doracle.classpath=" + str(classpath),
         ":client:oracleClasspath"], scratch, env, output / "build.log")
    bridge = output / "bridge-classes"
    bridge.mkdir(exist_ok=True)
    run([java_home / "bin/javac", "--release", "11", "-cp", classpath.read_text(),
         "-d", bridge, *sorted((ROOT / "tools/parity/reference").glob("*.java"))],
        ROOT, env, output / "build.log")
    vendor = ROOT / "tools/client-240/vendor/tiny_deps"
    asm = [vendor / name for name in ("asm-9.6.jar", "asm-tree-9.6.jar")]
    classes = output / "agent-classes"
    classes.mkdir(exist_ok=True)
    run([java_home / "bin/javac", "--release", "11", "-cp", os.pathsep.join(map(str, asm)),
         "-d", classes, ROOT / "tools/parity/Local240Agent.java"], ROOT, env, output / "build.log")
    agent = output / "local240-agent.jar"
    with zipfile.ZipFile(agent, "w", zipfile.ZIP_DEFLATED) as archive:
        archive.writestr("META-INF/MANIFEST.MF", "Manifest-Version: 1.0\nPremain-Class: Local240Agent\n\n")
        for dependency in asm:
            with zipfile.ZipFile(dependency) as library:
                for name in library.namelist():
                    if name.endswith(".class") and name != "module-info.class":
                        archive.writestr(name, library.read(name))
        for p in classes.rglob("*.class"):
            archive.write(p, str(p.relative_to(classes)))
    print(f"Built reference shell: {output}", flush=True)
    return output, java, classpath, agent, bridge


def reference_preferences(output, source):
    """Disable upstream automation/plugins in this launcher's isolated profile only."""
    keys = {"runelite.rustclientreference": "true"}
    for path in (source / "runelite-client/src/main/java/net/runelite/client/plugins").rglob("*.java"):
        text = path.read_text()
        if "@PluginDescriptor(" not in text:
            continue
        name = re.search(r"public (?:final )?class (\w+)", text)
        config = re.search(r'configName\s*=\s*"([^"]+)"', text)
        if name:
            keys["runelite." + (config[1] if config else name[1]).lower()] = "false"
    home = output / "home"
    config_dir = home / ".runelite"
    config_dir.mkdir(parents=True, exist_ok=True)
    paths = [config_dir / "settings.properties", *config_dir.glob("profiles2/*.properties")]
    for path in paths:
        old = path.read_text(encoding="latin1").splitlines() if path.exists() else []
        old = [line for line in old if line.split("=", 1)[0] not in keys]
        old.extend(key + "=" + value for key, value in sorted(keys.items()))
        path.write_text("\n".join(old) + "\n", encoding="latin1")
    return home


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--source", type=Path, default=ROOT / "references/open-runelite-rev-240")
    parser.add_argument("--java-home", type=Path)
    parser.add_argument("--output", type=Path, default=ROOT / "target/runelite-240-runtime")
    parser.add_argument("--prepare-only", action="store_true")
    parser.add_argument("--jav-config", type=Path, help="Revision-240 jav_config template")
    parser.add_argument("--public-key", type=Path, help="OpenRune public client.key")
    args = parser.parse_args()
    if not args.prepare_only and (not args.jav_config or not args.public_key):
        parser.error("Running requires --jav-config and --public-key")
    output, java, classpath, agent, bridge = prepare(args)
    if args.prepare_only:
        return
    # Use a local config, isolated preferences/cache, and no external plugin hub.
    lines = args.jav_config.read_text().splitlines()
    if "param=25=240" not in lines and "param=25=240.2" not in lines:
        raise SystemExit("jav_config template must specify revision 240")
    replacements = {"title": "OpenRune 240 Reference", "codebase": "http://127.0.0.1/",
                    "cachedir": "rustclient-reference-240"}
    for index, line in enumerate(lines):
        key = line.split("=", 1)[0]
        if key in replacements:
            lines[index] = key + "=" + replacements[key]
    web = output / "web"
    web.mkdir(exist_ok=True)
    (web / "jav_config.ws").write_text("\n".join(lines) + "\n")
    class Handler(http.server.SimpleHTTPRequestHandler):
        def __init__(self, *a, **kw):
            super().__init__(*a, directory=str(web), **kw)

        def log_message(self, *_):
            pass
    with http.server.ThreadingHTTPServer(("127.0.0.1", 0), Handler) as server:
        threading.Thread(target=server.serve_forever, daemon=True).start()
        home = reference_preferences(output, args.source)
        command = [str(java), "-ea", "-Duser.home=" + str(home), "-Djava.net.preferIPv4Stack=true",
                   "-Drustclient.reference.jar=" + str(ROOT / "references/gamepacks/osrs-240/injected-client-1.12.39.jar"),
                   "-Drustclient.reference.endpoint=" + str(output / "endpoint.json"),
                   "-javaagent:" + str(agent) + "=" + str(args.public_key.resolve()),
                   "-cp", str(bridge) + os.pathsep + classpath.read_text(), "net.runelite.client.RuneLite",
                   "--safe-mode", "--developer-mode", "--noupdate", "--jav_config",
                   f"http://127.0.0.1:{server.server_port}/jav_config.ws"]
        print(f"Starting reference client; log: {output / 'runtime.log'}", flush=True)
        try:
            run(command, output, os.environ, output / "runtime.log")
        finally:
            server.shutdown()


if __name__ == "__main__":
    main()
