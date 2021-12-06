package controller.command;

import controller.InputDataValidator;
import model.entity.Activity;
import model.exception.ServiceException;
import model.util.Util;
import service.implementations.ActivityService;

import javax.servlet.http.HttpServletRequest;

public class InsertActivityCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        req.getSession().removeAttribute("activityExists");
        req.getSession().removeAttribute("wrongDurationFormat");

        String name = req.getParameter("name");
        String duration = req.getParameter("duration");
        String description = req.getParameter("description");
        String reward = req.getParameter("reward");

        Activity activity = null;

        if (InputDataValidator.validateActivity(name, duration, description, reward) ) {

            activity = new Activity();
            activity.setName(name);
            activity.setDuration(duration);
            activity.setReward(Double.parseDouble(reward));
            activity.setDescription(description);
        } else {
            req.getSession().setAttribute("wrongDataFormat", true);
            return CommandFactory.getInstance().getCommand("showActivities").execute(req);
        }

        ActivityService activityService = new ActivityService();
        try {
            activityService.add(activity);

            int totalActivitiesAmount = activityService.getAllItemsAsList().size();

            req.getSession().setAttribute("totalActivitiesAmount", totalActivitiesAmount);

        } catch (ServiceException e) {
            req.getSession().setAttribute("activityExists", true);
        }
        return CommandFactory.getInstance().getCommand("showActivities").execute(req);
    }
}
