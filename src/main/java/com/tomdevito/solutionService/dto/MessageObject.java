package com.tomdevito.solutionService.dto;

public class MessageObject {

    String uuid;
    String formula;
    int solution;

    public MessageObject() {
    }

    public MessageObject(String uuid, String formula, int solution) {
        this.uuid = uuid;
        this.formula = formula;
        this.solution = solution;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public int getSolution() {
        return solution;
    }

    public void setSolution(int solution) {
        this.solution = solution;
    }

    @Override
    public String toString() {
        return "MessageObject{" +
                "uuid='" + uuid + '\'' +
                ", formula='" + formula + '\'' +
                ", solution=" + solution +
                '}';
    }
}
