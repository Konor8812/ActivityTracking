package controller.command;

import model.entity.User;
import model.util.Util;

import javax.servlet.http.HttpServletRequest;

public class ReturnToMainCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        Util.removeIndexAttributes(req);

        User user = (User)req.getSession().getAttribute("regedAs");

        return user.getRole().equals("user") ? "User.jsp" : "Admin.jsp";
    }
}
