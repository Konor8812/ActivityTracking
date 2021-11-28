package controller.command;

import javax.servlet.http.HttpServletRequest;

public class SetLanguage implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        String lang = req.getParameter("lang");



        req.getSession().setAttribute("language", lang);
        System.out.println(req.getSession().getAttribute("language"));
        return "index.jsp";
    }
}
