package com.tomdevito.solutionService.formula;


public final class FormulaParser {

    private static final String WHITESPACE = " ";

    private FormulaParser() {
    }

    public static String[] parse(String expressionString) {
        //Simple parser for now
        return expressionString.split(WHITESPACE);
    }
}
