package ru.nsu.govorukhina.task121;

import java.util.stream.Collectors;

/**
 * Implements the behavior of Graph representation.
 */
public abstract class AbstractGraph implements Graph {

    /**
     * Adds a vertex to the graph.
     *
     * @param vertex the vertex to add
     * @throws IllegalArgumentException if vertex is null
     */
    @Override
    public final void addVertex(String vertex) {
        requireNonNull(vertex);
        doAddVertex(vertex);
    }

    /**
     * Removes a vertex and every edge incident to it.
     *
     * @param vertex the vertex to remove
     */
    @Override
    public final void removeVertex(String vertex) {
        requireNonNull(vertex);
        doRemoveVertex(vertex);
    }

    /**
     * Adds a directed edge, adds missing endpoints.
     *
     * @param from the source vertex
     * @param to the target vertex
     * @throws IllegalArgumentException if either endpoint is null
     */
    @Override
    public final void addEdge(String from, String to) {
        requireNonNull(from);
        requireNonNull(to);
        doAddEdge(from, to);
    }

    /**
     * Removes the directed edge.
     *
     * @param from the source vertex
     * @param to the target vertex
     */
    @Override
    public final void removeEdge(String from, String to) {
        requireNonNull(from);
        requireNonNull(to);
        doRemoveEdge(from, to);
    }

    /**
     * Returns whether the given vertex exists.
     *
     * @param vertex the vertex to test
     * @return {@code true} if the vertex exists
     */
    @Override
    public boolean hasVertex(String vertex) {
        return vertex != null && getVertices().contains(vertex);
    }

    /**
     * Returns whether the given directed edge exists.
     *
     * @param from the source vertex
     * @param to the target vertex
     * @return {@code true} if the edge exists
     */
    @Override
    public boolean hasEdge(String from, String to) {
        return from != null && to != null && getEdges().contains(new Edge(from, to));
    }

    /**
     * Compares this graph with another by their vertices and edges, ignoring the internal
     * representation.
     *
     * @param obj the object to compare with
     * @return {@code true} if both graphs contain the same vertices and edges
     */
    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Graph other)) {
            return false;
        }
        return getVertices().equals(other.getVertices())
                && getEdges().equals(other.getEdges());
    }

    @Override
    public final int hashCode() {
        return 31 * getVertices().hashCode() + getEdges().hashCode();
    }

    @Override
    public final String toString() {
        String vertices = getVertices().stream().sorted().collect(Collectors.joining(", "));
        String edges = getEdges().stream().sorted().map(Edge::toString)
                .collect(Collectors.joining(", "));
        return "Graph{vertices=[" + vertices + "], edges=[" + edges + "]}";
    }

    private static void requireNonNull(String vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex name must not be null");
        }
    }

    /**
     * Adds a vertex that is known to be non-null and not yet present.
     *
     * @param vertex the vertex to add
     */
    protected abstract void doAddVertex(String vertex);

    /**
     * Removes a vertex that is known to be non-null.
     *
     * @param vertex the vertex to remove
     */
    protected abstract void doRemoveVertex(String vertex);

    /**
     * Adds a directed edge whose endpoints are known to be non-null.
     *
     * @param from the source vertex
     * @param to the target vertex
     */
    protected abstract void doAddEdge(String from, String to);

    /**
     * Removes a directed edge whose endpoints are known to be non-null.
     *
     * @param from the source vertex
     * @param to the target vertex
     */
    protected abstract void doRemoveEdge(String from, String to);
}