package controller.command;

import model.entity.Activity;
import model.exception.ServiceException;
import service.implementations.ActivityService;

import javax.servlet.http.HttpServletRequest;

public class InsertActivity implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        req.getSession().removeAttribute("activityExists");
        Activity activity = new Activity();
        activity.setName(req.getParameter("name"));
        activity.setDuration(req.getParameter("duration"));
        activity.setReward(Double.parseDouble(req.getParameter("reward")));
        activity.setDescription(req.getParameter("description"));

        ActivityService activityService = new ActivityService();
        try {
            activityService.add(activity);
        } catch (ServiceException e) {
            req.getSession().setAttribute("activityExists", true);
        }
        return CommandFactory.getInstance().getCommand("showActivities", req, null).execute(req);
    }
}
