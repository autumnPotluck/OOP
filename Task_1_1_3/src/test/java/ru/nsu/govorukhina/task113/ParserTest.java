package ru.nsu.govorukhina.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for parsing expressions from text.
 */
class ParserTest {

    @Test
    void parsesConstant() {
        assertEquals("3", Expression.parse("3").toString());
    }

    @Test
    void parsesVariable() {
        assertEquals("x", Expression.parse("x").toString());
    }

    @Test
    void parsesNestedExpression() {
        assertEquals("(3+(2*x))", Expression.parse("(3+(2*x))").toString());
    }

    @Test
    void parsesExpressionWithWhitespace() {
        assertEquals("(3+(2*x))", Expression.parse(" ( 3 + ( 2 * x ) ) ").toString());
    }

    @Test
    void parsesMultiLetterVariableNames() {
        assertEquals("(alpha+beta)", Expression.parse("(alpha+beta)").toString());
    }

    @Test
    void parsesDivisionAndSubtraction() {
        assertEquals("((a-b)/(c+d))", Expression.parse("((a-b)/(c+d))").toString());
    }
}