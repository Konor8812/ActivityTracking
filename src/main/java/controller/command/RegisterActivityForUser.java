package controller.command;

import model.dao.UserDAO;
import model.entity.User;
import model.exception.ServiceException;

import service.implementations.UserActivityService;

import javax.servlet.http.HttpServletRequest;

public class RegisterActivityForUser implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        req.getSession().setAttribute("activityTaken", false);
        int userId = Integer.parseInt(req.getParameter("userId"));
        int activityId = Integer.parseInt(req.getParameter("activityId"));
        User user = (User) req.getSession().getAttribute("regedAs");

        UserActivityService userActivityService = new UserActivityService();
        try {
            userActivityService.userTookActivity(userId, activityId);
            user.incrementActivitiesAmount();
            req.getSession().setAttribute("regedAs", user);
            return "Profile.jsp";
        } catch(ServiceException e){
            req.getSession().setAttribute("activityTaken", true);
            return "User.jsp";
        }
    }
}
