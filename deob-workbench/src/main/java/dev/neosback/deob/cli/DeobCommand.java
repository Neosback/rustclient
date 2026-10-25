package dev.neosback.deob.cli;

import java.util.concurrent.Callable;
import picocli.CommandLine.Command;

@Command(
    name = "deob-workbench",
    mixinStandardHelpOptions = true,
    version = "client-deob-workbench 0.1.0-SNAPSHOT",
    description = "Evidence-first OSRS client deobfuscation workbench",
    subcommands = {
        IndexCommand.class,
        CfgCommand.class,
        CfgStructureCommand.class,
        HierarchyCommand.class,
        ProvenanceCommand.class,
        DecodersCommand.class,
        PacketReadsCommand.class,
        PacketTimelinesCommand.class,
        LineageCommand.class,
        ProtocolCommand.class,
        SourcesCommand.class
    }
)
public final class DeobCommand implements Callable<Integer> {
    @Override
    public Integer call() {
        System.out.println(
            "Use --help or a subcommand: index, cfg, cfg-structure, hierarchy, provenance, "
                + "decoders, packet-reads, packet-timelines, lineage, protocol, sources."
        );
        return 0;
    }
}
