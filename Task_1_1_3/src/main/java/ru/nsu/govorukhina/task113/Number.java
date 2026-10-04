package ru.nsu.govorukhina.task113;

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
    public String toString() {
        return Integer.toString(value);
    }
}