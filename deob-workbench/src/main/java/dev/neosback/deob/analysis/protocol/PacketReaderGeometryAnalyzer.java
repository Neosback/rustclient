package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.corpus.ClassArtifact;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.identity.ClassId;
import dev.neosback.deob.identity.MethodId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;

/** Finds the large boolean-method exception-table geometry used by rev-240 packet readers. */
public final class PacketReaderGeometryAnalyzer {
    public PacketReaderGeometryReport analyze(JarCorpus corpus, int expectedReaderCount) {
        Map<Integer, List<MethodId>> buckets = new TreeMap<>();
        Map<MethodId, MethodNode> methods = new LinkedHashMap<>();
        for (ClassArtifact artifact : corpus.classes().values()) {
            ClassNode node = artifact.readNode();
            for (MethodNode method : node.methods) {
                if (!method.desc.endsWith(")Z")) continue;
                MethodId id = new MethodId(new ClassId(node.name), method.name, method.desc);
                methods.put(id, method);
                buckets.computeIfAbsent(method.tryCatchBlocks.size(), ignored -> new ArrayList<>()).add(id);
            }
        }

        Integer selected = buckets.entrySet().stream()
            .filter(e -> e.getKey() > 0 && (e.getKey() & 1) == 0)
            .filter(e -> e.getValue().size() == expectedReaderCount)
            .map(Map.Entry::getKey)
            .max(Integer::compareTo)
            .orElse(null);

        List<String> warnings = new ArrayList<>();
        if (selected == null) {
            warnings.add("No even try/catch bucket contains exactly " + expectedReaderCount + " boolean methods; do not guess packet readers.");
        }

        List<PacketReaderGeometryCandidate> candidates = new ArrayList<>();
        if (selected != null) {
            for (MethodId id : buckets.get(selected)) {
                MethodNode method = methods.get(id);
                int instructions = 0;
                int branches = 0;
                for (AbstractInsnNode insn : method.instructions) {
                    if (insn.getOpcode() >= 0) instructions++;
                    if (insn instanceof JumpInsnNode || insn instanceof LookupSwitchInsnNode || insn instanceof TableSwitchInsnNode) branches++;
                }
                List<String> reasons = List.of(
                    "boolean return descriptor",
                    "member of exact-size try/catch geometry bucket",
                    "large even exception-table geometry=" + selected
                );
                candidates.add(new PacketReaderGeometryCandidate(id, selected, instructions, branches, 100, reasons));
            }
        }
        candidates.sort(Comparator.comparing(c -> c.method().stableKey()));

        Map<Integer, Integer> histogram = new TreeMap<>();
        buckets.forEach((count, ids) -> histogram.put(count, ids.size()));
        return new PacketReaderGeometryReport(corpus.sha256(), expectedReaderCount, selected, histogram, candidates, warnings);
    }
}
