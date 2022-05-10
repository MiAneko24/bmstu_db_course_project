package com.fuzzy.fuzzyexpertsystemstool.parsers;

import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBRuleAntecedents;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RuleAntecedentsParser implements Parser<DBRuleAntecedents> {
    @Override
    public DBRuleAntecedents parse(ResultSet res) {
        DBRuleAntecedents ruleAntecedents = null;
        try {
            if (res.next())
                ruleAntecedents = new DBRuleAntecedents(res.getInt("r_id"), res.getInt("a_id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ruleAntecedents;
    }
}
