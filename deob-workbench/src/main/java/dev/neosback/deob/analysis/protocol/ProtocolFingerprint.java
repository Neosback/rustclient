package dev.neosback.deob.analysis.protocol;

import java.util.List;

/** Exact id-to-length packet-table fingerprint for one direction. */
public record ProtocolFingerprint(
    PacketDirection direction,
    int tableSize,
    List<Integer> lengths,
    String sha256
) {
    public ProtocolFingerprint {
        lengths = List.copyOf(lengths);
    }
}
