package service.command;

import model.dao.UserActivityDAO;
import model.entity.Activity;
import model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowUsersActivities implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        req.getSession().setAttribute("shouldShowUsersActivities", true);
        User user = (User)req.getSession().getAttribute("regedAs");
        List<Activity> usersActivities = UserActivityDAO.getInstance().getUsersActivities(user.getId());
        req.getSession().setAttribute("usersActivities", usersActivities);
        return "Profile.jsp";
    }
}
