package dev.neosback.deob.cli;

import dev.neosback.deob.analysis.hierarchy.ClassHierarchy;
import dev.neosback.deob.analysis.hierarchy.HierarchyReport;
import dev.neosback.deob.analysis.hierarchy.VirtualMethodFamilies;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.io.JsonSupport;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "hierarchy", description = "Index exact class hierarchy and virtual method families")
public final class HierarchyCommand implements Callable<Integer> {
    @Option(names = "--jar", required = true)
    private Path jar;

    @Option(names = "--out", required = true)
    private Path out;

    @Override
    public Integer call() throws Exception {
        JarCorpus corpus = JarCorpus.load(jar);
        ClassHierarchy hierarchy = ClassHierarchy.analyze(corpus);
        JsonSupport.write(out, new HierarchyReport(
                new ArrayList<>(hierarchy.relations().values()),
                VirtualMethodFamilies.analyze(corpus, hierarchy)));
        return 0;
    }
}
