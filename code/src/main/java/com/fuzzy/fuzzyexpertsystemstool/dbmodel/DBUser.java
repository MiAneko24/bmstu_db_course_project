package com.fuzzy.fuzzyexpertsystemstool.dbmodel;

import com.fuzzy.fuzzyexpertsystemstool.types.UserType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DBUser {
    private List<UserType> userTypes = null;

    public void setRoles(List<String> roles) {
        userTypes = new ArrayList<>();
        for (String s : roles)
            if (!Objects.equals(s, "expert"))
                userTypes.add(UserType.getUserType(s));
    }

    public DBUser(){}

    public List<UserType> getUserTypes() {
        return userTypes;
    }


}
