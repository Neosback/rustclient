package dev.neosback.deob.cli;

import dev.neosback.deob.analysis.decoder.DecoderEvidenceAnalyzer;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.io.JsonSupport;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "decoders", description = "Discover field multiplier evidence and prove inverse read/write pairs")
public final class DecodersCommand implements Callable<Integer> {
    @Option(names = "--jar", required = true)
    private Path jar;

    @Option(names = "--out", required = true)
    private Path out;

    @Override
    public Integer call() throws Exception {
        JarCorpus corpus = JarCorpus.load(jar);
        JsonSupport.write(out, new DecoderEvidenceAnalyzer().analyze(corpus));
        return 0;
    }
}
