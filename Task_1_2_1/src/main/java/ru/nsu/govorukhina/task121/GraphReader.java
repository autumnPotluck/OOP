package ru.nsu.govorukhina.task121;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

/**
 * Reads a graph from a text file.
 * Blank lines and lines starting with # are ignored.
 * Every line is one of,
 * V name - declares a vertex.
 * E from to - declares a directed edge, missing endpoints are added.
 * Commands are case-insensitive. Extra whitespace between tokens is allowed.
 */
public final class GraphReader {

    private GraphReader() {
    }

    /**
     * Reads a graph from the given file into a new AdjacencyListGraph.
     *
     * @param path the file to read
     * @return the graph described by the file
     * @throws IOException if the file cannot be read
     * @throws IllegalArgumentException if the file is malformed
     */
    public static Graph read(Path path) throws IOException {
        return read(path, AdjacencyListGraph::new);
    }

    /**
     * Reads a graph from the given file into a graph produced by factory.
     *
     * @param path the file to read
     * @param factory supplies a new, empty graph of the desired representation
     * @param <T> the graph type
     * @return the graph described by the file
     * @throws IOException if the file cannot be read
     * @throws IllegalArgumentException if the file is malformed
     */
    public static <T extends Graph> T read(Path path, Supplier<T> factory) throws IOException {
        T graph = factory.get();
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                process(line, lineNumber, graph);
            }
        }
        return graph;
    }

    private static void process(String line, int lineNumber, Graph graph) {
        String trimmed = line.trim();
        if (trimmed.isEmpty() || trimmed.startsWith("#")) {
            return;
        }
        String[] parts = trimmed.split("\\s+");
        switch (parts[0].toUpperCase()) {
            case "V" -> processVertex(parts, lineNumber, graph);
            case "E" -> processEdge(parts, lineNumber, graph);
            default -> throw new IllegalArgumentException(
                    "Unknown command '" + parts[0] + "' at line " + lineNumber);
        }
    }

    private static void processVertex(String[] parts, int lineNumber, Graph graph) {
        if (parts.length != 2) {
            throw new IllegalArgumentException(
                    "V requires one argument at line " + lineNumber);
        }
        graph.addVertex(parts[1]);
    }

    private static void processEdge(String[] parts, int lineNumber, Graph graph) {
        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "E requires two arguments at line " + lineNumber);
        }
        graph.addEdge(parts[1], parts[2]);
    }
}