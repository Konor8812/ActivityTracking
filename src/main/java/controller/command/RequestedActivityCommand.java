package controller.command;

import model.exception.ServiceException;
import service.implementations.UserActivityService;

import javax.servlet.http.HttpServletRequest;

public class RequestedActivityCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {

        req.getSession().removeAttribute("activityTaken");
        int userId = Integer.parseInt(req.getParameter("userId"));
        int activityId = Integer.parseInt(req.getParameter("activityId"));

        UserActivityService userActivityService = new UserActivityService();
        try {
            userActivityService.userRequestedActivity(userId, activityId);
            return CommandFactory.getInstance().getCommand("showUsersActivities").execute(req);
        } catch(ServiceException e){
            req.getSession().setAttribute("activityTaken", true);
            return "User.jsp";
        }
    }
}
