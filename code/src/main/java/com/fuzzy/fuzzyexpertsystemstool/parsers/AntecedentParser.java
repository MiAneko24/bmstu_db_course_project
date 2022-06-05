package com.fuzzy.fuzzyexpertsystemstool.parsers;

import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBAntecedent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AntecedentParser implements Parser<DBAntecedent> {
    @Override
    public DBAntecedent parse(ResultSet res) {
        DBAntecedent ant = null;
        try {
            if (res.next()) {
                int a = res.getInt("a_id");
                Integer m = res.getInt("m_id");
                if (res.wasNull())
                    m = null;
                ant = new DBAntecedent(a, m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ant;
    }
}
