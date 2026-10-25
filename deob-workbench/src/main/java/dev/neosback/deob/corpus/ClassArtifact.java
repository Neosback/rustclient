package dev.neosback.deob.corpus;

import dev.neosback.deob.identity.ClassId;
import dev.neosback.deob.util.Hashes;
import java.util.Arrays;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

/** Immutable classfile bytes. Callers receive a fresh ASM tree for each analysis pass. */
public final class ClassArtifact {
    private final ClassId id;
    private final byte[] bytes;
    private final String sha256;

    private ClassArtifact(ClassId id, byte[] bytes) {
        this.id = id;
        this.bytes = Arrays.copyOf(bytes, bytes.length);
        this.sha256 = Hashes.sha256(bytes);
    }

    public static ClassArtifact fromBytes(byte[] bytes) {
        ClassReader reader = new ClassReader(bytes);
        return new ClassArtifact(new ClassId(reader.getClassName()), bytes);
    }

    public ClassId id() {
        return id;
    }

    public String sha256() {
        return sha256;
    }

    public byte[] bytes() {
        return Arrays.copyOf(bytes, bytes.length);
    }

    public ClassNode readNode() {
        ClassNode node = new ClassNode();
        new ClassReader(bytes).accept(node, 0);
        return node;
    }
}
