package dev.neosback.deob.analysis.injected;

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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * Extracts only high-precision RuneLite-style injected API bridges. Complex methods are left
 * unclassified instead of being forced into a field/method mapping.
 */
public final class InjectedApiBridgeAnalyzer {
    private static final String RS_API_PREFIX = "net/runelite/rs/api/";

    public InjectedApiBridgeReport analyze(JarCorpus corpus) {
        Map<ClassId, ClassNode> nodes = new HashMap<>();
        for (ClassArtifact artifact : corpus.classes().values()) {
            nodes.put(artifact.id(), artifact.readNode());
        }

        List<InjectedApiBridge> bridges = new ArrayList<>();
        int unclassified = 0;
        for (Map.Entry<ClassId, ClassNode> entry : nodes.entrySet()) {
            ClassId owner = entry.getKey();
            ClassNode classNode = entry.getValue();
            List<ClassId> rsInterfaces = inheritedRsInterfaces(owner, nodes);
            if (rsInterfaces.isEmpty()) continue;

            for (MethodNode method : classNode.methods) {
                if (!isSemanticApiMethod(method)) continue;
                InjectedApiBridge bridge = classify(owner, method, rsInterfaces);
                if (bridge == null) {
                    unclassified++;
                } else {
                    bridges.add(bridge);
                }
            }
        }

        bridges.sort(Comparator
                .comparing(InjectedApiBridge::semanticName)
                .thenComparing(InjectedApiBridge::injectedMethod));
        List<String> warnings = new ArrayList<>();
        if (bridges.isEmpty()) {
            warnings.add("no exact injected API bridge shapes were found");
        }
        if (unclassified > 0) {
            warnings.add(unclassified + " semantic-looking public methods on RS API implementors were deliberately left unclassified");
        }
        return new InjectedApiBridgeReport(corpus.sha256(), bridges, unclassified, warnings);
    }

    private static InjectedApiBridge classify(
            ClassId owner,
            MethodNode method,
            List<ClassId> rsInterfaces) {
        InstructionIndex index = InstructionIndex.of(method);
        if (index.size() == 0) return null;

        List<Integer> fieldReads = new ArrayList<>();
        List<Integer> fieldWrites = new ArrayList<>();
        List<Integer> calls = new ArrayList<>();
        List<Integer> multiplies = new ArrayList<>();
        List<Integer> returns = new ArrayList<>();

        for (int i = 0; i < index.size(); i++) {
            AbstractInsnNode instruction = index.instruction(i);
            int opcode = instruction.getOpcode();
            if (instruction instanceof FieldInsnNode) {
                if (opcode == Opcodes.GETFIELD || opcode == Opcodes.GETSTATIC) fieldReads.add(i);
                if (opcode == Opcodes.PUTFIELD || opcode == Opcodes.PUTSTATIC) fieldWrites.add(i);
            }
            if (instruction instanceof MethodInsnNode) calls.add(i);
            if (opcode == Opcodes.IMUL || opcode == Opcodes.LMUL) multiplies.add(i);
            if (opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN) returns.add(i);
        }

        MethodId injected = new MethodId(owner, method.name, method.desc);
        Type returnType = Type.getReturnType(method.desc);

        if (fieldReads.size() == 1 && fieldWrites.isEmpty() && calls.isEmpty()
                && returns.size() == 1 && returnType.getSort() != Type.VOID
                && index.size() <= 7) {
            int fieldIndex = fieldReads.get(0);
            FieldInsnNode field = (FieldInsnNode) index.instruction(fieldIndex);
            FieldId target = new FieldId(new ClassId(field.owner), field.name, field.desc);
            Long multiplier = multiplies.size() == 1
                    ? nearbyIntegralConstant(index, multiplies.get(0))
                    : null;
            InjectedBridgeKind kind;
            if (multiplier != null && "I".equals(field.desc)
                    && index.instruction(multiplies.get(0)).getOpcode() == Opcodes.IMUL) {
                kind = InjectedBridgeKind.DIRECT_DECODED_INT_GETTER;
            } else if (multiplier != null && "J".equals(field.desc)
                    && index.instruction(multiplies.get(0)).getOpcode() == Opcodes.LMUL) {
                kind = InjectedBridgeKind.DIRECT_DECODED_LONG_GETTER;
            } else if (multiplies.isEmpty()) {
                kind = InjectedBridgeKind.DIRECT_FIELD_GETTER;
            } else {
                return null;
            }
            return new InjectedApiBridge(
                    injected,
                    rsInterfaces,
                    semanticTerm(method.name),
                    kind,
                    target,
                    null,
                    multiplier,
                    field.getOpcode() == Opcodes.GETSTATIC,
                    evidence(injected, fieldIndex, multiplies));
        }

        if (fieldWrites.size() == 1 && fieldReads.isEmpty() && calls.isEmpty()
                && returns.size() == 1 && returnType.getSort() == Type.VOID
                && Type.getArgumentTypes(method.desc).length >= 1
                && index.size() <= 8) {
            int fieldIndex = fieldWrites.get(0);
            FieldInsnNode field = (FieldInsnNode) index.instruction(fieldIndex);
            FieldId target = new FieldId(new ClassId(field.owner), field.name, field.desc);
            Long multiplier = multiplies.size() == 1
                    ? nearbyIntegralConstant(index, multiplies.get(0))
                    : null;
            InjectedBridgeKind kind;
            if (multiplier != null && "I".equals(field.desc)
                    && index.instruction(multiplies.get(0)).getOpcode() == Opcodes.IMUL) {
                kind = InjectedBridgeKind.DIRECT_ENCODED_INT_SETTER;
            } else if (multiplier != null && "J".equals(field.desc)
                    && index.instruction(multiplies.get(0)).getOpcode() == Opcodes.LMUL) {
                kind = InjectedBridgeKind.DIRECT_ENCODED_LONG_SETTER;
            } else if (multiplies.isEmpty()) {
                kind = InjectedBridgeKind.DIRECT_FIELD_SETTER;
            } else {
                return null;
            }
            return new InjectedApiBridge(
                    injected,
                    rsInterfaces,
                    semanticTerm(method.name),
                    kind,
                    target,
                    null,
                    multiplier,
                    field.getOpcode() == Opcodes.PUTSTATIC,
                    evidence(injected, fieldIndex, multiplies));
        }

        if (calls.size() == 1 && fieldReads.isEmpty() && fieldWrites.isEmpty()
                && returns.size() == 1 && index.size() <= 12) {
            int callIndex = calls.get(0);
            MethodInsnNode call = (MethodInsnNode) index.instruction(callIndex);
            MethodId target = new MethodId(new ClassId(call.owner), call.name, call.desc);
            return new InjectedApiBridge(
                    injected,
                    rsInterfaces,
                    semanticTerm(method.name),
                    InjectedBridgeKind.DIRECT_METHOD_INVOKER,
                    null,
                    target,
                    null,
                    call.getOpcode() == Opcodes.INVOKESTATIC,
                    List.of(new InstructionId(injected, callIndex)));
        }

        return null;
    }

