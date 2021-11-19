package controller.command;

import javax.servlet.http.HttpServletRequest;

public class LogOut implements Command{

    @Override
    public String execute(HttpServletRequest req) {

        req.getSession().removeAttribute("regedAs");
        req.getSession().removeAttribute("activityTaken");
        return "index.jsp";
    }
}
