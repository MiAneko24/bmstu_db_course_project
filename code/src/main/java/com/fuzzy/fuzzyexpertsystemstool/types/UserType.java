package com.fuzzy.fuzzyexpertsystemstool.types;

public enum UserType {
    User("user"),
    InformaticsExpert("informatics"),
    PhysicsExpert("physics"),
    ChemistryExpert("chemistry"),
    Admin("admin");

    private String text;

    private UserType(String t) {
        text = t;
    }

    @Override
    public String toString() {
        return text;
    }

    public static UserType getUserType(String s) {
        UserType userType;
        switch (s) {
            case "sociology":
                userType = InformaticsExpert;
                break;
            case "physics":
                userType = PhysicsExpert;
                break;
            case "chemistry":
                userType = ChemistryExpert;
                break;
            case "admin":
                userType = Admin;
                break;
            default:
                userType = User;
                break;
        }
        return userType;
    }
}
