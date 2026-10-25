package dev.neosback.deob.cli;

import dev.neosback.deob.analysis.protocol.PacketReaderGeometryAnalyzer;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.io.JsonSupport;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "packet-reader-geometry", description = "Find boolean packet-reader geometry outliers")
public final class PacketReaderGeometryCommand implements Callable<Integer> {
    @Option(names = "--jar", required = true) private Path jar;
    @Option(names = "--expected", defaultValue = "5") private int expected;
    @Option(names = "--out", required = true) private Path out;

    @Override public Integer call() throws Exception {
        JsonSupport.write(out, new PacketReaderGeometryAnalyzer().analyze(JarCorpus.load(jar), expected));
        return 0;
    }
}
