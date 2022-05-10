package com.fuzzy.fuzzyexpertsystemstool.repositories;

import com.fuzzy.fuzzyexpertsystemstool.dao.Dao;
import com.fuzzy.fuzzyexpertsystemstool.dao.RuleDao;
import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBRule;
import com.fuzzy.fuzzyexpertsystemstool.model.Antecedent;
import com.fuzzy.fuzzyexpertsystemstool.model.Consequent;
import com.fuzzy.fuzzyexpertsystemstool.model.Rule;
import com.fuzzy.fuzzyexpertsystemstool.types.AntecedentConnectionType;

import java.util.ArrayList;
import java.util.List;

public class RuleRepository {
    private Dao<DBRule> ruleDao = new RuleDao();
    private List<DBRule> rules = null;
    private AntecedentRepository antecedentRepository = new AntecedentRepository();
    private ConsequentRepository consequentRepository = new ConsequentRepository();

    private AntecedentConnectionType getRuleType(int rId) {
        return ruleDao.get(rId).getConnectionType();
    }

    private String getAntecedent(int rId) {
        List<String> antecedents = antecedentRepository.getAntecedentsText(rId);
        StringBuilder builder = new StringBuilder("if");
        AntecedentConnectionType type = getRuleType(rId);
        for (int i = 0; i < antecedents.size(); i++) {
            builder.append(" ").append(antecedents.get(i));
            if (i != antecedents.size() - 1) {
                if (type == AntecedentConnectionType.And)
                    builder.append(" and");
                else
                    builder.append(" or");
            }
        }
        return builder.toString();
    }

    private String getConsequent(int rId) {
        List<String> consequents = consequentRepository.getConsequentsText(rId);
        StringBuilder builder = new StringBuilder(" then");
        for (int i = 0; i < consequents.size(); i++) {
            builder.append(" ").append(consequents.get(i));
            if (i != consequents.size() - 1)
                builder.append(" and");
        }
        return builder.toString();
    }

    public List<String> getRules(int sId) {
        List<String> rulesText = new ArrayList<>();
        rules = ruleDao.getAll(sId);
        for (DBRule rule : rules) {
            String builder = getAntecedent(rule.getId()) +
                    getConsequent(rule.getId());
            rulesText.add(builder);
            System.out.println(builder);
        }
        System.out.println("out from rulesrepo");
        return rulesText;
    }

    public List<String> getAntecedents(int i) {
        Integer rId = getRuleIdByIndex(i);
        List<String> ants = new ArrayList<>();
        if (rId != null)
            ants = antecedentRepository.getAntecedentsText(rId);
        return ants;
    }

    public List<String> getConsequents(int i) {
        Integer rId = getRuleIdByIndex(i);
        List<String> cons = new ArrayList<>();
        if (rId != null)
            cons = consequentRepository.getConsequentsText(rId);
        return cons;
    }

    public Integer getRuleIdByIndex(int i) {
        if (rules != null && i < rules.size())
            return rules.get(i).getId();
        return null;
    }

    private List<Antecedent> getAntecedentsInfo(Integer rId) {
        return antecedentRepository.getAntecedents(rId);
    }

    private List<Consequent> getConsequentsInfo(Integer rId) {
        return consequentRepository.getConsequents(rId);
    }

    public Rule getRuleData(int i) {
        Integer rId = getRuleIdByIndex(i);
        if (rId != null) {
            DBRule rule = ruleDao.get(rId);
            return new Rule(
                    rId,
                    getAntecedentsInfo(rId),
                    getConsequentsInfo(rId),
                    rule.getConnectionType()
            );
        }
        return null;
    }
}
