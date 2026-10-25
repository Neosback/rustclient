package dev.neosback.deob.analysis.packet;

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
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;

/**
 * Conservative structural discovery of methods that consume bytes from an object-owned byte array.
 * Results are evidence candidates only; this pass never assigns names such as readUnsignedByte.
 */
public final class PacketReadDiscovery {
    public PacketReadDiscoveryReport analyze(JarCorpus corpus) {
        List<PacketReadCandidate> candidates = new ArrayList<>();
        for (ClassArtifact artifact : corpus.classes().values()) {
            var node = artifact.readNode();
            ClassId owner = artifact.id();
            for (MethodNode method : node.methods) {
                PacketReadCandidate candidate = analyzeMethod(owner, method);
                if (candidate != null) {
                    candidates.add(candidate);
                }
            }
        }
        candidates.sort(Comparator.comparing(PacketReadCandidate::method));
        return new PacketReadDiscoveryReport(corpus.sha256(), candidates);
    }

    private static PacketReadCandidate analyzeMethod(ClassId owner, MethodNode method) {
        InstructionIndex index = InstructionIndex.of(method);
        if (index.size() == 0) {
            return null;
        }

        int byteLoads = 0;
        int masks255 = 0;
        int shifts = 0;
        int branches = 0;
        boolean backward = false;
        Set<FieldId> byteArrays = new TreeSet<>();
        Set<FieldId> intReads = new TreeSet<>();
        Set<FieldId> intWrites = new TreeSet<>();

        for (int i = 0; i < index.size(); i++) {
            AbstractInsnNode instruction = index.instruction(i);
            int opcode = instruction.getOpcode();
            if (opcode == Opcodes.BALOAD) {
                byteLoads++;
            }
            if (opcode == Opcodes.ISHL || opcode == Opcodes.ISHR || opcode == Opcodes.IUSHR) {
                shifts++;
            }
            if (opcode == Opcodes.IAND && recentConstant(index, i, 255)) {
                masks255++;
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
                    } else if (opcode == Opcodes.PUTFIELD || opcode == Opcodes.PUTSTATIC) {
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
                backward |= isBackward(index, i, table.dflt);
                for (var label : table.labels) {
                    backward |= isBackward(index, i, label);
                }
            } else if (instruction instanceof LookupSwitchInsnNode lookup) {
                branches += lookup.labels.size() + 1;
                backward |= isBackward(index, i, lookup.dflt);
                for (var label : lookup.labels) {
                    backward |= isBackward(index, i, label);
                }
            }
        }

        if (byteLoads == 0 || byteArrays.isEmpty()) {
            return null;
        }

        Set<FieldId> offsets = new TreeSet<>(intReads);
        offsets.retainAll(intWrites);

        Type returnType = Type.getReturnType(method.desc);
        int score = 4;
        score += Math.min(3, byteArrays.size() * 2);
        score += offsets.isEmpty() ? 0 : 3;
        score += masks255 > 0 ? 1 : 0;
        score += shifts > 0 ? 1 : 0;
        score += branches > 0 ? 1 : 0;
        score += returnType.getSort() == Type.VOID ? 0 : 1;
        if (score < 7) {
            return null;
        }

        return new PacketReadCandidate(
                new MethodId(owner, method.name, method.desc),
                shape(byteLoads, backward),
                returnType.getDescriptor(),
                byteLoads,
                List.copyOf(byteArrays),
                List.copyOf(offsets),
                masks255,
                shifts,
                branches,
                backward,
                score);
    }

    private static PacketReadShape shape(int byteLoads, boolean backward) {
        if (backward) {
            return PacketReadShape.VARIABLE_OR_LOOPING;
        }
        return switch (byteLoads) {
            case 1 -> PacketReadShape.FIXED_ONE_BYTE;
            case 2 -> PacketReadShape.FIXED_TWO_BYTE;
            case 4 -> PacketReadShape.FIXED_FOUR_BYTE;
            case 8 -> PacketReadShape.FIXED_EIGHT_BYTE;
            default -> PacketReadShape.UNKNOWN;
        };
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

    private static boolean isBackward(
            InstructionIndex index,
            int from,
            org.objectweb.asm.tree.LabelNode label) {
        int target = index.targetIndex(label);
        return target >= 0 && target < from;
    }
}
