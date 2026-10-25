package dev.neosback.deob.analysis.decoder;

import dev.neosback.deob.analysis.provenance.OriginKind;
import dev.neosback.deob.analysis.provenance.ProvenanceStep;
import dev.neosback.deob.analysis.provenance.ProvenanceTrace;
import dev.neosback.deob.analysis.provenance.ValueOrigin;
import dev.neosback.deob.analysis.provenance.ValueProvenanceAnalyzer;
import dev.neosback.deob.bytecode.InstructionIndex;
import dev.neosback.deob.corpus.ClassArtifact;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.identity.ClassId;
import dev.neosback.deob.identity.FieldId;
import dev.neosback.deob.identity.InstructionId;
import dev.neosback.deob.identity.MethodId;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.AnalyzerException;

/**
 * Discovers integer/long multiplier evidence from exact bytecode provenance. A site is a candidate;
 * only a same-field read/write pair whose multipliers are inverses modulo 2^N is reported as proven.
 */
public final class DecoderEvidenceAnalyzer {
    private static final int MAX_PROVENANCE_DEPTH = 64;

    public DecoderEvidenceReport analyze(JarCorpus corpus) {
        List<MultiplierSite> candidates = new ArrayList<>();
        List<String> warnings = new ArrayList<>();
        ValueProvenanceAnalyzer provenanceAnalyzer = new ValueProvenanceAnalyzer();

        for (ClassArtifact artifact : corpus.classes().values()) {
            ClassNode classNode = artifact.readNode();
            ClassId owner = new ClassId(classNode.name);
            for (MethodNode method : classNode.methods) {
                if (method.instructions == null || method.instructions.size() == 0) {
                    continue;
                }
                MethodId methodId = new MethodId(owner, method.name, method.desc);
                try {
                    candidates.addAll(analyzeMethod(methodId, method, provenanceAnalyzer));
                } catch (AnalyzerException | RuntimeException failure) {
                    warnings.add(methodId + ": " + failure.getClass().getSimpleName()
                            + ": " + String.valueOf(failure.getMessage()));
                }
            }
        }

        List<DecoderPair> pairs = provePairs(candidates);
        List<MultiplierSite> sites = promoteProvenSites(candidates, pairs);
        sites.sort(DecoderEvidenceAnalyzer::compareSites);
        pairs.sort((left, right) -> {
            int field = left.field().compareTo(right.field());
            if (field != 0) {
                return field;
            }
            int bits = Integer.compare(left.bits(), right.bits());
            if (bits != 0) {
                return bits;
            }
            int read = Long.compareUnsigned(left.readMultiplier(), right.readMultiplier());
            return read != 0 ? read : Long.compareUnsigned(left.writeMultiplier(), right.writeMultiplier());
        });
        warnings.sort(String::compareTo);
        return new DecoderEvidenceReport(sites, pairs, warnings);
    }

