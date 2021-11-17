package model.dao;

import model.database.ConnectionPool;
import model.entity.Activity;
import model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserActivityDAO {
    private static UserActivityDAO instance;

    private static final String REG_ACTIVITY_FOR_USER = "INSERT INTO user_has_activity (user_id, activity_id) VALUES (?,?)";
    private static final String CHECK_IF_ACTIVITY_ALREADY_TAKEN_BY_USER = "SELECT * FROM user_has_activity WHERE user_id=(?) AND activity_id=(?)";
    private static final String GET_USERS_ACTIVITIES = "SELECT * FROM user_has_activity WHERE user_id=(?)";
    private static final String DELETE_USERS_ACTIVITY = "DELETE FROM user_has_activity WHERE user_id=(?) AND activity_id=(?)";
    private static final String GET_USERS_WITH_THIS_ACTIVITY = "SELECT * FROM user_has_activity WHERE activity_id=(?)";

    public static synchronized UserActivityDAO getInstance() {
        if (instance == null) {
            instance = new UserActivityDAO();
        }
        return instance;
    }

    public void regActivityForUser(String userId, String activityId) {
        Connection con = null;
        PreparedStatement prstmt = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(REG_ACTIVITY_FOR_USER);
            prstmt.setString(1, userId);
            prstmt.setString(2, activityId);
            prstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            close(prstmt, con);
        }
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
    public boolean checkIfUserHasThisActivity(String userId, String activityId){
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(CHECK_IF_ACTIVITY_ALREADY_TAKEN_BY_USER);
            prstmt.setString(1, userId);
            prstmt.setString(2, activityId);
            rs = prstmt.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            close(rs, prstmt, con);
        }

        return false;
    }

    public List<Activity> getUsersActivities(int id) {
        List<Activity> usersActivities = new ArrayList<>();
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        ActivityDAO ad = ActivityDAO.getInstance();
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(GET_USERS_ACTIVITIES);
            prstmt.setString(1, Integer.toString(id));
            rs = prstmt.executeQuery();
            while(rs.next()){
                usersActivities.add(ad.getActivityById(Integer.toString(rs.getInt("activity_id"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            close(rs, prstmt, con);
        }
        return usersActivities;
    }

    public User deleteUsersActivity(String userId, String activityId) {
        User user = new User();

        Connection con = null;
        PreparedStatement prstmt = null;
        UserDAO ud = UserDAO.getInstance();
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(DELETE_USERS_ACTIVITY);
            prstmt.setString(1, userId);
            prstmt.setString(2, activityId);
            prstmt.execute();

            ud.deleteUsersActivity(userId);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            close(prstmt, con);
        }


        return user;
    }

    public List<Integer> getListOfUsersHadThisActivity(String activityId) {
        List<Integer> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(GET_USERS_WITH_THIS_ACTIVITY);
            prstmt.setString(1, activityId);
            rs = prstmt.executeQuery();
            while(rs.next()){
                result.add(rs.getInt("userId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            close(rs, prstmt, con);
        }




        return result;
    }
}
