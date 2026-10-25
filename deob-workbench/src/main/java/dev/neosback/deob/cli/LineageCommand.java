package dev.neosback.deob.cli;

import dev.neosback.deob.analysis.lineage.MethodLineageMatcher;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.io.JsonSupport;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "lineage", description = "Rank explainable cross-revision method lineage candidates")
public final class LineageCommand implements Callable<Integer> {
    @Option(names = "--source", required = true, description = "Source revision jar")
    private Path source;

    @Option(names = "--target", required = true, description = "Target revision jar")
    private Path target;

    @Option(names = "--out", required = true)
    private Path out;

    @Override
    public Integer call() throws Exception {
        JarCorpus sourceCorpus = JarCorpus.load(source);
        JarCorpus targetCorpus = JarCorpus.load(target);
        JsonSupport.write(out, new MethodLineageMatcher().match(sourceCorpus, targetCorpus));
        return 0;
    }
}
