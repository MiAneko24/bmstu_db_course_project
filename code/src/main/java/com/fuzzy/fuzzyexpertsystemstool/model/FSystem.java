package com.fuzzy.fuzzyexpertsystemstool.model;

import com.fuzzy.fuzzyexpertsystemstool.types.SpecializationType;
import com.fuzzy.fuzzyexpertsystemstool.types.SystemType;

public class FSystem extends Model {
    private int id;

    private String name;

    private SystemType type;

    private SpecializationType specialization;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public SystemType getType() {
        return type;
    }

    public SpecializationType getSpecialization() {
        return specialization;
    }

    public FSystem(String n, SystemType t, SpecializationType sp) {
        id = generateId();
        name = n;
        type = t;
        specialization = sp;
    }

    public FSystem(int i, String n, SystemType t, SpecializationType sp) {
        id = i;
        name = n;
        type = t;
        specialization = sp;
    }
}
