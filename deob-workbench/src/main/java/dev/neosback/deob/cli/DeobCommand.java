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
        PacketProfileCommand.class,
        ProtocolFingerprintCommand.class,
        PacketReaderGeometryCommand.class,
        BufferInfrastructureCommand.class,
        ReflectionHooksCommand.class,
        ReflectionHooksControlledCommand.class,
        BitAccessCommand.class,
        SyncCandidatesCommand.class,
        SyncPathsCommand.class,
        UpdateMasksCommand.class,
        MaskFingerprintsCommand.class,
        MaskLineageCommand.class,
        ActorClustersCommand.class,
        ActorSemanticDossierCommand.class,
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
                + "decoders, packet-reads, packet-timelines, packet-profile, protocol-fingerprint, "
                + "packet-reader-geometry, buffer-infra, reflection-hooks, reflection-hooks-controlled, "
                + "bit-access, sync-candidates, sync-paths, update-masks, mask-fingerprints, "
                + "mask-lineage, actor-clusters, actor-semantic-dossier, lineage, protocol, sources."
        );
        return 0;
    }
}