    private static List<ClassId> inheritedRsInterfaces(
            ClassId start,
            Map<ClassId, ClassNode> nodes) {
        Set<ClassId> result = new TreeSet<>();
        Set<ClassId> seen = new LinkedHashSet<>();
        ClassId current = start;
        while (current != null && seen.add(current)) {
            ClassNode node = nodes.get(current);
            if (node == null) break;
            for (String iface : node.interfaces) {
                if (iface.startsWith(RS_API_PREFIX)) result.add(new ClassId(iface));
            }
            current = node.superName == null ? null : new ClassId(node.superName);
        }
        return List.copyOf(result);
    }

    private static boolean isSemanticApiMethod(MethodNode method) {
        if ((method.access & Opcodes.ACC_PUBLIC) == 0) return false;
        if ((method.access & Opcodes.ACC_ABSTRACT) != 0) return false;
        if (method.name.startsWith("<")) return false;
        return !method.name.matches("[a-z]{1,2}");
    }

    private static String semanticTerm(String methodName) {
        String base = methodName;
        if (base.startsWith("get") && base.length() > 3) base = base.substring(3);
        else if (base.startsWith("set") && base.length() > 3) base = base.substring(3);
        else if (base.startsWith("is") && base.length() > 2) base = base.substring(2);
        if (base.isEmpty()) return methodName;
        return Character.toLowerCase(base.charAt(0)) + base.substring(1);
    }

    private static List<InstructionId> evidence(
            MethodId method,
            int memberInstruction,
            List<Integer> multiplies) {
        Set<InstructionId> ids = new TreeSet<>();
        ids.add(new InstructionId(method, memberInstruction));
        for (int multiply : multiplies) ids.add(new InstructionId(method, multiply));
        return List.copyOf(ids);
    }

    private static Long nearbyIntegralConstant(InstructionIndex index, int operation) {
        for (int delta = 1; delta <= 3; delta++) {
            int candidate = operation - delta;
            if (candidate < 0) break;
            Long value = integralConstant(index.instruction(candidate));
            if (value != null) return value;
        }
        return null;
    }

    private static Long integralConstant(AbstractInsnNode instruction) {
        int opcode = instruction.getOpcode();
        if (opcode >= Opcodes.ICONST_M1 && opcode <= Opcodes.ICONST_5) {
            return (long) (opcode - Opcodes.ICONST_0);
        }
        if (opcode == Opcodes.LCONST_0) return 0L;
        if (opcode == Opcodes.LCONST_1) return 1L;
        if (instruction instanceof IntInsnNode intInsn) return (long) intInsn.operand;
        if (instruction instanceof LdcInsnNode ldc && ldc.cst instanceof Integer integer) {
            return integer.longValue();
        }
        if (instruction instanceof LdcInsnNode ldc && ldc.cst instanceof Long value) {
            return value;
        }
        return null;
    }
}
