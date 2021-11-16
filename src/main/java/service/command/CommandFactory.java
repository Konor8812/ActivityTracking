package service.command;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CommandFactory {
    private static CommandFactory instance;

    private CommandFactory() {
    }

    public static synchronized CommandFactory getInstance() {
        if (instance == null) {
            instance = new CommandFactory();
        }
        return instance;
    }

    public void executeCommand(HttpServletRequest req, HttpServletResponse resp, String command) throws IOException {
        Command com = getCommand(command, req, resp);
        String next = com.execute(req);

        resp.sendRedirect(next);
    }

    public Command getCommand(String name, HttpServletRequest req, HttpServletResponse resp) {
        Command com = null;
        switch (name) {
            case "regUser":
                com = new RegUser();
                break;
            case "deleteAllUsers":
                com = new DeleteAllUsers();
                break;
            case "deleteUser":
                com = new DeleteUser();
                break;
            case "showUsers":
                com = new ShowUsersList();
                break;
            case "logIn":
                com = new LoginUser();
                break;
            case "showActivities":
                com = new ShowActivitiesList();
                break;
            case "addActivity":
                com = new InsertActivity();
                break;
            case "getActivitiesDescription":
                com = new GetActivityDescription();
                break;
            case "hideActivitiesDescription":
                com = new HideActivityDescription();
                break;
            case "logOut":
                com = new LogOut();
                break;
            case "regActivityForUser":
                com = new RegisterActivityForUser();
        }
        return com;
    }

}
