package ru.nsu.govorukhina.task113;

import java.util.Map;

/**
 * A constant numeric expression.
 */
public final class Number extends Expression {

    private final int value;

    /**
     * Creates a constant expression.
     *
     * @param value the constant value
     */
    public Number(int value) {
        this.value = value;
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    @Override
    protected int eval(Map<String, Integer> bindings) {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}