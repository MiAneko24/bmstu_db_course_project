package com.fuzzy.fuzzyexpertsystemstool.parsers;

import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBRule;
import com.fuzzy.fuzzyexpertsystemstool.types.AntecedentConnectionType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RuleParser implements Parser<DBRule> {
    @Override
    public DBRule parse(ResultSet res) {
        DBRule r = null;
        try {
            if (res.next())
                r = new DBRule(res.getInt("r_id"), res.getInt("s_id"),
                    AntecedentConnectionType.getAntecedentConnectionType(res.getString("antecedent_connection")),
                    res.getDouble("weight"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r;
    }
}
