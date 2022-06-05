package com.fuzzy.fuzzyexpertsystemstool.dbmodel;

public class DBConsequent {
    private int id;

    private Integer membershipFunctionId;

    private int ruleId;

    private Integer variableId;

    public DBConsequent() {}

    public int getId() {
        return id;
    }

    public Integer getMembershipFunctionId() {
        return membershipFunctionId;
    }

    public int getRuleId() {
        return ruleId;
    }

    public Integer getVariableId() {
        return variableId;
    }

    public DBConsequent(int cId, Integer mf, int r, Integer var) {
        id = cId;
        membershipFunctionId = mf;
        variableId = var;
        ruleId = r;
    }
}
