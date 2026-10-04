package ru.nsu.govorukhina.task113;

/**
 * The product of two expressions.
 */
public final class Mul extends Expression {

    private final Expression left;
    private final Expression right;

    /**
     * Creates a product of two expressions.
     *
     * @param left the left operand
     * @param right the right operand
     */
    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left + "*" + right + ")";
    }
}