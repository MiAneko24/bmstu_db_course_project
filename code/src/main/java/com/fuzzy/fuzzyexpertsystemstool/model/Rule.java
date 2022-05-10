package com.fuzzy.fuzzyexpertsystemstool.model;

import com.fuzzy.fuzzyexpertsystemstool.types.AntecedentConnectionType;

import java.util.ArrayList;
import java.util.List;

public class Rule extends Model {
    private int id;

    private List<Antecedent> antecedents;

    private List<Consequent> consequents;

    private AntecedentConnectionType antecedentConnectionType;

    public int getId() {
        return id;
    }

    public List<Antecedent> getAntecedents() {
        return antecedents;
    }

    public List<Consequent> getConsequents() {
        return consequents;
    }

    public List<String> getAntecedentsText() {
        List<String> res = new ArrayList<>();
        for (Antecedent a: antecedents)
            res.add(a.toString());
        return res;
    }

    public List<String> getConsequentsText() {
        List<String> res = new ArrayList<>();
        for (Consequent c: consequents)
            res.add(c.toString());
        return res;
    }

    public AntecedentConnectionType getAntecedentConnectionType() {
        return antecedentConnectionType;
    }

    public Rule(int i, List<Antecedent> ants, List<Consequent> cons, AntecedentConnectionType conn) {
        id = i;
        antecedents = ants;
        consequents = cons;
        antecedentConnectionType = conn;
    }

    public Rule(List<Antecedent> ants, List<Consequent> cons, AntecedentConnectionType conn) {
        id = generateId();
        antecedents = ants;
        consequents = cons;
        antecedentConnectionType = conn;
    }
//    private
}
