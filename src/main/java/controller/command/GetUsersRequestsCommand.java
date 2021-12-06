package controller.command;

import model.entity.Activity;
import model.entity.User;
import model.exception.ServiceException;
import model.util.Util;
import service.implementations.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GetUsersRequestsCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {

        UserService userService = new UserService();
        int userId = Integer.parseInt(req.getParameter("userId"));
        User user = null;
        List<Activity> usersRequestedActivities = null;
        try{
            user = userService.getItemById(userId);
            usersRequestedActivities = userService.getRequestedActivities(userId);

            String lang = (String) req.getSession().getAttribute("language");
            for(Activity activity: usersRequestedActivities){
                activity.setName(Util.getNameAccordingToLang(activity.getName(), lang));
                activity.setDuration(Util.getDurationAccordingToLang(activity.getDuration(), lang));
                activity.setDescription(Util.getDescriptionAccordingToLang(activity.getDescription(), lang));
            }
        } catch(ServiceException e){
            //not gonna happen
        }

        req.getSession().setAttribute("requests", usersRequestedActivities);
        req.getSession().setAttribute("user", user);

        return "UsersRequests.jsp";
    }
}
