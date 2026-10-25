package dev.neosback.recaf;

import com.google.gson.GsonBuilder;
import com.sun.source.tree.*;
import com.sun.source.util.JavacTask;
import com.sun.source.util.TreeScanner;
import javax.tools.*;
import java.nio.file.*;
import java.util.*;

/** Syntax-only source inspection: no type checking, class loading or behavioral equivalence claim. */
public final class SourceMetrics {
    public static void main(String[] args) throws Exception {
        Path root = Path.of(args[0]).toAbsolutePath();
        var compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) throw new IllegalStateException("JDK compiler required");
        var diagnostics = new DiagnosticCollector<JavaFileObject>();
        try (var files = compiler.getStandardFileManager(diagnostics, Locale.ROOT, java.nio.charset.StandardCharsets.UTF_8)) {
            List<Path> sources;
            try (var stream = Files.walk(root)) { sources = stream.filter(p -> p.toString().endsWith(".java")).sorted().toList(); }
            var units = files.getJavaFileObjectsFromPaths(sources);
            JavacTask task = (JavacTask) compiler.getTask(null, files, diagnostics, List.of("-proc:none", "-Xmaxerrs", "100000"), null, units);
            Map<String, Object> output = new TreeMap<>();
            for (CompilationUnitTree unit : task.parse()) {
                Map<String, Integer> names = new TreeMap<>();
                Map<String, Integer> fieldNames = new TreeMap<>();
                int[] counts = new int[3];
                new TreeScanner<Void, Void>() {
                    private int classDepth;
                    private int methodDepth;
                    private int blockDepth;
                    @Override public Void visitClass(ClassTree cls, Void unused) {
                        classDepth++;
                        var result = super.visitClass(cls, unused);
                        classDepth--;
                        return result;
                    }
                    @Override public Void visitMethod(MethodTree method, Void unused) {
                        if (classDepth == 1) {
                            names.merge(method.getName().toString(), 1, Integer::sum);
                            counts[0]++;
                            if (method.getBody() != null) counts[1]++;
                        }
                        methodDepth++;
                        var result = super.visitMethod(method, unused);
                        methodDepth--;
                        return result;
                    }
                    @Override public Void visitBlock(BlockTree block, Void unused) {
                        blockDepth++;
                        var result = super.visitBlock(block, unused);
                        blockDepth--;
                        return result;
                    }
                    @Override public Void visitVariable(VariableTree variable, Void unused) {
                        if (classDepth == 1 && methodDepth == 0 && blockDepth == 0)
                            fieldNames.merge(variable.getName().toString(), 1, Integer::sum);
                        return super.visitVariable(variable, unused);
                    }
                    @Override public Void visitErroneous(ErroneousTree tree, Void unused) {
                        counts[2]++;
                        return super.visitErroneous(tree, unused);
                    }
                }.scan(unit, null);
                output.put(root.relativize(Path.of(unit.getSourceFile().toUri())).toString(),
                    new TreeMap<>(Map.of("declaredMethods", counts[0], "bodies", counts[1], "erroneousNodes", counts[2], "methodNames", names, "fieldNames", fieldNames,
                        "syntaxErrors", new ArrayList<>())));
            }
            for (var diagnostic : diagnostics.getDiagnostics()) {
                if (diagnostic.getKind() != Diagnostic.Kind.ERROR || diagnostic.getSource() == null) continue;
                String name = root.relativize(Path.of(diagnostic.getSource().toUri())).toString();
                @SuppressWarnings("unchecked") var record = (Map<String, Object>) output.get(name);
                if (record != null) {
                    @SuppressWarnings("unchecked") var errors = (List<Object>) record.get("syntaxErrors");
                    errors.add(Map.of("line", diagnostic.getLineNumber(), "column", diagnostic.getColumnNumber(), "message", diagnostic.getMessage(Locale.ROOT)));
                }
            }
            Files.writeString(Path.of(args[1]), new GsonBuilder().setPrettyPrinting().create().toJson(output) + "\n");
        }
    }
}
