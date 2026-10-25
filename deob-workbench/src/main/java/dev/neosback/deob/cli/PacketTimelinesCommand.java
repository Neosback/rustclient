package dev.neosback.deob.cli;

import dev.neosback.deob.analysis.packet.PacketTimelineAnalyzer;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.io.JsonSupport;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "packet-timelines", description = "Trace discovered packet-read results to exact consumers")
public final class PacketTimelinesCommand implements Callable<Integer> {
    @Option(names = "--jar", required = true)
    private Path jar;

    @Option(names = "--out", required = true)
    private Path out;

    @Override
    public Integer call() throws Exception {
        JarCorpus corpus = JarCorpus.load(jar);
        JsonSupport.write(out, new PacketTimelineAnalyzer().analyze(corpus));
        return 0;
    }
}
