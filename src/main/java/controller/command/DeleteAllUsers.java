package controller.command;

import service.implementations.UserService;

import javax.servlet.http.HttpServletRequest;

public class DeleteAllUsers implements Command{


    public DeleteAllUsers() { }

    @Override
    public String execute(HttpServletRequest req) {

        UserService userService = new UserService();
        userService.deleteAllUsers();

        req.getSession().removeAttribute("users");
        req.getSession().setAttribute("shouldPrintUsers", false);

        return "Admin.jsp";
    }
}
