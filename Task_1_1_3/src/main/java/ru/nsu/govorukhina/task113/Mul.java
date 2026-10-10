package ru.nsu.govorukhina.task113;

import java.util.Map;

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
    public Expression derivative(String variable) {
        Expression leftDerivative = left.derivative(variable);
        Expression rightDerivative = right.derivative(variable);
        return new Add(new Mul(leftDerivative, right), new Mul(left, rightDerivative));
    }

    @Override
    protected int eval(Map<String, Integer> bindings) {
        return left.eval(bindings) * right.eval(bindings);
    }

    @Override
    public String toString() {
        return "(" + left + "*" + right + ")";
    }
}