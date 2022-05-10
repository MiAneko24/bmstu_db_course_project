package com.fuzzy.fuzzyexpertsystemstool.repositories;

import com.fuzzy.fuzzyexpertsystemstool.dao.Dao;
import com.fuzzy.fuzzyexpertsystemstool.dao.VariableDao;
import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBVariable;
import com.fuzzy.fuzzyexpertsystemstool.model.Variable;

import java.util.ArrayList;
import java.util.List;

public class VariableRepository {
    private Dao<DBVariable> variableDao = new VariableDao();
    private List<DBVariable> variables = null;

    public String getVariableText(int vId) {
        return variableDao.get(vId).getName();
    }

    public Variable getVariable(int vId) {
        DBVariable var = variableDao.get(vId);
        return (var != null) ? new Variable(
                var.getId(),
                var.getName(),
                var.getMinValue(),
                var.getMaxValue(),
                var.getValue(),
                var.getSystemId()) : null;
    }

    public List<String> getVariableList(int sId) {
        variables = variableDao.getAll(sId);
        if (variables == null)
            return null;
        List<String> res = new ArrayList<>();
        for (DBVariable v: variables)
            res.add(v.getName());
        return res;
    }

    public Integer getVariableIdByIndex(int i) {
        if (variables != null && i >= 0)
            return variables.get(i).getId();
        return null;
    }

    public void save(Variable variable) {
        variableDao.save(new DBVariable(
                variable.getId(),
                variable.getName(),
                variable.getMinValue(),
                variable.getMaxValue(),
                variable.getValue(),
                variable.getSystemId()
                ));
    }
}
