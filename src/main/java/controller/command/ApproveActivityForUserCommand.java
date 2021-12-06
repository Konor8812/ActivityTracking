package controller.command;

import service.implementations.UserActivityService;

import javax.servlet.http.HttpServletRequest;

public class ApproveActivityForUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        int userId = Integer.parseInt(req.getParameter("userId"));
        int activityId = Integer.parseInt(req.getParameter("activityId"));

        UserActivityService userActivityService = new UserActivityService();
        userActivityService.approveActivityForUser(userId, activityId);

        return CommandFactory.getInstance().getCommand("getUsersRequests").execute(req);

    }
}
