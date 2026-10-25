package dev.neosback.deob.analysis.provenance;

import dev.neosback.deob.bytecode.InstructionIndex;
import dev.neosback.deob.identity.InstructionId;
import dev.neosback.deob.identity.MethodId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.MultiANewArrayInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.Frame;
import org.objectweb.asm.tree.analysis.SourceInterpreter;
import org.objectweb.asm.tree.analysis.SourceValue;

/**
 * Exact bytecode provenance using ASM source analysis. The graph is observational only: it never
 * rewrites locals, stack state, control flow, or descriptors.
 */
public final class ValueProvenanceAnalyzer {
    public ProvenanceTrace analyze(MethodId methodId, MethodNode method) throws AnalyzerException {
        InstructionIndex executable = InstructionIndex.of(method);
        Analyzer<SourceValue> analyzer = new Analyzer<>(new SourceInterpreter());
        Frame<SourceValue>[] frames = analyzer.analyze(methodId.owner().internalName(), method);

        Map<AbstractInsnNode, Integer> rawIndex = new IdentityHashMap<>();
        int raw = 0;
        for (AbstractInsnNode node = method.instructions.getFirst(); node != null; node = node.getNext()) {
            rawIndex.put(node, raw++);
        }

        Map<Integer, String> parameterLocals = parameterLocals(method);
        List<ProvenanceStep> steps = new ArrayList<>();
        for (int opcodeIndex = 0; opcodeIndex < executable.size(); opcodeIndex++) {
            AbstractInsnNode instruction = executable.instruction(opcodeIndex);
            Frame<SourceValue> frame = frames[rawIndex.get(instruction)];
            InputResult inputs = frame == null
                    ? new InputResult(List.of(), false)
                    : inputs(instruction, frame, executable, methodId, parameterLocals);
            steps.add(new ProvenanceStep(
                    new InstructionId(methodId, opcodeIndex),
                    instruction.getOpcode(),
                    describe(instruction),
                    inputs.origins(),
                    inputs.complete()));
        }
        return new ProvenanceTrace(methodId, steps);
    }

    private static InputResult inputs(
            AbstractInsnNode instruction,
            Frame<SourceValue> frame,
            InstructionIndex executable,
            MethodId methodId,
            Map<Integer, String> parameterLocals) {
        if (instruction instanceof VarInsnNode varInsn && isLoad(varInsn.getOpcode())) {
            return new InputResult(
                    originsFromLocal(frame, varInsn.var, executable, methodId, parameterLocals),
                    true);
        }
        if (instruction instanceof IincInsnNode iinc) {
            return new InputResult(
                    originsFromLocal(frame, iinc.var, executable, methodId, parameterLocals),
                    true);
        }
        if (instruction instanceof VarInsnNode varInsn && varInsn.getOpcode() == Opcodes.RET) {
            return new InputResult(
                    originsFromLocal(frame, varInsn.var, executable, methodId, parameterLocals),
                    true);
        }

        int consumed = consumedValues(instruction);
        if (consumed < 0 || consumed > frame.getStackSize()) {
            return new InputResult(List.of(), false);
        }
        TreeSet<ValueOrigin> origins = new TreeSet<>();
        int start = frame.getStackSize() - consumed;
        for (int i = start; i < frame.getStackSize(); i++) {
            addOrigins(origins, frame.getStack(i), executable, methodId);
        }
        return new InputResult(List.copyOf(origins), true);
    }

    private static List<ValueOrigin> originsFromLocal(
            Frame<SourceValue> frame,
            int local,
            InstructionIndex executable,
            MethodId methodId,
            Map<Integer, String> parameterLocals) {
        if (local < 0 || local >= frame.getLocals()) {
            return List.of(new ValueOrigin(OriginKind.UNKNOWN_LOCAL, "local:" + local));
        }
        SourceValue value = frame.getLocal(local);
        TreeSet<ValueOrigin> origins = new TreeSet<>();
        addOrigins(origins, value, executable, methodId);
        if (origins.isEmpty()) {
            String parameter = parameterLocals.get(local);
            if (parameter != null) {
                origins.add(new ValueOrigin(OriginKind.ARGUMENT, parameter));
            } else {
                origins.add(new ValueOrigin(OriginKind.UNKNOWN_LOCAL, "local:" + local));
            }
        }
        return List.copyOf(origins);
    }

