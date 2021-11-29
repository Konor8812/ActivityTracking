package controller.command;


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
                com = new ApproveActivityForUser();
                break;
            case "showProfile":
                com = new ShowProfile();
                break;
            case "changePass":
                com = new ChangePass();
                break;
            case "returnToMain":
                com = new ReturnToMain();
                break;
            case "showUsersActivities":
                com = new ShowUsersActivities();
                break;
            case "deleteUsersActivity":
                com = new DeleteUsersActivity();
                break;
            case "deleteActivity":
                com = new DeleteActivity();
                break;
            case "hideUsersActivities":
                com = new HideUsersActivities();
                break;
            case "blockUser":
                com = new BlockUser();
                break;
            case "unblockUser":
                com = new UnblockUser();
                break;
            case "showOnlyBlockedUsers":
                com = new GetAllBlocked();
                break;
            case "reqActivity":
                com = new RequestedActivity();
                break;
            case "getUsersRequests":
                com = new GetUsersRequests();
                break;
            case "denyApproval":
                com = new DenyApproval();
                break;
            case "sortActivities":
                com = new SortActivities();
                break;
            case "setLanguage":
                com = new SetLanguage();
                break;
        }
        return com;
    }

}
