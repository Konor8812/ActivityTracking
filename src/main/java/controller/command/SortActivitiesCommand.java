package controller.command;

import model.util.Util;
import model.entity.Activity;

import javax.servlet.http.HttpServletRequest;

import java.util.List;


public class SortActivitiesCommand implements Command{
    @Override
    public String execute(HttpServletRequest req) {

        String sortBy = req.getParameter("sortBy");
        List<Activity> activities = (List<Activity>) req.getSession().getAttribute("activities");

        activities = Util.sortBy(activities, sortBy, (String) req.getSession().getAttribute("language"));

        req.getSession().setAttribute("activities", activities);
        req.setAttribute("sorted", true);
        return CommandFactory.getInstance().getCommand("showActivities").execute(req);
    }

}
