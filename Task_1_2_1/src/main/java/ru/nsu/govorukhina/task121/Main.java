package ru.nsu.govorukhina.task121;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Command line that reads a graph from a file and prints it.
 * The argument is the path to a graph file in the format described by GraphReader.
 */
public final class Main {

    private Main() {
    }

    /**
     * Runs the program.
     *
     * @param args the path to the graph file
     * @throws IOException if the file cannot be read
     */
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("Usage: Main <graph-file>");
            return;
        }
        Graph graph = Graph.read(Paths.get(args[0]));
        System.out.println(graph);
        System.out.println("Vertices: " + graph.getVertices());
        System.out.println("Edges: " + graph.getEdges());
        for (String vertex : graph) {
            System.out.println("Neighbors of " + vertex + ": " + graph.getNeighbors(vertex));
        }
    }
}