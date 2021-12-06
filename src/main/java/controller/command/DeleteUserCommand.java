package controller.command;

import service.implementations.UserService;


import javax.servlet.http.HttpServletRequest;


public class DeleteUserCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {

        CommandFactory cf = CommandFactory.getInstance();

        UserService userService = new UserService();
        int id = Integer.parseInt(req.getParameter("userId"));
        userService.delete(id);

        return cf.getCommand("showUsers").execute(req);
    }
}
