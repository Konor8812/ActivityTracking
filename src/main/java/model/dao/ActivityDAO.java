package model.dao;

import model.database.ConnectionPool;
import model.util.Util;
import model.entity.Activity;
import model.exception.ActivityAlreadyExists;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityDAO {
    private static ActivityDAO instance;
    private static Logger logger = Logger.getLogger(ActivityDAO.class);

    private static final String INSERT_ACTIVITY = "INSERT INTO activity (name, duration, reward, description, taken_by) VALUES (?,?,?,?,?)";
    private static final String ALL_ACTIVITIES_LIST = "SELECT * FROM activity";
    private static final String GET_ACTIVITY_BY_ID = "SELECT * FROM activity WHERE id=(?)";
    private static final String GET_ACTIVITY_BY_NAME = "SELECT * FROM activity WHERE name=(?)";
    private static final String DELETE_ACTIVITY = "DELETE FROM activity WHERE id=(?)";
    private static final String UPDATE_ACTIVITY = "UPDATE activity SET name=(?), duration=(?), reward=(?), description=(?), taken_by=(?) WHERE id=(?) OR name=(?)";
    private static final String CHANGE_TAKEN_BY_AMOUNT = "UPDATE activity SET taken_by=(?) WHERE id=(?)";
    private static final String FIVE_ACTIVITIES_LIST = "SELECT * FROM activity LIMIT ?,?";

    private ActivityDAO() {
    }

    public static synchronized ActivityDAO getInstance() {
        if (instance == null) {
            instance = new ActivityDAO();
        }
        return instance;
    }


    public void insertActivity(Activity activity) throws ActivityAlreadyExists {
        Connection con = null;
        PreparedStatement prstmt = null;
        try {
            if (getActivityByName(activity.getName()) == null) {
                ConnectionPool cp = ConnectionPool.getInstance();
                con = cp.getConnection();
                prstmt = con.prepareStatement(INSERT_ACTIVITY);
                prstmt.setString(1, activity.getName());
                prstmt.setString(2, activity.getDuration());
                prstmt.setDouble(3, activity.getReward());
                prstmt.setString(4, activity.getDescription());
                prstmt.setInt(5, 0);
                prstmt.execute();
            } else {
                throw new ActivityAlreadyExists();
            }
        } catch (SQLException e) {
            logger.error("Error occurred during inserting activity", e);
        } finally {
            Util.close(prstmt, con);
        }
    }


    public List<Activity> getActivitiesList() {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement prstmt = null;
        List<Activity> activities = new ArrayList<>();
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(ALL_ACTIVITIES_LIST);
            rs = prstmt.executeQuery();
            while (rs.next()) {
                Activity activity = getActivityById(rs.getInt("id"));
                activities.add(activity);
            }
        } catch (Exception e) {
            logger.error("Error during getting activities list", e);
        } finally {
            Util.close(rs, prstmt, con);
        }
        return activities;
    }

    public Activity getActivityById(int id) {
        Activity activity = new Activity();
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement prstmt = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(GET_ACTIVITY_BY_ID);
            prstmt.setInt(1, id);
            rs = prstmt.executeQuery();
            if (rs.next()) {
                activity.setId(rs.getInt("id"));
                activity.setName(rs.getString("name"));
                activity.setDuration(rs.getString("duration"));
                activity.setReward(rs.getDouble("reward"));
                activity.setDescription(rs.getString("description"));
                activity.setTakenByAmount(rs.getInt("taken_by"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Util.close(rs, prstmt, con);
        }
        return activity;
    }

    public boolean deleteActivity(int activityId) {
        Connection con = null;
        PreparedStatement prstmt = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(DELETE_ACTIVITY);
            prstmt.setInt(1, activityId);
            prstmt.execute();
            return true;
        } catch (SQLException e) {
            logger.error("Wasn't able to delete activity", e);
        } finally {
            Util.close(prstmt, con);
        }
        return false;
    }

    public Activity updateActivity(Activity entity) {
        Activity activity = null;
        Connection con = null;
        PreparedStatement prstmt = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(UPDATE_ACTIVITY);
            prstmt.setString(1, entity.getName());
            prstmt.setString(2, entity.getDuration());
            prstmt.setDouble(3, entity.getReward());
            prstmt.setString(4, entity.getDescription());
            prstmt.setInt(5, entity.getTakenByAmount());
            prstmt.setInt(6, entity.getId());
            prstmt.setString(7, entity.getName());
            prstmt.execute();
            activity = getActivityById(entity.getId());

        } catch (SQLException e) {
            logger.error("Wasn't able to update activity");
        } finally {
            Util.close(prstmt, con);
        }
        return activity;
    }

    public void changeTakenByAmount(int activityId, boolean increment) {
        Connection con = null;
        PreparedStatement prstmt = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            Activity activity = getActivityById(activityId);
            int takenBy = activity.getTakenByAmount();
            prstmt = con.prepareStatement(CHANGE_TAKEN_BY_AMOUNT);
            if (increment) {
                prstmt.setInt(1, ++takenBy);
            } else {
                prstmt.setInt(1, --takenBy);
            }
            prstmt.setInt(2, activityId);
            prstmt.execute();

        } catch (SQLException e) {
            logger.error("Wasn't able to update activity");
        } finally {
            Util.close(prstmt, con);
        }
    }

    public Activity getActivityByName(String name) {
        Activity activity = new Activity();
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement prstmt = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(GET_ACTIVITY_BY_NAME);
            prstmt.setString(1, name);
            rs = prstmt.executeQuery();
            if (rs.next()) {
                activity.setId(rs.getInt("id"));
                activity.setName(name);
                activity.setDuration(rs.getString("duration"));
                activity.setReward(rs.getDouble("reward"));
                activity.setDescription(rs.getString("description"));
                activity.setTakenByAmount(rs.getInt("taken_by"));
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Error getting activity by name", e);
        } finally {
            Util.close(rs, prstmt, con);
        }
        return activity;
    }

    public List<Activity> getFiveActivities(int num) {

        Connection con = null;
        ResultSet rs = null;
        PreparedStatement prstmt = null;
        List<Activity> activities = new ArrayList<>();
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(FIVE_ACTIVITIES_LIST);
            prstmt.setInt(1, num);
            prstmt.setInt(2, 5);
            rs = prstmt.executeQuery();
            while (rs.next()) {
                Activity activity = getActivityById(rs.getInt("id"));
                activities.add(activity);
            }
        } catch (Exception e) {
            logger.error("Error during getting activities list", e);
        } finally {
            Util.close(rs, prstmt, con);
        }
        return activities;
    }
}
