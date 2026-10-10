package ru.nsu.govorukhina.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for symbolic differentiation.
 */
class DerivativeTest {

    @Test
    void derivativeOfConstantIsZero() {
        assertEquals("0", new Number(5).derivative("x").toString());
    }

    @Test
    void derivativeOfMatchingVariableIsOne() {
        assertEquals("1", new Variable("x").derivative("x").toString());
    }

    @Test
    void derivativeOfOtherVariableIsZero() {
        assertEquals("0", new Variable("y").derivative("x").toString());
    }

    @Test
    void derivativeOfSum() {
        assertEquals("(1+0)", Expression.parse("(x+y)").derivative("x").toString());
    }

    @Test
    void derivativeOfDifference() {
        assertEquals("(1-0)", Expression.parse("(x-y)").derivative("x").toString());
    }

    @Test
    void derivativeOfProduct() {
        assertEquals("((0*x)+(2*1))", Expression.parse("(2*x)").derivative("x").toString());
    }

    @Test
    void derivativeOfQuotient() {
        assertEquals(
                "(((1*y)-(x*0))/(y*y))",
                Expression.parse("(x/y)").derivative("x").toString());
    }

    @Test
    void derivativeOfTaskExample() {
        Expression expression = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        assertEquals("(0+((0*x)+(2*1)))", expression.derivative("x").toString());
    }

    @Test
    void derivativeDoesNotMutateOriginal() {
        Expression expression = Expression.parse("(3+(2*x))");
        expression.derivative("x");
        assertEquals("(3+(2*x))", expression.toString());
    }
}
