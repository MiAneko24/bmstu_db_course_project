package com.fuzzy.fuzzyexpertsystemstool.dbmodel;

public class DBAntecedent {
    private int id;

    private Integer membershipFunctionId;

    public DBAntecedent(){}

    public int getId() {
        return id;
    }

    public Integer getMembershipFunctionId() {
        return membershipFunctionId;
    }

    public DBAntecedent(int a_id, Integer mf) {
        id = a_id;
        membershipFunctionId = mf;
    }
}
