package dev.neosback.deob.cli;

import java.util.concurrent.Callable;
import picocli.CommandLine.Command;

@Command(
    name = "deob-workbench",
    mixinStandardHelpOptions = true,
    version = "client-deob-workbench 0.1.0-SNAPSHOT",
    description = "Evidence-first OSRS client deobfuscation workbench"
)
public final class DeobCommand implements Callable<Integer> {
    @Override
    public Integer call() {
        System.out.println("Use --help to inspect available evidence commands.");
        return 0;
    }
}
