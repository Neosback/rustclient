package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.analysis.packet.PacketReadCandidate;
import dev.neosback.deob.analysis.packet.PacketReadDiscovery;
import dev.neosback.deob.bytecode.InstructionIndex;
import dev.neosback.deob.corpus.ClassArtifact;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.identity.ClassId;
import dev.neosback.deob.identity.InstructionId;
import dev.neosback.deob.identity.MethodId;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;

/** Expands rev-specific sync roots through a bounded exact in-corpus call graph. */
public final class InterproceduralSyncAnalyzer {
    public SyncCallGraphReport analyze(JarCorpus corpus, PacketTable serverPackets, int maxDepth) {
        if (maxDepth < 0 || maxDepth > 8) {
            throw new IllegalArgumentException("maxDepth must be between 0 and 8");
        }

        Rev240SyncReport sync = new Rev240SyncAnalyzer().analyze(corpus, serverPackets);
        Map<MethodId, MethodNode> methods = methodIndex(corpus);
        Set<MethodId> bitMethods = bitMethods(corpus);
        Set<MethodId> readMethods = readMethods(corpus);
        List<SyncReachableMethod> reachable = new ArrayList<>();
        List<String> warnings = new ArrayList<>();

        for (SyncRoleCandidate root : sync.candidates()) {
            if (root.role() == SyncRole.UNKNOWN) {
                continue;
            }
            if (!methods.containsKey(root.method())) {
                warnings.add("sync root is outside the target corpus: " + root.method());
                continue;
            }

            Deque<State> queue = new ArrayDeque<>();
            Set<MethodId> visited = new HashSet<>();
            queue.addLast(new State(root.method(), 0, List.of()));

            while (!queue.isEmpty()) {
                State state = queue.removeFirst();
                if (!visited.add(state.method())) {
                    continue;
                }
                MethodNode method = methods.get(state.method());
                if (method == null) {
                    continue;
                }

                MethodFacts facts = scan(state.method(), method, bitMethods, readMethods, methods.keySet());
                reachable.add(new SyncReachableMethod(
                        root.method(),
                        state.method(),
                        root.role(),
                        root.packetNames(),
                        state.depth(),
                        state.callPath(),
                        facts.bitAccessCalls(),
                        facts.packetReadCalls(),
                        facts.fieldWrites(),
                        facts.branches(),
                        facts.callSites().size()));

                if (state.depth() >= maxDepth) {
                    continue;
                }
                for (CallSite call : facts.callSites()) {
                    if (visited.contains(call.target())) {
                        continue;
                    }
                    List<InstructionId> path = new ArrayList<>(state.callPath());
                    path.add(call.site());
                    queue.addLast(new State(call.target(), state.depth() + 1, List.copyOf(path)));
                }
            }
        }

        reachable.sort(Comparator
                .comparing(SyncReachableMethod::root)
                .thenComparingInt(SyncReachableMethod::depth)
                .thenComparing(SyncReachableMethod::method));
        warnings.sort(String::compareTo);
        return new SyncCallGraphReport(
                corpus.sha256(), serverPackets.revision(), maxDepth, reachable, warnings);
    }

    private static Map<MethodId, MethodNode> methodIndex(JarCorpus corpus) {
        Map<MethodId, MethodNode> result = new HashMap<>();
        for (ClassArtifact artifact : corpus.classes().values()) {
            var node = artifact.readNode();
            ClassId owner = artifact.id();
            for (MethodNode method : node.methods) {
                result.put(new MethodId(owner, method.name, method.desc), method);
            }
        }
        return result;
    }

    private static Set<MethodId> bitMethods(JarCorpus corpus) {
        Set<MethodId> result = new HashSet<>();
        for (BitAccessCandidate candidate : new BitAccessAnalyzer().analyze(corpus).candidates()) {
            result.add(candidate.method());
        }
        return result;
    }

    private static Set<MethodId> readMethods(JarCorpus corpus) {
        Set<MethodId> result = new HashSet<>();
        for (PacketReadCandidate candidate : new PacketReadDiscovery().analyze(corpus).candidates()) {
            result.add(candidate.method());
        }
        return result;
    }

    private static MethodFacts scan(
            MethodId methodId,
            MethodNode method,
            Set<MethodId> bitMethods,
            Set<MethodId> readMethods,
            Set<MethodId> inCorpus) {
        InstructionIndex index = InstructionIndex.of(method);
        int bitCalls = 0;
        int readCalls = 0;
        int fieldWrites = 0;
        int branches = 0;
        List<CallSite> calls = new ArrayList<>();

        for (int i = 0; i < index.size(); i++) {
            AbstractInsnNode instruction = index.instruction(i);
            int opcode = instruction.getOpcode();
            if (instruction instanceof MethodInsnNode call) {
                MethodId target = new MethodId(new ClassId(call.owner), call.name, call.desc);
                if (bitMethods.contains(target)) bitCalls++;
                if (readMethods.contains(target)) readCalls++;
                if (inCorpus.contains(target)) {
                    calls.add(new CallSite(target, new InstructionId(methodId, i)));
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
        calls.sort(Comparator.comparing(CallSite::target).thenComparing(call -> call.site().opcodeIndex()));
        return new MethodFacts(bitCalls, readCalls, fieldWrites, branches, calls);
    }

    private record State(MethodId method, int depth, List<InstructionId> callPath) {}
    private record CallSite(MethodId target, InstructionId site) {}
    private record MethodFacts(
            int bitAccessCalls,
            int packetReadCalls,
            int fieldWrites,
            int branches,
            List<CallSite> callSites) {}
}
