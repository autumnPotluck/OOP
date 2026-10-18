package ru.nsu.govorukhina.task121;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * A directed graph with string-labeled vertices and directed edges.
 */
public interface Graph extends Iterable<String> {

    /**
     * Adds a vertex to the graph.
     *
     * @param vertex the vertex to add
     */
    void addVertex(String vertex);

    /**
     * Removes a vertex and every edge incident to it.
     *
     * @param vertex the vertex to remove
     */
    void removeVertex(String vertex);

    /**
     * Adds a directed edge. Creates missing endpoints.
     *
     * @param from the source vertex
     * @param to the target vertex
     */
    void addEdge(String from, String to);

    /**
     * Removes the directed edge.
     *
     * @param from the source vertex
     * @param to the target vertex
     */
    void removeEdge(String from, String to);

    /**
     * Returns the neighbors of a vertex.
     *
     * @param vertex the vertex whose neighbors are requested
     * @return an immutable, alphabetically sorted list of neighbor names
     * @throws IllegalArgumentException if the vertex does not exist
     */
    List<String> getNeighbors(String vertex);

    /**
     * Returns all vertices in this graph.
     *
     * @return an immutable set of vertex names
     */
    Set<String> getVertices();

    /**
     * Returns all edges in this graph.
     *
     * @return an immutable set of edges
     */
    Set<Edge> getEdges();

    /**
     * Reports whether the given vertex exists.
     *
     * @param vertex the vertex to test
     * @return {@code true} if the vertex exists
     */
    boolean hasVertex(String vertex);

    /**
     * Reports whether the given directed edge exists.
     *
     * @param from the source vertex
     * @param to the target vertex
     * @return {@code true} if the edge exists
     */
    boolean hasEdge(String from, String to);

    /**
     * Returns the number of vertices.
     *
     * @return the vertex count
     */
    int vertexCount();

    /**
     * Returns the number of edges.
     *
     * @return the edge count
     */
    int edgeCount();

    /**
     * Removes all vertices and edges.
     */
    void clear();

    /**
     * Returns an iterator over the vertices.
     *
     * @return a vertex iterator
     */
    @Override
    default Iterator<String> iterator() {
        return getVertices().iterator();
    }

    /**
     * Reads a graph from a file in the graph text format. The returned graph uses the default
     * adjacency-list representation.
     *
     * @param path the file to read
     * @return a graph containing the data from the file
     * @throws IOException if the file cannot be read
     * @throws IllegalArgumentException if the file is malformed
     */
    static Graph read(Path path) throws IOException {
        return GraphReader.read(path);
    }
}
