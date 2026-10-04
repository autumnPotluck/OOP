package ru.nsu.govorukhina.task113;

/**
 * The difference of two expressions.
 */
public final class Sub extends Expression {

    private final Expression left;
    private final Expression right;

    /**
     * Creates a difference of two expressions.
     *
     * @param left the left operand
     * @param right the right operand
     */
    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left + "-" + right + ")";
    }
}