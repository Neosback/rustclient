import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.objectweb.asm.ConstantDynamic;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.MultiANewArrayInsnNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;
import org.objectweb.asm.tree.TryCatchBlockNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

/**
 * Bytecode-first evidence tooling for revision-240 research.
 *
 * <p>This tool intentionally does not rename or rewrite classes. It extracts facts from jars so
 * decompiled Java can remain a presentation layer rather than the source of truth.
 *
 * <pre>
 * java --source 17 -cp ASM_CLASSPATH Client240Evidence.java index JAR OUT.tsv
 * java --source 17 -cp ASM_CLASSPATH Client240Evidence.java audit ORIGINAL.jar SEMANTIC.jar OUT.tsv
 * java --source 17 -cp ASM_CLASSPATH Client240Evidence.java refs JAR CLASS [CLASS...]
 * </pre>
 */
public final class Client240Evidence {
    static final String OBF_NAME = "Lnet/runelite/mapping/ObfuscatedName;";
    static final String OBF_SIG = "Lnet/runelite/mapping/ObfuscatedSignature;";

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            usage();
            System.exit(2);
        }
        switch (args[0]) {
            case "index" -> {
                requireArgs(args, 3);
                index(Path.of(args[1]), Path.of(args[2]));
            }
            case "audit" -> {
                requireArgs(args, 4);
                audit(Path.of(args[1]), Path.of(args[2]), Path.of(args[3]));
            }
            case "refs" -> {
                if (args.length < 3) {
                    usage();
                    System.exit(2);
                }
                refs(Path.of(args[1]), List.of(args).subList(2, args.length));
            }
            default -> {
                usage();
                throw new IllegalArgumentException("unknown command: " + args[0]);
            }
        }
    }

    static void usage() {
        System.err.println("Client240Evidence commands:");
        System.err.println("  index JAR OUT.tsv");
        System.err.println("  audit ORIGINAL.jar SEMANTIC.jar OUT.tsv");
        System.err.println("  refs JAR CLASS [CLASS...]");
    }

    static void requireArgs(String[] args, int count) {
        if (args.length != count) {
            usage();
            throw new IllegalArgumentException("expected " + (count - 1) + " argument(s) after command");
        }
    }

    static Map<String, ClassNode> load(Path jar) throws IOException {
        Map<String, ClassNode> classes = new TreeMap<>();
        try (JarFile file = new JarFile(jar.toFile())) {
            for (JarEntry entry : Collections.list(file.entries())) {
                if (!entry.getName().endsWith(".class")) {
                    continue;
                }
                ClassNode node = new ClassNode();
                new org.objectweb.asm.ClassReader(file.getInputStream(entry).readAllBytes()).accept(node, 0);
                classes.put(node.name, node);
            }
        }
        return classes;
    }

    static String annotation(List<AnnotationNode> visible, List<AnnotationNode> invisible, String desc) {
        List<AnnotationNode> all = new ArrayList<>();
        if (visible != null) {
            all.addAll(visible);
        }
        if (invisible != null) {
            all.addAll(invisible);
        }
        for (AnnotationNode annotation : all) {
            if (!desc.equals(annotation.desc) || annotation.values == null) {
                continue;
            }
            for (int i = 0; i + 1 < annotation.values.size(); i += 2) {
                Object key = annotation.values.get(i);
                if ("value".equals(key) || "descriptor".equals(key)) {
                    return String.valueOf(annotation.values.get(i + 1));
                }
            }
        }
        return null;
    }

    record MemberKey(String owner, String name, String desc) {}

    static final class Identity {
        final Map<String, ClassNode> classes;
        final Map<String, String> classNames = new HashMap<>();
        final Map<MemberKey, String> fieldNames = new HashMap<>();
        final Map<MemberKey, String> methodNames = new HashMap<>();

        Identity(Map<String, ClassNode> classes) {
            this.classes = classes;
            for (ClassNode cls : classes.values()) {
                String obf = annotation(cls.visibleAnnotations, cls.invisibleAnnotations, OBF_NAME);
                classNames.put(cls.name, obf == null ? cls.name : obf);
                for (FieldNode field : cls.fields) {
                    String name = annotation(field.visibleAnnotations, field.invisibleAnnotations, OBF_NAME);
                    fieldNames.put(new MemberKey(cls.name, field.name, field.desc), name == null ? field.name : name);
                }
                for (MethodNode method : cls.methods) {
                    String name = annotation(method.visibleAnnotations, method.invisibleAnnotations, OBF_NAME);
                    methodNames.put(new MemberKey(cls.name, method.name, method.desc), name == null ? method.name : name);
                }
            }
        }

        String className(String current) {
            return classNames.getOrDefault(current, current);
        }

        String fieldName(String owner, String name, String desc) {
            for (ClassNode cls = classes.get(owner); cls != null; cls = classes.get(cls.superName)) {
                String found = fieldNames.get(new MemberKey(cls.name, name, desc));
                if (found != null) {
                    return found;
                }
            }
            return name;
        }

        String methodName(String owner, String name, String desc) {
            String direct = methodNames.get(new MemberKey(owner, name, desc));
            if (direct != null) {
                return direct;
            }
            for (ClassNode cls = classes.get(owner); cls != null; cls = classes.get(cls.superName)) {
                String found = methodNames.get(new MemberKey(cls.name, name, desc));
                if (found != null) {
                    return found;
                }
            }
            return name;
        }

        String descriptor(String desc) {
            Type type = Type.getType(desc);
            return canonicalType(type);
        }

        String methodDescriptor(String desc) {
            Type method = Type.getMethodType(desc);
            StringBuilder out = new StringBuilder("(");
            for (Type arg : method.getArgumentTypes()) {
                out.append(canonicalType(arg));
            }
            return out.append(')').append(canonicalType(method.getReturnType())).toString();
        }

        String canonicalType(Type type) {
            return switch (type.getSort()) {
                case Type.VOID -> "V";
                case Type.BOOLEAN -> "Z";
                case Type.CHAR -> "C";
                case Type.BYTE -> "B";
                case Type.SHORT -> "S";
                case Type.INT -> "I";
                case Type.FLOAT -> "F";
                case Type.LONG -> "J";
                case Type.DOUBLE -> "D";
                case Type.ARRAY -> "[".repeat(type.getDimensions()) + canonicalType(type.getElementType());
                case Type.OBJECT -> "L" + className(type.getInternalName()) + ";";
                case Type.METHOD -> methodDescriptor(type.getDescriptor());
                default -> throw new IllegalArgumentException("unsupported type: " + type);
            };
        }

        String methodIdentity(ClassNode cls, MethodNode method) {
            String obf = annotation(method.visibleAnnotations, method.invisibleAnnotations, OBF_NAME);
            String name = obf == null ? method.name : obf;
            String sig = annotation(method.visibleAnnotations, method.invisibleAnnotations, OBF_SIG);
            String desc = sig == null || sig.isBlank() ? methodDescriptor(method.desc) : normalizeObfuscatedDescriptor(sig);
            return className(cls.name) + "." + name + desc;
        }

        String normalizeObfuscatedDescriptor(String descriptor) {
            try {
                if (descriptor.startsWith("(")) {
                    return methodDescriptor(descriptor);
                }
                return descriptor(descriptor);
            } catch (IllegalArgumentException ignored) {
                return descriptor;
            }
        }
    }

    record Metrics(
            int instructions,
            int branches,
            int switches,
            int tryCatches,
            String strictHash,
            String canonicalHash,
            Set<String> invokes,
            Set<String> fields,
            Set<String> constants) {}

    static Metrics metrics(Identity identity, ClassNode owner, MethodNode method) {
        List<AbstractInsnNode> real = new ArrayList<>();
        Map<LabelNode, Integer> labels = new HashMap<>();
        int next = 0;
        for (AbstractInsnNode insn : method.instructions) {
            if (insn instanceof LabelNode label) {
                labels.put(label, next);
            }
            if (insn.getOpcode() >= 0) {
                real.add(insn);
                next++;
            }
        }

        StringBuilder strict = new StringBuilder();
        StringBuilder canonical = new StringBuilder();
        Set<String> invokes = new TreeSet<>();
        Set<String> fields = new TreeSet<>();
        Set<String> constants = new TreeSet<>();
        int branches = 0;
        int switches = 0;

        for (AbstractInsnNode insn : real) {
            strict.append(renderInsn(identity, insn, labels, false, invokes, fields, constants)).append('\n');
            canonical.append(renderInsn(identity, insn, labels, true, invokes, fields, constants)).append('\n');
            if (insn instanceof JumpInsnNode) {
                branches++;
            }
            if (insn instanceof TableSwitchInsnNode || insn instanceof LookupSwitchInsnNode) {
                switches++;
            }
        }
        for (TryCatchBlockNode block : method.tryCatchBlocks) {
            strict.append("TC:").append(labelIndex(labels, block.start)).append(':')
                    .append(labelIndex(labels, block.end)).append(':').append(labelIndex(labels, block.handler))
                    .append(':').append(block.type).append('\n');
            canonical.append("TC:").append(labelIndex(labels, block.start)).append(':')
                    .append(labelIndex(labels, block.end)).append(':').append(labelIndex(labels, block.handler))
                    .append(':').append(block.type == null ? "*" : identity.className(block.type)).append('\n');
        }
        return new Metrics(real.size(), branches, switches, method.tryCatchBlocks.size(), sha256(strict.toString()),
                sha256(canonical.toString()), invokes, fields, constants);
    }

    static String renderInsn(
            Identity identity,
            AbstractInsnNode insn,
            Map<LabelNode, Integer> labels,
            boolean canonical,
            Set<String> invokes,
            Set<String> fields,
            Set<String> constants) {
        StringBuilder out = new StringBuilder().append(insn.getOpcode());
        if (insn instanceof IntInsnNode value) {
            out.append(':').append(value.operand);
        } else if (insn instanceof VarInsnNode value) {
            out.append(':').append(value.var);
        } else if (insn instanceof TypeInsnNode value) {
            out.append(':').append(canonical ? identity.className(value.desc) : value.desc);
        } else if (insn instanceof FieldInsnNode value) {
            String owner = canonical ? identity.className(value.owner) : value.owner;
            String name = canonical ? identity.fieldName(value.owner, value.name, value.desc) : value.name;
            String desc = canonical ? identity.descriptor(value.desc) : value.desc;
            String ref = owner + "." + name + ":" + desc;
            out.append(':').append(ref);
            if (canonical) {
                fields.add(ref);
            }
        } else if (insn instanceof MethodInsnNode value) {
            String owner = canonical ? identity.className(value.owner) : value.owner;
            String name = canonical ? identity.methodName(value.owner, value.name, value.desc) : value.name;
            String desc = canonical ? identity.methodDescriptor(value.desc) : value.desc;
            String ref = owner + "." + name + desc;
            out.append(':').append(ref).append(':').append(value.itf ? 'I' : 'C');
            if (canonical) {
                invokes.add(ref);
            }
        } else if (insn instanceof InvokeDynamicInsnNode value) {
            String desc = canonical ? identity.methodDescriptor(value.desc) : value.desc;
            out.append(':').append(value.name).append(desc).append(':')
                    .append(renderHandle(identity, value.bsm, canonical));
            for (Object arg : value.bsmArgs) {
                out.append(':').append(renderConstant(identity, arg, canonical));
            }
        } else if (insn instanceof JumpInsnNode value) {
            out.append(':').append(labelIndex(labels, value.label));
        } else if (insn instanceof LdcInsnNode value) {
            String rendered = renderConstant(identity, value.cst, canonical);
            out.append(':').append(rendered);
            if (canonical) {
                constants.add(rendered);
            }
        } else if (insn instanceof IincInsnNode value) {
            out.append(':').append(value.var).append(':').append(value.incr);
        } else if (insn instanceof TableSwitchInsnNode value) {
            out.append(':').append(value.min).append(':').append(value.max).append(':')
                    .append(labelIndex(labels, value.dflt));
            for (LabelNode label : value.labels) {
                out.append(':').append(labelIndex(labels, label));
            }
        } else if (insn instanceof LookupSwitchInsnNode value) {
            out.append(':').append(labelIndex(labels, value.dflt));
            for (int i = 0; i < value.keys.size(); i++) {
                out.append(':').append(value.keys.get(i)).append('>')
                        .append(labelIndex(labels, value.labels.get(i)));
            }
        } else if (insn instanceof MultiANewArrayInsnNode value) {
            out.append(':').append(canonical ? identity.descriptor(value.desc) : value.desc)
                    .append(':').append(value.dims);
        }
        return out.toString();
    }

    static int labelIndex(Map<LabelNode, Integer> labels, LabelNode label) {
        return labels.getOrDefault(label, -1);
    }

    static String renderHandle(Identity identity, Handle handle, boolean canonical) {
        String owner = canonical ? identity.className(handle.getOwner()) : handle.getOwner();
        String desc = handle.getDesc();
        boolean method = desc.startsWith("(");
        String name = handle.getName();
        if (canonical) {
            name = method
                    ? identity.methodName(handle.getOwner(), name, desc)
                    : identity.fieldName(handle.getOwner(), name, desc);
            desc = method ? identity.methodDescriptor(desc) : identity.descriptor(desc);
        }
        return handle.getTag() + ":" + owner + "." + name + desc + ":" + handle.isInterface();
    }

    static String renderConstant(Identity identity, Object value, boolean canonical) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String text) {
            return "str:" + escape(text);
        }
        if (value instanceof Type type) {
            return "type:" + (canonical ? identity.canonicalType(type) : type.getDescriptor());
        }
        if (value instanceof Handle handle) {
            return "handle:" + renderHandle(identity, handle, canonical);
        }
        if (value instanceof ConstantDynamic dynamic) {
            String desc = canonical ? identity.descriptor(dynamic.getDescriptor()) : dynamic.getDescriptor();
            StringBuilder out = new StringBuilder("condy:").append(dynamic.getName()).append(':').append(desc)
                    .append(':').append(renderHandle(identity, dynamic.getBootstrapMethod(), canonical));
            for (int i = 0; i < dynamic.getBootstrapMethodArgumentCount(); i++) {
                out.append(':').append(renderConstant(identity, dynamic.getBootstrapMethodArgument(i), canonical));
            }
            return out.toString();
        }
        return value.getClass().getSimpleName() + ':' + value;
    }

    static String sha256(String value) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder out = new StringBuilder();
            for (byte b : digest) {
                out.append(String.format("%02x", b));
            }
            return out.toString();
        } catch (NoSuchAlgorithmException impossible) {
            throw new IllegalStateException(impossible);
        }
    }

    static void index(Path jar, Path out) throws IOException {
        Map<String, ClassNode> classes = load(jar);
        Identity identity = new Identity(classes);
        ReferenceGraph graph = referenceGraph(identity);
        StringBuilder data = new StringBuilder();
        data.append("# client240-evidence-v2\t1\n");
        data.append("# C current canonical super canonicalSuper access fields methods\n");
        data.append("# F owner name desc access canonicalOwner canonicalName canonicalDesc\n");
        data.append("# M owner name desc access identity insns branches switches tryCatches strictHash canonicalHash invokes fields constants\n");
        data.append("# R source target reasons count\n");
        for (ClassNode cls : classes.values()) {
            data.append("C\t").append(cell(cls.name)).append('\t').append(cell(identity.className(cls.name))).append('\t')
                    .append(cell(cls.superName)).append('\t')
                    .append(cell(cls.superName == null ? "-" : identity.className(cls.superName))).append('\t')
                    .append(cls.access).append('\t').append(cls.fields.size()).append('\t').append(cls.methods.size()).append('\n');
            for (FieldNode field : cls.fields) {
                data.append("F\t").append(cell(cls.name)).append('\t').append(cell(field.name)).append('\t')
                        .append(cell(field.desc)).append('\t').append(field.access).append('\t')
                        .append(cell(identity.className(cls.name))).append('\t')
                        .append(cell(identity.fieldName(cls.name, field.name, field.desc))).append('\t')
                        .append(cell(identity.descriptor(field.desc))).append('\n');
            }
            for (MethodNode method : cls.methods) {
                Metrics m = metrics(identity, cls, method);
                data.append("M\t").append(cell(cls.name)).append('\t').append(cell(method.name)).append('\t')
                        .append(cell(method.desc)).append('\t').append(method.access).append('\t')
                        .append(cell(identity.methodIdentity(cls, method))).append('\t')
                        .append(m.instructions).append('\t').append(m.branches).append('\t').append(m.switches).append('\t')
                        .append(m.tryCatches).append('\t').append(m.strictHash).append('\t').append(m.canonicalHash).append('\t')
                        .append(cell(join(m.invokes))).append('\t').append(cell(join(m.fields))).append('\t')
                        .append(cell(join(m.constants))).append('\n');
            }
        }
        for (var source : graph.edges.entrySet()) {
            for (var target : source.getValue().entrySet()) {
                data.append("R\t").append(cell(source.getKey())).append('\t').append(cell(target.getKey())).append('\t')
                        .append(cell(join(target.getValue().keySet()))).append('\t')
                        .append(target.getValue().values().stream().mapToInt(Integer::intValue).sum()).append('\n');
            }
        }
        Files.createDirectories(out.toAbsolutePath().getParent());
        Files.writeString(out, data.toString(), StandardCharsets.UTF_8);
        System.out.printf("index: %d classes, %d reference edges -> %s%n", classes.size(), graph.edgeCount(), out);
    }

    static final class ReferenceGraph {
        final Map<String, Map<String, Map<String, Integer>>> edges = new TreeMap<>();

        void add(String source, String target, String reason) {
            if (source == null || target == null || source.equals(target)) {
                return;
            }
            edges.computeIfAbsent(source, unused -> new TreeMap<>())
                    .computeIfAbsent(target, unused -> new TreeMap<>())
                    .merge(reason, 1, Integer::sum);
        }

        int edgeCount() {
            return edges.values().stream().mapToInt(Map::size).sum();
        }
    }

    static ReferenceGraph referenceGraph(Identity identity) {
        ReferenceGraph graph = new ReferenceGraph();
        for (ClassNode cls : identity.classes.values()) {
            addClass(graph, cls.name, cls.superName, "super");
            for (String iface : cls.interfaces) {
                addClass(graph, cls.name, iface, "interface");
            }
            for (FieldNode field : cls.fields) {
                addDescriptor(graph, cls.name, Type.getType(field.desc), "field-desc");
            }
            for (MethodNode method : cls.methods) {
                Type methodType = Type.getMethodType(method.desc);
                for (Type arg : methodType.getArgumentTypes()) {
                    addDescriptor(graph, cls.name, arg, "method-desc");
                }
                addDescriptor(graph, cls.name, methodType.getReturnType(), "method-desc");
                for (AbstractInsnNode insn : method.instructions) {
                    if (insn instanceof TypeInsnNode value) {
                        addClass(graph, cls.name, value.desc, "type-insn");
                    } else if (insn instanceof FieldInsnNode value) {
                        addClass(graph, cls.name, value.owner, "field-owner");
                        addDescriptor(graph, cls.name, Type.getType(value.desc), "field-ref-desc");
                    } else if (insn instanceof MethodInsnNode value) {
                        addClass(graph, cls.name, value.owner, "invoke-owner");
                        Type mt = Type.getMethodType(value.desc);
                        for (Type arg : mt.getArgumentTypes()) {
                            addDescriptor(graph, cls.name, arg, "invoke-desc");
                        }
                        addDescriptor(graph, cls.name, mt.getReturnType(), "invoke-desc");
                    } else if (insn instanceof LdcInsnNode value) {
                        addConstantRefs(graph, cls.name, value.cst, "ldc");
                    } else if (insn instanceof InvokeDynamicInsnNode value) {
                        Type mt = Type.getMethodType(value.desc);
                        for (Type arg : mt.getArgumentTypes()) {
                            addDescriptor(graph, cls.name, arg, "indy-desc");
                        }
                        addDescriptor(graph, cls.name, mt.getReturnType(), "indy-desc");
                        addHandleRefs(graph, cls.name, value.bsm, "indy-bootstrap");
                        for (Object arg : value.bsmArgs) {
                            addConstantRefs(graph, cls.name, arg, "indy-arg");
                        }
                    } else if (insn instanceof MultiANewArrayInsnNode value) {
                        addDescriptor(graph, cls.name, Type.getType(value.desc), "multi-array");
                    }
                }
                for (TryCatchBlockNode block : method.tryCatchBlocks) {
                    addClass(graph, cls.name, block.type, "catch-type");
                }
            }
        }
        return graph;
    }

    static void addHandleRefs(ReferenceGraph graph, String source, Handle handle, String reason) {
        addClass(graph, source, handle.getOwner(), reason + "-owner");
        String desc = handle.getDesc();
        if (desc.startsWith("(")) {
            Type mt = Type.getMethodType(desc);
            for (Type arg : mt.getArgumentTypes()) {
                addDescriptor(graph, source, arg, reason + "-desc");
            }
            addDescriptor(graph, source, mt.getReturnType(), reason + "-desc");
        } else {
            addDescriptor(graph, source, Type.getType(desc), reason + "-desc");
        }
    }

    static void addConstantRefs(ReferenceGraph graph, String source, Object value, String reason) {
        if (value instanceof Type type) {
            if (type.getSort() == Type.METHOD) {
                for (Type arg : type.getArgumentTypes()) {
                    addDescriptor(graph, source, arg, reason + "-type");
                }
                addDescriptor(graph, source, type.getReturnType(), reason + "-type");
            } else {
                addDescriptor(graph, source, type, reason + "-type");
            }
        } else if (value instanceof Handle handle) {
            addHandleRefs(graph, source, handle, reason + "-handle");
        } else if (value instanceof ConstantDynamic dynamic) {
            addDescriptor(graph, source, Type.getType(dynamic.getDescriptor()), reason + "-condy-desc");
            addHandleRefs(graph, source, dynamic.getBootstrapMethod(), reason + "-condy-bootstrap");
            for (int i = 0; i < dynamic.getBootstrapMethodArgumentCount(); i++) {
                addConstantRefs(graph, source, dynamic.getBootstrapMethodArgument(i), reason + "-condy-arg");
            }
        }
    }

    static void addDescriptor(ReferenceGraph graph, String source, Type type, String reason) {
        if (type.getSort() == Type.ARRAY) {
            addDescriptor(graph, source, type.getElementType(), reason);
        } else if (type.getSort() == Type.OBJECT) {
            addClass(graph, source, type.getInternalName(), reason);
        }
    }

    static void addClass(ReferenceGraph graph, String source, String target, String reason) {
        if (target != null) {
            graph.add(source, target, reason);
        }
    }

    static void refs(Path jar, List<String> queries) throws IOException {
        Map<String, ClassNode> classes = load(jar);
        Identity identity = new Identity(classes);
        ReferenceGraph graph = referenceGraph(identity);
        Map<String, String> canonicalToCurrent = new HashMap<>();
        for (String current : classes.keySet()) {
            canonicalToCurrent.putIfAbsent(identity.className(current), current);
        }
        for (String query : queries) {
            String current = classes.containsKey(query) ? query : canonicalToCurrent.get(query);
            if (current == null) {
                System.out.println("UNKNOWN\t" + cell(query));
                continue;
            }
            Map<String, Map<String, Integer>> outgoing = graph.edges.getOrDefault(current, Map.of());
            for (var target : outgoing.entrySet()) {
                System.out.println("OUT\t" + cell(query) + "\t" + cell(target.getKey()) + "\t"
                        + cell(identity.className(target.getKey())) + "\t" + cell(join(target.getValue().keySet())) + "\t"
                        + target.getValue().values().stream().mapToInt(Integer::intValue).sum());
            }
            for (var source : graph.edges.entrySet()) {
                Map<String, Integer> reasons = source.getValue().get(current);
                if (reasons != null) {
                    System.out.println("IN\t" + cell(query) + "\t" + cell(source.getKey()) + "\t"
                            + cell(identity.className(source.getKey())) + "\t" + cell(join(reasons.keySet())) + "\t"
                            + reasons.values().stream().mapToInt(Integer::intValue).sum());
                }
            }
        }
    }

    record MethodEvidence(String currentOwner, String currentName, String desc, String identity, Metrics metrics) {}

    static Map<String, MethodEvidence> methods(Identity identity) {
        Map<String, MethodEvidence> out = new TreeMap<>();
        for (ClassNode cls : identity.classes.values()) {
            for (MethodNode method : cls.methods) {
                String key = identity.methodIdentity(cls, method);
                MethodEvidence evidence = new MethodEvidence(cls.name, method.name, method.desc, key, metrics(identity, cls, method));
                MethodEvidence previous = out.putIfAbsent(key, evidence);
                if (previous != null) {
                    String fallback = key + "#" + identity.methodDescriptor(method.desc) + "#" + method.name;
                    out.put(fallback, evidence);
                }
            }
        }
        return out;
    }

    static void audit(Path originalJar, Path semanticJar, Path out) throws IOException {
        Identity original = new Identity(load(originalJar));
        Identity semantic = new Identity(load(semanticJar));
        Map<String, MethodEvidence> a = methods(original);
        Map<String, MethodEvidence> b = methods(semantic);
        Set<String> keys = new TreeSet<>();
        keys.addAll(a.keySet());
        keys.addAll(b.keySet());
        StringBuilder data = new StringBuilder();
        data.append("# client240-original-semantic-audit-v2\t1\n");
        data.append("identity\toriginal\tsemantic\tflags\torigInsn\tsemInsn\torigBranches\tsemBranches\torigSwitches\tsemSwitches\torigTryCatch\tsemTryCatch\torigHash\tsemHash\n");
        int changed = 0;
        int missing = 0;
        for (String key : keys) {
            MethodEvidence left = a.get(key);
            MethodEvidence right = b.get(key);
            Set<String> flags = new TreeSet<>();
            if (left == null) {
                flags.add("missing-original");
                missing++;
            } else if (right == null) {
                flags.add("missing-semantic");
                missing++;
            } else {
                Metrics lm = left.metrics;
                Metrics rm = right.metrics;
                if (lm.instructions != rm.instructions) flags.add("instruction-count-changed");
                if (lm.branches != rm.branches) flags.add("branch-shape-changed");
                if (lm.switches != rm.switches) flags.add("switch-shape-changed");
                if (lm.tryCatches != rm.tryCatches) flags.add("try-catch-changed");
                if (!lm.invokes.equals(rm.invokes)) flags.add("invoke-set-changed");
                if (!lm.fields.equals(rm.fields)) flags.add("field-set-changed");
                if (!lm.constants.equals(rm.constants)) flags.add("constant-set-changed");
                if (!lm.canonicalHash.equals(rm.canonicalHash)) flags.add("canonical-bytecode-changed");
            }
            if (flags.isEmpty()) {
                continue;
            }
            changed++;
            data.append(cell(key)).append('\t')
                    .append(cell(label(left))).append('\t').append(cell(label(right))).append('\t')
                    .append(cell(join(flags))).append('\t')
                    .append(metric(left, "instructions")).append('\t').append(metric(right, "instructions")).append('\t')
                    .append(metric(left, "branches")).append('\t').append(metric(right, "branches")).append('\t')
                    .append(metric(left, "switches")).append('\t').append(metric(right, "switches")).append('\t')
                    .append(metric(left, "tryCatches")).append('\t').append(metric(right, "tryCatches")).append('\t')
                    .append(left == null ? "-" : left.metrics.canonicalHash).append('\t')
                    .append(right == null ? "-" : right.metrics.canonicalHash).append('\n');
        }
        Files.createDirectories(out.toAbsolutePath().getParent());
        Files.writeString(out, data.toString(), StandardCharsets.UTF_8);
        System.out.printf("audit: %d changed/missing method identities (%d missing side) -> %s%n", changed, missing, out);
    }

    static String label(MethodEvidence evidence) {
        return evidence == null ? "-" : evidence.currentOwner + "." + evidence.currentName + evidence.desc;
    }

    static String metric(MethodEvidence evidence, String name) {
        if (evidence == null) {
            return "-";
        }
        return switch (name) {
            case "instructions" -> Integer.toString(evidence.metrics.instructions);
            case "branches" -> Integer.toString(evidence.metrics.branches);
            case "switches" -> Integer.toString(evidence.metrics.switches);
            case "tryCatches" -> Integer.toString(evidence.metrics.tryCatches);
            default -> throw new IllegalArgumentException(name);
        };
    }

    static String join(Collection<String> values) {
        return String.join("|", values);
    }

    static String cell(String value) {
        return value == null ? "-" : escape(value);
    }

    static String escape(String value) {
        return value.replace("\\", "\\\\")
                .replace("\t", "\\t")
                .replace("\r", "\\r")
                .replace("\n", "\\n")
                .replace("|", "\\p");
    }
}