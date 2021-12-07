package controller.command;

import javax.servlet.http.HttpServletRequest;

public class SetLanguageCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        String lang = req.getParameter("lang");

        req.getSession().setAttribute("language", lang);
        return "GreetingPage.jsp";
    }
}
