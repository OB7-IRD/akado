package fr.ird.akado.observe.result;

import io.ultreia.java4all.util.SortedProperties;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Created on 11/05/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 3.0.0
 */
public class LogSplitter {

    public static void main(String[] args) throws IOException {

    }

    private static void runForFile(Path source) throws IOException {
        Path sourcePath = source.resolve( "akado.log");
        Path targetPath = sourcePath.getParent().resolve("byCodes");
        if (Files.notExists(targetPath)) {
            Files.createDirectory(targetPath);
        }

        List<String> sourceLines = Files.readAllLines(sourcePath);
        Map<String, List<String>> splitByCode = splitByCode(sourceLines);
        SortedProperties byCodesProperties = new SortedProperties();
        for (Map.Entry<String, List<String>> entry : splitByCode.entrySet()) {
            write(entry.getKey(), entry.getValue(), targetPath);
            byCodesProperties.setProperty(entry.getKey(), String.valueOf(entry.getValue().size()));
        }
        try (BufferedWriter writer = Files.newBufferedWriter(targetPath.resolve("count.properties"))) {
            byCodesProperties.store(writer, null);
        }

        split("> Marée", sourceLines, source.resolve( "akado-M.log"));
        split("> Activité", sourceLines, source.resolve( "akado-A.log"));
        split("> Echantillon", sourceLines, source.resolve( "akado-E.log"));
    }

    private static void split(String pattern, List<String> sourceLines, Path target) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(target)) {
            for (String line : sourceLines) {
                if (line.contains(pattern)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }
    }

    private static void write(String pattern, List<String> sourceLines, Path targetDirectory) throws IOException {
        Path target = targetDirectory.resolve(pattern+".log");
        Files.write(target, sourceLines);
    }

    private static Map<String, List<String>> splitByCode(List<String> sourceLines) {
        Map<String, List<String>> result = new TreeMap<>();
        for (String line : sourceLines) {
            int indexOf = line.indexOf(">");
            if (indexOf==-1) {
                continue;
            }
            String code = line.substring(0, indexOf);
            List<String> values = result.computeIfAbsent(code, e -> new ArrayList<>());
            values.add(line);
        }
        return result;
    }
}
