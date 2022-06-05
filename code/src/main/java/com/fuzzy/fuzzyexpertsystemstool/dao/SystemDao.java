package com.fuzzy.fuzzyexpertsystemstool.dao;

import com.fuzzy.fuzzyexpertsystemstool.database.DBConnection;
import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBSystem;
import com.fuzzy.fuzzyexpertsystemstool.parsers.Parser;
import com.fuzzy.fuzzyexpertsystemstool.parsers.SystemParser;
import com.fuzzy.fuzzyexpertsystemstool.types.UserType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SystemDao implements Dao<DBSystem> {
    DBConnection connection = DBConnection.getInstance();
    Parser<DBSystem> parser = new SystemParser();

    @Override
    public DBSystem get(int id) {
        DBSystem system = null;
        if (connection.isValid()) {
            PreparedStatement st = connection.getPreparedStatement("select * from system where s_id = " + id + ";");
            try {
                ResultSet res = st.executeQuery();
                system = parser.parse(res);
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.closePreparedStatement(st);
            }
        }
        return system;
    }

    @Override
    public List<DBSystem> getAll(int id) {
        List<DBSystem> systems = new ArrayList<>();
        if (connection.isValid()) {
            PreparedStatement st = connection.getPreparedStatement("select * from system");
            try {
                ResultSet res = st.executeQuery();
                systems = parser.parseList(res);
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.closePreparedStatement(st);
            }
        }
        return systems;
    }

    @Override
    public void save(DBSystem dbSystem) {
        if (connection.isValid()) {
            List<UserType> types = connection.getUserTypes();
            if (types == null || !types.contains(UserType.Admin)) {
                System.out.println("Cry");
                return;
            }
            if (get(dbSystem.getId()) != null) {
                update(dbSystem);
                return;
            }
            System.out.println("welelelel");
            PreparedStatement st = connection.getPreparedStatement("insert into system values" +
                    "(" + dbSystem.getId() + ", '" + dbSystem.getName() +
                    "', '" + dbSystem.getType() + "', '" + dbSystem.getSpecialization() + "');");
            try {
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.closePreparedStatement(st);
            }
        }
    }

    @Override
    public void update(DBSystem dbSystem) {
        if (connection.isValid()) {
            List<UserType> types = connection.getUserTypes();
            if (types == null || !types.contains(UserType.Admin))
                return;
            PreparedStatement st = connection.getPreparedStatement("update system set s_name = '"
                    + dbSystem.getName() + "', s_type = '" + dbSystem.getType() +
                    "', specialization = '" + dbSystem.getSpecialization() + "' where s_id = " + dbSystem.getId() + ";");
            try {
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.closePreparedStatement(st);
            }
        }
    }

    @Override
    public void delete(DBSystem dbSystem) {
        if (connection.isValid()) {
            List<UserType> types = connection.getUserTypes();
            if (types == null || !types.contains(UserType.Admin))
                return;
            PreparedStatement st = connection.getPreparedStatement("delete from system where s_id = " + dbSystem.getId() + ";");
            try {
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.closePreparedStatement(st);
            }
        }
    }
}
