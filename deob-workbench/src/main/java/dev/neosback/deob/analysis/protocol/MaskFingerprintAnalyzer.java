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
import dev.neosback.deob.identity.FieldId;
import dev.neosback.deob.identity.MethodId;
import dev.neosback.deob.util.Hashes;
import java.nio.charset.StandardCharsets;
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
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

/** Builds rename-independent fingerprints for immediate update-mask successor blocks. */
public final class MaskFingerprintAnalyzer {
    public MaskFingerprintReport analyze(JarCorpus corpus, PacketTable serverPackets) {
        UpdateMaskReport masks = new UpdateMaskAnalyzer().analyze(corpus, serverPackets);
        Map<MethodId, MethodNode> methods = methodIndex(corpus);
        Set<MethodId> readMethods = readMethods(corpus);
        CfgAnalyzer cfgAnalyzer = new CfgAnalyzer();
        List<MaskBlockFingerprint> result = new ArrayList<>();

        for (UpdateMaskBitEvidence mask : masks.tests()) {
            MethodNode method = methods.get(mask.method());
            if (method == null) continue;
            InstructionIndex index = InstructionIndex.of(method);
            int branchIndex = mask.branchInstruction().opcodeIndex();
            if (branchIndex < 0 || branchIndex >= index.size()) continue;
            AbstractInsnNode raw = index.instruction(branchIndex);
            if (!(raw instanceof JumpInsnNode branch)) continue;

            ControlFlowGraph cfg = cfgAnalyzer.analyze(mask.method(), method);
            int target = index.targetIndex(branch.label);
            int fallthrough = branchIndex + 1 < index.size() ? branchIndex + 1 : -1;
            List<MaskSuccessorFingerprint> successors = new ArrayList<>();
            addSuccessor(successors, index, cfg, target, readMethods);
            addSuccessor(successors, index, cfg, fallthrough, readMethods);
            successors = successors.stream()
                    .distinct()
                    .sorted(Comparator.comparing(MaskSuccessorFingerprint::structuralHash)
                            .thenComparingInt(MaskSuccessorFingerprint::startInstruction))
                    .toList();

            StringBuilder combined = new StringBuilder();
            combined.append(branch.getOpcode()).append('|');
            for (MaskSuccessorFingerprint successor : successors) {
                combined.append(successor.structuralHash()).append('|');
            }
            String hash = Hashes.sha256(combined.toString().getBytes(StandardCharsets.UTF_8));
            result.add(new MaskBlockFingerprint(
                    mask.method(),
                    mask.role(),
                    mask.maskConstant(),
                    mask.branchInstruction(),
                    successors,
                    hash));
        }

        result.sort(Comparator
                .comparing(MaskBlockFingerprint::method)
                .thenComparingInt(value -> value.branchInstruction().opcodeIndex()));
        return new MaskFingerprintReport(corpus.sha256(), serverPackets.revision(), result);
    }

    private static void addSuccessor(
            List<MaskSuccessorFingerprint> target,
            InstructionIndex index,
            ControlFlowGraph cfg,
            int instructionIndex,
            Set<MethodId> readMethods) {
        BasicBlock block = cfg.blocks().stream()
                .filter(candidate -> candidate.contains(instructionIndex))
                .findFirst()
                .orElse(null);
        if (block == null) return;
        target.add(fingerprint(index, block, readMethods));
    }

    private static MaskSuccessorFingerprint fingerprint(
            InstructionIndex index,
            BasicBlock block,
            Set<MethodId> readMethods) {
        Set<MethodId> packetReads = new TreeSet<>();
        Set<MethodId> calls = new TreeSet<>();
        Set<FieldId> writes = new TreeSet<>();
        List<Integer> constants = new ArrayList<>();
        StringBuilder signature = new StringBuilder();

        for (int i = block.startInstruction(); i < block.endInstructionExclusive(); i++) {
            AbstractInsnNode instruction = index.instruction(i);
            int opcode = instruction.getOpcode();
            signature.append(opcode);
            if (instruction instanceof MethodInsnNode call) {
                MethodId method = new MethodId(new ClassId(call.owner), call.name, call.desc);
                calls.add(method);
                if (readMethods.contains(method)) packetReads.add(method);
                signature.append(':').append(normalizeDescriptor(call.desc));
            } else if (instruction instanceof FieldInsnNode field) {
                if (opcode == Opcodes.PUTFIELD || opcode == Opcodes.PUTSTATIC) {
                    writes.add(new FieldId(new ClassId(field.owner), field.name, field.desc));
                }
                signature.append(':').append(normalizeDescriptor(field.desc));
            }
            Integer constant = intConstant(instruction);
            if (constant != null) {
                constants.add(constant);
                signature.append('=').append(constant);
            }
            signature.append(';');
        }

        return new MaskSuccessorFingerprint(
                block.startInstruction(),
                List.copyOf(packetReads),
                List.copyOf(calls),
                List.copyOf(writes),
                List.copyOf(constants),
                Hashes.sha256(signature.toString().getBytes(StandardCharsets.UTF_8)));
    }

    private static String normalizeDescriptor(String descriptor) {
        return descriptor.replaceAll("L[^;]+;", "L*;");
    }

    private static Integer intConstant(AbstractInsnNode instruction) {
        int opcode = instruction.getOpcode();
        if (opcode >= Opcodes.ICONST_M1 && opcode <= Opcodes.ICONST_5) {
            return opcode - Opcodes.ICONST_0;
        }
        if (instruction instanceof IntInsnNode intInsn) return intInsn.operand;
        if (instruction instanceof LdcInsnNode ldc && ldc.cst instanceof Integer integer) return integer;
        return null;
    }

    private static Set<MethodId> readMethods(JarCorpus corpus) {
        Set<MethodId> result = new HashSet<>();
        for (PacketReadCandidate candidate : new PacketReadDiscovery().analyze(corpus).candidates()) {
            result.add(candidate.method());
        }
        return result;
    }

    private static Map<MethodId, MethodNode> methodIndex(JarCorpus corpus) {
        Map<MethodId, MethodNode> result = new HashMap<>();
        for (ClassArtifact artifact : corpus.classes().values()) {
            var node = artifact.readNode();
            for (MethodNode method : node.methods) {
                result.put(new MethodId(artifact.id(), method.name, method.desc), method);
            }
        }
        return result;
    }
}
