package service.command;


import model.dao.DaoFactory;
import model.dao.UserDAO;

public class RegUser implements  Command{
    private final Object[] params;


    public RegUser(Object... params) {
        this.params = params;
    }

    @Override
    public String execute() {
        String login = (String)params[0];
        String pass = (String)params[1];
        System.out.printf("regUser login ==> %s pass ==> %s", login, pass);
        UserDAO userDAO = DaoFactory.getInstance().getUserDAO();

        userDAO.insertUser(login, pass);


        return "regedPage.jsp";
    }
}
