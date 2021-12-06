package controller.command;

import model.util.Util;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command{

    @Override
    public String execute(HttpServletRequest req) {

        Util.removeAllAttributes(req);

        return "index.jsp";
    }
}
