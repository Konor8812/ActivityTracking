package service.command;

import model.dao.DaoFactory;
import model.dao.UserActivityDAO;
import model.dao.UserDAO;
import model.entity.User;

import javax.servlet.http.HttpServletRequest;

public class RegisterActivityForUser implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        req.getSession().setAttribute("activityTaken", false);
        UserActivityDAO uad = UserActivityDAO.getInstance();
        String userId = req.getParameter("userId");
        String activityId = req.getParameter("activityId");
        if(!uad.checkIfUserHasThisActivity(userId, activityId)) {
            uad.regActivityForUser(userId, activityId);
            User user = (User) req.getSession().getAttribute("regedAs");
            user.incrementActivitiesAmount();
            UserDAO ud = DaoFactory.getInstance().getUserDAO();
            ud.activityTakenByUser(userId);
            req.getSession().setAttribute("regedAs", user);
            return "Profile.jsp";
        } else{
            req.getSession().setAttribute("activityTaken", true);
            return "User.jsp";
        }
    }
}
