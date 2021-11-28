package controller.command;

import model.util.Util;
import model.entity.Activity;

import javax.servlet.http.HttpServletRequest;

import java.util.List;


public class SortActivities implements Command{
    @Override
    public String execute(HttpServletRequest req) {

        String sortBy = req.getParameter("sortBy");
        List<Activity> activities = (List<Activity>) req.getSession().getAttribute("activities");

        activities = Util.sortBy(activities, sortBy);

        req.getSession().setAttribute("activities", activities);
        req.setAttribute("sorted", true);
        return CommandFactory.getInstance().getCommand("showActivities", req, null).execute(req);
    }

}
