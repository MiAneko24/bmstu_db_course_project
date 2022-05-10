package com.fuzzy.fuzzyexpertsystemstool.parsers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public interface Parser<T> {
    T parse(ResultSet res);

    default List<T> parseList(ResultSet res) {
        List<T> ants = new ArrayList<>();
        T next;
        while((next = parse(res)) != null) {
            ants.add(next);
        }
        return ants;
    }
}
