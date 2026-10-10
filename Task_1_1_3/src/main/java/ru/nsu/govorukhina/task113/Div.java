package ru.nsu.govorukhina.task113;

import java.util.Map;

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
    public Expression derivative(String variable) {
        Expression numerator =
                new Sub(
                        new Mul(left.derivative(variable), right),
                        new Mul(left, right.derivative(variable)));
        Expression denominator = new Mul(right, right);
        return new Div(numerator, denominator);
    }

    @Override
    protected int eval(Map<String, Integer> bindings) {
        return left.eval(bindings) / right.eval(bindings);
    }

    @Override
    public String toString() {
        return "(" + left + "/" + right + ")";
    }
}