package dev.neosback.deob.analysis.protocol;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Parser for the rev-240 packet TSV shape published by rl-suite. */
public final class PacketTableParser {
    public PacketTable parse(Path path, PacketDirection direction) throws IOException {
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        String revision = "";
        int count = -1;
        int named = -1;
        int tableSize = -1;
        List<String> columns = List.of();
        List<PacketTableEntry> entries = new ArrayList<>();

        for (String raw : lines) {
            String line = raw.strip();
            if (line.isEmpty()) {
                continue;
            }
            if (line.startsWith("#")) {
                String comment = line.substring(1).strip();
                if (comment.startsWith("Server packets:") || comment.startsWith("Client packets:")) {
                    revision = afterColon(comment);
                } else if (comment.startsWith("count:")) {
                    count = parseMetadataInt(comment);
                } else if (comment.startsWith("named:")) {
                    named = parseMetadataInt(comment);
                } else if (comment.startsWith("server_table_size:") || comment.startsWith("client_table_size:")) {
                    tableSize = parseMetadataInt(comment);
                } else if (comment.startsWith("columns:")) {
                    String value = afterColon(comment);
                    columns = List.copyOf(Arrays.asList(value.split("\\t")));
                }
                continue;
            }

            String[] fields = line.split("\\t", -1);
            if (fields.length != 5) {
                throw new IllegalArgumentException(
                        "Expected 5 tab-separated packet columns at " + path + ": " + line);
            }
            int id = Integer.parseInt(fields[0]);
            String name = fields[1];
            int length = Integer.parseInt(fields[2]);
            int ioCount = Integer.parseInt(fields[3]);
            List<String> operations = "-".equals(fields[4]) || fields[4].isBlank()
                    ? List.of()
                    : splitOperations(fields[4]);
            entries.add(new PacketTableEntry(id, name, length, ioCount, operations));
        }

        if (count >= 0 && count != entries.size()) {
            throw new IllegalArgumentException(
                    "Packet table count mismatch for " + path + ": declared=" + count
                            + ", parsed=" + entries.size());
        }
        long parsedNamed = entries.stream().filter(PacketTableEntry::named).count();
        if (named >= 0 && named != parsedNamed) {
            throw new IllegalArgumentException(
                    "Packet table named-count mismatch for " + path + ": declared=" + named
                            + ", parsed=" + parsedNamed);
        }
        if (tableSize >= 0) {
            int maxId = entries.stream().mapToInt(PacketTableEntry::id).max().orElse(-1);
            if (maxId >= tableSize) {
                throw new IllegalArgumentException(
                        "Packet id exceeds declared table size for " + path + ": max=" + maxId
                                + ", size=" + tableSize);
            }
        }

        return new PacketTable(
                direction,
                revision,
                count,
                named,
                tableSize,
                columns,
                entries,
                path.toAbsolutePath().normalize().toString());
    }

    /** Splits commas only at top level; client write encodings contain commas inside brackets. */
    static List<String> splitOperations(String value) {
        List<String> result = new ArrayList<>();
        int squareDepth = 0;
        int parenDepth = 0;
        int start = 0;
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (ch == '[') {
                squareDepth++;
            } else if (ch == ']') {
                squareDepth--;
            } else if (ch == '(') {
                parenDepth++;
            } else if (ch == ')') {
                parenDepth--;
            } else if (ch == ',' && squareDepth == 0 && parenDepth == 0) {
                result.add(value.substring(start, i).strip());
                start = i + 1;
            }
            if (squareDepth < 0 || parenDepth < 0) {
                throw new IllegalArgumentException("Malformed packet operation list: " + value);
            }
        }
        if (squareDepth != 0 || parenDepth != 0) {
            throw new IllegalArgumentException("Malformed packet operation list: " + value);
        }
        result.add(value.substring(start).strip());
        return List.copyOf(result);
    }

    private static int parseMetadataInt(String comment) {
        return Integer.parseInt(afterColon(comment));
    }

    private static String afterColon(String value) {
        int colon = value.indexOf(':');
        if (colon < 0 || colon + 1 >= value.length()) {
            return "";
        }
        return value.substring(colon + 1).strip();
    }
}