    private static List<MultiplierSite> analyzeMethod(
            MethodId methodId,
            MethodNode method,
            ValueProvenanceAnalyzer provenanceAnalyzer) throws AnalyzerException {
        ProvenanceTrace trace = provenanceAnalyzer.analyze(methodId, method);
        InstructionIndex instructions = InstructionIndex.of(method);
        Map<String, ProvenanceStep> steps = new HashMap<>();
        Map<String, AbstractInsnNode> nodes = new HashMap<>();
        for (int i = 0; i < instructions.size(); i++) {
            String key = new InstructionId(methodId, i).toString();
            steps.put(key, trace.steps().get(i));
            nodes.put(key, instructions.instruction(i));
        }

        List<MultiplierSite> result = new ArrayList<>();
        for (int i = 0; i < instructions.size(); i++) {
            AbstractInsnNode node = instructions.instruction(i);
            int bits = multiplyWidth(node.getOpcode());
            if (bits == 0) {
                continue;
            }
            ProvenanceStep multiply = trace.steps().get(i);
            Set<Long> constants = collectConstants(
                    multiply.inputs(), steps, nodes, bits, new HashSet<>(), 0);
            Set<InstructionRef> fieldReads = collectFieldReads(
                    multiply.inputs(), steps, nodes, new HashSet<>(), 0);
            if (constants.size() != 1 || fieldReads.size() != 1) {
                continue;
            }
            long multiplier = constants.iterator().next();
            InstructionRef fieldRead = fieldReads.iterator().next();
            FieldId field = fieldId(fieldRead.node());
            if (!fieldWidthMatches(field, bits)) {
                continue;
            }
            result.add(new MultiplierSite(
                    field,
                    DecoderDirection.READ_TRANSFORM,
                    bits,
                    multiplier,
                    new InstructionId(methodId, i),
                    new InstructionId(methodId, fieldRead.opcodeIndex()),
                    DecoderConfidence.CANDIDATE));
        }

        for (int i = 0; i < instructions.size(); i++) {
            AbstractInsnNode node = instructions.instruction(i);
            if (!(node instanceof FieldInsnNode fieldInsn)
                    || (node.getOpcode() != Opcodes.PUTFIELD && node.getOpcode() != Opcodes.PUTSTATIC)) {
                continue;
            }
            FieldId field = fieldId(fieldInsn);
            int bits = fieldBits(field.descriptor());
            if (bits == 0) {
                continue;
            }
            ProvenanceStep write = trace.steps().get(i);
            Set<String> multiplies = collectMultiplyRoots(
                    write.inputs(), steps, nodes, bits, new HashSet<>(), 0);
            if (multiplies.size() != 1) {
                continue;
            }
            String multiplyKey = multiplies.iterator().next();
            ProvenanceStep multiply = steps.get(multiplyKey);
            Set<Long> constants = collectConstants(
                    multiply.inputs(), steps, nodes, bits, new HashSet<>(), 0);
            if (constants.size() != 1) {
                continue;
            }
            int multiplyIndex = parseOpcodeIndex(multiplyKey);
            result.add(new MultiplierSite(
                    field,
                    DecoderDirection.WRITE_TRANSFORM,
                    bits,
                    constants.iterator().next(),
                    new InstructionId(methodId, multiplyIndex),
                    new InstructionId(methodId, i),
                    DecoderConfidence.CANDIDATE));
        }

        return result;
    }

    private static Set<Long> collectConstants(
            List<ValueOrigin> origins,
            Map<String, ProvenanceStep> steps,
            Map<String, AbstractInsnNode> nodes,
            int bits,
            Set<String> visited,
            int depth) {
        Set<Long> result = new LinkedHashSet<>();
        if (depth > MAX_PROVENANCE_DEPTH) {
            return result;
        }
        for (ValueOrigin origin : origins) {
            if (origin.kind() != OriginKind.INSTRUCTION || !visited.add(origin.key())) {
                continue;
            }
            AbstractInsnNode node = nodes.get(origin.key());
            Long constant = constantValue(node, bits);
            if (constant != null) {
                result.add(constant);
                continue;
            }
            ProvenanceStep step = steps.get(origin.key());
            if (step != null) {
                result.addAll(collectConstants(
                        step.inputs(), steps, nodes, bits, visited, depth + 1));
            }
        }
        return result;
    }

    private static Set<InstructionRef> collectFieldReads(
            List<ValueOrigin> origins,
            Map<String, ProvenanceStep> steps,
            Map<String, AbstractInsnNode> nodes,
            Set<String> visited,
            int depth) {
        Set<InstructionRef> result = new TreeSet<>();
        if (depth > MAX_PROVENANCE_DEPTH) {
            return result;
        }
        for (ValueOrigin origin : origins) {
            if (origin.kind() != OriginKind.INSTRUCTION || !visited.add(origin.key())) {
                continue;
            }
            AbstractInsnNode node = nodes.get(origin.key());
            if (node instanceof FieldInsnNode
                    && (node.getOpcode() == Opcodes.GETFIELD || node.getOpcode() == Opcodes.GETSTATIC)) {
                result.add(new InstructionRef(parseOpcodeIndex(origin.key()), (FieldInsnNode) node));
                continue;
            }
            ProvenanceStep step = steps.get(origin.key());
            if (step != null) {
                result.addAll(collectFieldReads(
                        step.inputs(), steps, nodes, visited, depth + 1));
            }
        }
        return result;
    }

