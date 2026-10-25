import java.io.IOException;
import java.io.PrintWriter;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

/**
 * Read-only packet probe for the checksum-pinned original 240 client (runtime evidence only).
 *
 * Agent argument is {@code key=value} pairs separated by {@code |} (descriptors contain {@code ;}), using original obfuscated names:
 * <ul>
 * <li>{@code out=FILE} TSV output, rewritten every 10 s.</li>
 * <li>{@code send=CLASS.METHOD:DESC} method entered with the outbound packet node as local variable
 * {@code sendVar} (default 1); its buffer is still unflushed at entry.</li>
 * <li>{@code recv=OWNER.FIELD:MIN} connection field holding the current server prot; in every method with at least
 * MIN stores of {@code null} to it (the packet loop), the probe runs just before each such store, i.e. after the
 * handler consumed the payload and while the prot, payload length and buffer cursor are still intact.</li>
 * <li>{@code stream=FILE} optional: append every inbound packet in arrival order as {@code seq opcode length hex}
 * (the whole payload), flushed once a second, so a session can be replayed through another decoder.</li>
 * <li>{@code field.*} reflective field names and {@code mul.*} decoders (real value = stored value * multiplier),
 * so the probe never needs to touch private state through generated field accesses.</li>
 * </ul>
 * The probe adds one side-effect-free static call at method entry. It never changes behaviour and never retains
 * packet objects: only the first few payloads per (direction, opcode, length) are kept as hex.
 */
public final class Packet240Agent {
    private static final int SAMPLES = 4;
    private static final int SAMPLE_BYTES = 96;
    private static final Map<String, Entry> entries = new TreeMap<>();
    private static final Map<String, Field> fields = new HashMap<>();
    private static Map<String, String> options;
    private static Path out;
    private static boolean dirty;
    private static final StringBuilder pending = new StringBuilder();
    private static long sequence;

    private static final class Entry {
        long count;
        long mismatches;
        final java.util.Set<String> callers = new java.util.TreeSet<>();
        final java.util.List<String> samples = new java.util.ArrayList<>();
    }

