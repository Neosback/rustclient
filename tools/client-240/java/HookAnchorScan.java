import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

/**
 * Lists the methods of the original injected client that call RuneLite hooks (analysis evidence only).
 *
 * Usage: java --source 17 -cp ASM... HookAnchorScan.java ORIGINAL_JAR OUT.tsv
 *
 * The injector adds {@code Callbacks.post(new events.X(...))}, {@code DrawCallbacks.*} and similar calls to the
 * gamepack. Each such call site proves the enclosing method performs that event's semantic action (a script
 * firing, a menu click, a tick). Rows carry the original obfuscated symbol; {@code hook-anchors.py} joins them to
 * stable symbols. Names are never invented here.
 */
public final class HookAnchorScan {
    static final String HOOKS = "net/runelite/api/hooks/";
    static final String EVENTS = "net/runelite/api/events/";

    public static void main(String[] args) throws IOException {
        List<String> rows = new ArrayList<>();
        try (JarFile jar = new JarFile(Path.of(args[0]).toFile())) {
            for (JarEntry entry : Collections.list(jar.entries())) {
                if (!entry.getName().endsWith(".class")) {
                    continue;
                }
                ClassNode cls = new ClassNode();
                new ClassReader(jar.getInputStream(entry).readAllBytes()).accept(cls, ClassReader.SKIP_FRAMES);
                for (MethodNode method : cls.methods) {
                    scan(cls, method, rows);
                }
            }
        }
        Collections.sort(rows);
        try (PrintWriter out = new PrintWriter(Path.of(args[1]).toFile(), StandardCharsets.UTF_8)) {
            out.println("class\tmethod\tdesc\tkind\ttarget\tcount\tsize");
            rows.forEach(out::println);
        }
        System.out.println("anchor rows: " + rows.size());
    }

    /** Real instruction count, ignoring labels and line numbers. */
    static int realSize(MethodNode method) {
        int size = 0;
        for (AbstractInsnNode insn : method.instructions) {
            if (insn.getOpcode() >= 0) {
                size++;
            }
        }
        return size;
    }

    static void scan(ClassNode cls, MethodNode method, List<String> rows) {
        Map<String, Integer> counts = new TreeMap<>();
        for (AbstractInsnNode insn : method.instructions) {
            if (insn instanceof TypeInsnNode type && type.getOpcode() == org.objectweb.asm.Opcodes.NEW
                    && type.desc.startsWith(EVENTS)) {
                counts.merge("event\t" + type.desc.substring(EVENTS.length()), 1, Integer::sum);
            } else if (insn instanceof MethodInsnNode call && call.owner.startsWith(HOOKS)) {
                counts.merge("hook\t" + call.owner.substring(HOOKS.length()) + "." + call.name, 1, Integer::sum);
            }
        }
        counts.forEach((key, count) ->
                rows.add(cls.name + "\t" + method.name + "\t" + method.desc + "\t" + key + "\t" + count + "\t" + realSize(method)));
    }
}
