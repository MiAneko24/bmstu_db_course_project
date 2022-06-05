package com.fuzzy.fuzzyexpertsystemstool.dbmodel;

public class OutputResult {
    private final String name;
    private final double value;

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public OutputResult(String n, double v) {
        name = n;
        value = v;
    }
}
