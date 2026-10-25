package dev.neosback.deob.cli;

import dev.neosback.deob.io.JsonSupport;
import dev.neosback.deob.revision.SourceManifest;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "sources", description = "Inspect the revision evidence-source registry")
public final class SourcesCommand implements Callable<Integer> {
    @Option(
        names = "--manifest",
        defaultValue = "config/rev240-sources.json",
        description = "Source manifest path"
    )
    private Path manifest;

    @Option(names = "--out", description = "Optional JSON output path; stdout when omitted")
    private Path out;

    @Override
    public Integer call() throws Exception {
        SourceManifest sources = JsonSupport.read(manifest, SourceManifest.class);
        if (out == null) {
            System.out.println(JsonSupport.pretty(sources));
        } else {
            JsonSupport.write(out, sources);
        }
        return 0;
    }
}
