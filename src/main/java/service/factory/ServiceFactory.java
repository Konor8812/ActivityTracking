package service.factory;

import org.apache.log4j.Logger;
import service.implementations.ActivityService;
import service.implementations.UserService;

public class ServiceFactory {

    private static Logger logger = Logger.getLogger(ServiceFactory.class);
    private static ServiceFactory instance;

    private ActivityService activityService = new ActivityService();
    private UserService userService = new UserService();

    public static ServiceFactory getInstance() {
        if (instance == null) {
            logger.info("Create ServiceFactory");
            instance = new ServiceFactory();
        }
        return instance;
    }

    private ServiceFactory() { }

    public ActivityService getActivityService() {
        return activityService;
    }

    public UserService getUserService() {
        return userService;
    }
}
