package com.fuzzy.fuzzyexpertsystemstool.dbmodel;

import com.fuzzy.fuzzyexpertsystemstool.types.BarrierType;
import com.fuzzy.fuzzyexpertsystemstool.types.FunctionType;

public class DBMembershipFunction {
    private int id;

    private String term;

    private FunctionType mType;

    private Integer variableId;

    private Double parameter1;

    private Double parameter2;

    private Double parameter3;

    private Double parameter4;

    private Double value;

    private Integer pid;

    private BarrierType barrier;

    private boolean isActive;

    public int getId() {
        return id;
    }

    public Double getValue() {
        return value;
    }

    public BarrierType getBarrier() {
        return barrier;
    }

    public boolean isActive() {
        return isActive;
    }

    public Double getParameter1() {
        return parameter1;
    }

    public Double getParameter2() {
        return parameter2;
    }

    public Double getParameter3() {
        return parameter3;
    }

    public Double getParameter4() {
        return parameter4;
    }

    public FunctionType getmType() {
        return mType;
    }

    public Integer getPid() {
        return pid;
    }

    public String getTerm() {
        return term;
    }

    public Integer getVariableId() {
        return variableId;
    }


    public DBMembershipFunction(){}

    public DBMembershipFunction(int i, String t, FunctionType type, Integer var, Double par1, Double par2, Double par3, Double par4, Integer parent, BarrierType b, boolean active) {
        id = i;
        term = t;
        mType = type;
        variableId = var;
        parameter1 = par1;
        parameter2 = par2;
        parameter3 = par3;
        parameter4 = par4;
        pid = parent;
        barrier = b;
        isActive = active;
    }
}
