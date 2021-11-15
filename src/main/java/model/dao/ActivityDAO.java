package model.dao;

import model.database.ConnectionPool;
import model.entity.Activity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityDAO {
    private static ActivityDAO instance;

    private static final String INSERT_ACTIVITY = "INSERT INTO activity (name, duration, reward) VALUES (?,?,?)";
    private static final String ALL_ACTIVITIES_LIST = "select * from activity";
    private static final String GET_ACTIVITY_BY_ID = "SELECT * FROM activity WHERE id=(?)";
    private static final String GET_ACTIVITY_BY_NAME = "SELECT * FROM activity WHERE name=(?)";



    public static synchronized ActivityDAO getInstance() {
        if (instance == null) {
            instance = new ActivityDAO();
        }
        return instance;
    }


    public void insertActivity(String name, String duration, double reward){
        Connection con = null;
        PreparedStatement prstmt = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(INSERT_ACTIVITY);
            prstmt.setString(1, name);
            prstmt.setString(2, duration);
            prstmt.setDouble(3, reward);
            prstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            close(prstmt, con);
        }
    }

    public List<Activity> getActivitiesList(){
        Connection con = null;
        ResultSet rs = null;
        Statement stmt = null;
        List<Activity> activities = new ArrayList<>();
        try{
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(ALL_ACTIVITIES_LIST);
            while(rs.next()){
                Activity activity = new Activity();
                activity.setId(rs.getInt("id"));
                activity.setName(rs.getString("name"));
                activity.setDuration(rs.getString("duration"));
                activity.setReward(rs.getDouble("reward"));
                activity.setDescription(rs.getString("description"));
                activities.add(activity);
            }
        } catch(Exception e){
            e.printStackTrace();
        }   finally{
            close(rs, stmt, con);
        }
        return activities;
    }

    public Activity getActivityById(String id){
        Activity activity = new Activity();
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement prstmt = null;
        try{
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(GET_ACTIVITY_BY_ID);
            prstmt.setString(1, id);
            rs = prstmt.executeQuery();
            if(rs.next()){
                activity.setName(rs.getString("name"));
                activity.setDuration(rs.getString("duration"));
                activity.setReward(rs.getDouble("reward"));
                activity.setDescription(rs.getString("description"));
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            close(rs, prstmt, con);
        }
        return activity;
    }
    public Activity getActivityByName(String name){
        Activity activity = new Activity();
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement prstmt = null;
        try{
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(GET_ACTIVITY_BY_NAME);
            prstmt.setString(1, name);
            rs = prstmt.executeQuery();
            if(rs.next()){
                activity.setName(rs.getString("name"));
                activity.setDuration(rs.getString("duration"));
                activity.setReward(rs.getDouble("reward"));
                activity.setDescription(rs.getString("description"));
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            close(rs, prstmt, con);
        }
        return activity;
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
