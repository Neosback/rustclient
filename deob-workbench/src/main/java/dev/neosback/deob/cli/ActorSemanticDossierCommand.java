package dev.neosback.deob.cli;

import dev.neosback.deob.analysis.protocol.PacketDirection;
import dev.neosback.deob.analysis.protocol.PacketTable;
import dev.neosback.deob.analysis.protocol.PacketTableParser;
import dev.neosback.deob.analysis.semantic.ActorSemanticDossierAnalyzer;
import dev.neosback.deob.analysis.semantic.SemanticVocabularyCatalog;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.io.JsonSupport;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "actor-semantic-dossier", description = "Fuse mask lineage with pinned Actor semantic vocabulary")
public final class ActorSemanticDossierCommand implements Callable<Integer> {
    @Option(names = "--source-jar", required = true)
    private Path sourceJar;

    @Option(names = "--source-server-packets", required = true)
    private Path sourceServerPackets;

    @Option(names = "--target-jar", required = true)
    private Path targetJar;

    @Option(names = "--target-server-packets", required = true)
    private Path targetServerPackets;

    @Option(names = "--vocabulary", defaultValue = "config/runelite-1.12.36-actor-vocabulary.json")
    private Path vocabulary;

    @Option(names = "--out", required = true)
    private Path out;

    @Override
    public Integer call() throws Exception {
        PacketTableParser parser = new PacketTableParser();
        PacketTable sourcePackets = parser.parse(sourceServerPackets, PacketDirection.SERVER_TO_CLIENT);
        PacketTable targetPackets = parser.parse(targetServerPackets, PacketDirection.SERVER_TO_CLIENT);
        SemanticVocabularyCatalog catalog = JsonSupport.read(vocabulary, SemanticVocabularyCatalog.class);
        JarCorpus source = JarCorpus.load(sourceJar);
        JarCorpus target = JarCorpus.load(targetJar);
        JsonSupport.write(out, new ActorSemanticDossierAnalyzer().analyze(
                source, sourcePackets, target, targetPackets, catalog));
        return 0;
    }
}
