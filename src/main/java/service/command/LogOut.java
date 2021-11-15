package service.command;

import javax.servlet.http.HttpServletRequest;

public class LogOut implements Command{
    public LogOut() {    }

    @Override
    public String execute(HttpServletRequest req) {

        req.getSession().setAttribute("login", "");
        req.getSession().setAttribute("role", "");

        return "index.jsp";
    }
}
