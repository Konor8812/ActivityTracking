package controller.command;

import model.database.Util;
import model.entity.User;
import service.implementations.UserService;

import javax.servlet.http.HttpServletRequest;

public class LoginUser implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String encryptedPassword = Util.encodePassword(password);
        UserService us = new UserService();
        try {
            User user = us.getUserByLoginAndPassword(login, encryptedPassword);

            req.getSession().removeAttribute("loginError");
            req.getSession().removeAttribute("regError");

            String role = user.getRole();
            if(user.getStatus().equals("blocked")){
                req.getSession().setAttribute("userIsBlocked", true);
                return "index.jsp";
            }
            req.getSession().removeAttribute("userIsBlocked");
            req.getSession().setAttribute("regedAs", user);
            if (role.equals("user")) {
                return CommandFactory.getInstance().getCommand("showActivities", req, null).execute(req);
            } else if (role.equals("admin")) {
                return CommandFactory.getInstance().getCommand("showUsers", req, null).execute(req);
            }

        } catch(Exception e){
            req.getSession().setAttribute("loginError", true);
            req.getSession().removeAttribute("regError");
            return "index.jsp";
        }
        return "";
    }
}
