package controller.command;

import service.implementations.UserActivityService;

import javax.servlet.http.HttpServletRequest;

public class DenyApproval implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        UserActivityService userActivityService = new UserActivityService();
        int userId = Integer.parseInt(req.getParameter("userId"));
        int activityId = Integer.parseInt(req.getParameter("activityId"));

        userActivityService.denyApprovalActivityForUser(userId, activityId);

        return CommandFactory.getInstance().getCommand("getUsersRequests",req, null).execute(req);
    }
}
