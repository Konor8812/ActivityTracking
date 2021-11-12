package model.dao;

import model.database.ConnectionPool;
import model.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static UserDAO instance;


    public static synchronized UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public void insertUser(String login, String pass) {
        Connection con;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            Statement stmt = con.createStatement();
            stmt.execute(getInsertString(login, pass));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String login) {
        Connection con;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            Statement stmt = con.createStatement();
            stmt.execute(getDeleteUserString(login));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllUsers() {
        Connection con;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            Statement stmt = con.createStatement();
            stmt.execute(getClearAllUsersExceptAdmin());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getUsersList(){
        List<User> users = new ArrayList<>();
        Connection con;
        ResultSet rs;
        try{
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(getUsersListString());
            while(rs.next()){
                users.add(new User(rs.getString("login")));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getLogedInUser(String login){
        User user = null;
        Connection con = null;
        ResultSet rs = null;
        try{
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            Statement stmt = con.createStatement();
            rs=stmt.executeQuery(getLogedInUserString(login));
            if(rs.next()){
                String role = "user";
                if(rs.getString("role").equals("admin")){
                    role = "admin";
                }
                user = new User(rs.getString("login"), rs.getString("password"), role);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return user;
    }

    private String getLogedInUserString(String login) {
        return String.format("select * from user where login='%s'", login);
    }


    private String getUsersListString() {
        return "select * from user where role='user'";
    }


    private String getInsertString(String login, String pass) {
        return String.format("INSERT INTO user (login, password) VALUES ('%s', '%s')", login, pass);
    }
    private String getDeleteUserString(String login){
        return String.format("DELETE FROM user WHERE login='%s';", login);
    }

    private String getClearAllUsersExceptAdmin(){
        return"DELETE FROM user WHERE role='user';";
    }

//    private Statement getStatement(){
//        Statement stmt = null;
//        Connection con;
//        try {
//            ConnectionPool cp = ConnectionPool.getInstance();
//            con = cp.getConnection();
//            stmt = con.createStatement();
//        } catch(SQLException e){
//
//        }
//        return stmt;
//    }
}
