package service.entityExtention;

import model.entity.Activity;
import model.exception.ServiceException;

import java.util.List;

public interface UserActivityFunctionality {

    void activityGaveUp(int userId, int activityId);
    void activityCompleted(int userId, int activityId);
    long getTimeSpent(int userId, int ActivityId);

    List<Activity> getUsersActivities(int userId);
    List<Integer> usersWithThisActivity(int activityId);

    void userRequestedActivity(int userId, int activityId) throws ServiceException;
    void denyApprovalActivityForUser(int userId, int activityId);
    void approveActivityForUser(int userId, int activityId);

    List<Integer> usersWithRequestedActivity(int activityId);
}
