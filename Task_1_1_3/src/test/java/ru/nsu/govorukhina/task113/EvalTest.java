package ru.nsu.govorukhina.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests for expression evaluation.
 */
class EvalTest {

    @Test
    void evaluatesConstant() {
        assertEquals(3, new Number(3).eval(""));
    }

    @Test
    void evaluatesVariable() {
        assertEquals(10, new Variable("x").eval("x = 10"));
    }

    @Test
    void evaluatesTaskExample() {
        Expression expression = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        assertEquals(23, expression.eval("x = 10; y = 13"));
    }

    @Test
    void evaluatesAssignmentsWithoutSpaces() {
        assertEquals(50, Expression.parse("(x*y)").eval("x=5;y=10"));
    }

    @Test
    void evaluatesSubtractionAndDivision() {
        assertEquals(2, Expression.parse("((a-b)/c)").eval("a = 10; b = 4; c = 3"));
    }

    @Test
    void evaluatesAfterDifferentiation() {
        Expression expression = Expression.parse("(3+(2*x))").derivative("x");
        assertEquals(2, expression.eval("x = 100"));
    }

    @Test
    void throwsWhenVariableIsMissing() {
        assertThrows(IllegalArgumentException.class, () -> new Variable("x").eval("y = 1"));
    }

    @Test
    void throwsOnMalformedAssignment() {
        assertThrows(IllegalArgumentException.class, () -> new Number(1).eval("x 10"));
    }

    @Test
    void throwsOnNonNumericValue() {
        assertThrows(NumberFormatException.class, () -> new Variable("x").eval("x = abc"));
    }
}