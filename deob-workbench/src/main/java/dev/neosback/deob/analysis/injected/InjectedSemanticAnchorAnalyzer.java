package dev.neosback.deob.analysis.injected;

import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.identity.ClassId;
import dev.neosback.deob.identity.FieldId;
import dev.neosback.deob.identity.MethodId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Aggregates exact injected getter/setter/invoker bridges and proves getter/setter modular inverse
 * pairs where both constants are available.
 */
public final class InjectedSemanticAnchorAnalyzer {
    public InjectedSemanticAnchorReport analyze(JarCorpus corpus) {
        InjectedApiBridgeReport bridges = new InjectedApiBridgeAnalyzer().analyze(corpus);
        Map<FieldTermKey, MutableFieldAnchor> fields = new HashMap<>();
        List<InjectedMethodAnchor> methods = new ArrayList<>();
        List<String> warnings = new ArrayList<>(bridges.warnings());

        for (InjectedApiBridge bridge : bridges.bridges()) {
            if (bridge.kind() == InjectedBridgeKind.DIRECT_METHOD_INVOKER) {
                if (bridge.targetMethod() != null) {
                    methods.add(new InjectedMethodAnchor(
                            bridge.targetMethod(),
                            bridge.semanticName(),
                            bridge.injectedMethod(),
                            bridge.rsInterfaces(),
                            bridge.staticTarget(),
                            "EXACT_SINGLE_CALL_BRIDGE"));
                }
                continue;
            }
            if (bridge.targetField() == null) continue;
            FieldTermKey key = new FieldTermKey(bridge.targetField(), bridge.semanticName());
            MutableFieldAnchor anchor = fields.computeIfAbsent(
                    key, ignored -> new MutableFieldAnchor(bridge.targetField(), bridge.semanticName()));
            anchor.interfaces.addAll(bridge.rsInterfaces());
            switch (bridge.kind()) {
                case DIRECT_FIELD_GETTER -> anchor.getters.add(bridge.injectedMethod());
                case DIRECT_DECODED_INT_GETTER, DIRECT_DECODED_LONG_GETTER -> {
                    anchor.getters.add(bridge.injectedMethod());
                    addConstant(anchor.getterConstants, bridge.arithmeticConstant());
                }
                case DIRECT_FIELD_SETTER -> anchor.setters.add(bridge.injectedMethod());
                case DIRECT_ENCODED_INT_SETTER, DIRECT_ENCODED_LONG_SETTER -> {
                    anchor.setters.add(bridge.injectedMethod());
                    addConstant(anchor.setterConstants, bridge.arithmeticConstant());
                }
                case DIRECT_METHOD_INVOKER -> { }
            }
        }

        List<InjectedFieldAnchor> fieldAnchors = new ArrayList<>();
        for (MutableFieldAnchor anchor : fields.values()) {
            Long decoder = unique(anchor.getterConstants);
            Long encoder = unique(anchor.setterConstants);
            boolean inverse = decoder != null && encoder != null
                    && modularInverse(anchor.field.descriptor(), decoder, encoder);
            List<String> evidence = new ArrayList<>();
            evidence.add("exact injected bridge target=" + anchor.field.stableKey());
            if (!anchor.getters.isEmpty()) evidence.add("getter bridges=" + anchor.getters.size());
            if (!anchor.setters.isEmpty()) evidence.add("setter bridges=" + anchor.setters.size());
            if (decoder != null) evidence.add("getter decoder=" + decoder);
            if (encoder != null) evidence.add("setter encoder=" + encoder);
            if (inverse) evidence.add("getter*setter == 1 modulo field width");
            if (anchor.getterConstants.size() > 1) {
                warnings.add("multiple getter multipliers for " + anchor.field.stableKey()
                        + " semantic=" + anchor.term + ": " + anchor.getterConstants);
            }
            if (anchor.setterConstants.size() > 1) {
                warnings.add("multiple setter multipliers for " + anchor.field.stableKey()
                        + " semantic=" + anchor.term + ": " + anchor.setterConstants);
            }

            String confidence;
            if (inverse) confidence = "INJECTOR_MULTIPLIER_PROVEN";
            else if (!anchor.getters.isEmpty() && !anchor.setters.isEmpty()) confidence = "EXACT_GETTER_SETTER_BRIDGE";
            else if (!anchor.getters.isEmpty()) confidence = "EXACT_GETTER_BRIDGE";
            else confidence = "EXACT_SETTER_BRIDGE";

            fieldAnchors.add(new InjectedFieldAnchor(
                    anchor.field,
                    anchor.term,
                    List.copyOf(anchor.interfaces),
                    List.copyOf(anchor.getters),
                    List.copyOf(anchor.setters),
                    decoder,
                    encoder,
                    inverse,
                    confidence,
                    evidence));
        }

        fieldAnchors.sort(Comparator
                .comparing(InjectedFieldAnchor::field)
                .thenComparing(InjectedFieldAnchor::semanticTerm));
        methods.sort(Comparator
                .comparing(InjectedMethodAnchor::targetMethod)
                .thenComparing(InjectedMethodAnchor::semanticTerm));
        if (fieldAnchors.isEmpty()) warnings.add("no direct injected field anchors were derived");
        return new InjectedSemanticAnchorReport(corpus.sha256(), fieldAnchors, methods, warnings);
    }

    private static void addConstant(Set<Long> target, Long value) {
        if (value != null) target.add(value);
    }

    private static Long unique(Set<Long> values) {
        return values.size() == 1 ? values.iterator().next() : null;
    }

    private static boolean modularInverse(String descriptor, long decoder, long encoder) {
        if ("I".equals(descriptor)) {
            long product = ((long) (int) decoder) * ((long) (int) encoder);
            return (product & 0xffffffffL) == 1L;
        }
        if ("J".equals(descriptor)) {
            return decoder * encoder == 1L;
        }
        return false;
    }

    private record FieldTermKey(FieldId field, String term) { }

    private static final class MutableFieldAnchor {
        private final FieldId field;
        private final String term;
        private final Set<ClassId> interfaces = new TreeSet<>();
        private final Set<MethodId> getters = new TreeSet<>();
        private final Set<MethodId> setters = new TreeSet<>();
        private final Set<Long> getterConstants = new TreeSet<>();
        private final Set<Long> setterConstants = new TreeSet<>();

        private MutableFieldAnchor(FieldId field, String term) {
            this.field = field;
            this.term = term;
        }
    }
}
