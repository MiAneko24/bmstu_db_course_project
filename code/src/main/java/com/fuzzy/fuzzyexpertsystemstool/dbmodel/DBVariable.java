package com.fuzzy.fuzzyexpertsystemstool.dbmodel;

public class DBVariable {
    private int id;

    private String name;

    private double minValue;

    private double maxValue;

    private Double value;

    private int systemId;

    public DBVariable(){}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public Double getValue() {
        return value;
    }

    public int getSystemId() {
        return systemId;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public DBVariable(int i, String n, double min, double max, Double val, int s) {
        id = i;
        minValue = min;
        maxValue = max;
        value = val;
        systemId = s;
        name = n;
    }
}
