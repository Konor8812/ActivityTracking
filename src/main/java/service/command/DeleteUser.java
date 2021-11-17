package service.command;

import model.dao.DaoFactory;
import model.dao.UserDAO;


import javax.servlet.http.HttpServletRequest;


public class DeleteUser implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        CommandFactory cf = CommandFactory.getInstance();
        UserDAO ud = DaoFactory.getInstance().getUserDAO();
        ud.deleteUser(req.getParameter("login"));

        return cf.getCommand("showUsers", req, null).execute(req);
    }
}
