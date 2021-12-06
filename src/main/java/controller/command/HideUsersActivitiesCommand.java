package controller.command;

import javax.servlet.http.HttpServletRequest;

public class HideUsersActivitiesCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {

        req.getSession().removeAttribute("shouldShowUsersActivities");
        return "Profile.jsp";
    }
}
