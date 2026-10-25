package dev.neosback.deob.analysis.protocol;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/** Parsed external packet table. It is semantic/profile evidence, never bytecode authority. */
public record PacketTable(
        PacketDirection direction,
        String revision,
        int declaredCount,
        int declaredNamedCount,
        int declaredTableSize,
        List<String> columns,
        List<PacketTableEntry> entries,
        String source) {

    public PacketTable {
        revision = revision == null ? "" : revision;
        columns = List.copyOf(columns);
        entries = entries.stream()
                .sorted(Comparator.comparingInt(PacketTableEntry::id))
                .toList();
        source = source == null ? "" : source;
    }

    public Optional<PacketTableEntry> byName(String name) {
        return entries.stream().filter(entry -> entry.name().equals(name)).findFirst();
    }

    public Optional<PacketTableEntry> byId(int id) {
        return entries.stream().filter(entry -> entry.id() == id).findFirst();
    }
}
