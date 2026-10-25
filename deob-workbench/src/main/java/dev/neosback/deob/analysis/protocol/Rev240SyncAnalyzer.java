package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.analysis.cfg.BasicBlock;
import dev.neosback.deob.analysis.cfg.CfgAnalyzer;
import dev.neosback.deob.analysis.cfg.ControlFlowGraph;
import dev.neosback.deob.analysis.packet.PacketReadCandidate;
import dev.neosback.deob.analysis.packet.PacketReadDiscovery;
import dev.neosback.deob.bytecode.InstructionIndex;
import dev.neosback.deob.corpus.ClassArtifact;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.identity.ClassId;
import dev.neosback.deob.identity.InstructionId;
import dev.neosback.deob.identity.MethodId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;

/**
 * Correlates an external rev-240 server-packet profile with exact switch cases and structural
 * packet/bit-read evidence. Packet names affect role labels only; they never replace bytecode facts.
 */
public final class Rev240SyncAnalyzer {
    public Rev240SyncReport analyze(JarCorpus corpus, PacketTable serverPackets) {
        if (serverPackets.direction() != PacketDirection.SERVER_TO_CLIENT) {
            throw new IllegalArgumentException("sync analysis requires a server-to-client packet table");
        }

        List<PacketTableEntry> syncPackets = serverPackets.entries().stream()
                .filter(Rev240SyncAnalyzer::isSyncPacket)
                .toList();
        if (syncPackets.isEmpty()) {
            throw new IllegalArgumentException(
                    "packet table contains no PLAYER_INFO or NPC_INFO_* entries");
        }

        Set<MethodId> bitMethods = new HashSet<>();
        for (BitAccessCandidate candidate : new BitAccessAnalyzer().analyze(corpus).candidates()) {
            bitMethods.add(candidate.method());
        }
        Set<MethodId> readMethods = new HashSet<>();
        for (PacketReadCandidate candidate : new PacketReadDiscovery().analyze(corpus).candidates()) {
            readMethods.add(candidate.method());
        }

        Map<MethodId, MethodNode> methods = new HashMap<>();
        List<PacketDispatchEvidence> dispatch = new ArrayList<>();
        CfgAnalyzer cfgAnalyzer = new CfgAnalyzer();

        for (ClassArtifact artifact : corpus.classes().values()) {
            var classNode = artifact.readNode();
            ClassId owner = artifact.id();
            for (MethodNode method : classNode.methods) {
                MethodId methodId = new MethodId(owner, method.name, method.desc);
                methods.put(methodId, method);
                InstructionIndex index = InstructionIndex.of(method);
                if (index.size() == 0) {
                    continue;
                }
                ControlFlowGraph cfg = cfgAnalyzer.analyze(methodId, method);
                for (int instructionIndex = 0; instructionIndex < index.size(); instructionIndex++) {
                    AbstractInsnNode instruction = index.instruction(instructionIndex);
                    if (instruction instanceof TableSwitchInsnNode table) {
                        for (PacketTableEntry packet : syncPackets) {
                            if (packet.id() < table.min || packet.id() > table.max) {
                                continue;
                            }
                            int labelIndex = packet.id() - table.min;
                            int target = index.targetIndex(table.labels.get(labelIndex));
                            addDispatch(dispatch, methodId, instructionIndex, packet, target,
                                    directCalls(index, cfg, target));
                        }
                    } else if (instruction instanceof LookupSwitchInsnNode lookup) {
                        for (PacketTableEntry packet : syncPackets) {
                            int keyIndex = lookup.keys.indexOf(packet.id());
                            if (keyIndex < 0) {
                                continue;
                            }
                            int target = index.targetIndex(lookup.labels.get(keyIndex));
                            addDispatch(dispatch, methodId, instructionIndex, packet, target,
                                    directCalls(index, cfg, target));
                        }
                    }
                }
            }
        }

        dispatch.sort(Comparator
                .comparing(PacketDispatchEvidence::dispatcher)
                .thenComparingInt(evidence -> evidence.switchInstruction().opcodeIndex())
                .thenComparingInt(PacketDispatchEvidence::packetId));

        Map<MethodId, MutableCandidate> aggregate = new HashMap<>();
        for (PacketDispatchEvidence evidence : dispatch) {
            List<MethodId> targets = evidence.directCalls().isEmpty()
                    ? List.of(evidence.dispatcher())
                    : evidence.directCalls();
            for (MethodId target : targets) {
                MutableCandidate candidate = aggregate.computeIfAbsent(target, MutableCandidate::new);
                candidate.packetNames.add(evidence.packetName());
                candidate.dispatchEvidenceCount++;
            }
        }

        List<SyncRoleCandidate> candidates = new ArrayList<>();
        for (MutableCandidate candidate : aggregate.values()) {
            MethodNode method = methods.get(candidate.method);
            MethodStats stats = method == null
                    ? MethodStats.EMPTY
                    : scanMethod(candidate.method, method, bitMethods, readMethods);
            SyncRole role = role(candidate.packetNames);
            int score = Math.min(60, candidate.packetNames.size() * 20)
                    + Math.min(18, stats.bitAccessCalls * 6)
                    + Math.min(15, stats.packetReadCalls * 3)
                    + Math.min(10, stats.fieldWrites)
                    + Math.min(10, stats.branches)
                    + Math.min(8, stats.calls);
            candidates.add(new SyncRoleCandidate(
                    candidate.method,
                    role,
                    List.copyOf(candidate.packetNames),
                    candidate.dispatchEvidenceCount,
                    stats.bitAccessCalls,
                    stats.packetReadCalls,
                    stats.fieldWrites,
                    stats.branches,
                    stats.calls,
                    score));
        }
        candidates.sort(Comparator
                .comparingInt(SyncRoleCandidate::evidenceScore).reversed()
                .thenComparing(SyncRoleCandidate::method));

        return new Rev240SyncReport(
                corpus.sha256(), serverPackets.revision(), dispatch, candidates);
    }

