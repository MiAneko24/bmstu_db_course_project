package com.fuzzy.fuzzyexpertsystemstool.parsers;

import com.fuzzy.fuzzyexpertsystemstool.dbmodel.OutputResult;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OutputResultParser implements Parser<OutputResult> {
    @Override
    public OutputResult parse(ResultSet res) {
        OutputResult output = null;
        try {
            if (res.next()) {
                output = new OutputResult(res.getString("var_name"),
                        res.getDouble("value"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }
}
