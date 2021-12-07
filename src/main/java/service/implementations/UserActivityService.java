package service.implementations;

import model.dao.DAOFactory;
import model.dao.UserActivityDAO;
import model.entity.Activity;
import model.exception.ServiceException;
import service.entityExtention.UserActivityFunctionality;

import java.util.List;

public class UserActivityService implements UserActivityFunctionality {

    private final DAOFactory daoFactory = DAOFactory.getInstance();
    private UserActivityDAO userActivityDAO = daoFactory.getUserActivityDAO();

    @Override
    public void activityGaveUp(int userId, int activityId) {
        userActivityDAO.deleteUsersActivity(userId, activityId);
    }

    @Override
    public void activityCompleted(int userId, int activityId) {
        userActivityDAO.completedUsersActivity(userId, activityId);
    }

    @Override
    public long getTimeSpent(int userId, int activityId) {
        return userActivityDAO.getTimeSpent(userId, activityId);
    }

    @Override
    public List<Activity> getUsersActivities(int userId) {
        return userActivityDAO.getUsersActivities(userId);
    }

    @Override
    public List<Integer> usersWithThisActivity(int activityId) {
        return userActivityDAO.getListOfUsersHadThisActivity(activityId);
    }

    @Override
    public void userRequestedActivity(int userId, int activityId) throws ServiceException {
        userActivityDAO.reqActivityForUser(userId, activityId);
        UserService userService = new UserService();
        userService.changeUsersRequestsAmount(userId, true);
    }

    @Override
    public void denyApprovalActivityForUser(int userId, int activityId) {
        userActivityDAO.denyApproval(userId, activityId);
    }


    @Override
    public void approveActivityForUser(int userId, int activityId) {
        userActivityDAO.approveActivity(userId, activityId);

        UserService userService = new UserService();
        userService.userTookActivity(userId);

        ActivityService activityService = new ActivityService();
        activityService.wasTakenOrCompleted(activityId, true);
    }

    @Override
    public List<Integer> usersWithRequestedActivity(int activityId) {

        return userActivityDAO.getListOfUsersRequestedThisActivity(activityId);
    }


}
