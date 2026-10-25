package dev.neosback.deob;

import dev.neosback.deob.cli.DeobCommand;
import picocli.CommandLine;

/** Standalone entry point for the evidence workbench. */
public final class Main {
    private Main() {}

    public static void main(String[] args) {
        int exitCode = new CommandLine(new DeobCommand()).execute(args);
        if (exitCode != 0) {
            System.exit(exitCode);
        }
    }
}
