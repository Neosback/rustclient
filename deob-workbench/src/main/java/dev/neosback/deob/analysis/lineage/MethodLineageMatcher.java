package dev.neosback.deob.analysis.lineage;

import dev.neosback.deob.analysis.index.BytecodeIndexer;
import dev.neosback.deob.analysis.index.ClassIndex;
import dev.neosback.deob.analysis.index.CorpusIndex;
import dev.neosback.deob.analysis.index.MethodIndex;
import dev.neosback.deob.corpus.JarCorpus;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * Explainable cross-revision method matcher. It never writes mappings and never treats a score as
 * semantic proof; ambiguous candidates remain explicitly ambiguous.
 */
public final class MethodLineageMatcher {
    public MethodLineageReport match(JarCorpus source, JarCorpus target) {
        CorpusIndex sourceIndex = new BytecodeIndexer().index(source);
        CorpusIndex targetIndex = new BytecodeIndexer().index(target);
        List<IndexedMethod> sourceMethods = flatten(sourceIndex);
        List<IndexedMethod> targetMethods = flatten(targetIndex);
        List<MethodLineageDecision> decisions = new ArrayList<>();

        for (IndexedMethod sourceMethod : sourceMethods) {
            List<ScoredCandidate> candidates = new ArrayList<>();
            for (IndexedMethod targetMethod : targetMethods) {
                if (!compatible(sourceMethod, targetMethod)) {
                    continue;
                }
                candidates.add(score(sourceMethod, targetMethod));
            }
            candidates.sort(Comparator
                    .comparingInt(ScoredCandidate::score).reversed()
                    .thenComparing(candidate -> candidate.target().method().id()));

            if (candidates.isEmpty()) {
                decisions.add(new MethodLineageDecision(
                        sourceMethod.method().id(),
                        null,
                        0,
                        null,
                        LineageConfidence.UNMATCHED,
                        List.of()));
                continue;
            }

            ScoredCandidate best = candidates.get(0);
            Integer runnerUp = candidates.size() > 1 ? candidates.get(1).score() : null;
            decisions.add(new MethodLineageDecision(
                    sourceMethod.method().id(),
                    best.target().method().id(),
                    best.score(),
                    runnerUp,
                    confidence(best, runnerUp),
                    best.evidence()));
        }

        decisions.sort(Comparator.comparing(MethodLineageDecision::source));
        return new MethodLineageReport(
                source.sha256(),
                target.sha256(),
                sourceMethods.size(),
                targetMethods.size(),
                decisions);
    }

    private static boolean compatible(IndexedMethod source, IndexedMethod target) {
        if (!descriptorShape(source.method().id().descriptor())
                .equals(descriptorShape(target.method().id().descriptor()))) {
            return false;
        }
        if (isStatic(source.method().access()) != isStatic(target.method().access())) {
            return false;
        }
        if (constructorKind(source.method().id().name()) != constructorKind(target.method().id().name())) {
            return false;
        }
        return true;
    }

    private static ScoredCandidate score(IndexedMethod source, IndexedMethod target) {
        List<LineageEvidence> evidence = new ArrayList<>();
        int score = 0;

        boolean structuralExact = source.method().structuralSha256()
                .equals(target.method().structuralSha256());
        if (structuralExact) {
            score += add(evidence, "structural-fingerprint", 55, "rename-insensitive bytecode hash matches");
        }

        score += similarity(evidence, "instruction-count", 15,
                source.method().instructionCount(), target.method().instructionCount());
        score += similarity(evidence, "branch-count", 5,
                source.method().branchCount(), target.method().branchCount());
        score += similarity(evidence, "switch-count", 4,
                source.method().switchCount(), target.method().switchCount());
        score += similarity(evidence, "try-catch-count", 4,
                source.method().tryCatchCount(), target.method().tryCatchCount());
        score += similarity(evidence, "invoke-count", 5,
                source.method().invokeCount(), target.method().invokeCount());
        score += similarity(evidence, "field-access-count", 5,
                source.method().fieldAccessCount(), target.method().fieldAccessCount());
        score += classShape(evidence, source.owner(), target.owner());

        return new ScoredCandidate(target, Math.min(100, score), structuralExact, evidence);
    }

