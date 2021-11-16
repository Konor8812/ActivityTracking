package service.command;


import model.dao.DaoFactory;
import model.dao.UserDAO;
import model.entity.User;

import javax.servlet.http.HttpServletRequest;

public class RegUser implements  Command{
    public RegUser() {
    }

    @Override
    public String execute(HttpServletRequest req) {
        String login = req.getParameter("login");
        String pass = req.getParameter("password");

        UserDAO userDAO = DaoFactory.getInstance().getUserDAO();
        if(!userDAO.checkIfLoginExists(login)) {
            User user = userDAO.regUser(login, pass);
            req.getSession().setAttribute("regedAs", user);
            CommandFactory.getInstance().getCommand("showActivities", req, null).execute(req);
            System.out.println("returning user.jsp");
            return "User.jsp";
        } else{
            req.getSession().setAttribute("loginError", false);
            req.getSession().setAttribute("regError", true);
            return "index.jsp";
        }
    }
}
