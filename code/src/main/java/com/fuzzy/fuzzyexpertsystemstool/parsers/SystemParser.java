package com.fuzzy.fuzzyexpertsystemstool.parsers;

import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBSystem;
import com.fuzzy.fuzzyexpertsystemstool.types.SpecializationType;
import com.fuzzy.fuzzyexpertsystemstool.types.SystemType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SystemParser implements Parser<DBSystem> {
    @Override
    public DBSystem parse(ResultSet res) {
        DBSystem system = null;
        try {
            if (res.next())
                system = new DBSystem(res.getInt("s_id"), res.getString("s_name"),
                    SystemType.getSystemType(res.getString("s_type")),
                    SpecializationType.getSpecializationType(res.getString("specialization")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return system;
    }
}
