package ru.nsu.govorukhina.task113;

/**
 * The quotient of two expressions.
 */
public final class Div extends Expression {

    private final Expression left;
    private final Expression right;

    /**
     * Creates a quotient of two expressions.
     *
     * @param left the left operand
     * @param right the right operand
     */
    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left + "/" + right + ")";
    }
}