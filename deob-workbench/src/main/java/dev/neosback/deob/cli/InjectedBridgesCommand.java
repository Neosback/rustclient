package dev.neosback.deob.cli;

import dev.neosback.deob.analysis.injected.InjectedApiBridgeAnalyzer;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.io.JsonSupport;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "injected-bridges", description = "Extract exact small-shape RuneLite injected API bridges")
public final class InjectedBridgesCommand implements Callable<Integer> {
    @Option(names = "--jar", required = true)
    private Path jar;

    @Option(names = "--out", required = true)
    private Path out;

    @Override
    public Integer call() throws Exception {
        JsonSupport.write(out, new InjectedApiBridgeAnalyzer().analyze(JarCorpus.load(jar)));
        return 0;
    }
}
