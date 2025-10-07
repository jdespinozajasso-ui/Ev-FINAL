package com.citas.util;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class CsvUtil {
    public static List<String> readAllLines(Path path) throws IOException {
        if (!Files.exists(path)) return Collections.emptyList();
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }


    public static void writeAllLines(Path path, List<String> lines) throws IOException {
       Files.createDirectories(path.getParent());
       Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }


    public static void appendLine(Path path, String line) throws IOException {
        Files.createDirectories(path.getParent());
        Files.write(path, Arrays.asList(line), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }



    public static String[] parseCsvLine(String line) {
       if (line == null || line.isEmpty()) return new String[0];
       return line.split(",", -1);
    }
}