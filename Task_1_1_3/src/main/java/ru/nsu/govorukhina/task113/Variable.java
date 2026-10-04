package ru.nsu.govorukhina.task113;

/**
 * A named variable expression.
 */
public final class Variable extends Expression {

    private final String name;

    /**
     * Creates a variable expression.
     *
     * @param name the variable name
     */
    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}