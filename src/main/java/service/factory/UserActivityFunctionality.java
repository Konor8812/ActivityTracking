package service.factory;

import model.entity.Activity;
import model.entity.User;
import model.exception.ServiceException;

import java.util.List;

public interface UserActivityFunctionality {

    void activityGaveUp(int userId, int activityId);
    void activityCompleted(int userId, int activityId);
    void userTookActivity(int userId, int activityId) throws ServiceException;

    List<Activity> getUsersActivities(int userId);
    List<Integer> usersWithThisActivity(int activityId);

}
