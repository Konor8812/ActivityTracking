package service.command;

import model.dao.DaoFactory;
import model.dao.UserDAO;
import model.entity.User;

import javax.servlet.http.HttpServletRequest;

public class ChangePass implements Command{

    @Override
    public String execute(HttpServletRequest req) {
        if(req.getSession().getAttribute("showChangePassField") == null){
            req.getSession().setAttribute("showChangePassField", true);
            return "Profile.jsp";
        }

        User user = (User)req.getSession().getAttribute("regedAs");
        String newPassword = req.getParameter("newPassword");
        UserDAO ud = DaoFactory.getInstance().getUserDAO();
        ud.changeUserPassword(user.getId(), newPassword);
        user.setPassword(newPassword);
        req.getSession().setAttribute("regedAs", user);
        req.getSession().removeAttribute("showChangePassField");
        return "Profile.jsp";
    }
}
