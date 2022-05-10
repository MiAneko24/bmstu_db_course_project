package com.fuzzy.fuzzyexpertsystemstool.parsers;

import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBAntecedent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AntecedentParser implements Parser<DBAntecedent> {
    @Override
    public DBAntecedent parse(ResultSet res) {
        DBAntecedent ant = null;
        try {
            if (res.next())
                ant = new DBAntecedent(res.getInt("a_id"), res.getInt("m_id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ant;
    }
}
