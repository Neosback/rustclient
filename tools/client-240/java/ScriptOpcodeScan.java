import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

/**
 * Recovers the revision-240 CS2 opcode dispatch from bytecode (analysis evidence only).
 *
 * Usage: java --source 17 -cp ASM... ScriptOpcodeScan.java JAR OUT.tsv
 *
 * Every client script instruction is handled by a static method shaped {@code (I, Script, Z[, B])I}
 * whose first parameter is the opcode. A branch {@code if (opcode == N)} compiles to
 * {@code ILOAD 0; <push N>; IF_ICMPNE next}, so the instructions up to {@code next} are that opcode's body.
 * Stack sizes are stored multiplied by a per-field constant; the array index expression
 * {@code stack[size * k]} reveals k, and an update {@code size -= K} then moves the real stack by K * k.
 * The result is a per-opcode handler plus popped/pushed counts for the integer, object and long stacks.
 * The scan reads control flow only: it never decides what an opcode means.
 */
public final class ScriptOpcodeScan {
    static final Pattern HANDLER = Pattern.compile("\\(ILScript;Z[BISC]?\\)I");
    static final String INT_ARRAY = "[I";
    static final String OBJECT_ARRAY = "[Ljava/lang/Object;";
    static final String LONG_ARRAY = "[J";

    /** {@code owner} is the stack array (class.field); {@code field} is its multiplied size (class.field). */
    record Stack(String name, String owner, String field, int multiplier) {}

    static final class Body {
        int opcode;
        String handler;
        String form;
        int conditionals;
        Map<String, Integer> deltas = new TreeMap<>();
        Map<String, Integer> pops = new TreeMap<>();
        Map<String, Integer> pushes = new TreeMap<>();
        Set<String> calls = new TreeSet<>();
        boolean regionKnown;
        String group = "";
        boolean unreliable;
    }

    public static void main(String[] args) throws IOException {
        List<ClassNode> classes = load(Path.of(args[0]));
        List<MethodContext> handlers = new ArrayList<>();
        for (ClassNode cls : classes) {
            for (MethodNode method : cls.methods) {
                if ((method.access & Opcodes.ACC_STATIC) != 0 && HANDLER.matcher(method.desc).matches()) {
                    handlers.add(new MethodContext(cls, method));
                }
            }
        }
        Map<String, Stack> stacks = findStacks(handlers);
        Map<Integer, List<Body>> bodies = new TreeMap<>();
        for (MethodContext context : handlers) {
            scan(context, stacks, bodies);
        }
        try (PrintWriter out = new PrintWriter(Path.of(args[1]).toFile(), StandardCharsets.UTF_8)) {
            out.println("opcode\thandler\tform\tgroup\tregion\treliable\tconditionals\tint_pop\tint_push\tobj_pop\tobj_push\tlong_pop\tlong_push\tcalls");
            for (List<Body> list : bodies.values()) {
                for (Body body : list) {
                    out.printf("%d\t%s\t%s\t%s\t%s\t%s\t%d\t%s\t%s\t%s\t%s\t%s\t%s\t%s%n", body.opcode, body.handler, body.form,
                            body.group, body.regionKnown, !body.unreliable, body.conditionals, count(body.pops, "int"), count(body.pushes, "int"),
                            count(body.pops, "obj"), count(body.pushes, "obj"), count(body.pops, "long"),
                            count(body.pushes, "long"), String.join(",", body.calls));
                }
            }
        }
        System.out.printf("handlers: %d; opcodes: %d; stacks: %s%n", handlers.size(), bodies.size(), stacks);
    }

    static String count(Map<String, Integer> map, String key) {
        return map.containsKey(key) ? Integer.toString(map.get(key)) : "?";
    }

    record MethodContext(ClassNode owner, MethodNode method) {
        String id() {
            return owner.name + "." + method.name + method.desc;
        }
    }

    static List<ClassNode> load(Path jar) throws IOException {
        List<ClassNode> classes = new ArrayList<>();
        try (JarFile file = new JarFile(jar.toFile())) {
            for (JarEntry entry : Collections.list(file.entries())) {
                if (entry.getName().endsWith(".class")) {
                    ClassNode node = new ClassNode();
                    new ClassReader(file.getInputStream(entry).readAllBytes()).accept(node, 0);
                    classes.add(node);
                }
            }
        }
        classes.sort(Comparator.comparing(node -> node.name));
        return classes;
    }

