package controller.command;

import service.implementations.ActivityService;
import service.implementations.UserService;

import javax.servlet.http.HttpServletRequest;

public class DeleteActivity implements Command {
    @Override
    public String execute(HttpServletRequest req) {

        CommandFactory cf = CommandFactory.getInstance();

        ActivityService activityService = new ActivityService();
        int activityId = Integer.parseInt(req.getParameter("activityId"));

        UserService userService = new UserService();

        userService.updateActivitiesAmountAfterActivityWasDeleted(activityId);

        activityService.delete(activityId);

        return cf.getCommand("showActivities", req, null).execute(req);
    }
}