    private static Set<String> collectMultiplyRoots(
            List<ValueOrigin> origins,
            Map<String, ProvenanceStep> steps,
            Map<String, AbstractInsnNode> nodes,
            int bits,
            Set<String> visited,
            int depth) {
        Set<String> result = new LinkedHashSet<>();
        if (depth > MAX_PROVENANCE_DEPTH) {
            return result;
        }
        int wantedOpcode = bits == 32 ? Opcodes.IMUL : Opcodes.LMUL;
        for (ValueOrigin origin : origins) {
            if (origin.kind() != OriginKind.INSTRUCTION || !visited.add(origin.key())) {
                continue;
            }
            AbstractInsnNode node = nodes.get(origin.key());
            if (node != null && node.getOpcode() == wantedOpcode) {
                result.add(origin.key());
                continue;
            }
            ProvenanceStep step = steps.get(origin.key());
            if (step != null) {
                result.addAll(collectMultiplyRoots(
                        step.inputs(), steps, nodes, bits, visited, depth + 1));
            }
        }
        return result;
    }

    private static Long constantValue(AbstractInsnNode node, int bits) {
        if (node == null) {
            return null;
        }
        int opcode = node.getOpcode();
        if (node instanceof IntInsnNode intInsn
                && (opcode == Opcodes.BIPUSH || opcode == Opcodes.SIPUSH)) {
            return bits == 32 ? (long) intInsn.operand : null;
        }
        if (node instanceof LdcInsnNode ldc) {
            if (bits == 32 && ldc.cst instanceof Integer integer) {
                return integer.longValue();
            }
            if (bits == 64 && ldc.cst instanceof Long value) {
                return value;
            }
            return null;
        }
        if (!(node instanceof InsnNode)) {
            return null;
        }
        if (bits == 32) {
            return switch (opcode) {
                case Opcodes.ICONST_M1 -> -1L;
                case Opcodes.ICONST_0 -> 0L;
                case Opcodes.ICONST_1 -> 1L;
                case Opcodes.ICONST_2 -> 2L;
                case Opcodes.ICONST_3 -> 3L;
                case Opcodes.ICONST_4 -> 4L;
                case Opcodes.ICONST_5 -> 5L;
                default -> null;
            };
        }
        if (bits == 64) {
            return switch (opcode) {
                case Opcodes.LCONST_0 -> 0L;
                case Opcodes.LCONST_1 -> 1L;
                default -> null;
            };
        }
        return null;
    }

    private static List<DecoderPair> provePairs(List<MultiplierSite> sites) {
        List<DecoderPair> result = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        for (MultiplierSite read : sites) {
            if (read.direction() != DecoderDirection.READ_TRANSFORM) {
                continue;
            }
            for (MultiplierSite write : sites) {
                if (write.direction() != DecoderDirection.WRITE_TRANSFORM
                        || !read.field().equals(write.field())
                        || read.bits() != write.bits()) {
                    continue;
                }
                if (!areInverse(read.multiplier(), write.multiplier(), read.bits())) {
                    continue;
                }
                String key = read.field().stableKey() + ":" + read.bits() + ":"
                        + unsigned(read.multiplier(), read.bits()) + ":"
                        + unsigned(write.multiplier(), write.bits());
                if (seen.add(key)) {
                    result.add(new DecoderPair(
                            read.field(),
                            read.bits(),
                            read.multiplier(),
                            write.multiplier(),
                            unsigned(read.multiplier(), read.bits()),
                            unsigned(write.multiplier(), write.bits())));
                }
            }
        }
        return result;
    }

