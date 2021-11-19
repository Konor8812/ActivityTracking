package controller.command;

import model.entity.Activity;
import model.entity.User;
import service.implementations.ActivityService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowActivitiesList implements Command {

    public ShowActivitiesList() {
    }


    @Override
    public String execute(HttpServletRequest req) {

        ActivityService activityService = new ActivityService();
        List<Activity> activities = activityService.getAllItemsAsList();

        req.getSession().setAttribute("activities", activities);
        req.getSession().setAttribute("shouldShowActivities", true);
        req.getSession().setAttribute("shouldShowUsers", false);
        req.getSession().setAttribute("shouldShowTags", false);

        User user = (User)req.getSession().getAttribute("regedAs");
        return user.getRole().equals("user") ? "User.jsp" : "Admin.jsp";
    }
}
