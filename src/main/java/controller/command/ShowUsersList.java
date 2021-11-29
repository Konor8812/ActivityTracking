package controller.command;

import model.entity.User;
import service.implementations.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowUsersList implements Command{

    @Override
    public String execute(HttpServletRequest req) {

        UserService userService = new UserService();
        List<User> users = userService.getAllItemsAsList();
        req.getSession().removeAttribute("wrongDurationFormat");
        req.getSession().removeAttribute("blockedUsers");
        req.getSession().setAttribute("users", users);
        req.getSession().setAttribute("shouldShowUsers", true);
        req.getSession().setAttribute("shouldShowActivities", false);
        req.getSession().setAttribute("shouldShowBlockedUsers", false);
        return "Admin.jsp";
    }

}
