package controller.command;

import model.entity.User;

import javax.servlet.http.HttpServletRequest;

public class ReturnToMain implements Command {
    @Override
    public String execute(HttpServletRequest req) {

        User user = (User)req.getSession().getAttribute("regedAs");
        req.getSession().removeAttribute("shouldShowUsersActivities");
        return user.getRole().equals("user") ? "User.jsp" : "Admin.jsp";
    }
}
