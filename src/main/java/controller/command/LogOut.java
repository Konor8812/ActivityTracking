package controller.command;

import javax.servlet.http.HttpServletRequest;

public class LogOut implements Command{
    public LogOut() {    }

    @Override
    public String execute(HttpServletRequest req) {

        req.getSession().removeAttribute("regedAs");
        return "index.jsp";
    }
}
