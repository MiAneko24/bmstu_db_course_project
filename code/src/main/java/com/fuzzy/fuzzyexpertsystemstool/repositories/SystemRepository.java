package com.fuzzy.fuzzyexpertsystemstool.repositories;

import com.fuzzy.fuzzyexpertsystemstool.dao.Dao;
import com.fuzzy.fuzzyexpertsystemstool.dao.SystemDao;
import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBSystem;

import java.util.ArrayList;
import java.util.List;

public class SystemRepository {
    private final Dao<DBSystem> systemDao = new SystemDao();

    private List<DBSystem> systems = null;

    public List<String> getSystemList() {
        List<String> result = new ArrayList<>();
        systems = systemDao.getAll(0);
        if (systems == null)
            return null;
        for (DBSystem s : systems) {
            result.add(s.getName() + " (" + s.getType() + ")");
        }
        return result;
    }

    public Integer getSystemIdByIndex(int i) {
        if (systems != null && i >= 0)
            return systems.get(i).getId();
        return null;
    }

    public DBSystem getSystem(int sId) {
        return systemDao.get(sId);
    }
}
