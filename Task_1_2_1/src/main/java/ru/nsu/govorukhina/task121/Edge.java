package ru.nsu.govorukhina.task121;

/**
 * A directed edge from one vertex to another.
 *
 * @param from the source vertex
 * @param to the target vertex
 */
public record Edge(String from, String to) implements Comparable<Edge> {

    @Override
    public int compareTo(Edge other) {
        int bySource = from.compareTo(other.from);
        return bySource != 0 ? bySource : to.compareTo(other.to);
    }

    @Override
    public String toString() {
        return from + "->" + to;
    }
}