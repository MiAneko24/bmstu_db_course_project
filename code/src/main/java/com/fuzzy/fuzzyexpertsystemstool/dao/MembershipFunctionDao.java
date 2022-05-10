package com.fuzzy.fuzzyexpertsystemstool.dao;

import com.fuzzy.fuzzyexpertsystemstool.database.DBConnection;
import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBMembershipFunction;
import com.fuzzy.fuzzyexpertsystemstool.parsers.MembershipFunctionParser;
import com.fuzzy.fuzzyexpertsystemstool.parsers.Parser;
import com.fuzzy.fuzzyexpertsystemstool.types.UserType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MembershipFunctionDao implements Dao<DBMembershipFunction> {
    private final DBConnection connection = DBConnection.getInstance();
    private final Parser<DBMembershipFunction> parser = new MembershipFunctionParser();

    @Override
    public DBMembershipFunction get(int id) {
        DBMembershipFunction mf = null;
        if (connection.isValid()) {
            PreparedStatement st = connection.getPreparedStatement("select * from membership_function where m_id = " + id + ";");
            try {
                ResultSet res = st.executeQuery();
                mf = parser.parse(res);
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.closePreparedStatement(st);
            }
        }
        return mf;
    }

    @Override
    public List<DBMembershipFunction> getAll(int id) {
        List<DBMembershipFunction> mfs = null;
        if (connection.isValid()) {
            PreparedStatement st = connection.getPreparedStatement("select * from membership_function where v_id = " + id +
                    " and m_type != 'crisp' and m_type != 'linear'" + ";");
            try {
                ResultSet res = st.executeQuery();
                mfs = parser.parseList(res);
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.closePreparedStatement(st);
            }
        }
        return mfs;
    }

    @Override
    public void save(DBMembershipFunction dbMembershipFunction) {
        if (connection.isValid()) {
            List<UserType> types = connection.getUserTypes();
            if (types == null)
                return;
            StringBuilder query = new StringBuilder("insert into");
            if (types.contains(UserType.Admin))
                query.append(" membership_function");
            else if (types.contains(UserType.ChemistryExpert))
                query.append(" chemistry_experts_membership_functions");
            else if (types.contains(UserType.PhysicsExpert))
                query.append(" physics_expert_membership_functions");
            else if (types.contains(UserType.InformaticsExpert))
                query.append(" informatics_expert_membership_functions");
            else
                return;
            query.append(" values" + "(")
                    .append(dbMembershipFunction.getId()).append(", ")
                    .append(dbMembershipFunction.getTerm()).append(", ")
                    .append(dbMembershipFunction.getmType()).append(", ")
                    .append(dbMembershipFunction.getVariableId()).append(", ")
                    .append(dbMembershipFunction.getParameter1()).append(", ")
                    .append(dbMembershipFunction.getParameter2()).append(", ")
                    .append(dbMembershipFunction.getParameter3()).append(", ")
                    .append(dbMembershipFunction.getParameter4())
                    .append(", null, ")
                    .append(dbMembershipFunction.getPid()).append(", ")
                    .append(dbMembershipFunction.getBarrier()).append(", ")
                    .append(dbMembershipFunction.isActive()).append(");");
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
    public void update(DBMembershipFunction dbMembershipFunction) {
        if (connection.isValid()) {
            List<UserType> types = connection.getUserTypes();
            if (types == null)
                return;
            StringBuilder query = new StringBuilder("update");
            if (types.contains(UserType.Admin))
                query.append(" membership_function");
            else if (types.contains(UserType.ChemistryExpert))
                query.append(" chemistry_experts_membership_functions");
            else if (types.contains(UserType.PhysicsExpert))
                query.append(" physics_expert_membership_functions");
            else if (types.contains(UserType.InformaticsExpert))
                query.append(" informatics_expert_membership_functions");
            else
                return;
            query.append(" set term = ").append(dbMembershipFunction.getTerm())
                    .append(", m_type = ").append(dbMembershipFunction.getmType())
                    .append(", v_id = ").append(dbMembershipFunction.getVariableId())
                    .append(", parameter1 = ").append(dbMembershipFunction.getParameter1())
                    .append(", parameter2 = ").append(dbMembershipFunction.getParameter2())
                    .append(", parameter3 = ").append(dbMembershipFunction.getParameter3())
                    .append(", parameter4 = ").append(dbMembershipFunction.getParameter4())
                    .append(", p_id=").append(dbMembershipFunction.getPid())
                    .append(", barrier = ").append(dbMembershipFunction.getBarrier())
                    .append(", is_active = ").append(dbMembershipFunction.isActive())
                    .append(" where m_id = ").append(dbMembershipFunction.getId()).append(";");
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
    public void delete(DBMembershipFunction dbMembershipFunction) {
        if (connection.isValid()) {
            List<UserType> types = connection.getUserTypes();
            if (types == null)
                return;
            StringBuilder query = new StringBuilder("delete from");
            if (types.contains(UserType.Admin))
                query.append(" membership_function");
            else if (types.contains(UserType.ChemistryExpert))
                query.append(" chemistry_experts_membership_functions");
            else if (types.contains(UserType.PhysicsExpert))
                query.append(" physics_expert_membership_functions");
            else if (types.contains(UserType.InformaticsExpert))
                query.append(" informatics_expert_membership_functions");
            else
                return;
            query.append(" where m_id = ").append(dbMembershipFunction.getId()).append(";");
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
