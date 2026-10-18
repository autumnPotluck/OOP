package ru.nsu.govorukhina.task121;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests for the file reader.
 */
class GraphReaderTest {

    @Test
    void readsVerticesAndEdges() throws IOException {
        Path file = write("graph.txt", List.of(
                "V A",
                "V B",
                "V C",
                "E A B",
                "E B C"));
        Graph graph = GraphReader.read(file);
        assertTrue(graph.hasVertex("A"));
        assertTrue(graph.hasEdge("A", "B"));
        assertTrue(graph.hasEdge("B", "C"));
        assertEquals(3, graph.vertexCount());
        assertEquals(2, graph.edgeCount());
    }

    @Test
    void skipsCommentsAndBlankLines() throws IOException {
        Path file = write("graph.txt", List.of(
                "# A comment",
                "",
                "V A",
                "  ",
                "# Another comment",
                "E A B"));
        Graph graph = GraphReader.read(file);
        assertEquals(2, graph.vertexCount());
        assertEquals(1, graph.edgeCount());
    }

    @Test
    void commandsAreCaseInsensitive() throws IOException {
        Path file = write("graph.txt", List.of(
                "v A",
                "e A B"));
        Graph graph = GraphReader.read(file);
        assertTrue(graph.hasEdge("A", "B"));
    }

    @Test
    void edgeAutoAddsVertices() throws IOException {
        Path file = write("graph.txt", List.of("E A B"));
        Graph graph = GraphReader.read(file);
        assertTrue(graph.hasVertex("A"));
        assertTrue(graph.hasVertex("B"));
    }

    @Test
    void extraWhitespaceIsIgnored() throws IOException {
        Path file = write("graph.txt", List.of(
                "   V    A   ",
                "E   A   B"));
        Graph graph = GraphReader.read(file);
        assertTrue(graph.hasEdge("A", "B"));
    }

    @Test
    void emptyFileProducesEmptyGraph() throws IOException {
        Path file = write("graph.txt", List.of());
        Graph graph = GraphReader.read(file);
        assertEquals(0, graph.vertexCount());
    }

    @Test
    void rejectsUnknownCommand() throws IOException {
        Path file = write("graph.txt", List.of("X A"));
        assertThrows(IllegalArgumentException.class, () -> GraphReader.read(file));
    }

    @Test
    void rejectsWrongArgumentCountForV() throws IOException {
        Path file = write("graph.txt", List.of("V A B"));
        assertThrows(IllegalArgumentException.class, () -> GraphReader.read(file));
    }

    @Test
    void rejectsWrongArgumentCountForE() throws IOException {
        Path file = write("graph.txt", List.of("E A"));
        assertThrows(IllegalArgumentException.class, () -> GraphReader.read(file));
    }

    @Test
    void usesProvidedFactory() throws IOException {
        Path file = write("graph.txt", List.of("E A B"));
        Graph graph = GraphReader.read(file, AdjacencyListGraph::new);
        assertTrue(graph instanceof AdjacencyListGraph);
        assertTrue(graph.hasEdge("A", "B"));
    }

    private static Path write(String name, List<String> lines) throws IOException {
        Path directory = Paths.get("build", "test-graphs");
        Files.createDirectories(directory);
        Path file = directory.resolve(name);
        Files.write(file, lines, StandardCharsets.UTF_8);
        return file;
    }
}