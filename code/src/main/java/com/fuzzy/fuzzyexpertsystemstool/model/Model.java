package com.fuzzy.fuzzyexpertsystemstool.model;

import java.util.UUID;

public class Model {
    protected int generateId() {
        UUID uid = UUID.randomUUID();
        String str = "" + uid;
        int id = str.hashCode();
        String filterStr = "" + id;
        str = filterStr.replaceAll("-", "");
        return Integer.parseInt(str);
    }
}
