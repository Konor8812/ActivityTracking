package model.dao;

public class DaoFactory {

    private static DaoFactory factory;

    private DaoFactory() {
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


}