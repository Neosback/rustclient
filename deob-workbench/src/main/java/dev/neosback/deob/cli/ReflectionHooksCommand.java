package dev.neosback.deob.cli;

import dev.neosback.deob.analysis.hooks.ReflectionHookDeriver;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.io.JsonSupport;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "reflection-hooks", description = "Derive candidate exact-name hooks for later controlled reflection")
public final class ReflectionHooksCommand implements Callable<Integer> {
    @Option(names = "--jar", required = true) private Path jar;
    @Option(names = "--out", required = true) private Path out;

    @Override public Integer call() throws Exception {
        JsonSupport.write(out, new ReflectionHookDeriver().deriveBufferHooks(JarCorpus.load(jar)));
        return 0;
    }
}
