package ru.nsu.govorukhina.task113;

import java.io.PrintStream;
import java.util.Map;

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
     * Computes the symbolic derivative of this expression.
     *
     * @param variable the name of the variable to differentiate with respect to
     * @return a new expression representing the derivative
     */
    public abstract Expression derivative(String variable);

    /**
     * Evaluates this expression for the given variable assignments.
     *
     * @param assignments assignments separated by semicolons
     * @return the integer value of the expression
     */
    public int eval(String assignments) {
        return eval(Assignments.parse(assignments));
    }

    /**
     * Evaluates this expression using already parsed variable bindings.
     *
     * @param bindings the variable values
     * @return the integer value of the expression
     */
    protected abstract int eval(Map<String, Integer> bindings);

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