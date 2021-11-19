package controller.command;

import model.dao.UserDAO;
import model.entity.User;
import service.implementations.UserService;

import javax.servlet.http.HttpServletRequest;

public class LoginUser implements Command {

    public LoginUser() { }

    @Override
    public String execute(HttpServletRequest req) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserService us = new UserService();
        try {
            User user = us.getUserByLoginAndPassword(login, password);
            req.getSession().removeAttribute("loginError");
            req.getSession().removeAttribute("regError");
            String role = user.getRole();
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
