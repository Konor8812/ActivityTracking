package service.implementations;

import model.dao.DAOFactory;
import model.dao.UserDAO;
import model.entity.Activity;
import model.entity.User;
import model.exception.*;
import service.entityExtention.UserExtension;

import java.util.List;

public class UserService implements UserExtension {

    private final DAOFactory daoFactory = DAOFactory.getInstance();
    private UserDAO userDAO = daoFactory.getUserDAO();

    @Override
    public User getUserByLoginAndPassword(String login, String password) throws WrongLoginData {
        return userDAO.getLoggedInUser(login, password);
    }

    @Override
    public List<User> getAllBlocked() {
        return userDAO.getAllBlockedUsers();
    }

    @Override
    public void userTookActivity(int userId) {
        userDAO.activityTakenByUser(userId);
    }

    @Override
    public List<Activity> getUsersActivities(int userId) {
        UserActivityService userActivityService = new UserActivityService();
        return userActivityService.getUsersActivities(userId);
    }


    @Override
    public void changePass(int userId, String newPass) {
        userDAO.changeUserPassword(userId, newPass);
    }

    @Override
    public void updateActivitiesAmountAfterActivityWasDeleted(int activityId) {
        userDAO.updateActivitiesAmount(activityId);
    }

    @Override
    public void deleteAllUsers() {
        userDAO.deleteAllUsers();
    }

    @Override
    public void userCompletedActivity(int userId, double pointForActivity) {
        userDAO.decrementUsersActivitiesAmount(userId, pointForActivity);
    }

    @Override
    public void changeUsersStatus(int userId, boolean block) {
        userDAO.blockUser(userId, block);
    }

    @Override
    public User getItemById(Integer id) throws DBException, ServiceException {
        return userDAO.getUserById(id);
    }

    @Override
    public void add(User entity) throws WrongLoginData {
        userDAO.regUser(entity);
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return userDAO.deleteUser(id);
    }

    @Override
    public List<User> getAllItemsAsList() {
        return userDAO.getUsersList();
    }


}
