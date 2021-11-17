package service.command;

import model.dao.DaoFactory;
import model.dao.UserActivityDAO;
import model.entity.Activity;
import model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DeleteUsersActivity implements Command {
    @Override
    public String execute(HttpServletRequest req) {

        UserActivityDAO uad = UserActivityDAO.getInstance();
        User user = (User)req.getSession().getAttribute("regedAs");
        String userId = Integer.toString(user.getId());
        uad.deleteUsersActivity(userId, req.getParameter("activityId"));
        user.decrementActivitiesAmount();

        req.getSession().setAttribute("shouldShowUsersActivities", true);
        List<Activity> usersActivities = uad.getUsersActivities(user.getId());
        req.getSession().setAttribute("regedAs", user);
        req.getSession().setAttribute("usersActivities", usersActivities);

        return "Profile.jsp";
    }
}