    private static void addOrigins(
            Set<ValueOrigin> target,
            SourceValue value,
            InstructionIndex executable,
            MethodId methodId) {
        if (value == null || value.insns == null) {
            return;
        }
        for (AbstractInsnNode source : value.insns) {
            if (source.getOpcode() < 0) {
                continue;
            }
            try {
                int index = executable.indexOf(source);
                target.add(new ValueOrigin(
                        OriginKind.INSTRUCTION,
                        new InstructionId(methodId, index).toString()));
            } catch (IllegalArgumentException ignored) {
                // SourceInterpreter should report instructions from this method only. If ASM ever
                // changes that contract, omit the foreign node instead of fabricating an identity.
            }
        }
    }

    private static Map<Integer, String> parameterLocals(MethodNode method) {
        Map<Integer, String> result = new HashMap<>();
        int local = 0;
        if ((method.access & Opcodes.ACC_STATIC) == 0) {
            result.put(local++, "this");
        }
        Type[] arguments = Type.getArgumentTypes(method.desc);
        for (int argument = 0; argument < arguments.length; argument++) {
            result.put(local, "arg:" + argument);
            local += arguments[argument].getSize();
        }
        return Collections.unmodifiableMap(result);
    }

    private static boolean isLoad(int opcode) {
        return opcode == Opcodes.ILOAD
                || opcode == Opcodes.LLOAD
                || opcode == Opcodes.FLOAD
                || opcode == Opcodes.DLOAD
                || opcode == Opcodes.ALOAD;
    }

