package controller.command;

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

        String duration = req.getParameter("duration");
        Activity activity = null;

        Matcher m = Pattern.compile("\\d (hours|days)").matcher(duration);
        if(m.find()){
            activity = new Activity();
            activity.setName(req.getParameter("name"));
            activity.setDuration(duration);
            activity.setReward(Double.parseDouble(req.getParameter("reward")));
            activity.setDescription(req.getParameter("description"));
        } else {
            req.getSession().setAttribute("wrongDurationFormat", true);
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
