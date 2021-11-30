package controller.command;

import model.dao.UserActivityDAO;
import model.entity.Activity;
import model.entity.User;
import service.implementations.UserActivityService;
import service.implementations.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DeleteUsersActivity implements Command {
    @Override
    public String execute(HttpServletRequest req) {

        User user = (User)req.getSession().getAttribute("regedAs");
        int activityId = Integer.parseInt(req.getParameter("activityId"));
        boolean wasCompleted = Boolean.parseBoolean(req.getParameter("wasCompleted"));

        UserActivityService userActivityService = new UserActivityService();

        if(wasCompleted) {
            userActivityService.activityCompleted(user.getId(), activityId);
        } else{
            userActivityService.activityGaveUp(user.getId(), activityId);
        }

        UserService userService = new UserService();
        try {
            user = userService.getItemById(user.getId());
        }catch(Exception e){
            //not gonna happen
        }
        req.getSession().setAttribute("shouldShowUsersActivities", true);
        List<Activity> usersActivities = userActivityService.getUsersActivities(user.getId());
        req.getSession().setAttribute("regedAs", user);
        req.getSession().setAttribute("usersActivities", usersActivities);

        return CommandFactory.getInstance().getCommand("showProfile").execute(req);
    }
}
