package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.analysis.cfg.CfgAnalyzer;
import dev.neosback.deob.analysis.cfg.CfgStructureAnalyzer;
import dev.neosback.deob.analysis.packet.PacketReadCandidate;
import dev.neosback.deob.analysis.packet.PacketReadDiscovery;
import dev.neosback.deob.analysis.packet.PacketReadShape;
import dev.neosback.deob.analysis.packet.PacketTimeline;
import dev.neosback.deob.analysis.packet.PacketTimelineAnalyzer;
import dev.neosback.deob.analysis.packet.PacketTimelineEvent;
import dev.neosback.deob.analysis.packet.PacketTimelineEventKind;
import dev.neosback.deob.bytecode.InstructionIndex;
import dev.neosback.deob.corpus.ClassArtifact;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.identity.ClassId;
import dev.neosback.deob.identity.FieldId;
import dev.neosback.deob.identity.InstructionId;
import dev.neosback.deob.identity.MethodId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * Correlates packet-read, value-flow, and CFG evidence to rank protocol entry points. No candidate
 * emitted here is a semantic truth claim: ISAAC/framing, packet-handler, and player-update labels
 * remain hypotheses until external/revision evidence confirms them.
 */
public final class ProtocolEntryAnalyzer {
    public ProtocolEvidenceReport analyze(JarCorpus corpus) {
        BufferFamilyReport buffers = new BufferFamilyAnalyzer().analyze(corpus);
        var readReport = new PacketReadDiscovery().analyze(corpus);
        var timelineReport = new PacketTimelineAnalyzer().analyze(corpus);

        Set<MethodId> readMethods = new HashSet<>();
        for (PacketReadCandidate candidate : readReport.candidates()) {
            readMethods.add(candidate.method());
        }

        Map<MethodId, MethodNode> methodNodes = new HashMap<>();
        List<FramingMethodCandidate> framing = new ArrayList<>();
        for (ClassArtifact artifact : corpus.classes().values()) {
            var classNode = artifact.readNode();
            ClassId owner = artifact.id();
            for (MethodNode method : classNode.methods) {
                MethodId id = new MethodId(owner, method.name, method.desc);
                methodNodes.put(id, method);
                FramingMethodCandidate candidate = framingCandidate(id, method, readMethods);
                if (candidate != null) {
                    framing.add(candidate);
                }
            }
        }
        framing.sort(Comparator
                .comparingInt(FramingMethodCandidate::evidenceScore).reversed()
                .thenComparing(FramingMethodCandidate::method));

        Map<MethodId, List<PacketTimeline>> timelinesByHost = new HashMap<>();
        for (PacketTimeline timeline : timelineReport.timelines()) {
            timelinesByHost.computeIfAbsent(timeline.hostMethod(), ignored -> new ArrayList<>()).add(timeline);
        }

        List<String> warnings = new ArrayList<>(timelineReport.skippedMethods());
        List<PacketHandlerCandidate> handlers = new ArrayList<>();
        for (Map.Entry<MethodId, List<PacketTimeline>> entry : timelinesByHost.entrySet()) {
            PacketHandlerCandidate candidate = handlerCandidate(entry.getKey(), entry.getValue(), methodNodes, warnings);
            if (candidate != null) {
                handlers.add(candidate);
            }
        }
        handlers.sort(Comparator
                .comparingInt(PacketHandlerCandidate::evidenceScore).reversed()
                .thenComparing(PacketHandlerCandidate::method));

        List<PlayerUpdateDossierCandidate> playerCandidates = new ArrayList<>();
        Map<MethodId, PacketHandlerCandidate> handlerByMethod = new HashMap<>();
        for (PacketHandlerCandidate handler : handlers) {
            handlerByMethod.put(handler.method(), handler);
        }
        for (Map.Entry<MethodId, List<PacketTimeline>> entry : timelinesByHost.entrySet()) {
            PacketHandlerCandidate handler = handlerByMethod.get(entry.getKey());
            if (handler == null) {
                continue;
            }
            PlayerUpdateDossierCandidate dossier = playerDossier(handler, entry.getValue());
            if (dossier != null) {
                playerCandidates.add(dossier);
            }
        }
        playerCandidates.sort(Comparator
                .comparingInt(PlayerUpdateDossierCandidate::hypothesisScore).reversed()
                .thenComparing(PlayerUpdateDossierCandidate::method));

        warnings.sort(String::compareTo);
        return new ProtocolEvidenceReport(
                corpus.sha256(),
                buffers,
                framing,
                handlers,
                playerCandidates,
                warnings);
    }

