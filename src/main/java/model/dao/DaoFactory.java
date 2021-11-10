package model.dao;

public class DaoFactory {

    private static DaoFactory factory;

    private DaoFactory() {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    public static synchronized DaoFactory getInstance() {
        if (factory == null) {
            factory = new DaoFactory();
        }
        return factory;
    }


    public UserDAO getUserDAO() {
        return UserDAO.getInstance();
    }

    public ActivityDAO getActivityDAO() {
        return ActivityDAO.getInstance();
    }


//    public Connection openConnection() {
//        Connection con = null;
//        try {
//
//            ResourceBundle rb = ResourceBundle.getBundle("db");
//            con = DriverManager.getConnection(rb.getString("MYSQL_DB_URL"));
//
//        } catch (SQLException e) {
//            e.printStackTrace();
// //           throw new RuntimeException("wasn't able to connect db!");
//        }
//
//        return con;
//    }
}