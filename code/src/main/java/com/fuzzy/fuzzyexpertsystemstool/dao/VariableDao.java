package com.fuzzy.fuzzyexpertsystemstool.dao;

import com.fuzzy.fuzzyexpertsystemstool.database.DBConnection;
import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBVariable;
import com.fuzzy.fuzzyexpertsystemstool.parsers.Parser;
import com.fuzzy.fuzzyexpertsystemstool.parsers.VariableParser;
import com.fuzzy.fuzzyexpertsystemstool.types.UserType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VariableDao implements Dao<DBVariable> {
    DBConnection connection = DBConnection.getInstance();
    Parser<DBVariable> parser = new VariableParser();

    @Override
    public DBVariable get(int id) {
        DBVariable variable = null;
        if (connection.isValid()) {
            PreparedStatement st = connection.getPreparedStatement("select * from variable where v_id = " + id + ";");
            try {
                ResultSet res = st.executeQuery();
                variable = parser.parse(res);
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.closePreparedStatement(st);
            }
        }
        return variable;
    }

    @Override
    public List<DBVariable> getAll(int id) {
        List<DBVariable> variables = null;
        if (connection.isValid()) {
            PreparedStatement st = connection.getPreparedStatement("select * from variable where s_id = " + id + ";");
            try {
                ResultSet res = st.executeQuery();
                variables = parser.parseList(res);
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.closePreparedStatement(st);
            }
        }
        return variables;
    }

    @Override
    public void save(DBVariable dbVariable) {
        Boolean exists = get(dbVariable.getId()) != null;
        System.out.println("try save, "+exists);
        if (connection.isValid() && !exists) {
            List<UserType> types = connection.getUserTypes();
            if (types == null)
                return;
            StringBuilder query = new StringBuilder("insert into");
            if (types.contains(UserType.Admin))
                query.append(" variable");
            else if (types.contains(UserType.ChemistryExpert))
                query.append(" chemistry_experts_variables");
            else if (types.contains(UserType.PhysicsExpert))
                query.append(" physics_expert_variables");
            else if (types.contains(UserType.InformaticsExpert))
                query.append(" informatics_expert_variables");
            else
                return;
            query.append(" values" + "(").append(dbVariable.getId()).append(", '")
                    .append(dbVariable.getName()).append("', ")
                    .append(dbVariable.getMinValue()).append(", ")
                    .append(dbVariable.getMaxValue()).append(", null, ")
                    .append(dbVariable.getSystemId()).append(");");
            PreparedStatement st = connection.getPreparedStatement(query.toString());
            try {
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.closePreparedStatement(st);
            }
        } else if (exists) {
            System.out.println("Try upd");
            update(dbVariable);
        }
    }

    private String getValueUser(DBVariable dbVariable) {
        return "call set_variable_value(" + dbVariable.getId() + ", " + dbVariable.getValue() + ");";
    }

    @Override
    public void update(DBVariable dbVariable) {
        if (connection.isValid()) {
            List<UserType> types = connection.getUserTypes();
            if (types == null) {
                System.out.println("SAD");
                return;
            }
            StringBuilder query = new StringBuilder("update");
            if (types.contains(UserType.Admin)) {
                query.append(" variable");
                System.out.println("u are an ADMIN YEP");
            }
            else if (types.contains(UserType.ChemistryExpert))
                query.append(" chemistry_experts_variables");
            else if (types.contains(UserType.PhysicsExpert))
                query.append(" physics_expert_variables");
            else if (types.contains(UserType.InformaticsExpert))
                query.append(" informatics_expert_variables");
            query.append(" set v_name = '").append(dbVariable.getName())
                    .append("', min_value = ").append(dbVariable.getMinValue())
                    .append(", max_value = ").append(dbVariable.getMaxValue())
                    .append(", v_value = ").append(dbVariable.getValue())
                    .append(", s_id = ").append(dbVariable.getSystemId())
                    .append(" where v_id = ").append(dbVariable.getId()).append(";");
            PreparedStatement st;
            if (types.contains(UserType.User)) {
                st = connection.getPreparedStatement(getValueUser(dbVariable));
            }
            else
                st = connection.getPreparedStatement(query.toString());
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
    public void delete(DBVariable dbVariable) {
        if (connection.isValid()) {
            List<UserType> types = connection.getUserTypes();
            if (types == null)
                return;
            StringBuilder query = new StringBuilder("delete from");
            if (types.contains(UserType.Admin))
                query.append(" variable");
            else if (types.contains(UserType.ChemistryExpert))
                query.append(" chemistry_experts_variables");
            else if (types.contains(UserType.PhysicsExpert))
                query.append(" physics_expert_variables");
            else if (types.contains(UserType.InformaticsExpert))
                query.append(" informatics_expert_variables");
            else
                return;
            query.append(" where v_id = ").append(dbVariable.getId()).append(";");
            PreparedStatement st = connection.getPreparedStatement(query.toString());
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
