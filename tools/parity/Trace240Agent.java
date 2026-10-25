import java.io.IOException;
import java.io.PrintWriter;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.ProtectionDomain;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

/**
 * Read-only script-interpreter probe for the checksum-pinned original 240 client (runtime evidence only).
 *
 * Agent argument: {@code out=FILE;script=CLASS;int=OWNER.FIELD;obj=OWNER.FIELD;long=OWNER.FIELD} using original
 * obfuscated names. Every static handler shaped {@code (I<script>Z[B|I|S|C])I} is bracketed: on entry the three
 * (multiplied) stack-size fields are recorded, on each return the result and the size deltas are aggregated per
 * (handler, opcode, result, deltas). A result of 2 means "not handled here", so only handlers that really executed
 * an opcode appear. The probe never changes behavior: it adds side-effect-free static calls before the body and
 * before each IRETURN. Raw deltas are in stored units; {@code opcode-trace.py} converts them with the field
 * multipliers. The opcode is captured on entry because handlers rewrite it (IF variants fold into the CC number
 * after popping the widget id). This resolves which handler ran and each opcode's real stack effect despite the flattened dispatch.
 */
public final class Trace240Agent {
    private static final int MAX_DEPTH = 256;
    private static final int[][] before = new int[MAX_DEPTH][4];
    private static int depth;
    private static final Map<String, long[]> counts = new TreeMap<>();
    private static Path out;
    private static boolean dirty;

    public static void premain(String argument, Instrumentation instrumentation) throws Exception {
        Map<String, String> options = new TreeMap<>();
        for (String part : argument.split(";")) {
            int split = part.indexOf('=');
            options.put(part.substring(0, split), part.substring(split + 1));
        }
        out = Path.of(options.get("out"));
        String script = options.get("script");
        String[] sizes = {options.get("int"), options.get("obj"), options.get("long")};
        Pattern shape = Pattern.compile("\\(IL" + Pattern.quote(script) + ";Z[BISC]?\\)I");
        Runtime.getRuntime().addShutdownHook(new Thread(Trace240Agent::flush));
        Thread flusher = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException stop) {
                    return;
                }
                flush();
            }
        }, "trace240-flush");
        flusher.setDaemon(true);
        flusher.start();
        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String name, Class<?> type, ProtectionDomain domain,
                                    byte[] bytes) {
                if (name == null || name.startsWith("java/") || name.startsWith("net/") || name.startsWith("org/")
                        || name.startsWith("com/") || name.startsWith("sun/") || name.startsWith("jdk/")) {
                    return null;
                }
                try {
                    return instrument(bytes, shape, sizes);
                } catch (RuntimeException failure) {
                    System.err.println("Trace240Agent: skipped " + name + ": " + failure);
                    return null;
                }
            }
        });
    }

    /** Returns the instrumented class bytes, or null when the class has no interpreter handler. */
    public static byte[] instrument(byte[] bytes, Pattern shape, String[] sizes) {
        ClassNode node = new ClassNode();
        new ClassReader(bytes).accept(node, 0);
        boolean changed = false;
        for (MethodNode method : node.methods) {
            if ((method.access & Opcodes.ACC_STATIC) == 0 || !shape.matcher(method.desc).matches()) {
                continue;
            }
            InsnList enter = new InsnList();
            enter.add(new VarInsnNode(Opcodes.ILOAD, 0));
            for (String size : sizes) {
                enter.add(sizeField(size));
            }
            enter.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Trace240Agent", "enter", "(IIII)V", false));
            for (var insn : method.instructions.toArray()) {
                if (insn.getOpcode() == Opcodes.IRETURN) {
                    InsnList exit = new InsnList();
                    exit.add(new InsnNode(Opcodes.DUP));
                    exit.add(new LdcInsnNode(node.name + "." + method.name));
                    for (String size : sizes) {
                        exit.add(sizeField(size));
                    }
                    exit.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Trace240Agent", "exit",
                            "(ILjava/lang/String;III)V", false));
                    method.instructions.insertBefore(insn, exit);
                }
            }
            method.instructions.insert(enter);
            changed = true;
        }
        if (!changed) {
            return null;
        }
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        node.accept(writer);
        return writer.toByteArray();
    }

    private static FieldInsnNode sizeField(String qualified) {
        int dot = qualified.lastIndexOf('.');
        return new FieldInsnNode(Opcodes.GETSTATIC, qualified.substring(0, dot), qualified.substring(dot + 1), "I");
    }

    public static synchronized void enter(int opcode, int intSize, int objSize, int longSize) {
        if (depth < MAX_DEPTH) {
            before[depth][3] = opcode;
            before[depth][0] = intSize;
            before[depth][1] = objSize;
            before[depth][2] = longSize;
        }
        depth++;
    }

    public static synchronized void exit(int result, String handler, int intSize, int objSize, int longSize) {
        if (depth > 0) {
            depth--;
        }
        if (result == 2 || depth >= MAX_DEPTH) {
            return;
        }
        String key = handler + "\t" + before[depth][3] + "\t" + result + "\t" + (intSize - before[depth][0]) + "\t"
                + (objSize - before[depth][1]) + "\t" + (longSize - before[depth][2]);
        counts.computeIfAbsent(key, ignored -> new long[1])[0]++;
        dirty = true;
    }

    static synchronized void flush() {
        if (!dirty || out == null) {
            return;
        }
        Path temporary = out.resolveSibling(out.getFileName() + ".tmp");
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(temporary, StandardCharsets.UTF_8))) {
            writer.println("handler\topcode\tresult\traw_int_delta\traw_obj_delta\traw_long_delta\tcount");
            counts.forEach((key, count) -> writer.println(key + "\t" + count[0]));
        } catch (IOException failure) {
            return;
        }
        try {
            Files.move(temporary, out, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            dirty = false;
        } catch (IOException ignored) {
            // Retry on the next flush.
        }
    }
}
