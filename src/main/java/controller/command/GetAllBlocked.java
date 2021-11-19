package controller.command;

import model.entity.User;
import service.implementations.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GetAllBlocked implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        UserService userService = new UserService();
        List<User> blockedUsers = userService.getAllBlocked();
        req.getSession().setAttribute("blockedUsers", blockedUsers);
        req.getSession().setAttribute("shouldShowUsers", false);
        req.getSession().setAttribute("shouldShowActivities", false);
        req.getSession().setAttribute("shouldShowBlockedUsers", true);
        return "Admin.jsp";
    }
}
