package dev.neosback.deob.analysis.injected;

import dev.neosback.deob.corpus.JarCorpus;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import org.junit.Test;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import static org.junit.Assert.*;

public final class InjectedApiBridgeAnalyzerTest {
    @Test
    public void recognizesBothInjectedApiNamespacesAndIgnoresUnrelatedInterfaces() throws Exception {
        Path jar = Files.createTempFile("injected-api-namespaces-", ".jar");
        try {
            try (JarOutputStream output = new JarOutputStream(Files.newOutputStream(jar))) {
                addGetter(output, "Current", "net/runelite/api/Actor");
                addGetter(output, "Legacy", "net/runelite/rs/api/RSActor");
                addGetter(output, "Unrelated", "example/Actor");
            }
            JarCorpus corpus = JarCorpus.load(jar);
            InjectedApiBridgeReport report = new InjectedApiBridgeAnalyzer().analyze(corpus);
            assertEquals(corpus.sha256(), report.corpusSha256());
            assertEquals(2, report.bridges().size());
            assertTrue(report.bridges().stream().allMatch(bridge ->
                    bridge.kind() == InjectedBridgeKind.DIRECT_FIELD_GETTER));
            assertFalse(report.bridges().stream().anyMatch(bridge ->
                    bridge.injectedMethod().owner().internalName().equals("Unrelated")));
        } finally {
            Files.deleteIfExists(jar);
        }
    }

    private static void addGetter(JarOutputStream output, String name, String api) throws Exception {
        ClassWriter writer = new ClassWriter(0);
        writer.visit(Opcodes.V17, Opcodes.ACC_PUBLIC, name, null, "java/lang/Object", new String[]{api});
        writer.visitField(Opcodes.ACC_PRIVATE, "raw", "I", null, null).visitEnd();
        var method = writer.visitMethod(Opcodes.ACC_PUBLIC, "getHealth", "()I", null, null);
        method.visitCode();
        method.visitVarInsn(Opcodes.ALOAD, 0);
        method.visitFieldInsn(Opcodes.GETFIELD, name, "raw", "I");
        method.visitInsn(Opcodes.IRETURN);
        method.visitMaxs(1, 1);
        method.visitEnd();
        writer.visitEnd();
        output.putNextEntry(new JarEntry(name + ".class"));
        output.write(writer.toByteArray());
        output.closeEntry();
    }
}