    private static List<MultiplierSite> promoteProvenSites(
            List<MultiplierSite> candidates,
            List<DecoderPair> pairs) {
        Set<String> proven = new HashSet<>();
        for (DecoderPair pair : pairs) {
            proven.add(siteProofKey(
                    pair.field(), DecoderDirection.READ_TRANSFORM, pair.bits(), pair.readMultiplier()));
            proven.add(siteProofKey(
                    pair.field(), DecoderDirection.WRITE_TRANSFORM, pair.bits(), pair.writeMultiplier()));
        }
        List<MultiplierSite> result = new ArrayList<>();
        for (MultiplierSite site : candidates) {
            DecoderConfidence confidence = proven.contains(siteProofKey(
                    site.field(), site.direction(), site.bits(), site.multiplier()))
                    ? DecoderConfidence.PROVEN_INVERSE_PAIR
                    : DecoderConfidence.CANDIDATE;
            result.add(new MultiplierSite(
                    site.field(),
                    site.direction(),
                    site.bits(),
                    site.multiplier(),
                    site.multiplyInstruction(),
                    site.fieldInstruction(),
                    confidence));
        }
        return result;
    }

    private static String siteProofKey(
            FieldId field,
            DecoderDirection direction,
            int bits,
            long multiplier) {
        return field.stableKey() + ':' + direction + ':' + bits + ':' + unsigned(multiplier, bits);
    }

    private static boolean areInverse(long left, long right, int bits) {
        BigInteger modulus = BigInteger.ONE.shiftLeft(bits);
        BigInteger product = normalize(left, bits).multiply(normalize(right, bits)).mod(modulus);
        return BigInteger.ONE.equals(product);
    }

    private static String unsigned(long value, int bits) {
        return normalize(value, bits).toString();
    }

    private static BigInteger normalize(long value, int bits) {
        return BigInteger.valueOf(value).mod(BigInteger.ONE.shiftLeft(bits));
    }

    private static FieldId fieldId(FieldInsnNode field) {
        return new FieldId(new ClassId(field.owner), field.name, field.desc);
    }

    private static int fieldBits(String descriptor) {
        return switch (descriptor) {
            case "I" -> 32;
            case "J" -> 64;
            default -> 0;
        };
    }

    private static boolean fieldWidthMatches(FieldId field, int bits) {
        return fieldBits(field.descriptor()) == bits;
    }

    private static int multiplyWidth(int opcode) {
        return switch (opcode) {
            case Opcodes.IMUL -> 32;
            case Opcodes.LMUL -> 64;
            default -> 0;
        };
    }

    private static int parseOpcodeIndex(String instructionKey) {
        int separator = instructionKey.lastIndexOf('#');
        if (separator < 0 || separator + 1 >= instructionKey.length()) {
            throw new IllegalArgumentException("Not an instruction key: " + instructionKey);
        }
        return Integer.parseInt(instructionKey.substring(separator + 1));
    }

    private static int compareSites(MultiplierSite left, MultiplierSite right) {
        int field = left.field().compareTo(right.field());
        if (field != 0) {
            return field;
        }
        int direction = left.direction().compareTo(right.direction());
        if (direction != 0) {
            return direction;
        }
        int multiply = left.multiplyInstruction().toString().compareTo(right.multiplyInstruction().toString());
        if (multiply != 0) {
            return multiply;
        }
        return left.fieldInstruction().toString().compareTo(right.fieldInstruction().toString());
    }

    private record InstructionRef(int opcodeIndex, FieldInsnNode node)
            implements Comparable<InstructionRef> {
        @Override
        public int compareTo(InstructionRef other) {
            return Integer.compare(opcodeIndex, other.opcodeIndex);
        }
    }
}
