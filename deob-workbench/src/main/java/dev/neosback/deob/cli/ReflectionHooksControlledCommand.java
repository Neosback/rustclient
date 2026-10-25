package dev.neosback.deob.cli;

import dev.neosback.deob.analysis.hooks.ReflectionHookControlValidator;
import dev.neosback.deob.analysis.hooks.ReflectionHookManifest;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.io.JsonSupport;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "reflection-hooks-controlled", description = "Derive target reflection hooks only after reproducing a known-good control manifest")
public final class ReflectionHooksControlledCommand implements Callable<Integer> {
    @Option(names = "--control-jar", required = true)
    private Path controlJar;

    @Option(names = "--control-hooks", required = true)
    private Path controlHooks;

    @Option(names = "--jar", required = true)
    private Path jar;

    @Option(names = "--out", required = true)
    private Path out;

    @Override
    public Integer call() throws Exception {
        JarCorpus control = JarCorpus.load(controlJar);
        JarCorpus target = JarCorpus.load(jar);
        ReflectionHookManifest expected = JsonSupport.read(controlHooks, ReflectionHookManifest.class);
        JsonSupport.write(out, new ReflectionHookControlValidator().deriveWithControl(control, expected, target));
        return 0;
    }
}
