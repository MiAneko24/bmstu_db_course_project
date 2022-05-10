package com.fuzzy.fuzzyexpertsystemstool.dbmodel;

public class DBRuleAntecedents {
    private int ruleId;

    private int antecendentId;

    public DBRuleAntecedents(){}

    public int getRuleId() {
        return ruleId;
    }

    public int getAntecendentId() {
        return antecendentId;
    }

    public DBRuleAntecedents(int r, int a) {
        ruleId = r;
        antecendentId = a;
    }
}
