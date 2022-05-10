package com.fuzzy.fuzzyexpertsystemstool.types;

public enum AntecedentConnectionType {
    Or("or"),
    And("and");

    private final String text;

    AntecedentConnectionType(final String t) {
        text = t;
    }

    @Override
    public String toString() {
        return text;
    }

    public static AntecedentConnectionType getAntecedentConnectionType(String s) {
        AntecedentConnectionType antecedentConnectionType;
        switch (s) {
            case "or":
                antecedentConnectionType = Or;
                break;
            default:
                antecedentConnectionType = And;
                break;
        }
        return antecedentConnectionType;
    }
}