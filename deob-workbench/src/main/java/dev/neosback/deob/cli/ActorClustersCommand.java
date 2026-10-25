package dev.neosback.deob.cli;

import dev.neosback.deob.analysis.protocol.ActorBehaviorClusterAnalyzer;
import dev.neosback.deob.analysis.protocol.PacketDirection;
import dev.neosback.deob.analysis.protocol.PacketTable;
import dev.neosback.deob.analysis.protocol.PacketTableParser;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.io.JsonSupport;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "actor-clusters", description = "Rank broad actor behavior clusters from update-mask evidence")
public final class ActorClustersCommand implements Callable<Integer> {
    @Option(names = "--jar", required = true)
    private Path jar;

    @Option(names = "--server-packets", required = true)
    private Path serverPackets;

    @Option(names = "--out", required = true)
    private Path out;

    @Override
    public Integer call() throws Exception {
        JarCorpus corpus = JarCorpus.load(jar);
        PacketTable table = new PacketTableParser().parse(serverPackets, PacketDirection.SERVER_TO_CLIENT);
        JsonSupport.write(out, new ActorBehaviorClusterAnalyzer().analyze(corpus, table));
        return 0;
    }
}
