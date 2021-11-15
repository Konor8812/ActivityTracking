package service.command;

import model.dao.ActivityDAO;
import model.entity.Activity;

import javax.servlet.http.HttpServletRequest;

public class GetActivityDescription implements Command {

    @Override
    public String execute(HttpServletRequest req) {

        req.getSession().setAttribute("shouldShowTags", true);
        return "Admin.jsp";
    }
}
