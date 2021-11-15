package service.command;

import javax.servlet.http.HttpServletRequest;

public class HideActivityDescription implements Command {
    @Override
    public String execute(HttpServletRequest req) {

        req.getSession().setAttribute("shouldShowTags", false);
        return "Admin.jsp";
    }
}
