package com.fuzzy.fuzzyexpertsystemstool.types;

public enum SpecializationType {
    Physics("physics"),
    Chemistry("chemistry"),
    Sociology("sociology");
//    Biology("biology"),
//    Informatics("informatics");

    private String text;

    SpecializationType(String t) {
        text = t;
    }

    @Override
    public String toString() {
        return text;
    }

    public static SpecializationType getSpecializationType(String s) {
        SpecializationType st;
        switch (s) {
            case "physics":
                st = Physics;
                break;
            case "chemistry":
                st = Chemistry;
                break;
            default:
                st = Sociology;
                break;
        }
        return st;
    }
}
