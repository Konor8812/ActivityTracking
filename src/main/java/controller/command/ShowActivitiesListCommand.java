package controller.command;

import model.entity.Activity;
import model.entity.User;
import model.util.Util;
import service.implementations.ActivityService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowActivitiesListCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {

        int numberOfSeries = 0;
        try{
            numberOfSeries = Integer.parseInt(req.getParameter("numberOfSeries"));
        }catch(Exception e){
            //after added new activity or localization key, should return to begin
        }

        boolean isSorted = false;
        try {
            isSorted = (boolean) req.getAttribute("sorted");
        } catch(NullPointerException e){
            // means that list wasn't sorted
            // order by time activity was added to db
        }
        String language = (String) req.getSession().getAttribute("language");
        if(!isSorted){
            ActivityService activityService = new ActivityService();
            List<Activity> activities = activityService.getFiveItemsAsList(numberOfSeries);
            int totalActivitiesAmount;

            try{
                totalActivitiesAmount = (int) req.getSession().getAttribute("totalActivitiesAmount");
            }catch(Exception e){
                totalActivitiesAmount = activityService.getAllItemsAsList().size();
            }
            req.getSession().setAttribute("totalActivitiesAmount", totalActivitiesAmount);

            for(Activity activity: activities){
                activity.setName(Util.getNameAccordingToLang(activity.getName(), language));
                activity.setDescription(Util.getDescriptionAccordingToLang(activity.getDescription(), language));
                activity.setDuration(Util.getDurationAccordingToLang(activity.getDuration(), language));
            }

            req.getSession().setAttribute("numberOfSeries", numberOfSeries);
            req.getSession().setAttribute("activities", activities);

        }

        req.getSession().setAttribute("shouldShowActivities", true);
        req.getSession().setAttribute("shouldShowUsers", false);
        req.getSession().setAttribute("shouldShowTags", false);


        User user = (User)req.getSession().getAttribute("regedAs");
        return user.getRole().equals("user") ? "User.jsp" : "Admin.jsp";
    }
}
