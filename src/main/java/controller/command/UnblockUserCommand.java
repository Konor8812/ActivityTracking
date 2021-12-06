package controller.command;

import service.implementations.UserService;

import javax.servlet.http.HttpServletRequest;

public class UnblockUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        UserService userService = new UserService();
        int userId = Integer.parseInt(req.getParameter("userId"));
        userService.changeUsersStatus(userId, false);
        return CommandFactory.getInstance().getCommand("showUsers").execute(req);
    }
}
