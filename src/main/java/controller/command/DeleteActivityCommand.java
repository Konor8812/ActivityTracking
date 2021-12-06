package controller.command;

import service.implementations.ActivityService;
import service.implementations.UserService;

import javax.servlet.http.HttpServletRequest;

public class DeleteActivityCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {

        CommandFactory cf = CommandFactory.getInstance();

        ActivityService activityService = new ActivityService();
        int activityId = Integer.parseInt(req.getParameter("activityId"));

        UserService userService = new UserService();

        userService.updateActivitiesAmountAfterActivityWasDeleted(activityId);

        activityService.delete(activityId);

        int totalActivitiesAmount = activityService.getAllItemsAsList().size();
        req.getSession().setAttribute("totalActivitiesAmount", totalActivitiesAmount);

        return cf.getCommand("showActivities").execute(req);
    }
}
