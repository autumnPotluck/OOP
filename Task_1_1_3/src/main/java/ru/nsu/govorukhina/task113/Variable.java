package ru.nsu.govorukhina.task113;

import java.util.Map;

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
    public Expression derivative(String variable) {
        return new Number(name.equals(variable) ? 1 : 0);
    }

    @Override
    protected int eval(Map<String, Integer> bindings) {
        Integer value = bindings.get(name);
        if (value == null) {
            throw new IllegalArgumentException("No value provided for variable '" + name + "'");
        }
        return value;
    }

    @Override
    public String toString() {
        return name;
    }
}