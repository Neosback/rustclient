// Run with: npx --yes --package=tsx tsx tools/tsps/export-fixtures.mts /path/to/tsps
// The source repository is read-only. Generated outputs go under target/.
import { readFileSync, mkdirSync, writeFileSync } from "node:fs";
import { resolve, join } from "node:path";
import { pathToFileURL } from "node:url";
import { createHash } from "node:crypto";
import { execFileSync } from "node:child_process";

const source = resolve(process.argv[2] ?? "");
const fixtureRoot = "crates/protocol/tests/fixtures/tsps-241";
const pins = JSON.parse(readFileSync(`${fixtureRoot}/sources.json`, "utf8"));
if (execFileSync("git", ["-C", source, "rev-parse", "HEAD"], { encoding: "utf8" }).trim() !== pins.commit) {
    throw new Error("TSPS commit differs from the fixture baseline");
}
for (const file of pins.files) {
    if (createHash("sha256").update(readFileSync(join(source, file.path))).digest("hex") !== file.sha256) {
        throw new Error(`Changed source: ${file.path}`);
    }
}
const server = await import(pathToFileURL(join(source, "server/src/main/typescript/elvarg/net/protocol/ClientProtocol.ts")).href);
const { ClientBinaryEncoder } = await import(pathToFileURL(join(source, "client/network/packet/ClientBinaryEncoder.ts")).href);
const client = new ClientBinaryEncoder();
const appearance = { gender: 0, colors: [0, 0, 0, 0, 0], kits: [0, 10, 18, 26, 33, 36, 42], equip: Array(14).fill(-1) };
const local = { index:1,x:3200,y:3200,level:0,appearance:server.encodePlayerAppearance(appearance,"Test",3,0,[808,823,819,820,821,822,824]) };
const playerState = server.createPlayerSyncState(1,local);
const idle = server.encodePlayerSync(1,3152,3152,2,[local],playerState);
const shifted = {...local,x:3208};
const shift = server.encodePlayerSync(1,3160,3152,3,[shifted],playerState);
const repeated = server.encodePlayerSync(1,3160,3152,4,[shifted],playerState);
const cases: Record<string, Uint8Array> = {
    "login-success": server.encodeLoginResponse(true),
    "login-rejected": server.encodeLoginResponse(false, 3, "Invalid username or password."),
    "handshake": server.encodeHandshake(1, "Test", false, appearance, [], true),
    "animations": server.encodeDefaultAnimations(),
    "initial-placement": server.encodeInitialPlayerSync(1, 3200, 3200, 0, 1),
    "varp-small": server.encodeVarp(173, 1),
    "varp-large": server.encodeVarp(1000, 123456),
    "inventory": server.encodeInventorySnapshot([{ slot: 0, itemId: 995, quantity: 100000 }]),
    "logout": server.encodeLogoutResponse(),
    "walk": client.encodeWalk(3201, 3202, false, 1),
    "login-request": client.encodeLogin("Test", "fixture", 241, 0),
    "chat-request": client.encodeChat("hello"),
    "count-request": client.encodeResumeCountdialog(123),
    "widget-request": client.encodeWidgetAction({ widgetId:149<<16, groupId:149, childId:0,
        opId:1, buttonNum:1, cursorX:0, cursorY:0, isPrimary:true, slot:3, itemId:995 }),
    "appearance-naked": server.encodePlayerAppearance(appearance,"Test",3,0,[808,823,819,820,821,822,824]),
    "appearance-ring": server.encodePlayerAppearance({...appearance,equip:appearance.equip.map((id:number,index:number)=>index===12?1635:id)},"Test",3,0,[808,823,819,820,821,822,824]),
    "client-script": server.encodeRunClientScript(1234, ["hello", 42]),
    "widget-script": server.encodeWidgetRunScript(1234, ["hello", 42, -1234567890123n], {173:1}, {}, {93:{capacity:28,slots:[{slot:0,itemId:995,quantity:100000}]}}),
    "widget-script-varbit": server.encodeWidgetRunScript(1234, [], {}, {1:1}),
    "interface-open": server.encodeWidgetOpenSub((548<<16)|20,149,1,{varps:{173:1},hiddenUids:[149<<16]}),
    "interface-scoped-script": server.encodeWidgetOpenSub((548<<16)|20,149,1,{preScripts:[{scriptId:1234,args:["hello"]}]}),
    "interface-flags": server.encodeWidgetSetFlags(149<<16,3),
    "interface-range": server.encodeWidgetSetFlagsRange(149<<16,0,27,0x100002),
    "interface-range-v2": server.encodeWidgetSetFlagsRangeV2(149<<16,-1,27,3,0x80000001),
    "varbit": server.encodeVarbit(1,1),
    "interface-varbit": server.encodeWidgetOpenSub((548<<16)|20,149,1,{varbits:{1:1}}),
    "player-idle": idle,
    "player-map-shift": shift,
    "player-map-repeat": repeated,
    "script-inventory-limit": server.encodeWidgetRunScript(1234,[],{},{},{93:{capacity:16384,slots:[]},95:{capacity:16384,slots:[]}}),
    "logout-reason": server.encodeLogoutResponse("Server restarting"),
};
const out = resolve("target/tsps-fixtures");
mkdirSync(out, { recursive: true });
const results = [];
for (const [name, bytes] of Object.entries(cases)) {
    writeFileSync(join(out, `${name}.bin`), bytes);
    results.push({ name, bytes: bytes.length, sha256: createHash("sha256").update(bytes).digest("hex") });
}
writeFileSync(join(out, "generated.json"), JSON.stringify({ sourceCommit: pins.commit, cases: results }, null, 2) + "\n");
console.log(`Exported ${results.length} source-produced cases to ${out}`);
const expected = JSON.parse(readFileSync(join(fixtureRoot, "generated.json"), "utf8"));
if (JSON.stringify(expected) !== JSON.stringify({ sourceCommit: pins.commit, cases: results })) {
    throw new Error("Generated fixture manifest differs; inspect target/tsps-fixtures before updating evidence");
}
for (const result of results) {
    if (!readFileSync(join(fixtureRoot, `${result.name}.bin`)).equals(cases[result.name])) {
        throw new Error(`Committed fixture differs: ${result.name}`);
    }
}
console.log("All source-produced bytes match the recorded fixtures");
