package ru.nsu.govorukhina.task113;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Command line entry point that reads an expression and prints it back.
 * The first line of the input is the expression.
 * Input is read from the file given as the first argument,
 * or from standard input when no argument is provided.
 */
public final class Main {

    private Main() {
    }

    /**
     * Runs the command line application.
     *
     * @param args optionally the path of the file that contains the input
     * @throws IOException if the input cannot be read
     */
    public static void main(String[] args) throws IOException {
        List<String> lines = readLines(args);
        if (lines.isEmpty()) {
            return;
        }
        Expression expression = Expression.parse(lines.get(0).trim());
        expression.print();
        System.out.println();
        expression.derivative("x").print();
        System.out.println();
        if (lines.size() > 1) {
            System.out.println(expression.eval(lines.get(1)));
        }
    }

    private static List<String> readLines(String[] args) throws IOException {
        if (args.length > 0) {
            return Files.readAllLines(Paths.get(args[0]), StandardCharsets.UTF_8);
        }
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.toList());
        }
    }
}