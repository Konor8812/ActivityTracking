package service.command;


import model.dao.DaoFactory;
import model.dao.UserDAO;

import javax.servlet.http.HttpServletRequest;

public class RegUser implements  Command{
    private final Object[] params;


    public RegUser(Object... params) {
        this.params = params;
    }

    @Override
    public String execute(HttpServletRequest req) {
        String login = (String)params[0];
        String pass = (String)params[1];

        System.out.printf("regUser login ==> %s pass ==> %s\n", login, pass);
        UserDAO userDAO = DaoFactory.getInstance().getUserDAO();
        userDAO.insertUser(login, pass);

        req.getSession().setAttribute("regedAs", "user");
        req.getSession().setAttribute("login", login);

        return "User.jsp";
    }
}
