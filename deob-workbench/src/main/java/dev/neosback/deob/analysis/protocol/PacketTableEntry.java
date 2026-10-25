package dev.neosback.deob.analysis.protocol;

import java.util.List;

/** One row from an external packet profile. Operation strings are preserved verbatim as evidence. */
public record PacketTableEntry(
        int id,
        String name,
        int length,
        int ioCount,
        List<String> operations) {

    public PacketTableEntry {
        if (id < 0) {
            throw new IllegalArgumentException("packet id must be >= 0");
        }
        name = name == null ? "-" : name;
        operations = List.copyOf(operations);
    }

    public boolean named() {
        return !name.isBlank() && !"-".equals(name);
    }
}
