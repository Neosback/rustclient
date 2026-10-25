import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.ProtectionDomain;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;

/** Connection-only adapter for the checksum-pinned original injected 240 client. */
public final class Local240Agent {
    private static final String ORIGINAL_MODULUS =
        "8008cc488ea535aa529757737359586b5ab3018f4ddede24a3f447c2b478eaac392381302f8da057a1550f4cabf937ab9c975036b371178524c87ac213835323b928c80dbc4bf272164afb394446fc2eef6b180048ce3f844ddf800b70ae5db055ae9bc4850cbaed6053448bfa88170f8d11969ef6fef57bc43ba2dda04cc2bd";

    public static void premain(String keyFile, Instrumentation instrumentation) throws Exception {
        String modulus = null;
        String exponent = null;
        for (String line : Files.readAllLines(Path.of(keyFile))) {
            if (line.startsWith("Modulus:")) modulus = line.substring(8).trim();
            if (line.startsWith("Exponent:")) exponent = line.substring(9).trim();
        }
        if (modulus == null || !modulus.matches("[0-9a-fA-F]{128,1024}")
            || !"10001".equals(exponent)) {
            throw new IllegalArgumentException("Expected OpenRune public client.key with exponent 10001");
        }
        final String publicModulus = modulus;
        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String name, Class<?> type,
                                    ProtectionDomain domain, byte[] bytes) {
                if (!name.equals("bq")) return null;
                ClassNode node = new ClassNode();
                new ClassReader(bytes).accept(node, 0);
                int replaced = 0;
                for (var method : node.methods) {
                    if (!method.name.equals("<clinit>")) continue;
                    for (var instruction : method.instructions) {
                        if (instruction instanceof LdcInsnNode
                            && ORIGINAL_MODULUS.equals(((LdcInsnNode) instruction).cst)) {
                            ((LdcInsnNode) instruction).cst = publicModulus;
                            replaced++;
                        }
                    }
                }
                if (replaced != 1) {
                    // Transformer exceptions can be swallowed by Instrumentation. Stop instead.
                    System.err.println("Local240Agent: unexpected RSA bytecode; stopping");
                    Runtime.getRuntime().halt(1);
                }
                ClassWriter writer = new ClassWriter(0);
                node.accept(writer);
                System.err.println("Local240Agent: applied OpenRune public RSA modulus (bq.<clinit>, one constant)");
                return writer.toByteArray();
            }
        });
    }
}
