package controller.command;

import model.entity.User;
import model.util.Util;
import service.implementations.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowUsersListCommand implements Command{

    @Override
    public String execute(HttpServletRequest req) {
        Util.removeActivityRelatedAttributes(req);

        UserService userService = new UserService();
        List<User> users = userService.getAllItemsAsList();

        req.getSession().removeAttribute("blockedUsers");
        req.getSession().setAttribute("users", users);
        req.getSession().setAttribute("shouldShowUsers", true);
        req.getSession().setAttribute("shouldShowActivities", false);
        req.getSession().setAttribute("shouldShowBlockedUsers", false);

        return "Admin.jsp";
    }

}
