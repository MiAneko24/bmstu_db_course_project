package com.fuzzy.fuzzyexpertsystemstool.dao;

import com.fuzzy.fuzzyexpertsystemstool.database.DBConnection;
import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBRule;
import com.fuzzy.fuzzyexpertsystemstool.parsers.Parser;
import com.fuzzy.fuzzyexpertsystemstool.parsers.RuleParser;
import com.fuzzy.fuzzyexpertsystemstool.types.UserType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RuleDao implements Dao<DBRule> {
    private final DBConnection connection = DBConnection.getInstance();
    private final Parser<DBRule> parser = new RuleParser();
    @Override
    public DBRule get(int id) {
        DBRule rule = null;
        if (connection.isValid()) {
            PreparedStatement st = connection.getPreparedStatement("select * from rule where r_id = " + id + ";");
            try {
                ResultSet res = st.executeQuery();
                rule = parser.parse(res);
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.closePreparedStatement(st);
            }
        }
        return rule;
    }

    @Override
    public List<DBRule> getAll(int id) {
        List<DBRule> rules = new ArrayList<>();
        if (connection.isValid()) {
            PreparedStatement st = connection.getPreparedStatement("select * from rule where s_id = " + id + ";");
            try {
                ResultSet res = st.executeQuery();
                rules.addAll(parser.parseList(res));
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.closePreparedStatement(st);
            }
        }
        System.out.println("From rule dao");
        for (DBRule r : rules) {
            System.out.println("r : " + r.getId());
        }
        return rules;
    }

    @Override
    public void save(DBRule dbRule) {
        if (connection.isValid()) {
            List<UserType> types = connection.getUserTypes();
            if (types == null)
                return;
            if (get(dbRule.getId()) != null) {
                update(dbRule);
                return;
            }
            StringBuilder query = new StringBuilder("insert into");
            if (types.contains(UserType.Admin))
                query.append(" rule");
            else if (types.contains(UserType.ChemistryExpert))
                query.append(" chemistry_experts_rules");
            else if (types.contains(UserType.PhysicsExpert))
                query.append(" physics_expert_rules");
            else if (types.contains(UserType.InformaticsExpert))
                query.append(" informatics_expert_rules");
            else
                return;
            query.append(" values" + "(")
                    .append(dbRule.getId()).append(", ")
                    .append(dbRule.getSystemId()).append(", '")
                    .append(dbRule.getConnectionType()).append("', ")
                    .append(dbRule.getWeight()).append(");");
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
    public void update(DBRule dbRule) {
        if (connection.isValid()) {
            List<UserType> types = connection.getUserTypes();
            if (types == null)
                return;
            StringBuilder query = new StringBuilder("update");
            if (types.contains(UserType.Admin))
                query.append(" rule");
            else if (types.contains(UserType.ChemistryExpert))
                query.append(" chemistry_experts_rules");
            else if (types.contains(UserType.PhysicsExpert))
                query.append(" physics_expert_rules");
            else if (types.contains(UserType.InformaticsExpert))
                query.append(" informatics_expert_rules");
            else
                return;
            query.append(" set s_id = ").append(dbRule.getSystemId())
                    .append(", antecedent_connection = '").append(dbRule.getConnectionType())
                    .append("', weight = ").append(dbRule.getWeight())
                    .append(" where r_id = ").append(dbRule.getId()).append(";");
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
    public void delete(DBRule dbRule) {
        if (connection.isValid()) {
            List<UserType> types = connection.getUserTypes();
            if (types == null)
                return;
            StringBuilder query = new StringBuilder("delete from");
            if (types.contains(UserType.Admin))
                query.append(" rule");
            else if (types.contains(UserType.ChemistryExpert))
                query.append(" chemistry_experts_rules");
            else if (types.contains(UserType.PhysicsExpert))
                query.append(" physics_expert_rules");
            else if (types.contains(UserType.InformaticsExpert))
                query.append(" informatics_expert_rules");
            else
                return;
            query.append(" where r_id = ").append(dbRule.getId()).append(";");
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
