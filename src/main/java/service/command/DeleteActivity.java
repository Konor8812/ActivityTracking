package service.command;

import model.dao.ActivityDAO;
import model.dao.DaoFactory;
import model.dao.UserDAO;

import javax.servlet.http.HttpServletRequest;

public class DeleteActivity implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        CommandFactory cf = CommandFactory.getInstance();
        ActivityDAO ad = DaoFactory.getInstance().getActivityDAO();
        ad.deleteActivity(req.getParameter("activityId"));
        UserDAO ud = DaoFactory.getInstance().getUserDAO();
        ud.updateActivitiesAmount(req.getParameter("activityId"));

        return cf.getCommand("showActivities", req, null).execute(req);
    }
}
