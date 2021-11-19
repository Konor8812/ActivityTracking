package model.dao;

import model.database.ConnectionPool;
import model.entity.Activity;
import model.exception.ActivityAlreadyTaken;
import model.exception.ServiceException;
import org.apache.log4j.Logger;
import service.implementations.ActivityService;
import service.implementations.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserActivityDAO {
    private static UserActivityDAO instance;
    private static Logger logger = Logger.getLogger(UserActivityDAO.class);


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

    public void regActivityForUser(int userId, int activityId) throws ServiceException {
        Connection con = null;
        PreparedStatement prstmt = null;
        try {
            if(!checkIfUserHasThisActivity(userId, activityId)) {
                ConnectionPool cp = ConnectionPool.getInstance();
                con = cp.getConnection();
                prstmt = con.prepareStatement(REG_ACTIVITY_FOR_USER);
                prstmt.setInt(1, userId);
                prstmt.setInt(2, activityId);
                prstmt.execute();
            }else{
                throw new ActivityAlreadyTaken();
            }
        } catch (SQLException e) {
            logger.info("SQLException occurred during regging activity for user", e);
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
            logger.info("AutoCloseable wasn't closed", e);
        }
    }

    private boolean checkIfUserHasThisActivity(int userId, int activityId){
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(CHECK_IF_ACTIVITY_ALREADY_TAKEN_BY_USER);
            prstmt.setInt(1, userId);
            prstmt.setInt(2, activityId);
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

    public List<Activity> getUsersActivities(int userId) {
        List<Activity> usersActivities = new ArrayList<>();
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        ActivityDAO ad = ActivityDAO.getInstance();
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(GET_USERS_ACTIVITIES);
            prstmt.setInt(1, userId);
            rs = prstmt.executeQuery();
            while(rs.next()){
                usersActivities.add(ad.getActivityById(rs.getInt("activity_id")));
            }
        } catch (SQLException e) {
            logger.error("Error while getting users activities", e);
        }finally{
            close(rs, prstmt, con);
        }
        return usersActivities;
    }

    public void deleteUsersActivity(int userId, int activityId, boolean isCompleted) {
        Connection con = null;
        PreparedStatement prstmt = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(DELETE_USERS_ACTIVITY);
            prstmt.setInt(1, userId);
            prstmt.setInt(2, activityId);
            prstmt.execute();
            UserService userService = new UserService();
            if(isCompleted){
                ActivityService activityService = new ActivityService();
                double pointForActivity = activityService.getRewardForActivity(activityId);
                userService.userCompletedActivity(userId, pointForActivity);
            }else{
                userService.userCompletedActivity(userId, 0);
            }

        } catch (SQLException  e) {
            e.printStackTrace();
        } finally{
            close(prstmt, con);
        }

    }

    public List<Integer> getListOfUsersHadThisActivity(int activityId) {
        List<Integer> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(GET_USERS_WITH_THIS_ACTIVITY);
            prstmt.setInt(1, activityId);
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
