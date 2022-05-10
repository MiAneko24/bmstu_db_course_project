package com.fuzzy.fuzzyexpertsystemstool.repositories;

import com.fuzzy.fuzzyexpertsystemstool.dao.ConsequentDao;
import com.fuzzy.fuzzyexpertsystemstool.dao.Dao;
import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBConsequent;
import com.fuzzy.fuzzyexpertsystemstool.model.Consequent;
import com.fuzzy.fuzzyexpertsystemstool.model.MembershipFunction;
import com.fuzzy.fuzzyexpertsystemstool.model.Variable;
import com.fuzzy.fuzzyexpertsystemstool.types.FunctionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConsequentRepository {
    private Dao<DBConsequent> consequentDao = new ConsequentDao();
    private VariableRepository variableRepository = new VariableRepository();
    private MembershipFunctionRepository membershipFunctionRepository = new MembershipFunctionRepository();

    public List<Consequent> getConsequents(int rId) {
        List <DBConsequent> dbConsequents = consequentDao.getAll(rId);
        List<Consequent> consequents = new ArrayList<>();
        for (DBConsequent c: dbConsequents) {
            Integer vId = c.getVariableId();
            Variable variable = (vId != null) ? variableRepository.getVariable(vId) : null;
            MembershipFunction membershipFunction = membershipFunctionRepository.getMembershipFunction(c.getMembershipFunctionId());
            consequents.add(new Consequent(
                    c.getId(),
                    membershipFunction,
                    variable,
                    getConsequentText(c.getId())
            ));
        }
        return consequents;
    }

    public DBConsequent getConsequent(int cId) {
        return consequentDao.get(cId);
    }

    public String getConsequentText(int cId) {
        DBConsequent consequent = getConsequent(cId);
        StringBuilder text = new StringBuilder();
        if (consequent != null) {
            MembershipFunction function =
                    membershipFunctionRepository.getMembershipFunction(consequent.getMembershipFunctionId());
            Integer vId = consequent.getVariableId();
            if (vId != null) {
                text.append(variableRepository.getVariableText(consequent.getVariableId()))
                        .append(" = ")
                        .append(function.getParameter1());
                if (function.getmType() == FunctionType.Linear)
                    text.append(" ").append(function.getVariable().getName());

            } else {
                text.append(function.getVariable().getName())
                        .append(" is ")
                        .append(function.getTerm());
            }
        }
        return text.toString();
    }

    private List<String> getConsequentsSugeno(List<DBConsequent> consequents) {
        List<String> result = new ArrayList<>();
        HashMap<Integer, List<DBConsequent>> consFiltered = new HashMap<>();
        for (DBConsequent c: consequents) {
            if (!consFiltered.containsKey(c.getVariableId())) {
                consFiltered.put(c.getVariableId(), new ArrayList<>());
            }
            consFiltered.get(c.getVariableId()).add(c);
        }
        for (Integer i : consFiltered.keySet()) {
            List<DBConsequent> cons = consFiltered.get(i);
            StringBuilder stringBuilder = new StringBuilder(variableRepository.getVariableText(cons.get(0).getVariableId())
                    + " =");
            for (int j = 0; j < cons.size(); j++) {
                DBConsequent cur = cons.get(j);
                MembershipFunction function = membershipFunctionRepository.getMembershipFunction(cur.getMembershipFunctionId());
                if (j != 0 && function.getParameter1() > 0) {
                    stringBuilder.append(" +");
                }
                stringBuilder.append(" ").append(function.getParameter1());
                if (function.getmType() == FunctionType.Linear)
                    stringBuilder.append(" ").append(function.getVariable().getName());
            }
            result.add(stringBuilder.toString());
        }
        return result;
    }

    public List<String> getConsequentsText(int rId) {
        List<String> result;
        List<DBConsequent> consequents = consequentDao.getAll(rId);
        if (consequents.get(0).getVariableId() != null) {
            result = getConsequentsSugeno(consequents);
        }
        else {
            result = new ArrayList<>();
            for (DBConsequent c : consequents) {
                MembershipFunction function = membershipFunctionRepository.getMembershipFunction(c.getMembershipFunctionId());
                result.add(function.getVariable().getName() + " is " + function.getTerm());
            }
        }
        return result;
    }
}
