package com.fuzzy.fuzzyexpertsystemstool.database;

import com.fuzzy.fuzzyexpertsystemstool.dbmodel.OutputResult;
import com.fuzzy.fuzzyexpertsystemstool.model.*;
import com.fuzzy.fuzzyexpertsystemstool.repositories.*;

import java.util.List;

public class DataController {
    private final DBConnection connection = DBConnection.getInstance();

    private final OutputRepository outputRepository = new OutputRepository();
    private final SystemRepository systemRepository = new SystemRepository();

    private final RuleRepository ruleRepository = new RuleRepository();
    private final VariableRepository variableRepository = new VariableRepository();

    private Boolean cached = false;
    private final MembershipFunctionRepository membershipFunctionRepository = new MembershipFunctionRepository();
    public DataController() {
    }

    public State connect() {
        return connection.connect();
    }

    public State connect(String login, String password) {
        State state = connection.connect(login, password);
        if (state == State.Fail)
            connection.connect();
        return state;
    }

    public List<String> save(FSystem system) {
        cached = false;
        return systemRepository.save(system);
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
        return membershipFunctionRepository.getMembershipFunctionByIndex(i);
    }

    public MembershipFunction getMembershipFunctionByName(String s) {
        return membershipFunctionRepository.getMembershipFunctionByName(s);
    }
    public List<String> save(Variable var) {
        cached = false;
        return variableRepository.save(var);
    }

    public FSystem getSystemData(int i) {
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

    public List<String> getMembershipFunctionsById(int vId) {
        return membershipFunctionRepository.getMembershipFunctionsList(vId);
    }

    public List<String> getRules(int i) {
        Integer sId = systemRepository.getSystemIdByIndex(i);
        if (sId == null)
            return null;
        return ruleRepository.getRules(sId);
    }

    public List<List<Double>> getGraphData(MembershipFunction function) {
        List<List<Double>> res = null;
//        MembershipFunction function = membershipFunctionRepository.getMembershipFunction(mId);
//        if (function != null) {
        res = membershipFunctionRepository.getMembershipFunctionGraph(function);
//        }
        return res;
    }

    public List<OutputResult> getOutput(int i) {
        Integer sId = systemRepository.getSystemIdByIndex(i);
        if (sId != null) {
            List<OutputResult> res = outputRepository.getOutput(sId, cached);
            cached = true;
            return res;
        }
        return null;
    }

    public Rule getRuleData(int i) {
        return ruleRepository.getRuleData(i);
    }

    public boolean isAdmin() {
        System.out.println("Yuppy, admin! = " + connection.isAdmin());
        return connection.isAdmin();
    }

    public boolean isEditable(FSystem s) {
        return isAdmin() || connection.isEditable(s.getSpecialization().toString());
    }

    public Variable getVariableByName(String s, int i) {
        Integer sId = systemRepository.getSystemIdByIndex(i);
        if (sId != null)
            return variableRepository.getVariableByName(s, sId);
        return null;
    }

    public List<String> save(MembershipFunction function) {
        cached = false;
        return membershipFunctionRepository.save(function);
    }

    public List<String> delete(MembershipFunction function) {
        cached = false;
        return membershipFunctionRepository.delete(function);
    }
    public List<String> save(Rule rule) {
        cached = false;
        return ruleRepository.save(rule);
    }

    public List<String> delete(Rule rule) {
        cached = false;
        return ruleRepository.delete(rule);
    }

    public List<String> delete(FSystem system) {
        cached = false;
        return systemRepository.delete(system);
    }
    public List<String> delete(Variable variable) {
        cached = false;
        return variableRepository.delete(variable);
    }


}
