package model.dao;

import model.database.ConnectionPool;
import model.util.Util;
import model.entity.Activity;
import model.entity.User;
import model.exception.WrongLoginData;
import org.apache.log4j.Logger;
import service.implementations.ActivityService;
import service.implementations.UserActivityService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static UserDAO instance;
    private static Logger logger = Logger.getLogger(UserDAO.class);


    public static synchronized UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public void regUser(User user) throws WrongLoginData {
        Connection con = null;
        PreparedStatement prstmt = null;
        String login = user.getLogin();
        String pass = user.getPassword();
        try {
            if (!checkIfLoginExists(login)) {
                ConnectionPool cp = ConnectionPool.getInstance();
                con = cp.getConnection();
                prstmt = con.prepareStatement(ConstantsDAO.INSERT_USER);
                prstmt.setString(1, login);
                prstmt.setString(2, pass);
                prstmt.execute();
            } else {
                throw new WrongLoginData();
            }
        } catch (SQLException e) {
            logger.error("SQLException during registration occurred ", e);
        } finally {
            Util.close(prstmt, con);
        }
    }

    public boolean deleteUser(int userId) {
        Connection con = null;
        PreparedStatement prstmt = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(ConstantsDAO.DELETE_USER);
            prstmt.setInt(1, userId);
            prstmt.execute();
            return true;
        } catch (SQLException e) {
            logger.error("Wasn't able to delete user, id ==> " + userId, e);
        } finally {
            Util.close(prstmt, con);
        }
        return false;
    }

    public void deleteAllUsers() {
        Connection con = null;
        Statement stmt = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            stmt = con.createStatement();
            stmt.execute(ConstantsDAO.DELETE_ALL_USERS);
        } catch (SQLException e) {
            logger.error("SQLException occurred trying to delete all users", e);
        } finally {
            Util.close(stmt, con);
        }
    }

    public List<User> getUsersList() {
        List<User> users = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        Statement stmt = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(ConstantsDAO.GET_USERS_AS_LIST);
            while (rs.next()) {
                User user = new User();
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setId(rs.getInt("id"));
                user.setActivitiesAmount(rs.getInt("activities_amount"));
                user.setRole(rs.getString("role"));
                user.setTotalPoints(rs.getDouble("total_points"));
                user.setStatus(rs.getString("status"));
                user.setRequestsAmount(rs.getInt("requests_amount"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(rs, stmt, con);
        }
        return users;
    }

    public void changeUsersRequestsAmount(int userId, boolean increment){
        Connection con = null;

        PreparedStatement prstmt = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(ConstantsDAO.CHANGE_REQUESTS_AMOUNT);
            User user = getUserById(userId);
            int requestsAmount = user.getRequestsAmount();
            if(increment){
                prstmt.setInt(1, ++requestsAmount);
            }else{
                prstmt.setInt(1, --requestsAmount);
            }
            prstmt.setInt(2, userId);
            prstmt.execute();

        } catch (SQLException exception) {
            logger.error("SQLException occurred during getting logged in user!", exception);
        } catch (WrongLoginData wrongLoginData) {
            //not gonna happen
        } finally {
            Util.close( prstmt, con);
        }

    }

    public User getLoggedInUser(String login, String password) throws WrongLoginData {
        User user = null;
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement prstmt = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(ConstantsDAO.GET_LOGGED_IN_USER);
            prstmt.setString(1, login);
            rs = prstmt.executeQuery();
            if (rs.next()) {
                if (password.equals(rs.getString("password"))) {
                    user = getUserById(rs.getInt("id"));
                } else {
                    throw new WrongLoginData();
                }
            } else {
                throw new WrongLoginData();
            }
        } catch (SQLException exception) {
            logger.error("SQLException occurred during getting logged in user!", exception);
        } finally {
            Util.close(rs, prstmt, con);
        }

        return user;
    }


    private boolean checkIfLoginExists(String login) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement prstmt = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(ConstantsDAO.CHECK_IF_LOGIN_EXISTS);
            prstmt.setString(1, login);
            rs = prstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            Util.close(rs, prstmt, con);
        }

        return false;
    }


    public void activityTakenByUser(int userId) {
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(ConstantsDAO.GET_USER_BY_ID);
            prstmt.setInt(1, userId);
            rs = prstmt.executeQuery();
            if (rs.next()) {
                int activitiesAmount = rs.getInt("activities_amount");
                prstmt = con.prepareStatement(ConstantsDAO.CHANGE_ACTIVITIES_AMOUNT);
                prstmt.setString(1, Integer.toString(++activitiesAmount));
                prstmt.setInt(2, userId);
                prstmt.execute();
            }
        } catch (SQLException throwable) {
            logger.error("An error occurred during incrementing users activities amount", throwable);
        } finally {
            Util.close(rs, prstmt, con);
        }
    }

    public void changeUserPassword(int id, String newPassword) {
        Connection con = null;
        PreparedStatement prstmt = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(ConstantsDAO.UPDATE_PASSWORD);
            prstmt.setString(1, newPassword);
            prstmt.setString(2, Integer.toString(id));
            prstmt.execute();
        } catch (SQLException throwable) {
            logger.error("An error during changing password, user id ==> " + id, throwable);
        } finally {
            Util.close(prstmt, con);
        }
    }

    public void decrementUsersActivitiesAmount(int userId, double rewardForActivity) {
        Connection con = null;
        PreparedStatement prstmt = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            User user = getUserById(userId);

            prstmt = con.prepareStatement(ConstantsDAO.CHANGE_ACTIVITIES_AMOUNT);
            int activitiesAmount = user.getActivitiesAmount();
            prstmt.setInt(1, --activitiesAmount);
            prstmt.setInt(2, userId);
            prstmt.execute();
            if (rewardForActivity != 0) {
                double totalPoints = user.getTotalPoints();
                prstmt = con.prepareStatement(ConstantsDAO.GIVE_POINT_FOR_COMPLETED_ACTIVITY);
                prstmt.setDouble(1, totalPoints + rewardForActivity);
                prstmt.setInt(2, userId);
                prstmt.execute();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (WrongLoginData wrongLoginData) {
            //not gonna happen
        } finally {
            Util.close(prstmt, con);
        }
    }

    public User getUserById(int userId) throws WrongLoginData {
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        User user = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(ConstantsDAO.GET_USER_BY_ID);
            prstmt.setInt(1, userId);
            rs = prstmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword((rs.getString("password")));
                user.setRole(rs.getString("role"));
                user.setTotalPoints(rs.getDouble("total_points"));
                user.setActivitiesAmount(rs.getInt("activities_amount"));
                user.setStatus(rs.getString("status"));
                user.setRequestsAmount(rs.getInt("requests_amount"));

            } else {
                throw new WrongLoginData();
            }

        } catch (SQLException exception) {
            logger.error("Error occurred trying to get user, id ==> " + userId, exception);
        } finally {
            Util.close(rs, prstmt, con);
        }
        return user;
    }

    public void updateActivitiesAmount(int activityId) {
        Connection con = null;
        PreparedStatement prstmt = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(ConstantsDAO.CHANGE_ACTIVITIES_AMOUNT);

            UserActivityService userActivityService = new UserActivityService();

            List<Integer> usersIdHadActivity = userActivityService.usersWithThisActivity(activityId);
            List<Integer> usersWithRequest = userActivityService.usersWithRequestedActivity(activityId);

            for (int i : usersIdHadActivity) {
                User user = getUserById(i);
                int activitiesAmount = user.getActivitiesAmount();
                prstmt.setInt(1, --activitiesAmount);
                prstmt.setInt(2, i);
                prstmt.execute();
            }

            for(int i: usersWithRequest){
                changeUsersRequestsAmount(i, false);
            }

        } catch (SQLException throwable) {
            logger.error("SQLException while updating activities amount after activity was deleted by admin", throwable);
        } catch (WrongLoginData wrongLoginData) {
            logger.error("An error while updating users activities amount, user exists but something went wrong", wrongLoginData);
        } finally {
            Util.close(prstmt, con);
        }
    }

    public List<User> getAllBlockedUsers() {
        List<User> blockedUsers = new ArrayList<>();
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(ConstantsDAO.GET_ALL_BLOCKED);
            rs = prstmt.executeQuery();
            while (rs.next()) {
                blockedUsers.add(getUserById(rs.getInt("id")));
            }
        } catch (SQLException throwable) {
            logger.error("SQLException while blocking user", throwable);
        } catch (WrongLoginData wrongLoginData) {
            //not gonna happen
        } finally {
            Util.close(rs, prstmt, con);
        }
        return blockedUsers;
    }

    public void blockUser(int userId, boolean block) {
        Connection con = null;
        PreparedStatement prstmt = null;
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            if (block) {
                prstmt = con.prepareStatement(ConstantsDAO.BLOCK_USER);
            } else {
                prstmt = con.prepareStatement(ConstantsDAO.UNBLOCK_USER);
            }
            prstmt.setInt(1, userId);
            prstmt.execute();

        } catch (SQLException throwable) {
            logger.error("SQLException while blocking user", throwable);
        } finally {
            Util.close(prstmt, con);
        }
    }

    public List<Activity> getUsersRequestedActivities(int userId) {
        List<Activity> usersRequestedActivities = new ArrayList<>();
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        ActivityDAO ad = ActivityDAO.getInstance();
        try {
            ConnectionPool cp = ConnectionPool.getInstance();
            con = cp.getConnection();
            prstmt = con.prepareStatement(ConstantsDAO.GET_USERS_REQUESTED_ACTIVITIES);
            prstmt.setInt(1, userId);
            rs = prstmt.executeQuery();
            while (rs.next()) {
                Activity activity = ad.getActivityById(rs.getInt("activity_id"));
                usersRequestedActivities.add(activity);
            }
        } catch (SQLException e) {
            logger.error("Error while getting users requested activities", e);
        } finally {
            Util.close(rs, prstmt, con);
        }
        return usersRequestedActivities;
    }
}

