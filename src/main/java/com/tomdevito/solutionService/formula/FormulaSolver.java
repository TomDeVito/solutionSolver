package com.tomdevito.solutionService.formula;


import java.util.Stack;

public class FormulaSolver {

    public int solveFormula(String expressionString){

        String[] parsedExpression = FormulaParser.parse(expressionString);

        Stack<String> expressionStack = new Stack<String>();

        for (int i = parsedExpression.length - 1; i >= 0; i--) {
            expressionStack.push(parsedExpression[i]);
        }

        // assuming valid formula is given every time
        while (expressionStack.size() != 1) {
            String leftOperand = expressionStack.pop();
            String operator, rightOperand;

            operator = expressionStack.pop();
            rightOperand = expressionStack.pop();
            int result = solveEquation(leftOperand, operator, rightOperand);
            expressionStack.push(String.valueOf(result));
        }

        return parseInteger(expressionStack.peek());
    }

    private int parseInteger(String value){
        return Integer.valueOf(value);
    }

    private int solveEquation(String leftOperand, String operator, String rightOperand) {
        int leftOperandInt = parseInteger(leftOperand);
        int rightOperandInt = parseInteger(rightOperand);

        if (operator.equals("+")) {
            return leftOperandInt + rightOperandInt;
        }

        if (operator.equals("-")) {
            return leftOperandInt - rightOperandInt;
        }

        throw new UnsupportedOperationException(operator + " is not supported at this time.");
    }
}
