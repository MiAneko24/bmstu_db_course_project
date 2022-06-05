package com.fuzzy.fuzzyexpertsystemstool.dao;

import com.fuzzy.fuzzyexpertsystemstool.database.DBConnection;
import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBRuleAntecedents;
import com.fuzzy.fuzzyexpertsystemstool.parsers.Parser;
import com.fuzzy.fuzzyexpertsystemstool.parsers.RuleAntecedentsParser;
import com.fuzzy.fuzzyexpertsystemstool.types.UserType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RuleAntecedentsDao implements Dao<DBRuleAntecedents> {
    DBConnection connection = DBConnection.getInstance();
    Parser<DBRuleAntecedents> parser = new RuleAntecedentsParser();

    @Override
    public DBRuleAntecedents get(int id) {
        return null;
    }

    @Override
    public List<DBRuleAntecedents> getAll(int id) {
        List<DBRuleAntecedents> ra = new ArrayList<>();
        if (connection.isValid()) {
            PreparedStatement st = connection.getPreparedStatement("select * from rule_antecedents where r_id = " + id + ";");
            try {
                ResultSet res = st.executeQuery();
                ra = parser.parseList(res);
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.closePreparedStatement(st);
            }
        }
        return ra;
    }

    private DBRuleAntecedents checkIfExist(DBRuleAntecedents dbRuleAntecedents) {
        DBRuleAntecedents ant = null;
        if (connection.isValid()) {
            PreparedStatement st = connection.getPreparedStatement("select * from rule_antecedents where r_id = " + dbRuleAntecedents.getRuleId() +
                    " and a_id = " + dbRuleAntecedents.getAntecendentId() + ";");
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
    public void save(DBRuleAntecedents dbRuleAntecedents) {
        if (connection.isValid()) {
            List<UserType> types = connection.getUserTypes();
            if (types == null)
                return;
            System.out.println("check " + "(r="+ dbRuleAntecedents.getRuleId() + ", a=" + dbRuleAntecedents.getAntecendentId() + ");");
            if (checkIfExist(dbRuleAntecedents) != null) {
                System.out.println("out\n");
                return;
            }
            StringBuilder query = new StringBuilder("insert into");
            if (types.contains(UserType.Admin))
                query.append(" rule_antecedents");
            else if (types.contains(UserType.ChemistryExpert))
                query.append(" chemistry_experts_rule_antecedents");
            else if (types.contains(UserType.PhysicsExpert))
                query.append(" physics_expert_rule_antecedents");
            else if (types.contains(UserType.InformaticsExpert))
                query.append(" informatics_expert_rule_antecedents");
            else
                return;
            query.append(" values" + "(")
                    .append(dbRuleAntecedents.getRuleId()).append(", ")
                    .append(dbRuleAntecedents.getAntecendentId()).append(");");
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
    public void update(DBRuleAntecedents dbRuleAntecedents) {
    }

    @Override
    public void delete(DBRuleAntecedents dbRuleAntecedents) {
        if (connection.isValid()) {
            List<UserType> types = connection.getUserTypes();
            if (types == null)
                return;
            StringBuilder query = new StringBuilder("delete from");
            if (types.contains(UserType.Admin))
                query.append(" rule_antecedents");
            else if (types.contains(UserType.ChemistryExpert))
                query.append(" chemistry_experts_rule_antecedents");
            else if (types.contains(UserType.PhysicsExpert))
                query.append(" physics_expert_rule_antecedents");
            else if (types.contains(UserType.InformaticsExpert))
                query.append(" informatics_expert_rule_antecedents");
            else
                return;
            query.append(" where r_id = ").append(dbRuleAntecedents.getRuleId()).append(" and a_id = ").append(dbRuleAntecedents.getAntecendentId()).append(";");
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
