package dev.neosback.deob.analysis.provenance;

/** One origin feeding a value consumed by an instruction. */
public record ValueOrigin(OriginKind kind, String key) implements Comparable<ValueOrigin> {
    @Override
    public int compareTo(ValueOrigin other) {
        int kindCompare = kind.compareTo(other.kind);
        return kindCompare != 0 ? kindCompare : key.compareTo(other.key);
    }
}
