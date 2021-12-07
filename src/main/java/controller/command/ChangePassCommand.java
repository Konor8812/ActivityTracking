package controller.command;

import model.entity.User;
import model.util.Util;
import service.implementations.UserService;

import javax.servlet.http.HttpServletRequest;

public class ChangePassCommand implements Command{

    @Override
    public String execute(HttpServletRequest req) {
        if(req.getSession().getAttribute("showChangePassField") == null){
            req.getSession().setAttribute("showChangePassField", true);
            return "Profile.jsp";
        }

        User user = (User)req.getSession().getAttribute("regedAs");
        int id = user.getId();
        String newPassword = Util.encodePassword(req.getParameter("newPassword"));

        UserService userService = new UserService();
        userService.changePass(id, newPassword);

        user.setPassword(newPassword);
        req.getSession().setAttribute("regedAs", user);
        req.getSession().removeAttribute("showChangePassField");
        return "Profile.jsp";
    }
}
