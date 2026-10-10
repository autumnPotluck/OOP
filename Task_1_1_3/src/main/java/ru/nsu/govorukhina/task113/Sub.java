package ru.nsu.govorukhina.task113;

import java.util.Map;

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
    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    @Override
    protected int eval(Map<String, Integer> bindings) {
        return left.eval(bindings) - right.eval(bindings);
    }

    @Override
    public String toString() {
        return "(" + left + "-" + right + ")";
    }
}