package controller.command;

import service.implementations.UserService;

import javax.servlet.http.HttpServletRequest;

public class DeleteAllUsersCommand implements Command{


    public DeleteAllUsersCommand() { }

    @Override
    public String execute(HttpServletRequest req) {

        UserService userService = new UserService();
        userService.deleteAllUsers();

        req.getSession().removeAttribute("users");
        req.getSession().setAttribute("shouldPrintUsers", false);

        return "Admin.jsp";
    }
}