    private static int classShape(
            List<LineageEvidence> evidence,
            ClassIndex source,
            ClassIndex target) {
        int points = 0;
        if (source.fields().size() == target.fields().size()) {
            points += 2;
        }
        if (source.methods().size() == target.methods().size()) {
            points += 2;
        }
        if (source.interfaces().size() == target.interfaces().size()) {
            points += 1;
        }
        if (((source.access() & Opcodes.ACC_INTERFACE) != 0)
                == ((target.access() & Opcodes.ACC_INTERFACE) != 0)) {
            points += 1;
        }
        if ((source.superName() == null) == (target.superName() == null)) {
            points += 1;
        }
        if (points > 0) {
            add(evidence, "owner-class-shape", points,
                    "fields=" + source.fields().size() + "/" + target.fields().size()
                            + ", methods=" + source.methods().size() + "/" + target.methods().size()
                            + ", interfaces=" + source.interfaces().size() + "/" + target.interfaces().size());
        }
        return points;
    }

    private static int similarity(
            List<LineageEvidence> evidence,
            String feature,
            int maxPoints,
            int left,
            int right) {
        int max = Math.max(1, Math.max(left, right));
        double ratio = 1.0 - ((double) Math.abs(left - right) / max);
        int points = (int) Math.round(Math.max(0.0, ratio) * maxPoints);
        if (points > 0) {
            add(evidence, feature, points, left + " vs " + right);
        }
        return points;
    }

    private static LineageConfidence confidence(ScoredCandidate best, Integer runnerUp) {
        int gap = runnerUp == null ? 100 : best.score() - runnerUp;
        if (runnerUp != null && gap < 5) {
            return LineageConfidence.AMBIGUOUS;
        }
        if ((best.structuralExact() && gap >= 10) || (best.score() >= 85 && gap >= 12)) {
            return LineageConfidence.HIGH;
        }
        if (best.score() >= 70 && gap >= 8) {
            return LineageConfidence.MEDIUM;
        }
        return LineageConfidence.LOW;
    }

    private static int add(List<LineageEvidence> evidence, String feature, int points, String detail) {
        evidence.add(new LineageEvidence(feature, points, detail));
        return points;
    }

    private static List<IndexedMethod> flatten(CorpusIndex index) {
        List<IndexedMethod> methods = new ArrayList<>();
        for (ClassIndex owner : index.classes()) {
            for (MethodIndex method : owner.methods()) {
                methods.add(new IndexedMethod(owner, method));
            }
        }
        return methods;
    }

    private static boolean isStatic(int access) {
        return (access & Opcodes.ACC_STATIC) != 0;
    }

    private static int constructorKind(String name) {
        if ("<init>".equals(name)) {
            return 1;
        }
        if ("<clinit>".equals(name)) {
            return 2;
        }
        return 0;
    }

    private static String descriptorShape(String descriptor) {
        Type method = Type.getMethodType(descriptor);
        StringBuilder shape = new StringBuilder("(");
        for (Type argument : method.getArgumentTypes()) {
            shape.append(typeShape(argument));
        }
        return shape.append(')').append(typeShape(method.getReturnType())).toString();
    }

    private static String typeShape(Type type) {
        return switch (type.getSort()) {
            case Type.VOID -> "V";
            case Type.BOOLEAN -> "Z";
            case Type.CHAR -> "C";
            case Type.BYTE -> "B";
            case Type.SHORT -> "S";
            case Type.INT -> "I";
            case Type.FLOAT -> "F";
            case Type.LONG -> "J";
            case Type.DOUBLE -> "D";
            case Type.OBJECT -> "L*;";
            case Type.ARRAY -> "[".repeat(type.getDimensions()) + typeShape(type.getElementType());
            default -> "?";
        };
    }

    private record IndexedMethod(ClassIndex owner, MethodIndex method) {
    }

    private record ScoredCandidate(
            IndexedMethod target,
            int score,
            boolean structuralExact,
            List<LineageEvidence> evidence) {
        private ScoredCandidate {
            evidence = List.copyOf(evidence);
        }
    }
}
