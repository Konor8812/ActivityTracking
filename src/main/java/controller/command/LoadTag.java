package controller.command;

import controller.InputDataValidator;
import model.util.Util;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

public class LoadTag implements Command {
    @Override
    public String execute(HttpServletRequest req) {

        String tagName = req.getParameter("tag");
        if(InputDataValidator.validateTag(tagName)){
            String[] splitTagName = Util.splitTagName(tagName);
            Util.loadProperty(splitTagName[0], splitTagName[1], "ru");
            Util.loadProperty(splitTagName[0], splitTagName[0], "en");
        }else{
            req.getSession().setAttribute("wrongTagName", true);
        }
        return CommandFactory.getInstance().getCommand("showActivities", req, null).execute(req);
    }
}
