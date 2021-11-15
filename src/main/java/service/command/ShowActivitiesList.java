package service.command;

import model.dao.ActivityDAO;
import model.dao.DaoFactory;
import model.entity.Activity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowActivitiesList implements Command {

    public ShowActivitiesList() {
    }


    @Override
    public String execute(HttpServletRequest req) {
        ActivityDAO ad = DaoFactory.getInstance().getActivityDAO();
        List<Activity> activities = ad.getActivitiesList();
        req.getSession().setAttribute("activities", activities);
        req.getSession().setAttribute("shouldShowActivities", true);
        req.getSession().setAttribute("shouldShowUsers", false);
        req.getSession().setAttribute("shouldShowTags", false);
        return "Admin.jsp";
    }
}
