package dev.neosback.deob.analysis.hooks;

import java.util.List;

public record ReflectionHookManifest(
    String sourceSha256,
    String controlSha256,
    boolean controlVerified,
    List<ReflectionHookSpec> hooks,
    List<String> warnings
) {
    public ReflectionHookManifest {
        hooks = List.copyOf(hooks);
        warnings = List.copyOf(warnings);
    }
}
