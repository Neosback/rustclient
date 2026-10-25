package dev.neosback.deob.cli;

import dev.neosback.deob.analysis.index.BytecodeIndexer;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.io.JsonSupport;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "index", description = "Index exact bytecode classes, members, metrics, and fingerprints")
public final class IndexCommand implements Callable<Integer> {
    @Option(names = "--jar", required = true, description = "Input jar; use the original jar for behavioral evidence")
    private Path jar;

    @Option(names = "--out", required = true, description = "Deterministic JSON output path")
    private Path out;

    @Override
    public Integer call() throws Exception {
        JarCorpus corpus = JarCorpus.load(jar);
        JsonSupport.write(out, new BytecodeIndexer().index(corpus));
        return 0;
    }
}
