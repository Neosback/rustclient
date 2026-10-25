package dev.neosback.recaf;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.jar.JarFile;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.TraceClassVisitor;

/** Complete ASM reading fallback, including bootstrap values and exception tables. */
public final class BytecodeDump {
    public static void main(String[] args) throws Exception {
        Path output = Path.of(args[1]);
        Files.createDirectories(output);
        try (JarFile jar = new JarFile(args[0])) {
            for (int i = 2; i < args.length; i++) {
                var entry = jar.getJarEntry(args[i] + ".class");
                if (entry == null) throw new IllegalArgumentException("Unknown class: " + args[i]);
                Path file = output.resolve(args[i] + ".txt");
                Files.createDirectories(file.getParent());
                try (var input = jar.getInputStream(entry); var writer = new PrintWriter(Files.newBufferedWriter(file, StandardCharsets.UTF_8))) {
                    new ClassReader(input).accept(new TraceClassVisitor(writer), 0);
                }
            }
        }
    }
}
