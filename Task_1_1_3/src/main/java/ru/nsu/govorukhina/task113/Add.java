package ru.nsu.govorukhina.task113;

/**
 * The sum of two expressions.
 */
public final class Add extends Expression {

    private final Expression left;
    private final Expression right;

    /**
     * Creates a sum of two expressions.
     *
     * @param left the left operand
     * @param right the right operand
     */
    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left + "+" + right + ")";
    }
}