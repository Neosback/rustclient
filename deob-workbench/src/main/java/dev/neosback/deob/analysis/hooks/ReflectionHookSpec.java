package dev.neosback.deob.analysis.hooks;

import java.util.List;

/** Exact member identity intended for later reflective access. No semantic name is authoritative by itself. */
public record ReflectionHookSpec(
    String logicalName,
    ReflectionHookKind kind,
    String ownerInternalName,
    String memberName,
    String descriptor,
    boolean staticMember,
    boolean requiresAccessible,
    String status,
    List<String> evidence
) {
    public ReflectionHookSpec {
        evidence = List.copyOf(evidence);
    }
}
