package com.fuzzy.fuzzyexpertsystemstool.model;

import java.util.Objects;

public class Variable extends Model {
    private final int id;

    private String name;

    private double minValue;

    private double maxValue;

    private Double value;

    private int systemId;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getValue() {
        return value;
    }

    public int getSystemId() {
        return systemId;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public Variable(String n, double min, double max, Double val, int sId) {
        id = generateId();
        name = n;
        minValue = min;
        maxValue = max;
        value = val;
        systemId = sId;
    }

    public Variable(int i, String n, double min, double max, Double val, int sId) {
        id = i;
        name = n;
        minValue = min;
        maxValue = max;
        value = val;
        systemId = sId;
    }

    public void setValue(Double data) {
        value = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return id == variable.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
