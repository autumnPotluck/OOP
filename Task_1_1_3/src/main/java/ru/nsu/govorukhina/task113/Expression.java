package ru.nsu.govorukhina.task113;

import java.io.PrintStream;

/**
 * Base type for all mathematical expressions.
 */
public abstract class Expression {

    /**
     * Parses an expression from its textual representation.
     *
     * @param text the textual representation of the expression
     * @return the parsed expression
     * @throws IllegalArgumentException if the text is not a valid expression
     */
    public static Expression parse(String text) {
        return new Parser(text).parse();
    }

    /**
     * Prints this expression to the standard output.
     */
    public void print() {
        print(System.out);
    }

    /**
     * Prints this expression to the given stream.
     *
     * @param out the stream that receives the expression
     */
    public void print(PrintStream out) {
        out.print(this);
    }
}