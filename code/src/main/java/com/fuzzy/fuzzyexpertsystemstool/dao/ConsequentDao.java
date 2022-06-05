package com.fuzzy.fuzzyexpertsystemstool.dao;

import com.fuzzy.fuzzyexpertsystemstool.database.DBConnection;
import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBConsequent;
import com.fuzzy.fuzzyexpertsystemstool.parsers.ConsequentParser;
import com.fuzzy.fuzzyexpertsystemstool.parsers.Parser;
import com.fuzzy.fuzzyexpertsystemstool.types.UserType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ConsequentDao implements Dao<DBConsequent> {
    private final DBConnection connection = DBConnection.getInstance();
    private final Parser<DBConsequent> parser = new ConsequentParser();

    @Override
    public DBConsequent get(int id) {
        DBConsequent cons = null;
        if (connection.isValid()) {
            PreparedStatement st = connection.getPreparedStatement("select * from consequent where c_id = " + id + ";");
            try {
                ResultSet res = st.executeQuery();
                cons = parser.parse(res);
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.closePreparedStatement(st);
            }
        }
        return cons;
    }

    @Override
    public List<DBConsequent> getAll(int id) {
        List<DBConsequent> cons = null;
        if (connection.isValid()) {
            PreparedStatement st = connection.getPreparedStatement("select * from public.consequent where r_id = " + id + ";");
            try {
                ResultSet res = st.executeQuery();
                cons = parser.parseList(res);
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.closePreparedStatement(st);
            }
        }
        return cons;
    }

    @Override
    public void save(DBConsequent dbConsequent) {
        if (connection.isValid()) {
            List<UserType> types = connection.getUserTypes();
            if (types == null)
                return;
            if (get(dbConsequent.getId()) != null) {
                update(dbConsequent);
                return;
            }
            StringBuilder query = new StringBuilder("insert into");
            if (types.contains(UserType.Admin))
                query.append(" consequent");
            else if (types.contains(UserType.ChemistryExpert))
                query.append(" chemistry_experts_consequents");
            else if (types.contains(UserType.PhysicsExpert))
                query.append(" physics_expert_consequents");
            else if (types.contains(UserType.InformaticsExpert))
                query.append(" informatics_expert_consequents");
            else
                return;
            query.append(" values" + "(")
                    .append(dbConsequent.getId())
                    .append(", ")
                    .append(dbConsequent.getMembershipFunctionId())
                    .append(",  ")
                    .append(dbConsequent.getRuleId())
                    .append(", ")
                    .append(dbConsequent.getVariableId()).append(");");
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

    @Override
    public void update(DBConsequent dbConsequent) {
        if (connection.isValid()) {
            List<UserType> types = connection.getUserTypes();
            if (types == null)
                return;
            StringBuilder query = new StringBuilder("update");
            if (types.contains(UserType.Admin))
                query.append(" consequent");
            else if (types.contains(UserType.ChemistryExpert))
                query.append(" chemistry_experts_consequents");
            else if (types.contains(UserType.PhysicsExpert))
                query.append(" physics_expert_consequents");
            else if (types.contains(UserType.InformaticsExpert))
                query.append(" informatics_expert_consequents");
            else
                return;
            query.append(" set m_id = ")
                    .append(dbConsequent.getMembershipFunctionId())
                    .append(", r_id = ")
                    .append(dbConsequent.getRuleId())
                    .append(", v_id = ")
                    .append(dbConsequent.getVariableId())
                    .append(" where c_id = ")
                    .append(dbConsequent.getId()).append(";");
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

    @Override
    public void delete(DBConsequent dbConsequent) {
        if (connection.isValid()) {
            List<UserType> types = connection.getUserTypes();
            if (types == null)
                return;
            StringBuilder query = new StringBuilder("delete from");
            if (types.contains(UserType.Admin))
                query.append(" consequent");
            else if (types.contains(UserType.ChemistryExpert))
                query.append(" chemistry_experts_consequents");
            else if (types.contains(UserType.PhysicsExpert))
                query.append(" physics_expert_consequents");
            else if (types.contains(UserType.InformaticsExpert))
                query.append(" informatics_expert_consequents");
            else
                return;
            query.append(" where c_id = ")
                    .append(dbConsequent.getId()).append(";");
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