    /** Number of logical JVM values consumed from the operand stack, or -1 when shape-dependent. */
    private static int consumedValues(AbstractInsnNode instruction) {
        int opcode = instruction.getOpcode();
        if (instruction instanceof FieldInsnNode) {
            return switch (opcode) {
                case Opcodes.GETSTATIC -> 0;
                case Opcodes.PUTSTATIC, Opcodes.GETFIELD -> 1;
                case Opcodes.PUTFIELD -> 2;
                default -> -1;
            };
        }
        if (instruction instanceof MethodInsnNode call) {
            int arguments = Type.getArgumentTypes(call.desc).length;
            return arguments + (opcode == Opcodes.INVOKESTATIC ? 0 : 1);
        }
        if (instruction instanceof InvokeDynamicInsnNode dynamic) {
            return Type.getArgumentTypes(dynamic.desc).length;
        }
        if (instruction instanceof MultiANewArrayInsnNode multi) {
            return multi.dims;
        }

        return switch (opcode) {
            case Opcodes.NOP,
                    Opcodes.ACONST_NULL,
                    Opcodes.ICONST_M1,
                    Opcodes.ICONST_0,
                    Opcodes.ICONST_1,
                    Opcodes.ICONST_2,
                    Opcodes.ICONST_3,
                    Opcodes.ICONST_4,
                    Opcodes.ICONST_5,
                    Opcodes.LCONST_0,
                    Opcodes.LCONST_1,
                    Opcodes.FCONST_0,
                    Opcodes.FCONST_1,
                    Opcodes.FCONST_2,
                    Opcodes.DCONST_0,
                    Opcodes.DCONST_1,
                    Opcodes.BIPUSH,
                    Opcodes.SIPUSH,
                    Opcodes.LDC,
                    Opcodes.NEW,
                    Opcodes.GETSTATIC,
                    Opcodes.GOTO,
                    Opcodes.RETURN -> 0;

            case Opcodes.ISTORE,
                    Opcodes.LSTORE,
                    Opcodes.FSTORE,
                    Opcodes.DSTORE,
                    Opcodes.ASTORE,
                    Opcodes.POP,
                    Opcodes.INEG,
                    Opcodes.LNEG,
                    Opcodes.FNEG,
                    Opcodes.DNEG,
                    Opcodes.I2L,
                    Opcodes.I2F,
                    Opcodes.I2D,
                    Opcodes.L2I,
                    Opcodes.L2F,
                    Opcodes.L2D,
                    Opcodes.F2I,
                    Opcodes.F2L,
                    Opcodes.F2D,
                    Opcodes.D2I,
                    Opcodes.D2L,
                    Opcodes.D2F,
                    Opcodes.I2B,
                    Opcodes.I2C,
                    Opcodes.I2S,
                    Opcodes.IRETURN,
                    Opcodes.LRETURN,
                    Opcodes.FRETURN,
                    Opcodes.DRETURN,
                    Opcodes.ARETURN,
                    Opcodes.ARRAYLENGTH,
                    Opcodes.ATHROW,
                    Opcodes.CHECKCAST,
                    Opcodes.INSTANCEOF,
                    Opcodes.MONITORENTER,
                    Opcodes.MONITOREXIT,
                    Opcodes.NEWARRAY,
                    Opcodes.ANEWARRAY,
                    Opcodes.TABLESWITCH,
                    Opcodes.LOOKUPSWITCH,
                    Opcodes.IFEQ,
                    Opcodes.IFNE,
                    Opcodes.IFLT,
                    Opcodes.IFGE,
                    Opcodes.IFGT,
                    Opcodes.IFLE,
                    Opcodes.IFNULL,
                    Opcodes.IFNONNULL -> 1;

            case Opcodes.IALOAD,
                    Opcodes.LALOAD,
                    Opcodes.FALOAD,
                    Opcodes.DALOAD,
                    Opcodes.AALOAD,
                    Opcodes.BALOAD,
                    Opcodes.CALOAD,
                    Opcodes.SALOAD,
                    Opcodes.IADD,
                    Opcodes.LADD,
                    Opcodes.FADD,
                    Opcodes.DADD,
                    Opcodes.ISUB,
                    Opcodes.LSUB,
                    Opcodes.FSUB,
                    Opcodes.DSUB,
                    Opcodes.IMUL,
                    Opcodes.LMUL,
                    Opcodes.FMUL,
                    Opcodes.DMUL,
                    Opcodes.IDIV,
                    Opcodes.LDIV,
                    Opcodes.FDIV,
                    Opcodes.DDIV,
                    Opcodes.IREM,
                    Opcodes.LREM,
                    Opcodes.FREM,
                    Opcodes.DREM,
                    Opcodes.ISHL,
                    Opcodes.LSHL,
                    Opcodes.ISHR,
                    Opcodes.LSHR,
                    Opcodes.IUSHR,
                    Opcodes.LUSHR,
                    Opcodes.IAND,
                    Opcodes.LAND,
                    Opcodes.IOR,
                    Opcodes.LOR,
                    Opcodes.IXOR,
                    Opcodes.LXOR,
                    Opcodes.LCMP,
                    Opcodes.FCMPL,
                    Opcodes.FCMPG,
                    Opcodes.DCMPL,
                    Opcodes.DCMPG,
                    Opcodes.IF_ICMPEQ,
                    Opcodes.IF_ICMPNE,
                    Opcodes.IF_ICMPLT,
                    Opcodes.IF_ICMPGE,
                    Opcodes.IF_ICMPGT,
                    Opcodes.IF_ICMPLE,
                    Opcodes.IF_ACMPEQ,
                    Opcodes.IF_ACMPNE -> 2;

            case Opcodes.IASTORE,
                    Opcodes.LASTORE,
                    Opcodes.FASTORE,
                    Opcodes.DASTORE,
                    Opcodes.AASTORE,
                    Opcodes.BASTORE,
                    Opcodes.CASTORE,
                    Opcodes.SASTORE -> 3;

            case Opcodes.DUP, Opcodes.DUP_X1, Opcodes.DUP_X2,
                    Opcodes.DUP2, Opcodes.DUP2_X1, Opcodes.DUP2_X2,
                    Opcodes.POP2, Opcodes.SWAP,
                    Opcodes.JSR -> -1;
            default -> 0;
        };
    }

    private static String describe(AbstractInsnNode instruction) {
        if (instruction instanceof FieldInsnNode field) {
            String action = switch (instruction.getOpcode()) {
                case Opcodes.GETFIELD, Opcodes.GETSTATIC -> "field-read ";
                case Opcodes.PUTFIELD, Opcodes.PUTSTATIC -> "field-write ";
                default -> "field ";
            };
            return action + field.owner + "." + field.name + field.desc;
        }
        if (instruction instanceof MethodInsnNode call) {
            return "invoke " + call.owner + "." + call.name + call.desc;
        }
        if (instruction instanceof InvokeDynamicInsnNode dynamic) {
            return "invokedynamic " + dynamic.name + dynamic.desc;
        }
        if (instruction instanceof VarInsnNode varInsn) {
            return (isLoad(varInsn.getOpcode()) ? "local-load " : "local-store ") + varInsn.var;
        }
        if (instruction instanceof IincInsnNode iinc) {
            return "local-iinc " + iinc.var + " by " + iinc.incr;
        }
        if (instruction instanceof JumpInsnNode) {
            return "branch";
        }
        return instruction.getClass().getSimpleName();
    }

    private record InputResult(List<ValueOrigin> origins, boolean complete) {
    }
}
