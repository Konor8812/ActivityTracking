package service.command;

import model.dao.DaoFactory;
import model.dao.UserDAO;

import javax.servlet.http.HttpServletRequest;

public class DeleteAllUsers implements Command{


    public DeleteAllUsers() { }

    @Override
    public String execute(HttpServletRequest req) {
        UserDAO userDAO = DaoFactory.getInstance().getUserDAO();
        userDAO.deleteAllUsers();
        req.setAttribute("users", null);
        req.getSession().setAttribute("shouldPrintUsers", false);
        return "regedAdminPage.jsp";
    }
}
