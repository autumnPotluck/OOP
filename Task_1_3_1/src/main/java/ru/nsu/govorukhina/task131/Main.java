package ru.nsu.govorukhina.task131;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

/**
 * Entry point for Substring Finder.
 */
public final class Main {

    private static final int EXIT_USAGE = 1;
    private static final int EXIT_IO = 2;

    private Main() {
    }

    /**
     * Runs the finder against the given arguments and writes results to the given streams.
     *
     * @param args two elements expected: the file path and the target substring
     * @param out  stream for printing result
     * @param err  stream for printing result
     * @return list of matched indices, or null when the arguments are invalid
     * @throws IOException if the file cannot be read
     */
    static List<Long> run(String[] args, PrintStream out, PrintStream err) throws IOException {
        if (args.length != 2) {
            err.println("Usage: <file> <target>");
            return null;
        }
        List<Long> found = SubstringFinder.find(args[0], args[1]);
        out.println(found);
        return found;
    }

    /**
     * Parses the command line and delegates to run().
     *
     * @param args two elements: file path and target substring
     */
    public static void main(String[] args) {
        try {
            List<Long> found = run(args, System.out, System.err);
            if (found == null) {
                System.exit(EXIT_USAGE);
            }
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
            System.exit(EXIT_IO);
        }
    }
}