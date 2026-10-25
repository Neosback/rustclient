package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.analysis.cfg.BasicBlock;
import dev.neosback.deob.analysis.cfg.CfgAnalyzer;
import dev.neosback.deob.analysis.cfg.ControlFlowGraph;
import dev.neosback.deob.bytecode.InstructionIndex;
import dev.neosback.deob.corpus.ClassArtifact;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.identity.ClassId;
import dev.neosback.deob.identity.FieldId;
import dev.neosback.deob.identity.InstructionId;
import dev.neosback.deob.identity.MethodId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * Finds mask-test idioms inside rev-240 sync-role candidates and attaches deliberately broad
 * RuneLite-style Actor vocabulary to fields written in their immediate successor blocks.
 */
public final class UpdateMaskAnalyzer {
    public UpdateMaskReport analyze(JarCorpus corpus, PacketTable serverPackets) {
        Rev240SyncReport sync = new Rev240SyncAnalyzer().analyze(corpus, serverPackets);
        Map<MethodId, MethodNode> methods = methodIndex(corpus);
        List<UpdateMaskBitEvidence> tests = new ArrayList<>();
        Map<FieldId, MutableActorField> actorFields = new HashMap<>();
        CfgAnalyzer cfgAnalyzer = new CfgAnalyzer();

        for (SyncRoleCandidate syncCandidate : sync.candidates()) {
            MethodNode method = methods.get(syncCandidate.method());
            if (method == null) {
                continue;
            }
            InstructionIndex index = InstructionIndex.of(method);
            ControlFlowGraph cfg = cfgAnalyzer.analyze(syncCandidate.method(), method);
            for (int i = 0; i + 1 < index.size(); i++) {
                AbstractInsnNode instruction = index.instruction(i);
                if (instruction.getOpcode() != Opcodes.IAND) {
                    continue;
                }
                Integer mask = recentConstant(index, i);
                if (mask == null || mask == 0) {
                    continue;
                }
                AbstractInsnNode next = index.instruction(i + 1);
                if (!(next instanceof JumpInsnNode branch)
                        || branch.getOpcode() == Opcodes.GOTO
                        || branch.getOpcode() == Opcodes.JSR) {
                    continue;
                }

                int target = index.targetIndex(branch.label);
                int fallthrough = i + 2 < index.size() ? i + 2 : -1;
                List<FieldId> targetWrites = writesInBlock(index, cfg, target);
                List<FieldId> fallthroughWrites = writesInBlock(index, cfg, fallthrough);
                UpdateMaskBitEvidence evidence = new UpdateMaskBitEvidence(
                        syncCandidate.method(),
                        syncCandidate.role(),
                        mask,
                        new InstructionId(syncCandidate.method(), i),
                        new InstructionId(syncCandidate.method(), i + 1),
                        targetWrites,
                        fallthroughWrites);
                tests.add(evidence);

                for (FieldId field : union(targetWrites, fallthroughWrites)) {
                    MutableActorField mutable = actorFields.computeIfAbsent(field, MutableActorField::new);
                    mutable.roles.add(syncCandidate.role());
                    mutable.evidenceCount++;
                    mutable.reasons.add("mask 0x" + Integer.toHexString(mask)
                            + " controls an immediate CFG successor write in "
                            + syncCandidate.role());
                }
            }
        }

        tests.sort(Comparator
                .comparing(UpdateMaskBitEvidence::method)
                .thenComparingInt(test -> test.andInstruction().opcodeIndex()));

        List<ActorStateFieldCandidate> fields = new ArrayList<>();
        for (MutableActorField mutable : actorFields.values()) {
            if (mutable.roles.contains(SyncRole.PLAYER_LIKE)
                    && mutable.roles.contains(SyncRole.NPC_LIKE)) {
                mutable.reasons.add("written from both player-like and NPC-like sync paths; shared Actor state is plausible");
            }
            fields.add(new ActorStateFieldCandidate(
                    mutable.field,
                    mutable.roles.stream().sorted().toList(),
                    mutable.evidenceCount,
                    semanticVocabulary(mutable.field.descriptor()),
                    List.copyOf(mutable.reasons)));
        }
        fields.sort(Comparator
                .comparingInt(ActorStateFieldCandidate::maskControlledWriteEvidence).reversed()
                .thenComparing(ActorStateFieldCandidate::field));

        return new UpdateMaskReport(
                corpus.sha256(), serverPackets.revision(), tests, fields);
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

    private static List<FieldId> writesInBlock(
            InstructionIndex index,
            ControlFlowGraph cfg,
            int instructionIndex) {
        if (instructionIndex < 0) {
            return List.of();
        }
        BasicBlock block = cfg.blocks().stream()
                .filter(candidate -> candidate.contains(instructionIndex))
                .findFirst()
                .orElse(null);
        if (block == null) {
            return List.of();
        }
        Set<FieldId> writes = new TreeSet<>();
        for (int i = block.startInstruction(); i < block.endInstructionExclusive(); i++) {
            AbstractInsnNode instruction = index.instruction(i);
            if (instruction instanceof FieldInsnNode field
                    && (instruction.getOpcode() == Opcodes.PUTFIELD
                    || instruction.getOpcode() == Opcodes.PUTSTATIC)) {
                writes.add(new FieldId(new ClassId(field.owner), field.name, field.desc));
            }
        }
        return List.copyOf(writes);
    }

    private static Set<FieldId> union(List<FieldId> left, List<FieldId> right) {
        Set<FieldId> result = new TreeSet<>(left);
        result.addAll(right);
        return result;
    }

    private static Integer recentConstant(InstructionIndex index, int instruction) {
        for (int delta = 1; delta <= 2; delta++) {
            int candidate = instruction - delta;
            if (candidate >= 0) {
                Integer value = intConstant(index.instruction(candidate));
                if (value != null) {
                    return value;
                }
            }
        }
        return null;
    }

    private static Integer intConstant(AbstractInsnNode instruction) {
        int opcode = instruction.getOpcode();
        if (opcode >= Opcodes.ICONST_M1 && opcode <= Opcodes.ICONST_5) {
            return opcode - Opcodes.ICONST_0;
        }
        if (instruction instanceof IntInsnNode intInsn) {
            return intInsn.operand;
        }
        if (instruction instanceof LdcInsnNode ldc && ldc.cst instanceof Integer integer) {
            return integer;
        }
        return null;
    }

    private static List<String> semanticVocabulary(String descriptor) {
        return switch (descriptor) {
            case "I" -> List.of(
                    "animation",
                    "poseAnimation",
                    "animationFrame",
                    "poseAnimationFrame",
                    "orientation",
                    "targetIndex",
                    "movementCycle");
            case "[I" -> List.of("pathX/pathY", "actorIndexOrMovementArrayState");
            case "Ljava/lang/String;" -> List.of("overheadText");
            case "Z" -> List.of("actorBooleanState");
            default -> List.of("actorState");
        };
    }

    private static final class MutableActorField {
        private final FieldId field;
        private final Set<SyncRole> roles = new TreeSet<>();
        private final Set<String> reasons = new TreeSet<>();
        private int evidenceCount;

        private MutableActorField(FieldId field) {
            this.field = field;
        }
    }
}
