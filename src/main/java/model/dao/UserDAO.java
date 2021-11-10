package model.dao;

import model.database.DBManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
            con = DBManager.getInstance().getConnection();
            Statement stmt = con.createStatement();
            stmt.execute(getInsertString(login, pass));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String login) {
        Connection con;
        try {
            con = DBManager.getInstance().getConnection();
            Statement stmt = con.createStatement();
            stmt.execute(getDeleteUserString(login));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllUsers() {
        Connection con;
        try {
            con = DBManager.getInstance().getConnection();
            Statement stmt = con.createStatement();
            stmt.execute(getClearAllUsersExceptAdmin());
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

}
