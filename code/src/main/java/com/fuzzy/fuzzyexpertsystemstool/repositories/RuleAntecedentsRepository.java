package com.fuzzy.fuzzyexpertsystemstool.repositories;

import com.fuzzy.fuzzyexpertsystemstool.dao.Dao;
import com.fuzzy.fuzzyexpertsystemstool.dao.RuleAntecedentsDao;
import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBRuleAntecedents;

public class RuleAntecedentsRepository {
    private Dao<DBRuleAntecedents> ruleAntecedentsRepositoryDao = new RuleAntecedentsDao();

    public void save(DBRuleAntecedents dbRuleAntecedents) {
        if (dbRuleAntecedents != null) {
            ruleAntecedentsRepositoryDao.save(dbRuleAntecedents);
        }
    }
}
