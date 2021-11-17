package model.dao;

import model.database.ConnectionPool;
import model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static UserDAO instance;

    private static final String GET_LOGGED_IN_USER = "SELECT * FROM user WHERE login=(?)";
    private static final String GET_USER_BY_ID = "SELECT * FROM user WHERE id=(?)";
    private static final String GET_USERS_AS_LIST = "SELECT * FROM user WHERE role='user'";
    private static final String INSERT_USER = "INSERT INTO user (login, password) VALUES (?, ?)";
    private static final String DELETE_USER = "DELETE FROM user WHERE login=(?)";
    private static final String DELETE_ALL_USERS = "DELETE FROM user WHERE role='user'";
    private static final String CHECK_IF_LOGIN_EXISTS = "SELECT * FROM user WHERE login=(?)";
    private static final String UPDATE_PASSWORD = "UPDATE user SET password=(?) WHERE id=(?)";
    private static final String CHANGE_ACTIVITIES_AMOUNT = "UPDATE user SET activities_amount=(?) WHERE id=(?)";

    public static synchronized UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public User regUser(String login, String pass) {
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
        return getLogedInUser(login, pass);
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

    public User getLogedInUser(String login, String password){
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
            if(rs.next()) {
                if (password.equals(rs.getString("password"))) {
                    user = getUserById(Integer.toString(rs.getInt("id")));
                }
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

    public boolean checkIfLoginExists(String login) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement prstmt = null;
        try{
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(CHECK_IF_LOGIN_EXISTS);
            prstmt.setString(1, login);
            rs = prstmt.executeQuery();
            if(rs.next()) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            close(rs, prstmt, con);
        }

        return false;
    }


    public void activityTakenByUser(String userId) {
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        try{
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(GET_USER_BY_ID);
            prstmt.setString(1, userId);
            rs = prstmt.executeQuery();
            if(rs.next()) {
                int activitiesAmount = rs.getInt("activities_amount");
                prstmt = con.prepareStatement(CHANGE_ACTIVITIES_AMOUNT);
                prstmt.setString(1, Integer.toString(++activitiesAmount));
                prstmt.setString(2, userId);
                prstmt.execute();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            close(rs, prstmt, con);
        }

    }

    public void changeUserPassword(int id, String newPassword) {
        Connection con = null;
        PreparedStatement prstmt = null;
        try{
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(UPDATE_PASSWORD);
            prstmt.setString(1, newPassword);
            prstmt.setString(2, Integer.toString(id));
            prstmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            close( prstmt, con);
        }

    }

    public void deleteUsersActivity(String userId) {
        Connection con = null;
        PreparedStatement prstmt = null;
        try{
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(CHANGE_ACTIVITIES_AMOUNT);
            User user = getUserById(userId);
            int activitiesAmount = user.getActivitiesAmount();
            prstmt.setInt(1, --activitiesAmount);
            prstmt.setString(2, userId);
            prstmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            close(prstmt, con);
        }
    }

    private User getUserById(String userId) {
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        User user = null;
        try{
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(GET_USER_BY_ID);
            prstmt.setString(1, userId);
            rs = prstmt.executeQuery();
            if(rs.next()){
                System.out.println("~~~~");
                user = new User();
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword((rs.getString("password")));
                user.setRole(rs.getString("role"));
                user.setTotalPoints(rs.getInt("total_points"));
                user.setActivitiesAmount(rs.getInt("activities_amount"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            close(rs, prstmt, con);
        }
        return user;
    }

    public void updateActivitiesAmount(String activityId) {
        Connection con = null;
        PreparedStatement prstmt = null;
        try{
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(CHANGE_ACTIVITIES_AMOUNT);

            UserActivityDAO uad = UserActivityDAO.getInstance();
            List<Integer> usersIdHadActivity = uad.getListOfUsersHadThisActivity(activityId);
            for(int i: usersIdHadActivity){
               User user = getUserById(Integer.toString(i));
               int activitiesAmount = user.getActivitiesAmount();
                prstmt.setInt(1,--activitiesAmount);
                prstmt.setInt(2, i);
                prstmt.execute();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            close(prstmt, con);
        }

    }
}

