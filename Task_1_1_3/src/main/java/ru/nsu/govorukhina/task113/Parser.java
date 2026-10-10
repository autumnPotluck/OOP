package ru.nsu.govorukhina.task113;

/**
 * Recursive descent parser for expressions written in parenthesized infix form.
 */
public class Parser {

    private final String text;
    private int position;

    /**
     * Creates a parser for the given text.
     *
     * @param text the expression text
     */
    Parser(String text) {
        this.text = text;
        this.position = 0;
    }

    /**
     * Parses the whole text as a single expression.
     *
     * @return the parsed expression
     * @throws IllegalArgumentException if the text is not a valid expression
     */
    Expression parse() {
        Expression expression = parseExpression();
        skipWhitespace();
        if (position < text.length()) {
            throw error("Unexpected trailing input");
        }
        return expression;
    }

    private Expression parseExpression() {
        skipWhitespace();
        if (position >= text.length()) {
            throw error("Unexpected end of input");
        }
        if (text.charAt(position) == '(') {
            return parseOperation();
        }
        return parseAtom();
    }

    private Expression parseOperation() {
        position++;
        final Expression left = parseExpression();
        skipWhitespace();
        char operator = readOperator();
        final Expression right = parseExpression();
        skipWhitespace();
        expect(')');
        return build(operator, left, right);
    }

    private Expression build(char operator, Expression left, Expression right) {
        switch (operator) {
            case '+':
                return new Add(left, right);
            case '-':
                return new Sub(left, right);
            case '*':
                return new Mul(left, right);
            case '/':
                return new Div(left, right);
            default:
                throw error("Unknown operator '" + operator + "'");
        }
    }

    private char readOperator() {
        if (position >= text.length()) {
            throw error("Expected an operator");
        }
        char operator = text.charAt(position);
        if (operator != '+' && operator != '-' && operator != '*' && operator != '/') {
            throw error("Expected an operator but found '" + operator + "'");
        }
        position++;
        return operator;
    }

    private Expression parseAtom() {
        char current = text.charAt(position);
        if (Character.isDigit(current)) {
            return new Number(readNumber());
        }
        if (Character.isLetter(current)) {
            return new Variable(readIdentifier());
        }
        throw error("Unexpected character '" + current + "'");
    }

    private int readNumber() {
        int start = position;
        while (position < text.length() && Character.isDigit(text.charAt(position))) {
            position++;
        }
        return Integer.parseInt(text.substring(start, position));
    }

    private String readIdentifier() {
        int start = position;
        while (position < text.length() && Character.isLetterOrDigit(text.charAt(position))) {
            position++;
        }
        return text.substring(start, position);
    }

    private void skipWhitespace() {
        while (position < text.length() && Character.isWhitespace(text.charAt(position))) {
            position++;
        }
    }

    private void expect(char expected) {
        if (position >= text.length() || text.charAt(position) != expected) {
            throw error("Expected '" + expected + "'");
        }
        position++;
    }

    private IllegalArgumentException error(String message) {
        return new IllegalArgumentException(message + " at position " + position);
    }
}
