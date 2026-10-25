import dev.rl.suite.model.JarArchive;
import dev.rl.suite.util.DecoderTable;
import java.nio.file.*;
import java.util.*;

/** Optional read-only cross-check against a caller-supplied rl-suite binary; applies no transforms. */
public class RlSuiteDecoderProbe {
    public static void main(String[] args) throws Exception {
        var archive = JarArchive.read(Path.of(args[0]));
        Map<String, Set<String>> descriptors = new HashMap<>();
        for (var unit : archive.getClasses()) {
            for (var field : unit.getNode().fields) {
                descriptors.computeIfAbsent(unit.getNode().name + "." + field.name, key -> new TreeSet<>()).add(field.desc);
            }
        }
        var rows = new ArrayList<String>();
        rows.add("field\tdescriptor\tmultiplier\tconfidence");
        var decoders = new TreeMap<>(DecoderTable.extract(archive));
        for (var entry : decoders.entrySet()) {
            var types = descriptors.getOrDefault(entry.getKey(), Set.of());
            String descriptor = types.size() == 1 ? types.iterator().next() : "AMBIGUOUS";
            rows.add(entry.getKey() + "\t" + descriptor + "\t" + entry.getValue().getMultiplier() + "\t" + entry.getValue().getConfidence());
        }
        Files.write(Path.of(args[1]), rows);
        System.out.println("rl-suite read-only decoder candidates: " + decoders.size());
    }
}
