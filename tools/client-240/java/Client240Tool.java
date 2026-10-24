import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceClassVisitor;

/**
 * Research tooling for docs/reference/client-240. Read-only over jars; writes only the paths it is
 * given. Run with: java -cp asm*.jar Client240Tool.java COMMAND ARGS.
 *
 * <pre>
 *   seed-api JAR OUT.tiny OUT.tsv      T1 names from RuneLite API getters returning one field
 *   validate JAR SHARD.tiny...         structural and naming checks of mapping shards
 *   compare JAR_A JAR_B                per-class normalized bytecode equality (rename-only proof)
 *   annotations JAR                    ordered obfuscated-name/signature evidence, one line each
 *   dump-classes JAR OUT.tsv           structure dump (classes, members, string constants) for matching
 * </pre>
 */
public final class Client240Tool {
    static final String OBF_NAME = "Lnet/runelite/mapping/ObfuscatedName;";
    static final String OBF_SIG = "Lnet/runelite/mapping/ObfuscatedSignature;";
    static final Pattern GENERIC_FIELD = Pattern.compile("field\\d+");
    static final Pattern GETTER = Pattern.compile("(get|is)([A-Z]\\w*)");
    static final Pattern IDENT = Pattern.compile("[A-Za-z_][A-Za-z0-9_]*");
    static final Pattern STABLE = Pattern.compile("(class[A-Z0-9]+|field\\d+|method\\d+)");
    static final Set<String> KEYWORDS = Set.of(
            "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else", "enum", "extends", "final", "finally", "float",
            "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp",
            "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void",
            "volatile", "while", "true", "false", "null", "var", "record", "yield", "_");

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            throw new IllegalArgumentException("command required");
        }
        switch (args[0]) {
            case "seed-api" -> seedApi(Path.of(args[1]), Path.of(args[2]), Path.of(args[3]));
            case "validate" -> validate(Path.of(args[1]), args);
            case "compare" -> compare(Path.of(args[1]), Path.of(args[2]));
            case "annotations" -> annotations(Path.of(args[1]));
            case "dump-classes" -> dumpClasses(Path.of(args[1]), Path.of(args[2]));
            default -> throw new IllegalArgumentException("unknown command " + args[0]);
        }
    }

    static Map<String, ClassNode> load(Path jar) throws IOException {
        Map<String, ClassNode> classes = new TreeMap<>();
        try (JarFile file = new JarFile(jar.toFile())) {
            for (JarEntry entry : java.util.Collections.list(file.entries())) {
                if (!entry.getName().endsWith(".class")) {
                    continue;
                }
                ClassNode node = new ClassNode();
                new ClassReader(file.getInputStream(entry).readAllBytes()).accept(node, 0);
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
        for (AnnotationNode a : all) {
            if (a.desc.equals(desc) && a.values != null) {
                for (int i = 0; i + 1 < a.values.size(); i += 2) {
                    if (a.values.get(i).equals("value") || a.values.get(i).equals("descriptor")) {
                        return String.valueOf(a.values.get(i + 1));
                    }
                }
            }
        }
        return null;
    }

    // ---------------------------------------------------------------- seed-api

    /** Instruction sequence ignoring labels, line numbers and frames. */
    static List<AbstractInsnNode> real(MethodNode method) {
        List<AbstractInsnNode> out = new ArrayList<>();
        for (AbstractInsnNode insn : method.instructions) {
            if (insn.getOpcode() >= 0) {
                out.add(insn);
            }
        }
        return out;
    }

    /** The single field a body such as {@code return this.f * K;} reads, or null. */
    static FieldInsnNode returnedField(MethodNode method) {
        List<AbstractInsnNode> code = real(method);
        int i = 0;
        boolean instance = (method.access & Opcodes.ACC_STATIC) == 0;
        FieldInsnNode field = null;
        // Optional leading constant (K * this.f).
        boolean leadingConstant = false;
        if (i < code.size() && (code.get(i) instanceof LdcInsnNode || code.get(i) instanceof IntInsnNode)) {
            leadingConstant = true;
            i++;
        }
        if (instance) {
            if (i + 1 >= code.size() || !(code.get(i) instanceof VarInsnNode load)
                    || load.getOpcode() != Opcodes.ALOAD || load.var != 0
                    || !(code.get(i + 1) instanceof FieldInsnNode f) || f.getOpcode() != Opcodes.GETFIELD) {
                return null;
            }
            field = (FieldInsnNode) code.get(i + 1);
            i += 2;
        } else {
            if (i >= code.size() || !(code.get(i) instanceof FieldInsnNode f) || f.getOpcode() != Opcodes.GETSTATIC) {
                return null;
            }
            field = (FieldInsnNode) code.get(i);
            i++;
        }
        boolean trailingConstant = false;
        if (!leadingConstant && i < code.size() && (code.get(i) instanceof LdcInsnNode || code.get(i) instanceof IntInsnNode)) {
            trailingConstant = true;
            i++;
        }
        int op = i < code.size() ? code.get(i).getOpcode() : -1;
        if (leadingConstant || trailingConstant) {
            if (op != Opcodes.IMUL && op != Opcodes.LMUL) {
                return null;
            }
            i++;
        }
        if (i != code.size() - 1) {
            return null;
        }
        int ret = code.get(i).getOpcode();
        if (ret < Opcodes.IRETURN || ret > Opcodes.ARETURN) {
            return null;
        }
        return field;
    }

    static String readableFromGetter(String methodName) {
        var m = GETTER.matcher(methodName);
        if (!m.matches()) {
            return null;
        }
        String tail = m.group(2);
        String name = tail.length() > 1 && Character.isLowerCase(tail.charAt(1))
                ? Character.toLowerCase(tail.charAt(0)) + tail.substring(1)
                : tail.length() == 1 ? tail.toLowerCase() : tail;
        return IDENT.matcher(name).matches() && !KEYWORDS.contains(name) ? name : null;
    }

    static void seedApi(Path jar, Path tinyOut, Path tsvOut) throws IOException {
        Map<String, ClassNode> classes = load(jar);
        // field key -> proposed name -> evidence getters
        Map<String, Map<String, List<String>>> proposals = new TreeMap<>();
        for (ClassNode cls : classes.values()) {
            for (MethodNode method : cls.methods) {
                String name = readableFromGetter(method.name);
                if (name == null || !method.desc.startsWith("()") || (method.access & Opcodes.ACC_ABSTRACT) != 0) {
                    continue;
                }
                FieldInsnNode field = returnedField(method);
                if (field == null || !classes.containsKey(field.owner)) {
                    continue;
                }
                ClassNode owner = declaring(classes, field.owner, field.name, field.desc);
                if (owner == null || !GENERIC_FIELD.matcher(field.name).matches()) {
                    continue;
                }
                proposals.computeIfAbsent(owner.name + "." + field.name + ":" + field.desc, k -> new TreeMap<>())
                        .computeIfAbsent(name, k -> new ArrayList<>())
                        .add(cls.name + "." + method.name + "()");
            }
        }
        Map<String, Map<String, String>> accepted = new TreeMap<>();
        Map<String, String[]> rows = new TreeMap<>();
        int conflicts = 0;
        int collisions = 0;
        for (var entry : proposals.entrySet()) {
            if (entry.getValue().size() != 1) {
                conflicts++;
                continue;
            }
            String key = entry.getKey();
            String owner = key.substring(0, key.indexOf('.'));
            String fieldName = key.substring(key.indexOf('.') + 1, key.indexOf(':'));
            String desc = key.substring(key.indexOf(':') + 1);
            String readable = entry.getValue().keySet().iterator().next();
            if (collides(classes, owner, readable, accepted)) {
                collisions++;
                continue;
            }
            accepted.computeIfAbsent(owner, k -> new TreeMap<>()).put(fieldName + "\t" + desc, readable);
            ClassNode node = declaring(classes, owner, fieldName, desc);
            FieldNode fn = node.fields.stream().filter(f -> f.name.equals(fieldName) && f.desc.equals(desc)).findFirst().get();
            String obf = annotation(fn.visibleAnnotations, fn.invisibleAnnotations, OBF_NAME);
            String evidence = String.join(",", entry.getValue().values().iterator().next());
            rows.put(owner + "." + fieldName, new String[] {owner + "." + fieldName, owner, obf == null ? "-" : obf,
                    readable, "field", "T1", "A", "getter:" + evidence, "-", "00-seed-api"});
        }
        StringBuilder tiny = new StringBuilder("tiny\t2\t0\tsemantic\treadable\n");
        for (var cls : accepted.entrySet()) {
            tiny.append("c\t").append(cls.getKey()).append('\t').append(cls.getKey()).append('\n');
            for (var field : cls.getValue().entrySet()) {
                String[] parts = field.getKey().split("\t");
                tiny.append("\tf\t").append(parts[1]).append('\t').append(parts[0]).append('\t')
                        .append(field.getValue()).append('\n');
            }
        }
        Files.writeString(tinyOut, tiny.toString(), StandardCharsets.UTF_8);
        StringBuilder tsv = new StringBuilder();
        for (String[] row : rows.values()) {
            tsv.append(String.join("\t", row)).append('\n');
        }
        Files.writeString(tsvOut, tsv.toString(), StandardCharsets.UTF_8);
        System.out.printf("seed-api: %d fields named, %d conflicting getters skipped, %d name collisions skipped%n",
                rows.size(), conflicts, collisions);
    }

    static ClassNode declaring(Map<String, ClassNode> classes, String owner, String name, String desc) {
        for (ClassNode c = classes.get(owner); c != null; c = c.superName == null ? null : classes.get(c.superName)) {
            for (FieldNode f : c.fields) {
                if (f.name.equals(name) && f.desc.equals(desc)) {
                    return c;
                }
            }
        }
        return null;
    }

    /** A proposed field name must be unused across the class's whole hierarchy. */
    static boolean collides(Map<String, ClassNode> classes, String owner, String readable,
            Map<String, Map<String, String>> accepted) {
        Set<String> related = new HashSet<>();
        for (ClassNode c = classes.get(owner); c != null; c = c.superName == null ? null : classes.get(c.superName)) {
            related.add(c.name);
        }
        for (ClassNode c : classes.values()) {
            for (ClassNode s = c; s != null; s = s.superName == null ? null : classes.get(s.superName)) {
                if (s.name.equals(owner)) {
                    related.add(c.name);
                }
            }
        }
        for (String name : related) {
            ClassNode c = classes.get(name);
            for (FieldNode f : c.fields) {
                if (f.name.equals(readable)) {
                    return true;
                }
            }
            Map<String, String> renames = accepted.get(name);
            if (renames != null && renames.containsValue(readable)) {
                return true;
            }
        }
        return false;
    }

    // ---------------------------------------------------------------- validate

    record Entry(String shard, int line, String kind, String owner, String desc, String from, String to) {
        String key() {
            return kind + " " + owner + "." + from + (desc == null ? "" : " " + desc);
        }
    }

    static void validate(Path jar, String[] args) throws IOException {
        Map<String, ClassNode> classes = load(jar);
        List<String> errors = new ArrayList<>();
        List<Entry> entries = new ArrayList<>();
        Map<String, String> classOwner = new HashMap<>();
        for (int a = 2; a < args.length; a++) {
            Path shard = Path.of(args[a]);
            List<String> lines = Files.readAllLines(shard, StandardCharsets.UTF_8);
            if (lines.isEmpty() || !lines.get(0).equals("tiny\t2\t0\tsemantic\treadable")) {
                errors.add(shard + ": header must be 'tiny<TAB>2<TAB>0<TAB>semantic<TAB>readable'");
                continue;
            }
            String current = null;
            for (int n = 1; n < lines.size(); n++) {
                String line = lines.get(n);
                if (line.isBlank() || line.startsWith("#")) {
                    continue;
                }
                String[] p = line.split("\t", -1);
                String where = shard.getFileName() + ":" + (n + 1);
                if (p[0].equals("c") && p.length == 3) {
                    current = p[1];
                    // The deterministic seed shard (00-*) shares classes with the cluster shards.
                    String previous = shard.getFileName().toString().startsWith("00-")
                            ? null
                            : classOwner.putIfAbsent(current, shard.getFileName().toString());
                    if (previous != null && !previous.equals(shard.getFileName().toString())) {
                        errors.add(where + ": class " + current + " already owned by shard " + previous);
                    }
                    entries.add(new Entry(shard.toString(), n + 1, "c", current, null, p[1], p[2]));
                } else if (p.length == 5 && p[0].isEmpty() && (p[1].equals("f") || p[1].equals("m")) && current != null) {
                    entries.add(new Entry(shard.toString(), n + 1, p[1], current, p[2], p[3], p[4]));
                } else {
                    errors.add(where + ": unsupported tiny line (only class, field and method entries are allowed)");
                }
            }
        }
        Set<String> seenKeys = new HashSet<>();
        Map<String, Set<String>> newMembers = new HashMap<>();
        Map<String, String> newClassNames = new HashMap<>();
        for (Entry e : entries) {
            String where = Path.of(e.shard).getFileName() + ":" + e.line;
            if (!seenKeys.add(e.key())) {
                errors.add(where + ": duplicate entry " + e.key());
            }
            ClassNode cls = classes.get(e.owner);
            if (cls == null) {
                errors.add(where + ": stale class " + e.owner);
                continue;
            }
            if (!e.kind.equals("c") && !STABLE.matcher(e.from).matches() && !e.from.matches("[A-Za-z_]\\w*")) {
                errors.add(where + ": bad source member name " + e.from);
            }
            if (!IDENT.matcher(e.to).matches() || KEYWORDS.contains(e.to)) {
                errors.add(where + ": target is not a legal Java identifier: " + e.to);
            }
            if (e.kind.equals("c")) {
                if (e.to.equals(e.from)) {
                    continue; // ownership marker only
                }
                if (newClassNames.put(e.to, e.owner) != null || classes.containsKey(e.to)) {
                    errors.add(where + ": class target collides: " + e.to);
                }
            } else if (e.kind.equals("f")) {
                boolean exists = cls.fields.stream().anyMatch(f -> f.name.equals(e.from) && f.desc.equals(e.desc));
                if (!exists) {
                    errors.add(where + ": stale field " + e.owner + "." + e.from + " " + e.desc);
                }
                if (!newMembers.computeIfAbsent(e.owner + " f", k -> new HashSet<>()).add(e.to)
                        || (!e.to.equals(e.from) && cls.fields.stream().anyMatch(f -> f.name.equals(e.to)))) {
                    errors.add(where + ": field target collides: " + e.owner + "." + e.to);
                }
            } else {
                boolean exists = cls.methods.stream().anyMatch(m -> m.name.equals(e.from) && m.desc.equals(e.desc));
                if (!exists) {
                    errors.add(where + ": stale method " + e.owner + "." + e.from + e.desc);
                }
                if (!newMembers.computeIfAbsent(e.owner + " m " + e.desc, k -> new HashSet<>()).add(e.to)
                        || (!e.to.equals(e.from) && cls.methods.stream().anyMatch(m -> m.name.equals(e.to) && m.desc.equals(e.desc)))) {
                    errors.add(where + ": method target collides: " + e.owner + "." + e.to + e.desc);
                }
            }
        }
        if (errors.isEmpty()) {
            System.out.printf("validate: OK, %d entries in %d shard(s) against %d classes%n",
                    entries.size(), args.length - 2, classes.size());
        } else {
            errors.forEach(System.out::println);
            System.out.printf("validate: FAILED, %d problem(s)%n", errors.size());
            System.exit(1);
        }
    }

    // ----------------------------------------------------------------- compare

    static String textify(ClassNode node) {
        StringWriter out = new StringWriter();
        node.accept(new TraceClassVisitor(null, new Textifier(), new PrintWriter(out)));
        return out.toString();
    }

    static void compare(Path baseline, Path candidate) throws IOException {
        Map<String, ClassNode> a = load(baseline);
        Map<String, ClassNode> b = load(candidate);
        int differences = 0;
        if (!a.keySet().equals(b.keySet())) {
            Set<String> onlyA = new TreeSet<>(a.keySet());
            onlyA.removeAll(b.keySet());
            Set<String> onlyB = new TreeSet<>(b.keySet());
            onlyB.removeAll(a.keySet());
            System.out.println("class sets differ: only baseline " + onlyA + ", only candidate " + onlyB);
            differences++;
        }
        for (String name : a.keySet()) {
            ClassNode other = b.get(name);
            if (other != null && !textify(a.get(name)).equals(textify(other))) {
                System.out.println("bytecode differs: " + name);
                differences++;
            }
        }
        if (differences == 0) {
            System.out.printf("compare: IDENTICAL, %d classes have equal normalized bytecode%n", a.size());
        } else {
            System.out.printf("compare: FAILED, %d difference(s)%n", differences);
            System.exit(1);
        }
    }

    // ------------------------------------------------------------- annotations

    static void annotations(Path jar) throws IOException {
        Map<String, ClassNode> classes = load(jar);
        // Keyed by the class's own ObfuscatedName so a renamed class still lines up.
        Map<String, List<String>> perClass = new TreeMap<>();
        for (ClassNode cls : classes.values()) {
            String obf = annotation(cls.visibleAnnotations, cls.invisibleAnnotations, OBF_NAME);
            String key = obf == null ? "<none:" + cls.name + ">" : obf;
            List<String> lines = new ArrayList<>();
            lines.add("class name=" + obf + " sig=" + annotation(cls.visibleAnnotations, cls.invisibleAnnotations, OBF_SIG));
            for (FieldNode f : cls.fields) {
                lines.add("field name=" + annotation(f.visibleAnnotations, f.invisibleAnnotations, OBF_NAME)
                        + " sig=" + annotation(f.visibleAnnotations, f.invisibleAnnotations, OBF_SIG));
            }
            for (MethodNode m : cls.methods) {
                lines.add("method name=" + annotation(m.visibleAnnotations, m.invisibleAnnotations, OBF_NAME)
                        + " sig=" + annotation(m.visibleAnnotations, m.invisibleAnnotations, OBF_SIG));
            }
            perClass.put(key, lines);
        }
        for (var entry : perClass.entrySet()) {
            for (String line : entry.getValue()) {
                System.out.println(entry.getKey() + " " + line);
            }
        }
    }

    // ------------------------------------------------------------ dump-classes

    /** Tab-separated structure records used by the 235 carry-forward matcher. */
    static void dumpClasses(Path jar, Path out) throws IOException {
        Map<String, ClassNode> classes = load(jar);
        StringBuilder sb = new StringBuilder();
        for (ClassNode cls : classes.values()) {
            String obf = annotation(cls.visibleAnnotations, cls.invisibleAnnotations, OBF_NAME);
            sb.append("C\t").append(cls.name).append('\t').append(cls.superName).append('\t')
                    .append(String.join(",", cls.interfaces)).append('\t').append(cls.access).append('\t')
                    .append(obf == null ? "-" : obf).append('\n');
            for (FieldNode f : cls.fields) {
                String fobf = annotation(f.visibleAnnotations, f.invisibleAnnotations, OBF_NAME);
                sb.append("F\t").append(cls.name).append('\t').append(f.name).append('\t').append(f.desc).append('\t')
                        .append(f.access).append('\t').append(fobf == null ? "-" : fobf).append('\n');
            }
            Set<String> strings = new TreeSet<>();
            for (MethodNode m : cls.methods) {
                String mobf = annotation(m.visibleAnnotations, m.invisibleAnnotations, OBF_NAME);
                int size = 0;
                int calls = 0;
                for (AbstractInsnNode insn : m.instructions) {
                    if (insn.getOpcode() >= 0) {
                        size++;
                    }
                    if (insn instanceof org.objectweb.asm.tree.MethodInsnNode) {
                        calls++;
                    }
                    if (insn instanceof LdcInsnNode ldc && ldc.cst instanceof String text && text.length() >= 4) {
                        strings.add(text.length() > 80 ? text.substring(0, 80) : text);
                    }
                }
                sb.append("M\t").append(cls.name).append('\t').append(m.name).append('\t').append(m.desc).append('\t')
                        .append(m.access).append('\t').append(mobf == null ? "-" : mobf).append('\t')
                        .append(size).append('\t').append(calls).append('\n');
            }
            for (String text : strings) {
                sb.append("S\t").append(cls.name).append('\t')
                        .append(text.replace("\\", "\\\\").replace("\t", "\\t").replace("\n", "\\n").replace("\r", "\\r")).append('\n');
            }
        }
        Files.writeString(out, sb.toString(), StandardCharsets.UTF_8);
        System.out.printf("dump-classes: %d classes written to %s%n", classes.size(), out);
    }
}
