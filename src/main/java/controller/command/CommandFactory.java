package controller.command;


import model.util.Util;

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
        Command com = getCommand(command);
        String next = "index.jsp";
        if(Util.checkForSecurity(command, req)) {
            next = com.execute(req);
        }
        resp.sendRedirect(next);
    }

    public Command getCommand(String name) {
        Command com = null;
        switch (name) {
            case "regUser":
                com = new RegUserCommand();
                break;
            case "deleteAllUsers":
                com = new DeleteAllUsersCommand();
                break;
            case "deleteUser":
                com = new DeleteUserCommand();
                break;
            case "showUsers":
                com = new ShowUsersListCommand();
                break;
            case "logIn":
                com = new LoginUserCommand();
                break;
            case "showActivities":
                com = new ShowActivitiesListCommand();
                break;
            case "addActivity":
                com = new InsertActivityCommand();
                break;
            case "getActivitiesDescription":
                com = new GetActivityDescriptionCommand();
                break;
            case "hideActivitiesDescription":
                com = new HideActivityDescriptionCommand();
                break;
            case "logOut":
                com = new LogOutCommand();
                break;
            case "regActivityForUser":
                com = new ApproveActivityForUserCommand();
                break;
            case "showProfile":
                com = new ShowProfileCommand();
                break;
            case "changePass":
                com = new ChangePassCommand();
                break;
            case "returnToMain":
                com = new ReturnToMainCommand();
                break;
            case "showUsersActivities":
                com = new ShowUsersActivitiesCommand();
                break;
            case "deleteUsersActivity":
                com = new DeleteUsersActivityCommand();
                break;
            case "deleteActivity":
                com = new DeleteActivityCommand();
                break;
            case "hideUsersActivities":
                com = new HideUsersActivitiesCommand();
                break;
            case "blockUser":
                com = new BlockUserCommand();
                break;
            case "unblockUser":
                com = new UnblockUserCommand();
                break;
            case "showOnlyBlockedUsers":
                com = new GetAllBlockedCommand();
                break;
            case "reqActivity":
                com = new RequestedActivityCommand();
                break;
            case "getUsersRequests":
                com = new GetUsersRequestsCommand();
                break;
            case "denyApproval":
                com = new DenyApprovalCommand();
                break;
            case "sortActivities":
                com = new SortActivitiesCommand();
                break;
            case "setLanguage":
                com = new SetLanguageCommand();
                break;
            case "loadProperty":
                com = new LoadPropertyCommand();
                break;
            case "searchByTag":
                com = new SearchByTagCommand();
                break;
        }
        return com;
    }

}
