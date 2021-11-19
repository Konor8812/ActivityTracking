package controller.command;

import model.dao.UserActivityDAO;
import model.entity.Activity;
import model.entity.User;
import service.implementations.UserActivityService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowUsersActivities implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        req.getSession().setAttribute("shouldShowUsersActivities", true);
        User user = (User)req.getSession().getAttribute("regedAs");
        UserActivityService userActivityService = new UserActivityService();

        List<Activity> usersActivities = userActivityService.getUsersActivities(user.getId());
        req.getSession().setAttribute("usersActivities", usersActivities);
        return "Profile.jsp";
    }
}
