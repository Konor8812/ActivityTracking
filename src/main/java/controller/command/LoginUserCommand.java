package controller.command;

import controller.InputDataValidator;
import model.util.Util;
import model.entity.User;
import service.implementations.UserService;

import javax.servlet.http.HttpServletRequest;

public class LoginUserCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        Util.removeIndexAttributes(req);
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (InputDataValidator.validatePass(password) && InputDataValidator.validateLogin(login)) {
            String encryptedPassword = Util.encodePassword(password);
            UserService us = new UserService();
            try {
                User user = us.getUserByLoginAndPassword(login, encryptedPassword);

                String role = user.getRole();
                if (user.getStatus().equals("blocked")) {
                    req.getSession().setAttribute("userIsBlocked", true);
                    return "LogIn.jsp";
                }
                Util.removeIndexAttributes(req);
                req.getSession().setAttribute("regedAs", user);
                if (role.equals("user")) {
                    return CommandFactory.getInstance().getCommand("showActivities").execute(req);
                } else if (role.equals("admin")) {
                    return CommandFactory.getInstance().getCommand("showUsers").execute(req);
                }else{
                    return "UnknownError.jsp";
                }

            } catch (Exception e) {
                req.getSession().setAttribute("loginError", true);
                return "LogIn.jsp";
            }
        }else{
            req.getSession().setAttribute("loginError", true);
            return "LogIn.jsp";
        }
    }
}