    private static FramingMethodCandidate framingCandidate(
            MethodId id,
            MethodNode method,
            Set<MethodId> readMethods) {
        InstructionIndex index = InstructionIndex.of(method);
        Set<MethodId> readsCalled = new TreeSet<>();
        Set<MethodId> externalIntCalls = new TreeSet<>();
        Set<FieldId> stateFields = new TreeSet<>();
        int byteArrayAccesses = 0;
        int arithmetic = 0;

        for (AbstractInsnNode instruction : index.instructions()) {
            int opcode = instruction.getOpcode();
            if (opcode == Opcodes.BALOAD || opcode == Opcodes.BASTORE) {
                byteArrayAccesses++;
            }
            if (isArithmetic(opcode)) {
                arithmetic++;
            }
            if (instruction instanceof FieldInsnNode field) {
                if ((field.desc.startsWith("L") || field.desc.startsWith("[")) && !"[B".equals(field.desc)) {
                    stateFields.add(new FieldId(new ClassId(field.owner), field.name, field.desc));
                }
            }
            if (instruction instanceof MethodInsnNode call) {
                MethodId called = new MethodId(new ClassId(call.owner), call.name, call.desc);
                if (readMethods.contains(called)) {
                    readsCalled.add(called);
                } else if (Type.getReturnType(call.desc).getSort() == Type.INT) {
                    externalIntCalls.add(called);
                }
            }
        }

        int score = readsCalled.size() * 5;
        score += externalIntCalls.size() * 4;
        score += Math.min(8, byteArrayAccesses * 2);
        score += Math.min(6, arithmetic);
        score += Math.min(6, stateFields.size() * 2);
        if (score < 10 || (readsCalled.isEmpty() && externalIntCalls.isEmpty())) {
            return null;
        }

        List<String> reasons = new ArrayList<>();
        if (!readsCalled.isEmpty()) reasons.add("packet-read-calls=" + readsCalled.size());
        if (!externalIntCalls.isEmpty()) reasons.add("external-int-return-calls=" + externalIntCalls.size());
        if (byteArrayAccesses > 0) reasons.add("byte-array-accesses=" + byteArrayAccesses);
        if (arithmetic > 0) reasons.add("arithmetic-ops=" + arithmetic);
        if (!stateFields.isEmpty()) reasons.add("object-state-fields=" + stateFields.size());

        return new FramingMethodCandidate(
                id,
                readsCalled,
                externalIntCalls,
                stateFields,
                byteArrayAccesses,
                arithmetic,
                score,
                reasons);
    }

