package dev.neosback.deob.analysis.packet;

import dev.neosback.deob.analysis.provenance.OriginKind;
import dev.neosback.deob.analysis.provenance.ProvenanceStep;
import dev.neosback.deob.analysis.provenance.ProvenanceTrace;
import dev.neosback.deob.analysis.provenance.ValueOrigin;
import dev.neosback.deob.analysis.provenance.ValueProvenanceAnalyzer;
import dev.neosback.deob.bytecode.InstructionIndex;
import dev.neosback.deob.corpus.ClassArtifact;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.identity.ClassId;
import dev.neosback.deob.identity.InstructionId;
import dev.neosback.deob.identity.MethodId;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.tree.analysis.AnalyzerException;

/**
 * Builds forward value-flow timelines rooted at structurally discovered packet-read calls. A timeline
 * is evidence of dependency, not proof of protocol semantics or packet identity.
 */
public final class PacketTimelineAnalyzer {
    public PacketTimelineReport analyze(JarCorpus corpus) {
        PacketReadDiscoveryReport discovery = new PacketReadDiscovery().analyze(corpus);
        Map<MethodId, PacketReadCandidate> reads = new HashMap<>();
        for (PacketReadCandidate candidate : discovery.candidates()) {
            reads.put(candidate.method(), candidate);
        }

        List<PacketTimeline> timelines = new ArrayList<>();
        List<String> skipped = new ArrayList<>();
        ValueProvenanceAnalyzer provenanceAnalyzer = new ValueProvenanceAnalyzer();

        for (ClassArtifact artifact : corpus.classes().values()) {
            var classNode = artifact.readNode();
            ClassId owner = artifact.id();
            for (MethodNode method : classNode.methods) {
                MethodId host = new MethodId(owner, method.name, method.desc);
                InstructionIndex index = InstructionIndex.of(method);
                List<ReadRoot> roots = findRoots(index, reads);
                if (roots.isEmpty()) {
                    continue;
                }

                ProvenanceTrace trace;
                try {
                    trace = provenanceAnalyzer.analyze(host, method);
                } catch (AnalyzerException | RuntimeException failure) {
                    skipped.add(host.stableKey() + " :: " + failure.getClass().getSimpleName()
                            + ": " + String.valueOf(failure.getMessage()));
                    continue;
                }

                Map<String, ProvenanceStep> byId = new LinkedHashMap<>();
                Map<String, List<ProvenanceStep>> consumers = new HashMap<>();
                for (ProvenanceStep step : trace.steps()) {
                    String stepKey = step.instruction().toString();
                    byId.put(stepKey, step);
                    for (ValueOrigin input : step.inputs()) {
                        if (input.kind() == OriginKind.INSTRUCTION) {
                            consumers.computeIfAbsent(input.key(), ignored -> new ArrayList<>()).add(step);
                        }
                    }
                }

                for (ReadRoot root : roots) {
                    timelines.add(buildTimeline(host, root, index, byId, consumers));
                }
            }
        }

        timelines.sort(Comparator
                .comparing(PacketTimeline::hostMethod)
                .thenComparingInt(timeline -> timeline.readCall().opcodeIndex()));
        skipped.sort(String::compareTo);
        return new PacketTimelineReport(corpus.sha256(), reads.size(), timelines, skipped);
    }

    private static List<ReadRoot> findRoots(
            InstructionIndex index,
            Map<MethodId, PacketReadCandidate> reads) {
        List<ReadRoot> roots = new ArrayList<>();
        for (int i = 0; i < index.size(); i++) {
            AbstractInsnNode instruction = index.instruction(i);
            if (!(instruction instanceof MethodInsnNode call)) {
                continue;
            }
            MethodId called = new MethodId(new ClassId(call.owner), call.name, call.desc);
            PacketReadCandidate candidate = reads.get(called);
            if (candidate != null) {
                roots.add(new ReadRoot(i, candidate));
            }
        }
        return roots;
    }

