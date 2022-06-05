package com.fuzzy.fuzzyexpertsystemstool.dao;

import com.fuzzy.fuzzyexpertsystemstool.database.DBConnection;
import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBAntecedent;
import com.fuzzy.fuzzyexpertsystemstool.parsers.AntecedentParser;
import com.fuzzy.fuzzyexpertsystemstool.parsers.Parser;
import com.fuzzy.fuzzyexpertsystemstool.types.UserType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AntecedentDao implements Dao<DBAntecedent> {
    private final DBConnection connection = DBConnection.getInstance();
    private final Parser<DBAntecedent> parser = new AntecedentParser();
    @Override
    public DBAntecedent get(int id) {
        DBAntecedent ant = null;
        if (connection.isValid()) {
            PreparedStatement st = connection.getPreparedStatement("select * from antecedent where a_id = " + id + ";");
            try {
                ResultSet res = st.executeQuery();
                ant = parser.parse(res);
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.closePreparedStatement(st);
            }
        }
        return ant;
    }

    @Override
    public List<DBAntecedent> getAll(int id) {
        List<DBAntecedent> ant = null;
        if (connection.isValid()) {
            PreparedStatement st = connection.getPreparedStatement("select * from public.antecedent where a_id in " +
                    "(select a_id from rule_antecedents ra where ra.r_id = " + id + ");");
            try {
                ResultSet res = st.executeQuery();
                ant = parser.parseList(res);
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.closePreparedStatement(st);
            }
        }
        return ant;
    }

    @Override
    public void save(DBAntecedent dbAntecedent) {
        if (connection.isValid()) {
            List<UserType> types = connection.getUserTypes();
            if (types == null)
                return;
            if (get(dbAntecedent.getId()) != null) {
                update(dbAntecedent);
                return;
            }
            StringBuilder query = new StringBuilder("insert into");
            if (types.contains(UserType.Admin))
                query.append(" antecedent");
            else if (types.contains(UserType.ChemistryExpert))
                query.append(" chemistry_experts_antecedents");
            else if (types.contains(UserType.PhysicsExpert))
                query.append(" physics_expert_antecedents");
            else if (types.contains(UserType.InformaticsExpert))
                query.append(" informatics_expert_antecedents");
            else
                return;
            query.append(" values (")
                    .append(dbAntecedent.getId())
                    .append(", ")
                    .append(dbAntecedent.getMembershipFunctionId())
                    .append(");");
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
    public void update(DBAntecedent dbAntecedent) {
        if (connection.isValid()) {
            List<UserType> types = connection.getUserTypes();
            if (types == null)
                return;
            StringBuilder query = new StringBuilder("update");
            if (types.contains(UserType.Admin))
                query.append(" antecedent");
            else if (types.contains(UserType.ChemistryExpert))
                query.append(" chemistry_experts_antecedents");
            else if (types.contains(UserType.PhysicsExpert))
                query.append(" physics_expert_antecedents");
            else if (types.contains(UserType.InformaticsExpert))
                query.append(" informatics_expert_antecedents");
            else
                return;
            query.append(" set m_id = ")
                    .append(dbAntecedent.getMembershipFunctionId())
                    .append(" where a_id = ")
                    .append(dbAntecedent.getId())
                    .append(";");
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
    public void delete(DBAntecedent dbAntecedent) {
        if (connection.isValid()) {
            List<UserType> types = connection.getUserTypes();
            if (types == null)
                return;
            StringBuilder query = new StringBuilder("delete from");
            if (types.contains(UserType.Admin))
                query.append(" antecedent");
            else if (types.contains(UserType.ChemistryExpert))
                query.append(" chemistry_experts_antecedents");
            else if (types.contains(UserType.PhysicsExpert))
                query.append(" physics_expert_antecedents");
            else if (types.contains(UserType.InformaticsExpert))
                query.append(" informatics_expert_antecedents");
            else
                return;
            query.append(" where a_id = ").append(dbAntecedent.getId()).append(";");
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