    private static PacketHandlerCandidate handlerCandidate(
            MethodId method,
            List<PacketTimeline> timelines,
            Map<MethodId, MethodNode> methodNodes,
            List<String> warnings) {
        Set<MethodId> readMethods = new TreeSet<>();
        int transforms = 0;
        int fieldWrites = 0;
        int branches = 0;
        int calls = 0;
        for (PacketTimeline timeline : timelines) {
            readMethods.add(timeline.readMethod());
            for (PacketTimelineEvent event : timeline.events()) {
                switch (event.kind()) {
                    case TRANSFORM -> transforms++;
                    case FIELD_WRITE -> fieldWrites++;
                    case BRANCH -> branches++;
                    case CALL -> calls++;
                    default -> { }
                }
            }
        }

        int loops = 0;
        MethodNode node = methodNodes.get(method);
        if (node != null) {
            try {
                var cfg = new CfgAnalyzer().analyze(method, node);
                loops = new CfgStructureAnalyzer().analyze(cfg).naturalLoops().size();
            } catch (RuntimeException failure) {
                warnings.add(method.stableKey() + " :: CFG structure: " + failure.getClass().getSimpleName()
                        + ": " + String.valueOf(failure.getMessage()));
            }
        }

        int score = timelines.size() * 4;
        score += Math.min(16, readMethods.size() * 4);
        score += Math.min(12, branches * 2);
        score += Math.min(12, fieldWrites * 2);
        score += Math.min(8, transforms);
        score += Math.min(8, calls);
        score += Math.min(12, loops * 4);
        if (timelines.size() < 2 || score < 18) {
            return null;
        }

        List<String> reasons = new ArrayList<>();
        reasons.add("packet-read-calls=" + timelines.size());
        reasons.add("distinct-read-methods=" + readMethods.size());
        if (branches > 0) reasons.add("dependent-branches=" + branches);
        if (fieldWrites > 0) reasons.add("dependent-field-writes=" + fieldWrites);
        if (loops > 0) reasons.add("natural-loops=" + loops);

        return new PacketHandlerCandidate(
                method,
                timelines.size(),
                readMethods,
                transforms,
                fieldWrites,
                branches,
                calls,
                loops,
                score,
                reasons);
    }

    private static PlayerUpdateDossierCandidate playerDossier(
            PacketHandlerCandidate handler,
            List<PacketTimeline> timelines) {
        Set<MethodId> reads = new TreeSet<>();
        Set<PacketReadShape> shapes = EnumSet.noneOf(PacketReadShape.class);
        Set<InstructionId> fieldWrites = new LinkedHashSet<>();
        Set<InstructionId> branches = new LinkedHashSet<>();
        Set<InstructionId> calls = new LinkedHashSet<>();

        for (PacketTimeline timeline : timelines) {
            reads.add(timeline.readMethod());
            shapes.add(timeline.readShape());
            for (PacketTimelineEvent event : timeline.events()) {
                switch (event.kind()) {
                    case FIELD_WRITE -> fieldWrites.add(event.instruction());
                    case BRANCH -> branches.add(event.instruction());
                    case CALL -> calls.add(event.instruction());
                    default -> { }
                }
            }
        }

        int score = handler.evidenceScore();
        score += Math.min(18, reads.size() * 3);
        score += Math.min(15, fieldWrites.size() * 3);
        score += Math.min(15, branches.size() * 2);
        score += Math.min(10, calls.size());
        score += Math.min(12, handler.naturalLoopCount() * 4);
        if (score < 35) {
            return null;
        }

        List<String> notes = new ArrayList<>();
        notes.add("HYPOTHESIS ONLY: dense packet/update structure; not yet proven to be player synchronization");
        notes.add("packet-read-calls=" + handler.packetReadCalls());
        notes.add("distinct-read-methods=" + reads.size());
        notes.add("field-write-sites=" + fieldWrites.size());
        notes.add("branch-sites=" + branches.size());
        notes.add("call-sites=" + calls.size());
        notes.add("natural-loops=" + handler.naturalLoopCount());

        Comparator<InstructionId> order = Comparator.comparingInt(InstructionId::opcodeIndex);
        List<InstructionId> orderedFieldWrites = fieldWrites.stream().sorted(order).toList();
        List<InstructionId> orderedBranches = branches.stream().sorted(order).toList();
        List<InstructionId> orderedCalls = calls.stream().sorted(order).toList();

        return new PlayerUpdateDossierCandidate(
                handler.method(),
                score,
                reads,
                shapes,
                orderedFieldWrites,
                orderedBranches,
                orderedCalls,
                handler.naturalLoopCount(),
                notes);
    }

    private static boolean isArithmetic(int opcode) {
        return switch (opcode) {
            case Opcodes.IADD, Opcodes.ISUB, Opcodes.IMUL, Opcodes.IDIV, Opcodes.IREM,
                    Opcodes.ISHL, Opcodes.ISHR, Opcodes.IUSHR,
                    Opcodes.IAND, Opcodes.IOR, Opcodes.IXOR -> true;
            default -> false;
        };
    }
}
