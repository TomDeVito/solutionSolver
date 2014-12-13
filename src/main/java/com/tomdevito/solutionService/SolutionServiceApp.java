package com.tomdevito.solutionService;

import com.tomdevito.solutionService.formula.FormulaSolver;

public class SolutionServiceApp {

    public static void main(String[] args) {
        FormulaSolver formulaSolver = new FormulaSolver();
        SolutionService solutionService = new SolutionService(formulaSolver);

        String mqHostname = "";

        if(args.length > 0) {
            mqHostname = args[0];
        }

        try {
            solutionService.startService(mqHostname);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}