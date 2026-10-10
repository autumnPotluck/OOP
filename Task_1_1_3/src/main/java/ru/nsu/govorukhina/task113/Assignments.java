package ru.nsu.govorukhina.task113;

import java.util.HashMap;
import java.util.Map;

/**
 * Parses variable assignments.
 */
final class Assignments {

    private Assignments() {
    }

    /**
     * Parses a semicolon separated list of assignments.
     *
     * @param text the assignments text.
     * @return a map from variable names to their values
     * @throws IllegalArgumentException if an assignment is malformed
     */
    static Map<String, Integer> parse(String text) {
        Map<String, Integer> bindings = new HashMap<>();
        if (text == null || text.isBlank()) {
            return bindings;
        }
        for (String part : text.split(";")) {
            parseAssignment(part.trim(), bindings);
        }
        return bindings;
    }

    private static void parseAssignment(String part, Map<String, Integer> bindings) {
        if (part.isEmpty()) {
            return;
        }
        String[] pieces = part.split("=");
        if (pieces.length != 2) {
            throw new IllegalArgumentException("Invalid assignment: " + part);
        }
        String name = pieces[0].trim();
        String value = pieces[1].trim();
        if (name.isEmpty() || value.isEmpty()) {
            throw new IllegalArgumentException("Invalid assignment: " + part);
        }
        bindings.put(name, Integer.parseInt(value));
    }
}