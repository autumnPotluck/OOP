package ru.nsu.govorukhina.task121;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * Tests for the representation-independent equality, hash code, and string form of graphs.
 */
class GraphEqualityTest {

    @Test
    void equalGraphsBuiltInDifferentOrderAreEqual() {
        Graph a = new AdjacencyListGraph();
        a.addEdge("A", "B");
        a.addEdge("B", "C");
        Graph b = new AdjacencyListGraph();
        b.addEdge("B", "C");
        b.addEdge("A", "B");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void graphsWithDifferentVerticesAreNotEqual() {
        Graph a = new AdjacencyListGraph();
        a.addEdge("A", "B");
        Graph b = new AdjacencyListGraph();
        b.addEdge("A", "C");
        assertNotEquals(a, b);
    }

    @Test
    void graphsWithDifferentEdgesAreNotEqual() {
        Graph a = new AdjacencyListGraph();
        a.addEdge("A", "B");
        a.addEdge("B", "C");
        Graph b = new AdjacencyListGraph();
        b.addEdge("A", "B");
        assertNotEquals(a, b);
    }

    @Test
    void graphIsEqualToItself() {
        Graph a = new AdjacencyListGraph();
        a.addEdge("A", "B");
        assertEquals(a, a);
    }

    @Test
    void graphIsNotEqualToNull() {
        Graph a = new AdjacencyListGraph();
        assertNotEquals(a, null);
    }

    @Test
    void graphIsNotEqualToString() {
        Graph a = new AdjacencyListGraph();
        assertNotEquals(a, "Graph{}");
    }

    @Test
    void emptyGraphsAreEqual() {
        assertEquals(new AdjacencyListGraph(), new AdjacencyListGraph());
    }

    @Test
    void toStringIsDeterministic() {
        Graph a = new AdjacencyListGraph();
        a.addEdge("A", "C");
        a.addEdge("A", "B");
        a.addEdge("B", "D");
        Graph b = new AdjacencyListGraph();
        b.addEdge("B", "D");
        b.addEdge("A", "B");
        b.addEdge("A", "C");
        assertEquals(a.toString(), b.toString());
    }

    @Test
    void toStringListsSortedVerticesAndEdges() {
        Graph graph = new AdjacencyListGraph();
        graph.addEdge("B", "C");
        graph.addEdge("A", "B");
        assertEquals(
                "Graph{vertices=[A, B, C], edges=[A->B, B->C]}",
                graph.toString());
    }

    @Test
    void toStringOfEmptyGraph() {
        assertEquals("Graph{vertices=[], edges=[]}", new AdjacencyListGraph().toString());
    }

    @Test
    void verticesAndEdgesUseSetEquality() {
        Graph a = new AdjacencyListGraph();
        a.addEdge("A", "B");
        Graph b = new AdjacencyListGraph();
        b.addEdge("A", "B");
        assertEquals(a.getVertices(), b.getVertices());
        assertEquals(a.getEdges(), b.getEdges());
        assertEquals(Set.of("A", "B"), a.getVertices());
        assertEquals(List.of("B"), a.getNeighbors("A"));
    }
}