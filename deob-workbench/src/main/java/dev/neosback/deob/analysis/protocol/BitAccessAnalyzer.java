package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.bytecode.InstructionIndex;
import dev.neosback.deob.corpus.ClassArtifact;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.identity.ClassId;
import dev.neosback.deob.identity.FieldId;
import dev.neosback.deob.identity.MethodId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;

/** Conservative discovery of bit-oriented buffer methods from exact bytecode shape. */
public final class BitAccessAnalyzer {
    public BitAccessReport analyze(JarCorpus corpus) {
        List<BitAccessCandidate> candidates = new ArrayList<>();
        for (ClassArtifact artifact : corpus.classes().values()) {
            var classNode = artifact.readNode();
            ClassId owner = artifact.id();
            for (MethodNode method : classNode.methods) {
                BitAccessCandidate candidate = analyzeMethod(owner, method);
                if (candidate != null) {
                    candidates.add(candidate);
                }
            }
        }
        candidates.sort(Comparator
                .comparingInt(BitAccessCandidate::evidenceScore).reversed()
                .thenComparing(BitAccessCandidate::method));
        return new BitAccessReport(corpus.sha256(), candidates);
    }

    private static BitAccessCandidate analyzeMethod(ClassId owner, MethodNode method) {
        InstructionIndex index = InstructionIndex.of(method);
        if (index.size() == 0) {
            return null;
        }

        Set<FieldId> byteArrays = new TreeSet<>();
        Set<FieldId> intReads = new TreeSet<>();
        Set<FieldId> intWrites = new TreeSet<>();
        int byteLoads = 0;
        int shiftByThree = 0;
        int masks = 0;
        int sevenEightConstants = 0;
        int branches = 0;
        boolean backward = false;

        for (int i = 0; i < index.size(); i++) {
            AbstractInsnNode instruction = index.instruction(i);
            int opcode = instruction.getOpcode();
            if (opcode == Opcodes.BALOAD) {
                byteLoads++;
            }
            Integer constant = intConstant(instruction);
            if (constant != null && (constant == 7 || constant == 8)) {
                sevenEightConstants++;
            }
            if ((opcode == Opcodes.ISHR || opcode == Opcodes.IUSHR || opcode == Opcodes.ISHL)
                    && recentConstant(index, i, 3)) {
                shiftByThree++;
            }
            if (opcode == Opcodes.IAND || opcode == Opcodes.IOR) {
                masks++;
            }
            if (instruction instanceof FieldInsnNode field) {
                FieldId id = new FieldId(new ClassId(field.owner), field.name, field.desc);
                if ("[B".equals(field.desc)
                        && (opcode == Opcodes.GETFIELD || opcode == Opcodes.GETSTATIC)) {
                    byteArrays.add(id);
                }
                if ("I".equals(field.desc)) {
                    if (opcode == Opcodes.GETFIELD || opcode == Opcodes.GETSTATIC) {
                        intReads.add(id);
                    }
                    if (opcode == Opcodes.PUTFIELD || opcode == Opcodes.PUTSTATIC) {
                        intWrites.add(id);
                    }
                }
            }
            if (instruction instanceof JumpInsnNode jump) {
                branches++;
                int target = index.targetIndex(jump.label);
                backward |= target >= 0 && target < i;
            } else if (instruction instanceof TableSwitchInsnNode table) {
                branches += table.labels.size() + 1;
                backward |= backward(index, i, table.dflt);
                for (var label : table.labels) {
                    backward |= backward(index, i, label);
                }
            } else if (instruction instanceof LookupSwitchInsnNode lookup) {
                branches += lookup.labels.size() + 1;
                backward |= backward(index, i, lookup.dflt);
                for (var label : lookup.labels) {
                    backward |= backward(index, i, label);
                }
            }
        }

        Set<FieldId> state = new TreeSet<>(intReads);
        state.retainAll(intWrites);
        int score = 0;
        score += byteArrays.isEmpty() ? 0 : 3;
        score += Math.min(4, byteLoads);
        score += shiftByThree * 4;
        score += Math.min(3, masks);
        score += Math.min(3, sevenEightConstants);
        score += state.isEmpty() ? 0 : 3;
        score += backward ? 2 : 0;
        score += branches > 0 ? 1 : 0;

        // Requiring both byte storage and bit-index-style arithmetic avoids ordinary integer code.
        if (byteArrays.isEmpty() || shiftByThree == 0 || score < 10) {
            return null;
        }
        return new BitAccessCandidate(
                new MethodId(owner, method.name, method.desc),
                List.copyOf(byteArrays),
                List.copyOf(state),
                byteLoads,
                shiftByThree,
                masks,
                sevenEightConstants,
                branches,
                backward,
                score);
    }

    private static boolean recentConstant(InstructionIndex index, int instruction, int value) {
        for (int delta = 1; delta <= 2; delta++) {
            int candidate = instruction - delta;
            if (candidate >= 0 && Integer.valueOf(value).equals(intConstant(index.instruction(candidate)))) {
                return true;
            }
        }
        return false;
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

    private static boolean backward(
            InstructionIndex index,
            int from,
            org.objectweb.asm.tree.LabelNode label) {
        int target = index.targetIndex(label);
        return target >= 0 && target < from;
    }
}
