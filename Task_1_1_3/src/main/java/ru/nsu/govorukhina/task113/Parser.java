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
     */
    Expression parse() {
        Expression expression = parseExpression();
        skipWhitespace();
        return expression;
    }

    private Expression parseExpression() {
        skipWhitespace();
        if (text.charAt(position) == '(') {
            return parseOperation();
        }
        return parseAtom();
    }

    private Expression parseOperation() {
        position++;
        final Expression left = parseExpression();
        skipWhitespace();
        final char operator = text.charAt(position);
        position++;
        final Expression right = parseExpression();
        skipWhitespace();
        position++;
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
            default:
                return new Div(left, right);
        }
    }

    private Expression parseAtom() {
        char current = text.charAt(position);
        if (Character.isDigit(current)) {
            return new Number(readNumber());
        }
        return new Variable(readIdentifier());
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
}