    private static PacketTimeline buildTimeline(
            MethodId host,
            ReadRoot root,
            InstructionIndex index,
            Map<String, ProvenanceStep> byId,
            Map<String, List<ProvenanceStep>> consumers) {
        InstructionId rootId = new InstructionId(host, root.opcodeIndex());
        List<PacketTimelineEvent> events = new ArrayList<>();
        events.add(new PacketTimelineEvent(
                rootId,
                PacketTimelineEventKind.READ_CALL,
                "packet-read " + root.candidate().method().stableKey(),
                0));

        ArrayDeque<DependencyNode> queue = new ArrayDeque<>();
        queue.add(new DependencyNode(rootId.toString(), 0));
        Set<String> visited = new HashSet<>();
        visited.add(rootId.toString());

        while (!queue.isEmpty()) {
            DependencyNode current = queue.removeFirst();
            for (ProvenanceStep consumer : consumers.getOrDefault(current.key(), List.of())) {
                String key = consumer.instruction().toString();
                if (!visited.add(key)) {
                    continue;
                }
                int depth = current.depth() + 1;
                int opcodeIndex = consumer.instruction().opcodeIndex();
                AbstractInsnNode instruction = index.instruction(opcodeIndex);
                events.add(new PacketTimelineEvent(
                        consumer.instruction(),
                        classify(instruction),
                        consumer.operation(),
                        depth));
                queue.addLast(new DependencyNode(key, depth));
            }
        }

        events.sort(Comparator
                .comparingInt(PacketTimelineEvent::dependencyDepth)
                .thenComparingInt(event -> event.instruction().opcodeIndex()));
        return new PacketTimeline(
                host,
                rootId,
                root.candidate().method(),
                root.candidate().shape(),
                events);
    }

    private static PacketTimelineEventKind classify(AbstractInsnNode instruction) {
        int opcode = instruction.getOpcode();
        if (instruction instanceof VarInsnNode || instruction instanceof IincInsnNode) {
            return PacketTimelineEventKind.LOCAL_FLOW;
        }
        if (instruction instanceof FieldInsnNode
                && (opcode == Opcodes.PUTFIELD || opcode == Opcodes.PUTSTATIC)) {
            return PacketTimelineEventKind.FIELD_WRITE;
        }
        if (instruction instanceof JumpInsnNode
                || instruction instanceof TableSwitchInsnNode
                || instruction instanceof LookupSwitchInsnNode) {
            return PacketTimelineEventKind.BRANCH;
        }
        if (instruction instanceof MethodInsnNode || instruction instanceof InvokeDynamicInsnNode) {
            return PacketTimelineEventKind.CALL;
        }
        if (opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN) {
            return PacketTimelineEventKind.RETURN;
        }
        if (isTransform(opcode)) {
            return PacketTimelineEventKind.TRANSFORM;
        }
        return PacketTimelineEventKind.OTHER;
    }

    private static boolean isTransform(int opcode) {
        return switch (opcode) {
            case Opcodes.IADD, Opcodes.LADD, Opcodes.FADD, Opcodes.DADD,
                    Opcodes.ISUB, Opcodes.LSUB, Opcodes.FSUB, Opcodes.DSUB,
                    Opcodes.IMUL, Opcodes.LMUL, Opcodes.FMUL, Opcodes.DMUL,
                    Opcodes.IDIV, Opcodes.LDIV, Opcodes.FDIV, Opcodes.DDIV,
                    Opcodes.IREM, Opcodes.LREM, Opcodes.FREM, Opcodes.DREM,
                    Opcodes.ISHL, Opcodes.LSHL, Opcodes.ISHR, Opcodes.LSHR,
                    Opcodes.IUSHR, Opcodes.LUSHR, Opcodes.IAND, Opcodes.LAND,
                    Opcodes.IOR, Opcodes.LOR, Opcodes.IXOR, Opcodes.LXOR,
                    Opcodes.INEG, Opcodes.LNEG, Opcodes.I2B, Opcodes.I2C,
                    Opcodes.I2S, Opcodes.I2L, Opcodes.L2I -> true;
            default -> false;
        };
    }

    private record ReadRoot(int opcodeIndex, PacketReadCandidate candidate) {
    }

    private record DependencyNode(String key, int depth) {
    }
}
