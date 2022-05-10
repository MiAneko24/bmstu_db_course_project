package com.fuzzy.fuzzyexpertsystemstool.model;

public class Antecedent extends Model {
    private int id;

    private MembershipFunction membershipFunction;
    private String text;

    public int getId() {
        return id;
    }

    public MembershipFunction getMembershipFunction() {
        return membershipFunction;
    }

    @Override
    public String toString() {
        return text;
    }

    public Antecedent(MembershipFunction mf, String t) {
        id = generateId();
        membershipFunction = mf;
        text = t;
    }

    public Antecedent(int i, MembershipFunction mfId, String t) {
        id = i;
        membershipFunction = mfId;
        text = t;
    }
}
