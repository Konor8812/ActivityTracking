package controller.command;

import model.entity.User;
import model.exception.WrongLoginData;
import service.implementations.UserService;

import javax.servlet.http.HttpServletRequest;

public class ShowProfile implements Command{
    @Override
    public String execute(HttpServletRequest req) {

        UserService userService = new UserService();
        User user = (User)req.getSession().getAttribute("regedAs");

        try {
            user = userService.getUserByLoginAndPassword(user.getLogin(), user.getPassword());
        }catch (WrongLoginData wrongLoginData) {
            // not gonna happen
        }
        req.getSession().setAttribute("regedAs", user);
        return "Profile.jsp";
    }
}
