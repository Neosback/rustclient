package dev.neosback.deob.analysis.index;

import dev.neosback.deob.bytecode.InstructionIndex;
import dev.neosback.deob.util.Hashes;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.MultiANewArrayInsnNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

/** Exact and rename-insensitive method fingerprints over opcode-bearing instructions. */
public final class MethodFingerprints {
    private MethodFingerprints() {}

    public record Result(String exactSha256, String structuralSha256) {}

    public static Result compute(MethodNode method) {
        InstructionIndex index = InstructionIndex.of(method);
        return new Result(hash(method, index, false), hash(method, index, true));
    }

    private static String hash(MethodNode method, InstructionIndex index, boolean structural) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(bytes)) {
                writeString(out, structural ? normalizeMethodDescriptor(method.desc) : method.desc);
                out.writeInt(method.tryCatchBlocks == null ? 0 : method.tryCatchBlocks.size());
                for (AbstractInsnNode instruction : index.instructions()) {
                    out.writeInt(instruction.getOpcode());
                    writeOperand(out, instruction, index, structural);
                }
            }
            return HexFormat.of().formatHex(Hashes.sha256Digest().digest(bytes.toByteArray()));
        } catch (IOException exception) {
            throw new IllegalStateException("Unexpected in-memory fingerprint I/O failure", exception);
        }
    }

    private static void writeOperand(
        DataOutputStream out,
        AbstractInsnNode instruction,
        InstructionIndex index,
        boolean structural
    ) throws IOException {
        if (instruction instanceof IntInsnNode node) {
            out.writeInt(node.operand);
        } else if (instruction instanceof VarInsnNode node) {
            out.writeInt(node.var);
        } else if (instruction instanceof TypeInsnNode node) {
            writeString(out, structural ? normalizeInternalOrDescriptor(node.desc) : node.desc);
        } else if (instruction instanceof FieldInsnNode node) {
            if (!structural) {
                writeString(out, node.owner);
                writeString(out, node.name);
            }
            writeString(out, structural ? normalizeTypeDescriptor(node.desc) : node.desc);
        } else if (instruction instanceof MethodInsnNode node) {
            if (!structural) {
                writeString(out, node.owner);
                writeString(out, node.name);
            }
            writeString(out, structural ? normalizeMethodDescriptor(node.desc) : node.desc);
            out.writeBoolean(node.itf);
        } else if (instruction instanceof InvokeDynamicInsnNode node) {
            if (!structural) {
                writeString(out, node.name);
            }
            writeString(out, structural ? normalizeMethodDescriptor(node.desc) : node.desc);
            writeHandle(out, node.bsm, structural);
            out.writeInt(node.bsmArgs.length);
            for (Object argument : node.bsmArgs) {
                writeBootstrapArgument(out, argument, structural);
            }
        } else if (instruction instanceof JumpInsnNode node) {
            out.writeInt(index.targetIndex(node.label));
        } else if (instruction instanceof LdcInsnNode node) {
            writeConstant(out, node.cst, structural);
        } else if (instruction instanceof IincInsnNode node) {
            out.writeInt(node.var);
            out.writeInt(node.incr);
        } else if (instruction instanceof TableSwitchInsnNode node) {
            out.writeInt(node.min);
            out.writeInt(node.max);
            out.writeInt(index.targetIndex(node.dflt));
            out.writeInt(node.labels.size());
            for (var label : node.labels) {
                out.writeInt(index.targetIndex(label));
            }
        } else if (instruction instanceof LookupSwitchInsnNode node) {
            out.writeInt(index.targetIndex(node.dflt));
            out.writeInt(node.keys.size());
            for (int i = 0; i < node.keys.size(); i++) {
                out.writeInt(node.keys.get(i));
                out.writeInt(index.targetIndex(node.labels.get(i)));
            }
        } else if (instruction instanceof MultiANewArrayInsnNode node) {
            writeString(out, structural ? normalizeTypeDescriptor(node.desc) : node.desc);
            out.writeInt(node.dims);
        }
    }

    private static void writeBootstrapArgument(DataOutputStream out, Object value, boolean structural)
        throws IOException {
        if (value instanceof Type type) {
            writeString(out, "type");
            writeString(out, structural ? normalizeType(type) : type.getDescriptor());
        } else if (value instanceof Handle handle) {
            writeString(out, "handle");
            writeHandle(out, handle, structural);
        } else {
            writeConstant(out, value, structural);
        }
    }

    private static void writeHandle(DataOutputStream out, Handle handle, boolean structural)
        throws IOException {
        out.writeInt(handle.getTag());
        out.writeBoolean(handle.isInterface());
        if (!structural) {
            writeString(out, handle.getOwner());
            writeString(out, handle.getName());
        }
        String descriptor = handle.getDesc();
        String normalized = descriptor.startsWith("(")
            ? normalizeMethodDescriptor(descriptor)
            : normalizeTypeDescriptor(descriptor);
        writeString(out, structural ? normalized : descriptor);
    }

    private static void writeConstant(DataOutputStream out, Object value, boolean structural)
        throws IOException {
        if (value == null) {
            writeString(out, "null");
            return;
        }
        writeString(out, value.getClass().getName());
        if (value instanceof Type type) {
            writeString(out, structural ? normalizeType(type) : type.getDescriptor());
        } else if (value instanceof Handle handle) {
            writeHandle(out, handle, structural);
        } else {
            writeString(out, String.valueOf(value));
        }
    }

    private static String normalizeInternalOrDescriptor(String value) {
        return value.startsWith("[") ? normalizeTypeDescriptor(value) : "L*;";
    }

    private static String normalizeTypeDescriptor(String descriptor) {
        return normalizeType(Type.getType(descriptor));
    }

    private static String normalizeMethodDescriptor(String descriptor) {
        return normalizeType(Type.getMethodType(descriptor));
    }

    private static String normalizeType(Type type) {
        return switch (type.getSort()) {
            case Type.METHOD -> {
                StringBuilder builder = new StringBuilder("(");
                for (Type argument : type.getArgumentTypes()) {
                    builder.append(normalizeType(argument));
                }
                builder.append(')').append(normalizeType(type.getReturnType()));
                yield builder.toString();
            }
            case Type.ARRAY -> "[".repeat(type.getDimensions()) + normalizeType(type.getElementType());
            case Type.OBJECT -> "L*;";
            default -> type.getDescriptor();
        };
    }

    private static void writeString(DataOutputStream out, String value) throws IOException {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        out.writeInt(bytes.length);
        out.write(bytes);
    }
}
