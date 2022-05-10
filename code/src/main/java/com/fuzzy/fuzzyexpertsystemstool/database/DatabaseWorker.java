package com.fuzzy.fuzzyexpertsystemstool.database;

import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBMembershipFunction;
import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBSystem;
import com.fuzzy.fuzzyexpertsystemstool.dbmodel.OutputResult;
import com.fuzzy.fuzzyexpertsystemstool.model.MembershipFunction;
import com.fuzzy.fuzzyexpertsystemstool.model.Rule;
import com.fuzzy.fuzzyexpertsystemstool.model.Variable;
import com.fuzzy.fuzzyexpertsystemstool.repositories.*;

import java.util.List;

public class DatabaseWorker {
    private final DBConnection connection = DBConnection.getInstance();

    private final OutputRepository outputRepository = new OutputRepository();
    private final SystemRepository systemRepository = new SystemRepository();

    private final RuleRepository ruleRepository = new RuleRepository();
    private final VariableRepository variableRepository = new VariableRepository();

    private final MembershipFunctionRepository membershipFunctionRepository = new MembershipFunctionRepository();
    public DatabaseWorker() {
    }

    public State connect() {
        return connection.connect();
    }

    public State connect(String login, String password) {
        return connection.connect();
    }

    public void disconnect() {
        connection.disconnect();
    }


//    public List<DBVariable> getVariables(int sysId) {
//            return variableDao.getAll(sysId);
//    }

//    public List<DBRule> getRules(int sysId) {
//            return ruleDao.getAll(sysId);
//    }

    public List<String> getSystems() {
        return systemRepository.getSystemList();
    }

    public List<String> getVariables(int i) {
        Integer sId = systemRepository.getSystemIdByIndex(i);
        if (sId == null)
            return null;
        return variableRepository.getVariableList(sId);
    }

    public Variable getVariableData(int i) {
        return variableRepository.getVariable(variableRepository.getVariableIdByIndex(i));
    }

    public MembershipFunction getMembershipFunctionData(int i) {
        DBMembershipFunction fun = membershipFunctionRepository.getMembershipFunctionByIndex(i);
        Integer vId = fun.getVariableId();
        return new MembershipFunction(
                fun.getId(),
                fun.getTerm(),
                fun.getmType(),
                (vId != null)
                    ? variableRepository.getVariable(vId)
                    : null,
                fun.getParameter1(),
                fun.getParameter2(),
                fun.getParameter3(),
                fun.getParameter4(),
                (fun.getPid() != null)
                    ? membershipFunctionRepository.getMembershipFunction(fun.getPid())
                    : null,
                fun.getBarrier(),
                fun.isActive()
        );
    }

    public void save(Variable var) {
        variableRepository.save(var);
    }

    public DBSystem getSystemData(int i) {
        Integer sId = systemRepository.getSystemIdByIndex(i);
        if (sId != null)
            return systemRepository.getSystem(sId);
        return null;
    }

    public List<String> getAntecedents(int i) {
        return ruleRepository.getAntecedents(i);
    }

    public List<String> getConsequents(int i) {
        return ruleRepository.getConsequents(i);
    }
    public List<String> getMembershipFunctions(int i) {
        Integer vId = variableRepository.getVariableIdByIndex(i);
        if (vId == null)
            return null;
        return membershipFunctionRepository.getMembershipFunctionsList(vId);
    }

    public List<String> getRules(int i) {
        Integer sId = systemRepository.getSystemIdByIndex(i);
        if (sId == null)
            return null;
        return ruleRepository.getRules(sId);
    }

    public List<List<Double>> getGraphData(int i) {
        List<List<Double>> res = null;
        DBMembershipFunction function = membershipFunctionRepository.getMembershipFunctionByIndex(i);
        if (function != null) {
            Variable variable = variableRepository.getVariable(function.getVariableId());
            if (variable != null)
                res = membershipFunctionRepository.getMembershipFunctionGraph(function, variable.getMinValue(), variable.getMaxValue());
        }
        return res;
    }

    public List<OutputResult> getOutput(int i) {
        Integer sId = systemRepository.getSystemIdByIndex(i);
        if (sId != null)
            return outputRepository.getOutput(sId);
        return null;
    }

    public Rule getRuleData(int i) {
        return ruleRepository.getRuleData(i);
    }

}
