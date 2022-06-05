package com.fuzzy.fuzzyexpertsystemstool.parsers;

import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBConsequent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsequentParser implements Parser<DBConsequent> {
    @Override
    public DBConsequent parse(ResultSet res) {
        DBConsequent cons = null;
        try {
            if (res.next()) {
                int id = res.getInt("c_id");
                Integer mId = res.getInt("m_id");
                if (res.wasNull())
                    mId = null;
                int rId = res.getInt("r_id");
                Integer vId = res.getInt("v_id");
                if (res.wasNull())
                    vId = null;
                cons = new DBConsequent(id, mId, rId, vId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cons;
    }
//
//    @Override
//    public List<DBConsequent> parseList(ResultSet res) {
//        List<DBConsequent> cons = new ArrayList<>();
//        DBConsequent next;
//        while ((next = parse(res)) != null)
//            cons.add(next);
//        return cons;
//    }
}
