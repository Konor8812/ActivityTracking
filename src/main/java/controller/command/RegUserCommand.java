package controller.command;

import controller.InputDataValidator;
import model.util.Util;
import model.entity.User;
import model.exception.ServiceException;
import service.implementations.UserService;

import javax.servlet.http.HttpServletRequest;

public class RegUserCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {

        Util.removeIndexAttributes(req);
        String login = req.getParameter("login");
        String pass = req.getParameter("password");

        if (InputDataValidator.validatePass(pass) && InputDataValidator.validateLogin(login)) {
            User user = new User();
            String encryptedPassword = Util.encodePassword(pass);
            user.setLogin(login);
            user.setPassword(encryptedPassword);

            UserService userService = new UserService();
            try {
                userService.add(user);
                user = userService.getUserByLoginAndPassword(login, encryptedPassword);
                req.getSession().setAttribute("regedAs", user);
                CommandFactory cf = CommandFactory.getInstance();
                Util.removeIndexAttributes(req);
                return cf.getCommand("showActivities").execute(req);

            } catch (ServiceException e) {
                req.getSession().setAttribute("regError", true);
                return "Registration.jsp";
            }

        }else{
            req.getSession().setAttribute("wrongData", true);
            return "Registration.jsp";
    }
    }
}
