package com.fuzzy.fuzzyexpertsystemstool.model;

public class Consequent extends Model {
    private int id;
    private Variable variable;
    private MembershipFunction membershipFunction;
    private String text;

    public int getId() {
        return id;
    }

    public MembershipFunction getMembershipFunction() {
        return membershipFunction;
    }

    public Variable getVariable() {
        return variable;
    }

    @Override
    public String toString() {
        return text;
    }

    public Consequent(int i, MembershipFunction mf, Variable var, String t) {
        id = i;
        membershipFunction = mf;
        variable = var;
        text = t;
    }

    public Consequent(MembershipFunction mf, Variable var, String t) {
        id = generateId();
        membershipFunction = mf;
        variable = var;
        text = t;
    }

//    private int
}
