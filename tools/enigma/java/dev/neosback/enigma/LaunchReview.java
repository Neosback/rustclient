package dev.neosback.enigma;

import java.nio.file.Path;
import java.util.EnumSet;
import java.util.List;
import javax.swing.SwingUtilities;
import cuchaz.enigma.Enigma;
import cuchaz.enigma.gui.EditableType;
import cuchaz.enigma.gui.Gui;
import cuchaz.enigma.gui.config.McpConfig;
import cuchaz.enigma.gui.config.Themes;
import cuchaz.enigma.translation.mapping.serde.MappingFormat;

/** Launch the existing MCP fork with a dedicated input, draft mappings and configuration. */
public final class LaunchReview {
    public static void main(String[] args) throws Exception {
        Path input = Path.of(args[0]), mappings = Path.of(args[1]);
        int port = Integer.parseInt(args[2]);
        SwingUtilities.invokeAndWait(() -> {
            try {
                McpConfig.setAutoStart(false);
                McpConfig.setBindAll(false);
                McpConfig.setPort(port);
                Themes.setupTheme();
                Gui gui = new Gui(Enigma.create(), EnumSet.allOf(EditableType.class));
                gui.getFrame().setTitle("RustClient — Enigma Mapping Review");
                gui.getController().openJar(List.of(input), List.of()).whenComplete((unused, failure) -> {
                    if (failure != null) { failure.printStackTrace(); System.exit(1); }
                    gui.getController().openMappings(MappingFormat.TINY_V2, mappings).whenComplete((ignored, mappingFailure) -> {
                        if (mappingFailure != null) { mappingFailure.printStackTrace(); System.exit(1); }
                        SwingUtilities.invokeLater(() -> {
                            try {
                                gui.mappingsFileChooser.setSelectedFile(mappings.toFile());
                                gui.getController().startMcpServer(port, false);
                                System.out.println("RUSTCLIENT_ENIGMA_READY " + port);
                            } catch (Exception error) { error.printStackTrace(); System.exit(1); }
                        });
                    });
                });
            } catch (Exception error) { error.printStackTrace(); System.exit(1); }
        });
    }
}
