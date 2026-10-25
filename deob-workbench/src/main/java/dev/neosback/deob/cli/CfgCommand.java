package dev.neosback.deob.cli;

import dev.neosback.deob.analysis.cfg.CfgAnalyzer;
import dev.neosback.deob.corpus.ClassArtifact;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.identity.ClassId;
import dev.neosback.deob.identity.MethodId;
import dev.neosback.deob.io.JsonSupport;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "cfg", description = "Build an exact original-bytecode CFG for one method")
public final class CfgCommand implements Callable<Integer> {
    @Option(names = "--jar", required = true)
    private Path jar;

    @Option(names = "--class", required = true, description = "Internal JVM class name, for example aa or classDF")
    private String className;

    @Option(names = "--method", required = true)
    private String methodName;

    @Option(names = "--desc", required = true, description = "Exact JVM method descriptor")
    private String descriptor;

    @Option(names = "--out", required = true)
    private Path out;

    @Override
    public Integer call() throws Exception {
        JarCorpus corpus = JarCorpus.load(jar);
        ClassId classId = new ClassId(className);
        ClassArtifact artifact = corpus.requireClass(classId);
        ClassNode classNode = artifact.readNode();
        MethodNode method = classNode.methods.stream()
            .filter(candidate -> candidate.name.equals(methodName) && candidate.desc.equals(descriptor))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(
                "Method not found: " + className + "." + methodName + descriptor
            ));

        MethodId methodId = new MethodId(classId, methodName, descriptor);
        JsonSupport.write(out, new CfgAnalyzer().analyze(methodId, method));
        return 0;
    }
}
