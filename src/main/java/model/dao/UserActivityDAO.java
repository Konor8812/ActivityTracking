package model.dao;

import model.database.ConnectionPool;
import model.util.Util;
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

    private static final String REQ_ACTIVITY_FOR_USER = "INSERT INTO user_has_activity (user_id, activity_id) VALUES (?,?)";
    private static final String REG_ACTIVITY_FOR_USER = "UPDATE user_has_activity SET time_spent=(?), status=(?) WHERE user_id=(?) AND activity_id=(?)";
    private static final String CHECK_IF_ACTIVITY_ALREADY_TAKEN_BY_USER = "SELECT * FROM user_has_activity WHERE user_id=(?) AND activity_id=(?)";
    private static final String GET_USERS_ACTIVITIES = "SELECT * FROM user_has_activity WHERE user_id=(?)";
    private static final String DELETE_USERS_ACTIVITY = "DELETE FROM user_has_activity WHERE user_id=(?) AND activity_id=(?)";
    private static final String GET_USERS_WITH_THIS_ACTIVITY = "SELECT * FROM user_has_activity WHERE activity_id=(?)";
    private static final String GET_TIME_SPENT = "SELECT * FROM user_has_activity WHERE user_id=(?) AND activity_id=(?)";

    public static synchronized UserActivityDAO getInstance() {
        if (instance == null) {
            instance = new UserActivityDAO();
        }
        return instance;
    }


    private boolean checkIfUserHasThisActivity(int userId, int activityId) {
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
            if (rs.next()) {
                String status = rs.getString("status");
                if (status.equals("requested") || status.equals("in_process") || status.equals("denied") || status.equals("completed")) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(rs, prstmt, con);
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
            while (rs.next()) {

                Activity activity = ad.getActivityById(rs.getInt("activity_id"));
                String status = getUserActivityStatus(userId, activity.getId());
                if (status.equals("in_process")) {
                    long timeSpentForActivity = System.currentTimeMillis() - getTimeSpent(userId, activity.getId());
                    activity.setTimeSpent(Util.getFormattedTime(timeSpentForActivity));
                }
                activity.setStatus(status);
                usersActivities.add(activity);
            }
        } catch (SQLException e) {
            logger.error("Error while getting users activities", e);
        } finally {
            Util.close(rs, prstmt, con);
        }
        return usersActivities;
    }

    private String getUserActivityStatus(int userId, int activityId) {
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
            if (rs.next()) {
                return rs.getString("status");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(rs, prstmt, con);
        }
        return "";
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
            userService.userCompletedActivity(userId, 0);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(prstmt, con);
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
            while (rs.next()) {
                if(!rs.getString("status").equals("requested"))
                result.add(rs.getInt("user_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(rs, prstmt, con);
        }

        return result;
    }

    public long getTimeSpent(int userId, int activityId) {
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(GET_TIME_SPENT);
            prstmt.setInt(1, userId);
            prstmt.setInt(2, activityId);
            rs = prstmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("time_spent");
            }
        } catch (SQLException e) {
            logger.error("Error while getting time spend by user for activity", e);
        } finally {
            Util.close(rs, prstmt, con);
        }
        return 0;
    }


    public void reqActivityForUser(int userId, int activityId) throws ServiceException {
        Connection con = null;
        PreparedStatement prstmt = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            if (!checkIfUserHasThisActivity(userId, activityId)) {
                prstmt = con.prepareStatement(REQ_ACTIVITY_FOR_USER);
                prstmt.setInt(1, userId);
                prstmt.setInt(2, activityId);
                prstmt.execute();

            } else {
                throw new ActivityAlreadyTaken();
            }
        } catch (SQLException e) {
            logger.error("Error while user requested activity", e);
        } finally {
            Util.close(prstmt, con);
        }
    }

    public void approveActivity(int userId, int activityId) {
        Connection con = null;
        PreparedStatement prstmt = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(REG_ACTIVITY_FOR_USER);

            prstmt.setLong(1, System.currentTimeMillis());
            prstmt.setString(2, "in_process");
            prstmt.setInt(3, userId);
            prstmt.setInt(4, activityId);
            prstmt.execute();
            UserService userService = new UserService();
            userService.changeUsersRequestsAmount(userId, false);
        } catch (SQLException e) {
            logger.info("SQLException occurred during regging activity for user", e);
        } finally {
            Util.close(prstmt, con);
        }
    }

    public void denyApproval(int userId, int activityId) {
        Connection con = null;
        PreparedStatement prstmt = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(REG_ACTIVITY_FOR_USER);
            prstmt.setLong(1, 0);
            prstmt.setString(2, "denied");
            prstmt.setInt(3, userId);
            prstmt.setInt(4, activityId);
            prstmt.execute();
            UserService userService = new UserService();
            userService.changeUsersRequestsAmount(userId, false);
        } catch (SQLException e) {
            logger.info("SQLException occurred during denying activity for user", e);
        } finally {
            Util.close(prstmt, con);
        }
    }

    public void completedUsersActivity(int userId, int activityId) {
        Connection con = null;
        PreparedStatement prstmt = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(REG_ACTIVITY_FOR_USER);
            long timeSpent = getTimeSpent(userId, activityId);
            prstmt.setLong(1, timeSpent);
            prstmt.setString(2, "completed");
            prstmt.setInt(3, userId);
            prstmt.setInt(4, activityId);
            prstmt.execute();

            ActivityService activityService = new ActivityService();
            UserService userService = new UserService();

            double pointForActivity = activityService.getRewardForActivity(activityId);
            userService.userCompletedActivity(userId, pointForActivity);

        } catch (SQLException e) {
            logger.info("SQLException occurred when user completed activity ", e);
        } finally {
            Util.close(prstmt, con);
        }
    }

    public List<Integer> getListOfUsersRequestedThisActivity(int activityId) {
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
            while (rs.next()) {
                if(rs.getString("status").equals("requested")) {
                    result.add(rs.getInt("user_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(rs, prstmt, con);
        }
        return result;
    }
}
