package com.fuzzy.fuzzyexpertsystemstool.dbmodel;

import com.fuzzy.fuzzyexpertsystemstool.types.AntecedentConnectionType;

public class DBRule {
    private int id;

    private int systemId;

    private AntecedentConnectionType connectionType;

    private double weight;

    public DBRule(){}

    public int getId() {
        return id;
    }

    public int getSystemId() {
        return systemId;
    }

    public AntecedentConnectionType getConnectionType() {
        return connectionType;
    }

    public double getWeight() {
        return weight;
    }

    public DBRule(int i, int s, AntecedentConnectionType conn, double w) {
        id = i;
        systemId = s;
        connectionType = conn;
        weight = w;
    }
}
