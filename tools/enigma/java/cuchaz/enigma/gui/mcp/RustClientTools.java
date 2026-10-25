package cuchaz.enigma.gui.mcp;

import com.google.gson.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.*;
import javax.swing.SwingUtilities;
import cuchaz.enigma.EnigmaProject;
import cuchaz.enigma.analysis.EntryReference;
import cuchaz.enigma.gui.Gui;
import cuchaz.enigma.translation.mapping.EntryChange;
import cuchaz.enigma.translation.representation.entry.*;
import cuchaz.enigma.utils.validation.ValidationContext;

/** Revision corpus identity, bounded indexes and non-mutating rename checks. */
public final class RustClientTools {
    public static void guard(Gui gui) {
        String expected = System.getProperty("rustclient.enigma.input");
        if (expected == null) return;
        EnigmaProject project = gui.getController().getProject();
        if (project == null || project.getJarPaths().size() != 1
                || !project.getJarPaths().get(0).toAbsolutePath().normalize().equals(Path.of(expected)))
            throw new McpException("This MCP session requires its prepared RustClient analysis corpus");
        try {
            String sha = HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(Path.of(expected))));
            if (!sha.equals(System.getProperty("rustclient.enigma.analysisSha256")))
                throw new McpException("The prepared analysis JAR changed");
        } catch (McpException error) { throw error; }
        catch (Exception error) { throw new McpException("Cannot verify analysis corpus: " + error); }
    }

    public static int integer(JsonObject args, String name, int fallback, int maximum) {
        if (!args.has(name)) return fallback;
        try {
            int value = args.get(name).getAsBigDecimal().intValueExact();
            if (value < 0 || value > maximum) throw new IllegalArgumentException();
            return value;
        } catch (RuntimeException error) { throw new McpException(name + " must be an integer between 0 and " + maximum); }
    }

    public static <T> List<T> page(List<T> values, JsonObject args) {
        int start = Math.min(integer(args, "offset", 0, Integer.MAX_VALUE), values.size());
        return values.subList(start, Math.min(values.size(), start + integer(args, "limit", 100, 500)));
    }

    private static Map<String, Object> entry(EnigmaProject project, Entry<?> entry) {
        Map<String, Object> value = new LinkedHashMap<>();
        value.put("original", entry.toString());
        value.put("mapped", project.getMapper().deobfuscate(entry).toString());
        value.put("inCorpus", project.getJarIndex().getEntryIndex().hasEntry(entry));
        return value;
    }

    public static Object status(Gui gui, JsonObject args) {
        EnigmaProject project = gui.getController().getProject();
        var index = project.getJarIndex().getEntryIndex();
        return Map.of("classes", index.getClasses().size(), "fields", index.getFields().size(), "methods", index.getMethods().size(),
            "draftOnly", true, "fork", "Fabric Enigma 4.0.2 + supplied Enigma-MCP fork",
            "analysisNote", "Enigma indexes may add analysis frames; original Code remains in the pinned corpus. External RuneLite API libraries are not loaded.");
    }

    public static Object hierarchy(Gui gui, JsonObject args) {
        var project = gui.getController().getProject();
        ClassEntry cls = McpEntryRef.resolveClass(project, args.get("class").getAsString());
        var index = project.getJarIndex().getInheritanceIndex();
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("class", entry(project, cls));
        Map<String, Collection<ClassEntry>> groups = Map.of("parents", index.getParents(cls), "children", index.getChildren(cls),
            "ancestors", index.getAncestors(cls), "descendants", index.getDescendants(cls));
        groups.forEach((name, values) -> {
            var sorted = values.stream().sorted(Comparator.comparing(Object::toString)).toList();
            result.put(name + "Count", sorted.size());
            result.put(name, page(sorted, args).stream().map(e -> entry(project, e)).toList());
        });
        return result;
    }

    public static Object family(Gui gui, JsonObject args) {
        var project = gui.getController().getProject();
        JsonObject target = args.deepCopy(); target.addProperty("kind", "method");
        Entry<?> method = McpEntryRef.resolveTarget(project, target);
        var entries = project.getJarIndex().getEntryResolver().resolveEquivalentEntries(method).stream()
            .sorted(Comparator.comparing(Object::toString)).toList();
        return Map.of("total", entries.size(), "entries", page(entries, args).stream().map(e -> entry(project, e)).toList());
    }

    public static Object references(Gui gui, JsonObject args) {
        var project = gui.getController().getProject();
        Entry<?> target = McpEntryRef.resolveTarget(project, args);
        var index = project.getJarIndex().getReferenceIndex();
        List<EntryReference<?, ?>> references = new ArrayList<>();
        if (target instanceof ClassEntry cls) {
            references.addAll(index.getReferencesToClass(cls));
            references.addAll(index.getFieldTypeReferencesToClass(cls));
            references.addAll(index.getMethodTypeReferencesToClass(cls));
        } else if (target instanceof MethodEntry method) references.addAll(index.getReferencesToMethod(method));
        else if (target instanceof FieldEntry field) references.addAll(index.getReferencesToField(field));
        else throw new McpException("References require a class, field or method");
        var sorted = references.stream().map(ref -> Map.of("target", entry(project, ref.entry),
            "context", ref.context == null ? "" : ref.context.toString())).distinct()
            .sorted(Comparator.comparing(Object::toString)).toList();
        return Map.of("total", sorted.size(), "references", page(sorted, args));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Object validate(Gui gui, JsonObject args) {
        var project = gui.getController().getProject();
        Entry target = McpEntryRef.resolveTarget(project, args);
        if (target instanceof MethodEntry method && method.isConstructor())
            throw new McpException("Constructors cannot be renamed");
        if (!project.isRenamable(target)) throw new McpException("Entry is not renamable");
        String name = args.get("newName").getAsString();
        ValidationContext validation = new ValidationContext();
        Runnable check = () -> gui.getController().validateChange(validation, EntryChange.modify(target).withDeobfName(name));
        try { if (SwingUtilities.isEventDispatchThread()) check.run(); else SwingUtilities.invokeAndWait(check); }
        catch (Exception error) { throw new McpException("Validation failed: " + error); }
        return Map.of("canProceed", validation.canProceed(), "messages", validation.getMessages().stream().map(m -> m.getText()).toList(),
            "family", project.getJarIndex().getEntryResolver().resolveEquivalentEntries(target).stream().map(Object::toString).sorted().toList(),
            "note", "Enigma structural check only; evidence ledger and rename roundtrip still required before promotion");
    }
}
