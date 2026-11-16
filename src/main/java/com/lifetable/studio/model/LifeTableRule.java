package com.lifetable.studio.model;

public class LifeTableRule {

    private int step;
    private int order;

    private int startYear;
    private int endYear;

    private String itemName;
    private String formula;

    public LifeTableRule(
            int step,
            int order,
            int startYear,
            int endYear,
            String itemName,
            String formula) {

        this.step = step;
        this.order = order;
        this.startYear = startYear;
        this.endYear = endYear;
        this.itemName = itemName;
        this.formula = formula;
    }

    public String getItemName() {
        return itemName;
    }

    public String getFormula() {
        return formula;
    }

    public int getStep() {
        return step;
    }

    public int getOrder() {
        return order;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getEndYear() {
        return endYear;
    }
}