    public static void premain(String argument, Instrumentation instrumentation) {
        options = new TreeMap<>();
        for (String part : argument.split("\\|")) {
            int split = part.indexOf('=');
            options.put(part.substring(0, split), part.substring(split + 1));
        }
        out = Path.of(options.get("out"));
        Runtime.getRuntime().addShutdownHook(new Thread(Packet240Agent::flush));
        Thread flusher = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException stop) {
                    return;
                }
                flush();
            }
        }, "packet240-flush");
        flusher.setDaemon(true);
        flusher.start();
        if (options.get("stream") != null) {
            Thread streamer = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(1_000);
                    } catch (InterruptedException stop) {
                        return;
                    }
                    flushStream();
                }
            }, "packet240-stream");
            streamer.setDaemon(true);
            streamer.start();
            Runtime.getRuntime().addShutdownHook(new Thread(Packet240Agent::flushStream));
        }
        String send = options.get("send");
        String recv = options.get("recv");
                instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String name, Class<?> type, ProtectionDomain domain,
                                    byte[] bytes) {
                if (name == null) {
                    return null;
                }
                boolean sends = send != null && send.startsWith(name + ".");
                boolean receives = recv != null && !name.startsWith("java/") && !name.startsWith("net/")
                        && !name.startsWith("org/") && !name.startsWith("com/") && !name.startsWith("sun/")
                        && !name.startsWith("jdk/");
                if (!sends && !receives) {
                    return null;
                }
                try {
                    return hook(bytes, sends ? send : null, receives ? recv : null);
                } catch (RuntimeException failure) {
                    System.err.println("Packet240Agent: skipped " + name + ": " + failure);
                    return null;
                }
            }
        });
    }

    private static byte[] hook(byte[] bytes, String send, String recv) {
        ClassNode node = new ClassNode();
        new ClassReader(bytes).accept(node, 0);
        boolean changed = false;
        String store = null;
        int minimum = 0;
        if (recv != null) {
            int colon = recv.indexOf(':');
            store = recv.substring(0, colon);
            minimum = Integer.parseInt(recv.substring(colon + 1));
        }
        for (MethodNode method : node.methods) {
            String id = node.name + "." + method.name + ":" + method.desc;
            if (id.equals(send)) {
                InsnList enter = new InsnList();
                enter.add(new VarInsnNode(Opcodes.ALOAD, Integer.parseInt(options.getOrDefault("sendVar", "1"))));
                enter.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Packet240Agent", "sent",
                        "(Ljava/lang/Object;)V", false));
                method.instructions.insert(enter);
                changed = true;
            }
            if (store == null) {
                continue;
            }
            java.util.List<AbstractInsnNode> sites = new java.util.ArrayList<>();
            for (AbstractInsnNode insn : method.instructions) {
                if (insn.getOpcode() == Opcodes.PUTFIELD
                        && (((FieldInsnNode) insn).owner + "." + ((FieldInsnNode) insn).name).equals(store)
                        && insn.getPrevious() != null && insn.getPrevious().getOpcode() == Opcodes.ACONST_NULL) {
                    sites.add(insn);
                }
            }
            if (sites.size() < minimum) {
                continue;
            }
            for (AbstractInsnNode site : sites) {
                // Stack is [connection, null]: drop the null, call the probe on a copy, restore the null.
                InsnList probe = new InsnList();
                probe.add(new InsnNode(Opcodes.POP));
                probe.add(new InsnNode(Opcodes.DUP));
                probe.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Packet240Agent", "received",
                        "(Ljava/lang/Object;)V", false));
                probe.add(new InsnNode(Opcodes.ACONST_NULL));
                method.instructions.insertBefore(site, probe);
            }
            changed = true;
        }
        if (!changed) {
            return null;
        }
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        node.accept(writer);
        return writer.toByteArray();
    }

    private static Object read(Object target, String fieldKey) throws ReflectiveOperationException {
        String name = options.get(fieldKey);
        Class<?> owner = target.getClass();
        Field field = fields.get(fieldKey);
        if (field == null) {
            for (Class<?> type = owner; type != null && field == null; type = type.getSuperclass()) {
                try {
                    field = type.getDeclaredField(name);
                } catch (NoSuchFieldException next) {
                    // Keep walking up: the buffer fields live in a superclass.
                }
            }
            if (field == null) {
                throw new NoSuchFieldException(fieldKey + "=" + name);
            }
            field.setAccessible(true);
            fields.put(fieldKey, field);
        }
        return field.get(target);
    }

    private static int decode(Object stored, String multiplierKey) {
        int value = (Integer) stored;
        String multiplier = options.get(multiplierKey);
        return multiplier == null ? value : value * Integer.parseInt(multiplier);
    }

    private static String hex(byte[] data, int from, int to) {
        StringBuilder text = new StringBuilder();
        for (int i = from; i < to && i < from + SAMPLE_BYTES; i++) {
            text.append(String.format("%02x", data[i] & 255));
        }
        return text.toString();
    }

    private static synchronized void record(String direction, int opcode, int declared, int actual, String sample,
                                            String caller, int consumed) {
        Entry entry = entries.computeIfAbsent(direction + "\t" + opcode + "\t" + declared + "\t" + actual,
                ignored -> new Entry());
        entry.count++;
        if (consumed >= 0 && consumed != actual) {
            entry.mismatches++;
        }
        if (caller != null && entry.callers.size() < 8) {
            entry.callers.add(caller);
        }
        if (entry.samples.size() < SAMPLES) {
            entry.samples.add(sample);
        }
        dirty = true;
    }

    /** Outbound: the client packet node, before it is queued (buffer offset is its total encoded size). */
    public static void sent(Object packet) {
        try {
            Object prot = read(packet, "field.send.prot");
            Object buffer = read(packet, "field.send.buffer");
            int opcode = prot == null ? -1 : decode(read(prot, "field.prot.opcode"), "mul.send.opcode");
            int declared = prot == null ? -3 : decode(read(prot, "field.prot.length"), "mul.send.length");
            byte[] array = (byte[]) read(buffer, "field.buffer.array");
            int end = decode(read(buffer, "field.buffer.offset"), "mul.buffer.offset");
            record("out", opcode, declared, end, hex(array, 0, Math.min(end, array.length)), caller(), -1);
        } catch (ReflectiveOperationException | RuntimeException failure) {
            failure("send", failure);
        }
    }

    /** Inbound: called with the connection when a whole server packet is buffered, before its handler runs. */
    public static void received(Object connection) {
        try {
            Object prot = read(connection, "field.recv.prot");
            if (prot == null) {
                return;
            }
            Object buffer = read(connection, "field.recv.buffer");
            int opcode = decode(read(prot, "field.sprot.opcode"), "mul.recv.opcode");
            int declared = decode(read(prot, "field.sprot.length"), "mul.recv.length");
            int length = decode(read(connection, "field.recv.length"), "mul.recv.payload");
            int consumed = decode(read(buffer, "field.buffer.offset"), "mul.buffer.offset");
            byte[] array = (byte[]) read(buffer, "field.buffer.array");
            int end = Math.min(length, array.length);
            record("in", opcode, declared, length, "consumed=" + consumed + ":" + hex(array, 0, end), null, consumed);
            if (options.get("stream") != null) {
                stream(opcode, array, end);
            }
        } catch (ReflectiveOperationException | RuntimeException failure) {
            failure("recv", failure);
        }
    }

    /** First frame outside this agent and the queue method: the original method that built the packet. */
    private static String caller() {
        return StackWalker.getInstance().walk(frames -> frames.map(f -> f.getClassName() + "." + f.getMethodName())
                .filter(name -> !name.startsWith("Packet240Agent.") && !name.equals(options.get("send")
                        .substring(0, options.get("send").indexOf(':'))))
                .skip(0).findFirst().orElse(null));
    }

    private static synchronized void stream(int opcode, byte[] array, int end) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < end; i++) {
            text.append(String.format("%02x", array[i] & 255));
        }
        pending.append(sequence++).append(' ').append(opcode).append(' ').append(end).append(' ').append(text)
                .append('\n');
    }

    private static synchronized void flushStream() {
        if (pending.length() == 0) {
            return;
        }
        try {
            Files.writeString(Path.of(options.get("stream")), pending, StandardCharsets.UTF_8,
                    java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
            pending.setLength(0);
        } catch (IOException ignored) {
            // Keep the lines and retry on the next flush.
        }
    }

    private static synchronized void failure(String where, Exception failure) {
        if (entries.putIfAbsent("error\t" + where + "\t" + failure, new Entry()) == null) {
            System.err.println("Packet240Agent " + where + ": " + failure);
        }
    }

    private static synchronized void flush() {
        if (!dirty || out == null) {
            return;
        }
        Path temporary = out.resolveSibling(out.getFileName() + ".tmp");
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(temporary, StandardCharsets.UTF_8))) {
            writer.println("direction\topcode\tdeclared_length\tactual_length\tcount\tconsumed_mismatches\tcallers\tsamples");
            entries.forEach((key, entry) -> writer.println(key + "\t" + entry.count + "\t" + entry.mismatches + "\t"
                    + String.join(",", entry.callers) + "\t" + String.join(",", entry.samples)));
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
