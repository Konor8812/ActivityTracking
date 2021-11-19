package controller.command;

import model.entity.User;
import model.exception.ServiceException;
import service.implementations.UserService;

import javax.servlet.http.HttpServletRequest;

public class RegUser implements  Command{
    public RegUser() {
    }

    @Override
    public String execute(HttpServletRequest req) {
        String login = req.getParameter("login");
        String pass = req.getParameter("password");
        User user = new User();
        user.setLogin(login);
        user.setPassword(pass);

        UserService userService = new UserService();
        try {
            userService.add(user);
            req.getSession().setAttribute("regedAs", user);
            CommandFactory cf = CommandFactory.getInstance();
            req.getSession().removeAttribute("loginError");
            req.getSession().removeAttribute("regError");
            return cf.getCommand("showActivities", req, null).execute(req);

        } catch(ServiceException e){
            req.getSession().removeAttribute("loginError");
            req.getSession().setAttribute("regError", true);
            return "index.jsp";
        }
    }
}
