package ru.nsu.govorukhina.task121;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * Tests for the adjacency-list graph implementation.
 */
class AdjacencyListGraphTest {

    @Test
    void startsEmpty() {
        Graph graph = new AdjacencyListGraph();
        assertEquals(0, graph.vertexCount());
        assertEquals(0, graph.edgeCount());
        assertTrue(graph.getVertices().isEmpty());
        assertTrue(graph.getEdges().isEmpty());
    }

    @Test
    void addsVertices() {
        Graph graph = new AdjacencyListGraph();
        graph.addVertex("A");
        graph.addVertex("B");
        assertEquals(Set.of("A", "B"), graph.getVertices());
    }

    @Test
    void addingSameVertexTwiceIsIdempotent() {
        Graph graph = new AdjacencyListGraph();
        graph.addVertex("A");
        graph.addVertex("A");
        assertEquals(1, graph.vertexCount());
    }

    @Test
    void removesVertex() {
        Graph graph = new AdjacencyListGraph();
        graph.addVertex("A");
        graph.removeVertex("A");
        assertFalse(graph.hasVertex("A"));
    }

    @Test
    void removingMissingVertexIsNoOp() {
        Graph graph = new AdjacencyListGraph();
        graph.removeVertex("ghost");
        assertEquals(0, graph.vertexCount());
    }

    @Test
    void addVertexRejectsNull() {
        Graph graph = new AdjacencyListGraph();
        assertThrows(IllegalArgumentException.class, () -> graph.addVertex(null));
    }

    @Test
    void addEdgeAutoAddsVertices() {
        Graph graph = new AdjacencyListGraph();
        graph.addEdge("A", "B");
        assertTrue(graph.hasVertex("A"));
        assertTrue(graph.hasVertex("B"));
        assertTrue(graph.hasEdge("A", "B"));
    }

    @Test
    void addingSameEdgeTwiceIsIdempotent() {
        Graph graph = new AdjacencyListGraph();
        graph.addEdge("A", "B");
        graph.addEdge("A", "B");
        assertEquals(1, graph.edgeCount());
    }

    @Test
    void edgesAreDirected() {
        Graph graph = new AdjacencyListGraph();
        graph.addEdge("A", "B");
        assertTrue(graph.hasEdge("A", "B"));
        assertFalse(graph.hasEdge("B", "A"));
    }

    @Test
    void removesEdge() {
        Graph graph = new AdjacencyListGraph();
        graph.addEdge("A", "B");
        graph.removeEdge("A", "B");
        assertFalse(graph.hasEdge("A", "B"));
    }

    @Test
    void removingMissingEdgeIsNoOp() {
        Graph graph = new AdjacencyListGraph();
        graph.addEdge("A", "B");
        graph.removeEdge("A", "Z");
        assertEquals(1, graph.edgeCount());
    }

    @Test
    void removeVertexRemovesIncidentEdges() {
        Graph graph = new AdjacencyListGraph();
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");
        graph.removeVertex("B");
        assertFalse(graph.hasEdge("A", "B"));
        assertFalse(graph.hasEdge("B", "C"));
        assertTrue(graph.hasEdge("C", "A"));
    }

    @Test
    void neighborsAreSorted() {
        Graph graph = new AdjacencyListGraph();
        graph.addEdge("A", "C");
        graph.addEdge("A", "B");
        graph.addEdge("A", "D");
        assertEquals(List.of("B", "C", "D"), graph.getNeighbors("A"));
    }

    @Test
    void isolatedVertexHasNoNeighbors() {
        Graph graph = new AdjacencyListGraph();
        graph.addVertex("A");
        assertTrue(graph.getNeighbors("A").isEmpty());
    }

    @Test
    void neighborsOfMissingVertexThrows() {
        Graph graph = new AdjacencyListGraph();
        assertThrows(IllegalArgumentException.class, () -> graph.getNeighbors("ghost"));
    }

    @Test
    void getVerticesIsImmutable() {
        Graph graph = new AdjacencyListGraph();
        graph.addVertex("A");
        Set<String> vertices = graph.getVertices();
        assertThrows(UnsupportedOperationException.class, () -> vertices.add("X"));
    }

    @Test
    void getEdgesIsImmutable() {
        Graph graph = new AdjacencyListGraph();
        graph.addEdge("A", "B");
        Set<Edge> edges = graph.getEdges();
        assertThrows(UnsupportedOperationException.class, () -> edges.add(new Edge("X", "Y")));
    }

    @Test
    void clearRemovesEverything() {
        Graph graph = new AdjacencyListGraph();
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.clear();
        assertEquals(0, graph.vertexCount());
        assertEquals(0, graph.edgeCount());
    }

    @Test
    void iteratorVisitsEveryVertex() {
        Graph graph = new AdjacencyListGraph();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        Set<String> seen = new java.util.HashSet<>();
        for (String vertex : graph) {
            seen.add(vertex);
        }
        assertEquals(Set.of("A", "B", "C"), seen);
    }
}