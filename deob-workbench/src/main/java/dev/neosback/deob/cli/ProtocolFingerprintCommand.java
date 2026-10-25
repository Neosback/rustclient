package dev.neosback.deob.cli;

import dev.neosback.deob.analysis.protocol.PacketDirection;
import dev.neosback.deob.analysis.protocol.PacketTableParser;
import dev.neosback.deob.analysis.protocol.ProtocolFingerprint;
import dev.neosback.deob.analysis.protocol.ProtocolFingerprintAnalyzer;
import dev.neosback.deob.io.JsonSupport;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "protocol-fingerprint", description = "Build exact packet id-to-length fingerprints")
public final class ProtocolFingerprintCommand implements Callable<Integer> {
    @Option(names = "--server-packets", required = true) private Path serverPackets;
    @Option(names = "--client-packets", required = true) private Path clientPackets;
    @Option(names = "--out", required = true) private Path out;

    @Override public Integer call() throws Exception {
        PacketTableParser parser = new PacketTableParser();
        ProtocolFingerprintAnalyzer analyzer = new ProtocolFingerprintAnalyzer();
        ProtocolFingerprint server = analyzer.fingerprint(parser.parse(serverPackets, PacketDirection.SERVER_TO_CLIENT));
        ProtocolFingerprint client = analyzer.fingerprint(parser.parse(clientPackets, PacketDirection.CLIENT_TO_SERVER));
        JsonSupport.write(out, new Fingerprints(server, client));
        return 0;
    }

    private record Fingerprints(ProtocolFingerprint server, ProtocolFingerprint client) {}
}
