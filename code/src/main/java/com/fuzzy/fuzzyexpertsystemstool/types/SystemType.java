package com.fuzzy.fuzzyexpertsystemstool.types;

public enum SystemType {
    Mamdani("Mamdani"),
    Sugeno("Sugeno");

    private String text;

    SystemType(String t) {
        text = t;
    }

    @Override
    public String toString() {
        return text;
    }

    public static SystemType getSystemType(String s) {
        SystemType st;
        switch (s) {
            case "Mamdani":
                st = Mamdani;
                break;
            default:
                st = Sugeno;
                break;
        }
        return st;
    }
}
