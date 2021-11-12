package service.command;

import model.dao.DaoFactory;
import model.dao.UserDAO;
import model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowUsersList implements Command{


    public ShowUsersList() { }

    @Override
    public String execute(HttpServletRequest req) {

        UserDAO userDAO = DaoFactory.getInstance().getUserDAO();
        List<User> users = userDAO.getUsersList();
        req.getSession().setAttribute("users", users);
        req.getSession().setAttribute("shouldPrintUsers", true);
        return null;
    }


}
