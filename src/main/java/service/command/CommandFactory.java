package service.command;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CommandFactory {
    private static CommandFactory instance;

    private CommandFactory(){}

    public static synchronized CommandFactory getInstance(){
        if(instance == null){
            instance = new CommandFactory();
        }
        return  instance;
    }

    public Command getCommand(String name, HttpServletRequest req, HttpServletResponse resp){
        Command com = null;
        switch(name){
            case "regUser":
                com = new RegUser(req.getParameter("login"), req.getParameter("password"));
                break;

        }
        return com;
    }

    public void executeCommand(HttpServletRequest req, HttpServletResponse resp, String command) throws IOException {
        Command com = this.getCommand(command, req, resp);
        String next = com.execute();
        resp.sendRedirect(next);

    }
}
