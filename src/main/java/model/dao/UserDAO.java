package model.dao;

import model.database.ConnectionPool;
import model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static UserDAO instance;

    private static final String GET_LOGGED_IN_USER = "SELECT * FROM user WHERE login=(?)";
    private static final String GET_USERS_AS_LIST = "SELECT * FROM user WHERE role='user'";
    private static final String INSERT_USER = "INSERT INTO user (login, password) VALUES (?, ?)";
    private static final String DELETE_USER = "DELETE FROM user WHERE login=(?)";
    private static final String DELETE_ALL_USERS = "DELETE FROM user WHERE role='user'";

    public static synchronized UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public void insertUser(String login, String pass) {
        Connection con = null;
        PreparedStatement prstmt = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(INSERT_USER);
            prstmt.setString(1, login);
            prstmt.setString(2, pass);
            prstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            close(prstmt, con);
        }
    }

    public void deleteUser(String login) {
        Connection con = null;
        PreparedStatement prstmt = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(DELETE_USER);
            prstmt.setString(1, login);
            prstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            close(prstmt, con);
        }
    }

    public void deleteAllUsers() {
        Connection con = null;
        Statement stmt = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
           stmt = con.createStatement();
           stmt.execute(DELETE_ALL_USERS);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            close(stmt, con);
        }
    }

    public List<User> getUsersList(){
        List<User> users = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        Statement stmt = null;
        try{
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(GET_USERS_AS_LIST);
            while(rs.next()){
                users.add(new User(rs.getString("login")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            close(rs, stmt, con);
        }
        return users;
    }

    public User getLogedInUser(String login){
        User user = null;
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement prstmt = null;
        try{
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(GET_LOGGED_IN_USER);
            prstmt.setString(1, login);
            rs = prstmt.executeQuery();
            if(rs.next()){
                String role = "user";
                if(rs.getString("role").equals("admin")){
                    role = "admin";
                }
                user = new User(rs.getString("login"), rs.getString("password"), role);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            close(rs, prstmt, con);
        }

        return user;
    }

    private void close(AutoCloseable... ac){
        try{
            for(AutoCloseable autoCloseable: ac){
                autoCloseable.close();
            }
        }  catch(Exception e){
            e.printStackTrace();
        }
    }

}
