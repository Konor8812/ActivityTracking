package service.implementations;

import model.dao.DAOFactory;
import model.dao.UserActivityDAO;
import model.entity.Activity;
import model.exception.ServiceException;
import org.apache.log4j.Logger;
import service.factory.UserActivityFunctionality;

import java.util.List;

public class UserActivityService implements UserActivityFunctionality {

    private static Logger logger = Logger.getLogger(UserActivityService.class);
    private final DAOFactory daoFactory = DAOFactory.getInstance();
    private UserActivityDAO userActivityDAO = daoFactory.getUserActivityDAO();

    @Override
    public void activityGaveUp(int userId, int activityId) {
        userActivityDAO.deleteUsersActivity(userId, activityId, false);
    }

    @Override
    public void activityCompleted(int userId, int activityId) {
        userActivityDAO.deleteUsersActivity(userId, activityId, true);
    }

    @Override
    public void userTookActivity(int userId, int activityId) throws ServiceException {
        userActivityDAO.regActivityForUser(userId, activityId);
        UserService userService = new UserService();
        userService.userTookActivity(userId);
        ActivityService activityService = new ActivityService();
        activityService.wasTakenOrCompleted(activityId, true);
    }

    @Override
    public List<Activity> getUsersActivities(int userId) {
        return userActivityDAO.getUsersActivities(userId);
    }

    @Override
    public List<Integer> usersWithThisActivity(int activityId) {
        return userActivityDAO.getListOfUsersHadThisActivity(activityId);
    }


}
