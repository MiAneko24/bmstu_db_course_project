package com.fuzzy.fuzzyexpertsystemstool.dbmodel;

public class DBConsequent {
    private int id;

    private int membershipFunctionId;

    private int ruleId;

    private Integer variableId;

    public DBConsequent() {}

    public int getId() {
        return id;
    }

    public int getMembershipFunctionId() {
        return membershipFunctionId;
    }

    public int getRuleId() {
        return ruleId;
    }

    public Integer getVariableId() {
        return variableId;
    }

    public DBConsequent(int cId, int mf, int r, Integer var) {
        id = cId;
        membershipFunctionId = mf;
        variableId = var;
        ruleId = r;
    }
}
