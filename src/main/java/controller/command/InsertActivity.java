package controller.command;

import controller.InputDataValidator;
import model.entity.Activity;
import model.exception.ServiceException;
import service.implementations.ActivityService;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InsertActivity implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        req.getSession().removeAttribute("activityExists");
        req.getSession().removeAttribute("wrongDurationFormat");

        String name = req.getParameter("name");
        String duration = req.getParameter("duration");
        String description = req.getParameter("description");
        String reward = req.getParameter("reward");
        //Double reward = Double.parseDouble();
        Activity activity = null;


        if(InputDataValidator.validateActivity(name, duration, description, reward)){
            activity = new Activity();
            activity.setName(name);
            activity.setDuration(duration);
            activity.setReward(Double.parseDouble(reward));
            activity.setDescription(description);
        } else {
            req.getSession().setAttribute("wrongDataFormat", true);
            return CommandFactory.getInstance().getCommand("showActivities", req, null).execute(req);
        }

        ActivityService activityService = new ActivityService();
        try {
            activityService.add(activity);
        } catch (ServiceException e) {
            req.getSession().setAttribute("activityExists", true);
        }
        return CommandFactory.getInstance().getCommand("showActivities", req, null).execute(req);
    }
}
