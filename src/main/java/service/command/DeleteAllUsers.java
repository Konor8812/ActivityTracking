package service.command;

import model.dao.DaoFactory;
import model.dao.UserDAO;

public class DeleteAllUsers implements Command{
    private final Object[] params;


    public DeleteAllUsers(Object... params) {
        this.params = params;
    }

    @Override
    public String execute() {
        UserDAO userDAO = DaoFactory.getInstance().getUserDAO();
        userDAO.deleteAllUsers();

        return "index.jsp";
    }
}
