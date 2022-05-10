package com.fuzzy.fuzzyexpertsystemstool.parsers;

import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBMembershipFunction;
import com.fuzzy.fuzzyexpertsystemstool.types.BarrierType;
import com.fuzzy.fuzzyexpertsystemstool.types.FunctionType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MembershipFunctionParser implements Parser<DBMembershipFunction> {


    @Override
    public DBMembershipFunction parse(ResultSet res) {
        DBMembershipFunction mf = null;
        try {
            if (res.next()) {
                int mId = res.getInt("m_id");
                String term = res.getString("term");
                FunctionType fType = FunctionType.getFunctionType(res.getString("m_type"));
                Integer vId = res.getInt("v_id");
                if (res.wasNull())
                    vId = null;
                Double parameter1 = res.getDouble("parameter1");
                if (res.wasNull())
                    parameter1 = null;
                Double parameter2 = res.getDouble("parameter2");
                if (res.wasNull())
                    parameter2 = null;
                Double parameter3 = res.getDouble("parameter3");
                if (res.wasNull())
                    parameter3 = null;
                Double parameter4 = res.getDouble("parameter4");
                if (res.wasNull())
                    parameter4 = null;
                Double value = res.getDouble("m_value");
                if (res.wasNull())
                    value = null;
                Integer pid = res.getInt("p_id");
                if (res.wasNull())
                    pid = null;
                mf = new DBMembershipFunction(mId,
                        term,
                        fType,
                        vId,
                        parameter1,
                        parameter2,
                        parameter3,
                        parameter4,
                        value,
                        pid,
                        BarrierType.getBarrierType(res.getString("barrier")),
                        res.getBoolean("is_active")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mf;
    }
}
