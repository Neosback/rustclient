package dev.neosback.deob.analysis.hierarchy;

import dev.neosback.deob.identity.MethodId;
import java.util.List;

/** One override-equivalent method family inside the loaded corpus. */
public record VirtualMethodFamily(String familyKey, List<MethodId> members) {
    public VirtualMethodFamily {
        members = List.copyOf(members);
        if (members.isEmpty()) {
            throw new IllegalArgumentException("Virtual method family cannot be empty");
        }
    }
}
