package com.fuzzy.fuzzyexpertsystemstool.model;

import com.fuzzy.fuzzyexpertsystemstool.types.BarrierType;
import com.fuzzy.fuzzyexpertsystemstool.types.FunctionType;

public class MembershipFunction extends Model {

        private int id;

        private String term;

        private FunctionType mType;

        private Variable variable;

        private Double parameter1;

        private Double parameter2;

        private Double parameter3;

        private Double parameter4;

        private MembershipFunction parent;

        private BarrierType barrier;

        private boolean isActive;

        public int getId() {
            return id;
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

        public MembershipFunction getParent() {
            return parent;
        }

        public String getTerm() {
            return term;
        }

        public Variable getVariable() {
            return variable;
        }


        public MembershipFunction(String t, FunctionType type, Variable var, Double par1, Double par2, Double par3, Double par4, MembershipFunction par, BarrierType b, boolean active){
            id = generateId();
            term = t;
            mType = type;
            variable = var;
            parameter1 = par1;
            parameter2 = par2;
            parameter3 = par3;
            parameter4 = par4;
            parent = par;
            barrier = b;
            isActive = active;
        }

        public MembershipFunction(int i, String t, FunctionType type, Variable var, Double par1, Double par2, Double par3, Double par4, MembershipFunction par, BarrierType b, boolean active) {
            id = i;
            term = t;
            mType = type;
            variable = var;
            parameter1 = par1;
            parameter2 = par2;
            parameter3 = par3;
            parameter4 = par4;
            parent = par;
            barrier = b;
            isActive = active;
        }

}
