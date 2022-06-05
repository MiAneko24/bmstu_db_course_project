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
                    (a.getMembershipFunctionId() != null)
                        ? membershipFunctionRepository.getMembershipFunction(a.getMembershipFunctionId())
                        : null,
                    getAntecedentText(a.getId())));
        }
        return antecedents;
    }

    public List<String> getAntecedentsText(int rId) {
        List<String> antecedentsText = new ArrayList<>();
        for (Antecedent a : getAntecedents(rId)) {
            if (a.getMembershipFunction() != null) {
                MembershipFunction function = a.getMembershipFunction();
                antecedentsText.add(function.getVariable().getName() + " is " + function.getTerm());
            }
            else {
                antecedentsText.add("");
            }
        }
        return antecedentsText;
    }

    public String getAntecedentText(int aId) {
        DBAntecedent a = getAntecedent(aId);
        if (a != null) {
            if (a.getMembershipFunctionId() != null) {
                MembershipFunction function = membershipFunctionRepository.getMembershipFunction(a.getMembershipFunctionId());
                return function.getVariable().getName() + " is " + function.getTerm();
            }
            else
                return "antecedent";
        }
        return null;
    }

    private DBAntecedent transform(Antecedent antecedent) {
        return (antecedent != null)
                ? new DBAntecedent(antecedent.getId(), (antecedent.getMembershipFunction() != null)
                    ? antecedent.getMembershipFunction().getId()
                    : null)
                : null;
    }

    public void save(List<Antecedent> antecedents) {
        if (antecedents != null) {
            for (Antecedent a: antecedents) {
                antecendentDao.save(transform(a));
            }
        }
    }
    public void delete(List<Antecedent> antecedents) {
        if (antecedents != null) {
            for (Antecedent a: antecedents) {
                antecendentDao.delete(transform(a));
            }
        }
    }
}
