package controller.command;

import controller.InputDataValidator;
import model.entity.Activity;
import model.util.Util;
import service.implementations.ActivityService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class SearchByTagCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        req.getSession().removeAttribute("invalidTagName");

        String language = (String) req.getSession().getAttribute("language");
        String tagName = req.getParameter("tagName");


        if (InputDataValidator.validateTagName(tagName)) {
            ActivityService activityService = new ActivityService();
            List<Activity> activities = activityService.getAllItemsAsList();
            List<Activity> activitiesWithTag = new ArrayList<>();

            for (Activity ac : activities) {
                String description = Util.getDescriptionAccordingToLang(ac.getDescription(), language);

                if (description.contains(tagName)) {
                    ac.setDescription(Util.getDescriptionAccordingToLang(ac.getDescription(), language));
                    ac.setDuration(Util.getDurationAccordingToLang(ac.getDuration(), language));
                    ac.setName(Util.getNameAccordingToLang(ac.getName(), language));

                    activitiesWithTag.add(ac);
                }
            }

            req.getSession().setAttribute("activitiesByTag", activitiesWithTag);
        } else {
            req.getSession().setAttribute("invalidTagName", true);
        }
        return "TagSearch.jsp";
    }
}
