package com.example.app;

public class Calculator {

    public static double evaluate(String expression) {
        return new Parser(expression).parseExpression();
    }

    private static class Parser {
        private final String input;
        private int pos = 0;

        Parser(String input) {
            this.input = input.replaceAll("\\s+", "");
        }

        double parseExpression() {
            double value = parseTerm();
            while (pos < input.length()) {
                char op = input.charAt(pos);
                if (op != '+' && op != '-') {
                    break;
                }
                pos++;
                double rhs = parseTerm();
                value = op == '+' ? value + rhs : value - rhs;
            }
            return value;
        }

        private double parseTerm() {
            double value = parseFactor();
            while (pos < input.length()) {
                char op = input.charAt(pos);
                if (op != '*' && op != '/') {
                    break;
                }
                pos++;
                double rhs = parseFactor();
                if (op == '*') {
                    value *= rhs;
                } else {
                    if (rhs == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    value /= rhs;
                }
            }
            return value;
        }

        private double parseFactor() {
            if (pos < input.length() && input.charAt(pos) == '(') {
                pos++;
                double value = parseExpression();
                if (pos >= input.length() || input.charAt(pos) != ')') {
                    throw new IllegalArgumentException("Missing closing parenthesis");
                }
                pos++;
                return value;
            }
            if (pos < input.length() && input.charAt(pos) == '-') {
                pos++;
                return -parseFactor();
            }
            int start = pos;
            while (pos < input.length() && (Character.isDigit(input.charAt(pos)) || input.charAt(pos) == '.')) {
                pos++;
            }
            if (start == pos) {
                throw new IllegalArgumentException("Unexpected character at position " + pos);
            }
            return Double.parseDouble(input.substring(start, pos));
        }
    }

    public static void main(String[] args) {
        String[] samples = {
            "3 + 4 * 2",
            "(1 + 2) * (3 + 4)",
            "10 / 2 - 3",
            "-5 + 8"
        };
        for (String expression : samples) {
            System.out.println(expression + " = " + evaluate(expression));
        }
    }
}
