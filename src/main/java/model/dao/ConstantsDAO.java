package model.dao;

/**
 * This class contains all database queries
 */

public abstract class ConstantsDAO {

    /**
     * Activity DAO queries
     */
    public static final String INSERT_ACTIVITY = "INSERT INTO activity (name, duration, reward, description, taken_by) VALUES (?,?,?,?,?)";
    public static final String ALL_ACTIVITIES_LIST = "SELECT * FROM activity";
    public static final String GET_ACTIVITY_BY_ID = "SELECT * FROM activity WHERE id=(?)";
    public static final String GET_ACTIVITY_BY_NAME = "SELECT * FROM activity WHERE name=(?)";
    public static final String DELETE_ACTIVITY = "DELETE FROM activity WHERE id=(?)";
    public static final String UPDATE_ACTIVITY = "UPDATE activity SET name=(?), duration=(?), reward=(?), description=(?), taken_by=(?) WHERE id=(?) OR name=(?)";
    public static final String CHANGE_TAKEN_BY_AMOUNT = "UPDATE activity SET taken_by=(?) WHERE id=(?)";
    public static final String FIVE_ACTIVITIES_LIST = "SELECT * FROM activity LIMIT ?,?";

    /**
     * User DAO queries
     */
    public static final String GET_LOGGED_IN_USER = "SELECT * FROM user WHERE login=(?)";
    public static final String GET_USER_BY_ID = "SELECT * FROM user WHERE id=(?)";
    public static final String GET_USERS_AS_LIST = "SELECT * FROM user WHERE role='user' AND status='available'";
    public static final String INSERT_USER = "INSERT INTO user (login, password) VALUES (?, ?)";
    public static final String DELETE_USER = "DELETE FROM user WHERE id=(?)";
    public static final String DELETE_ALL_USERS = "DELETE FROM user WHERE role='user'";
    public static final String CHECK_IF_LOGIN_EXISTS = "SELECT * FROM user WHERE login=(?)";
    public static final String UPDATE_PASSWORD = "UPDATE user SET password=(?) WHERE id=(?)";
    public static final String CHANGE_ACTIVITIES_AMOUNT = "UPDATE user SET activities_amount=(?) WHERE id=(?)";
    public static final String GIVE_POINT_FOR_COMPLETED_ACTIVITY = "UPDATE user SET total_points=(?) WHERE id=(?)";
    public static final String BLOCK_USER = "UPDATE user SET status='blocked' WHERE id=(?)";
    public static final String UNBLOCK_USER = "UPDATE user SET status='available' WHERE id=(?)";
    public static final String GET_ALL_BLOCKED = "SELECT * FROM user WHERE status='blocked'";
    public static final String CHANGE_REQUESTS_AMOUNT = "UPDATE user SET requests_amount=(?) WHERE id=(?)";
    public static final String GET_USERS_REQUESTED_ACTIVITIES = "SELECT * FROM user_has_activity WHERE user_id=(?) AND status='requested'";


    /**
     * ActivityUser DAO queries
     */
    public static final String REQ_ACTIVITY_FOR_USER = "INSERT INTO user_has_activity (user_id, activity_id) VALUES (?,?)";
    public static final String REG_ACTIVITY_FOR_USER = "UPDATE user_has_activity SET time_spent=(?), status=(?) WHERE user_id=(?) AND activity_id=(?)";
    public static final String CHECK_IF_ACTIVITY_ALREADY_TAKEN_BY_USER = "SELECT * FROM user_has_activity WHERE user_id=(?) AND activity_id=(?)";
    public static final String GET_USERS_ACTIVITIES = "SELECT * FROM user_has_activity WHERE user_id=(?)";
    public static final String DELETE_USERS_ACTIVITY = "DELETE FROM user_has_activity WHERE user_id=(?) AND activity_id=(?)";
    public static final String GET_USERS_WITH_THIS_ACTIVITY = "SELECT * FROM user_has_activity WHERE activity_id=(?)";
    public static final String GET_TIME_SPENT = "SELECT * FROM user_has_activity WHERE user_id=(?) AND activity_id=(?)";
}
