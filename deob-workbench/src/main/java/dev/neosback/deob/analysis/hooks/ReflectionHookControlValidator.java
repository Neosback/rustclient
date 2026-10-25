package dev.neosback.deob.analysis.hooks;

import dev.neosback.deob.corpus.JarCorpus;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Re-runs reflection-hook derivation on a known control JAR before allowing the same rules to
 * produce hooks for a target JAR. Failure is closed: target hooks are withheld on any mismatch.
 */
public final class ReflectionHookControlValidator {
    public ReflectionHookManifest deriveWithControl(
            JarCorpus control,
            ReflectionHookManifest expectedControl,
            JarCorpus target) {
        List<String> warnings = new ArrayList<>();
        if (expectedControl.hooks().isEmpty()) {
            warnings.add("control manifest contains no expected hooks; target derivation refused");
            return refused(target, control, warnings);
        }
        if (expectedControl.sourceSha256() != null
                && !expectedControl.sourceSha256().isBlank()
                && !expectedControl.sourceSha256().equals(control.sha256())) {
            warnings.add("control SHA mismatch: expected " + expectedControl.sourceSha256()
                    + " but received " + control.sha256());
            return refused(target, control, warnings);
        }

        ReflectionHookManifest actualControl = new ReflectionHookDeriver().deriveBufferHooks(control);
        Map<String, List<ReflectionHookSpec>> actualByLogical = group(actualControl.hooks());
        boolean passed = true;

        for (ReflectionHookSpec expected : expectedControl.hooks()) {
            List<ReflectionHookSpec> sameLogical = actualByLogical.getOrDefault(expected.logicalName(), List.of());
            List<ReflectionHookSpec> exact = sameLogical.stream()
                    .filter(candidate -> sameIdentity(expected, candidate))
                    .toList();
            if (sameLogical.size() != 1 || exact.size() != 1) {
                passed = false;
                warnings.add("control rule failed for " + expected.logicalName()
                        + ": derived=" + sameLogical.size() + ", exact=" + exact.size());
            }
        }

        if (!passed) {
            warnings.add("one or more control hooks did not reproduce uniquely; target hooks withheld");
            warnings.sort(String::compareTo);
            return refused(target, control, warnings);
        }

        ReflectionHookManifest targetCandidates = new ReflectionHookDeriver().deriveBufferHooks(target);
        List<ReflectionHookSpec> verified = targetCandidates.hooks().stream()
                .map(ReflectionHookControlValidator::markControlVerified)
                .sorted(Comparator.comparing(ReflectionHookSpec::logicalName)
                        .thenComparing(ReflectionHookSpec::ownerInternalName)
                        .thenComparing(ReflectionHookSpec::memberName))
                .toList();
        warnings.addAll(targetCandidates.warnings());
        warnings.add("all supplied control hooks reproduced uniquely before target derivation");
        warnings.sort(String::compareTo);
        return new ReflectionHookManifest(target.sha256(), control.sha256(), true, verified, warnings);
    }

    private static ReflectionHookManifest refused(
            JarCorpus target,
            JarCorpus control,
            List<String> warnings) {
        warnings.sort(String::compareTo);
        return new ReflectionHookManifest(target.sha256(), control.sha256(), false, List.of(), warnings);
    }

    private static Map<String, List<ReflectionHookSpec>> group(List<ReflectionHookSpec> hooks) {
        Map<String, List<ReflectionHookSpec>> result = new HashMap<>();
        for (ReflectionHookSpec hook : hooks) {
            result.computeIfAbsent(hook.logicalName(), ignored -> new ArrayList<>()).add(hook);
        }
        return result;
    }

    private static boolean sameIdentity(ReflectionHookSpec left, ReflectionHookSpec right) {
        return left.kind() == right.kind()
                && left.ownerInternalName().equals(right.ownerInternalName())
                && left.memberName().equals(right.memberName())
                && left.descriptor().equals(right.descriptor())
                && left.staticMember() == right.staticMember();
    }

    private static ReflectionHookSpec markControlVerified(ReflectionHookSpec hook) {
        List<String> evidence = new ArrayList<>(hook.evidence());
        evidence.add("derivation rules reproduced the supplied known-good control hooks");
        return new ReflectionHookSpec(
                hook.logicalName(),
                hook.kind(),
                hook.ownerInternalName(),
                hook.memberName(),
                hook.descriptor(),
                hook.staticMember(),
                hook.requiresAccessible(),
                "CONTROL_VERIFIED",
                evidence);
    }
}
