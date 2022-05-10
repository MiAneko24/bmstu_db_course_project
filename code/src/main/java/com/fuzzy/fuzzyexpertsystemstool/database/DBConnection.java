package com.fuzzy.fuzzyexpertsystemstool.database;

import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBUser;
import com.fuzzy.fuzzyexpertsystemstool.parsers.Parser;
import com.fuzzy.fuzzyexpertsystemstool.parsers.StringParser;
import com.fuzzy.fuzzyexpertsystemstool.types.UserType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
public class DBConnection {
    private static DBConnection instance = null;
    private DBUser currentUser;
    private Parser<String> parser = new StringParser();
    private Connection conn = null;
    private String url = "jdbc:postgresql://172.17.0.3:5432/postgres";

    public DBConnection() {

    }

    public PreparedStatement getPreparedStatement(String query) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return st;
    }

    public void closePreparedStatement(PreparedStatement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static DBConnection getInstance() {
        if (instance == null)
            instance = new DBConnection();
        return instance;
    }

    public List<UserType> getUserTypes() {
        if (currentUser != null) {
            return currentUser.getUserTypes();
        }
        return null;
    }

    private DBUser getUser() {
        DBUser usr = null;
        PreparedStatement st = getPreparedStatement("select * from get_roles();");
        try{
            List<String> strings = parser.parseList(st.executeQuery());
            if (strings.contains("admin")) {
                strings.removeIf(s -> !Objects.equals(s, "admin"));
            }
            usr = new DBUser();
            usr.setRoles(strings);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usr;
    }

    private State _connect(Properties props) {
        State s = State.Success;
        try {
            conn = DriverManager.getConnection(url, props);
            System.out.println("Success");
        } catch (Exception e) {
            s = State.Fail;
            e.printStackTrace();
            System.out.println("Fail");
        }
        currentUser = getUser();
        return s;
    }

    public DBUser getCurrentUser() {
        return currentUser;
    }

    public State connect() {
        Properties props = new Properties();
        props.setProperty("user","usr");
        props.setProperty("password","123");
        return _connect(props);
    }

    public State connect(String login, String password) {
        Properties props = new Properties();
        props.setProperty("user", login);
        props.setProperty("password", password);
//        props.setProperty("")
        return _connect(props);
    }

    public void disconnect() {
        boolean opened = conn != null;
        Boolean closed = null;
        try {
            closed = opened && conn.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closed = closed == null || closed;
        try {
            if (opened && !closed)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn = null;
    }

    public boolean isValid() {
        boolean valid = false;
        try {
            if (conn != null && !conn.isClosed())
                valid = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return valid;
    }
}
