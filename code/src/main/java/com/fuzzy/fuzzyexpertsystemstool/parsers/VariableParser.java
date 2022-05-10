package com.fuzzy.fuzzyexpertsystemstool.parsers;

import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBVariable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VariableParser implements Parser<DBVariable> {

    @Override
    public DBVariable parse(ResultSet res) {
        DBVariable var = null;
        try {
            if (res.next()) {
                var = new DBVariable(res.getInt("v_id"),
                        res.getString("v_name"),
                        res.getDouble("min_value"),
                        res.getDouble("max_value"),
                        res.getDouble("v_value"),
                        res.getInt("s_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return var;
    }

//    @Override
//    public List<DBVariable> parseList(ResultSet res) {
//        List<DBVariable> variables = new ArrayList<>();
//        try {
//            while (res.next())
//                variables.add(parse(res));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return variables;
//    }
}
