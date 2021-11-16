package service.command;

import model.entity.User;

import javax.servlet.http.HttpServletRequest;

public class HideActivityDescription implements Command {
    @Override
    public String execute(HttpServletRequest req) {

        req.getSession().setAttribute("shouldShowTags", false);
        User user =(User)req.getSession().getAttribute("regedAs");

        if(user.getRole().equals("admin")) {
            return "Admin.jsp";
        } else{
            return "User.jsp";
        }
    }
}
