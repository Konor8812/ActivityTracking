package controller.command;

import model.entity.Activity;
import model.entity.User;
import model.util.Util;
import service.implementations.ActivityService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowActivitiesList implements Command {

    public ShowActivitiesList() {
    }


    @Override
    public String execute(HttpServletRequest req) {
        int numberOfSeries = 1;
        try {
            numberOfSeries = Integer.parseInt(req.getParameter("num"));
        }catch(Exception e){
            //means should show first page
        }
        boolean isSorted = false;
        try {
            isSorted = (boolean) req.getAttribute("sorted");
        } catch(NullPointerException e){
            // means that list wasn't sorted
            // order by time activity was added to db
        }
        String language = (String) req.getSession().getAttribute("language");
        System.out.println(language);
        if(!isSorted){
            ActivityService activityService = new ActivityService();
            List<Activity> activities = activityService.getAllItemsAsList(numberOfSeries);
            for(Activity activity: activities){
                activity.setDescription(Util.getDescriptionAccordingToLang(activity.getDescription(), language));
            }

            req.getSession().setAttribute("numberOfSeries", numberOfSeries);
            req.getSession().setAttribute("activitiesAmount", activities.size());
            req.getSession().setAttribute("activities", activities);
        }

        req.getSession().setAttribute("shouldShowActivities", true);
        req.getSession().setAttribute("shouldShowUsers", false);
        req.getSession().setAttribute("shouldShowTags", false);
        User user = (User)req.getSession().getAttribute("regedAs");
        return user.getRole().equals("user") ? "User.jsp" : "Admin.jsp";
    }
}
