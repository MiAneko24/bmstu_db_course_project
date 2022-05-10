package com.fuzzy.fuzzyexpertsystemstool.model;

import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBSystem;
import com.fuzzy.fuzzyexpertsystemstool.types.SpecializationType;
import com.fuzzy.fuzzyexpertsystemstool.types.SystemType;

public class System extends Model {
    private int id;

    private String name;

    private SystemType type;

    private SpecializationType specialization;

    public System(String n, SystemType t, SpecializationType sp) {
        id = generateId();
        name = n;
        type = t;
        specialization = sp;
    }

    public System(DBSystem s) {
        id = s.getId();
        name = s.getName();
        type = s.getType();
        specialization = s.getSpecialization();
    }
}
