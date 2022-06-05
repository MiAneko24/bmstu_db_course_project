package com.fuzzy.fuzzyexpertsystemstool.parsers;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StringParser implements Parser<String> {
    @Override
    public String parse(ResultSet res) {
        String str = null;
        try {
            if (res.next())
                str = res.getString("usr_role");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return str;
    }

//    @Override
//    public List<String> parseList(ResultSet res) {
//        List<String> strings = new ArrayList<>();
//        String next =
//            while (res.next())
//                strings.add(parse(res));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return strings;
//    }
}
