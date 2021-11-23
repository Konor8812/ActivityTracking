package controller.command;

import model.database.Util;
import model.entity.User;
import model.exception.ServiceException;
import service.implementations.UserService;

import javax.servlet.http.HttpServletRequest;

public class RegUser implements  Command{

    @Override
    public String execute(HttpServletRequest req) {
        String login = req.getParameter("login");
        String pass = req.getParameter("password");
        User user = new User();
        String encryptedPassword = Util.encodePassword(pass);
        user.setLogin(login);
        user.setPassword(encryptedPassword);

        UserService userService = new UserService();
        try {
            userService.add(user);
            user = userService.getUserByLoginAndPassword(login,encryptedPassword);
            req.getSession().setAttribute("regedAs", user);
            CommandFactory cf = CommandFactory.getInstance();
            req.getSession().removeAttribute("loginError");
            req.getSession().removeAttribute("regError");
            req.getSession().removeAttribute("userIsBlocked");
            return cf.getCommand("showActivities", req, null).execute(req);

        } catch(ServiceException e){
            req.getSession().removeAttribute("loginError");
            req.getSession().removeAttribute("userIsBlocked");
            req.getSession().setAttribute("regError", true);
            return "index.jsp";
        }
    }
}