    static List<AbstractInsnNode> real(MethodNode method) {
        List<AbstractInsnNode> list = new ArrayList<>();
        for (AbstractInsnNode insn : method.instructions) {
            if (insn.getOpcode() >= 0) {
                list.add(insn);
            }
        }
        return list;
    }

    static Integer intConstant(AbstractInsnNode insn) {
        int op = insn.getOpcode();
        if (op >= Opcodes.ICONST_M1 && op <= Opcodes.ICONST_5) {
            return op - Opcodes.ICONST_0;
        }
        if (insn instanceof IntInsnNode push && (op == Opcodes.BIPUSH || op == Opcodes.SIPUSH)) {
            return push.operand;
        }
        if (insn instanceof LdcInsnNode ldc && ldc.cst instanceof Integer value) {
            return value;
        }
        return null;
    }

    /** Pair each stack array with the multiplied size field that indexes it, by majority vote. */
    static Map<String, Stack> findStacks(List<MethodContext> handlers) {
        Map<String, Map<String, Integer>> votes = new HashMap<>();
        for (MethodContext context : handlers) {
            List<AbstractInsnNode> code = real(context.method);
            for (int i = 0; i + 4 < code.size(); i++) {
                if (!(code.get(i) instanceof FieldInsnNode array) || array.getOpcode() != Opcodes.GETSTATIC) {
                    continue;
                }
                String kind = switch (array.desc) {
                    case INT_ARRAY -> "int";
                    case OBJECT_ARRAY -> "obj";
                    case LONG_ARRAY -> "long";
                    default -> null;
                };
                if (kind == null) {
                    continue;
                }
                for (int offset = 1; offset <= 3 && i + offset + 2 < code.size(); offset++) {
                    if (code.get(i + offset) instanceof FieldInsnNode size && size.getOpcode() == Opcodes.GETSTATIC
                            && size.desc.equals("I")) {
                        Integer k = null;
                        AbstractInsnNode after = code.get(i + offset + 1);
                        AbstractInsnNode then = code.get(i + offset + 2);
                        if (intConstant(after) != null && then.getOpcode() == Opcodes.IMUL) {
                            k = intConstant(after);
                        }
                        if (k != null) {
                            String key = array.owner + "." + array.name + "|" + size.owner + "." + size.name + "|" + k;
                            votes.computeIfAbsent(kind, ignored -> new HashMap<>()).merge(key, 1, Integer::sum);
                        }
                        break;
                    }
                }
            }
        }
        Map<String, Stack> result = new LinkedHashMap<>();
        for (String kind : List.of("int", "obj", "long")) {
            Map<String, Integer> options = votes.getOrDefault(kind, Map.of());
            String best = options.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
            if (best != null) {
                String[] parts = best.split("\\|");
                result.put(kind, new Stack(kind, parts[0], parts[1], Integer.parseInt(parts[2])));
            }
        }
        return result;
    }

