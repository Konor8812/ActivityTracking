package service.command;

import javax.servlet.http.HttpServletRequest;

public class HideUsersActivities implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        req.getSession().setAttribute("shouldShowUsersActivities", false);
        return "Profile.jsp";
    }
}
