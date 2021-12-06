package controller.command;


import model.entity.Activity;
import model.entity.User;
import model.util.Util;
import service.implementations.UserActivityService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowUsersActivitiesCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        req.getSession().setAttribute("shouldShowUsersActivities", true);
        User user = (User)req.getSession().getAttribute("regedAs");

        UserActivityService userActivityService = new UserActivityService();

        List<Activity> usersActivities = userActivityService.getUsersActivities(user.getId());
        String lang = (String) req.getSession().getAttribute("language");
        for(Activity activity:usersActivities){
            activity.setName(Util.getNameAccordingToLang(activity.getName(), lang));
            activity.setDuration(Util.getDurationAccordingToLang(activity.getDuration(), lang));
            activity.setDescription(Util.getDescriptionAccordingToLang(activity.getDescription(), lang));
        }

        req.getSession().setAttribute("usersActivities", usersActivities);
        return CommandFactory.getInstance().getCommand("showProfile").execute(req);
    }
}
