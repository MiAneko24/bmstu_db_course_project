package com.fuzzy.fuzzyexpertsystemstool.types;

import java.util.ArrayList;
import java.util.List;

public enum FunctionType {
    Shoulder("shoulder"),
    Gauss("gauss"),
    Triangle("triangle"),
    Trapezoidal("trapezoidal"),
    Linguistic("linguistic"),
    Linear("linear"),
    Crisp("crisp");

    private final String text;

    FunctionType(final String t) {
        text = t;
    }

    @Override
    public String toString() {
        return text;
    }

    public static FunctionType getFunctionType(String s) {
        FunctionType ft;
        switch (s) {
            case "gauss":
                ft = FunctionType.Gauss;
                break;
            case "linear":
                ft = FunctionType.Linear;
                break;
            case "linguistic":
                ft = FunctionType.Linguistic;
                break;
            case "shoulder":
                ft = FunctionType.Shoulder;
                break;
            case "trapezoidal":
                ft = FunctionType.Trapezoidal;
                break;
            case "triangle":
                ft = FunctionType.Triangle;
                break;
            default:
                ft = FunctionType.Crisp;
                break;
        }
        return ft;
    }

    public static List<String> getFunctionTypes() {
        List<String> res = new ArrayList<>();
        res.add(Gauss.toString());
        res.add(Linguistic.toString());
        res.add(Shoulder.toString());
        res.add(Trapezoidal.toString());
        res.add(Triangle.toString());
        return res;
    }
}