    private static boolean isSyncPacket(PacketTableEntry entry) {
        return "PLAYER_INFO".equals(entry.name()) || entry.name().startsWith("NPC_INFO_");
    }

    private static void addDispatch(
            List<PacketDispatchEvidence> target,
            MethodId dispatcher,
            int switchIndex,
            PacketTableEntry packet,
            int targetInstruction,
            List<MethodId> calls) {
        if (targetInstruction < 0) {
            return;
        }
        target.add(new PacketDispatchEvidence(
                dispatcher,
                new InstructionId(dispatcher, switchIndex),
                packet.id(),
                packet.name(),
                packet.length(),
                targetInstruction,
                calls));
    }

    private static List<MethodId> directCalls(
            InstructionIndex index,
            ControlFlowGraph cfg,
            int targetInstruction) {
        BasicBlock block = cfg.blocks().stream()
                .filter(candidate -> candidate.contains(targetInstruction))
                .findFirst()
                .orElse(null);
        if (block == null) {
            return List.of();
        }
        Set<MethodId> calls = new LinkedHashSet<>();
        for (int i = block.startInstruction(); i < block.endInstructionExclusive(); i++) {
            AbstractInsnNode instruction = index.instruction(i);
            if (instruction instanceof MethodInsnNode call) {
                calls.add(new MethodId(new ClassId(call.owner), call.name, call.desc));
            }
        }
        return calls.stream().sorted().toList();
    }

    private static MethodStats scanMethod(
            MethodId methodId,
            MethodNode method,
            Set<MethodId> bitMethods,
            Set<MethodId> readMethods) {
        InstructionIndex index = InstructionIndex.of(method);
        int bitCalls = 0;
        int readCalls = 0;
        int fieldWrites = 0;
        int branches = 0;
        int calls = 0;

        for (AbstractInsnNode instruction : index.instructions()) {
            int opcode = instruction.getOpcode();
            if (instruction instanceof MethodInsnNode call) {
                calls++;
                MethodId called = new MethodId(new ClassId(call.owner), call.name, call.desc);
                if (bitMethods.contains(called)) {
                    bitCalls++;
                }
                if (readMethods.contains(called)) {
                    readCalls++;
                }
            }
            if (instruction instanceof FieldInsnNode
                    && (opcode == Opcodes.PUTFIELD || opcode == Opcodes.PUTSTATIC)) {
                fieldWrites++;
            }
            if (instruction instanceof JumpInsnNode
                    || instruction instanceof TableSwitchInsnNode
                    || instruction instanceof LookupSwitchInsnNode) {
                branches++;
            }
        }
        return new MethodStats(bitCalls, readCalls, fieldWrites, branches, calls);
    }

    private static SyncRole role(Set<String> packetNames) {
        boolean player = packetNames.stream().anyMatch("PLAYER_INFO"::equals);
        boolean npc = packetNames.stream().anyMatch(name -> name.startsWith("NPC_INFO_"));
        if (player && npc) {
            return SyncRole.MIXED;
        }
        if (player) {
            return SyncRole.PLAYER_LIKE;
        }
        if (npc) {
            return SyncRole.NPC_LIKE;
        }
        return SyncRole.UNKNOWN;
    }

    private static final class MutableCandidate {
        private final MethodId method;
        private final Set<String> packetNames = new TreeSet<>();
        private int dispatchEvidenceCount;

        private MutableCandidate(MethodId method) {
            this.method = method;
        }
    }

    private record MethodStats(
            int bitAccessCalls,
            int packetReadCalls,
            int fieldWrites,
            int branches,
            int calls) {
        private static final MethodStats EMPTY = new MethodStats(0, 0, 0, 0, 0);
    }
}
