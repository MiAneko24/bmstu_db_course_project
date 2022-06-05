package com.fuzzy.fuzzyexpertsystemstool.dao;

import com.fuzzy.fuzzyexpertsystemstool.database.DBConnection;
import com.fuzzy.fuzzyexpertsystemstool.dbmodel.OutputResult;
import com.fuzzy.fuzzyexpertsystemstool.parsers.OutputResultParser;
import com.fuzzy.fuzzyexpertsystemstool.parsers.Parser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OutputResultDao implements Dao<OutputResult> {
    DBConnection connection = DBConnection.getInstance();
    Parser<OutputResult> parser = new OutputResultParser();
    @Override
    public OutputResult get(int id) {
        return null;
    }

    @Override
    public List<OutputResult> getAll(int id) {
        PreparedStatement st = connection.getPreparedStatement("select * from get_output(" + id + ");");
        List<OutputResult> outputResult = null;
        try {
            ResultSet res = st.executeQuery();
            outputResult = parser.parseList(res);
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closePreparedStatement(st);
        }
        return outputResult;
    }

    @Override
    public void save(OutputResult outputResult) {

    }

    @Override
    public void update(OutputResult outputResult) {

    }

    @Override
    public void delete(OutputResult outputResult) {

    }
}
