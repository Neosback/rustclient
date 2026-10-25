package dev.neosback.deob.corpus;

import dev.neosback.deob.identity.ClassId;
import dev.neosback.deob.util.Hashes;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Enumeration;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/** Read-only class corpus loaded from one exact jar artifact. */
public final class JarCorpus {
    private final Path source;
    private final String sha256;
    private final NavigableMap<ClassId, ClassArtifact> classes;

    private JarCorpus(Path source, String sha256, NavigableMap<ClassId, ClassArtifact> classes) {
        this.source = source.toAbsolutePath().normalize();
        this.sha256 = sha256;
        this.classes = Collections.unmodifiableNavigableMap(new TreeMap<>(classes));
    }

    public static JarCorpus load(Path source) throws IOException {
        NavigableMap<ClassId, ClassArtifact> classes = new TreeMap<>();
        try (JarFile jar = new JarFile(source.toFile())) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.isDirectory() || !entry.getName().endsWith(".class")) {
                    continue;
                }
                byte[] bytes;
                try (InputStream input = jar.getInputStream(entry)) {
                    bytes = input.readAllBytes();
                }
                ClassArtifact artifact = ClassArtifact.fromBytes(bytes);
                ClassArtifact previous = classes.putIfAbsent(artifact.id(), artifact);
                if (previous != null) {
                    throw new IOException("Duplicate class identity in jar: " + artifact.id());
                }
            }
        }
        return new JarCorpus(source, Hashes.sha256(source), classes);
    }

    public Path source() {
        return source;
    }

    public String sha256() {
        return sha256;
    }

    public NavigableMap<ClassId, ClassArtifact> classes() {
        return classes;
    }

    public ClassArtifact requireClass(ClassId id) {
        ClassArtifact artifact = classes.get(id);
        if (artifact == null) {
            throw new IllegalArgumentException("Class not found in corpus: " + id);
        }
        return artifact;
    }
}
