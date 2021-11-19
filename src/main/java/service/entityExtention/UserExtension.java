package service.entityExtention;

import model.entity.Activity;
import model.entity.User;
import model.exception.WrongLoginData;
import service.factory.ItemService;

import java.util.List;

public interface UserExtension extends ItemService<Integer, User> {
    User getUserByLoginAndPassword(String login, String password) throws WrongLoginData;

    List<User> getAllBlocked();

    void userTookActivity(int userId);

    List<Activity> getUsersActivities(int userId);

    void changePass(int userId, String newPass);

    void updateActivitiesAmountAfterActivityWasDeleted(int activityId);

    void deleteAllUsers();

    void userCompletedActivity(int userId, double pointForActivity);

    void blockUser(int userId);
}
