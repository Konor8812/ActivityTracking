package service.command;

import model.dao.DaoFactory;
import model.dao.UserDAO;
import model.entity.User;

import javax.servlet.http.HttpServletRequest;

public class ShowProfile implements Command{
    @Override
    public String execute(HttpServletRequest req) {
        UserDAO ud = DaoFactory.getInstance().getUserDAO();
        User user = (User) req.getSession().getAttribute("regedAs");
        user =  ud.getLogedInUser(user.getLogin(), user.getPassword());
        req.getSession().setAttribute("regedAs", user);
        return "Profile.jsp";
    }
}
