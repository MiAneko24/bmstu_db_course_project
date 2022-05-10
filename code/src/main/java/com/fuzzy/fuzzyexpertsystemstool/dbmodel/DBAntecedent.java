package com.fuzzy.fuzzyexpertsystemstool.dbmodel;

public class DBAntecedent {
    private int id;

    private int membershipFunctionId;

    public DBAntecedent(){}

    public int getId() {
        return id;
    }

    public int getMembershipFunctionId() {
        return membershipFunctionId;
    }

    public DBAntecedent(int a_id, int mf) {
        id = a_id;
        membershipFunctionId = mf;
    }
}
