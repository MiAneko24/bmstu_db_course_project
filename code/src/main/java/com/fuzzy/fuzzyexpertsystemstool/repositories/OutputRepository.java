package com.fuzzy.fuzzyexpertsystemstool.repositories;

import com.fuzzy.fuzzyexpertsystemstool.dao.Dao;
import com.fuzzy.fuzzyexpertsystemstool.dao.OutputResultDao;
import com.fuzzy.fuzzyexpertsystemstool.dbmodel.OutputResult;

import java.util.List;

public class OutputRepository {
    private final Dao<OutputResult> outputResultDao = new OutputResultDao();

    public List<OutputResult> getOutput(int sId) {
        return outputResultDao.getAll(sId);
    }
}
