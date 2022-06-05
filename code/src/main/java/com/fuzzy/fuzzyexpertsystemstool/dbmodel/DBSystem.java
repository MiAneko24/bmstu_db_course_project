package com.fuzzy.fuzzyexpertsystemstool.dbmodel;

import com.fuzzy.fuzzyexpertsystemstool.types.SpecializationType;
import com.fuzzy.fuzzyexpertsystemstool.types.SystemType;

public class DBSystem {
    private int id;

    private String name;

    private SystemType type;

    private SpecializationType specialization;

    public int getId() {
        return id;
    }

    public SpecializationType getSpecialization() {
        return specialization;
    }

    public String getName() {
        return name;
    }

    public SystemType getType() {
        return type;
    }

    public DBSystem(){}

    public DBSystem(int i, String n, SystemType t, SpecializationType s) {
        id = i;
        name = n;
        type = t;
        specialization = s;
    }
}
