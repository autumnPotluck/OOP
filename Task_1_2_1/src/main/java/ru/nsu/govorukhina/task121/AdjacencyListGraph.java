package ru.nsu.govorukhina.task121;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A Graph in an adjacency list: a map from each vertex to the set of its successors.
 */
public final class AdjacencyListGraph extends AbstractGraph {

    private final Map<String, Set<String>> adjacency;

    /**
     * Creates an empty adjacency-list graph.
     */
    public AdjacencyListGraph() {
        this.adjacency = new LinkedHashMap<>();
    }

    @Override
    protected void doAddVertex(String vertex) {
        adjacency.putIfAbsent(vertex, new LinkedHashSet<>());
    }

    @Override
    protected void doRemoveVertex(String vertex) {
        adjacency.remove(vertex);
        for (Set<String> neighbors : adjacency.values()) {
            neighbors.remove(vertex);
        }
    }

    @Override
    protected void doAddEdge(String from, String to) {
        doAddVertex(from);
        doAddVertex(to);
        adjacency.get(from).add(to);
    }

    @Override
    protected void doRemoveEdge(String from, String to) {
        Set<String> neighbors = adjacency.get(from);
        if (neighbors != null) {
            neighbors.remove(to);
        }
    }

    @Override
    public List<String> getNeighbors(String vertex) {
        Set<String> neighbors = adjacency.get(vertex);
        if (neighbors == null) {
            throw new IllegalArgumentException("No such vertex: " + vertex);
        }
        List<String> sorted = new ArrayList<>(neighbors);
        Collections.sort(sorted);
        return List.copyOf(sorted);
    }

    @Override
    public Set<String> getVertices() {
        return Set.copyOf(adjacency.keySet());
    }

    @Override
    public Set<Edge> getEdges() {
        Set<Edge> edges = new LinkedHashSet<>();
        for (Map.Entry<String, Set<String>> entry : adjacency.entrySet()) {
            String from = entry.getKey();
            for (String to : entry.getValue()) {
                edges.add(new Edge(from, to));
            }
        }
        return Set.copyOf(edges);
    }

    @Override
    public int vertexCount() {
        return adjacency.size();
    }

    @Override
    public int edgeCount() {
        int total = 0;
        for (Set<String> neighbors : adjacency.values()) {
            total += neighbors.size();
        }
        return total;
    }

    @Override
    public void clear() {
        adjacency.clear();
    }
}