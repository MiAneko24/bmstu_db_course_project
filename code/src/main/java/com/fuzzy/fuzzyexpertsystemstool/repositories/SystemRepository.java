package com.fuzzy.fuzzyexpertsystemstool.repositories;

import com.fuzzy.fuzzyexpertsystemstool.dao.Dao;
import com.fuzzy.fuzzyexpertsystemstool.dao.SystemDao;
import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBSystem;
import com.fuzzy.fuzzyexpertsystemstool.model.FSystem;

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

    public FSystem getSystem(int sId) {
        DBSystem system = systemDao.get(sId);
        return (system != null)
                ? new FSystem(system.getId(), system.getName(), system.getType(), system.getSpecialization())
                : null;
    }

    private DBSystem transform(FSystem system) {
        return new DBSystem(system.getId(), system.getName(), system.getType(),
                system.getSpecialization());
    }

    public List<String> save(FSystem system) {
        List<String> res=  null;
        if (system != null) {
            systemDao.save(transform(system));
            res = getSystemList();
        }
        return res;
    }

    public List<String> delete(FSystem system) {
        List<String> res=  null;
        if (system != null) {
            systemDao.delete(transform(system));
            res = getSystemList();
        }
        return res;
    }

}
