package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.util.Hashes;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/** Builds an exact packet id->length vector so same-sized but different protocols do not align accidentally. */
public final class ProtocolFingerprintAnalyzer {
    public ProtocolFingerprint fingerprint(PacketTable table) {
        int size = table.declaredTableSize() > 0 ? table.declaredTableSize() : table.entries().stream().mapToInt(PacketTableEntry::id).max().orElse(-1) + 1;
        List<Integer> lengths = new ArrayList<>(java.util.Collections.nCopies(size, Integer.MIN_VALUE));
        for (PacketTableEntry entry : table.entries()) {
            if (entry.id() >= 0 && entry.id() < size) lengths.set(entry.id(), entry.length());
        }
        StringBuilder canonical = new StringBuilder(table.direction().name()).append(':').append(size).append('\n');
        for (int id = 0; id < lengths.size(); id++) canonical.append(id).append('=').append(lengths.get(id)).append('\n');
        return new ProtocolFingerprint(table.direction(), size, lengths, Hashes.sha256(canonical.toString().getBytes(StandardCharsets.UTF_8)));
    }

    public boolean exactMatch(ProtocolFingerprint left, ProtocolFingerprint right) {
        return left.direction() == right.direction()
            && left.tableSize() == right.tableSize()
            && left.lengths().equals(right.lengths())
            && left.sha256().equals(right.sha256());
    }
}
