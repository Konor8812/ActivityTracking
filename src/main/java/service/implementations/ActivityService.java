package service.implementations;

import model.dao.ActivityDAO;
import model.dao.DAOFactory;
import model.entity.Activity;

import model.exception.ServiceException;
import service.entityExtention.ActivityExtension;

import java.util.List;

public class ActivityService implements ActivityExtension {

    private final DAOFactory daoFactory = DAOFactory.getInstance();
    private ActivityDAO activityDAO = daoFactory.getActivityDAO();

    @Override
    public void wasTakenOrCompleted(int activityId, boolean increment) {
        activityDAO.changeTakenByAmount(activityId, increment);
    }

    @Override
    public double getRewardForActivity(int activityId) {
        return activityDAO.getActivityById(activityId).getReward();
    }

    @Override
    public Activity getActivityByName(String name) {
        return activityDAO.getActivityByName(name);
    }

    @Override
    public List<Activity> getFiveItemsAsList(int number) {
        return activityDAO.getFiveActivities(number);
    }

    @Override
    public Activity getItemById(Integer id)  {
        return activityDAO.getActivityById(id);
    }

    @Override
    public void add(Activity entity) throws ServiceException {
        activityDAO.insertActivity(entity);
    }

    @Override
    public Activity update(Activity entity) {
        return activityDAO.updateActivity(entity);
    }

    @Override
    public boolean delete(Integer id) {
        return activityDAO.deleteActivity(id);
    }

    @Override
    public List<Activity> getAllItemsAsList() {
        return activityDAO.getActivitiesList();
    }
}
