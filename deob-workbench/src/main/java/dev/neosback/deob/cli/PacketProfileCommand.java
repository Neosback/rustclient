package dev.neosback.deob.cli;

import dev.neosback.deob.analysis.protocol.PacketDirection;
import dev.neosback.deob.analysis.protocol.PacketTable;
import dev.neosback.deob.analysis.protocol.PacketTableParser;
import dev.neosback.deob.io.JsonSupport;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "packet-profile", description = "Parse external rev-240 server/client packet TSV evidence")
public final class PacketProfileCommand implements Callable<Integer> {
    @Option(names = "--server-packets", required = true)
    private Path serverPackets;

    @Option(names = "--client-packets", required = true)
    private Path clientPackets;

    @Option(names = "--out", required = true)
    private Path out;

    @Override
    public Integer call() throws Exception {
        PacketTableParser parser = new PacketTableParser();
        PacketTable server = parser.parse(serverPackets, PacketDirection.SERVER_TO_CLIENT);
        PacketTable client = parser.parse(clientPackets, PacketDirection.CLIENT_TO_SERVER);
        JsonSupport.write(out, new ParsedPacketProfile(server, client));
        return 0;
    }

    private record ParsedPacketProfile(PacketTable server, PacketTable client) {}
}
