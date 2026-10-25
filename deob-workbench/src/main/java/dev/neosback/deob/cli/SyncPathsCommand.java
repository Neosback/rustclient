package dev.neosback.deob.cli;

import dev.neosback.deob.analysis.protocol.InterproceduralSyncAnalyzer;
import dev.neosback.deob.analysis.protocol.PacketDirection;
import dev.neosback.deob.analysis.protocol.PacketTable;
import dev.neosback.deob.analysis.protocol.PacketTableParser;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.io.JsonSupport;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "sync-paths", description = "Expand rev-specific player/NPC sync roots through exact in-corpus calls")
public final class SyncPathsCommand implements Callable<Integer> {
    @Option(names = "--jar", required = true)
    private Path jar;

    @Option(names = "--server-packets", required = true)
    private Path serverPackets;

    @Option(names = "--max-depth", defaultValue = "3")
    private int maxDepth;

    @Option(names = "--out", required = true)
    private Path out;

    @Override
    public Integer call() throws Exception {
        JarCorpus corpus = JarCorpus.load(jar);
        PacketTable table = new PacketTableParser().parse(serverPackets, PacketDirection.SERVER_TO_CLIENT);
        JsonSupport.write(out, new InterproceduralSyncAnalyzer().analyze(corpus, table, maxDepth));
        return 0;
    }
}