    static void scan(MethodContext context, Map<String, Stack> stacks, Map<Integer, List<Body>> bodies) {
        MethodNode method = context.method;
        List<AbstractInsnNode> code = real(method);
        Map<LabelNode, Integer> labelIndex = new HashMap<>();
        // Map labels to the index of the next real instruction.
        int index = 0;
        for (AbstractInsnNode insn : method.instructions) {
            if (insn instanceof LabelNode label) {
                labelIndex.put(label, index);
            } else if (insn.getOpcode() >= 0) {
                index++;
            }
        }
        for (int i = 0; i + 2 < code.size(); i++) {
            Integer opcode = null;
            AbstractInsnNode jump = null;
            if (isLoadParameter(code.get(i)) && intConstant(code.get(i + 1)) != null && code.get(i + 2) instanceof JumpInsnNode j) {
                opcode = intConstant(code.get(i + 1));
                jump = j;
            } else if (intConstant(code.get(i)) != null && isLoadParameter(code.get(i + 1)) && code.get(i + 2) instanceof JumpInsnNode j) {
                opcode = intConstant(code.get(i));
                jump = j;
            }
            if (opcode != null && opcode >= 0 && jump != null) {
                JumpInsnNode comparison = (JumpInsnNode) jump;
                Body body = new Body();
                body.opcode = opcode;
                body.handler = context.id();
                int target = labelIndex.getOrDefault(comparison.label, -1);
                if (comparison.getOpcode() == Opcodes.IF_ICMPNE) {
                    body.form = "ne";
                    int end = target > i + 3 ? target : untilReturn(code, i + 3);
                    body.regionKnown = target > i + 3;
                    analyse(code, i + 3, end, stacks, body);
                } else if (comparison.getOpcode() == Opcodes.IF_ICMPEQ && target > i) {
                    // `a == N || b == M || ...` jumps into one shared body: rows with the same target are a group.
                    body.form = "eq";
                    body.group = handlerLabel(context, target);
                    analyse(code, target, untilReturn(code, target), stacks, body);
                } else {
                    continue;
                }
                bodies.computeIfAbsent(opcode, ignored -> new ArrayList<>()).add(body);
            }
        }
        for (int i = 0; i + 1 < code.size(); i++) {
            if (isLoadParameter(code.get(i))) {
                AbstractInsnNode next = code.get(i + 1);
                if (next instanceof TableSwitchInsnNode table) {
                    for (int key = table.min; key <= table.max; key++) {
                        addSwitch(context, bodies, key);
                    }
                } else if (next instanceof LookupSwitchInsnNode lookup) {
                    for (int key : lookup.keys) {
                        addSwitch(context, bodies, key);
                    }
                }
            }
        }
    }

    static int untilReturn(List<AbstractInsnNode> code, int from) {
        for (int i = from; i < code.size(); i++) {
            if (code.get(i).getOpcode() == Opcodes.IRETURN) {
                return i + 1;
            }
        }
        return code.size();
    }

    static String handlerLabel(MethodContext context, int target) {
        return context.owner.name + "." + context.method.name + "@" + target;
    }

    static void addSwitch(MethodContext context, Map<Integer, List<Body>> bodies, int key) {
        Body body = new Body();
        body.opcode = key;
        body.handler = context.id();
        body.form = "switch";
        bodies.computeIfAbsent(key, ignored -> new ArrayList<>()).add(body);
    }

    static boolean isLoadParameter(AbstractInsnNode insn) {
        return insn instanceof VarInsnNode load && load.getOpcode() == Opcodes.ILOAD && load.var == 0;
    }

    static void analyse(List<AbstractInsnNode> code, int from, int to, Map<String, Stack> stacks, Body body) {
        for (int i = from; i < to && i < code.size(); i++) {
            AbstractInsnNode insn = code.get(i);
            if (insn instanceof JumpInsnNode jump && jump.getOpcode() != Opcodes.GOTO) {
                body.conditionals++;
            }
            if (insn instanceof MethodInsnNode call && !call.owner.startsWith("java/")) {
                body.calls.add(call.owner + "." + call.name);
            }
            if (i + 3 < code.size() && insn instanceof FieldInsnNode get && get.getOpcode() == Opcodes.GETSTATIC
                    && code.get(i + 3) instanceof FieldInsnNode put && put.getOpcode() == Opcodes.PUTSTATIC
                    && put.owner.equals(get.owner) && put.name.equals(get.name)) {
                Integer delta = intConstant(code.get(i + 1));
                int arithmetic = code.get(i + 2).getOpcode();
                if (delta != null && (arithmetic == Opcodes.ISUB || arithmetic == Opcodes.IADD)) {
                    for (Map.Entry<String, Stack> entry : stacks.entrySet()) {
                        Stack stack = entry.getValue();
                        if ((get.owner + "." + get.name).equals(stack.field())) {
                            // Real change in stack entries: multiplied delta times the index multiplier.
                            int units = (arithmetic == Opcodes.ISUB ? -delta : delta) * stack.multiplier();
                            if (Math.abs(units) > 64) {
                                body.unreliable = true;
                                continue;
                            }
                            body.deltas.merge(entry.getKey(), units, Integer::sum);
                            if (units < 0) {
                                body.pops.merge(entry.getKey(), -units, Integer::sum);
                            } else {
                                body.pushes.merge(entry.getKey(), units, Integer::sum);
                            }
                        }
                    }
                }
            }
        }
        for (String kind : stacks.keySet()) {
            body.pops.putIfAbsent(kind, 0);
            body.pushes.putIfAbsent(kind, 0);
        }
    }
}
