package com.fuzzy.fuzzyexpertsystemstool.types;

import java.util.ArrayList;
import java.util.List;

public enum SpecializationType {
    Physics("physics"),
    Chemistry("chemistry"),
//    Sociology("sociology");
//    Biology("biology"),
    Informatics("informatics");

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
                st = Informatics;
                break;
        }
        return st;
    }

    public static List<String> getSpecializationTypes() {
        List<String> res = new ArrayList<>();
        res.add(Physics.toString());
        res.add(Chemistry.toString());
        res.add(Informatics.toString());
        return res;
    }
}
