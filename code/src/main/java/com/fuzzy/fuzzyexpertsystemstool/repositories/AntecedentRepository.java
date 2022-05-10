package com.fuzzy.fuzzyexpertsystemstool.repositories;

import com.fuzzy.fuzzyexpertsystemstool.dao.AntecedentDao;
import com.fuzzy.fuzzyexpertsystemstool.dao.Dao;
import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBAntecedent;
import com.fuzzy.fuzzyexpertsystemstool.model.Antecedent;
import com.fuzzy.fuzzyexpertsystemstool.model.MembershipFunction;

import java.util.ArrayList;
import java.util.List;

public class AntecedentRepository {
    private Dao<DBAntecedent> antecendentDao = new AntecedentDao();
    private VariableRepository variableRepository = new VariableRepository();
    private MembershipFunctionRepository membershipFunctionRepository = new MembershipFunctionRepository();

    public DBAntecedent getAntecedent(int aId) {
        return antecendentDao.get(aId);
    }
    public List<Antecedent> getAntecedents(int rId) {
        List<DBAntecedent> dbAntecedents = antecendentDao.getAll(rId);
        List<Antecedent> antecedents = new ArrayList<>();
        for (DBAntecedent a: dbAntecedents) {
            antecedents.add(new Antecedent(
                    a.getId(),
                    membershipFunctionRepository.getMembershipFunction(a.getMembershipFunctionId()),
                    getAntecedentText(a.getId())));
        }
        return antecedents;
    }

    public List<String> getAntecedentsText(int rId) {
        List<String> antecedentsText = new ArrayList<>();
        for (Antecedent a : getAntecedents(rId)) {
            MembershipFunction function = a.getMembershipFunction();
            antecedentsText.add(function.getVariable().getName() + " is " + function.getTerm());
        }
        return antecedentsText;
    }

    public String getAntecedentText(int aId) {
        DBAntecedent a = getAntecedent(aId);
        if (a != null) {
            MembershipFunction function = membershipFunctionRepository.getMembershipFunction(a.getMembershipFunctionId());
            return function.getVariable().getName() + " is " + function.getTerm();
        }
        return null;
    }
}
