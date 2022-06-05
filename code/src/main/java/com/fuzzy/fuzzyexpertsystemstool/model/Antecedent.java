package com.fuzzy.fuzzyexpertsystemstool.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Antecedent that = (Antecedent) o;
        System.out.println("from antecedent eq! "+ this +" (" + this.id + ", mf= " + ((membershipFunction != null )
                ? membershipFunction.getId()
                : "null")
            +") vs " + that + " (" + that.getId() +", mf= " + ((that.membershipFunction != null)
                ? that.getMembershipFunction().getId()
                : "null") +")  = " + (id == that.id && membershipFunction == that.membershipFunction));
        return id == that.id && (membershipFunction == null && that.membershipFunction == null || membershipFunction.equals(that.membershipFunction));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, membershipFunction);
    }
}
